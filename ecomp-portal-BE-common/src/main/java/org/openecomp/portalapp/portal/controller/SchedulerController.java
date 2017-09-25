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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.openecomp.portalapp.portal.logging.aop.EPAuditLog;
import org.openecomp.portalapp.portal.scheduler.SchedulerProperties;
import org.openecomp.portalapp.portal.scheduler.SchedulerRestInterface;
import org.openecomp.portalapp.portal.scheduler.SchedulerUtil;
import org.openecomp.portalapp.portal.scheduler.restobjects.GetTimeSlotsRestObject;
import org.openecomp.portalapp.portal.scheduler.restobjects.PostCreateNewVnfRestObject;
import org.openecomp.portalapp.portal.scheduler.restobjects.PostSubmitVnfChangeRestObject;
import org.openecomp.portalapp.portal.scheduler.wrapper.GetTimeSlotsWrapper;
import org.openecomp.portalapp.portal.scheduler.wrapper.PostCreateNewVnfWrapper;
import org.openecomp.portalapp.portal.scheduler.wrapper.PostSubmitVnfChangeTimeSlotsWrapper;
import org.openecomp.portalapp.portal.utils.PortalConstants;
import org.openecomp.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.openecomp.portalsdk.core.util.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PortalConstants.REST_AUX_API)
@Configuration
@EnableAspectJAutoProxy
@EPAuditLog
public class SchedulerController implements BasicAuthenticationController {

	@Autowired
	private SchedulerRestInterface schedulerRestController;

	private EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(SchedulerController.class);

	/** The request date format. */
	public DateFormat requestDateFormat = new SimpleDateFormat("EEE, dd MMM YYYY HH:mm:ss z");

	@RequestMapping(value = "/get_time_slots/{scheduler_request}", method = RequestMethod.GET)
	public ResponseEntity<String> getTimeSlots(HttpServletRequest request,
			@PathVariable("scheduler_request") String scheduler_request) throws Exception {

		Date startingTime = new Date();
		String startTimeRequest = requestDateFormat.format(startingTime);

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println(startTimeRequest + " | Controller Scheduler GET : /get_time_slots/{scheduler_request} \n");
		System.out.println("Original Request : \n " + scheduler_request + '\n');

		String path = SystemProperties.getProperty(SchedulerProperties.SCHEDULER_GET_TIME_SLOTS) + scheduler_request;

		GetTimeSlotsWrapper schedulerResWrapper = getTimeSlots(scheduler_request, path, scheduler_request);

		Date endTime = new Date();
		String endTimeRequest = requestDateFormat.format(endTime);
		System.out.println(endTimeRequest + " | Controller Scheduler - GET\n");

		return (new ResponseEntity<String>(schedulerResWrapper.getResponse(), HttpStatus.OK));

	}

