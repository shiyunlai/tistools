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
    'angularFileUpload',
    'ui.grid',
    'ui.grid.selection',
    'ui.grid.exporter',
    'ui.grid.edit',
    'ui.grid.pagination',
    'ui.grid.resizeColumns',
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
MetronicApp.factory('settings', ['$rootScope', function($rootScope,service) {
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

    $rootScope.settings = settings;

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
     * @param codetype 代码数据表类型
     */
    settings.getBpmsDicCodeData = function (dictKey) {
        console.debug(settings.diclist[dictKey]);
        if(_.isNil(settings.diclist[dictname])) {
            var subForm = {};
            subForm.dictKey = dictKey;
            service.post("DictController", "queryDictItemListByDictKey", subForm, null, function (data) {
                if (data.status == 'success') {
                    settings.diclist[codetype] = data.data;
                }
            })
        }
    }
    settings.getDicCodeData = function (codetype, table) {
        if (isNull(settings.diclist[codetype])) {
            var subForm = {};
            subForm.code_data_type = codetype;
            if (isNull(table)) {
                service.post("common", "getConstant", subForm, null, function (data) {
                    if (data.status == '1') {
                        settings.diclist[codetype] = data.data;
                    }
                })
            } else if (table == 'F') {
                service.post("common", "getConstantFreq", subForm, null, function (data) {
                    if (data.status == '1') {
                        settings.diclist[codetype + table] = data.data;
                    }
                })
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
    var res = $http.get(manurl + '/tools/json/service.json').then(function (response) {
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
MetronicApp.controller('HeaderController', ['$scope','filterFilter','$rootScope', '$http','$uibModal','common_service', function($scope,filterFilter,$rootScope,$http,$uibModal,common_service) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initHeader(); // init header
        Demo.init();


    });
    //个人信息页面
    $scope.information = function(){
        openwindow($uibModal, 'views/landinginfor/personalinfor.html','lg',
            function ($scope, $modalInstance) {
                var session = angular.fromJson(sessionStorage.user)
                $scope.userid = session.userId;
                $scope.operatorName = session.operatorName;
                $scope.menuType = session.menuType;
                $scope.lastLogin = session.lastLogin;
                $scope.opertor = sessionStorage.opertor;
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }
    //修改密码页面
    $scope.improved = function(){
        var res = $rootScope.res.login_service;
        openwindow($uibModal, 'views/landinginfor/Improved.html','lg',
            function ($scope, $modalInstance) {
                $scope.add = function(item){//保存新增的函数
                    var subFrom = {};
                    var session = angular.fromJson(sessionStorage.user)
                    subFrom.userId =session.userId;
                    subFrom.newPwd =item.newPassword ;
                    subFrom.oldPwd =item.oldPassword;
                    common_service.post(res.updatePassword,subFrom).then(function(data){
                        console.log(data);
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

}]);

/* Setup Layout Part - Sidebar */
MetronicApp.controller('SidebarController', ['$scope', '$timeout',function($scope,$timeout) {
    $scope.$on('$includeContentLoaded', function () {
        Layout.initSidebar(); // init sidebar
        var sessionjson = angular.fromJson(sessionStorage.menus)
        var item = sessionjson.children;
        //根据order进行排序
        let getSortData = (data, sortFn) => {
            data = data.sort(sortFn);
            data.forEach(v => {
                if (v.children && v.children.length !== 0) {
                    v.children = getSortData(v.children, sortFn);
            }
        })
        return data;
    }
    var sts= getSortData(item,(a, b) => {
            return b.order - a.order;//倒序排序
        });
        $scope.menusAndTrans = angular.copy(sts);//拿到登录页那边传来的目录
        //$scope.menusAndTrans = angular.copy(item);//拿到登录页那边传来的目录
    });


    var sessionjson = angular.fromJson(sessionStorage.menus)
    var item = sessionjson.children;
    $scope.menusAndTrans = angular.copy(item);//拿到登录页那边传来的目录
    $scope.search = function(searchParam){
        if(_.isEmpty(searchParam)){ //如果是数组
            $scope.menusAndTrans = angular.copy(item);//复制数据
            $timeout(function(){
                $('.sub-menu').slideUp();//显示
            })
        }else{ //如果不是数组
            $scope.menusAndTrans = search(angular.copy(item),searchParam);//调用搜索方法，传入搜索的值
            $timeout(function(){
                $('.sub-menu').slideDown();//动画隐藏
            })
        }
    };


    function search(all,key){ //包装了一个搜索方法，只要数据结构做成类似的，这个直接拿来用。
        var hitLevel1 = [];//定义空数组
        _.each(all,function(item1){//循环
            var hitLevel2 = [];//定义空数组
            _.each(item1.children,function(item2){//循环
                var hitLevel3 = [];//定义空数组
                _.each(item2.children,function(item3){
                    if((item3.label && item3.label.indexOf(key) != -1) || (item3.label && item3.label.indexOf(key) != -1)){//如果能找到
                        hitLevel3.push(item3);//追加给新数组
                    }
                });
                item2.children = hitLevel3;//让item2.children 就等同于hitLevel3；
                if(item2.children.length > 0 || (item2.label && item2.label.indexOf(key) != -1) || (item2.label && item2.label.indexOf(key) != -1)){
                    hitLevel2.push(item2);
                }
            });
            item1.children = hitLevel2;
            if(item1.children.length > 0 || (item1.label && item1.label.indexOf(key) != -1) || (item1.label && item1.label.indexOf(key) != -1)){
                hitLevel1.push(item1);
            }
        });
        return hitLevel1;
    }
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

MetronicApp.controller('ThemePanelController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Demo.init(); // init theme panel
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

/* Setup Rounting For All Pages */
MetronicApp.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
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
            controller:"biztraceQuery_controller"
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
            controller:"application_controller"
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
        .state("Reorganizemenu",{
            url:"/Reorganizemenu.html",
            templateUrl:"views/operator/Reorganizemenu.html",
            data: {pageTitle: '重组菜单'},
            controller:"reomenu_controller"
        })
        .state("roleConfig",{
            url:"/roleConfig.html",
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
            templateUrl:"views/Emp/Emp.html",
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
}]);

/* Init global settings and run the app */
MetronicApp.run(["$rootScope", "settings", "$state", function($rootScope, settings, $state) {
    $rootScope.$state = $state; // state to be accessed from view
    $rootScope.$settings = settings; // state to be accessed from view
}]);