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
package org.openecomp.portalapp.portal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openecomp.portalapp.portal.domain.WidgetCatalogParameter;
import org.openecomp.portalapp.portal.logging.aop.EPMetricsLog;
import org.openecomp.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.openecomp.portalsdk.core.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

@Service("widgetParameterService")
@EnableAspectJAutoProxy
@EPMetricsLog
public class WidgetParameterServiceImpl implements WidgetParameterService{
	EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(WidgetParameterServiceImpl.class);

	@Autowired
	private DataAccessService dataAccessService;

	@SuppressWarnings("unchecked")
	@Override
	public WidgetCatalogParameter getUserParamById(Long widgetId, Long userId, Long paramId) {
		List<Criterion> restrictionsList = new ArrayList<Criterion>();
		Criterion widgetIdCrit = Restrictions.eq("widgetId", widgetId);
		restrictionsList.add(widgetIdCrit);
		Criterion attIdCrit = Restrictions.eq("userId", userId);
		restrictionsList.add(attIdCrit);
		Criterion paramIdCrit = Restrictions.eq("paramId", paramId);
		restrictionsList.add(paramIdCrit);
		
		
		WidgetCatalogParameter widgetParam = null;
		List<WidgetCatalogParameter> list = (List<WidgetCatalogParameter>) dataAccessService
				.getList(WidgetCatalogParameter.class, null, restrictionsList, null);
		if(list.size() != 0)
			widgetParam = list.get(0);
		logger.debug(EELFLoggerDelegate.debugLogger,
				"getUserParamById: widget parameters: " + widgetParam);
		return widgetParam;
	}

	@Override
	public void saveUserParameter(WidgetCatalogParameter newParameter) {
		dataAccessService.saveDomainObject(newParameter, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WidgetCatalogParameter> getUserParameterById(Long paramId) {
		List<Criterion> restrictionsList = new ArrayList<Criterion>();
		Criterion paramIdCrit = Restrictions.eq("paramId", paramId);
		restrictionsList.add(paramIdCrit);
		List<WidgetCatalogParameter> list = (List<WidgetCatalogParameter>) dataAccessService
				.getList(WidgetCatalogParameter.class, null, restrictionsList, null);
		return list;
	}

	@Override
	public void deleteUserParameterById(Long paramId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("paramId", Long.toString(paramId));
		dataAccessService.executeNamedQuery("deleteWidgetCatalogParameter", params, null);
		dataAccessService.executeNamedQuery("deleteMicroserviceParameterById", params, null);
	}
}
