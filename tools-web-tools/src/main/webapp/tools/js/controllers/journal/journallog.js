/**
 * Created by wangbo on 2017/9/8.
 */

angular.module('MetronicApp').controller('journal_controller', function($rootScope, $scope, $http,$state,i18nService,$modal,filterFilter,behavior_service,common_service) {
    i18nService.setCurrentLang("zh-cn");
    var logdetails = {};
    $scope.logdetails = logdetails;
    var res = $rootScope.res.log_service;//页面所需调用的日志服务
    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var com = [
        { field: 'operateFrom', displayName: '操作渠道'},
        { field: 'operateResult', displayName: '操作结果'},
        { field: 'operateTime', displayName: '操作时间',type: 'date', cellFilter: 'date:"yyyy-MM-dd HH:mm:ss"'},
        //{ field: 'operateType', displayName: '操作类型'},
        //{ field: 'operatorName', displayName: '操作员姓名'},
        { field: 'processDesc', displayName: '操作描述'},
        //{ field: 'restfulUrl', displayName: '服务地址'},
        { field: 'userId', displayName: '操作员'},
        { field: 'edit', displayName: '查看详情',cellTemplate: '<a ng-click="grid.appScope.stategohistory(row.entity)" style="text-decoration: none;display:block;margin-top: 5px;margin-left: 3px;">详情</a>'}
    ];

    $scope.stategohistory = function(item){
        if(item.operateType == 'login'){
            //直接去查看历史
            var item = 'l'+item.userId;//到历史页面去调用，查询登陆的历史。 需要修改接口
            $state.go("loghistory",{id:item});//跳转新页面
        }else{
            var items = item.guid;//数据信息;
            $state.go("journinfo",{id:items})
        }
    }
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);
    common_service.post(res.queryOperateLogList,{}).then(function(data){
        var datas = data.retMessage;
        if(data.status == "success"){
            $scope.gridOptions.data =  datas;
            $scope.gridOptions.mydefalutData = datas;
            $scope.gridOptions.getPage(1,$scope.gridOptions.paginationPageSize);
        }else{
            toastr['error']('查询失败'+'<br/>'+data.retMessage);
        }
    })

    $scope.logdetails.look = function(){
        var getSel = $scope.gridOptions.getSelectedRows();
        console.log(getSel[0])
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据来查看！");
        }else{
            if(getSel[0].operateType == 'login'){
                //直接去查看历史
                var item = 'l'+getSel[0].userId;//到历史页面去调用，查询登陆的历史。 需要修改接口
                $state.go("loghistory",{id:item});//跳转新页面
            }else{
                var items = getSel[0].guid;//数据信息;
                $state.go("journinfo",{id:items})
            }

        }
    }
});


