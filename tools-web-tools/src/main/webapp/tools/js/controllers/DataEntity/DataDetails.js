/**
 * Created by wangbo on 2017/11/8  实体详情页面
 */

MetronicApp.controller('dataDetails_controller', function ($filter, $scope, $rootScope, common_service,filterFilter,$stateParams, $modal,$uibModal, $http, $timeout,$interval,i18nService) {
    var dataDet = {};
    $scope.dataDet = dataDet;

    //返回方法
    $scope.myback = function(){
        window.history.back(-1);
    }

    var testid = {};
    $scope.testid = $stateParams.id;//拿到传入的内容


    //tab控制页签方法
    $scope.dataDet.Attributes = true;//默认展示一个
    dataDet.loadgwdata = function (type) {
        if(type == 0){
            $scope.dataDet.Attributes = true;
            $scope.dataDet.Permissions = false;
        }else if (type == 1){
            $scope.dataDet.Permissions = true;
            $scope.dataDet.Attributes = false;
        }
    }


    //实体属性表格
    var gridAttributes = {};
    $scope.gridAttributes = gridAttributes;
    var grid = [
        { field: "bhvName", displayName:'行为名称'},
        { field: "bhvCode", displayName:'行为代码'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.gridAt = row.entity;
        }else{
            $scope.gridAt = '';//制空
        }
    }
    $scope.gridAttributes = initgrid($scope,gridAttributes,filterFilter,grid,false,f);
    //模拟获取数据渲染表格
    function getData() {
        var permit= [
            {'bhvName':'测试','bhvCode':0001},
            {'bhvName':'测试','bhvCode':0002},
            {'bhvName':'测试','bhvCode':0003},
            {'bhvName':'测试','bhvCode':0004},
            {'bhvName':'测试','bhvCode':0005},
            {'bhvName':'测试','bhvCode':0006},
            {'bhvName':'测试','bhvCode':0007},
            {'bhvName':'测试','bhvCode':0008},
            {'bhvName':'测试','bhvCode':0009},
            {'bhvName':'测试','bhvCode':0000},
            {'bhvName':'测试','bhvCode':0001},
            {'bhvName':'测试','bhvCode':0002}
        ]
        $scope.gridAttributes.data = permit;
        $scope.gridAttributes.mydefalutData = permit;
        $scope.gridAttributes.getPage(1,$scope.gridAttributes.paginationPageSize);
    }
    getData();
    //新增属性
    $scope.dataDet_add = function () {
        console.log("新增成功")
    }
    //修改属性
    $scope.dataDet_edit = function () {
        var getSel = $scope.gridAttributes.getSelectedRows();//拿到数据
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来修改！");
        }else{
            toastr['success']("修改成功！");
        }
    }

    //删除属性
    $scope.dataDet_delete = function () {
        var getSel = $scope.gridAttributes.getSelectedRows();//拿到数据
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来删除！");
        }else{
            toastr['success']("删除成功！");
        }
    }

    //实体范围权限表格
    var gridPermissions = {};
    $scope.gridPermissions = gridPermissions;
    var gridPer = [
        { field: "bhvName", displayName:'行为名称'},
        { field: "bhvCode", displayName:'行为代码'}
    ];
    var fPer = function(row){
        if(row.isSelected){
            $scope.gridPer = row.entity;
        }else{
            $scope.gridPer = '';//制空
        }
    }
    $scope.gridPermissions = initgrid($scope,gridPermissions,filterFilter,gridPer,false,fPer);

    //模拟获取数据渲染表格
    function getDatas() {
        var permit= [
            {'bhvName':'范围','bhvCode':0001},
            {'bhvName':'范围','bhvCode':0002},
            {'bhvName':'范围','bhvCode':0003},
            {'bhvName':'范围','bhvCode':0004},
            {'bhvName':'范围','bhvCode':0005},
            {'bhvName':'范围','bhvCode':0006},
            {'bhvName':'范围','bhvCode':0007},
            {'bhvName':'范围','bhvCode':0008},
            {'bhvName':'范围','bhvCode':0009},
            {'bhvName':'范围','bhvCode':0000},
            {'bhvName':'范围','bhvCode':0001},
            {'bhvName':'范围','bhvCode':0002}
        ]
        $scope.gridPermissions.data = permit;
        $scope.gridPermissions.mydefalutData = permit;
        $scope.gridPermissions.getPage(1,$scope.gridPermissions.paginationPageSize);
    }
    getDatas();

    //新增范围
    $scope.dataDett_add = function () {
        console.log("新增成功")
    }
    //修改范围
    $scope.dataDett_edit = function () {
        var getSel = $scope.gridPermissions.getSelectedRows();//拿到数据
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来修改！");
        }else{
            toastr['success']("修改成功！");
        }
    }

    //删除范围
    $scope.dataDett_delete = function () {
        var getSel = $scope.gridPermissions.getSelectedRows();//拿到数据
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来删除！");
        }else{
            toastr['success']("删除成功！");
        }
    }


});
