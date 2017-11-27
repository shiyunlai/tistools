/**
 * Created by wangbo on 2017/7/4.
 */
angular.module('MetronicApp').controller('dictionary_controller', function($rootScope, $scope,$state,$filter, $http,i18nService,$modal,filterFilter,common_service,$timeout,$uibModal,uiGridConstants,dictonary_service){

    var res = $rootScope.res.dictonary_service;//页面所需调用的服务

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
    //默认查询的是所有业务字典
    var dictFrom = {};
    $scope.dictFrom = dictFrom;
    dictFrom.Alldict = 'All';


    var gridOptions0 = {};
    $scope.gridOptions0 = gridOptions0;
    var com = [{ field: 'dictKey', displayName: '业务字典'},
        { field: "dictName", displayName:'字典名称'},
        { field: "dictType", displayName:'字典类型',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.dictType | translateConstants :\'DICT_TYPE\') + $root.constant[\'DICT_TYPE-\'+row.entity.dictType]}}</div>',
            //配置搜索下拉框
            filter:{
                //term: '0',//默认搜索那项
                type: uiGridConstants.filter.SELECT,
                selectOptions: [{ value: 'A', label: '应用级'}, { value: 'S', label: '系统级' }]
            }},
        { field: "fromType", displayName:'字典项来源',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.fromType | translateConstants :\'DICT_SYS_DICTFROMTYPE\') + $root.constant[\'DICT_SYS_DICTFROMTYPE-\'+row.entity.fromType]}}</div>'}
    ];

    //自定义点击事件
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
            jstreeshu();//调用树节点
            if($scope.selectRow.fromType == 0){
                $scope.dictconfig.show =true;
            }else{
                $scope.dictconfig.show =false;
            }
            dictflag.inittx($scope.selectRow.guid)
        }else{
            $scope.selectRow = '';//制空
            $scope.dictconfig.show =false;
        }
    }
    $scope.gridOptions0 = initgrid($scope,gridOptions0,filterFilter,com,false,f);

