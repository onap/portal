-- --------------------------------------------------------------------------------------------
-- This is the common default data for 3.3.0 Version of Portal database called portal 

USE portal;

set foreign_key_checks=1; 


INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (1,2,'Home');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (1,3,'Application Catalog');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (1,4,'Widget Catalog');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (1,5,'Admins');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (1,6,'Roles');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (1,7,'Users');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (1,8,'Portal Admins');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (1,9,'Application Onboarding');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (1,10,'Widget Onboarding');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (1,11,'Edit Functional Menu');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (1,12,'User Notifications');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (1,13,'Microservice Onboarding');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (1,15,'App Account Management');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (2,2,'主页');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (2,3,'应用目录');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (2,4,'部件目录');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (2,5,'管理员');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (2,6,'角色');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (2,7,'用户');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (2,8,'门户管理员');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (2,9,'应用管理');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (2,10,'部件管理');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (2,11,'编辑功能菜单');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (2,12,'用户通知');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (2,13,'微服务管理');
INSERT INTO fn_display_text (language_id,text_id,text_label) VALUES (2,15,'应用账户管理');

INSERT INTO fn_language (language_name,language_alias) VALUES ('English','EN');
INSERT INTO fn_language (language_name,language_alias) VALUES ('简体中文','CN');

-- FN_FUNCTION
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_process','Process List');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_job','Job Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_job_create','Job Create');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_job_designer','Process in Designer view');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_task','Task Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_task_search','Task Search');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_map','Map Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_sample','Sample Pages Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('login','Login');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_home','Home Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_customer','Customer Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_reports','Reports Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_profile','Profile Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_admin','Admin Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_feedback','Feedback Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_help','Help Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_logout','Logout Menu'); 
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_notes','Notes Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_ajax','Ajax Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_customer_create','Customer Create');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_profile_create','Profile Create');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_profile_import','Profile Import');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_tab','Sample Tab Menu');
Insert into fn_function (FUNCTION_CD,FUNCTION_NAME) values ('view_reports','View Raptor reports');

-- new 1702
Insert into fn_function (function_cd,function_name) values ('edit_notification','User Notification');
Insert INTO fn_function (function_cd,function_name) values ('getAdminNotifications', 'Admin Notifications');
Insert INTO fn_function (function_cd,function_name) values ('saveNotification', 'publish notifications');
-- end new 1702

-- new 1707
INSERT INTO fn_function	(function_cd, function_name) VALUES ('menu_web_analytics', 'Web Analytics');
-- end new 1707

-- new 3_2
insert into fn_function (function_cd, function_name) values ('menu_acc_admin', 'Account Admin Menu');
insert into fn_function (function_cd, function_name) values ('menu_app_onboarding', 'App Onboarding menu');
-- end new 3_2

-- FN_LU_ACTIVITY
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('add_role','add_role');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('remove_role','remove_role');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('add_user_role','add_user_role');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('remove_user_role','remove_user_role');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('add_role_function','add_role_function');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('remove_role_function','remove_role_function');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('add_child_role','add_child_role');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('remove_child_role','remove_child_role');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('mobile_login','Mobile Login');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('mobile_logout','Mobile Logout');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('login','Login');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('logout','Logout');

-- new 1610.2
insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values('guest_login','Guest Login');  
-- end new 1610.2

-- new 1702
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('tab_access','Tab Access');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('app_access','App Access');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values('functional_access','Functional Access');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('left_menu_access','Left Menu Access');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('search','Search');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('apa','Add Portal Admin');                            
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('dpa','Delete Portal Admin');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('uaa','Update Account Admin');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('uu','Update User');

-- new El Alto
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('eaaf','External auth add function');                            
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('eaar','External auth add role');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('eadf','External auth delete function');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('eadr','External auth delete role');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('eauf','External auth update function');
Insert into fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('eaurf','External auth update role and function');

-- FN_LU_MENU_SET
Insert into fn_lu_menu_set (MENU_SET_CD,MENU_SET_NAME) values ('APP','Application Menu');

