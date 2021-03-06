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
package org.onap.portalapp.service.sessionmgt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.onap.portalapp.annotation.ApiVersion;
import org.onap.portalapp.controller.sessionmgt.SessionCommunicationController;
import org.onap.portalapp.controller.sessionmgt.SessionCommunicationVersionController;
import org.onap.portalapp.portal.framework.MockitoTestSuite;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.aop.support.AopUtils;

public class SessionCommunicationVersionControllerTest {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@InjectMocks
	SessionCommunicationVersionController sessionCommunicationVersionController;
	
	@Mock
	SessionCommunicationController sessionCommunicationController;
	
	MockitoTestSuite mockitoTestSuite = new MockitoTestSuite();
	HttpServletRequest mockedRequest = mockitoTestSuite.getMockedRequest();
	HttpServletResponse mockedResponse = mockitoTestSuite.getMockedResponse();
	NullPointerException nullPointerException = new NullPointerException();
	
	@Test
	public void getSessionSlotCheckIntervalTest() throws Exception {
		Mockito.when(sessionCommunicationController.getSessionSlotCheckInterval(mockedRequest, mockedResponse)).thenReturn(1);
		int result = sessionCommunicationVersionController.getSessionSlotCheckInterval(mockedRequest, mockedResponse);
		assertEquals(result, 1);
		
	}

	@Test
	public void extendSessionTimeOutsTest() throws Exception {
		Mockito.when(sessionCommunicationController.extendSessionTimeOuts(mockedRequest, mockedResponse,"testsession")).thenReturn(true);
		boolean result = sessionCommunicationVersionController.extendSessionTimeOuts(mockedRequest, mockedResponse,"testsession");
		assertEquals(result, true);
		
	}
	
}
