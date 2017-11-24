/**
 * Created by wangbo on 2017/6/11.
 */
angular.module('MetronicApp').controller('role_controller', function($scope ,$rootScope,$state,$modal,$http,abftree_service,$filter,$timeout,dictonary_service,common_service,i18nService,role_service,menu_service,operator_service,filterFilter,$uibModal,uiGridConstants) {
        var role = {};
        $scope.role = role;
        var res = $rootScope.res.abftree_service;//页面所需调用的服务
        /* 左侧角色查询逻辑 */
        i18nService.setCurrentLang("zh-cn");
    //查询应用
    var subFrom  = {};
    var headers ='FUN0001'
    menu_service.queryAllAcApp(subFrom,headers).then(function(data){
        if(data.status == "success"){
            var datas = data.retMessage;
           role.Appall = datas;//所有应用数据，最终要在弹窗中渲染
        }else{
            toastr['error']('初始化查询失败'+'<br/>'+data.retMessage);
        }
    })

    var gridOptions = {};
        $scope.gridOptions = gridOptions;
        var com = [{ field: 'roleCode', displayName: '角色代码'},
            { field: "roleName", displayName:'角色名称'},
            { field: "roleType", displayName:'角色类别',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.roleType | translateConstants :\'DICT_AC_ROLETYPE\') + $root.constant[\'DICT_AC_ROLETYPE-\'+row.entity.roleType]}}</div>',
                //配置搜索下拉框
                filter:{
                    //term: '0',//默认搜索那项
                    type: uiGridConstants.filter.SELECT,
                    selectOptions: [{ value: 'sys', label: '系统级'}, { value: 'app', label: '应用级' }]
                }},
            { field: "appName", displayName:'隶属应用'
              /*  filter:{
                    //term: '0',//默认搜索那项
                    type: uiGridConstants.filter.SELECT,
                    //如果非常多，直接自己拼接，所有的数组，循环，拼接成这个样子就可以了。
                    selectOptions:  [{ value: 'ABF', label: 'ABF'}, { value: 'TWS', label: '柜面系统' }]
                }*/
            }
        ];
        var f = function(row){
            if(row.isSelected){
                $scope.selectRow = row.entity;
                $scope.rolePer = true;//角色分配权限按钮显示
            }else{
                delete $scope.selectRow;//制空
                $scope.rolePer = false;//角色分配权限按钮隐藏
            }
        }
        $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);
    //查询角色列表
    role.inint = function(items){
        var subFrom = {};
        role_service.queryRoleList(subFrom).then(function(data){
                var datas = $filter('Arraysort')(data.retMessage);//调用管道排序
                if(data.status == "success"){
                    $scope.gridOptions.data =  datas;
                    $scope.gridOptions.mydefalutData = datas;
                    $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
                    $scope.rolePer = false;//角色分配权限按钮隐藏
                }else{
                    toastr['error']('初始化查询失败'+'<br/>'+data.retMessage);
                }
        })
    }
    role.inint();
    //新增角色逻辑
    $scope.role_add = function(){
            openwindow($modal, 'views/roleManage/rolemanageAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.roleList = role.Appall;//循环渲染，在弹窗中
                    $scope.add = function(item){
                        var subFrom = {};
                        subFrom = item;
                        console.log(item)
                        role_service.createRole(subFrom).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("新增成功！");
                                role.inint(subFrom);//把新增的数据传过去
                                $modalInstance.close();
                            }else{
                                toastr['error']('初始化查询失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };

                })
        }
    //修改角色逻辑
    $scope.role_edit = function(id){
           if($scope.selectRow){
               var items = $scope.selectRow;
               openwindow($modal, 'views/roleManage/rolemanageAdd.html', 'lg',//弹出页面
                   function ($scope, $modalInstance) {
                       $scope.roleList = role.Appall;//循环渲染，在弹窗中
                       $scope.roleFrom = items;
                       $scope.id = id;
                       $scope.add = function(item){
                           var subFrom = {};
                           subFrom = item;
                           subFrom.guid = items.guid;
                           role_service.editRole(subFrom).then(function(data){
                               if(data.status == "success"){
                                   toastr['success']("修改成功！");
                                   $modalInstance.close();
                                   role.inint();
                                   role.rofault(items.guid);//刷新组织关系列表
                                   queryOeper(items.guid);//刷新操作员列表
                               }else{
                                   toastr['error']('修改失败'+'<br/>'+data.retMessage);
                               }
                           })
                       }
                       $scope.cancel = function () {
                           $modalInstance.dismiss('cancel');
                       };
                   })
           }else{
               toastr['error']("请至少选择一条数据进行修改！");
           }
        }
    //删除角色逻辑
    $scope.role_delete = function(){
            if($scope.selectRow){
                var itemguid  = $scope.selectRow.guid;
                if(confirm("您确认要删除选中的角色吗,删除角色将同时删除角色的功能分配信息以及角色在操作员和组织对象上的分配")){
                    var subFrom = {};
                    subFrom.roleGuid = itemguid;
                    role_service.deleteRole(subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']("删除成功！");
                            role.inint();//刷新列表
                            $scope.role.shows = false;
                        }else{
                            toastr['error']('初始化查询失败'+'<br/>'+data.retMessage);
                        }
                    })
                }
            }else{
                toastr['error']("请至少选择一条数据进行删除！");
            }
        }

    //查看角色历史
    $scope.role_history = function () {
        var dats = $scope.gridOptions.getSelectedRows();//拿到选择角色的数据
        if($scope.selectRow){
            var item = dats[0].guid;
            $state.go("loghistory",{id:item});//跳转新页面
        }else{
            toastr['error']("请至少选择一条数据进行查看！");
        }

    }

    //角色权限配置页面
    $scope.role_Permission = function () {
        var dats = $scope.gridOptions.getSelectedRows();//拿到选择角色的数据
        var jsonObj= angular.toJson(dats[0]);//利用 state.go进行页面跳转的话，如果要传对象，必须转成json格式传入
        $state.go("rolePermission",{'id':jsonObj})
    }

});


