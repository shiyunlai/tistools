/**
 * Created by gaojie on 2017/5/9.
 */
angular.module('MetronicApp').controller('abftree_controller', function($rootScope, $scope,abftree_service, $http, $timeout,i18nService,filterFilter,uiGridConstants,$uibModal) {
    $scope.$on('$viewContentLoaded', function() {
        // initialize core components
        App.initAjax();
    });
    var abftree = {};
    $scope.abftree = abftree;
    //控制是否是修改状态
    var editflag = false;
    $scope.editflag = editflag;
    //控制页签跳转
    var flag = {};
    $scope.flag = flag;
    //下级机构页签控制
    var xjjg = false;
    flag.xjjg = xjjg;
    //员工信息页签控制
    var ygxx = false;
    flag.ygxx = ygxx;
    //机构信息展示页
    var xqxx = true;
    flag.xqxx = xqxx;
    //岗位页签跳转控制
    var gwflag = {};
    $scope.gwflag = gwflag;
    //岗位详情
    var gwxx = false;
    gwflag.gwxx = gwxx;
    //岗位员工列表
    var gwyg = false;
    gwflag.gwyg = gwyg;
    //岗位应用列表
    var gwyy = false;
    gwflag.gwyy = gwyy;
    //下级岗位列表
    var xjgw = false;
    gwflag.xjgw = xjgw;
    //岗位权限列表;
    var gwqx = false;
    gwflag.gwqx = gwqx;


    //机构,岗位页签切换
    var tabflag = true;//true为机构详情
    $scope.tabflag = tabflag;
    //生成公共方法
    initController($scope,abftree,"abftree",abftree,filterFilter);

    var item = {};
    $scope.item = item;
    abftree.item = item;
    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageContentWhite = true;
    $rootScope.settings.layout.pageBodySolid = false;
    $rootScope.settings.layout.pageSidebarClosed = false;
    //树过滤
    $("#s").submit(function(e) {
        e.preventDefault();
        $("#container").jstree(true).search($("#q").val());
    });

    //树自定义右键功能
    var items = function customMenu(node) {
        // The default set of all items
        var control;

        var it = {

            "新建菜单":{
                        "id":"create",
                        "label":"新建机构",
                        "action":function(data){
                            var inst = jQuery.jstree.reference(data.reference),
                                obj = inst.get_node(data.reference);
                            console.log(obj)
                            openwindow($uibModal, 'views/org/addorg_window.html', 'lg',
                                function ($scope, $modalInstance) {
                                    //创建机构实例
                                    var subFrom = {};
                                    $scope.subFrom = subFrom;
                                    //处理新增机构父机构
                                    subFrom.guidParents = obj.original.guid;
                                    //增加方法
                                    $scope.add = function (subFrom) {
                                        //TODO.新增逻辑
                                    }

                                    $scope.cancel = function () {
                                        $modalInstance.dismiss('cancel');
                                    };
                                }
                            )
                        }
                    },

            "删除菜单":{
                        "label":"删除机构",
                        "action":function(data){
                            var inst = jQuery.jstree.reference(data.reference),
                                obj = inst.get_node(data.reference);
                            if(confirm("确定要删除此菜单？删除后不可恢复。")){
                                //TODO.删除逻辑
                            }
                        }
                    },
            "拷贝菜单":{
                        "label":"拷贝机构",
                        "action":function (node) {
                            var inst = jQuery.jstree.reference(node.reference),
                                obj = inst.get_node(node.reference);
                            var og  = $('#container').jstree(true).copy_node(obj,obj);
                            // console.log(obj)
                        }
            },

            "粘贴菜单":{
                "label":"粘贴机构",
                "action":function (node) {
                    var inst = jQuery.jstree.reference(node.reference),
                        obj = inst.get_node(node.reference);
                    var og  = $('#container').jstree(true).paste(node);
                    console.log(og)
                }
            },
        }
        return it;
    };

    //组织机构树
    $("#container").jstree({
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
                abftree_service.loadmaintree(subFrom).then(function (data) {
                    for(var i = 0 ;i < data.length ; i++){
                                data[i].text = data[i].orgName;
                                data[i].children = true;
                                data[i].id = data[i].orgCode;
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
        "contextmenu":{'items':items
        //     "items":{
        //     "create":null,
        //     "rename":null,
        //     "remove":null,
        //     "ccp":null,
        //     "新建菜单":{
        //         "id":"create",
        //         "label":"新建机构",
        //         "action":function(data){
        //             var inst = jQuery.jstree.reference(data.reference),
        //                 obj = inst.get_node(data.reference);
        //             console.log(obj)
        //             openwindow($uibModal, 'views/org/addorg_window.html', 'lg',
        //                 function ($scope, $modalInstance) {
        //                     //创建机构实例
        //                     var subFrom = {};
        //                     $scope.subFrom = subFrom;
        //                     //处理新增机构父机构
        //                     subFrom.guidParents = obj.original.guid;
        //                     //增加方法
        //                     $scope.add = function (subFrom) {
        //                         //TODO.新增逻辑
        //                     }
        //
        //                     $scope.cancel = function () {
        //                         $modalInstance.dismiss('cancel');
        //                     };
        //                 }
        //             )
        //         }
        //     },
        //     "删除菜单":{
        //         "label":"删除机构",
        //         "action":function(data){
        //             var inst = jQuery.jstree.reference(data.reference),
        //                 obj = inst.get_node(data.reference);
        //             if(confirm("确定要删除此菜单？删除后不可恢复。")){
        //                 //TODO.删除逻辑
        //             }
        //         }
        //     },
        //     "拷贝菜单":{
        //         "label":"拷贝机构",
        //         "action":function(data){
        //             var inst = jQuery.jstree.reference(data.reference),
        //                 obj = inst.get_node(data.reference);
        //             //TODO.拷贝逻辑
        //         }
        //     }
        // }
        },
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
        if(confirm("确认要移动此机构吗?")){
            //TODO.
        }else{
            // data.inst.refresh(data.inst._get_parent(data.rslt.oc));
            return false;
        }
        console.log(e);
        console.log(data);
    }).bind("dnd_stop.vakata",function (e,data) {
        console.log(data);
    });




    //tree点击事件
    $('#container').on("changed.jstree", function (e, data) {
        if(typeof data.node !== 'undefined'){//拿到结点详情
            // console.log(data.node.original.id.indexOf("@"));
            $scope.abftree.item = data.node.original;
            if(data.node.original.id.indexOf("@") == 0){
                for(var i in $scope.flag){
                    flag[i] = false;
                }
                $scope.gwflag.gwxx = true;
                $scope.tabflag = false;
            }else {
                for(var i in $scope.gwflag){
                    gwflag[i] = false;
                }
                $scope.flag.xqxx = true;
                $scope.tabflag = true;
            }

            $scope.$apply();
        }
    });




//     $( "#module_tree" )
//         .on('move_node.jstree', function(e,data){
//             console.info(data);
//             jQuery.post("modulemng/dndmodule",
//                 {
//                     id : data.node.id,
//                     parent : data.parent,
//                     position:data.position
//                 },
//                 function(data,status){
//                     alert("Data: " + data + "\nStatus: " + status);
//                 }, 'json');
//
//         })
//         .jstree({
//             //plugins-各种jstree的插件引入，展示树的多样性
//             'plugins' : [ "dnd", "types", "wholerow" ],
//             'core' : {
//                 "check_callback" : true,//在对树进行改变时，check_callback是必须设置为true；
//                 'data' :{
//                     'url' : 'modulemng/list',
//                     dataType:'json'
//                 }
//             },
//             //types-对树的节点进行设置，包括子节点type、个数
//             'types' : {
//                 "#" : {
//                     "max_children" : 1
//                 }
//             }
//         });
// });






    abftree.test = function () {
        console.log(1)
    }
    
    //删除
    abftree.delete = function () {
        if(confirm("确认要删除此机构吗?")){
            //TODO.删除逻辑
        }
    }

    //启用
    abftree.enable = function () {
        //TODO.启用逻辑
    }

    //修改
    abftree.edit = function () {
        $scope.editflag = !$scope.editflag;
    }
    
    //覆盖searchN方法
    abftree.searchN = function () {
        
    }
    //保存
    abftree.save = function () {
        $scope.editflag = !$scope.editflag;
        //TODO
        //保存逻辑
    }
    //点击页签,加载指定数据
    abftree.loaddata = function (type) {
        //type加载的数据,0--下级机构,1--员工信息,2--岗位信息,999-机构详情
        if(type == 0){
            console.log($scope.abftree.item.guid)
            $scope.flag.xqxx = false;
            $scope.flag.xjjg = true;
            $scope.flag.ygxx = false;
            $scope.flag.qxxx = false;
            //todo
            //通过GUID查询下级机构信息
        }else if(type == 1){
            $scope.flag.xqxx = false;
            $scope.flag.xjjg = false;
            $scope.flag.ygxx = true;
            $scope.flag.qxxx = false;
            //todo
            //通过GUID查询员工列表

        }else if(type == 2){
            $scope.flag.xqxx = false;
            $scope.flag.xjjg = false;
            $scope.flag.ygxx = false;
            $scope.flag.qxxx = false;
            //todo
        }else if(type == 3){
            $scope.flag.xqxx = false;
            $scope.flag.xjjg = false;
            $scope.flag.ygxx = false;
            $scope.flag.qxxx = true;
            //todo
        }else if(type == 999){
            $scope.flag.xqxx = true;
            $scope.flag.xjjg = false;
            $scope.flag.ygxx = false;
            $scope.flag.qxxx = false;
        }
    }
    //点击页签,加载指定岗位信息
    abftree.loadgwdata = function (type) {
        //type 0--岗位详情,1--员工列表,2--岗位应用列表,3--下级岗位列表,4--岗位权限列表
        //点击加载数据,此处需要4个方法.
        //将机构页面隐藏
        for(var i in $scope.flag){
            console.log($scope.flag[i]);
            $scope.flag[i] = false;
        }
        if(type == 0){
            for(var i in $scope.gwflag){
                $scope.gwflag[i] = false;
            }
            $scope.gwflag.gwxx = true;
        }else if (type == 1){
            for(var i in $scope.gwflag){
                $scope.gwflag[i] = false;
            }
            $scope.gwflag.gwyg = true;
        }else if (type == 2){
            for(var i in $scope.gwflag){
                $scope.gwflag[i] = false;
            }
            $scope.gwflag.gwyy = true
        }else if (type == 3){
            for(var i in $scope.gwflag){
                $scope.gwflag[i] = false;
            }
            $scope.gwflag.xjgw = true
        }else if (type == 4){
            for(var i in $scope.gwflag){
                $scope.gwflag[i] = false;
            }
            $scope.gwflag.gwqx = true;
        }
    }




    i18nService.setCurrentLang("zh-cn");
    //ui-grid
    $scope.myData = [{name: "Moroni", age: 50},
        {name: "Tiancum", age: 43},
        {name: "Jacob", age: 27},
        {name: "Nephi", age: 29},
        {name: "Enos", age: 34}];
    //ui-grid 具体配置
    //下级机构列表
    $scope.gridOptions1 = {
        data: 'myData',
        //-------- 分页属性 ----------------
        enablePagination: true, //是否分页，默认为true
        enablePaginationControls: true, //使用默认的底部分页
        paginationPageSizes: [10, 15, 20], //每页显示个数可选项
        paginationCurrentPage:1, //当前页码
        paginationPageSize: 10, //每页显示个数
        //paginationTemplate:"<div></div>", //自定义底部分页代码
        totalItems : 0, // 总数量
        useExternalPagination: true,//是否使用分页按钮
        //是否多选
        multiSelect:false,
        onRegisterApi: function(gridApi) {
            $scope.gridApi = gridApi;
            //分页按钮事件
            gridApi.pagination.on.paginationChanged($scope,function(newPage, pageSize) {
                if(getPage) {
                    getPage(newPage, pageSize);
                }
            });
            //行选中事件
            $scope.gridApi.selection.on.rowSelectionChanged($scope,function(row,event){
                if(row){
                    $scope.selectRow = row.entity;
                    console.log($scope.selectRow)
                }
            });
        }
    };
    //人员信息列表
    $scope.gridOptions2 = {
        data: 'myData',
        //-------- 分页属性 ----------------
        enablePagination: true, //是否分页，默认为true
        enablePaginationControls: true, //使用默认的底部分页
        paginationPageSizes: [10, 15, 20], //每页显示个数可选项
        paginationCurrentPage:1, //当前页码
        paginationPageSize: 10, //每页显示个数
        //paginationTemplate:"<div></div>", //自定义底部分页代码
        totalItems : 0, // 总数量
        useExternalPagination: true,//是否使用分页按钮
        //是否多选
        multiSelect:false,
        onRegisterApi: function(gridApi) {
            $scope.gridApi = gridApi;
            //分页按钮事件
            gridApi.pagination.on.paginationChanged($scope,function(newPage, pageSize) {
                if(getPage) {
                    getPage(newPage, pageSize);
                }
            });
            //行选中事件
            $scope.gridApi.selection.on.rowSelectionChanged($scope,function(row,event){
                if(row){
                    $scope.selectRow = row.entity;
                    console.log($scope.selectRow)
                }
            });
        }
    };
    //ui-grid getPage方法
    var getPage = function(curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions.totalItems = $scope.myData.length;
        $scope.gridOptions.data = $scope.myData.slice(firstRow, firstRow + pageSize);
        //或者像下面这种写法
        //$scope.myData = mydefalutData.slice(firstRow, firstRow + pageSize);
    };


});