/**
 * Created by wangbo on 2017/7/10.
 */

MetronicApp.controller('numres_controller', function ($filter, $scope, $state,numres_service, $stateParams, filterFilter, $modal,$uibModal, $http, $timeout,$interval,i18nService) {

    var numres = {};
    $scope.numres = numres;

    //查询所有系统资源
    var subFrom= {} ;
    numres_service.querySeqnoList(subFrom).then(function (data) {
        if(data.status == "success"){
            var datas = data.retMessage;
            $scope.gridOptions.data =  datas;
            $scope.gridOptions.mydefalutData = datas;
            $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
        }else{
            toastr['error']('初始化查询失败'+'<br/>'+data.retMessage);
        }
    })

    //封装
    numres.initt = function(){//查询服务公用方法
        var subFrom= {} ;
        numres_service.querySeqnoList(subFrom).then(function (data) {
            if(data.status == "success"){
                var datas = data.retMessage;
                $scope.gridOptions.data =  datas;
                $scope.gridOptions.mydefalutData = datas;
                $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
            }else{
                toastr['error']('初始化查询失败'+'<br/>'+data.retMessage);
            }
        })
    }

    //grid表格
    i18nService.setCurrentLang("zh-cn");
    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var com = [
        { field: 'seqName', displayName: '序号资源名称'},
        { field: 'seqKey', displayName: '序号键值'},
        { field: 'seqNo', displayName: '当前序号值'},
        { field: "reset", displayName:'重置方式',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.reset | translateConstants :\'DICT_SYS_RESET\') + $root.constant[\'DICT_SYS_RESET-\'+row.entity.reset]}}</div>'},
        { field: "resetParams", displayName:'重置处理参数',visible: false}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);


    //重置序号
    $scope.numresReset =function(){
        var getSel = $scope.gridOptions.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条进行重置！");
        }else{
            var str = getSel[0];
            if(str.reset == 'E'){
                  str.config = '不重置'
            }else if(str.reset == 'D'){
                str.config = '按天重置'
            }else if(str.reset == 'W'){
                str.config = '按周重置'
            }else if(str.reset == 'C'){
                str.config = '自定义重置'
            }
            if(confirm('确定要把序号键为' + str.seqKey+ '的值按照' + str.config +  '方式重置吗?' )){
                $scope.selectRow.seqNo = '0'
            }
        }
    }


    //修改序号
    $scope.numresEdit = function(){
        var getSel = $scope.gridOptions.getSelectedRows();
        console.log(getSel);
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请选则一条数据进行修改！");
        }else{
            var str =  getSel[0]
            openwindow($uibModal, 'views/numberResources/numberEdit.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    if(!isNull(str)){//如果参数不为空，则就回显
                        console.log(str);
                        $scope.numberFrom = angular.copy(str);
                    }
                    $scope.editsflag = true;
                    $scope.add = function(item){//保存新增的函数
                        //if(confirm('确定要把'+ str.seqKey+'的序号数修改成'+str.seqNo +'吗？')){
                        if(confirm('确定要修改该数据吗？')){
                            var subFrom = {};
                            subFrom = item;
                            numres_service.editSeqno(subFrom).then(function (data) {
                                if(data.status == "success"){
                                    toastr['success']("修改成功！");
                                    numres.initt();//重新查询
                                    $modalInstance.close();
                                }else{
                                    toastr['error']('修改失败'+'<br/>'+data.retMessage);
                                }
                            })
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
                    var copynums = angular.copy(str)
                    $scope.numberFrom =copynums;
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
            if(confirm('确定删除'+ str.seqKey+'运行参数吗')){
                var subFrom = {};
                subFrom.seqKey = str.seqKey;
                numres_service.deleteSeqno(subFrom).then(function (data) {
                    if(data.status == "success"){
                        toastr['success']("删除成功！");
                        numres.initt();//重新查询列表
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }

                })

            }
        }

    }


});
