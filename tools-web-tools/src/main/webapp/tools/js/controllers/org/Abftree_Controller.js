/**
 * Created by gaojie on 2017/5/9.
 */
angular.module('MetronicApp').controller('abftree_controller', function ($rootScope, $scope,$window, abftree_service, $http, $timeout, i18nService, filterFilter, uiGridConstants, $uibModal, $state,dictonary_service) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        App.initAjax();
    });
    var abftree = {};
    $scope.abftree = abftree;
    //控制是否是修改状态
    var editflag = false;
    $scope.editflag = editflag;
    //控制页签跳转
    var flag = {};
    $scope.flag = flag;
    //下级机构页签控制
    var xjjg = false;
    flag.xjjg = xjjg;
    //员工信息页签控制
    var ygxx = false;
    flag.ygxx = ygxx;
    //机构信息展示页
    var xqxx = true;
    flag.xqxx = xqxx;
    //岗位页签跳转控制
    var gwflag = {};
    $scope.gwflag = gwflag;
    //岗位详情
    var gwxx = false;
    gwflag.gwxx = gwxx;
    //岗位员工列表
    var gwyg = false;
    gwflag.gwyg = gwyg;
    //岗位应用列表
    var gwyy = false;
    gwflag.gwyy = gwyy;
    //下级岗位列表
    var xjgw = false;
    gwflag.xjgw = xjgw;
    //岗位权限列表;
    var gwqx = false;
    gwflag.gwqx = gwqx;
    //岗位列表
    var gwlb = false;
    gwflag.gwlb = gwlb;
    //首页清空
    $scope.flag.index = false;
    i18nService.setCurrentLang("zh-cn");
    //机构,岗位页签切换
    var tabflag = true;//true为机构详情
    $scope.tabflag = tabflag;
    //节点导航栏
    var currNode = "";
    $scope.currNode = currNode;
    //拖拽方法控制标志
    var dndflag = false;
    $scope.dndflag = dndflag;

    //生成公共方法
    initController($scope, abftree, "abftree", abftree, filterFilter);
    var item = {};
    $scope.item = item;
    abftree.item = item;
    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageContentWhite = true;
    $rootScope.settings.layout.pageBodySolid = false;
    $rootScope.settings.layout.pageSidebarClosed = false;
    //树过滤
    // $("#s").submit(function(e) {
    //     e.preventDefault();
    //     $("#container").jstree(true).search($("#q").val());
    // });

    //树自定义右键功能
    var items = function customMenu(node) {
        // The default set of all items
        var control;
        console.log(node);
        if (node.parent == "#") {
            var it = {
                "新建菜单": {
                    "id": "create",
                    "label": "新建根机构",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        openwindow($uibModal, 'views/org/addrootorg_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建机构实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //根机构标识
                                subFrom.flag = "root";
                                //存放机构等级字典翻译
                                //生成机构代码
                                var next = true;
                                $scope.next = next;
                                $scope.skip = function () {
                                    if (isNull(subFrom.orgDegree) || isNull(subFrom.AREA)) {
                                        toastr['error']("请填写相关信息!");
                                        return false;
                                    }
                                    //调用服务生成机构代码
                                    abftree_service.initcode(subFrom).then(function (data) {
                                        if(data.status == "error"){
                                            toastr['error']( data.retMessage);
                                        }else{
                                            subFrom.orgCode = data.retMessage;
                                            $scope.next = !next;
                                        }
                                    })

                                }
                                //处理新增机构父机构
                                subFrom.guidParents = null;
                                //增加方法
                                $scope.add = function (subFrom) {
                                    //TODO.新增逻辑
                                    abftree_service.addorg(subFrom).then(function (data) {
                                        if (data.status == "success") {
                                            toastr['success'](data.retMessage);
                                        } else {
                                            toastr['error'](data.retMessage);
                                        }
                                        $("#container").jstree().refresh();
                                        $scope.cancel();
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
        } else if (!isNull(node.original.orgCode) && node.original.orgCode.indexOf("GW") != 0) {
            var it = {
                "新建菜单": {
                    "id": "create",
                    "label": "新建子机构",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        openwindow($uibModal, 'views/org/addorg_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建机构实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                subFrom.flag = "child";
                                //生成机构代码
                                var next = true;
                                $scope.next = next;
                                $scope.skip = function () {
                                    if (isNull(subFrom.orgDegree) || isNull(subFrom.AREA)) {
                                        toastr['error']("请填写相关信息!");
                                        return false;
                                    }
                                    //调用服务生成机构代码
                                    abftree_service.initcode(subFrom).then(function (data) {
                                        if(data.status == "error"){
                                            toastr['error']( data.retMessage);
                                        }else{
                                            subFrom.orgCode = data.retMessage;
                                            $scope.next = !next;
                                        }
                                    })

                                }
                                //处理新增机构父机构
                                subFrom.guidParents = obj.original.guid;
                                //增加方法
                                $scope.add = function (subFrom) {
                                    //TODO.新增逻辑
                                    abftree_service.addorg(subFrom).then(function (data) {
                                        if (data.status == "success") {
                                            toastr['success'](data.retMessage);
                                        } else {
                                            toastr['error'](data.retMessage);
                                        }
                                        $("#container").jstree().refresh();
                                        $scope.cancel();
                                    });
                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            }
                        )
                    }
                },

                "删除菜单": {
                    "label": "删除机构",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if (confirm("确定要删除此菜单？删除后不可恢复。")) {
                            var subFrom = {};
                            subFrom.orgCode = obj.original.orgCode;
                            abftree_service.deleteOrg(subFrom).then(function (data) {
                                if (data.status == "success") {
                                    toastr['success'](data.retMessage);
                                    $("#container").jstree().refresh();
                                } else {
                                    toastr['error'](data.retMessage);
                                }
                            })
                        }
                    }
                },
                "拷贝菜单": {
                    "label": "拷贝机构",
                    "action": function (node) {
                        var inst = jQuery.jstree.reference(node.reference),
                            obj = inst.get_node(node.reference);
                        var node = {};
                        node.id = "99999";
                        var og = inst.copy(obj);
                        console.log(og)
                    }
                },

                "粘贴菜单": {
                    "label": "粘贴机构",
                    "action": function (node) {
                        var inst = jQuery.jstree.reference(node.reference),
                            obj = inst.get_node(node.reference);
                        var og = $('#container').jstree(true).paste(node);
                        console.log(og)
                    }
                },
            }
            return it;

        } else if (node.id.indexOf("POSITION") == 0) {
            var it = {
                "新建菜单": {
                    "id": "create",
                    "label": "新建子岗位",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        var subFrom = {};
                        subFrom.guidParents = obj.original.guid;
                        subFrom.guidOrg = obj.original.guidOrg;
                        //调用封装方法
                        creatNewPosition(subFrom, null);
                        // openwindow($uibModal, 'views/org/addPosition_window.html', 'lg',
                        //     function ($scope, $modalInstance) {
                        //         //创建机构实例
                        //         var subFrom = {};
                        //         $scope.subFrom = subFrom;
                        //         //控制表单
                        //         var next = true;
                        //         $scope.next = next;
                        //         //判断是根岗位还是子岗位
                        //         if(obj.id.indexOf("GW") == 0){
                        //             //处理新增机构父机构
                        //             subFrom.guidParents = "";
                        //             subFrom.guidOrg = obj.original.guid;
                        //         }else{
                        //             subFrom.guidParents = obj.original.guid;
                        //             subFrom.guidOrg = obj.original.guidOrg;
                        //         }
                        //         //下一步
                        //         $scope.skip = function () {
                        //             abftree_service.initPosCode($scope.subFrom).then(function (data) {
                        //                 console.log(data)
                        //                 if(data.status == "success"){
                        //                     $scope.subFrom.positionCode = data.retMessage;
                        //                 }else{
                        //                     toastr['error'](data.retMessage);
                        //                 }
                        //             })
                        //             $scope.next = !$scope.next;
                        //         }
                        //         //增加方法
                        //         $scope.add = function (subFrom) {
                        //             //TODO.新增逻辑
                        //             abftree_service.addposit(subFrom).then(function (data) {
                        //                 if(data.status == "success"){
                        //                     toastr['success'](data.retMessage);
                        //                 }else{
                        //                     toastr['error'](data.retMessage);
                        //                 }
                        //                 $("#container").jstree().refresh();
                        //                 $scope.cancel();
                        //             });
                        //         }
                        //         $scope.cancel = function () {
                        //             $modalInstance.dismiss('cancel');
                        //         };
                        //     }
                        // )
                    }
                },

                "删除菜单": {
                    "label": "删除岗位",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if (confirm("确定要删除此菜单？删除后不可恢复。")) {
                            var subFrom = {};
                            subFrom.posCode = obj.original.positionCode;
                            abftree_service.deletePosition(subFrom).then(function (data) {
                                if (data.status == "success") {
                                    toastr['success'](data.retMessage);
                                    var sele = function sele() {
                                        var node = {};
                                        node.id = "99999"
                                        $("#container").jstree().select_node(node);
                                    }
                                    $("#container").jstree().refresh(sele());
                                } else {
                                    toastr['error'](data.retMessage);
                                }
                            })
                        }
                    }
                }
            }
            return it;
        } else {
            var it = {
                "新建菜单": {
                    "id": "create",
                    "label": "新建岗位",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj);
                        var subFrom = {};
                        //处理新增机构父机构
                        subFrom.guidParents = "";
                        subFrom.guidOrg = obj.original.guid;
                        creatNewPosition(subFrom, null);
                    }
                }

            }
            return it;
        }

    };
    ;
    //组织机构树
    $("#container").jstree({
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
                if (!isNull(obj.original)) {
                    subFrom.guidOrg = obj.original.guid;
                    subFrom.positionCode = obj.original.positionCode;
                } else {
                    subFrom.guidOrg = "";
                    subFrom.positionCode = "";
                }

                abftree_service.loadmaintree(subFrom).then(function (datas) {
                    var data = datas.retMessage;
                    if (isNull(data)) {

                    } else if (isNull(data[0].orgName)) {
                        for (var i = 0; i < data.length; i++) {
                            data[i].text = data[i].positionName;
                            data[i].children = true;
                            data[i].id = data[i].guid;
                            data[i].icon = 'fa fa-users icon-state-info icon-lg'

                        }
                    } else {
                        for (var i = 0; i < data.length; i++) {
                            data[i].text = data[i].orgName;
                            data[i].children = true;
                            data[i].id = data[i].orgCode;
                            data[i].icon = 'fa fa-institution  icon-state-info icon-lg';
                            data[i].position = parseInt(data[i].sortNo);
                            if (data[i].orgName == "岗位信息") {
                                data[i].icon = 'fa fa-users  icon-state-info icon-lg';
                            }
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
            'is_draggable': function (node) {
                //用于控制节点是否可以拖拽.
                console.log(node)
                var node = node[0];
                if (node.id == "99999" || node.id.indexOf("GW") == 0) {
                    return false;
                } else {
                    return true;
                }
            },
            'dnd_start': function () {
                console.log("start");
            },
        },
        'search': {
            show_only_matches: true,
        },
        'callback': {
            move_node: function (node) {
                console.log(node)
            }
        },

        "plugins": ["dnd", "state", "types", "search", "contextmenu"]
    }).bind("copy.jstree", function (node, e, data) {
        console.log(e);
        console.log(data);
        console.log(node);
    }).bind("paste.jstree", function (a, b, c, d) {
        console.log(a);
        console.log(b);
        console.log(c);
        console.log(d);
    }).bind("move_node.jstree", function (e, data) {
        if (confirm("确认要移动此机构吗?")) {
            console.log(data);
        } else {

            $("#container").jstree().refresh();
        }
    }).bind("select_node.jstree", function (e, data) {
        if (typeof data.node !== 'undefined') {//拿到结点详情
            // console.log(data.node.original.id.indexOf("@"));
            $scope.abftree.item = {};
            console.log(data.node.original);
            $scope.currNode = data.node.text;
            if (data.node.original.id.indexOf("POSIT") == 0) {
                for (var i in $scope.flag) {
                    flag[i] = false;
                }
                for (var i in $scope.gwflag) {
                    gwflag[i] = false;
                }
                $scope.flag.index = true;
                $scope.gwflag.gwxx = true;
                $scope.tabflag = false;
                $scope.abftree.item = data.node.original;
            } else if (data.node.original.id.indexOf("9999") == 0) {
                for (var i in $scope.flag) {
                    flag[i] = false;
                }
                for (var i in $scope.gwflag) {
                    gwflag[i] = false;
                }
                $scope.flag.index = false;
            } else if (data.node.original.id.indexOf("GW") == 0) {
                for (var i in $scope.gwflag) {
                    gwflag[i] = false;
                }
                for (var i in $scope.flag) {
                    flag[i] = false;
                }
                $scope.gwflag.gwlb = true;
                $scope.flag.index = true;
                var subFrom = {};
                subFrom.orgGuid = data.node.original.guid;
                $scope.abftree.item = data.node.original;
                regwlbgird(subFrom)
            } else {
                for (var i in $scope.gwflag) {
                    gwflag[i] = false;
                }
                for (var i in $scope.flag) {
                    flag[i] = false;
                }
                $scope.flag.index = true;
                $scope.flag.xqxx = true;
                $scope.tabflag = true;
                $scope.abftree.item = data.node.original;
            }

            ($scope.$$phase) ? null : $scope.$apply();
        }
    });
    //dnd插件事件监听
    $(document).on('dnd_start.vakata',function(e,data){
        console.log(e)
    });
    //jstree 自定义筛选事件
    //筛选字段
    $scope.searchitem = "";
    //清空
    abftree.clear = function () {
        $scope.searchitem = "";
        $scope.showtree = true;
        if( $("#searchtree").jstree()){
            $("#searchtree").jstree().destroy();
        }
        console.log($("#container").jstree().get_json())
    }

    //控制2个树显示标识,true为默认值,false为筛选状态
    var showtree = true;
    $scope.showtree = showtree;
    // $scope.$watch('searchitem', function (newValue, oldValue) {
    //     console.log(newValue)
    //     var timeid;
    //     if (isNull(newValue)) {
    //         $scope.showtree = true;
    //     } else {
    //         $scope.showtree = false;
    //         if(timeid){
    //             console.log(timeid)
    //             clearInterval(timeid)
    //         }else {
    //             timeid = setInterval(function () {
    //                 console.log(123)
    //
    //                 clearInterval(timeid)
    //
    //             }, 500);
    //         }
    //     }
    // }, true);
    //用于修改的实例
    var position = {};
    $scope.position = position;


    abftree.test = function () {
        console.log(1)
    }

    //删除
    abftree.delete = function () {
        if (confirm("确认要删除此机构吗?")) {
            //TODO.删除逻辑
            var subFrom = {};
            subFrom.orgCode = $scope.abftree.item.orgCode;
            abftree_service.deleteOrg(subFrom).then(function (data) {
                if (data.status == "success") {
                    toastr['success'](data.retMessage);
                    $("#container").jstree().refresh();
                } else {
                    toastr['error'](data.retMessage);
                }
            })
        }
    }

    //启用
    abftree.enable = function () {
        var orgCode = $scope.abftree.item.orgCode;
        openwindow($uibModal, 'views/org/enablecom_window.html', 'lg',
            function ($scope, $modalInstance) {
                //创建参数实体
                var subFrom = {};
                $scope.subFrom = subFrom;
                subFrom.orgCode = orgCode;
                subFrom.flag = "0";
                $scope.save = function (subFrom) {
                    abftree_service.enableorg(subFrom).then(function (data) {
                        console.log(data)
                        if (data.status == "success") {
                            toastr['success'](data.retMessage);
                            $("#container").jstree().refresh();
                        } else {
                            toastr['error'](data.retMessage);
                        }
                        $scope.cancel();
                    });
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }
    //停用
    abftree.disenable = function () {
        var orgCode = $scope.abftree.item.orgCode;
        var subFrom = {};
        subFrom.orgCode = orgCode;
        subFrom.flag = "3"
        abftree_service.enableorg(subFrom).then(function (data) {
            console.log(data)
            if (data.status == "success") {
                toastr['success'](data.retMessage);
                $("#container").jstree().refresh();
            } else {
                toastr['error'](data.retMessage);
            }
        })
    }
    abftree.cancelorg = function () {
        var subFrom = {};
        subFrom.orgCode = $scope.abftree.item.orgCode;
        subFrom.flag = "1"
        abftree_service.enableorg(subFrom).then(function (data) {
            console.log(data)
            if (data.status == "success") {
                toastr['success'](data.retMessage);
                $("#container").jstree().refresh();
            } else {
                toastr['error'](data.retMessage);
            }
        })
    }
    //重新启用
    abftree.reenable = function () {
        var subFrom = {};
        subFrom.orgCode = $scope.abftree.item.orgCode;
        subFrom.flag = "2"
        abftree_service.enableorg(subFrom).then(function (data) {
            if (data.status == "success") {
                toastr['success'](data.retMessage);
                $("#container").jstree().refresh();
            } else {
                toastr['error'](data.retMessage);
            }
        })
    }

    abftree.enableposition = function () {
        var subFrom = {};
        subFrom.posCode = $scope.abftree.item.positionCode;
        subFrom.flag = "1";
        abftree_service.enableposition(subFrom).then(function (data) {
            if (data.status == "success") {
                toastr['success'](data.retMessage);
                $("#container").jstree().refresh();
            } else {
                toastr['error'](data.retMessage);
            }
        })
    }
    abftree.cancelposition = function () {
        var subFrom = {};
        subFrom.posCode = $scope.abftree.item.positionCode;
        subFrom.flag = "2";
        abftree_service.enableposition(subFrom).then(function (data) {
            if (data.status == "success") {
                toastr['success'](data.retMessage);
                $("#container").jstree().refresh();
            } else {
                toastr['error'](data.retMessage);
            }
        })
    }
    abftree.deletePosition = function () {
        var subFrom = {};
        subFrom.posCode = $scope.abftree.item.positionCode;
        abftree_service.deletePosition(subFrom).then(function (data) {
            if (data.status == "success") {
                toastr['success'](data.retMessage);
                $("#container").jstree().refresh();
            } else {
                toastr['error'](data.retMessage);
            }
        })
    }

    //修改
    abftree.edit = function () {
        $scope.editflag = !$scope.editflag;
        $scope.position = angular.copy($scope.abftree.item);
    }

    //覆盖searchN方法
    abftree.searchN = function () {

    }
    //保存
    abftree.save = function () {
        abftree_service.updateOrg($scope.position).then(function (data) {
            if (data.status == "success") {
                toastr['success'](data.retMessage);
            } else {
                toastr['error'](data.retMessage);
            }
            $("#container").jstree().refresh();
            console.log(data)
            $scope.editflag = !$scope.editflag;
        })

        //TODO
        //保存逻辑
    }
    //点击页签,加载指定数据
    abftree.loaddata = function (type) {
        //type加载的数据,0--下级机构,1--员工信息,2--岗位信息,999-机构详情
        if (type == 0) {
            console.log($scope.abftree.item.guid)
            $scope.flag.xqxx = false;
            $scope.flag.xjjg = true;
            $scope.flag.ygxx = false;
            $scope.flag.qxxx = false;
            //通过GUID查询下级机构信息,生成下级机构列表
            var gridOptions2 = {};
            $scope.gridOptions2 = gridOptions2;
            //定义单选事件
            var selework = function () {

            }
            //定义表头名
            var com = [{field: 'orgCode', displayName: '机构代码', enableHiding: false},
                {field: 'orgName', displayName: '机构名称', enableHiding: false},
                {field: 'orgType', displayName: '机构类型', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.orgType | translateConstants :\'DICT_OM_ORGTYPE\') + $root.constant[\'DICT_OM_ORGTYPE-\'+row.entity.orgType]}}</div>'},
                {field: 'orgDegree', displayName: '机构等级', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.orgDegree | translateConstants :\'DICT_OM_ORGDEGREE\') + $root.constant[\'DICT_OM_ORGDEGREE-\'+row.entity.orgDegree]}}</div>'},
                {field: 'orgStatus', displayName: '机构状态', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.orgStatus | translateConstants :\'DICT_OM_ORGSTATUS\') + $root.constant[\'DICT_OM_ORGSTATUS-\'+row.entity.orgStatus]}}</div>'},
                {field: 'orgAddr', displayName: '机构地址', enableHiding: false},
                {field: 'guidEmpMaster', displayName: '机构主管', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidEmpMaster | translateEmp) + $root.constant[row.entity.guidEmpMaster]}}</div>'},
                {field: 'linkMan', displayName: '联系人姓名', enableHiding: false},
                {field: 'linkTel', displayName: '联系电话', enableHiding: false},
                {field: 'createTime', displayName: '创建时间', enableHiding: false}
            ]
            $scope.gridOptions2 = initgrid($scope, gridOptions2, filterFilter, com, true, selework);
            //塞入测试数组
            list.push($scope.gridOptions2);
            var regridOptions2 = function () {
                var subFrom = {};
                subFrom.orgCode = $scope.abftree.item.orgCode;
                console.log(subFrom.orgCode)
                //调取工作组信息OM_GROUP
                abftree_service.loadxjjg(subFrom).then(function (data) {
                    console.log(data.retMessage)
                    if (data.status == "success") {
                        $scope.gridOptions2.data = data.retMessage;
                        $scope.gridOptions2.mydefalutData = data.retMessage;
                        $scope.gridOptions2.getPage(1, $scope.gridOptions2.paginationPageSize);
                    } else {

                    }

                })
            }
            //拉取列表
            regridOptions2();
            //下级机构新增
            abftree.addxjjg = function () {
                var id = $scope.abftree.item.guid;
                openwindow($uibModal, 'views/org/addorg_window.html', 'lg',
                    function ($scope, $modalInstance) {
                        //创建机构实例
                        var subFrom = {};
                        $scope.subFrom = subFrom;
                        subFrom.flag = "child";
                        //生成机构代码
                        var next = true;
                        $scope.next = next;
                        $scope.skip = function () {
                            if (isNull(subFrom.orgDegree) || isNull(subFrom.AREA)) {
                                toastr['error']("请填写相关信息!");
                                return false;
                            }
                            //调用服务生成机构代码
                            abftree_service.initcode(subFrom).then(function (data) {
                                if(data.status == "error"){
                                    toastr['error']( data.retMessage);
                                }else{
                                    subFrom.orgCode = data.retMessage;
                                    $scope.next = !next;
                                }
                            })

                        }
                        //处理新增机构父机构
                        subFrom.guidParents = id;
                        //增加方法
                        $scope.add = function (subFrom) {
                            //TODO.新增逻辑
                            abftree_service.addorg(subFrom).then(function (data) {
                                if (data.status == "success") {
                                    toastr['success'](data.retMessage);
                                } else {
                                    toastr['error'](data.retMessage);
                                }
                                $("#container").jstree().refresh();
                                $scope.cancel();
                            });
                        }
                        $scope.cancel = function () {
                            $modalInstance.dismiss('cancel');
                        };
                    }
                )
            }
            //编辑下级机构信息
            abftree.editxjjg = function () {
                var arr = $scope.gridOptions2.getSelectedRows();
                if (arr.length != 1) {
                    toastr['error']("请选择一条记录！");
                    return false;
                } else {
                    for (var a in $scope.flag) {
                        flag[a] = false;
                    }
                    $scope.flag.index = true;
                    $scope.flag.xqxx = true;
                    console.log($scope.flag);
                    var node = {};
                    node.id = arr[0].orgCode;
                    var node2 = {};
                    node2.id = $scope.abftree.item.id;
                    $("#container").jstree().deselect_all(true);
                    $("#container").jstree().load_node(node2, function () {
                        $("#container").jstree().select_node(node, false, false);
                    });
                }

            }


        } else if (type == 1) {

            $scope.flag.xqxx = false;
            $scope.flag.xjjg = false;
            $scope.flag.ygxx = true;
            $scope.flag.qxxx = false;
            //todo
            //通过GUID查询员工列表
            //生成员工列表
            var gridOptions1 = {};
            $scope.gridOptions1 = gridOptions1;
            //定义单选事件
            var selework = function () {

            }
            //定义表头名
            var com = [{field: 'empCode', displayName: '员工代码', enableHiding: false},
                {field: 'empName', displayName: '员工姓名', enableHiding: false},
                {field: 'gender', displayName: '性别', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.gender | translateConstants :\'DICT_OM_GENDER\') + $root.constant[\'DICT_OM_GENDER-\'+row.entity.gender]}}</div>'},
                {field: 'empstatus', displayName: '员工状态', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.empstatus | translateConstants :\'DICT_OM_EMPSTATUS\') + $root.constant[\'DICT_OM_EMPSTATUS-\'+row.entity.empstatus]}}</div>'},
                {field: 'empDegree', displayName: '员工职级', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.empDegree | translateConstants :\'DICT_OM_EMPDEGREE\') + $root.constant[\'DICT_OM_EMPDEGREE-\'+row.entity.empDegree]}}</div>'},
                {field: 'guidPostition', displayName: '基本岗位', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidPostition | translatePosition) + $root.constant[row.entity.guidPostition]}}</div>'},
                {field: 'guidempmajor', displayName: '直接主管', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidempmajor | translateEmp) + $root.constant[row.entity.guidempmajor]}}</div>'},
                {field: 'indate', displayName: '入职日期', enableHiding: false},
                {field: 'otel', displayName: '办公电话', enableHiding: false}
            ]
            $scope.gridOptions1 = initgrid($scope, gridOptions1, filterFilter, com, true, selework);
            list.push($scope.gridOptions1)
            var regridOptions1 = function () {
                var subFrom = {};
                subFrom.guid = $scope.abftree.item.guid;
                console.log(subFrom.guid)
                //调取工作组信息OM_GROUP
                abftree_service.loadempbyorg(subFrom).then(function (data) {
                    console.log(data)
                    if (data.status == "success" && !isNull(data.retMessage)) {
                        $scope.gridOptions1.data = data.retMessage;
                        $scope.gridOptions1.mydefalutData = data.retMessage;
                        $scope.gridOptions1.getPage(1, $scope.gridOptions1.paginationPageSize);
                    } else {
                        $scope.gridOptions1.data = [];
                        $scope.gridOptions1.mydefalutData = [];
                        $scope.gridOptions1.getPage(1, $scope.gridOptions1.paginationPageSize);
                    }

                })
            }
            //拉取列表
            regridOptions1();

            //机构下新增人员信息
            abftree.addyg = function () {
                console.log($scope.abftree.item.guid);
                var id = $scope.abftree.item.guid;
                openwindow($uibModal, 'views/org/addsearhgrid_window.html', 'lg',
                    function ($scope, $modalInstance) {
                        $scope.title = "添加员工";
                        //加载不在本机构下的员工信息,生成列表
                        var commonGrid = {};
                        $scope.commonGrid = commonGrid;
                        //定义单选事件
                        var selework = function () {

                        }
                        //定义表头名
                        var com = [{field: 'empCode', displayName: '员工代码', enableHiding: false},
                            {field: 'empName', displayName: '员工姓名', enableHiding: false},
                            {field: 'gender', displayName: '性别', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.gender | translateConstants :\'DICT_OM_GENDER\') + $root.constant[\'DICT_OM_GENDER-\'+row.entity.gender]}}</div>'},
                            {field: 'empstatus', displayName: '员工状态', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.empstatus | translateConstants :\'DICT_OM_EMPSTATUS\') + $root.constant[\'DICT_OM_EMPSTATUS-\'+row.entity.empstatus]}}</div>'},
                            {field: 'empDegree', displayName: '员工职级', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.empDegree | translateConstants :\'DICT_OM_EMPDEGREE\') + $root.constant[\'DICT_OM_EMPDEGREE-\'+row.entity.empDegree]}}</div>'},
                            {field: 'guidPostition', displayName: '基本岗位', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidPostition | translatePosition) + $root.constant[row.entity.guidPostition]}}</div>'},
                            {field: 'guidempmajor', displayName: '直接主管', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidempmajor | translateEmp) + $root.constant[row.entity.guidempmajor]}}</div>'},
                            {field: 'indate', displayName: '入职日期', enableHiding: false},
                            {field: 'otel', displayName: '办公电话', enableHiding: false}
                        ]
                        $scope.commonGrid = initgrid($scope, commonGrid, filterFilter, com, true, selework);

                        var recommonGrid = function () {
                            var subFrom = {};
                            subFrom.guid = id;
                            console.log(subFrom.guid)
                            //调取工作组信息OM_GROUP
                            abftree_service.loadempNotinorg(subFrom).then(function (data) {
                                if (data.status == "success") {
                                    // for(var i = 0;i<data.retMessage.length;i++){
                                    //     data.retMessage[i].birthdate = FormatDate(data.retMessage[i].birthdate);
                                    //     data.retMessage[i].indate = FormatDate(data.retMessage[i].indate);
                                    //     data.retMessage[i].outdate = FormatDate(data.retMessage[i].outdate);
                                    //     data.retMessage[i].regdate = FormatDate(data.retMessage[i].regdate);
                                    //     data.retMessage[i].lastmodytime = FormatDate(data.retMessage[i].lastmodytime);
                                    //     data.retMessage[i].createtime = FormatDate(data.retMessage[i].createtime);
                                    // }
                                    $scope.commonGrid.data = data.retMessage;
                                    $scope.commonGrid.mydefalutData = data.retMessage;
                                    $scope.commonGrid.getPage(1, $scope.commonGrid.paginationPageSize);
                                } else {

                                }

                            })
                        }
                        //拉取列表
                        recommonGrid();

                        $scope.add = function () {
                            var arr = $scope.commonGrid.getSelectedRows();
                            if (arr.length == 0) {
                                toastr['error']("请选择需要添加的员工！");
                                return false;
                            } else {
                                var empGuidlist = [];
                                for (var i = 0; i < arr.length; i++) {
                                    empGuidlist.push(arr[i].guid);
                                }
                                var subFrom = {};
                                subFrom.orgGuid = id;
                                subFrom.empGuidlist = empGuidlist;
                                abftree_service.addEmpOrg(subFrom).then(function (data) {
                                    if (data.status == "success") {
                                        toastr['success'](data.retMessage);
                                    } else {
                                        toastr['error'](data.retMessage);
                                    }
                                    regridOptions1();
                                    $scope.cancel();
                                })
                            }
                        }

                        $scope.cancel = function () {
                            $modalInstance.dismiss('cancel');
                        };
                    }
                )
            }

            //机构下编辑人员信息
            abftree.edityg = function () {
                var it = $scope.gridOptions2.selectRow1;
                var a = $scope.gridOptions2.getSelectedRows();
                console.log(a);
                if (isNull(a) || a.length > 1) {
                    toastr['error']("请选择一条记录！");
                    return false;
                }
                openwindow($uibModal, 'views/org/addemp_window.html', 'lg',
                    function ($scope, $modalInstance) {
                        //创建员工实体
                        var subFrom = it;
                        $scope.subFrom = subFrom;
                        //标识以区分新增和编辑
                        //emp
                        var emp = {};
                        $scope.emp = emp;
                        //修改方法
                        emp.add = function (subFrom) {
                            console.log(subFrom)
                            toastr['success']("修改成功！");
                            $scope.cancel();
                        }
                        $scope.cancel = function () {
                            $modalInstance.dismiss('cancel');
                        };
                    });
            }

            //机构下删除人员信息
            abftree.deleteyg = function () {
                var arr = $scope.gridOptions1.getSelectedRows();
                if (arr.length == 0) {
                    toastr['error']("请选择需要删除的记录！");
                    return false;
                } else {
                    //TODO
                    if (confirm("确认要从本机构中删除此人员信息吗?")) {
                        var empGuidlist = [];
                        for (var i = 0; i < arr.length; i++) {
                            empGuidlist.push(arr[i].guid);
                        }
                        var subFrom = {};
                        subFrom.orgGuid = $scope.abftree.item.guid;
                        subFrom.empGuidlist = empGuidlist;
                        abftree_service.deleteEmpOrg(subFrom).then(function (data) {
                            if (data.status == "success") {
                                toastr['success'](data.retMessage);
                            } else {
                                toastr['error'](data.retMessage);
                            }
                            regridOptions1();
                        })
                    }

                }
            }
        } else if (type == 2) {
            $scope.flag.xqxx = false;
            $scope.flag.xjjg = false;
            $scope.flag.ygxx = false;
            $scope.flag.qxxx = false;
            //todo
        } else if (type == 3) {
            $scope.flag.xqxx = false;
            $scope.flag.xjjg = false;
            $scope.flag.ygxx = false;
            $scope.flag.qxxx = true;
            //todo
            //传递参数
            var guid = $scope.abftree.item.guid;
            // $scope.$broadcast('to-child', abc);
            // 生成权限三表
            var mygrid = {}
            var alrolegird = {}
            var notrolegird = {}
            commRole(filterFilter, $scope, mygrid, alrolegird, notrolegird, guid, abftree_service, toastr)

        } else if (type == 999) {
            $scope.flag.xqxx = true;
            $scope.flag.xjjg = false;
            $scope.flag.ygxx = false;
            $scope.flag.qxxx = false;
        }
    }
    //点击页签,加载指定岗位信息
    abftree.loadgwdata = function (type) {
        //type 0--岗位详情,1--员工列表,2--岗位应用列表,3--下级岗位列表,4--岗位权限列表
        //点击加载数据,此处需要4个方法.
        //将机构页面隐藏
        for (var i in $scope.flag) {
            $scope.flag[i] = false;
        }
        $scope.flag.index = true;
        if (type == 0) {
            for (var i in $scope.gwflag) {
                $scope.gwflag[i] = false;
            }
            $scope.gwflag.gwxx = true;

        } else if (type == 1) {//岗位员工
            for (var i in $scope.gwflag) {
                $scope.gwflag[i] = false;
            }
            console.log($scope.abftree.item);
            $scope.gwflag.gwyg = true;
            //生成岗位员工列表
            var gwemp = {};
            $scope.gwemp = gwemp;
            // var selework = function () {
            //
            // }
            //定义表头名
            var com = [{field: 'empCode', displayName: '员工代码', enableHiding: false},
                {field: 'empName', displayName: '员工姓名', enableHiding: false},
                {field: 'gender', displayName: '性别', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.gender | translateConstants :\'DICT_OM_GENDER\') + $root.constant[\'DICT_OM_GENDER-\'+row.entity.gender]}}</div>'},
                {field: 'empstatus', displayName: '员工状态', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.empstatus | translateConstants :\'DICT_OM_EMPSTATUS\') + $root.constant[\'DICT_OM_EMPSTATUS-\'+row.entity.empstatus]}}</div>'},
                {field: 'empDegree', displayName: '员工职级', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.empDegree | translateConstants :\'DICT_OM_EMPDEGREE\') + $root.constant[\'DICT_OM_EMPDEGREE-\'+row.entity.empDegree]}}</div>'},
                {field: 'guidPostition', displayName: '基本岗位', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidPostition | translatePosition) + $root.constant[row.entity.guidPostition]}}</div>'},
                {field: 'guidempmajor', displayName: '直接主管', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidempmajor | translateEmp) + $root.constant[row.entity.guidempmajor]}}</div>'},
                {field: 'indate', displayName: '入职日期', enableHiding: false},
                {field: 'otel', displayName: '办公电话', enableHiding: false}
            ]
            $scope.gwemp = initgrid($scope, gwemp, filterFilter, com, true, function () {
            });

            var regwemp = function () {
                var subFrom = {};
                subFrom.positionCode = $scope.abftree.item.positionCode;
                //调取岗位员工信息
                abftree_service.loadEmpbyPosition(subFrom).then(function (data) {
                    console.log(data)
                    if (data.status == "success" && !isNull(data.retMessage)) {
                        $scope.gwemp.data = data.retMessage;
                        $scope.gwemp.mydefalutData = data.retMessage;
                        $scope.gwemp.getPage(1, $scope.gwemp.paginationPageSize);
                    } else {
                        $scope.gwemp.data = [];
                        $scope.gwemp.mydefalutData = [];
                        $scope.gwemp.getPage(1, $scope.gwemp.paginationPageSize);
                    }

                })
            }
            //拉取列表
            regwemp();

            //岗位下新增人员信息
            abftree.addgwry = function () {
                var id = $scope.abftree.item.positionCode;
                var guid = $scope.abftree.item.guid;
                openwindow($uibModal, 'views/org/addsearhgrid_window.html', 'lg',
                    function ($scope, $modalInstance) {
                        $scope.title = "添加员工";
                        //加载不在本机构下的员工信息,生成列表
                        var commonGrid = {};
                        $scope.commonGrid = commonGrid;
                        //定义单选事件
                        var selework = function () {

                        }
                        //定义表头名
                        var com = [{field: 'empCode', displayName: '员工代码', enableHiding: false},
                            {field: 'empName', displayName: '员工姓名', enableHiding: false},
                            {field: 'gender', displayName: '性别', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.gender | translateConstants :\'DICT_OM_GENDER\') + $root.constant[\'DICT_OM_GENDER-\'+row.entity.gender]}}</div>'},
                            {field: 'empstatus', displayName: '员工状态', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.empstatus | translateConstants :\'DICT_OM_EMPSTATUS\') + $root.constant[\'DICT_OM_EMPSTATUS-\'+row.entity.empstatus]}}</div>'},
                            {field: 'empDegree', displayName: '员工职级', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.empDegree | translateConstants :\'DICT_OM_EMPDEGREE\') + $root.constant[\'DICT_OM_EMPDEGREE-\'+row.entity.empDegree]}}</div>'},
                            {field: 'guidPostition', displayName: '基本岗位', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidPostition | translatePosition) + $root.constant[row.entity.guidPostition]}}</div>'},
                            {field: 'guidempmajor', displayName: '直接主管', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidempmajor | translateEmp) + $root.constant[row.entity.guidempmajor]}}</div>'},
                            {field: 'indate', displayName: '入职日期', enableHiding: false},
                            {field: 'otel', displayName: '办公电话', enableHiding: false},
                        ]
                        $scope.commonGrid = initgrid($scope, commonGrid, filterFilter, com, true, selework);

                        var recommonGrid = function () {
                            var subFrom = {};
                            subFrom.positionCode = id;
                            console.log(subFrom.positionCode)
                            //调取工作组信息OM_GROUP
                            abftree_service.loadempNotinposit(subFrom).then(function (data) {
                                if (data.status == "success" && !isNull(data.retMessage)) {
                                    $scope.commonGrid.data = data.retMessage;
                                    $scope.commonGrid.mydefalutData = data.retMessage;
                                    $scope.commonGrid.getPage(1, $scope.commonGrid.paginationPageSize);
                                } else {

                                }

                            })
                        }
                        //拉取列表
                        recommonGrid();

                        $scope.add = function () {
                            var arr = $scope.commonGrid.getSelectedRows();
                            if (arr.length == 0) {
                                toastr['error']("请选择需要添加的员工！");
                                return false;
                            } else {
                                var empGuidlist = [];
                                for (var i = 0; i < arr.length; i++) {
                                    empGuidlist.push(arr[i].guid);
                                }
                                var subFrom = {};
                                subFrom.posGuid = guid;
                                subFrom.empGuidlist = empGuidlist;
                                abftree_service.addEmpPosition(subFrom).then(function (data) {
                                    if (data.status == "success") {
                                        toastr['success'](data.retMessage);
                                    } else {
                                        toastr['error'](data.retMessage);
                                    }
                                    regwemp();
                                    $scope.cancel();
                                })
                            }
                        }

                        $scope.cancel = function () {
                            $modalInstance.dismiss('cancel');
                        };
                    }
                )
            }
            //岗位下删除人员信息
            abftree.deletegwyg = function () {
                var arr = $scope.gwemp.getSelectedRows();
                console.log(arr);
                if (arr.length == 0) {
                    toastr['error']("请选择需要删除的记录！");
                    return false;
                } else {
                    //TODO
                    if (confirm("确认要从本机构中删除此人员信息吗?")) {
                        var empGuidlist = [];
                        for (var i = 0; i < arr.length; i++) {
                            empGuidlist.push(arr[i].guid);
                        }
                        var subFrom = {};
                        subFrom.posGuid = $scope.abftree.item.guid;
                        subFrom.empGuidlist = empGuidlist;
                        abftree_service.deleteEmpPosition(subFrom).then(function (data) {
                            if (data.status == "success") {
                                toastr['success'](data.retMessage);
                            } else {
                                toastr['error'](data.retMessage);
                            }
                            regwemp();
                        })
                    }

                }
            }

        } else if (type == 2) {
            for (var i in $scope.gwflag) {
                $scope.gwflag[i] = false;
            }
            $scope.gwflag.gwyy = true
            //生成岗位应用列表
            var gwApplication = {};
            $scope.gwApplication = gwApplication;
            var com = [
                {field: 'appName', displayName: '应用名称', enableHiding: false},
                {field: 'appType', displayName: '应用类别', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.appType | translateConstants :\'DICT_AC_APPTYPE\') + $root.constant[\'DICT_AC_APPTYPE-\'+row.entity.appType]}}</div>'},
                {field: 'openDate', displayName: '开通时间', enableHiding: false},
                {field: 'appDesc', displayName: '功能描述', enableHiding: false}
            ];
            $scope.gwApplication = initgrid($scope, gwApplication, filterFilter, com, false, function () {

            });

            regwApplication();
        } else if (type == 3) {
            for (var i in $scope.gwflag) {
                $scope.gwflag[i] = false;
            }
            $scope.gwflag.xjgw = true
            //生成下级岗位列表
            var xjPosition = {};
            $scope.xjPosition = xjPosition;

            //定义表头名
            var com = [{field: 'positionCode', displayName: '岗位代码', enableHiding: false},
                {field: 'positionName', displayName: '岗位名称', enableHiding: false},
                {field: 'positionType', displayName: '岗位类型', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.positionType | translateConstants :\'DICT_OM_POSITYPE\') + $root.constant[\'DICT_OM_POSITYPE-\'+row.entity.positionType]}}</div>'},
                {field: 'positionStatus', displayName: '岗位状态', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.positionStatus | translateConstants :\'DICT_OM_POSISTATUS\') + $root.constant[\'DICT_OM_POSISTATUS-\'+row.entity.positionStatus]}}</div>'},
                {field: 'guidDuty', displayName: '所属职务', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidDuty | translateDuty) + $root.constant[row.entity.guidDuty]}}</div>'},
                {field: 'startDate', displayName: '有效开始日期', enableHiding: false},
                {field: 'endDate', displayName: '有效截止日期', enableHiding: false}
            ]
            $scope.xjPosition = initgrid($scope, xjPosition, filterFilter, com, true, function () {
            });

            var rexjPosition = function () {
                var subFrom = {};
                subFrom.posCode = $scope.abftree.item.positionCode;
                //调取工作组信息OM_GROUP
                abftree_service.loadxjposit(subFrom).then(function (data) {
                    if (data.status == "success" && !isNull(data.retMessage)) {
                        $scope.xjPosition.data = data.retMessage;
                        $scope.xjPosition.mydefalutData = data.retMessage;
                        $scope.xjPosition.getPage(1, $scope.xjPosition.paginationPageSize);
                    } else {
                        $scope.xjPosition.data = [];
                        $scope.xjPosition.mydefalutData = [];
                        $scope.xjPosition.getPage(1, $scope.xjPosition.paginationPageSize);
                    }

                })
            }
            //拉取列表
            rexjPosition();
            //下级岗位三个按钮事件
            abftree.addxjPosition = function () {
                console.log($scope.abftree.item.guid);
                var guidParents = $scope.abftree.item.guid;
                var guidOrg = $scope.abftree.item.guidOrg;
                var subFrom = {};
                subFrom.guidParents = guidParents;
                subFrom.guidOrg = guidOrg;
                creatNewPosition(subFrom,rexjPosition())
            }
            abftree.editxjPosition = function () {
                var arr = $scope.xjPosition.getSelectedRows();
                if(arr.length != 1) {
                    toastr['error']("请选择一条需要查看的岗位信息!");
                    return false;
                }
                var node = {};
                node.id = arr[0].guid;
                var node2 = {};
                node2.id = $scope.abftree.item.id;
                $("#container").jstree().deselect_all(true);
                $("#container").jstree().load_node(node2, function () {
                    $("#container").jstree().select_node(node, false, true);
                });
            }
            abftree.deletexjPosition = function () {
                var arr = $scope.xjPosition.getSelectedRows();
                if(arr.length != 1) {
                    toastr['error']("请选择一条需要删除的岗位信息!");
                    return false;
                }
                if (confirm("确定要删除此菜单？删除后不可恢复。")) {
                    var subFrom = {};
                    subFrom.posCode = arr[0].positionCode;
                    abftree_service.deletePosition(subFrom).then(function (data) {
                        if (data.status == "success") {
                            toastr['success'](data.retMessage);
                            $("#container").jstree().refresh();
                        } else {
                            toastr['error'](data.retMessage);
                        }
                    })
                }
            }
        } else if (type == 4) {
            for (var i in $scope.gwflag) {
                $scope.gwflag[i] = false;
            }
            $scope.gwflag.gwqx = true;

            //传递参数
            var guid = $scope.abftree.item.guid;
            // $scope.$broadcast('to-child', abc);
            // 生成权限三表
            var mygrid = {}
            var alrolegird = {}
            var notrolegird = {}
            commRole(filterFilter, $scope, mygrid, alrolegird, notrolegird, guid, abftree_service, toastr)
        }
    }


    //编辑岗位保存事件
    abftree.savePosition = function () {
        console.log($scope.position)
        abftree_service.addposit($scope.position).then(function (data) {
            if (data.status == "success") {
                toastr['success'](data.retMessage);
                $("#container").jstree().refresh();
                $scope.editflag = !$scope.editflag;
            } else {
                toastr['error'](data.retMessage);
            }
        })
    }


    //下级机构删除按钮
    abftree.deletexjjg = function () {
        var a = getSelectedCount();
        console.log(a);
    }

    //岗位下操作点击事件
    var gw = {};
    $scope.gw = gw;


    /**--------------------------------各类按钮事件--------------------------*/
    //岗位应用下按钮事件
    //新增应用
    abftree.addApp = function () {
        console.log($scope.abftree.item)
        var subFrom = {};
        subFrom.posGuid = $scope.abftree.item.guid;
        subFrom.posCode = $scope.abftree.item.positionCode;
        //打开通用列表窗口
        openwindow($uibModal, 'views/org/addsearhgrid_window.html', 'lg',
            function ($scope, $modalInstance) {
                //设置标题栏
                $scope.title = "新增应用";
                //实例化列表
                var commonGrid = {};
                $scope.commonGrid = commonGrid;
                var com = [
                    {field: 'appName', displayName: '应用名称', enableHiding: false},
                    {field: 'appType', displayName: '应用类别', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.appType | translateConstants :\'DICT_AC_APPTYPE\') + $root.constant[\'DICT_AC_APPTYPE-\'+row.entity.appType]}}</div>'},
                    {field: 'openDate', displayName: '开通时间', enableHiding: false},
                    {field: 'appDesc', displayName: '功能描述', enableHiding: false}
                ];
                $scope.commonGrid = initgrid($scope, commonGrid, filterFilter, com, true, function () {
                });
                (function (subFrom) {
                    abftree_service.queryAppNotinPos(subFrom).then(function (data) {
                        console.log(subFrom)
                        console.log(data)
                        if (data.status == "success" && !isNull(data.retMessage)) {
                            $scope.commonGrid.data = data.retMessage;
                            $scope.commonGrid.mydefalutData = data.retMessage;
                            $scope.commonGrid.getPage(1, $scope.commonGrid.paginationPageSize);
                        } else {
                            $scope.commonGrid.data = [];
                            $scope.commonGrid.mydefalutData = [];
                            $scope.commonGrid.getPage(1, $scope.commonGrid.paginationPageSize);
                        }
                    })
                })(subFrom)
                $scope.add = function () {
                    var arr = $scope.commonGrid.getSelectedRows();
                    if (arr.length != 1) {
                        toastr['error']("请选择一条需要添加的应用！");
                    }
                    subFrom.appGuid = arr[0].guid;
                    abftree_service.addAppPosition(subFrom).then(function (data) {
                        if (data.status == "success") {
                            toastr['success'](data.retMessage);
                            regwApplication();
                            $scope.cancel();
                        } else {
                            toastr['error'](data.retMessage);
                        }
                    })
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            });
    }
    //删除应用
    abftree.deleteApp = function () {
        var subFrom = {};
        subFrom.posGuid = $scope.abftree.item.guid;
        subFrom.posCode = $scope.abftree.item.positionCode;
        var arr = $scope.gwApplication.getSelectedRows();
        if (arr.length != 1) {
            toastr['error']("请选择一条需要删除的应用!");
            return false;
        }
        subFrom.appGuid = arr[0].guid;
        abftree_service.deleteAppPosition(subFrom).then(function (data) {
            if (data.status == "success") {
                toastr['success'](data.retMessage);
                regwApplication();
            } else {
                toastr['error'](data.retMessage);
            }
        });
    }
    //岗位信息下两个按钮事件
    //新增
    abftree.addpos = function () {
        console.log($scope.abftree.item.guid)
        var guidOrg = $scope.abftree.item.guid;
        var subFrom = {};
        subFrom.guidParents = "";
        subFrom.guidOrg = guidOrg;
        subFrom.orgGuid = guidOrg;
        creatNewPosition(subFrom,regwlbgird(subFrom));
    }
    //删除
    abftree.deletepos = function () {
        if (confirm("确定要删除此菜单？删除后不可恢复。")) {
            var arr = $scope.gwlbgird.getSelectedRows();
            if (arr.length != 1) {
                toastr['error']("请选择一条需要删除的岗位信息!");
                return false;
            }
            var subFrom = {};
            subFrom.posCode = arr[0].positionCode;
            subFrom.orgGuid = $scope.abftree.item.guid;
            abftree_service.deletePosition(subFrom).then(function (data) {
                if (data.status == "success") {
                    toastr['success'](data.retMessage);
                    regwlbgird(subFrom)
                    $("#container").jstree().refresh();
                } else {
                    toastr['error'](data.retMessage);
                }


            })
        }
    }

    /**--------------------------------各类列表生成--------------------------*/
        //机构下所有岗位列表生成
    var gwlbgird = {};
    $scope.gwlbgird = gwlbgird;
    var com = [{field: 'positionCode', displayName: '岗位代码', enableHiding: false},
        {field: 'positionName', displayName: '岗位名称', enableHiding: false},
        {field: 'positionType', displayName: '岗位类型', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.positionType | translateConstants :\'DICT_OM_POSITYPE\') + $root.constant[\'DICT_OM_POSITYPE-\'+row.entity.positionType]}}</div>'},
        {field: 'positionStatus', displayName: '岗位状态', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.positionStatus | translateConstants :\'DICT_OM_POSISTATUS\') + $root.constant[\'DICT_OM_POSISTATUS-\'+row.entity.positionStatus]}}</div>'},
        {field: 'guidDuty', displayName: '所属职务', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidDuty | translateDuty) + $root.constant[row.entity.guidDuty]}}</div>'},
        {field: 'startDate', displayName: '有效开始日期', enableHiding: false},
        {field: 'endDate', displayName: '有效截止日期', enableHiding: false}
    ]
    $scope.gwlbgird = initgrid($scope, gwlbgird, filterFilter, com, true, function () {
    });
    //拉取数据
    var regwlbgird = function (subFrom) {
        abftree_service.queryAllposbyOrg(subFrom).then(function (data) {
            if (data.status == "success" && !isNull(data.retMessage)) {
                $scope.gwlbgird.data = data.retMessage;
                $scope.gwlbgird.mydefalutData = data.retMessage;
                $scope.gwlbgird.getPage(1, $scope.gwlbgird.paginationPageSize);
            } else {
                $scope.gwlbgird.data = [];
                $scope.gwlbgird.mydefalutData = [];
                $scope.gwlbgird.getPage(1, $scope.gwlbgird.paginationPageSize);
            }
        })
    }


    //拉取岗位应用列表
    var regwApplication = function () {
        var subFrom = {};
        subFrom.posCode = $scope.abftree.item.positionCode;
        abftree_service.queryAppinPos(subFrom).then(function (data) {
            if (data.status == "success" && !isNull(data.retMessage)) {
                $scope.gwApplication.data = data.retMessage;
                $scope.gwApplication.mydefalutData = data.retMessage;
                $scope.gwApplication.getPage(1, $scope.gwApplication.paginationPageSize);
            } else {
                $scope.gwApplication.data = [];
                $scope.gwApplication.mydefalutData = [];
                $scope.gwApplication.getPage(1, $scope.gwApplication.paginationPageSize);
            }
        })
    }
    /**--------------------------------封装方法--------------------------*/
    //封装新增岗位方法
    /**
     * @param subFrom
     * subFrom.guidParents
     * subFrom.guidOrg
     */
    var creatNewPosition = function (subFrom, fun) {
        openwindow($uibModal, 'views/org/addPosition_window.html', 'lg',
            function ($scope, $modalInstance) {
                //创建机构实例
                $scope.subFrom = subFrom;
                console.log(subFrom)
                var next = true;
                $scope.next = next;
                //下一步
                $scope.skip = function () {
                    abftree_service.initPosCode($scope.subFrom).then(function (data) {
                        console.log(data)
                        if (data.status == "success") {
                            $scope.subFrom.positionCode = data.retMessage;
                        } else {
                            toastr['error'](data.retMessage);
                        }
                    })
                    $scope.next = !$scope.next;
                }
                //增加方法
                $scope.add = function (subFrom) {
                    abftree_service.addposit(subFrom).then(function (data) {
                        if (data.status == "success") {
                            toastr['success'](data.retMessage);
                            $("#container").jstree().refresh();
                            $scope.cancel();
                        } else {
                            toastr['error'](data.retMessage);
                        }
                        if (!isNull(fun)) {
                            fun();
                        }

                    });
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }
    /**---------测试RXJS---------*/
    // const source = Rx.Observable.create($scope.searchitem);
    // const example = source.mergeMap(val => Rx.Observable.of(`${val} World!`));
    // const subscribe = example.subscribe(val => console.log(val));

    var text = document.querySelector('#search');
    Rx.Observable.fromEvent(text, 'keyup')
        .debounceTime(1500) // <- throttling behaviour
        .pluck('target', 'value')
        .map(url => loadsearchtree({"id":'#',"searchitem":url})).subscribe(data => console.log(data));

    var loadsearchtree = function (subFrom) {
        console.log(subFrom)
        if(isNull(subFrom.searchitem)){
            $scope.showtree = true;
            if( $("#searchtree").jstree()){
                $("#searchtree").jstree().destroy();
            }
            ($scope.$$phase) ? null : $scope.$apply();
        }else{
            if( $("#searchtree").jstree()){
                $("#searchtree").jstree().destroy();
            }
            $scope.showtree = false;
            $("#searchtree").jstree({
                "core": {
                    "themes": {
                        "responsive": false
                    },
                    // so that create works
                    "check_callback": true,
                    'data': function (obj, callback) {
                        var jsonarray = [];
                        $scope.jsonarray = jsonarray;
                        console.log(obj)
                        subFrom.id = obj.id;
                        if(obj.id != "#"){
                            if (!isNull(obj.original)) {
                                subFrom.guidOrg = obj.original.guid;
                                subFrom.positionCode = obj.original.positionCode;
                            } else {
                                subFrom.guidOrg = "";
                                subFrom.positionCode = "";
                            }
                        }
                        abftree_service.loadsearchtree(subFrom).then(function (datas) {
                            console.log(datas)
                            var data = datas.retMessage;
                            if (isNull(data)) {

                            } else if (isNull(data[0].orgName)) {
                                for (var i = 0; i < data.length; i++) {
                                    data[i].text = data[i].positionName;
                                    data[i].children = true;
                                    data[i].id = data[i].guid;
                                    data[i].icon = 'fa fa-users icon-state-info icon-lg'
                                }
                            } else {
                                for (var i = 0; i < data.length; i++) {
                                    data[i].text = data[i].orgName;
                                    data[i].children = true;
                                    data[i].id = data[i].orgCode;
                                    data[i].icon = 'fa fa-institution  icon-state-info icon-lg';
                                    if (data[i].orgName == "岗位信息") {
                                        data[i].icon = 'fa fa-users  icon-state-info icon-lg';
                                        data[i].children = true;
                                    }
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
                        return true;
                    }
                },
                'callback': {
                    move_node: function (node) {
                        console.log(node)
                    }
                },

                "plugins": ["dnd", "types", "contextmenu"]
            }).bind("select_node.jstree", function (e, data) {
                    if (typeof data.node !== 'undefined') {//拿到结点详情
                        // console.log(data.node.original.id.indexOf("@"));
                        $scope.abftree.item = {};
                        console.log(data.node.original);
                        $scope.currNode = data.node.text;
                        if (data.node.original.id.indexOf("POSIT") == 0) {
                            for (var i in $scope.flag) {
                                flag[i] = false;
                            }
                            for (var i in $scope.gwflag) {
                                gwflag[i] = false;
                            }
                            $scope.flag.index = true;
                            $scope.gwflag.gwxx = true;
                            $scope.tabflag = false;
                            $scope.abftree.item = data.node.original;
                        } else if (data.node.original.id.indexOf("9999") == 0) {
                            for (var i in $scope.flag) {
                                flag[i] = false;
                            }
                            for (var i in $scope.gwflag) {
                                gwflag[i] = false;
                            }
                            $scope.flag.index = false;
                        } else if (data.node.original.id.indexOf("GW") == 0) {
                            for (var i in $scope.gwflag) {
                                gwflag[i] = false;
                            }
                            for (var i in $scope.flag) {
                                flag[i] = false;
                            }
                            $scope.gwflag.gwlb = true;
                            $scope.flag.index = true;
                            var subFrom = {};
                            subFrom.orgGuid = data.node.original.guid;
                            $scope.abftree.item = data.node.original;
                            regwlbgird(subFrom)
                        } else {
                            for (var i in $scope.gwflag) {
                                gwflag[i] = false;
                            }
                            for (var i in $scope.flag) {
                                flag[i] = false;
                            }
                            $scope.flag.index = true;
                            $scope.flag.xqxx = true;
                            $scope.tabflag = true;
                            $scope.abftree.item = data.node.original;
                        }

                        ($scope.$$phase) ? null : $scope.$apply();
                    }
                });
        }
    }

    /**-------------------------------测试table自适应---------------------------*/
        //测试封装
    var list = [];
    table($scope,$window,list)

    /**-------------------------------测试常量翻译---------------------------*/

    console.log($rootScope)
});

