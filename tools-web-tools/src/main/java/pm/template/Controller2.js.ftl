'use strict';
MetronicApp.controller('${table.id}_controller', function($rootScope, $scope,$state,$stateParams,${table.id}_service,filterFilter,$modal, $http, $timeout) {
    $scope.$on('$viewContentLoaded', function() {
        // initialize core components
        Metronic.initAjax();
        ComponentsPickers.init();
    });
    var ${table.id} = {};
    $scope.${table.id} = ${table.id};
    //初始化controller
    initController($scope, ${table.id}, '${table.id}',  ${table.id}_service, filterFilter);
    ${table.id}.searchForm.orderGuize="id desc";
    ${table.id}.search1();

    $scope.add=function(){
        $state.go('${table.id}edit',{id: ''});
    }
    $scope.edit=function(d){
        $state.go('${table.id}edit',{id: d.id});
    }
    $scope.detail=function(d){
        $state.go('${table.id}detail',{id: d.id});
    }
    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageBodySolid = true;
    $rootScope.settings.layout.pageSidebarClosed = false;
});


MetronicApp.controller('${table.id}edit_controller', function($rootScope, $scope,$state,$stateParams,${table.id}_service,filterFilter,$modal, $http, $timeout) {
    $scope.$on('$viewContentLoaded', function() {
        // initialize core components
        Metronic.initAjax();
        ComponentsPickers.init();
       
    });
    var ${table.id} = {};
    $scope.${table.id} = ${table.id};
    var id= $stateParams.id;
    if(!isNull(id)){
       ${table.id}_service.loadById(id).then(function (res) {
            //初始化变量
           ${table.id}.item = res
        });
    }


    // 增加修改保存
    // 增加修改保存
    ${table.id}.save = function(action) {
        var submitform={};
        submitform.item = ${table.id}.item;
        var promise = ${table.id}_service.createOrUpdate(submitform);
        promise.then(function (data) {  // 调用承诺API获取数据 .resolve
            toastr['success']("您的信息已保存成功！", "恭喜您！")
            ${table.id}.item={}
            if(action==1){
                $state.go('${table.id}');
            }
        }, function (data) {  // 处理错误 .reject
            toastr['error']("您的信息保存出错！", "SORRY！")
        });
    }
    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageBodySolid = true;
    $rootScope.settings.layout.pageSidebarClosed = false;
})
