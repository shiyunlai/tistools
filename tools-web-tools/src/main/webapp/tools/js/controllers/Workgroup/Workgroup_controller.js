/**
 * Created by gaojie on 2017/6/29.
 */
angular.module('MetronicApp').controller('Workgroup_controller', function ($rootScope, $scope, Workgroup_service, abftree_service, $filter,$http, $timeout, i18nService, filterFilter, uiGridConstants, $uibModal, $state) {
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
    //树刷新
    $scope.jstreereload = function(){
        $("#container").jstree().refresh();
    }

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
                        openwindow($uibModal, 'views/Workgroup/addworkgroup_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建机构实例
                                var next = false;
                                $scope.next = next;
                                $scope.skip = function (items) {
                                    items.flag = "root";
                                    var subFrom = {};
                                    subFrom= items;
                                    if (isNull(subFrom.groupType) || isNull(subFrom.groupName) || isNull(subFrom.guidOrg)) {
                                        toastr['error']("请填写相关信息!");
                                        return false;
                                    }
                                    //调用服务生成机构代码
                                    Workgroup_service.addgroup(subFrom).then(function (data) {
                                        if (data.status == "success") {
                                            toastr['success']("新增成功!");
                                            $scope.next = !next;
                                            var subFrom = {};
                                            $scope.subFrom = subFrom;
                                            subFrom.groupCode = data.retMessage.groupCode;
                                        } else {
                                            toastr['error'](data.retMessage);
                                        }
                                        $("#container").jstree().refresh();
                                        $scope.add=function (items) {
                                            var tis =Object.assign(data.retMessage, items);
                                            Workgroup_service.updateGroup(tis).then(function (data) {
                                                if (data.status == "success") {
                                                    console.log(data)
                                                    toastr['success']("添加成功!");
                                                    $("#container").jstree().refresh();
                                                    $scope.cancel();
                                                } else {
                                                    toastr['error'](data.retMessage);
                                                }
                                            })
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
                            obj = inst.get_node(data.reference)
                        openwindow($uibModal, 'views/Workgroup/addworkgroup_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                var next = false;
                                $scope.next = next;
                                $scope.skip = function (items) {
                                    items.flag = "child";
                                    var subFrom = {};
                                    subFrom= items;
                                    if (isNull(subFrom.groupType) || isNull(subFrom.groupName) || isNull(subFrom.guidOrg)) {
                                        toastr['error']("请填写相关信息!");
                                        return false;
                                    }
                                    subFrom.guidParents = obj.original.guid;
                                    //调用服务生成机构代码
                                    Workgroup_service.addgroup(subFrom).then(function (data) {
                                        if (data.status == "success") {
                                            toastr['success']("新增成功!");
                                            $scope.next = !next;
                                            var subFrom = {};
                                            $scope.subFrom = subFrom;
                                            subFrom.groupCode = data.retMessage.groupCode;
                                        } else {
                                            toastr['error'](data.retMessage);
                                        }
                                        $("#container").jstree().refresh();
                                        $scope.add=function (subFrom) {
                                            var tis =Object.assign(data.retMessage, subFrom);
                                            console.log(tis)
                                            Workgroup_service.updateGroup(tis).then(function (data) {
                                                if (data.status == "success") {
                                                    toastr['success']("添加成功!");
                                                    $("#container").jstree().refresh();
                                                    $scope.cancel();
                                                } else {
                                                    toastr['error'](data.retMessage);
                                                }
                                            })
                                        }
                                    });
                                }
                                //增加方法
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
                }
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
            $scope.indextitle = data.node.text;
            if (data.node.original.id == "00000") {
                for (var a in flag) {
                    flag[a] = false;
                }
                flag.index = true;
                reworkgroupgrid();
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
        //{field: 'guidEmpManager', displayName: '工作组管理员', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidEmpManager | translateEmp) + $root.constant[row.entity.guidEmpManager]}}</div>'},
        {field: 'guidOrg', displayName: '所属机构', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidOrg | translateOrg) + $root.constant[row.entity.guidOrg]}}</div>'},
        {field: 'startDate', displayName: '工作组有效起始日', enableHiding: false},
        {field: 'endDate', displayName: '工作组有效到期日', enableHiding: false},
        {field: 'lastupdate', displayName: '最后修改日', enableHiding: false}
    ]
    $scope.workgroupgrid = initgrid($scope, workgroupgrid, filterFilter, com, false, selework);

    var reworkgroupgrid = function () {
        //调取工作组信息OM_GROUP
        Workgroup_service.loadallgroup().then(function (data) {
            if(data.status == 'success'){
                var datas = $filter('Arraysort')(data.retMessage);//调用管道排序
                for (var i = 0; i < datas.length; i++) {
                    datas[i].startDate = FormatDate(datas[i].startDate);
                    datas[i].createtime = FormatDate(datas[i].createtime);
                    datas[i].endDate = FormatDate(datas[i].endDate);
                    datas[i].lastupdate = FormatDate(datas[i].lastupdate);
                }
                $scope.workgroupgrid.data = datas;
                $scope.workgroupgrid.mydefalutData = datas;
                $scope.workgroupgrid.getPage(1, $scope.workgroupgrid.paginationPageSize);
            }else{
            }
        })
    }
    reworkgroupgrid();
    //查看概况
    workgroup.histroy= function () {
        var workGuid = $scope.sub.guid;
        $state.go("loghistory",{id:workGuid});//跳转新页面
    }

    //新增根工作组
    workgroup.add = function (str) {
        var guidParents = $scope.sub.guid;
        openwindow($uibModal, 'views/Workgroup/addworkgroup_window.html', 'lg',
            function ($scope, $modalInstance) {
                //创建机构实例
                var subFrom = {};
                $scope.subFrom = subFrom;
                //标识,根-子节点
                // subFrom.parentCode = parentgroupCode;
                //增加方法
                var next = false;
                $scope.next = next;
                $scope.skip = function (items) {
                    items.flag = str;
                    items.guidParents = guidParents;
                    var subFrom = {};
                    subFrom= items;
                    if (isNull(subFrom.groupType) || isNull(subFrom.groupName) || isNull(subFrom.guidOrg)) {
                        toastr['error']("请填写相关信息!");
                        return false;
                    }
                    Workgroup_service.addgroup(subFrom).then(function (data) {
                        if (data.status == "success") {
                            toastr['success']("新增成功!");
                            $scope.next = !next;
                            var subFrom = {};
                            $scope.subFrom = subFrom;
                            subFrom.groupCode = data.retMessage.groupCode;
                        } else {
                            toastr['error'](data.retMessage);
                        }
                        $("#container").jstree().refresh();
                        $scope.add=function (subFrom) {
                            var tis =Object.assign(data.retMessage, subFrom);
                            Workgroup_service.updateGroup(tis).then(function (data) {
                                if (data.status == "success") {
                                    toastr['success']("添加成功!");
                                    $("#container").jstree().refresh();
                                    reworkgroupgrid();
                                    $scope.cancel();
                                } else {
                                    toastr['error'](data.retMessage);
                                }
                            })
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
                    toastr['success']('删除成功');
                    reworkgroupgrid();
                    $("#container").jstree().refresh();
                } else {
                    toastr['error']('删除失败'+ data.message);
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
                //{field: 'guidEmpManager', displayName: '工作组管理员', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidEmpManager | translateEmp) + $root.constant[row.entity.guidEmpManager]}}</div>'},
                {field: 'guidOrg', displayName: '所属机构', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidOrg | translatePosition) + $root.constant[row.entity.guidOrg]}}</div>'},
                {field: 'startDate', displayName: '工作组有效起始日', enableHiding: false},
                {field: 'endDate', displayName: '工作组有效到期日', enableHiding: false},
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
                {field: 'guidEmpMajor', displayName: '直接主管', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidEmpMajor | translateEmp) + $root.constant[row.entity.guidEmpMajor]}}</div>'},
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
        } else if (num == 5) {
            for(var a in flag) {
                flag[a] = false;
            }
            flag.yylb = true;
            reappgrid();
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

    /**--------------------------------------各类按钮事件------------------------------------*/

    //详情页按钮
    workgroup.enableGroup = function () {
        console.log($scope.sub)
        var subFrom = {};
        subFrom.groupCode = $scope.sub.groupCode;
        subFrom.flag = $scope.sub.groupStatus;
        Workgroup_service.enableGroup(subFrom).then(function (data) {
            if (data.status == "success") {
                toastr['success']('工作组状态更改成功');
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
            if (data.status == "success") {
                toastr['success']('删除工作组成功');
            } else {
                toastr['error']('删除工作组失败'+ data.retMessage);
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
                toastr['success']("修改工作组成功");
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
                    {field: 'guidPosition', displayName: '基本岗位', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidPosition | translatePosition) + $root.constant[row.entity.guidPosition]}}</div>'},
                    {field: 'guidEmpMajor', displayName: '直接主管', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidEmpMajor | translateEmp) + $root.constant[row.entity.guidEmpMajor]}}</div>'},
                    {field: 'indate', displayName: '入职日期', enableHiding: false},
                    {field: 'otel', displayName: '办公电话', enableHiding: false}
                ]
                $scope.commonGrid = initgrid($scope, commonGrid, filterFilter, com, false, selework);//先不做批量

                var recommonGrid = function () {
                    //调取工作组信息OM_GROUP
                    Workgroup_service.loadempNotin(subFrom).then(function (data) {
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
                        /* 先不做批量
                        var empGuidlist = [];
                        for (var i = 0; i < arr.length; i++) {
                            empGuidlist.push(arr[i].guid);
                        }*/
                        var subFrom = {};
                    /*    subFrom.groupGuid = guid;
                        subFrom.empGuidlist =empGuidlist;*/
                        subFrom.guidGroup = guid;
                        subFrom.guidEmp = arr[0].guid;
                        Workgroup_service.addEmpGroup({data:subFrom}).then(function (data) {
                            if (data.status == "success") {
                                toastr['success']('添加成功');
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
        var arr = $scope.xjempgrid.getSelectedRows();
        if (arr.length == 0) {
            toastr['error']("请选择需要删除的员工！");
            return false;
        } else {
           /* var empGuidlist = [];
            for (var i = 0; i < arr.length; i++) {
                empGuidlist.push(arr[i].guid);
            }
            var subFrom = {};
            subFrom.groupGuid = guid;
            subFrom.empGuidlist = empGuidlist;*/
           var subFrom = {};
            subFrom.guidGroup = guid;
            subFrom.guidEmp = arr[0].guid;
            Workgroup_service.deleteEmpGroup({data:subFrom}).then(function (data) {
                if (data.status == "success") {
                    toastr['success']('删除员工成功');
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
                    {field: 'positionType', displayName: '岗位类型', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.positionType | translateConstants :\'DICT_OM_POSITYPE\') + $root.constant[\'DICT_OM_POSITYPE-\'+row.entity.positionType]}}</div>'},
                    {field: 'positionStatus', displayName: '岗位状态', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.positionStatus | translateConstants :\'DICT_OM_GROUPSTATUS\') + $root.constant[\'DICT_OM_GROUPSTATUS-\'+row.entity.positionStatus]}}</div>'},
                    {field: 'guidDuty', displayName: '所属职务', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidDuty | translateDuty) + $root.constant[row.entity.guidDuty]}}</div>'},
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
                                toastr['success']('添加岗位成功');
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
                    toastr['success']('删除员工成功');
                } else {
                    toastr['error'](data.retMessage);
                }
                reposgrid();
            })
        }
    }

    //应用列表事件
    workgroup.addGpApp = function () {
        console.log($scope.sub)
        var subFrom = {};
        subFrom.groupGuid = $scope.sub.guid;
        subFrom.groupCode = $scope.sub.groupCode;
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
                    {field: 'appType', displayName: '应用类别', enableHiding: false,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.appType | translateConstants :\'DICT_AC_APPTYPE\') + $root.constant[\'DICT_AC_APPTYPE-\'+row.entity.appType]}}</div>',
                        filter:{
                            //term: '0',//默认搜索那项
                            type: uiGridConstants.filter.SELECT,
                            selectOptions: [{ value: 'local', label: '本地'}, { value: 'remote', label: '远程' }]
                        }

                    },
                    {field: 'openDate', displayName: '开通时间', enableHiding: false},
                    {field: 'appDesc', displayName: '功能描述', enableHiding: false}
                ];
                $scope.commonGrid = initgrid($scope, commonGrid, filterFilter, com, false, function () {
                });
                (function (subFrom) {
                    Workgroup_service.queryNotInApp(subFrom).then(function (data) {
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
                    Workgroup_service.addGroupApp(subFrom).then(function (data) {
                        if (data.status == "success") {
                            toastr['success']("新增成功!");
                            reappgrid();
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

    workgroup.deleteGpApp = function () {
        var subFrom = {};
        subFrom.groupGuid = $scope.sub.guid;
        subFrom.groupCode = $scope.sub.groupCode;
        var arr = $scope.appgrid.getSelectedRows();
        if (arr.length != 1) {
            toastr['error']("请选择一条需要删除的应用!");
            return false;
        }
        subFrom.appGuid = arr[0].guid;
        Workgroup_service.deleteGroupApp(subFrom).then(function (data) {
            if (data.status == "success") {
                toastr['success']("删除成功!");
                reappgrid();
            } else {
                toastr['error'](data.retMessage);
            }
        });
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




    /**--------------------------------------生成各类grid------------------------------------*/
    var appgrid = {};
    $scope.appgrid = appgrid;
    //定义单选事件
    var selework = function () {

    }
    //定义表头名
    var com = [
        {field: 'appName', displayName: '应用名称', enableHiding: false},
        {field: 'appType', displayName: '应用类别', enableHiding: false,
            cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.appType | translateConstants :\'DICT_AC_APPTYPE\') + $root.constant[\'DICT_AC_APPTYPE-\'+row.entity.appType]}}</div>',
            filter:{
                //term: '0',//默认搜索那项
                type: uiGridConstants.filter.SELECT,
                selectOptions: [{ value: 'local', label: '本地'}, { value: 'remote', label: '远程' }]
            }
            },
        {field: 'openDate', displayName: '开通时间', enableHiding: false},
        {field: 'appDesc', displayName: '功能描述', enableHiding: false}
    ];
    $scope.appgrid = initgrid($scope, appgrid, filterFilter, com, false, selework);

    var reappgrid = function () {
        var subFrom = {};
        subFrom.groupCode = $scope.sub.groupCode
        Workgroup_service.queryApp(subFrom).then(function (data) {
            if (data.status == "success" && !isNull(data.retMessage)) {
                $scope.appgrid.data = data.retMessage;
                $scope.appgrid.mydefalutData = data.retMessage;
                $scope.appgrid.getPage(1, $scope.appgrid.paginationPageSize);
            } else {
                $scope.appgrid.data = [];
                $scope.appgrid.mydefalutData = [];
                $scope.appgrid.getPage(1, $scope.appgrid.paginationPageSize);
            }
        })
    }
});