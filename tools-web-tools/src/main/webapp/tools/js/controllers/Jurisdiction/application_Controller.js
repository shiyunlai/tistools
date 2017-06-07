/**
 * Created by wangbo on 2017/6/1.
 */

angular.module('MetronicApp').controller('application_controller', function($rootScope, $scope ,$modal,$http,i18nService, $timeout,filterFilter,$uibModal) {
    var biz = {};
    $scope.biz = biz;
    //定义权限
     $scope.biz.applica = false;

    //岗位页签跳转控制
    var yyflag = {};
    $scope.yyflag = yyflag;
    //应用信息
    var yyxx = false;
    yyflag.yyxx = yyxx;
    //功能组列表
    var gnzlb = false;
    yyflag.gnzlb = gnzlb;
    initController($scope, biz,'biz',biz,filterFilter)//初始化一下，才能使用里面的方法

    //初始化tab展现
    $scope.yyflag.yyxx = true;

    //控制页切换代码
    yyflag.loadgwdata = function (type) {
        if(type == 0){
            $scope.yyflag.yyxx = true;
            $scope.yyflag.gnzlb = false;
        }else if (type == 1){
            $scope.yyflag.gnzlb = true;
            $scope.yyflag.yyxx = false;
        }
    }


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
        if(node.parent == '#'){
            var it = {
                "新增应用":{
                    "id":"createa",
                    "label":"新增应用",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        openwindow($uibModal, 'views/Jurisdiction/applicationAdd.html', 'lg',
                            function ($scope, $modalInstance) {
                                console.log($modalInstance)
                                //创建机构实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //处理新增机构父机构
                                subFrom.guidParents = obj.original.guid;
                                //增加方法
                                $scope.add = function (subFrom) {
                                    //TODO.新增逻辑
                                }

                                $scope.saveDict = function(item){//保存新增的函数
                                    console.log(item);//用户输入的信息
                                    /*item.item.data=moment(item.item.data).format('YYYY-MM-DD');//转换时间
                                     localStorage.setItem("addlist",JSON.stringify(item.item));
                                    //调用添加接口，添加数据*/
                                    $http({
                                        method: 'get',
                                        url: 'http://localhost:8030/addData?id=4&appname=应用框架模型7&appcode=ABFRAME7&dict_type=远程&dict_open=是&dict_data=2017-06-28&address=无锡&ip=192.168.1.110&port=8070&dict_des=这是添加的数据'
                                    }).success(function(data) {
                                        toastr['success'](data.retMessage, "新增成功！");
                                        $modalInstance.close();
                                    }).error(function(data) {
                                        // 当响应以错误状态返回时调用
                                        console.log('调用错误接口')
                                    });

                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            }
                        )
                    }
                },

                "刷新":{
                    "label":"刷新",
                    "action":function(data){

                    }
                }
            }
            return it;
        }
        if(node.parent == 1){
            var it = {
                "新增功能组":{
                    "id":"createc",
                    "label":"新增功能组",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        openwindow($uibModal, 'views/Jurisdiction/appgroupAdd.html', 'lg',
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

                "删除应用":{
                    "label":"删除应用",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if(confirm("您确认要删除选中的功能组,删除功能组将删除该功能组下的所有子功能组和资源")){
                            //TODO.删除逻辑
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
                        }
                    }
                },
                '刷新':{
                    "label":"刷新",
                    "action":function(data){

                    }
                }
            }
            return it;
        }
        if(node.parent == 2){
            var it = {
                "新建子功能组":{
                    "id":"createb",
                    "label":"新建子功能组",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        openwindow($uibModal, 'views/Jurisdiction/childfunctionAdd.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建机构实例
                                var childFrom = {};
                                $scope.childFrom = childFrom;
                                //处理新增机构父机构
                                childFrom.guidParents = obj.original.guid;
                                //增加方法
                                $scope.addchild = function (childFrom) {
                                    console.log(childFrom)
                                    toastr['success']("保存成功！");
                                    $modalInstance.close();
                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            }
                        )
                    }
                },

                "删除功能组":{
                    "label":"删除功能组",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if(confirm("您确认要删除选中的功能组,删除功能组将删除该功能组下的所有子功能组和资源。")){
                            //TODO.删除逻辑
                        }
                    }
                },
                "新增功能":{
                    "label":"新增功能 ",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        openwindow($uibModal, 'views/Jurisdiction/afAdd.html', 'lg',
                            function ($scope, $modalInstance) {
                                console.log($modalInstance)
                                //创建机构实例
                                var appFrom = {};
                                $scope.appFrom = appFrom;
                                //处理新增功能父功能
                                appFrom.guidParents = obj.original.guid;
                                //增加方法
                                $scope.add = function (appFrom) {
                                    //TODO.新增逻辑
                                    toastr['success']("保存成功！");
                                    $modalInstance.close();

                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            }
                        )
                    }
                },
                "刷新":{
                    "label":"刷新",
                    "action":function (node) {
                      //刷新页面
                    }
                },
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
            'data':
                [{
                        "id": "1",
                        "text": "应用功能管理",
                        "children":
                            [
                                {
                                    "id": "2",
                                    "text": "应用基础框架",
                                    "children":
                                        [
                                            {
                                                "id": "4",
                                                "text": "授权认证",
                                                "children":[{
                                                    'id':'75',
                                                    "text": "登陆策略管理"
                                                },{
                                                    'id':'76',
                                                    "text": "操作员管理"
                                                },{
                                                    'id':'77',
                                                    "text": "Prota资源管理"
                                                },{
                                                    'id':'78',
                                                    "text": "密码设置"
                                                }
                                                ]
                                            },{
                                            "id": "5",
                                            "text": "权限管理",
                                            "children":[{
                                               'id':'80',
                                                "text": "应用功能管理",
                                            },{
                                                'id':'81',
                                                "text": "菜单显示",
                                            },{
                                                'id':'82',
                                                "text": "菜单管理",
                                            },{
                                                    'id':'83',
                                                    "text": "约束管理",
                                                },{
                                                'id':'84',
                                                "text": "角色管理",
                                            },

                                            ]
                                        },{
                                            "id": "6",
                                            "text": "组织管理"
                                        }]
                                },
                                {
                                    "id": "3",
                                    "text": "测试应用",
                                }
                            ]
                    }
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
    }).bind('click.jstree', function(event) {
        var eventNodeName = event;
        console.log(eventNodeName)
    })
1
        $('#container').on("changed.jstree", function (e, data) {
        if(typeof data.node !== 'undefined'){//拿到结点详情
            console.log(data.node.parent)
            if(data.node.parent == '#'){
                $scope.biz.applica = true;
                $scope.biz.apptab = false;
                $scope.biz.appchild = false;
            }else if(data.node.parent == '1'){
                $scope.biz.apptab = true;
                $scope.biz.applica = false;
                $scope.biz.appchild = false;
            }else if(data.node.parent == '2'){
                $scope.biz.appchild = true;
                $scope.biz.applica = false;
                $scope.biz.apptab = false;
            } else{
                $scope.biz.apptab = false;
            }
            $scope.$apply();
        }
    });

    //ui-grid表格模块
    i18nService.setCurrentLang("zh-cn");
    $scope.myData = [
        {id: "0", 'appname':'应用框架模型1', 'appcode':'ABFRAME', 'dict_type':'本地', 'dict_open':'是', 'dict_data':'2017-06-01', 'address':'上海', 'ip':'192.168.1.101', 'port':'8080', 'dict_des':'这里是测试描述页面1'
        },
        {id: "1", 'appname':'应用框架模型2', 'appcode':'ABFRAME1', 'dict_type':'远程', 'dict_open':'否', 'dict_data':'2017-06-03', 'address':'苏州', 'ip':'192.168.1.102', 'port':'8081', 'dict_des':'这里是测试描述页面2'
        },
        {id: "2", 'appname':'应用框架模型3', 'appcode':'ABFRAME3', 'dict_type':'本地', 'dict_open':'是', 'dict_data':'2017-06-03', 'address':'北京', 'ip':'192.168.1.103', 'port':'8082', 'dict_des':'这里是测试描述页面3'
        },
        {id: "3", 'appname':'应用框架模型4', 'appcode':'ABFRAME4', 'dict_type':'本地', 'dict_open':'是', 'dict_data':'2017-06-04', 'address':'深圳', 'ip':'192.168.1.103', 'port':'8083', 'dict_des':'这里是测试描述页面4'
        },
        {id: "4", 'appname':'应用框架模型5', 'appcode':'ABFRAME5', 'dict_type':'远程', 'dict_open':'否', 'dict_data':'2017-06-04', 'address':'南京', 'ip':'192.168.1.104', 'port':'8085', 'dict_des':'这里是测试描述页面5'
        },
        {id: "5", 'appname':'应用框架模型6', 'appcode':'ABFRAME6', 'dict_type':'本地', 'dict_open':'是', 'dict_data':'2017-06-05', 'address':'佛山', 'ip':'192.168.1.105', 'port':'8086', 'dict_des':'这里是测试描述页面6'}
    ];
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
                if(row.isSelected){
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
    };



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

                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            }
        )

    }
    //删除代码
    $scope.transsetDelAll = function () {
        var sel = biz.getSelectItems();//获取到对应的内容
        console.log(sel)
        //调用删除接口
        $http({
            method: 'get',
            url: 'http://localhost:8030/removeData?id=1'
        }).success(function(data) {
            console.log(data);
        }).error(function(data) {
            // 当响应以错误状态返回时调用
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

    //保存页签
    $scope.saveDict = function(item){//保存新增的函数
        console.log(item);
        $http({
            method: 'get',
            url: 'http://localhost:8030/addData?id=4&appname=应用框架模型7&appcode=ABFRAME7&dict_type=远程&dict_open=是&dict_data=2017-06-28&address=无锡&ip=192.168.1.110&port=8070&dict_des=这是添加的数据'
        }).success(function(data) {
            toastr['success'](data.retMessage, "保存成功！");
            $scope.biz.dataList = data;//更新数据
        }).error(function(data) {
            // 当响应以错误状态返回时调用
            console.log('调用错误接口')
        });
    }

    //功能组列表代码模块
    var appcom = {};
    $scope.appcom = appcom;
    initController($scope, appcom,'appcom',appcom,filterFilter);
    $scope.appcom.dataList = [{'renzhen':'授权认证','nodestrs':'1','appxh':'.1.','isnodes':'否'},
        {'renzhen':'权限管理','nodestrs':'1','appxh':'.2.','isnodes':'否'},
        {'renzhen':'组织管理','nodestrs':'1','appxh':'.3.','isnodes':'否'},
        {'renzhen':'其他管理','nodestrs':'1','appxh':'.4.','isnodes':'否'},
        {'renzhen':'工作流','nodestrs':'1','appxh':'.5.','isnodes':'否'}
    ]
    //功能组新增
    $scope.addApp = function(){
            openwindow($modal, 'views/Jurisdiction/appgroupAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.add = function(item){
                        //新增代码
                        toastr['success']("保存成功！");
                        $modalInstance.close();
                    }

                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };

                }
            )
    }
    //功能组修改
    $scope.exidApp = function(){

    }


    /*子功能组页面逻辑*/
    //岗位页签跳转控制
    var childflag = {};
    $scope.childflag = childflag;
    //功能组信息
    var gnzxx = false;
    childflag.gnzxx = gnzxx;
    //功能组列表
    var zgnzlb = false;
    childflag.zgnzlb = zgnzlb;
    //功能列表
    var gnlb = false;
    childflag.gnlb = gnlb;

    //初始化tab展现
    $scope.childflag.gnzxx = true;

    //控制页切换代码
    childflag.loadgwdata = function (type) {
        if(type == 0){
            $scope.childflag.gnzxx = true;
            $scope.childflag.zgnzlb = false;
            $scope.childflag.gnlb = false;
        }else if (type == 1){
            $scope.childflag.gnzxx = false;
            $scope.childflag.zgnzlb = true;
            $scope.childflag.gnlb = false;
        }else if(type == 2){
            $scope.childflag.gnzxx = false;
            $scope.childflag.zgnzlb = false;
            $scope.childflag.gnlb = true;
        }
    }
    /* 功能组信息页签逻辑*/
    $scope.addchild =function(item){
        console.log(item)
        toastr['success']("保存成功！");
    }

    /*子功能组页签内容*/
    $scope.myDatas = [
        {id: "0", '功能组名称':'功能组1', '所属功能组':'准备删除', '节点层次':'2', '是否叶子节点':''},
        {id: "1", '功能组名称':'功能组2', '所属功能组':'准备删除', '节点层次':'2', '是否叶子节点':''},
    ];
    //ui-grid 具体配置
    //下级机构列表
    $scope.gridOptions2 = {
        data: 'myDatas',
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
                if(row.isSelected){
                    $scope.selectedRow = row.entity;
                    console.log($scope.selectedRow)
                }
            });
        }
    };
    //子功能组列表新增功能
    $scope.addchildApp = function(){
        openwindow($modal, 'views/Jurisdiction/childfunctionAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.addchild = function(item){
                    //新增代码
                    toastr['success']("保存成功！");
                    $modalInstance.close();
                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }

    //子功能页签编辑页面
    $scope.exidchildApp = function(){
        if($scope.selectedRow){
            openwindow($modal, 'views/Jurisdiction/afAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.add = function(item){
                        //新增代码
                        console.log(item)
                        toastr['success']("保存成功！");
                        $modalInstance.close();
                    }

                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }else{
            toastr['error']("请至少选中一条！");
        }
    }

    //子功能页签删除方法
    $scope.appchildDelAll = function(){
        if($scope.selectedRow){
            confirm("您确认要删除选中的功能组吗?")
        }else{
            toastr['error']("请至少选中一条删除项！");
        }
    }


    $scope.appfuncAdd = [
        {id: "0", '功能名称':'测试功能', '功能类型':'页面流', '是否定义为菜单':'否', '所属功能组':'测试功能组'}
    ];
    $scope.gridOptions3 = {
        data: 'appfuncAdd',
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
                if(row.isSelected){
                    $scope.selectRow = row.entity;
                    console.log($scope.selectRow)
                }
            });
        }
    };

    //功能列表新增方法
    $scope.addappList = function(){
        openwindow($modal, 'views/Jurisdiction/afAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){
                    //新增代码
                    console.log(item)
                    toastr['success']("保存成功！");
                    $modalInstance.close();
                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }

    //编辑方法
    $scope.exitappList = function(){
        if($scope.selectRow){
            openwindow($modal, 'views/Jurisdiction/afAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.add = function(item){
                        //新增代码
                        console.log(item)
                        toastr['success']("保存成功！");
                        $modalInstance.close();
                    }

                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }else{
            toastr['error']("请至少选中一条！");
        }
    }
    //删除方法
    $scope.exitapplistDelAll=function(){
        if( $scope.selectRow){
            confirm("确认要删除此功能信息吗?")
        }else{
            toastr['error']("请至少选中一条！");
        }
    }
});