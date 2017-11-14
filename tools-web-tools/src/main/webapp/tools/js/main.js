/***
 Metronic AngularJS App Main Script
 ***/

/* Metronic App */
var isdebug = false;
var manurl = 'http://localhost:8089/tis';

var MetronicApp = angular.module("MetronicApp", [
    "ui.router",
    "ui.bootstrap",
    "oc.lazyLoad",
    "ngSanitize",
    "ngSanitize",
    'angularFileUpload',
    'ui.grid',
    'ui.grid.selection',
    'ui.grid.exporter',
    'ui.grid.edit',
    'ui.grid.pagination',
    'ui.grid.resizeColumns',
    'ui.grid.emptyBaseLayer',
    'ui-iconpicker'
]);

function action(bdy){
    var request ={};
    var COH = {};
    var CTL = {};
    var BDY = {};
    COH.tranTime = getHHMMSS();
    COH.TRANSCODE = "TX1313";
    COH.OFSREQHEAD = "";
    COH.tranDate = getYYYYMMDD();
    COH.TRANSDATE = getFormatTimeHHmmss();
    COH.TRANSTELLER = "";
    COH.AGENTINSTNO = "";
    COH.AUTHTELLER = "";
    COH.medium = "";
    COH.consumerId = "";
    COH.WORKSTATIONIP = "";
    COH.TELLERWORKMODEL = "";
    COH.CHECKTELLER = "";
    COH.branchId = "";
    COH.tranCode = "TX1313";
    COH.TRANSINSTNO = "";
    COH.TRANSTELLERPWD = "";
    COH.TRANSSERIALNO = "";
    COH.tranTellerNo = "";
    COH.TRANSTIME = getHMS();
    COH.TRANSAUTHNO = "";
    CTL.TRANSMODELSERVICE="TCM";
    CTL.TRANSMODEL="1";
    CTL.ISREGOPRTRACE="";
    CTL.RECORDNUM="0";
    CTL.ISREGTXJOURNAL="";
    CTL.FILENAME="";
    CTL.FUNCODE="I";
    CTL.PAGENO="0";
    CTL.PAGELENGTH="20";
    CTL.OPERATEMODEL="";
    BDY = bdy;
    request.COH = COH;
    request.CTL = CTL;
    request.BDY = BDY;
    return request;
}

/* Configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
MetronicApp.config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
    $ocLazyLoadProvider.config({
        // global configs go here
    });
}]);

/********************************************
 BEGIN: BREAKING CHANGE in AngularJS v1.3.x:
 *********************************************/
/**
 `$controller` will no longer look for controllers on `window`.
 The old behavior of looking on `window` for controllers was originally intended
 for use in examples, demos, and toy apps. We found that allowing global controller
 functions encouraged poor practices, so we resolved to disable this behavior by
 default.

 To migrate, register your controllers with modules rather than exposing them
 as globals:

 Before:

 ```javascript
 function MyController() {
  // ...
}
 ```

 After:

 ```javascript
 angular.module('myApp', []).controller('MyController', [function() {
  // ...
}]);

 Although it's not recommended, you can re-enable the old behavior like this:

 ```javascript
 angular.module('myModule').config(['$controllerProvider', function($controllerProvider) {
  // this option might be handy for migrating old apps, but please don't use it
  // in new ones!
  $controllerProvider.allowGlobals();
}]);
 **/




MetronicApp.factory('httpInterceptor', ['$log', function($log) {
    return {
        //请求的方法
        request: function (config){
            //console.log(config);//打印所有的请求
            return config;
        },
        //响应的方法
        response: function (response){
            //console.log(response);//打印所有的响应
            if(response.status == '200'){//首先要请求成功
                if(response.config.url.indexOf(manurl) ==0 ){//判断post请求，post请求的url都是marurl开头。
                    if(response.data.status =="success"){
                        //console.log('成功的请求')
                    }else if(response.data.status =="error"){
                        //console.log('失败的请求,进行处理 ')
                    }else if(response.data.status =="failed"){
                        toastr['error']("登陆失效，请重新登陆!");
                        window.location = "../tools/login.html";//如果正确，则进入主页
                    }
                }
            }
            return response;
        }
    };
}]);

//引入http拦截器
MetronicApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('httpInterceptor');
}]);

/* 统一添加请求头方法，弊端，可以给不同的请求方式添加统一的请求头，不符合需求
MetronicApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.headers.common = { 'My-Header' : 'testceshi' }
}]);
*/



//AngularJS v1.3.x workaround for old style controller declarition in HTML
MetronicApp.config(['$controllerProvider', function($controllerProvider) {
    // this option might be handy for migrating old apps, but please don't use it
    // in new ones!
    $controllerProvider.allowGlobals();
}]);

/********************************************
 END: BREAKING CHANGE in AngularJS v1.3.x:
 *********************************************/

