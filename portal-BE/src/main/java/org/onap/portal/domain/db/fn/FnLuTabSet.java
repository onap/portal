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

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.SafeHtml;

/*
CREATE TABLE `fn_lu_tab_set` (
        `tab_set_cd` varchar(30) NOT NULL,
        `tab_set_name` varchar(50) NOT NULL,
        PRIMARY KEY (`tab_set_cd`)
        )
*/

@Table(name = "fn_lu_tab_set")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class FnLuTabSet implements Serializable {
       @Id
       @Column(name = "tab_set_cd", length = 30, nullable = false)
       @Size(max = 30)
       @SafeHtml
       private String tabSetCd;
       @Column(name = "tab_set_name", length = 50, nullable = false)
       @Size(max = 50)
       @SafeHtml
       @NotNull
       private String tabSetName;
       @OneToMany(
               targetEntity = FnTab.class,
               mappedBy = "fnLuTabSet",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY
       )
       private Set<FnTab> fnTabs;
}
