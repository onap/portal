# ECOMP Portal Web Application Back End for Open Source

## Overview

This is a Maven project with the ECOMP Portal web application back-end files
for public release, containing Java files specific to requirements of the
open-source version.  This project uses the Maven war plugin to copy in
("overlay") the contents of the ECOMP Portal web application back-end
common distribution at package time.

Use Apache Maven to build, package and deploy this webapp to a web container
like Apache Tomcat.  Eclipse users must install the M2E-WTP connector, see 
https://www.eclipse.org/m2e-wtp/

## Release Notes

version 2.5
- [PORTAL-319] Integrate with CADI AAF

Version 2.2
- [PORTAL-255] Scheduler popup shows up automatically when users land on home page
- [PORTAL-210] Music jar update
- [PORTAL-179] wrong menu available to App Admin User
- [PORTAL-207] Raise 4 % percentage (from 44 to 48) sonar code coverage for ONAP PORTAL
- [PORTAL-136] Raise JUnit test coverage ONAP Portal to 50% for Beijing 
- [PORTAL-133] replace ECOMP to ONAP on Contact Us and Get Access page
- [PORTAL-145] Harden code to address SQL injection, XSS vulnerabilities
- [PORTAL-156] Left menu are showing web analytics items and missing plus icons on Account Details page
- [Portal-148] ONAP Portal Beijing does not accept logins; currently OIDC code is disabled; please check the web.xml
  Long term solution is being worked on.
- [Portal-140] Portal role synch error with partner apps
- [Portal-111] To get Centralized apps on Roles and Role Functions page
   Create, Edit and Delete calls for Roles and Role Functions
- [Portal-121] GUI controls overlapping on app onboarding dialog
- [Portal-133] Replace ECOMP wording from contact us page with ONAP
- [Portal-88] unable to deselect widget on Widget catalog page
- [Portal-111] Centralized Role Management Task(Integration to AAF)
- [PORTAL-157] OpenID connect conflicting with spring security
- [Portal-174] missing DB statement in DML script(Users, Roles Page fix)
- [Portal-162] CVE-2015-5211 - Spring - File Upload issue. Upgrade to 4.2.2
- [Portal-163] NVD - CVE-2016-1000341: bouncycastle issue. Upgrade to 2.4.4
- [Portal-168] CVE-2015-0254 JSTL-Upgrade to 1.2.3+
- [Portal-155] Review security issues: portal
- [Portal-19] Removed the ecomp keyword from the license text
- [Portal-256] Fix to make the userRole dynamic on Users modal popup
- [Portal-241] To retrieve roles on Users page for sdk(external app)
- [Portal-220] Fix to delete a portal admin, removed the OS test case
- [Portal-248] Id value fix in in Add App Account Management modal
- [Portal-211] High memory Usage by Portal, SDK, Cassandra
- [Portal-202] Remove files that have GPL or other license issues - b2b-library.min.js
- [Portal-432]Push userroles even if the app is centralized(ASDC)

Version 1.1.0 (Amsterdam), November 2017
- [Portal-6] Updates to License and Trademark in the PORTAL Source Code
- [Portal-7] Improvements added as part of the rebasing process
- [Portal-17] Remove jfree related items
- [PORTAL-21] FE changes to OS for AAF centralization and name space field; DB script updates for EcompPortalDDLMySql_1710_Common.sql under ecomp-portal-DB-common, EcompPortalDMLMySql_1710_OS.sql under ecomp-portal-DB-os;
- [Portal-30] Failed to communicate with the widget microservice: Fixed
- [Portal-35] Replaced the portal logo with onap logo on the login screen.
- [Portal-40] Fix to add user roles 
- [Portal-45] Fix to update an existing app on Application Onboarding
- [Portal-47] Fix to eliminate duplicate roles on Users page
- [Portal-48] Fix to save a new app on Application onboarding
- [Portal-49] image icon is missing on Widget corner
- [Portal-63] remove att_abs_tpls*.js and greensock url
- [Portal-69] unable to pick role in Functional Menu Update
- [Portal-73] unable to onboard new Application fix
- [Portal-50] Enabled the junit coverage in ONAP
- [Portal-76] Edit functional menu modal doesn't show
- [Portal-61] Fixed the routing problem, loaded data and changed the notification hyperlink 
- [Portal-77] Changes to remove preview image and update the new image automatically on App onbarding page
- [Portal-82] Unable to pick Role in Widget onboarding for assigned app
- [Portal-94] Unable to see the updates on Edit Functional Menu
- [Portal-104] Replaced mysql DB connector with mariaDB
- [Portal-72] Sonar scan - resolving severity Blocker issues 
- [Portal-103] Fix to enhanced notification ticket source for ONAP
- [Portal-50] Fix to get the Sonar coverage for ONAP
- [Portal-86] Remove internal att.com usages from tests and other files (rework)
- [Portal-102] Fixed the page refresh issue on App Account Management page
- [Portal-104] replace mysql DB connector with mariaDB connector
- [Portal-116] Empty Widgets on Home page - widget onboarded with a wrong format in database
- [Portal-59] Fix to to display Portal users on User page
- [Portal-125] Fixed the missing role assignment when adding a user in portal

Version 1.0.0, February 2017
- Initial release
