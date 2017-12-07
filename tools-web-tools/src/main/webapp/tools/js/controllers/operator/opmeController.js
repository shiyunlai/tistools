/**
 * Created by wangbo on 2017/6/20.
 */
/*操作员管理控制器*/
MetronicApp.controller('opmanage_controller', function ($rootScope, $scope, $state, $stateParams,$filter, filterFilter,operator_service,dictonary_service,common_service, $modal,$uibModal, $http, $timeout,$interval,i18nService) {
    //grid表格
    i18nService.setCurrentLang("zh-cn");
    var res = $rootScope.res.operator_service;//操作员请求所需服务
    //查询操作员列表
    var operman ={};
    $scope.operman = operman;
    operman.queryAll = function(){
        //查询所有操作员列表
        var subFrom = {};
        operator_service.queryAllOperator(subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
                var datas = $filter('Arraysort')(data.retMessage);//调用管道排序
                console.log(datas);
                $scope.gridOptions.data =  datas;
                $scope.gridOptions.mydefalutData = datas;
                $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
            }else{
                toastr['error']('查询失败'+'<br/>'+data.retMessage);
            }
        })
    }
    operman.queryAll();


    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var com = [{ field: 'operatorName', displayName: '操作员姓名'},
        { field: "userId", displayName:'登录用户名'},
        { field: "authMode",
            displayName:'认证模式',
            cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.authMode | translateConstants :\'DICT_AC_AUTHMODE\') + $root.constant[\'DICT_AC_AUTHMODE-\'+row.entity.authMode]}}</div>',
            /*filter:{
                type: uiGridConstants.filter.SELECT,
                selectOptions: [{ value: 'password', label: '密码'},{ value: 'dynpassword', label: '动态密码'},{ value: 'captcha', label: '验证码'},{ value: 'ldap', label: 'LDAP认证'},{ value: 'fingerprint', label: '指纹'},{ value: 'fingerprintcard', label: '指纹卡'}]
            }*/
        },
        { field: "operatorStatus",displayName:'操作员状态',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.operatorStatus | translateConstants :\'DICT_AC_OPERATOR_STATUS\') + $root.constant[\'DICT_AC_OPERATOR_STATUS-\'+row.entity.operatorStatus]}}</div>',
            selectOptions: [{ value: 'password', label: '密码'},{ value: 'dynpassword', label: '动态密码'},{ value: 'captcha', label: '验证码'},{ value: 'ldap', label: 'LDAP认证'},{ value: 'fingerprint', label: '指纹'},{ value: 'fingerprintcard', label: '指纹卡'}]
        },
        { field: "menuType",displayName:'菜单风格',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.menuType | translateConstants :\'DICT_AC_MENUTYPE\') + $root.constant[\'DICT_AC_MENUTYPE-\'+row.entity.menuType]}}</div>'},
        { field: "lockLimit",displayName:'锁定次数限制'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
            operman.info = $scope.selectRow.userId;//传入userid.进行权限的分配
            $scope.Status = row.entity.operatorStatus;//把状态绑定
            $scope.isfalse = false;
            $scope.copyfunEdit = angular.copy(row.entity);//修改之前的值
            //操作员状态改变值
            $scope.checkstatus = function (key,array) {
                //封装方法，把几种状态放在数组中，拿到此时的状态，匹配 靠返回值来完成是否可以选择
                for(var i = 0;i<array.length;i++){
                    if(key === array[i]){
                        return true;
                    }
                    //不能在这里写else，否则第一个没有就直接进入else里了，return false应该写在for循环后面，循环玩了都没有才return false
                }
                return false;
            }
        }else{
            delete $scope.selectRow;//制空
            $scope.isfalse = true;
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);

    //查看概况
    operman.histroy = function () {
        if($scope.selectRow) {
            var items = $scope.selectRow.guid;
            $state.go("loghistory",{id:items});//跳转到历史页面
        }else{
            toastr['error']("请至少选择一条数据进行查看！");
        }
    }
    //查询业务字典
    var tits = {};
    tits.dictKey='DICT_AC_OPERATOR_STATUS';
    dictKey($rootScope,tits,dictonary_service);

    //新增操作员代码
    $scope.operatAdd = function(item){
        openwindow($uibModal, 'views/operator/operatorAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                var operatFrom = {};
                $scope.operatFrom = operatFrom;
                $scope.operatFrom.lockLimit = 5;
                $scope.operatFrom.operatorStatus = 'stop';//默认停用
                // $scope.operatFrom.operatorStatus = 'login';//默认停用
                $scope.add = function(item){//保存新增的函数
                    var subFrom = {};
                    $scope.subFrom = subFrom;
                    subFrom.data = item;
                    common_service.post(res.createOperator,subFrom).then(function(data){
                        if(data.status == 'success'){
                            toastr['success']( "新增操作员成功！");
                            operman.queryAll();
                            $modalInstance.close();
                        }else{
                            toastr['error']('新增失败'+'<br/>'+data.retMessage);
                        }
                    })
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }
    //修改操作员代码
    $scope.operatEdit = function(id){
        if($scope.selectRow){
            var items = $scope.selectRow;
            var changesDate = $scope.copyfunEdit;//修改之前的值
            openwindow($uibModal, 'views/operator/operatorAdd.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    if(!isNull(items)){//如果参数不为空，则就回显
                        items.invalDate = moment(items.invalDate).format('YYYY-MM-DD');
                        items.startDate = moment(items.startDate).format('YYYY-MM-DD');
                        items.endDate = moment(items.endDate).format('YYYY-MM-DD');
                        $scope.operatFrom = angular.copy(items);
                    }
                    $scope.id = id;//获取到id，用来判断是否编辑，因为scope作用域不同，所以不同
                    $scope.add = function(item){//保存新增的函数
                        var  changeData = {};//创建对象
                        for(var key in changesDate){//循环最初的数据?
                            if(changesDate[key] !== item[key]){
                                changeData[key]= changesDate[key];
                            }
                        }
                        var subFrom = {};
                        $scope.subFrom = subFrom;
                        subFrom.data = item;
                        subFrom.changeData = changeData;
                        operator_service.editOperator(subFrom).then(function(data){
                            if(data.status == "success"){
                                toastr['success']( "修改成功！");
                                operman.queryAll();
                                $modalInstance.close();
                            }else{
                                toastr['error']('修改失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }else{
            toastr['error']("请至少选中一个操作员进行操作！");
        }
    }

    //操作员个性化配置
    $scope.operatsetqx = function (id) {
        if($scope.selectRow){
            $state.go("operatsetqx",{id:id})
        }else{
            toastr['error']("请至少选中一个操作员进行权限的分配！");
        }
    }


    //操作员配置功能行为权限
    $scope.funconfig = function (id) {
        if($scope.selectRow){
            var opertis = {"userid":$scope.selectRow.userId,"operguid":$scope.selectRow.guid}
            var jsonObj= angular.toJson(opertis);//传对象，必须转成json格式传入
            $state.go("permission",{'id':jsonObj})
        }else{
            toastr['error']("请至少选中一个操作员进行权限的分配！");
        }
    }

    //启用操作员状态
    $scope.operman.login = function (item) {
        var res = $rootScope.res.operator_service;//页面所需调用的服务
        var dats = $scope.gridOptions.getSelectedRows();
        var subFrom  = {};
        subFrom.data = {};
        subFrom.data.userId = dats[0].userId;
        subFrom.data.status = 'logout';
        common_service.post(res.changeOperatorStatus,subFrom).then(function(data){
            if(data.status == 'success'){
                toastr['success']( "启用成功！");
                operman.queryAll();
            }else{
                toastr['error']('新增失败'+'<br/>'+data.retMessage);
            }
        })
    }

    //删除操作员状态
    $scope.operman.del = function (item) {
        var res = $rootScope.res.operator_service;//页面所需调用的服务
        var dats = $scope.gridOptions.getSelectedRows();
        var subFrom  = {};
        subFrom.data = {};
        subFrom.data.operatorGuid = dats[0].guid;
        common_service.post(res.deleteOperator,subFrom).then(function(data){
            if(data.status == 'success'){
                toastr['success']( "删除成功！");
                operman.queryAll();
            }else{
                toastr['error']('删除失败'+'<br/>'+data.retMessage);
            }
        })
    }

    //解锁操作员状态
    $scope.operman.logout = function (item) {
        var res = $rootScope.res.operator_service;//页面所需调用的服务
        var dats = $scope.gridOptions.getSelectedRows();
        var subFrom  = {};
        subFrom.data = {};
        subFrom.data.userId = dats[0].userId;
        subFrom.data.status = 'logout';
        common_service.post(res.changeOperatorStatus,subFrom).then(function(data){
            if(data.status == 'success'){
                toastr['success']( "解锁成功！");
                operman.queryAll();
            }else{
                toastr['error']('解锁失败'+'<br/>'+data.retMessage);
            }
        })
    }
    
    //注销操作员状态
    $scope.operman.clear = function (item) {
        var res = $rootScope.res.operator_service;//页面所需调用的服务
        var dats = $scope.gridOptions.getSelectedRows();
        var subFrom = {};
        subFrom.data = {};
        subFrom.data.userId = dats[0].userId;
        subFrom.data.status = 'clear';
        common_service.post(res.changeOperatorStatus, subFrom).then(function (data) {
            if(data.status == 'success'){
                toastr['success']( "注销成功！");
                operman.queryAll();
            }else{
                toastr['error']('注销失败'+'<br/>'+data.retMessage);
            }
        })
    }
    
    //清除单挑操作员缓存
    $scope.clearCache = function () {
        var getSel = $scope.gridOptions.getSelectedRows();
        console.log(getSel)
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选择一个操作员进行清除！");
        }else{
            var subFrom = {};
            subFrom.userId = getSel[0].userId;
            common_service.post(res.refreshOperator, {data:subFrom}).then(function (data) {
                console.log(data)
                if(data.status == 'success'){
                    toastr['success']( "清除缓存成功！");
                    operman.queryAll();
                }else{
                    toastr['error']('清除缓存失败'+'<br/>'+data.retMessage);
                }
            })
        }
    }
    //清除所有操作员缓存
    $scope.clearCacheAll = function () {
        if(confirm("您确认要删除所有操作员的缓存吗")){
            var subFrom = {};
            common_service.post(res.refreshAll, {data:subFrom}).then(function (data) {
                console.log(data)
                if(data.status == 'success'){
                    toastr['success']( "更新权限信息成功！");
                    operman.queryAll();
                }else{
                    toastr['error']('更新权限信息成功'+'<br/>'+data.retMessage);
                }
            })
        }
    }
});


/*操作员个人配置控制器*/
MetronicApp.controller('operat_controller', function ($rootScope, $scope, $state, $stateParams,common_service,filterFilter,i18nService,$modal,$interval) {
    var res = $rootScope.res.operator_service;//页面所需调用的服务
    var role = $rootScope.res.role_service;//添加操作员服务
    //grid表格中文
    i18nService.setCurrentLang("zh-cn");
    //返回方法
    $scope.myback = function(){
        window.history.back(-1);
    }
    var operatqx = {};
    $scope.operatqx = operatqx;
    var userid = $stateParams.id;//接受传入的值
    operatqx.userid= userid;
    $scope.currRole = userid;//显示当前操作员
    //查询userid对应的guid
    var  serFrom = {}
    serFrom.userId = operatqx.userid;
    common_service.post(res.queryOperatorByUserId,serFrom).then(function(data){
        var datas = data.retMessage;
        operatqx.operatorGuid = datas.guid;
    })

    //未授予角色表格生成
    var notrolegird = {};
    $scope.notrolegird = notrolegird;
    var com = [
        { field: "roleName", displayName:'角色名'},
        { field: "guidApp", displayName:'所属应用',cellTemplate:'<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidApp | translateApp) + $root.constant[row.entity.guidApp]}}</div>'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            $scope.selectRow = '';//制空
        }
    }
    $scope.notrolegird = initgrid($scope,notrolegird,filterFilter,com,false,f);
    $scope.notrolegird.enablePaginationControls = false;//禁止有分页
    $scope.notrolegird.enableFiltering = false;//禁止有搜索
    $scope.notrolegird.enableGridMenu = false;//禁止有菜单
    //查询未授予表格逻辑
    operatqx.querynotrole = function(){
        //查询所有未授予的
        var subFrom = {};
        subFrom.userId = operatqx.userid;
            common_service.post(res.queryOperatorUnauthorizedRoleList,subFrom).then(function(data){
                var datas = data.retMessage;
            if(data.status == "success"){
                $scope.notrolegird.data =  datas;
                $scope.notrolegird.mydefalutData = datas;
                $scope.notrolegird.getPage(1,$scope.notrolegird.paginationPageSize);
            }else{
                $scope.notrolegird.data =  [];
                $scope.notrolegird.mydefalutData = [];
                $scope.notrolegird.getPage(1,$scope.notrolegird.paginationPageSize);
            }
        })
    }
    operatqx.querynotrole();

    //添加未授予角色逻辑
    $scope.operatqx.add = function(){
        var dats = $scope.notrolegird.getSelectedRows();
        if($scope.selectRow == ""){
            toastr['error']("请至少选择一个角色");
            return false;
        }else{
                var subFrom = {};
                subFrom.guidRole = $scope.selectRow.guid;//角色guid
                subFrom.guidOperator = operatqx.operatorGuid;//操作员guid
                var tis = {};
                tis.data = []
                tis.data.push(subFrom)
                common_service.post(role.addOperatorRole,tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("授予成功！");
                        operatqx.querynotrole();//查询未授予
                        operatqx.queryalrole();//查询已授予
                    }else{
                        toastr['error']('查询失败'+'<br/>'+data.retMessage);
                    }
                })
        }
    }
    //已授予角色表格生成
    var alrolegird = {};
    $scope.alrolegird = notrolegird;
    var com1 = [
        { field: "roleName", displayName:'角色名'},
        { field: "guidApp", displayName:'所属应用',cellTemplate:'<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidApp | translateApp) + $root.constant[row.entity.guidApp]}}</div>'}
    ];
    var f1 = function(row){
        if(row.isSelected){
            $scope.deleteGUid = row.entity;
        }else{
            $scope.deleteGUid = '';//制空
        }
    }
    $scope.alrolegird = initgrid($scope,alrolegird,filterFilter,com1,false,f1);
    $scope.alrolegird.enablePaginationControls = false;//禁止有分页
    $scope.alrolegird.enableFiltering = false;//禁止有搜索
    $scope.alrolegird.enableGridMenu = false;//禁止有菜单

    //查询已授予角色
    operatqx.queryalrole = function(){
        //查询已授予的角色
        var subFrom = {};
        subFrom.userId = operatqx.userid;
        common_service.post(res.queryOperatorAuthorizedRoleList,subFrom).then(function(data){
            var datas = data.retMessage;
            console.log(datas);
            if(data.status == "success"){
                $scope.alrolegird.data =  datas;
                $scope.alrolegird.mydefalutData = datas;
                $scope.alrolegird.getPage(1,$scope.alrolegird.paginationPageSize);
            }else{
                $scope.alrolegird.data =  [];
                $scope.alrolegird.mydefalutData = [];
                $scope.alrolegird.getPage(1,$scope.alrolegird.paginationPageSize);
            }
        })
    }
    operatqx.queryalrole()
    //已授予角色删除
    $scope.operatqx.del = function(){
        if($scope.deleteGUid == ""){
            toastr['error']("请至少选择一个角色");
            return false;
        }else{
            var subFrom = {};
            subFrom.guidRole = $scope.deleteGUid.guid;//角色guid
            subFrom.guidOperator = operatqx.operatorGuid;//操作员guid
            var tis = {};
            tis.data = [];
            tis.data.push(subFrom)
            common_service.post(role.removeOperatorRole,tis).then(function(data){
                if(data.status == "success"){
                    toastr['success']("删除成功！");
                    operatqx.querynotrole();//查询未授予
                    operatqx.queryalrole();//查询已授予
                }else{
                    toastr['error']('查询失败'+'<br/>'+data.retMessage);
                }
            })
        }
    }

    //特别权限表格添加
    operatqx.authority = function(){
        $scope.operisleaf = false;
        var queryuserid= operatqx.userid;
        var queryguid = operatqx.operatorGuid;//操作员guid
        openwindow($modal, 'views/operator/speciallimit.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                var opearqxFrom = {};
                $scope.opearqxFrom = opearqxFrom;//表单信息
                var  operqxinpfo = {};
                $scope.operqxinpfo = operqxinpfo;//表单信息
                //查询所有应用
                var subFrom = {};
                subFrom.userId = queryuserid;
                //根据usid查询功能权限
                common_service.post(res.queryOperatorFuncInfoInApp,subFrom).then(function(data){
                    if(data.status == "success"){
                        var datas = data.retMessage;
                     /*   var result= datas.replace(/guid/g,"id").replace(/label/g,'text');//把guid和label替换成自己需要的*/
                        var menuAll = angular.fromJson(datas);
                        var tisMenu = [];;//绑定数组
                        tisMenu.push(menuAll)
                        console.log(tisMenu)
                        //菜单一权限
                        $("#opercontainer").jstree({
                            "core" : {
                                "themes" : {
                                    "responsive": false
                                },
                                "check_callback" : false,//在对树结构进行改变时，必须为true
                                'data':tisMenu
                            },
                            "force_text": true,
                            plugins: ["sort", "types", "themes", "html_data"],
                            "checkbox": {
                                "keep_selected_style": false,//是否默认选中
                            },
                            "types" : {
                                "default" : {
                                    "icon" : "fa fa-folder icon-state-warning icon-lg"
                                },
                                "file" : {
                                    "icon" : "fa fa-file icon-state-warning icon-lg"
                                }
                            },
                            "state" : { "key" : "demo3" },
                            'dnd': {
                                'is_draggable':function (node) {
                                    //用于控制节点是否可以拖拽.
                                    if(node.id == 3){
                                        return false;//根节点禁止拖拽
                                    }
                                    return true;
                                },
                                'always_copy':true//拖拽拷贝，非移除
                            },
                            'callback' : {
                                move_node:function (node) {
                                }
                            },
                            plugins: ["sort", "types", "wholerow", "themes", "html_data"],
                        }).bind("select_node.jstree", function (e, data) {
                            operqxinpfo.funinfo = data.node.original;
                            if(data.node.original.isLeaf == 'Y'){
                                    $scope.operisleaf = true;
                            }else{
                                $scope.operisleaf = false;
                            }
                            $scope.$apply();
                        })
                    }else{
                        toastr['error']('暂无该应用操作权限');
                    }
                })
                //导入
                $scope.add = function (item) {
                    var subFrom  = {};
                    subFrom=item;
                    subFrom.guidOperator = queryguid;//操作员guid
                    subFrom.guidFunc = operqxinpfo.funinfo.id;//功能guid
                    subFrom.guidFuncgroup = operqxinpfo.funinfo.parentGuid;//功能组guid
                    subFrom.guidApp = operqxinpfo.funinfo.appGuid//功能组guid
                    common_service.post(res.addAcOperatorFun,subFrom).then(function(data){
                        var datas = data.retMessage;
                        if(data.status == "success"){
                            toastr['success']( "保存成功！");
                            operatqx.queryspeciallimit()
                            $modalInstance.close();
                        }else{

                        }
                    })
                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }

    //特别权限表格生成
    var postgrid = {};
    $scope.postgrid = postgrid;
    //操作员名称，代码  应用系统名称 代码
    //操作员名称，代码  应用系统名称 代码
    var com2 = [
        { field: 'FUNC_NAME', displayName: '功能名称'},
        { field: 'authType', displayName: '授权标志'},
        { field: 'startDate', displayName: '有效开始日期'},
        { field: 'endDate', displayName: '有效截止日期'}
    ];
    var f2 = function(row){
        if(row.isSelected){
            $scope.speciallimit = row.entity;
        }else{
            $scope.speciallimit = '';//制空
        }
    }
    $scope.postgrid = initgrid($scope,postgrid,filterFilter,com2,false,f2);
    $scope.postgrid.data = $scope.postgrids;//表格数据
    $scope.postgrid.enablePaginationControls = false;//禁止有分页
    $scope.postgrid.enableFiltering = false;//禁止有搜索
    $scope.postgrid.enableGridMenu = false;//禁止有菜单
    //查询特殊权限列表
    operatqx.queryspeciallimit = function(){
        var subFrom = {};
        subFrom.userId = operatqx.userid;
        common_service.post(res.queryAcOperatorFunListByUserId,subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
                $scope.postgrid.data =  datas;
                $scope.postgrid.mydefalutData = datas;
                $scope.postgrid.getPage(1,$scope.postgrid.paginationPageSize);
            }else{
                $scope.postgrid.data =  [];
                $scope.postgrid.mydefalutData = [];
                $scope.postgrid.getPage(1,$scope.postgrid.paginationPageSize);
            }
        })
    }

    operatqx.queryspeciallimit()//调用查询特殊权限
    //删除特殊权限列表逻辑
    $scope.operatqx.deleat = function(){
        if($scope.speciallimit == ""){
            toastr['error']("请至少选择一个角色进行删除");
            return false;
        }else{
            var subFrom = {};
            subFrom.operatorGuid = operatqx.operatorGuid;//操作员guid
            subFrom.funcGuid = $scope.speciallimit.guidFunc;//功能guid
            /*var tis = [];
            tis.push(subFrom)*/
            common_service.post(res.removeAcOperatorFun,subFrom).then(function(data){
                if(data.status == "success"){
                    if(confirm("您确认要删除选中的特殊功能吗")){
                        toastr['success']("删除成功！");
                        operatqx.queryspeciallimit()//调用查询特殊权限
                    }

                }else{
                    toastr['error']('查询失败'+'<br/>'+data.retMessage);
                }
            })
        }
    }

    //查询继承角色列表
    operatqx.queryinheritgrid = function(){
        var subFrom = {};
        subFrom.userId = operatqx.userid;
        common_service.post(res.queryOperatorInheritRoleList,subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
                $scope.inheritgrid.data =  datas;
                $scope.inheritgrid.mydefalutData = datas;
                $scope.inheritgrid.getPage(1,$scope.inheritgrid.paginationPageSize);
            }else{
                $scope.inheritgrid.data =  [];
                $scope.inheritgrid.mydefalutData = [];
                $scope.inheritgrid.getPage(1,$scope.inheritgrid.paginationPageSize);
            }
        })
    }
    operatqx.queryinheritgrid();
    //继承角色表格生成
    var inheritgrid = {};
    $scope.inheritgrid = inheritgrid;
    //操作员名称，代码  应用系统名称 代码
    var com3 = [
        { field: 'roleName', displayName: '角色'}
    ];
    var f3 = function(row){
        if(row.isSelected){
            $scope.inherit = row.entity;
        }else{
            $scope.inherit = '';//制空
        }
    }
    $scope.inheritgrid = initgrid($scope,inheritgrid,filterFilter,com3,false,f3);
    $scope.inheritgrid.enablePaginationControls = false;//禁止有分页
    $scope.inheritgrid.enableFiltering = false;//禁止有搜索
    $scope.inheritgrid.enableGridMenu = false;//禁止有菜单

});

/* 重组菜单控制器*/
MetronicApp.controller('reomenu_controller', function ($filter,$rootScope,common_service,$scope, $state, $stateParams, filterFilter, $modal,$uibModal, $http, $timeout) {
    //拿到传递的内容,即userid
    var userId = $stateParams.id;
    var searchFrom = {};
    $scope.searchFrom= searchFrom;
    searchFrom.operuser = userId;//绑定
    var opmer ={};
    $scope.opmer = opmer;
    var res = $rootScope.res.menu_service;//页面所需调用的服务
    //查询所有应用
    var subFrom = {};
    common_service.post(res.queryAllAcApp,subFrom).then(function(data){
        if(data.status == "success"){
            opmer.appselectApp= data.retMessage;
        }
    })
    //查询
    $scope.opmer.search = function(data){
        opmer.guidApp = data.appselect;
        var subFrom = {};
        subFrom.appGuid = opmer.guidApp;
        subFrom.userId = data.operuser;

        //根据userid查询guid
        var ret = $rootScope.res.operator_service;
        var  serFrom = {}
        serFrom.userId = data.operuser;
        common_service.post(ret.queryOperatorByUserId,serFrom).then(function(data){
            var datas = data.retMessage;
            opmer.operatorGuid = datas.guid;
        })

        //菜单刷新功能
        $scope.refers = function(){
            $("#container").jstree().refresh();
        }
        //查询菜单
        common_service.post(res.getMenuByUserId,subFrom).then(function(data){
            if(data.status == "success"){
                var datas = data.retMessage;
                var result= datas.replace(/guid/g,"id").replace(/label/g,'text');//把guid和label替换成自己需要的
                var menuAll = angular.fromJson(result);
                //var menucless = menuAll.children;
                var tisMenu = [];;//绑定数组
                tisMenu.push(menuAll)
                opmer.mensuAll = tisMenu;
                $scope.opmer.searchok = true;
                //菜单一权限
                $('#container').jstree('destroy',false);
                $("#container").jstree({
                    "core" : {
                        "themes" : {
                            "responsive": false
                        },
                        "check_callback" : false,//在对树结构进行改变时，必须为true
                        'data':opmer.mensuAll
                    },
                    "force_text": true,
                    plugins: ["sort", "types", "themes", "html_data"],
                    "checkbox": {
                        "keep_selected_style": false,//是否默认选中
                    },
                    "types" : {
                        "default" : {
                            "icon" : "fa fa-folder icon-state-warning icon-lg"
                        },
                        "file" : {
                            "icon" : "fa fa-file icon-state-warning icon-lg"
                        }
                    },
                    "state" : { "key" : "demo3" },
                    'dnd': {
                        'is_draggable':function (node) {
                            //用于控制节点是否可以拖拽.
                            if(node.id == 3){
                                return false;//根节点禁止拖拽
                            }
                            return true;
                        },
                        'always_copy':true//拖拽拷贝，非移除
                    },
                    'callback' : {
                        move_node:function (node) {
                        }
                    },
                    "plugins" : [ "state", "types","dnd","search" ,"wholerow"]// 插件引入 dnd拖拽插件 state缓存插件(刷新保存) types多种数据结构插件  checkbox复选框插件
                })
            }else{
                toastr['error']('暂无该应用操作权限');
            }
        })
        //新增子菜单提取
        var creatchildmenu = function(node){
            openwindow($uibModal, 'views/Management/configMana.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    var menuFrom = {};
                    $scope.menuFrom = menuFrom;
                    $scope.add = function (item) {
                        var subFrom = {};
                        subFrom = item;
                        subFrom.menuLabel = subFrom.menuName
                        subFrom.guidApp = opmer.guidApp;
                        subFrom.guidParents = node.id;
                        subFrom.isLeaf = 'N';
                        subFrom.guidOperator = opmer.operatorGuid;//操作员guid 先写死
                        common_service.post(res.createChildOperatorMenu,subFrom).then(function(data){
                            if(data.status == "success"){
                                toastr['success']( "新增成功！");
                                searchMenu(node);//刷新重新加载
                                $modalInstance.close();
                            }else{
                                toastr['error']('新增失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }
        //修改菜单提取
        var editchildmenu = function(node){
            openwindow($uibModal, 'views/Management/configMana.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    $scope.id = 2;
                    var menuFrom = {};
                    $scope.menuFrom = menuFrom;
                    menuFrom.expandPath = node.original.icon;
                    subFrom.menuLabel = subFrom.menuName
                    menuFrom.menuName = node.original.text;
                    $scope.add = function (item) {
                        var subFrom = {};
                        subFrom = item;
                        subFrom.guid = node.id;
                        subFrom.isLeaf = 'N';
                        common_service.post(res.editOperatorMenu,subFrom).then(function(data){
                            if(data.status == "success"){
                                toastr['success']( "新增成功！");
                                searchMenu();//刷新重新加载
                                $modalInstance.close();
                            }else{
                                toastr['error']('新增失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }
        //删除菜单提取
        var deleatmenu = function(node){
            var subFrom = {};
            subFrom.operatorMenuGuid = node.id;
            common_service.post(res.deleteOperatorMenu,subFrom).then(function(data){
                if(data.status == "success"){
                    toastr['success']( "删除成功！");
                    searchMenu();
                }else{
                    toastr['error']('删除失败'+'<br/>'+data.retMessage);
                }
            })
        }
        //定义右侧树结构右击事件
        var items = function customMenu(node) {
            var control;
            if(node.parent == '#'){
                var it = {
                    "新增子菜单":{
                        "label":"新增子菜单",
                        "action":function(data){
                            creatchildmenu(node);
                        }
                    },
                    "修改顶级菜单":{
                        "id":"createa",
                        "label":"修改顶级菜单",
                        "action":function(data){
                            editchildmenu(node)
                        }
                    },
                    "删除顶级菜单":{
                        "label":"删除顶级菜单",
                        "action":function(data){
                            if(confirm("您确认要删除顶级菜单吗,删除菜单将删除改菜单下所有子菜单")){
                                deleatmenu(node);
                            }
                        }
                    }
                }
                return it;
            }else if(node.parents[1] == '#'&& node.original.isLeaf!=='Y'){
                var it = {
                    "新增子菜单":{
                        "label":"新增子菜单",
                        "action":function(data){
                            creatchildmenu(node)
                        }
                    },
                    "修改子菜单":{
                        "id":"createa",
                        "label":"修改子菜单",
                        "action":function(data){
                            editchildmenu(node)
                        }
                    },
                    "删除菜单":{
                        "label":"删除菜单",
                        "action":function(data){
                            if(confirm("您确认要删除选中的应用,删除应用将删除该应用下的所有功能组")){
                                deleatmenu(node);
                            }
                        }
                    }
                }
                return it;
            }
            else if(node.original.isLeaf!=='Y'){
                var it = {
                    "新增子菜单":{
                        "label":"新增子菜单",
                        "action":function(data){
                            creatchildmenu(node)
                        }
                    },
                    "修改子菜单":{
                        "label":"修改子菜单",
                        "action":function(data){
                            editchildmenu(node)
                        }
                    },
                    "删除菜单":{
                        "label":"删除菜单",
                        "action":function(data){
                            if(confirm("您确认要删除选中的功能吗")){
                                deleatmenu(node);
                            }
                        }
                    }
                }
                return it;
            }else if(node.original.isLeaf=='Y'){
                var it = {
                    "修改菜单":{
                        "label":"修改菜单",
                        "action":function(data){
                            editchildmenu(node)
                        }
                    },
                    "删除菜单":{
                        "label":"删除菜单",
                        "action":function(data){
                            if(confirm("您确认要删除选中的功能吗")){
                                deleatmenu(node);
                            }
                        }
                    },
                    '刷新':{
                        "label":"刷新",
                        "action":function(data){
                            searchMenu(node);
                        }
                    }
                }
                return it;
            }
        };
        //刷新
        $scope.resfroen= function(){
            searchMenu()
        }
        //查询重组菜单逻辑
        var searchMenu =  function(node){
            //修改，在调用的时候把节点传入，要打开此节点，完成刷新加载
            common_service.post(res.getOperatorMenuByUserId,subFrom).then(function(data){
                //重新组装数据
                var dates = data.retMessage;
                var results= dates.replace(/guid/g,"id").replace(/label/g,'text');//把guid和label替换成自己需要的
                var menusAll = angular.fromJson(results);
                opmer.mensussAll = menusAll;
                $('#container2').jstree('destroy',false);
                if(dates =='{}'){
                    $scope.iscreat = true;

                }else{
                    $scope.iscreat = false;
                    jstreecre(opmer.mensussAll)
                }
                var timer=$timeout(function(){
                    //自动打开对应的节点，模拟按需加载功能
                    $("#container2").jstree().open_node(node)
                },500);

            })
        }
        //提取新增顶级菜单
        var creattopmenu = function(){
            openwindow($uibModal, 'views/Management/configMana.html', 'lg',
                function ($scope, $modalInstance) {
                    $scope.add= function(item){
                        var subFrom = {};
                        subFrom = item;
                        subFrom.menuLabel = subFrom.menuName
                        subFrom.guidApp = opmer.guidApp;
                        subFrom.guidParents = '#';
                        subFrom.guidOperator = opmer.operatorGuid;//操作员guid 先写死
                        common_service.post(res.createRootOperatorMenu,subFrom).then(function(data){
                            if(data.status == "success"){
                                toastr['success']( "新增成功！");
                                searchMenu();//刷新重新加载
                                $modalInstance.close();
                            }else{
                                toastr['error']('新增失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }
        //点击新增重组菜单
        $scope.opmer.saveconfig = function () {
            creattopmenu()
            }


        //撤销重组菜单
        $scope.opmer.delconfig= function () {
            if(confirm("撤销之后，原有重组菜单将消失，菜单恢复成默认菜单")){
                //让菜单树消失
                searchMenu();//刷新重新加载
            }
        }
        //树结构生成提取
        var jstreecre = function(datas){
            $("#container2").jstree({
                "core" : {
                    "themes" : {
                        "responsive": false
                    },
                    "check_callback" : true,
                    'data':datas
                },
                "force_text": true,
                plugins: ["sort", "types", "checkbox", "themes", "html_data"],
                "types" : {
                    "default" : {
                        "icon" : "fa fa-folder icon-state-warning icon-lg"
                    },
                    "file" : {
                        "icon" : "fa fa-file icon-state-warning icon-lg"
                    }
                },
                "contextmenu":{'items':items
                },
                "state" : { "key" : "demo3" },
                'dnd': {
                    'dnd_start': function () {
                    },
                    'is_draggable':function (node) {
                        //用于控制节点是否可以拖拽.
                        if(node.id == 3){
                            return false;//根节点禁止拖拽
                        }
                        return true;
                    }
                },
                "plugins" : [ "dnd", "state", "types","search" ,"wholerow","contextmenu"]// 插件引入 dnd拖拽插件 state缓存插件(刷新保存) types多种数据结构插件  checkbox复选框插件
            }).bind("move_node.jstree",function (e,data) {
                if(confirm("确认要移动此机构吗?")){
                    var subFrom = {};
                    subFrom.targetGuid =  data.parent;//新的父节点guid;
                    subFrom.moveGuid =  data.node.id;//当前guid。
                    subFrom.order =  data.position;//新的位置
                    common_service.post(res.moveOperatorMenu,subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']( "移动成功！");
                        }else{
                            searchMenu();//刷新重新加载
                            toastr['error']('移动失败'+'<br/>'+data.retMessage);
                        }
                    })
                }else{
                    searchMenu();//刷新重新加载
                }
            }).bind("copy_node.jstree",function(event,data){
                var subFrom = {};
                subFrom.goalGuid =  data.parent;//新的父节点guid;
                subFrom.copyGuid =  data.original.id;//当前guid。
                subFrom.order =  data.position;//新的位置
                subFrom.operatorGuid =  opmer.operatorGuid;//操作员guid
                common_service.post(res.copyMenuToOperatorMenu,subFrom).then(function(data){
                    if(data.status == "success"){
                        toastr['success']( "复制成功！");
                        searchMenu(data.parent);//刷新之后自动打开父节点，传入移动的父节点
                    }else{
                        searchMenu(data.parent);//刷新重新加载
                        toastr['error']('复制失败'+'<br/>'+data.retMessage);
                    }
                })
            })
        }
        //查询重组菜单
        common_service.post(res.getOperatorMenuByUserId,subFrom).then(function(data){
            if(data.status == "success"){
                var dates = data.retMessage;
                var results= dates.replace(/guid/g,"id").replace(/label/g,'text');//把guid和label替换成自己需要的
                var menusAll = angular.fromJson(results);
                opmer.mensussAll = menusAll;
                if(dates == '{}'){
                    if(confirm("该用户无重组菜单，是否创建")){
                        $('#container2').jstree('destroy',false);
                        creattopmenu();
                        $scope.opmer.config = true;//显示新增的顶级菜单
                    }else{
                        $scope.iscreat = true;
                        $scope.opmer.config = true;
                    }
                }else{
                    //有重组菜单，自己配置即可
                    $scope.opmer.searchok = true;
                    $scope.opmer.config = true;
                    $('#container2').jstree('destroy',false);
                    jstreecre(opmer.mensussAll)
                }
            }else{
                toastr['error']('查询重组菜单失败'+'<br/>'+data.retMessage);
            }
        })
    }
});



















