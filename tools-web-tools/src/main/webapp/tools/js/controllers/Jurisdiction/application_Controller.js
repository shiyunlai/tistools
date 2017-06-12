/**
 * Created by wangbo on 2017/6/1.
 */

angular.module('MetronicApp').controller('application_controller', function($rootScope, $scope ,$modal,$http,i18nService, $timeout,filterFilter,$uibModal,uiGridConstants) {
    var biz = {};
    $scope.biz = biz;
    //定义权限
     $scope.biz.applica = false;
    /*-------------------------------------------------------------------------------分割符--------------------------------------------------------------------------------*/
    //0、树结构逻辑代码
    /* 树结构逻辑代码*/
    //树过滤,搜索功能
    $("#s").submit(function(e) {
        e.preventDefault();
        $("#container").jstree(true).search($("#q").val());
    });
    //树自定义右键功能(根据类型判断)
    var items = function customMenu(node) {
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
                        $("#container").jstree().refresh();
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
                            obj = inst.get_node(data.reference);//从数据库中获取所有的数据
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
                                    //TODO.批量新增逻辑，循环添加即可
                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            }
                        )
                    }
                },
                '导入功能':{
                    "label":"导入功能",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);//从数据库中获取所有的数据
                        openwindow($uibModal, 'views/Jurisdiction/importAdd.html', 'lg',
                            function ($scope, $modalInstance) {
                                //弹窗列表,写在弹窗内部
                                $scope.importadd = [
                                    {'构架包':'com.primeton.workflow.manager.def', '名称':'com.primeton.workflow.manager.def'},
                                    {'构架包':'com.primeton.workflow.client.process', '名称':'com.primeton.workflow.client.process'},
                                    {'构架包':'com.primeton.eos.exp', '名称':'com.primeton.workflow.eos.exp'},
                                    {'构架包':'org.gocom.abframe.ztest', '名称':'org.gocom.abframe.ztest'},
                                    {'构架包':'org.gocom.abframe.test', '名称':'测试'},
                                    {'构架包':'org.gocom.abframe.test', '名称':'测试'},
                                    {'构架包':'org.gocom.abframe.rights', '名称':'权限管理'},
                                    {'构架包':'org.gocom.abframe.tools', '名称':'其他管理'},
                                    {'构架包':'com.primeton.workflow.core', '名称':'com.primeton.workflow.core'}
                                ];
                                $scope.gridOptions5 = {
                                    data: 'importadd',
                                    //-------- 分页属性 ----------------
                                    enablePagination: true, //是否分页，默认为true
                                    enablePaginationControls: true, //使用默认的底部分页
                                    paginationPageSizes: [10, 15, 20], //每页显示个数可选项
                                    paginationCurrentPage:1, //当前页码
                                    paginationPageSize: 10, //每页显示个数
                                    totalItems : 0, // 总数量
                                    useExternalPagination: true,//是否使用分页按钮
                                    //是否多选，允许多选
                                    multiSelect:true,
                                    enableFooterTotalSelected: true, // 是否显示选中的总数，默认为true, 如果显示，showGridFooter 必须为true
                                    showGridFooter:true,
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
                                                $scope.selectfunRow = row.entity;
                                                console.log($scope.selectfunRow)
                                            }else{
                                                delete $scope.selectfunRow;//不选中则清空
                                            }
                                        });


                                    }
                                };
                                //导入功能分页
                                var getPage = function(curPage, pageSize) {
                                    var firstRow = (curPage - 1) * pageSize;
                                    $scope.gridOptions5.totalItems = $scope.importadd.length;
                                    $scope.gridOptions5.data = $scope.importadd.slice(firstRow, firstRow + pageSize);
                                };
                                //多选功能方法，直接var 然后return即可，在导入方调用即可
                                var importAll = function(){
                                    return $scope.gridApi.selection.getSelectedRows();
                                }
                                //创建机构实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //处理新增机构父机构
                                subFrom.guidParents = obj.original.guid;
                                //导入方法
                                $scope.importAdd = function () {
                                    var dats = importAll();
                                    if(dats.length >0){
                                        console.log(dats)//选中的数据
                                        //TODO.批量导入新增逻辑，加入数据库即可
                                        toastr['success']("导入成功！");
                                        $modalInstance.close();
                                    }else{
                                        toastr['error']("请至少选中一个！");
                                    }
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
                        if(confirm("您确认要删除选中的应用,删除应用将删除该应用下的所有功能组")){

                        }
                    }
                },
                '刷新':{
                    "label":"刷新",
                    "action":function(data){
                        $("#container").jstree().refresh();
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
                        $("#container").jstree().refresh();
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
                                                'type':'fun',
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
                                                    "text": "密码设置",
                                                    'type':'childs'
                                                },
                                                    {
                                                        'id':'79',
                                                        "text": "子功能组",
                                                        'type':'fun',
                                                        "children":[{
                                                            'id':'80',
                                                            "text": "菜单显示",
                                                            'type':'childs'
                                                        }]
                                                    }
                                                ]
                                            },{
                                            "id": "5",
                                            "text": "权限管理",
                                            'type':'fun',
                                            "children":[{
                                               'id':'81',
                                                "text": "应用功能管理",
                                            },{
                                                'id':'82',
                                                "text": "菜单显示",
                                            },{
                                                'id':'83',
                                                "text": "菜单管理",
                                            },{
                                                    'id':'84',
                                                    "text": "约束管理",
                                                },{
                                                'id':'85',
                                                "text": "角色管理",
                                            },

                                            ]
                                        },{
                                            "id": "6",
                                            "text": "组织管理",
                                            'type':'fun',
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
                return true;
            }
        },
        'callback' : {
            move_node:function (node) {
            }
        },

        "plugins" : [ "dnd", "state", "types","search","contextmenu" ]
    }).bind("copy.jstree", function (node,e, data ) {
    })
    /* 定义树列表改变事件*/
    $('#container').on("changed.jstree", function (e, data) {
        console.log(data);
        if(typeof data.node !== 'undefined'){//拿到结点详情
            console.log(data.node.parent)
            if(data.node.parent == '#'){
                $scope.biz.applica = true;
                $scope.biz.apptab = false;
                $scope.biz.appfund = false;
                $scope.biz.appchild = false;
            }else if(data.node.parent == '1'){
                $scope.biz.apptab = true;
                $scope.biz.appfund = false;
                $scope.biz.applica = false;
                $scope.biz.appchild = false;
            }else if(data.node.original.type == 'fun'){
                $scope.biz.appfund = false;
                $scope.biz.appchild = true;
                $scope.biz.applica = false;
                $scope.biz.apptab = false;
            }else if(data.node.parent == '5'||data.node.parent == '4'){
                $scope.biz.appfund = true;
                $scope.biz.appchild = false;
                $scope.biz.applica = false;
                $scope.biz.apptab = false;
            }else{
                $scope.biz.apptab = false;
            }
            $scope.$apply();
        }
    });
    /*--------------------------------------------------------------------------------------分割符--------------------------------------------------------------------------------*/
    //1、应用功能跟模块逻辑
    //ui-grid表格模块
    i18nService.setCurrentLang("zh-cn");
    /*应用功能模块逻辑*/
    $scope.myData = [
        {id: "0", 'APP_NAME':'应用框架模型1', 'APP_CODE':'ABFRAME', 'APP_TYPE':'本地', 'ISOPEN':'是', 'OPEN_DATE':'2017-06-01', 'URL':'上海', 'IP_ADDR':'192.168.1.101', 'IP_PORT':'8080', 'APP_DESC':'这里是测试描述页面1'
        },
        {id: "1", 'APP_NAME':'应用框架模型2', 'APP_CODE':'ABFRAME1', 'APP_TYPE':'远程', 'ISOPEN':'否', 'OPEN_DATE':'2017-06-03', 'URL':'苏州', 'IP_ADDR':'192.168.1.102', 'IP_PORT':'8081', 'APP_DESC':'这里是测试描述页面2'
        },
        {id: "2", 'APP_NAME':'应用框架模型3', 'APP_CODE':'ABFRAME3', 'APP_TYPE':'本地', 'ISOPEN':'是', 'OPEN_DATE':'2017-06-03', 'URL':'北京', 'IP_ADDR':'192.168.1.103', 'IP_PORT':'8082', 'APP_DESC':'这里是测试描述页面3'
        },
        {id: "3", 'APP_NAME':'应用框架模型4', 'APP_CODE':'ABFRAME4', 'APP_TYPE':'本地', 'ISOPEN':'是', 'OPEN_DATE':'2017-06-04', 'URL':'深圳', 'IP_ADDR':'192.168.1.103', 'IP_PORT':'8083', 'APP_DESC':'这里是测试描述页面4'
        },
        {id: "4", 'APP_NAME':'应用框架模型5', 'APP_CODE':'ABFRAME5', 'APP_TYPE':'远程', 'ISOPEN':'否', 'OPEN_DATE':'2017-06-04', 'URL':'南京', 'IP_ADDR':'192.168.1.104', 'IP_PORT':'8085', 'APP_DESC':'这里是测试描述页面5'
        },
        {id: "5", 'APP_NAME':'应用框架模型6', 'APP_CODE':'ABFRAME6', 'APP_TYPE':'本地', 'ISOPEN':'是', 'OPEN_DATE':'2017-06-05', 'URL':'佛山', 'IP_ADDR':'192.168.1.105', 'IP_PORT':'8086', 'APP_DESC':'这里是测试描述页面6'}
    ];
    //ui-grid 具体配置
    //应用列表
    $scope.gridOptions0 = {
        data: 'myData',
        columnDefs: [{ field: 'APP_NAME', displayName: '应用名称'},
            { field: "APP_CODE", displayName:'应用代码'},
            { field: "APP_TYPE", displayName:'应用类型',
                //配置搜索下拉框
                filter:{
                //term: '0',//默认搜索那项
                type: uiGridConstants.filter.SELECT,
                selectOptions: [{ value: '本地', label: '本地' }, { value: '远程', label: '远程' }]
            }},
            { field: "ISOPEN", displayName:'是否开通'},
            { field: "OPEN_DATE",displayName:'开通日期'},
            { field: "URL",displayName:'访问地址'},
            { field: "IP_ADDR",displayName:'ip'},
            { field: "IP_PORT",displayName:'端口'},
            { field: "APP_DESC",displayName:'应用描述'}
        ],
        //---------切换属性-----------------
        enableGridMenu: true, //是否显示grid 菜单
        enableFiltering:true,//打开标识,用于搜索
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
                }else{
                    delete $scope.selectRow;//制空
                }
            });
        }
    };
    //ui-grid getPage方法 分页方法
    var getPage = function(curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions0.totalItems = $scope.myData.length;
        $scope.gridOptions0.data = $scope.myData.slice(firstRow, firstRow + pageSize);
    };
    //新增页面代码
    $scope.show_win = function(){
        openwindow($modal, 'views/Jurisdiction/applicationAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.saveDict = function(item){//保存新增的函数
                    toastr['success']("保存成功！");
                    $modalInstance.close();
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }
    //删除代码
    $scope.transsetDelAll = function () {
        if($scope.selectRow){
            confirm("确定删除选中的应用吗？删除应用将删除该应用下的所有功能组")
        }else {
            toastr['error']("请至少选择一条记录进行删除！","SORRY！");
        }

    }

    //修改页面代码
    $scope.show_edit = function(){
        if($scope.selectRow){
            openwindow($modal, 'views/Jurisdiction/applicationAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    //修改页面代码逻辑
                    $scope.saveDict = function(item){//保存新增的函数
                        toastr['success']("保存成功！");
                        $modalInstance.close();
                    }

                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                })
        }else{
            toastr['error']("请至少选中一条！");
        }
    }