//查询所有业务字典
    var  queryAlldict = function(){
        //监控单选框，配置数据
        $scope.$watch('dictFrom.Alldict',function(newvalue,oldvalue){
            if(newvalue == 'Root'){
                var subFrom = {};
                subFrom.isQueryRoot = 'Y';
                dictonary_service.querySysDictList(subFrom).then(function(data){
                    var datas = $filter('Arraysort')(data.retMessage);//调用管道排序
                    if(data.status == "success"){
                        dictflag.dictnameL = datas;
                        $scope.gridOptions0.data =  datas;
                        $scope.gridOptions0.mydefalutData = datas;
                        $scope.gridOptions0.getPage(1,$scope.gridOptions0.paginationPageSize);
                    }else{
                        toastr['error']('查询失败'+'<br/>'+data.retMessage);
                    }
                })
            }else{
                var subFrom = {};
                subFrom.isQueryRoot = 'N';
                dictonary_service.querySysDictList(subFrom).then(function(data){
                    var datas = $filter('Arraysort')(data.retMessage);//调用管道排序
                    if(data.status == "success"){
                        dictflag.dictnameL = datas;
                        $scope.gridOptions0.data =  datas;
                        $scope.gridOptions0.mydefalutData = datas;
                        $scope.gridOptions0.getPage(1,$scope.gridOptions0.paginationPageSize);
                    }else{
                        toastr['error']('查询失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        })

    }
    queryAlldict();//调用查询业务字典

    //查询公共方法
    dictflag.initt = function(pages){//查询服务公用方法
        var subFrom = {};
        dictonary_service.querySysDictList(subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
                $scope.dictconfig.show =false;
                $scope.gridOptions0.data =  datas;
                $scope.gridOptions0.mydefalutData = datas;
                $scope.gridOptions0.refresh($timeout);//调用刷新方法
                if(!isNull(pages)){
                    var str = $scope.gridApi.pagination.getPage();
                    $scope.gridOptions0.getPage(str,$scope.gridOptions0.paginationPageSize);
                    angular.forEach($scope.gridOptions0.data, function(data,index){
                        if(data.dictKey == pages){
                            $timeout(function() {
                                //选中之前的节点
                                if ($scope.gridApi.selection.selectRow) {
                                    $scope.gridApi.selection.selectRow($scope.gridOptions0.data[index]);
                                }
                            },50);
                        }
                    });
                }
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
                dictFrom.dictType ='A';
                $scope.dictlist = dictflag.dictnameL;//渲染业务字典数据
                $scope.add = function(item){//保存新增的函数
                    var subFrom = {};
                    subFrom = item;
                    dictonary_service.createSysDict(subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']( "新增成功！");
                            $modalInstance.close();
                            queryAlldict();//重新查询业务字典
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
                    var tsxtcom = datasRow.guidParents;//业务字典
                    if(!isNull(tsxtcom)){
                        var tsxttable = datasRow.fromTable;//来自于表回选
                        $timeout(function () {
                            $(".test123").select2("val",tsxtcom);//渲染表格数据
                            $(".dictfrom").select2("val",tsxttable);//渲染表格数据
                        },50);
                    }
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
                dictdelete(guid);//删除业务字典
            }
        }else{
            toastr['error']("请至少选中一条进行修改！");
        }
    }

    //查看概况
    dictflag.histroy = function () {
        var getSel = $scope.gridOptions0.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条进行查看！");
        }else{
            $state.go("loghistory",{id:getSel[0].guid});//跳转到历史页面
        }
    }
    /*删除业务字典提取*/
    var  dictdelete = function(id){
        var subFrom = {};
        $scope.subFrom=subFrom;
        subFrom.dictGuid = id;
        dictonary_service.deleteSysDict(subFrom).then(function(data){
            if(data.status == "success"){
                toastr['success']( "删除成功！");
                $scope.dictconfig.show =false;
                dictflag.initt();//调用刷新列表
                queryAlldict();//重新查询业务字典
            }else{
                toastr['error']('删除失败'+'<br/>'+data.retMessage);
            }
        })
    }
    /* 修改业务字典提取*/
    var dictedit = function(id,item){
        openwindow($uibModal, 'views/dictionary/dictnameAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                //调用查询业务字典详情接口
                var dictKey = item.itemValue;
                var subFrom = {};
                subFrom.dictKey = dictKey;
                $scope.dictlist = dictflag.dictnameL;
                var res = $rootScope.res.dictonary_service;//页面所需调用的服务
                common_service.post(res.queryDict,subFrom).then(function(data){
                    if(data.status == "success"){
                        var dates = data.retMessage;
                        console.log(dates);
                        var idds = id;
                        $scope.id = idds;//编辑id
                        $scope.dictFrom = dates;//根据获得的内容渲染数据
                         var tsxtcom = dates.guidParents;//业务字典
                         var tsxttable = dates.fromTable;//来自于表回选
                         $timeout(function () {
                             $(".test123").select2("val",tsxtcom);//渲染表格数据
                             $(".dictfrom").select2("val",tsxttable);//渲染表格数据
                         },50);
                        if(dates.fromType=='0'){
                            $scope.one = true;
                            $scope.two = false;
                            $scope.three = false;
                        }else if(dates.fromType=='1'){
                            $scope.one = false;
                            $scope.two = true;
                            $scope.three = false;
                        }else{
                            $scope.one = false;
                            $scope.two = false;
                            $scope.three = true;
                        }
                    }else{
                        toastr['error']('查询失败'+'<br/>'+data.retMessage);
                    }
                })
                $scope.add = function(datas){//保存新增的函数
                    var subFrom = {};
                    subFrom.guid = item.dictguid;
                    subFrom = datas;
                    dictonary_service.editSysDict(subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']( "修改成功！");
                            dictflag.initt();//调用刷新列表
                            //jstreeshu()//重新查询树结构
                            $modalInstance.close();
                            $scope.dictconfig.show =false;
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
    /* 新增字典项提取*/
    var creadictitem = function(item){
        var guidParents = item;
        openwindow($uibModal, 'views/dictionary/dicttypeAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                //隐藏的显示
                $scope.one = true;
                $scope.two = true;
                $scope.showspan = true;
                var dictFrom = {};
                $scope.dictFrom = dictFrom;
                dictFrom.guidDict = guidParents.dictName;
                dictFrom.seqno =0;//默认
                $scope.dictlist = dictflag.dictnameL;//渲染业务字典数据
                var ser = {};
                //业务字典新增跳转逻辑
                $scope.dictcreat = function(dictvalue) {
                    $modalInstance.dismiss('cancel');
                    openwindow($uibModal, 'views/dictionary/dictnameAdd.html', 'lg',// 弹出页面
                        function ($scope, $modalInstance) {
                            /*控制按钮显示*/
                            $scope.one = true;
                            var dictFrom = {};
                            $scope.dictFrom =dictFrom;
                            dictFrom.seqno =0;//默认
                            dictFrom.fromType =0;//首先给个默认
                            var querydict = {};
                            dictonary_service.querySysDictList(querydict).then(function(data){ //渲染业务字典循环
                                var datas = data.retMessage;
                                if(data.status == "success"){
                                    $scope.dictlist = datas;//渲染业务字典数据
                                }
                            })
                            $scope.add = function(item){//保存新增的函数
                                var subFrom = {};
                                subFrom = item;
                                dictonary_service.createSysDict(subFrom).then(function(data){
                                    if(data.status == "success"){
                                        toastr['success']( "新增成功！");
                                        $modalInstance.close();
                                        dictvalue.dictKey = subFrom.dictKey;
                                        dictvalue.dictType = subFrom.dictType;
                                        dictvalue.itemsValue = guidParents.itemValue;
                                        backdictitem(dictvalue);
                                    }else{
                                        toastr['error']('新增失败'+'<br/>'+data.retMessage);
                                    }
                                })
                            }
                            $scope.cancel = function () {
                                $modalInstance.dismiss('cancel');
                                backdictitem(dictvalue);
                            };
                        }
                    )
                }
                ser.dictKey = guidParents.itemValue;
                common_service.post(res.queryDict,ser).then(function(data){
                    dictFrom.dictparents = data.retMessage.guid;//保存当前字典项对应的业务字典guid
                })
                $scope.add = function(item){
                    //调用查询服务，通过dictkey查询业务字典信息
                    var subFrom={};
                    $scope.subFrom =subFrom;
                    subFrom =item;
                    subFrom.guidDict = dictFrom.dictparents;
                    dictonary_service.createSysDictItem(subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']( "新增成功！");
                            $modalInstance.close();
                            //dictflag.inittx(guidParents.guid);//重新查询业务字典
                            jstreeshu()//重新查询树结构
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

    /* 设置默认值提取*/
    var editdefault = function(item){
        console.log(item)
        if(confirm("确定设置"+item.text+"为默认值吗")){
                var serFrom = {};
                serFrom.dictKey = item.guidDict;
                serFrom.itemValue = item.sendValue;
                common_service.post(res.setDefaultDictValue,serFrom).then(function(data){
                    if(data.status == "success"){
                        toastr['success']( "设置默认值成功！");
                        dictflag.initt();//调用刷新列表
                    }else{
                        toastr['success']( "设置默认值失败！");
                    }

                })

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }
    }
    /*增完业务字典返回字典项*/
    var backdictitem = function(dictvalue){
        var guidParents = angular.copy(dictvalue);
        openwindow($uibModal, 'views/dictionary/dicttypeAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                $scope.one = true;
                $scope.showspan = false;
                var dictFrom = {};
                $scope.dictFrom = dictFrom;
                dictFrom.itemType = 'dict';//按钮选择
                dictFrom.itemName = dictvalue.itemName;//回显
                dictFrom.sendValue = dictvalue.sendValue;//回显
                dictFrom.seqno = dictvalue.seqno;//回显
                var querydict = {};
                dictonary_service.querySysDictList(querydict).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        $scope.dictlist = datas;//渲染业务字典数据
                        if(!isNull(dictvalue.dictKey)){
                            var tsxtcom = dictvalue.dictKey;
                            $timeout(function () {
                                $(".test456").select2("val",tsxtcom);//渲染表格数据
                            },50);
                        }
                    }
                })
                $scope.add = function(item){
                    //调用查询服务，通过dictkey查询业务字典信息
                    var subFrom={};
                    $scope.subFrom =subFrom;
                    subFrom =item;
                    subFrom.guidDict = dictvalue.dictparents;//赋值
                    dictonary_service.createSysDictItem(subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']( "新增成功！");
                            $modalInstance.close();
                            console.log(guidParents)
                            dictflag.initt(guidParents.itemsValue);//调用刷新列表
                            jstreeshu()//重新查询树结构
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

    /*删除字典项-批量*/
    var delectdictitem = function(id){
        openwindow($uibModal, 'views/dictionary/delectdictitem.html', 'lg',
            function ($scope, $modalInstance) {
                var gridOptions = {};
                $scope.gridOptions = gridOptions;
                var com = [
                    { field: "itemName", displayName:'字典项名称'}
                ];
                //自定义点击事件
                var f1 = function(row){
                    if(row.isSelected){
                        $scope.selectRow = row.entity;
                    }else{
                        delete $scope.selectRow;//制空
                    }
                }
                $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f1);
                //查询业务字典
                var subFrom = {};
                subFrom.dictKey = id;
                dictonary_service.queryDictItemListByDictKey(subFrom).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        $scope.gridOptions.data =  datas;
                        $scope.gridOptions.mydefalutData = datas;
                        $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
                    }else{
                        toastr['error'](data.retCode,data.retMessage+"初始化失败!");
                    }
                    //选择导入条件
                    $scope.importAdd = function(){
                         var dats = $scope.gridOptions.getSelectedRows();
                        if(dats.length == 1){
                            var subFrom = {};
                            $scope.subFrom=subFrom;
                            subFrom.dictItemGuid = dats[0].guid;
                            dictonary_service.deleteSysDictItem(subFrom).then(function(data){
                                if(data.status == "success"){
                                    toastr['success']('删除成功');
                                    jstreeshu();//重新刷新一下树结构
                                    $modalInstance.close();
                                }else{
                                    toastr['error']('删除失败'+'<br/>'+data.retMessage);
                                }
                            })
                        }else{
                            toastr['error']("请至少选中一条进行删除！");
                        }
                     }

                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                })
            })
    }
    /*修改字典项提取*/
    var editdictitem = function(id,item){
        openwindow($uibModal, 'views/dictionary/dicttypeAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                $scope.dictlist = dictflag.dictnameL;//拿到渲染的数据
                $scope.one = true;
                $scope.two = true;
                var dictFrom = {};
                $scope.dictFrom = dictFrom;
                if(!isNull(item)){//如果参数不为空，则就回显
                    $scope.dictFrom = angular.copy(item);
                }
                var ids = id;
                $scope.id = ids;
                var tsxtcom = item.itemValue;//业务字典
                if(item.itemType =="value"){
                    dictFrom.itemValue  = item.itemValue
                }else{
                    $timeout(function () {
                        $(".test456").select2("val",tsxtcom);//渲染表格数据
                    },50);
                }
                $scope.add = function(item){//保存修改的函数
                    var subFrom={};
                    $scope.subFrom =subFrom;
                    subFrom =item;
                    subFrom.guid = item.guid;
                    dictonary_service.editSysDictItem(subFrom).then(function(data){
                        if(data.status == "success"){
                            toastr['success']( "修改成功！");
                            jstreeshu()//重新查询树结构
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
    /*删除单个字典提取*/
    var delitem = function(id){
        var subFrom = {};
        $scope.subFrom=subFrom;
        subFrom.dictItemGuid = id.guid;
        dictonary_service.deleteSysDictItem(subFrom).then(function(data){
            if(data.status == "success"){
                toastr['success']('删除成功');
                jstreeshu();//重新刷新一下树结构
            }else{
                toastr['error']('删除失败'+'<br/>'+data.retMessage);
            }
        })
    }

    //业务字典树结构关系
    var jstreeshu = function(){
        //树自定义右键功能(根据类型判断)
        var items = function customMenu(node) {
            if(node.parent == '#'){
                var it = {
                    "删除业务字典":{
                        "label":"删除业务字典",
                        "action":function(data){
                            if(confirm("您确认要删除选中的业务字典吗？删除业务字典对应的字典项也会删除")){
                                dictdelete(node.original.dictguid);//调用删除方法
                            }
                        }
                    },
                    "新增字典项":{
                        "id":"createa",
                        "label":"新增字典项",
                        "action":function(data){
                           //新增逻辑
                            creadictitem(node.original)
                        }
                    },
                    "删除字典项":{
                        "label":"删除字典项",
                        "action":function(data){
                            delectdictitem(node.original.itemValue);
                        }
                    },
                    "修改业务字典":{
                        "label":"修改业务字典",
                        "action":function(data){
                            //修改逻辑
                            dictedit(1,node.original)
                        }
                    }
                }
                return it;
            }
            if(node.original.itemType == "dict"){
                var it = {
                    "删除字典项":{
                        "label":"删除字典项",
                        "action":function(data){
                                delectdictitem(node.original.itemValue);
                        }
                    },
                    "增加字典项":{
                        "label":"增加字典项",
                        "action":function(data){
                            creadictitem(node.original)
                        }
                    },
                    "修改字典项":{
                        "label":"修改字典项",
                        "action":function(data){
                            //修改逻辑
                            editdictitem(1,node.original)

                        }
                    },
                    "设置默认值":{
                        "label":"设置默认值",
                        "action":function(data){
                            //修改逻辑
                            editdefault(node.original);
                            //editdefault();
                        }
                    }
                }
                return it;
            }
            if(node.original.itemType == "value"){
                var it = {
                    "删除字典项":{
                        "label":"删除字典项",
                        "action":function(data){
                            if(confirm("您确认要删除选中的字典项吗")){
                                delitem(node.original)
                            }
                        }
                    },
                    '修改字典项':{
                        "label":"修改字典项",
                        "action":function(data){
                            editdictitem(1,node.original)
                        }
                    },
                    "设置默认值":{
                        "label":"设置默认值",
                        "action":function(data){
                            //修改逻辑
                            editdefault(node.original);
                        }
                    }
                }
                return it;
            }
        };
        $('#container').jstree('destroy',false);
        $("#container").jstree({
            "core" : {
                "themes" : {
                    "responsive": false
                },
                "check_callback" : false,
                'data' : function (obj, callback) {
                    var jsonarray = [];
                    $scope.jsonarray = jsonarray;
                    var treeFrom = {};
                    $scope.treeFrom = treeFrom;
                    treeFrom.id = obj.id;
                    if(!isNull($scope.selectRow)){
                        $scope.treeFrom.dictKey = $scope.selectRow.dictKey;
                        if(!isNull(obj.original)){
                            if(obj.original.itemType == 'dict'){
                                $scope.treeFrom.dictKey = obj.original.itemValue;
                            }
                        }
                    }
                    common_service.post(res.queryDictTree,$scope.treeFrom).then(function(data){
                        if(data.status == 'success'){
                            var datas = data.retMessage;
                            if(!isNull(datas.defaultValue)){
                                var defaultValuedata = datas.defaultValue;
                                $scope.defaultValuedata = defaultValuedata;
                            }
                            var its = [];
                            if(!isNull(datas.rootName)){
                                datas.text = datas.itemValue+'_'+datas.rootName;
                                datas.children = true;
                                datas.id = datas.rootCode;
                                datas.icon = "fa fa-home icon-state-info icon-lg"
                                its.push(datas)
                            }else{
                                for(var i =0;i <datas.length;i++){
                                    if(datas[i].sendValue == $scope.defaultValuedata ){
                                        datas[i].text = datas[i].itemValue+'_'+datas[i].sendValue+'_'+datas[i].itemName + '*';
                                    }else{
                                        datas[i].text = datas[i].itemValue+'_'+datas[i].sendValue+'_'+datas[i].itemName;
                                    }
                                    datas[i].children = true;
                                    if(datas[i].itemType == 'value'){
                                        datas[i].children = false;
                                    }
                                    datas[i].id = datas[i].guid;
                                    datas[i].icon = "fa  fa-files-o icon-state-info icon-lg"
                                    its.push(datas[i]);
                                }
                            }
                            $scope.jsonarray = angular.copy(its);
                            callback.call(this, $scope.jsonarray);
                        }else{
                            var tis = [];
                            $scope.jsonarray = angular.copy(tis);
                            callback.call(this, $scope.jsonarray);
                        }
                    })
                },
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
            'sort': function (a, b) {
                //排序插件，会两者比较，获取到节点的order属性，插件会自动两两比较。
                return this.get_node(a).original.seqno > this.get_node(b).original.seqno ? 1 : -1;
            },
            'callback' : {
                move_node:function (node) {
                }
            },

            "plugins" : [ "state", "types","search","sort","contextmenu"]
        })
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
        { field: "itemName", displayName:'字典项名称'},
        { field: "itemValue", displayName:'字典项'},
        { field: "sendValue", displayName:'实际值'}
    ];
    //自定义点击事件
    var f1 = function(row){
        console.log(row)
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
            $scope.dictflag.inlength = datas.length;
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
                $scope.one = true;
                $scope.two = true;
                var dictFrom = {};
                $scope.dictFrom = dictFrom;
                dictFrom.guidDict = guidParents.dictName;
                dictFrom.seqno =0;//默认
                $scope.dictlist = dictflag.dictnameL;//渲染业务字典数据
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
                    $scope.one = true;
                    $scope.two = true;
                    if(!isNull(items)){//如果参数不为空，则就回显
                        $scope.dictFrom = angular.copy(items);
                    }
                    var ids = id;
                    $scope.id = ids;
                    /*$timeout(function () {
                        $(".test123").select2("val",tsxtcom);//渲染表格数据
                    },50);*/
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
                var subFrom = {};
                $scope.subFrom=subFrom;
                subFrom.dictItemGuid = getSel[0].guid;
                //subFrom.dictItemGuid = fun; //批量删除
                dictonary_service.deleteSysDictItem(subFrom).then(function(data){
                    if(data.status == "success"){
                        dictflag.inittx(getSel[0].guidDict);
                        if(dictflag.inlength==1){
                            $scope.dictconfig.show =false;
                        }
                        //dictflag.initt();//调用刷新列表
                    }else{
                        toastr['error']('删除失败'+'<br/>'+data.retMessage);
                    }
                })
            }
        }
    }




})


angular.module('MetronicApp').controller('dictitwos_controller', function($rootScope, $scope,common_service, $http,$modal,filterFilter,FileUploader){

    var res = $rootScope.res.dictonary_service;//页面所需调用的服务
    //下载全量业务字典
    $scope.downdict = function () {
        window.location.href = manurl+ '/'+res.exportDictExcel.ctrl + "/" + res.exportDictExcel.func;
        //console.log(manurl + '/'+res.ctrl.)
        /*common_service.get(res.exportDictExcel).then(function(data){
            if(data.status == "success"){

            }
        })*/
    }



    //导入业务字典
    $scope.import =function(){
        $scope.imporots = true;
    }
    
    
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

