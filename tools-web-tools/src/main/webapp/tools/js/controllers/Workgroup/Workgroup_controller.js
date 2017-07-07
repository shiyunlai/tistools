/**
 * Created by gaojie on 2017/6/29.
 */
angular.module('MetronicApp').controller('Workgroup_controller', function($rootScope, $scope,Workgroup_service, $http, $timeout,i18nService,filterFilter,uiGridConstants,$uibModal,$state) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        App.initAjax();
    });
    //定义主题对象
    var workgroup = {};
    $scope.workgroup = workgroup;
    //筛选树标志
    var showtree = true;
    $scope.showtree = showtree;
    //筛选树条件
    var searchitem = "";
    $scope.searchitem = searchitem;
    //设置插件语言为中文
    i18nService.setCurrentLang("zh-cn");
    //页签控制标识
    var flag = {};
    $scope.flag = flag;
    //默认为初始页面列表
    flag.index = true;
    //树自定义右键功能
    var items = function customMenu(node) {
        // The default set of all items
        var control;
        console.log(node);
        if(node.parent == "#"){
            var it = {
                "新建菜单":{
                    "id":"create",
                    "label":"新建工作组",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        openwindow($uibModal, 'views/org/addorg_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建机构实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //处理新增机构父机构
                                subFrom.guidParents = obj.original.guid;
                                //增加方法
                                $scope.add = function (subFrom) {
                                    //TODO.新增逻辑
                                    abftree_service.addorg(subFrom).then(function (data) {
                                        console.log(data);
                                    });
                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            }
                        )
                    }
                }
            };
            return it;
        }else if(node.id.indexOf("@") != 0){
            var it = {
                "新建菜单":{
                    "id":"create",
                    "label":"新建机构",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        openwindow($uibModal, 'views/org/addorg_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建机构实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //处理新增机构父机构
                                subFrom.guidParents = obj.original.guid;
                                //增加方法
                                $scope.add = function (subFrom) {
                                    //TODO.新增逻辑
                                    abftree_service.addorg(subFrom).then(function (data) {
                                        console.log(data);
                                    });
                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            }
                        )
                    }
                },

                "删除菜单":{
                    "label":"删除机构",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if(confirm("确定要删除此菜单？删除后不可恢复。")){
                            //TODO.删除逻辑
                        }
                    }
                },
                "拷贝菜单":{
                    "label":"拷贝机构",
                    "action":function (node) {
                        var inst = jQuery.jstree.reference(node.reference),
                            obj = inst.get_node(node.reference);
                        var og  = $('#container').jstree(true).copy_node(obj,obj);
                        // console.log(obj)
                    }
                },

                "粘贴菜单":{
                    "label":"粘贴机构",
                    "action":function (node) {
                        var inst = jQuery.jstree.reference(node.reference),
                            obj = inst.get_node(node.reference);
                        var og  = $('#container').jstree(true).paste(node);
                        console.log(og)
                    }
                },
            }
            return it;

        }else{
            var it = {
                "新建菜单":{
                    "id":"create",
                    "label":"新建岗位",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        openwindow($uibModal, 'views/org/addPosition_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建机构实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //处理新增机构父机构
                                subFrom.GUID_PARENTS = obj.original.guid;
                                //增加方法
                                $scope.add = function (subFrom) {
                                    //TODO.新增逻辑
                                    abftree_service.addorg(subFrom).then(function (data) {
                                        console.log(data);
                                    });
                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            }
                        )
                    }
                },

                "删除菜单":{
                    "label":"删除岗位",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if(confirm("确定要删除此菜单？删除后不可恢复。")){
                            //TODO.删除逻辑
                        }
                    }
                },

            }
            return it;
        }

    };

    //工作组树
    $("#container").jstree({
        "core" : {
            "multiple":true,
            "themes" : {
                "responsive": false
            },
            // so that create works
            "check_callback" : true,
            'data' : function (obj, callback) {
                console.log(obj.id)
                var jsonarray = [];
                $scope.jsonarray = jsonarray;
                var subFrom = {};
                subFrom.id = obj.id;
                Workgroup_service.loadmaintree(subFrom).then(function (data) {
                    console.log(data)
                    for(var i = 0 ;i < data.length ; i++){
                        data[i].text = data[i].groupName;
                        data[i].children = true;
                        data[i].id = data[i].guid;
                    }
                    $scope.jsonarray = angular.copy(data);
                    callback.call(this, $scope.jsonarray);
                })
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
        "contextmenu":{'items':items},
        'dnd': {
            'dnd_start': function () {
                console.log("start");
            },
            'is_draggable':function (node) {
                //用于控制节点是否可以拖拽.
                console.log(node)
                return true;
            }
        },
        'search':{
            show_only_matches:true,

        },
        'callback' : {
            move_node:function (node) {
                console.log(node)
            }
        },

        "plugins" : [ "dnd", "state", "types","search","contextmenu" ]
    }).bind("move_node.jstree",function (e,data) {
        if(confirm("确认要移动此机构吗?")){
            //TODO.
        }else{
            // data.inst.refresh(data.inst._get_parent(data.rslt.oc));
            return false;
        }
        console.log(e);
        console.log(data);
    }).bind("dnd_stop.vakata",function (e,data) {
        console.log(data);
    }).bind("changed.jstree", function (e, data) {
        if(typeof data.node !== 'undefined'){//拿到结点详情
            // console.log(data.node.original.id.indexOf("@"));
            console.log(data.node.original);
            if(data.node.original.id == "00000"){
                for(var a in flag){
                    flag[a] = false;
                }
                flag.index = true;
            }else {
                for(var a in flag){
                    flag[a] = false;
                }
                flag.xqxx = true;
            }
            $scope.$apply();
        }
    });

    //生成工作组列表
    var workgroupgrid = {};
    $scope.workgroupgrid = workgroupgrid
    var initworkdata = function () {
        //调取工作组信息OM_GROUP
        Workgroup_service.loadallgroup().then(function (data) {
            console.log(data);
            $scope.workgroupgrid.data = data;
        })
    }
    //定义单选事件
    var selework = function () {

    }
    //定义表头名
    var com = [{ field: 'groupCode', displayName: '工作组代码', enableHiding: false},
        { field: 'groupName', displayName: '工作组名称', enableHiding: false},
        { field: 'groupStatus', displayName: '工作组状态', enableHiding: false},
        { field: 'groupType', displayName: '工作组类型', enableHiding: false},
        { field: 'guidEmpManager', displayName: '工作组管理员', enableHiding: false},
        { field: 'guidOrg', displayName: '所属机构', enableHiding: false},
        { field: 'startDate', displayName: '工作组有效起始日', enableHiding: false},
        { field: 'startDate', displayName: '工作组有效到期日', enableHiding: false},
        { field: 'lastupdate', displayName: '最后修改日', enableHiding: false}
    ]
    $scope.workgroupgrid = initgrid($scope,workgroupgrid,initworkdata(),filterFilter,com,false,selework);
    
    //新增根工作组
    workgroup.add = function () {
        openwindow($uibModal, 'views/Workgroup/addworkgroup_window.html', 'lg',
            function ($scope, $modalInstance) {
                //创建机构实例
                var subFrom = {};
                $scope.subFrom = subFrom;
                //增加方法
                $scope.add = function (subFrom) {
                    //TODO.新增逻辑
                    Workgroup_service.addgroup(subFrom).then(function (data) {
                        console.log(data);
                        if(data.status == "success"){
                            toastr['success']( data.status.message);
                            $scope.cancel();
                            initworkdata();
                        }else{
                            toastr['error']( "新增失败！");
                        }
                    });
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }
    //删除工作组
    workgroup.delete = function () {
        var item = $scope.workgroupgrid.getSelectedRows();
        console.log(item)
        console.log(item.length)
        if(item.length != 1 ){
            toastr['error']("请选择一条数据!");
            return false;
        }else if(item[0].groupStatus != "123"){
            toastr['error']("只能删除停用状态下的工作组!");
        }else{
            var subFrom = {};
            subFrom.id = item[0].groupCode;
            Workgroup_service.deletegroup(subFrom).then(function (data) {
                if(data.status == "success"){
                    console.log(data)
                    toastr['success']( data.message);
                    initworkdata();
                }else{
                    toastr['error']( data.message);
                }
            })
        }
    }
    //详情页绑定的对象
    var sub = {};
    $scope.sub = sub;

    //编辑工作组
    workgroup.edit = function () {
        var item = $scope.workgroupgrid.getSelectedRows();
        $("#container").jstree().open_node(true);
        console.log($("#container").jstree().data)
        if(item.length != 1 ){
            toastr['error']("请选择一条数据!");
            return false;
        }else{
            for(var a in flag){
                flag[a] = false;
            }
            flag.xqxx = true;
            $scope.sub = item[0];
            $("#container").jstree().deselect_all(true);
            for(var i = 0; i < $scope.jsonarray.length ; i ++){
                console.log(item)
                if(item[0].guid == $scope.jsonarray[i].guid){
                    $("#container").jstree().select_node($scope.jsonarray[i],false,true);
                }
            }
            console.log($scope.sub)
        }
    }
});