-- FN_MENU   Ecomp Portal now uses the left menu entries from fn_menu
Insert into fn_menu(MENU_ID,LABEL,PARENT_ID,SORT_ORDER,ACTION,FUNCTION_CD,ACTIVE_YN,MENU_SET_CD,SEPARATOR_YN,IMAGE_SRC)
                               values(1,'root',NULL,10,NULL,'menu_home','N','APP','N',NULL);
                               
Insert into fn_menu(MENU_ID,LABEL,PARENT_ID,SORT_ORDER,ACTION,FUNCTION_CD,ACTIVE_YN,MENU_SET_CD,SEPARATOR_YN,IMAGE_SRC)
                               values(2,'Home',1,10,'applicationsHome','menu_home','Y','APP','N','home');

Insert into fn_menu(MENU_ID,LABEL,PARENT_ID,SORT_ORDER,ACTION,FUNCTION_CD,ACTIVE_YN,MENU_SET_CD,SEPARATOR_YN,IMAGE_SRC)
                               values(3,'Application Catalog',1,15,'appCatalog','menu_home','Y','APP','N','apps');

Insert into fn_menu(MENU_ID,LABEL,PARENT_ID,SORT_ORDER,ACTION,FUNCTION_CD,ACTIVE_YN,MENU_SET_CD,SEPARATOR_YN,IMAGE_SRC)
                               values(4,'Widget Catalog',1,20,'widgetCatalog','menu_home','Y','APP','N','apps');

Insert into fn_menu(MENU_ID,LABEL,PARENT_ID,SORT_ORDER,ACTION,FUNCTION_CD,ACTIVE_YN,MENU_SET_CD,SEPARATOR_YN,IMAGE_SRC)
                               values(5,'Admins',1,40,'admins','menu_admin','Y','APP','N','star');

Insert into fn_menu(MENU_ID,LABEL,PARENT_ID,SORT_ORDER,ACTION,FUNCTION_CD,ACTIVE_YN,MENU_SET_CD,SEPARATOR_YN,IMAGE_SRC)
                               values(6,'Roles',1,45,'roles','menu_acc_admin','Y','APP','N','person');

Insert into fn_menu(MENU_ID,LABEL,PARENT_ID,SORT_ORDER,ACTION,FUNCTION_CD,ACTIVE_YN,MENU_SET_CD,SEPARATOR_YN,IMAGE_SRC)
                               values(7,'Users',1,50,'users','menu_acc_admin','Y','APP','N','person');

Insert into fn_menu(MENU_ID,LABEL,PARENT_ID,SORT_ORDER,ACTION,FUNCTION_CD,ACTIVE_YN,MENU_SET_CD,SEPARATOR_YN,IMAGE_SRC)
                               values(8,'Portal Admins',1,60,'portalAdmins','menu_admin','Y','APP','N','settings');

Insert into fn_menu(MENU_ID,LABEL,PARENT_ID,SORT_ORDER,ACTION,FUNCTION_CD,ACTIVE_YN,MENU_SET_CD,SEPARATOR_YN,IMAGE_SRC)
                               values(9,'Application Onboarding',1,70,'applications','menu_app_onboarding','Y','APP','N','filing');

Insert into fn_menu(MENU_ID,LABEL,PARENT_ID,SORT_ORDER,ACTION,FUNCTION_CD,ACTIVE_YN,MENU_SET_CD,SEPARATOR_YN,IMAGE_SRC)
                               values(10,'Widget Onboarding',1,80,'widgetOnboarding','menu_admin','Y','APP','N','filing');

Insert into fn_menu(MENU_ID,LABEL,PARENT_ID,SORT_ORDER,ACTION,FUNCTION_CD,ACTIVE_YN,MENU_SET_CD,SEPARATOR_YN,IMAGE_SRC)
                               values(11,'Edit Functional Menu',1,90,'functionalMenu','menu_admin','Y','APP','N','menu');

Insert into fn_menu(MENU_ID,LABEL,PARENT_ID,SORT_ORDER,ACTION,FUNCTION_CD,ACTIVE_YN,MENU_SET_CD,SEPARATOR_YN,IMAGE_SRC)
                               values(12,'User Notifications',1,100,'userNotifications','edit_notification','Y','APP','N','settings');
                               
-- end new 1702

