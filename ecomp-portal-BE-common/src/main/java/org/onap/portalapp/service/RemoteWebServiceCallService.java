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
package org.onap.portalapp.service;

public interface RemoteWebServiceCallService {

	/**
	 * Answers whether the specified credentials match application information
	 * in the database.
	 * 
	 * @param secretKey
	 *            Key used to decrypt passwords; ignored if null.
	 * @param requestUebKey
	 *            UEB key that identifies the application
	 * @param requestUserName
	 *            User name for the application
	 * @param requestPassword
	 *            Password for the application
	 * @return True if the UEB key and the credentials match the database
	 *         entries; else false.
	 * @throws Exception
	 *             If decryption fails.
	 */
	public boolean verifyRESTCredential(String secretKey, String requestUebKey, String requestUserName,
			String requestPassword) throws Exception;

	/**
	 * 
	 * @param requestUebKey
	 *            UEB key
	 * @return boolean
	 * @throws Exception
	 *             on error
	 */
	public boolean verifyAppKeyCredential(String requestUebKey) throws Exception;

}
