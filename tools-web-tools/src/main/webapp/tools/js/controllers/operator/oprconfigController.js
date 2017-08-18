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
angular.module('MetronicApp').controller('operstatus_controller', function($rootScope, $scope ,$modal,$http,operator_service,dictonary_service,common_service,i18nService, role_service,$timeout,filterFilter,$uibModal,uiGridConstants) {
    //操作员身份控制器
    var opensf = {};
    $scope.opensf = opensf;
    i18nService.setCurrentLang("zh-cn");
    var res = $rootScope.res.operator_service;//页面所需调用的服务
    //查询业务字典
    var tits = {};
    tits.dictKey='DICT_AC_RESOURCETYPE';
    dictKey($rootScope,tits,dictonary_service);

    //查询事件
    $scope.opensf.search = function(item){
        var subFrom = {};
        opensf.info = item;//绑定信息
        subFrom.userId = item.userid;
        subFrom.operatorName = item.username;
        //根据userid查询guid接口
        var ret = $rootScope.res.operator_service;
        var  serFrom = {}
        serFrom.userId = item.userid;
        common_service.post(ret.queryOperatorByUserId,serFrom).then(function(data){
            var datas = data.retMessage;
            opensf.operatorguid = datas.guid;//拿到操作员对应的guid
        })
        operator_service.queryAllOperatorIdentity(subFrom).then(function(data){
            var datas = data.retMessage;
            $scope.opensf.operguid =  opensf.operatorguid;//操作员的guid
            if(data.status == "success"){
                opensf.guidperator =  opensf.operatorguid;
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
            $scope.opensf.idenright = true;
            //$scope.opensf.openguid = $scope.selectRow.guid;//绑定对应身份id给全局
            //查询身份对应资源
            var subFrom = {};
            subFrom.identityGuid = $scope.selectRow.guid
            common_service.post(res.queryAllOperatorIdentityRes,subFrom).then(function(data){
                if(data.status == "success"){
                    var datas = data.retMessage;
                    $scope.gridOptions1.data = datas
                }

            })
        }else{
            delete $scope.selectRow;//制空
            $scope.opensf.idenright = false;
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);

    //新增身份
    $scope.idenAdd = function(){
        var info = $scope.opensf.info;//用户信息
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
            var info = $scope.opensf.info;
            var identityGuid = $scope.selectRow.guid;
            if (confirm("确定删除选中的身份吗？删除身份将删除该身份下的所有权限")) {
                var subFrom = {};
                subFrom.identityGuid = identityGuid;
                operator_service.deleteOperatorIdentity(subFrom).then(function (data) {
                    console.log(data)
                    if (data.status == "success") {
                        toastr['success']("修改成功！");
                        opensf.inittx(info.userid, info.username);//测试查询
                    } else {
                        toastr['error'](data.retCode, data.retMessage + "删除失败!");
                    }
                })
            } else {
                toastr['error']("请至少选中一条身份信息进行删除！");
            }
        }
    }


    //设置默认身份
    $scope.idenSave = function(){
        if($scope.selectRow){
            var info = $scope.opensf.info;
            var identityGuid = $scope.selectRow.guid;
           if(confirm('是否把此身份设置成默认身份？')){
               var subFrom = {};
               subFrom.identityGuid = identityGuid;
               operator_service.setDefaultOperatorIdentity(subFrom).then(function (data) {
                   console.log(data);
                   if (data.status == "success") {
                       toastr['success']("修改成功！");
                       opensf.inittx(info.userid, info.username);//测试查询
                   } else {
                       toastr['error'](data.retCode, data.retMessage + "删除失败!");
                   }
               })
           }else{
           }
        }else{
            toastr['error']("请至少选中一条身份！");
        }
    }

    //查询身份对应资源类型列表
    opensf.inittx1 = function(opersguid){
        var subFrom = {};
        subFrom.identityGuid =opersguid;
        common_service.post(res.queryAllOperatorIdentityRes,subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
                $scope.gridOptions1.data = datas
            }else{
            }
        })
    }


    /* 右侧身份对应权限*/
/*    $scope.myDatas= [
        {'acResourcetype':'组织类型','roleName':'身份一'},
        {'acResourcetype':'职务类型','roleName':'身份二'}
    ];*/

    var gridOptions1 = {};
    $scope.gridOptions1 = gridOptions1;
    //操作员名称，代码  应用系统名称 代码
    var com1 = [
        { field: "acResourcetype", displayName:'资源类型'},
        { field: "roleName", displayName:'角色名称'},
    ];
    var f1 = function(row){
        if(row.isSelected){
            $scope.selectRow1 = row.entity;
        }else{
            delete $scope.selectRow1;//制空
        }
    }

    $scope.gridOptions1 = initgrid($scope,gridOptions1,filterFilter,com1,false,f1);

    //资源身份新增
    $scope.identypeAdd = function(){
        var opersguid = $scope.selectRow.guid;//身份对应guid
        openwindow($modal, 'views/operator/identtypeAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                var gridOptions = {};
                $scope.gridOptions = gridOptions;
                var com = [
                    { field: "positionName", displayName:'功能名称'}
                ];
                //自定义点击事件
                var f1 = function(row){
                    if(row.isSelected){
                        $scope.selectRow3 = row.entity;
                    }
                    else{
                        delete $scope.selectRow3;//制空
                    }
                }
                $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f1);
                //不同类型，查询不同数据，表头展现不同
                $scope.$watch('identypeFrom.idtnyype',function(newValue,oldValue){
                    if(newValue==undefined){
                        $scope.operflage = false;
                    }else{
                        $scope.operflage = true;
                    }
                    var subFrom  = {};
                    //根据不同的类型去请求不同的数据，然后赋值给表格
                    if(newValue =='role'){
                        var com = [
                            { field: "roleName", displayName:'角色名称'}
                        ];
                        //查询操作员对应的角色
                        var  subFrom = {};
                        subFrom.operatorGuid =opensf.operguid;
                        subFrom.resType = newValue;
                        console.log(subFrom)
                        common_service.post(res.queryRoleInOperatorByResType,subFrom).then(function(data){
                            console.log(data);
                            var datas  = data.retMessage;
                            if(data.status == "success"){
                                $scope.gridOptions.data = datas;
                            }
                        })
                        $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f1);
                        //导入
                    }else if(newValue=='function'){
                        var com = [
                            { field: "funcName", displayName:'功能名称'}
                        ];
                        //查询操作员对应功能
                        var  subFrom = {};
                        subFrom.operatorGuid =opensf.operguid;
                        subFrom.resType = newValue;
                        common_service.post(res.queryRoleInOperatorByResType,subFrom).then(function(data){
                            var datas  = data.retMessage;
                            if(data.status == "success"){
                                $scope.gridOptions.data = datas;
                            }
                        })
                        $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f1);
                    }else if(newValue=='position'){
                        var com = [
                            { field: "roleName", displayName:'岗位名称'}
                        ];
                        //查询操作员对应岗位
                        var  subFrom = {};
                        subFrom.operatorGuid =opensf.operguid;
                        subFrom.resType = newValue;
                        common_service.post(res.queryRoleInOperatorByResType,subFrom).then(function(data){
                            var datas  = data.retMessage;
                            if(data.status == "success"){
                                $scope.gridOptions.data = datas;
                            }
                        })
                        $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f1);
                    }else if(newValue=='duty'){
                        var com = [
                            { field: "roleName", displayName:'职务名称'}
                        ];
                        //查询操作员对应职务
                        var  subFrom = {};
                        subFrom.operatorGuid =opensf.operguid;
                        subFrom.resType = newValue;
                        common_service.post(res.queryRoleInOperatorByResType,subFrom).then(function(data){
                            var datas  = data.retMessage;
                            if(data.status == "success"){
                                $scope.gridOptions.data = datas;
                            }
                        })
                        $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f1);

                    }else if(newValue=='workgroup'){
                        var com = [
                            { field: "roleName", displayName:'工作组名称'}
                        ];
                        //查询操作员对应工作组
                        var  subFrom = {};
                        subFrom.operatorGuid =opensf.operguid;
                        subFrom.resType = newValue;
                        common_service.post(res.queryRoleInOperatorByResType,subFrom).then(function(data){
                            var datas  = data.retMessage;
                            if(data.status == "success"){
                                $scope.gridOptions.data = datas;
                            }
                        })
                        $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f1);
                    }else if(newValue=='organization'){
                        var com = [
                            { field: "roleName", displayName:'机构名称'}
                        ];
                        //查询操作员对应机构
                        var  subFrom = {};
                        subFrom.operatorGuid =opensf.operguid;
                        subFrom.resType = newValue;
                        common_service.post(res.queryRoleInOperatorByResType,subFrom).then(function(data){
                            console.log(data);
                            var datas  = data.retMessage;
                            if(data.status == "success"){
                                $scope.gridOptions.data = datas;
                            }
                        })
                        $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f1);
                    }

                    $scope.importAdd = function(item){//导入资源代码
                        var getSel = $scope.gridOptions.getSelectedRows();
                        if(isNull(getSel) || getSel.length>1){
                            toastr['error']("请选则一条数据进行添加！");
                        }else{
                            var  subFrom = {};
                            subFrom.guidIdentity = opersguid; //身份guid
                            subFrom.acResourcetype =newValue;//资源类型
                            subFrom.guidAcResource =getSel[0].guid;//资源guid
                            common_service.post(res.createOperatorIdentityres,subFrom).then(function(data){
                                if(data.status == "success"){
                                    var datas  = data.retMessage;
                                    $scope.gridOptions.data = datas;
                                    //新增代码
                                    toastr['success']("保存成功！");
                                    opensf.inittx1(opersguid);//重新查询列表
                                    $modalInstance.close();
                                }
                            })
                        }

                    }
                });
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            })
    }

    //资源身份删除
    $scope.identypeDel = function(){
        var  opersguid = $scope.selectRow.guid;//身份对应guid
        var getSel = $scope.gridOptions1.getSelectedRows();
        console.log(getSel)
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请选则一条数据进行删除！");
        }else{
            //调用删除接口
            if(confirm("您确认删除选中的资源吗？")){
                var subFrom = {};
                subFrom.identityresGuid = opersguid;
                subFrom.resGuid = getSel[0].guidAcResource;//资源guid
                common_service.post(res.deleteOperatorIdentityres,subFrom).then(function(data){
                    if(data.status == "success"){
                        var datas  = data.retMessage;
                        toastr['success']("删除成功！");
                        opensf.inittx1(opersguid);//重新查询列表
                    }
                })
            }

        }
    }
});
