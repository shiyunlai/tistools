/**
 * Created by wangbo on 2017/6/20.
 */

MetronicApp.controller('opmanage_controller', function ($filter,$rootScope, $scope, $state, $stateParams, filterFilter,operator_service,dictonary_service, $modal,$uibModal, $http, $timeout,$interval,i18nService) {
    //grid表格
    i18nService.setCurrentLang("zh-cn");
    //查询操作员列表
    var operman ={};
    $scope.operman = operman;

    operman.queryAll = function(){
        //查询所有
        var subFrom = {};
        operator_service.queryAllOperator(subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
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
        { field: "authMode", displayName:'认证模式'},
        { field: "operatorStatus",displayName:'操作员状态'},
        { field: "menuType",displayName:'菜单风格'},
        { field: "lockLimit",displayName:'锁定次数限制'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);

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
                $scope.add = function(item){//保存新增的函数
                    var subFrom = {};
                    $scope.subFrom = subFrom;
                    subFrom = item;
                    operator_service.createOperator(subFrom).then(function(data){
                        console.log(data)
                        if(data.status == "success"){
                            toastr['success']( "新增成功！");
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
            openwindow($uibModal, 'views/operator/operatorAdd.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    if(!isNull(items)){//如果参数不为空，则就回显
                        $scope.operatFrom = angular.copy(items);
                    }
                    $scope.id = id;//获取到id，用来判断是否编辑，因为scope作用域不同，所以不同
                    $scope.add = function(item){//保存新增的函数
                        var subFrom = {};
                        $scope.subFrom = subFrom;
                        subFrom = item;
                        console.log(subFrom)
                        operator_service.editOperator(subFrom).then(function(data){
                            if(data.status == "success"){
                                toastr['success']( "新增成功！");
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
        }else{
            toastr['error']("请至少选中一个操作员进行操作！");
        }
    }

});

/* 重组菜单控制器*/
MetronicApp.controller('reomenu_controller', function ($filter,$rootScope,common_service,$scope, $state, $stateParams, filterFilter, $modal,$uibModal, $http, $timeout,$interval,i18nService) {
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
                    console.log(node.original.icon)
                    menuFrom.expandPath = node.original.icon;
                    console.log(menuFrom.expandPath)
                    menuFrom.menuName = node.original.text;
                    $scope.add = function (item) {
                        var subFrom = {};
                        subFrom = item;
                        subFrom.guid = node.id;
                        subFrom.isLeaf = 'N';
                        common_service.post(res.editOperatorMenu,subFrom).then(function(data){
                            console.log(data);
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
                console.log(data);
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



















