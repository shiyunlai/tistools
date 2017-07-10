/**
 * Created by wangbo on 2017/6/1.
 */
angular.module('MetronicApp').controller('application_controller', function($rootScope, $scope ,$modal,$http,i18nService, $timeout,filterFilter,$uibModal,uiGridConstants,application_service) {
    var biz = {};
    $scope.biz = biz;
    var item = {};
    $scope.item = item;
    $scope.biz.item = item;
    $scope.biz.datas = [];
    //定义权限
    $scope.biz.applica = false;
    /*-------------------------------------------------------------------------------分割符--------------------------------------------------------------------------------*/
    //0、树结构逻辑代码
    $("#s").submit(function(e) {    //树过滤,搜索功能
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
                        openwindow($uibModal, 'views/Jurisdiction/applicationAdd.html', 'lg',
                            function ($scope, $modalInstance) {
                                var ids = obj.id;
                                //增加方法
                                $scope.saveDict = function(item){//保存新增的函数
                                    application_service.appAdd(item).then(function(data){
                                        if(data.status == "success"){
                                            toastr['success']("保存成功！");
                                            biz.initt(ids);//调用查询服务
                                            $("#container").jstree().refresh();
                                            $modalInstance.close();
                                        }else{
                                            toastr['error'](data.retCode,data.retMessage+"新增失败!");
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
                                            toastr['success']("保存成功！");
                                            $("#container").jstree().refresh();
                                            biz.initt1(ids);//调用查询服务
                                            $modalInstance.close();
                                        }else{
                                            toastr['error'](data.retCode,data.retMessage+"新增失败!");
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
                                    biz.initt(ids);//调用查询服务,传入点击树的id，查询
                                    //销毁树，然后在重新生成

                                }else{
                                    toastr['error'](data.retCode,data.retMessage+"删除失败!");
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
        if(node.parents[1] == "AC0000"){
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
                                    item.guidApp = ids.parent;//归属应用
                                    item.guidParents = ids.id;
                                    application_service.groupAdd(item).then(function(data){
                                        if(data.status == "success"){
                                            biz.initt2(ids.id);//调用列表刷新方法
                                            toastr['success']("保存成功！");
                                            $modalInstance.close();
                                        }else{
                                            toastr['error'](data.retCode,data.retMessage+"新增失败!");
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
                        if(confirm("确定删除选中的应用吗？删除应用将删除该应用下的所有功能组")){
                            var guids = {};
                            guids.id = guid;//删除传入的必须是json格式
                            application_service.groupDel(guids).then(function(data){
                                if(data.status == "success"){
                                    toastr['success']("删除成功!");
                                    $("#container").jstree().refresh();//重新刷新树
                                    biz.initt1(ids);//调用查询服务//调用查询服务,传入点击树的id，查询
                                }else{
                                    toastr['error'](data.retCode,data.retMessage+"删除失败!");
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
                        //console.log($scope.biz.item.id);
                        openwindow($modal, 'views/Jurisdiction/afAdd.html', 'lg',//弹出页面
                            function ($scope, $modalInstance) {
                                $scope.add = function(item){
                                    item.guidFuncgroup = ids;
                                    application_service.acFuncAdd(item).then(function(data){
                                        //console.log(data);
                                        if(data.status == "success"){
                                            toastr['success']("保存成功！");
                                            $modalInstance.close();
                                        }else if(data.status == "error"){
                                            toastr['error'](data.extraMessage,"新增失败!");
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
                '导入功能':{
                    "label":"导入功能",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);//从数据库中获取所有的数据
                        openwindow($uibModal, 'views/Jurisdiction/importAdd.html', 'lg',
                            function ($scope, $modalInstance) {
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
                                var gridOptions5 = {};
                                $scope.gridOptions5 = gridOptions5;
                                var initdata5 = function(){
                                    return $scope.importadd;//数据方法
                                }
                                var com5 = [{ field: '构架包', displayName: '构架包'},
                                    { field: "名称", displayName:'名称'}
                                ];
                                //自定义点击事件
                                var f5 = function(row){
                                    if(row.isSelected){
                                        $scope.selectRow3 = row.entity;
                                    }else{
                                        delete $scope.selectRow3;//制空
                                    }
                                }
                                $scope.gridOptions5 = initgrid($scope,gridOptions5,initdata5(),filterFilter,com5,true,f5);
                                //创建机构实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //处理新增机构父机构
                                subFrom.guidParents = obj.original.guid;
                                //导入方法
                                $scope.importAdd = function () {
                                    var dats = $scope.gridOptions5.getSelectedRows();
                                    if(dats.length >0){
                                        //console.log(dats)//选中的数据
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
            // so that create works
            "check_callback" : true,
            'data' : function (obj, callback) {
                var jsonarray = [];
                $scope.jsonarray = jsonarray;
                var subFrom = {};
                subFrom.id = obj.id;
                //console.log(subFrom.id)
                /*if(isNull(obj.guid)){
                 subFrom.guid = '';
                 }else{
                 subFrom.guid = obj.guid;
                 }*/
                application_service.appQuery(subFrom).then(function (data) {
                    var datas = data.retMessage;
                    var its = [];
                    if(datas instanceof Array){
                        for(var i = 0; i < datas.length;i++){
                            //console.log(datas[i])
                            if(obj.id == 'AC0000'){
                                datas[i].text = datas[i].appName;
                                datas[i].id = datas[i].guid;
                                datas[i].children = true;
                                its.push(datas[i])
                                /* $scope.jsonarray = angular.copy(datas);
                                 callback.call(this, $scope.jsonarray);*/
                            }else if(isNull(datas[i].appName) && obj.id != 'AC0000'){
                                datas[i].text = datas[i].funcgroupName;
                                datas[i].id = datas[i].guid;
                                datas[i].children = true;
                                its.push(datas[i])
                                /* $scope.jsonarray = angular.copy(datas);
                                 callback.call(this, $scope.jsonarray);*/
                            }
                        }
                    }
                    else{
                        var itemss = [];
                        if(!isNull(datas.funcList)){//如果存在funcList，则显示功能数据
                            //var datsea = datas.funcList;
                            for(var i =0;i <datas.funcList.length;i++){
                                //console.log(datas.funcList[i])
                                //console.log(datas.funcList[i])
                                    datas.funcList[i].text = datas.funcList[i].funcName;
                                    datas.funcList[i].id = datas.funcList[i].guid;
                                    datas.funcList[i].children = false;
                                    its.push(datas.funcList[i])
                                //itemss.push(datas.funcList[i])
                                /*$scope.jsonarray = angular.copy(data.funcList);
                                 callback.call(this, itemss);*/
                            }
                        }
                        if(!isNull(datas.groupList)){//如果存在groupList，则显示功能组数据
                            //var datsea = datas.groupList;
                            //调用用问题
                            for(var i =0;i< datas.groupList.length;i++){
                                //console.log(datas.groupList[i])
                                datas.groupList[i].text = datas.groupList[i].funcgroupName;
                                datas.groupList[i].id = datas.groupList[i].guid;
                                datas.groupList[i].children = true;
                                its.push(datas.groupList[i])
                                //itemss.push(datas.groupList[i])
                                /*$scope.jsonarray = angular.copy(data.groupList);
                                 callback.call(this, itemss);*/
                            }
                        }
                        if(!isNull(datas.rootName)){
                            datas.text = datas.rootName;
                            datas.children = true;
                            datas.id = datas.rootCode;
                            its.push(datas)
                        }
                    }
                    $scope.jsonarray = angular.copy(its);
                    console.log($scope.jsonarray)
                    callback.call(this, $scope.jsonarray);
                })
            },
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
                //console.log("start");
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
    $('#container').on("changed.jstree", function (e, data){
        if(typeof data.node !== 'undefined'){//拿到结点详情
            $scope.dictionaryAdd = data.node.original;
            $scope.biz.item = data.node;//全局点击值传递
            //console.log($scope.biz.item);
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
                    $scope.gridOptions0.data = datas;//把获取到的数据复制给表
                    //console.log($scope.biz.datas);
                    /*      ($scope.$$phase)?null: $scope.$apply();*/
                })
            }else if(data.node.parent == "AC0000"){
                ($scope.$$phase)?null: $scope.$apply();
                $scope.biz.apptab = true;
                $scope.biz.appfund = false;
                $scope.biz.applica = false;
                $scope.biz.appchild = false;
                //调用服务.查询右侧内容
                var subFrom = {};
                $scope.subFrom = subFrom;
                $scope.subFrom.id = data.node.id;
                //console.log(subFrom);
                application_service.appQuery(subFrom).then(function (data) {
                    //console.log(data);
                    var datas = data.retMessage;
                    $scope.gridOptions1.data = datas;
                })
            }else if(data.node.parents[1] == 'AC0000'||!isNull(data.node.original.funcgroupName)){
                $scope.biz.appfund = false;
                $scope.biz.appchild = true;
                $scope.biz.applica = false;
                $scope.biz.apptab = false;
                var subFrom = {};
                $scope.subFrom = subFrom;
                $scope.subFrom.id = data.node.id;
                application_service.appQuery(subFrom).then(function (data) {
                    if(!isNull(data.retMessage.funcList ||!isNull(data.retMessage.groupList))){//查询判断，如果是空，则返回空。
                        var datas = data.retMessage.funcList;//功能列表资源
                        var dates = data.retMessage.groupList;
                        $scope.gridOptions3.data = datas;//把获取到的数据复制给功能表
                        $scope.gridOptions2.data = dates;//把获取到的数据复制给表
                    }else{
                        var datast = [];
                        $scope.gridOptions2.data = datast;
                        $scope.gridOptions3.data = datast;
                    }
                })
            }else if(!isNull(data.node.original.funcName)){
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
    biz.initdata = function(){
        return $scope.myDate;
    }
    var com = [{ field: 'appName', displayName: '应用名称'},
        { field: "appCode", displayName:'应用代码'},
        { field: "appType", displayName:'应用类型'},
        { field: "isopen", displayName:'是否开通'},
        { field: "openDateStr", displayName:'开通日期'},
        { field: "url", displayName:'访问地址'},
        { field: "ipAddr", displayName:'IP'},
        { field: "ipPort", displayName:'端口'},
        { field: "appDesc", displayName:'应用描述'}
    ];
    //自定义点击事件
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
            //console.log($scope.selectRow)
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions0 = initgrid($scope,gridOptions0,biz.initdata(),filterFilter,com,false,f);

    biz.initt = function(num){//查询服务公用方法
        var subFrom = {};
        subFrom.id = num;
        //console.log($scope.subFrom.id)
        application_service.appQuery(subFrom).then(function (data) {
            //console.log(data);
            var datas = data.retMessage;
            $scope.gridOptions0.data = datas;//把获取到的数据复制给表
            ($scope.$$phase)?null: $scope.$apply();
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
                            toastr['error'](data.retCode,data.retMessage+"新增失败!");
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
            if(confirm("确定删除选中的应用吗？删除应用将删除该应用下的所有功能组")){
                var guids = {};
                guids.id = guid;//删除传入的必须是json格式
                application_service.appDel(guids).then(function(data){
                    //console.log(data);
                    if(data.status == "success"){
                        toastr['success'](data.retCode,data.retMessage+"删除成功!");
                        biz.initt(ids);//调用查询服务,传入点击树的id，查询
                        $("#container").jstree().refresh();
                    }else{
                        toastr['error'](data.retCode,data.retMessage+"删除失败!");
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
                    if(!isNull(item)){//如果参数不为空，则就回显
                        $scope.dictionaryAdd = angular.copy(item);
                    }
                    //修改页面代码逻辑
                    $scope.saveDict = function(item){//保存新增的函数
                        item.id = item.guid;
                        application_service.appEdit(item).then(function(data){
                            //console.log(data);
                            if(data.status == "success"){
                                toastr['success']("修改成功！");
                                $modalInstance.close();
                                biz.initt(ids);//调用查询服务,传入点击树的id，查询
                            }else{
                                toastr['error'](data.retCode,data.retMessage+"修改失败!");
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
    var initdata1 = function(){
        return $scope.myDataone;//数据方法
    }
    var com1 = [{ field: 'funcgroupName', displayName: '功能组名称'},
        { field: "groupLevel", displayName:'节点层次'},
        { field: "funcgroupSeq", displayName:'功能组序号'},
        { field: "isleaf", displayName:'是否叶子节点'}
    ];
    //自定义点击事件
    var f1 = function(row){
        if(row.isSelected){
            $scope.selectRow1 = row.entity;
        }else{
            delete $scope.selectRow1;//制空
        }
    }
    $scope.gridOptions1 = initgrid($scope,gridOptions1,initdata1(),filterFilter,com1,false,f1);
    biz.initt1 = function(num){//查询服务公用方法
        var subFrom = {};
        subFrom.id = num;
        application_service.appQuery(subFrom).then(function (data) {
            //console.log(data);
            var datas = data.retMessage;
            $scope.gridOptions1.data = datas;//把获取到的数据复制给表
            ($scope.$$phase)?null: $scope.$apply();
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
                            toastr['error'](data.retCode,data.retMessage+"新增失败!");
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
                                toastr['error'](data.retCode,data.retMessage+"修改失败!");
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
            if(confirm("确定删除选中的应用吗？删除应用将删除该应用下的所有功能组")){
                var guids = {};
                guids.id = guid;//删除传入的必须是json格式
                application_service.groupDel(guids).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除成功!");
                        $("#container").jstree().refresh();//重新刷新树
                        biz.initt1(ids);//调用查询服务//调用查询服务,传入点击树的id，查询
                    }else{
                        toastr['error'](data.retCode,data.retMessage+"删除失败!");
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
        $scope.editsflag = !$scope.editsflag;//让保存取消方法显现,并且让文本框可以输入
    }
    //保存方法
    $scope.biz.functionsave = function () {
        $scope.editsflag = !$scope.editsflag;//让保存取消方法显现
        //调用后台保存逻辑
        toastr['success']("保存成功！");
    }
    $scope.biz.childsEdit = function(){
        $scope.editsflag = !$scope.editsflag;//让保存取消方法显现
    }

    /*子功能组页签内容*/

    //ui-grid 具体配置
    var gridOptions2 = {};
    $scope.gridOptions2 = gridOptions2;
    var initdata2 = function(){
        return $scope.myDatas;//数据方法
    }
    var com2 = [{ field: 'funcgroupName', displayName: '功能组名称'},
        { field: "groupLevel", displayName:'节点层次'},
        { field: "funcgroupSeq", displayName:'功能组序号'},
        { field: "isleaf", displayName:'是否叶子节点'}
    ];

    //自定义点击事件
    var f2 = function(row){
        if(row.isSelected){
            $scope.selectRow2 = row.entity;
        }else{
            delete $scope.selectRow2;
        }
    }
    $scope.gridOptions2 = initgrid($scope,gridOptions2,initdata2(),filterFilter,com2,false,f2);

    biz.initt2 = function(num){//查询服务公用方法
        var subFrom = {};
        subFrom.id = num;
        //console.log($scope.subFrom.id)
        application_service.appQuery(subFrom).then(function (data) {
            //console.log(data);
            var datas = data.retMessage.groupList;
            if(isNull(datas)){
                var datas = [];
                $scope.gridOptions2.data = datas;
            }
            $scope.gridOptions2.data = datas;//把获取到的数据复制给表
            ($scope.$$phase)?null: $scope.$apply();
        })
    }

    //子功能组列表新增功能
    $scope.addchildApp = function(){
        var ids = $scope.biz.item//获取到点击根节点
        //console.log(ids);
        openwindow($modal, 'views/Jurisdiction/childfunctionAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.addchild = function(item){
                    item.guidApp = ids.original.guidApp;//归属应用
                    item.guidParents = ids.id;
                    application_service.groupAdd(item).then(function(data){
                        //console.log(data);
                        if(data.status == "success"){
                            biz.initt2(ids.id);//调用列表刷新方法
                            toastr['success']("保存成功！");
                            $("#container").jstree().refresh();//重新刷新树
                            $modalInstance.close();
                        }else{
                            toastr['error'](data.retCode,data.retMessage+"新增失败!");
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
                                toastr['error'](data.retCode,data.retMessage+"新增失败!");
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
            if(confirm("确定删除选中的功能组？删除功能组将删除该功能下的所有子功能组和资源")){
                //获取选中的guid,传入删除
                var guids = {};
                guids.id = guid;//删除传入的必须是json格式
                application_service.groupDel(guids).then(function(data){
                    //console.log(data);
                    if(data.status == "success"){
                        toastr['success']("删除成功!");
                        biz.initt2(ids.id);//调用查询服务
                        $("#container").jstree().refresh();//重新刷新树
                    }else{
                        toastr['error'](data.retCode,data.retMessage+"删除失败!");
                    }
                })
            }
        }
    }

    var gridOptions3 = {};
    $scope.gridOptions3 = gridOptions3;
    var initdata3 = function(){
        return $scope.appfuncAdd;//数据方法
    }
    var com3 = [{ field: 'funcName', displayName: '功能名称'},
        { field: "funcType", displayName:'功能类型'},
        { field: "ismenu", displayName:'是否定义为菜单'},
        { field: "guidFuncgroup", displayName:'所属功能组'}
    ];
    //自定义点击事件
    var f3 = function(row){
        if(row.isSelected){
            $scope.selectRow3 = row.entity;
        }else{
            delete $scope.selectRow3;//制空
        }
    }
    $scope.gridOptions3 = initgrid($scope,gridOptions3,initdata3(),filterFilter,com3,false,f3);

    biz.initt3 = function(num){//查询服务公用方法
        var subFrom = {};
        subFrom.id = num;
        application_service.appQuery(subFrom).then(function (data){
            //console.log(data);
            if(isNull(data.retMessage.funcList)){
                $scope.gridOptions3.data = [];
                ($scope.$$phase)?null: $scope.$apply();
            }else{
                var datas = data.retMessage.funcList;
                $scope.gridOptions3.data = datas;//把获取到的数据复制给表
                ($scope.$$phase)?null: $scope.$apply();
            }
        })
    }

    //功能列表新增方法
    $scope.addappList = function(){
        var ids = $scope.biz.item.id;//获取点击的根节点guid
        openwindow($modal, 'views/Jurisdiction/afAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){
                    item.guidFuncgroup = ids;
                    application_service.acFuncAdd(item).then(function(data){
                        //console.log(data);
                        if(data.status == "success"){
                            toastr['success']("保存成功！");
                            biz.initt3(ids);//刷新列表
                            $modalInstance.close();
                            $("#container").jstree().refresh();//重新刷新树
                        }else if(data.status == "error"){
                            toastr['error'](data.retCode,data.retMessage+"新增失败!");
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
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条！");
        }else{
            var item = getSel[0];
            openwindow($modal, 'views/Jurisdiction/afAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    var ids = id;
                    $scope.id = ids;
                    if(!isNull(item)){//如果参数不为空，则就回显
                        $scope.appFrom = angular.copy(item);
                    }
                    $scope.add = function(item){
                        item.id = item.guid;
                        //获取到选中的guid，参入item，然后传入修改就可以
                        application_service.acFuncEdit(item).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("修改成功！");
                                biz.initt3(ides);//刷新列表
                                delete $scope.selectRow3;
                                $("#container").jstree().refresh();
                                $modalInstance.close();
                            }else if(data.status == "error"){
                                toastr['error'](data.retCode,data.retMessage+"新增失败!");
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
            if(confirm("确定删除选中的应用吗？删除应用将删除该应用下的所有功能组")){
                var guids = {};
                guids.id = guid;//删除传入的必须是json格式
                application_service.acFuncDel(guids).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除成功!");
                        biz.initt3(ids);//调用查询服务//调用查询服务,传入点击树的id，查询
                        $("#container").jstree().refresh();//重新刷新树
                    }else{
                        toastr['error'](data.retCode,data.retMessage+"删除失败!");
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
    $scope.biz.appedit = function(item){
        $scope.editflag = !$scope.editflag;//让保存取消方法显现,并且让文本框可以输入

    }


    //功能信息保存方法
    $scope.biz.appsave = function (item) {
        //有问题
        //console.log(item)
        application_service.acFuncEdit(item).then(function(data){
            //console.log(data);
            if(data.status == "success"){
                toastr['success']("保存成功！");
            }else{
                toastr['error'](data.retCode,data.retMessage+"保存失败!");
            }
        })
        $scope.editflag = !$scope.editflag;//让保存取消方法显现
    }

    $scope.biz.edit = function(item){
        $scope.editflag = !$scope.editflag;//让保存取消方法显现
    }
    //资源修改保存方法
    $scope.biz.save = function(){
        $scope.editflag = !$scope.editflag;
    }

    //功能行为 逻辑
    $scope.myDataapp = [{'BHVTYPE_CODE': 's', 'BHVTYPE_NAME': '测试类型'}, {'BHVTYPE_CODE': 'a', 'BHVTYPE_NAME': '测试类型11'}]
    var gridOption4 = {};
    $scope.gridOption4 = gridOption4;
    var initdata4 = function(){
        return $scope.myDataapp;//数据方法
    }
    var  com4= [{ field: 'BHVTYPE_CODE', displayName: '行为类型代码'},
        { field: "BHVTYPE_NAME", displayName:'行为类型名称'}
    ];
    //自定义点击事件
    var f4 = function(row){
        if(row.isSelected){
            $scope.selectRow4 = row.entity;
            //console.log($scope.selectRow4)
            $scope.biz.active = true;
        }else{
            delete $scope.selectRow4;//制空
            $scope.biz.active = false;
        }
    }
    $scope.gridOption4 = initgrid($scope,gridOption4,initdata4(),filterFilter,com4,false,f4);

    /*事件行为列表*/
    $scope.myDatasapp = [{BHV_CODE:'TXT1001',BHV_NAME:'测试行为1','ISEFFECTIVE': 'Y'}, {'BHV_CODE':'TXT1002','BHV_NAME':'测试行为2','ISEFFECTIVE': 'N'}]
    var gridOptions5 = {};
    $scope.gridOptions5 = gridOptions5;
    var initdata5 = function(){
        return $scope.myDatasapp;//数据方法
    }
    var  com5= [{ field: 'BHV_NAME', displayName: '行为名称'},
        { field: "BHV_CODE", displayName:'行为代码'},
        { field: "ISEFFECTIVE", displayName:'是否有效'}
    ];
    //自定义点击事件
    var f5 = function(row){
        if(row.isSelected){
            $scope.selectRow5 = row.entity;
            //console.log($scope.selectRow5)
        }else{
            delete $scope.selectRow5;//制空
        }
    }
    $scope.gridOptions5 = initgrid($scope,gridOptions5,initdata5(),filterFilter,com5,true,f5);

    //功能操作行为保存
    $scope.biz.sesave = function(){
        var it = $scope.gridOptions5.getSelectedRows();//多选事件
        if(it.length>0){
            toastr['success']("保存成功");
        }else{
            toastr['error']("请至少选中一条！");
        }
    }
});
