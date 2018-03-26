-- ---------------------------------------------------------------------------------------------------------------
-- This is the default data for the 2.1.0 Version of Portal database called portal - the Opensource project
-- First run the common Opensource DML; then run this file to add The Opensource only data
USE portal;

set foreign_key_checks=1;

--- update fn_menu for roles
UPDATE fn_menu
SET function_cd = 'menu_acc_admin'
WHERE  label = 'Roles';

--- update fn_menu for users
UPDATE fn_menu
SET function_cd = 'menu_acc_admin'
WHERE label = 'Users';


-- fn_user
Insert into fn_user (USER_ID, ORG_ID, MANAGER_ID,FIRST_NAME,MIDDLE_NAME,LAST_NAME,PHONE,FAX,CELLULAR,EMAIL,ADDRESS_ID,ALERT_METHOD_CD,HRID,ORG_USER_ID,ORG_CODE,LOGIN_ID,LOGIN_PWD,LAST_LOGIN_DATE,ACTIVE_YN,CREATED_ID,CREATED_DATE,MODIFIED_ID,MODIFIED_DATE,IS_INTERNAL_YN,ADDRESS_LINE_1,ADDRESS_LINE_2,CITY,STATE_CD,ZIP_CODE,COUNTRY_CD,LOCATION_CLLI,ORG_MANAGER_USERID,COMPANY,DEPARTMENT_NAME,JOB_TITLE,TIMEZONE,DEPARTMENT,BUSINESS_UNIT,BUSINESS_UNIT_NAME,COST_CENTER,FIN_LOC_CODE,SILO_STATUS) values (1,NULL,NULL,'Demo',NULL,'User',NULL,NULL,NULL,'demo@openecomp.org',NULL,NULL,NULL,'demo',NULL,'demo','4Gl6WL1bmwviYm+XZa6pS1vC0qKXWtn9wcZWdLx61L0=','2016-10-20 15:11:16','Y',NULL,'2016-10-14 21:00:00',1,'2016-10-20 15:11:16','N',NULL,NULL,NULL,'NJ',NULL,'US',NULL,NULL,NULL,NULL,NULL,10,NULL,NULL,NULL,NULL,NULL,NULL);

-- fn_appokYTaDrhzibcbGVq5mjkVQ==
Insert INTO fn_app (APP_ID, APP_NAME, APP_IMAGE_URL, APP_DESCRIPTION, APP_NOTES, APP_URL, APP_ALTERNATE_URL, APP_REST_ENDPOINT, ML_APP_NAME, ML_APP_ADMIN_ID, MOTS_ID, APP_PASSWORD, OPEN, ENABLED, THUMBNAIL, APP_USERNAME, UEB_KEY, UEB_SECRET, UEB_TOPIC_NAME, APP_TYPE, AUTH_CENTRAL, AUTH_NAMESPACE) values (1,'Default','assets/images/tmp/portal1.png','Some Default Description','Some Default Note','http://localhost','http://localhost','http://localhost:8080/ecompportal','EcompPortal','',NULL,'okYTaDrhzibcbGVq5mjkVQ==','N','N',NULL,'portal','EkrqsjQqZt4ZrPh6',NULL,NULL,1,'Y',NULL);

-- fn_user_role
Insert into fn_user_role (USER_ID,ROLE_ID,PRIORITY,APP_ID) values (1,1,NULL,1);
Insert into fn_user_role (USER_ID,ROLE_ID,PRIORITY,APP_ID) values (1,950,NULL,1);
Insert into fn_user_role (USER_ID,ROLE_ID,PRIORITY,APP_ID) values (1,999,NULL,1);

