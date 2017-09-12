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
        { field: 'operateTime', displayName: '操作时间',type: 'date', cellFilter: 'date:"yyyy-MM-dd HH:mm:ss"'},
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


//日志详情页面
angular.module('MetronicApp').controller('jourinfo_controller', function($rootScope, $scope,$state, $http,i18nService,$uibModal,$stateParams,filterFilter,behavior_service,common_service) {
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
        if(data.status == "success"){
            $scope.loginfo = datas.instance;
            if(datas.instance.operateResult =='fail'){
                $scope.logofail = true;
            }else{
                $scope.logofail = false;
            }
            $scope.loginfo.operateTime = moment(datas.instance).format('YYYY-MM-DD HH:mm:ss');
        }else{
            toastr['error']('查询失败'+'<br/>'+data.retMessage);
        }
    })

    var gridOptions1 = {};
    $scope.gridOptions1 = gridOptions1;
    var com = [
        { field: 'instance.objGuid', displayName: '对象guid',cellTemplate: '<a ng-click="grid.appScope.ceshi(row.entity.instance.objGuid)" style="text-decoration: none;display:block;margin-top: 5px;margin-left: 3px;">{{row.entity.instance.objGuid}}</a>'},
        { field: 'instance.objFrom', displayName: '对象来源'},
        { field: 'instance.objType', displayName: '对象类型'},
        { field: 'instance.objName', displayName: '对象名'},
        { field: 'instance.objValue', displayName: '对象值'}
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
        if(data.status == "success"){
            $scope.gridOptions1.data =  datas.allObj;
            $scope.gridOptions1.mydefalutData = datas.allObj;
            $scope.gridOptions1.getPage(1,$scope.gridOptions1.paginationPageSize);
        }else{
            toastr['error']('查询失败'+'<br/>'+data.retMessage);
        }
    })


    $scope.ceshi = function(item){
        $state.go("loghistory",{id:item});//跳转新页面
    }

    //查看关键参数
    $scope.parameter = function(){
        var getSel = $scope.gridOptions1.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来查看！");
        }else{
            openwindow($uibModal, 'views/journal/keywords.html', 'lg',
                function ($scope, $modalInstance) {
                    var gridOptions2 = {};
                    $scope.gridOptions2 = gridOptions1;
                    var com = [
                        { field: 'guidHistory', displayName: '操作记录guid'},
                        { field: 'param', displayName: '关键值名称' },
                        { field: 'value', displayName: '关键值'}
                    ];
                    var f = function(row){
                        if(row.isSelected){
                            $scope.selectRow1 = row.entity;
                        }else{
                            delete $scope.selectRow1;//制空
                        }
                    }
                    $scope.gridOptions2 = initgrid($scope,gridOptions2,filterFilter,com,false,f);
                    var subFrom = {};
                    subFrom.logGuid = $stateParams.id;
                    common_service.post(res.queryOperateDetail,subFrom).then(function(data){
                        var datas = data.retMessage;
                        if(data.status == "success"){
                            $scope.gridOptions2.data =  datas.allObj[0].keywords;
                            $scope.gridOptions2.mydefalutData = datas.allObj[0].keywords;
                            $scope.gridOptions2.getPage(1,$scope.gridOptions2.paginationPageSize);
                        }else{
                            toastr['error']('查询失败'+'<br/>'+data.retMessage);
                        }
                    })
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }
    }

});

//日志历史页面
angular.module('MetronicApp').controller('loghistory_controller', function($rootScope, $scope,$state, $http,i18nService,$uibModal,$stateParams,filterFilter,behavior_service,common_service) {
    i18nService.setCurrentLang("zh-cn");

    var res = $rootScope.res.log_service;//页面所需调用的日志服务
    var loghistory = {};
    $scope.loghistory = loghistory;
    //返回方法
    $scope.myback = function(){
        window.history.back(-1);
    }
    var objGuid = $stateParams.id;//拿到传入的对象guid
    $scope.objGuid = objGuid;

    //生成表格
    var gridOptionsh = {};
    $scope.gridOptionsh = gridOptionsh;
    var com = [
        { field: 'instance.objGuid', displayName: '对象guid',cellTemplate: '<a ng-click="grid.appScope.ceshi(row.entity.instance.objGuid)" style="text-decoration: none;display:block;margin-top: 5px;margin-left: 3px;">{{row.entity.instance.objGuid}}</a>'},
        { field: 'instance.objFrom', displayName: '对象来源'},
        { field: 'instance.objType', displayName: '对象类型'},
        { field: 'instance.objName', displayName: '对象名'},
        { field: 'instance.objValue', displayName: '对象值'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow1 = row.entity;
        }else{
            delete $scope.selectRow1;//制空
        }
    }
    $scope.gridOptionsh = initgrid($scope,gridOptionsh,filterFilter,com,false,f);
    var subFrom = {};
    subFrom.objGuid = objGuid;
    console.log(subFrom)
    common_service.post(res.queryOperateHistoryList,subFrom).then(function(data){
        console.log(data);//出现问题，服务报错
        var datas = data.retMessage;
        if(data.status == "success"){
            $scope.gridOptions1.data =  datas.allObj;
            $scope.gridOptions1.mydefalutData = datas.allObj;
            $scope.gridOptions1.getPage(1,$scope.gridOptions1.paginationPageSize);
        }else{
            toastr['error']('查询失败'+'<br/>'+data.retMessage);
        }
    })

});