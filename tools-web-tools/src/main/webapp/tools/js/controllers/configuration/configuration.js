/**
 * Created by wangbo on 2017/9/24.
 */

MetronicApp.controller('configuration_controller', function ($filter, $scope, $state,$rootScope, common_service,filterFilter, $modal,$uibModal, $http, $timeout,$interval,i18nService) {
    var config = {};
    $scope.config = config;
    //查询所有个性化配置
    var res = $rootScope.res.operator_service;//页面所需调用的服务
    //查询所有配置
    config.queryallconfig = function(){
        common_service.post(res.queryConfigList,{}).then(function(data){
            if(data.status == "success"){
                var datas  = data.retMessage;
                $scope.gridOptions.data =  datas;
                $scope.gridOptions.mydefalutData = datas;
                $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
            }
        })
    }
    config.queryallconfig();//调用查询所有配置


    //grid表格
    i18nService.setCurrentLang("zh-cn");
    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var com = [
        { field: 'guidApp', displayName: '应用名称',width:"10%",cellTemplate:'<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidApp | translateApp) + $root.constant[row.entity.guidApp]}}</div>'},
        { field: 'configType', displayName: '配置类型',width:"8%",cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.configType | translateConstants :\'DICT_AC_CONFIGTYPE\') + $root.constant[\'DICT_AC_CONFIGTYPE-\'+row.entity.configType]}}</div>'},
        { field: 'configName', displayName: '配置名',width:"8%"},
        { field: 'configDict', displayName: '配置值字典',width:"10%",cellTemplate:'<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.configDict | translateDictKey) + $root.constant[row.entity.configDict]}}</div>'},
        { field: 'configValue', displayName: '配置默认值',width:"15%",cellTemplate:'<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.configValue | translateDictitemvalue) + $root.constant[row.entity.configValue]}}</div>'},
        { field: "enabled", displayName:'配置风格',width:"8%",cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.configStyle | translateConstants :\'DICT_CONFIG_STYLE\') + $root.constant[\'DICT_CONFIG_STYLE-\'+row.entity.configStyle]}}</div>'},
        { field: "configStyle", displayName:'是否启用',width:"8%",cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.enabled | translateConstants :\'DICT_YON\') + $root.constant[\'DICT_YON-\'+row.entity.enabled]}}</div>'},
        { field: "configDesc", displayName:'配置描述说明'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f);
    
    //查看概况
    config.histroy = function () {
        var getSel = $scope.gridOptions.getSelectedRows();
        var confGuid= getSel[0].guid
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请选则一条数据进行修改！");
        }else{
            $state.go("loghistory",{id:confGuid});//跳转到历史页面
        }
    }

    //修改配置
    $scope.config.edit = function(){
        var queryallconfig =config.queryallconfig;//拿到列表刷新方法
        var getSel = $scope.gridOptions.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请选则一条数据进行修改！");
        }else{
            var str =  getSel[0]
            openwindow($uibModal, 'views/configuration/configAdd.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    $scope.id = true;
                    if(!isNull(str)){//如果参数不为空，则就回显
                        $scope.configFrom = angular.copy(str);
                        var textshow  = str.configDict;
                        $timeout(function () {
                            $(".test123").select2("val",textshow);//渲染表格数据
                        },200);
                    }
                    var config = {};
                    $scope.config= config;
                    $scope.$watch('configFrom.configDict',function(newValue,oldValue) {

                            $scope.config.dictisNull = true;//默认字典值展现

                        //查询字典对应字典项
                        var subFrom = {};
                        subFrom.dictKey = newValue;//key进行查询
                        var dict =  $rootScope.res.dictonary_service;
                        common_service.post(dict.queryDictItemListByDictKey,subFrom).then(function(data){
                            if(data.status == "success"){
                                config.dictnameLitem = data.retMessage;//把所有的业务字典绑定给全局
                            }
                        })
                    });
                    $scope.add = function(item){//保存新增的函数
                        var subFrom = {};
                        subFrom = item;
                        var res = $rootScope.res.operator_service;//页面所需调用的服务
                        common_service.post(res.updateConfig,subFrom).then(function(data){
                            console.log(data);
                            if(data.status == "success"){
                                queryallconfig();//查询所有列表
                                toastr['success']("修改配置成功！");
                                $modalInstance.close();
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }
    }
    //新增配置
    $scope.config.add = function(){
        var queryallconfig =config.queryallconfig;
        openwindow($uibModal, 'views/configuration/configAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                var config = {};
                $scope.config =config;
                $scope.$watch('configFrom.configDict',function(newValue,oldValue){
                    if(newValue != oldValue){
                        $scope.config.dictisNull = true;//默认字典值展现
                    }else{
                        $scope.config.dictisNull = false;//默认字典值隐藏
                    }
                    //查询字典对应字典项
                    var subFrom = {};
                    subFrom.dictKey = newValue;//key进行查询
                    var dict =  $rootScope.res.dictonary_service;
                    common_service.post(dict.queryDictItemListByDictKey,subFrom).then(function(data){
                        if(data.status == "success"){
                            config.dictnameLitem = data.retMessage;//把所有的业务字典绑定给全局
                        }
                    })
                })
                $scope.add = function(item){//保存新增的函数
                    var subFrom  = {};
                    subFrom = item;
                    var res = $rootScope.res.operator_service;//页面所需调用的服务
                    common_service.post(res.addConfig,subFrom).then(function(data){
                        if(data.status == "success"){
                            queryallconfig();//查询所有列表
                            toastr['success']("新增配置成功！");
                            $modalInstance.close();
                        }
                    })

                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }
    //删除配置
    $scope.config.del = function(){
        var getSel = $scope.gridOptions.getSelectedRows();
        if(isNull(getSel) || getSel.length<1){
            toastr['error']("请选则一条数据进行修改！");
        }else{
            var str =  getSel[0];
            if(confirm('确定删除改配置吗')){
                var tis = [];
                for(var i =0;i<getSel.length;i++){
                    var subFrom = {};
                    subFrom =getSel[i];
                    tis.push(subFrom);
                }
                common_service.post(res.deleteConfig,tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除配置成功！");
                        config.queryallconfig();//调用查询所有配置
                    }else{
                        toastr['error']("'删除失败'+'<br/>'+data.retMessage！");
                    }
                })
            }
        }

    }


});