INSERT INTO cr_report 
	(rep_id, title, descr, public_yn, report_xml, create_id, create_date, maint_id, maint_date, menu_id, menu_approved_yn, owner_id, folder_id, dashboard_type_yn, dashboard_yn) 
	VALUES  (
	15,     
	'Application Usage Report Wid', 
	'',    
	'Y',       
	'<?xml version="1.0" encoding="UTF-8" standalone="yes"?>\n<customReport pageSize="200" reportType="Linear">\n    <reportName>Application Usage Report Wid</reportName>\n    <reportDescr></reportDescr>\n    <dbInfo>local</dbInfo>\n    <dbType>mysql</dbType>\n    <chartType>BarChart3D</chartType>\n    <chartWidth>700</chartWidth>\n    <chartHeight>500</chartHeight>\n    <showChartTitle>false</showChartTitle>\n    <public>false</public>\n    <hideFormFieldAfterRun>false</hideFormFieldAfterRun>\n    <createId>27</createId>\n    <createDate>2017-01-28-05:00</createDate>\n    <reportSQL>SELECT \n	l.date audit_date, \n	app_id app_id, \n	IF(CHAR_LENGTH(l.app_name) &gt;14, CONCAT(CONCAT(SUBSTR(l.app_name,1,7),\'...\'), SUBSTR(l.app_name, CHAR_LENGTH(l.app_name)-3,CHAR_LENGTH(l.app_name))) , l.app_name) app_name, \n	IFNULL(r.ct,0) ct \nfrom\n(\n	select a.Date, app_id, app_name\n	from (\n	    select curdate() - INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as Date\n	    from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a\n	    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b\n	    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c\n	) a, \n	(\n		SELECT  \n			app_id, app_name\n		from\n		(\n			select @rn := @rn+1 AS rowId, app_id, app_name from \n				(\n					select app_id, app_name, ct from \n					(\n						select affected_record_id, count(*) ct\n						from fn_audit_log l\n						where audit_date &gt; date_add( curdate(), interval -6 day)\n						and affected_record_id not in ( 1, -1)\n						and activity_cd in (\'tab_access\', \'app_access\')\n						and user_id = [USER_ID]\n						group by affected_record_id\n					) a, fn_app f\n					where a.affected_record_id = f.app_id\n					order by ct desc \n				) b,\n				(SELECT @rn := 0) t2\n		) mm where rowId &lt;= 4\n	)b\n	where a.Date between date_add( curdate(), interval -6 day) and  curdate()\n) l left outer join\n(\n	select app_name,  DATE(audit_date) audit_date_1 ,count(*) ct from fn_audit_log a, fn_app b\n	where user_id = [USER_ID]\n	and audit_date &gt; date_add( curdate(), interval -6 day)\n	and activity_cd in (\'tab_access\', \'app_access\')\n	and a.affected_record_id = b.app_id\n	and b.app_id &lt;&gt; 1\n	and b.app_id in \n	(\n		SELECT  \n			app_id\n		from\n		(\n			select @rn := @rn+1 AS rowId, app_id from \n				(\n					select app_id, ct from \n					(\n						select affected_record_id app_id, count(*) ct\n						from fn_audit_log \n						where audit_date &gt; date_add( curdate(), interval -6 day)\n						and affected_record_id not in ( 1, -1)\n						and activity_cd in (\'tab_access\', \'app_access\')\n						and user_id = [USER_ID]\n						group by affected_record_id\n					) a\n					order by ct desc \n				) b,\n				(SELECT @rn := 0) t2\n		) mm \n	)\n	group by app_name,  DATE(audit_date)\n) r\non l.Date = r.audit_date_1\nand l.app_name = r.app_name</reportSQL>\n    <reportTitle></reportTitle>\n    <reportSubTitle></reportSubTitle>\n    <reportHeader></reportHeader>\n    <frozenColumns>0</frozenColumns>\n    <emptyMessage>Your Search didn\'t yield any results.</emptyMessage>\n    <dataGridAlign>left</dataGridAlign>\n    <reportFooter></reportFooter>\n    <numFormCols>1</numFormCols>\n    <displayOptions>NNNNNNN</displayOptions>\n    <dataContainerHeight>100</dataContainerHeight>\n    <dataContainerWidth>100</dataContainerWidth>\n    <allowSchedule>N</allowSchedule>\n    <multiGroupColumn>N</multiGroupColumn>\n    <topDown>N</topDown>\n    <sizedByContent>N</sizedByContent>\n    <comment>N|</comment>\n    <dataSourceList>\n        <dataSource tableId="du0">\n            <tableName>DUAL</tableName>\n            <tablePK></tablePK>\n            <displayName>DUAL</displayName>\n            <dataColumnList>\n                <dataColumn colId="audit_date">\n                    <tableId>du0</tableId>\n                    <dbColName>l.date</dbColName>\n                    <colName>l.date</colName>\n                    <displayName>audit_date_1</displayName>\n                    <displayWidth>10</displayWidth>\n                    <displayWidthInPxls>nullpxpx</displayWidthInPxls>\n                    <displayAlignment>Left</displayAlignment>\n                    <orderSeq>1</orderSeq>\n                    <visible>true</visible>\n                    <calculated>true</calculated>\n                    <colType>VARCHAR2</colType>\n                    <groupBreak>false</groupBreak>\n                    <colOnChart>LEGEND</colOnChart>\n                    <chartSeq>1</chartSeq>\n                    <chartColor></chartColor>\n                    <chartLineType></chartLineType>\n                    <chartSeries>false</chartSeries>\n                    <dbColType>VARCHAR2</dbColType>\n                    <chartGroup></chartGroup>\n                    <yAxis></yAxis>\n                </dataColumn>\n                <dataColumn colId="app_id">\n                    <tableId>du0</tableId>\n                    <dbColName>app_id</dbColName>\n                    <colName>app_id</colName>\n                    <displayName>app_id</displayName>\n                    <displayWidth>10</displayWidth>\n                    <displayAlignment>Left</displayAlignment>\n                    <orderSeq>2</orderSeq>\n                    <visible>true</visible>\n                    <calculated>true</calculated>\n                    <colType>VARCHAR2</colType>\n                    <groupBreak>false</groupBreak>\n                    <dbColType>VARCHAR2</dbColType>\n                </dataColumn>\n                <dataColumn colId="app_name">\n                    <tableId>du0</tableId>\n                    <dbColName>IF(CHAR_LENGTH(l.app_name) &gt;14, CONCAT(CONCAT(SUBSTR(l.app_name,1,7),\'...\'), SUBSTR(l.app_name, CHAR_LENGTH(l.app_name)-3,CHAR_LENGTH(l.app_name))) , l.app_name)</dbColName>\n                    <colName>IF(CHAR_LENGTH(l.app_name) &gt;14, CONCAT(CONCAT(SUBSTR(l.app_name,1,7),\'...\'), SUBSTR(l.app_name, CHAR_LENGTH(l.app_name)-3,CHAR_LENGTH(l.app_name))) , l.app_name)</colName>\n                    <displayName>app_name</displayName>\n                    <displayWidth>10</displayWidth>\n                    <displayWidthInPxls>nullpxpx</displayWidthInPxls>\n                    <displayAlignment>Left</displayAlignment>\n                    <orderSeq>3</orderSeq>\n                    <visible>true</visible>\n                    <calculated>true</calculated>\n                    <colType>VARCHAR2</colType>\n                    <groupBreak>false</groupBreak>\n                    <chartSeq>2</chartSeq>\n                    <chartColor></chartColor>\n                    <chartLineType></chartLineType>\n                    <chartSeries>true</chartSeries>\n                    <dbColType>VARCHAR2</dbColType>\n                    <chartGroup></chartGroup>\n                    <yAxis></yAxis>\n                </dataColumn>\n                <dataColumn colId="ct">\n                    <tableId>du0</tableId>\n                    <dbColName>IFNULL(r.ct,0)</dbColName>\n                    <colName>IFNULL(r.ct,0)</colName>\n                    <displayName>ct</displayName>\n                    <displayWidth>10</displayWidth>\n                    <displayWidthInPxls>nullpxpx</displayWidthInPxls>\n                    <displayAlignment>Left</displayAlignment>\n                    <orderSeq>4</orderSeq>\n                    <visible>true</visible>\n                    <calculated>true</calculated>\n                    <colType>VARCHAR2</colType>\n                    <groupBreak>false</groupBreak>\n                    <colOnChart>0</colOnChart>\n                    <chartSeq>1</chartSeq>\n                    <chartColor></chartColor>\n                    <chartLineType></chartLineType>\n                    <chartSeries>false</chartSeries>\n                    <dbColType>VARCHAR2</dbColType>\n                    <chartGroup></chartGroup>\n                    <yAxis></yAxis>\n                </dataColumn>\n            </dataColumnList>\n        </dataSource>\n    </dataSourceList>\n    <reportInNewWindow>false</reportInNewWindow>\n    <displayFolderTree>false</displayFolderTree>\n    <maxRowsInExcelDownload>500</maxRowsInExcelDownload>\n    <chartAdditionalOptions>\n        <chartOrientation>vertical</chartOrientation>\n        <hidechartLegend>N</hidechartLegend>\n        <legendPosition>bottom</legendPosition>\n        <labelAngle>up90</labelAngle>\n        <rangeAxisUpperLimit></rangeAxisUpperLimit>\n        <rangeAxisLowerLimit></rangeAxisLowerLimit>\n        <animate>true</animate>\n        <animateAnimatedChart>true</animateAnimatedChart>\n        <stacked>true</stacked>\n        <barControls>false</barControls>\n        <xAxisDateType>false</xAxisDateType>\n        <lessXaxisTickers>false</lessXaxisTickers>\n        <timeAxis>true</timeAxis>\n        <logScale>false</logScale>\n        <topMargin>30</topMargin>\n        <bottomMargin>50</bottomMargin>\n        <rightMargin>60</rightMargin>\n        <leftMargin>100</leftMargin>\n    </chartAdditionalOptions>\n    <folderId>NULL</folderId>\n    <isOneTimeScheduleAllowed>N</isOneTimeScheduleAllowed>\n    <isHourlyScheduleAllowed>N</isHourlyScheduleAllowed>\n    <isDailyScheduleAllowed>N</isDailyScheduleAllowed>\n    <isDailyMFScheduleAllowed>N</isDailyMFScheduleAllowed>\n    <isWeeklyScheduleAllowed>N</isWeeklyScheduleAllowed>\n    <isMonthlyScheduleAllowed>N</isMonthlyScheduleAllowed>\n</customReport>\n', 
	1, 
	now(), 
	1, 
	now(), 
	'', 
	'N', 
	(select user_id from fn_user where org_user_id = 'demo'), 
	NULL, 
	'N', 
	'N'
	);