/*-----------------------------------------------------------------------------------------分割符----------------------------------------------------------------------------------*/
    //2、应用模块逻辑
    //应用tab切换列表
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

    //应用信息保存页签
    $scope.saveDict = function(item){//保存新增的函数
        toastr['success']("保存成功！");
    }


    //功能组列表
    $scope.myDataone = [
        {'FUNCGROUP_NAME':'	授权认证','GROUP_LEVEL':'1','FUNCGROUP_SEQ':'.1.','ISLEAF':'否'},
        {'FUNCGROUP_NAME':'	权限管理','GROUP_LEVEL':'1','FUNCGROUP_SEQ':'.2.','ISLEAF':'否'},
        {'FUNCGROUP_NAME':'	组织管理','GROUP_LEVEL':'1','FUNCGROUP_SEQ':'.3.','ISLEAF':'否'},
        {'FUNCGROUP_NAME':'	其他管理','GROUP_LEVEL':'1','FUNCGROUP_SEQ':'.4.','ISLEAF':'否'},
        {'FUNCGROUP_NAME':'	工作流','GROUP_LEVEL':'1','FUNCGROUP_SEQ':'.5.','ISLEAF':'否'}
    ];
    $scope.gridOptions1 = {
        data: 'myDataone',
        columnDefs: [{ field: 'FUNCGROUP_NAME', displayName: '功能组名称'},
            { field: "GROUP_LEVEL", displayName:'节点层次'},
            { field: "FUNCGROUP_SEQ", displayName:'功能组序号'},
            { field: "ISLEAF", displayName:'是否叶子节点'}
        ],
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
                if(getPageone) {
                    getPageone(newPage, pageSize);
                }
            });
            //行选中事件
            $scope.gridApi.selection.on.rowSelectionChanged($scope,function(row,event){
                if(row.isSelected){
                    $scope.selecttwoRow = row.entity;
                    console.log($scope.selecttwoRow)
                }else{
                    delete $scope.selecttwoRow;//不选中，则制空
                }
            });
        }
    };
    //分页功能
    var getPageone = function(curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions1.totalItems = $scope.myDataone.length;
        $scope.gridOptions1.data = $scope.myDataone.slice(firstRow, firstRow + pageSize);
    };

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
        if($scope.selecttwoRow){
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
        }else{
            toastr['error']("请至少选择一条应用进行修改");
        }
    }
    //功能组删除
    $scope.funlistDel =  function(){
        if($scope.selecttwoRow){
            confirm("确定删除选中的功能组？删除功能组将删除该功能下的所有子功能组和资源")
        }else{
            toastr['error']("请至少选择一条功能组进行修改");
        }
    }
