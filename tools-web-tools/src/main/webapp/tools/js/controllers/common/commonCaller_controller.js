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
    //$scope.requestInfo.mapping = '/tis/log/analyse/172.20.10.9:20883';
    $scope.requestInfo.mapping = '';
    $scope.requestInfo.app_name = '/tis';
    $scope.requestInfo.parent_mapping = '/commonCaller';
    $scope.requestInfo.child_mapping = '/testPostController';
    $scope.requestInfo.protocol = 'http';
    $scope.requestInfo.method = 'POST';
    $scope.requestInfo.postJsonData = "{\"type\":\"part\",\"logs\":[\"biztrace.log.1\",\"biztrace.log.2\"]}";
    $scope.requestInfo.getParamData = [];
    $scope.finalUrl= assemblyUrl(requestInfo);

    $scope.responseJsonData = "";

    var paramValues = [];
    var paramItem1 = {};
    paramItem1.paramKey = 'id';
    paramItem1.paramVal = '123';
    paramValues.push(paramItem1);
    var paramItem2 = {};
    paramItem2.paramKey = 'name';
    paramItem2.paramVal = 'Jack';
    paramValues.push(paramItem2);



    $scope.paramValues = paramValues;
    $scope.requestInfo.getParamData = paramValues;
    $scope.addParamItem = function() {
        var paramItem = {};
        paramValues.push(paramItem);
    }
    $scope.delParamItem = function(index) {
        paramValues.splice(index,1);
    }
    //$scope.addParamItem();

    //requestInfo 任何一个属性变化,都更新finalUrl
    var watch = $scope.$watch('requestInfo',function(newInfo,oldInfo, scope){


        $scope.finalUrl= assemblyUrl(newInfo);

    },true);

    //requestInfo 任何一个属性变化,都更新finalUrl
    var watch = $scope.$watch('requestInfo',function(newInfo,oldInfo, scope){


        $scope.finalUrl= assemblyUrl(newInfo);

    },true);

    // call按钮事件
    $scope.btnCall = function( requestInfo ){
        $scope.responseJsonData = undefined;
        console.log( requestInfo ) ;
        var promise = commonCaller_service.callTest( requestInfo ) ;
        promise.then(function(data){
            console.log( data ) ;
            $scope.responseJsonData = data.data ;
        })

    }

});