-- new for 1707
INSERT INTO cr_report 
	(rep_id, title, descr, public_yn, report_xml, create_id, create_date, maint_id, maint_date, menu_id, menu_approved_yn, owner_id, folder_id, dashboard_type_yn, dashboard_yn) 
	VALUES  (
	18,     
	'Application Usage bar Wid',
	'',    
	'Y',       
	'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<customReport pageSize=\"200\" reportType=\"Linear\">\n    <reportName>Application Usage Line Wid</reportName>\n    <reportDescr></reportDescr>\n    <dbInfo>local</dbInfo>\n    <dbType>mysql</dbType>\n    <chartType>TimeSeriesChart</chartType>\n    <chartMultiSeries>N</chartMultiSeries>\n    <chartWidth>700</chartWidth>\n    <chartHeight>300</chartHeight>\n    <showChartTitle>false</showChartTitle>\n    <public>false</public>\n    <hideFormFieldAfterRun>false</hideFormFieldAfterRun>\n    <createId>27</createId>\n    <createDate>2017-01-28-05:00</createDate>\n    <reportSQL>SELECT \n	l.date audit_date, \n	IF(CHAR_LENGTH(l.app_name) &gt;14, CONCAT(CONCAT(SUBSTR(l.app_name,1,7),\'...\'), SUBSTR(l.app_name, CHAR_LENGTH(l.app_name)-3,CHAR_LENGTH(l.app_name))) , l.app_name) app_name, \n	IFNULL(r.ct,0) ct \nfrom\n(\n	select a.Date, app_id, app_name\n	from (\n	    select curdate() - INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as Date\n	    from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a\n	    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b\n	    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c\n	) a, \n	(\n		SELECT  \n			app_id, app_name\n		from\n		(\n			select @rn := @rn+1 AS rowId, app_id, app_name from \n				(\n					select app_id, app_name, ct from \n					(\n						select affected_record_id, count(*) ct\n						from fn_audit_log l\n						where audit_date &gt; date_add( curdate(), interval -30 day)\n						and affected_record_id not in ( 1, -1)\n						and activity_cd in (\'tab_access\', \'app_access\')\n						and user_id = [USER_ID]\n						group by affected_record_id\n					) a, fn_app f\n					where a.affected_record_id = f.app_id\n					order by ct desc \n				) b,\n				(SELECT @rn := 0) t2\n		) mm where rowId &lt;= 4\n	)b\n	where a.Date between date_add( curdate(), interval -30 day) and  curdate()\n) l left outer join\n(\n	select app_name,  DATE(audit_date) audit_date_1 ,count(*) ct from fn_audit_log a, fn_app b\n	where user_id = [USER_ID]\n	and audit_date &gt; date_add( curdate(), interval -30 day)\n	and activity_cd in (\'tab_access\', \'app_access\')\n	and a.affected_record_id = b.app_id\n	and b.app_id &lt;&gt; 1\n	and b.app_id in \n	(\n		SELECT  \n			app_id\n		from\n		(\n			select @rn := @rn+1 AS rowId, app_id from \n				(\n					select app_id, ct from \n					(\n						select affected_record_id app_id, count(*) ct\n						from fn_audit_log \n						where audit_date &gt; date_add( curdate(), interval -30 day)\n						and affected_record_id not in ( 1, -1)\n						and activity_cd in (\'tab_access\', \'app_access\')\n						and user_id = [USER_ID]\n						group by affected_record_id\n					) a\n					order by ct desc \n				) b,\n				(SELECT @rn := 0) t2\n		) mm \n	)\n	group by app_name,  DATE(audit_date)\n) r\non l.Date = r.audit_date_1\nand l.app_name = r.app_name</reportSQL>\n    <reportTitle></reportTitle>\n    <reportSubTitle></reportSubTitle>\n    <reportHeader></reportHeader>\n    <frozenColumns>0</frozenColumns>\n    <emptyMessage>Your Search didn\'t yield any results.</emptyMessage>\n    <dataGridAlign>left</dataGridAlign>\n    <reportFooter></reportFooter>\n    <numFormCols>1</numFormCols>\n    <displayOptions>NNNNNNN</displayOptions>\n    <dataContainerHeight>100</dataContainerHeight>\n    <dataContainerWidth>100</dataContainerWidth>\n    <allowSchedule>N</allowSchedule>\n    <multiGroupColumn>N</multiGroupColumn>\n    <topDown>N</topDown>\n    <sizedByContent>N</sizedByContent>\n    <comment>N|</comment>\n    <dataSourceList>\n        <dataSource tableId=\"du0\">\n            <tableName>DUAL</tableName>\n            <tablePK></tablePK>\n            <displayName>DUAL</displayName>\n            <dataColumnList>\n                <dataColumn colId=\"audit_date\">\n                    <tableId>du0</tableId>\n                    <dbColName>l.date</dbColName>\n                    <colName>l.date</colName>\n                    <displayName>audit_date_1</displayName>\n                    <displayWidth>10</displayWidth>\n                    <displayWidthInPxls>nullpxpx</displayWidthInPxls>\n                    <displayAlignment>Left</displayAlignment>\n                    <orderSeq>1</orderSeq>\n                    <visible>true</visible>\n                    <calculated>true</calculated>\n                    <colType>VARCHAR2</colType>\n                    <groupBreak>false</groupBreak>\n                    <colOnChart>LEGEND</colOnChart>\n                    <chartSeq>1</chartSeq>\n                    <chartSeries>false</chartSeries>\n                    <isRangeAxisFilled>false</isRangeAxisFilled>\n                    <drillinPoPUp>false</drillinPoPUp>\n                    <dbColType>VARCHAR2</dbColType>\n                    <enhancedPagination>false</enhancedPagination>\n                </dataColumn>\n                <dataColumn colId=\"app_name\">\n                    <tableId>du0</tableId>\n                    <dbColName>IF(CHAR_LENGTH(l.app_name) &gt;14, CONCAT(CONCAT(SUBSTR(l.app_name,1,7),\'...\'), SUBSTR(l.app_name, CHAR_LENGTH(l.app_name)-3,CHAR_LENGTH(l.app_name))) , l.app_name)</dbColName>\n                    <colName>IF(CHAR_LENGTH(l.app_name) &gt;14, CONCAT(CONCAT(SUBSTR(l.app_name,1,7),\'...\'), SUBSTR(l.app_name, CHAR_LENGTH(l.app_name)-3,CHAR_LENGTH(l.app_name))) , l.app_name)</colName>\n                    <displayName>app_name</displayName>\n                    <displayWidth>10</displayWidth>\n                    <displayWidthInPxls>nullpxpx</displayWidthInPxls>\n                    <displayAlignment>Left</displayAlignment>\n                    <orderSeq>2</orderSeq>\n                    <visible>true</visible>\n                    <calculated>true</calculated>\n                    <colType>VARCHAR2</colType>\n                    <groupBreak>false</groupBreak>\n                    <colOnChart>0</colOnChart>\n                    <chartSeq>2</chartSeq>\n                    <chartColor></chartColor>\n                    <chartLineType></chartLineType>\n                    <chartSeries>true</chartSeries>\n                    <isRangeAxisFilled>false</isRangeAxisFilled>\n                    <drillinPoPUp>false</drillinPoPUp>\n                    <dbColType>VARCHAR2</dbColType>\n                    <chartGroup></chartGroup>\n                    <yAxis></yAxis>\n                    <enhancedPagination>false</enhancedPagination>\n                </dataColumn>\n                <dataColumn colId=\"ct\">\n                    <tableId>du0</tableId>\n                    <dbColName>IFNULL(r.ct,0)</dbColName>\n                    <colName>IFNULL(r.ct,0)</colName>\n                    <displayName>ct</displayName>\n                    <displayWidth>10</displayWidth>\n                    <displayWidthInPxls>nullpxpx</displayWidthInPxls>\n                    <displayAlignment>Left</displayAlignment>\n                    <orderSeq>3</orderSeq>\n                    <visible>true</visible>\n                    <calculated>true</calculated>\n                    <colType>VARCHAR2</colType>\n                    <groupBreak>false</groupBreak>\n                    <colOnChart>0</colOnChart>\n                    <chartSeq>1</chartSeq>\n                    <chartColor></chartColor>\n                    <chartLineType></chartLineType>\n                    <chartSeries>false</chartSeries>\n                    <isRangeAxisFilled>false</isRangeAxisFilled>\n                    <drillinPoPUp>false</drillinPoPUp>\n                    <dbColType>VARCHAR2</dbColType>\n                    <chartGroup></chartGroup>\n                    <yAxis></yAxis>\n                    <enhancedPagination>false</enhancedPagination>\n                </dataColumn>\n            </dataColumnList>\n        </dataSource>\n    </dataSourceList>\n    <reportInNewWindow>false</reportInNewWindow>\n    <displayFolderTree>false</displayFolderTree>\n    <maxRowsInExcelDownload>500</maxRowsInExcelDownload>\n    <chartAdditionalOptions>\n        <chartOrientation>vertical</chartOrientation>\n        <hidechartLegend>N</hidechartLegend>\n        <legendPosition>bottom</legendPosition>\n        <labelAngle>down45</labelAngle>\n        <animate>true</animate>\n        <animateAnimatedChart>true</animateAnimatedChart>\n        <stacked>true</stacked>\n        <barControls>false</barControls>\n        <xAxisDateType>false</xAxisDateType>\n        <lessXaxisTickers>false</lessXaxisTickers>\n        <timeAxis>true</timeAxis>\n        <timeSeriesRender>line</timeSeriesRender>\n        <multiSeries>false</multiSeries>\n        <showXAxisLabel>false</showXAxisLabel>\n        <addXAxisTickers>false</addXAxisTickers>\n        <topMargin>30</topMargin>\n        <bottomMargin>50</bottomMargin>\n        <rightMargin>60</rightMargin>\n        <leftMargin>100</leftMargin>\n    </chartAdditionalOptions>\n    <folderId>NULL</folderId>\n    <drillURLInPoPUpPresent>false</drillURLInPoPUpPresent>\n    <isOneTimeScheduleAllowed>N</isOneTimeScheduleAllowed>\n    <isHourlyScheduleAllowed>N</isHourlyScheduleAllowed>\n    <isDailyScheduleAllowed>N</isDailyScheduleAllowed>\n    <isDailyMFScheduleAllowed>N</isDailyMFScheduleAllowed>\n    <isWeeklyScheduleAllowed>N</isWeeklyScheduleAllowed>\n    <isMonthlyScheduleAllowed>N</isMonthlyScheduleAllowed>\n</customReport>\n',
	1, 
	now(), 
	1, 
	now(), 
	'', 
	'N', 
	(select user_id from fn_user where org_user_id = 'demo'), 
	NULL, 
	'N', 
	'N'
	);
	
