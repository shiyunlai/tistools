/**
 * Created by wangbo on 2017/11/8
 */

MetronicApp.controller('dataEntity_controller', function ($filter, $scope, $rootScope, common_service,filterFilter, $state,$modal,$uibModal, $http, $timeout,$interval,i18nService) {
    //定义公共对象
    var dataEnt = {};
    $scope.dataEnt = dataEnt;
    //表格内容
    var dataEntFrom = {}
    $scope.dataEntFrom = dataEntFrom;
    i18nService.setCurrentLang("zh-cn");//表格翻译
    //刷新树方法
    dataEnt.refresh = function () {
        $("#container").jstree().refresh();
    }


    //搜索方法
    $scope.searchitem = "";
    var to = false;
    $('#q').keyup(function () {
        if(to) { clearTimeout(to); }
        $('#container').jstree().load_all();
        to = setTimeout(function () {
            var v = $('#q').val();
            $('#container').jstree(true).search(v);
        }, 250);
    });
    //清空
    dataEnt.clear = function () {
        $scope.searchitem = "";
    }


    //树结构数据模拟
    var aymssys = [
            {
                "id": "1",
                "text": "数据实体",
                "state": {
                    "opened": true,          //展示第一个层级下面的node
                    "disabled": true         //该根节点不可点击
                },
                "children":
                    [
                        {
                            "id": "2",
                            "text": "ABF应用",
                            "type":"app",
                            "children":
                                [
                                    {
                                        "id": "3",
                                        "text": "表",
                                        "type":'biao',
                                        "children": [
                                            {
                                                "id": "4",
                                                "text": "机构-OM_ORG",
                                                "type":'biao',
                                            }
                                        ]
                                    },
                                    {
                                        "id": "31",
                                        "text": "视图",
                                        "type":'shitu',
                                        "children": [
                                            {
                                                "id": "41",
                                                "text": "机构-OM_ORG_VIEW",
                                                "type":'shitu'
                                            }
                                        ]
                                    },
                                    {
                                        "id": "311",
                                        "text": "查询实体",
                                        "type":'shiti',
                                        "children": [
                                            {
                                                "id": "411",
                                                "text": "机构-OM_ORG_SDO",
                                                "type":'shiti',
                                            }
                                        ]
                                    },
                                    {
                                        "id": "3111",
                                        "text": "内存对象类型",
                                        "type":'neicun',
                                        "children": [
                                            {
                                                "id": "4111",
                                                "text": "机构-OM_ORG_RAM",
                                                "type":'neicun'
                                            }
                                        ]
                                    }
                                ]
                        },
                        {
                            "id": "6",
                            "text": "测试应用",
                            "type":'app',
                            "children":
                                [
                                    {
                                        "id": "61",
                                        "text": "Test",
                                        "type":'biao',
                                        "children": [
                                            {
                                                "id": "611",
                                                "text": "表类型1",
                                                "type":'biao',
                                                "children":
                                                    [
                                                        {
                                                            "id": "6111",
                                                            "text": "表类型1子类型",
                                                            "type":'biao',
                                                        }
                                                    ]
                                            }
                                        ]
                                    }
                                ]
                        }
                    ]
            }
        ]

    //定义树结构右击
    //树自定义右键功能(根据类型判断)
    var items = function customMenu(node) {
        var judge = node.original;
        var control;
        if(judge.type == 'app'){
            var it = {
                "刷新":{
                    "label":"刷新",
                    "action":function(data){
                        $("#container").jstree().refresh();
                    }
                }
            }
            return it;
        }
        if(judge.type == 'biao'|| judge.type == 'shitu' ||judge.type == 'shiti' || judge.type == 'neicun'){
            var it = {
                "新增实体":{
                    "id":"createc",
                    "label":"新增实体",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);//得到值
                        var objGuid = obj.original.id;
                        creatFrom(objGuid)//传入guid
                    }
                },
                "删除实体":{
                    "label":"删除实体",
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

    };


    //树结构生成
    $("#container").jstree({
        "core": {
            "themes": {
                "responsive": false
            },
            "check_callback": true,
            'data':aymssys
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
        "contextmenu": {'items': items},
        'dnd': {
            'is_draggable': function (node) {
                //用于控制节点是否可以拖拽.
                var node = node[0];
                if (node.id == "99999" || node.id.indexOf("GW") == 0) {
                    return false;
                } else {
                    return true;
                }
            },
            'dnd_start': function () {
            },
        },
        'search': {
            show_only_matches: true,
        },
        'callback': {
            move_node: function (node) {
            }
        },
        'sort': function (a, b) {
            //排序插件，会两者比较，获取到节点的order属性，插件会自动两两比较。
            return this.get_node(a).original.sortNo > this.get_node(b).original.sortNo ? 1 : -1;
        },

        "plugins": ["dnd", "state", "types", "contextmenu","sort","search"]
    }).bind("select_node.jstree", function (e, data) {
        if (typeof data.node !== 'undefined') {//拿到结点详情
            $scope.dataEnt.item = data.node.original;//绑定内容给全局
            if(data.node.original.type == 'app'){
                $scope.dataEnt.dataEnis = false;
            }else{
                $scope.dataEnt.dataEnis = true;//让右侧内容显示
            }
            ($scope.$$phase) ? null : $scope.$apply();
        }
    });


    //新增表结构实体类型
    function  creatFrom(id) {
        openwindow($modal, 'views/dataEntity/creatEntity.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.title = '新增实体属性'
                //删除引用表关系 还未改成统一使用 dataEnt
                var qualityRatingExt = [];//定义数组
                $scope.qualityRatingExt = qualityRatingExt;
                var item = {tableName : '',columeName : '',isDel:''};
                qualityRatingExt.push(item);

                //删一个
                $scope.delQualityRatingExt = function (index) {
                    qualityRatingExt.splice(index, 1);
                };
                //加一个
                $scope.addQualityRatingExt = function () {
                    var item = {tableName : '',columeName : '',isDel:''};
                    qualityRatingExt.push(item);
                };

                $scope.add = function(){
                    $modalInstance.dismiss('cancel');
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            })
    }

    
    //编辑实体
    dataEnt.dataEntEdit =function (item) {
      /*  $scope.copyEdit = angular.copy(item)
        $scope.editflag = !$scope.editflag;//让保存取消方法显现,并且让文本框可以输入*/
        openwindow($modal, 'views/dataEntity/creatEntity.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
            $scope.title = '编辑实体属性'
                //删除引用表关系 还未改成统一使用 dataEnt
                var qualityRatingExt = [];//定义数组
                $scope.qualityRatingExt = qualityRatingExt;
                var item = {tableName : '',columeName : '',isDel:''};
                qualityRatingExt.push(item);

                //删一个
                $scope.delQualityRatingExt = function (index) {
                    qualityRatingExt.splice(index, 1);
                };
                //加一个
                $scope.addQualityRatingExt = function () {
                    var item = {tableName : '',columeName : '',isDel:''};
                    qualityRatingExt.push(item);
                };

                $scope.add = function(){
                    //刷新树结构，进行保存后台数据
                    $modalInstance.dismiss('cancel');
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            })
    }

    //当前实体信息
    function Current () {
        //放在dataEnt数组中，这样就好拿数据了
        dataEnt.qualityRatingExt = [];
        var item = {tableName : '',columeName : '',isDel:''};
        dataEnt.qualityRatingExt.push(item);
        //数组从后台拿 即可,目前先写空，保证效果
        //删一个
        $scope.delQualityRatingExt = function (index) {
            dataEnt.qualityRatingExt.splice(index, 1);
        };
        //加一个
        $scope.addQualityRatingExt = function () {
            var item = {tableName : '',columeName : '',isDel:''};
            dataEnt.qualityRatingExt.push(item);
        };

        $scope.dataEntAdd = function (item) {
            $scope.editflag = !$scope.editflag;//让保存取消方法显现,并且让文本框可以输入
        }

    }



    //tab页签控制
    //默认当前实体打开
    dataEnt.dqst = true;
    Current();//默认调用当前实体信息
    dataEnt.loadgwdata = function (type) {
        if(type == 0){
            dataEnt.dqst = true;
            dataEnt.sssx = false;
            dataEnt.sjfw = false;//隐藏
            Current();//调用当前实体
        }else if (type == 1) {
            dataEnt.dqst = false;
            dataEnt.sssx = true;
            dataEnt.sjfw = false;//隐藏
        }else if(type == 2){
            dataEnt.sssx = false;
            dataEnt.sjfw = true;//显示
            dataEnt.dqst = false;
        }
    }


//实体属性表格
    var gridAttributes = {};
    $scope.gridAttributes = gridAttributes;
    var grid = [
        { field: "fieldName", displayName:'属性名称'},
        { field: "fieldType", displayName:'字段类型'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.gridAt = row.entity;
        }else{
            $scope.gridAt = '';//制空
        }
    }
    $scope.gridAttributes = initgrid($scope,gridAttributes,filterFilter,grid,false,f);
    //模拟获取数据渲染表格
    function getData() {
        var permit= [
            {'fieldName':'测试','fieldType':0001},
            {'fieldName':'测试','fieldType':0002},
            {'fieldName':'测试','fieldType':0003},
            {'fieldName':'测试','fieldType':0004},
            {'fieldName':'测试','fieldType':0005},
            {'fieldName':'测试','fieldType':0006},
            {'fieldName':'测试','fieldType':0007},
            {'fieldName':'测试','fieldType':0008},
            {'fieldName':'测试','fieldType':0009},
            {'fieldName':'测试','fieldType':0000},
            {'fieldName':'测试','fieldType':0001},
            {'fieldName':'测试','fieldType':0002}
        ]
        $scope.gridAttributes.data = permit;
        $scope.gridAttributes.mydefalutData = permit;
        $scope.gridAttributes.getPage(1,$scope.gridAttributes.paginationPageSize);
    }
    getData();
    //新增属性
    $scope.dataDet_add = function () {
        openwindow($modal, 'views/dataEntity/Entityattribute.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.title = '新增实体属性'
                $scope.add = function(item){
                    console.log(item)
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }
    //修改属性
    $scope.dataDet_edit = function () {
        var getSel = $scope.gridAttributes.getSelectedRows();//拿到数据
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来修改！");
        }else{
            openwindow($modal, 'views/dataEntity/Entityattribute.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.title = '修改实体属性'
                    $scope.add = function(item){
                        toastr['success']("修改成功！");
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                })
        }
    }

    //删除属性
    $scope.dataDet_delete = function () {
        var getSel = $scope.gridAttributes.getSelectedRows();//拿到数据
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来删除！");
        }else{
            toastr['success']("删除成功！");
        }
    }

    //查看详情
    $scope.dataDett_details = function () {
        var getSel = $scope.gridAttributes.getSelectedRows();//拿到数据
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来查看详情！");
        }else{
            openwindow($modal, 'views/dataEntity/Entityattribute.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.title = '查看实体属性'
                    $scope.add = function(item){
                        toastr['success']("修改成功！");
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                })
        }
    }
    //实体范围权限表格
    var gridPermissions = {};
    $scope.gridPermissions = gridPermissions;
    var gridPer = [
        { field: "bhvName", displayName:'行为名称'},
        { field: "bhvCode", displayName:'行为代码'}
    ];
    var fPer = function(row){
        if(row.isSelected){
            $scope.gridPer = row.entity;
        }else{
            $scope.gridPer = '';//制空
        }
    }
    $scope.gridPermissions = initgrid($scope,gridPermissions,filterFilter,gridPer,false,fPer);

    //模拟获取数据渲染表格
    function getDatas() {
        var permit= [
            {'bhvName':'范围','bhvCode':0001},
            {'bhvName':'范围','bhvCode':0002},
            {'bhvName':'范围','bhvCode':0003},
            {'bhvName':'范围','bhvCode':0004},
            {'bhvName':'范围','bhvCode':0005},
            {'bhvName':'范围','bhvCode':0006},
            {'bhvName':'范围','bhvCode':0007},
            {'bhvName':'范围','bhvCode':0008},
            {'bhvName':'范围','bhvCode':0009},
            {'bhvName':'范围','bhvCode':0000},
            {'bhvName':'范围','bhvCode':0001},
            {'bhvName':'范围','bhvCode':0002}
        ]
        $scope.gridPermissions.data = permit;
        $scope.gridPermissions.mydefalutData = permit;
        $scope.gridPermissions.getPage(1,$scope.gridPermissions.paginationPageSize);
    }
    getDatas();


    //新增范围
    $scope.dataDett_add = function () {
        openwindow($modal, 'views/dataEntity/Entityauthority.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.title = '新增范围数据权限'

                var dataEnt = {};
                $scope.dataEnt = dataEnt;
                $scope.dataEnt.qualityRatingExt = [];
                $scope.dataEnt.cardArray = [];
                var items = []
                var item = {};
                items.push(item);
                dataEnt.cardArray.push(items);

                //条件删除
                $scope.delQualityRatingExt = function (d,index) {
                    d.splice(index, 1);
                };

                //条件新增
                $scope.addQualityRatingExt = function (index) {
                    console.log(index)
                    var item = {};
                    dataEnt.cardArray[index].push(item)
                };

                //卡片删一个
                $scope.carddelQualityRatingExt = function (index) {
                    dataEnt.cardArray.splice(index, 1);
                };
                //卡片加一个
                $scope.cardaddQualityRatingExt = function () {
                    var items = []
                    var item = {};
                    items.push(item);
                    dataEnt.cardArray.push(items);
                };
                $scope.add = function(){
                    console.log(dataEnt)
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            })
    }
    //修改范围
    $scope.dataDett_edit = function () {
        var getSel = $scope.gridPermissions.getSelectedRows();//拿到数据
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来修改！");
        }else{
            toastr['success']("修改成功！");
        }
    }

    //删除范围
    $scope.dataDett_delete = function () {
        var getSel = $scope.gridPermissions.getSelectedRows();//拿到数据
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来删除！");
        }else{
            toastr['success']("删除成功！");
        }
    }


});