/*------------------------------------------------------------------------------------------分割符-------------------------------------------------------------------------------*/
    //2、子功能组页面逻辑
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
        toastr['success']("保存成功！");
    }

    /*子功能组页签内容*/
    $scope.myDatas = [
        {'FUNCGROUP_NAME':'功能组1', 'GUID_PARENTS':'准备删除', 'GROUP_LEVEL':'2', 'ISLEAF':''},
        {'FUNCGROUP_NAME':'功能组2', 'GUID_PARENTS':'准备删除', '节点层次':'2', 'ISLEAF':''},
    ];
    //ui-grid 具体配置
    //功能组列表
    $scope.gridOptions2 = {
        data: 'myDatas',
        columnDefs: [{ field: 'FUNCGROUP_NAME', displayName: '功能组名称'},
            { field: "GUID_PARENTS", displayName:'所属功能组'},
            { field: "GROUP_LEVEL", displayName:'节点层次'},
            { field: "ISLEAF", displayName:'是否叶子节点'}
        ],
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
                if(getPagetwo) {
                    getPagetwo(newPage, pageSize);
                }
            });
            //行选中事件
            $scope.gridApi.selection.on.rowSelectionChanged($scope,function(row,event){
                if(row.isSelected){
                    $scope.selectedRow = row.entity;
                    console.log($scope.selectedRow)
                }else{
                    delete $scope.selectedRow;//不选中，则清空
                }
            });
        }
    };
    /* 功能组列表分页功能*/
    var getPagetwo = function(curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions2.totalItems = $scope.myDatas.length;
        $scope.gridOptions2.data = $scope.myDatas.slice(firstRow, firstRow + pageSize);
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

    //子功能组页签编辑页面
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
            toastr['error']("请至少选中一条功能组进行编辑！");
        }
    }

    //子功能组页签删除方法
    $scope.appchildDelAll = function(){
        if($scope.selectedRow){
            confirm("您确认要删除选中的功能组吗?")
        }else{
            toastr['error']("请至少选中一条删除项！");
        }
    }


    //功能列表表格
    $scope.appfuncAdd = [
        {'FUNC_NAME':'测试功能', 'FUNC_TYPE':'页面流', 'ISMENU':'否', 'GUID_FUNCGROUP':'测试功能组'}
    ];
    $scope.gridOptions3 = {
        data: 'appfuncAdd',
        columnDefs: [{ field: 'FUNC_NAME', displayName: '功能名称'},
            { field: "FUNC_TYPE", displayName:'功能类型'},
            { field: "ISMENU", displayName:'是否定义为菜单'},
            { field: "GUID_FUNCGROUP", displayName:'所属功能组'}
        ],
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
                if(getPagethree) {
                    getPagethree(newPage, pageSize);
                }
            });
            //行选中事件
            $scope.gridApi.selection.on.rowSelectionChanged($scope,function(row,event){
                if(row.isSelected){
                    $scope.selectchildRow = row.entity;
                    console.log($scope.selectchildRow)
                }else{
                    delete $scope.selectchildRow;//不选中，则清空
                }
            });
        }
    };
    /*功能列表分页功能*/
    var getPagethree = function(curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions3.totalItems = $scope.appfuncAdd.length;
        $scope.gridOptions3.data = $scope.appfuncAdd.slice(firstRow, firstRow + pageSize);
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

    //功能列表编辑方法
    $scope.exitappList = function(){
        if($scope.selectchildRow){
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
    //功能列表删除方法
    $scope.exitapplistDelAll=function(){
        if($scope.selectchildRow){
            confirm("确认要删除此功能信息吗?")
        }else{
            toastr['error']("请至少选中一条！");
        }
    }

    /*-------------------------------------------------------------------分割符-------------------------------------------------------------------*/
    /*3、功能界面逻辑*/
    //功能页签跳转控制
    var appfunflag = {};
    $scope.appfunflag = appfunflag;
    //功能列表
    var gnlist = false;
    appfunflag.gnlist = gnlist;
    //资源列表
    var zylist = false;
    appfunflag.zylist = zylist;
    //功能行为
    var gnactive = false;
    appfunflag.gnactive = gnactive;

    //初始化tab展现
    $scope.appfunflag.gnlist = true;
    //控制页切换代码
    appfunflag.loadgwdata = function (type) {
        if(type == 0){
            $scope.appfunflag.gnlist = true;
            $scope.appfunflag.zylist = false;
            $scope.appfunflag.gnactive = false;
        }else if (type == 1){
            $scope.appfunflag.gnlist = false;
            $scope.appfunflag.zylist = true;
            $scope.appfunflag.gnactive = false;
        }else if(type == 2){
            $scope.appfunflag.gnlist = false;
            $scope.appfunflag.gnactive = true;
            $scope.appfunflag.zylist = false;
        }
    }

    //资源列表表格处理
    /*分页功能*/
    var getPagefour = function(curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions4.totalItems = $scope.appfunAdd.length;
        $scope.gridOptions4.data = $scope.appfunAdd.slice(firstRow, firstRow + pageSize);
    };

    /* 功能tab页面逻辑*/
    $scope.biz.appedit = function(item){
        $scope.editflag = !$scope.editflag;//让保存取消方法显现,并且让文本框可以输入
        console.log(item)
    }
    //保存方法
    $scope.biz.appsave = function () {
        $scope.editflag = !$scope.editflag;//让保存取消方法显现
        //调用后台保存逻辑
    }


    $scope.biz.edit = function(){
        $scope.editflag = !$scope.editflag;//让保存取消方法显现
    }
    //资源修改保存方法
    $scope.biz.save = function(){
        $scope.editflag = !$scope.editflag;
    }


    //功能行为 逻辑
    $scope.myDataapp = [{'BHVTYPE_CODE': 's', 'BHVTYPE_NAME': '测试类型'}, {'BHVTYPE_CODE': 'a', 'BHVTYPE_NAME': '测试类型11'}]
    /*定义行为类型列表结构*/
    $scope.gridOption6 = {
        data: 'myDataapp',
        columnDefs: [{field: 'BHVTYPE_CODE', displayName: '行为类型代码'},
            {field: "BHVTYPE_NAME", displayName: '行为类型名称'}
        ],
        //-------- 分页属性 ----------------
        enablePagination: true, //是否分页，默认为true
        enablePaginationControls: true, //使用默认的底部分页
        paginationPageSizes: [10, 15, 20], //每页显示个数可选项
        paginationCurrentPage: 1, //当前页码
        paginationPageSize: 10, //每页显示个数
        //paginationTemplate:"<div></div>", //自定义底部分页代码
        totalItems: 0, // 总数量
        useExternalPagination: true,//是否使用分页按钮
        //是否多选
        multiSelect:false,
        onRegisterApi: function (gridApi) {
            $scope.gridApi = gridApi;
            //分页按钮事件
            gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {
                if (getPagefive) {
                    getPagefive(newPage, pageSize);
                }
            });
            //行选中事件
            $scope.gridApi.selection.on.rowSelectionChanged($scope, function (row, event) {
                if (row.isSelected) {
                    $scope.selectappRow = row.entity;
                    $scope.biz.active = false;
                    console.log(1)
                }else{
                    $scope.biz.active = false;
                    delete $scope.selectappRow;
                }
            });
        }
    };
    /*功能列表分页功能*/
    var getPagefive = function (curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOption6.totalItems = $scope.myDataapp.length;
        $scope.gridOption6.data = $scope.myDataapp.slice(firstRow, firstRow + pageSize);
    };

    //点击查看事件
    $scope.biz.looking = function(){
        if($scope.selectappRow){
            $scope.biz.active = true;
        }else{
            $scope.biz.active = false;
            toastr['error']("请至少选择一种类型");
        }
    }
    /*事件行为列表*/
    $scope.myDatasapp = [{BHV_CODE:'TXT1001',BHV_NAME:'测试行为1','ISEFFECTIVE': 'Y'}, {'BHV_CODE':'TXT1002','BHV_NAME':'测试行为2','ISEFFECTIVE': 'N'}]
    $scope.gridOptions7 = {
        data: 'myDatasapp',
        columnDefs: [
            {field: "BHV_NAME", displayName: '行为名称'},
            {field: "BHV_CODE", displayName: '行为代码'},
            {field: 'ISEFFECTIVE', displayName: '是否有效'}
        ],
        //-------- 分页属性 ----------------
        enablePagination: true, //是否分页，默认为true
        enablePaginationControls: true, //使用默认的底部分页
        paginationPageSizes: [10, 15, 20], //每页显示个数可选项
        paginationCurrentPage: 1, //当前页码
        paginationPageSize: 10, //每页显示个数
        //paginationTemplate:"<div></div>", //自定义底部分页代码
        totalItems: 0, // 总数量
        useExternalPagination: true,//是否使用分页按钮
        //是否多选
        // multiSelect:false,
        onRegisterApi: function (gridApi) {
            $scope.gridApi = gridApi;
            //分页按钮事件
            gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {
                if (getPagefrees) {
                    getPagefrees(newPage, pageSize);
                }
            });
            //行选中事件
            $scope.gridApi.selection.on.rowSelectionChanged($scope, function (row, event) {
                if (row.isSelected) {
                    $scope.selectappRow = row.entity;
                }else{
                    delete $scope.selectappRow;
                }
            });
        }
    };
    /*功能列表分页功能*/
    var getPagefrees = function (curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions7.totalItems = $scope.myDatasapp.length;
        $scope.gridOptions7.data = $scope.myDatasapp.slice(firstRow, firstRow + pageSize);
    };
    //多选方法
    var checkAll = function(){
        return $scope.gridApi.selection.getSelectedRows();
    }
    //功能操作行为保存
    $scope.biz.sesave = function(){
        var dats = checkAll()
        if(dats.length>0){
            toastr['success']("保存成功");
        }else{
            toastr['error']("请至少选中一条！");
        }
    }
});