/* Setup global settings */
MetronicApp.factory('settings', ['$rootScope','$http', function($rootScope,$http) {
    // supported languages
    var settings = {
        utils:{},
        layout: {
            pageSidebarClosed: false, // sidebar menu state
            pageContentWhite: true, // set page content layout
            pageBodySolid: false, // solid body color state
            pageAutoScrollOnLoad: 1000 // auto scroll to top on page load
        },
        assetsPath: '../assets',
        globalPath: '../assets/global',
        layoutPath: '../assets/layouts/layout4'
    };

    settings.getchangliang=function(leibie){
        if (settings.chang[leibie] == undefined) {
            var promise = changliang_service.getchangliang(leibie);
            promise.then(function(d){
                settings.chang[leibie] = d;
            });
        }
    }

    settings.utils.back=function(){
        window.history.back()
    }
    settings.utils.initpicker=function(){
        ComponentsDateTimePickers.init();
    }
    settings.utils.initdropdown=function(){
        ComponentsDropdowns.init();
    }
    settings.utils.initSelect2=function(){
        if ($().select2) {
            $.fn.select2.defaults.set("theme", "bootstrap");
            $('.select2me').select2({
                placeholder: "Select",
                width: 'auto',
                allowClear: false
            });
        }
    }
    $rootScope.settings = settings;
    var constant = {};
    $rootScope.constant = constant;
    $rootScope.formatT = function(val){
        var mm="";
        if(val!=null&&val!=''){
            mm = val.substring(0,val.length-2);
            return mm;
        }
        return mm;
    }


    settings.diclist = {};
    /**
     * 获取代码数据库表数据
     * @param dictKey 代码数据表表名
     */
    settings.getDictData = function (dictKey) {
        if(_.isNil(settings.diclist[dictKey])) {
            var subForm = {};
            subForm.dictKey = dictKey;
            $http.post(manurl + "/DictController/queryDictItemListByDictKey",subForm).then(function (response) {
                settings.diclist[dictKey] = response.data.retMessage;
            });
        }
    }


    settings.commlist = {};
    /**
     * 获取机构-人员-工作组-职务-岗位
     * 翻译guid-objName
     * @param type
     */
    settings.getCommData = function (type,key) {
        if(type == "ORG"){
            if(_.isNil(settings.commlist[type])) {
                $http.post(manurl + "/om/org/queryAllorg").then(function (response) {
                    settings.commlist[type] = response.data.retMessage;
                });
            }
        }else if(type == "POS"){
            if(_.isNil(settings.commlist[type])) {
                $http.post(manurl + "/om/org/queryAllposition").then(function (response) {
                    settings.commlist[type] = response.data.retMessage;
                });
            }
        }else if(type == "EMP"){
            if(_.isNil(settings.commlist[type])) {
                $http.post(manurl + "/om/emp/queryemployee").then(function (response) {
                    settings.commlist[type] = response.data.retMessage;
                    console.log(response.data.retMessage)
                });
            }
        }else if(type == "ROLE"){
            if(_.isNil(settings.commlist[type])) {
                $http.post(manurl + "/AcRoleController/queryRoleList",{}).then(function (response) {
                    settings.commlist[type] = response.data.retMessage;
                });
            }
        }else if(type == "DUTY"){
            if(_.isNil(settings.commlist[type])) {
                $http.post(manurl + "/om/duty/loadallduty").then(function (response) {
                    console.log(response)
                    settings.commlist[type] = response.data.retMessage;
                });
            }
        }else if(type == "APP"){
            if(_.isNil(settings.commlist[type])) {
                $http.post(manurl + "/AcMenuController/queryAllAcApp",{}).then(function (response) {
                    settings.commlist[type] = response.data.retMessage;
                });
            }
        }else if(type == "OPER"){
            if(_.isNil(settings.commlist[type])) {
                $http.post(manurl + "/AcOperatorController/getOperatorsNotLinkEmp",{}).then(function (response) {
                    settings.commlist[type] = response.data.retMessage;
                    console.log(response.data.retMessage)
                });
            }
        }else if(type == "DICT"){
            if(_.isNil(settings.commlist[type])) {
                $http.post(manurl + "/DictController/querySysDictList",{}).then(function (response) {
                    settings.commlist[type] = response.data.retMessage;
                });
            }
        }else if(type == "BHVTYPE"){
            //查询所有行为类型
            if(_.isNil(settings.commlist[type])) {
                $http.post(manurl + "/AcAppController/functypequery",{}).then(function (response) {
                    settings.commlist[type] = response.data.retMessage;
                });
            }
        }
    }



    return settings;
}]);

/* Setup App Main Controller */
MetronicApp.controller('AppController', ['$scope','$rootScope','$http','$q', function($scope, $rootScope,$http,$q) {
   /* $scope.$on('$viewContentLoaded', function() {
        App.initComponents(); // init core components
        //Layout.init(); //  Init entire layout(header, footer, sidebar, etc) on page load if the partials included in server side instead of loading with ng-include directive
    });*/
    /*var res = $http.get(manurl + '/tools/json/service.json').then(function (response) {
        $rootScope.res = response.data;//绑定到rootscope中，在其他页面可以直接调用
        return response;
    })*/
    var res = $http.get('./json/service.json').then(function (response) {
        $rootScope.res = response.data;//绑定到rootscope中，在其他页面可以直接调用
        return response;
    })

}]);


