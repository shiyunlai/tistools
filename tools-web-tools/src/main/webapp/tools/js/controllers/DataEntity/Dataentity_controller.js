/**
 * Created by wangbo on 2017/11/8
 */

MetronicApp.controller('dataEntity_controller', function ($filter, $scope, $rootScope, common_service,filterFilter, $state,$modal,$uibModal, $http, $timeout,$interval,i18nService) {

    //定义公共对象
    var dataEnt = {};
    $scope.dataEnt = dataEnt;

    //实体服务请求方法
    var res = $rootScope.res.entity_service;//页面所需调用的服务

    //表格内容
    var dataEntFrom = {}
    $scope.dataEntFrom = dataEntFrom;
    i18nService.setCurrentLang("zh-cn");//表格翻译

    //刷新树方法
    dataEnt.refresh = function () {
        $("#container").jstree().refresh();
    }

    //判断页签逻辑
    var dataEntIf = {};
    $scope.dataEntIf = dataEntIf;

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

    //树自定义右键功能(根据类型判断)
    var items = function customMenu(node) {
        var judge = node.original;
        var control;
        if(judge.types == 'appType'){
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
        if(judge.types == 'enType'){
            var it = {
                "新增实体":{
                    "id":"createc",
                    "label":"新增实体",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);//得到值
                        var objGuid = obj;
                        creatFrom(objGuid)//传入guid
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
        }else if(judge.types == 'eneits'){
            var it = {
                "删除实体":{
                    "label":"删除实体",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        if(confirm("确定删除选中的实体吗？")){
                            dataEnt.deleteAcEntity(obj.original.guid);
                        }
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

    };
    //树结构生成
    $("#container").jstree({
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
                    data.text = '实体属性';
                    data.children = true;
                    data.types = 'root';
                    data.id = 'root';
                    its.push(data);
                    $scope.jsonarray = angular.copy(its);
                    callback.call(this, $scope.jsonarray);
                }else if(obj.id =='root'){
                    var subFrom  = {};
                    subFrom.type = 'app';//查询所有应用
                    common_service.post(res.queryAcEntityTreeList,{data:subFrom}).then(function(data){
                        if(data.status == "success"){
                            var datas = data.retMessage;
                            for(var i =0;i <datas.length;i++){
                                datas[i].text = datas[i].appName;
                                datas[i].children = true;
                                datas[i].id = datas[i].guid;
                                datas[i].types = 'appType';
                                datas[i].icon = "fa  fa-files-o icon-state-info icon-lg"
                                its.push(datas[i]);
                            }
                            $scope.jsonarray = angular.copy(its);
                            callback.call(this, $scope.jsonarray);
                        }
                    })
                }else if(obj.original.types =='appType'){
                    var subFrom  = {};
                    subFrom.type = 'entityType';//查询所有类型
                    subFrom.appGuid = obj.id;//应用Guid
                    common_service.post(res.queryAcEntityTreeList,{data:subFrom}).then(function(data){
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
                    subFrom.appGuid = obj.parent;
                    subFrom.entity = obj.original.itemValue;
                    common_service.post(res.queryAcEntityTreeList,{data:subFrom}).then(function(data){
                        var datas = data.retMessage;
                        if(data.status == "success"){
                            for(var i =0;i <datas.length;i++){
                                datas[i].text = datas[i].entityName;
                                datas[i].children = false;
                                datas[i].id = datas[i].guid;
                                datas[i].types = 'eneits';
                                datas[i].icon = "fa  fa-files-o icon-state-info icon-lg"
                                its.push(datas[i]);
                            }
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
            $scope.dataEnt = data.node.original;//绑定内容给页面
            if(data.node.original.types == 'eneits'){
                getData();
                getDatas();
                $scope.dataEntIf.dataEnis = true;//只有点击实体属性的时候，才让右侧内容显示
            }else{
                $scope.dataEntIf.dataEnis = false;
            }
            ($scope.$$phase) ? null : $scope.$apply();
        }
    });

    //查询实体属性方法
    dataEnt.queryAcEntityfieldList = function () {
        var subFrom = {};
        subFrom.entityGuid = entityGuid;//传入实体的guid
        common_service.post(res.queryAcEntityfieldList,{data:subFrom}).then(function(data){
            if(data.status == "success"){

            }
        })
    }

    //新增实体对象
    dataEnt.createAcEntity = function (item,$modalInstance) {
        var subFrom = {};
        subFrom=item;
        common_service.post(res.createAcEntity,{data:subFrom}).then(function(data){
            if(data.status == "success"){
                toastr['success']("新增成功!");
                $("#container").jstree().refresh();
                $modalInstance.dismiss('cancel');
            }
        })
    }

    //删除实体对象
    dataEnt.deleteAcEntity = function (item) {
        var tis = [];
        tis.push(item);
        common_service.post(res.deleteAcEntity,{data:tis}).then(function(data){
            if(data.status == "success"){
                toastr['success']("删除成功!");
                $("#container").jstree().refresh();
            }
        })
    }

    //删除记录检查引用方法调用
    dataEnt.conversion=function (item) {
        if(!isNull(item)){
            var str = '';
            for (var i = 0; i < item.length; i++) {
                if(!isNull(item[i].tableName) && !isNull(item[i].columeName) && !isNull(item[i].isDel)) {
                    str += item[i].tableName + '.' + item[i].columeName + '/[' + item[i].isDel + ']';
                    if (i !== item.length - 1)
                        str += ';';//单次匹配，如果没有，那就默认为空
                }
            }
            return str;
        }
    }

    //新增表结构实体类型
    function  creatFrom(item) {
        var items = item;
        openwindow($modal, 'views/dataEntity/creatEntity.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.title = '新增实体属性'
                var dataEnt = {};
                $scope.dataEnt = dataEnt;
                $scope.dataEnt.entityType = items.original.itemValue;
                $scope.dataEnt.guidApp = items.parent;
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
                $scope.add = function(item){
                    dataEnt.conversion=function (item) {
                        if(!isNull(item)){
                            var str = '';
                            for (var i = 0; i < item.length; i++) {
                                if(!isNull(item[i].tableName) && !isNull(item[i].columeName) && !isNull(item[i].isDel)) {
                                    str += item[i].tableName + '.' + item[i].columeName + '/[' + item[i].isDel + ']';
                                    if (i !== item.length - 1)
                                        str += ';';//单次匹配，如果没有，那就默认为空
                                }
                            }
                            return str;
                        }
                    }
                    var preview = dataEnt.conversion(qualityRatingExt);//调用查询方法
                    item.checkref = preview;
                    //拼接传给后台
                    dataEnt.createAcEntity = function (item,$modalInstance) {
                        var subFrom = {};
                        subFrom=item;
                        common_service.post(res.createAcEntity,{data:subFrom}).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("新增成功!");
                                $("#container").jstree().refresh();
                                $modalInstance.dismiss('cancel');
                            }
                        })
                    }
                    dataEnt.createAcEntity(item,$modalInstance);
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            })
    }

    //编辑实体方法
    $scope.dataEntEdit =function (item) {
        console.log(item)
        var EntDatas = $scope.dataEnt;
        openwindow($modal, 'views/dataEntity/creatEntity.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.title = '编辑实体属性'
                var dataEnt = {};
                $scope.dataEnt = dataEnt;
                $scope.dataEnt = angular.copy(EntDatas);
                var EntStr = EntDatas.checkRef;//拿到字符串
                var EntArray = [];//定义空数组,来接受
                //截取原先字符串
                var arr = EntStr.split(";");
                for(var i =0;i<arr.length;i++){
                    //获取/和.的index？
                    var y = arr[i].indexOf('.');//切割.
                    var x = arr[i].indexOf('/');//切割/
                    var spot = arr[i].substring(0,y);//根据.切割字符串
                    var Slash = arr[i].substring(y+1,x);//根据/切割字符串
                    var Whether = arr[i].substring(x+2,x+3);//最后切出Y/N
                    var Mosaic = {};//定义对象来接收
                    Mosaic.tableName = spot;
                    Mosaic.columeName = Slash;
                    Mosaic.isDel = Whether;
                    EntArray.push(Mosaic);
                }
                //删除引用表关系 还未改成统一使用 dataEnt
                var qualityRatingExt = [];//定义数组
                $scope.qualityRatingExt = qualityRatingExt;
                if(!isNull(EntArray)){//是否存在，存在那就拼接
                    for(var i =0;i<EntArray.length;i++){
                        qualityRatingExt.push(EntArray[i]);
                    }
                }else{
                    var item = {tableName : '',columeName : '',isDel:''};
                    qualityRatingExt.push(item);
                }
                //删一个
                $scope.delQualityRatingExt = function (index) {
                    qualityRatingExt.splice(index, 1);
                };
                //加一个
                $scope.addQualityRatingExt = function () {
                    var item = {tableName : '',columeName : '',isDel:''};
                    qualityRatingExt.push(item);
                };
                $scope.add = function(item){
                    var subFrom = {};
                    dataEnt.conversion=function (item) {
                        if(!isNull(item)){
                            var str = '';
                            for (var i = 0; i < item.length; i++) {
                                if(!isNull(item[i].tableName) && !isNull(item[i].columeName) && !isNull(item[i].isDel)) {
                                    str += item[i].tableName + '.' + item[i].columeName + '/[' + item[i].isDel + ']';
                                    if (i !== item.length - 1)
                                        str += ';';//单次匹配，如果没有，那就默认为空
                                }
                            }
                            return str;
                        }
                    };//删除关系数据转换
                    var preview = dataEnt.conversion(qualityRatingExt);
                    item.checkref = preview;
                    item.checkRef = preview;
                    //调用更新接口
                    subFrom=item;
                    //修改接口报错
                    common_service.post(res.editAcEntity,{data:subFrom}).then(function(data){
                        if(data.status == "success"){
                            toastr['success']("修改成功!");
                            $("#container").jstree().refresh();
                            $modalInstance.dismiss('cancel');
                        }
                    })
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            })
    }

    //tab页签控制
    //默认当前实体打开
    dataEntIf.dqst = true;
    dataEntIf.loadgwdata = function (type) {
        if(type == 0){
            dataEntIf.dqst = true;
            dataEntIf.sssx = false;
            dataEntIf.sjfw = false;//隐藏
        }else if (type == 1) {
            getData();//查询实体属性方法
            dataEntIf.dqst = false;
            dataEntIf.sssx = true;
            dataEntIf.sjfw = false;//隐藏
        }else if(type == 2){
            getDatas();//查询实体范围方法
            dataEntIf.sssx = false;
            dataEntIf.sjfw = true;//显示
            dataEntIf.dqst = false;
        }
    }

    //实体属性表格
    var gridAttributes = {};
    $scope.gridAttributes = gridAttributes;
    var grid = [
        { field: "fieldName", displayName:'属性名称'},
        { field: "fieldType", displayName:'字段类型',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.fieldType | translateConstants :\'DICT_FIELDTYPE\') + $root.constant[\'DICT_FIELDTYPE-\'+row.entity.fieldType]}}</div>'},
        { field: "doclistCode", displayName:'代码大类'},
        { field: "ismodify", displayName:'是否可修改',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.ismodify | translateConstants :\'DICT_YON\') + $root.constant[\'DICT_YON-\'+row.entity.ismodify]}}</div>'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.gridAt = row.entity;
        }else{
            $scope.gridAt = '';//制空
        }
    }
    $scope.gridAttributes = initgrid($scope,gridAttributes,filterFilter,grid,true,f);

    // 查询实体属性方法
    function getData() {
        var subFrom = {};
        subFrom.entityGuid = $scope.dataEnt.guid;
        common_service.post(res.queryAcEntityfieldList,{data:subFrom}).then(function(data){
            var datas  = data.retMessage;
            $scope.dataEnt.EntityDates = datas;
            if(data.status == "success"){
                $scope.gridAttributes.data = datas;
                $scope.gridAttributes.mydefalutData = datas;
                $scope.gridAttributes.getPage(1,$scope.gridAttributes.paginationPageSize);
            }else{
                $scope.gridAttributes.data = [];
                $scope.gridAttributes.mydefalutData = [];
                $scope.gridAttributes.getPage(1,$scope.gridAttributes.paginationPageSize);
            }
        })
    }

    //新增实体属性方法
    var createAcEntityfield = function (item,$modalInstance) {
        var subFrom = {};
        subFrom=item;
        common_service.post(res.createAcEntityfield,{data:subFrom}).then(function(data){
            if(data.status == "success"){
                toastr['success']("新增属性成功!");
                $("#container").jstree().refresh();
                getData();//查询实体属性树;
                $modalInstance.dismiss('cancel');
            }
        })
    }

    //新增实体属性
    $scope.dataDet_add = function () {
        var guidEntity = $scope.dataEnt.guid;
        openwindow($modal, 'views/dataEntity/Entityattribute.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.title = '新增实体属性'
                $scope.add = function(item){
                    item.guidEntity = guidEntity;
                    createAcEntityfield(item,$modalInstance)
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            })
    }

    //修改属性
    $scope.dataDet_edit = function () {
        var getSel = $scope.gridAttributes.getSelectedRows();//拿到数据
        var entityDate = getSel[0]
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来修改！");
        }else{
            openwindow($modal, 'views/dataEntity/Entityattribute.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    var  dataEntFrom = {};
                    $scope.dataEntFrom = entityDate;
                    $scope.title = '修改实体属性'
                    $scope.add = function(item){
                        var subFrom = {};
                        subFrom=item;
                        common_service.post(res.editAcEntityfield,{data:subFrom}).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("修改属性成功!");
                                getData();//查询实体属性树;
                                $modalInstance.dismiss('cancel');
                            }
                        })
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
        if(isNull(getSel) || getSel.length<1){
            toastr['error']("请至少选中一条数据来删除！");
        }else{
            if(confirm('确定删除改配置吗')){
                var tis = [];
                for(var i =0;i<getSel.length;i++){
                    tis.push(getSel[i].guid);
                }
                common_service.post(res.deleteAcEntityfield,{data:tis}).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除成功！");
                        getData();//查询实体属性树;
                    }
                })
            }
        }
    }

    //查看详情
    $scope.dataDett_details = function () {
        var getSel = $scope.gridAttributes.getSelectedRows();//拿到数据
        var entityDate = getSel[0]
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来查看详情！");
        }else{
            openwindow($modal, 'views/dataEntity/EntityLooking.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    var  dataEntFrom = {};
                    $scope.dataEntFrom = entityDate;
                    $scope.title = '查看实体属性'
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
        { field: "entityName", displayName:'实体名称'},
        { field: "privName", displayName:'数据范围权限名称'},
        { field: "dataOpType", displayName:'数据操作类型',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.dataOpType | translateConstants :\'DICT_AC_DATAOPTYPE\') + $root.constant[\'DICT_AC_DATAOPTYPE-\'+row.entity.dataOpType]}}</div>'},
        { field: "filterSqlString", displayName:'SQL查询条件'}
    ];
    var fPer = function(row){
        if(row.isSelected){
            $scope.gridPer = row.entity;
        }else{
            $scope.gridPer = '';//制空
        }
    }
    $scope.gridPermissions = initgrid($scope,gridPermissions,filterFilter,gridPer,false,fPer);

    //查询实体范围接口
    function getDatas() {
        var subFrom = {};
        subFrom.entityGuid = $scope.dataEnt.guid;
        common_service.post(res.queryAcDatascopeList,{data:subFrom}).then(function(data){
            var datas  = data.retMessage;
            if(data.status == "success"){
                $scope.gridPermissions.data = datas;
                $scope.gridPermissions.mydefalutData = datas;
                $scope.gridPermissions.getPage(1,$scope.gridPermissions.paginationPageSize);
            }else{
                $scope.gridPermissions.data = [];
                $scope.gridPermissions.mydefalutData = [];
                $scope.gridPermissions.getPage(1,$scope.gridPermissions.paginationPageSize);
            }
        })
    }
    getDatas();

    //新增范围接口
    var createAcDatascope = function (item,$modalInstance) {
        var subFrom = {};
        subFrom=item;
        common_service.post(res.createAcDatascope,{data:subFrom}).then(function(data){
            if(data.status == "success"){
                toastr['success']("新增属性成功!");
                $("#container").jstree().refresh();
                getDatas();//查询实体范围列表;
                $modalInstance.dismiss('cancel');
            }
        })
    }

    //实体范围编译方法
    dataEnt.conFversion=function (val) {
        if(!isNull(val)){
            var str = '';
            for(var i=0,len=val.length;i<len;i++){
                var str2 = '';
                for(var j=0,len2=val[i].length;j<len2;j++){
                    if(val[i][j].type){
                        var sql = '';
                        if(val[i][j].rlea){//如果存在多个
                            sql = val[i][j].rlea + '('+  val[i][j].type +' '+ val[i][j].guanxi +' '+ val[i][j].ce+')';
                        }else{
                            sql = '('+ val[i][j].type +' '+ val[i][j].guanxi +' '+ val[i][j].ce+')';
                        }
                        str2 += sql;
                    }
                }
                if(JSON.stringify(val[0]) == "[{}]"){
                }else{
                    if(val[i].orAdd){//如果存在 这样没问题的
                        str = str + '('+ str2 +')' + val[i].orAdd +"(";
                    }else{
                        str +=  str2;
                    }
                }
            }
            return str;
        }
    }
    


    //新增范围
    $scope.dataDett_add = function () {
        var guidEntity = $scope.dataEnt.guid;
        var EntityDates = $scope.dataEnt.EntityDates;
        openwindow($modal, 'views/dataEntity/Entityauthority.html', 'lg',//弹出页面
            function ($scope, $modalInstance) {
                $scope.title = '新增范围数据权限';
                var dataEnt = {};
                $scope.dataEnt = dataEnt;//双向绑定数据
                $scope.dataEnt.qualityRatingExt = [];//定义条件数组
                $scope.dataEnt.cardArray = [];//定义卡片

                //打开新增按钮
                dataEnt.Addpush = function () {
                    $scope.isshow = true;
                }

                //关闭内容
                $scope.ENtitycancel = function () {
                    $scope.isshow = false;
                }

                //拼接成对应的方法
                var EntiDates = [];
                for(var i =0;i<EntityDates.length;i++){
                    var subFrom  ={};
                    subFrom.key = EntityDates[i].columnName;
                    subFrom.value = EntityDates[i].fieldType;
                    EntiDates.push(subFrom)
                }
                $scope.EntiDates = EntiDates;

                var items = []
                var item = {};
                items.push(item);
                dataEnt.cardArray.push(items);
                //条件删除
                $scope.delQualityRatingExt = function (d,index) {
                    if(index ==0){
                    }
                    d.splice(index, 1);
                };

                //条件新增
                $scope.addQualityRatingExt = function (index) {
                    var item = {};
                    dataEnt.cardArray[index].push(item)
                };

                $scope.selectchange  = function (d) {
                    console.log(d)
                }

                //卡片删一个
                $scope.carddelQualityRatingExt = function (index) {
                    dataEnt.cardArray.splice(index, 1);
                };
                //卡片加一个
                $scope.cardaddQualityRatingExt = function (d) {

                    var items = []
                    var item = {};
                    items.push(item);
                    // console.log(dataEnt.cardArray)
                    //因为没有push大数组里啊
                    dataEnt.cardArray.push(items)  //这个放开就有两个了啊 shide
                    // dataEnt.cardArray[dataEnt.cardArray.length-2].push({"orAdd":'add'});
                    //执行这个
                    console.log(dataEnt.cardArray)
                };

                $scope.add = function(item){
                    item.guidEntity = guidEntity;
                    dataEnt.conFversion=function (val) {
                        if(!isNull(val)){
                            var str = '';
                            for(var i=0,len=val.length;i<len;i++){
                                var str2 = '';
                                for(var j=0,len2=val[i].length;j<len2;j++){
                                    if(val[i][j].type){
                                        var sql = '';
                                        if(val[i][j].rlea){//如果存在多个
                                            sql = val[i][j].rlea + '('+  val[i][j].type +' '+ val[i][j].guanxi +' '+ val[i][j].ce+')';
                                        }else{
                                            sql = '('+ val[i][j].type +' '+ val[i][j].guanxi +' '+ val[i][j].ce+')';
                                        }
                                        str2 += sql;
                                    }
                                }
                                if(JSON.stringify(val[0]) == "[{}]"){
                                }else{
                                    if(val[i].orAdd){//如果存在 这样没问题的
                                        str = str + '('+ str2 +')' + val[i].orAdd +"(";
                                    }else{
                                        str +=  str2;
                                    }
                                }
                            }
                            return str;
                        }
                    };//修改范围
                    var produc =dataEnt.conFversion(dataEnt.cardArray);//传入数组，进行拆分和拼接
                    item.filterSqlString = produc;//传入后台
                    createAcDatascope(item,$modalInstance)
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            })
    }
    //修改接口
    var editAcDatascope = function (item,$modalInstance) {
        var subFrom = {};
        subFrom=item;
        common_service.post(res.editAcDatascope,{data:subFrom}).then(function(data){
            if(data.status == "success"){
                toastr['success']("修改属性成功!");
                getDatas();//查询实体范围列表;
                $modalInstance.dismiss('cancel');
            }
        })
    }

    //修改范围
    $scope.dataDett_edit = function () {
        var EntityDates = $scope.dataEnt.EntityDates;
        var getSel = $scope.gridPermissions.getSelectedRows();//拿到数据
        console.log(getSel);
        var guidEntity = $scope.dataEnt.guid;
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来修改！");
        }else{
            openwindow($modal, 'views/dataEntity/Entityauthority.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.title = '修改范围数据权限'
                    var dataEnt = {};
                    $scope.dataEnt = dataEnt;
                    $scope.dataEnt.qualityRatingExt = [];
                    $scope.dataEnt.cardArray = [];
                    //拼接成对应的方法
                    var EntiDates = [];
                    for(var i =0;i<EntityDates.length;i++){
                        var subFrom  ={};
                        subFrom.key = EntityDates[i].columnName;
                        subFrom.value = EntityDates[i].fieldType;
                        EntiDates.push(subFrom)
                    }
                    $scope.EntiDates = EntiDates;

                    //回选
                    var dataEntFrom = {};
                    $scope.dataEntFrom = getSel[0];

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
                    $scope.add = function(item){
                        // item.guidEntity = getSel[0].guid;
                        item.guidEntity = guidEntity;
                        item.filterSqlString = 'cets';
                        editAcDatascope(item,$modalInstance)
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };

                })
        }
    }

    //删除范围
    $scope.dataDett_delete = function () {
        var getSel = $scope.gridPermissions.getSelectedRows();//拿到数据
        if(isNull(getSel) || getSel.length<1){
            toastr['error']("请至少选中一条数据来删除！");
        }else{
            if(confirm('确定删除实体配置吗')){
                var tis = [];
                for(var i =0;i<getSel.length;i++){
                    tis.push(getSel[i].guid);
                }
                common_service.post(res.deleteAcDatescope,{data:tis}).then(function(data){
                    if(data.status == "success"){
                        toastr['success']("删除成功！");
                        getDatas();//查询实体范围列表;
                    }
                })
            }
        }
    }

    //查看详情
    $scope.dataDett_lookedit = function () {
        var getSel = $scope.gridPermissions.getSelectedRows();//拿到数据
        var guidEntity = $scope.dataEnt.guid;
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来修改！");
        }else{
            openwindow($modal, 'views/dataEntity/EntityauthorityLook.html', 'lg',//弹出页面
                function ($scope, $modalInstance) {
                    $scope.title = '查看范围数据权限'
                    var dataEnt = {};
                    $scope.dataEnt = dataEnt;
                    $scope.dataEnt.qualityRatingExt = [];
                    $scope.dataEnt.cardArray = [];
                    //回选
                    var dataEntFrom = {};
                    $scope.dataEntFrom = getSel[0];

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
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };

                })
        }
    }
});
