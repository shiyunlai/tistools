/**
 * Created by HP on 2016/9/19.
 */
MetronicApp.controller('commonCaller_controller', function ($filter,$rootScope, $scope, $state, $stateParams, filterFilter,commonCaller_service, $modal, $http, $timeout,$interval) {

    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });

    var requestInfo = {};
    $scope.requestInfo = requestInfo;

    //给一些默认值
    //$scope.requestInfo.host = '127.0.0.1'; //FIXME 这么些会导致跨域问题,错误如下
    /*
     XMLHttpRequest cannot load http://127.0.0.1:8089/tis/testController/test. Response to preflight request doesn't pass access control check: No 'Access-Control-Allow-Origin' header is present on the requested resource. Origin 'http://localhost:8089' is therefore not allowed access. The response had HTTP status code 403.
     */
    $scope.requestInfo.host = 'localhost';
    $scope.requestInfo.port = '8089';
    $scope.requestInfo.mapping = '/tis/testController/test';
    $scope.requestInfo.protocol = 'http';
    $scope.requestInfo.method = 'POST';
    $scope.requestInfo.postJsonData = "{trans_serial:\"123123\"}";

    $scope.finalUrl= assemblyUrl(requestInfo);

    $scope.responseJsonData= "" ;


    //requestInfo 任何一个属性变化,都更新finalUrl
    var watch = $scope.$watch('requestInfo',function(newInfo,oldInfo, scope){

        $scope.finalUrl= assemblyUrl(newInfo);

    },true);

    // call按钮事件
    $scope.btnCall = function( requestInfo ){
        console.log( requestInfo ) ;
        var promise = commonCaller_service.callTest( requestInfo ) ;
        promise.then(function(data){
            console.log( requestInfo ) ;
            $scope.responseJsonData = data.data ;
        })

    }

});
