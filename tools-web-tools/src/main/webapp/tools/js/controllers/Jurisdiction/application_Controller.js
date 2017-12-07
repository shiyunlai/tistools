/**
 * Created by wangbo on 2017/6/1.
 */
angular.module('MetronicApp').controller('application_controller', function($rootScope, $scope ,$state,$modal,$http,i18nService,common_service, $timeout,filterFilter,$uibModal,uiGridConstants,application_service) {
    var biz = {};
    $scope.biz = biz;
    var item = {};
    $scope.item = item;
    $scope.biz.item = item;
    $scope.biz.datas = [];
    //定义权限
    $scope.biz.applica = false;
    //当前节点定义
    var thisNode = '';
    $scope.thisNode = thisNode;
    //点击刷新树
    $scope.biz.reload = function(){
        $("#container").jstree().refresh();
    }

    var res = $rootScope.res.application_service;//页面所需调用的服务
    /*-------------------------------------------------------------------------------分割符--------------------------------------------------------------------------------*/
    //0、树结构逻辑代码
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

    //清空
    biz.clear = function () {
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
                        openwindow($uibModal, 'views/Jurisdiction/applicationAdd.html', 'lg',
                            function ($scope, $modalInstance) {
                                var ids = obj.id;
                                //增加方法
                                $scope.saveDict = function(item){//保存新增的函数
                                    application_service.appAdd(item).then(function(data){
                                        if(data.status == "success"){
                                            toastr['success']("新增成功！");
                                            biz.initt(ids);//调用查询服务
                                            $("#container").jstree().refresh();
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
        if(node.parent == 'AC0000'){
            var it = {
                "新增功能组":{
                    "id":"createc",
                    "label":"新增功能组",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);//从数据库中获取所有的数据
                        var ids = $scope.biz.item.id;//拿到id
                        openwindow($modal, 'views/Jurisdiction/appgroupAdd.html', 'lg',//弹出页面
                            function ($scope, $modalInstance) {
                                $scope.add = function(item){
                                    item.guidApp = ids;
                                    item.guidParents = '';

                                    application_service.groupAdd(item).then(function(data){
                                        if(data.status == "success"){
                                            toastr['success']("新增成功！");
                                            $("#container").jstree().refresh();
                                            biz.initt1(ids);//调用查询服务
                                            $modalInstance.close();
                                        }else{
                                            toastr['error']('新增失败'+'<br/>'+data.retMessage);
                                        }
                                    })
                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            })
                    }
                },
                "删除应用":{
                    "label":"删除应用",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        var guid = obj.id;
                        var ids = $scope.biz.item.id;
                        if(confirm("确定删除选中的应用吗？删除应用将删除该应用下的所有功能组")){
                            var guids = {};
                            guids.id = guid;//删除传入的必须是json格式
                            application_service.appDel(guids).then(function(data){
                                if(data.status == "success"){
                                    toastr['success']("删除成功!");
                                    $scope.dictionaryAdd = {};
                                    $("#container").jstree().refresh();
                                    $scope.biz.apptab = false;
                                    biz.initt(ids);//调用查询服务,传入点击树的id，查询
                                }else{
                                    toastr['error']('删除失败'+'<br/>'+data.retMessage);
                                }
                            })
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
        if(node.parents[1] == "AC0000" || !isNull(node.original.funcgroupName)){
            var it = {
                "新建子功能组":{
                    "id":"createb",
                    "label":"新建子功能组",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        var ids = $scope.biz.item//获取到点击根节点
                        openwindow($modal, 'views/Jurisdiction/childfunctionAdd.html', 'lg',//弹出页面
                            function ($scope, $modalInstance) {
                                $scope.addchild = function(item){
                                    item.guidApp = ids.original.guidApp;//归属应用
                                    item.guidParents = ids.id;
                                    application_service.groupAdd(item).then(function(data){
                                        if(data.status == "success"){
                                            biz.initt2(ids.id);//调用列表刷新方法
                                            toastr['success']("新增成功！");
                                            $("#container").jstree().refresh();
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
                },
                "删除功能组":{
                    "label":"删除功能组",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        var guid = obj.original.guid;
                        var ids = $scope.biz.item.id;//获取点击的根节点的值
                        //获取选中的guid,传入删除
                        if(confirm("确定删除该功能组吗？将一同删除所有下级功能组和功能！")){
                            var guids = {};
                            guids.id = guid;//删除传入的必须是json格式
                            application_service.groupDel(guids).then(function(data){
                                if(data.status == "success"){
                                    toastr['success']("删除成功!");
                                    $("#container").jstree().refresh();//重新刷新树
                                    //biz.initt1(ids);//调用查询服务//调用查询服务,传入点击树的id，查询
                                }else{
                                    toastr['error']('删除失败'+'<br/>'+data.retMessage);
                                }
                            })
                        }
                    }
                },
                "新增功能":{
                    "label":"新增功能 ",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        var ids = obj.id;//获取到右击节点的id
                        openwindow($modal, 'views/Jurisdiction/afAdd.html', 'lg',//弹出页面
                            function ($scope, $modalInstance) {
                                $scope.add = function(item){
                                    var subFrom = {};
                                    item.guidFuncgroup = ids;
                                    subFrom.data = item;
                                    console.log(subFrom)
                                    application_service.acFuncAdd(subFrom).then(function(data){
                                        if(data.status == "success"){
                                            toastr['success']("新增成功！");
                                            biz.initt2(ids.id);//调用列表刷新方法
                                            $modalInstance.close();
                                            $("#container").jstree().refresh();//重新刷新树
                                        }else if(data.status == "error"){
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
                },
                "刷新":{
                    "label":"刷新",
                    "action":function (node) {
                        $("#container").jstree().refresh();
                    }
                },
            }
            return it;
        }
    };
    //  应用管理树结构
    $("#container").jstree({
        "core" : {
            "themes" : {
                "responsive": false
            },
            "check_callback" : false,
            'data' : function (obj, callback) {
                var jsonarray = [];
                $scope.jsonarray = jsonarray;
                var subFrom = {};
                subFrom.id = obj.id;
                application_service.appQuery(subFrom).then(function (data) {
                    var datas = data.retMessage;
                    var its = [];
                    if(datas instanceof Array){
                        for(var i = 0; i < datas.length;i++){
                            if(obj.id == 'AC0000'){
                                datas[i].text = datas[i].appName;
                                datas[i].id = datas[i].guid;
                                datas[i].children = true;
                                datas[i].icon = "fa fa-th-large icon-state-info icon-lg";
                                its.push(datas[i])
                            }else if(isNull(datas[i].appName) && obj.id != 'AC0000'){
                                datas[i].text = datas[i].funcgroupName;
                                datas[i].id = datas[i].guid;
                                datas[i].children = true;
                                datas[i].icon = "fa  fa-th-list  icon-state-info icon-lg";
                                its.push(datas[i])
                            }
                        }
                    }
                    else{
                        var itemss = [];
                        if(!isNull(datas.funcList)){//如果存在funcList，则显示功能数据
                            //var datsea = datas.funcList;
                            for(var i =0;i <datas.funcList.length;i++){
                                    datas.funcList[i].text = datas.funcList[i].funcName;
                                    datas.funcList[i].id = datas.funcList[i].guid;
                                    datas.funcList[i].children = false;
                                    datas.funcList[i].icon = "fa fa-wrench icon-state-info icon-lg"
                                    its.push(datas.funcList[i])
                            }
                        }
                        if(!isNull(datas.groupList)){//如果存在groupList，则显示功能组数据
                            //调用用问题
                            for(var i =0;i< datas.groupList.length;i++){
                                datas.groupList[i].text = datas.groupList[i].funcgroupName;
                                datas.groupList[i].id = datas.groupList[i].guid;
                                datas.groupList[i].children = true;
                                datas.groupList[i].icon = "fa  fa-th-list  icon-state-info icon-lg"
                                its.push(datas.groupList[i])
                            }
                        }
                        if(!isNull(datas.rootName)){
                            datas.text = datas.rootName;
                            datas.children = true;
                            datas.id = datas.rootCode;
                            datas.icon = "fa fa-home icon-state-info icon-lg"
                            its.push(datas)
                        }
                    }
                    $scope.jsonarray = angular.copy(its);
                    callback.call(this, $scope.jsonarray);
                })
            },
        },
        'search': {
            //只显示符合条件的
            show_only_matches: true,
        },
        "state" : { "key" : "demo3" },
        "contextmenu":{'items':items
        },
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

        "plugins" : [ "state", "types","search","contextmenu" ]
    })
    /* 定义树列表改变事件*/
    $('#container').on("changed.jstree", function (e, data){
        if(typeof data.node !== 'undefined'){//拿到结点详情
            $scope.dictionaryAdd = data.node.original;
            $scope.dictionaryAdd.openDateStr = moment($scope.dictionaryAdd.openDate).format('YYYY-MM-DD');
            $scope.thisNode = data.node.text;
            $scope.biz.item = data.node;//全局点击值传递$scope.biz.item = data.node;//全局点击值传递
            if(data.node.parent == '#'){
                //创建机构实例
                $scope.biz.applica = true;
                $scope.biz.apptab = false;
                $scope.biz.appfund = false;
                $scope.biz.appchild = false;
                //调用服务.查询右侧内容
                var subFrom = {};
                $scope.subFrom = subFrom;
                $scope.subFrom.id = data.node.id;
                application_service.appQuery(subFrom).then(function (data) {
                    var datas = data.retMessage;
                    //判断是否开通
                    $scope.gridOptions0.data = datas;
                    $scope.gridOptions0.mydefalutData = datas;
                    $scope.gridOptions0.getPage(1,$scope.gridOptions0.paginationPageSize);
                })
            }else if(data.node.parent == "AC0000"){
                $scope.biz.apptab = true;
                $scope.biz.appfund = false;
                $scope.biz.applica = false;
                $scope.biz.appchild = false;
                yyflag.yyxx = true;
                yyflag.gnzlb =false;
                //调用服务.查询右侧内容
                var subFrom = {};
                $scope.subFrom = subFrom;
                $scope.subFrom.id = data.node.id;
                application_service.appQuery(subFrom).then(function (data) {
                    var datas = data.retMessage;
                    $scope.gridOptions1.data = datas;
                    $scope.gridOptions1.mydefalutData = datas;
                    $scope.gridOptions1.getPage(1,$scope.gridOptions1.paginationPageSize);
                })
            }else if(data.node.parents[1] == 'AC0000'||!isNull(data.node.original.funcgroupName)){
                $scope.biz.appfund = false;
                $scope.biz.appchild = true;
                $scope.biz.applica = false;
                $scope.biz.apptab = false;
                childflag.gnzxx = true;
                childflag.zgnzlb =false;
                childflag.gnlb = false;
                var subFrom = {};
                $scope.subFrom = subFrom;
                $scope.subFrom.id = data.node.id;
                application_service.appQuery(subFrom).then(function (data) {
                    if(!isNull(data.retMessage.funcList ||!isNull(data.retMessage.groupList))){//查询判断，如果是空，则返回空。
                        var datas = data.retMessage.funcList;//功能列表资源
                        var dates = data.retMessage.groupList;
                        if(isNull(dates)){
                            var datast = [];
                            $scope.gridOptions2.data = datast;
                        }else{
                            $scope.gridOptions2.data = dates;
                            $scope.gridOptions2.mydefalutData = dates;
                            $scope.gridOptions2.getPage(1,$scope.gridOptions2.paginationPageSize);
                        }
                        if(isNull(datas)){
                            var datast = [];
                            $scope.gridOptions3.data = datast;
                        }else{
                            $scope.gridOptions3.data = datas;
                            $scope.gridOptions3.mydefalutData = datas;
                            $scope.gridOptions3.getPage(1,$scope.gridOptions3.paginationPageSize);
                        }

                    }else{
                        var datast = [];
                        $scope.gridOptions3.data = datast;
                        $scope.gridOptions2.data = datast;
                    }
                })
            }else if(!isNull(data.node.original.funcName)){
                var subFrom = {};
                $scope.subFrom = subFrom;
                $scope.subFrom.funcGuid = data.node.original.guid;
                appfunflag.gnlist = true;
                appfunflag.zylist = false;
                appfunflag.gnactive =false;
                //查询功能行为
                application_service.queryAllBhvDefForFunc(subFrom).then(function (data) {
                    if(data.status == 'success'){
                        var datas = data.retMessage;
                        $scope.gridOptions5.data = datas;//把获取到的数据复制给表
                        $scope.gridOptions5.mydefalutData = datas;
                        $scope.gridOptions5.getPage(1,$scope.gridOptions5.paginationPageSize);
                    }else{
                        $scope.gridOptions5.data = [];
                    }
                })
                //查询资源列表
                var resouir = {};
                resouir.data = {}
                resouir.data.funcGuid = data.node.id;
                common_service.post(res.queryAcFuncResource,resouir).then(function(data){
                    if(data.status == "success"){
                        var datas  = data.retMessage;
                        $scope.gridResources.data =  datas;
                        $scope.gridResources.mydefalutData = datas;
                        $scope.gridResources.getPage(1,$scope.gridResources.paginationPageSize);
                    }
                })
                var ids = data.node.original.id;//点击功能的id
                biz.initt5(ids);
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
    //ui-grid 具体配置
    var gridOptions0 = {};
    $scope.gridOptions0 = gridOptions0;
    var com = [{ field: 'appName', displayName: '应用名称'},
        { field: "appCode", displayName:'应用代码'},
        { field: "appType", displayName:'应用类型',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.appType | translateConstants :\'DICT_AC_APPTYPE\') + $root.constant[\'DICT_AC_APPTYPE-\'+row.entity.appType]}}</div>'},
        { field: "isopen", displayName:'是否开通',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.isopen | translateConstants :\'DICT_YON\') + $root.constant[\'DICT_YON-\'+row.entity.isopen]}}</div>'},
        { field: "url", displayName:'访问地址'},
        { field: "ipAddr", displayName:'IP'},
        { field: "ipPort", displayName:'端口'},
        { field: "appDesc", displayName:'应用描述'}
    ];
    //自定义点击事件
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions0 = initgrid($scope,gridOptions0,filterFilter,com,false,f);

    biz.initt = function(num){//查询服务公用方法
        var subFrom = {};
        subFrom.id = num;
        application_service.appQuery(subFrom).then(function (data) {
            var datas = data.retMessage;
            $scope.gridOptions0.data = datas;//把获取到的数据复制给表
            $scope.gridOptions0.mydefalutData = datas;
            $scope.gridOptions0.getPage(1,$scope.gridOptions0.paginationPageSize);
        })
    }
    //新增应用页面代码
    $scope.show_win = function(){
        var ids = $scope.biz.item.id;//获取点击的根节点的值
        openwindow($modal, 'views/Jurisdiction/applicationAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.saveDict = function(item){//保存新增的函数
                    application_service.appAdd(item).then(function(data){
                        if(data.status == "success"){
                            toastr['success']("保存成功！");
                            biz.initt(ids);//调用查询服务
                            $("#container").jstree().refresh();
                            $modalInstance.close();
                        }else if(data.status == "error"){
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
    //删除应用代码
    $scope.transsetDelAll = function (item) {
        var getSel = $scope.gridOptions0.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){ //如果是多选，只需要判断是否为空就可以了
            toastr['error']("请至少选择一条记录进行删除！","SORRY！");
        }else{
            var guid = getSel[0].guid;
            var ids = $scope.biz.item.id;//获取点击的根节点的值
            //获取选中的guid,传入删除
            if(confirm("确定删除选中的应用吗？删除应用将删除该应用下的所有功能组和对应功能")){
                var guids = {};
                guids.id = guid;//删除传入的必须是json格式
                application_service.appDel(guids).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除成功!");
                        biz.initt(ids);//调用查询服务,传入点击树的id，查询
                        $("#container").jstree().refresh();
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }
    }
    //修改页面代码
    $scope.show_edit = function(id){
        var getSel = $scope.gridOptions0.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条进行修改！");
        }else{
            var item = getSel[0];
            var ids = $scope.biz.item.id;//获取点击的根节点的值
            openwindow($modal, 'views/Jurisdiction/applicationAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.id = id;
                    if(!isNull(item)){//如果参数不为空，则就回显
                        $scope.dictionaryAdd = angular.copy(item);
                    }
                    //修改页面代码逻辑
                    $scope.saveDict = function(item){//保存新增的函数
                        item.id = item.guid;
                        application_service.appEdit(item).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("修改成功！");
                                $modalInstance.close();
                                biz.initt(ids);//调用查询服务,传入点击树的id，查询
                            }else{
                                toastr['error']('修改失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');

                    };
                })
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

    var gridOptions1 = {};
    $scope.gridOptions1 = gridOptions1;
    var com1 = [{ field: 'funcgroupName', displayName: '功能组名称'},
        { field: "groupLevel", displayName:'节点层次'},
        { field: "funcgroupSeq", displayName:'功能组序号'}
 /*       { field: "isleaf", displayName:'是否叶子节点'}*/
    ];
    //自定义点击事件
    var f1 = function(row){
        if(row.isSelected){
            $scope.selectRow1 = row.entity;
        }else{
            delete $scope.selectRow1;//制空
        }
    }
    $scope.gridOptions1 = initgrid($scope,gridOptions1,filterFilter,com1,false,f1);
    biz.initt1 = function(num){//查询服务公用方法
        var subFrom = {};
        subFrom.id = num;
        application_service.appQuery(subFrom).then(function (data) {
            var datas = data.retMessage;
            $scope.gridOptions1.data = datas;//把获取到的数据复制给表
            $scope.gridOptions1.mydefalutData = datas;
            $scope.gridOptions1.getPage(1,$scope.gridOptions1.paginationPageSize);
        })
    }
    //功能组新增
    $scope.addApp = function(){
        var ids = $scope.biz.item.id;
        openwindow($modal, 'views/Jurisdiction/appgroupAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){
                    item.guidApp = ids;
                    item.guidParents = '';
                    application_service.groupAdd(item).then(function(data){
                        if(data.status == "success"){
                            toastr['success']("保存成功！");
                            $("#container").jstree().refresh();
                            biz.initt1(ids);//调用查询服务
                            $modalInstance.close();
                        }else{
                            toastr['error']('新增失败'+'<br/>'+data.retMessage);
                        }
                    })
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }
    //功能组修改
    $scope.exidApp = function(id){
        var ids = $scope.biz.item.id;
        var getSel = $scope.gridOptions1.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条进行修改！");
        }else{
            var item = getSel[0];
            openwindow($modal, 'views/Jurisdiction/appgroupAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    var ids = id;
                    $scope.id = ids;
                    if(!isNull(item)){//如果参数不为空，则就回显
                        $scope.subFrom = angular.copy(item);
                    }
                    $scope.add = function(item){
                        item.id = item.guid;
                        item.GUID_PARENTS = '';
                        application_service.groupEdit(item).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("修改成功！");
                                $("#container").jstree().refresh();
                                $modalInstance.close();
                                biz.initt1(ids);//调用查询服务
                            }else{
                                toastr['error']('修改失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }
    }
    //功能组删除
    $scope.funlistDel =  function(){
        var getSel = $scope.gridOptions1.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条进行删除！");
        }else{
            var guid = getSel[0].guid;
            var ids = $scope.biz.item.id;//获取点击的根节点的值
            //获取选中的guid,传入删除
            if(confirm("确定删除选中的功能组吗？删除功能将删除该功能组下的下级功能组和功能")){
                var guids = {};
                guids.id = guid;//删除传入的必须是json格式
                application_service.groupDel(guids).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除成功!");
                        $("#container").jstree().refresh();//重新刷新树
                        biz.initt1(ids);//调用查询服务//调用查询服务,传入点击树的id，查询
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
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
    /* 功能组编辑逻辑*/
    $scope.biz.addschild = function(item){
        $scope.copyssEdit = angular.copy(item)
        $scope.editsflag = !$scope.editsflag;//让保存取消方法显现,并且让文本框可以输入
    }
    //功能组保存方法
    $scope.biz.functionsave = function (item) {
        //调用后台保存逻辑
        item.id = item.guid;
        item.GUID_PARENTS = '';
        application_service.groupEdit(item).then(function(data){
            if(data.status == "success"){
                toastr['success']("修改成功！");
                $scope.editsflag = !$scope.editsflag;//让保存取消方法显现
                $("#container").jstree().refresh();//刷新树结构
            }else{
                toastr['error']('修改失败'+'<br/>'+data.retMessage);
            }
        })
    }
    $scope.biz.childsEdit = function(){
        $scope.dictionaryAdd = $scope.copyssEdit;
        $scope.editsflag = !$scope.editsflag;//让保存取消方法显现
    }

    /*子功能组页签内容*/

    //ui-grid 具体配置
    var gridOptions2 = {};
    $scope.gridOptions2 = gridOptions2;
    var com2 = [{ field: 'funcgroupName', displayName: '功能组名称'},
        { field: "groupLevel", displayName:'节点层次'},
        { field: "funcgroupSeq", displayName:'功能组序号'}
        /*{ field: "isleaf", displayName:'是否叶子节点'}*/
    ];

    //自定义点击事件
    var f2 = function(row){
        if(row.isSelected){
            $scope.selectRow2 = row.entity;
        }else{
            delete $scope.selectRow2;
        }
    }
    $scope.gridOptions2 = initgrid($scope,gridOptions2,filterFilter,com2,false,f2);

    biz.initt2 = function(num){//查询服务公用方法
        var subFrom = {};
        subFrom.id = num;
        application_service.appQuery(subFrom).then(function (data) {
            var datas = data.retMessage.groupList;
            if(isNull(datas)){
                var datas = [];
                $scope.gridOptions2.data = datas;
            }
            $scope.gridOptions2.data = datas;//把获取到的数据复制给表
            //把获取到的数据复制给表
            $scope.gridOptions2.mydefalutData = datas;
            $scope.gridOptions2.getPage(1,$scope.gridOptions2.paginationPageSize);
        })
    }

    //子功能组列表新增功能
    $scope.addchildApp = function(){
        var ids = $scope.biz.item//获取到点击根节点
        openwindow($modal, 'views/Jurisdiction/childfunctionAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.addchild = function(item){
                    item.guidApp = ids.original.guidApp;//归属应用
                    item.guidParents = ids.id;
                    application_service.groupAdd(item).then(function(data){
                        if(data.status == "success"){
                            biz.initt2(ids.id);//调用列表刷新方法
                            toastr['success']("保存成功！");
                            $("#container").jstree().refresh();//重新刷新树
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

    //子功能组页签编辑页面
    $scope.exidchildApp = function(id){
        var ids = $scope.biz.item//获取到点击根节点
        var getSel = $scope.gridOptions2.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条进行修改！");
        }else{
            var items = getSel[0];
            openwindow($modal, 'views/Jurisdiction/childfunctionAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    var idds = id;
                    $scope.id = idds;
                    if(!isNull(item)){//如果参数不为空，则就回显
                        $scope.childFrom = angular.copy(items);
                    }
                    $scope.addchild = function(item){
                        item.id = items.guid;
                        item.GUID_PARENTS = '';
                        //获取到选中的guid,传入
                        application_service.groupEdit(item).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("修改成功！");
                                biz.initt2(ids.id);
                                $("#container").jstree().refresh();
                                $modalInstance.close();
                            }else{
                                toastr['error']('修改失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }

    }
    //子功能组页签删除方法
    $scope.appchildDelAll = function(){
        var getSel = $scope.gridOptions2.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条进行删除！");
        }else{
            var guid = getSel[0].guid;
            var ids = $scope.biz.item;//获取点击的根节点的值
            if(confirm("确定删除选中的功能组？删除功能组将删除该功能下的所有下级功能组和功能")){
                //获取选中的guid,传入删除
                var guids = {};
                guids.id = guid;//删除传入的必须是json格式
                application_service.groupDel(guids).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除成功!");
                        biz.initt2(ids.id);//调用查询服务
                        $("#container").jstree().refresh();//重新刷新树
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }
    }

    var gridOptions3 = {};
    $scope.gridOptions3 = gridOptions3;
    var com3 = [{ field: 'funcName', displayName: '功能名称'},
        { field: "funcType", displayName:'功能类型',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.funcType | translateConstants :\'DICT_AC_FUNCTYPE\') + $root.constant[\'DICT_AC_FUNCTYPE-\'+row.entity.funcType]}}</div>'},
        { field: "ismenu", displayName:'是否定义为菜单',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.ismenu | translateConstants :\'DICT_YON\') + $root.constant[\'DICT_YON-\'+row.entity.ismenu]}}</div>'},
        // { field: "guidFuncgroup", displayName:'所属功能组'}
    ];
    //自定义点击事件
    var f3 = function(row){
        if(row.isSelected){
            $scope.selectRow3 = row.entity;
        }else{
            delete $scope.selectRow3;//制空
        }
    }
    $scope.gridOptions3 = initgrid($scope,gridOptions3,filterFilter,com3,false,f3);

    biz.initt3 = function(num){//查询服务公用方法
        var subFrom = {};
        subFrom.id = num;
        application_service.appQuery(subFrom).then(function (data){
            if(isNull(data.retMessage.funcList)){
                $scope.gridOptions3.data = [];
            }else{
                var datas = data.retMessage.funcList;
                $scope.gridOptions3.data = datas;//把获取到的数据复制给表
                $scope.gridOptions3.mydefalutData = datas;
                $scope.gridOptions3.getPage(1,$scope.gridOptions3.paginationPageSize);
            }
        })
    }

    //功能列表新增方法
    $scope.addappList = function(){
        var ids = $scope.biz.item.id;//获取点击的根节点guid
        openwindow($modal, 'views/Jurisdiction/afAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){
                    var  subFrom = {};
                    item.guidFuncgroup = ids;
                    subFrom.data = item;
                  console.log(subFrom)
                    application_service.acFuncAdd(subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']("新增成功！");
                            biz.initt3(ids);//刷新列表
                            $modalInstance.close();
                            $("#container").jstree().refresh();//重新刷新树
                        }else if(data.status == "error"){
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

    //功能列表编辑方法
    $scope.exitappList = function(id){
        var ides = $scope.biz.item.id;//获取点击的根节点guid
        var getSel = $scope.gridOptions3.getSelectedRows();
        console.log(getSel)
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条！");
        }else{
            var item = getSel[0];
            openwindow($modal, 'views/Jurisdiction/afAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    var ids = id;
                    $scope.id = ids;
                    var original = $scope.copyfunEdit;//原本修改前的数据
                    if(!isNull(item)){//如果参数不为空，则就回显
                        $scope.appFrom = angular.copy(item);
                    }
                    $scope.add = function(item){
                        var  changeData = {};
                        for(var key in original){//循环最初的数据?
                            if(original[key] !== item[key]){
                                changeData[key]= original[key];
                            }
                        }
                        var subFrom = {}
                        item.id = item.guid;
                        subFrom.data = item;
                        subFrom.changeData = changeData;
                        //获取到选中的guid，参入item，然后传入修改就可以
                        application_service.acFuncEdit(subFrom).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("修改成功！");
                                biz.initt3(ides);//刷新列表
                                delete $scope.selectRow3;
                                $("#container").jstree().refresh();
                                $modalInstance.close();
                            }else if(data.status == "error"){
                                toastr['error']('修改失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }
    }
    //功能列表删除方法
    $scope.exitapplistDelAll=function(){
        var getSel = $scope.gridOptions3.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条！");
        }else{
            var guid = getSel[0].guid
            var ids = $scope.biz.item.id;//获取点击的根节点的值
            if(confirm("确定删除选中的功能吗？")){
                var guids = {};
                guids.data = {};
                guids.data.id = guid;//删除传入的必须是json格式
                application_service.acFuncDel(guids).then(function(data){
                    console.log(data)
                    if(data.status == "success"){
                        toastr['success']("删除成功!");
                        biz.initt3(ids);//调用查询服务//调用查询服务,传入点击树的id，查询
                        $("#container").jstree().refresh();//重新刷新树
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
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
    /* 功能tab页面逻辑*/
    //应用开通逻辑
    biz.openApp = function(item){
        var ids = $scope.biz.item.id;//获取点击的根节点的值
        var times = new Date();
        times  = moment().format('YYYY-MM-DD');
        item.openDateStr=times;
        var subFrom = {};
        $scope.subFrom = subFrom;
        subFrom.appGuid = ids;
        subFrom.openStr = item.openDateStr;
        //保存到后台接口
        application_service.enableApp(subFrom).then(function(data){
            if(data.status == "success"){
                toastr['success']("开通成功!");
                item.isopen = 'Y';
            }else{
                toastr['error']('开通失败'+'<br/>'+data.retMessage);
            }
        })
    }


    $scope.$watch('dictionaryAdd.isopen',function(newValue,oldValue, scope){
        if(newValue =='Y'){
            $scope.isOpenY = true;
        }else{
            $scope.isOpenY = false;
        }

    });
    //关闭应用逻辑
    biz.clearApp = function(item){
        var ids = $scope.biz.item.id;//获取点击的根节点的值
        var subFrom = {};
        $scope.subFrom = subFrom;
        subFrom.appGuid=ids;
        application_service.disableApp(subFrom).then(function(data){
            if(data.status == "success"){
                toastr['success']("关闭成功!");
                item.isopen = 'N';
            }else{
                toastr['error']('关闭失败'+'<br/>'+data.retMessage);
            }
        })
    }
    //是否开通逻辑
    $scope.isOpen = function(item){
        if(item == 'Y'){
            times  = moment().format('YYYY-MM-DD');
            item.openDateStr=times;
            $scope.clearapp = true;
            $scope.openapp = false;

        }else{
            item.openDateStr='';
            $scope.clearapp = false;
            $scope.openapp = true;
        }

    }

    //查看概况
    $scope.biz.histroy = function () {
        $state.go("loghistory",{id:$scope.dictionaryAdd.guid});//跳转到历史页面
    }

    //应用编辑方法
    $scope.biz.appedit = function(item){
        $scope.copyEdit = angular.copy(item)
        $scope.editflag = !$scope.editflag;//让保存取消方法显现,并且让文本框可以输入

    }
    //应用编辑取消方法
    $scope.biz.edit = function(item){
        $scope.dictionaryAdd = $scope.copyEdit;
        $scope.editflag = !$scope.editflag;//让保存取消方法显现
    }

    //全局对象
    //功能信息编辑方法
    $scope.biz.appssedit = function(item){
        $scope.copyfunEdit = angular.copy(item)
        $scope.editflag = !$scope.editflag;//让保存取消方法显现,并且让文本框可以输入
    }
    //应用编辑取消方法
    $scope.biz.funedit = function(item){
        $scope.dictionaryAdd = $scope.copyfunEdit;
        $scope.editflag = !$scope.editflag;//让保存取消方法显现
    }


    //应用信息修改方法
    $scope.biz.appsave = function(item){
            item.id = item.guid;
        application_service.appEdit(item).then(function(data){
            if(data.status == "success"){
                toastr['success']("修改成功！");
                $("#container").jstree().refresh();
                $scope.editflag = !$scope.editflag;
                $scope.dictionaryAdd = item;
            }else{
                toastr['error']('修改失败'+'<br/>'+data.retMessage);
            }
        })
    }
    
    //功能修改方法
    $scope.biz.appfunsave = function (item) {
        var original = $scope.copyfunEdit;//原本修改前的数据
        //item 是修改后的数据
        var  changeData = {};
        for(var key in original){//循环最初的数据?
            if(original[key] !== item[key]){
                changeData[key]= original[key];
            }
        }
        // console.log(changedata);//修改前的值
        var subFrom = {}
        item.id = item.guid;
        subFrom.data = item;
        subFrom.changeData = changeData;
        //获取到选中的guid，参入item，然后传入修改就可以
        application_service.acFuncEdit(subFrom).then(function(data){
            if(data.status == "success"){;
                toastr['success']("修改成功！");
                $("#container").jstree().refresh();//树结构重新刷新
                $scope.editflag = !$scope.editflag;
            }else{
                toastr['error']('修改失败'+'<br/>'+data.retMessage);
            }
        })
    }




    //资源类型列表
    var gridResources = {};
    $scope.gridResources = gridResources;
    var comRes = [
        { field: 'attrType', displayName: '属性类型'},
        { field: 'attrKey', displayName: '属性名'},
        { field: 'attrValue', displayName: '属性值'},
        { field: 'memo', displayName: '备注'}
    ];
    var fRes = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            delete $scope.selectRow;//制空
        }
}
    $scope.gridResources = initgrid($scope,gridResources,filterFilter,comRes,true,fRes);

    //查询功能资源封装
    biz.queryResources = function(num){
        var resouir = {};
        resouir.data = {}
        resouir.data.funcGuid = num;
        common_service.post(res.queryAcFuncResource,resouir).then(function(data){
            if(data.status == "success"){
                var datas  = data.retMessage;
                $scope.gridResources.data = datas;
                $scope.gridResources.mydefalutData = datas;
                $scope.gridResources.getPage(1,$scope.gridResources.paginationPageSize);
            }else{
                $scope.gridResources.data = [];
                $scope.gridResources.mydefalutData = [];
                $scope.gridResources.getPage(1,$scope.gridResources.paginationPageSize);
            }
        })
    }

    //新增资源逻辑
    $scope.biz.addResources = function () {
        var ids = $scope.biz.item//获取到点击根节点
        openwindow($uibModal, 'views/Jurisdiction/addResources.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){//保存新增的函数
                    var subFrom = {};
                    subFrom.data  = {};
                    subFrom.data = item;
                    subFrom.data.guidFunc = ids.id;
                    common_service.post(res.createAcFuncResource,subFrom).then(function(data){
                        console.log(data);
                        if(data.status == "success"){
                            toastr['success']("新增资源成功！");
                            biz.queryResources(ids.id)
                            $modalInstance.close();
                        }
                    })
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }


    //修改资源逻辑
    $scope.biz.editResources = function () {
        var ids = $scope.biz.item//获取到点击根节点
        var getSel = $scope.gridResources.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条！");
        }else{
            var str =  getSel[0];//信息回选
            openwindow($uibModal, 'views/Jurisdiction/addResources.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    $scope.id = true;
                    if(!isNull(str)){//如果参数不为空，则就回显
                        $scope.resourFrom = angular.copy(str);
                    }
                    $scope.add = function(item){//保存新增的函数
                        var subFrom = {};
                        subFrom.data  = {};
                        subFrom.data = item;
                        subFrom.data.guidFunc = ids.id;
                        common_service.post(res.updateAcFuncResource,subFrom).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("修改资源成功！");
                                biz.queryResources(ids.id)
                                $modalInstance.close();
                            }else{
                                toastr['error']('修改资源失败'+'<br/>'+data.retMessage);
                            }
                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }
    }

    //删除资源成功
    $scope.biz.delResources = function () {
        var ids = $scope.biz.item;//获取到点击根节点
        var getSel = $scope.gridResources.getSelectedRows();
        if(isNull(getSel) || getSel.length<1){
            toastr['error']("请选择一条数据进行修改！");
        }else{
            if(confirm('确定删除改配置吗')){
                var tis = {};
                tis.data = []
                for(var i =0;i<getSel.length;i++){
                    var subFrom = {};
                    subFrom = getSel[i];
                    subFrom.guidFunc = ids.id;
                    tis.data.push(subFrom);
                }
                common_service.post(res.deleteAcFuncResource,tis).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除资源成功！");
                        biz.queryResources(ids.id)
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }
    }


    //改变功能类型方法
    $scope.changetype = function (item) {
        if(confirm('确定切换类型吗？切换类型会导致原类型下行为删除')){
            var ids = $scope.biz.item.id;//获取点击的根节点的值，这里指点击功能节点的guid
            //调用保存接口，传入item类型guid 和 ids 功能guid 即可
            var subFrom = {};
            subFrom.data = {};
            subFrom.data.funcGuid = ids;
            subFrom.data.bhvtypeGuid = item;
            common_service.post(res.updateFuncBhvType,subFrom).then(function(data){
                if(data.status == "success"){
                    toastr['success']("更改资源类型成功！");
                    biz.initt5(ids);//重新查询功能下行为
                }else{
                    toastr['error']('更改资源类型失败'+'<br/>'+data.retMessage);
                }
            })
        }
    }

    //查询功能行为
    biz.initt5 = function(funcGuid){//查询类型对应操作行为方法
        var subFrom = {};
        subFrom.funcGuid = funcGuid;
        // subFrom.bhvtypeGuid = bhvtypeGuid;
        application_service.queryAllBhvDefForFunc(subFrom).then(function (data){
            if(data.status == "success"){
                invalid();//有效无效按钮全部隐藏
                var datas = data.retMessage
                $scope.gridOptions5.data = datas;//把获取到的数据复制给表
                $scope.gridOptions5.mydefalutData = datas;
                $scope.gridOptions5.getPage(1,$scope.gridOptions5.paginationPageSize);
            }
        })
    }



    //查询功能下所有行为
/*    biz.inittAll = function(funcGuid){//查询类型对应操作行为方法
        var subFrom = {};
        subFrom.funcGuid = funcGuid;
        application_service.queryAllBhvDefForFunc(subFrom).then(function (data){
            var datas = data.retMessage
           $scope.gridOptions5.data = datas;//把获取到的数据复制给表
            $scope.gridOptions5.mydefalutData = datas;
            $scope.gridOptions5.getPage(1,$scope.gridOptions5.paginationPageSize);
        })
    }*/

    //按钮全部无效方法
    function invalid() {
        $scope.seteffinv =false;//设为有效按钮隐藏
        $scope.invalid = false;//设为无效按钮隐藏
    }
    /*事件行为列表*/
    var gridOptions5 = {};
    $scope.gridOptions5 = gridOptions5;
    var initdata5 = function(){
        return $scope.myDatasapp;//数据方法
    }
    var  com5= [{ field: 'bhvName', displayName: '行为名称'},
        { field: "bhvCode", displayName:'行为代码'},
        { field: "iseffective", displayName:'是否有效',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.iseffective | translateConstants :\'DICT_YON\') + $root.constant[\'DICT_YON-\'+row.entity.iseffective]}}</div>'}
    ];
    //自定义点击事件
    var f5 = function(row){
        if(row.isSelected){
            $scope.selectRow5 = row.entity;
            if(row.entity.iseffective == 'Y'){
                $scope.seteffinv =false;//设为有效按钮隐藏
                $scope.invalid = true;//设为无效按钮显示
            }else{
                $scope.seteffinv =true;//设为有效按钮显示
                $scope.invalid = false;//设为无效按钮隐藏
            }
        }else{
            delete $scope.selectRow5;//制空
            invalid();//让按钮全部无效
        }
    }
    $scope.gridOptions5 = initgrid($scope,gridOptions5,filterFilter,com5,true,f5);
    $scope.gridOptions5.paginationPageSize = 20, //每页显示个数

    //设为有效方法
     biz.effective=function () {
         var dats = $scope.gridOptions5.getSelectedRows()[0];//拿到对应行为数据
         var ids = $scope.biz.item.id;//功能的guid
         var subFrom = {};
         subFrom.data = {};
         subFrom.data.funcBhvGuid = dats.guidFuncBhv;
         subFrom.data.isEffective = 'Y';
         common_service.post(res.setFuncBhvStatus,subFrom).then(function(data){
             if(data.status == "success"){
                 toastr['success']("设置有效成功!");
                 biz.initt5(ids);//重新查询功能下行为
             }else{
                 toastr['success']("设置有效失败!"+'<br/>'+data.retMessage);
             }
         })
     }

    //设为无效
    biz.invalid=function () {
        var dats = $scope.gridOptions5.getSelectedRows()[0];//拿到对应行为数据
        var ids = $scope.biz.item.id;//功能的guid
        console.log(dats);
        var subFrom = {};
        subFrom.data = {};
        subFrom.data.funcBhvGuid = dats.guidFuncBhv;
        subFrom.data.isEffective = 'N';
        common_service.post(res.setFuncBhvStatus,subFrom).then(function(data){
            if(data.status == "success"){
                toastr['success']("设置无效成功!");
                biz.initt5(ids);//重新查询功能下行为
            }else{
                toastr['success']("设置无效失败!"+'<br/>'+data.retMessage);
            }
        })
    }
    //新增功能行为
        $scope.biz.functactive = function() {
            var ids = $scope.biz.item.id;//获取点击的根节点的值，这里指点击功能节点的guid
            var guidBhv = $scope.biz.item.original.guidBhvtypeDef;
            var guid = guidBhv;//类型guid 因为模型改了,通过ids可以拿到类型的guid,把类型的guid写上去就可以了,目前还不是 等到后台部署好就可以了
            openwindow($uibModal, 'views/Jurisdiction/funactiveAdd.html', 'lg',
                function ($scope, $modalInstance) {
                    var gridOptions = {};
                    $scope.gridOptions = gridOptions;
                    var initdata = function () {
                        return $scope.importadd;//数据方法
                    }
                    var com = [
                        {field: "bhvName", displayName: '操作行为名称'},
                        {field: "bhvCode", displayName: '操作行为代码'},
                    ];
                    //自定义点击事件
                    var f1 = function (row) {
                        if (row.isSelected) {
                            $scope.selectRow3 = row.entity;
                        } else {
                            delete $scope.selectRow3;//制空
                        }
                    }
                    $scope.gridOptions = initgrid($scope, gridOptions, filterFilter, com, true, f1);
                    var subFrom = {};
                    subFrom.id = guid;
                    application_service.queryBhvDefByBhvType(subFrom).then(function (data) {
                        if(data.status == "success"){
                            var datas = data.retMessage
                            $scope.gridOptions.data = datas;//把获取到的数据复制给表
                            $scope.gridOptions.mydefalutData = datas;
                            $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
                        }
                    })
                    //创建机构实例
                    //导入方法
                    $scope.importAdd = function () {
                        var dats = $scope.gridOptions.getSelectedRows();
                        if (dats.length > 0) {
                            var fun = [];
                            for(var i =0; i<dats.length;i++){
                                fun.push(dats[i].guid)
                            }
                            subFrom.id = ids;
                            subFrom.typeGuidList = fun;
                            application_service.addBhvDefForFunc(subFrom).then(function(data){
                                if(data.status == "success"){
                                    toastr['success']("新增成功！");
                                    biz.initt5(ids);//重新查询功能下行为
                                    $modalInstance.close();
                                }else{
                                    toastr['error']('导入失败'+'<br/>'+data.retMessage);
                                }
                            })
                        } else {
                            toastr['error']("请至少选中一个！");
                        }
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }

    //删除功能行为
    $scope.biz.functactiveDel = function(){
        var it = $scope.gridOptions5.getSelectedRows();//多选事件
        var ids = $scope.biz.item.id;//功能的guid
        var fun = [];
        if(it.length>0){
            if(confirm('确定删除该行为?')){
                for(var i =0 ; i<it.length;i++){
                    fun.push(it[i].guid);
                }
                var guids = {};
                guids.funcGuid = ids;//删除传入的必须是json格式
                guids.bhvDefGuids = fun;//删除传入的必须是json格式
                application_service.delFuncBhvDef(guids).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除成功!");
                        biz.initt5(ids);//重新查询功能下行为
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }else{
            toastr['error']("请至少选中一条类型进行删除！");
        }
    }
});