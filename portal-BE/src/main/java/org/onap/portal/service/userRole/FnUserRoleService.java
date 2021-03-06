/*
 * ============LICENSE_START==========================================
 * ONAP Portal
 * ===================================================================
 * Copyright (C) 2019 AT&T Intellectual Property. All rights reserved.
 * ===================================================================
 * Modifications Copyright (c) 2019 Samsung
 * ===================================================================
 *
 * Unless otherwise specified, all software contained herein is licensed
 * under the Apache License, Version 2.0 (the "License");
 * you may not use this software except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Unless otherwise specified, all documentation contained herein is licensed
 * under the Creative Commons License, Attribution 4.0 Intl. (the "License");
 * you may not use this documentation except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             https://creativecommons.org/licenses/by/4.0/
 *
 * Unless required by applicable law or agreed to in writing, documentation
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ============LICENSE_END============================================
 *
 *
 */

package org.onap.portal.service.userRole;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.transport.http.HTTPException;
import org.onap.portal.domain.db.ep.EpUserRolesRequest;
import org.onap.portal.domain.db.ep.EpUserRolesRequestDet;
import org.onap.portal.domain.db.fn.FnApp;
import org.onap.portal.domain.db.fn.FnRole;
import org.onap.portal.domain.db.fn.FnUser;
import org.onap.portal.domain.db.fn.FnUserRole;
import org.onap.portal.domain.dto.ecomp.EPUserAppCatalogRoles;
import org.onap.portal.domain.dto.ecomp.ExternalSystemAccess;
import org.onap.portal.domain.dto.transport.AppWithRolesForUser;
import org.onap.portal.domain.dto.transport.FieldsValidator;
import org.onap.portal.domain.dto.transport.RemoteRole;
import org.onap.portal.domain.dto.transport.RemoteUserWithRoles;
import org.onap.portal.domain.dto.transport.Role;
import org.onap.portal.domain.dto.transport.RoleInAppForUser;
import org.onap.portal.domain.dto.transport.UserApplicationRoles;
import org.onap.portal.service.ApplicationsRestClientService;
import org.onap.portal.service.userRolesRequestDet.EpUserRolesRequestDetService;
import org.onap.portal.service.userRolesRequest.EpUserRolesRequestService;
import org.onap.portal.service.app.FnAppService;
import org.onap.portal.service.role.FnRoleService;
import org.onap.portal.service.user.FnUserService;
import org.onap.portal.utils.EPCommonSystemProperties;
import org.onap.portal.utils.PortalConstants;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.restful.domain.EcompRole;
import org.onap.portalsdk.core.util.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FnUserRoleService {

  private static final String GET_ROLE_FUNCTIONS_OF_USERFOR_ALLTHE_APPLICATIONS =
      "select\n"
          + "  distinct ep.function_cd functionCd\n"
          + "from\n"
          + "  fn_user_role fu,\n"
          + "  ep_app_role_function ep,\n"
          + "  ep_app_function ea\n"
          + "where\n"
          + "  fu.role_id = ep.role_id\n"
          + "  and fu.app_id = ep.app_id\n"
          + "  and fu.user_id = 'userId'\n"
          + "  and ea.function_cd = ep.function_cd\n"
          + "  and exists (\n"
          + "    select\n"
          + "      fa.app_id\n"
          + "    from\n"
          + "      fn_user fu,\n"
          + "      fn_user_role ur,\n"
          + "      fn_app fa\n"
          + "    where\n"
          + "      fu.user_id = 'userId'\n"
          + "      and fu.user_id = ur.user_id\n"
          + "      and ur.app_id = fa.app_id\n"
          + "      and fa.enabled = 'Y'\n"
          + "  )";

  private static final String USER_APP_CATALOG_ROLES =
      "select\n"
          + "  A.reqId as reqId,\n"
          + "  B.requestedRoleId.id as requestedRoleId,\n"
          + "  A.requestStatus as requestStatus,\n"
          + "  A.appId.id as appId,\n"
          + "  (\n"
          + "    select\n"
          + "      roleName\n"
          + "    from\n"
          + "      FnRole\n"
          + "    where\n"
          + "      id = B.requestedRoleId.id\n"
          + "  ) as roleName\n"
          + "from\n"
          + "  EpUserRolesRequest A\n"
          + "  left join EpUserRolesRequestDet B on A.reqId = B.reqId.reqId\n"
          + "where\n"
          + "  A.userId.id = :userid\n"
          + "  and A.appId IN (\n"
          + "    select\n"
          + "      id\n"
          + "    from\n"
          + "      FnApp\n"
          + "    where\n"
          + "      appName = :appName\n"
          + "  )\n"
          + "  and A.requestStatus = 'P'\n";

  private final EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(FnUserRoleService.class);

  private final FnUserRoleDao fnUserRoleDao;
  private final FnAppService fnAppService;
  private final FnRoleService fnRoleService;
  private final FnUserService fnUserService;
  private final EpUserRolesRequestService epUserRolesRequestService;
  private final EpUserRolesRequestDetService epUserRolesRequestDetService;
  private final EntityManager entityManager;
  private final ApplicationsRestClientService applicationsRestClientService;

  @Autowired
  public FnUserRoleService(FnUserRoleDao
      fnUserRoleDao,
      FnAppService fnAppService,
      FnRoleService fnRoleService,
      FnUserService fnUserService,
      EpUserRolesRequestService epUserRolesRequestService,
      EpUserRolesRequestDetService epUserRolesRequestDetService,
      EntityManager entityManager,
      ApplicationsRestClientService applicationsRestClientService) {
    this.fnUserRoleDao = fnUserRoleDao;
    this.fnAppService = fnAppService;
    this.fnRoleService = fnRoleService;
    this.fnUserService = fnUserService;
    this.epUserRolesRequestService = epUserRolesRequestService;
    this.epUserRolesRequestDetService = epUserRolesRequestDetService;
    this.entityManager = entityManager;
    this.applicationsRestClientService = applicationsRestClientService;
  }

  public List<FnUserRole> getAdminUserRoles(final Long userId, final Long roleId, final Long appId) {
    return fnUserRoleDao.getAdminUserRoles(userId, roleId, appId).orElse(new ArrayList<>());
  }

  public boolean isSuperAdmin(final String loginId, final Long roleId, final Long appId) {
    List<FnUserRole> roles = getUserRolesForRoleIdAndAppId(roleId, appId).stream()
        .filter(role -> role.getUserId().getOrgUserId().equals(loginId)).collect(Collectors.toList());
    return !roles.isEmpty();
  }

  public List<FnUserRole> getUserRolesForRoleIdAndAppId(final Long roleId, final Long appId) {
    return Optional.of(fnUserRoleDao.retrieveByAppIdAndRoleId(appId, roleId)).orElse(new ArrayList<>());
  }

  public List<FnUserRole> retrieveByUserIdAndRoleId(final Long userId, final Long roleId){
    return Optional.of(fnUserRoleDao.retrieveByUserIdAndRoleId(userId, roleId)).orElse(new ArrayList<>());
  }

  public FnUserRole saveOne(final FnUserRole fnUserRole) {
    return fnUserRoleDao.save(fnUserRole);
  }

  public ExternalSystemAccess getExternalRequestAccess() {
    ExternalSystemAccess res = null;
    try {
      res = new ExternalSystemAccess(EPCommonSystemProperties.EXTERNAL_ACCESS_ENABLE,
          Boolean.parseBoolean(
              SystemProperties.getProperty(EPCommonSystemProperties.EXTERNAL_ACCESS_ENABLE)));
    } catch (Exception e) {
      logger.error(EELFLoggerDelegate.errorLogger, "getExternalRequestAccess failed" + e.getMessage());
    }
    return res;
  }

  public List<EPUserAppCatalogRoles> getUserAppCatalogRoles(FnUser userid, String appName) {
    List<Tuple> tuples = entityManager.createQuery(USER_APP_CATALOG_ROLES, Tuple.class)
        .setParameter("userid", userid.getId())
        .setParameter("appName", appName)
        .getResultList();
    return Optional.of(tuples.stream().map(this::tupleToEPUserAppCatalogRoles).collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

  private EPUserAppCatalogRoles tupleToEPUserAppCatalogRoles(Tuple tuple) {
    return new EPUserAppCatalogRoles((Long) tuple.get("reqId"), (Long) tuple.get("requestedRoleId"),
        (String) tuple.get("roleName"), (String) tuple.get("requestStatus"), (Long) tuple.get("appId"));
  }

  private boolean postUserRolesToMylogins(AppWithRolesForUser userAppRolesData,
      ApplicationsRestClientService applicationsRestClientService, Long appId, Long userId)
      throws JsonProcessingException, HTTPException {
    boolean result = false;
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    String userRolesAsString = mapper.writeValueAsString(userAppRolesData);
    logger.error(EELFLoggerDelegate.errorLogger,
        "Should not be reached here, as the endpoint is not defined yet from the Mylogins");
    applicationsRestClientService.post(AppWithRolesForUser.class, appId, userRolesAsString,
        String.format("/user/%s/myLoginroles", userId));
    return result;
  }

  public FieldsValidator putUserAppRolesRequest(AppWithRolesForUser newAppRolesForUser, FnUser user) {
    FieldsValidator fieldsValidator = new FieldsValidator();
    List<FnRole> appRole;
    try {
      logger.error(EELFLoggerDelegate.errorLogger,
          "Should not be reached here, still the endpoint is yet to be defined");
      boolean result = postUserRolesToMylogins(newAppRolesForUser, applicationsRestClientService,
          newAppRolesForUser.getAppId(), user.getId());
      logger.debug(EELFLoggerDelegate.debugLogger, "putUserAppRolesRequest: result {}", result);
      FnApp app = fnAppService.getById(newAppRolesForUser.getAppId());
      EpUserRolesRequest epUserRolesRequest = new EpUserRolesRequest();
      epUserRolesRequest.setCreatedDate(LocalDateTime.now());
      epUserRolesRequest.setUpdatedDate(LocalDateTime.now());
      epUserRolesRequest.setUserId(user);
      epUserRolesRequest.setAppId(app);
      epUserRolesRequest.setRequestStatus("P");
      List<RoleInAppForUser> appRoleIdList = newAppRolesForUser.getAppRoles();
      Set<EpUserRolesRequestDet> appRoleDetails = new LinkedHashSet<>();
      epUserRolesRequestService.saveOne(epUserRolesRequest);
      for (RoleInAppForUser userAppRoles : appRoleIdList) {
        Boolean isAppliedVal = userAppRoles.getIsApplied();
        if (isAppliedVal) {
          appRole = fnRoleService
              .retrieveAppRoleByAppRoleIdAndByAppId(newAppRolesForUser.getAppId(),
                  userAppRoles.getRoleId());
          if (!appRole.isEmpty()) {
            EpUserRolesRequestDet epAppRoleDetail = new EpUserRolesRequestDet();
            epAppRoleDetail.setRequestedRoleId(appRole.get(0));
            epAppRoleDetail.setRequestType("P");
            epAppRoleDetail.setReqId(epUserRolesRequest);
            epUserRolesRequestDetService.saveOne(epAppRoleDetail);
          }
        }
      }
      epUserRolesRequest.setEpRequestIdDetail(appRoleDetails);
      fieldsValidator.setHttpStatusCode((long) HttpServletResponse.SC_OK);

    } catch (Exception e) {
      logger.error(EELFLoggerDelegate.errorLogger, "putUserAppRolesRequest failed", e);
      fieldsValidator.setHttpStatusCode((long) HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    return fieldsValidator;
  }

  public List<FnRole> importRolesFromRemoteApplication(Long appId) throws HTTPException {
    FnRole[] appRolesFull = applicationsRestClientService.get(FnRole[].class, appId, "/rolesFull");
    List<FnRole> rolesList = Arrays.asList(appRolesFull);
    for (FnRole externalAppRole : rolesList) {

      // Try to find an existing extern role for the app in the local
      // onap DB. If so, then use its id to update the existing external
      // application role record.
      Long externAppId = externalAppRole.getId();
      FnRole existingAppRole = fnRoleService.getRole(appId, externAppId);
      if (existingAppRole != null) {
        logger.debug(EELFLoggerDelegate.debugLogger,
            String.format(
                "ecomp role already exists for app=%s; appRoleId=%s. No need to import this one.",
                appId, externAppId));
      }
    }

    return rolesList;
  }

  public List<UserApplicationRoles> getUsersFromAppEndpoint(Long appId) throws HTTPException {
    ArrayList<UserApplicationRoles> userApplicationRoles = new ArrayList<>();

    FnApp app = fnAppService.getById(appId);
    //If local or centralized application
    if (PortalConstants.PORTAL_APP_ID.equals(appId) || app.getAuthCentral()) {
      List<FnUser> userList = fnUserService.getActiveUsers();
      for (FnUser user : userList) {
        UserApplicationRoles userWithAppRoles = convertToUserApplicationRoles(appId, user, app);
        if (userWithAppRoles.getRoles() != null && userWithAppRoles.getRoles().size() > 0) {
          userApplicationRoles.add(userWithAppRoles);
        }
      }

    }
    // remote app
    else {
      RemoteUserWithRoles[] remoteUsers;
      String remoteUsersString = applicationsRestClientService.getIncomingJsonString(appId, "/users");

      remoteUsers = doGetUsers(remoteUsersString);

      userApplicationRoles = new ArrayList<>();
      for (RemoteUserWithRoles remoteUser : remoteUsers) {
        UserApplicationRoles userWithRemoteAppRoles = convertToUserApplicationRoles(appId,
            remoteUser);
        if (userWithRemoteAppRoles.getRoles() != null
            && userWithRemoteAppRoles.getRoles().size() > 0) {
          userApplicationRoles.add(userWithRemoteAppRoles);
        } else {
          logger.debug(EELFLoggerDelegate.debugLogger,
              "User " + userWithRemoteAppRoles.getOrgUserId()
                  + " doesn't have any roles assigned to any app.");
        }
      }
    }

    return userApplicationRoles;
  }

  private UserApplicationRoles convertToUserApplicationRoles(Long appId, RemoteUserWithRoles remoteUser) {
    UserApplicationRoles userWithRemoteAppRoles = new UserApplicationRoles();
    userWithRemoteAppRoles.setAppId(appId);
    userWithRemoteAppRoles.setOrgUserId(remoteUser.getOrgUserId());
    userWithRemoteAppRoles.setFirstName(remoteUser.getFirstName());
    userWithRemoteAppRoles.setLastName(remoteUser.getLastName());
    userWithRemoteAppRoles.setRoles(remoteUser.getRoles());
    return userWithRemoteAppRoles;
  }

  private RemoteUserWithRoles[] doGetUsers(String remoteUsersString) {

    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(remoteUsersString, RemoteUserWithRoles[].class);
    } catch (IOException e) {
      logger.error(EELFLoggerDelegate.errorLogger,
          "doGetUsers : Failed : Unexpected property in incoming JSON",
          e);
      logger.error(EELFLoggerDelegate.errorLogger,
          "doGetUsers : Incoming JSON that caused it --> " + remoteUsersString);
    }

    return new RemoteUserWithRoles[0];
  }

  private UserApplicationRoles convertToUserApplicationRoles(Long appId, FnUser user, FnApp app) {
    UserApplicationRoles userWithRemoteAppRoles = new UserApplicationRoles();
    userWithRemoteAppRoles.setAppId(appId);
    userWithRemoteAppRoles.setOrgUserId(user.getOrgUserId());
    userWithRemoteAppRoles.setFirstName(user.getFirstName());
    userWithRemoteAppRoles.setLastName(user.getLastName());
    userWithRemoteAppRoles.setRoles(convertToRemoteRoleList(user, app));
    return userWithRemoteAppRoles;
  }

  private List<RemoteRole> convertToRemoteRoleList(FnUser user, FnApp app) {
    List<RemoteRole> roleList = new ArrayList<>();
    SortedSet<FnRole> roleSet = user.getAppEPRoles(app);
    for (FnRole role : roleSet) {
      logger.debug(EELFLoggerDelegate.debugLogger,
          "In convertToRemoteRoleList() - for user {}, found Name {}", user.getOrgUserId(),
          role.getRoleName());
      RemoteRole rRole = new RemoteRole();
      rRole.setId(role.getId());
      rRole.setName(role.getRoleName());
      roleList.add(rRole);
    }

    //Get the active roles of user for that application using query
    List<FnRole> userEpRoleList = fnRoleService.getUserRoleOnUserIdAndAppId(user.getId(), app.getId());

    for (FnRole remoteUserRoleList : userEpRoleList) {

      RemoteRole remoteRoleListId = roleList.stream()
          .filter(x -> remoteUserRoleList.getId().equals(x.getId()))
          .findAny().orElse(null);
      if (remoteRoleListId == null) {
        logger.debug(EELFLoggerDelegate.debugLogger,
            "Adding the role to the rolelist () - for user {}, found Name {}",
            user.getOrgUserId(),

            remoteUserRoleList.getRoleName());
        RemoteRole role = new RemoteRole();
        role.setId(remoteUserRoleList.getId());
        role.setName(remoteUserRoleList.getRoleName());

        roleList.add(role);
      }

    }

    logger.debug(EELFLoggerDelegate.debugLogger,
        "rolelist size of the USER() - for user {}, found RoleListSize {}", user.getOrgUserId(),
        roleList.size());
    return roleList;
  }

  public List getRoleFunctionsOfUserforAlltheApplications(Long userId) {
    List<Tuple> tuples = entityManager
        .createQuery(GET_ROLE_FUNCTIONS_OF_USERFOR_ALLTHE_APPLICATIONS, Tuple.class)
        .setParameter("userid", userId)
        .getResultList();
    return Optional.of(tuples.stream().map(tuple -> tuple.get("functionCd")).collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

  public List<FnUserRole> retrieveByAppIdAndUserId(final Long appId, final String userId) {
    return Optional.of(fnUserRoleDao.retrieveByAppIdAndUserId(appId, userId)).orElse(new ArrayList<>());
  }

  public String updateRemoteUserProfile(String orgUserId, long appId) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    FnUser client = fnUserService.loadUserByUsername(orgUserId);
    FnUser newUser = new FnUser();
    newUser.setActiveYn(client.getActiveYn());
    newUser.setFirstName(client.getFirstName());
    newUser.setLastName(client.getLastName());
    newUser.setLoginId(client.getLoginId());
    newUser.setLoginPwd(client.getLoginPwd());
    newUser.setMiddleName(client.getMiddleName());
    newUser.setEmail(client.getEmail());
    newUser.setOrgUserId(client.getLoginId());
    try {
      String userAsString = mapper.writeValueAsString(newUser);
      List<FnApp> appList = fnAppService.getUserRemoteApps(client.getId().toString());
      // applicationsRestClientService.post(EPUser.class, appId,
      // userAsString, String.format("/user", orgUserId));
      for (FnApp eachApp : appList) {
        try {
          applicationsRestClientService.post(FnUser.class, eachApp.getId(), userAsString,
              String.format("/user/%s", orgUserId));
        } catch (Exception e) {
          logger.error(EELFLoggerDelegate.errorLogger, "Failed to update user: " + client.getOrgUserId()
              + " in remote app. appId = " + eachApp.getId());
        }
      }
    } catch (Exception e) {
      logger.error(EELFLoggerDelegate.errorLogger, "updateRemoteUserProfile failed", e);
      return "failure";
    }
    return "success";
  }

  public void deleteById(final Long id) {
    fnUserRoleDao.deleteById(id);
  }

  public void deleteByUserIdAndRoleId(final Long userId, final String roleId){
    final String query =  "DELETE FROM FnUserRole id = :userId AND roleId.id = :roleId";
    entityManager.createQuery(query).setParameter("userId", userId).setParameter("roleId", roleId).executeUpdate();
  }

  public List<RoleInAppForUser> constructRolesInAppForUserGet(List<Role> appRoles, FnRole[] userAppRoles,
      Boolean extRequestValue) {
    List<RoleInAppForUser> rolesInAppForUser = new ArrayList<>();

    Set<Long> userAppRolesMap = new HashSet<>();
    if (userAppRoles != null) {
      for (FnRole ecompRole : userAppRoles) {
        userAppRolesMap.add(ecompRole.getAppId());
      }
      logger.debug(EELFLoggerDelegate.debugLogger, "In constructRolesInAppForUserGet() - userAppRolesMap = {}",
          userAppRolesMap);

    } else {
      logger.error(EELFLoggerDelegate.errorLogger,
          "constructRolesInAppForUserGet has received userAppRoles list empty.");
    }

    if (appRoles != null) {
      for (Role ecompRole : appRoles) {
        logger.debug(EELFLoggerDelegate.debugLogger, "In constructRolesInAppForUserGet() - appRoles not null = {}",
            ecompRole);

        if (ecompRole.getId().equals(PortalConstants.ACCOUNT_ADMIN_ROLE_ID) && !extRequestValue) {
          continue;
        }
        RoleInAppForUser roleForUser = new RoleInAppForUser(ecompRole.getId(), ecompRole.getRoleName());
        roleForUser.setIsApplied(userAppRolesMap.contains(ecompRole.getId()));
        rolesInAppForUser.add(roleForUser);
        logger.debug(EELFLoggerDelegate.debugLogger, "In constructRolesInAppForUserGet() - rolesInAppForUser = {}",
            rolesInAppForUser);

      }
    } else {
      logger.error(EELFLoggerDelegate.errorLogger,
          "constructRolesInAppForUser has received appRoles list empty.");
    }
    return rolesInAppForUser;
  }

  public List<RoleInAppForUser> constructRolesInAppForUserGet(EcompRole[] appRoles, EcompRole[] userAppRoles) {
    List<RoleInAppForUser> rolesInAppForUser = new ArrayList<>();

    Set<Long> userAppRolesMap = new HashSet<>();
    if (userAppRoles != null) {
      for (EcompRole ecompRole : userAppRoles) {
        userAppRolesMap.add(ecompRole.getId());
      }
    } else {
      logger.error(EELFLoggerDelegate.errorLogger,
          "constructRolesInAppForUserGet has received userAppRoles list empty");
    }

    if (appRoles != null) {
      for (EcompRole ecompRole : appRoles) {
        RoleInAppForUser roleForUser = new RoleInAppForUser(ecompRole.getId(), ecompRole.getName());
        roleForUser.setIsApplied(userAppRolesMap.contains(ecompRole.getId()));
        rolesInAppForUser.add(roleForUser);
      }
    } else {
      logger.error(EELFLoggerDelegate.errorLogger, "constructRolesInAppForUser has received appRoles list empty");
    }
    return rolesInAppForUser;
  }

  public List<FnUserRole> saveAll(List<FnUserRole> userRoles) {
    return fnUserRoleDao.saveAll(userRoles);
  }
}
