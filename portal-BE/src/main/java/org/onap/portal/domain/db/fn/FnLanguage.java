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

package org.onap.portal.domain.db.fn;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.SafeHtml;
/*
CREATE TABLE `fn_language` (
        `language_id` int(11) NOT NULL AUTO_INCREMENT,
        `language_name` varchar(100) NOT NULL,
        `language_alias` varchar(100) NOT NULL,
        PRIMARY KEY (`language_id`)
        )
*/

@Table(name = "fn_language")

@NamedQuery(name = "FnLanguage.getByLanguageAlias",
query = "FROM FnLanguage WHERE languageAlias =: alias")

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@JsonInclude()
public class FnLanguage implements Serializable {

       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       @Column(name = "language_id", length = 11, nullable = false)
       private Long languageId;
       @Column(name = "language_name", length = 100, nullable = false)
       @Size(max = 100)
       @NotNull(message = "languageName must not be null")
       @SafeHtml
       private String languageName;
       @Column(name = "language_alias", length = 100, nullable = false)
       @Size(max = 100)
       @NotNull(message = "languageAlias must not be null")
       @SafeHtml
       private String languageAlias;
       @OneToMany(
               targetEntity = FnUser.class,
               mappedBy = "languageId",
               cascade = CascadeType.MERGE,
               fetch = FetchType.EAGER
       )
       private Set<FnUser> fnUsers = new HashSet<>();

       @Override
       public String toString() {
              String sb = "FnLanguage{" + "languageId=" + languageId
                      + ", languageName='" + languageName + '\''
                      + ", languageAlias='" + languageAlias + '\''
                      + '}';
              return sb;
       }

       public FnLanguage(
           @Size(max = 100) @NotNull(message = "languageName must not be null") @SafeHtml String languageName,
           @Size(max = 100) @NotNull(message = "languageAlias must not be null") @SafeHtml String languageAlias) {
              this.languageName = languageName;
              this.languageAlias = languageAlias;
       }
}