//日志详情页面
angular.module('MetronicApp').controller('jourinfo_controller', function($rootScope, $scope,$state, $timeout,$http,i18nService,$uibModal,$stateParams,filterFilter,behavior_service,common_service) {
    i18nService.setCurrentLang("zh-cn");
    var res = $rootScope.res.log_service;//页面所需调用的日志服务
    var loginfo = {};
    $scope.loginfo = loginfo;
    //返回方法
    $scope.myback = function(){
        window.history.back(-1);
    }
    $scope.operguid = $stateParams.id;
    var subFrom = {};
    subFrom.logGuid =  $stateParams.id;//接受传入的值
    //根据guid查询日志详情服务
    common_service.post(res.queryOperateDetail,subFrom).then(function(data){
        var datas = data.retMessage;
        if(data.status == "success"){
            $scope.loginfo = datas.instance;
            if(datas.instance.operateResult =='fail'){
                $scope.logofail = true;
            }else{
                $scope.logofail = false;
            }
            //渲染表格
            $scope.gridOptions1.data =  datas.allObj;
            $scope.gridOptions1.mydefalutData = datas.allObj;
            $scope.gridOptions1.getPage(1,$scope.gridOptions1.paginationPageSize);
            $scope.loginfo.operateTime = moment(datas.instance.operateTime).format('YYYY-MM-DD HH:mm:ss');
        }else{
            toastr['error']('查询失败'+'<br/>'+data.retMessage);
        }
    })

    var gridOptions1 = {};
    $scope.gridOptions1 = gridOptions1;
    var com = [
        { field: 'instance.objGuid', displayName: '对象guid',cellTemplate: '<a ng-click="grid.appScope.ceshi(row.entity.instance.objGuid)" style="text-decoration: none;display:block;margin-top: 5px;margin-left: 3px;">{{row.entity.instance.objGuid}}</a>'},
        { field: 'instance.objFrom', displayName: '对象来源'},
        { field: 'instance.objType', displayName: '对象类型'},
        { field: 'instance.objName', displayName: '对象名'},
        { field: 'instance.objValue', displayName: '对象值',cellTemplate: '<a ng-click="grid.appScope.jsonlook(row.entity)" style="text-decoration: none;display:block;margin-top: 5px;margin-left: 3px;">详情</a>'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow1 = row.entity;
        }else{
            delete $scope.selectRow1;//制空
        }
    }
    $scope.gridOptions1 = initgrid($scope,gridOptions1,filterFilter,com,false,f);
/*      没有必要调两次
    common_service.post(res.queryOperateDetail,subFrom).then(function(data){
        var datas = data.retMessage;
        if(data.status == "success"){
            $scope.gridOptions1.data =  datas.allObj;
            $scope.gridOptions1.mydefalutData = datas.allObj;
            $scope.gridOptions1.getPage(1,$scope.gridOptions1.paginationPageSize);
        }else{
            toastr['error']('查询失败'+'<br/>'+data.retMessage);
        }
    })*/

    $scope.ceshi = function(item){
        $state.go("loghistory",{id:item});//跳转新页面
    }

    //查看对象值，自动格式化
    $scope.jsonlook = function(item){
        openwindow($uibModal, 'views/journal/jsonAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                //json自动格式化，展现页面。
                //http://www.cnblogs.com/lvdabao/p/4662612.html 原文。
                $scope.keywords =item.keywords;
                function output(inp) {
                    $timeout(function () {
                     var id = document.getElementById('test');
                     var test = document.createElement('pre');
                     var  list = id.appendChild(test);
                     list.innerHTML = inp;
                    },50);
                }
                function syntaxHighlight(json) {
                    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
                    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
                        var cls = 'number';
                        if (/^"/.test(match)) {
                            if (/:$/.test(match)) {
                                if(match.indexOf('&') != -1){
                                    cls = 'string reds';
                                    match= match.replace(/&amp;/, "");//把标识$替换掉，不然影响界面效果
                                }else{
                                    cls = 'key';
                                }
                            } else {

                                cls = 'string';
                            }
                        } else if (/true|false/.test(match)) {
                            cls = 'boolean';
                        } else if (/null/.test(match)) {
                            cls = 'null';
                        }
                        return '<span class="' + cls + '" >' + match + '</span>';
                    });
                }
                var obj = angular.fromJson(item.instance.objValue);
                if(!isNull(item.changes)){//如果有修改值，那就给对应的key加上标识
                    var newObj = {};//定义一个新对象
                    for(var key in obj){//循环
                        newObj[key] = obj[key];//新对象拷贝旧对象的值
                        var changeArray = item.changes;
                        for(var i = 0; i<changeArray.length;i++){
                            if(key == changeArray[i].changeKey){//判断key是否相等
                                newObj['&'+key] = newObj[key];//重写符合条件的key,添加标识
                                changeArray[i].newValue = newObj[key];//即把最新的值也添加给changeArray中
                                delete newObj[key];
                                break;
                            }
                        }
                    }
                    $scope.changedata = changeArray;//修改的项,拿到前台循环
                    $scope.ischange = true;
                    var str = JSON.stringify(newObj, undefined, 4);//如果存在改变值，那就用新对象去处理
                }else{
                    $scope.ischange = false;
                    var str = JSON.stringify(obj, undefined, 4);
                }
                output(syntaxHighlight(str));
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }
});

