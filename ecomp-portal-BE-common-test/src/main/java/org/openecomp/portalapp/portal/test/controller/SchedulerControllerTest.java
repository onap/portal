package org.openecomp.portalapp.portal.test.controller;

import java.sql.DriverManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openecomp.portalapp.portal.controller.SchedulerController;
import org.openecomp.portalapp.portal.scheduler.SchedulerProperties;
import org.openecomp.portalapp.portal.scheduler.SchedulerRestInterface;
import org.openecomp.portalapp.portal.test.core.MockEPUser;
import org.openecomp.portalapp.test.framework.MockitoTestSuite;
import org.openecomp.portalsdk.core.util.SystemProperties;
import org.openecomp.portalsdk.core.web.support.UserUtils;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UserUtils.class,SystemProperties.class})

public class SchedulerControllerTest {

	@Mock
	SchedulerRestInterface schedulerRestInterface;
	
	

	@InjectMocks
	SchedulerController schedulerController = new SchedulerController();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	MockEPUser mockUser = new MockEPUser();
	MockitoTestSuite mockitoTestSuite = new MockitoTestSuite();

	HttpServletRequest mockedRequest = mockitoTestSuite.getMockedRequest();
	HttpServletResponse mockedResponse = mockitoTestSuite.getMockedResponse();
	NullPointerException nullPointerException = new NullPointerException();

	@Test
	public void  getTimeSlotsTest() throws Exception{
		schedulerController.getTimeSlots(mockedRequest, "12");
		
	}
	
	@Test
	public void postCreateNewVNFChangeTest() throws Exception{
		//String testJsonData="{\"domain\":\"ChangeManagement\",\"scheduleName\":\"VnfUpgrade/DWF\",\"userId\":\"su7376\",\"domainData\":[{\"WorkflowName\":\"HEAT Stack Software Update for vNFs\",\"CallbackUrl\":\"http://127.0.0.1:8989/scheduler/v1/loopbacktest/vid\",\"CallbackData\":\"testing\"}],\"schedulingInfo\":{\"normalDurationInSeconds\":60,\"additionalDurationInSeconds\":60,\"concurrencyLimit\":60,\"policyId\":\"SNIRO_CM_1707.Config_MS_Demo_TimeLimitAndVerticalTopology_zone_localTime.1.xml\",\"vnfDetails\":[{\"groupId\":\"group1\",\"node\":[\"satmo415vbc\",\"satmo455vbc\"]}]}}";
		JSONObject jsonObject =Mockito.mock(JSONObject.class);
		
		schedulerController.postCreateNewVNFChange(mockedRequest, jsonObject);
	}
	
	@Test
	public void postSubmitVnfChangeTimeslotsTest() throws Exception{
		JSONObject jsonObject =Mockito.mock(JSONObject.class);
		Mockito.when(jsonObject.get("scheduleId")).thenReturn("12");
        PowerMockito.mockStatic(SystemProperties.class);
		PowerMockito.when(SystemProperties.getProperty(SchedulerProperties.SCHEDULER_SUBMIT_NEW_VNF_CHANGE)).thenReturn("/v1/ChangeManagement/schedules/{scheduleId}/approvals");
		schedulerController.postSubmitVnfChangeTimeslots(mockedRequest, jsonObject);
	}
	
}