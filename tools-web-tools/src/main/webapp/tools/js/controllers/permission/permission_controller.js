
/*操作员功能行为权限控制器*/
MetronicApp.controller('permission_controller', function ($rootScope, $scope, $state, $stateParams,common_service, filterFilter, $modal,$uibModal, $http, $timeout,$interval,i18nService) {

    var  permiss = {}
    $scope.permiss = permiss;
   //返回方法
    $scope.myback = function(){
        window.history.back(-1);
    }
    //因为传入的是对象，拿到的是字符串，所以先转成json对象
    var peaids = angular.fromJson($stateParams.id);
    var userid = peaids.userid;//操作员userid
    var operguid = peaids.operguid;//操作员guid
    $scope.currRole = userid;//显示当前操作员

    var res = $rootScope.res.operator_service;//页面所需调用的服务
    var subFrom = {};
    subFrom.userId  = userid;
    common_service.post(res.queryOperatorAllApp,subFrom).then(function(data){
        console.log(data)
        if(data.status == "success"){
            permiss.appselectApp= data.retMessage;

        }
    })

    /* tab 栏切换逻辑 */
    var proflat = {};
    $scope.proflat = proflat;
    //角色下功能 默认显示
    var funcon= true;
    proflat.funcon = funcon;
    //角色下功能行为
    var funactive = false;
    proflat.funactive = funactive;
    permiss.loadgwdata = function (type) {
        if(type==0){
            $scope.proflat.funcon = true;
            $scope.proflat.funactive = false;
            $scope.permiss.func = true;
            queryfunMap($scope.appGuid,userid);//调用刷新类别方法，传入功能guid才行
        }else{
            $scope.proflat.funcon = false;
            $scope.proflat.funactive = true;
        }
    }



    //查询应用内容显示
    $scope.permiss.search =function (item) {
           //让下方内容显示
            if(!isNull(item)){
                $scope.permiss.selectApp = true;
                //调用刷新树方法，传入应用
                queryjstree(userid,item);//查询所有树结构
                $scope.appGuid = item;
                queryfunMap($scope.appGuid,userid);//查询操作员功能权限
        }else{
                $scope.permiss.selectApp = false;
            }
    }
    /* 树结构逻辑代码*/
    //菜单搜索修改，改成键盘弹起事件，加上search组件
    var to = false;
    $('#q').keyup(function () {
        if(to) {
            clearTimeout(to);
        }
        $('#container').jstree().load_all();
        to = setTimeout(function () {
            var v = $('#q').val();
            $('#container').jstree(true).search(v);
        }, 250);
    });

    //清空n
    permiss.clear = function () {
        $scope.searchitem = "";
        if(to) {
            clearTimeout(to);
        }
        $('#container').jstree().load_all();
        to = setTimeout(function () {
            var v = $('#q').val();
            $('#container').jstree(true).search(v);
        }, 250);
    }

    //根据用户和应用id查询功能树
    var queryjstree = function (userid,appguid) {
        var subFrom = {};
        subFrom.userId  = userid;
        subFrom.appGuid  = appguid;
        common_service.post(res.getOperatorFuncInfo,subFrom).then(function(data){
            if(data.status == "success"){
                var jstree = [angular.fromJson(data.retMessage)];
                //生成树结构
                $('#container').jstree('destroy',false);//防止多次查询，先销毁
                $("#container").jstree({
                    "core" : {
                        "themes" : {
                            "responsive": false
                        },
                        "check_callback" : false,//在对树结构进行改变时，必须为true
                        'data':jstree
                    },
                    "force_text": true,
                    plugins: ["sort", "types", "themes", "html_data"],
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
                        },
                        'always_copy':true//拖拽拷贝，非移除
                    },
                    'callback' : {
                        move_node:function (node) {
                        }
                    },
                    "plugins" : [ "state", "types","dnd","search" ,"wholerow"]// 插件引入 dnd拖拽插件 state缓存插件(刷新保存) types多种数据结构插件  checkbox复选框插件
                }).bind("select_node.jstree", function (e, data) {
                    if(data.node.original.isLeaf =='Y'){
                        $scope.proflat.funactive = true;//功能行为显示
                        $scope.proflat.funcon = false;//功能显示
                        $scope.isYear = false;
                        $scope.permiss.funcselect = true;//配置角色功能行为显示
                        $scope.isselfts =false;//控制tab二隐藏
                        blackAdd(data.node.id);//调用添加特别禁止功能
                        delblack(data.node.id);//调用移除特别禁止功能
                        allow(data.node.id);//调用添加特别允许功能
                        removeallow(data.node.id);//调用移除特别允许功能
                        queryfunlist(data.node.id,userid);//调用查询列表方法
                    }else{
                        $scope.proflat.funactive = false;//功能行为显示
                        $scope.proflat.funcon = true;//功能显示
                        // $scope.isYear = true;//禁止点击
                        $scope.permiss.func = true;//配置角色功能显示
                        queryfunMap($scope.appGuid,userid);//查询操作员功能权限
                        blackFuncAdd();//添加功能特别禁止方法
                        delblackfunc();//调用功能移除禁止方法
                        allowfunc();//调用功能特别允许方法
                        removeallowfunc();//调用功能特别允许方法
                        $scope.isselfts =true;
                        $scope.notrolegird.data =[] ;//数据全部清空
                        $scope.tograntgird.data =  [];
                        $scope.alrolegird.data =  [];
                        $scope.permissiongird.data =  [];
                    }

                    $scope.$apply();
                });
            }
        })
    }



    i18nService.setCurrentLang("zh-cn");
    /*-------------------------------- 操作员角色功能代码逻辑--------------------------------*/
    //查询操作员拥有的功能
    var queryfunMap= function (appguid,userid) {
        var subFrom = {};
        subFrom.data = {};
        subFrom.data.userId  = userid;
        subFrom.data.appGuid  = appguid;
        common_service.post(res.getOperatorFuncAuthInfo,subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
                //已授予表格
                if(!isNull(datas.auth)){
                    $scope.notfuncrolegird.data =datas.auth ;
                }else{
                    $scope.notfuncrolegird.data =[] ;
                }
                //未授予功能
                if(!isNull(datas.unauth)){
                    $scope.tograntfuncgird.data = datas.unauth;
                }else{
                    $scope.tograntfuncgird.data =  [];
                }
                //特别禁止功能
                if(!isNull(datas.forbid)){
                    $scope.alrolefuncgird.data = datas.forbid;
                }else{
                    $scope.alrolefuncgird.data =  [];
                }
                //特别允许
                if(!isNull(datas.permit)){
                    $scope.permissfuncgird.data = datas.permit;
                }else{
                    $scope.permissfuncgird.data =  [];
                }

            }
        })
    }

    //已授予功能
    $scope.notfuncrolegird = {
        enablePaginationControls: false,
        paginationPageSize: 10,
        multiSelect:false,
        columnDefs: [
            { field: "funcName", displayName:'功能名称'},
            { field: "funcCode", displayName:'功能代码'}
        ]
    };
    var ffunc = function(row){
        if(row.isSelected){
            $scope.notrolfuncRow = row.entity;
        }else{
            $scope.notrolfuncRow = '';//制空
        }
    }

    $scope.notfuncrolegird.onRegisterApi = function (gridApi) {
        $scope.notfuncrolegird = gridApi;
        $scope.notfuncrolegird.selection.on.rowSelectionChanged($scope,ffunc);
    }



    //添加特别禁止功能逻辑
    var blackFuncAdd = function () {
        $scope.permiss.funcadd = function () {
            /*var dats = $scope.notrolegird.getSelectedRows();
            var dats = $scope.alrolegird.getSelectedRows();
             if(!dats.length> 0){
                 toastr['error']("请至少选择一个角色");
                 return false;
             }else{
             }*/
            /*多选有问题，会冲突，先单选测试*/
            if($scope.notrolfuncRow == ""){
                toastr['error']("请至少选择一个角色进行添加");
                return false;
            }else{
                var dates = $scope.notrolfuncRow;
                var subFrom = {};
                subFrom.guidOperator = operguid;
                subFrom.guidFunc = dates.guid;
                subFrom.authType = 0;//特别禁止
                subFrom.guidApp = $scope.appGuid;//应用Guid
                subFrom.guidFuncgroup = dates.guidFuncgroup;//应用Guid
                //调用查询接口,模拟多选
                var tis = {};
                tis.data = [];
                tis.data.push(subFrom)
                common_service.post(res.addAcOperatorFunc,tis).then(function(data){
                    console.log(data)
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        toastr['success']("添加特别禁止成功");
                        queryfunMap($scope.appGuid,userid);//调用刷新类别方法，传入功能guid才行
                    }
                })
            }
        }
    }


    //特别禁止功能表格
    $scope.alrolefuncgird = {
        enablePaginationControls: false,
        paginationPageSize: 10,
        multiSelect:false,
        columnDefs: [
            { field: "funcName", displayName:'功能名称'},
            { field: "funcCode", displayName:'功能代码'}
        ]
    };
    var alrolfunc = function(row){
        if(row.isSelected){
            $scope.alrolfuncs = row.entity;
        }else{
            $scope.alrolfuncs = '';//制空
        }
    }

    $scope.alrolefuncgird.onRegisterApi = function (gridApi) {
        $scope.alrolefuncgird = gridApi;
        $scope.alrolefuncgird.selection.on.rowSelectionChanged($scope,alrolfunc);
    }


    //移除特别禁止行为逻辑
    var delblackfunc = function () {
        $scope.permiss.funcdel = function () {
            if($scope.alrolfuncs == ""){
                toastr['error']("请至少选择一个角色进行移除");
                return false;
            }else{
                var dates = $scope.alrolfuncs;
                var subFrom = {};
                subFrom.guidOperator = operguid;
                subFrom.guidFunc = dates.guid;
                subFrom.authType = 0;//特别禁止
                subFrom.guidApp = $scope.appGuid;//应用Guid
                subFrom.guidFuncgroup = dates.guidFuncgroup;//应用Guid
                //调用查询接口,模拟多选
                var tis = {};
                tis.data = [];
                tis.data.push(subFrom)
                common_service.post(res.removeAcOperatorFunc,tis).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        toastr['success']("移除特别禁止成功");
                        queryfunMap($scope.appGuid,userid);//调用刷新类别方法，传入功能guid才行
                    }
                })
            }
        }
    }




    //未授予功能表格
    $scope.tograntfuncgird = {
        enablePaginationControls: false,
        paginationPageSize: 10,
        multiSelect:false,
        columnDefs: [
            { field: "funcName", displayName:'功能名称'},
            { field: "funcCode", displayName:'功能代码'}
        ]
    };
    var ffunctogra = function(row){
        if(row.isSelected){
            $scope.ogranfunc = row.entity;
        }else{
            delete $scope.ogran;//制空
            $scope.ogranfunc = '';//制空
        }
    }
    $scope.tograntfuncgird.onRegisterApi = function (gridApi) {
        $scope.tograntfuncgird = gridApi;
        $scope.tograntfuncgird.selection.on.rowSelectionChanged($scope,ffunctogra);
    }


    //添加特别允许功能
    var allowfunc = function () {

        $scope.permiss.paginafunc = function () {

            if($scope.ogranfunc == ""){
                toastr['error']("请至少选择一个角色进行添加");
                return false;
            }else{
                var dates = $scope.ogranfunc;
                var subFrom = {};
                subFrom.guidOperator = operguid;
                subFrom.guidFunc = dates.guid;
                subFrom.authType = 1;//特别允许
                subFrom.guidApp = $scope.appGuid;//应用Guid
                subFrom.guidFuncgroup = dates.guidFuncgroup;//应用Guid
                //调用查询接口,模拟多选
                var tis = {};
                tis.data = [];
                tis.data.push(subFrom)
                common_service.post(res.addAcOperatorFunc,tis).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        toastr['success']("添加特别禁止成功");
                        queryfunMap($scope.appGuid,userid);//调用刷新类别方法，传入功能guid才行
                    }
                })
            }
        }
    }

    //特别允许行为表格
    var permissfuncgird = {};
    $scope.permissfuncgird = permissfuncgird;

    $scope.permissfuncgird = {
        enablePaginationControls: false,
        paginationPageSize: 10,
        multiSelect:false,
        columnDefs: [
            { field: "funcName", displayName:'功能名称'},
            { field: "funcCode", displayName:'功能代码'}
        ]
    };
    var ftopermifunc = function(row) {
        if (row.isSelected) {
            $scope.permiesssfunc = row.entity;
        } else {
            delete $scope.permiesssfunc;//制空
            $scope.permiesssfunc = '';//制空
        }
    }
    $scope.permissfuncgird.onRegisterApi = function (gridApi) {
        $scope.permissfuncgird = gridApi;
        $scope.permissfuncgird.selection.on.rowSelectionChanged($scope,ftopermifunc);
    }

    //移除特别允许功能
    var removeallowfunc = function () {
        $scope.permiss.permissfunc = function () {
            if($scope.permiesssfunc == ""){
                toastr['error']("请至少选择一个角色进行移除");
                return false;
            }else{
                var dates = $scope.permiesssfunc;
                var subFrom = {};
                subFrom.guidOperator = operguid;
                subFrom.guidFunc = dates.guid;
                subFrom.authType = 0;//特别禁止
                subFrom.guidApp = $scope.appGuid;//应用Guid
                subFrom.guidFuncgroup = dates.guidFuncgroup;//应用Guid
                //调用查询接口,模拟多选
                var tis = {};
                tis.data = [];
                tis.data.push(subFrom)
                common_service.post(res.removeAcOperatorFunc,tis).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        toastr['success']("移除特别禁止成功");
                        queryfunMap($scope.appGuid,userid);//调用刷新类别方法，传入功能guid才行
                    }
                })
            }
        }
    }







    queryfunMap($scope.appGuid,userid);//查询操作员功能权限
    blackFuncAdd();//添加功能特别禁止方法
    delblackfunc();//调用功能移除禁止方法
    allowfunc();//调用功能特别允许方法
    removeallowfunc();//调用功能特别允许方法





    /*-------------------------------- 操作员角色功能行为代码逻辑--------------------------------*/
    //查询操作员在某功能的行为白名单和黑名单
    var queryfunlist= function (funcGuid,userid) {
        var subFrom = {};
        subFrom.data = {};
        subFrom.data.userId  = userid;
        subFrom.data.funcGuid  = funcGuid;
        common_service.post(res.getOperatorFuncBhvInfo,subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
                //已授予表格
                if(!isNull(datas.auth)){
                    $scope.notrolegird.data =datas.auth ;
                }else{
                    $scope.notrolegird.data =[] ;
                }
                //未授予
                if(!isNull(datas.unauth)){
                    $scope.tograntgird.data = datas.unauth;
                }else{
                    $scope.tograntgird.data =  [];
                }
                //特别禁止
                if(!isNull(datas.forbid)){
                    $scope.alrolegird.data = datas.forbid;
                }else{
                    $scope.alrolegird.data =  [];
                }
                //特别允许
                if(!isNull(datas.permit)){
                    $scope.permissiongird.data = datas.permit;
                }else{
                    $scope.permissiongird.data =  [];
                }

            }
        })
    }


        //已授予行为
        $scope.notrolegird = {
            enablePaginationControls: false,
            paginationPageSize: 10,
            multiSelect:false,
            columnDefs: [
                { field: "bhvName", displayName:'行为名称'},
                { field: "bhvCode", displayName:'行为代码'}
            ]
        };
        var f = function(row){
            if(row.isSelected){
                $scope.notrolRow = row.entity;
            }else{
                $scope.notrolRow = '';//制空
            }
        }

        $scope.notrolegird.onRegisterApi = function (gridApi) {
            $scope.notrolegird = gridApi;
            $scope.notrolegird.selection.on.rowSelectionChanged($scope,f);
        }



    //添加特别禁止行为逻辑
    var blackAdd = function (funtguid) {
        $scope.permiss.add = function () {
            /*var dats = $scope.notrolegird.getSelectedRows();
            var dats = $scope.alrolegird.getSelectedRows();
             if(!dats.length> 0){
                 toastr['error']("请至少选择一个角色");
                 return false;
             }else{
             }*/
            /*多选有问题，会冲突，先单选测试*/
            if($scope.notrolRow == ""){
                toastr['error']("请至少选择一个角色进行添加");
                return false;
            }else{
                var dates = $scope.notrolRow;
                var subFrom = {};
                subFrom.guidOperator = operguid;
                subFrom.guidFuncBhv = dates.guidFuncBhv;
                subFrom.authType = 0;//特别禁止
                //调用查询接口,模拟多选
                var tis = {};
                tis.data = [];
                tis.data.push(subFrom)
                common_service.post(res.addAcOperatorBhv,tis).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        toastr['success']("添加特别禁止成功");
                        queryfunlist(funtguid,userid);//调用刷新类别方法，传入功能guid才行
                    }
                })
            }
        }
    }


    //特别禁止行为表格
    $scope.alrolegird = {
        enablePaginationControls: false,
        paginationPageSize: 10,
        multiSelect:false,
        columnDefs: [
            { field: "bhvName", displayName:'行为名称'},
            { field: "bhvCode", displayName:'行为代码'}
        ]
    };
    var alrol = function(row){
        if(row.isSelected){
            $scope.alrols = row.entity;
        }else{
            $scope.alrols = '';//制空
        }
    }

    $scope.alrolegird.onRegisterApi = function (gridApi) {
        $scope.alrolegird = gridApi;
        $scope.alrolegird.selection.on.rowSelectionChanged($scope,alrol);
    }


    //移除特别禁止行为逻辑
    var delblack = function (funtguid) {
        $scope.permiss.del = function () {
            if($scope.alrols == ""){
                toastr['error']("请至少选择一个角色进行移除");
                return false;
            }else{
                var dates = $scope.alrols;
                var subFrom = {};
                subFrom.guidOperator = operguid;
                subFrom.guidFuncBhv = dates.guidFuncBhv;
                subFrom.authType = 0;//特别禁止
                //调用查询接口,模拟多选
                var tis = {};
                tis.data = [];
                tis.data.push(subFrom)
                common_service.post(res.removeAcOperatorBhv,tis).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        toastr['success']("移除特别禁止成功");
                        queryfunlist(funtguid,userid);//调用刷新类别方法，传入功能guid才行
                    }
                })
            }
        }
    }



    //未授予行为表格
    $scope.tograntgird = {
        enablePaginationControls: false,
        paginationPageSize: 10,
        multiSelect:false,
        columnDefs: [
            { field: "bhvName", displayName:'行为名称'},
            { field: "bhvCode", displayName:'行为代码'}
        ]
    };
    var ftogra = function(row){
        if(row.isSelected){
            $scope.ogran = row.entity;
        }else{
            delete $scope.ogran;//制空
            $scope.ogran = '';//制空
        }
    }
    $scope.tograntgird.onRegisterApi = function (gridApi) {
        $scope.tograntgird = gridApi;
        $scope.tograntgird.selection.on.rowSelectionChanged($scope,ftogra);
    }


    //添加特别允许
    var allow = function (funtguid) {
        $scope.permiss.pagina = function () {
            if($scope.ogran == ""){
                toastr['error']("请至少选择一个角色进行添加");
                return false;
            }else{
                var dates = $scope.ogran;
                var subFrom = {};
                subFrom.guidOperator = operguid;
                subFrom.guidFuncBhv = dates.guidFuncBhv;
                subFrom.authType = 1;//特别允许
                //调用查询接口,模拟多选
                var tis = {};
                tis.data = [];
                tis.data.push(subFrom)
                common_service.post(res.addAcOperatorBhv,tis).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        toastr['success']("添加特别禁止成功");
                        queryfunlist(funtguid,userid);//调用刷新类别方法，传入功能guid才行
                    }
                })
            }
        }
    }

    //特别允许行为表格
    var permissiongird = {};
    $scope.permissiongird = permissiongird;
    var tossi = [
        { field: "bhvName", displayName:'行为名称'},
        { field: "bhvCode", displayName:'行为代码'}
    ];
    var ftopermi = function(row){
        if(row.isSelected){
            $scope.permiesss = row.entity;
        }else{
            delete $scope.permiesss;//制空
            $scope.permiesss = '';//制空
        }
    }
    $scope.permissiongird = initgrid($scope,permissiongird,filterFilter,tossi,true,ftopermi);

    $scope.permissiongird = {
        enablePaginationControls: false,
        paginationPageSize: 10,
        multiSelect:false,
        columnDefs: [
            { field: "bhvName", displayName:'行为名称'},
            { field: "bhvCode", displayName:'行为代码'}
        ]
    };
    var ftopermi = function(row) {
        if (row.isSelected) {
            $scope.permiesss = row.entity;
        } else {
            delete $scope.permiesss;//制空
            $scope.permiesss = '';//制空
        }
    }
    $scope.permissiongird.onRegisterApi = function (gridApi) {
        $scope.permissiongird = gridApi;
        $scope.permissiongird.selection.on.rowSelectionChanged($scope,ftopermi);
    }

    //移除特别允许行为
    var removeallow = function (funtguid) {
        $scope.permiss.permiss = function () {
            if($scope.permiesss == ""){
                toastr['error']("请至少选择一个角色进行移除");
                return false;
            }else{
                var dates = $scope.permiesss;
                var subFrom = {};
                subFrom.guidOperator = operguid;
                subFrom.guidFuncBhv = dates.guidFuncBhv;
                subFrom.authType = 1;//特别允许
                //调用查询接口,模拟多选
                var tis = {};
                tis.data = [];
                tis.data.push(subFrom)
                common_service.post(res.removeAcOperatorBhv,tis).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        toastr['success']("移除特别禁止成功");
                        queryfunlist(funtguid,userid);//调用刷新类别方法，传入功能guid才行
                    }
                })
            }
        }
    }



});



















