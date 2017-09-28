
/*操作员功能行为权限控制器*/
MetronicApp.controller('permission_controller', function ($rootScope, $scope, $state, $stateParams,common_service, filterFilter, $modal,$uibModal, $http, $timeout,$interval,i18nService) {

    var  permiss = {}
    $scope.permiss = permiss;
   //返回方法
    $scope.myback = function(){
        window.history.back(-1);
    }

    var userid = $stateParams.id;//接受传入的值
    $scope.currRole = userid;//显示当前操作员


   /* var res = $rootScope.res.operator_service;//页面所需调用的服务
    var subFrom = {};
    subFrom.userId  = userid;
    common_service.post(res.queryOperatorAllApp,subFrom).then(function(data){
        if(data.status == "success"){
            permiss.appselectApp= data.retMessage;
        }
    })*/

    //查询应用内容显示
    // $scope.permiss.search =function (item) {
    //        //让下方内容显示
    //         if(!isNull(item)){
    //             $scope.permiss.selectApp = true;
    //             //调用刷新树方法，传入应用
    //             //调用刷新列表方法，传入应用
    //     }else{
    //             $scope.permiss.selectApp = false;
    //         }
    // }
    /* 树结构逻辑代码*/
    //菜单搜索修改，改成键盘弹起事件，加上search组件
    var to = false;
    $('#q').keyup(function () {
        if(to) {
            clearTimeout(to);
        }
        $('#container').jstree().load_all();
        to = setTimeout(function () {
            var v = $('#q').val();
            $('#container').jstree(true).search(v);
        }, 250);
    });

    //清空n
    permiss.clear = function () {
        $scope.searchitem = "";
        if(to) {
            clearTimeout(to);
        }
        $('#container').jstree().load_all();
        to = setTimeout(function () {
            var v = $('#q').val();
            $('#container').jstree(true).search(v);
        }, 250);
    }




    //表格创建
    i18nService.setCurrentLang("zh-cn");
    //未允许行为表格创建
    var notrolegird = {};
    $scope.notrolegird = notrolegird;
    var com = [
        { field: "roleName", displayName:'行为名'},
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            $scope.selectRow = '';//制空
        }
    }
    $scope.notrolegird = initgrid($scope,notrolegird,filterFilter,com,false,f);
    $scope.notrolegird.enablePaginationControls = false;//禁止有分页
    $scope.notrolegird.enableFiltering = false;//禁止有搜索
    $scope.notrolegird.enableGridMenu = false;//禁止有菜单
    $scope.notrolegird.data = [
        {'roleName':'测试行为'}

    ]


    //授予移除逻辑


    //已允许行为表格生成
    var alrolegird = {};
    $scope.alrolegird = notrolegird;
    var com1 = [
        { field: "roleName", displayName:'行为名'}
    ];
    var f1 = function(row){
        if(row.isSelected){
            $scope.deleteGUid = row.entity;
        }else{
            $scope.deleteGUid = '';//制空
        }
    }
    $scope.alrolegird = initgrid($scope,alrolegird,filterFilter,com1,false,f1);
    $scope.alrolegird.enablePaginationControls = false;//禁止有分页
    $scope.alrolegird.enableFiltering = false;//禁止有搜索
    $scope.alrolegird.enableGridMenu = false;//禁止有菜单
    $scope.alrolegird.data = [
        {'roleName':'查询行为'},
        {'roleName':'测试行为'},
        {'roleName':'新增行为'},
        {'roleName':'删除行为'},
        {'roleName':'特殊行为'},
    ]


});



















