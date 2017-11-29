/***
 Metronic AngularJS App Main Script
 ***/

/* Metronic App */
var isdebug = false;
var manurl = 'http://localhost:8089/tis';
// var manurl = 'http://106.15.103.14:8080/tis';

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

/* Configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
MetronicApp.config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
    $ocLazyLoadProvider.config({
        // global configs go here
    });
}]);

//http请求拦截器
MetronicApp.factory('httpInterceptor', ['$log', function($log) {
    return {
        //请求的方法
        request: function (config){
            //console.log(config);//打印所有的请求
            return config;
        },
        //响应的方法
        response: function (response){
            // console.log(response);//打印所有的响应
            if(response.status == '200'){//首先要请求成功
                if(response.config.url.indexOf(manurl) ==0 ){//判断post请求，post请求的url都是marurl开头。
                    if(response.data.status =="success"){
                        //console.log('成功的请求')
                    }else if(response.data.status =="error"){
                        //console.log('失败的请求,进行处理 ')
                    }else if(response.data.status =="unAuth"){
                        toastr['error']("登陆失效，请重新登陆!");
                        window.location = "../tools/login.html";//如果正确，则进入主页
                    }
                }
            }else{
                window.location = "../tools/views/errorInfo/ServerException.html";//如果不正确，则进入500页面，说明服务器异常
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
MetronicApp.factory('settings', ['$rootScope','$http','common_service', function($rootScope,$http,common_service) {
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
            $http.post(manurl + "/om/emp/queryemployee").then(function (response) {
                settings.commlist[type] = response.data.retMessage;
            });

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
MetronicApp.controller('AppController', ['$scope','$rootScope','$http','$q','common_service','$state','$uibModal','$timeout', function($scope, $rootScope,$http,$q,common_service,$state,$uibModal,$timeout) {
    $scope.$on('$includeContentLoaded', function() {
        Demo.init(); // init theme panel 初始化主题风格
    });


    var res = $http.get('./json/service.json').then(function (response) {
        $rootScope.res = response.data;//绑定到rootscope中，在其他页面可以直接调用
        return response;
    });
    var ret ={ctrl: "AcAuthenticationController", func: "pageInit", emo: "页面初始化"};
    common_service.post(ret,{}).then(function(data){
        console.log(data)
        if(data.status == "success"){
            $rootScope.userconfig = data.retMessage;//绑定个全局使用
            //1.0头部请求信息
            var session  = $rootScope.userconfig;//拿去所有大数据
            $scope.userId = session.user.userId;//绑定登陆信息
            session.user.opertor = sessionStorage.opertor;//身份绑定;
            userinfo(session.user);//登陆信息页面
            passedit(session.user);//修改密码页面
            opermenu($scope.userId);//重组菜单入口
            persona(session.user);//个性化配置入口

            //2.0 菜单栏调用信息
            var sessionjson = angular.fromJson(data.retMessage.menu);
            $scope.menusAndTrans = angular.copy(sessionjson);
            $scope.issearchmenu = false;//让搜索菜单隐藏
            $timeout(function () {
                Layout.initSidebar(); // 初始化菜单
            },500)
            //菜单搜索框改变事件调用
            $scope.test = function (searchParam) {
                if (_.isEmpty(searchParam)) { //如果是数组
                    // $('.search').html('');//制空
                    $scope.searchParam = '';//把搜索内容复制过去
                    $('.search').slideUp();//让搜索的内容隐藏
                    $(".ids1").slideDown();//让原本的树结构显示
                    $scope.issearchmenu = false;//让搜索菜单隐藏
                }
            }
            //菜单调用搜索逻辑
            $scope.search = function (searchParam) {
                //如果不是数组,说明有搜索值,那就进行处理
                if (_.isEmpty(searchParam)) { //如果是数组
                }else{
                    $scope.issearchmenu = true;//让搜索菜单隐藏
                    $scope.searchParam = searchParam;//把搜索内容复制过去
                    search([sessionjson], searchParam);//调用搜索方法，传入搜索的值
                    //看是否能用动画来做
                }
            };
        }
    })
    //1.0   头部信息请求
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
                    $scope.opertor =item.opertor;
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
                    sessionStorage.clear('toState');
                    sessionStorage.clear('toParams');
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

    /*2.0 菜单栏搜索方法 */
    function search(all, key) {
        //包装了一个搜索方法，只要数据结构做成类似的，这个直接拿来用。
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


}])


/***
 Layout Partials.
 By default the partials are loaded through AngularJS ng-include directive. In case they loaded in server side(e.g: PHP include function) then below partial
 initialization can be disabled and Layout.init() should be called on page load complete as explained above.
 ***/

/* Setup Layout Part - Header */
MetronicApp.controller('HeaderController', ['$scope','filterFilter','$rootScope', '$http','$uibModal','common_service','$state','$timeout', function($scope,filterFilter,$rootScope,$http,$uibModal,common_service,$state,$timeout) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initHeader(); // init header
        Demo.init();
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
MetronicApp.controller('PageHeadController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Demo.init(); // init theme panel
    });
}]);


/* Setup Layout Part - Footer */
MetronicApp.controller('FooterController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initFooter(); // init footer
    });
}]);

//菜单控制器
MetronicApp.controller('SidebarController', ['$scope', '$timeout','$rootScope','common_service',function($scope,$timeout,$rootScope,common_service) {
    $scope.$on('$includeContentLoaded', function () {
        //Layout.initSidebar(); // init sidebar
        //定时器，去取数据
        /*console.log($rootScope.userconfig)
                //万一拿不到数据，才请求后台，以防万一
                var ret ={ctrl: "AcAuthenticationController", func: "pageInit", emo: "页面初始化"};
                common_service.post(ret, {}).then(function (data) {

                })*/

    });
}]);

