/**
 * Created by gaojie on 2017/6/29.
 */
angular.module('MetronicApp').controller('Workgroup_controller', function ($rootScope, $scope, Workgroup_service, abftree_service, $http, $timeout, i18nService, filterFilter, uiGridConstants, $uibModal, $state) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        App.initAjax();
    });
    //定义主题对象
    var workgroup = {};
    $scope.workgroup = workgroup;
    //修改控制标志
    var editflag = false;
    $scope.editflag = editflag;
    //放置对象
    var subFrom = {};
    $scope.subFrom = subFrom;
    //设置插件语言为中文
    i18nService.setCurrentLang("zh-cn");
    //页签控制标识
    var flag = {};
    $scope.flag = flag;
    //默认为初始页面列表
    flag.index = true;
    //节点导航
    var indextitle = "";
    $scope.indextitle = indextitle;
    //树自定义右键功能
    var items = function customMenu(node) {
        if (node.parent == "#") {
            var it = {
                "新建菜单": {
                    "id": "create",
                    "label": "新建根工作组",

                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        openwindow($uibModal, 'views/Workgroup/addworkgroup_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建机构实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //标识,根-子节点
                                subFrom.flag = "root";
                                var next = false;
                                $scope.next = next;
                                $scope.skip = function () {
                                    if (isNull(subFrom.groupType)) {
                                        toastr['error']("请填写相关信息!");
                                        return false;
                                    }
                                    //调用服务生成机构代码
                                    Workgroup_service.initGroupCode(subFrom).then(function (data) {
                                        if (data.status == "success") {
                                            toastr['success']("生成成功!");
                                            $scope.subFrom.groupCode = data.retMessage;
                                            $scope.next = !next;
                                        } else {
                                            toastr['error'](data.retMessage);
                                        }
                                    })
                                }
                                //增加方法
                                $scope.add = function (subFrom) {
                                    //TODO.新增逻辑
                                    Workgroup_service.addgroup(subFrom).then(function (data) {
                                        if (data.status == "success") {
                                            toastr['success'](data.retMessage);
                                            //刷新树和列表
                                            $("#container").jstree().refresh();
                                            reworkgroupgrid();
                                            $scope.cancel();
                                        } else {
                                            toastr['error'](data.retMessage + " " + data.retCode);
                                            $("#container").jstree().refresh();
                                            reworkgroupgrid();
                                            $scope.cancel();
                                        }
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
        } else if (node.id.indexOf("GROUP") == 0) {
            var it = {
                "新建菜单": {
                    "id": "create",
                    "label": "新建子工作组",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)

                        openwindow($uibModal, 'views/Workgroup/addworkgroup_window.html', 'lg',

                            function ($scope, $modalInstance) {
                                //创建机构实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //处理父工作组GUID
                                subFrom.guidParents = obj.original.guid;
                                //标识,根-子节点
                                subFrom.flag = "child";
                                var next = false;
                                $scope.next = next;
                                $scope.skip = function () {
                                    if (isNull(subFrom.groupType)) {
                                        toastr['error']("请填写相关信息!");
                                        return false;
                                    }
                                    //调用服务生成机构代码
                                    Workgroup_service.initGroupCode(subFrom).then(function (data) {
                                        if (data.status == "success") {
                                            toastr['success']("生成成功!");
                                            $scope.subFrom.groupCode = data.retMessage;
                                            $scope.next = !next;
                                        } else {
                                            toastr['error'](data.retMessage);
                                        }
                                    })
                                }
                                //增加方法
                                $scope.add = function (subFrom) {
                                    //TODO.新增逻辑
                                    Workgroup_service.addgroup(subFrom).then(function (data) {
                                        if (data.status == "success") {
                                            toastr['success'](data.retMessage);
                                            //刷新树和列表
                                            $("#container").jstree().refresh();
                                            $scope.cancel();
                                        } else {
                                            toastr['error'](data.retMessage + " " + data.retCode);
                                            $("#container").jstree().refresh();
                                            $scope.cancel();
                                        }
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

                    "label": "删除工作组",

                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if (confirm("确定要删除此菜单？删除后不可恢复。")) {
                            var subFrom = {};
                            subFrom.groupCode = obj.original.groupCode;
                            Workgroup_service.deletegroup(subFrom).then(function (data) {
                                if (data.status == "success") {
                                    console.log(data)
                                    toastr['success'](data.message);
                                    $("#container").jstree().refresh();
                                } else {
                                    toastr['error'](data.message);
                                }
                            })
                        }
                    }
                },
                "拷贝菜单": {
                    "label": "拷贝工作组",
                    "action": function (node) {
                        var inst = jQuery.jstree.reference(node.reference),
                            obj = inst.get_node(node.reference);
                        var og = $('#container').jstree(true).copy_node(obj, obj);
                        // console.log(obj)
                    }
                },

                "粘贴菜单": {
                    "label": "粘贴工作组",
                    "action": function (node) {
                        var inst = jQuery.jstree.reference(node.reference),
                            obj = inst.get_node(node.reference);
                        var og = $('#container').jstree(true).paste(node);
                        console.log(og)
                    }
                },
            }
            return it;

        }

    };

    //工作组树
    $("#container").jstree({
        "core": {
            "multiple": true,
            "themes": {
                "responsive": false
            },
            // so that create works
            "check_callback": true,
            'data': function (obj, callback) {
                console.log(obj.id)
                var jsonarray = [];
                $scope.jsonarray = jsonarray;
                var subFrom = {};
                subFrom.id = obj.id;
                Workgroup_service.loadmaintree(subFrom).then(function (data) {
                    console.log(data)
                    var data = data.retMessage;
                    for (var i = 0; i < data.length; i++) {
                        data[i].text = data[i].groupName;
                        data[i].children = true;
                        data[i].id = data[i].groupCode;
                        data[i].icon = "fa fa-users icon-state-info icon-lg"
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
            show_only_matches: true,

        },
        'callback': {
            move_node: function (node) {
                console.log(node)
            }
        },

        "plugins": ["dnd", "state", "types", "search", "contextmenu"]
    }).bind("move_node.jstree", function (e, data) {
        if (confirm("确认要移动此机构吗?")) {
            //TODO.
        } else {
            // data.inst.refresh(data.inst._get_parent(data.rslt.oc));
            return false;
        }
        console.log(e);
        console.log(data);
    }).bind("dnd_stop.vakata", function (e, data) {
        console.log(data);
    }).bind("changed.jstree", function (e, data) {
        if (typeof data.node !== 'undefined') {//拿到结点详情
            // console.log(data.node.original.id.indexOf("@"));
            console.log(data.node.original);
            $scope.indextitle = data.node.text;
            if (data.node.original.id == "00000") {
                for (var a in flag) {
                    flag[a] = false;
                }
                flag.index = true;
            } else {
                for (var a in flag) {
                    flag[a] = false;
                }
                flag.xqxx = true;

                $scope.sub = data.node.original;

            }
            $scope.$apply();
        }
    });

    //生成工作组列表
    var workgroupgrid = {};
    $scope.workgroupgrid = workgroupgrid;
    //定义单选事件
    var selework = function () {

    }
    //定义表头名
    var com = [{field: 'groupCode', displayName: '工作组代码', enableHiding: false},
        {field: 'groupName', displayName: '工作组名称', enableHiding: false},
        {field: 'groupStatus', displayName: '工作组状态', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.groupStatus | translateConstants :\'DICT_OM_GROUPSTATUS\') + $root.constant[\'DICT_OM_GROUPSTATUS-\'+row.entity.groupStatus]}}</div>'},
        {field: 'groupType', displayName: '工作组类型', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.groupType | translateConstants :\'DICT_OM_GROUPTYPE\') + $root.constant[\'DICT_OM_GROUPTYPE-\'+row.entity.groupType]}}</div>'},
        {field: 'guidEmpManager', displayName: '工作组管理员', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidEmpManager | translateEmp) + $root.constant[row.entity.guidEmpManager]}}</div>'},
        {field: 'guidOrg', displayName: '所属机构', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidOrg | translateOrg) + $root.constant[row.entity.guidOrg]}}</div>'},
        {field: 'startDate', displayName: '工作组有效起始日', enableHiding: false},
        {field: 'startDate', displayName: '工作组有效到期日', enableHiding: false},
        {field: 'lastupdate', displayName: '最后修改日', enableHiding: false}
    ]
    $scope.workgroupgrid = initgrid($scope, workgroupgrid, filterFilter, com, false, selework);

    var reworkgroupgrid = function () {
        //调取工作组信息OM_GROUP
        Workgroup_service.loadallgroup().then(function (data) {
            for (var i = 0; i < data.length; i++) {
                data[i].startDate = FormatDate(data[i].startDate);
                data[i].createtime = FormatDate(data[i].createtime);
                data[i].endDate = FormatDate(data[i].endDate);
                data[i].lastupdate = FormatDate(data[i].lastupdate);
            }
            $scope.workgroupgrid.data = data;
            $scope.workgroupgrid.mydefalutData = data;
            $scope.workgroupgrid.getPage(1, $scope.workgroupgrid.paginationPageSize);
        })
    }
    reworkgroupgrid();


    //新增根工作组
    workgroup.add = function (str) {
        var parentgroupCode = $scope.sub.groupCode;
        openwindow($uibModal, 'views/Workgroup/addworkgroup_window.html', 'lg',
            function ($scope, $modalInstance) {
                //创建机构实例
                var subFrom = {};
                $scope.subFrom = subFrom;
                //标识,根-子节点
                subFrom.flag = str;
                subFrom.parentCode = parentgroupCode;
                //增加方法
                var next = false;
                $scope.next = next;
                $scope.skip = function () {
                    if (isNull(subFrom.groupType)) {
                        toastr['error']("请填写相关信息!");
                        return false;
                    }
                    //调用服务生成机构代码
                    Workgroup_service.initGroupCode(subFrom).then(function (data) {
                        if (data.status == "success") {
                            toastr['success']("生成成功!");
                            $scope.subFrom.groupCode = data.retMessage;
                            $scope.next = !next;
                        } else {
                            toastr['error'](data.retMessage);
                        }
                    })
                }
                $scope.add = function (subFrom) {
                    //TODO.新增逻辑
                    Workgroup_service.addgroup(subFrom).then(function (data) {
                        console.log(data);
                        if (data.status == "success") {
                            toastr['success'](data.retMessage);
                            reworkgroupgrid();
                            $("#container").jstree().refresh();
                            $scope.cancel();
                        } else {
                            toastr['error']("新增失败！");
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
        if (item.length != 1) {
            toastr['error']("请选择一条数据!");
            return false;
        } else {
            var subFrom = {};
            subFrom.groupCode = item[0].groupCode;
            Workgroup_service.deletegroup(subFrom).then(function (data) {
                if (data.status == "success") {
                    console.log(data)
                    toastr['success'](data.message);
                    rexjgroup();
                } else {
                    toastr['error'](data.message);
                }
            })
        }
    }
    //详情页绑定的对象
    var sub = {};
    $scope.sub = sub;
    //首页编辑工作组
    workgroup.edit = function () {
        var item = $scope.workgroupgrid.getSelectedRows();
        if (item.length != 1) {
            toastr['error']("请选择一条数据!");
            return false;
        } else {
            for (var a in $scope.flag) {
                flag[a] = false;
            }
            $scope.flag.xqxx = true;
            $scope.subFrom = item[0];
            $scope.sub = item[0];
            $scope.editflag = !$scope.editflag;
        }
    }
    //下级工作组页面编辑工作组
    workgroup.editxj = function () {
        var item = $scope.xjworkgroupgrid.getSelectedRows();
        if (item.length != 1) {
            toastr['error']("请选择一条数据!");
            return false;
        } else {
            for (var a in flag) {
                flag[a] = false;
            }
            flag.xqxx = true;
            $scope.subFrom = item[0];
            $scope.sub = item[0];
            var node = {};
            node.id = item[0].groupCode;
            var node2 = {};
            node2.id = $scope.sub.groupCode;
            $("#container").jstree().deselect_all(true);
            $("#container").jstree().load_node(node2, function () {
                $("#container").jstree().select_node(node, false, false);
            });
            console.log($scope.sub)
            $scope.editflag = !$scope.editflag;
        }
    }

    //页签切换数据载入方法
    workgroup.loaddata = function (num) {
        if (num == 0) {//详情页
            for (var a in $scope.flag) {
                flag[a] = false;
            }
            $scope.flag.xqxx = true;
        } else if (num == 1) {//下级机构
            for (var a in $scope.flag) {
                flag[a] = false;
            }
            $scope.flag.xjgz = true;
            console.log($scope.sub.guid)
            console.log($scope.flag.xjgz)
            //生成下级工作组列表
            var xjworkgroupgrid = {};
            $scope.xjworkgroupgrid = xjworkgroupgrid;
            //定义单选事件
            var xjselework = function () {

            }
            //定义表头名
            var com2 = [{field: 'groupCode', displayName: '工作组代码', enableHiding: false},
                {field: 'groupName', displayName: '工作组名称', enableHiding: false},
                {field: 'groupStatus', displayName: '工作组状态', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.groupStatus | translateConstants :\'DICT_OM_GROUPSTATUS\') + $root.constant[\'DICT_OM_GROUPSTATUS-\'+row.entity.groupStatus]}}</div>'},
                {field: 'groupType', displayName: '工作组类型', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.groupType | translateConstants :\'DICT_OM_GROUPTYPE\') + $root.constant[\'DICT_OM_GROUPTYPE-\'+row.entity.groupType]}}</div>'},
                {field: 'guidEmpManager', displayName: '工作组管理员', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidEmpManager | translateEmp) + $root.constant[row.entity.guidEmpManager]}}</div>'},
                {field: 'guidOrg', displayName: '所属机构', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidOrg | translatePosition) + $root.constant[row.entity.guidOrg]}}</div>'},
                {field: 'startDate', displayName: '工作组有效起始日', enableHiding: false},
                {field: 'startDate', displayName: '工作组有效到期日', enableHiding: false},
                {field: 'lastupdate', displayName: '最后修改日', enableHiding: false}
            ]
            $scope.xjworkgroupgrid = initgrid($scope, xjworkgroupgrid, filterFilter, com2, false, xjselework);
            var rexjgroup = function () {
                var subFrom = {};
                subFrom.groupCode = $scope.sub.groupCode;
                //调取工作组信息OM_GROUP
                Workgroup_service.loadxjgroup(subFrom).then(function (data) {
                    data = data.retMessage;
                    console.log(data);
                    $scope.xjworkgroupgrid.data = data;
                    $scope.xjworkgroupgrid.mydefalutData = data;
                    $scope.xjworkgroupgrid.getPage(1, $scope.xjworkgroupgrid.paginationPageSize);
                })
            }
            rexjgroup();

        } else if (num == 2) {
            for (var a in $scope.flag) {
                flag[a] = false;
            }
            $scope.flag.xjgw = true;
            //生成岗位列表
            var posgrid = {};
            $scope.posgrid = posgrid;
            //定义表头名
            var com = [{field: 'positionCode', displayName: '岗位代码', enableHiding: false},
                {field: 'positionName', displayName: '岗位名称', enableHiding: false},
                {field: 'positionType', displayName: '岗位类型', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.positionType | translateConstants :\'DICT_OM_POSITYPE\') + $root.constant[\'DICT_OM_POSITYPE-\'+row.entity.positionType]}}</div>'},
                {field: 'positionStatus', displayName: '岗位状态', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.positionStatus | translateConstants :\'DICT_OM_GROUPSTATUS\') + $root.constant[\'DICT_OM_GROUPSTATUS-\'+row.entity.positionStatus]}}</div>'},
                {field: 'guidDuty', displayName: '所属职务', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidDuty | translateDuty) + $root.constant[row.entity.guidDuty]}}</div>'},
                {field: 'startDate', displayName: '有效开始日期', enableHiding: false},
                {field: 'endDate', displayName: '有效截止日期', enableHiding: false}
            ]
            $scope.posgrid = initgrid($scope, posgrid, filterFilter, com, false, function () {
            });
            reposgrid();
        } else if (num == 3) {
            for (var a in $scope.flag) {
                flag[a] = false;
            }
            $scope.flag.ygxx = true;
            //生成人员列表
            var xjempgrid = {};
            $scope.xjempgrid = xjempgrid;
            var com = [{field: 'empCode', displayName: '员工代码', enableHiding: false},
                {field: 'empName', displayName: '员工姓名', enableHiding: false},
                {field: 'gender', displayName: '性别', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.gender | translateConstants :\'DICT_OM_GENDER\') + $root.constant[\'DICT_OM_GENDER-\'+row.entity.gender]}}</div>'},
                {field: 'empstatus', displayName: '员工状态', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.empstatus | translateConstants :\'DICT_OM_EMPSTATUS\') + $root.constant[\'DICT_OM_EMPSTATUS-\'+row.entity.empstatus]}}</div>'},
                {field: 'empDegree', displayName: '员工职级', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.empDegree | translateConstants :\'DICT_OM_EMPDEGREE\') + $root.constant[\'DICT_OM_EMPDEGREE-\'+row.entity.empDegree]}}</div>'},
                {field: 'guidPosition', displayName: '基本岗位', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidPosition | translatePosition) + $root.constant[row.entity.guidPosition]}}</div>'},
                {field: 'guidempmajor', displayName: '直接主管', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidempmajor | translateEmp) + $root.constant[row.entity.guidempmajor]}}</div>'},
                {field: 'indate', displayName: '入职日期', enableHiding: false},
                {field: 'otel', displayName: '办公电话', enableHiding: false}
            ]
            $scope.xjempgrid = initgrid($scope, xjempgrid, filterFilter, com, false, function () {
            });

            //拉取
            reempgrid();
        } else if (num == 4) {
            console.log(num)
            for (var a in flag) {
                flag[a] = false;
            }
            flag.qxxx = true;
            //传递参数
            var guid = $scope.sub.guid;
            //生成权限3表
            var mygrid = {}
            var alrolegird = {}
            var notrolegird = {}
            commRole(filterFilter, $scope, mygrid, alrolegird, notrolegird, guid, abftree_service, toastr)
        }
    }

    //生成emp列表方法
    var reempgrid = function () {
        var subFrom = {};
        subFrom.groupCode = $scope.sub.groupCode;
        // subFrom.orgCode = $scope.sub.orgCode;
        //拉取列表
        Workgroup_service.loadempin(subFrom).then(function (data) {
            data = data.retMessage;
            console.log(data);
            $scope.xjempgrid.data = data;
            $scope.xjempgrid.mydefalutData = data;
            $scope.xjempgrid.getPage(1, $scope.xjempgrid.paginationPageSize);
        })
    }
    //生成岗位列表方法
    var reposgrid = function () {
        var subFrom = {};
        subFrom.groupCode = $scope.sub.groupCode;
        Workgroup_service.loadposin(subFrom).then(function (data) {
            data = data.retMessage;
            console.log(data);
            $scope.posgrid.data = data;
            $scope.posgrid.mydefalutData = data;
            $scope.posgrid.getPage(1, $scope.posgrid.paginationPageSize);
        })
    }


    //详情页按钮
    workgroup.enableGroup = function () {
        console.log($scope.sub)
        var subFrom = {};
        subFrom.groupCode = $scope.sub.groupCode;
        subFrom.flag = $scope.sub.groupStatus;
        Workgroup_service.enableGroup(subFrom).then(function (data) {
            if (data.status == "success") {
                toastr['success'](data.retMessage);
            } else {
                toastr['error'](data.retMessage);
            }
            $("#container").jstree().refresh();
        })
    }
    workgroup.deleteGroup = function () {
        var subFrom = {};
        subFrom.groupCode = $scope.sub.groupCode;
        Workgroup_service.deletegroup(subFrom).then(function (data) {
            console.log(data)
            if (data.status == "success") {
                toastr['success'](data.retMessage);
            } else {
                toastr['error'](data.retMessage);
            }
            $("#container").jstree().refresh();
        })
    }
    workgroup.editGroup = function () {
        $scope.subFrom = angular.copy($scope.sub);
        $scope.editflag = !$scope.editflag;
    }
    workgroup.back = function () {
        $scope.editflag = !$scope.editflag;
    }
    workgroup.updateGroup = function () {
        console.log($scope.subFrom)
        Workgroup_service.updateGroup($scope.subFrom).then(function (data) {
            if (data.status == "success") {
                toastr['success'](data.retMessage);
            } else {
                toastr['error'](data.retMessage);
            }
            $scope.editflag = !$scope.editflag;
            $("#container").jstree().refresh();
        })
    }
    //人员页签下,3个按钮事件
    //新增人员
    workgroup.addemp = function () {
        var subFrom = {};
        subFrom.groupCode = $scope.sub.groupCode;
        subFrom.guidOrg = $scope.sub.guidOrg;
        var guid = $scope.sub.guid;
        console.log(subFrom);
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
                    {field: 'gender', displayName: '性别', enableHiding: false},
                    {field: 'empstatus', displayName: '员工状态', enableHiding: false},
                    {field: 'empDegree', displayName: '员工职级', enableHiding: false},
                    {field: 'guidPostition', displayName: '基本岗位', enableHiding: false},
                    {field: 'guidempmajor', displayName: '直接主管', enableHiding: false},
                    {field: 'indate', displayName: '入职日期', enableHiding: false},
                    {field: 'otel', displayName: '办公电话', enableHiding: false}
                ]
                $scope.commonGrid = initgrid($scope, commonGrid, filterFilter, com, true, selework);

                var recommonGrid = function () {
                    //调取工作组信息OM_GROUP
                    Workgroup_service.loadempNotin(subFrom).then(function (data) {
                        console.log(data)
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
                        subFrom.groupGuid = guid;
                        subFrom.empGuidlist = empGuidlist;
                        console.log(subFrom)
                        Workgroup_service.addEmpGroup(subFrom).then(function (data) {
                            console.log(data)
                            if (data.status == "success") {
                                toastr['success'](data.retMessage);
                            } else {
                                toastr['error'](data.retMessage);
                            }
                            reempgrid();
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
    //删除人员
    workgroup.deletemp = function () {
        var guid = $scope.sub.guid;
        console.log(subFrom);
        var arr = $scope.xjempgrid.getSelectedRows();
        if (arr.length == 0) {
            toastr['error']("请选择需要删除的员工！");
            return false;
        } else {
            var empGuidlist = [];
            for (var i = 0; i < arr.length; i++) {
                empGuidlist.push(arr[i].guid);
            }
            var subFrom = {};
            subFrom.groupGuid = guid;
            subFrom.empGuidlist = empGuidlist;
            console.log(subFrom)
            Workgroup_service.deleteEmpGroup(subFrom).then(function (data) {
                console.log(data)
                if (data.status == "success") {
                    toastr['success'](data.retMessage);
                } else {
                    toastr['error'](data.retMessage);
                }
                reempgrid();
            })
        }
    }
    //岗位下两个按钮
    workgroup.addpos = function () {
        var subFrom = {};
        subFrom.groupCode = $scope.sub.groupCode;
        var guid = $scope.sub.guid;
        console.log(subFrom);
        openwindow($uibModal, 'views/org/addsearhgrid_window.html', 'lg',
            function ($scope, $modalInstance) {
                $scope.title = "添加岗位";
                var commonGrid = {};
                $scope.commonGrid = commonGrid;
                //定义单选事件
                var selework = function () {

                }
                //定义表头名
                var com = [{field: 'positionCode', displayName: '岗位代码', enableHiding: false},
                    {field: 'positionName', displayName: '岗位名称', enableHiding: false},
                    {field: 'positionType', displayName: '岗位类型', enableHiding: false},
                    {field: 'positionStatus', displayName: '岗位状态', enableHiding: false},
                    {field: 'guidDuty', displayName: '所属职务', enableHiding: false},
                    {field: 'startDate', displayName: '有效开始日期', enableHiding: false},
                    {field: 'endDate', displayName: '有效截止日期', enableHiding: false}
                ]
                $scope.commonGrid = initgrid($scope, commonGrid, filterFilter, com, true, selework);

                var recommonGrid = function () {
                    //调取工作组信息OM_GROUP
                    Workgroup_service.loadposNotin(subFrom).then(function (data) {
                        console.log(data)
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
                        toastr['error']("请选择需要添加的岗位！");
                        return false;
                    } else {
                        var posGuidlist = [];
                        for (var i = 0; i < arr.length; i++) {
                            posGuidlist.push(arr[i].guid);
                        }
                        var subFrom = {};
                        subFrom.groupGuid = guid;
                        subFrom.posGuidlist = posGuidlist;
                        console.log(subFrom)
                        Workgroup_service.addGroupPosition(subFrom).then(function (data) {
                            console.log(data)
                            if (data.status == "success") {
                                toastr['success'](data.retMessage);
                            } else {
                                toastr['error'](data.retMessage);
                            }
                            reposgrid();
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
    workgroup.deletepos = function () {
        var guid = $scope.sub.guid;
        var arr = $scope.posgrid.getSelectedRows();
        if (arr.length == 0) {
            toastr['error']("请选择需要删除的员工！");
            return false;
        } else {
            var posGuidlist = [];
            for (var i = 0; i < arr.length; i++) {
                posGuidlist.push(arr[i].guid);
            }
            var subFrom = {};
            subFrom.groupGuid = guid;
            subFrom.posGuidlist = posGuidlist;
            console.log(subFrom)
            Workgroup_service.deleteGroupPosition(subFrom).then(function (data) {
                console.log(data)
                if (data.status == "success") {
                    toastr['success'](data.retMessage);
                } else {
                    toastr['error'](data.retMessage);
                }
                reposgrid();
            })
        }
    }


    /**--------------------------------------筛选树------------------------------------*/
    //jstree 自定义筛选事件
    //筛选字段
    $scope.searchitem = "";
    //清空
    workgroup.clear = function () {
        $scope.searchitem = "";
        $scope.showtree = true;
        if ($("#searchtree").jstree()) {
            $("#searchtree").jstree().destroy();
        }
        console.log($("#container").jstree().get_json())
    }

    //控制2个树显示标识,true为默认值,false为筛选状态
    var showtree = true;
    $scope.showtree = showtree;

    var text = document.querySelector('#search');
    Rx.Observable.fromEvent(text, 'keyup')
        .debounceTime(1500) // <- throttling behaviour
        .pluck('target', 'value')
        .map(url => loadsearchtree({"id": '#', "searchitem": url})).subscribe(data => console.log(data));

    var loadsearchtree = function (subFrom) {
        console.log(subFrom)
        if (isNull(subFrom.searchitem)) {
            $scope.showtree = true;
            if ($("#searchtree").jstree()) {
                $("#searchtree").jstree().destroy();
            }
            ($scope.$$phase) ? null : $scope.$apply();
        } else {
            if ($("#searchtree").jstree()) {
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
                        subFrom.id = obj.id;
                        console.log(obj)
                        Workgroup_service.loadworksearchtree(subFrom).then(function (datas) {
                            console.log(datas)
                            var data = datas.retMessage;
                            if (!isNull(data[0])) {
                                for (var i = 0; i < data.length; i++) {
                                    data[i].text = data[i].groupName;
                                    data[i].children = false;
                                    data[i].id = data[i].groupCode;
                                    data[i].icon = "fa fa-users icon-state-info icon-lg"
                                }
                            }
                            $scope.jsonarray = angular.copy(data);
                            callback.call(this, $scope.jsonarray);
                        })
                    }
                },
                "state": {"key": "demo3"},
                "contextmenu": {'items': items},
                "plugins": ["state", "types", "contextmenu"]
            }).bind("select_node.jstree", function (e, data) {
                if (typeof data.node !== 'undefined') {//拿到结点详情
                    // console.log(data.node.original.id.indexOf("@"));
                    console.log(data.node.original);
                    $scope.indextitle = data.node.text;
                    for (var a in flag) {
                        flag[a] = false;
                    }
                    flag.xqxx = true;
                    $scope.sub = data.node.original;
                    ($scope.$$phase) ? null : $scope.$apply();
                }
            });
        }
    }
});