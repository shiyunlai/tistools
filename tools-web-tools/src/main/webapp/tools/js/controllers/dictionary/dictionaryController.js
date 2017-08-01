/**
 * Created by wangbo on 2017/7/4.
 */
angular.module('MetronicApp').controller('dictionary_controller', function($rootScope, $scope, $http,i18nService,$modal,filterFilter,$uibModal,dictonary_service){

    //tab页切换
    var dictflag = {};
    $scope.dictflag = dictflag;
    var zdgl = false;
    dictflag.zdgl = zdgl;
    //功能组列表
    var zddr = false;
    dictflag.zddr = zddr;
    //初始化tab展现
    $scope.dictflag.zdgl = true;
    initController($scope, dictflag,'dictflag',dictflag,filterFilter)//初始化一下，才能使用里面的方法
    dictflag.loadgwdata = function (type) {
        if(type == 0){
            $scope.dictflag.zdgl = true;
            $scope.dictflag.zddr = false;
        }else if (type == 1){
            $scope.dictflag.zdgl = false;
            $scope.dictflag.zddr = true;
        }
    }

    // 公用对象
    var dictconfig = {};
    $scope.dictconfig = dictconfig;
    //表格渲染
    i18nService.setCurrentLang("zh-cn");

    var gridOptions0 = {};
    $scope.gridOptions0 = gridOptions0;
    var com = [{ field: 'dictKey', displayName: '业务字典'},
        { field: "dictName", displayName:'字典名称'}
    ];
    //自定义点击事件
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
            if($scope.selectRow.fromType == 0){
                $scope.dictconfig.show =true;
                $scope.dictconfig.toshow = false;
            }else{
                $scope.dictconfig.show =false;
                $scope.dictconfig.toshow = true;
            }
            dictflag.inittx($scope.selectRow.guid)
        }else{
            delete $scope.selectRow;//制空
            $scope.dictconfig.show =false;
            $scope.dictconfig.toshow =false;
        }
    }
    $scope.gridOptions0 = initgrid($scope,gridOptions0,filterFilter,com,false,f);


    //查询所有业务字典
    var subFrom = {};
    dictonary_service.querySysDictList(subFrom).then(function(data){
        var datas = data.retMessage;
        dictflag.dictnameL = datas;
        if(data.status == "success"){
            $scope.gridOptions0.data =  datas;
            $scope.gridOptions0.mydefalutData = datas;
            $scope.gridOptions0.getPage(1,$scope.gridOptions0.paginationPageSize);
        }else{
            toastr['error']('查询失败'+'<br/>'+data.retMessage);
        }
    })
    //查询公共方法
    dictflag.initt = function(){//查询服务公用方法
        var subFrom = {};
        dictonary_service.querySysDictList(subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
                $scope.gridOptions0.data =  datas;
                $scope.gridOptions0.mydefalutData = datas;
                $scope.gridOptions0.getPage(1,$scope.gridOptions0.paginationPageSize);
            }else{
                toastr['error'](data.retCode,data.retMessage+"初始化失败!");
            }
        })
    }

    //新增业务字典
    $scope.show_win=function(){
        openwindow($uibModal, 'views/dictionary/dictnameAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                /*控制按钮显示*/
                $scope.one = true;
                $scope.two = true;
                $scope.three = true;
                var dictFrom = {};
                $scope.dictFrom =dictFrom;
                dictFrom.seqno =0;//默认
                dictFrom.fromType =0;//首先给个默认
                $scope.dictlist = dictflag.dictnameL;
                $scope.add = function(item){//保存新增的函数
                    var subFrom = {};
                    subFrom = item;
                    dictonary_service.createSysDict(subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']( "新增成功！");
                            $modalInstance.close();
                            dictflag.initt();//调用刷新列表
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
    //修改业务字典
    $scope.show_edit=function(id){
        if($scope.selectRow){
            var datasRow = $scope.selectRow;
            openwindow($uibModal, 'views/dictionary/dictnameAdd.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    $scope.dictlist = dictflag.dictnameL;
                    var idds = id;
                    $scope.id = idds;//编辑id
                    $scope.dictFrom = datasRow;//渲染数据
                    if(datasRow.fromType=='0'){
                        $scope.one = true;
                        $scope.two = false;
                        $scope.three = false;
                    }else if(datasRow.fromType=='1'){
                        $scope.one = false;
                        $scope.two = true;
                        $scope.three = false;
                    }else{
                        $scope.one = false;
                        $scope.two = false;
                        $scope.three = true;
                    }
                    $scope.add = function(item){//保存新增的函数
                        var subFrom = {};
                        subFrom.guid = datasRow.guid;
                        subFrom = item;
                        dictonary_service.editSysDict(subFrom).then(function(data){
                            if(data.status == "success"){
                                toastr['success']( "修改成功！");
                                //dictflag.initt();//调用刷新列表
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
        }else{
            toastr['error']("请至少选中一条进行修改！");
        }
    }

    //删除业务字典
    $scope.showDelAll=function(){
        if($scope.selectRow){
            var guid = $scope.selectRow.guid;
            if(confirm("确定删除选中的业务字典？删除业务字典将删除该业务字典下的所有的子字典和字典项")){
                var subFrom = {};
                $scope.subFrom=subFrom;
                subFrom.dictGuid = guid;
                dictonary_service.deleteSysDict(subFrom).then(function(data){
                    if(data.status == "success"){
                        toastr['success']( "删除成功！");
                        dictflag.initt();//调用刷新列表
                        $scope.dictconfig.show =false;
                        $scope.dictconfig.toshow =false;
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }else{
            toastr['error']("请至少选中一条进行修改！");
        }
    }

    //刷新
    $scope.dictconfig_refresh = function(){
   /*     dictconfig.initt2(ids);//刷新*/
        toastr['success']("刷新成功！");
    }

    //导出
    $scope.dictconfig_down = function(){
        if($scope.selectRow){
            toastr['success']("导出一条成功！");
        }else{
            toastr['success']("导出全部数据成功！");
        }
    }



    //数据字典项列表渲染
    var gridOptions1 = {};
    $scope.gridOptions1 = gridOptions1;
    var com1 = [
        //{ field: 'guidDict', displayName: '隶属业务字典'},
        { field: "itemName", displayName:'字典项名称'},
        { field: "itemValue", displayName:'字典项'},
        { field: "sendValue", displayName:'实际值'}
    ];
    //自定义点击事件
    var f1 = function(row){
        if(row.isSelected){
            $scope.selectRow1 = row.entity;
        }else{
            delete $scope.selectRow1;//制空
        }
    }
    $scope.gridOptions1 = initgrid($scope,gridOptions1,filterFilter,com1,true,f1);


    //多表或单表数据
    var gridOptions2 = {};
    $scope.gridOptions2 = gridOptions2;
    var com2 = [
        { field: "itemName", displayName:'字典项名称'},
        { field: "itemValue", displayName:'字典项'},
    ];
    //自定义点击事件
    var f2 = function(row){
        if(row.isSelected){
            $scope.selectRow1 = row.entity;
        }else{
            delete $scope.selectRow1;//制空
        }
    }
    $scope.gridOptions2 = initgrid($scope,gridOptions2,filterFilter,com2,false,f2);


    //查询字典对应字典项
    dictflag.inittx = function(id){//查询服务公用方法
        var subFrom = {};
        subFrom.dictGuid = id;
        dictonary_service.querySysDictItemList(subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
                $scope.gridOptions1.data =  datas;
                $scope.gridOptions1.mydefalutData = datas;
                $scope.gridOptions1.getPage(1,$scope.gridOptions1.paginationPageSize);
                $scope.gridOptions2.data =  datas;
                $scope.gridOptions2.mydefalutData = datas;
                $scope.gridOptions2.getPage(1,$scope.gridOptions2.paginationPageSize);
            }else{
                toastr['error'](data.retCode,data.retMessage+"初始化失败!");
            }
        })
    }

    //新增字典项
    $scope.dict_win = function(){
        var guidParents = $scope.selectRow;
        openwindow($uibModal, 'views/dictionary/dicttypeAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                var dictFrom = {};
                $scope.dictFrom = dictFrom;
                dictFrom.guidDict = guidParents.dictName;
                dictFrom.seqno =0;//默认
                $scope.add = function(item){//保存新增的函数
                    var subFrom={};
                    $scope.subFrom =subFrom;
                    subFrom =item;
                    subFrom.guidDict = guidParents.guid;
                    dictonary_service.createSysDictItem(subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']( "修改成功！");
                            $modalInstance.close();
                            dictflag.inittx(guidParents.guid);
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

    //修改字典项
    $scope.dict_edit = function(id){
        var getSel = $scope.gridOptions1.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条进行修改！");
        }else{
            var items = getSel[0]
            openwindow($uibModal, 'views/dictionary/dicttypeAdd.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    if(!isNull(items)){//如果参数不为空，则就回显
                        $scope.dictFrom = angular.copy(items);
                    }
                    var ids = id;
                    $scope.id = ids;
                    $scope.add = function(item){//保存修改的函数
                        var subFrom={};
                        $scope.subFrom =subFrom;
                        subFrom =item;
                        subFrom.guid = items.guid;
                        dictonary_service.editSysDictItem(subFrom).then(function(data){
                            if(data.status == "success"){
                                toastr['success']( "修改成功！");
                                dictflag.inittx(items.guidDict);
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
    }

    //删除字典项
    $scope.dictDelAll = function(){
        var getSel = $scope.gridOptions1.getSelectedRows();
        var fun = [];
        if(isNull(getSel) || getSel.length==0){
            toastr['error']("请至少选中一条数据进行删除！");
        }else{
            if(confirm("确定删除改字典项吗？")){
                /*for(var i =0 ; i<getSel.length;i++){
                    fun.push(getSel[i].guid);
                }*/
                var subFrom = {};
                $scope.subFrom=subFrom;
                subFrom.dictItemGuid = getSel[0].guid;
                //subFrom.dictItemGuid = fun; //批量删除
                dictonary_service.deleteSysDictItem(subFrom).then(function(data){
                    if(data.status == "success"){
                        dictflag.inittx(getSel[0].guidDict);
                        dictflag.initt();//调用刷新列表
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }
    }

});


angular.module('MetronicApp').controller('dictitwos_controller', function($rootScope, $scope, $http,$modal,filterFilter,FileUploader){

    //上传业务字典
    $scope.typeFile = function openVersion() {
        openwindow( $modal,'views/dictionary/fillwindow.html','lg',function ($scope, $modalInstance) {
            var uploader = $scope.uploader = new FileUploader({
                url: manurl+'/DictController/importDict',//保存的地址，后台的地址
                mime_types: [
                    {title : "war", extensions : "war"},
                    {title : "zip", extensions : "zip"},
                    {title : "pkg", extensions : "pkg"}
                ] //定义上传的格式

            })
            uploader.filters.push({ //过滤器，控制上传数量
                name: 'customFilter',//过滤器的名字
                fn: function(item /*{File|FileLikeObject}*/ , options) {
                    return this.queue.length < 10;
                }
            });
            uploader.onSuccessItem = function(item, response, status, headers) {
                if(response.returnCode == '000000'){
                    version.item.fileName = response.returnMessage;
                } else if(response.returnCode != '000000'){
                    uploader.queue[i].isSuccess = false;
                    uploader.queue[i].isCancel = false;
                    uploader.queue[i].isError = true;
                } else {
                    uploader.queue[i].isSuccess = false;
                    uploader.queue[i].isCancel = false;
                    uploader.queue[i].isError = true;
                }
            }

            $scope.ok = function () {
                $modalInstance.close();
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        });
    };

   /* //上传字典项
    $scope.projectFile = function openVersion() {
        openwindow( $modal,'views/dictionary/fillwindow.html','lg',function ($scope, $modalInstance) {
            var uploader = $scope.uploader = new FileUploader({
                /!*
                 url: myport+'/GovernorService/UPLOADFILE',//保存的地址，后台的地址
                 mime_types: [
                 {title : "war", extensions : "war"},
                 {title : "zip", extensions : "zip"},
                 {title : "pkg", extensions : "pkg"}
                 ]*!/ //定义上传的格式
            })

            uploader.filters.push({
                name: 'customFilter',
                fn: function(item /!*{File|FileLikeObject}*!/ , options) {
                    return this.queue.length < 10;
                }
            });
            uploader.onSuccessItem = function(item, response, status, headers) {
                if(response.returnCode == '000000'){
                    version.item.fileName = response.returnMessage;
                } else if(response.returnCode != '000000'){
                    uploader.queue[i].isSuccess = false;
                    uploader.queue[i].isCancel = false;
                    uploader.queue[i].isError = true;
                } else {
                    uploader.queue[i].isSuccess = false;
                    uploader.queue[i].isCancel = false;
                    uploader.queue[i].isError = true;
                }
            }

            $scope.ok = function () {
                $modalInstance.close();
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        });
    };*/

})

