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
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);
    $scope.gridOptions.data =  $scope.myData;
    //重置序号
    $scope.numresReset =function(){
        var getSel = $scope.gridOptions.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条进行重置！");
        }else{
            var str = getSel[0];
            console.log(str)
            if(confirm('确定要把序号键为' + str.seqKey+ '的值按照' + str.resE +  '方式重置吗?' )){
                $scope.selectRow.seqNo = '0'
            }
        }

    }


    //修改序号
    $scope.numresEdit = function(){
        var getSel = $scope.gridOptions.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请选则一条数据进行修改！");
        }else{
            var str =  getSel[0]
            openwindow($uibModal, 'views/numberResources/numberEdit.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    $scope.numberFrom =str;
                    $scope.editsflags = true;
                    $scope.add = function(item){//保存新增的函数
                        if(confirm('确定要把'+ str.seqKey+'的序号数修改成'+str.seqNo +'吗？')){
                            toastr['success']("修改成功！");
                            $modalInstance.close();
                        }
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }
    }

    //查看参数
    $scope.numreslook = function(){
        var getSel = $scope.gridOptions.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请选则一条数据进行修改！");
        }else{
            var str =  getSel[0];
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
        }

    }
    //删除序号资源
    $scope.numresDel = function(){
        var getSel = $scope.gridOptions.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请选则一条数据进行修改！");
        }else{
            var str =  getSel[0];
            if(confirm('确定删除该运行参数吗')){
                toastr['success']("删除成功！");
            }
        }

    }


});
