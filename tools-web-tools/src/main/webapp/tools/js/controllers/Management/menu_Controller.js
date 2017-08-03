/**
 * Created by wangbo on 2017/6/1.
 */
angular.module('MetronicApp').controller('menu_controller', function($rootScope, $scope, $http,menu_service, $timeout,filterFilter,$uibModal) {
    var menu = {};
    $scope.menu = menu;
    var subFrom = {};
    //查询所有应用
    menu_service.queryAllAcApp(subFrom).then(function (data) {
        menu.appselectApp= data.retMessage;
    })



    /*0、菜单管理机构树逻辑*/
    $("#s").submit(function(e) {
        e.preventDefault();
        $("#container").jstree(true).search($("#q").val());
    });

    //刷新菜单树
    $scope.reload = function(){
        $("#container").jstree().refresh();
    }

    //应用查询
    $scope.menu.search = function(item){
        if(item.appselect !== undefined ){
            //查询应用下所有菜单
            subFrom.guidApp =item.appselect;
            $scope.menu.show = true;
            //树自定义右键功能(根据类型判断)
            var items = function customMenu(node) {
                var control;
                if(node.parent == '#'){
                    var it = {
                        "新增顶级菜单":{
                            "id":"createa",
                            "label":"新增顶级菜单",
                            "action":function(data){
                                var inst = jQuery.jstree.reference(data.reference),
                                    obj = inst.get_node(data.reference);
                                openwindow($uibModal, 'views/Management/manachildAdd.html', 'lg',
                                    function ($scope, $modalInstance) {
                                        $scope.add = function (menuFrom) {
                                            var guidApp = menu.appselect
                                            var  subFrom = {};
                                            subFrom = menuFrom;
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
                        "删除顶级菜单":{
                            "label":"删除顶级菜单",
                            "action":function(data){
                                var inst = jQuery.jstree.reference(data.reference),
                                    obj = inst.get_node(data.reference);
                                if(confirm("您确认要删除选中的应用,删除应用将删除该应用下的所有功能组")){
                                    var subFrom = {};
                                    subFrom.menuGuid = node.original.guid;
                                    menu_service.deleteMenu(subFrom).then(function(data){
                                        if(data.status == "success"){
                                            toastr['success']( "删除成功！");
                                            $("#container").jstree().refresh();
                                        }else{
                                            toastr['error']('删除失败'+'<br/>'+data.retMessage);
                                        }
                                    })
                                }
                            }
                        },
                        "增加子菜单":{
                            "id":"createc",
                            "label":"增加子菜单",
                            "action":function(data){
                                var inst = jQuery.jstree.reference(data.reference),
                                    obj = inst.get_node(data.reference);//从数据库中获取所有的数据
                                openwindow($uibModal, 'views/Management/menuchildAdd.html', 'lg',
                                    function ($scope, $modalInstance) {
                                        $scope.add = function (item) {
                                            var guidApp = menu.appselect
                                            var subFrom = {};
                                            subFrom = item;
                                            subFrom.guidApp = guidApp;
                                            subFrom.guidParents = node.id;
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
                        },
                        "删除菜单":{
                            "label":"删除菜单",
                            "action":function(data){
                                var inst = jQuery.jstree.reference(data.reference),
                                    obj = inst.get_node(data.reference);
                                if(confirm("您确认要删除选中的应用,删除应用将删除该应用下的所有功能组")){
                                    var subFrom = {};
                                    subFrom.menuGuid = node.original.guid;
                                    menu_service.deleteMenu(subFrom).then(function(data){
                                        if(data.status == "success"){
                                            toastr['success']( "删除成功！");
                                            $("#container").jstree().refresh();
                                        }else{
                                            toastr['error']('删除失败'+'<br/>'+data.retMessage);
                                        }
                                    })
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
                if(node.parents[0] !== "AC0000" && node.original.isleaf !=='Y'){
                    var it = {
                        "增加子菜单":{
                            "id":"createc",
                            "label":"增加子菜单",
                            "action":function(data){
                                var inst = jQuery.jstree.reference(data.reference),
                                    obj = inst.get_node(data.reference);//从数据库中获取所有的数据
                                openwindow($uibModal, 'views/Management/menuchildAdd.html', 'lg',
                                    function ($scope, $modalInstance) {
                                        $scope.add = function (item) {
                                            var guidApp = menu.appselect
                                            var subFrom = {};
                                            subFrom = item;
                                            subFrom.guidApp = guidApp;
                                            subFrom.guidParents = node.id;
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
                        },
                        "删除菜单":{
                            "label":"删除菜单",
                            "action":function(data){
                                var inst = jQuery.jstree.reference(data.reference),
                                    obj = inst.get_node(data.reference);
                                if(confirm("您确认要删除选中的应用,删除应用将删除该应用下的所有功能组")){
                                    var subFrom = {};
                                    subFrom.menuGuid = node.original.guid;
                                    menu_service.deleteMenu(subFrom).then(function(data){
                                        if(data.status == "success"){
                                            toastr['success']( "删除成功！");
                                            $("#container").jstree().refresh();
                                        }else{
                                            toastr['error']('删除失败'+'<br/>'+data.retMessage);
                                        }
                                    })
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
                        "删除菜单":{
                            "label":"删除菜单",
                            "action":function(data){
                                var inst = jQuery.jstree.reference(data.reference),
                                    obj = inst.get_node(data.reference);
                                if(confirm("您确认要删除选中的应用,删除应用将删除该应用下的所有功能组")){
                                    var subFrom = {};
                                    subFrom.menuGuid = node.original.guid;
                                    menu_service.deleteMenu(subFrom).then(function(data){
                                        if(data.status == "success"){
                                            toastr['success']( "删除成功！");
                                            $("#container").jstree().refresh();
                                        }else{
                                            toastr['error']('删除失败'+'<br/>'+data.retMessage);
                                        }
                                    })
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
                                datas.iocon = "fa fa-home icon-state-info icon-lg"
                                its.push(datas);
                                $scope.jsonarray = angular.copy(its);
                                callback.call(this, $scope.jsonarray);
                            })
                        }else if(obj.id == 'AC0000'){
                            subFrom.guidApp = menu.appselect;
                            menu_service.queryRootMenuTree(subFrom).then(function (data) {
                                var datas = data.retMessage.data;
                                for(var i =0;i <datas.length;i++){
                                    datas[i].text = datas[i].menuLabel;
                                    datas[i].children = true;
                                    datas[i].id = datas[i].guid;
                                    datas[i].iocon = "fa fa-home icon-state-info icon-lg"
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
                                    datas[i].text = datas[i].menuLabel;
                                    datas[i].children = true;
                                    datas[i].id = datas[i].guid;
                                    datas[i].iocon = "fa fa-home icon-state-info icon-lg"
                                    its.push(datas[i]);
                                }
                                $scope.jsonarray = angular.copy(its);
                                callback.call(this, $scope.jsonarray);
                            })
                        }
                    }
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
                'callback' : {
                    move_node:function (node) {
                    }
                },
                "plugins" : [ "dnd", "state", "types","search","contextmenu" ]
            }).bind("select_node.jstree", function (e, data) {
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


    /*2.菜单详情修改*/
    //编辑
    $scope.menu.menuEdit = function(item){
        $scope.editflag = !$scope.editflag;//让保存取消方法显现
        $scope.copyscript = angular.copy(item);
        if(item.isleaf == 'N'){
            $scope.isleaftrue = true;
        }else if(item.isleaf == 'Y'){
            $scope.isleaftrue = false;
        }
    }

    //新增子菜单逻辑
    $scope.menu.childAdd = function(){
        var menuGuide = $scope.menu.item;
        openwindow($uibModal, 'views/Management/menuchildAdd.html', 'lg',// 弹出页面//弹出页面
            function ($scope, $modalInstance) {
                //修改页面代码逻辑
                $scope.add = function(item){//保存新增的函数
                    var subFrom = {};
                    var guidApp = menu.appselect;
                    var menuGuid = menuGuide;
                    subFrom = item;
                    subFrom.guidApp = guidApp;
                    subFrom.guidParents = menuGuid;
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
        var subFrom = {};
        var guidApp = menu.appselect;
        subFrom = item;
        subFrom.guidApp = guidApp;
        if(isNull(item.guidParents)){
            subFrom.guidParents = null;
        }else{
            subFrom.guidParents = item.guidParents;
        }
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
