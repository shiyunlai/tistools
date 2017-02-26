/**
 * Created by Jack.Gao on 2017/1/4.
 */
MetronicApp.controller('busiorg_controller', function ($filter,$rootScope, $scope, $state, $stateParams, filterFilter,busiorg_service, $modal, $http, $timeout,$interval) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });
    var orgs = {};
    $scope.orgs = orgs;
    initController($scope, orgs, 'orgs', busiorg_service, filterFilter);
    orgs.search1();
    console.log(orgs.dataList);
    var orglevel = "";
    $scope.orglevel = orglevel;
    //单个业务套别
    var busi = {};
    $scope.busi = busi;
    var contextualMenuSample = function () {
        $("#org_tree").jstree({
            "core": {
                // "themes": {
                //     "responsive": false
                // },
                // so that create works
                // "check_callback": true,
                'data' :  {
                    'url' : "http://localhost:8089/tis/torgtree/treeList",
                    'type':'GET',
                    'data':function (node) {
                        return { 'id' : node.id };
                    },
                    "dataType" : "json",
                    "success":function(data){
                        // console.log(data)
                    }
                },

            },
            "types": {
                "default": {
                    "icon": "fa fa-folder icon-state-warning icon-lg"
                },
                "file": {
                    "icon": "fa fa-file icon-state-warning icon-lg"
                }
            },
            "state": {"key": "demo2"},
            "plugins": ["contextmenu", "dnd", "state", "types"],
            "contextmenu": {
                "items": {
                    "create": null,
                    "rename": null,
                    "remove": null,
                    "ccp": null,
                    "增加下级机构": {
                        "icon": "fa fa-file icon-state-warning icon-lg",
                        "label": "增加下级机构",
                        "action": function (obj) {
                            alert((obj))
                        }
                    },
                    "增加下级岗位": {
                        "icon": "fa fa-file icon-state-warning icon-lg",
                        "label": "增加下级岗位",
                        "action": function (obj) {
                            alert((obj))
                        }
                    },
                    "增加人员": {
                        "icon": "fa fa-file icon-state-warning icon-lg",
                        "label": "增加人员",
                        "action": function (obj) {
                            alert((obj))
                        }
                    },
                    /*"包含子级菜单": {
                     "label": "包含子级菜单",
                     "submenu": {
                     "cut": {
                     "separator_before": false,
                     "separator_after": false,
                     "label": "Cut",
                     "action": function (obj) {
                     alert("Cut")
                     }
                     }
                     }
                     }*/
                }
            }
        });
    }
    contextualMenuSample();
    $("#org_tree").bind("activate_node.jstree", function(obj,e) {
        console.log(e);
        var currentNode = e.node.id;
        busiorg_service.loadById(currentNode).then(function (datas) {
            $scope.orglevel = datas.orglevel;
            $scope.busi = datas;
        })
        $scope.$apply();
    })

});