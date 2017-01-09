'use strict';
MetronicApp.controller('orgList_controller', function ($filter, $rootScope, $scope, $state, $stateParams, organization_service, employee_service, filterFilter, $modal, $http, $timeout, $interval) {

    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });
    var orgs = {};
    $scope.orgs = orgs;
    initController($scope, orgs, 'orgs', organization_service, filterFilter);
    orgs.search1();

    var emps = {};
    $scope.emps = emps;
    initController($scope, emps, 'emps', employee_service, filterFilter);
    emps.search1();

    var contextualMenuSample = function () {
        $("#org_tree").jstree({
            "core": {
                // "themes": {
                //     "responsive": false
                // },
                // so that create works
                // "check_callback": true,
                'data' :  {
                    'url' : "http://localhost:8089/tis/torg/treeList",
                    'type':'GET',
                    'data':function (node) {
                        return { 'id' : node.id };
                    },
                    "dataType" : "json",
                    "success":function(data){
                        console.log(data)
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
});
