
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
        if(data.status == "success"){
            permiss.appselectApp= data.retMessage;
        }
    })

    //查询应用内容显示
    $scope.permiss.search =function (item) {
           //让下方内容显示
            if(!isNull(item)){
                $scope.permiss.selectApp = true;
                //调用刷新树方法，传入应用
                queryjstree(userid,item);//查询所有树结构
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
                        $scope.permiss.funcselect = true;
                        queryfunlist(data.node.id,userid);//调用查询列表方法
                        blackAdd(data.node.id);//调用新增黑名单
                        delblack(data.node.id);//调用移除黑名单
                    }else{
                        $scope.permiss.funcselect = false;
                    }
                    $scope.$apply();
                });
            }
        })
    }


    //查询操作员在某功能的行为白名单和黑名单
    var queryfunlist= function (funcGuid,userid) {
        var subFrom = {};
        subFrom.userId  = userid;
        subFrom.funcGuid  = funcGuid;
        common_service.post(res.queryOperatorBhvListInFunc,subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
                //白名单
                if(!isNull(datas.whiteList)){
                    $scope.notrolegird.data =datas.whiteList ;
                    $scope.notrolegird.mydefalutData = datas.whiteList;
                    $scope.notrolegird.getPage(1,$scope.notrolegird.paginationPageSize);
                }else{
                    $scope.notrolegird.data =  [];
                    $scope.notrolegird.mydefalutData = [];
                    $scope.notrolegird.getPage(1,$scope.notrolegird.paginationPageSize);
                }
                //黑名单
                if(!isNull(datas.blackList)){
                    $scope.alrolegird.data = datas.blackList;
                    $scope.alrolegird.mydefalutData = datas.blackList;
                    $scope.alrolegird.getPage(1,$scope.alrolegird.paginationPageSize);
                }else{
                    $scope.alrolegird.data =  [];
                    $scope.alrolegird.mydefalutData = [];
                    $scope.alrolegird.getPage(1,$scope.alrolegird.paginationPageSize);
                }

            }
        })
    }

    i18nService.setCurrentLang("zh-cn");

    //白名单表格创建
    var notrolegird = {};
    $scope.notrolegird = notrolegird;
    var not = [
        { field: "bhvtypeName", displayName:'行为类型'},
        { field: "bhvName", displayName:'行为名'},
        { field: "bhvCode", displayName:'行为代码'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.notrolRow = row.entity;
        }else{
            $scope.notrolRow = '';//制空
        }
    }
    $scope.notrolegird = initgrid($scope,notrolegird,filterFilter,not,false,f);
   /* $scope.notrolegird.enableFiltering = false;//禁止有搜索
    $scope.notrolegird.enableGridMenu = false;//禁止有菜单*/

    //添加黑名单逻辑
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
                toastr['error']("请至少选择一个角色进行删除");
                return false;
            }else{
                var dates = $scope.notrolRow;
                var subFrom = {};
                subFrom.guidOperator = operguid;
                subFrom.guidFuncBhv = dates.guid;
                subFrom.authType = 0;
                //调用查询接口,模拟多选
                var tis = [];
                tis.push(subFrom)
                common_service.post(res.addOperatorBhvBlackList,tis).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        queryfunlist(funtguid,userid);//调用刷新类别方法，传入功能guid才行
                    }
                })
            }
        }
    }






    var alrolegird = {};
    $scope.alrolegird = alrolegird;
    var alr = [
        { field: "bhvtypeName", displayName:'行为类型'},
        { field: "bhvName", displayName:'行为名'},
        { field: "bhvCode", displayName:'行为代码'}
    ];
    var f2 = function(row){
        if(row.isSelected){
            $scope.selectRow2 = row.entity;
        }else{
            delete $scope.selectRow2;//制空
           $scope.selectRow2 = '';//制空
        }
    }
    $scope.alrolegird = initgrid($scope,alrolegird,filterFilter,alr,true,f2);
    // $scope.alrolegird.enableFiltering = false;//禁止有搜索
    // $scope.alrolegird.enableGridMenu = false;//禁止有菜单


    //移除黑名单逻辑
    var delblack = function (funtguid) {
        $scope.permiss.del = function () {
            var dats = $scope.alrolegird.getSelectedRows();
            if(!dats.length> 0){
                toastr['error']("请至少选择一个角色");

            }else{
                var tis = [];
                for(var i =0;i<dats.length;i++){
                    var subFrom = {};
                    subFrom.guidOperator = operguid;
                    subFrom.guidFuncBhv = dats[i].guid;
                    tis.push(subFrom)
                }
                console.log(tis)
                common_service.post(res.deleteOperatorBhvBlackList,tis).then(function(data){
                    console.log(data)
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        queryfunlist(funtguid,userid);//调用刷新类别方法，传入功能guid才行
                    }
                })
            }
        }
    }

});



