	protected GetTimeSlotsWrapper getTimeSlots(String request, String path, String uuid) throws Exception {

		try {
			// STARTING REST API CALL AS AN FACTORY INSTACE
			System.out.println("<== Get Time Slots Request START \n");

			GetTimeSlotsRestObject<String> restObjStr = new GetTimeSlotsRestObject<String>();
			String str = new String();

			restObjStr.set(str);

			schedulerRestController.Get(str, uuid, path, restObjStr);
			GetTimeSlotsWrapper schedulerRespWrapper = SchedulerUtil.getTimeSlotsWrapResponse(restObjStr);

			System.out.println(
					"<== Get Time Slots Request END : Response = " + schedulerRespWrapper.getResponse() + '\n');

			return schedulerRespWrapper;

		} catch (Exception e) {
			System.out.println("<== Get Time Slots Request ERROR : " + e.toString() + '\n');
			logger.error(EELFLoggerDelegate.errorLogger, "Get Time Slots Request failed", e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/post_create_new_vnf_change", method = RequestMethod.POST)
	public ResponseEntity<String> postCreateNewVNFChange(HttpServletRequest request,
			@RequestBody JSONObject scheduler_request) throws Exception {

		Date startingTime = new Date();
		String startTimeRequest = requestDateFormat.format(startingTime);

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println(startTimeRequest + " | Controller Scheduler POST : post_create_new_vnf_change \n");

		// Generating uuid
		String uuid = UUID.randomUUID().toString();

		scheduler_request.put("scheduleId", uuid);
		System.out.println("<== UUID : " + uuid + '\n');

		// adding uuid to the request payload
		scheduler_request.put("scheduleId", uuid);

		System.out.println("<== UUID : " + uuid + '\n');
		System.out.println("Original Request : \n " + scheduler_request.toString() + '\n');

		String path = SystemProperties.getProperty(SchedulerProperties.SCHEDULER_CREATE_NEW_VNF_CHANGE_INSTANCE_VAL)
				+ uuid;

		PostCreateNewVnfWrapper responseWrapper = postSchedulingRequest(scheduler_request, path, uuid);

		Date endTime = new Date();
		String endTimeRequest = requestDateFormat.format(endTime);
		System.out.println(endTimeRequest + " | Controller Scheduler - POST\n");

		return (new ResponseEntity<String>(responseWrapper.getResponse(), HttpStatus.OK));
	}

	protected PostCreateNewVnfWrapper postSchedulingRequest(JSONObject request, String path, String uuid)
			throws Exception {

		try {
			// STARTING REST API CALL AS AN FACTORY INSTACE
			System.out.println("<== Post Create New Vnf Scheduling Request START \n");

			PostCreateNewVnfRestObject<String> restObjStr = new PostCreateNewVnfRestObject<String>();
			String str = new String();

			restObjStr.set(str);
			schedulerRestController.<String>Post(str, request, path, restObjStr);

			int status = restObjStr.getStatusCode();
			if (status >= 200 && status <= 299) {
				restObjStr.setUUID(uuid);
			}

			PostCreateNewVnfWrapper responseWrapper = SchedulerUtil.postCreateNewVnfWrapResponse(restObjStr);

			System.out.println("<== Post Create New Vnf Scheduling Request END : Response = "
					+ responseWrapper.getResponse() + '\n');

			return responseWrapper;

		} catch (Exception e) {
			System.out.println("<== Post Create New Vnf Scheduling Request ERROR : " + e.toString() + '\n');
			logger.error(EELFLoggerDelegate.errorLogger, "Post Create New Vnf Scheduling Request failed", e);
			throw e;
		}
	}

	@RequestMapping(value = "/submit_vnf_change_timeslots", method = RequestMethod.POST)
	public ResponseEntity<String> postSubmitVnfChangeTimeslots(HttpServletRequest request,
			@RequestBody JSONObject scheduler_request) throws Exception {

		Date startingTime = new Date();
		String startTimeRequest = requestDateFormat.format(startingTime);

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println(startTimeRequest + " | Controller Scheduler POST : submit_vnf_change_timeslots \n");

		// Generating uuid
		String uuid = (String) scheduler_request.get("scheduleId");
		scheduler_request.remove("scheduleId");

		System.out.println("<== UUID : " + uuid + '\n');
		System.out.println("Original Request : \n " + scheduler_request.toString() + '\n');

		String path = SystemProperties.getProperty(SchedulerProperties.SCHEDULER_SUBMIT_NEW_VNF_CHANGE)
				.replace("{scheduleId}", uuid);

		PostSubmitVnfChangeTimeSlotsWrapper responseWrapper = postSubmitSchedulingRequest(scheduler_request, path,
				uuid);

		Date endTime = new Date();
		String endTimeRequest = requestDateFormat.format(endTime);
		System.out.println(endTimeRequest + " | Controller Scheduler - POST Submit\n");

		return (new ResponseEntity<String>(responseWrapper.getResponse(), HttpStatus.OK));
	}

	protected PostSubmitVnfChangeTimeSlotsWrapper postSubmitSchedulingRequest(JSONObject request, String path,
			String uuid) throws Exception {

		try {
			// STARTING REST API CALL AS AN FACTORY INSTACE
			System.out.println("<== Post Submit Scheduling Request START \n");

			PostSubmitVnfChangeRestObject<String> restObjStr = new PostSubmitVnfChangeRestObject<String>();
			String str = new String();

			restObjStr.set(str);
			schedulerRestController.<String>Post(str, request, path, restObjStr);

			int status = restObjStr.getStatusCode();
			if (status >= 200 && status <= 299) {
				restObjStr.setUUID(uuid);
			}

			PostSubmitVnfChangeTimeSlotsWrapper responseWrapper = SchedulerUtil
					.postSubmitNewVnfWrapResponse(restObjStr);

			System.out.println(
					"<== Post Submit Scheduling Request END : Response = " + responseWrapper.getResponse() + '\n');

			return responseWrapper;

		} catch (Exception e) {
			System.out.println("<== Post Submit Scheduling Request ERROR : " + e.toString() + '\n');
			logger.error(EELFLoggerDelegate.errorLogger, "Post Submit Scheduling Request failed", e);
			throw e;
		}
	}

}
