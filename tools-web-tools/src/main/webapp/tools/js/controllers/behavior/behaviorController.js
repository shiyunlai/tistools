/**
 * Created by wangbo on 2017/6/10.
 */
angular.module('MetronicApp').controller('behavior_controller', function($rootScope, $scope, $http,i18nService,$modal,filterFilter,behavior_service) {
    /* 行为定义配置*/
    var beha = {};
    $scope.beha = beha;





    /*定义行为类型列表结构*/
    i18nService.setCurrentLang("zh-cn");
    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var initdata = function(){
        return $scope.myData;//数据方法
    }
    var com = [{ field: 'bhvtypeCode', displayName: '行为类型代码'},
        { field: "bhvtypeName", displayName:'行为类型名称'}
    ];

    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
            $scope.beha.active = true;
            var ids = $scope.selectRow.guid;
            beha.funact(ids);
        }else{
            delete $scope.selectRow;//制空
            $scope.beha.active = false;
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,initdata(),filterFilter,com,false,f);

    var subFrom = {};
    //查询功能类型数据
    behavior_service.functypequery(subFrom).then(function(data){
        var datas = data.retMessage;
        $scope.gridOptions.data = datas;
        if(data.status == "success"){
        }else{
            toastr['error'](data.retCode,data.retMessage+"初始化失败!");
        }
    })


    beha.initt = function(){//查询服务公用方法
        var subFrom = {};
        //console.log($scope.subFrom.id)
        behavior_service.functypequery(subFrom).then(function(data){
            var datas = data.retMessage;
            $scope.gridOptions.data = datas;
            if(data.status == "success"){
            }else{
                toastr['error'](data.retCode,data.retMessage+"初始化失败!");
            }
        })
    }

    //新增类型功能
    $scope.beha.beAdd = function(item){
        openwindow($modal, 'views/behavior/behavtypeAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){//保存新增的函数
                    behavior_service.functypeAdd(item).then(function(data){
                        if(data.status == "success"){
                            toastr['success']("保存成功！");
                            beha.initt()
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

    //修改类型功能
    $scope.beha.beEdit = function(id){
        if($scope.selectRow){
            var its = $scope.selectRow;
            openwindow($modal, 'views/behavior/behavtypeAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    if(!isNull(its)){//如果参数不为空，则就回显
                        $scope.behaFrom = angular.copy(its);
                    }
                    var guid = its.guid;
                    $scope.id=id;
                    //修改页面代码逻辑
                    $scope.add = function(item){//保存新增的函数
                        item.id = guid;
                        behavior_service.functypeEdit(item).then(function(data){
                            console.log(item);
                            console.log(data);
                            if(data.status == "success"){
                                toastr['success']("修改成功！");
                                beha.initt()
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
        }else{
            toastr['error']("请至少选中一条类型进行修改！");
        }
    }

    //删除类型功能
    $scope.beha.besDelete = function(){
        if(!$scope.selectRow){ //如果是多选，只需要判断是否为空就可以了
            toastr['error']("请至少选择一条记录进行删除！","SORRY！");
        }else{
            var guid = $scope.selectRow.guid;
            //获取选中的guid,传入删除
            if(confirm("确定对应行为类型吗?删除对应行为类型会使下面的所有功能行为都消失")){
                var guids = {};
                guids.id = guid;//删除传入的必须是json格式
                behavior_service.functypeDel(guids).then(function(data){
                    if(data.status == "success"){
                        beha.initt()
                        toastr['success'](data.retCode,data.retMessage+"删除成功!");
                    }else{
                        toastr['error'](data.retCode,data.retMessage+"删除失败!");
                    }
                })
            }
        }
    }

    /*事件行为列表*/
    $scope.myDatas = [{'BHV_CODE': 'TXT002', 'BHV_NAME': '搜索行为'}, {'BHV_CODE': 'TXT003', 'BHV_NAME': '查询行为'}]
    var gridOptions1 = {};
    $scope.gridOptions1 = gridOptions1;
    var initdata1 = function(){
        return $scope.myDatas;//数据方法
    }
    var com1 = [{ field: 'bhvCode', displayName: '行为代码'},
        { field: "bhvName", displayName:'行为名称'}

    ];
    var f1 = function(row){
        if(row.isSelected){
            $scope.selectRow1 = row.entity;
        }else{
            delete $scope.selectRow1;//制空
        }
    }
    $scope.gridOptions1 = initgrid($scope,gridOptions1,initdata1(),filterFilter,com1,true,f1);


    //查询行为
    beha.funact = function(id){
        var subFrom = {};
        subFrom.id = id;
        //查询功能类型数据
        behavior_service.queryBhvDefByBhvType(subFrom).then(function(data){
            var datas = data.retMessage;
            $scope.gridOptions1.data = datas;
            if(data.status == "success"){
            }else{
                toastr['error'](data.retCode,data.retMessage+"初始化失败!");
            }
        })
    }

    beha.initt1 = function(ids){//查询服务公用方法
        var subFrom = {};
        subFrom.id = ids;
        behavior_service.queryBhvDefByBhvType(subFrom).then(function(data){
            console.log(data);
            var datas = data.retMessage;
            $scope.gridOptions1.data = datas;
            if(data.status == "success"){
            }else{
                toastr['error'](data.retCode,data.retMessage+"初始化失败!");
            }
        })
    }

    //新增行为
    $scope.beha.beacAdd = function () {
        var guid = $scope.selectRow.guid;
        openwindow($modal, 'views/behavior/beactionAdd.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                //修改页面代码逻辑
                $scope.add = function(item){//保存新增的函数
                    item.guid = guid;
                    behavior_service.funactAdd(item).then(function(data){
                        if(data.status == "success"){
                            toastr['success']("保存成功！");
                            beha.initt1(guid)
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

    //修改行为方法
    $scope.beha.beacEdit = function(id){
        var it = $scope.gridOptions1.getSelectedRows();//多选事件
        var ids = it[0].guid;
        if(it.length == 1 || $scope.selectRow1){
            openwindow($modal, 'views/behavior/beactionAdd.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.behaFrom = angular.copy(it[0]);
                    $scope.id=id;
                    //修改页面代码逻辑
                    $scope.add = function(item){//保存新增的函数
                        item.id = ids;//要修改的guid
                        console.log(item);
                        behavior_service.funactEdit(item).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("修改成功！");
                                beha.initt1(ids)
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
        }else{
            toastr['error']("请选择一条数据进行修改！");
        }
    }
    //删除方法
    $scope.beha.beDelete = function(){
        var it = $scope.gridOptions1.getSelectedRows();//多选事件
        var ids = it[0].guidBehtype;
        console.log(ids);
        if(it.length>0){
            if(confirm('确定删除选中的行为吗?')){
                var guids = {};
                guids.id = it[0].guid;//删除传入的必须是json格式
                behavior_service.funactDel(guids).then(function(data){
                    if(data.status == "success"){
                        beha.initt1(ids)
                        toastr['success'](data.retCode,data.retMessage+"删除成功!");
                    }else{
                        toastr['error'](data.retCode,data.retMessage+"删除失败!");
                    }
                })
            }
        }else{
            toastr['error']("请至少选中一条数据进行删除！");
        }
    }



});