/**
 * Created by zhangsu on 2016/5/9.
 */
MetronicApp.controller('login_controller', function($rootScope, $scope,$state,$stateParams,login_service,filterFilter,$modal, $http, $timeout) {

    $scope.$on('$viewContentLoaded', function() {
        // initialize core components
        App.initAjax();
    });

    $rootScope.settings.layout.pageBodySolid = true;
    $rootScope.settings.layout.pageSidebarClosed = false;
    ComponentsDateTimePickers.init()

});