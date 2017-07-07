/**
 * Created by wangbo on 2017/6/30.
 */

angular.module('MetronicApp').controller('shortcutMenu_controller', function($rootScope, $scope, $http, $timeout,i18nService,filterFilter,uiGridConstants,$modal) {
    var shortcu = {};
    $scope.shortcu = shortcu;
    i18nService.setCurrentLang("zh-cn");
    $scope.myData = [
        {'funcName':'测试功能','funcgroupName':'测试功能组','appName':'测试应用','orderNo':1,"imagePath":'默认'},
        {'funcName':'测试功能1','funcgroupName':'测试功能组1','appName':'测试应用1','orderNo':2,"imagePath":'默认'},
        {'funcName':'测试功能2','funcgroupName':'测试功能组2','appName':'测试应用2','orderNo':3,"imagePath":'默认'},
        {'funcName':'测试功能3','funcgroupName':'测试功能组3','appName':'测试应用3','orderNo':4,"imagePath":'默认'},
    ];
    //ui-grid 具体配置

    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var initdata = function(){
        return $scope.myData;//数据方法
    }
    //操作员名称，代码  应用系统名称 代码
    var com = [
        { field: "funcName", displayName:'功能名称'},
        { field: "funcgroupName", displayName:'功能组名称'},
        { field: "appName", displayName:'应用名称'},
        { field: "orderNo", displayName:'排列顺序'},
        { field: 'imagePath', displayName: '快捷菜单图片路径'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
            console.log($scope.selectRow)
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,initdata(),filterFilter,com,false,f);

    //修改个性化配置
    $scope.shortcuEdit = function(){
        if($scope.selectRow){
            var item = $scope.selectRow;
            openwindow($modal, 'views/shortcutMenu/shortcutAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.shortcuFrom = item;
                    $scope.add = function(item){
                        //修改
                        toastr['success']("保存成功！");
                        $modalInstance.close();
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };

                })
        }else{
            toastr['error']("请至少选中一条！");
        }
    }


});