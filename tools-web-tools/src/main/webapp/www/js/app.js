// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
var plat=''
var alertPopup;
angular.module('starter', ['ionic','starter.controllers','starter.services','ngCordova'])
    .filter("splitspace", function () {
        var filterfun = function (val) {
            if (val != undefined && val != '') {
                return val.replace(" ", "&nbsp;");
            }
        }
        return filterfun;
    })
    .run(function($ionicPlatform,$rootScope,$ionicModal,$state,$ionicLoading,$timeout) {
        $rootScope.$state = $state;
        $rootScope.go=function(name){
            $state.go(name)  
        }
        $rootScope.$on('loading:show', function() {
            $ionicLoading.show({template: "<img src='img/home.png'/>"})
        })

        $rootScope.$on('loading:hide', function() {
            $ionicLoading.hide()
        })
        $ionicPlatform.ready(function() {
            // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
            // for form inputs)
            if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
                cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
            }
            if (window.StatusBar) {
                // org.apache.cordova.statusbar required
                StatusBar.styleLightContent();
            }


            //android 返回按钮
            $ionicPlatform.registerBackButtonAction(function (event) {

                var currentUrl = window.location.hash;



                event.preventDefault();
            }, 100);



            $rootScope.waitfun = function () {
                toast('即将发布...');
            }

            $rootScope.formatTime = function (time) {
                //eg:20150909 -- 2015-09-09
                if(!isNaN(time)) {
                    time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8);
                }
                return time;
            }

            $rootScope.forMM = function(val){
                var mm,yue = '';
                if(val!=null&&val!=''){
                    mm = val.substring(0,val.length-1);
                    if(parseInt(mm)>parseInt(3000)){
                        yue = '永续';
                    }else{
                        yue = val;
                    }
                }
                return yue;
            }

            $rootScope.formatUserName = function(val) {
                var name = '';
                if(val != null && val != '') {
                    if(val == '匿名') {
                        name = val;
                    } else {
                        var arr = val.split('');
                        if(arr.length == 2) {
                            name = arr[0] + "*";
                        } else {
                            for (var i = 0; i < arr.length; i++) {
                                if (0 < i && i < (arr.length-1)) {
                                    name += "*";
                                } else {
                                    name += arr[i];
                                }
                            }
                        }
                    }
                }
                return name;
            }
            $rootScope.getdays = function (s1, s2) {
                if (isNaN(s1) && isNaN(s2))
                    s1 = new Date(s1);
                s2 = new Date(s2);
                var days = s2.getTime() - s1.getTime();
                var time = parseInt(days / (1000 * 60 * 60 * 24));
                return time;
            }
            $rootScope.formatTimeYYYY = function (val) {
                if (val != undefined && val != null && val != '') {
                    var yyyyMMdd = val.substring(0, 10);
                    var hhmmss = val.substring(11,19);

                    var timearr1 = yyyyMMdd.split('-');
                    var timearr2 = hhmmss.split(':');

                    val = timearr1[0] + "年" + timearr1[1] + "月" + timearr1[2] + "日" + " " + timearr2[0] + "时" + timearr2[1] + "分" + timearr2[2] + "秒";
                }

                return val;
            }
        });

        $rootScope.back=function(){
            window.history.back();
        }
        $rootScope.TEST=TEST
        //注册恢复事件
        document.addEventListener("resume", function(){

        }, false);

    })

    .config(function($stateProvider, $urlRouterProvider,$httpProvider,$injector) {
        //$httpProvider.defaults.timeout = 9000;
        $httpProvider.interceptors.push(function ($q, $rootScope, $injector) {
            return {
                request: function (config) {
					
                    try {
                        var url = config.url

                        if (url.indexOf('templates/') >= 0) {
                            config.timeout = 2000;
                            return config
                        }
                        if (url.indexOf('/phone/version') >= 0) {

                            config.timeout = 2000;
                            return config
                        }


                        //alert(JSON.stringify(config));
                        config.timeout = 15000;
                        $rootScope.$broadcast('loading:show')
                        config.headers.token = window.localStorage.token
                        config.headers.uid = window.localStorage.uid
                        config.headers.version = versionId
                        config.headers.plat = plat
                        var last = window.localStorage.cancelUpdateVersionTime
                        if(exist(last)){
                            var lasttime =  parseInt(last)
                            var nowtime =new Date().getTime();
                            var diffsec = (nowtime-lasttime)/1000
                            if(diffsec<60*10){
                                config.headers.cancel = '1'
                            }
                        }

                    } catch (e) {
                        //alert(e)
                    }

                    //config.defaults.timeout=5000
                    return config
                },
                response: function (response) {
                    if (response.timeout == 2000) {
                        return response
                    }

                    $rootScope.$broadcast('loading:hide')
                    var s = JSON.stringify((response))
                    if (s.indexOf('loginstatuserrorerror') > 0) {
                        //alert(s)
                        if(exist(window.localStorage.userId && exist(window.localStorage.token))) {
                            toast('登录过期')
                        }
                        $injector.get('$state').transitionTo('login');
                        //$state.go('login')
                        return false;
                    }
                    if (s.indexOf('versionupdate') >= 0) {
                        //response.data.versionupdate
                        //var t= '<div class="more_service_top">发现新版本'+response.data.versionupdate+'</div>' +
                        //    '<div class="more_service_bot">更新新版即获</div>';
                        var t='';
                        if(exist(response.data.desc1)){
                            t=t+'<div class="more_service_bot">'+response.data.desc1+'</div>'
                        }
                        if(exist(response.data.desc2)){
                            t=t+'<div class="more_service_bot">'+response.data.desc2+'</div>'
                        }
                        if(exist(response.data.desc3)){
                            t=t+'<div class="more_service_bot">'+response.data.desc3+'</div>'
                        }

                        var confirmPopup =  $injector.get('$ionicPopup').confirm({
                            title: '<div class="more_service_top">发现新版本'+response.data.versionupdate+'</div>',
                            template: t,
                            cancelText: '稍后再说',
                            cancelType: 'button more_service_btleft',
                            okText: '马上更新',
                            okType: 'button more_service_btright'
                        });
                        //$('.popup').addClass('service_popup');
                        //$('.popup-buttons').addClass('service_call');
                        //$('.popup-head').addClass('service_head');
                        confirmPopup.then(function (res) {
                            if (res) {
                                window.open(response.data.url, '_system', 'location=yes')
                            } else {
                                var force = response.data.force
                                if(force=='1'){
                                    toast('本版本必须更新')
                                    window.location.reload()
                                }else{
                                    var date1=new Date();
                                    window.localStorage.cancelUpdateVersionTime=''+date1.getTime()
                                    window.location.reload()
                                }
                            }
                        });

                        return false;
                    }
                    return response
                },
                responseError: function (rejection) {
                    //alert(JSON.stringify(rejection))
                    toast('请求超时,请检查网络')
                    $rootScope.$broadcast('loading:hide')
                    return $q.reject(rejection);
                }
            }
        });
        initRoute($urlRouterProvider,$stateProvider)
    })

    .config(function($ionicConfigProvider) {
        $ionicConfigProvider.views.maxCache(0);
        $ionicConfigProvider.tabs.position('bottom')
        
    })

