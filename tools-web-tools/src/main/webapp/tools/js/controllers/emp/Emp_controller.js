/**
 * Created by gaojie on 2017/6/26.
 */
angular.module('MetronicApp').controller('Emp_controller', function($rootScope, $scope,abftree_service, $http, $timeout,i18nService,filterFilter,uiGridConstants,$uibModal,$state) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        App.initAjax();
    });
    //定义主题对象
    var emp = {};
    $scope.emp = emp;
    //定义页签控制对象
    var flag = {};
    $scope.flag = flag;
    //默认为员工列表
    flag.yglb = true;
    flag.xqxx = false;
    flag.zzgs = false;
    flag.ryqx = false;
    //详情页编辑状态标识
    var editflag = true;
    $scope.editflag = editflag;
    //页签切换控制,0--列表,1--详情,2--归属,3--权限
    emp.loaddata = function (number) {
        if(number == "0"){
            for(var i in $scope.flag){
                $scope.flag[i] = false;
            }
            $scope.flag.yglb = true;
            //todo
        }else if(number == "1"){
            for(var i in $scope.flag){
                $scope.flag[i] = false;
            }
            $scope.flag.xqxx = true;
            //todo
        }else if(number == "2"){
            for(var i in $scope.flag){
                $scope.flag[i] = false;
            }
            $scope.flag.zzgs = true;
            //todo
        }else if(number == "3"){
            for(var i in $scope.flag){
                $scope.flag[i] = false;
            }
            $scope.flag.ryqx = true;
            //todo
        }
    }
    //设置插件语言为中文
    i18nService.setCurrentLang("zh-cn");
    //生成员工列表信息
    var empgrid = {};
    $scope.empgrid = empgrid;
    var initdata = function () {
        //调取员工信息
        return $scope.myData = [{name: "Moroni", age: 50},
            {name: "Tiancum", age: 43},
            {name: "Jacob", age: 27},
            {name: "Nephi", age: 29},
            {name: "Enos", age: 34}];
    }
    //定义单选事件
    var sele = function () {
        
    }
    $scope.empgrid = initgrid($scope,empgrid,initdata(),filterFilter,null,false,sele);
    //定义放置一个员工详情的对象
    var item = {};
    $scope.item = {};
    //返回详情页按钮事件
    emp.back = function () {
        for(var i in $scope.flag){
            $scope.flag[i] = false;
        }
        $scope.flag.yglb = true;
    }
    //详情按钮事件
    emp.detail = function () {
        var arr = $scope.empgrid.getSelectedRows();
        $scope.item = arr[0];
        if(isNull($scope.item)){
            toastr['error']( "请选择一条记录！");
            return false;
        }
        for(var i in $scope.flag){
            $scope.flag[i] = false;
        }
        $scope.flag.xqxx = true;
        $scope.editflag = true;
        $scope.title = "员工详情信息"
    }
    //编辑按钮事件
    emp.edit = function () {
        var arr = $scope.empgrid.getSelectedRows();
        $scope.item = arr[0];
        if(isNull($scope.item)){
            toastr['error']( "请选择一条记录！");
            return false;
        }
        for(var i in $scope.flag){
            $scope.flag[i] = false;
        }
        $scope.flag.xqxx = true;
        $scope.editflag = false;
        $scope.title = "修改员工信息"
    }
    //员工组织归属按钮事件
    emp.belongorg = function () {
        var arr = $scope.empgrid.getSelectedRows();
        $scope.item = arr[0];
        if(isNull($scope.item)){
            toastr['error']( "请选择一条记录！");
            return false;
        }
        for(var i in $scope.flag){
            $scope.flag[i] = false;
        }
        $scope.flag.zzgs = true;
    }
    //个人权限设置按钮事件
    emp.setqx = function () {
        var arr = $scope.empgrid.getSelectedRows();
        $scope.item = arr[0];
        if(isNull($scope.item)){
            toastr['error']( "请选择一条记录！");
            return false;
        }
        for(var i in $scope.flag){
            $scope.flag[i] = false;
        }
        $scope.flag.ryqx = true;
    }




    //所属机构列表生成
    var orggrid = {};
    $scope.orggrid = orggrid;
    var initorgdata = function () {
        //调取员工-机构信息OM_EMP_ORG
        //机构名称,机构类型,是否主机构
        return $scope.orgData = [{机构名称: "Moroni", 机构类型: "总行",是否主机构:"是"},
            {机构名称: "Jack", 机构类型: "支行",是否主机构:"否"}];
    }
    //定义单选事件
    var seleorg = function () {

    }
    $scope.orggrid = initgrid($scope,orggrid,initorgdata(),filterFilter,null,false,seleorg);
    //所属岗位列表生成
    var postgrid = {};
    $scope.postgrid = postgrid;
    var initpostdata = function () {
        //调取员工-岗位信息OM_EMP_POSITION
        //岗位名称,岗位类型,是否主岗位
        return $scope.postData = [{岗位名称: "Moroni", 岗位类型: "总行",是否主岗位:"是"},
            {岗位名称: "Jack", 岗位类型: "支行",是否主岗位:"否"}]
    }
    //定义单选事件
    var selepost = function () {

    }
    $scope.postgrid = initgrid($scope,postgrid,initpostdata(),filterFilter,null,false,selepost)
});