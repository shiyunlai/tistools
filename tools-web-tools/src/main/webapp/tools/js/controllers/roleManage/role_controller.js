/**
 * Created by wangbo on 2017/6/11.
 */
angular.module('MetronicApp').controller('role_controller', function($rootScope, $scope ,$modal,$http,i18nService, $timeout,filterFilter,$uibModal,uiGridConstants) {
        var role = {};
        $scope.role = role;
        /* 左侧角色查询逻辑 */
        i18nService.setCurrentLang("zh-cn");
        //ui-grid
        $scope.myData = [
            {'ROLE_CODE': "001000", 'ROLE_NAME': '角色名称','ROLE_TYPE':'一类','GUID_APP':"测试"},
            {'ROLE_CODE': "001001", 'ROLE_NAME': '角色名称','ROLE_TYPE':'一类','GUID_APP':"搜索"},
            {'ROLE_CODE': "001002", 'ROLE_NAME': '角色名称','ROLE_TYPE':'二类','GUID_APP':"查询"}
        ];
        //ui-grid 具体配置
        //下级机构列表
        $scope.gridOptions = {
            data: 'myData',
            columnDefs: [{ field: 'ROLE_CODE', displayName: '角色代码'},
                { field: "ROLE_NAME", displayName:'角色名称'},
                { field: "ROLE_TYPE", displayName:'角色类别',
                    //配置搜索下拉框
                    filter:{
                        //term: '0',//默认搜索那项
                        type: uiGridConstants.filter.SELECT,
                        selectOptions: [{ value: '一类', label: '一类' }, { value: '二类', label: '二类' }]
                    }},
                { field: "GUID_APP", displayName:'隶属应用'}
            ],
            //---------搜索配置---------------------
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
                        console.log(row)
                        $scope.role.shows = true;
                    }else{
                       delete  $scope.selectRow;
                        $scope.role.shows = false;
                    }
                });
            }
        };
        //新增逻辑
        $scope.role_add = function(){
            openwindow($modal, 'views/roleManage/rolemanageAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.add = function(item){
                        //新增代码
                        toastr['success']("保存成功！");
                        $modalInstance.close();
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };

                })
        }
        //修改逻辑
        $scope.role_edit = function(){
           if($scope.selectRow){
               openwindow($modal, 'views/roleManage/rolemanageAdd.html', 'lg',//弹出页面
                   function ($scope, $modalInstance) {
                       $scope.add = function(item){
                           //新增代码
                           toastr['success']("修改成功！");
                           $modalInstance.close();
                       }
                       $scope.cancel = function () {
                           $modalInstance.dismiss('cancel');
                       };
                   })
           }else{
               toastr['error']("请至少选择一条数据进行修改！");
           }
        }
        //删除逻辑
        $scope.role_delete = function(){
            if($scope.selectRow){
                confirm("您确认要删除选中的角色吗,删除角色将同时删除角色的功能分配信息以及角色在操作员和组织对象上的分配")
                toastr['success']("删除成功！");
            }else{
                toastr['error']("请至少选择一条数据进行删除！");
            }
        }

    /* tab 栏切换逻辑 */
    var roleflag = {};
    $scope.roleflag = roleflag;
    //应用信息
    var limit = false;
    roleflag.limit = limit;
    //功能组列表
    var dist = false;
    roleflag.dist = dist;
    //权限与操作员
    var operatorer =operatorer;
    roleflag.operatorer = operatorer;
    //初始化tab展现
    $scope.roleflag.limit = true;
    //控制页切换代码
    role.loadgwdata = function (type) {
        if(type == 0){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.roleflag.limit = true;
            $scope.roleflag.dist = false;
            $scope.roleflag.operatorer = false;
        }else if (type == 1){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.roleflag.limit = false;
            $scope.roleflag.dist = true;
            $scope.roleflag.operatorer = false;
            $scope.rolesflag.operators = true;//初始化打开
        }else if (type == 2){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.rolesflag.operators = true;
        }else if (type == 3){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.rolesflag.org = true;
        }else if (type == 4){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.rolesflag.worklist = true;
        }else if (type == 5){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.rolesflag.post = true;
        }else if (type == 6){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.rolesflag.position = true;
        }else if (type == 7){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.roleflag.operatorer = true;
            $scope.roleflag.limit = false;
            $scope.roleflag.dist = false;
        }

    }
    /* 树结构逻辑代码*/
    //树过滤
    $("#s").submit(function(e) {
        e.preventDefault();
        $("#container").jstree(true).search($("#q").val());
    });


    //  应用管理树结构
    $("#container").jstree({
        "core" : {
            "themes" : {
                "responsive": false
            },
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
        },
        "force_text": true,
        plugins: ["sort", "types", "checkbox", "themes", "html_data"],
        "checkbox": {
            "keep_selected_style": false,//是否默认选中
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
        "plugins" : [ "wholerow", "checkbox" ]
    }).bind("copy.jstree", function (node,e, data ) {
    })

    $scope.role.checkAll = function(){
        var nodes=$("#container").jstree("get_checked");
        if(nodes.length> 0 ){
            console.log(nodes);//获取所有节点的id
            toastr['success']("保存成功！");
        }else{
            toastr['error']("请至少选择一个分配权限！");
        }
    }

    /*权限分配业务逻辑*/
    var rolesflag = {};
    $scope.rolesflag = rolesflag;
    //操作员
    var operators = false;
    rolesflag.operators = operators;
    //机构
    var org = false;
    rolesflag.org = org;
    //工作组
    var worklist = false;
    rolesflag.worklist = worklist;
    //岗位
    var post = false;
    rolesflag.post = post;
    //职位
    var position = false;
    rolesflag.position = position;
    //初始化tab展现
    $scope.rolesflag.operators = true;


    /*grid表格*/
    i18nService.setCurrentLang("zh-cn");
    //ui-grid
    $scope.myDatas = [{USER_ID: "成成", OPERATOR_NAME: '成成',OPERATOR_STATUS:'一类'},
        {USER_ID: "豪豪", OPERATOR_NAME: '豪豪',OPERATOR_STATUS:'二类'},
        {USER_ID: "肖肖", OPERATOR_NAME: '肖肖',OPERATOR_STATUS:'一类'},
        {USER_ID: "测试", OPERATOR_NAME: '测试',OPERATOR_STATUS:'三类'}
    ]
    //ui-grid 具体配置
    //下级机构列表
    $scope.gridOptions1 = {
        data: 'myDatas',
        columnDefs: [{ field: 'USER_ID', displayName: '登陆名称'},
            { field: "OPERATOR_NAME", displayName:'操作员名称'},
            { field: "OPERATOR_STATUS", displayName:'所属类别'}
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
    //ui-grid getPage方法
    var getPageone = function(curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions1.totalItems = $scope.myDatas.length;
        $scope.gridOptions1.data = $scope.myDatas.slice(firstRow, firstRow + pageSize);
        //或者像下面这种写法
        //$scope.myData = mydefalutData.slice(firstRow, firstRow + pageSize);
    };

    //新增操作员方法
    $scope.role.rolAddone = function(){
        openwindow($modal, 'views/roleManage/roleAddcaozuo.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){
                    //新增代码
                    toastr['success']("新增操作员成功！");
                    $modalInstance.close();
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }
    //修改tab操作员方法
    $scope.role_rolaEditone = function(){
        if($scope.selecttwoRow){
            openwindow($modal, 'views/roleManage/roleAddcaozuo.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.add = function(item){
                        //新增代码
                        toastr['success']("新增操作员成功！");
                        $modalInstance.close();
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                })
        }else{
            toastr['error']("请最少选中一条进行编辑！");
        }
    }
    //删除tab操作员方法
    $scope.role_rolaDeleteone = function(){
        if($scope.selecttwoRow){
            toastr['success']("删除操作员成功！");
        }else{
            toastr['error']("请最少选中一条进行删除！");
        }
    }

    /*tab下机构详情*/
    $scope.orgData = [{ROLE_NAME: "成成", ROLE_CODE: 'TX1001',ROLE_org:'组织机构'},
        {ROLE_NAME: "豪豪", ROLE_CODE: 'TX1002',ROLE_org:'上海分行'},
        {ROLE_NAME: "肖肖", ROLE_CODE: 'TX1003',ROLE_org:'内容分行'},
    ]

    $scope.gridOptions2 = {
        data: 'orgData',
        columnDefs: [{ field: 'ROLE_NAME', displayName: '角色名称'},
            { field: "ROLE_CODE", displayName:'角色代码'},
            { field: "ROLE_org", displayName:'所属机构'}
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
                    $scope.selectthrRow = row.entity;
                }else{
                    delete $scope.selectthrRow;//不选中，则制空
                }
            });
        }
    };
    //ui-grid getPage方法
    var getPagetwo = function(curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions2.totalItems = $scope.orgData.length;
        $scope.gridOptions2.data = $scope.orgData.slice(firstRow, firstRow + pageSize);
    };

    //新增操作员方法
    $scope.role.orgAdd = function(){
        openwindow($modal, 'views/roleManage/roleAddorg.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){
                    //新增代码
                    toastr['success']("新增操作员成功！");
                    $modalInstance.close();
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }
    //修改tab操作员方法
    $scope.role_orgEdit = function(){
        if($scope.selectthrRow){
            openwindow($modal, 'views/roleManage/roleAddorg.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.add = function(item){
                        //新增代码
                        toastr['success']("新增操作员成功！");
                        $modalInstance.close();
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                })
        }else{
            toastr['error']("请最少选中一条进行编辑！");
        }
    }
    //删除tab操作员方法
    $scope.role_orgDelete = function(){
        if($scope.selectthrRow){
            toastr['success']("删除对应机构成功！");
        }else{
            toastr['error']("请最少选中一条进行删除！");
        }
    }

    /* tab 下对应工作组管理详情*/
    $scope.workData = [{ROLE_NAME: "成成", ROLE_CODE: 'TX1001',ROLE_work:'搜索组'},
        {ROLE_NAME: "豪豪", ROLE_CODE: 'TX1002',ROLE_work:'查询组'},
        {ROLE_NAME: "肖肖", ROLE_CODE: 'TX1003',ROLE_work:'交易组'},
    ]
    $scope.gridOptions3 = {
        data: 'workData',
        columnDefs: [{ field: 'ROLE_NAME', displayName: '角色名称'},
            { field: "ROLE_CODE", displayName:'角色代码'},
            { field: "ROLE_work", displayName:'所属工作组'}
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
                    $scope.selectforRow = row.entity;
                }else{
                    delete $scope.selectforRow;//不选中，则制空
                }
            });
        }
    };
    //ui-grid getPage方法
    var getPagethree = function(curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions3.totalItems = $scope.workData.length;
        $scope.gridOptions3.data = $scope.workData.slice(firstRow, firstRow + pageSize);
    };

    //新增tab下功能组方法
    $scope.role.workAdd = function(){
        openwindow($modal, 'views/roleManage/roleAddwork.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){
                    //新增代码
                    toastr['success']("新增操作员成功！");
                    $modalInstance.close();
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }
    //修改tab功能组方法
    $scope.role_workEdit = function(){
        if($scope.selectforRow){
            openwindow($modal, 'views/roleManage/roleAddwork.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.add = function(item){
                        //新增代码
                        toastr['success']("新增操作员成功！");
                        $modalInstance.close();
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                })
        }else{
            toastr['error']("请最少选中一条进行编辑！");
        }
    }
    //删除tab操作员方法
    $scope.role_workDelete = function(){
        if($scope.selectforRow){
            toastr['success']("删除对应功能组成功！");
        }else{
            toastr['error']("请最少选中一条进行删除！");
        }
    }
    /*角色与岗位关系*/
    $scope.postData = [{ROLE_NAME: "成成", ROLE_CODE: 'TX1001',ROLE_post:'搜索岗'},
        {ROLE_NAME: "豪豪", ROLE_CODE: 'TX1002',ROLE_post:'查询岗'},
        {ROLE_NAME: "肖肖", ROLE_CODE: 'TX1003',ROLE_post:'交易岗'},
    ]
    $scope.gridOptions4 = {
        data: 'postData',
        columnDefs: [{ field: 'ROLE_NAME', displayName: '角色名称'},
            { field: "ROLE_CODE", displayName:'角色代码'},
            { field: "ROLE_post", displayName:'岗位'}
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
                if(getPagefiv) {
                    getPagefiv(newPage, pageSize);
                }
            });
            //行选中事件
            $scope.gridApi.selection.on.rowSelectionChanged($scope,function(row,event){
                if(row.isSelected){
                    $scope.selectfiveRow = row.entity;
                }else{
                    delete $scope.selectfiveRow;//不选中，则制空
                }
            });
        }
    };
    //ui-grid getPage方法
    var getPagefiv = function(curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions4.totalItems = $scope.postData.length;
        $scope.gridOptions4.data = $scope.postData.slice(firstRow, firstRow + pageSize);
    };

    //新增tab下功能组方法
    $scope.role.postAdd = function(){
        openwindow($modal, 'views/roleManage/roleAddpost.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){
                    //新增代码
                    toastr['success']("新增操作员成功！");
                    $modalInstance.close();
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }
    //修改tab功能组方法
    $scope.role_postEdit = function(){
        if($scope.selectfiveRow){
            openwindow($modal, 'views/roleManage/roleAddpost.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.add = function(item){
                        //新增代码
                        toastr['success']("新增操作员成功！");
                        $modalInstance.close();
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                })
        }else{
            toastr['error']("请最少选中一条进行编辑！");
        }
    }
    //删除tab操作员方法
    $scope.role_postDelete = function(){
        if($scope.selectfiveRow){
            toastr['success']("删除角色对应岗位成功！");
        }else{
            toastr['error']("请最少选中一条进行删除！");
        }
    }


    /*权限与操作员详情*/
    //ui-grid
    $scope.myDataer = [
        {'OPERATOR_NAME': "成", 'OPERATOR_STATUS': 'TX10001','USER_ID':'成','AUTH_MODE':"本地密码认证"},
        {'OPERATOR_NAME': "波", 'OPERATOR_STATUS': 'TX2002','USER_ID':'波','AUTH_MODE':"LDAP认证"},
        {'OPERATOR_NAME': "李", 'OPERATOR_STATUS': 'TX3002','USER_ID':'李','AUTH_MODE':"本地密码认证"}
    ];
    //ui-grid 具体配置
    //下级机构列表
    $scope.gridOptioner = {
        data: 'myDataer',
        columnDefs: [{ field: 'OPERATOR_NAME', displayName: '操作员姓名'},
            { field: "OPERATOR_STATUS", displayName:'操作员状态'},
            { field: "USER_ID", displayName:'登录用户名'},
            { field: "AUTH_MODE", displayName:'认证模式'}
        ],
        //---------搜索配置---------------------
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
                if(getPages) {
                    getPage(newPage, pageSize);
                }
            });
            //行选中事件
            $scope.gridApi.selection.on.rowSelectionChanged($scope,function(row,event){
                if(row.isSelected){
                    $scope.selecterRow = row.entity;
                    console.log(row)
                }else{
                    delete  $scope.selecterRow;
                }
            });
        }
    };
    //ui-grid getPage方法
    var getPages = function(curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptioner.totalItems = $scope.myDataer.length;
        $scope.gridOptioner.data = $scope.myDataer.slice(firstRow, firstRow + pageSize);
        //或者像下面这种写法
        //$scope.myData = mydefalutData.slice(firstRow, firstRow + pageSize);
    };

    //新增操作员逻辑
    $scope.role.operaAdd = function(){
        openwindow($modal, 'views/roleManage/roleoperAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){
                    //新增代码
                    toastr['success']("新增成功！");
                    $modalInstance.close();
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            })
    }
    //修改操作员逻辑
    $scope.role.operaEdit = function(){
        if($scope.selecterRow){
            openwindow($modal, 'views/roleManage/roleoperAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.add = function(item){
                        //新增代码
                        toastr['success']("新增成功！");
                        $modalInstance.close();
                    }
                    //新增操作员弹窗保存
                    $scope.add = function(item){
                        console.log(item)
                        toastr['success']("保存成功！");
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };

                })
        }else{
            toastr['error']("请选中一条进行修改！");
        }
    }
    //删除操作员逻辑
    $scope.role.operaDelete = function(){
        if($scope.selecterRow){
            toastr['success']("删除成功！");
        }else{
            toastr['error']("请选中一条进行删除！");
        }
    }

});
