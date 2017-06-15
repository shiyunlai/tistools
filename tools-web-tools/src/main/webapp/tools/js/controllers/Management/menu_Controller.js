/**
 * Created by wangbo on 2017/6/1.
 */
angular.module('MetronicApp').controller('menu_controller', function($rootScope, $scope, $http, $timeout,i18nService,uiGridConstants,uiGridSelectionService,$uibModal) {
    var menu = {};
    $scope.menu = menu;
    /*0、菜单管理机构树逻辑*/
    $("#s").submit(function(e) {
        e.preventDefault();
        $("#container").jstree(true).search($("#q").val());
    });
    //树自定义右键功能(根据类型判断)
    var items = function customMenu(node) {
        var control;
        if(node.parent == '#'){
            var it = {
                "新增顶级菜单":{
                    "id":"createa",
                    "label":"新增顶级菜单",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        openwindow($uibModal, 'views/Management/manachildAdd.html', 'lg',
                            function ($scope, $modalInstance) {
                                console.log($modalInstance)
                                //创建机构实例
                                var menuFrom = {};
                                $scope.menuFrom = menuFrom;
                                //处理新增机构父机构
                                menuFrom.guidParents = obj.original.guid;
                                //增加方法
                                $scope.add = function (menuFrom) {
                                    //TODO.
                                    console.log(menuFrom)
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
                    "action":function(data){
                        $("#container").jstree().refresh();
                    }
                }
            }
            return it;
        }
        if(node.parent == 1){
            var it = {
                "增加子菜单":{
                    "id":"createc",
                    "label":"增加子菜单",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);//从数据库中获取所有的数据
                        console.log(obj)
                        openwindow($uibModal, 'views/Management/manachildAdd.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建机构实例
                                var menchFrom = {};
                                $scope.menchFrom = menchFrom;
                                //处理新增机构父机构
                                menchFrom.guidParents = obj.original.guid;
                                //增加方法
                                $scope.add = function (subFrom) {
                                    //TODO.批量新增逻辑，循环添加即可
                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            })
                    }
                },
                "删除菜单":{
                    "label":"删除菜单",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if(confirm("您确认要删除选中的应用,删除应用将删除该应用下的所有功能组")){
                            //TODO.删除逻辑
                            toastr['success']( "删除成功！");
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
                    "text": "应用菜单",
                    icon:'icon-state-warning',
                    "children":
                        [
                            {
                                "id": "2",
                                "text": "组织管理",
                            },
                            {
                                "id": "3",
                                "text": "权限管理",
                            },
                            {
                                "id": "4",
                                "text": "授权认证",
                            },
                            {
                            "id": "5",
                            "text": "其他管理",
                            },{
                            "id": "6",
                            "text": "工作流",
                            }
                        ]
                }
                ]
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
                $scope.menu.menushow = true;
                $scope.menu.menusearch = false;
            }else if(data.node.parent == '1'){
                $scope.menu.menusearch = true;
                $scope.menu.menushow = false;
            }else if(data.node.original.type == 'fun'){
            }else if(data.node.parent == '5'||data.node.parent == '4'){
            }else{
                $scope.biz.apptab = false;
            }
            $scope.$apply();
        }
    });

    /* 右侧菜单详情列表调用*/
    i18nService.setCurrentLang("zh-cn");
    /*应用功能模块逻辑*/
    $scope.myData = [
        {'MENU_NAME':'组织管理','MENU_CODE':'zuzhi','ISLEAF':'是','MENU_LABEL':'组织管理','IMAGE_PATH':'test1.com','EXPAND_PATH':'test1.com'},
        {'MENU_NAME':'权限管理','MENU_CODE':'quanxian','ISLEAF':'否','MENU_LABEL':'权限管理','IMAGE_PATH':'test2.com','EXPAND_PATH':'test2.com'},
        {'MENU_NAME':'授权认证','MENU_CODE':'shouquan','ISLEAF':'否','MENU_LABEL':'授权认证','IMAGE_PATH':'test3.com','EXPAND_PATH':'test3.com'},
        {'MENU_NAME':'其他管理','MENU_CODE':'info','ISLEAF':'是','MENU_LABEL':'其他管理','IMAGE_PATH':'test4.com','EXPAND_PATH':'test4.com'},
        {'MENU_NAME':'工作流','MENU_CODE':'work','ISLEAF':'是','MENU_LABEL':'工作流','IMAGE_PATH':'test5.com','EXPAND_PATH':'test5.com'}
    ];
    //ui-grid 具体配置
    //应用列表
    $scope.gridOptions = {
        data: 'myData',
        columnDefs: [{ field: 'MENU_NAME', displayName: '菜单名称'},
            { field: "MENU_CODE", displayName:'菜单代码'},
            { field: "ISLEAF", displayName:'是否叶子标签'},
            { field: "MENU_LABEL",displayName:'菜单显示名称'},
            { field: "IMAGE_PATH",displayName:'菜单闭合路径'},
            { field: "EXPAND_PATH",displayName:'菜单展开图片路径'}
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

    //新增代码
    $scope.menuAdd = function(){
        openwindow($uibModal, 'views/Management/manachildAdd.html', 'lg',// 弹出页面
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

    //修改菜单逻辑
    $scope.menuEdit = function(){
        if($scope.selectRow){
            openwindow($uibModal, 'views/Management/manachildAdd.html', 'lg',// 弹出页面//弹出页面
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

    //删除代码逻辑
    $scope.menuDelete = function () {
        if($scope.selectRow){
            confirm("确定删除选中的菜单吗？删除应用将删除该应用下的所有子菜单")
        }else {
            toastr['error']("请至少选择一条记录进行删除！","SORRY！");
        }

    }



    /*2.菜单详情修改*/
    //编辑
    $scope.menu.menuEdit = function(){
        $scope.editflag = !$scope.editflag;//让保存取消方法显现
    }
    //新增子菜单逻辑
    $scope.menu.childAdd = function(){
        openwindow($uibModal, 'views/Management/manachildAdd.html', 'lg',// 弹出页面//弹出页面
            function ($scope, $modalInstance) {
                //修改页面代码逻辑
                $scope.add = function(item){//保存新增的函数
                    toastr['success']("保存成功！");
                    $modalInstance.close();
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }
    //保存方法
    $scope.menu.save = function (item) {
        toastr['success']("保存成功");
        $scope.editflag = !$scope.editflag;//让保存取消方法显现
        //调用后台保存逻辑
    }

    //取消按钮
    $scope.menu.cenel = function(){
        $scope.editflag = !$scope.editflag;
    }


});
