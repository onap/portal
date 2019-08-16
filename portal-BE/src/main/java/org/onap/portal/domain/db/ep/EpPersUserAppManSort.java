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

package org.onap.portal.domain.db.ep;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.onap.portalapp.portal.domain.db.fn.FnApp;
import org.onap.portalapp.portal.domain.db.fn.FnUser;

/*
CREATE TABLE `ep_pers_user_app_man_sort` (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `user_id` int(11) NOT NULL,
        `app_id` int(11) NOT NULL,
        `sort_order` int(11) NOT NULL,
        PRIMARY KEY (`id`),
        UNIQUE KEY `uk_1_ep_pers_user_app_man_sort` (`user_id`,`app_id`),
        KEY `fk_ep_pers_app_man_sort_fn_app` (`app_id`),
        CONSTRAINT `fk_ep_pers_app_man_sort_fn_app` FOREIGN KEY (`app_id`) REFERENCES `fn_app` (`app_id`),
        CONSTRAINT `fk_ep_pers_app_man_sort_fn_user` FOREIGN KEY (`user_id`) REFERENCES `fn_user` (`user_id`)
        )
*/

@NamedQueries({
        @NamedQuery(
                name = "EpPersUserAppManSort.retrieveByUserId",
                query = "from EpPersUserAppManSort where user_id =:userId"
        )
})

@Table(name = "ep_pers_user_app_man_sort", indexes ={
        @Index(name = "uk_1_ep_pers_user_app_man_sort", columnList = "user_id, app_id", unique = true),
        @Index(name = "fk_ep_pers_app_man_sort_fn_app", columnList = "app_id")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class EpPersUserAppManSort {
       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       @Column(name = "id", length = 11, nullable = false, columnDefinition = "int(11) AUTO_INCREMENT")
       @Digits(integer = 11, fraction = 0)
       private Long id;
       @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
       @JoinColumn(name = "user_id", nullable = false)
       @NotNull
       @Valid
       private FnUser userId;
       @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
       @JoinColumn(name = "app_id", nullable = false)
       @NotNull
       @Valid
       private FnApp appId;
       @Column(name = "sort_order", length = 11, nullable = false)
       @Digits(integer = 11, fraction = 0)
       @NotNull
       private Long sortOrder;
}