/***
 Layout Partials.
 By default the partials are loaded through AngularJS ng-include directive. In case they loaded in server side(e.g: PHP include function) then below partial
 initialization can be disabled and Layout.init() should be called on page load complete as explained above.
 ***/

/* Setup Layout Part - Header */
MetronicApp.controller('HeaderController', ['$scope','filterFilter','$rootScope', '$http','$uibModal','common_service','$state', function($scope,filterFilter,$rootScope,$http,$uibModal,common_service,$state) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initHeader(); // init header
        Demo.init();
        // var res = $rootScope.res.login_service;//页面所需调用的服务
        var ret ={ctrl: "AcAuthenticationController", func: "pageInit", emo: "页面初始化"};
        common_service.post(ret,{}).then(function(data){
            if(data.status == "success"){
                var session = data.retMessage.user;
                session.opertor = sessionStorage.opertor;//身份绑定;
                $scope.userId = session.userId;//绑定登陆信息
                userinfo(data.retMessage.user);//登陆信息页面
                passedit(data.retMessage.user);//修改密码页面
                opermenu($scope.userId);//重组菜单入口
                persona(session);//个性化配置入口
                $rootScope.menus = angular.fromJson(data.retMessage.menu);
            }
        })
        /*var res = $rootScope.res.login_service;//页面所需调用的服务
        common_service.post(res.pageInit,{}).then(function(data){
            if(data.status == "success"){
                 var session = data.retMessage.user;
                session.opertor = sessionStorage.opertor;//身份绑定;
                $scope.userId = session.userId;//绑定登陆信息
                userinfo(data.retMessage.user);//登陆信息页面
                passedit(data.retMessage.user);//修改密码页面
                opermenu($scope.userId);//重组菜单入口
                persona(session);//个性化配置入口
                $rootScope.menus = angular.fromJson(data.retMessage.menu);
            }
        })*/
    });
    //个人信息页面
    function userinfo(item){
        $scope.information = function(){
            openwindow($uibModal, 'views/landinginfor/personalinfor.html','lg',
                function ($scope, $modalInstance) {
                    //var session = angular.fromJson(sessionStorage.user)
                    $scope.userid = item.userId;
                    $scope.operatorName = item.operatorName;
                    $scope.menuType = item.menuType;
                    $scope.lastLogin =moment(item.lastLogin).format('YYYY-MM-DD HH:mm:ss');
                    $scope.opertor = item.opertor;
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }
    }
    //修改密码页面
    function passedit(datas){
        $scope.improved = function(){
           /* var res = $rootScope.res.login_service;*/
            var ret ={"ctrl": "AcAuthenticationController", "func": "updatePassword","emo":"修改密码"};
            openwindow($uibModal, 'views/landinginfor/Improved.html','lg',
                function ($scope, $modalInstance) {
                    $scope.add = function(item){//保存新增的函数
                        var subFrom = {};
                        subFrom.userId =datas.userId;
                        subFrom.newPwd =item.newPassword ;
                        subFrom.oldPwd =item.oldPassword;
                        common_service.post(ret,subFrom).then(function(data){
                            if(data.status == "success"){
                                toastr['success']("修改成功！");
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
    }
    //注销登录
    $scope.logout  = function(){
       if(confirm('是否确定注销')){
           var res = {"ctrl": "AcAuthenticationController", "func": "logout","emo":"注销登陆"}//页面所需调用的服务
           common_service.post(res,{}).then(function(data){
               if(data.status == "success"){
                   window.location = "../tools/login.html";//如果正确，则进入主页
               }
           })
       }
    }

    //重组菜单
    function opermenu(item){
        $scope.opermenus = function(){
            $state.go("Reorganizemenu",{id:item})
        }

    }
    //个性化配置页面
    function persona(item){
        var opertis = {"userid":item.userId,"operguid":item.guid}
        var jsonObj= angular.toJson(opertis);//传对象，必须转成json格式传入
        $scope.personalise = function(){
            $state.go("roleConfig",{'id':jsonObj})
        }
    }
}]);

/* Setup Layout Part - Sidebar */
MetronicApp.controller('SidebarController', ['$scope', '$timeout','$rootScope','common_service',function($scope,$timeout,$rootScope,common_service) {
    $scope.$on('$includeContentLoaded', function () {
        Layout.initSidebar(); // init sidebar
        //拿不到数据,目前先写死 在找原因
        //调用菜单
        /*var res = $rootScope.res.login_service;//页面所需调用的服务
        common_service.post(res.pageInit, {}).then(function (data) {*/
            var ret ={ctrl: "AcAuthenticationController", func: "pageInit", emo: "页面初始化"};
            common_service.post(ret, {}).then(function (data) {
            if (data.status == "success") {
                var sessionjson = angular.fromJson(data.retMessage.menu);
                $scope.menusAndTrans = angular.copy(sessionjson);
                $scope.issearchmenu = false;//让搜索菜单隐藏
                //搜索框改变事件调用
                $scope.test = function (searchParam) {
                    if (_.isEmpty(searchParam)) { //如果是数组
                        // $('.search').html('');//制空
                        $scope.searchParam = '';//把搜索内容复制过去
                        $('.search').slideUp();//让搜索的内容隐藏
                        $(".ids1").slideDown();//让原本的树结构显示
                        $scope.issearchmenu = false;//让搜索菜单隐藏
                    }
                }
                //调用搜索逻辑
                $scope.search = function (searchParam) {
                    //如果不是数组,说明有搜索值,那就进行处理
                    if (_.isEmpty(searchParam)) { //如果是数组
                      /*  $('.search').slideDown();//让搜索的内容隐藏
                        $(".ids1").slideUp();//让原本的树结构显显示*!/*/
                    }else{
                        $scope.issearchmenu = true;//让搜索菜单隐藏
                        $scope.searchParam = searchParam;//把搜索内容复制过去
                        search([sessionjson], searchParam);//调用搜索方法，传入搜索的值
                       //看是否能用动画来做
                    }
                };
            }
        })

        function search(all, key) { //包装了一个搜索方法，只要数据结构做成类似的，这个直接拿来用。
            // $('.search').html('');//制空
            var sum = 0;//标识，判断第几次循环
            var html = ''//字符串模板
            var num = all//数组
            getArray(all, key);//调用
            function getArray(data, key) {
                sum++;//标识++
                var sumer = sum+1;
                if(!isNull(num)){
                    num = [];
                    for(var i=0;i< data.length;i++){
                        if(!isNull(data[i].children)){
                            num = num.concat(data[i].children);//把所有的子合并在一个数组中。即把所有的子取出
                        }
                    }
                }
                if (sum == 1) {//sum是标识，区分第一次循环
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].isLeaf == 'Y') {//如果是，那么按照最后一层循环
                            html +=  '<li class="nav-item"><a href=" '+ data[i].href + '"><i class="'+data[i].icon+'"></i><span class="title">'+data[i].label+'</span></a><ul class="sub-menu search'+ sumer +'" id="'+ data[i].guid +'"></ul></li>'
                        } else {//如果不是，按照正常循环方式
                            html +=  '<li class="nav-item"><a href="javascript:;"><i class="'+data[i].icon+'"></i><span class="title">'+data[i].label+'</span> <span class="arrow "></span></a><ul class="sub-menu search'+ sumer +'" id="'+ data[i].guid +'"></ul></li>'
                        }
                    }
                    $(".search" + sum).append(html);//追加到li中
                }

                for(var j =0;j<data.length;j++){//循环，拿到第一层,与本身这层进行判断，找到对应的层级，guid和parentguid匹配
                    var array = [];
                    for(var i =0; i<num.length;i++){//循环所有的子级，然后进行匹配
                        if(data[j].guid == num[i].parentGuid){
                            array.push(num[i]);
                        }
                    }
                    var htmltwo = '';//模板标识
                    array.forEach(function(v,i){
                        if(array[i].isLeaf =='Y'){//是否是最后一层，如果是，那么按照最后一层的样式拼接
                            /*if(array[i].label.indexOf(key)>0){
                                console.log(array[i])
                            }*/
                            if(array[i].label.indexOf(key) == 0){
                                console.log(array[i])
                                htmltwo += '<li><a style="height: 41px;line-height: 31px;"  href="#/'+array[i].href + '"><i class="'+array[i].icon+'"></i><span class="title">'+array[i].label+'</span></a></li>'
                                // htmltwo += '<li class="start nav-item"><a  style="height: 41px;line-height: 31px;"   href="javascript:;"><i class="'+array[i].icon+'"></i><span class="title">'+array[i].label+'</span><span class="arrow "></span></a><ul class="sub-menu search'+ sumer +'"id="'+ array[i].guid+'"></ul></li>'
                            }

                        }else if(array[i].isLeaf !=='Y' && !isNull(array[i].children) ){//否则，按照非最后一层样式拼接
                            /*if(array[i].label.indexOf(key) == 0){
                                htmltwo += '<li class="start nav-item"><a  style="height: 41px;line-height: 31px;"   href="javascript:;"><i class="'+array[i].icon+'"></i><span class="title">'+array[i].label+'</span><span class="arrow "></span></a><ul class="sub-menu ids'+ sumer +'"id="'+ array[i].guid+'"></ul></li>'
                            }*//*if(array[i].label.indexOf(key) == 0){
                                htmltwo += '<li class="start nav-item"><a  style="height: 41px;line-height: 31px;"   href="javascript:;"><i class="'+array[i].icon+'"></i><span class="title">'+array[i].label+'</span><span class="arrow "></span></a><ul class="sub-menu ids'+ sumer +'"id="'+ array[i].guid+'"></ul></li>'
                            }*/
                        }
                    })

                    $('#'+data[j].guid).append(htmltwo);//追加到对应的父节点的标签中
                }
                if(sum<40){//标识,如果有children,那么就一直递归下去
                    getArray(num,key);//递归调用
                }else{
                    return num;
                }
            }

        }
    });
}]);

MetronicApp.controller('QuickSidebarController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        setTimeout(function(){
            QuickSidebar.init(); // init quick sidebar
        }, 2000)
    });
}]);

