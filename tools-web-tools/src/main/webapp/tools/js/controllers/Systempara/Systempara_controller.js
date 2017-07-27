/**
 * Created by wangbo on 2017/7/26.
 */

angular.module('MetronicApp').controller('systempara_controller', function($rootScope, $scope, $http,menu_service, $timeout,filterFilter,$uibModal,i18nService) {
    var sys = {};
    $scope.sys = sys;
    i18nService.setCurrentLang("zh-cn");
    $scope.myData = [
        {'guidApp':'Y','groupName':'0','keyName':'不重置','valueFrom':'H','value':'1','description':'这里是描述哦'},
        {'guidApp':'N','groupName':'0','keyName':'不重置','valueFrom':'S','value':'1','description':'这里是描述哦'},
        {'guidApp':'Y','groupName':'0','keyName':'不重置','valueFrom':'S','value':'1','description':'这里是描述哦'},
        {'guidApp':'N','groupName':'0','keyName':'不重置','valueFrom':'H','value':'1','description':'这里是描述哦'},
        {'guidApp':'N','groupName':'0','keyName':'不重置','valueFrom':'S','value':'1','description':'这里是描述哦'}
    ];

    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var com = [
        { field: 'guidApp', displayName: '应用系统'},
        { field: 'groupName', displayName: '参数组别'},
        { field: "keyName", displayName:'参数值'},
        { field: "valueFrom", displayName:'值来源'},
        { field: "value", displayName:'参数值'},
        { field: "description", displayName:'参数描述'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f);
    $scope.gridOptions.data = $scope.myData;

    //判断是否展现


    //新增运行参数
    $scope.sys.add = function(){
        openwindow($uibModal, 'views/Systempara/SystemparaAdd.html', 'lg',
            function ($scope, $modalInstance) {
                $scope.valuefrom = function(item){
                   if(item =='H'){
                       $scope.manual = true;
                       $scope.selectes =false;
                   }else{
                       $scope.selectes =true;
                       $scope.manual = false;
                   }
                }
                $scope.add=function(){
                    toastr['success']("新增成功！");

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
                    $scope.sysFrom = getSel[0];
                    //根据来源选择值内容
                        if(getSel[0].valueFrom =='H'){
                            $scope.manual = true;
                            $scope.selectes =false;
                        }else{
                            $scope.selectes =true;
                            $scope.manual = false;
                        }
                    $scope.add=function(){
                        toastr['success']("新增成功！");
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
            toastr['success']("删除成功！");
            $modalInstance.close();
        }else{
            toastr['error']("请至少选中一条！");
        }
    }

});