//自定义主题控制器
MetronicApp.controller('ThemePanelController', ['$scope','common_service','$rootScope', '$http' ,'$timeout',function($scope,common_service,$rootScope,$http,$timeout) {
    $scope.$on('$includeContentLoaded', function() {
        Demo.init(); // init theme panel
        $timeout(function () {
            if(!isNull($rootScope.userconfig)){
                var sessionUser = $rootScope.userconfig.user;
                //存储用户选择颜色信息
                subjectColor(sessionUser.guid);
                //获取
                getsubColor($rootScope.userconfig);
            }
        },1000)
        //3.0    颜色风格方法内容---更改颜色
        var color_ = localStorage.getItem("colors_")
        if(!isNull(color_)){
            $('#style_color1').attr("href", Layout.getLayoutCssPath() + 'themes/' + color_ + ".css");//设置成我们想要的
        }

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
            if(!isNull(item.configs)){
                var configArray = item.configs;
                for(var i=0;i<configArray.length;i++){
                    if(configArray[i].configDict == "DICT_STYLE_COLOR"){
                        var color_ = configArray[i].configValue;//用户之前存储的颜色
                        $('#style_color1').attr("href", Layout.getLayoutCssPath() + 'themes/' + color_ + ".css");//设置成用户之前保存的
                    }
                }
            }
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

//服务端定义路由控制，这里主要作用就是取数据
MetronicApp.provider('router', function ($stateProvider,$urlRouterProvider) {
    this.$get = function ($http, $state,$rootScope,$timeout,common_service) {
        var ret ={ctrl: "AcAuthenticationController", func: "pageInit", emo: "页面初始化"};
        return {
            setUpRoutes: function () {
                $timeout(function () {
                    if(!isNull($rootScope.userconfig)){
                        var datas =$rootScope.userconfig.resources;
                        var collection = JSON.parse(datas);
                        for (var routeName in collection) {
                            if (!$state.get(routeName)) {
                                $stateProvider.state(routeName, collection[routeName]);
                            }
                        }
                        //如何判断是第一次？这是一个问题,还存在一个问题，如果地址栏发生改变，那么会跳转到原来存储的位置,解决思路--地址栏发生改变就清空
                        if(!isNull(sessionStorage.getItem('toState'))){
                            $state.go(sessionStorage.getItem('toState'),{id:sessionStorage.getItem('toParams')});
                        }
                        $urlRouterProvider.otherwise('/dashboard');
                    }
                },2000)
                /*common_service.post(ret,{}).then(function(data){
                    console.log(data)
                    var datas = data.retMessage.resources;
                    if(!isNull(datas)){
                        var collection = JSON.parse(datas)
                    }
                    if(data.status == "success"){
                        for (var routeName in collection) {
                            if (!$state.get(routeName)) {
                                $stateProvider.state(routeName, collection[routeName]);
                            }
                        }
                        //如何判断是第一次？这是一个问题,还存在一个问题，如果地址栏发生改变，那么会跳转到原来存储的位置,解决思路--地址栏发生改变就清空
                        if(!isNull(sessionStorage.getItem('toState'))){
                            $state.go(sessionStorage.getItem('toState'),{id:sessionStorage.getItem('toParams')});
                        }
                        $urlRouterProvider.otherwise('/dashboard');
                    }
                })*/
            }
        }
    };
})

//配置内容,首页写死，其他页面路由从后台拿取
MetronicApp.config(function ($stateProvider, $urlRouterProvider, routerProvider) {
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
        })
        .state('Nopermission', {
            url: "/403",
            templateUrl: "views/errorInfo/Nopermission.html",
            data: {pageTitle: '无权限访问'},
            controller: "errorInfo_controller"
        })
        .state('NonExistent', {
            url: "/404",
            templateUrl: "views/errorInfo/NonExistent.html",
            data: {pageTitle: '访问不存在'},
            controller: "errorInfo_controller"
        })
        .state('ServerException', {
            url: "/500",
            templateUrl: "views/errorInfo/ServerException.html",
            data: {pageTitle: '服务器异常'},
            controller: "errorInfo_controller"
        })
        .state('Reorganizemenu', {
            url: "/Reorganizemenu/{id:.*}",
            templateUrl: "views/operator/Reorganizemenu.html",
            data: {pageTitle: '重组菜单'},
            controller: "reomenu_controller"
        })
}).run(function (router) {
    router.setUpRoutes();
});

//angular路由监控，跳转开始之前。
MetronicApp.run(['$rootScope', '$state','$http','common_service', function ($rootScope, $state,$http,common_service) {
    $rootScope.$state = $state;
    $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){
        if(toState.name !=='dashboard'){//如果不是首页保存到临时存储中
            sessionStorage.setItem('toState',toState.name);
            sessionStorage.setItem('toParams',toParams.id);//把传入的值也放进存储中,刷新也保存着
        }

        if(toState.name =='dashboard' && fromState.name !==''){//如果新目标是首页并且原本的路由不是空，那就代表首页刷新
            sessionStorage.setItem('toState',toState.name);
        }
        if(toState.name !=='dashboard'&& toState.name !==''&& toState.name !=='NonExistent'&& toState.name !=='Nopermission'){
            var subFrom = {};
            subFrom.funcCode = toState.funcCode;//新路由的功能id
            var ret ={"ctrl": "AcAuthenticationController", "func": "viewCheck","emo":"用于路由跳转权限判断"};
            common_service.post(ret,{data:subFrom}).then(function(data){
                if(data.status == 'forbid'){
                    event.preventDefault();// 取消默认跳转行为
                    $state.go('Nopermission');//无权限，跳转
                }
            })
        }

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