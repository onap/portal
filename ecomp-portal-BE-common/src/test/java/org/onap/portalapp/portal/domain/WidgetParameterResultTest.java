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
package org.onap.portalapp.portal.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.onap.portalapp.portal.domain.WidgetParameterResult;

public class WidgetParameterResultTest {

	public WidgetParameterResult mockWidgetParameterResult(){
				
		WidgetParameterResult widgetParameterResult = new WidgetParameterResult();
		widgetParameterResult.setParam_id((long)1);
		widgetParameterResult.setParam_key("test");
		widgetParameterResult.setUser_value("test");
		widgetParameterResult.setDefault_value("test");
		
		return widgetParameterResult;
	}
	
	@Test
	public void widgetParameterResultTest(){
		
		WidgetParameterResult widgetParameterResult = mockWidgetParameterResult();
		
		assertEquals(widgetParameterResult.getParam_key(), "test");
		assertEquals(widgetParameterResult.getParam_id(), new Long(1));
		assertEquals(widgetParameterResult.getUser_value(), "test");
		assertEquals(widgetParameterResult.getDefault_value(), "test");
		
		assertEquals("WidgetParameterResult [param_id=1, param_key=test, user_value=test, default_value=test]", widgetParameterResult.toString());
	}
}
