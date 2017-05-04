/*-
 * ================================================================================
 * ECOMP Portal SDK
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
package org.openecomp.portalapp.portal.service;

import org.apache.cxf.transport.http.HTTPException;
import org.openecomp.portalapp.util.SystemType;

public interface ApplicationsRestClientService {
	public <T> T get(Class<T> clazz, long app, String restPath) throws HTTPException;
	public String getIncomingJsonString(long appId, String restPath) throws HTTPException;

	public <T> T post(Class<T> clazz, long appId, Object payload, String restPath) throws HTTPException;
	public <T> T post(Class<T> clazz, long appId, Object payload, String restPath, SystemType type) throws HTTPException;

	public <T> T put(Class<T> clazz, long appId, Object payload, String restPath) throws HTTPException;

	/**
	 * Sends a GET request to the specified application at the specified path.
	 * This is a workaround for a problem triggered by a superclass/subclass
	 * with identical field names.
	 * 
	 * @param clazz
	 *            Expected response type
	 * @param appId
	 *            Application ID
	 * @param restPath
	 *            Path at the remote application
	 * @param useJacksonMapper
	 *            If true, uses a com.fasterxml.jackson.databind.ObjectMapper to
	 *            translate the remote application response from JSON to an
	 *            object. Otherwise, uses a com.google.gson.Gson.
	 * @return Instance of the specified class
	 * @throws HTTPException
	 */
	public <T> T get(Class<T> clazz, long appId, String restPath, boolean useJacksonMapper) throws HTTPException;
}
