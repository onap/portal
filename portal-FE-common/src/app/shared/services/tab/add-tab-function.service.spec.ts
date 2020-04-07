/*-
 * ============LICENSE_START==========================================
 * ONAP Portal
 * ===================================================================
 * Copyright (C) 2019 AT&T Intellectual Property. All rights reserved.
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
import { TestBed } from '@angular/core/testing';

import { AddTabFunctionService } from './add-tab-function.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AddTabFunctionService', () => {
  beforeEach(() => TestBed.configureTestingModule({imports:[HttpClientTestingModule]}));

  it('should be created', () => {
    const service: AddTabFunctionService = TestBed.get(AddTabFunctionService);
    expect(service).toBeTruthy();
  });

  it('filter should return stubbed value', () => {
    const service: AddTabFunctionService = TestBed.get(AddTabFunctionService);
    spyOn(service, 'filter').and.callThrough();
    service.filter("TEST");
    expect(service.filter).toHaveBeenCalledWith("TEST")  
  });
});