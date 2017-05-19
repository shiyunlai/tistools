/**
 * Created by gaojie on 2017/5/9.
 */
angular.module('MetronicApp').controller('abftree_controller', function($rootScope, $scope,abftree_service, $http, $timeout,i18nService,filterFilter) {
    $scope.$on('$viewContentLoaded', function() {
        // initialize core components
        App.initAjax();
    });
    var abftree = {};
    $scope.abftree = abftree;

    initController($scope,abftree,"abftree",abftree,filterFilter);

    var item = {};
    $scope.item = item;
    abftree.item = item;
    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageContentWhite = true;
    $rootScope.settings.layout.pageBodySolid = false;
    $rootScope.settings.layout.pageSidebarClosed = false;
    //按需加载节点数据
    // abftree_service.test({'id':"123"}).then(function (data) {
    //     //处理orgName为text方便使用
    //     console.log(data.length)
    //     for(var i = 0 ;i < data.length ; i++){
    //         data[i].text = data[i].orgName;
    //         data[i].children = true;
    //         console.log(data[i].text)
    //     }
    //     $("#mytree").jstree({
    //         "core" : {
    //             "themes" : {
    //                 "responsive": false
    //             },
    //             "check_callback" : true,
    //             "check_callback" :function (operation, node, node_parent, node_position, more) {
    //                 // operation can be 'create_node', 'rename_node', 'delete_node', 'move_node', 'copy_node' or 'edit'
    //                 // in case of 'rename_node' node_position is filled with the new node name
    //                 console.log(node);
    //                 return operation === 'rename_node' ? true : false;
    //             },
    //             'data':data,
    //         },
    //         "massload" : {
    //             "url" : "localhost:8089/tis/om/org/chlid",
    //             "type" : "json",
    //             "data" : function (nodes) {
    //                 console.log(nodes)
    //                 return { "Pguid" : nodes.id };
    //             }
    //         },
    //         "plugins" : [ "themes", "json_data","contextmenu", "dnd", "state", "types","massload","search" ]
    //     });
    //     $('#mytree').on('changed.jstree',function(e,data){
    //         // // //当前选中节点的id
    //         // // console.log(data.instance.get_node(data.selected[0]));
    //         // var obj = data.instance.get_node(data.selected[0]).original;
    //         // console.log(obj)
    //         // var pobj = angular.toJson(obj);
    //         // abftree_service.loadchild(pobj).then(function (data) {
    //         //     console.log(data)
    //         // });
    //     });
    //     $("#s").submit(function(e) {
    //         e.preventDefault();
    //         $("#mytree").jstree(true).search($("#q").val());
    //     });
    //
    // })
    $("#s").submit(function(e) {
        e.preventDefault();
        $("#container").jstree(true).search($("#q").val());
    });


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
                abftree_service.test(subFrom).then(function (data) {
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
        "contextmenu":{
            "items":{
                "create":null,
                "rename":null,
                "remove":null,
                "ccp":null,
                "新建菜单":{
                    "label":"新建机构",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                            console.log(obj)
                    }
                },
                "删除菜单":{
                    "label":"删除机构",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if(confirm("确定要删除此菜单？删除后不可恢复。")){
                            jQuery.get("/accountmanage/deleteMenu?id="+obj.id,function(dat){
                                if(dat == 1){
                                    alert("删除菜单成功！");
                                    jQuery("#"+treeid).jstree("refresh");
                                }else{
                                    alert("删除菜单失败！");
                                }
                            });
                        }
                    }
                },
                "编辑菜单":{
                    "label":"编辑机构",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        dialog.show({"title":"编辑“"+obj.text+"”菜单",url:"/accountmanage/editMenu?id="+obj.id,height:280,width:400});
                    }
                }
            }
        },

        "plugins" : [ "dnd", "state", "types","search","contextmenu" ]
    });
    //tree点击事件
    $('#container').on("changed.jstree", function (e, data) {
        if(typeof data.node !== 'undefined'){//拿到结点详情
            $scope.abftree.item = data.node.original;
            $scope.$apply();
        }
    });
    abftree.test = function () {
        console.log($scope)
    }

    //修改
    abftree.edit = function () {
        abftree.switchToEditView();
    }
    
    //覆盖searchN方法
    abftree.searchN = function () {
        
    }







    i18nService.setCurrentLang("zh-cn");
    //ui-grid
    $scope.myData = [{name: "Moroni", age: 50},
        {name: "Tiancum", age: 43},
        {name: "Jacob", age: 27},
        {name: "Nephi", age: 29},
        {name: "Enos", age: 34}];
    $scope.gridOptions = { data: 'myData' };

});