/* Setup Layout Part - Sidebar */
/*
MetronicApp.controller('PageHeadController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Demo.init(); // init theme panel
    });
}]);

*/

/* Setup Layout Part - Footer */
MetronicApp.controller('FooterController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initFooter(); // init footer
    });
}]);

MetronicApp.controller('ThemePanelController', ['$scope','common_service','$rootScope', '$http' ,function($scope,common_service,$rootScope,$http) {
    $scope.$on('$includeContentLoaded', function() {
        var color_ = localStorage.getItem("colors_")
        $('#style_color1').attr("href", Layout.getLayoutCssPath() + 'themes/' + color_ + ".css");//设置成我们想要的

        var res = $rootScope.res.login_service;//页面所需调用的服务
        common_service.post(res.pageInit,{}).then(function(data){
            if(data.status == "success"){
                var session = data.retMessage.user;
                //获取用户选择的配置信息
                getsubColor(session)
                //存储用户选择颜色信息
                subjectColor(session.guid)

            }
        })
        Demo.init(); // init theme panel
        //存储主题风格颜色内容
        function subjectColor(operguid){
            $("#proid").find("li").on("click",function(){
                var tis = $(this).attr('data-style');//用户选择的颜色值
                var res = $rootScope.res.operator_service;//页面所需调用的服务
                var subFrom = {};
                subFrom.guidOperator = operguid;
                subFrom.guidConfig = 'OPERATORCFG1507720783';
                subFrom.configValue = tis;
                //把用户选择的颜色存入后台
                common_service.post(res.saveOperatorLog,subFrom).then(function(data){
                    var datas = data.retMessage;
                    if(data.status == "success"){
                        toastr['success']("保存成功");
                    }
                })
            })
        }
        //取用户存储的主题风格颜色
        function getsubColor(item){
            var subFrom = {}
            subFrom.userId= item.userId;
            subFrom.appGuid= 'APP1499956132';
            var res = $rootScope.res.operator_service;//页面所需调用的服务
            common_service.post(res.queryOperatorConfig,subFrom).then(function(data){
                var datas = data.retMessage;
                if(data.status == "success"){
                    var styles = datas.style
                    for(var i = 0; i<styles.length; i++){
                        if(styles[i].configDict == "DICT_STYLE_COLOR"){
                            var color_ = styles[i].configValue;//用户之前存储的颜色
                            $('#style_color1').attr("href", Layout.getLayoutCssPath() + 'themes/' + color_ + ".css");//设置成用户之前保存的
                        }

                    }
                }
            })
        }

    });
}]);



