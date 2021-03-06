/*-
 * ============LICENSE_START==========================================
 * ONAP Portal
 * ===================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights reserved.
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

package org.onap.portal.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.onap.portal.domain.db.ep.EpNotification;
import org.onap.portal.domain.db.ep.EpRoleNotification;
import org.onap.portal.domain.db.fn.FnUser;
import org.onap.portal.domain.dto.PortalRestResponse;
import org.onap.portal.domain.dto.PortalRestStatusEnum;
import org.onap.portal.logging.aop.EPAuditLog;
import org.onap.portal.service.epNotification.EpNotificationService;
import org.onap.portal.service.user.FnUserService;
import org.onap.portal.utils.EPCommonSystemProperties;
import org.onap.portal.utils.PortalConstants;
import org.onap.portal.validation.DataValidator;
import org.onap.portal.validation.SecureString;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.util.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PortalConstants.REST_AUX_API)
@Configuration
@EnableAspectJAutoProxy
@EPAuditLog
public class TicketEventController {

    private EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(TicketEventController.class);

    private static final String EVENT_DATE = "eventDate";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final DataValidator dataValidator;
    private final FnUserService fnUserService;
    private final EpNotificationService epNotificationService;

    @Autowired
    public TicketEventController(final DataValidator dataValidator,
        final FnUserService fnUserService,
        final EpNotificationService epNotificationService) {
        this.dataValidator = dataValidator;
        this.fnUserService = fnUserService;
        this.epNotificationService = epNotificationService;
    }

    @ApiOperation(
        value = "Accepts messages from external ticketing systems and creates notifications for Portal users.",
        response = PortalRestResponse.class)
    @RequestMapping(value = { "/ticketevent" }, method = RequestMethod.POST)
    public PortalRestResponse<String> handleRequest(HttpServletRequest request, HttpServletResponse response,
        @RequestBody String ticketEventJson) {

        logger.debug(EELFLoggerDelegate.debugLogger, "Ticket Event notification" + ticketEventJson);
        PortalRestResponse<String> portalResponse = new PortalRestResponse<>();

        if(!dataValidator.isValid(ticketEventJson)){
            portalResponse.setStatus(PortalRestStatusEnum.ERROR);
            portalResponse.setMessage("Data is not valid");
            return portalResponse;
        }

        try {
            JsonNode ticketEventNotif = objectMapper.readTree(ticketEventJson);

            // Reject request if required fields are missing.
            String error = validateTicketEventMessage(ticketEventNotif);
            if (error != null) {
                portalResponse.setStatus(PortalRestStatusEnum.ERROR);
                portalResponse.setMessage(error);
                response.setStatus(400);
                return portalResponse;
            }

            EpNotification epItem = new EpNotification();
            epItem.setCreatedDate(LocalDateTime.now());
            epItem.setIsForOnlineUsers(true);
            epItem.setIsForAllRoles(false);
            epItem.setActiveYn(false);

            JsonNode event = ticketEventNotif.get("event");
            JsonNode header = event.get("header");
            JsonNode body = event.get("body");
            JsonNode application = ticketEventNotif.get("application");
            epItem.setMsgDescription(body.toString());
            long eventDate = System.currentTimeMillis();
            if (body.get(EVENT_DATE) != null) {
                eventDate = body.get(EVENT_DATE).asLong();
            }
            String eventSource = header.get("eventSource").asText();
            epItem.setMsgSource(eventSource);
            String ticket = body.get("ticketNum").asText();
            String hyperlink = this.getNotificationHyperLink(application, ticket, eventSource);
            if (body.get("notificationHyperlink") != null) {
                hyperlink = body.get("notificationHyperlink").asText();
            }
            epItem.setNotificationHyperlink(hyperlink);
            epItem.setStartTime(LocalDateTime.now());
            epItem.setEndTime(epItem.getStartTime().plusDays(30));
            String severityString = "1";
            if (body.get("severity") != null) {
                severityString = (body.get("severity").toString()).substring(1, 2);
            }
            Long severity = Long.parseLong(severityString);
            epItem.setPriority(severity);
            epItem.setCreatorId(null);
            JsonNode subscriberInfo = ticketEventNotif.get("SubscriberInfo");
            JsonNode userList = subscriberInfo.get("UserList");
            String[] userIds = userList.toString().replace("[", "").replace("]", "").trim().replace("\"", "")
                .split(",");
            String assetID = eventSource + ' '
                + userList.toString().replace("[", "").replace("]", "").trim().replace("\"", "") + ' '
                + new Date(eventDate);
            if (body.get("assetID") != null) {
                assetID = body.get("assetID").asText();
            }
            epItem.setMsgHeader(assetID);
            List<FnUser> users = fnUserService.getUsersByOrgIds(new ArrayList<>(Arrays.asList(userIds)));
            Set<EpRoleNotification> roles = new HashSet<>();
            for (String userId : userIds) {
                EpRoleNotification roleNotifItem = new EpRoleNotification();
                for (FnUser user : users) {
                    if (user.getOrgUserId().equals(userId)) {
                        roleNotifItem.setRecvUserId(user.getId());
                        roles.add(roleNotifItem);
                        break;
                    }
                }

            }
            epItem.setEpRoleNotifications(roles);
            epNotificationService.saveNotification(epItem);

            portalResponse.setStatus(PortalRestStatusEnum.OK);
            portalResponse.setMessage("processEventNotification: notification created");
            portalResponse.setResponse("NotificationId is :" + epItem.getNotificationId());
        } catch (Exception ex) {
            logger.error(EELFLoggerDelegate.errorLogger, "Expection in handleRequest", ex);
            portalResponse.setStatus(PortalRestStatusEnum.ERROR);
            response.setStatus(400);
            portalResponse.setMessage(ex.toString());
        }
        return portalResponse;
    }

    private String getNotificationHyperLink(JsonNode application, String ticket, String eventSource) {
        return (SystemProperties.getProperty(EPCommonSystemProperties.EXTERNAL_SYSTEM_NOTIFICATION_URL)+ticket);
    }

    private String validateTicketEventMessage(JsonNode ticketEventNotif) {
        JsonNode application = ticketEventNotif.get("application");
        JsonNode event = ticketEventNotif.get("event");
        JsonNode header = event.get("header");
        JsonNode eventSource = header.get("eventSource");
        JsonNode body = event.get("body");
        JsonNode subscriberInfo = ticketEventNotif.get("SubscriberInfo");
        JsonNode userList = subscriberInfo.get("UserList");

        if (application == null || application.asText().length() == 0 || "null".equalsIgnoreCase(application.asText()))
            return "Application is mandatory";
        if (body == null)
            return "body is mandatory";
        if (eventSource == null || eventSource.asText().trim().length() == 0
            || "null".equalsIgnoreCase(eventSource.asText()))
            return "Message Source is mandatory";
        if (userList == null)
            return "At least one user Id is mandatory";
        JsonNode eventDate = body.get(EVENT_DATE);

        if (eventDate != null && eventDate.asText().length() == 8)
            return "EventDate is invalid";
        String[] userIds = userList.toString().replace("[", "").replace("]", "").trim().replace("\"", "")
            .split(",");
        List<FnUser> users = fnUserService.getUsersByOrgIds(new ArrayList<>(Arrays.asList(userIds)));
            fnUserService.getUsersByOrgIds(new ArrayList<>(Arrays.asList(userIds)));
        if (users == null || users.isEmpty())
            return "Invalid Org User ID";
        return null;
    }

}