-- new 1707
Insert into fn_menu(MENU_ID,LABEL,PARENT_ID,SORT_ORDER,ACTION,FUNCTION_CD,ACTIVE_YN,MENU_SET_CD,SEPARATOR_YN,IMAGE_SRC)
								values (13,'Microservice Onboarding', 1, 110, 'microserviceOnboarding', 'menu_admin', 'Y', 'APP', 'N', 'filing');

Insert into fn_menu (menu_id, label, parent_id, sort_order, action, function_cd, active_yn, menu_set_cd, separator_yn, image_src)
								values(15,'App Account Management', 1, 130, 'accountOnboarding', 'menu_admin', 'Y', 'App', 'N', 'filing');  

-- end new 1707

-- FN_LU_ALERT_METHOD
Insert into fn_lu_alert_method (ALERT_METHOD_CD,ALERT_METHOD) values ('PHONE','Phone');
Insert into fn_lu_alert_method (ALERT_METHOD_CD,ALERT_METHOD) values ('FAX','Fax');
Insert into fn_lu_alert_method (ALERT_METHOD_CD,ALERT_METHOD) values ('PAGER','Pager');
Insert into fn_lu_alert_method (ALERT_METHOD_CD,ALERT_METHOD) values ('EMAIL','Email');
Insert into fn_lu_alert_method (ALERT_METHOD_CD,ALERT_METHOD) values ('SMS','SMS');

-- FN_LU_PRIORITY
Insert into fn_lu_priority (PRIORITY_ID,PRIORITY,ACTIVE_YN,SORT_ORDER) values (10,'Low','Y',10);
Insert into fn_lu_priority (PRIORITY_ID,PRIORITY,ACTIVE_YN,SORT_ORDER) values (20,'Normal','Y',20);
Insert into fn_lu_priority (PRIORITY_ID,PRIORITY,ACTIVE_YN,SORT_ORDER) values (30,'High','Y',30);
Insert into fn_lu_priority (PRIORITY_ID,PRIORITY,ACTIVE_YN,SORT_ORDER) values (40,'Urgent','Y',40);
Insert into fn_lu_priority (PRIORITY_ID,PRIORITY,ACTIVE_YN,SORT_ORDER) values (50,'Fatal','Y',50);

-- FN_LU_TAB_SET
Insert into fn_lu_tab_set (TAB_SET_CD,TAB_SET_NAME) values ('APP','Application Tabs');

-- FN_LU_TIMEZONE
Insert into fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (10,'US/Eastern','US/Eastern');
Insert into fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (20,'US/Central','US/Central');
Insert into fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (30,'US/Mountain','US/Mountain');
Insert into fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (40,'US/Arizona','America/Phoenix');
Insert into fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (50,'US/Pacific','US/Pacific');
Insert into fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (60,'US/Alaska','US/Alaska');
Insert into fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (70,'US/Hawaii','US/Hawaii');

