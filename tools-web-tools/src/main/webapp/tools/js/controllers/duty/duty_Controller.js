/**
 * Created by gaojie on 2017/7/26.
 */
angular.module('MetronicApp').controller('duty_controller', function($rootScope, $scope,duty_service, $http, $timeout,i18nService,filterFilter,uiGridConstants,$uibModal,$state) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        App.initAjax();
    });
    //定义主题信息
    var duty = {};
    $scope.duty = duty;
    var item = {};
    $scope.duty.item = item;
    //定义页签标识
    var flag = {};
    $scope.flag = flag;
    //定义右侧页面切换标志
    var tabflag = true;
    $scope.tabflag = tabflag;
    //导航节点信息
    var title = "";
    $scope.title = title;
    //定义标签控制标识
    var xqxx = false;
    flag.xqxx = xqxx;
    var xjzw = false;
    flag.xjzw = xjzw;
    var zwry = false;
    flag.zwry = zwry;
    var zwqx = false;
    flag.zwqx = zwqx;
    //编辑标志
    var editflag = false;
    $scope.editflag = editflag;
    
    //ui-grid
    i18nService.setCurrentLang("zh-cn");
    //自定义树右键菜单
    var items = function customMenu(node) {
        console.log(node);
        if(node.parent == "#") {
            var it = {
                "新建菜单": {
                    "id": "create",
                    "label": "新建职务套别",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        openwindow($uibModal, 'views/org/enablecom_window.html', 'lg',
                            function ($scope, $modalInstance) {

                            })
                    }
                }
            };
            return it;
        }else if(node.id.length == 2){
            var it = {
                "新建菜单": {
                    "id": "create",
                    "label": "新增职务",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj);
                        var dutyType = obj.id;
                        openwindow($uibModal, 'views/duty/addDuty_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建职务实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //首先生成职务代码,获取职务套别
                                subFrom.dutyType = dutyType;
                                console.log(subFrom)
                                duty_service.initdutyCode(subFrom).then(function (data) {
                                    console.log(data)
                                    if(data.status == "success"){
                                        subFrom.dutyCode = data.retMessage;
                                    }else{
                                        toastr['error'](data.retMessage);
                                    }
                                })
                                $scope.save = function () {
                                    duty_service.addduty(subFrom).then(function (data) {
                                        if(data.status == "success"){
                                            toastr['success'](data.retMessage);
                                            $("#dutytree").jstree().refresh();
                                            redutygrid();
                                            $scope.cancel();
                                        }else{
                                            toastr['error'](data.retMessage);
                                        }
                                    })
                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                        });
                    }
                },
                "删除职务":{
                    "id":"delete",
                    "label":"删除职务",
                    "action":function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj);
                        if(confirm("确认要删除此职务吗?")){
                            var subFrom = {};
                            subFrom.dutyCode = obj.id;
                            duty_service.deletedutyByCode(subFrom).then(function (data) {
                                if(data.status == "success"){
                                    toastr['success'](data.retMessage);
                                    $("#dutytree").jstree().refresh();
                                }else{
                                    toastr['error'](data.retMessage);
                                }
                            })
                        }else{
                            return false;
                        }
                    }
                }
            };
            return it;
        }else {
            var it = {
                "新建菜单": {
                    "id": "create",
                    "label": "新增子职务",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj);
                        if(obj.id.length == 2){
                            var dutyType = obj.id;
                            var parentsCode = "";
                        }else{
                            var dutyType = obj.original.dutyType;
                            var parentsCode = obj.original.dutyCode;
                        }

                        openwindow($uibModal, 'views/duty/addDuty_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建职务实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //首先生成职务代码,获取职务套别
                                subFrom.dutyType = dutyType;
                                subFrom.parentsCode = parentsCode;
                                console.log(subFrom)
                                duty_service.initdutyCode(subFrom).then(function (data) {
                                    console.log(data)
                                    if(data.status == "success"){
                                        subFrom.dutyCode = data.retMessage;
                                    }else{
                                        toastr['error'](data.retMessage);
                                    }
                                })
                                $scope.save = function () {
                                    duty_service.addduty(subFrom).then(function (data) {
                                        if(data.status == "success"){
                                            toastr['success'](data.retMessage);
                                            $("#dutytree").jstree().refresh();
                                            redutygrid();
                                            $scope.cancel();
                                        }else{
                                            toastr['error'](data.retMessage);
                                        }
                                    })
                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            });
                    }
                },
                "删除职务":{
                    "id":"delete",
                    "label":"删除职务",
                    "action":function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj);
                        if(confirm("确认要删除此职务吗?")){
                            var subFrom = {};
                            subFrom.dutyCode = obj.id;
                            duty_service.deletedutyByCode(subFrom).then(function (data) {
                                if(data.status == "success"){
                                    toastr['success'](data.retMessage);
                                    $("#dutytree").jstree().refresh();
                                }else{
                                    toastr['error'](data.retMessage);
                                }
                            })
                        }else{
                            return false;
                        }
                    }
                }
            };
            return it;
        }
    }

    //组织职务树
    $("#dutytree").jstree({
        "core" : {
            "themes" : {
                "responsive": false
            },
            // so that create works
            "check_callback" : true,
            'data' : function (obj, callback) {
                var jsonarray = [];
                $scope.jsonarray = jsonarray;
                var subFrom = {};
                subFrom.id = obj.id;
                console.log(obj)
                if(!isNull(obj.original)){
                    subFrom.guidOrg = obj.original.guid;
                    subFrom.positionCode = obj.original.positionCode;
                }else{
                    subFrom.guidOrg = "";
                    subFrom.positionCode = "";
                }

                duty_service.loadmaintree(subFrom).then(function (datas) {
                    console.log(datas)
                    var data = datas.retMessage;
                    if(!isNull(data[0])){
                        if(!isNull(data[0].itemName)){
                            for(var i = 0;i<data.length;i++){
                                data[i].id = data[i].itemValue;
                                data[i].text = data[i].itemName;
                                data[i].children = true;
                                data[i].icon = 'fa fa-users icon-state-info icon-lg'
                            }
                        }else if(isNull(data[0].text)){
                            for(var i = 0;i<data.length;i++){
                                data[i].id = data[i].dutyCode;
                                data[i].text = data[i].dutyName;
                                data[i].children = true;
                                data[i].icon = 'fa fa-users icon-state-info icon-lg'
                            }
                        }else{
                            for(var i = 0;i<data.length;i++){
                                data[i].children = true;
                                data[i].icon = 'fa fa-users icon-state-info icon-lg'
                            }
                        }
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
    }).bind("copy.jstree", function (node,e, data ) {
        console.log(e);
        console.log(data);
        console.log(node);
    }).bind("paste.jstree",function (a,b,c,d) {
        console.log(a);
        console.log(b);
        console.log(c);
        console.log(d);
    }).bind("move_node.jstree",function (e,data) {
        if(confirm("确认要移动此职务吗?")){
            //TODO.
        }else{
            // data.inst.refresh(data.inst._get_parent(data.rslt.oc));
            return false;
        }
        console.log(e);
        console.log(data);
    }).bind("dnd_stop.vakata",function (e,data) {
        console.log(data);
    }).bind("select_node.jstree", function (e, data) {
        if(typeof data.node !== 'undefined'){//拿到结点详情
            // console.log(data.node.original.id.indexOf("@"));
            $scope.duty.item = data.node.original;
            console.log(data.node);
            $scope.title = data.node.text;
            if(data.node.id == "00000") {
                for (var i in $scope.flag){
                    flag[i] = false;
                }
                $scope.tabflag = true;
                $scope.addflag = false;
                console.log(1)
                redutygrid();
            }else if(data.node.id.length == 2){
                for (var i in $scope.flag){
                    flag[i] = false;
                }
                $scope.tabflag = true;
                $scope.addflag = false;
                console.log($scope.duty.item.itemValue);
                console.log(2)
                dutygridbyType($scope.duty.item.itemValue);
                $scope.addflag = true;
            }else{
                $scope.tabflag = false;
                for (var i in $scope.flag){
                    flag[i] = false;
                }
                $scope.flag.xqxx = true;
                $scope.duty.item.parentsCode = data.node.parent;
            }
            ($scope.$$phase)?null: $scope.$apply();
        }
    });
    //页签切换
    duty.loaddata = function (num) {
        if(num == 0){
            //基本信息
            for (var i in $scope.flag){
                flag[i] = false;
            }
            $scope.flag.xqxx = true;
            //TODO
        }else if(num == 1){
            //下级职务
            for (var i in $scope.flag){
                flag[i] = false;
            }
            $scope.flag.xjzw = true;
            //生成下级职务列表
            var xjzwgrid = {};
            $scope.xjzwgrid = xjzwgrid;
            var com = [{ field: 'dutyCode', displayName: '职务代码', enableHiding: false},
                { field: 'dutyName', displayName: '职务名称', enableHiding: false},
                { field: 'dutyType', displayName: '职务类型', enableHiding: false},
                { field: 'remark', displayName: '备注', enableHiding: false}]
            $scope.xjzwgrid = initgrid($scope,xjzwgrid,filterFilter,com,true,function () {
                
            });
            var rexjzwgrid = function () {
                var subFrom = {};
                subFrom.dutyCode = $scope.duty.item.dutyCode;
                duty_service.querychild(subFrom).then(function (data) {
                    if(data.status == "success"){
                        $scope.xjzwgrid.data =  data.retMessage;
                        $scope.xjzwgrid.mydefalutData =  data.retMessage;
                        $scope.xjzwgrid.getPage(1,$scope.xjzwgrid.paginationPageSize);
                    }else{

                    }
                })
            };
            //拉取下级职务列表
            rexjzwgrid();
        }else if(num == 2){
            //人员信息
            for (var i in $scope.flag){
                flag[i] = false;
            }
            $scope.flag.zwry = true;
            //生成人员列表
            var zwrygrid = {};
            $scope.zwrygrid = zwrygrid;
            //定义表头名
            var com = [{ field: 'empCode', displayName: '员工代码', enableHiding: false},
                { field: 'empName', displayName: '员工姓名', enableHiding: false},
                { field: 'gender', displayName: '性别', enableHiding: false},
                { field: 'empstatus', displayName: '员工状态', enableHiding: false},
                { field: 'empDegree', displayName: '员工职级', enableHiding: false},
                { field: 'guidPostition', displayName: '基本岗位', enableHiding: false},
                { field: 'guidempmajor', displayName: '直接主管', enableHiding: false},
                { field: 'indate', displayName: '入职日期', enableHiding: false},
                { field: 'otel', displayName: '办公电话', enableHiding: false}
            ]
            $scope.zwrygrid = initgrid($scope,zwrygrid,filterFilter,com,true,function () {
                
            });
            var rezwrygrid = function () {
                var subFrom = {};
                subFrom.dutyCode = $scope.duty.item.dutyCode;
                duty_service.queryempbudutyCode(subFrom).then(function (data) {
                    if(data.status == "success"){
                        $scope.zwrygrid.data =  data.retMessage;
                        $scope.zwrygrid.mydefalutData =  data.retMessage;
                        $scope.zwrygrid.getPage(1,$scope.zwrygrid.paginationPageSize);
                    }else{

                    }
                })
            };
            //拉取列表
            rezwrygrid();
        }else if(num == 3){
            for (var i in $scope.flag){
                flag[i] = false;
            }
            $scope.flag.zwqx = true;
        }
    }
    
    
    
    
    //生成职务列表
    var dutygrid = {};
    $scope.dutygrid = dutygrid;
    var com = [{ field: 'dutyCode', displayName: '职务代码', enableHiding: false},
        { field: 'dutyName', displayName: '职务名称', enableHiding: false},
        { field: 'dutyType', displayName: '职务类型', enableHiding: false},
        { field: 'remark', displayName: '备注', enableHiding: false}
    ]
    $scope.dutygrid = initgrid($scope,dutygrid,filterFilter,com,true,function () {
        
    });
    //通过type拉取列表
    var dutygridbyType = function (type) {
        var subFrom = {};
        subFrom.dutyType = type;
        duty_service.querydutybyType(subFrom).then(function (data) {
            console.log(data)
            if(data.status == "success"){
                $scope.dutygrid.data =  data.retMessage;
                $scope.dutygrid.mydefalutData =  data.retMessage;
                $scope.dutygrid.getPage(1,$scope.dutygrid.paginationPageSize);
            }else{

            }
        })
    };
    //拉取列表方法
    var redutygrid = function () {
        //调取所有职务信息
        duty_service.loadallduty().then(function (data) {
            console.log(data.retMessage)
            if(data.status == "success"){
                $scope.dutygrid.data =  data.retMessage;
                $scope.dutygrid.mydefalutData =  data.retMessage;
                $scope.dutygrid.getPage(1,$scope.dutygrid.paginationPageSize);
            }else{

            }

        })
    }
    //拉取列表
    redutygrid();
    
    //编辑------返回方法
    duty.editduty = function () {
        var subFrom = {};
        $scope.subFrom = subFrom;
        $scope.subFrom = angular.copy($scope.duty.item);
        $scope.editflag = !$scope.editflag;
    }
    //更新
    duty.save = function () {
        console.log($scope.subFrom);
        duty_service.updateDuty($scope.subFrom).then(function (data) {
            if(data.status == "success"){
                toastr['success'](data.retMessage);
                $("#dutytree").jstree().refresh();
                $scope.editflag = !$scope.editflag;
                // var node = {};
                // node.id = $scope.duty.item.dutyCode;
                // $("#dutytree").jstree().select_node(node,false,false);
            }else{
                toastr['error'](data.retMessage);
            }
        })
    }
    //删除
    duty.deleteduty = function () {
        console.log($scope.duty.item)
        if(confirm("确认要删除此职务吗?")){
            var subFrom = {};
            subFrom.dutyCode = $scope.duty.item.dutyCode;
            duty_service.deletedutyByCode(subFrom).then(function (data) {
                if(data.status == "success"){
                    toastr['success'](data.retMessage);
                    $("#dutytree").jstree().refresh();
                    var node = {};
                    node.id = $scope.duty.item.parentsCode;
                    $("#dutytree").jstree().select_node(node,false,false);
                }else{
                    toastr['error'](data.retMessage);
                }
            })
        }else{
            return false;
        }
    }

    //职务列表按钮事件
    duty.addzw = function () {
        var dutyType = $scope.duty.item.itemValue;
        openwindow($uibModal, 'views/duty/addDuty_window.html', 'lg',
            function ($scope, $modalInstance) {
                //创建职务实例
                var subFrom = {};
                $scope.subFrom = subFrom;
                //首先生成职务代码,获取职务套别
                subFrom.dutyType = dutyType;
                console.log(subFrom)
                duty_service.initdutyCode(subFrom).then(function (data) {
                    console.log(data)
                    if(data.status == "success"){
                        subFrom.dutyCode = data.retMessage;
                    }else{
                        toastr['error'](data.retMessage);
                    }
                })
                $scope.save = function () {
                    duty_service.addduty(subFrom).then(function (data) {
                        if(data.status == "success"){
                            toastr['success'](data.retMessage);
                            $("#dutytree").jstree().refresh();
                            redutygrid();
                            $scope.cancel();
                        }else{
                            toastr['error'](data.retMessage);
                        }
                    })
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            });
    }
    duty.editzw = function () {
        var arr = $scope.dutygrid.getSelectedRows();
        if(arr.length != 1){
            toastr['error']("请选择一条需要修改的数据!");
            return false;
        }else{
            console.log(arr[0].dutyCode)
            var node = {};
            node.id = arr[0].dutyType;
            var node2 = {};
            node2.id = arr[0].dutyCode;
            $("#dutytree").jstree().deselect_all(true);
            $("#dutytree").jstree().load_node(node,function () {
                $("#dutytree").jstree().select_node(node2,false,false);
            });
            $scope.subFrom = arr[0];
            $scope.editflag = !$scope.editflag;
        }
    }
    duty.deletezw = function () {
        var arr = $scope.dutygrid.getSelectedRows();
        if(arr.length != 1){
            toastr['error']("请选择一条需要删除的数据!");
            return false;
        }else{
            var subFrom = {};
            subFrom.dutyCode = arr[0].dutyCode;
            duty_service.deletedutyByCode(subFrom).then(function (data) {
                if(data.status == "success"){
                    toastr['success'](data.retMessage);
                    $("#dutytree").jstree().refresh();
                    redutygrid();
                }else{
                    toastr['error'](data.retMessage);
                }
            })
        }
    }
});