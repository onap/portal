/*
 * ============LICENSE_START==========================================
 * ONAP Portal
 * ===================================================================
 * Copyright (C) 2019 AT&T Intellectual Property. All rights reserved.
 * ===================================================================
 * Modifications Copyright (c) 2019 Samsung
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

package org.onap.portal.controller;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.portal.dao.fn.FnLanguageDao;
import org.onap.portal.dao.fn.FnUserDao;
import org.onap.portal.domain.db.fn.FnLanguage;
import org.onap.portal.domain.db.fn.FnUser;
import org.onap.portal.domain.db.fn.FnWidget;
import org.onap.portal.domain.dto.transport.FieldsValidator;
import org.onap.portal.domain.dto.transport.OnboardingWidget;
import org.onap.portal.framework.MockitoTestSuite;
import org.onap.portal.service.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
public class WidgetsControllerTest {

       private UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken("demo",
               "demo123");

       MockitoTestSuite mockitoTestSuite = new MockitoTestSuite();

       HttpServletRequest request = mockitoTestSuite.getMockedRequest();
       HttpServletResponse response = mockitoTestSuite.getMockedResponse();

       @Autowired
       private WidgetsController widgetsController;
       @Autowired
       private FnUserDao fnUserDao;
       @Autowired
       private FnLanguageDao fnLanguageDao;
       @Autowired
       private WidgetService widgetService;

       private FnLanguage language = getFnLanguage();
       private FnUser questUser = getQuestUser();
       private FnUser notQuestUser = getNotQuestUser();

       @Test(expected = UsernameNotFoundException.class)
       public void getOnboardingWidgetsNullUserTest() {
              UsernamePasswordAuthenticationToken nullPrincipal = new UsernamePasswordAuthenticationToken("nulluser",
                      "demo123");
              widgetsController.getOnboardingWidgets(nullPrincipal, request, response);
       }

       @Test
       public void getOnboardingWidgetsQuestUserTest() {
              UsernamePasswordAuthenticationToken questPrincipal = new UsernamePasswordAuthenticationToken("questUser",
                      "demo123");
              fnUserDao.save(questUser);
              List<OnboardingWidget> onboardingWidgets = widgetsController
                      .getOnboardingWidgets(questPrincipal, request, response);
              assertNull(onboardingWidgets);

              //Clean up
              fnUserDao.delete(questUser);
              fnLanguageDao.delete(language);
       }

       @Test
       public void getOnboardingWidgetsUserTest() {
              UsernamePasswordAuthenticationToken notQuestprincipal = new UsernamePasswordAuthenticationToken("notQuestUser",
                      "demo123");
              fnUserDao.save(notQuestUser);
              List<OnboardingWidget> expected = new ArrayList<>();
              when(request.getHeader("X-Widgets-Type")).thenReturn("managed");

              List<OnboardingWidget> actual = widgetsController
                      .getOnboardingWidgets(notQuestprincipal, request, response);

              assertEquals(expected, actual);
              fnUserDao.delete(notQuestUser);
       }

       @Test
       public void getOnboardingWidgetsWrongHeaderTest() {
              UsernamePasswordAuthenticationToken notQuestprincipal = new UsernamePasswordAuthenticationToken("notQuestUser",
                      "demo123");
              fnUserDao.save(notQuestUser);
              when(request.getHeader("X-Widgets-Type")).thenReturn("test");
              List<OnboardingWidget> actual = widgetsController
                      .getOnboardingWidgets(notQuestprincipal, request, response);

              assertNull(actual);
              fnUserDao.delete(notQuestUser);
       }

       @Test
       public void putOnboardingWidgetSameWidget() {
              //Given
              UsernamePasswordAuthenticationToken notQuestprincipal = new UsernamePasswordAuthenticationToken("cs0008",
                      "demo123");
              fnUserDao.save(notQuestUser);
              when(request.getHeader("X-Widgets-Type")).thenReturn("managed");

              OnboardingWidget onboardingWidget = OnboardingWidget.builder()
                      .id(123L)
                      .name("Application")
                      .appId(1421L)
                      .appName("Application name")
                      .width(123)
                      .height(45)
                      .url("testurl")
                      .build();


              FnWidget fnWidget = FnWidget.builder()
                      .name("Application")
                      .appId(453L)
                      .width(123)
                      .height(45)
                      .url("testurl")
                      .build();

              widgetService.saveOne(fnWidget);

              FieldsValidator expected = new FieldsValidator();
              //When
              FieldsValidator actual = widgetsController.putOnboardingWidget(principal, fnWidget.getWidgetId(), onboardingWidget, response);
              //Then
              assertEquals(expected.getErrorCode(), actual.getErrorCode());
              assertEquals(expected.getHttpStatusCode(), actual.getHttpStatusCode());
              assertEquals(expected.getFields(), actual.getFields());
       }

       @Test
       public void putOnboardingWidgetAOP() {
              //Given
              UsernamePasswordAuthenticationToken notQuestprincipal = new UsernamePasswordAuthenticationToken("cs0008",
                      "demo123");
              fnUserDao.save(notQuestUser);
              when(request.getHeader("X-Widgets-Type")).thenReturn("managed");

              OnboardingWidget onboardingWidget = OnboardingWidget.builder()
                      .id(123L)
                      .name("")
                      .appId(1L)
                      .appName("")
                      .width(123)
                      .height(45)
                      .url("testurl")
                      .build();


              FnWidget fnWidget = FnWidget.builder()
                      .name("Application")
                      .appId(1421L)
                      .width(123)
                      .height(45)
                      .url("testurl")
                      .build();

              widgetService.saveOne(fnWidget);

              FieldsValidator expected = new FieldsValidator();
              expected.setHttpStatusCode(406L);
              expected.addProblematicFieldName("appName can't be blank, appId value must be higher than 1");
              //When
              FieldsValidator actual = widgetsController.putOnboardingWidget(principal, fnWidget.getWidgetId(), onboardingWidget, response);
              //Then
              assertEquals(expected.getHttpStatusCode(), actual.getHttpStatusCode());
              assertEquals(expected.getFields().size(), actual.getFields().size());
       }

       @Test
       public void putOnboardingWidgetAOPXSSTest() {
              //Given
              UsernamePasswordAuthenticationToken notQuestprincipal = new UsernamePasswordAuthenticationToken("cs0008",
                      "demo123");
              fnUserDao.save(notQuestUser);
              when(request.getHeader("X-Widgets-Type")).thenReturn("managed");

              OnboardingWidget onboardingWidget = OnboardingWidget.builder()
                      .id(123L)
                      .name("<script>alert(“XSS”);</script>\n")
                      .appId(34L)
                      .appName("<ScRipT>alert(\"XSS\");</ScRipT>")
                      .width(123)
                      .height(45)
                      .url("testurl")
                      .build();

              FieldsValidator expected = new FieldsValidator();
              expected.setHttpStatusCode(406L);
              expected.addProblematicFieldName("appName may have unsafe html content, name may have unsafe html content");
              //When
              FieldsValidator actual = widgetsController.putOnboardingWidget(principal, 15L, onboardingWidget, response);
              //Then
              assertEquals(expected.getHttpStatusCode(), actual.getHttpStatusCode());
              assertEquals(expected.getFields().size(), actual.getFields().size());
       }

       @Test
       public void postOnboardingWidget() {
       }

       @Test
       public void deleteOnboardingWidget() {
       }

       @Test
       public void putWidgetCatalogSelection() {
       }

       private FnUser getQuestUser(){
              return FnUser.builder()
                      .loginId("questUser")
                      .loginPwd("demo123")
                      .lastLoginDate(LocalDateTime.now())
                      .activeYn(true)
                      .createdDate(LocalDateTime.now())
                      .modifiedDate(LocalDateTime.now())
                      .isInternalYn(true)
                      .languageId(language)
                      .guest(true)
                      .build();
       }

       private FnUser getNotQuestUser(){
              return FnUser.builder()
                      .loginId("notQuestUser")
                      .loginPwd("demo123")
                      .lastLoginDate(LocalDateTime.now())
                      .activeYn(true)
                      .createdDate(LocalDateTime.now())
                      .modifiedDate(LocalDateTime.now())
                      .isInternalYn(true)
                      .languageId(language)
                      .guest(false)
                      .build();
       }

       private FnLanguage getFnLanguage(){
              return FnLanguage.builder().languageName("Polish").languageAlias("Pl").build();
       }
}