INSERT INTO cr_report 
	(rep_id, title, descr, public_yn, report_xml, create_id, create_date, maint_id, maint_date, menu_id, menu_approved_yn, owner_id, folder_id, dashboard_type_yn, dashboard_yn) 
	VALUES  (
	20,     
	'Average time spend on portal',
	'',    
	'Y',       
	'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<customReport pageSize=\"200\" reportType=\"Linear\">\n    <reportName>Average time spend on portal</reportName>\n    <reportDescr></reportDescr>\n    <dbInfo>local</dbInfo>\n    <dbType>mysql</dbType>\n    <chartType>TimeSeriesChart</chartType>\n    <chartMultiSeries>N</chartMultiSeries>\n    <chartWidth>700</chartWidth>\n    <chartHeight>300</chartHeight>\n    <showChartTitle>false</showChartTitle>\n    <public>true</public>\n    <hideFormFieldAfterRun>false</hideFormFieldAfterRun>\n    <createId>27</createId>\n    <createDate>2017-01-28-05:00</createDate>\n    <reportSQL>SELECT \n	d.dat audit_date, \n	\'# of Minutes\' app, \n	coalesce(diff, null, 0) mins \nfrom\n(\n	select * from\n	(\n	select curdate() - INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as dat\n	from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a\n	cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b\n	cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c \n	) d where d.dat between date_add( curdate(), interval -30 day) and  curdate()\n) d left outer join\n(\n	select dat, mi, mx, TIMESTAMPDIFF(MINUTE, coalesce(mi, null, 0), coalesce(mx, null, 0)) + 30  diff\n	from\n	(\n		select DATE(audit_date) dat, coalesce(min(audit_date), null, 0) mi, coalesce(max(audit_date), null, 0) mx\n		from fn_audit_log \n		where user_id = [USER_ID] and DATE(audit_date) between CURDATE()-300 and CURDATE()\n		group by DATE(audit_date)\n	) a\n) a\non a.dat = d.dat\norder by 1</reportSQL>\n    <reportTitle></reportTitle>\n    <reportSubTitle></reportSubTitle>\n    <reportHeader></reportHeader>\n    <frozenColumns>0</frozenColumns>\n    <emptyMessage>Your Search didn\'t yield any results.</emptyMessage>\n    <dataGridAlign>left</dataGridAlign>\n    <reportFooter></reportFooter>\n    <numFormCols>1</numFormCols>\n    <displayOptions>NNNNNNN</displayOptions>\n    <dataContainerHeight>100</dataContainerHeight>\n    <dataContainerWidth>100</dataContainerWidth>\n    <allowSchedule>N</allowSchedule>\n    <multiGroupColumn>N</multiGroupColumn>\n    <topDown>N</topDown>\n    <sizedByContent>N</sizedByContent>\n    <comment>N|</comment>\n    <dataSourceList>\n        <dataSource tableId=\"du0\">\n            <tableName>DUAL</tableName>\n            <tablePK></tablePK>\n            <displayName>DUAL</displayName>\n            <dataColumnList>\n                <dataColumn colId=\"audit_date\">\n                    <tableId>du0</tableId>\n                    <dbColName>d.dat</dbColName>\n                    <colName>d.dat</colName>\n                    <displayName>audit_date_1</displayName>\n                    <displayWidth>10</displayWidth>\n                    <displayWidthInPxls>nullpxpx</displayWidthInPxls>\n                    <displayAlignment>Left</displayAlignment>\n                    <orderSeq>1</orderSeq>\n                    <visible>true</visible>\n                    <calculated>true</calculated>\n                    <colType>VARCHAR2</colType>\n                    <groupBreak>false</groupBreak>\n                    <colOnChart>LEGEND</colOnChart>\n                    <chartSeq>1</chartSeq>\n                    <chartSeries>false</chartSeries>\n                    <isRangeAxisFilled>false</isRangeAxisFilled>\n                    <drillinPoPUp>false</drillinPoPUp>\n                    <dbColType>VARCHAR2</dbColType>\n                    <enhancedPagination>false</enhancedPagination>\n                </dataColumn>\n                <dataColumn colId=\"app\">\n                    <tableId>du0</tableId>\n                    <dbColName>\'# of Minutes\'</dbColName>\n                    <colName>\'# of Minutes\'</colName>\n                    <displayName>app</displayName>\n                    <displayWidth>10</displayWidth>\n                    <displayWidthInPxls>nullpxpx</displayWidthInPxls>\n                    <displayAlignment>Left</displayAlignment>\n                    <orderSeq>2</orderSeq>\n                    <visible>true</visible>\n                    <calculated>true</calculated>\n                    <colType>VARCHAR2</colType>\n                    <groupBreak>false</groupBreak>\n                    <chartSeries>true</chartSeries>\n                    <dbColType>VARCHAR2</dbColType>\n                </dataColumn>\n                <dataColumn colId=\"mins\">\n                    <tableId>du0</tableId>\n                    <dbColName>coalesce(diff, null, 0)</dbColName>\n                    <colName>coalesce(diff, null, 0)</colName>\n                    <displayName>mins</displayName>\n                    <displayWidth>10</displayWidth>\n                    <displayWidthInPxls>nullpxpx</displayWidthInPxls>\n                    <displayAlignment>Left</displayAlignment>\n                    <orderSeq>3</orderSeq>\n                    <visible>true</visible>\n                    <calculated>true</calculated>\n                    <colType>VARCHAR2</colType>\n                    <groupBreak>false</groupBreak>\n                    <colOnChart>0</colOnChart>\n                    <chartSeq>1</chartSeq>\n                    <chartColor></chartColor>\n                    <chartLineType></chartLineType>\n                    <chartSeries>false</chartSeries>\n                    <dbColType>VARCHAR2</dbColType>\n                    <chartGroup></chartGroup>\n                    <yAxis></yAxis>\n                </dataColumn>\n            </dataColumnList>\n        </dataSource>\n    </dataSourceList>\n    <reportInNewWindow>false</reportInNewWindow>\n    <displayFolderTree>false</displayFolderTree>\n    <maxRowsInExcelDownload>500</maxRowsInExcelDownload>\n    <chartAdditionalOptions>\n        <chartOrientation>vertical</chartOrientation>\n        <hidechartLegend>N</hidechartLegend>\n        <legendPosition>bottom</legendPosition>\n        <labelAngle>down45</labelAngle>\n        <animate>true</animate>\n        <animateAnimatedChart>true</animateAnimatedChart>\n        <stacked>true</stacked>\n        <barControls>false</barControls>\n        <xAxisDateType>false</xAxisDateType>\n        <lessXaxisTickers>false</lessXaxisTickers>\n        <timeAxis>true</timeAxis>\n        <timeSeriesRender>line</timeSeriesRender>\n        <multiSeries>false</multiSeries>\n        <showXAxisLabel>false</showXAxisLabel>\n        <addXAxisTickers>false</addXAxisTickers>\n        <topMargin>30</topMargin>\n        <bottomMargin>50</bottomMargin>\n        <rightMargin>60</rightMargin>\n        <leftMargin>100</leftMargin>\n    </chartAdditionalOptions>\n    <folderId>NULL</folderId>\n    <drillURLInPoPUpPresent>false</drillURLInPoPUpPresent>\n    <isOneTimeScheduleAllowed>N</isOneTimeScheduleAllowed>\n    <isHourlyScheduleAllowed>N</isHourlyScheduleAllowed>\n    <isDailyScheduleAllowed>N</isDailyScheduleAllowed>\n    <isDailyMFScheduleAllowed>N</isDailyMFScheduleAllowed>\n    <isWeeklyScheduleAllowed>N</isWeeklyScheduleAllowed>\n    <isMonthlyScheduleAllowed>N</isMonthlyScheduleAllowed>\n</customReport>\n',
	1, 
	now(), 
	1, 
	now(), 
	'', 
	'N', 
	(select user_id from fn_user where org_user_id = 'demo'), 
	NULL, 
	'N', 
	'N'
	);
    
    
