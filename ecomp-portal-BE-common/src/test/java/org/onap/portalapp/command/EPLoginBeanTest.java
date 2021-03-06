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
package org.onap.portalapp.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.onap.portalapp.command.EPLoginBean;

public class EPLoginBeanTest {

	
public EPLoginBean ePLoginBean(){
		
	EPLoginBean ePLoginBean = new EPLoginBean();
	ePLoginBean.setLoginId("guestT");
	ePLoginBean.setLoginPwd("password");
	ePLoginBean.setHrid("hrId");
	ePLoginBean.setOrgUserId("guestT");
	ePLoginBean.setBusinessDirectMenu(null);
	ePLoginBean.setSiteAccess("site_test");
	ePLoginBean.setLoginErrorMessage("error");
	ePLoginBean.setUser(null);
	ePLoginBean.setMenu(null);
	return ePLoginBean;
	}
	
	@Test
	public void ePLoginBeanTest(){
		EPLoginBean ePLoginBean = ePLoginBean();
		
		assertEquals(ePLoginBean.getLoginId(), "guestT");
		assertEquals(ePLoginBean.getLoginPwd(), "password");
		assertEquals(ePLoginBean.getHrid(), "hrId");
		assertEquals(ePLoginBean.getOrgUserId(), "guestT");
		assertNull(ePLoginBean.getBusinessDirectMenu());
		assertEquals(ePLoginBean.getSiteAccess(), "site_test");
		assertEquals(ePLoginBean.getLoginErrorMessage(), "error");
		assertNull(ePLoginBean.getUser());
		assertNull(ePLoginBean.getMenu());

		
	}
}
