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

package org.onap.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.onap.portal.domain.dto.ecomp.WidgetServiceHeaders;
import org.onap.portal.logging.aop.EPAuditLog;
import org.onap.portal.service.WidgetMService;
import org.onap.portal.utils.EcompPortalUtils;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.util.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@EPAuditLog
@RestController
public class WidgetsCatalogMarkupController {

       private EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(WidgetsCatalogMarkupController.class);
       private final RestTemplate template = new RestTemplate();
       private final String whatService = "widgets-service";

       @Autowired
       private WidgetMService widgetMService;

       @Bean
       public CommonsMultipartResolver multipartResolver() {
              return new CommonsMultipartResolver();
       }

       static {
              // for localhost testing only
              javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> hostname.equals("localhost"));
       }

       @GetMapping(value = "/portalApi/microservices/markup/{widgetId}")
       public String getWidgetMarkup(HttpServletRequest request, HttpServletResponse response,
               @PathVariable("widgetId") long widgetId) throws RestClientException, Exception {
              return template
                      .getForObject(
                              EcompPortalUtils.widgetMsProtocol() + "://"
                                      + widgetMService.getServiceLocation(whatService,
                                      SystemProperties.getProperty("microservices.widget.local.port"))
                                      + "/widget/microservices/markup/" + widgetId,
                              String.class, WidgetServiceHeaders.getInstance());
       }

}
