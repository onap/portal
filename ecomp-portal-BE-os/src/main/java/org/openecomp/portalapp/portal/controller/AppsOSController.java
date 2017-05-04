/*-
 * ================================================================================
 * ECOMP Portal
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ================================================================================
 */
package org.openecomp.portalapp.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.openecomp.portalapp.portal.domain.EPUser;
import org.openecomp.portalapp.portal.ecomp.model.PortalRestResponse;
import org.openecomp.portalapp.portal.ecomp.model.PortalRestStatusEnum;
import org.openecomp.portalapp.portal.logging.aop.EPAuditLog;
import org.openecomp.portalapp.portal.service.AdminRolesService;
import org.openecomp.portalapp.portal.service.EPAppService;
import org.openecomp.portalapp.portal.service.PersUserAppService;
import org.openecomp.portalapp.portal.service.UserService;
import org.openecomp.portalapp.util.EPUserUtils;
import org.openecomp.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
@EPAuditLog
public class AppsOSController extends AppsController {
	
	static final String FAILURE = "failure";
	EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(AppsOSController.class);

	@Autowired
	AdminRolesService adminRolesService;
	@Autowired
	EPAppService appService;
	@Autowired
	PersUserAppService persUserAppService;
	@Autowired
	UserService userService;

	
	
	/**
	 * Create new application's contact us details.
	 * 
	 * @param contactUs
	 * @return
	 */
	@RequestMapping(value = "/portalApi/saveNewUser", method = RequestMethod.POST, produces = "application/json")
	public PortalRestResponse<String> saveNewUser(HttpServletRequest request,@RequestBody EPUser newUser) {
		EPUser user = EPUserUtils.getUserSession(request);
		if (newUser == null)
			return new PortalRestResponse<String>(PortalRestStatusEnum.ERROR, FAILURE,
					"New User cannot be null or empty");
		
		if (!(adminRolesService.isSuperAdmin(user) || adminRolesService.isAccountAdmin(user))){
			if(!user.getLoginId().equalsIgnoreCase(newUser.getLoginId()))
				return new PortalRestResponse<String>(PortalRestStatusEnum.ERROR, FAILURE,
						"UnAuthorized");
		}
			
        String checkDuplicate = request.getParameter("isCheck");
		String saveNewUser = FAILURE;
		try {
			saveNewUser = userService.saveNewUser(newUser,checkDuplicate);
		} catch (Exception e) {
			return new PortalRestResponse<String>(PortalRestStatusEnum.ERROR, saveNewUser, e.getMessage());
		}
		return new PortalRestResponse<String>(PortalRestStatusEnum.OK, saveNewUser, "");
	}
	
	@RequestMapping(value = { "/portalApi/currentUserProfile/{loginId}" }, method = RequestMethod.GET, produces = "application/json")
	public String getCurrentUserProfile(HttpServletRequest request, @PathVariable("loginId") String loginId) {
		
		Map<String,String> map = new HashMap<String,String>();
		EPUser user = null;
		try {
			 user = (EPUser) userService.getUserByUserId(loginId).get(0);
			 map.put("firstName", user.getFirstName());
		     map.put("lastName", user.getLastName());
		     map.put("email", user.getEmail());
			 map.put("loginId", user.getLoginId());
			 map.put("loginPwd",user.getLoginPwd());
			 map.put("middleInitial",user.getMiddleInitial());
		} catch (Exception e) {
			logger.error(EELFLoggerDelegate.errorLogger, "Failed to get user info", e);
		}

		JSONObject j = new JSONObject(map);;
		return j.toString();
	}

}