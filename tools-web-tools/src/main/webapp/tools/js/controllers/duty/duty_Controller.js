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
                            var guidParents = "";
                        }else{
                            var dutyType = obj.original.dutyType;
                            var guidParents = obj.original.guid;
                        }

                        openwindow($uibModal, 'views/duty/addDuty_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建职务实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //首先生成职务代码,获取职务套别
                                subFrom.dutyType = dutyType;
                                subFrom.guidParents = guidParents;
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
            console.log(data.node.original);
            $scope.title = data.node.text;
            if(data.node.id.length == 2 || data.node.id == "00000") {
                $scope.tabflag = true;
                if(data.node.id != "00000"){
                    console.log($scope.duty.item.itemValue)
                    dutygridbyType($scope.duty.item.itemValue);
                }
            }else{
                $scope.tabflag = false;
                for (var i in $scope.flag){
                    flag[i] = false;
                }
                $scope.flag.xqxx = true;
            }
            ($scope.$$phase)?null: $scope.$apply();
        }
    });
    //页签切换
    duty.loaddata = function (num) {
        if(num == 0){
            //基本信息
            for (var i in flag){
                flag[i] = false;
            }
            flag.xqxx = true;
            //TODO
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
    $scope.dutygrid = initgrid($scope,dutygrid,filterFilter,com,true);
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
        $scope.editflag = !$scope.editflag;
    }
    //更新
    duty.save = function () {
        
    }
    //删除
    duty.deleteduty = function () {
        
    }
});