MetronicApp.controller('Memocontroller', ['$scope','Memo_service', function($scope,Memo_service) {
    var promise = Memo_service.search({})//查询
    promise.then(function (data) {  // 调用承诺API获取数据 .resolve
        var times = [];
        var tims = new Date();
        //时间转换
        for(var i=0;i<data.list.length;i++){
            data.list[i].times = new Date(data.list[i].memo_date.replace(/-/g, "/"));
            data.list[i].item = moment(data.list[i].memo_date).format('YYYY-MM-DD');
            data.list[i].paixu = data.list[i].times.getTime();
        }
        //比较时间
        for(var i =0; i<data.list.length; i++){
            var dats = data.list[i].times.getTime()-tims.getTime()
            if(data.list[i].times.getTime()-tims.getTime()<432000000 &&data.list[i].times.getTime()>tims.getTime()){
                times.push(data.list[i])
            }
        }
        //冒泡排序
        (function maopaopaixu() {
            for (var i = 0; i < times.length - 1; i++) {
                for (var j = 0; j < times.length - 1 - i; j++) {
                    if (times[j].paixu > times[j + 1].paixu) {
                        var tmp = times[j];
                        times[j] = times[j + 1];
                        times[j + 1] = tmp;
                    }
                }
            }
            $scope.dats = times;
        })();
    });

}]);

var strs = {

};

//服务端定义路由控制，这里主要作用就是取数据
MetronicApp.provider('router', function ($stateProvider) {
    //定义一个router服务
    var urlCollection;

    this.$get = function ($http, $state) {
        return {
            setUpRoutes: function () {
                $http.get(urlCollection).success(function (collection) {
                    for (var routeName in collection) {
                        //对每一项进行循环
                        if (!$state.get(routeName)) {
                            $stateProvider.state(routeName, collection[routeName]);
                        }
                    }
                });
            }
        }
    };

    this.setCollectionUrl = function (url) {
        urlCollection = url;
    }
})

//配置内容,首页写死，其他页面路由从后台拿取
MetronicApp.config(function ($stateProvider, $urlRouterProvider, routerProvider) {
    $urlRouterProvider.otherwise('/dashboard');
        $stateProvider
            .state('dashboard', {
                url: '/dashboard',
                templateUrl: "views/dashboard.html",
                data: {pageTitle: '控制台'},
                controller: "DashboardController",
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'MetronicApp',
                            insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                            files: [
                                '../assets/global/plugins/morris/morris.css',
                                '../assets/global/plugins/morris/morris.min.js',
                                '../assets/global/plugins/morris/raphael-min.js',
                                '../assets/global/plugins/jquery.sparkline.min.js',

                                '../assets/pages/scripts/dashboard.min.js',
                                'js/controllers/DashboardController.js',
                            ]
                        });
                    }]
                }
            });
        routerProvider.setCollectionUrl('json/test.json');
    })
    .run(function (router) {
        router.setUpRoutes();
    });


