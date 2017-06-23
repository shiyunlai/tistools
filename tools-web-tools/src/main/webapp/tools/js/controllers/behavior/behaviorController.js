/**
 * Created by wangbo on 2017/6/10.
 */
angular.module('MetronicApp').controller('behavior_controller', function($rootScope, $scope, $http,i18nService,$modal,filterFilter) {
    /* 行为定义配置*/
    var beha = {};
    $scope.beha = beha;

    /*定义行为类型列表结构*/
    i18nService.setCurrentLang("zh-cn");
    $scope.myData = [{'BHVTYPE_CODE': 's', 'BHVTYPE_NAME': '测试类型'}, {'BHVTYPE_CODE': 'a', 'BHVTYPE_NAME': '测试类型11'}]
    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var initdata = function(){
        return $scope.myData;//数据方法
    }
    var com = [{ field: 'BHVTYPE_CODE', displayName: '行为类型代码'},
        { field: "BHVTYPE_NAME", displayName:'行为类型名称'}

    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
            console.log($scope.selectRow)
            $scope.beha.active = true;
        }else{
            delete $scope.selectRow;//制空
            $scope.beha.active = false;
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,initdata(),filterFilter,com,false,f);

    //新增类型功能
    $scope.beha.beAdd = function(){
        openwindow($modal, 'views/behavior/behavtypeAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                //修改页面代码逻辑
                $scope.add = function(item){//保存新增的函数
                    toastr['success']("新增成功！");
                    $modalInstance.close();
                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }

    //修改类型功能
    $scope.beha.beEdit = function(id){
        if($scope.selectRow){
            openwindow($modal, 'views/behavior/behavtypeAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.id=id;
                    //修改页面代码逻辑
                    $scope.add = function(item){//保存新增的函数
                        toastr['success']("新增成功！");
                        $modalInstance.close();
                    }

                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                })
        }else{
            toastr['error']("请至少选中一条类型进行修改！");
        }
    }
    //删除类型功能
    $scope.beha.besDelete = function(){
        if($scope.selectRow){
            confirm("确定删除选中的类型吗？删除类型将删除该类型下的所有行为")
            toastr['success']("删除成功！");
        }else{
            toastr['error']("请至少选中一条类型进行删除！");
        }
    }

    /*事件行为列表*/
    $scope.myDatas = [{'BHV_CODE': 'TXT002', 'BHV_NAME': '搜索行为'}, {'BHV_CODE': 'TXT003', 'BHV_NAME': '查询行为'}]
    var gridOptions1 = {};
    $scope.gridOptions1 = gridOptions1;
    var initdata1 = function(){
        return $scope.myDatas;//数据方法
    }
    var com1 = [{ field: 'BHV_CODE', displayName: '行为代码'},
        { field: "BHV_NAME", displayName:'行为名称'}

    ];
    var f1 = function(row){
        if(row.isSelected){
            $scope.selectRow1 = row.entity;
            console.log($scope.selectRow1)
        }else{
            delete $scope.selectRow1;//制空
        }
    }
    $scope.gridOptions1 = initgrid($scope,gridOptions1,initdata1(),filterFilter,com1,true,f1);



    //新增行为
    $scope.beha.beacAdd = function () {
        openwindow($modal, 'views/behavior/beactionAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                //修改页面代码逻辑
                $scope.add = function(item){//保存新增的函数
                    toastr['success']("新增成功！");
                    $modalInstance.close();
                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }

    //修改方法
    $scope.beha.beacEdit = function(id){
        var it = $scope.gridOptions1.getSelectedRows();//多选事件
        if(it.length == 1 || $scope.selectRow1){
            openwindow($modal, 'views/behavior/beactionAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.id=id;
                    //修改页面代码逻辑
                    $scope.add = function(item){//保存新增的函数
                        toastr['success']("新增成功！");
                        $modalInstance.close();
                    }

                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                })
        }else{
            toastr['error']("请选择一条数据进行修改！");
        }
    }
    //删除方法
    $scope.beha.beDelete = function(){
        var it = $scope.gridOptions1.getSelectedRows();//多选事件
        if(it.length>0){
            confirm('确定删除选中的行为吗?')
            toastr['success']("删除成功");
        }else{
            toastr['error']("请至少选中一条数据进行删除！");
        }
    }



});