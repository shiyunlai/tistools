/**
 * Created by wangbo on 2017/7/26.
 */

angular.module('MetronicApp').controller('systempara_controller', function($rootScope,$state, $scope, $http,dictonary_service,menu_service,systempara_service,$timeout,filterFilter,$uibModal,i18nService) {
    var sys = {};
    $scope.sys = sys;

    //查询所有应用
    var subFrom  = {};
    menu_service.queryAllAcApp(subFrom).then(function(data){
        if(data.status == "success"){
            var datas = data.retMessage;
            sys.Appall = datas;//所有应用数据，最终要在弹窗中渲染
            sys.init();
        }else{
        }
    })

    //查询所有业务字典
    dictonary_service.querySysDictList(subFrom).then(function(data){
        if(data.status == "success"){
            var datas = data.retMessage;
            sys.dictAll = datas;//所有应用数据，最终要在弹窗中渲染
        }else{
        }
    })


    //查询所有业务字典项
    dictonary_service.queryAllDictItem(subFrom).then(function(data){
        if(data.status == "success"){
            var datas = data.retMessage;
            sys.dictitemAll = datas;//所有应用数据，最终要在弹窗中渲染
        }else{
        }
    })
    
    
    //查看概况
    sys.histroy = function () {
        var getSel = $scope.gridOptions.getSelectedRows();
        var sysGuid = getSel[0].guid;
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请选则一条数据进行修改！");
        }else{
            $state.go("loghistory",{id:sysGuid});//跳转到历史页面
        }
    }

    dictonary_service.querySysDictList(subFrom).then(function(data){
        if(data.status == "success"){
            var datas = data.retMessage;
            sys.dictAll = datas;//所有应用数据，最终要在弹窗中渲染
        }else{
        }
    })


    //查询业务字典对应字典项
    var dictinfo = function(id,$scope){
        var subFrom={};
        subFrom.dictGuid = id;
        dictonary_service.querySysDictItemList(subFrom).then(function(data){
            if(data.status == "success"){
                var datas = data.retMessage;
                $scope.dictinfoAll = datas;//所有应用数据，最终要在弹窗中渲染,传入scope，作用域不同。
            }else{
            }
        })
    }

    i18nService.setCurrentLang("zh-cn");

    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var com = [
        { field: 'guidApp', displayName: '应用系统',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidApp | translateApp) + $root.constant[row.entity.guidApp]}}</div>'},
        { field: 'groupName', displayName: '参数组别'},
        { field: "keyName", displayName:'参数键'},
        { field: "valueFrom", displayName:'值来源', cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.valueFrom | translateDict) + $root.constant[row.entity.valueFrom]}}</div>'},
        { field: "value", displayName:'参数值',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.value | translateDictitem) + $root.constant[row.entity.value]}}</div>'},
        { field: "description", displayName:'参数描述'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);

    //查询所有运行参数
    sys.init=function(){
        var subFrom = {};
        systempara_service.queryRunConfigList(subFrom).then(function (data) {
            var datas = data.retMessage;
            $scope.gridOptions.data = datas;//把获取到的数据复制给表
            $scope.gridOptions.mydefalutData = datas;
            $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
        })
    }

    //新增运行参数
    $scope.sys.add = function(){
        openwindow($uibModal, 'views/Systempara/SystemparaAdd.html', 'lg',
            function ($scope, $modalInstance) {
                $scope.sysList = sys.Appall;//循环渲染，在弹窗中
                $scope.sysdict = sys.dictAll;
                $scope.valuefrom = function(item){
                    if(item =='H'){
                        $scope.manual = true;
                        $scope.selectes =false;
                        $scope.sysFrom.value='';
                    }else{
                        $scope.selectes =true;
                        $scope.manual = false;
                        dictinfo(item,$scope);//查询对应的
                    }
                }
                $scope.add=function(item){
                    var subFrom = {};
                    subFrom=item;
                    systempara_service.createRunConfig(subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']("新增成功！");
                            sys.init();//重新查询列表
                            $modalInstance.close();
                        }else{
                            toastr['error']('新增失败'+'<br/>'+data.retMessage);
                        }
                    })
                }
                $scope.cancel=function(){
                    $modalInstance.close();
                }
            }
        )
    }

    //修改/查看运行参数
    $scope.sys.edit = function(id){
        var getSel = $scope.gridOptions.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请选则一条数据进行修改！");
        }else{
            openwindow($uibModal, 'views/Systempara/SystemparaAdd.html', 'lg',
                function ($scope, $modalInstance) {
                    var ids = id;
                    $scope.id = ids;
                    var copydatas = angular.copy(getSel[0])
                    $scope.sysFrom = copydatas;
                    $timeout(function () {
                        $(".sysfrom").select2("val",getSel[0].valueFrom);//渲染表格数据
                    },50);
                    $scope.sysList = sys.Appall;//循环渲染，在弹窗中
                    $scope.sysdict = sys.dictAll;
                    //根据来源选择值内容
                    if(getSel[0].valueFrom =='H'){
                        $scope.manual = true;
                        $scope.selectes =false;
                    }else{
                        $scope.selectes =true;
                        $scope.manual = false;
                        dictinfo(getSel[0].valueFrom,$scope);//查询对应的
                    }
                    //修改展现方法
                    $scope.valuefrom = function(item){
                        var dictid = item;
                        if(item =='H'){
                            $scope.manual = true;
                            $scope.selectes =false;
                            $scope.sysFrom.value='';
                        }else{
                            $scope.selectes =true;
                            $scope.manual = false;
                            dictinfo(item,$scope);//查询对应的
                        }
                    }

                    $scope.add=function(item){
                        var subFrom = {};
                        subFrom=item;
                        subFrom.guid=getSel[0].guid;
                        systempara_service.editRunConfig(subFrom).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("修改成功！");
                                sys.init();//重新查询列表
                                $modalInstance.close();
                            }else{
                                toastr['error']('新增失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    $scope.cancel=function(){
                        $modalInstance.close();
                    }
                }
            )
        }
    }


    //删除运行参数,可批量删除
    $scope.sys.del = function(){
        var getSel = $scope.gridOptions.getSelectedRows();
        if(getSel.length>0){
            if(confirm('确定删除该运行参数吗')){
                var subFrom = {};
                subFrom.cfgGuid=getSel[0].guid;
                systempara_service.deleteRunConfig(subFrom).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除成功！");
                        sys.init();//重新查询列表
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }else{
            toastr['error']("请至少选中一条！");
        }
    }

});