//角色功能权限页面

angular.module('MetronicApp').controller('rolePermission_controller', function($scope ,$rootScope,$state,$stateParams,$modal,$timeout,$http,common_service,i18nService,role_service,menu_service,operator_service,filterFilter,$uibModal,uiGridConstants) {
    var role = {};
    $scope.role = role;
    var res = $rootScope.res.abftree_service;//页面所需调用的服务
    var gridDate = angular.fromJson($stateParams.id);//因为页面跳转传入的是转换过的json对象,因此
    //返回方法
    $scope.myback = function(){
        window.history.back(-1);
    }

    /* 左侧角色查询逻辑 */
    i18nService.setCurrentLang("zh-cn");
    //组织类别
    var  DICT_AC_PARTYTYPE   = ['organization','workgroup','position','duty'];

    //查询对应功能权限
    function queryBhvs(item){
        var res = $rootScope.res.role_service;
        var subFrom = {};
        subFrom.data = {};
        subFrom.data.roleGuid =gridDate.guid;//角色guid
        subFrom.data.funcGuid =item.guid;
        common_service.post(res.queryAcRoleBhvsByFuncGuid,subFrom).then(function(data){
            if(data.status == 'success'){
                var datas = data.retMessage;
                $scope.gridFuncedit.data =  datas;
                $scope.gridFuncedit.mydefalutData = datas;
                $scope.gridFuncedit.getPage(1,$scope.gridFuncedit.paginationPageSize);
            }else{
                toastr['error']('查询失败'+'<br/>'+data.retMessage);
            }
        })
    }

    //功能对应行为列表
    var gridFuncedit = {};
    $scope.gridFuncedit = gridOptions2;
    var comFuncedit =[{ field: 'bhvName', displayName: '行为名称'},
        { field: "bhvCode", displayName:'行为代码'},
    ];
    var fFuncedit = function(row){
        if(row.isSelected){
            $scope.selectRow2 = row.entity;
        }else{
            delete $scope.selectRow2;//制空
        }
    }
    $scope.gridFuncedit = initgrid($scope,gridFuncedit,filterFilter,comFuncedit,true,fFuncedit);

    // /公共创建树方法
    function creaJstree(){
    //创建树
        var control=function(id,arrs){
            $('#container').jstree('destroy',false);//删除重新加载，只删除数据。
            $("#container").jstree({
                "core" : {
                    "themes" : {
                        "responsive": false
                    },
                    'data' : function (obj, callback) {
                        var jsonarray = [];
                        $scope.jsonarray = jsonarray;
                        var subFrom = {};
                        if(obj.id == '#'){
                            subFrom.id = id;
                        }else{
                            subFrom.id = obj.id;
                        }
                        var items = {};
                        items.roleGuid = arrs.guid
                        role_service.queryRoleFunc(items).then(function(data){
                            var alldatas = data.retMessage;//拿到角色跟功能绑定的所有guid数组
                            var chenageDatas = []
                            for(var i = 0; i < alldatas.length;i++){
                                chenageDatas.push(alldatas[i].guidFunc);
                            }
                            role_service.appQuery(subFrom).then(function (res){
                                if(res.status == "success"){
                                    var datas = res.retMessage;
                                    var dataes =datas.data;//整体的树结构数组
                                    var type = datas.type;//类型
                                    var  its  =  [];
                                    creatJstree(dataes,type,its,chenageDatas);//调用创建树结构
                                    $scope.jsonarray = angular.copy(its);
                                    callback.call(this, $scope.jsonarray);
                                    $timeout(function(){
                                        $('#container').jstree().open_all();
                                    },80)
                                }
                            })
                        })

                    },
                },
                "force_text": true,
                plugins: ["sort", "types", "wholerow", "sort","themes", "html_data","search"],
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
                    },
                    'is_draggable':function (node) {
                        return true;
                    }
                },
                'search':{
                    show_only_matches:true,
                },
                'sort': function (a, b) {
                    //排序插件，会两者比较，获取到节点的order属性，插件会自动两两比较。
                    return this.get_node(a).original.displayOrder > this.get_node(b).original.displayOrder ? 1 : -1;
                },
                'callback' : {
                    move_node:function (node) {
                    }
                },
            })
        }
        //封装创建树结构函数
        function creatJstree(dataes,type,its,alldatas) {
            if(type=='root'){
                //root加载
                for(var i = 0 ;i <dataes.length;i++){
                    dataes[i].text = dataes[i].rootName;
                    dataes[i].children = true;
                    dataes[i].id = dataes[i].rootCode;
                    dataes[i].icon = "fa fa-home icon-state-info icon-lg";
                    dataes[i].check_node =true;
                    its.push(dataes[i])
                }

            }else if(type =="app"){
                for(var i = 0 ;i <dataes.length;i++){
                    dataes[i].text = dataes[i].funcgroupName;
                    dataes[i].children = true;
                    dataes[i].id = dataes[i].guid;
                    dataes[i].icon = "fa fa-th-large icon-state-info icon-lg";
                    its.push(dataes[i])
                }
            }else if(type =="group"){
                if(!isNull(dataes.groupList)){
                    for(var i = 0 ;i <dataes.groupList.length;i++){
                        dataes.groupList[i].text = dataes.groupList[i].funcgroupName;
                        dataes.groupList[i].children = true;
                        dataes.groupList[i].id = dataes.groupList[i].guid;
                        dataes.groupList[i].icon = "fa  fa-th-list  icon-state-info icon-lg"
                        its.push(dataes.groupList[i])
                    }
                }
                if(!isNull(dataes.funcList)){
                    console.log(dataes.funcList)
                        for (var i = 0; i < dataes.funcList.length; i++) {
                            if(alldatas.indexOf(dataes.funcList[i].guid) != -1){
                                dataes.funcList[i].text = dataes.funcList[i].funcName;
                                dataes.funcList[i].children = false;
                                dataes.funcList[i].id = dataes.funcList[i].guid;
                                dataes.funcList[i].icon = "fa fa-wrench icon-state-info icon-lg"
                                its.push(dataes.funcList[i])
                            }
                        }
                }
            }
        }
        control('#'+ gridDate.guidApp,gridDate); //调用树结构函数
        $('#container').on("changed.jstree", function (e, data){
            if(typeof data.node !== 'undefined'){//拿到结点详情
                if(!isNull(data.node.original.funcCode)){
                    $scope.role.item = data.node.original;//全局点击数据传递
                    queryBhvs(data.node.original);//调用查询按钮
                    $scope.funcedit = true;
                }else{
                    $scope.funcedit = false;//把表格消失
                }
                $scope.$apply();
            }
        });
    }
    creaJstree()//调用创建树

    //修改配置
    $scope.role.edidConfig = function () {
        $scope.edit = true;
        $scope.funcedit = false;//让列表消失
        //干掉原来的树，让新的树显示
        var control=function(id,arrs){
            $('#container').jstree('destroy',false);//删除重新加载，只删除数据。
            $("#container").jstree({
                "core" : {
                    "themes" : {
                        "responsive": false
                    },
                    "check_callback" : true,
                    'data' : function (obj, callback) {
                        var jsonarray = [];
                        $scope.jsonarray = jsonarray;
                        var subFrom = {};
                        if(obj.id == '#'){
                            subFrom.id = id;
                        }else{
                            subFrom.id = obj.id;
                        }
                        role_service.appQuery(subFrom).then(function (res) {
                            var datas = res.retMessage;
                            var dataes = datas.data;
                            var type = datas.type;
                            var  its  =  [];
                            creatJstree(datas,dataes,type,its);//调用创建树结构
                            $scope.jsonarray = angular.copy(its);
                            callback.call(this, $scope.jsonarray);
                            var items = {};
                            items.roleGuid = arrs.guid
                            role_service.queryRoleFunc(items).then(function(data){
                                //获取表中所有角色功能
                                var alldatas = data.retMessage;
                                if(data.status == "success"){
                                    if(alldatas.length!==0){
                                        $timeout(function(){
                                            for(var i = 0; i<alldatas.length;i++){
                                                $('#container').jstree(true).check_node(alldatas[i].guidFunc);//选中
                                            }
                                        },50)
                                    }
                                    $timeout(function(){
                                        $('#container').jstree().open_all();
                                    },30)
                                }else{

                                }
                            })
                        })
                    },
                },
                "force_text": true,
                plugins: ["sort", "types", "checkbox", "wholerow", "sort","themes", "html_data","search"],
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
                    },
                    'is_draggable':function (node) {
                        return true;
                    }
                },
                'search':{
                    show_only_matches:true,
                },
                'sort': function (a, b) {
                    //排序插件，会两者比较，获取到节点的order属性，插件会自动两两比较。
                    return this.get_node(a).original.displayOrder > this.get_node(b).original.displayOrder ? 1 : -1;
                },
                'callback' : {
                    move_node:function (node) {
                    }
                },
            }).bind("copy.jstree", function (node,e, data ) {
            })
        }
        //封装创建树结构函数
        function creatJstree(datas,dataes,type,its) {
            if(type=='root'){
                //root加载
                for(var i = 0 ;i <dataes.length;i++){
                    dataes[i].text = dataes[i].rootName;
                    dataes[i].children = true;
                    dataes[i].id = dataes[i].rootCode;
                    dataes[i].icon = "fa fa-home icon-state-info icon-lg";
                    dataes[i].check_node =true;
                    its.push(dataes[i])
                }
            }else if(type =="app"){
                for(var i = 0 ;i <dataes.length;i++){
                    dataes[i].text = dataes[i].funcgroupName;
                    dataes[i].children = true;
                    dataes[i].id = dataes[i].guid;
                    dataes[i].icon = "fa fa-th-large icon-state-info icon-lg";
                    its.push(dataes[i])
                }
            }else if(type =="group"){
                if(!isNull(dataes.groupList)){
                    for(var i = 0 ;i <dataes.groupList.length;i++){
                        dataes.groupList[i].text = dataes.groupList[i].funcgroupName;
                        dataes.groupList[i].children = true;
                        dataes.groupList[i].id = dataes.groupList[i].guid;
                        dataes.groupList[i].icon = "fa  fa-th-list  icon-state-info icon-lg"
                        its.push(dataes.groupList[i])
                    }
                    if(!isNull(dataes.funcList)){
                        for(var i = 0 ;i <dataes.funcList.length;i++){
                            dataes.funcList[i].text = dataes.funcList[i].funcName;
                            dataes.funcList[i].children = false;
                            dataes.funcList[i].id = dataes.funcList[i].guid;
                            dataes.funcList[i].icon = "fa fa-wrench icon-state-info icon-lg"
                            its.push(dataes.funcList[i])
                        }
                    }
                }else{
                    if(!isNull(dataes.funcList)){
                        var allFunc = [];
                        $scope.allFunc = allFunc;
                        for(var i = 0 ;i <dataes.funcList.length;i++){
                            allFunc.push(dataes.funcList[i])
                            dataes.funcList[i].text = dataes.funcList[i].funcName;
                            dataes.funcList[i].children = false;
                            dataes.funcList[i].id = dataes.funcList[i].guid;
                            dataes.funcList[i].icon = "fa fa-wrench icon-state-info icon-lg"
                            its.push(dataes.funcList[i])
                        }
                    }
                }
            }
        }
        control('#'+ gridDate.guidApp,gridDate); //调用树结构函数
    }
    //点击保存权限分配
    $scope.role.checkAll = function(){
        var nodes=$("#container").jstree("get_checked");//获取所有选中的节点
        if(nodes.length>=0 ) {
            var subFrom = {};
            subFrom.roleGuid = gridDate.guid;
            subFrom.funcList = [];
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].indexOf('FUNC') == 0 && nodes[i].indexOf('FUNCGROUP') !== 0) {
                    var item = {};
                    item.guidFunc = nodes[i];
                    item.guidFuncgroup = $("#container").jstree().get_node(nodes[i]).parent;
                    item.guidApp = gridDate.guidApp;
                    subFrom.funcList.push(item);
                }
            }
            role_service.configRoleFunc({data:subFrom}).then(function (data) {
                var datas = data.retMessage;
                if (data.status == "success") {
                    toastr['success']('保存权限成功');
                    creaJstree();//调用创建树结构
                    $scope.edit = false;//保存按钮隐藏
                } else {
                    toastr['error']('保存权限失败' + '<br/>' + data.retMessage);
                }
            })
        }
    }



    //新增功能行为方法
    $scope.role.funclist = function(){
        var funcGuid = $scope.role.item;//点击值guid
        openwindow($modal, 'views/roleManage/roleFunc.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                var gridOptions = {};
                $scope.gridOptions = gridOptions;
                var com = [{ field: 'bhvName', displayName: '行为名称'},
                    { field: "bhvCode", displayName:'行为代码'},
                ];
                //自定义点击事件
                var f1 = function(row){
                    if(row.isSelected){
                        $scope.selectRow = row.entity;
                    }else{
                        delete $scope.selectRow;//制空
                    }
                }
                $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f1);
                var queryfunc  = $rootScope.res.application_service;
                var subFrom ={};
                subFrom.funcGuid = funcGuid.guid;
                common_service.post(queryfunc.queryAllBhvDefForFunc,subFrom).then(function(data){
                    if(data.status == "success"){
                        var datas  = data.retMessage;
                        $scope.gridOptions.data =  datas;
                        $scope.gridOptions.mydefalutData = datas;
                        $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
                    }else{
                        var datas  = [];
                        $scope.gridOptions.data =  datas;
                        $scope.gridOptions.mydefalutData = datas;
                        $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
                    }
                })
                //导入方法
                $scope.importAdd = function () {
                    var dats = $scope.gridOptions.getSelectedRows();
                    if(dats.length >0){
                        var tis = {};
                        tis.data = [];
                        for(var i =0; i<dats.length; i++){
                            var subFrom = {};
                            subFrom.guidRole = gridDate.guid;//角色guid
                            subFrom.guidFuncBhv=dats[i].guidFuncBhv;
                            subFrom.guidApp = gridDate.guidApp;
                            tis.data.push(subFrom)
                        }
                        var res = $rootScope.res.role_service;
                        common_service.post(res.addAcRoleBhvs,tis).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("导入成功！");
                                $modalInstance.close();
                                queryBhvs(funcGuid);//重新查询列表
                            }else{
                                toastr['error']('导入失败'+'<br/>'+data.retMessage);
                            }
                        })

                    }else{
                        toastr['error']("请至少选中一个！");
                    }
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }
    $scope.role_Deletefunc = function(){
        var dats = $scope.gridFuncedit.getSelectedRows();
        var funcGuid = $scope.role.item;//点击值guid
        if(dats.length>0){
            if(confirm('确定要删除行为类型吗?')){
                var tis = {};
                tis.data = [];
                for(var i =0;i<dats.length;i++){
                    var subFrom = {};
                    subFrom.guidRole = gridDate.guid;//角色guid
                    subFrom.guidFuncBhv= dats[i].guidFuncBhv;
                    subFrom.guidApp = gridDate.guidApp;
                    tis.data.push(subFrom);
                }
                var res = $rootScope.res.role_service;
                common_service.post(res.removeAcRoleBhvs,tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除对应行为成功！");
                        queryBhvs(funcGuid);//重新查询列表
                    }else{
                        toastr['error']('删除对应行为失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }else{
            toastr['error']("请最少选中一条进行删除！");
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

    //查询所有组织权限
    var queryParrty=function(id,ars){
        var  subFrom = {};
        subFrom.roleGuid = id;
        subFrom.partyType = ars;
        role_service.queryRoleInParty(subFrom).then(function(data){
            if(data.status == "success"){
                var datas = data.retMessage;
                tabshow(datas,ars)
            }else{
                toastr['error']('初始化查询失败'+'<br/>'+data.retMessage);
            }
        })
    }
    //控制tab列表显示
    var tabshow = function(datas,ars){
        if(ars =='organization'){
            $scope.gridOptions2.data =  datas;
            $scope.gridOptions2.mydefalutData = datas;
            $scope.gridOptions2.getPage(1,$scope.gridOptions2.paginationPageSize);
        }else if(ars =='workgroup'){
            $scope.gridOptions3.data =  datas;
            $scope.gridOptions3.mydefalutData = datas;
            $scope.gridOptions3.getPage(1,$scope.gridOptions3.paginationPageSize);
        }else if(ars == 'position'){
            $scope.gridOptions4.data =  datas;
            $scope.gridOptions4.mydefalutData = datas;
            $scope.gridOptions4.getPage(1,$scope.gridOptions4.paginationPageSize);
        }else if(ars == 'duty'){
            $scope.gridOptionzw.data =  datas;
            $scope.gridOptionzw.mydefalutData = datas;
            $scope.gridOptionzw.getPage(1,$scope.gridOptionzw.paginationPageSize);
        }

    }

    //切换角色刷新调用方法
    role.rofault = function(id){
        queryParrty(id,DICT_AC_PARTYTYPE[0]);//重新查询组织信息
        queryParrty(id,DICT_AC_PARTYTYPE[1]);//重新查询工作组信息
        queryParrty(id,DICT_AC_PARTYTYPE[2]);//重新查询岗位信息
        queryParrty(id,DICT_AC_PARTYTYPE[3]);//重新查询职务信息
        queryOeper(id)//查询操作员列表
    }



    //控制页切换代码
    role.loadgwdata = function (type) {
        role.guid = gridDate.guid;
        if(type == 0){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.roleflag.limit = true;
            $scope.roleflag.dist = false;
            $scope.roleflag.entity = false;
            $scope.roleflag.operatorer = false;
        }else if (type == 1){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
                $scope.rolesflag.zhiwei = true;//初始化打开职务
            }
            $scope.roleflag.limit = false;
            $scope.roleflag.dist = true;
            $scope.roleflag.operatorer = false;
            $scope.roleflag.entity = false;
            queryParrty(role.guid,DICT_AC_PARTYTYPE[3]);//重新查询职务信息
        }else if (type == 3){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.rolesflag.org = true;
            queryParrty(role.guid,DICT_AC_PARTYTYPE[0]);
        }else if (type == 4){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.rolesflag.worklist = true;
            queryParrty(role.guid,DICT_AC_PARTYTYPE[1]);
        }else if (type == 5){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.rolesflag.post = true;
            queryParrty(role.guid,DICT_AC_PARTYTYPE[2]);
        }else if (type == 6){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.rolesflag.zhiwei = true;
            queryParrty(role.guid,DICT_AC_PARTYTYPE[3]);
        }else if (type == 7){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;
            }
            $scope.roleflag.operatorer = true;
            $scope.roleflag.limit = false;
            $scope.roleflag.dist = false;
            $scope.roleflag.entity = false;
            queryOeper(role.guid);
        }else if(type == 'entity'){
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;//每一项全部不显示
            }
            $scope.roleflag.operatorer = false;
            $scope.roleflag.limit = false;
            $scope.roleflag.dist = false;
            $scope.roleflag.entity = true;//页面展示
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

    var gridOptions2 = {};
    $scope.gridOptions2 = gridOptions2;
    var com2 = [{ field: 'roleName', displayName: '角色名称'},
        { field: "partyName", displayName:'所属机构'}
    ];
    var f2 = function(row){
        if(row.isSelected){
            $scope.selectRow2 = row.entity;
        }else{
            delete $scope.selectRow2;//制空
        }
    }
    $scope.gridOptions2 = initgrid($scope,gridOptions2,filterFilter,com2,true,f2);
    $scope.gridOptions2.data = $scope.orgData;
    //新增组织方法
    $scope.role.orgAdd = function(){
        openwindow($modal, 'views/roleManage/roleAddorg.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                var gridOptions = {};
                $scope.gridOptions = gridOptions;
                var com = [
                    { field: "orgName", displayName:'组织名称'}
                ];
                //自定义点击事件
                var f1 = function(row){
                    if(row.isSelected){
                        $scope.selectRow = row.entity;
                    }else{
                        delete $scope.selectRow;//制空
                    }
                }
                $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f1);
                var subFrom = {};
                common_service.post(res.queryAllorg,subFrom).then(function(data){
                    if(data.status == "success"){
                        var datas  = data.retMessage;
                        $scope.gridOptions.data = datas;
                    }
                })
                //导入方法
                $scope.importAdd = function () {
                    var dats = $scope.gridOptions.getSelectedRows();
                    if(dats.length >0){
                        var tis = {};
                        tis.data = [];
                        for(var i =0; i<dats.length; i++){
                            var subFrom = {};
                            subFrom.guidRole = gridDate.guid;
                            subFrom.partyType=DICT_AC_PARTYTYPE[0];
                            subFrom.guidParty =dats[i].guid;
                            tis.data.push(subFrom)
                        }
                        role_service.addPartyRole(tis).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("导入成功！");
                                $modalInstance.close();
                                queryParrty(gridDate.guid,DICT_AC_PARTYTYPE[0]);//重新查询
                            }else{
                                toastr['error']('导入失败'+'<br/>'+data.retMessage);
                            }
                        })

                    }else{
                        toastr['error']("请至少选中一个！");
                    }
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }
    //删除tab组织方法
    $scope.role_orgDelete = function(){
        var dats = $scope.gridOptions2.getSelectedRows();
        if(dats.length>0){
            if(confirm('确定要删除对应组织吗')){
                var tis = {};
                tis.data = [];
                for(var i =0;i<dats.length;i++){
                    var subFrom = {};
                    subFrom.guidRole =gridDate.guid;
                    subFrom.guidParty =dats[i].guidParty;
                    subFrom.partyType=DICT_AC_PARTYTYPE[0];
                    tis.data.push(subFrom);
                }
                role_service.removePartyRole(tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除组织成功！");
                        queryParrty(gridDate.guid,DICT_AC_PARTYTYPE[0]);//重新查询组织信息
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }else{
            toastr['error']("请最少选中一条进行删除！");
        }
    }
    /* tab 下对应工作组管理详情*/
    var gridOptions3 = {};
    $scope.gridOptions3 = gridOptions3;
    var com3 = [{ field: 'roleName', displayName: '角色名称'},
        { field: "partyName", displayName:'所属工作组'}
    ];
    var f3 = function(row){
        if(row.isSelected){
            $scope.selectRow3 = row.entity;
        }else{
            delete $scope.selectRow3;//制空
        }
    }
    $scope.gridOptions3 = initgrid($scope,gridOptions3,filterFilter,com3,true,f3);
    $scope.gridOptions3.data = $scope.workData;
    //新增tab下功能组方法
    $scope.role.workAdd = function(){
        openwindow($modal, 'views/roleManage/roleAddwork.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                var gridOptions = {};
                $scope.gridOptions = gridOptions;
                var com = [
                    { field: "groupName", displayName:'工作组名称'}
                ];

                //自定义点击事件
                var f1 = function(row){
                    if(row.isSelected){
                        $scope.selectRow3 = row.entity;
                    }
                    else{
                        delete $scope.selectRow3;//制空
                    }
                }
                $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f1);
                //查询所有工作组
                var res = $rootScope.res.Workgroup_service;//查询工作组所需服务
                var subFrom = {};
                common_service.post(res.queryAllGroup,subFrom).then(function(data){
                    if(data.status == "success"){
                        var datas  = data.retMessage;
                        $scope.gridOptions.data = datas;
                    }
                })
                $scope.importAdd = function () {
                    var dats = $scope.gridOptions.getSelectedRows();
                    if (dats.length > 0) {
                        var tis = {};
                        tis.data = [];
                        for(var i =0; i<dats.length; i++){
                            var subFrom = {};
                            subFrom.guidRole = gridDate.guid;
                            subFrom.partyType=DICT_AC_PARTYTYPE[1];
                            subFrom.guidParty =dats[i].guid;
                            tis.data.push(subFrom)
                        }
                        role_service.addPartyRole(tis).then(function(data){
                            var  datas = data.retMessage;
                            if(data.status == "success"){
                                toastr['success']("导入成功！");
                                $modalInstance.close();
                                queryParrty(gridDate.guid,DICT_AC_PARTYTYPE[1]);//重新查询工作组
                            }else{
                                toastr['error']('导入失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    else {
                        toastr['error']("请至少选中一个！");
                    }
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }

    //删除tab工作组方法
    $scope.role_workDelete = function(){
        var dats = $scope.gridOptions3.getSelectedRows();
        if(dats.length>0){
            if(confirm('确定要删除该工作组吗')){
                var tis = {};
                tis.data = [];
                for(var i =0;i<dats.length;i++){
                    var subFrom = {};
                    subFrom.guidRole =gridDate.guid;
                    subFrom.guidParty =dats[i].guidParty;
                    subFrom.partyType=DICT_AC_PARTYTYPE[1];
                    tis.data.push(subFrom);
                }
                role_service.removePartyRole(tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除组织成功！");
                        queryParrty(gridDate.guid,DICT_AC_PARTYTYPE[1]);//重新查询组织信息
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }else{
            toastr['error']("请最少选中一条进行删除！");
        }
    }


    /*角色与岗位关系*/
    var gridOptions4 = {};
    $scope.gridOptions4 = gridOptions4;
    var com4 = [{ field: 'roleName', displayName: '角色名称'},
        { field: "partyName", displayName:'岗位名称'}
    ];
    var f4 = function(row){
        if(row.isSelected){
            $scope.selectRow4 = row.entity;
        }else{
            delete $scope.selectRow4;//制空
        }
    }
    $scope.gridOptions4 = initgrid($scope,gridOptions3,filterFilter,com4,true,f4);
    $scope.gridOptions4.data = $scope.postData;
    $scope.role.postAdd = function(){
        openwindow($modal, 'views/roleManage/roleAddpost.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                var gridOptions = {};
                $scope.gridOptions = gridOptions;
                var com = [
                    { field: "positionName", displayName:'岗位名称'}
                ];
                //自定义点击事件
                var f1 = function(row){
                    if(row.isSelected){
                        $scope.selectRow3 = row.entity;
                    }
                    else{
                        delete $scope.selectRow3;//制空
                    }
                }
                $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f1);
                //查询所有岗位
                var subFrom = {};
                common_service.post(res.queryAllposition,subFrom).then(function(data){
                    if(data.status == "success"){
                        var datas  = data.retMessage;
                        $scope.gridOptions.data = datas;
                    }
                })
                $scope.importAdd = function () {
                    var dats = $scope.gridOptions.getSelectedRows();
                    if (dats.length > 0) {
                        var tis = {};
                        tis.data = [];
                        for(var i =0; i<dats.length; i++){
                            var subFrom = {};
                            subFrom.guidRole = gridDate.guid;
                            subFrom.partyType=DICT_AC_PARTYTYPE[2];
                            subFrom.guidParty =dats[i].guid;
                            tis.data.push(subFrom)
                        }
                        role_service.addPartyRole(tis).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("导入成功！");
                                $modalInstance.close();
                                queryParrty(gridDate.guid,DICT_AC_PARTYTYPE[2]);//重新查询
                            }else{
                                toastr['error']('导入失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    else {
                        toastr['error']("请至少选中一个！");
                    }
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }

    //删除tab岗位方法
    $scope.role_postDelete = function(){
        var dats = $scope.gridOptions4.getSelectedRows();
        if(dats.length>0){
            if(confirm('确定要删除对应岗位吗')){
                var tis = {};
                tis.data = [];
                for(var i =0;i<dats.length;i++){
                    var subFrom = {};
                    subFrom.guidRole =gridDate.guid;
                    subFrom.guidParty =dats[i].guidParty;
                    subFrom.partyType=DICT_AC_PARTYTYPE[2];
                    tis.data.push(subFrom);
                }
                role_service.removePartyRole(tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除组织成功！");
                        queryParrty(gridDate.guid,DICT_AC_PARTYTYPE[2]);//重新查询组织信息
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }else{
            toastr['error']("请最少选中一条进行删除！");
        }
    }

    //职务内容列表
    var gridOptionzw = {};
    $scope.gridOptionzw = gridOptionzw;
    var comzw = [{ field: 'roleName', displayName: '角色名称'},
        { field: "partyName", displayName:'职务名称'}
    ];
    var fzw = function(row){
        if(row.isSelected){
            $scope.selectRowzw = row.entity;
        }else{
            delete $scope.selectRowzw;//制空
        }
    }
    $scope.gridOptionzw = initgrid($scope,gridOptionzw,filterFilter,comzw,true,fzw);
    $scope.gridOptionzw.data = $scope.zwDate;
    //新增tab下职务方法
    $scope.role.zhiwuAdd = function(){
        openwindow($modal, 'views/roleManage/roleAddzw.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                var gridOptions = {};
                $scope.gridOptions = gridOptions;
                var com = [
                    { field: "dutyName", displayName:'职位名称'}
                ];
                //自定义点击事件
                var f1 = function(row){
                    if(row.isSelected){
                        $scope.selectRow3 = row.entity;
                    }
                    else{
                        delete $scope.selectRow3;//制空
                    }
                }
                $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f1);
                /*查询所有职务*/
                var subFrom = {};
                var res = $rootScope.res.duty_service;//职务所需调用服务
                common_service.post(res.queryDucyList,subFrom).then(function(data){
                    if(data.status == "success"){
                        var datas  = data.retMessage;
                        $scope.gridOptions.data = datas;

                    }
                })
                $scope.importAdd = function () {
                    var dats = $scope.gridOptions.getSelectedRows();
                    if (dats.length > 0) {
                        var tis = {};
                        tis.data = [];
                        for(var i =0; i<dats.length; i++){
                            var subFrom = {};
                            subFrom.guidRole = gridDate.guid;
                            subFrom.partyType=DICT_AC_PARTYTYPE[3];
                            subFrom.guidParty =dats[i].guid;
                            tis.data.push(subFrom)
                        }
                        role_service.addPartyRole(tis).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("导入成功！");
                                $modalInstance.close();
                                queryParrty(gridDate.guid,DICT_AC_PARTYTYPE[3]);//重新查询
                            }else{
                                toastr['error']('导入失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    else {
                        toastr['error']("请至少选中一个！");
                    }
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }

    //删除tab职位方法
    $scope.role.zhiwuDelete = function(){
        var dats = $scope.gridOptionzw.getSelectedRows();
        if(dats.length>0){
            if(confirm('确定要删除对应职务吗')){
                var tis = {};
                tis.data = [];
                for(var i =0;i<dats.length;i++){
                    var subFrom = {};
                    subFrom.guidRole =gridDate.guid;
                    subFrom.guidParty =dats[i].guidParty;
                    subFrom.partyType=DICT_AC_PARTYTYPE[3];
                    tis.data.push(subFrom);
                }
                role_service.removePartyRole(tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除职务成功！");
                        queryParrty(gridDate.guid,DICT_AC_PARTYTYPE[3]);//重新查询组织信息
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }else{
            toastr['error']("请最少选中一条进行删除！");
        }
    }

    //查询角色下的操作员集合
    var queryOeper=function(id){
        var  subFrom = {};
        subFrom.roleGuid = id;
        role_service.queryOperatorRole(subFrom).then(function(data){
            if(data.status == "success"){
                datas = data.retMessage;
                $scope.gridOptioner.data =  datas;
                $scope.gridOptioner.mydefalutData = datas;
                $scope.gridOptioner.getPage(1,$scope.gridOptioner.paginationPageSize);
            }else{
                toastr['error']('初始化查询失败'+'<br/>'+data.retMessage);
            }
        })
    }

    var gridOptioner = {};
    $scope.gridOptioner = gridOptioner;
    var comer = [{ field: 'operatorName', displayName: '操作员姓名'},
        { field: "userId", displayName:'登录用户名'},
        { field: "operatorStatus",displayName:'操作员状态',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.operatorStatus | translateConstants :\'DICT_AC_OPERATOR_STATUS\') + $root.constant[\'DICT_AC_OPERATOR_STATUS-\'+row.entity.operatorStatus]}}</div>'},
        { field: "authMode", displayName:'认证模式',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.authMode | translateConstants :\'DICT_AC_AUTHMODE\') + $root.constant[\'DICT_AC_AUTHMODE-\'+row.entity.authMode]}}</div>'}
    ];
    var fer = function(row){
        if(row.isSelected){
            $scope.selectRower = row.entity;
        }else{
            delete $scope.selectRower;//制空
        }
    }
    $scope.gridOptioner = initgrid($scope,gridOptioner,filterFilter,comer,true,fer);


    //新增操作员逻辑
    $scope.role.operaAdd = function(){
        openwindow($modal, 'views/roleManage/roleoperAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                var subFrom={};
                operator_service.queryAllOperator(subFrom).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        $scope.gridOptions.data = datas;
                    }else{
                        toastr['error']('查询失败'+'<br/>'+data.retMessage);
                    }
                })
                var gridOptions = {};
                $scope.gridOptions = gridOptions;//数据方法
                var com = [
                    { field: 'operatorName', displayName: '操作员姓名'},
                    { field: "userId", displayName:'登录用户名'},
                    { field: "operatorStatus",displayName:'操作员状态',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.operatorStatus | translateConstants :\'DICT_AC_OPERATOR_STATUS\') + $root.constant[\'DICT_AC_OPERATOR_STATUS-\'+row.entity.operatorStatus]}}</div>'},
                    { field: "authMode", displayName:'认证模式',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.authMode | translateConstants :\'DICT_AC_AUTHMODE\') + $root.constant[\'DICT_AC_AUTHMODE-\'+row.entity.authMode]}}</div>'}
                ];
                //自定义点击事件
                var f1 = function(row){
                    if(row.isSelected){
                        $scope.selectRow3 = row.entity;
                    }
                    else{
                        delete $scope.selectRow3;//制空
                    }
                }
                $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,true,f1);
                $scope.importAdd = function () {
                    var dats = $scope.gridOptions.getSelectedRows();
                    if (dats.length > 0) {
                        var tis = {};
                        tis.data = [];
                        for(var i =0;i<dats.length;i++){
                            var subFrom = {};
                            subFrom.guidRole = gridDate.guid;
                            subFrom.guidOperator = dats[i].guid;
                            subFrom.auth = 'Y';
                            tis.data.push(subFrom)
                        }
                        role_service.addOperatorRole(tis).then(function(data){
                            var datas = data.retMessage;
                            if(data.status == "success"){
                                toastr['success']("导入成功！");
                                $modalInstance.close();
                                queryOeper(gridDate.guid)
                            }else{
                                toastr['error']('查询失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    else {
                        toastr['error']("请至少选中一个！");
                    }
                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }

    //删除操作员逻辑
    $scope.role.operaDelete = function(){
        var dats = $scope.gridOptioner.getSelectedRows();
        if(dats.length>0){
            if(confirm('确定要删除选中操作员吗')){
                var tis = {};
                tis.data = [];
                for(var i=0;i<dats.length;i++){
                    var subFrom = {};
                    subFrom.guidRole =gridDate.guid;
                    subFrom.guidOperator =dats[i].guidOperator;
                    subFrom.auth = 'Y';
                    tis.data.push(subFrom)
                }
                role_service.removeOperatorRole(tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除操作员成功！");
                        queryOeper(gridDate.guid)//查询操作员列表
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }else{
            toastr['error']("请最少选中一条进行删除！");
        }
    }
});