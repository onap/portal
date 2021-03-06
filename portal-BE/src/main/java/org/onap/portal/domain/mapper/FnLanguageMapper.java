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

package org.onap.portal.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.onap.portal.domain.db.fn.FnLanguage;
import org.onap.portal.domain.dto.fn.FnLanguageDto;
import org.springframework.stereotype.Component;

@Component
public class FnLanguageMapper {

       public FnLanguageDto fnLanguageToDto(final FnLanguage fnLanguage){
              FnLanguageDto dto = new FnLanguageDto();
              dto.setLanguageId(fnLanguage.getLanguageId());
              dto.setLanguageName(fnLanguage.getLanguageName());
              dto.setLanguageAlias(fnLanguage.getLanguageAlias());
              return dto;
       }

       public List<FnLanguageDto> fnLanguageListToDtoList(final List<FnLanguage> fnLanguages){
              return fnLanguages.stream().map(this::fnLanguageToDto).collect(
                      Collectors.toList());
       }
}