/* Setup Rounting For All Pages */
/*MetronicApp.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

    // Redirect any unmatched url
    $urlRouterProvider.otherwise("/dashboard.html");
    $stateProvider
        // Dashboard
        .state('dashboard', {
            url: "/dashboard.html",
            templateUrl: "views/dashboard.html",
            data: {pageTitle: '控制台'},
            controller: "DashboardController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            '../assets/global/plugins/morris/morris.css',
                            '../assets/global/plugins/morris/morris.min.js',
                            '../assets/global/plugins/morris/raphael-min.js',
                            '../assets/global/plugins/jquery.sparkline.min.js',

                            '../assets/pages/scripts/dashboard.min.js',
                            'js/controllers/DashboardController.js',
                        ]
                    });
                }]
            }
        })

        // AngularJS plugins
        .state('fileupload', {
            url: "/file_upload.html",
            templateUrl: "views/file_upload.html",
            data: {pageTitle: 'AngularJS File Upload'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'angularFileUpload',
                        files: [
                            '../assets/global/plugins/angularjs/plugins/angular-file-upload/angular-file-upload.min.js',
                        ]
                    }, {
                        name: 'MetronicApp',
                        files: [
                            'js/controllers/GeneralPageController.js'
                        ]
                    }]);
                }]
            }
        })

        // UI Select
        .state('uiselect', {
            url: "/ui_select.html",
            templateUrl: "views/ui_select.html",
            data: {pageTitle: 'AngularJS Ui Select'},
            controller: "UISelectController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'ui.select',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
                            '../assets/global/plugins/angularjs/plugins/ui-select/select.min.css',
                            '../assets/global/plugins/angularjs/plugins/ui-select/select.min.js'
                        ]
                    }, {
                        name: 'MetronicApp',
                        files: [
                            'js/controllers/UISelectController.js'
                        ]
                    }]);
                }]
            }
        })

        // UI Bootstrap
        .state('uibootstrap', {
            url: "/ui_bootstrap.html",
            templateUrl: "views/ui_bootstrap.html",
            data: {pageTitle: 'AngularJS UI Bootstrap'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        files: [
                            'js/controllers/GeneralPageController.js'
                        ]
                    }]);
                }]
            }
        })

        // Tree View
        .state('tree', {
            url: "/tree",
            templateUrl: "views/tree.html",
            data: {pageTitle: 'jQuery Tree View'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
                            '../assets/global/plugins/jstree/dist/themes/default/style.min.css',

                            '../assets/global/plugins/jstree/dist/jstree.min.js',
                            '../assets/pages/scripts/ui-tree.min.js',
                            'js/controllers/GeneralPageController.js'
                        ]
                    }]);
                }]
            }
        })

        // Form Tools
        .state('formtools', {
            url: "/form-tools",
            templateUrl: "views/form_tools.html",
            data: {pageTitle: 'Form Tools'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
                            '../assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css',
                            '../assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css',
                            '../assets/global/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css',
                            '../assets/global/plugins/typeahead/typeahead.css',

                            '../assets/global/plugins/fuelux/js/spinner.min.js',
                            '../assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js',
                            '../assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js',
                            '../assets/global/plugins/jquery.input-ip-address-control-1.0.min.js',
                            '../assets/global/plugins/bootstrap-pwstrength/pwstrength-bootstrap.min.js',
                            '../assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js',
                            '../assets/global/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js',
                            '../assets/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js',
                            '../assets/global/plugins/typeahead/handlebars.min.js',
                            '../assets/global/plugins/typeahead/typeahead.bundle.min.js',
                            '../assets/pages/scripts/components-form-tools-2.min.js',

                            'js/controllers/GeneralPageController.js'
                        ]
                    }]);
                }]
            }
        })

        // Date & Time Pickers
        .state('pickers', {
            url: "/pickers",
            templateUrl: "views/pickers.html",
            data: {pageTitle: 'Date & Time Pickers'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
                            '../assets/global/plugins/clockface/css/clockface.css',
                            '../assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css',
                            '../assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css',
                            '../assets/global/plugins/bootstrap-colorpicker/css/colorpicker.css',
                            '../assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css',

                            '../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js',
                            '../assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js',
                            '../assets/global/plugins/clockface/js/clockface.js',
                            '../assets/global/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js',
                            '../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js',

                            '../assets/pages/scripts/components-date-time-pickers.min.js',

                            'js/controllers/GeneralPageController.js'
                        ]
                    }]);
                }]
            }
        })

        // Custom Dropdowns
        .state('dropdowns', {
            url: "/dropdowns",
            templateUrl: "views/dropdowns.html",
            data: {pageTitle: 'Custom Dropdowns'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
                            '../assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css',
                            '../assets/global/plugins/select2/css/select2.min.css',
                            '../assets/global/plugins/select2/css/select2-bootstrap.min.css',

                            '../assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js',
                            '../assets/global/plugins/select2/js/select2.full.min.js',

                            '../assets/pages/scripts/components-bootstrap-select.min.js',
                            '../assets/pages/scripts/components-select2.min.js',

                            'js/controllers/GeneralPageController.js'
                        ]
                    }]);
                }]
            }
        })

        // Advanced Datatables
        .state('datatablesAdvanced', {
            url: "/datatables/managed.html",
            templateUrl: "views/datatables/managed.html",
            data: {pageTitle: 'Advanced Datatables'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
                            '../assets/global/plugins/datatables/datatables.min.css',
                            '../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css',

                            '../assets/global/plugins/datatables/datatables.all.min.js',

                            '../assets/pages/scripts/table-datatables-managed.min.js',

                            'js/controllers/GeneralPageController.js'
                        ]
                    });
                }]
            }
        })

        // Ajax Datetables
        .state('datatablesAjax', {
            url: "/datatables/ajax.html",
            templateUrl: "views/datatables/ajax.html",
            data: {pageTitle: 'Ajax Datatables'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
                            '../assets/global/plugins/datatables/datatables.min.css',
                            '../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css',
                            '../assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css',

                            '../assets/global/plugins/datatables/datatables.all.min.js',
                            '../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js',
                            '../assets/global/scripts/datatable.min.js',

                            'js/scripts/table-ajax.js',
                            'js/controllers/GeneralPageController.js'
                        ]
                    });
                }]
            }
        })

        // User Profile
        .state("profile", {
            url: "/profile",
            templateUrl: "views/profile/main.html",
            data: {pageTitle: 'User Profile'},
            controller: "UserProfileController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
                            '../assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css',
                            '../assets/pages/css/profile.css',
                            '../assets/global/plugins/jquery.sparkline.min.js',
                            '../assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js',
                            '../assets/pages/scripts/profile.min.js',
                            'js/controllers/UserProfileController.js'
                        ]
                    });
                }]
            }
        })

        // User Profile Dashboard
        .state("profile.dashboard", {
            url: "/dashboard",
            templateUrl: "views/profile/dashboard.html",
            data: {pageTitle: 'User Profile'}
        })

        // User Profile Account
        .state("profile.account", {
            url: "/account",
            templateUrl: "views/profile/account.html",
            data: {pageTitle: 'User Account'}
        })

        // User Profile Help
        .state("profile.help", {
            url: "/help",
            templateUrl: "views/profile/help.html",
            data: {pageTitle: 'User Help'}
        })

        .state("commonCaller",{
            url:"/commonCaller.html",
            templateUrl:"views/common/commonCaller.html",
            data: {pageTitle: '通用Controller调用'},
            controller:"commonCaller_controller"
        })

        .state("biztraceHandle",{
            url:"/biztraceHandle.html",
            templateUrl:"views/biztrace/biztraceHandle.html",
            data: {pageTitle: '业务日志分析'},
            controller:"biztraceHandle_controller"
        })

        .state("biztrace",{
            url:"/biztraceQuery.html",
            templateUrl:"views/biztrace/biztraceQuery.html",
            data: {pageTitle: '业务日志查询'},
            controller:"biztraceQuery_controller",
        })

        .state("biztraceSum",{
            url:"/biztraceSumQuery.html",
            templateUrl:"views/biztrace/biztraceSumQuery.html",
            data: {pageTitle: '业务日志汇总查询'},
            controller:"biztraceSumQuery_controller"
        })

        .state("redis_clean",{
            url:"/redisClean.html",
            templateUrl:"views/biztrace/redisClean.html",
            data: {pageTitle: 'redis清理'},
            controller:"redisClean_controller"
        })

        .state("listcheck",{
            url:"/listcheck.html",
            templateUrl:"views/LPC/listcheck.html",
            data: {pageTitle: '清单-包检查'},
            controller:"ListCheck_controller"
        })

        .state("featureReg",{
            url:"/featureReg.html",
            templateUrl:"views/LPC/featureReg.html",
            data: {pageTitle: '开发分支登记'},
            controller:"FeatureReg_controller"
        })
        .state("abftree",{
            url:"/abftree.html",
            templateUrl:"views/org/abftree.html",
            data: {pageTitle: '组织机构管理'},
            controller:"abftree_controller"
        })
        .state("applicationFun",{
            url:"/applicationFun.html",
            templateUrl:"views/Jurisdiction/applicationFun.html",
            data: {pageTitle: '应用功能管理'},
            controller:"application_controller",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before',
                        files: [
                            '../assets/global/plugins/angular-ui-grid/ui-grid2.min.js'
                            // '../assets/global/plugins/angular-ui-grid/ui-grid.js'
                        ]
                    }]);
                }]
            }
        })
        .state("menuManagement",{
            url:"/menuManagement.html",
            templateUrl:"views/Management/menuManagement.html",
            data: {pageTitle: '菜单管理'},
            controller:"menu_controller"
        })
        .state("roleManagement",{
            url:"/roleManagement.html",
            templateUrl:"views/roleManage/roleManagement.html",
            data: {pageTitle: '角色管理'},
            controller:"role_controller"
        })
        .state("behavior",{
            url:"/behavior.html",
            templateUrl:"views/behavior/behavior.html",
            data: {pageTitle: '功能管理'},
            controller:"behavior_controller"
        })
        .state("opManage",{
            url:"/opManage.html",
            templateUrl:"views/operator/opManage.html",
            data: {pageTitle: '操作员管理'},
            controller:"opmanage_controller"
        })
        .state("operatsetqx",{
            url:"/operatsetqx.html/{id:.*}",
            templateUrl:"views/operator/operatsetqx.html",
            data: {pageTitle: '操作员个人配置'},
            controller:"operat_controller"
        })
        .state("permission",{
            url:"/permission.html/{id:.*}",
            templateUrl:"views/permission/permission.html",
            data: {pageTitle: '操作员功能行为权限配置'},
            controller:"permission_controller"
        })
        .state("Reorganizemenu",{
            url:"/Reorganizemenu.html/{id:.*}",
            templateUrl:"views/operator/Reorganizemenu.html",
            data: {pageTitle: '重组菜单'},
            controller:"reomenu_controller"
        })
        .state("roleConfig",{
            url:"/roleConfig.html/{id:.*}",
            templateUrl:"views/operator/roleConfig.html",
            data: {pageTitle: '操作员个性配置'},
            controller:"operconfig_controller"
        })
        .state("shortcutMenu",{
            url:"/shortcutMenu.html",
            templateUrl:"views/shortcutMenu/shortcutMenu.html",
            data: {pageTitle: '快捷菜单'},
            controller:"shortcutMenu_controller"
        })
        .state("operstatus",{
            url:"/operstatus.html",
            templateUrl:"views/operator/operstatus.html",
            data: {pageTitle: '操作员身份'},
            controller:"operstatus_controller"
        })
        .state("emp",{
            url:"/Emp.html",
            templateUrl:"views/emp/emp.html",
            data: {pageTitle: '员工管理'},
            controller:"Emp_controller"
        })
        .state("Workgroup", {
            url: "/Workgroup.html",
            templateUrl: "views/Workgroup/Workgroup.html",
            data: {pageTitle: '工作组管理'},
            controller: "Workgroup_controller"
        })
        .state("dictionary",{
            url:"/dictionary.html",
            templateUrl:"views/dictionary/dictionary.html",
            data: {pageTitle: '业务字典'},
            controller:"dictionary_controller"
        })
        .state("numberResources",{
            url:"/numberResources.html",
            templateUrl:"views/numberResources/numberResources.html",
            data: {pageTitle: '序号资源表'},
            controller:"numres_controller"
        })
        .state("duty",{
            url:"/duty.html",
            templateUrl:"views/duty/duty.html",
            data: {pageTitle: '职务定义'},
            controller:"duty_controller"
        })
        .state("Systempara",{
            url:"/Systempara.html",
            templateUrl:"views/Systempara/Systempara.html",
            data: {pageTitle: '序号资源表'},
            controller:"systempara_controller"
        })
        .state("busiorg",{
            url:"/busiorg.html",
            templateUrl:"views/busiorg/busiorg.html",
            data: {pageTitle: '业务机构'},
            controller:"busiorg_controller"
        })
        .state("transtime",{
            url:"/transtimeer.html",
            templateUrl:"views/transtimes/transtimeer.html",
            data: {pageTitle: '定时器'},
            controller:"transtime_controller"
        })
        .state("journal",{
            url:"/journal.html",
            templateUrl:"views/journal/journal.html",
            data: {pageTitle: '日志页面'},
            controller:"journal_controller"
        })
        .state("journinfo",{
            url:"/journinfo.html/{id:.*}",
            templateUrl:"views/journal/journinfo.html",
            data: {pageTitle: '日志详情页面'},
            controller:"jourinfo_controller"
        })
        .state("loghistory",{
            url:"/loghistory.html/{id:.*}",
            templateUrl:"views/journal/loghistory.html",
            data: {pageTitle: '日志历史页面'},
            controller:"loghistory_controller"
        })
        .state("configuration",{
            url:"/configuration.html",
            templateUrl:"views/configuration/configuration.html",
            data: {pageTitle: '个性化配置管理'},
            controller:"configuration_controller"
        })

}]);*/

//angular路由监控，跳转开始之前。
MetronicApp.run(['$rootScope', '$state', function ($rootScope, $state) {
    $rootScope.$state = $state;
    console.log($rootScope.$state)
    $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){
        if(!isNull(toState.header)){
            $rootScope.Appfunc = toState.header;//把路由中对应的请求头，放入全局rootscope中。
        }
    });
}
]);

/* Init global settings and run the app */
MetronicApp.run(["$rootScope", "settings", "$state", function($rootScope, settings, $state) {
    $rootScope.$state = $state; // state to be accessed from view
    $rootScope.$settings = settings; // state to be accessed from view
}]);