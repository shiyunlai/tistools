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
                        console.log(obj)
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
                        console.log(obj)
                        openwindow($uibModal, 'views/duty/addDuty_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建职务实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //首先生成职务代码,获取职务套别
                                subFrom.dutyType = $scope.duty.item.dutyType;
                                duty_service.initdutyCode(subFrom).then(function (data) {
                                    console.log(data)
                                    if(data.status == "success"){

                                    }else{
                                        toastr['error'](data.retMessage);
                                    }
                                })
                                $scope.save = function () {
                                    duty_service.addduty(subFrom).then(function (data) {
                                        console.log(data);
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

                abftree_service.loadmaintree(subFrom).then(function (datas) {
                    console.log(datas)
                    var data = datas.retMessage;
                    for(var i = 0;i<data.length;i++){
                        data[i].children = true;
                        data[i].icon = 'fa fa-users icon-state-info icon-lg'
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
            $scope.duty.item = {};
            console.log(data.node.original);
            if(data.node.original.id.indexOf("POSIT") == 0) {

            }
            ($scope.$$phase)?null: $scope.$apply();
        }
    });

    //生成职务列表
    var dutygrid = {};
    $scope.dutygrid = dutygrid;
    var com = [{ field: 'dutyCode', displayName: '职务代码', enableHiding: false},
        { field: 'dutyName', displayName: '职务名称', enableHiding: false},
        { field: 'dutyType', displayName: '职务类型', enableHiding: false},
        { field: 'remark', displayName: '备注', enableHiding: false}
    ]
    $scope.dutygrid = initgrid($scope,dutygrid,filterFilter,com,true);
    //拉取列表方法
    var redutygrid = function () {
        //调取所有职务信息
        abftree_service.loadallduty().then(function (data) {
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
});