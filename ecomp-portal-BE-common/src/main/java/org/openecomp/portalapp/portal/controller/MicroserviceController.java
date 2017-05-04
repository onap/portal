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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openecomp.portalapp.controller.EPRestrictedBaseController;
import org.openecomp.portalapp.portal.domain.MicroserviceData;
import org.openecomp.portalapp.portal.domain.WidgetCatalog;
import org.openecomp.portalapp.portal.domain.WidgetServiceHeaders;
import org.openecomp.portalapp.portal.ecomp.model.PortalRestResponse;
import org.openecomp.portalapp.portal.ecomp.model.PortalRestStatusEnum;
import org.openecomp.portalapp.portal.logging.aop.EPAuditLog;
import org.openecomp.portalapp.portal.service.ConsulHealthService;
import org.openecomp.portalapp.portal.service.MicroserviceService;
import org.openecomp.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("unchecked")
@RestController
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
@EPAuditLog
public class MicroserviceController extends EPRestrictedBaseController {
	
	private static final String HTTPS = "https://";
	
	String whatService = "widgets-service";
	RestTemplate template = new RestTemplate();

	@Autowired
	private ConsulHealthService consulHealthService;

	@Autowired
	private MicroserviceService microserviceService;
	
	@RequestMapping(value = { "/portalApi/microservices" }, method = RequestMethod.POST)
	public PortalRestResponse<String> createMicroservice(HttpServletRequest request, HttpServletResponse response,
			@RequestBody MicroserviceData newServiceData) throws Exception {
		if (newServiceData == null) {
			return new PortalRestResponse<String>(PortalRestStatusEnum.ERROR, "FAILURE",
					"MicroserviceData cannot be null or empty");
		}
		long serviceId = microserviceService.saveMicroservice(newServiceData);

		try {
			microserviceService.saveServiceParameters(serviceId, newServiceData.getParameterList());
		} catch (Exception e) {
			return new PortalRestResponse<String>(PortalRestStatusEnum.ERROR, "FAILURE", e.getMessage());
		}

		return new PortalRestResponse<String>(PortalRestStatusEnum.OK, "SUCCESS", "");
	}

	@RequestMapping(value = { "/portalApi/microservices" }, method = RequestMethod.GET)
	public List<MicroserviceData> getMicroservice(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<MicroserviceData> list = microserviceService.getMicroserviceData();
		return list;
	}

	@RequestMapping(value = { "/portalApi/microservices/{serviceId}" }, method = RequestMethod.PUT)
	public PortalRestResponse<String> updateMicroservice(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("serviceId") long serviceId, @RequestBody MicroserviceData newServiceData) throws Exception {

		if (newServiceData == null) {
			return new PortalRestResponse<String>(PortalRestStatusEnum.ERROR, "FAILURE",
					"MicroserviceData cannot be null or empty");
		}
		try {
			microserviceService.updateMicroservice(serviceId, newServiceData);
		} catch (Exception e) {
			return new PortalRestResponse<String>(PortalRestStatusEnum.ERROR, "FAILURE", e.getMessage());
		}
		return new PortalRestResponse<String>(PortalRestStatusEnum.OK, "SUCCESS", "");
	}
	
	@RequestMapping(value = { "/portalApi/microservices/{serviceId}" }, method = RequestMethod.DELETE)
	public PortalRestResponse<String> deleteMicroservice(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("serviceId") long serviceId) throws Exception {
		try {
			ParameterizedTypeReference<List<WidgetCatalog>> typeRef = new ParameterizedTypeReference<List<WidgetCatalog>>() {
			};
			// If this service is assoicated with widgets, cannnot be deleted
			ResponseEntity<List<WidgetCatalog>> ans = (ResponseEntity<List<WidgetCatalog>>) template.exchange(
					HTTPS + consulHealthService.getServiceLocation(whatService)
							+ "/widget/microservices/widgetCatalog/service/" + serviceId,
					HttpMethod.GET, new HttpEntity(WidgetServiceHeaders.getInstance()), typeRef);
			List<WidgetCatalog> widgets = ans.getBody();
			if(widgets.size() == 0)
				microserviceService.deleteMicroservice(serviceId);
			else{
				StringBuilder sb = new StringBuilder();
				for(int i = 0; i < widgets.size(); i++){
					sb.append("'" + widgets.get(i).getName() + "' ");
					if(i < (widgets.size()-1)){
						sb.append(",");
					}
				}
				return new PortalRestResponse<String>(PortalRestStatusEnum.WARN, "SOME WIDGETS ASSOICATE WITH THIS SERVICE", sb.toString());
			}
		} catch (Exception e) {
			return new PortalRestResponse<String>(PortalRestStatusEnum.ERROR, "FAILURE", e.getMessage());
		}
		return new PortalRestResponse<String>(PortalRestStatusEnum.OK, "SUCCESS", "");
	}

}