-- FN_RESTRICTED_URL
Insert into fn_restricted_url (restricted_url, function_cd) values ('attachment.htm','menu_admin');
Insert into fn_restricted_url (restricted_url, function_cd) values ('broadcast.htm','menu_admin');
Insert into fn_restricted_url (restricted_url, function_cd) values ('file_upload.htm','menu_admin');
Insert into fn_restricted_url (restricted_url, function_cd) values ('job.htm','menu_admin');
Insert into fn_restricted_url (restricted_url, function_cd) values ('role.htm','menu_admin');
Insert into fn_restricted_url (restricted_url, function_cd) values ('role_function.htm','menu_admin');
Insert into fn_restricted_url (restricted_url, function_cd) values ('test.htm','menu_admin');
Insert into fn_restricted_url (restricted_url, function_cd) values ('async_test.htm','menu_home');
Insert into fn_restricted_url (restricted_url, function_cd) values ('chatWindow.htm','menu_home');
Insert into fn_restricted_url (restricted_url, function_cd) values ('contact_list.htm','menu_home');
Insert into fn_restricted_url (restricted_url, function_cd) values ('customer_dynamic_list.htm','menu_home');
Insert into fn_restricted_url (restricted_url, function_cd) values ('event.htm','menu_home');
Insert into fn_restricted_url (restricted_url, function_cd) values ('event_list.htm','menu_home');
Insert into fn_restricted_url (restricted_url, function_cd) values ('mobile_welcome.htm','menu_home');
Insert into fn_restricted_url (restricted_url, function_cd) values ('sample_map.htm','menu_home');
Insert into fn_restricted_url (restricted_url, function_cd) values ('template.jsp','menu_home');
Insert into fn_restricted_url (restricted_url, function_cd) values ('jbpm_designer.htm','menu_job_create');
Insert into fn_restricted_url (restricted_url, function_cd) values ('jbpm_drools.htm','menu_job_create');
Insert into fn_restricted_url (restricted_url, function_cd) values ('process_job.htm','menu_job_create');
Insert into fn_restricted_url (restricted_url, function_cd) values ('profile.htm','menu_profile_create');
Insert into fn_restricted_url (restricted_url, function_cd) values ('raptor.htm','menu_reports');
Insert into fn_restricted_url (restricted_url, function_cd) values ('raptor2.htm','menu_reports');
Insert into fn_restricted_url (restricted_url, function_cd) values ('raptor_blob_extract.htm','menu_reports');
Insert into fn_restricted_url (restricted_url, function_cd) values ('raptor_email_attachment.htm','menu_reports');
Insert into fn_restricted_url (restricted_url, function_cd) values ('raptor_search.htm','menu_reports');
Insert into fn_restricted_url (restricted_url, function_cd) values ('report_list.htm','menu_reports');
Insert into fn_restricted_url (restricted_url, function_cd) values ('gauge.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('gmap_controller.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('gmap_frame.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('map.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('map_download.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('map_grid_search.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('sample_animated_map.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('sample_map_2.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('sample_map_3.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('tab2_sub1.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('tab2_sub2_link1.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('tab2_sub2_link2.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('tab2_sub3.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('tab3.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('tab4.htm','menu_tab');
Insert into fn_restricted_url (restricted_url, function_cd) values ('raptor.htm','view_reports');
Insert into fn_restricted_url (restricted_url, function_cd) values ('raptor_blob_extract.htm','view_reports');

-- FN_ROLE
Insert into fn_role (ROLE_ID, ROLE_NAME, ACTIVE_YN, PRIORITY, APP_ID, APP_ROLE_ID) values (1,'System Administrator','Y',1,NULL,NULL);
Insert into fn_role (ROLE_ID, ROLE_NAME, ACTIVE_YN, PRIORITY, APP_ID, APP_ROLE_ID) values (16,'Standard User','Y',5,NULL,NULL);
Insert into fn_role (ROLE_ID, ROLE_NAME, ACTIVE_YN, PRIORITY, APP_ID, APP_ROLE_ID) values (999,'Account Administrator','Y',1,NULL,NULL);
Insert into fn_role (ROLE_ID, ROLE_NAME, ACTIVE_YN, PRIORITY, APP_ID, APP_ROLE_ID) values (900,'Restricted App Role','Y','1',NULL,NULL);

-- new 1702
Insert into fn_role (ROLE_ID, ROLE_NAME, ACTIVE_YN, PRIORITY, APP_ID, APP_ROLE_ID) values (950,'Portal Notification Admin','Y','1',NULL,NULL);
-- end new 1702

-- new 1707
INSERT INTO fn_role (role_id, role_name, active_yn, priority) VALUES ('1010', 'Usage Analyst', 'Y', '1');
INSERT INTO fn_role (role_id, role_name, active_yn, priority) VALUES ('2115', 'Portal Usage Analyst', 'Y', '6');
-- end new 1707

-- FN_ROLE_Composite
Insert into fn_role_composite (PARENT_ROLE_ID,CHILD_ROLE_ID) values (1,16);

-- FN_ROLE_FUNCTION
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'login');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_admin');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_ajax');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_customer');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_customer_create');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_feedback');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_help');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_home');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_job');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_job_create');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_logout');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_notes');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_process');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_profile');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_profile_create');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_profile_import');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_reports');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_sample');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_tab');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'login');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_ajax');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_customer');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_customer_create');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_home');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_logout');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_map');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_profile');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_reports');
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_tab');

