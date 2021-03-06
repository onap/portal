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
 */
package org.onap.portalapp.controller.sessionmgt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onap.portalapp.annotation.ApiVersion;
import org.onap.portalapp.portal.logging.aop.EPAuditLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
@EPAuditLog
@ApiVersion
public class SessionCommunicationVersionController {

	@Autowired
	SessionCommunicationController sessionCommunicationController;

	@ApiVersion(max = "v3", service = "/v3/getSessionSlotCheckInterval", min = 0,method = "GET")
	public Integer getSessionSlotCheckInterval(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return sessionCommunicationController.getSessionSlotCheckInterval(request, response);
	}
	
	
	@ApiVersion(max = "v3", service = "/v3/extendSessionTimeOuts", min = 0,method = "POST")
	public Boolean extendSessionTimeOuts(HttpServletRequest request, HttpServletResponse response, String sessionMap) throws Exception {
		return sessionCommunicationController.extendSessionTimeOuts(request, response, sessionMap);
	}

}
