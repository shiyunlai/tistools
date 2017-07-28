/**
 * Created by wangbo on 2017/6/23.
 */

//操作员个性化配置
angular.module('MetronicApp').controller('operconfig_controller', function($rootScope, $scope ,$modal,$http,i18nService, $timeout,filterFilter,$uibModal,uiGridConstants) {
    var operconfig = {};
    $scope.operconfig = operconfig;
    i18nService.setCurrentLang("zh-cn");
    $scope.myData = [
        {'USER_ID':'001000','OPERATOR_NAME':'王五','APP_CODE':'ABFRAME','APP_NAME':'应用框架模型1','CONFIG_TYPE': "测试类型", 'CONFIG_NAME': '测试','CONFIG_VALUE':'一','ISVALID':"是"},
        {'USER_ID':'001001','OPERATOR_NAME':'李四','APP_CODE':'ABFRAME1','APP_NAME':'应用框架模型2','CONFIG_TYPE': "练习类型", 'CONFIG_NAME': '练习','CONFIG_VALUE':'二','ISVALID':"否"},
        {'USER_ID':'001002','OPERATOR_NAME':'张三','APP_CODE':'ABFRAME2','APP_NAME':'应用框架模型3','CONFIG_TYPE': "保护类型", 'CONFIG_NAME': '配置','CONFIG_VALUE':'三','ISVALID':"是"}
    ];
    //ui-grid 具体配置

    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    //操作员名称，代码  应用系统名称 代码
    var com = [
        { field: "USER_ID", displayName:'登录用户名'},
        { field: "OPERATOR_NAME", displayName:'操作用姓名'},
        { field: "APP_CODE", displayName:'应用代码'},
        { field: "APP_NAME", displayName:'应用名称'},
        { field: 'CONFIG_TYPE', displayName: '配置类型'},
        { field: "CONFIG_NAME", displayName:'配置名'},
        { field: "CONFIG_VALUE", displayName:'配置值'},
        { field: "ISVALID", displayName:'是否启用',
            //配置搜索下拉框
            filter:{
                //term: '0',//默认搜索那项
                type: uiGridConstants.filter.SELECT,
                selectOptions: [{ value: '是', label: '是' }, { value: '否', label: '否' }]
            }}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
            console.log($scope.selectRow)
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);
    $scope.gridOptions.data = $scope.myData;
    //修改个性化配置
    $scope.opconfigEdit = function(){
       if($scope.selectRow){
           openwindow($modal, 'views/operator/opconfiEdit.html', 'lg',//弹出页面
               function ($scope, $modalInstance) {
                   $scope.add = function(item){
                       //新增代码
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



//操作员身份
angular.module('MetronicApp').controller('operstatus_controller', function($rootScope, $scope ,$modal,$http,operator_service,i18nService, $timeout,filterFilter,$uibModal,uiGridConstants) {
    //操作员身份控制器

    var opensf = {};
    $scope.opensf = opensf;
    i18nService.setCurrentLang("zh-cn");


    //查询事件
    $scope.opensf.search = function(item){
        var subFrom = {};
        opensf.info = item;//绑定信息
        subFrom.userId = item.userid;
        subFrom.operatorName = item.username;
        operator_service.queryAllOperatorIdentity(subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
                opensf.guidperator = datas[0].guidOperator;
                $scope.opensf.identity = true;
                opensf.inittx(subFrom.userId,subFrom.operatorName);
            }else{
                toastr['error'](data.retCode,data.retMessage+"初始化失败!");
            }
        })

    }

    opensf.inittx = function(userId,operatorName){//查询操作员下所有身份
        var subFrom = {};
        subFrom.userId = userId;
        subFrom.operatorName = operatorName;
        operator_service.queryAllOperatorIdentity(subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
                $scope.gridOptions.data =  datas;
                $scope.gridOptions.mydefalutData = datas;
                $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
            }else{
                toastr['error'](data.retCode,data.retMessage+"初始化失败!");
            }
        })
    }



    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    //操作员名称，代码  应用系统名称 代码
    var com = [
        { field: "identityName", displayName:'身份名称'},
        { field: "identityFlag", displayName:'默认身份标志'},
        { field: "seqNo", displayName:'显示顺序'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
            console.log($scope.selectRow)
            $scope.opensf.idenright = true;
        }else{
            delete $scope.selectRow;//制空
            $scope.opensf.idenright = false;
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);

    //新增身份
    $scope.idenAdd = function(){
        var info = $scope.opensf.info;//用户身份
        var guidoperator = opensf.guidperator;
        openwindow($modal, 'views/operator/identAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){
                    var subFrom = {};
                    $scope.subFrom = subFrom;
                    subFrom = item;
                    subFrom.guidOperator = guidoperator;
                    subFrom.identityFlag = 'N';//默认不是默认身份
                    console.log(subFrom)
                    operator_service.createOperatorIdentity(subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']("新增成功！");
                            opensf.inittx(info.userid,info.username);//测试查询
                            $modalInstance.close();
                        }else{
                            toastr['error']('新增失败'+'<br/>'+data.retMessage);
                        }
                    })
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            })
    }

    //编辑身份
    $scope.idenEdit = function(id){
        var info = $scope.opensf.info;
        if($scope.selectRow){
            var datas = $scope.selectRow;
            openwindow($modal, 'views/operator/identAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    if(!isNull(datas)){
                        $scope.identFrom = angular.copy(datas);
                    }
                    $scope.id = id;
                    $scope.add = function(item){
                        var subFrom = {};
                        subFrom = item;
                        operator_service.editOperatorIdentity(subFrom).then(function(data){
                            var datas = data.retMessage;
                            if(data.status == "success"){
                                toastr['success']("修改成功！");
                                opensf.inittx(info.userid,info.username);//测试查询
                                $modalInstance.close();
                            }else{
                                toastr['error'](data.retCode,data.retMessage+"初始化失败!");
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };

                })
        }else{
            toastr['error']("请至少选中一条身份进行修改！");
        }
    }

    //删除身份
    $scope.idenDel =function(){
        if($scope.selectRow) {
            var identityGuid = $scope.selectRow.guid;
            console.log(identityGuid);
            if (confirm("确定删除选中的身份吗？删除身份将删除该身份下的所有权限")) {
                var subFrom = {};
                subFrom.identityGuid = identityGuid;
                operator_service.deleteOperatorIdentity(subFrom).then(function (data) {
                    if (data.status == "success") {
                        toastr['success']("修改成功！");
                        opensf.inittx(info.userid, info.username);//测试查询
                        $modalInstance.close();
                    } else {
                        toastr['error'](data.retCode, data.retMessage + "删除失败!");
                    }
                })
            } else {
                toastr['error']("请至少选中一条身份信息进行删除！");
            }
        }
    }


    //选定身份
    $scope.idenSave = function(){
        if($scope.selectRow){
           if(confirm('确定选用此条身份记录？')){
               toastr['success']("保存成功！");
           }else{
           }
        }else{
            toastr['error']("请至少选中一条身份！");
        }
    }


    /* 右侧身份对应权限*/

    $scope.myDatas= [
        {'AC_RESOURCETYPE':'组织类型','IDENTITY_NAME':'身份一','GUID_AC_RESOURCE':'测试'},
        {'AC_RESOURCETYPE':'职务类型','IDENTITY_NAME':'身份二','GUID_AC_RESOURCE':'经理'},
        {'AC_RESOURCETYPE':'岗位类型','IDENTITY_NAME':'身份三','GUID_AC_RESOURCE':'上海银行'},
    ];

    var gridOptions1 = {};
    $scope.gridOptions1 = gridOptions1;
    //操作员名称，代码  应用系统名称 代码
    var com1 = [
        { field: "AC_RESOURCETYPE", displayName:'身份类型'},
        { field: "IDENTITY_NAME", displayName:'身份名称'},
        { field: "GUID_AC_RESOURCE", displayName:'资源名称'}
    ];
    var f1 = function(row){
        if(row.isSelected){
            $scope.selectRow1 = row.entity;
            console.log($scope.selectRow1)
        }else{
            delete $scope.selectRow1;//制空
        }
    }
    $scope.gridOptions1 = initgrid($scope,gridOptions1,filterFilter,com1,false,f1);
    $scope.gridOptions1.data = $scope.myDatas;
    //资源身份新增
    $scope.identypeAdd = function(){
        openwindow($modal, 'views/operator/identtypeAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){
                    //新增代码
                    toastr['success']("保存成功！");
                    $modalInstance.close();
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            })
    }

    //资源身份修改
    $scope.identypeDel = function(){
        if($scope.selectRow1){
            toastr['success']("删除成功！");
        }else{
            toastr['error']("请至少选中一条进行删除！");
        }

    }
});
