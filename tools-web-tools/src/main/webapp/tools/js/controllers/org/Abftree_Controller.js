/**
 * Created by gaojie on 2017/5/9.
 */
angular.module('MetronicApp').controller('abftree_controller', function($rootScope, $scope,abftree_service, $http, $timeout,i18nService,filterFilter,uiGridConstants,$uibModal,$state) {
    $scope.$on('$viewContentLoaded', function() {
        // initialize core components
        App.initAjax();
    });
    var abftree = {};
    $scope.abftree = abftree;
    //控制是否是修改状态
    var editflag = false;
    $scope.editflag = editflag;
    //控制页签跳转
    var flag = {};
    $scope.flag = flag;
    //下级机构页签控制
    var xjjg = false;
    flag.xjjg = xjjg;
    //员工信息页签控制
    var ygxx = false;
    flag.ygxx = ygxx;
    //机构信息展示页
    var xqxx = true;
    flag.xqxx = xqxx;
    //岗位页签跳转控制
    var gwflag = {};
    $scope.gwflag = gwflag;
    //岗位详情
    var gwxx = false;
    gwflag.gwxx = gwxx;
    //岗位员工列表
    var gwyg = false;
    gwflag.gwyg = gwyg;
    //岗位应用列表
    var gwyy = false;
    gwflag.gwyy = gwyy;
    //下级岗位列表
    var xjgw = false;
    gwflag.xjgw = xjgw;
    //岗位权限列表;
    var gwqx = false;
    gwflag.gwqx = gwqx;
    //首页清空
    $scope.flag.index = false;

    //机构,岗位页签切换
    var tabflag = true;//true为机构详情
    $scope.tabflag = tabflag;
    //生成公共方法
    initController($scope,abftree,"abftree",abftree,filterFilter);
    var item = {};
    $scope.item = item;
    abftree.item = item;
    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageContentWhite = true;
    $rootScope.settings.layout.pageBodySolid = false;
    $rootScope.settings.layout.pageSidebarClosed = false;
    //树过滤
    // $("#s").submit(function(e) {
    //     e.preventDefault();
    //     $("#container").jstree(true).search($("#q").val());
    // });

    //树自定义右键功能
    var items = function customMenu(node) {
        // The default set of all items
        var control;
        console.log(node);
        if(node.parent == "#"){
            var it = {
                "新建菜单":{
                    "id":"create",
                    "label":"新建根机构",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        openwindow($uibModal, 'views/org/addrootorg_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建机构实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //根机构标识
                                subFrom.flag = "root";
                                //生成机构代码
                                var next = true;
                                $scope.next = next;
                                $scope.skip = function () {
                                    if(isNull(subFrom.orgDegree) || isNull(subFrom.AREA)){
                                        toastr['error']("请填写相关信息!");
                                        return false;
                                    }
                                    //调用服务生成机构代码
                                    abftree_service.initcode(subFrom).then(function (data) {
                                        // if(data.status == "error"){
                                        //     toastr['error']( "！");
                                        // }else{
                                        //     subFrom.orgCode = data.orgCode;
                                        //     toastr['success'](data.retMessage);
                                        //     $scope.next = !next;
                                        // }
                                        subFrom.orgCode = "00001";
                                        toastr['success'](data.retMessage);
                                        $scope.next = !next;
                                    })

                                }
                                //处理新增机构父机构
                                subFrom.guidParents = null;
                                //增加方法
                                $scope.add = function (subFrom) {
                                    //TODO.新增逻辑
                                    abftree_service.addorg(subFrom).then(function (data) {
                                        if(data.status == "success"){
                                            toastr['success'](data.retMessage);
                                        }else{
                                            toastr['error'](data.retMessage);
                                        }
                                        $("#container").jstree().refresh();
                                        $scope.cancel();
                                    });
                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            }
                        )
                    }
                }
            };
            return it;
        }else if(node.original.orgCode.indexOf("GW") != 0){
            var it = {
                "新建菜单":{
                    "id":"create",
                    "label":"新建子机构",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        openwindow($uibModal, 'views/org/addorg_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建机构实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                subFrom.flag = "child";
                                //生成机构代码
                                var next = true;
                                $scope.next = next;
                                $scope.skip = function () {
                                    if(isNull(subFrom.orgDegree) || isNull(subFrom.AREA)){
                                        toastr['error']("请填写相关信息!");
                                        return false;
                                    }
                                    //调用服务生成机构代码
                                    abftree_service.initcode(subFrom).then(function (data) {
                                        // if(data.status == "error"){
                                        //     toastr['error']( "！");
                                        // }else{
                                        //     subFrom.orgCode = data.orgCode;
                                        //     toastr['success'](data.retMessage);
                                        //     $scope.next = !next;
                                        // }
                                        subFrom.orgCode = "00002";
                                        toastr['success'](data.retMessage);
                                        $scope.next = !next;
                                    })

                                }
                                //处理新增机构父机构
                                subFrom.guidParents = obj.original.guid;
                                //增加方法
                                $scope.add = function (subFrom) {
                                    //TODO.新增逻辑
                                    abftree_service.addorg(subFrom).then(function (data) {
                                        if(data.status == "success"){
                                            toastr['success'](data.retMessage);
                                        }else{
                                            toastr['error'](data.retMessage);
                                        }
                                        $("#container").jstree().refresh();
                                        $scope.cancel();
                                    });
                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            }
                        )
                    }
                },

                "删除菜单":{
                    "label":"删除机构",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if(confirm("确定要删除此菜单？删除后不可恢复。")){
                            //TODO.删除逻辑
                        }
                    }
                },
                "拷贝菜单":{
                    "label":"拷贝机构",
                    "action":function (node) {
                        var inst = jQuery.jstree.reference(node.reference),
                            obj = inst.get_node(node.reference);
                        var og  = $('#container').jstree(true).copy_node(obj,obj);
                        // console.log(obj)
                    }
                },

                "粘贴菜单":{
                    "label":"粘贴机构",
                    "action":function (node) {
                        var inst = jQuery.jstree.reference(node.reference),
                            obj = inst.get_node(node.reference);
                        var og  = $('#container').jstree(true).paste(node);
                        console.log(og)
                    }
                },
            }
            return it;

        }else{
            var it = {
                "新建菜单":{
                    "id":"create",
                    "label":"新建岗位",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        openwindow($uibModal, 'views/org/addPosition_window.html', 'lg',
                            function ($scope, $modalInstance) {
                                //创建机构实例
                                var subFrom = {};
                                $scope.subFrom = subFrom;
                                //判断是根岗位还是子岗位
                                if(obj.id.indexOf("GW") == 0){
                                    //处理新增机构父机构
                                    subFrom.guidParents = "";
                                    subFrom.guidOrg = obj.original.guid;
                                }else{
                                    subFrom.guidParents = obj.original.guid;
                                    subFrom.guidOrg = obj.original.guidOrg;
                                }
                                //增加方法
                                $scope.add = function (subFrom) {
                                    //TODO.新增逻辑
                                    abftree_service.addposit(subFrom).then(function (data) {
                                        if(data.status == "success"){
                                            toastr['success'](data.retMessage);
                                        }else{
                                            toastr['error'](data.retMessage);
                                        }
                                        $("#container").jstree().refresh();
                                        $scope.cancel();
                                    });
                                }
                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            }
                        )
                    }
                },

                "删除菜单":{
                    "label":"删除岗位",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if(confirm("确定要删除此菜单？删除后不可恢复。")){
                            //TODO.删除逻辑
                        }
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
            'data' : function (obj, callback) {
                var jsonarray = [];
                $scope.jsonarray = jsonarray;
                var subFrom = {};
                subFrom.id = obj.id;
                if(!isNull(obj.original)){
                    subFrom.guidOrg = obj.original.guid;

                }else{
                    subFrom.guidOrg = "";
                }

                abftree_service.loadmaintree(subFrom).then(function (data) {
                    if(isNull(data)){

                    }else if(isNull(data[0].orgName)){
                        for(var i = 0 ;i < data.length ; i++){
                            data[i].text = data[i].positionName;
                            data[i].children = true;
                            data[i].id = data[i].guid;
                            data[i].startDate = FormatDate(data[i].startDate);
                            data[i].createTime = FormatDate(data[i].createTime);
                            data[i].endDate = FormatDate(data[i].endDate);
                            data[i].lastUpdate = FormatDate(data[i].lastUpdate);
                        }
                    }else{
                        for(var i = 0 ;i < data.length ; i++){
                            data[i].text = data[i].orgName;
                            data[i].children = true;
                            data[i].id = data[i].orgCode;
                            data[i].startDate = FormatDate(data[i].startDate);
                            data[i].createTime = FormatDate(data[i].createTime);
                            data[i].endDate = FormatDate(data[i].endDate);
                            data[i].lastUpdate = FormatDate(data[i].lastUpdate);
                        }
                    }

                    $scope.jsonarray = angular.copy(data);
                    callback.call(this, $scope.jsonarray);
                })
            }
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
        "contextmenu":{'items':items},
        'dnd': {
            'dnd_start': function () {
                console.log("start");
            },
            'is_draggable':function (node) {
                //用于控制节点是否可以拖拽.
                console.log(node)
                return true;
            }
        },
        'search':{
            show_only_matches:true,

        },
        'callback' : {
            move_node:function (node) {
                console.log(node)
            }
        },

        "plugins" : [ "dnd", "state", "types","search","contextmenu" ]
    }).bind("copy.jstree", function (node,e, data ) {
        console.log(e);
        console.log(data);
        console.log(node);
    }).bind("paste.jstree",function (a,b,c,d) {
        console.log(a);
        console.log(b);
        console.log(c);
        console.log(d);
    }).bind("move_node.jstree",function (e,data) {
        if(confirm("确认要移动此机构吗?")){
            //TODO.
        }else{
            // data.inst.refresh(data.inst._get_parent(data.rslt.oc));
            return false;
        }
        console.log(e);
        console.log(data);
    }).bind("dnd_stop.vakata",function (e,data) {
        console.log(data);
    }).bind("changed.jstree", function (e, data) {
        if(typeof data.node !== 'undefined'){//拿到结点详情
            // console.log(data.node.original.id.indexOf("@"));
            $scope.abftree.item = {};
            console.log(data.node.original);
            if(data.node.original.id.indexOf("POSIT") == 0){
                for(var i in $scope.flag){
                    flag[i] = false;
                }
                for(var i in $scope.gwflag){
                    gwflag[i] = false;
                }
                $scope.flag.index = true;
                $scope.gwflag.gwxx = true;
                $scope.tabflag = false;
                $scope.abftree.item = data.node.original;
            }else if(data.node.original.id.indexOf("9999") == 0){
                for(var i in $scope.flag){
                    flag[i] = false;
                }
                for(var i in $scope.gwflag){
                    gwflag[i] = false;
                }
                $scope.flag.index = false;
            }else if(data.node.original.id.indexOf("GW") == 0){

            }else {
                for(var i in $scope.gwflag){
                    gwflag[i] = false;
                }
                for(var i in $scope.flag){
                    flag[i] = false;
                }
                $scope.flag.index = true;
                $scope.flag.xqxx = true;
                $scope.tabflag = true;
                $scope.abftree.item = data.node.original;
            }

            $scope.$apply();
        }
    });




    //jstree 自定义筛选事件
    //筛选字段
    $scope.searchitem = "";
    //清空
    abftree.clear = function () {
        $scope.searchitem = "";
    }
    //控制2个树显示标识,true为默认值,false为筛选状态
    var showtree = true;
    $scope.showtree = showtree;
    $scope.$watch('searchitem', function(newValue, oldValue) {
        console.log(newValue)
        if(isNull(newValue)){
            $scope.showtree = true;
        }else{
            console.log(9999)
            $scope.showtree = false;
            //筛选重组树
            $("#searchtree").data('jstree', false).empty().jstree({
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
                        subFrom.searchitem = newValue;
                        abftree_service.loadsearchtree(subFrom).then(function (data) {
                            if(isNull(data[0].orgName)){
                                for(var i = 0 ;i < data.length ; i++){
                                    data[i].text = data[i].positionName;
                                    data[i].children = true;
                                    data[i].id = data[i].guid;
                                }
                            }else{
                                for(var i = 0 ;i < data.length ; i++){
                                    data[i].text = data[i].orgName;
                                    data[i].children = true;
                                    data[i].id = data[i].orgCode;
                                }
                            }

                            $scope.jsonarray = angular.copy(data);
                            callback.call(this, $scope.jsonarray);
                        })
                    }
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
                "contextmenu":{'items':items},
                'dnd': {
                    'dnd_start': function () {
                        console.log("start");
                    },
                    'is_draggable':function (node) {
                        //用于控制节点是否可以拖拽.
                        console.log(node)
                        return true;
                    }
                },
                'search':{
                    show_only_matches:true,

                },
                'callback' : {
                    move_node:function (node) {
                        console.log(node)
                    }
                },

                "plugins" : [ "dnd", "state", "types","search","contextmenu" ]
            }).bind("changed.jstree", function (e, data) {
                if(typeof data.node !== 'undefined'){//拿到结点详情
                    // console.log(data.node.original.id.indexOf("@"));
                    $scope.abftree.item = data.node.original;
                    console.log(data.node.original);
                    if(data.node.original.id.indexOf("@") == 0){
                        for(var i in $scope.flag){
                            flag[i] = false;
                        }
                        for(var i in $scope.gwflag){
                            gwflag[i] = false;
                        }
                        $scope.gwflag.gwxx = true;
                        $scope.tabflag = false;
                    }else {
                        for(var i in $scope.gwflag){
                            gwflag[i] = false;
                        }
                        for(var i in $scope.flag){
                            flag[i] = false;
                        }
                        $scope.flag.xqxx = true;
                        $scope.tabflag = true;
                    }

                    $scope.$apply();
                }
            });
        }
    },true);
    //用于修改的实例
    var position = {};
    $scope.position = position;


    abftree.test = function () {
        console.log(1)
    }

    //删除
    abftree.delete = function () {
        if(confirm("确认要删除此机构吗?")){
            //TODO.删除逻辑
        }
    }

    //启用
    abftree.enable = function () {
        //TODO.启用逻辑
        console.log($scope.abftree.item)
    }

    //修改
    abftree.edit = function () {
        $scope.editflag = !$scope.editflag;
        $scope.position = angular.copy($scope.abftree.item);
    }

    //覆盖searchN方法
    abftree.searchN = function () {

    }
    //保存
    abftree.save = function () {
        $scope.editflag = !$scope.editflag;
        //TODO
        //保存逻辑
    }
    //点击页签,加载指定数据
    abftree.loaddata = function (type) {
        //type加载的数据,0--下级机构,1--员工信息,2--岗位信息,999-机构详情
        if(type == 0){
            console.log($scope.abftree.item.guid)
            $scope.flag.xqxx = false;
            $scope.flag.xjjg = true;
            $scope.flag.ygxx = false;
            $scope.flag.qxxx = false;
            //todo
            //通过GUID查询下级机构信息
        }else if(type == 1){

            $scope.flag.xqxx = false;
            $scope.flag.xjjg = false;
            $scope.flag.ygxx = true;
            $scope.flag.qxxx = false;
            //todo
            //通过GUID查询员工列表

        }else if(type == 2){
            $scope.flag.xqxx = false;
            $scope.flag.xjjg = false;
            $scope.flag.ygxx = false;
            $scope.flag.qxxx = false;
            //todo
        }else if(type == 3){
            $scope.flag.xqxx = false;
            $scope.flag.xjjg = false;
            $scope.flag.ygxx = false;
            $scope.flag.qxxx = true;
            //todo
            //传递参数
            var abc = $scope.abftree.item.guid
            $scope.$broadcast('to-child', abc);
        }else if(type == 999){
            $scope.flag.xqxx = true;
            $scope.flag.xjjg = false;
            $scope.flag.ygxx = false;
            $scope.flag.qxxx = false;
        }
    }
    //点击页签,加载指定岗位信息
    abftree.loadgwdata = function (type) {
        //type 0--岗位详情,1--员工列表,2--岗位应用列表,3--下级岗位列表,4--岗位权限列表
        //点击加载数据,此处需要4个方法.
        //将机构页面隐藏
        for(var i in $scope.flag){
            $scope.flag[i] = false;
        }
        if(type == 0){
            for(var i in $scope.gwflag){
                $scope.gwflag[i] = false;
            }
            $scope.gwflag.gwxx = true;
        }else if (type == 1){
            for(var i in $scope.gwflag){
                $scope.gwflag[i] = false;
            }
            console.log($scope.abftree.item);
            $scope.gwflag.gwyg = true;
            //生成岗位员工列表
            var gwemp = {};
            $scope.gwemp = gwemp;
            //获取员工数据.//todo
            $scope.a = [{员工姓名:"张三",员工编号:"233",员工性别:"男"},
                {员工姓名:"李四",员工编号:"244",员工性别:"男"}];
            var itd = function () {
                return  $scope.a;
            }
            //自定义点击事件
            var select = function () {

            }
            $scope.gwemp = initgrid($scope, $scope.gwemp,itd(), filterFilter,null,false,select);
        }else if (type == 2){
            for(var i in $scope.gwflag){
                $scope.gwflag[i] = false;
            }
            $scope.gwflag.gwyy = true
            //生成岗位应用列表
            var gwApplication = {};
            $scope.gwApplication = gwApplication;
            //获取岗位应用数据.//todo
            $scope.b = [{应用名称:"开发",应用功能:"啊实打实大师的"},
                {应用名称:"其他",应用功能:"啊实打实大师的"}];
            var itd = function () {
                return  $scope.b;
            }
            //自定义点击事件
            var select = function () {

            }
            $scope.gwApplication = initgrid($scope, $scope.gwApplication,itd(), filterFilter,null,false,select);
        }else if (type == 3){
            for(var i in $scope.gwflag){
                $scope.gwflag[i] = false;
            }
            $scope.gwflag.xjgw = true
            //生成下级岗位列表
            var gwJunior = {};
            $scope.gwJunior = gwJunior;
            //获取下级岗位数据.//todo
            $scope.c = [{岗位名称:"开发",岗位信息:"股份供热"},
                {岗位名称:"其他",岗位信息:"大声道"}];
            var itd = function () {
                return  $scope.c;
            }
            //自定义点击事件
            var select = function () {

            }
            $scope.gwJunior = initgrid($scope, $scope.gwJunior,itd(), filterFilter,null,false,select);
        }else if (type == 4){
            for(var i in $scope.gwflag){
                $scope.gwflag[i] = false;
            }
            $scope.gwflag.gwqx = true;
        }
    }




    i18nService.setCurrentLang("zh-cn");
    //ui-grid
    $scope.myData = [{name: "Moroni", age: 50},
        {name: "Tiancum", age: 43},
        {name: "Jacob", age: 27},
        {name: "Nephi", age: 29},
        {name: "Enos", age: 34}];
    //ui-grid 具体配置
    //下级机构列表
    // $scope.gridOptions1 = {
    //     data: 'myData',
    //     //-------- 分页属性 ----------------
    //     enablePagination: true, //是否分页，默认为true
    //     enablePaginationControls: true, //使用默认的底部分页
    //     paginationPageSizes: [10, 15, 20], //每页显示个数可选项
    //     paginationCurrentPage:1, //当前页码
    //     paginationPageSize: 10, //每页显示个数
    //     //paginationTemplate:"<div></div>", //自定义底部分页代码
    //     totalItems : 0, // 总数量
    //     useExternalPagination: true,//是否使用分页按钮
    //     //是否多选
    //     // multiSelect:false,
    //     onRegisterApi: function(gridApi) {
    //         $scope.gridApi = gridApi;
    //         //分页按钮事件
    //         gridApi.pagination.on.paginationChanged($scope,function(newPage, pageSize) {
    //             if(getPage) {
    //                 getPage(newPage, pageSize);
    //             }
    //         });
    //         //行选中事件
    //         $scope.gridApi.selection.on.rowSelectionChanged($scope,function(row,event){
    //             if(row){
    //                 $scope.selectRow1 = row.entity;
    //                 console.log($scope.selectRow1)
    //                 console.log(event)
    //             }
    //         });
    //
    //     }
    // };
    var gridOptions1 = {};
    $scope.gridOptions1 = gridOptions1;
    var initdata1 = function () {
        return $scope.myData;
    }
    $scope.gridOptions1 = initgrid($scope,$scope.gridOptions1,initdata1(),filterFilter);
    //测试global方法
    var gridOptions2 = {};
    $scope.gridOptions2 = gridOptions2;
    var initdata = function () {
        return $scope.myData;
    }
    var com = {};
    var f = function (a,b) {
        console.log(a)
        console.log(b)
    }
    $scope.gridOptions2 = initgrid($scope,$scope.gridOptions2,initdata(),filterFilter,null,true,f);


    //机构下新增人员信息
    abftree.addyg = function () {
        console.log($scope.abftree.item.guid);
        var id = $scope.abftree.item.guid;
        openwindow($uibModal, 'views/org/addemp_window.html', 'lg',
            function ($scope, $modalInstance) {
                //创建员工实例
                var subFrom = {};
                $scope.subFrom = subFrom;
                //Emp
                var emp = {};
                $scope.emp = emp;
                //处理新增员工-机构关系表
                subFrom.guidParents = id;
                //增加方法
                emp.add = function (subFrom) {
                    console.log(subFrom)
                    //TODO.新增逻辑
                    toastr['success']( "新增成功！");
                    $scope.cancel();
                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }

    //机构下编辑人员信息
    abftree.edityg = function () {
        var it = $scope.gridOptions2.selectRow1;
        var a = $scope.gridOptions2.getSelectedRows();
        console.log(a);
        if(isNull(a)||a.length>1){
            toastr['error']( "请选择一条记录！");
            return false;
        }
        openwindow($uibModal, 'views/org/addemp_window.html', 'lg',
            function ($scope, $modalInstance) {
                //创建员工实体
                var subFrom = it;
                $scope.subFrom = subFrom;
                //标识以区分新增和编辑
                //Emp
                var emp = {};
                $scope.emp = emp;
                //修改方法
                emp.add = function (subFrom) {
                    console.log(subFrom)
                    toastr['success']( "修改成功！");
                    $scope.cancel();
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            });
    }

    //机构下删除人员信息
    abftree.deleteyg = function () {
        var it = $scope.selectRow;
        if(isNull($scope.selectRow)){
            toastr['error']( "请选择一条记录！");
            return false;
        }else{
            //TODO
            if(confirm("确认要删除此人员信息吗?")){
                //TODO.删除逻辑
                toastr['success']( "删除成功！");
            }

        }
    }

    //下级机构新增
    abftree.addxjjg = function () {
        var id = $scope.abftree.item.guid
        openwindow($uibModal, 'views/org/addorg_window.html', 'lg',
            function ($scope, $modalInstance) {
                //创建机构实例
                var subFrom = {};
                $scope.subFrom = subFrom;
                //处理新增机构父机构
                subFrom.guidParents = id;
                //增加方法
                $scope.add = function (subFrom) {
                    //TODO.新增逻辑
                    console.log(subFrom)
                    toastr['success']( "新增成功！");
                    $scope.cancel();
                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }

    //编辑下级机构信息
    abftree.editxjjg = function () {
        var it = $scope.selectRow1;
        if(isNull(it)){
            toastr['error']( "请选择一条记录！");
            return false;
        }
        openwindow($uibModal, 'views/org/addorg_window.html', 'lg',
            function ($scope, $modalInstance) {
                //创建员工实体
                var subFrom = it;
                $scope.subFrom = subFrom;
                //标识以区分新增和编辑

                //修改方法
                $scope.add = function (subFrom) {
                    console.log(subFrom)
                    toastr['success']( "修改成功！");
                    $scope.cancel();
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            });
    }
    //下级机构删除按钮
    abftree.deletexjjg = function () {
        var a = getSelectedCount();
        console.log(a);
    }

    //岗位下操作点击事件
    var gw = {};
    $scope.gw = gw;

    //为岗位新增人员.传入当前机构GUID
    gw.addemp = function (guid) {
        //打开通用列表窗口
        openwindow($uibModal, 'views/org/addsearhgrid_window.html', 'lg',
            function ($scope, $modalInstance) {
                //设置标题栏
                $scope.title = "人员";
                //实例化列表
                var commonGrid = {};
                $scope.commonGrid = commonGrid;
                //加载数据方法
                var initd = function () {
                    //todo
                    return a = [{人员名称:"卡卡",人员性别:"男",工号:"007"},
                        {人员名称:"强强",人员性别:"男",工号:"001"},
                        {人员名称:"稳稳",人员性别:"男",工号:"002"},
                        {人员名称:"恩恩",人员性别:"男",工号:"003"}];
                }
                //单击事件
                var sel = function () {

                }
                $scope.commonGrid = initgrid($scope,commonGrid,initd(),filterFilter,null,true,sel);

                //保存方法
                $scope.add = function (subFrom) {
                    console.log(subFrom)
                    toastr['success']( "修改成功！");
                    $scope.cancel();
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            });
    }

});

