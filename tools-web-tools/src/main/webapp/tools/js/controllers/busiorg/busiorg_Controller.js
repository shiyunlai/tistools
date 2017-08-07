/**
 * Created by gaojie on 2017/8/2.
 */
angular.module('MetronicApp').controller('busiorg_controller', function ($rootScope, $scope, busiorg_service, $http, $timeout, i18nService, filterFilter, uiGridConstants, $uibModal, $state) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        App.initAjax();
    });
    /**-----------------------------------各类变量定义-------------------------------- */
        //主题信息
    var busiorg = {};
    $scope.busiorg = busiorg;
    //节点导航信息
    var currNode = "";
    $scope.currNode = currNode;
    //存放节点对象
    var item = {};
    $scope.busiorg.item = item;
    //页签控制对象
    var flag = {};
    $scope.flag = flag;
    //机构套别
    var jgtb = true;
    $scope.flag.jgtb = jgtb;
    //页签页面
    var page = false;
    $scope.flag.page = page;
    /**-----------------------------------右键自定义菜单-------------------------------- */
        //树自定义右键功能
    var items = function customMenu(node) {
            if (node.parent == "#") {
                var it = {
                    "刷新菜单": {
                        "id": "refresh",
                        "label": "刷新",
                        "action": function (data) {
                            var inst = jQuery.jstree.reference(data.reference),
                                obj = inst.get_node(data.reference);
                            console.log(obj)
                            $("#busiorgtree").jstree().refresh();
                        }
                    }
                }
            } else if (node.id.length == 3) {
                var it = {
                    "新增子机构": {
                        "id": "creat",
                        "label": "新增子机构",
                        "action": function (data) {
                            var inst = jQuery.jstree.reference(data.reference),
                                obj = inst.get_node(data.reference);
                            console.log(obj)
                        }
                    },
                    "新增子虚拟机构": {
                        "id": "creat2",
                        "label": "新增子虚拟机构",
                        "action": function (data) {
                            var inst = jQuery.jstree.reference(data.reference),
                                obj = inst.get_node(data.reference);
                            console.log(obj)
                        }
                    },
                    "删除业务机构": {
                        "id": "delete",
                        "label": "删除业务机构",
                        "action": function (data) {
                            var inst = jQuery.jstree.reference(data.reference),
                                obj = inst.get_node(data.reference);
                            console.log(obj)
                        }
                    },
                    "刷新菜单": {
                        "id": "refresh",
                        "label": "刷新",
                        "action": function (data) {
                            var inst = jQuery.jstree.reference(data.reference),
                                obj = inst.get_node(data.reference);
                            console.log(obj)
                            $("#busiorgtree").jstree().refresh();
                        }
                    }
                };
            }
            return it;
        }


    /**-----------------------------------业务机构树-------------------------------- */
    //业务机构树
    $("#busiorgtree").jstree({
        "core": {
            "themes": {
                "responsive": false
            },
            "check_callback": true,
            'data': function (obj, callback) {
                var jsonarray = [];
                $scope.jsonarray = jsonarray;
                var subFrom = {};
                subFrom.id = obj.id;
                console.log(obj)


                busiorg_service.loadmaintree(subFrom).then(function (datas) {
                    var data = datas.retMessage;
                    console.log(data);
                    if(isNull(data[0].id)){
                        console.log(123)
                        for (var i = 0; i < data.length; i++) {
                            data[i].id = data[i].sendValue;
                            data[i].text = data[i].itemName;
                            data[i].children = true;
                            data[i].icon = 'fa fa-users icon-state-info icon-lg'
                        }
                    }else if(data[0].d){

                    } else{
                        for (var i = 0; i < data.length; i++) {
                            data[i].children = true;
                            data[i].icon = 'fa fa-users icon-state-info icon-lg'
                        }
                    }

                    $scope.jsonarray = angular.copy(data);
                    callback.call(this, $scope.jsonarray);
                })
            }
        },
        "types": {
            "default": {
                "icon": "fa fa-folder icon-state-warning icon-lg"
            },
            "file": {
                "icon": "fa fa-file icon-state-warning icon-lg"
            }
        },
        "state": {"key": "demo3"},
        "contextmenu": {'items': items},
        'dnd': {
            'dnd_start': function () {
                console.log("start");
            },
            'is_draggable': function (node) {
                //用于控制节点是否可以拖拽.
                console.log(node)
                return true;
            }
        },
        'search': {
            show_only_matches: true
        },
        'callback': {
            move_node: function (node) {
                console.log(node)
            }
        },
        "plugins": ["dnd", "state", "types", "search", "contextmenu"]
    }).bind("select_node.jstree", function (e, data) {
        if (typeof data.node !== 'undefined') {//拿到结点详情
            // console.log(data.node.original.id.indexOf("@"));
            $scope.busiorg.item = {};
            console.log(data.node);
            if (data.node.id == "00000") {
                for (var i in $scope.flag) {
                    flag[i] = false;
                }
                $scope.flag.jgtb = true;
                rebusiorgGrid();
            } else if (data.node.id.length == 3) {
                for (var i in $scope.flag) {
                    flag[i] = false;
                }
                $scope.flag.page = true;
                $scope.flag.xqxx = true;
                $scope.busiorg.item = data.node.original;
            }
            ($scope.$$phase) ? null : $scope.$apply();
        }
    });
    /**-----------------------------------生成各类列表-------------------------------- */
    //设置列表语言
    i18nService.setCurrentLang("zh-cn");
    //业务套别列表
    var busiorgGrid = {};
    $scope.busiorgGrid = busiorgGrid;
    //表头
    var com = [{field: 'itemName', displayName: '业务机构套别名称', enableHiding: false},
        {field: 'itemValue', displayName: '字典项', enableHiding: false}]
    $scope.busiorgGrid = initgrid($scope, busiorgGrid, filterFilter, com, false, function () {
    });
    //下级业务机构列表
    var loworgGrid = {};
    $scope.loworgGrid = loworgGrid;
    com = [{ field: 'nodeType', displayName: '节点类型', enableHiding: false},
        { field: 'busiorgode', displayName: '业务机构代码', enableHiding: false},
        { field: 'busiDomain', displayName: '业务线条', enableHiding: false},
        { field: 'busiorgName', displayName: '业务机构名称', enableHiding: false},
        { field: 'guidOrg', displayName: '对应实体机构', enableHiding: false},
        { field: 'guidPosition', displayName: '主管岗位', enableHiding: false},
        { field: 'orgCode', displayName: '机构代号', enableHiding: false}
    ]
    $scope.loworgGrid = initgrid($scope, loworgGrid, filterFilter, com, false, function () {
    });

    /**-----------------------------------各类列表数据拉取方法-------------------------------- */
        //业务套别列表拉取方法
    var rebusiorgGrid = function () {
            busiorg_service.loadywtb().then(function (data) {
                console.log(data.retMessage);
                if (data.status == "success") {
                    $scope.busiorgGrid.data = data.retMessage;
                    $scope.busiorgGrid.mydefalutData = data.retMessage;
                    $scope.busiorgGrid.getPage(1, $scope.busiorgGrid.paginationPageSize);
                } else {

                }
            })
        }
    //下级业务机构列表拉取
    var reloworgGrid = function () {
        busiorg_service.loadloworg().then(function (data) {
            console.log(data.retMessage);
            if (data.status == "success") {
                $scope.loworgGrid.data = data.retMessage;
                $scope.loworgGrid.mydefalutData = data.retMessage;
                $scope.loworgGrid.getPage(1, $scope.busiorgGrid.paginationPageSize);
            } else {

            }
        })
    }

    /**-----------------------------------各类按钮事件定义-------------------------------- */


    /**-----------------------------------页签控制-------------------------------- */
    busiorg.loaddata = function (num) {
        if(num == 0){
            //控制页签
            for (var i in $scope.flag) {
                flag[i] = false;
            }
            $scope.flag.page = true;
            $scope.flag.xjjg = true;
            //拉取下级业务机构列表
            reloworgGrid();
        }
    }
});