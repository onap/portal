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

package org.onap.portal.domain.dto.transport;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.SafeHtml;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FunctionalMenuItem implements Serializable {

       private static final long serialVersionUID = 1L;

       @Column(name = "MENU_ID")
       @Digits(integer = 11, fraction = 0)
       private Long menuId;
       @Digits(integer = 2, fraction = 0)
       @NotNull
       private Integer column;
       @Max(value = 100)
       @SafeHtml
       @NotNull
       private String text;
       @Digits(integer = 11, fraction = 0)
       private Integer parentMenuId;
       @Max(value = 128)
       @SafeHtml
       @NotNull
       private String url;
       @Max(value = 1)
       @SafeHtml
       @NotNull
       private String active_yn;
       private Integer appid;
       private List<Integer> roles;
       private Boolean restrictedApp;

       public void normalize() {
              if (this.column == null) {
                     this.column = 1;
              }
              this.text = (this.text == null) ? "" : this.text.trim();
              if (this.parentMenuId == null) {
                     this.parentMenuId = -1;
              }
              this.url = (this.url == null) ? "" : this.url.trim();
       }
}
