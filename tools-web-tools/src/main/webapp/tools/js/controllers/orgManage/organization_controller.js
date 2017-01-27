
MetronicApp.controller('orgList_controller', function ($filter, $rootScope, $scope, $state, $stateParams, organization_service,position_service, employee_service,childOrg_service, childPosi_service, childEmp_service, filterFilter, $modal, $http, $timeout, $interval) {

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
    emps.initEmpList = function () {
        initController($scope, emps, 'emps', employee_service, filterFilter);
        emps.search1();
    }

    //初始界面
    var viewType = "root";
    $scope.viewType = viewType;


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

    var posi = {};
    $scope.posi = posi;
    posi.savePosi = function (item) {
        var promise = position_service.save(item);
        promise.then(function (data) {
            if (data.retCode == '1') {
                toastr['success'](data.retMessage, "更改岗位信息成功！");
                $("#org_tree").jstree(true).refresh("#" + item.positionid);
            } else if(data.retCode == '2') {
                toastr['error'](data.retMessage, "更改岗位信息失败！");
            } else {
                toastr['error']( "更改岗位信息异常！");
            }
        });

    }


    /**下级机构信息---开始**/
    var childOrg = {};
    $scope.childOrg = childOrg;
    initController($scope, childOrg, 'childOrg', childOrg_service, filterFilter);
    childOrg.initChildOrgList = function (data) {
        childOrg.searchForm.searchItems = {"parentorgid_eq":data }
        childOrg.search1();
    }

    childOrg.add = function(id,name){
        openwindow($modal, 'views/orgManage/org_add.html', 'lg',
            function ($scope, $modalInstance) {
                var item = {};
                $scope.item = item;
                if(!isNull(id)) {
                    $scope.parentorg = name;
                    item.parentorgid = id;
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
                $scope.saveOrg = function(item){
                    organization_service.save(item).then(function (data) {
                        if (data.retCode == '1') {
                            toastr['success'](data.retMessage, "新增成功！");
                            $modalInstance.close();
                            $("#org_tree").jstree(true).refresh("#" + id);
                        } else if(data.retCode == '2') {
                            toastr['error'](data.retMessage, "新增失败！");
                        } else {
                            toastr['error']( "新增异常！");
                        }
                    });
                }
            }
        )

    }

    childOrg.edit = function(id,name){
        openwindow($modal, 'views/orgManage/org_add.html', 'lg',
            function ($scope, $modalInstance) {

                organization_service.loadById(id).then(function (res) {
                    $scope.item = res;
                    $scope.parentorg = name;
                });

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
                $scope.saveOrg = function(item){
                    alert(JSON.stringify(item))
                    organization_service.save(item).then(function (data) {
                        if (data.retCode == '1') {
                            toastr['success'](data.retMessage, "编辑成功！");
                            $modalInstance.close();
                            $("#org_tree").jstree(true).refresh("#" + id);
                        } else if(data.retCode == '2') {
                            toastr['error'](data.retMessage, "编辑失败！");
                        } else {
                            toastr['error']( "编辑异常！");
                        }
                    });
                }
            }
        )

    }
    
    childOrg.del = function (id) {
        var res = confirm("删除是不可恢复的，你确认要删除吗？");
        if (res) {
            var promise = childOrg_service.delete(id);
            promise.then(function (data) {
                if(data.retCode == "1") {
                    toastr['success'](data.retMessage, "恭喜您！")
                    childOrg.searchN();
                    $("#org_tree").jstree(true).refresh("#" + currentOrgId);
                } else if(data.retCode == "2") {
                    toastr['error'](data.retMessage, "删除失败！");
                } else {
                    toastr['error']( "删除异常！");
                }
            });
        }
    }
    /**下级机构信息---结束**/

    /**下级岗位信息---开始**/
    var childPosi = {};
    $scope.childPosi = childPosi;
    initController($scope, childPosi, 'childPosi', childPosi_service, filterFilter);
    childPosi.initChildPosiList = function (data) {
        if($scope.viewType == 'org') {
            childPosi.searchForm.searchItems = {"orgid_eq":data,"manaposi_isNul":''}
        }
        if($scope.viewType == 'posi') {
            childPosi.searchForm.searchItems = {"manaposi_eq":data }
        }
        childPosi.search1();
    }

    childPosi.add = function(parid,name){
        openwindow($modal, 'views/orgManage/posi_add.html', 'lg',
            function ($scope, $modalInstance) {
                var item = {};
                $scope.item = item;
                if(!isNull(parid)) {

                    if($scope.viewType == 'org') {
                        item.orgid = parid;
                        $scope.parentorg = name;
                    }
                    if($scope.viewType == 'posi') {
                        item.manaposi = parid;
                        item.orgid = name;
                        $scope.parentorg = name;
                    }

                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
                $scope.savePosi = function(item){
                    childPosi_service.save(item).then(function (data) {
                        if (data.retCode == '1') {
                            toastr['success'](data.retMessage, "新增成功！");
                            $modalInstance.close();
                            $("#org_tree").jstree(true).refresh("#" + id);
                        } else if(data.retCode == '2') {
                            toastr['error'](data.retMessage, "新增失败！");
                        } else {
                            toastr['error']( "新增异常！");
                        }
                    });
                }
            }
        )

    }

    childPosi.edit = function(id,name){
        openwindow($modal, 'views/orgManage/posi_add.html', 'lg',
            function ($scope, $modalInstance) {

                childPosi_service.loadById(id).then(function (res) {
                    if(res.dutyid == 0) {
                        res.dutyid = undefined
                    }
                    if(res.manaposi == 0) {
                        res.manaposi = undefined
                    }
                    $scope.item = res;
                    $scope.parentorg = name;
                });

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
                $scope.savePosi = function(item){
                    childPosi_service.save(item).then(function (data) {

                        if (data.retCode == '1') {
                            toastr['success'](data.retMessage, "编辑成功！");
                            $modalInstance.close();
                            $("#org_tree").jstree(true).refresh("#" + id);
                        } else if(data.retCode == '2') {
                            toastr['error'](data.retMessage, "编辑失败！");
                        } else {
                            toastr['error']( "编辑异常！");
                        }
                    });
                }
            }
        )

    }

    childPosi.del = function (id) {
        var res = confirm("删除是不可恢复的，你确认要删除吗？");
        if (res) {
            var promise = childPosi_service.delete(id);
            promise.then(function (data) {
                if(data.retCode == "1") {
                    toastr['success'](data.retMessage, "恭喜您！")
                    childPosi.searchN();
                    $("#org_tree").jstree(true).refresh("#" + currentOrgId);
                } else if(data.retCode == "2") {
                    toastr['error'](data.retMessage, "删除失败！");
                } else {
                    toastr['error']( "删除异常！");
                }
            });
        }
    }
    /**下级岗位信息---结束**/

    /**下级人员信息---开始**/
    var childEmp = {};
    $scope.childEmp = childEmp;
    initController($scope, childEmp, 'childEmp', childEmp_service, filterFilter);
    childEmp.initChildEmpList = function (data) {
        if($scope.viewType == 'org') {
            childEmp.searchForm.searchType = "org";
            childEmp.searchForm.searchItems = {"orgid_eq":data }
        }
        if($scope.viewType == 'posi') {
            childEmp.searchForm.searchType = "posi";
            childEmp.searchForm.searchItems = {"positionid_eq":data }
        }
        childEmp.search1();
    }

    childEmp.add = function(id){
        openwindow($modal, 'views/orgManage/emp_add.html', 'lg',
            function ($scope, $modalInstance) {
                var requestBody ={};
                $scope.requestBody = requestBody;
                var item = {};
                requestBody.item = item;
                if(!isNull(id)) {
                    requestBody.orgid = id;
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
                $scope.saveEmp = function(data){
                    childEmp_service.save(data).then(function (data) {
                        if (data.retCode == '1') {
                            toastr['success'](data.retMessage, "新增成功！");
                            $modalInstance.close();
                            $("#org_tree").jstree(true).refresh("#" + id);
                        } else if(data.retCode == '2') {
                            toastr['error'](data.retMessage, "新增失败！");
                        } else {
                            toastr['error']( "新增异常！");
                        }
                    });
                }
            }
        )

    }

    childEmp.edit = function(empId,orgId){
        openwindow($modal, 'views/orgManage/emp_add.html', 'lg',
            function ($scope, $modalInstance) {
                var requestBody ={};
                $scope.requestBody = requestBody;
                var item = {};
                requestBody.item = item;
                requestBody.orgid = orgId;
                childEmp_service.loadById(empId).then(function (res) {
                    $scope.requestBody.item = res;
                });

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
                $scope.saveEmp = function(item){
                    childEmp_service.save(item).then(function (data) {

                        if (data.retCode == '1') {
                            toastr['success'](data.retMessage, "编辑成功！");
                            $modalInstance.close();
                            $("#org_tree").jstree(true).refresh("#" + orgId);
                        } else if(data.retCode == '2') {
                            toastr['error'](data.retMessage, "编辑失败！");
                        } else {
                            toastr['error']( "编辑异常！");
                        }
                    });
                }
            }
        )

    }

    childEmp.del = function (id,empId,orgId) {
        var res = confirm("删除是不可恢复的，并且会同时删除相关组织信息，你确认要删除吗？");
        if (res) {
            var params = {};
            params.id = id;
            params.empId = empId;
            params.orgId = orgId;
            var promise = childEmp_service.delete(params);
            promise.then(function (data) {
                if(data.retCode == "1") {
                    toastr['success'](data.retMessage, "成功！")
                    childEmp.searchN();
                    $("#org_tree").jstree(true).refresh("#" + currentOrgId);
                } else if(data.retCode == "2") {
                    toastr['error'](data.retMessage, "失败！");
                } else {
                    toastr['error']( "删除异常！");
                }
            });
        }
    }
    /**下级人员信息---结束**/


    //JsTree
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
                    return { 'id' : node.id, 'name' : node.text};
                },
                "dataType" : "json"
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
        var viewType = data.node.data.viewType;
        var currentId = data.node.data.currentId;
        var parentId = data.node.data.parentId;
        var parentName = data.node.data.parentName;
        var currentName = data.node.text;

  //      var orgId = data.node.id;
  //      var orgName = data.node.text;
        //解决改变点击节点时，数据不刷新
        $scope.$apply(function () {
            $scope.viewType= ""
        });
        $scope.$apply(function () {
            $scope.viewType= viewType;
            $scope.currentId = currentId;
            $scope.parentId = parentId;
            $scope.parentName = parentName;
            $scope.currentName = currentName;

//            $scope.currentOrgId = orgId;
//            $scope.currentOrgName = orgName;
        });
        if(viewType == 'org') {
            organization_service.loadByOrgId(data.node.id).then(function (res) {
                if(res.parentorgid == 0) {
                    res.parentorgid = undefined
                }
                $scope.org.item = res;
            });
        }
        if(viewType == 'user') {

        }
        if(viewType == 'posi') {
            position_service.loadByPosiId(data.node.id.split(".")[1]).then(function (res) {
                if(res.dutyid == 0) {
                    res.dutyid = undefined
                }
                if(res.manaposi == 0) {
                    res.manaposi = undefined
                }
                $scope.posi.item = res;
                $scope.currentPosiId = res.positionid;
                //不添加此行，日期控件不起作用
                ComponentsDateTimePickers.init()
            })
        }
    });
});