-- new 1702
Insert into fn_role_function (ROLE_ID,FUNCTION_CD) values (950,'edit_notification');
Insert INTO fn_role_function (ROLE_ID,FUNCTION_CD) values (950, 'getAdminNotifications');
Insert INTO fn_role_function (ROLE_ID,FUNCTION_CD) values (950, 'saveNotification');
-- end new 1702

-- new 1707
INSERT INTO fn_role_function (role_id, function_cd) VALUES ('1010', 'menu_web_analytics');
INSERT INTO fn_role_function (role_id, function_cd) VALUES ('2115', 'menu_web_analytics');
-- end new 1707

-- new 3_2
insert into fn_role_function (role_id, function_cd) values (16, 'menu_acc_admin');
insert into fn_role_function (role_id, function_cd) values (16, 'menu_app_onboarding');
-- end new 3_2

-- FN_TAB
Insert into fn_tab (TAB_CD,TAB_NAME,TAB_DESCR,ACTION,FUNCTION_CD,ACTIVE_YN,SORT_ORDER,PARENT_TAB_CD,TAB_SET_CD) values ('TAB2_SUB1_S1','Left Tab 1','Sub - Sub Tab 1 Information','tab2_sub1.htm','menu_tab','Y',10,'TAB2_SUB1','APP');
Insert into fn_tab (TAB_CD,TAB_NAME,TAB_DESCR,ACTION,FUNCTION_CD,ACTIVE_YN,SORT_ORDER,PARENT_TAB_CD,TAB_SET_CD) values ('TAB1','Tab 1','Tab 1 Information','tab1.htm','menu_tab','Y',10,null,'APP');
Insert into fn_tab (TAB_CD,TAB_NAME,TAB_DESCR,ACTION,FUNCTION_CD,ACTIVE_YN,SORT_ORDER,PARENT_TAB_CD,TAB_SET_CD) values ('TAB2','Tab 2','Tab 2 Information','tab2_sub1.htm','menu_tab','Y',20,null,'APP');
Insert into fn_tab (TAB_CD,TAB_NAME,TAB_DESCR,ACTION,FUNCTION_CD,ACTIVE_YN,SORT_ORDER,PARENT_TAB_CD,TAB_SET_CD) values ('TAB3','Tab 3','Tab 3 Information','tab3.htm','menu_tab','Y',30,null,'APP');
Insert into fn_tab (TAB_CD,TAB_NAME,TAB_DESCR,ACTION,FUNCTION_CD,ACTIVE_YN,SORT_ORDER,PARENT_TAB_CD,TAB_SET_CD) values ('TAB4','Tab 4','Tab 4 Information','tab4.htm','menu_tab','Y',40,null,'APP');
Insert into fn_tab (TAB_CD,TAB_NAME,TAB_DESCR,ACTION,FUNCTION_CD,ACTIVE_YN,SORT_ORDER,PARENT_TAB_CD,TAB_SET_CD) values ('TAB2_SUB1','Sub Tab 1','Sub Tab 1 Information','tab2_sub1.htm','menu_tab','Y',10,'TAB2','APP');
Insert into fn_tab (TAB_CD,TAB_NAME,TAB_DESCR,ACTION,FUNCTION_CD,ACTIVE_YN,SORT_ORDER,PARENT_TAB_CD,TAB_SET_CD) values ('TAB2_SUB2','Sub Tab 2','Sub Tab 2 Information','tab2_sub2.htm','menu_tab','Y',20,'TAB2','APP');
Insert into fn_tab (TAB_CD,TAB_NAME,TAB_DESCR,ACTION,FUNCTION_CD,ACTIVE_YN,SORT_ORDER,PARENT_TAB_CD,TAB_SET_CD) values ('TAB2_SUB3','Sub Tab 3','Sub Tab 3 Information','tab2_sub3.htm','menu_tab','Y',30,'TAB2','APP');

