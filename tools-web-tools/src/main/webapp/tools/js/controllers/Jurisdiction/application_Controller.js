/**
 * Created by wangbo on 2017/6/1.
 */

angular.module('MetronicApp').controller('application_controller', function($rootScope, $scope ,$modal,$http, $timeout,filterFilter,abftree_service) {
    var biz = {};
    $scope.biz = biz;
    initController($scope, biz,'biz',biz,filterFilter)//初始化一下，才能使用里面的方法
   /* $scope.biz.dataList = [
        {
            'appname':'应用框架模型1','appcode':'ABFRAME','dict_type':'本地','dict_open':'是','dict_data':'2017-06-01','address':'上海','ip':'192.168.1.101','port':'8080','dict_des':'这里是测试描述页面1'
        },
        {
            'appname':'应用框架模型2','appcode':'ABFRAME','dict_type':'远程','dict_open':'否','dict_data':'2017-06-03','address':'','ip':'192.168.1.105','port':'8080','dict_des':'这里是测试描述页面2'
        },
        {
            'appname':'应用框架模型3','appcode':'ABFRAME','dict_type':'远程','dict_open':'是','dict_data':'2017-06-06','address':'','ip':'192.168.1.101','port':'8080','dict_des':'这里是测试描述页面3'
        },
        {
            'appname':'应用框架模型4','appcode':'ABFRAME','dict_type':'本地','dict_open':'否','dict_data':'2017-06-02','address':'','ip':'192.168.1.100','port':'8080','dict_des':'这里是测试描述页面4'
        },
        {
            'appname':'应用框架模型5','appcode':'ABFRAME','dict_type':'本地','dict_open':'是','dict_data':'2017-06-10','address':'','ip':'192.168.1.101','port':'8080','dict_des':'这里是测试描述页面5'
        }
    ]*/


    //拿到数据
/*
    var box;
    box = $scope.box = [];
    $http({
        url: 'http://g.cn',
        method: 'POST',
    }).success(function(data) {
        $scope.biz = data;
        console.log(data);
        return box.push(data);
    });
    console.log(box);//undefined
*/

    $http({
        method: 'get',
        url: 'http://localhost:8030/getData'
    }).success(function(data) {
        $scope.biz.dataList = data;//绑定数据
        // 当相应准备就绪时调用
    }).error(function(data) {
        // 当响应以错误状态返回时调用
        console.log('调用错误接口')
    });

    //树过滤
    $("#s").submit(function(e) {
        e.preventDefault();
        $("#container").jstree(true).search($("#q").val());
    });
    //树自定义右键功能
    var items = function customMenu(node) {
        console.log(node)

        // The default set of all items
        var control;
        if(node.parent == 1){
            var it = {
                "新建菜单":{
                    "id":"create",
                    "label":"新增应用",
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
                    "label":"删除应用",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if(confirm("确定要删除此菜单？删除后不可恢复。")){
                            //TODO.删除逻辑
                        }
                    }
                },
                "拷贝菜单":{
                    "label":"拷贝应用 ",
                    "action":function (node) {
                        var inst = jQuery.jstree.reference(node.reference),
                            obj = inst.get_node(node.reference);
                        var og  = $('#container').jstree(true).copy_node(obj,obj);
                        // console.log(obj)
                    }
                },

                "粘贴菜单":{
                    "label":"粘贴应用",
                    "action":function (node) {
                        var inst = jQuery.jstree.reference(node.reference),
                            obj = inst.get_node(node.reference);
                        var og  = $('#container').jstree(true).paste(node);
                        console.log(og)
                    }
                },
                "导入菜单":{
                    "label":"导入功能",
                    "action":function (node) {
                        var inst = jQuery.jstree.reference(node.reference),
                            obj = inst.get_node(node.reference);
                        var og  = $('#container').jstree(true).paste(node);
                        console.log(og)
                    }
                },
            }
            return it;
        }
        if(node.parent == 2){
            alert('出现')
            var it = {
                "新建菜单":{
                    "id":"create",
                    "label":"新增应用",
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
                    "label":"删除应用",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if(confirm("确定要删除此菜单？删除后不可恢复。")){
                            //TODO.删除逻辑
                        }
                    }
                }
            }
            return it;
        }

    };
    //组织机构树
    $("#container").jstree({
        "core" : {
            "themes" : {
                "responsive": false
            },
            // so that create works
            "check_callback" : true,
            'data': [
                { "id" : "1", "parent" : "#", "text" : "应用功能管理" },
                { "id" : "2", "parent" : "1", "text" : "应用基础框架" },
                { "id" : "3", "parent" : "2", "text" : "测试应用" }
            ]

            /* function (obj, callback) {
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
            }*/
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
        },
        'dnd': {
            'dnd_start': function () {
                console.log("start");
            },
            'is_draggable':function (node) {
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
    })


    //新增页面代码
    $scope.show_win = function(d){
        openwindow($modal, 'views/Jurisdiction/applicationAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.dictKey = angular.copy(d);
                var dictionaryAdd = {}
                $scope.dictionaryAdd = dictionaryAdd;
                if(dictionaryAdd.item == undefined){
                    dictionaryAdd.item = {};
                }
                if(!isNull(d)){
                    dictionaryAdd.item = angular.copy(d);//如果存在则复制
                    if(!isNull(dictionaryAdd.item.multi_selected)){
                        if(dictionaryAdd.item.multi_selected == '0'){
                            dictionaryAdd.item.multi_selected = false;
                        }
                        if(dictionaryAdd.item.multi_selected == '1'){
                            dictionaryAdd.item.multi_selected = true;
                        }
                    }
                }
                $scope.saveDict = function(item){//保存新增的函数
                    /*item.item.data=moment(item.item.data).format('YYYY-MM-DD');//转换时间
                    localStorage.setItem("addlist",JSON.stringify(item.item));
                    console.log(item.item)*/
                    //调用添加接口，添加数据
                    $http({
                        method: 'get',
                        url: 'http://localhost:8030/addData?id=4&appname=应用框架模型7&appcode=ABFRAME7&dict_type=远程&dict_open=是&dict_data=2017-06-28&address=无锡&ip=192.168.1.110&port=8070&dict_des=这是添加的数据'
                    }).success(function(data) {
                        toastr['success'](data.retMessage, "新增成功！");
                        $modalInstance.close();
                        $scope.biz.dataList = data;//更新数据
                    }).error(function(data) {
                        // 当响应以错误状态返回时调用
                        console.log('调用错误接口')
                    });

                   /* item.saveType = isNull(d) ? 'insert' : 'update';
                    if(!isNull(d)){
                        item.oldItem = angular.copy(d);
                    }
                    toastr['success']("新增成功");
                    $modalInstance.close();
                    console.log(JSON.parse(localStorage.getItem("addlist")));*/

                    /* dictionary_service.saveDictionary(item).then(function (data) {
                     if (data.retCode == '1') {
                     toastr['success'](data.retMessage, "新增成功！");
                     $modalInstance.close();
                     dictionary.search1();
                     } else if(data.retCode == '2') {
                     toastr['error'](data.retMessage, "新增失败！");
                     } else {
                     toastr['error']( "新增异常！");
                     }
                     });
                     }*/
                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            }
        )

    }
    //删除页面
    $scope.transsetDelAll = function () {
        var sel = biz.getSelectItems();//获取到对应的内容
        console.log(sel)
        //调用删除接口
        $http({
            method: 'get',
            url: 'http://localhost:8030/removeData?id=1'
        }).success(function(data) {
            console.log(data);
            alert('删除成功')
        }).error(function(data) {
            // 当响应以错误状态返回时调用
            console.log('调用错误接口')
        });
        if(sel.length>0){
            if(confirm("确认要批量删除选中的功能组吗？")){
                var submitForm = {};//定义一个新的对象
                submitForm.list = sel;//想这个对象的list就是我们选中的内容
                /*Transset_service.delByList(submitForm).then(function (data) {//传给后台
                    if (data.data.retCode == '1') {
                        toastr['success'](data.data.retMessage, "删除成功！");
                        transsets.search1();
                    } else if(data.data.retCode == '2') {
                        toastr['error'](data.data.retMessage, "删除失败！");
                    } else {
                        toastr['error']( "删除异常！");
                    }
                });*/
            }
        } else {
            toastr['error']("请至少选择一条记录进行删除！","SORRY！");
        }

    }

    //修改页面代码
    $scope.show_edit = function(){
        openwindow($modal, 'views/Jurisdiction/applicationAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                //修改页面代码逻辑

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })

    }


});