/**
 * Created by gaojie on 2017/8/2.
 */
angular.module('MetronicApp').controller('busiorg_controller', function($rootScope, $scope,busiorg_service, $http, $timeout,i18nService,filterFilter,uiGridConstants,$uibModal,$state) {
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
    //页签控制对象
    var flag = {};
    $scope.flag = flag;
    /**-----------------------------------右键自定义菜单-------------------------------- */




    /**-----------------------------------业务机构树-------------------------------- */
    //业务机构树
    $("#busiorgtree").jstree({
        "core" : {
            "themes" : {
                "responsive": false
            },
            "check_callback" : true,
            'data' : function (obj, callback) {
                var jsonarray = [];
                $scope.jsonarray = jsonarray;
                var subFrom = {};
                subFrom.id = obj.id;
                console.log(obj)


                abftree_service.loadmaintree(subFrom).then(function (datas) {
                    var data = datas.retMessage;

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
            show_only_matches:true
        },
        'callback' : {
            move_node:function (node) {
                console.log(node)
            }
        },
        "plugins" : [ "dnd", "state", "types","search","contextmenu" ]
    }).bind("select_node.jstree", function (e, data) {
        if(typeof data.node !== 'undefined'){//拿到结点详情
            // console.log(data.node.original.id.indexOf("@"));
            $scope.abftree.item = {};
            console.log(data.node.original);

            ($scope.$$phase)?null: $scope.$apply();
        }
    });



    /**-----------------------------------各类列表数据拉取方法-------------------------------- */




    /**-----------------------------------各类按钮事件定义-------------------------------- */



    /**-----------------------------------页签控制-------------------------------- */
});