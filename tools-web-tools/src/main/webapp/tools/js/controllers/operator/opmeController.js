/**
 * Created by wangbo on 2017/6/20.
 */

MetronicApp.controller('opmanage_controller', function ($filter,$rootScope, $scope, $state, $stateParams, filterFilter,operator_service, $modal,$uibModal, $http, $timeout,$interval,i18nService) {
    //grid表格
    i18nService.setCurrentLang("zh-cn");

    //查询操作员列表
    var operman ={};
    $scope.operman = operman;
    operman.queryAll = function(){
        //查询所有业务字典
        var subFrom = {};
        operator_service.queryAllOperator(subFrom).then(function(data){
            var datas = data.retMessage;
            console.log(datas);
            if(data.status == "success"){
                $scope.gridOptions.data =  datas;
                $scope.gridOptions.mydefalutData = datas;
                $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
            }else{
                toastr['error']('查询失败'+'<br/>'+data.retMessage);
            }
        })
    }
    operman.queryAll();
    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var com = [{ field: 'operatorName', displayName: '操作员姓名'},
        { field: "userId", displayName:'登录用户名'},
        { field: "authMode", displayName:'认证模式'},
        { field: "operatorStatus",displayName:'操作员状态'},
        { field: "menuType",displayName:'菜单风格'},
        { field: "lockLimit",displayName:'锁定次数限制'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);


    //新增操作员代码
    $scope.operatAdd = function(item){
        openwindow($uibModal, 'views/operator/operatorAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                var operatFrom = {};
                $scope.operatFrom = operatFrom;
                $scope.operatFrom.lockLimit = 5;
                $scope.add = function(item){//保存新增的函数
                    var subFrom = {};
                    $scope.subFrom = subFrom;
                    subFrom = item;
                    operator_service.createOperator(subFrom).then(function(data){
                        console.log(data)
                        if(data.status == "success"){
                            toastr['success']( "新增成功！");
                            operman.queryAll();
                            $modalInstance.close();
                        }else{
                            toastr['error']('新增失败'+'<br/>'+data.retMessage);
                        }
                    })
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }
    //修改操作员代码
    $scope.operatEdit = function(id){
        if($scope.selectRow){
            var items = $scope.selectRow;
            openwindow($uibModal, 'views/operator/operatorAdd.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    if(!isNull(items)){//如果参数不为空，则就回显
                        $scope.operatFrom = angular.copy(items);
                    }
                    $scope.id = id;//获取到id，用来判断是否编辑，因为scope作用域不同，所以不同
                    $scope.add = function(item){//保存新增的函数
                        var subFrom = {};
                        $scope.subFrom = subFrom;
                        subFrom = item;
                        operator_service.editOperator(subFrom).then(function(data){
                            console.log(data)
                            if(data.status == "success"){
                                toastr['success']( "新增成功！");
                                operman.queryAll();
                                $modalInstance.close();
                            }else{
                                toastr['error']('新增失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
        )
        }else{
            toastr['error']("请至少选中一个操作员进行操作！");
        }
    }

});

/* 重组菜单控制器*/
MetronicApp.controller('reomenu_controller', function ($filter,$rootScope, $scope, $state, $stateParams, filterFilter, $modal,$uibModal, $http, $timeout,$interval,i18nService) {
    var opmer ={};
    $scope.opmer = opmer;

    //查询
    $scope.opmer.search = function(data){
        console.log(data)
        if (data.operuser == '张三'){
            $scope.opmer.searchok = true;
            $scope.opmer.config = true;
        }else if(data.appCode == '123'){
            if(confirm('该用户未自定义重组菜单，是否配置')){
                $scope.opmer.searchok = true;
                $scope.opmer.config = true;
            }
        }
        else{
            confirm('找不到目标用户，请重新输入')
            $scope.opmer.searchok = false;
            $scope.opmer.config = false;
            $scope.searchFrom.appCode = '';
            $scope.searchFrom.operuser = '';
        }

    }


    //左侧树结构
    //  应用管理树结构
    $("#container").jstree({
        "core" : {
            "themes" : {
                "responsive": false
            },
            "check_callback" : true,//在对树结构进行改变时，必须为true
            'data':
                [{
                    "id": "1",
                    "text": "应用菜单",
                    icon:'fa fa-hospital-o icon-state-info icon-lg ',
                    "children":
                        [
                            {
                                "id": "2",
                                "text": "组织管理",
                                icon:'fa fa-home icon-state-info icon-lg',
                            },
                            {
                                "id": "3",
                                "text": "权限管理",
                                icon:'fa fa-home icon-state-info icon-lg',
                            },
                            {
                                "id": "4",
                                "text": "授权认证",
                                icon:'fa fa-home icon-state-info icon-lg',
                            },
                            {
                                "id": "5",
                                "text": "其他管理",
                                icon:'fa fa-home icon-state-info icon-lg',
                            },{
                            "id": "6",
                            "text": "工作流",
                            icon:'fa fa-home icon-state-info icon-lg',
                        },{
                            "id": "7",
                            "text": "测试图标",
                            icon:'fa fa-home icon-state-info icon-lg',
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
            'is_draggable':function (node) {
                //用于控制节点是否可以拖拽.
                if(node.id == 3){
                    return false;//根节点禁止拖拽
                }
                return true;
            }
        },
        'callback' : {
            move_node:function (node) {
                console.log(node)
            }
        },
        "plugins" : [ "state", "types","search" ,"checkbox","wholerow"]// 插件引入 dnd拖拽插件 state缓存插件(刷新保存) types多种数据结构插件  checkbox复选框插件
    }).bind("move_node.jstree",function (e,data) {
        if(confirm("确认要移动此机构吗?")){
            //TODO.
        }else{
            data.position = data.old_position;
            return false;
        }

        console.log(data);
    })


    /* 菜单二 */
    $("#container2").jstree({
        "core" : {
            "themes" : {
                "responsive": false
            },
            "check_callback" : true,
            'data':
                [{
                    "id": "1",
                    "text": "应用菜单",
                    icon:'fa fa-hospital-o icon-state-info icon-lg ',
                    "children":
                        [
                            {
                                "id": "2",
                                "text": "组织管理",
                                icon:'fa fa-home icon-state-info icon-lg',
                            },
                            {
                                "id": "3",
                                "text": "权限管理",
                                icon:'fa fa-home icon-state-info icon-lg',
                            },
                            {
                                "id": "4",
                                "text": "授权认证",
                                icon:'fa fa-home icon-state-info icon-lg',
                            },
                            {
                                "id": "5",
                                "text": "其他管理",
                                icon:'fa fa-home icon-state-info icon-lg',
                            },{
                            "id": "6",
                            "text": "工作流",
                            icon:'fa fa-home icon-state-info icon-lg',
                        },{
                            "id": "7",
                            "text": "测试图标",
                            icon:'fa fa-home icon-state-info icon-lg',
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
                console.log("start");//拖拽开始
            },
            'is_draggable':function (node) {
                //用于控制节点是否可以拖拽.
                if(node.id == 3){
                    return false;//根节点禁止拖拽
                }
                return true;
            }
        },
        'callback' : {
            move_node:function (node) {
            }
        },
        "plugins" : [ "dnd", "state", "types","search" ,"checkbox","wholerow"]// 插件引入 dnd拖拽插件 state缓存插件(刷新保存) types多种数据结构插件  checkbox复选框插件
    }).bind("move_node.jstree",function (e,data) {
        if(confirm("确认要移动此机构吗?")){
            //TODO.
        }else{
            // data.inst.refresh(data.inst._get_parent(data.rslt.oc));
            return false;
        }
        console.log(e);
        console.log(data);
    })


    $scope.opmer.saveconfig = function(){
        toastr['success']("保存成功，下次重启后生效！");
    }
});













