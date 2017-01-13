
MetronicApp.controller('orgList_controller', function ($filter, $rootScope, $scope, $state, $stateParams, organization_service, employee_service, filterFilter, $modal, $http, $timeout, $interval) {

    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });


    var orgs = {};
    $scope.orgs = orgs;
    initController($scope, orgs, 'orgs', organization_service, filterFilter);
    orgs.search1();
    var org = {};
    $scope.org = org;

    org.saveOrg = function (item) {
        var promise = organization_service.save(item);
        promise.then(function (data) {
            if (data.retCode == '1') {
                toastr['success'](data.retMessage, "更改机构信息成功！");
                $("#org_tree").jstree(true).refresh("#" + item.orgid);
            } else if(data.retCode == '2') {
                toastr['error'](data.retMessage, "更改机构信息失败！");
            } else {
                toastr['error']( "更改机构信息异常！");
            }
        });

    }


    var emps = {};
    $scope.emps = emps;
    initController($scope, emps, 'emps', employee_service, filterFilter);
    emps.initEmpList = function () {
        emps.search1();
    }



    var viewType = "root";
    $scope.viewType = viewType;

    var orgTree = $("#org_tree");
    orgTree.jstree({
        "core": {
             "themes": {
                 "responsive": false
             },
            // so that create works
            "check_callback": true,
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
    orgTree.bind("select_node.jstree", function (obj, data) {
        var a=data.node.data;
        $scope.$apply(function () {
            $scope.viewType=a;
        });
        if(a == 'org') {
            organization_service.loadByOrgId(data.node.id).then(function (res) {
                if(res.parentorgid == 0) {
                    res.parentorgid = undefined
                }
                $scope.org.item = res;
            });
        }
        $scope.$apply(function () {
            $scope.viewType=a;
        });


    });
});
