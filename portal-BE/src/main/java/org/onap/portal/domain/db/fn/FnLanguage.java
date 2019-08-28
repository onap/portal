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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@JsonInclude()
@SequenceGenerator(name="seq", initialValue=3, allocationSize=100)
public class FnLanguage {

       @Id
       @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
       @Column(name = "language_id", length = 11, nullable = false, columnDefinition = "int(11) AUTO_INCREMENT")
       @Digits(integer = 11, fraction = 0)
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

}
