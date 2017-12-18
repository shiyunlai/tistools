/**
 * Created by wangbo on 2017/6/11.
 */
angular.module('MetronicApp').controller('role_controller', function($scope ,$rootScope,$state,$modal,$http,$ocLazyLoad,abftree_service,$filter,$timeout,dictonary_service,common_service,i18nService,role_service,menu_service,operator_service,filterFilter,$uibModal,uiGridConstants) {
        var role = {};
        $scope.role = role;
        var res = $rootScope.res.abftree_service;//页面所需调用的服务
        /* 左侧角色查询逻辑 */
        i18nService.setCurrentLang("zh-cn");

    $ocLazyLoad.load({//重新加载一次 main.js  保证拿到行为权限
        name: 'MetronicApp',
        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
        files: [
            'js/main.js'
        ]
    });
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
                    // $scope.roleList = role.Appall;//循环渲染，在弹窗中
                    $scope.add = function(item){
                        var subFrom = {};
                        subFrom = item;
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
                        $scope.editflag = true;//不允许修改
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
    $scope.RoleName = gridDate.roleName;//绑定角色
    //返回方法
    $scope.myback = function(){
        window.history.back(-1);
    }
    var roleservice = $rootScope.res.role_service;//页面所需调用的服务
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
            var items = {};
            items.roleGuid = arrs.guid;
            role_service.queryRoleFunc(items).then(function(data){
                var alldatas = data.retMessage;//拿到角色跟功能绑定的所有guid数组
                var chenageDatas = [];
                for(var i = 0; i < alldatas.length;i++){
                    chenageDatas.push(alldatas[i].guidFunc);
                }
                $scope.chenageDatas = chenageDatas;
            })
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
                            role_service.appQuery(subFrom).then(function (res){
                                if(res.status == "success"){
                                    var datas = res.retMessage;
                                    var dataes =datas.data;//整体的树结构数组
                                    var type = datas.type;//类型
                                    var  its  =  [];
                                    creatJstree(dataes,type,its,$scope.chenageDatas);//调用创建树结构
                                    $scope.jsonarray = angular.copy(its);
                                    callback.call(this, $scope.jsonarray);
                                    /*$timeout(function(){
                                        $('#container').jstree().open_all();
                                    },80)*/
                                }
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
            var items = {};
            items.roleGuid = arrs.guid
            role_service.queryRoleFunc(items).then(function(data){
                //获取表中所有角色功能
                var alldatas = data.retMessage;
                $scope.alldatas = alldatas;
                if(data.status == "success"){
                    if(alldatas.length!==0){
                        $timeout(function(){
                            for(var i = 0; i<alldatas.length;i++){
                                $('#container').jstree(true).check_node(alldatas[i].guidFunc);//选中
                            }
                        },1500)
                    }
                     $timeout(function(){
                         $('#container').jstree().open_all();
                     },30)
                }
            })
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
        if(nodes.length>=0) {
            if(confirm('如果取消功能，会删除改功能对应所有行为?')){
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
    }

    //新增功能行为方法
    $scope.role.funclist = function(){
        var funcGuid = $scope.role.item;//点击值guid
        openwindow($modal, 'views/roleManage/roleFunc.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
            $scope.title = '新增行为'
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
                        toastr('error')
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
    //实体范围tab页面逻辑
    var eneitsflag ={};
    $scope.eneitsflag = eneitsflag;

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
        }else if(type == 'entity'){//实体tab页面
            for(var i in $scope.rolesflag){
                $scope.rolesflag[i] = false;//每一项全部不显示
            }
            $scope.roleflag.operatorer = false;
            $scope.roleflag.limit = false;
            $scope.roleflag.dist = false;
            $scope.roleflag.entity = true;//实体范围页面展示
            $scope.eneitsflag.current = true;//默认显示当前实体
            Entityjstree(gridDate.guid);//传入角色guid;
            $scope.edit = false;//让配置按钮显示
            //实体下tab页面
            role.eneisflag = function (type) {
               if(type == 'current'){
                   $scope.eneitsflag.current = true;
                   $scope.eneitsflag.Attributes = false;
                   $scope.eneitsflag.range = false;
               }else if(type == 'Attributes'){
                   $scope.eneitsflag.current = false;
                   $scope.eneitsflag.Attributes = true;
                   $scope.eneitsflag.range = false;
               }else{
                   $scope.eneitsflag.current = false;
                   $scope.eneitsflag.Attributes =false;
                   $scope.eneitsflag.range = true;
               }
            }
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



    /*---------------------------------------------------------------实体内容------------------------------------------------------*/
    //查询角色下实体
        var Entityjstree = function (item) {
        var res = $rootScope.res.role_service;//页面所需调用的服务
        $('#entitconter').jstree('destroy',false);//删除重新加载，只删除数据。
        $("#entitconter").jstree({
            "core": {
                "themes" : {
                    "responsive": false
                },
                'data': function (obj, callback) {
                    // obj.text='实体管理'
                    var jsonarray = [];
                    $scope.jsonarray = jsonarray;
                    var subFrom = {};
                    var its = [];
                    if(obj.id == '#'){
                        var data ={};
                        data.text = '实体';
                        data.children = true;
                        data.types = 'root';
                        data.id = 'root';
                        its.push(data);
                        $scope.jsonarray = angular.copy(its);
                        callback.call(this, $scope.jsonarray);
                    }else if(obj.id =='root'){
                        var subFrom  = {};
                        subFrom.type = 'entityType';//查询类型
                        subFrom.roleGuid = item;//角色guid
                        common_service.post(res.acRoleEntityTree,{data:subFrom}).then(function(data){
                            if(data.status == "success"){
                                var datas = data.retMessage;
                                for(var i =0;i <datas.length;i++){
                                    datas[i].text = datas[i].itemName;
                                    datas[i].children = true;
                                    datas[i].id = obj.id + datas[i].guid;
                                    datas[i].types = 'enType';
                                    datas[i].icon = "fa  fa-files-o icon-state-info icon-lg"
                                    its.push(datas[i]);
                                }
                                $scope.jsonarray = angular.copy(its);
                                callback.call(this, $scope.jsonarray);
                            }
                        })
                    }else if(obj.original.types =='enType'){
                        var subFrom  = {};
                        subFrom.type = 'entity';//查询所有实体
                        subFrom.roleGuid = item;
                        subFrom.entityType = obj.original.itemValue;
                        common_service.post(res.acRoleEntityTree,{data:subFrom}).then(function(data){
                            var datas = data.retMessage;
                            if(data.status == "success"){
                                for(var i =0;i <datas.length;i++){
                                    datas[i].text = datas[i].entityName;
                                    datas[i].children = false;
                                    datas[i].id = datas[i].guidEntity;
                                    datas[i].types = 'eneits';
                                    datas[i].icon = "fa fa-file-text icon-state-info icon-lg"
                                    its.push(datas[i]);
                                }
                                $scope.jsonarray = angular.copy(its);
                                callback.call(this, $scope.jsonarray);
                            }
                        })
                    }

                }
            },
            "force_text": true,
            plugins: ["sort", "types", "wholerow", "themes", "html_data","search"],
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
            'sort': function (a, b) {
                //排序插件，会两者比较，获取到节点的order属性，插件会自动两两比较。
                return this.get_node(a).original.seqno > this.get_node(b).original.seqno ? 1 : -1;
            }

        }).bind("changed.jstree", function (e, data) {
            if (typeof data.node !== 'undefined') {//拿到结点详情
                $scope.dataEnt = data.node.original;//绑定内容给页面
                queryEntity(data.node.original.guidEntity);//调用查询实体属性的方法
                queryEntityRange(data.node.original.guidEntity);//调用查询实体属性范围方法
                if(data.node.original.types == 'eneits'){
                    $scope.eneitsGrid = true;//只有点击实体属性的时候，才让右侧内容显示
                }else{
                    $scope.eneitsGrid = false;
                }
                ($scope.$$phase) ? null : $scope.$apply();
            }
        });
    }

    //配置页面
    $scope.role.editEntity = function () {
        $('#entitconter').jstree('destroy',false);//删除重新加载，只删除数据。
        var guidApp = gridDate.guidApp;//应用的guid
        //重新生成树结构 问题是如何只查询一个应用
        $("#entitconter").jstree({
            "core": {
                "themes": {
                    "responsive": false
                },
                "check_callback": true,
                'data':function(obj, callback){
                    // obj.text='实体管理'
                    var jsonarray = [];
                    $scope.jsonarray = jsonarray;
                    var subFrom = {};
                    var its = [];
                    if(obj.id == '#'){
                        var data ={};
                        data.text = '实体';
                        data.children = true;
                        data.types = 'root';
                        data.id = 'root';
                        its.push(data);
                        $scope.jsonarray = angular.copy(its);
                        callback.call(this, $scope.jsonarray);
                    }else if(obj.id =='root'){
                        var subFrom  = {};
                        subFrom.type = 'entityType';//查询类型
                        subFrom.roleGuid = gridDate.guid;//角色guid
                        common_service.post(roleservice.acRoleEntityTree,{data:subFrom}).then(function(data){
                            if(data.status == "success"){
                                var datas = data.retMessage;
                                for(var i =0;i <datas.length;i++){
                                    datas[i].text = datas[i].itemName;
                                    datas[i].children = true;
                                    datas[i].id = obj.id + datas[i].guid;
                                    datas[i].types = 'enType';
                                    datas[i].icon = "fa  fa-files-o icon-state-info icon-lg"
                                    its.push(datas[i]);
                                }
                                $scope.jsonarray = angular.copy(its);
                                callback.call(this, $scope.jsonarray);
                            }
                        })
                    }else if(obj.original.types =='enType'){
                        //查询角色拥有的实体
                        var subFrom  = {};
                        subFrom.type = 'entity';//查询所有实体
                        subFrom.roleGuid = gridDate.guid;
                        subFrom.appGuid = guidApp;
                        subFrom.entityType = obj.original.itemValue;
                        subFrom.isEdit = 'y';
                        common_service.post(roleservice.acRoleEntityTree,{data:subFrom}).then(function(data){
                            var datas = data.retMessage;
                            var datasAll = datas.all;
                            var datasOwn = datas.own;
                            var tisAll = []
                            if(data.status == "success"){
                                for(var i =0;i <datasAll.length;i++){
                                    datasAll[i].text = datasAll[i].entityName;
                                    datasAll[i].children = false;
                                    datasAll[i].id = datasAll[i].guid;
                                    datasAll[i].types = 'eneits';
                                    datasAll[i].icon = "fa fa-file-text icon-state-info icon-lg"
                                    its.push(datasAll[i]);
                                    tisAll.push(datasAll[i].id)
                                }
                                $timeout(function () {//勾选
                                    for(var i =0;i<tisAll.length;i++){
                                        for(var j =0;j<datasOwn.length;j++){
                                            if(datasOwn[j] == datasAll[i].id){
                                                $('#entitconter').jstree(true).check_node(datasOwn);//让对应的选中
                                                break;
                                            }
                                        }
                                    }
                                },800)
                                $scope.jsonarray = angular.copy(its);
                                callback.call(this, $scope.jsonarray);
                            }
                        })
                    }
                }
            },
            "types": {
                "default": {
                    "icon": "fa fa-folder icon-state-warning icon-lg"
                },
                "file": {
                    "icon": "fa fa-file icon-state-warning icon-lg"
                }
            },
            "state": {"key": "demo3"},
            'search': {
                show_only_matches: true,
            },
            'callback': {
                move_node: function (node) {
                }
            },
            "plugins": ["state", "types","search","sort","checkbox"],
            "checkbox": {
                "keep_selected_style": false,//是否默认选中
            },
            'sort': function (a, b) {
                //排序插件，会两者比较，获取到节点的order属性，插件会自动两两比较。
                return this.get_node(a).original.seqno > this.get_node(b).original.seqno ? 1 : -1;
            }
        })
        $timeout(function(){
            $('#entitconter').jstree().open_all();
        },30)
        $scope.edit = true;//让保存按钮显示
    }

    //配置实体属性方法
    $scope.role.EntitySave = function () {
        var nodes=$("#entitconter").jstree("get_checked");//
        $('#entitconter').jstree('destroy',true);//删除重新加载，只删除数据。
        $scope.edit = false;//让配置按钮显示
        if(nodes.length>=0) {
            if(confirm('如果删除实体，会删除实体对应的所有属性和范围')){
                $('#entitconter').jstree('destroy',true);//删除重新加载，只删除数据。
                Entityjstree(gridDate.guid);//调用刷新树
                var subFrom = {};
                subFrom.roleGuid = gridDate.guid;
                subFrom.entityGuids= []
                for(var i = 0;i<nodes.length;i++){
                    if(nodes[i].indexOf('root') !== 0 ){//把实体类型的guid干掉，只保留实体的guid
                        subFrom.entityGuids.push(nodes[i])
                    }
                }
                common_service.post(roleservice.configRoleEntity,{data:subFrom}).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("修改成功！");
                        /*$('#entitconter').jstree('destroy',true);//删除重新加载，只删除数据。
                        Entityjstree(gridDate.guid);//调用刷新树*/
                    }else{
                        toastr['error']("修改失败！"+'<br/>'+data.retMessage);
                    }
                })

            }
        }
    }
    //编辑当前实体方法
    $scope.role.entityEdit = function (item) {
        $scope.editflag = !$scope.editflag;
    }

    //保存当前实体方法
    $scope.role.entitySave = function (item) {
        //调用保存方法
        common_service.post(roleservice.updateAcRoleEntity,{data:item}).then(function(data){
            if(data.status == "success"){
                toastr['success']("修改成功！");
                $scope.editflag = !$scope.editflag;
            }else{
                toastr['error']("修改失败！"+'<br/>'+data.retMessage);
            }
        })
    }

    //取消当前实体修改
    $scope.role.entityCancel = function () {
        $scope.editflag = !$scope.editflag;
    }

    //生成实体下属性表格
    var gridAttributes = {};
    $scope.gridAttributes = grideditEntity;
    var gridAttributestcom =[
        { field: 'fieldName', displayName: '实体名称'},
        { field: "ismodify", displayName:'是否可修改',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.ismodify | translateConstants :\'DICT_YON\') + $root.constant[\'DICT_YON-\'+row.entity.ismodify]}}</div>'},
        { field: "isview", displayName:'是否可查看',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.isview | translateConstants :\'DICT_YON\') + $root.constant[\'DICT_YON-\'+row.entity.isview]}}</div>'}
    ];
    var fFunenetit = function(row){
        if(row.isSelected){
            $scope.selectRow2 = row.entity;
        }else{
            delete $scope.selectRow2;//制空
        }
    }
    $scope.gridAttributes = initgrid($scope,gridAttributes,filterFilter,gridAttributestcom,true,fFunenetit);


    //查询实体属性列表
    function  queryEntity(item) {
        var subFrom = {};
        subFrom.roleGuid =gridDate.guid;//角色guid
        subFrom.entityGuid =item;//实体的GUID
        common_service.post(roleservice.getAcRoleEntitityfields,{data:subFrom}).then(function(data){
            var datas = data.retMessage;
            if(data.status == 'success'){
                $scope.gridAttributes.data =  datas;
                $scope.gridAttributes.mydefalutData = datas;
                $scope.gridAttributes.getPage(1,$scope.gridAttributes.paginationPageSize);
            }
        })
    }

    //新增实体属性
    $scope.role.AttributesAdd = function () {
        var entityGuid = $scope.dataEnt.guidEntity;//实体的guid
        var roleguid = gridDate.guid;
        openwindow($modal, 'views/roleManage/roleFunc.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.title = '新增实体属性';
                var gridOptions = {};
                $scope.gridOptions = gridOptions;
                var com = [
                    { field: 'fieldName', displayName: '属性名称'},
                    { field: "columnName", displayName:'列名'},
                    { field: "fieldDesc", displayName:'属性描述'}
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
                var roleservice = $rootScope.res.role_service;//页面所需调用的服务
                var subFrom ={};
                subFrom.entityGuid = entityGuid;
                common_service.post(roleservice.queryAcEntityfieldList,{data:subFrom}).then(function(data){
                    var datas  = data.retMessage;
                    if(data.status == "success"){
                        $scope.gridOptions.data = datas;
                        $scope.gridOptions.mydefalutData = datas;
                        $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
                    }
                })
                //导入方法
                $scope.importAdd = function () {
                    var dats = $scope.gridOptions.getSelectedRows();
                    if(dats.length >0){
                        for(var i =0; i<dats.length; i++){
                            dats[i].isview = 'Y';
                            dats[i].guidRole =roleguid;
                            dats[i].guidEntityfield =dats[i].guid;
                        }
                        common_service.post(roleservice.addAcRoleEntityfield,{data:dats}).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("导入成功！");
                                $modalInstance.close();
                                queryEntity(entityGuid);//调用查询实体属性
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

    //删除实体属性
    $scope.role_AttributesDel = function () {
        var roleguid = gridDate.guid;
        var entityGuid = $scope.dataEnt.guidEntity;//实体的guid
        var AttributesDate = $scope.gridAttributes.getSelectedRows();
        if(AttributesDate.length>0){
            if(confirm('确定要删除实体属性吗')){
                for(var i =0; i<AttributesDate.length; i++){
                    AttributesDate[i].guidRole =roleguid;
                };
                common_service.post(roleservice.deleteAcRoleEntityfield,{data:AttributesDate}).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == 'success'){
                        toastr['success']("删除成功！");
                        queryEntity(entityGuid);//调用查询实体属性
                    }
                })
            }
        }else{
            toastr['error']("请最少选中一条进行删除！");
        }
    }

    //修改实体属性
    $scope.role.AttributesEdit = function () {
        var AttributesDate = $scope.gridAttributes.getSelectedRows();
        var roleguid = gridDate.guid;//角色guid
        var entityGuid = $scope.dataEnt.guidEntity;//实体的guid
        if(AttributesDate.length>0&& AttributesDate.length<2){
            openwindow($modal, 'views/roleManage/editEntityAttr.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                   $scope.subFrom = angular.copy(AttributesDate[0]);
                    //导入方法
                    $scope.add = function (subFrom) {
                        subFrom.guidRole = roleguid;
                        subFrom.guidEntityfield = AttributesDate[0].guidEntityfield;
                        common_service.post(roleservice.updateAcRoleEntityfield,{data:subFrom}).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("修改成功！");
                                queryEntity(entityGuid);//调用查询实体属性
                                $modalInstance.close();

                            }else{
                                toastr['error']("修改失败！"+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                })
        }else{
            toastr['error']("请选中一条进行修改！");
        }
    }


    //实体范围列表
    var grideditEntity = {};
    $scope.grideditEntity = grideditEntity;
    var grideditcom =[
        { field: "privName", displayName:'数据范围权限名称'}
        ]
    var fFunenetit = function(row){
        if(row.isSelected){
            $scope.selectRow2 = row.entity;
        }else{
            delete $scope.selectRow2;//制空
        }
    }
    $scope.grideditEntity = initgrid($scope,grideditEntity,filterFilter,grideditcom,true,fFunenetit);


    //查询实体范围
    function  queryEntityRange(item) {
        var subFrom = {};
        subFrom.roleGuid = gridDate.guid;//角色guid
        subFrom.entityType =item;//实体的GUID
        common_service.post(roleservice.getAcRoleDatascopes,{data:subFrom}).then(function(data){
            var datas = data.retMessage;
            if(data.status == 'success'){
                $scope.grideditEntity.data =  datas;
                $scope.grideditEntity.mydefalutData = datas;
                $scope.grideditEntity.getPage(1,$scope.gridAttributes.paginationPageSize);
            }
        })
    }

    //新增实体范围
    $scope.role.funEntityclist = function(){
        var entityGuid = $scope.dataEnt.guidEntity;//实体的guid
        var roleguid = gridDate.guid;
        openwindow($modal, 'views/roleManage/roleFunc.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.title ='新增实体范围'
                var gridOptions = {};
                $scope.gridOptions = gridOptions;
                var com = [
                    { field: 'entityName', displayName: '实体范围名称'},
                    { field: "privName", displayName:'数据范围权限名称'},
                    { field: "dataOpType", displayName:'数据操作类型',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.dataOpType | translateConstants :\'DICT_AC_DATAOPTYPE\') + $root.constant[\'DICT_AC_DATAOPTYPE-\'+row.entity.dataOpType]}}</div>'}
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

                var subFrom ={};
                subFrom.entityGuid = entityGuid;
                common_service.post(roleservice.queryAcDatascopeList,{data:subFrom}).then(function(data){
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
                //导入实体范围方法
                $scope.importAdd = function () {
                    var dats = $scope.gridOptions.getSelectedRows();
                    if(dats.length >0){
                        for(var i =0; i<dats.length; i++){
                            dats[i].guidRole =roleguid;
                            dats[i].guidDatascope =dats[i].guid;
                        }
                        common_service.post(roleservice.addAcRoleDatascope,{data:dats}).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("导入成功！");
                                $modalInstance.close();
                                queryEntityRange(entityGuid);//调用查询实体范围属性
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

    //删除实体范围
    $scope.role.DeleteEntityfunc = function(){
        var roleguid = gridDate.guid;
        var entityGuid = $scope.dataEnt.guidEntity;//实体的guid
        var AttributesDate = $scope.gridAttributes.getSelectedRows();
        if(AttributesDate.length>0){
            if(confirm('确定要删除实体范围吗')){
                for(var i =0; i<AttributesDate.length; i++){
                    AttributesDate[i].guidRole =roleguid;
                };
                common_service.post(roleservice.deleteAcRoleDatascope,{data:AttributesDate}).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == 'success'){
                        toastr['success']("删除成功！");
                        queryEntityRange(entityGuid);//调用查询实体范围属性
                    }
                })
            }
        }else{
            toastr['error']("请最少选中一条进行删除！");
        }
    }
});






