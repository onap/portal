'use strict';
(function () {
    class AccountAddDetailsCtrl {
        constructor($scope, $log, $interval, basicAuthAccountService, errorMessageByCode, ECOMP_URL_REGEX, $window, confirmBoxService, $cookies,items) {
         
           this.addEndpoint = () => {
           	  this.account.endpointList.push({
           		  valid: true
           	  }); 
           }
            let init = () => {
            	this.account = [];
                this.account.endpointList = [];
                this.passwordMatched = true;
                this.dupliateName = false;
                this.emptyAccountName = false;
                this.emptyAccountUsername = false;
                this.accountList = items.list;
                
                if (items&& items.account) {
                    this.isEditMode = true;
                    this.account = _.clone(items.account);
                    this.account.repassword = this.account.password;
                    this.account.endpointList = this.account.endpoints;
                    if(this.account.isActive == 'Y')
                    	this.account.active = true;
                    else
                    	this.account.active = false;
                } else {
                    this.isEditMode = false;
                    this.account.active = true;
                } 
                console.log(this.account);
            };
            
            let resetConflict = fieldName => {
                delete this.conflictMessages[fieldName];
                if($scope.widgetForm[fieldName]){
                    $scope.widgetForm[fieldName].$setValidity('conflict', true);
                }
            };

            
          /*  this.closeThisDialog = () => {
            	$scope.closeThisDialog(true);
            }*/
            
            this.removeEndpointItem = (endpoint) => {
        		for(var i = 0; i < this.account.endpointList.length; i++){
        			if(this.account.endpointList[i].name == endpoint.name){
        				this.account.endpointList.splice(i, 1);
        				return; 
        			}
        		}
            }
            
            this.updateUsername = () => {
            	this.emptyAccountUsername = false;
            }
            
            this.updateAccountName = () => {
            	this.dupliateName = false;
            	for(var i = 0; i < this.accountList.length; i++){
            		if(this.accountList[i].applicationName == this.account.applicationName){
            			this.dupliateName = true;
            			return;
            		}
            	}
            }
            
            this.updateAccountEndpoint = (endpoint) => {
            	endpoint.valid = true;
            }
           
            this.saveChanges = () => {

            	var isValid = true;
            	var r = /\/[^ "]+$/;
            	
            	for(var i = 0; i < this.account.endpointList.length; i++){
               		if(this.account.endpointList[i].name == undefined
            		|| this.account.endpointList[i].name == null
            		|| this.account.endpointList[i].name == ""){
            			this.account.endpointList.splice(i, 1);
            			i--;
            		}else{
            			if(!this.account.endpointList[i].name.startsWith("/")){
            				this.account.endpointList[i].name = "/" + this.account.endpointList[i].name;
            			}
            			if(!r.test(this.account.endpointList[i].name)){
            				this.account.endpointList[i].valid = false;
            				isValid = false;
            			}
            			
            		}
            	}
            	
            	if(this.account.applicationName == ''
                || this.account.applicationName == undefined){
            		this.emptyAccountName = true;
                	isValid = false;
                }
            	
            	if(this.account.username == ''
                || this.account.username == undefined){
            		this.emptyAccountUsername = true;
                    isValid = false;
                }
            	
            	if(this.dupliateName == true){
                   isValid = false;
               	}
            	
            	if(this.account.password != this.account.repassword){
            		this.passwordMatched =  false;
            		isValid = false;
            	}
            	
            	if(!isValid)
            		return;
            	
               	
            	
            	var active = 'N';
            	if(this.account.active == true)
            		active = 'Y';
            	
            	var newAccount = {
            			applicationName: this.account.applicationName,
            			username: this.account.username,
            			password: this.account.password,
            			endpoints: this.account.endpointList,
            			isActive: active
            	};
            	
            	
            	if(this.isEditMode){
            		var message = "Are you sure you want to change '" + this.account.applicationName + "'?"
            		confirmBoxService.editItem(message).then(isConfirmed => {
	            		if(isConfirmed){
	            			basicAuthAccountService.updateAccount(this.account.id, newAccount).then(() => {
                                $scope.$dismiss('cancel');
                                $window.location.reload();

	                		});
	           			}
            		});
            	}else{
            		basicAuthAccountService.createAccount(newAccount).then(() => {
                        $scope.$dismiss('cancel');
                        $window.location.reload();

            		});
            	}
            }
            
            init();
            $scope.$on('$stateChangeStart', e => {
                e.preventDefault();
            });
        }
    }
    AccountAddDetailsCtrl.$inject = ['$scope', '$log', '$interval', 'basicAuthAccountService', 'errorMessageByCode', 'ECOMP_URL_REGEX', '$window', 'confirmBoxService', '$cookies','items'];
    angular.module('ecompApp').controller('AccountAddDetailsCtrl', AccountAddDetailsCtrl);
})(); 