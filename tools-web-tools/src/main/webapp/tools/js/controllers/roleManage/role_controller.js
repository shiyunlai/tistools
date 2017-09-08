/**
 * Created by wangbo on 2017/6/11.
 */
angular.module('MetronicApp').controller('role_controller', function($scope ,$rootScope,$modal,$timeout,$http,abftree_service,dictonary_service,common_service,i18nService,role_service,menu_service,operator_service,filterFilter,$uibModal,uiGridConstants) {
        var role = {};
        $scope.role = role;

    var res = $rootScope.res.abftree_service;//页面所需调用的服务

        /* 左侧角色查询逻辑 */
        i18nService.setCurrentLang("zh-cn");
        //组织类别
        var  DICT_AC_PARTYTYPE   = ['organization','workgroup','position','duty'];

    //查询应用
    var subFrom  = {};
    menu_service.queryAllAcApp(subFrom).then(function(data){
        if(data.status == "success"){
            var datas = data.retMessage;
           role.Appall = datas;//所有应用数据，最终要在弹窗中渲染
        }else{
            toastr['error']('初始化查询失败'+'<br/>'+data.retMessage);
        }
    })

    //ui-grid 具体配置
    var testgrid = function(ids){
        var gridOptions = {};
        $scope.gridOptions = gridOptions;
        var com = [{ field: 'roleCode', displayName: '角色代码'},
            { field: "roleName", displayName:'角色名称'},
            { field: "roleType", displayName:'角色类别',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.roleType | translateConstants :\'DICT_AC_ROLETYPE\') + $root.constant[\'DICT_AC_ROLETYPE-\'+row.entity.roleType]}}</div>',
                //配置搜索下拉框
                filter:{
                    //term: '0',//默认搜索那项
                    type: uiGridConstants.filter.SELECT,
                    selectOptions: [{ value: 'sys', label: 'sys'}, { value: 'app', label: 'app' }]
                }},
            { field: "appName", displayName:'隶属应用',
                filter:{
                    //term: '0',//默认搜索那项
                    type: uiGridConstants.filter.SELECT,
                    //selectOptions: [{ value: 'ABF', label: 'ABF' }, { value: '测试应用', label: '测试应用' }]
                    selectOptions: ids
                }
            },
        ];
        var f = function(row){
            if(row.isSelected){
                $scope.selectRow = row.entity;
                $scope.role.shows = true;//执行
                role.roleinfo = row.entity;
                role.guidApp = $scope.selectRow.guidApp;
                control('#'+role.guidApp,$scope.selectRow); //调用树结构函数
                $("#container").jstree().refresh();//刷新树结构
                role.rofault($scope.selectRow.guid);//刷新组织关系列表
                queryOeper($scope.selectRow.guid);//刷新操作员列表
            }else{
                delete $scope.selectRow;//制空
                $scope.role.shows = false;
            }
        }
        $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);
    }

    testgrid()//调用grid函数

    //查询所有角色列表
    role_service.queryRoleList(subFrom).then(function(data){
        var  datas = data.retMessage;
        if(data.status == "success"){
            var lodash = angular.copy(datas);
            var tis = [];
            for(var i = 0;i<lodash.length;i++){
                tis.push(lodash[i].appName)
            }
            lodash =_.uniq(tis, true);//去重
            var array=[];
            for(var i=0;i<lodash.length;i++){
                var obj={};
                obj['value'] = lodash[i];
                obj['label'] = lodash[i];
                array.push(obj)//把对象push进去
            }
            testgrid(array);//调用列表生成方法
            $scope.gridOptions.data =  datas;
            $scope.gridOptions.mydefalutData = datas;
            $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
        }else{
            toastr['error']('初始化查询失败'+'<br/>'+data.retMessage);
        }
    })
    //初始化列表函数

    /* 树结构逻辑代码*/
    //树过滤
    $("#s").submit(function(e) {
        e.preventDefault();
        $("#container").jstree(true).search($("#q").val());
    });
    //创建树结构
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
                        var its = [];
                        if(type=='root'){
                            //root加载
                            for(var i = 0 ;i <dataes.length;i++){
                                dataes[i].text = dataes[i].rootName;
                                dataes[i].children = true;
                                dataes[i].id = dataes[i].rootCode;
                                dataes[i].icon = "fa fa-home  icon-state-info icon-lg";
                                dataes[i].check_node =true;
                                its.push(dataes[i])
                            }

                        }else if(type =="app"){
                            for(var i = 0 ;i <dataes.length;i++){
                                dataes[i].text = dataes[i].funcgroupName;
                                dataes[i].children = true;
                                dataes[i].id = dataes[i].guid;
                                dataes[i].icon = "fa  fa-files-o icon-state-info icon-lg";
                                its.push(dataes[i])
                            }
                        }else if(type =="group"){
                            console.log(dataes)
                            if(!isNull(dataes.groupList)){
                                for(var i = 0 ;i <dataes.groupList.length;i++){
                                    dataes.groupList[i].text = dataes.groupList[i].funcgroupName;
                                    dataes.groupList[i].children = true;
                                    dataes.groupList[i].id = dataes.groupList[i].guid;
                                    dataes.groupList[i].icon = "fa  fa-files-o icon-state-info icon-lg"
                                    its.push(dataes.groupList[i])
                                }
                                if(!isNull(dataes.funcList)){
                                    for(var i = 0 ;i <dataes.funcList.length;i++){
                                        dataes.funcList[i].text = dataes.funcList[i].funcName;
                                        dataes.funcList[i].children = true;
                                        dataes.funcList[i].id = dataes.funcList[i].guid;
                                        dataes.funcList[i].icon = "fa  fa-files-o icon-state-info icon-lg"
                                        its.push(dataes.funcList[i])
                                    }
                                }

                            }else{
                                for(var i = 0 ;i <dataes.funcList.length;i++){
                                    dataes.funcList[i].text = dataes.funcList[i].funcName;
                                    dataes.funcList[i].children = false;
                                    dataes.funcList[i].id = dataes.funcList[i].guid;
                                    dataes.funcList[i].icon = "fa fa-wrench icon-state-info icon-lg"
                                    its.push(dataes.funcList[i])
                                    console.log(its);
                                }
                            }
                        }
                        $scope.jsonarray = angular.copy(its);
                        callback.call(this, $scope.jsonarray);
                        var items = {};
                        items.roleGuid = arrs.guid
                        role_service.queryRoleFunc(items).then(function(data){
                            var datas = data.retMessage;
                            if(data.status == "success"){
                                if(datas.length!==0){
                                    for(var i = 0; i<datas.length;i++){
                                        $('#container').jstree(true).check_node(datas[i].guidFunc);//选中
                                    }
                                    $('#container').jstree().open_all();
                                }
                            }else{

                            }
                        })
                    })
                },
            },
            "force_text": true,
            plugins: ["sort", "types", "checkbox", "wholerow", "themes", "html_data"],
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
            'callback' : {
                move_node:function (node) {
                }
            },
        }).bind("copy.jstree", function (node,e, data ) {
        })
    }

    //点击保存权限分配
    $scope.role.checkAll = function(){
        var nodes=$("#container").jstree("get_checked");//获取所有选中的节点

        if(nodes.length>=0 ) {
            var subFrom = {};
            subFrom.roleGuid = role.roleinfo.guid;
            subFrom.appGuid = role.roleinfo.guidApp;
            subFrom.funcList = [];
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].indexOf('FUNC') == 0 && nodes[i].indexOf('FUNCGROUP') !== 0) {
                    var item = {};
                    item.funcGuid = nodes[i];
                    item.groupGuid = $("#container").jstree().get_node(nodes[i]).parent;
                    subFrom.funcList.push(item);
                }
            }
            role_service.configRoleFunc(subFrom).then(function (data) {
                var datas = data.retMessage;
                if (data.status == "success") {
                    toastr['success']('保存权限成功');
                } else {
                    toastr['error']('保存权限失败' + '<br/>' + data.retMessage);
                }
            })
        }
    }

    //查询角色列表
    role.inint = function(){
        var subFrom = {};
        role_service.queryRoleList(subFrom).then(function(data){
            var  datas = data.retMessage;
            if(data.status == "success"){
                $scope.gridOptions.data =  datas;
                $scope.gridOptions.mydefalutData = datas;
                $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
            }else{
                toastr['error']('初始化查询失败'+'<br/>'+data.retMessage);
            }
        })
    }

    //新增角色逻辑
    $scope.role_add = function(){
            openwindow($modal, 'views/roleManage/rolemanageAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.roleList = role.Appall;//循环渲染，在弹窗中
                    $scope.add = function(item){
                        var subFrom = {};
                        subFrom = item;
                        role_service.createRole(subFrom).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("新增成功！");
                                role.inint();
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
                                   role.inint();

                                   control('#'+items.guidApp,items); //调用树结构函数
                                   $("#container").jstree().refresh();//刷新树结构
                                   role.rofault(items.guid);//刷新组织关系列表
                                   queryOeper(items.guid);//刷新操作员列表
                                   $modalInstance.close();

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
        queryParrty(id,DICT_AC_PARTYTYPE[3]);//重新查询服务信息
        queryOeper(id)//查询操作员列表
    }



    //控制页切换代码
    role.loadgwdata = function (type) {
        role.guid = $scope.selectRow.guid;
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
            $scope.rolesflag.org = true;//初始化打开
            queryParrty(role.guid,DICT_AC_PARTYTYPE[0]);
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
            queryOeper(role.guid);
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
                        var tis = [];
                        for(var i =0; i<dats.length; i++){
                            var subFrom = {};
                            subFrom.guidRole = role.roleinfo.guid;
                            subFrom.partyType=DICT_AC_PARTYTYPE[0];
                            subFrom.guidParty =dats[i].guid;
                            tis.push(subFrom)
                        }
                        role_service.addPartyRole(tis).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("导入成功！");
                                $modalInstance.close();
                                queryParrty(role.roleinfo.guid,DICT_AC_PARTYTYPE[0]);//重新查询
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
        var dats = $scope.gridOptions.getSelectedRows();
        if(dats.length>0){
            if(confirm('确定要删除对应组织吗')){
                var tis = [];
                for(var i =0;i<dats.length;i++){
                    var subFrom = {};
                    subFrom.roleGuid =role.roleinfo.guid;
                    subFrom.partyGuid =dats[i].guidParty;
                    tis.push(subFrom);
                }
                role_service.removePartyRole(tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除组织成功！");
                        queryParrty(role.roleinfo.guid,DICT_AC_PARTYTYPE[0]);//重新查询组织信息
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
                        var tis = [];
                        for(var i =0;i<dats.length;i++){
                            var subFrom = {};
                            subFrom.guidRole = role.roleinfo.guid;
                            subFrom.partyType=DICT_AC_PARTYTYPE[1];
                            subFrom.guidParty =dats[i].guid;
                            tis.push(subFrom)
                        }
                        role_service.addPartyRole(tis).then(function(data){
                            var  datas = data.retMessage;
                            if(data.status == "success"){
                                toastr['success']("导入成功！");
                                $modalInstance.close();
                                queryParrty(role.roleinfo.guid,DICT_AC_PARTYTYPE[1]);//重新查询工作组
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
                var tis = [];
                for(var i = 0 ;i<dats.length; i++){
                    var subFrom = {};
                    subFrom.roleGuid =role.roleinfo.guid;
                    subFrom.partyGuid =dats[i].guidParty;
                    tis.push(subFrom);
                }
                role_service.removePartyRole(tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除组织成功！");
                        queryParrty(role.roleinfo.guid,DICT_AC_PARTYTYPE[1]);//重新查询组织信息
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
                        var tis = [];
                        for(var i =0;i<dats.length;i++){
                            var subFrom = {};
                            subFrom.guidRole = role.roleinfo.guid;
                            subFrom.partyType=DICT_AC_PARTYTYPE[2];
                            subFrom.guidParty =dats[i].guid;
                            tis.push(subFrom)
                        }
                        role_service.addPartyRole(tis).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("导入成功！");
                                $modalInstance.close();
                                queryParrty(role.roleinfo.guid,DICT_AC_PARTYTYPE[2]);//重新查询
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
                var tis=[];
                for(var i = 0;i<dats.length; i++){
                    var subFrom = {};
                    subFrom.roleGuid =role.roleinfo.guid;
                    subFrom.partyGuid =dats[i].guidParty;
                    tis.push(subFrom)
                }
                role_service.removePartyRole(tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除组织成功！");
                        queryParrty(role.roleinfo.guid,DICT_AC_PARTYTYPE[2]);//重新查询组织信息
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
                       var tis = [];
                        for(var i =0;i<dats.length;i++){
                            var subFrom = {};
                            subFrom.guidRole = role.roleinfo.guid;
                            subFrom.partyType=DICT_AC_PARTYTYPE[3]
                            subFrom.guidParty =dats[i].guid;
                            tis.push(subFrom)
                        }
                        role_service.addPartyRole(tis).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("导入成功！");
                                $modalInstance.close();
                                queryParrty(role.roleinfo.guid,DICT_AC_PARTYTYPE[3]);//重新查询
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
                var tis =[];
                for(var i = 0;i<dats.length; i++){
                    var subFrom = {};
                    subFrom.roleGuid =role.roleinfo.guid;
                    subFrom.partyGuid =dats[i].guidParty;
                    tis.push(subFrom)
                }
                role_service.removePartyRole(tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除组织成功！");
                        queryParrty(role.roleinfo.guid,DICT_AC_PARTYTYPE[3]);//重新查询组织信息
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
                        var tis = [];
                        for(var i =0;i<dats.length;i++){
                            var subFrom = {};
                            subFrom.guidRole = role.roleinfo.guid;
                            subFrom.guidOperator = dats[i].guid;
                            tis.push(subFrom)
                        }
                        role_service.addOperatorRole(tis).then(function(data){
                            var datas = data.retMessage;
                            if(data.status == "success"){
                                toastr['success']("导入成功！");
                                $modalInstance.close();
                                queryOeper(role.roleinfo.guid)
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
                var tis = [];
                for(var i=0;i<dats.length;i++){
                    var subFrom = {};
                    subFrom.roleGuid =role.roleinfo.guid;
                    subFrom.operatorGuid =dats[i].guidOperator;
                    tis.push(subFrom)
                }
                role_service.removeOperatorRole(tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除操作员成功！");
                        queryOeper(role.roleinfo.guid)//查询操作员列表
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
