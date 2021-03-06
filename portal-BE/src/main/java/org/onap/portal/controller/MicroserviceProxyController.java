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

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.onap.portal.domain.db.fn.FnUser;
import org.onap.portal.service.MicroserviceProxyService;
import org.onap.portal.service.user.FnUserService;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@Configuration
@EnableAspectJAutoProxy
public class MicroserviceProxyController {

    private EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(MicroserviceProxyController.class);

    private final MicroserviceProxyService microserviceProxyService;
    private final FnUserService fnUserService;

    @Autowired
    public MicroserviceProxyController(MicroserviceProxyService microserviceProxyService,
        FnUserService fnUserService) {
        this.microserviceProxyService = microserviceProxyService;
        this.fnUserService = fnUserService;
    }

    @RequestMapping(value = {"/portalApi/microservice/proxy/{serviceId}"}, method = {
        RequestMethod.GET}, produces = "application/json")
    public String getMicroserviceProxy(Principal principal, HttpServletRequest request, HttpServletResponse response,
        @PathVariable("serviceId") long serviceId) throws Exception {
        FnUser user = fnUserService.loadUserByUsername(principal.getName());
        String answer = "";
        try {
            answer = microserviceProxyService.proxyToDestination(serviceId, user, request);
        } catch (HttpClientErrorException e) {
            answer = e.getResponseBodyAsString();
        }
        return isValidJSON(answer) ? answer
            : "{\"error\":\"" + answer.replace(System.getProperty("line.separator"), "") + "\"}";
    }

    @RequestMapping(value = {"/portalApi/microservice/proxy/parameter/{widgetId}"}, method = {
        RequestMethod.GET}, produces = "application/json")
    public String getMicroserviceProxyByWidgetId(Principal principal, HttpServletRequest request,
        HttpServletResponse response,
        @PathVariable("widgetId") long widgetId) throws Exception {
        FnUser user = fnUserService.loadUserByUsername(principal.getName());
        String answer = "";
        try {
            answer = microserviceProxyService.proxyToDestinationByWidgetId(widgetId, user, request);
        } catch (HttpClientErrorException e) {
            answer = e.getResponseBodyAsString();
        }
        return isValidJSON(answer) ? answer
            : "{\"error\":\"" + answer.replace(System.getProperty("line.separator"), "") + "\"}";
    }

    private boolean isValidJSON(String response) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(response);
            return true;
        } catch (IOException e) {
            logger.debug(EELFLoggerDelegate.debugLogger, "isValidJSON failed", e);
            return false;
        }
    }
}
