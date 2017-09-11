/**
 * Created by wangbo on 2017/9/8.
 */

angular.module('MetronicApp').controller('journal_controller', function($rootScope, $scope, $http,$state,i18nService,$modal,filterFilter,behavior_service,common_service) {

    i18nService.setCurrentLang("zh-cn");
    var logdetails = {};
    $scope.logdetails = logdetails;
    var res = $rootScope.res.log_service;//页面所需调用的日志服务
    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var com = [
        { field: 'operateFrom', displayName: '操作渠道'},
        { field: 'operateResult', displayName: '操作结果'},
        { field: 'operateTime', displayName: '操作时间'},
        //{ field: 'operateType', displayName: '操作类型'},
        //{ field: 'operatorName', displayName: '操作员姓名'},
        { field: 'processDesc', displayName: '操作描述'},
        //{ field: 'restfulUrl', displayName: '服务地址'},
        { field: 'userId', displayName: '操作员'}
        //{ field: 'edit', displayName: '详情',cellTemplate: '<a  style="text-decoration:none; display: block;" ng-click = "ceshi()" class="ui-grid-cell-contents" title="TOOLTIP">详情</a>'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);
    common_service.post(res.queryOperateLogList,{}).then(function(data){
        var datas = data.retMessage;
        if(data.status == "success"){
            $scope.gridOptions.data =  datas;
            $scope.gridOptions.mydefalutData = datas;
            $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
        }else{
            toastr['error']('查询失败'+'<br/>'+data.retMessage);
        }
    })

    $scope.logdetails.look = function(){
        var getSel = $scope.gridOptions.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来查看！");
        }else{
            var items = getSel[0].guid;//数据信息;

            $state.go("journinfo",{id:items})
        }
    }
});




angular.module('MetronicApp').controller('jourinfo_controller', function($rootScope, $scope, $http,i18nService,$stateParams,$modal,filterFilter,behavior_service,common_service) {
    i18nService.setCurrentLang("zh-cn");

    var res = $rootScope.res.log_service;//页面所需调用的日志服务
    var loginfo = {};
    $scope.loginfo = loginfo;
    //返回方法
    $scope.myback = function(){
        window.history.back(-1);
    }

    var subFrom = {};
    subFrom.logGuid =  $stateParams.id;//接受传入的值
    //根据guid查询操作员详情服务
    common_service.post(res.queryOperateDetail,subFrom).then(function(data){
        var datas = data.retMessage;
        console.log(datas)
        if(data.status == "success"){
            $scope.loginfo = datas.instance;
            if(datas.instance.operateResult =='fail'){
                $scope.logofail = true;
            }else{
                $scope.logofail = false;
            }
            $scope.loginfo.operateTime = moment(datas.instance).format('YYYY-MM-DD');
        }else{
            toastr['error']('查询失败'+'<br/>'+data.retMessage);
        }
    })

    var gridOptions1 = {};
    $scope.gridOptions1 = gridOptions1;
    var com = [
        { field: 'instance.objName', displayName: '对象名'},
        { field: 'instance.objValue', displayName: '对象值'},
        { field: 'keywords', displayName: '操作记录'},
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow1 = row.entity;
        }else{
            delete $scope.selectRow1;//制空
        }
    }
    $scope.gridOptions1 = initgrid($scope,gridOptions1,filterFilter,com,false,f);

    common_service.post(res.queryOperateDetail,subFrom).then(function(data){

        var datas = data.retMessage;
        console.log(datas)
        if(data.status == "success"){
            $scope.gridOptions1.data =  datas.allObj;
            $scope.gridOptions1.mydefalutData = datas.allObj;
            $scope.gridOptions1.getPage(1,$scope.gridOptions1.paginationPageSize);
        }else{
            toastr['error']('查询失败'+'<br/>'+data.retMessage);
        }
    })


});