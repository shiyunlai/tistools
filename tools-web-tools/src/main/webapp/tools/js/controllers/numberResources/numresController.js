/**
 * Created by wangbo on 2017/7/10.
 */

MetronicApp.controller('numres_controller', function ($filter,$rootScope, $scope, $state, $stateParams, filterFilter, $modal,$uibModal, $http, $timeout,$interval,i18nService) {
    //grid表格
    i18nService.setCurrentLang("zh-cn");
    $scope.myData = [
        {'seqKey':'0','seqNo':'0','resEt':'不重置','resetPapams':'隐藏'},
        {'seqKey':'1','seqNo':'1','resEt':'按天重置','resetPapams':'隐藏'},
        {'seqKey':'2','seqNo':'2','resEt':'按周重置','resetPapams':'隐藏'},
        {'seqKey':'3','seqNo':'3','resEt':'自定义重置周期','resetPapams':'隐藏'}
    ];

    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var initdata = function(){
        return $scope.myData;//数据方法
    }
    var com = [
        { field: 'seqKey', displayName: '序号键值'},
        { field: 'seqNo', displayName: '序号数'},
        { field: "resEt", displayName:'重置方式'},
        { field: "resetPapams", displayName:'重置处理参数',visible: false}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,initdata(),filterFilter,com,false,f);

    //重置序号
    $scope.numresReset =function(){
        if($scope.selectRow){
            $scope.selectRow.seqNo = '0'
        }else{
            toastr['error']("请至少选中一条进行重置！");
        }
    }


    //修改序号
    $scope.numresEdit = function(){
        if($scope.selectRow){
            var str =  $scope.selectRow
            openwindow($uibModal, 'views/numberResources/numberEdit.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    $scope.numberFrom =str;
                    $scope.editsflags = true;
                    $scope.add = function(item){//保存新增的函数
                        toastr['success']("保存成功！");
                        $modalInstance.close();
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }else{
            toastr['error']("请至少选中一条进行重置！");
        }
    }

    //查看参数
    $scope.numreslook = function(){
        if($scope.selectRow){
            var str =  $scope.selectRow
            openwindow($uibModal, 'views/numberResources/numberEdit.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    $scope.numberFrom =str;
                    $scope.add = function(item){//保存新增的函数
                        toastr['success']("保存成功！");
                        $modalInstance.close();
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }else{
            toastr['error']("请至少选中一条进行重置！");
        }
    }


});