insert into ep_app_function (app_id, function_cd, function_name) values
(1,	'url|edit_notification|*',	'User Notification'),
(1,	'url|getAdminNotifications|*',	'Admin Notifications'),
(1,	'url|login|*',	'Login'),
(1,	'menu|menu_admin|*','Admin Menu'),
(1,'menu|menu_home|*','Home Menu'),
(1,	'menu|menu_logout|*','Logout Menu'),
(1,	'menu|menu_web_analytics|*','Web Analytics'),
(1,	'url|saveNotification|*','publish notifications'),
(1,	'url|url_role.htm|*','role page'),
(1,	'url|url_welcome.htm|*','welcome page'),
(1, 'menu|menu_acc_admin|*','Admin Account Menu'),
(1,'url|addWebAnalyticsReport|*','Add Web Analytics Report'), 
(1,'url|appsFullList|*','Apps Full List'),
(1,'url|centralizedApps|*','Centralized Apps'),
(1,'url|functionalMenu|*','Functional Menu'),
(1,'url|getAllWebAnalytics|*','Get All Web Analytics'),
(1,'url|getFunctionalMenuRole|*','Get Functional Menu Role'),
(1,'url|getNotificationAppRoles|*','Get Notification App Roles'),
(1,'url|getUserAppsWebAnalytics|*','Get User Apps Web Analytics'),
(1,'url|getUserJourneyAnalyticsReport|*','Get User Journey Report'),
(1,'url|get_roles%2f%2a|*','getRolesOfApp'),
(1,'url|get_role_functions%2f%2a|*','Get Role Functions'),
(1,'url|notification_code|*','Notification Code'),
(1,'url|role_function_list%2fsaveRoleFunction%2f%2a|*','Save Role Function'),
(1,'url|syncRoles|*','SyncRoles'),
(1,'url|userAppRoles|*','userAppRoles'),
(1,'url|userApps|*','User Apps')
;


