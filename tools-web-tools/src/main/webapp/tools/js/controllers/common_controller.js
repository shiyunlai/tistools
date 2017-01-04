'use strict';
MetronicApp.controller('common_controller', function ($rootScope, $scope, $state, $stateParams, quanxianbiao_service, filterFilter, $modal, $http, $timeout) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        Metronic.initAjax();
        ComponentsPickers.init();
    });

    $scope.login = {}
    $scope.initlogin = function () {
        var promise = $http.post(baseUrl + 'ngres/login/info');
        promise.then(function (data) {
            $rootScope.renyuan = data.data.renyuan;
            $rootScope.zuzhi = data.data.zuzhi;
        });
    }
    function initquanxian() {

        var promise = quanxianbiao_service.getResButtonSelf();
        promise.then(function (data) {
            $rootScope.mybuttons = data.data;

            $rootScope.auth = function (fnid) {
                if ($rootScope.renyuan == undefined) {
                    return false;
                }
                if ($rootScope.renyuan.dengluming == 'admin') {
                    return true;
                }
                if ($rootScope.mybuttons != undefined) {
                    for (var i = 0; i < $rootScope.mybuttons.length; i++) {
                        if ($rootScope.mybuttons[i] == null) {
                            return;
                        }
                        if ($rootScope.mybuttons[i].gongnengid == fnid) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    initquanxian();
    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageBodySolid = true;
    $rootScope.settings.layout.pageSidebarClosed = false;
});
