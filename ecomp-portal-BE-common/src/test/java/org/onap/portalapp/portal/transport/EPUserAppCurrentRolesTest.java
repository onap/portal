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
package org.onap.portalapp.portal.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.onap.portalapp.portal.transport.EPUserAppCurrentRoles;

public class EPUserAppCurrentRolesTest {

	private static final String TEST="test";
	private static final Long ID=1l;
	private  EPUserAppCurrentRoles mockEPUserAppCurrentRoles(){
		EPUserAppCurrentRoles epUserAppCurrentRoles = new EPUserAppCurrentRoles();
			
		epUserAppCurrentRoles.setRoleName(TEST);
		epUserAppCurrentRoles.setUserId(ID);
		epUserAppCurrentRoles.setPriority((Integer) 123);
		epUserAppCurrentRoles.setRoleId(ID);
		
		return epUserAppCurrentRoles;
	}
	
	
	@Test
	public void epUserAppCurrentRolesTest(){
		EPUserAppCurrentRoles epUserAppCurrentRoles = mockEPUserAppCurrentRoles();
		
		EPUserAppCurrentRoles epUserAppCurrentRoles1 = new EPUserAppCurrentRoles();
		
		epUserAppCurrentRoles1.setRoleName(epUserAppCurrentRoles.getRoleName());
		epUserAppCurrentRoles1.setUserId(epUserAppCurrentRoles.getUserId());
		epUserAppCurrentRoles1.setPriority(epUserAppCurrentRoles.getPriority());
		epUserAppCurrentRoles1.setRoleId(epUserAppCurrentRoles.getRoleId());
		
		assertEquals(epUserAppCurrentRoles.hashCode(), epUserAppCurrentRoles1.hashCode());
		assertTrue(epUserAppCurrentRoles.equals(epUserAppCurrentRoles1));
		assertFalse(epUserAppCurrentRoles1.equals(null));
		epUserAppCurrentRoles1.setUserId(null);
		assertFalse(epUserAppCurrentRoles1.equals(epUserAppCurrentRoles));
		epUserAppCurrentRoles1.setRoleName(null);
		assertFalse(epUserAppCurrentRoles1.equals(epUserAppCurrentRoles));
		epUserAppCurrentRoles1.setRoleId(null);
		assertFalse(epUserAppCurrentRoles1.equals(epUserAppCurrentRoles));
		epUserAppCurrentRoles1.setPriority(null);
		assertFalse(epUserAppCurrentRoles1.equals(epUserAppCurrentRoles));
		
	}
}
