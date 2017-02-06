'use strict';
/* Controllers */
angular.module('nglanmo.${table.id}_controller', []).controller('${table.id}_controller', ['$scope','$rootScope', '$modal', '$routeParams', '${table.id}_service', 'changliang_service', '$location', 'filterFilter',
    function ($scope,$rootScope, $modal, $routeParams, ${table.id}_service, changliang_service, $location, filterFilter) {
        //begin-本业务的全局变量
        var ${table.id} = {};
        $scope.${table.id} = ${table.id};
        //初始化controller  TODO
        initController($scope, ${table.id}, '${table.id}', changliang_service, ${table.id}_service, filterFilter);
        ${table.id}.searchForm.searchItems = [
            {"name": "wuliaomingcheng", "code": "lk"},
            {"name": "wuliaobianma", "code": "lk"}
        ];
        ${table.id}.searchForm.orderGuize="createTime desc";

        //        增加 修改开始
        var id = $routeParams.id;
        if (id != 0 && id != undefined) {
            var promise = ${table.id}_service.loadById(id);
            promise.then(function (res) {
                //初始化变量
                ${table.id}.item = res;
            });
        }
        // 增加修改保存
        ${table.id}.save = function() {
            var submitform={};
            submitform.item = ${table.id}.item;
            var promise = ${table.id}_service.createOrUpdate(submitform);
            promise.then(function (data) {  // 调用承诺API获取数据 .resolve
                alert('提交成功');
                $location.path('${table.id}-list/0');
            }, function (data) {  // 处理错误 .reject
                alert('提交失败');
            });
        }
        //        执行数据初始化
        var type = $routeParams.type;
        if(type==0){
            ${table.id}.search1();
        }
        //        执行数据初始化结束
        //-end功能结束
    }]);
	
angular.module('nglanmo.${table.id}_edit_controller', []).controller('${table.id}_edit_controller', ['$scope','$rootScope', '$modal', '$routeParams', '${table.id}_service', 'changliang_service', '$location', 'filterFilter',
    function ($scope,$rootScope, $modal, $routeParams, ${table.id}_service,changliang_service, $location, filterFilter) {
       var id =  $routeParams.id;
       var ${table.id} = {};
        $scope.${table.id} = ${table.id};
		 ${table.id}.chang={};
        ${table.id}.getchangliang = function(fenlei){
            if(${table.id}.chang[fenlei]==undefined){
                changliang_service.getchangliang(fenlei).then(function(data){
                    ${table.id}.chang[fenlei]=data;
                });
            }
        }
        if(id!='0'){
          var promise=  ${table.id}_service.loadById(id);
            promise.then(function(data){
                ${table.id}.item=data;
            });
        }
        $scope.submit = function () {
			${table.id}.item.files=[];
			initUploadFiles( ${table.id}.fileflow.files, ${table.id}.item.files);
            var promise = ${table.id}_service.createOrUpdate(${table.id}.item);
            promise.then(function (data) {  // 调用承诺API获取数据 .resolve
                alert('提交成功');
                $location.path("${table.id}-list");
            }, function (data) {  // 处理错误 .reject
                alert('提交失败');
            });
        };
        $scope.cancel = function () {
            $location.path("${table.id}-list");
        };
    }]);