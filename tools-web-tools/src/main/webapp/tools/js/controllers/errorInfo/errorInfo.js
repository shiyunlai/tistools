/**
 * Created by wangbo on 2017/11/20.
 */

angular.module('MetronicApp').controller('errorInfo_controller', function($rootScope, $scope, $http,$state,i18nService,$modal,filterFilter,behavior_service,common_service) {

    //返回首页
    $scope.backhome = function(){
        $state.go("dashboard")
    }
    console.log($rootScope.funcCode)
});