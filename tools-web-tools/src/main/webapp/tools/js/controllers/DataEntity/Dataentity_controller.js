/**
 * Created by wangbo on 2017/11/8
 */

MetronicApp.controller('dataEntity_controller', function ($filter, $scope, $rootScope, common_service,filterFilter, $state,$modal,$uibModal, $http, $timeout,$interval,i18nService) {
    //定义公共对象
    var dataEnt = {};
    $scope.dataEnt = dataEnt;

    i18nService.setCurrentLang("zh-cn");//表格翻译
    //数据实体表格
    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var grid = [
        { field: "bhvName", displayName:'行为名称'},
        { field: "bhvCode", displayName:'行为代码'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.notrolRow = row.entity;
        }else{
            $scope.notrolRow = '';//制空
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,grid,false,f);
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
    $scope.gridOptions.data = permit;
    $scope.gridOptions.mydefalutData = permit;
    $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);


    //新增
    $scope.dataEnt_add = function () {


    }

    //修改
    $scope.dataEnt_edit = function () {
        var getSel = $scope.gridOptions.getSelectedRows();//拿到数据
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来修改！");
        }else{

        }
    }

    //删除
    $scope.dataEnt_delete = function () {
        var getSel = $scope.gridOptions.getSelectedRows();//拿到数据
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来删除！");
        }else{

        }
    }

    //查看
    $scope.dataEnt.info = function () {
        var getSel = $scope.gridOptions.getSelectedRows();//拿到数据
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来查看！");
        }else{
            var item = getSel[0].bhvCode;//到历史页面去调用，查询登陆的历史。 需要修改接口
            $state.go("dataDetails",{id:item});//跳转新页面
        }
    }

});
