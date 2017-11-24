/**
 * Created by wangbo on 2017/6/1.
 */
angular.module('MetronicApp').controller('menu_controller', function($rootScope,$state, $scope, $http,menu_service,common_service,i18nService,$timeout,filterFilter,$uibModal) {
    var menu = {};
    $scope.menu = menu;
    var subFrom = {};
    //定义当前节点:
    var thisNode = '';
    $scope.thisNode =thisNode;

    i18nService.setCurrentLang("zh-cn");
    //查询所有应用
    menu_service.queryAllAcApp(subFrom).then(function (data) {
        menu.appselectApp= data.retMessage;
    })
    var res = $rootScope.res.menu_service;//页面所需调用的服务
    /*0、菜单管理机构树逻辑*/
    //菜单搜索修改，改成键盘弹起事件，加上search组件
    var to = false;
    $('#q').keyup(function () {
        if(to) {
            clearTimeout(to);
        }
        $('#container').jstree().load_all();
        to = setTimeout(function () {
            var v = $('#q').val();
            $('#container').jstree(true).search(v);
        }, 250);
    });

    //清空
    menu.clear = function () {
        $scope.searchitem = "";
        if(to) {
            clearTimeout(to);
        }
        $('#container').jstree().load_all();
        to = setTimeout(function () {
            var v = $('#q').val();
            $('#container').jstree(true).search(v);
        }, 250);
    }
    //刷新菜单树
    $scope.reload = function(){
        $("#container").jstree().refresh();
    }
    //应用查询
    $scope.menu.search = function(item){
        var guidApp = item.appselect;
        $scope.menu.guidApp = guidApp;//绑定给全局，全局可拿
        if(item.appselect !== undefined ){
            subFrom.guidApp =item.appselect;
            $scope.menushow = true;
            $scope.menu.show  = false;
            //新增顶级菜单提取
            var  creatopMenu = function(){
                openwindow($uibModal, 'views/Management/manachildAdd.html', 'lg',
                    function ($scope, $modalInstance) {
                        var menuFrom = {};
                        $scope.menuFrom = menuFrom;
                        $scope.menuFrom.isleaf ='N'
                        $scope.menuFrom.displayOrder = 0;//默认为0
                        $scope.add = function (menuFrom) {
                            var guidApp = menu.appselect
                            var  subFrom = {};
                            subFrom = menuFrom;
                            subFrom.expandPath = subFrom.imagePath;
                            subFrom.guidApp = guidApp;
                            menu_service.createRootMenu(subFrom).then(function(data){
                                if(data.status == "success"){
                                    toastr['success']( "新增成功！");
                                    $modalInstance.close();
                                    $("#container").jstree().refresh();
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
            //新增子菜单提取
            var creachildMenu = function(node){
                openwindow($uibModal, 'views/Management/menuchildAdd.html', 'lg',
                    function ($scope, $modalInstance) {
                        var menuFrom = {};
                        $scope.menuFrom = menuFrom;
                        $scope.menuFrom.isleaf = 'N';//默认为N
                        $scope.menuFrom.displayOrder = 0;//默认为0
                        $scope.add = function (item) {
                            var guidApp = menu.appselect
                            var subFrom = {};
                            subFrom = item;
                            subFrom.guidApp = guidApp;
                            subFrom.guidParents = node.id;
                            subFrom.expandPath = subFrom.imagePath;
                            if(!isNull(menuFrom.infosava)){
                                subFrom.guidFunc = menuFrom.infosava.guid;
                            }
                            menu_service.createChildMenu(subFrom).then(function(data){
                                if(data.status == "success"){
                                    toastr['success']( "新增成功！");
                                    $("#container").jstree().refresh();
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
            //新增子节点提取
            var creachildNode = function(node){
                openwindow($uibModal, 'views/Management/menunodechild.html', 'lg',
                    function ($scope, $modalInstance) {
                        var menuFrom = {};
                        $scope.menuFrom = menuFrom;
                        $scope.menuFrom.isleaf = 'Y';//默认为N
                        $scope.menuFrom.displayOrder = 0;//默认为0
                         //页面逻辑
                         $scope.selectfunc = true;
                         $scope.selectfuncs = function(){
                         openwindow($uibModal, 'views/Management/selectfunc.html', 'lg',
                         function ($scope, $modalInstance) {
                         var gridOptions = {};
                         $scope.gridOptions = gridOptions;
                         var com = [
                            { field: "funcName", displayName:'功能名称'}
                            ];
                         //自定义点击事件
                         var f1 = function(row){
                         if(row.isSelected){
                         $scope.selectRow = row.entity;
                            }else{
                                delete $scope.selectRow;//制空
                            }
                         }
                         $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f1);
                         //调用查询应用下功能服务
                         var  subFrom = {};
                         subFrom.appGuid =menu.guidApp;
                         common_service.post(res.queryAllFuncInApp,subFrom).then(function(data){
                         var datas  = data.retMessage;
                         $scope.gridOptions.data = datas;
                         $scope.gridOptions.mydefalutData = datas;
                         $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
                         })
                         $scope.importAdd = function(){
                         var dats = $scope.gridOptions.getSelectedRows();
                         if(dats.length >=0){
                                 $modalInstance.close();
                                 menuFrom.infosava = dats[0];
                                 menuFrom.guidFunc =dats[0].funcName;
                         }}
                             $scope.cancel = function () {
                             $modalInstance.dismiss('cancel');
                             };
                         })}

                        $scope.add = function (item) {
                            var guidApp = menu.appselect
                            var subFrom = {};
                            subFrom = item;
                            subFrom.guidApp = guidApp;
                            subFrom.guidParents = node.id;
                            subFrom.expandPath = subFrom.imagePath;
                            if(!isNull(menuFrom.infosava)){
                                subFrom.guidFunc = menuFrom.infosava.guid;
                            }
                            menu_service.createChildMenu(subFrom).then(function(data){
                                if(data.status == "success"){
                                    toastr['success']( "新增成功！");
                                    $("#container").jstree().refresh();
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
            //删除菜单提取
            var deleMenu = function(node){
                var subFrom = {};
                subFrom.menuGuid = node.original.guid;
                menu_service.deleteMenu(subFrom).then(function(data){
                    if(data.status == "success"){
                        toastr['success']( "删除成功！");
                        $scope.menu.show  = false;
                        $("#container").jstree().refresh();
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
            //树自定义右键功能(根据类型判断)
            var items = function customMenu(node) {
                var control;
                if(node.parent == '#'){
                    var it = {
                        "新增菜单":{
                            "id":"createa",
                            "label":"新增菜单",
                            "action":function(data){
                                creatopMenu();
                            }
                        },
                        "刷新":{
                            "label":"刷新",
                            "action":function(data){
                                $("#container").jstree().refresh();
                            }
                        }
                    }
                    return it;
                }
                if(node.parent == "AC0000"){
                    var it = {
                        "增加子菜单":{
                            "id":"createc",
                            "label":"增加子菜单",
                            "action":function(data){
                                creachildMenu(node);
                            }
                        },
                        "增加子节点":{
                            "label":"增加子节点",
                            "action":function(data){
                                creachildNode(node)
                            }
                        },
                        "删除":{
                            "label":"删除",
                            "action":function(data){
                                if(confirm("您确认要删除选中的菜单吗,删除菜单将删除该菜单下的所有子菜单和功能")){
                                    deleMenu(node)
                                }
                            }
                        },
                        '刷新':{
                            "label":"刷新",
                            "action":function(data){
                                $("#container").jstree().refresh();
                            }
                        }
                    }
                    return it;
                }
                if(node.parents[0] !== "AC0000" && node.original.isleaf!=='Y'){
                    var it = {
                        "增加子菜单":{
                            "id":"createc",
                            "label":"增加子菜单",
                            "action":function(data){
                                creachildMenu(node);
                            }
                        },
                        "增加子节点":{
                            "label":"增加子节点",
                            "action":function(data){
                                creachildNode(node)
                            }
                        },
                        "删除":{
                            "label":"删除",
                            "action":function(data){
                                if(confirm("您确认要删除选中的菜单吗,删除菜单将删除该菜单下的所有子菜单和功能")){
                                    deleMenu(node)
                                }
                            }
                        },
                        '刷新':{
                            "label":"刷新",
                            "action":function(data){
                                $("#container").jstree().refresh();
                            }
                        }
                    }
                    return it;
                }
                if(node.original.isleaf == 'Y' ){
                    var it = {
                        "删除":{
                            "label":"删除",
                            "action":function(data){
                                if(confirm("您确认要删除选中的菜单吗,删除菜单将删除该菜单下的所有功能")){
                                    deleMenu(node)
                                }
                            }
                        },
                        '刷新':{
                            "label":"刷新",
                            "action":function(data){
                                $("#container").jstree().refresh();
                            }
                        }
                    }
                    return it;
                }
            };
            $('#container').jstree('destroy',false);
            $('#container').jstree({
                "core" : {
                    "themes": {
                        "responsive": false
                    },
                    "check_callback": true,
                    'data':function(obj, callback){
                        var jsonarray = [];
                        $scope.jsonarray = jsonarray;
                        var subFrom = {};
                        var its = [];
                        if(obj.id == '#'){
                            subFrom.guidApp = '#';
                            menu_service.queryRootMenuTree(subFrom).then(function (data) {
                                var datas = data.retMessage.data;
                                datas.text = datas.rootName;
                                datas.children = true;
                                datas.id = datas.rootCode;
                                datas.icon = "fa fa-home icon-state-info icon-lg"
                                its.push(datas);
                                $scope.jsonarray = angular.copy(its);
                                callback.call(this, $scope.jsonarray);
                            })
                        }else if(obj.id == 'AC0000'){
                            subFrom.guidApp = menu.appselect;
                            menu_service.queryRootMenuTree(subFrom).then(function (data) {
                                var datas = data.retMessage.data;
                                console.log(datas);
                                for(var i =0;i <datas.length;i++){
                                    datas[i].text = datas[i].menuLabel;
                                    datas[i].children = true;
                                    datas[i].id = datas[i].guid;
                                    datas[i].icon = "fa  fa-files-o icon-state-info icon-lg"
                                    its.push(datas[i]);
                                }
                                $scope.jsonarray = angular.copy(its);
                                callback.call(this, $scope.jsonarray);
                            })
                        }else{
                            subFrom.guidMenu = obj.id;
                            menu_service.queryChildMenu(subFrom).then(function (data) {
                                var datas = data.retMessage;
                                for(var i =0;i <datas.length;i++){
                                    if(datas[i].isleaf == 'Y'){//如果是子节点，那么就是功能，不会有子集了
                                        datas[i].children = false;
                                        datas[i].icon = "fa fa-wrench icon-state-info icon-lg"
                                    }else{
                                        datas[i].children = true;
                                        datas[i].icon = "fa  fa-files-o icon-state-info icon-lg"
                                    }
                                    datas[i].text = datas[i].menuLabel;
                                    datas[i].id = datas[i].guid;
                                    its.push(datas[i]);
                                }
                                $scope.jsonarray = angular.copy(its);
                                callback.call(this, $scope.jsonarray);
                            })
                        }
                    }
                },
                "state" : { "key" : "demo3" },
                "contextmenu":{'items':items
                },
                'dnd': {
                    'dnd_start': function () {
                    },
                    'is_draggable':function (node) {
                        return true;
                    }
                },
                'search':{
                    show_only_matches:true,
                },
                'sort': function (a, b) {
                    //排序插件，会两者比较，获取到节点的order属性，插件会自动两两比较。
                    return this.get_node(a).original.displayOrder > this.get_node(b).original.displayOrder ? 1 : -1;
                },
                'callback' : {
                    move_node:function (node) {
                    }
                },
                "plugins" : [ "dnd", "state", "types","search","sort","contextmenu" ]
            }).bind("move_node.jstree",function (e,data) {
                if(confirm("确认要移动此机构吗?")){
                    var subFrom = {};
                    subFrom.targetGuid =  data.parent;//新的父节点guid;
                    subFrom.moveGuid =  data.node.id;//当前guid。
                    subFrom.order =  data.position;//新的位置
                    var res = $rootScope.res.menu_service;//页面所需调用的服务
                    common_service.post(res.moveMenu,subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']( "移动成功！");
                            $("#container").jstree().refresh();
                        }else{
                            $("#container").jstree().redraw();
                            toastr['error']('移动失败'+'<br/>'+data.retMessage);
                        }
                    })
                }else{
                    $("#container").jstree().refresh();
                }
            }).bind("select_node.jstree", function (e, data) {
                if(data.node.original.isleaf == 'Y'){
                    $scope.testussef = true;
                    $scope.selectfunc = true;
                }else{
                    $scope.testussef = false;
                    $scope.selectfunc = false;
                }
                $scope.thisNode = data.node.text;
                if(typeof data.node !== 'undefined'){//拿到结点详情
                    $scope.menuFrom = data.node.original;
                    $scope.menu.item = data.node.original.guid;
                    if(data.node.parent == '#'){
                        $scope.menu.menusearch = false;
                        $scope.menu.show  = true;
                    }else if(data.node.parent !== '#'){
                        $scope.menu.menusearch = true;
                        $scope.menu.show  = true;
                    }
                    $scope.$apply();
                }
            });
        }else{
            confirm("请选择一项应用进行查询")
            $scope.menu.show = false;
        }
    }


    //查看概况
    menu.histroy = function () {
        var menuGuid =$scope.menu.item;
        $state.go("loghistory",{id:menuGuid});//跳转到历史页面
    }
    /*2.菜单详情修改*/
    //编辑
    $scope.menu.menuEdit = function(item){
        $scope.editflag = !$scope.editflag;//让保存取消方法显现
        //取消方法
        $scope.watcher =function(item){

        }
        $scope.copyscript = angular.copy(item);
        if(item.isleaf == 'N'){
            $scope.isleaftrue = true;
            $scope.selectfunc = false;
        }else if(item.isleaf == 'Y'){
            $scope.isleaftrue = false;
            $scope.selectfunc = true;
            $scope.selectfuncs = function(){
                openwindow($uibModal, 'views/Management/selectfunc.html', 'lg',
                    function ($scope, $modalInstance) {
                        var gridOptions = {};
                        $scope.gridOptions = gridOptions;
                        var com = [
                            { field: "funcName", displayName:'功能名称'}
                        ];
                        //自定义点击事件
                        var f1 = function(row){
                            if(row.isSelected){
                                $scope.selectRow = row.entity;
                            }else{
                                delete $scope.selectRow;//制空
                            }
                        }
                        $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f1);
                        var  subFrom = {};
                        subFrom.appGuid =menu.guidApp;
                        common_service.post(res.queryAllFuncInApp,subFrom).then(function(data){
                            var datas  = data.retMessage;
                            $scope.gridOptions.data = datas;
                            $scope.gridOptions.mydefalutData = datas;
                            $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
                        })
                        var menuFrom = {};
                        $scope.menuFrom = menuFrom;
                        $scope.importAdd = function(){
                            var dats = $scope.gridOptions.getSelectedRows();
                            if(dats.length >=0){
                                $modalInstance.close();
                                item.guidFunc = dats[0].guid;
                            }
                        }
                        $scope.cancel = function () {
                            $modalInstance.dismiss('cancel');
                        };
                    })
            }
        }
    }
    var menuFrom = {};
    $scope.menuFrom = menuFrom;
    $scope.menuFrom.displayOrder = 0;//默认为0
    //新增子菜单逻辑
    $scope.menu.childAdd = function(){
        var menuGuide = $scope.menu.item;
        openwindow($uibModal, 'views/Management/menuchildAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                var menuFrom = {};
                $scope.menuFrom = menuFrom;
                $scope.menuFrom.displayOrder = 0;//默认为0
                $scope.ceshi = function(item){
                    if(item == 'Y'){
                        //逻辑
                        $scope.selectfunc = true;
                        $scope.selectfuncs = function(){
                            openwindow($uibModal, 'views/Management/selectfunc.html', 'lg',
                                function ($scope, $modalInstance) {
                                    var gridOptions = {};
                                    $scope.gridOptions = gridOptions;
                                    var com = [
                                        { field: "funcName", displayName:'功能名称'}
                                    ];
                                    //自定义点击事件
                                    var f1 = function(row){
                                        if(row.isSelected){
                                            $scope.selectRow = row.entity;
                                        }else{
                                            delete $scope.selectRow;//制空
                                        }
                                    }
                                    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f1);
                                    var  subFrom = {};
                                    subFrom.appGuid =menu.guidApp;
                                    common_service.post(res.queryAllFuncInApp,subFrom).then(function(data){
                                        var datas  = data.retMessage;
                                        $scope.gridOptions.data = datas;
                                        $scope.gridOptions.mydefalutData = datas;
                                        $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
                                    })
                                    $scope.importAdd = function(){
                                        var dats = $scope.gridOptions.getSelectedRows();
                                        if(dats.length >=0){
                                            $modalInstance.close();
                                            menuFrom.infosava = dats[0];
                                            menuFrom.guidFunc =dats[0].funcName;
                                        }
                                    }
                                    $scope.cancel = function () {
                                        $modalInstance.dismiss('cancel');
                                    };
                                })
                        }
                    }else{
                        $scope.selectfunc = false;
                    }
                }
                $scope.add = function(item){//保存新增的函数
                    var subFrom = {};
                    var guidApp = menu.appselect;
                    var menuGuid = menuGuide;
                    subFrom = item;
                    subFrom.guidApp = guidApp;
                    subFrom.guidParents = menuGuid;
                    if(!isNull(menuFrom.infosava)){
                        subFrom.guidFunc = menuFrom.infosava.guid;
                    }
                    subFrom.expandPath = subFrom.imagePath;
                    menu_service.createChildMenu(subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']( "新增成功！");
                            $("#container").jstree().refresh();
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
    //修改保存方法
    $scope.menu.save = function (item) {
        $scope.isleaftrue = false;
        //绑定功能方法
        var subFrom = {};
        var guidApp = menu.appselect;
        subFrom = item;
        subFrom.guidApp = guidApp;
        if(isNull(item.guidParents)){
            subFrom.guidParents = null;
        }else{
            subFrom.guidParents = item.guidParents;
        }
        subFrom.expandPath = subFrom.imagePath;
        menu_service.editMenu(subFrom).then(function(data){
            if(data.status == "success"){
                toastr['success']("保存成功");
                $("#container").jstree().refresh();
                $scope.editflag = !$scope.editflag;//让保存取消方法显现
            }else{
                toastr['error']('修改失败'+'<br/>'+data.retMessage);
            }
        })
    }

    //取消按钮
    $scope.menu.cenel = function(){
        $scope.menuFrom = $scope.copyscript
        $scope.editflag = !$scope.editflag;
    }
});