//日志历史页面
angular.module('MetronicApp').controller('loghistory_controller', function($rootScope,$timeout, $scope,$state, $http,i18nService,$uibModal,$stateParams,filterFilter,behavior_service,common_service) {
    i18nService.setCurrentLang("zh-cn");
    var res = $rootScope.res.log_service;//页面所需调用的日志服务
    var loghistory = {};
    $scope.loghistory = loghistory;
    //返回方法
    $scope.myback = function(){
        window.history.back(-1);
    }
    var objGuid = $stateParams.id;//拿到传入的对象guid
    console.log(objGuid)
    //判断是否是登陆，如果是单独操作，否则还是原本的操作
    var subFrom = {};
    if(objGuid.substring(0,1) =='l'){
        var objuserid =objGuid.substr (1,objGuid.length-1);//把第一位截取掉，只保留后面的登陆id
        subFrom.userId = objuserid;
        common_service.post(res.queryLoginHistory,subFrom).then(function(data){
            var datas = data.retMessage;
            if(data.status == "success"){
                $scope.login = true;
                for(var i = 0;i<datas.length;i++){
                    datas[i].operateYmder = moment(datas[i].operateTime).format('YYYY-MM-DD');
                    datas[i].operateYmdess = moment(datas[i].operateTime).format('HH:mm:ss');
                }
                $scope.loghistory.history = datas;
            }else{
                toastr['error']('查询失败'+'<br/>'+data.retMessage);
            }
        })
    }else{
        //操作原本的代码逻辑
        subFrom.objGuid = objGuid;
        //请求数据
        common_service.post(res.queryOperateHistoryList,subFrom).then(function(data){
            var datas = data.retMessage.list;//时间轴数据
            var infodatas = data.retMessage.subject;
            console.log(infodatas)
            //对详情进行处理，去除()内的英文

            function subObj(obj) {
                var array = [];
                for(var i in obj){
                    var  strs = {};
                    var number = i.indexOf('(');
                    var skip = i.substring(0,number);//得到截取的内容
                    strs.key = skip;
                    strs.value = obj[i];
                    array.push(strs);
                }
                return array;
            }

            var infoList = subObj(infodatas);//前台渲染数据
            $scope.infoList = infoList;
            if(data.status == "success"){
                for(var i = 0;i<datas.length;i++){
                    datas[i].instance.operateYmder = moment(datas[i].instance.operateTime).format('YYYY-MM-DD');
                    datas[i].instance.operateYmdess = moment(datas[i].instance.operateTime).format('HH:mm:ss');
                }

                if(!isNull(datas[datas.length-1])){
                    //对象名，数组的最后一项，就是对象名,因为要对对象guid进行翻译，后台给了objname，可以利用state传入，也可以利用这种方法，历史的最后一项就是最开始的对象名，偷懒的做法
                    $scope.objGuid = datas[datas.length-1].allObj[0].instance.objName;
                }
                $scope.loghistory.history = datas;
            }else{
                toastr['error']('查询失败'+'<br/>'+data.retMessage);
            }
        })
    }


    //查看对象详情:
    $scope.jsonlook =function(item){
        openwindow($uibModal, 'views/journal/jsonAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                //json自动格式化，展现页面。
                //http://www.cnblogs.com/lvdabao/p/4662612.html 原文。
                $scope.keywords =item.keywords;//关键值
                function output(inp) {
                    $timeout(function () {
                        var id = document.getElementById('test');
                        var test = document.createElement('pre');
                        var  list = id.appendChild(test);
                        list.innerHTML = inp;
                    },50);
                }
                /*  原内容
                function syntaxHighlight(json) {
                    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
                    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
                        var cls = 'number';
                        if (/^"/.test(match)) {
                            if (/:$/.test(match)) {
                                cls = 'key';
                            } else {
                                cls = 'string';
                            }
                        } else if (/true|false/.test(match)) {
                            cls = 'boolean';
                        } else if (/null/.test(match)) {
                            cls = 'null';
                        }
                        return '<span class="' + cls + '">' + match + '</span>';
                    });
                }
                var obj = angular.fromJson(item.instance.objValue);
                var str = JSON.stringify(obj, undefined, 4);
                output(syntaxHighlight(str));
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };*/
                function syntaxHighlight(json) {
                    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
                    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
                        var cls = 'number';
                        if (/^"/.test(match)) {
                            if (/:$/.test(match)) {
                                if(match.indexOf('&') != -1){
                                    cls = 'string reds';
                                    match= match.replace(/&amp;/, "");//把标识$替换掉，不然影响界面效果
                                }else{
                                    cls = 'key';
                                }
                            } else {

                                cls = 'string';
                            }
                        } else if (/true|false/.test(match)) {
                            cls = 'boolean';
                        } else if (/null/.test(match)) {
                            cls = 'null';
                        }
                        return '<span class="' + cls + '" >' + match + '</span>';
                    });
                }
                var obj = angular.fromJson(item.instance.objValue);
                if(!isNull(item.changes)){//如果有修改值，那就给对应的key加上标识
                    var newObj = {};//定义一个新对象
                    for(var key in obj){//循环
                        newObj[key] = obj[key];//新对象拷贝旧对象的值
                        var changeArray = item.changes;
                        for(var i = 0; i<changeArray.length;i++){
                            if(key == changeArray[i].changeKey){//判断key是否相等
                                newObj['&'+key] = newObj[key];//重写符合条件的key,添加标识
                                changeArray[i].newValue = newObj[key];//即把最新的值也添加给changeArray中
                                delete newObj[key];
                                break;
                            }
                        }
                    }
                    $scope.changedata = changeArray;//修改的项,拿到前台循环
                    $scope.ischange = true;
                    var str = JSON.stringify(newObj, undefined, 4);
                }else{
                    $scope.ischange = false;
                    var str = JSON.stringify(obj, undefined, 4);
                }
                output(syntaxHighlight(str));
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }

    //查看操作详情:
    $scope.loghistory.look = function(items){
            $state.go("journinfo",{id:items})
    }

});