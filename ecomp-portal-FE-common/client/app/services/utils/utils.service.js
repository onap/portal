/*-
 * ================================================================================
 * eCOMP Portal
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ================================================================================
 */
'use strict';

(function () {
    class UtilsService {
        constructor($log) {
            this.$log = $log;
        }

        isRunningInLocalDevEnv() {
            var myHostName;
            myHostName = location.host;

            if (myHostName.includes('localhost')) {
                return true;
            } else {
                return false;
            }
        }

        isValidJSON(json) {
            try {
                var checkJSON = JSON.parse(JSON.stringify(json));
                if (checkJSON && typeof checkJSON === 'object' && checkJSON !== null) {
                    // this.$log.debug('UtilsService::isValidJSON: JSON is valid!');
                    return true;
                }
            } catch (err) {
                this.$log.debug('UtilsService::isValidJSON: json passed is not valid: ' + JSON.stringify(err));
            }
            return false;
        }
        
        showLoadingLayer() {
        	$("#loadLayer").css('display','inline');
        }
        
        hideLoadingLayer() {
        	$("#loadLayer").css('display','none');
        }

    }
    UtilsService.$inject = ['$log'];
    angular.module('ecompApp').service('utilsService', UtilsService)
})();