insert into ep_app_role_function (id, app_id, role_id, function_cd, role_app_id) values
(1, 1, 1, 'url|login|*', null),
(2, 1, 1, 'menu|menu_admin|*', null),
(3, 1, 1, 'menu|menu_home|*', null),
(4, 1, 1, 'menu|menu_logout|*', null),
(5, 1, 16, 'url|login|*', null),
(6, 1, 16, 'menu|menu_home|*', null),
(7, 1, 16, 'menu|menu_logout|*', null),
(8, 1, 950, 'url|edit_notification|*', null),
(9, 1, 950, 'url|getAdminNotifications|*', null),
(10,1, 950, 'url|saveNotification|*', null),
(11,1, 999,'url|userAppRoles|*', null),
(12,1, 999, 'url|getAdminNotifications|*', null),
(13,1, 999,'url|userApps|*', null),
(14,1, 1010, 'menu|menu_web_analytics|*', null),
(15, 1, 2115, 'menu|menu_web_analytics|*', null),
(16, 1 , 1, 'menu|menu_acc_admin|*' , null),
(17, 1 , 999 ,'menu|menu_acc_admin|*', null),
(18,1,999,'url|centralizedApps|*', null),
(19,1,999,'url|getAllWebAnalytics|*', null),
(20,1,999,'url|getFunctionalMenuRole|*', null),
(21,1,999,'url|getNotificationAppRoles|*', null),
(22,1,999,'url|getUserAppsWebAnalytics|*', null),
(23,1,999,'url|getUserJourneyAnalyticsReport|*', null),
(24,1,999,'url|get_roles%2f%2a|*', null),
(25,1,999,'url|get_role_functions%2f%2a|*', null),
(26,1,999,'url|notification_code|*', null),
(27,1,999,'url|role_function_list%2fsaveRoleFunction%2f%2a|*', null),
(28,1,999,'url|syncRoles|*', null);

commit;    