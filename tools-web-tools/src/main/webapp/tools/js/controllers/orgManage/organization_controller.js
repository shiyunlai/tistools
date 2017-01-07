'use strict';
MetronicApp.controller('orgList_controller', function ($filter, $rootScope, $scope, $state, $stateParams, organization_service, filterFilter, $modal, $http, $timeout, $interval) {

    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });
    var orgs = {};
    $scope.orgs = orgs;
    initController($scope, orgs, 'orgs', organization_service, filterFilter);
    orgs.search1();


    var contextualMenuSample = function () {
        $("#org_tree").jstree({
            "core": {
                "themes": {
                    "responsive": false
                },
                // so that create works
                "check_callback": true,
                'data': [
                    {
                        "text": "机构人员树",
                        "icon": "fa fa-warning icon-state-success",
                        "children": [{
                            "text": "机构",
                            "children": [{
                                "text": "子机构"
                            }, {
                                "text": "Custom Icon",
                                "icon": "fa fa-warning icon-state-danger"
                            }, {
                                "text": "Initially open",
                                "icon": "fa fa-folder icon-state-success",
                                "children": [
                                    {"text": "Another node", "icon": "fa fa-file icon-state-warning"}
                                ]
                            }, {
                                "text": "Another Custom Icon",
                                "icon": "fa fa-warning icon-state-warning"
                            }, {
                                "text": "Disabled Node",
                                "icon": "fa fa-check icon-state-success",
                                "state": {
                                    "disabled": true
                                }
                            }, {
                                "text": "Sub Nodes",
                                "icon": "fa fa-folder icon-state-danger",
                                "children": [
                                    {"text": "Item 1", "icon": "fa fa-file icon-state-warning"},
                                    {"text": "Item 2", "icon": "fa fa-file icon-state-success"},
                                    {"text": "Item 3", "icon": "fa fa-file icon-state-default"},
                                    {"text": "Item 4", "icon": "fa fa-file icon-state-danger"},
                                    {"text": "Item 5", "icon": "fa fa-file icon-state-info"}
                                ]
                            }]
                        },
                            "Another Node"
                        ],
                        "state":{
                            "opened": true
                        }

                    }]
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

/**
 * Created by Administrator on 2016/12/1.
 */
MetronicApp.controller('attrList_controller', function ($rootScope, $scope, $state, $stateParams,Attr_service, filterFilter, $modal, $http, $timeout,$interval) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });
    var attrs = {};
    $scope.attrs = attrs;
    initController($scope, attrs, 'attrs', Attr_service, filterFilter);
    attrs.search1();

    //应用类别列表

    Attr_service.bizTypeList().then(function(data){
        if (attrs.bizTypeList == undefined) {
            attrs.bizTypeList = data.list;
        }
    });

    //新增用户
    $scope.AttrAdd = function () {
        $state.go("attrEdit",{id:""});
    }
    //编辑用户
    $scope.AttrEdit = function (id) {
        $state.go("attrEdit",{id:id});
    }
    //级联查询
    $scope.type2func = function(selected) {
        attrs.funcCodeList = {};
        Attr_service.funcCodeList(selected).then(function (data) {
            attrs.funcCodeList = data.list;
        })
    }
    //删除属性定义
    $scope.AttrDel = function (id) {
        var res = confirm("确认要删除该控制属性吗？");
        if(res) {
            var promise = Attr_service.attrDel(id);
            promise.then(function (data) {
                if (data.retCode == '1') {
                    toastr['success'](data.retMessage, "删除成功！");
                    attrs.search1();
                } else if(data.retCode == '2') {
                    toastr['error'](data.retMessage, "删除失败！");
                } else {
                    toastr['error']( "删除异常！");
                }
            });
        }
    }

});

MetronicApp.controller('attrEdit_controller', function ($rootScope, $scope, $state, $stateParams,Attr_service, filterFilter, $modal, $http, $timeout,$interval) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });
    var id = $stateParams.id;
    var attr = {};
    $scope.attr = attr;

    //控制属性值来源定义
    var attrValueSrc = {};
    $scope.attrValueSrc =attrValueSrc;
    if(attrValueSrc.item == undefined) {
        attrValueSrc.item = {};
    }
    //获取业务字典列表
    Attr_service.dictList().then(function (data) {
        if(attr.dictList == undefined) {
            attr.dictList = data.dict;
        }
    });

    //获取应用类别下拉框option
    Attr_service.bizTypeList().then(function(data){
        if (attr.bizTypeList == undefined) {
            attr.bizTypeList = data.list;
        }
    });
    //级联查询功能码option
    $scope.type2func = function(selected) {
        attr.funcCodeList = {};
        attr.item.func_code = null;
        Attr_service.funcCodeList(selected).then(function (data) {
            attr.funcCodeList = data.list;
        })
    }
    //添加控制属性定义
    var attrValues = {};
    $scope.attrValues = attrValues;
    attrValues.item = [];
    var avid = 0;
    $scope.addAttrValue = function() {
        var attrItem = {};
        attrItem.value = '';
        attrItem.desc = '';
        attrItem.isDef = 'true';
        attrItem.valueSrc = {};
        attrItem.valueSrc.type = 'self';
        attrItem.multiSelected = 'false';
        attrItem.id = avid;
        attrValues.item.push(attrItem);
        avid++;
    }
    //删除控制属性定义
    $scope.delAttrValue = function (index) {
        attrValues.item.splice(index, 1);
    }

    //新增constant值
    $scope.addConstantVal = function (index) {
        var constantItem = {};
        attrValues.item[index].valueSrc.values.push(constantItem);
    }
    //删除constant
    $scope.delConstantVal = function (index,id) {
        attrValues.item[index].valueSrc.values.splice(id, 1);
    }

    if(!isNull(id)){
        attr.item = {};

        Attr_service.loadById(id).then(function (res) {

            attr.item = res.item;
            Attr_service.funcCodeList(attr.item.biz_type).then(function(data) {
                attr.funcCodeList = data.list;
            });
            attrValues.item = res.attrVal;
            for(var i = 0; i < attrValues.item.length; i++) {
                if(attrValues.item[i].multiSelected == undefined) {
                    attrValues.item[i].multiSelected = 'false';
                }
                attrValues.item[i].id = avid;
                avid++;
            }
        });


        $scope.attrSave = function (item,itemVal) {
            for (var i = 0; i < itemVal.length; i++) {
                //去除自行添加的id
                itemVal[i].id = undefined;
                //multiSelected为false时不保存到字段
                if(itemVal[i].multiSelected == 'false') {
                    itemVal[i].multiSelected = undefined;
                }

                var vsItem = {};
                if(itemVal[i].valueSrc.type == 'self') {
                    vsItem.type = 'self';
                }
                if(itemVal[i].valueSrc.type == 'constant') {
                    vsItem.type = 'constant';
                    vsItem.values = itemVal[i].valueSrc.values;
                }
                if(itemVal[i].valueSrc.type == 'dictionary') {
                    vsItem.type = 'dictionary';
                    vsItem.dictKey = itemVal[i].valueSrc.dictKey;
                }
                if(itemVal[i].valueSrc.type == 'table') {
                    vsItem.type = 'table';
                    vsItem.tableName = itemVal[i].valueSrc.tableName;
                    vsItem.valueColumn = itemVal[i].valueSrc.valueColumn;
                    vsItem.descColumn = itemVal[i].valueSrc.descColumn;
                    vsItem.filterSQL = itemVal[i].valueSrc.filterSQL;
                }
                if(itemVal[i].valueSrc.type == 'byhand') {
                    vsItem.type = 'byhand';
                }
                itemVal[i].valueSrc = vsItem;
            }

            item.attr_value = itemVal;
            //alert(JSON.stringify(item));
            //return;
            if(!isNull(item.attr_key)){
                var code = item.attr_key;
                var nameReg = !!code.match(/^[A-Za-z_]{1,64}$/);
                if(nameReg == false){
                    toastr['error']("控制属性只能是字母或下划线且不能超过64位字符！", "SORRY！");
                    return;
                }
            }

            var promise = Attr_service.save(item);
            promise.then(function (data) {
                if (data.retCode == '1') {
                    toastr['success'](data.retMessage, "更改控制属性定义成功！");
                    $state.go("attr_list")
                } else if(data.retCode == '2') {
                    toastr['error'](data.retMessage, "更改控制属性定义失败！");
                } else {
                    toastr['error']( "更改控制属性定义异常！");
                }
            });
        }
    } else {
        $scope.addAttrValue();

        if(attr.item == undefined){
            attr.item = {};
        }

        if(isNull(attr.item.ctl_desc)) {
            attr.item.ctl_desc = '';
        }

        if(isNull(attr.item.multi_choose)){
            attr.item.multi_choose = '1';
        }

        $scope.attrAdd = function (item,itemVal) {
            for (var i = 0; i < itemVal.length; i++) {
                //去除自行添加的id
                itemVal[i].id = undefined;
                //multiSelected为false时不保存到字段
                if(itemVal[i].multiSelected == 'false') {
                    itemVal[i].multiSelected = undefined;
                }

                var vsItem = {};
                if(itemVal[i].valueSrc.type == 'self') {
                    vsItem.type = 'self';
                }
                if(itemVal[i].valueSrc.type == 'constant') {
                    vsItem.type = 'constant';
                    vsItem.values = itemVal[i].valueSrc.values;
                }
                if(itemVal[i].valueSrc.type == 'dictionary') {
                    vsItem.type = 'dictionary';
                    vsItem.dictKey = itemVal[i].valueSrc.dictKey;
                }
                if(itemVal[i].valueSrc.type == 'table') {
                    vsItem.type = 'table';
                    vsItem.tableName = itemVal[i].valueSrc.tableName;
                    vsItem.valueColumn = itemVal[i].valueSrc.valueColumn;
                    vsItem.descColumn = itemVal[i].valueSrc.descColumn;
                    if(!isNull(itemVal[i].valueSrc.filterSQL)) {
                        vsItem.filterSQL = itemVal[i].valueSrc.filterSQL;
                    }
                }
                if(itemVal[i].valueSrc.type == 'byhand') {
                    vsItem.type = 'byhand';
                }
                itemVal[i].valueSrc = vsItem;
            }

            item.attr_value = itemVal;

            if(!isNull(attr.item.attr_key)){
                var code = attr.item.attr_key;
                var nameReg = !!code.match(/^[a-z_]{1,64}$/);
                if(nameReg == false){
                    toastr['error']("控制属性只能是字母或下划线且不能超过64位字符！", "SORRY！")
                    return;
                }
            }
            var promise = Attr_service.save(item);
            promise.then(function (data) {
                if (data.retCode == '1') {
                    toastr['success'](data.retMessage, "新增控制属性定义成功！");
                    $state.go("attr_list")
                } else if(data.retCode == '2') {
                    toastr['error'](data.retMessage, "新增控制属性定义失败！");
                } else {
                    toastr['error']( "新增控制属性定义异常！");
                }
            });
        }
    }
    //来源的单选按钮逻辑：开始
    attrValueSrc.self = function (index) {
        attrValues.item[index].valueSrc.type='self';

    }
    attrValueSrc.constant = function (index) {
        attrValues.item[index].valueSrc.type='constant';
        if(attrValues.item[index].valueSrc.values == undefined) {
            attrValues.item[index].valueSrc.values = [];
            var valueItem = {};
            valueItem.value = '';
            valueItem.desc = '';
            attrValues.item[index].valueSrc.values.push(valueItem);
        }
    }
    attrValueSrc.dictionary = function (index) {
        attrValues.item[index].valueSrc.type='dictionary';
    }
    attrValueSrc.table = function (index) {
        attrValues.item[index].valueSrc.type='table';
    }
    attrValueSrc.byhand = function (index) {
        attrValues.item[index].valueSrc.type='byhand';
    }

});