-- FN_TAB_SELECTED
Insert into fn_tab_selected (SELECTED_TAB_CD,TAB_URI) values ('TAB1','tab1');
Insert into fn_tab_selected (SELECTED_TAB_CD,TAB_URI) values ('TAB2','tab2_sub1');
Insert into fn_tab_selected (SELECTED_TAB_CD,TAB_URI) values ('TAB2','tab2_sub2');
Insert into fn_tab_selected (SELECTED_TAB_CD,TAB_URI) values ('TAB2','tab2_sub3');
Insert into fn_tab_selected (SELECTED_TAB_CD,TAB_URI) values ('TAB2_SUB1','tab2_sub1');
Insert into fn_tab_selected (SELECTED_TAB_CD,TAB_URI) values ('TAB2_SUB1_S1','tab2_sub1');
Insert into fn_tab_selected (SELECTED_TAB_CD,TAB_URI) values ('TAB2_SUB2','tab2_sub2');
Insert into fn_tab_selected (SELECTED_TAB_CD,TAB_URI) values ('TAB2_SUB3','tab2_sub3');
Insert into fn_tab_selected (SELECTED_TAB_CD,TAB_URI) values ('TAB3','tab3');
Insert into fn_tab_selected (SELECTED_TAB_CD,TAB_URI) values ('TAB4','tab4');

UPDATE
   fn_menu 
SET 
   ACTION = 'webAnalytics',
   image_src= 'pie'
WHERE
   active_yn = 'Y' AND label = 'Web Analytics';
   
   
UPDATE
   fn_menu 
SET 
   ACTION = 'webAnlayticsSource',
   image_src= 'pie'
WHERE
   active_yn = 'Y' AND label = 'Web Analytics Onboarding';
   

UPDATE
   fn_menu 
SET 
   ACTION = 'applicationsHome', 
   image_src = 'home'
WHERE
   active_yn = 'Y' AND label = 'Home';
   
   
UPDATE
   fn_menu 
SET 
   ACTION = 'appCatalog',
   image_src= 'apps'
WHERE
   active_yn = 'Y' AND label = 'Application Catalog';
   

UPDATE
   fn_menu 
SET 
   ACTION = 'widgetCatalog',
   image_src= 'apps'
WHERE
   active_yn = 'Y' AND label = 'Widget Catalog';
   
UPDATE
   fn_menu 
SET 
   ACTION = 'admins' ,
   image_src= 'star'
WHERE
   active_yn = 'Y' AND label = 'Admins';
   
UPDATE
   fn_menu 
SET 
   ACTION = 'roles' ,
   image_src= 'person'
WHERE
   active_yn = 'Y' AND label = 'Roles';
   
   
UPDATE
   fn_menu 
SET 
   ACTION = 'users',
   image_src= 'person'
WHERE
   active_yn = 'Y' AND label = 'Users';
   
   
UPDATE
   fn_menu 
SET 
   ACTION = 'portalAdmins',
   image_src= 'settings'
WHERE
   active_yn = 'Y' AND label = 'Portal Admins';
   
   
UPDATE
   fn_menu 
SET 
   ACTION = 'applications',
   image_src= 'filing'
WHERE
   active_yn = 'Y' AND label = 'Application Onboarding';
   
UPDATE
   fn_menu 
SET 
   ACTION = 'widgetOnboarding',
   image_src= 'filing'
WHERE
   active_yn = 'Y' AND label = 'Widget Onboarding';
   
UPDATE
   fn_menu 
SET 
   ACTION = 'functionalMenu',
   image_src= 'menu'
WHERE
   active_yn = 'Y' AND label = 'Edit Functional Menu';
 
    
UPDATE
   fn_menu 
SET 
   ACTION = 'userNotifications',
   image_src= 'settings'
WHERE
   active_yn = 'Y' AND label = 'User Notifications';
    
    
UPDATE
   fn_menu 
SET 
   ACTION = 'microserviceOnboarding',
   image_src= 'filing'
WHERE
   active_yn = 'Y' AND label = 'Microservice Onboarding';

    
    
UPDATE
   fn_menu 
SET 
   ACTION = 'webAnalytics',
   image_src= 'pie'
WHERE
   active_yn = 'Y' AND label = 'Web Analytics';
   
   
UPDATE
   fn_menu 
SET 
   ACTION = 'webAnlayticsSource',
   image_src= 'pie'
WHERE
   active_yn = 'Y' AND label = 'Web Analytics Onboarding';
   
UPDATE
   fn_menu 
SET 
   ACTION = 'accountOnboarding',
   image_src= 'filing'
WHERE
   active_yn = 'Y' AND label = 'App Account Management';


   
commit;
