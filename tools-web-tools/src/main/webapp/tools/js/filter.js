/**
 * 高亮字符
 * @author:jiangwq
 * @time:2017-07-13
 * @params: val:整体字符串 type:string
 *           highlightVal:高亮字符串 type:string
 *           color: 高亮颜色 type:string
 */
MetronicApp.filter('highlightTrust2Html', ['$sce', function ($sce) {
    return function (val, highlightVal, color) {
        if (_.isEmpty(highlightVal)) {
            return val;
        }
        val = val.replace(highlightVal, "<span style='color : " + color + "'>" + highlightVal + "</span>");
        return $sce.trustAsHtml(val);
    };
}])
    .filter('translateConstants', ['$http', '$rootScope', function ($http, $rootScope) {
        return function (val, name) {
            var subFrom = {};
            subFrom.dictKey = name;
            // console.info($rootScope.constant)
            if (isNull($rootScope.constant[name + "-" + val])) {
                $http.post(manurl +  "/DictController/queryDictItemListByDictKey", subFrom).then(function (data) {
                    var retval = "";
                    if (data.data.status == "success") {
                        for (var i = 0; i < data.data.retMessage.length; i++) {
                            if (val == data.data.retMessage[i].sendValue) {
                                // console.log(data.data.retMessage)
                                retval = data.data.retMessage[i].itemName;
                                // console.log(retval)
                                $rootScope.constant[name + "-" + val] = retval;
                            }
                        }
                    }

                })
            }
            return '';
        };
    }])
    .filter('translatePosition', ['$http', '$rootScope', function ($http, $rootScope) {
        return function (val, name) {
            // console.info($rootScope.constant)
            if (isNull($rootScope.constant[val])) {
                $http.post(manurl +  "/om/org/queryAllposition").then(function (data) {
                    var retval = "";
                    if (data.data.status == "success") {
                        for (var i = 0; i < data.data.retMessage.length; i++) {
                            if (val == data.data.retMessage[i].guid) {
                                // console.log(data.data.retMessage)
                                retval = data.data.retMessage[i].positionName;
                                // console.log(retval)
                                $rootScope.constant[val] = retval;
                            }
                        }
                    }

                })
            }
            return '';
        };
    }])
    .filter('translateEmp', ['$http', '$rootScope', function ($http, $rootScope) {
        return function (val, name) {
            var subFrom = {};
            subFrom.dictKey = name;
            console.info(name + "-" + val)
            // console.info($rootScope.constant)
            if (isNull($rootScope.constant[val])) {
                $http.post(manurl +  "/om/emp/queryemployee").then(function (data) {
                    var retval = "";
                    // console.log(data)
                    if (data.data.status == "success") {
                        for (var i = 0; i < data.data.retMessage.length; i++) {
                            if (val == data.data.retMessage[i].guid) {
                                // console.log(data.data.retMessage)
                                retval = data.data.retMessage[i].empName;
                                // console.log(retval)
                                $rootScope.constant[val] = retval;
                            }
                        }
                    }

                })
            }
            return '';
        };
    }])
    .filter('translateOrg', ['$http', '$rootScope', function ($http, $rootScope) {
        return function (val, name) {
            var subFrom = {};
            subFrom.dictKey = name;
            console.info(name + "-" + val)
            // console.info($rootScope.constant)
            if (isNull($rootScope.constant[val])) {
                $http.post(manurl +  "/om/org/queryAllorg").then(function (data) {
                    var retval = "";
                    // console.log(data)
                    if (data.data.status == "success") {
                        for (var i = 0; i < data.data.retMessage.length; i++) {
                            if (val == data.data.retMessage[i].guid) {
                                // console.log(data.data.retMessage)
                                retval = data.data.retMessage[i].orgName;
                                // console.log(retval)
                                $rootScope.constant[val] = retval;
                            }
                        }
                    }

                })
            }
            return '';
        };
    }])
    .filter('translateDuty', ['$http', '$rootScope', function ($http, $rootScope) {
        return function (val, name) {
            var subFrom = {};
            subFrom.dictKey = name;
            // console.info($rootScope.constant)
            if (isNull($rootScope.constant[val])) {
                $http.post(manurl +  "/om/duty/loadallduty").then(function (data) {
                    var retval = "";
                    if (data.data.status == "success") {
                        for (var i = 0; i < data.data.retMessage.length; i++) {
                            if (val == data.data.retMessage[i].guid) {
                                // console.log(data.data.retMessage)
                                retval = data.data.retMessage[i].dutyName;
                                // console.log(retval)
                                $rootScope.constant[val] = retval;
                            }
                        }
                    }

                })
            }
            return '';
        };
    }])
    .filter('translateApp', ['$http', '$rootScope', function ($http, $rootScope) {
        return function (val, name) {
            var subFrom = {};
            subFrom.dictKey = name;
            // console.info($rootScope.constant)
            if (isNull($rootScope.constant[val])) {
                $http.post(manurl +  "/AcMenuController  /queryAllAcApp",{}).then(function (data) {
                    var retval = "";
                    if (data.data.status == "success") {
                        for (var i = 0; i < data.data.retMessage.length; i++) {
                            if (val == data.data.retMessage[i].guid) {
                                // console.log(data.data.retMessage)
                                retval = data.data.retMessage[i].appName;
                                // console.log(retval)
                                $rootScope.constant[val] = retval;
                            }
                        }
                    }

                })
            }
            return '';
        };
    }])
    .filter('translateDict', ['$http', '$rootScope', function ($http, $rootScope) {
        //翻译所有业务字典
        return function (val, name) {
            var subFrom = {};
            subFrom.dictKey = name;
            if (isNull($rootScope.constant[val])) {
                $http.post(manurl +  "/DictController/querySysDictList",{}).then(function (data) {
                    var retval = "";
                    if (data.data.status == "success") {
                        for (var i = 0; i < data.data.retMessage.length; i++) {
                            if (val == data.data.retMessage[i].guid) {
                                // console.log(data.data.retMessage)
                                retval = data.data.retMessage[i].dictName;
                                 console.log(retval)
                                $rootScope.constant[val] = retval;
                                return;
                            }else{
                                $rootScope.constant[val] = '手动输入';
                            }
                        }
                    }

                })
            }
            return '';
        };
    }])
    .filter('translateDictitem', ['$http', '$rootScope', function ($http, $rootScope) {
        //翻译所有业务字典项
        return function (val, name) {
            var subFrom = {};
            subFrom.dictKey = name;
            console.info(name + "-" + val)
            // console.info($rootScope.constant)
            if (isNull($rootScope.constant[val])) {
                $http.post(manurl +  "/DictController/queryAllDictItem",{}).then(function (data) {
                    var retval = "";
                    if (data.data.status == "success") {
                        for (var i = 0; i < data.data.retMessage.length; i++) {
                            if (val == data.data.retMessage[i].guid) {
                                retval = data.data.retMessage[i].itemName;
                                console.log(retval)
                                $rootScope.constant[val] = retval;
                                return;
                            }else{
                                $rootScope.constant[val] = val;
                            }
                        }
                    }

                })
            }
            return '';
        };
    }])
    .filter('translateDictKey', ['$http', '$rootScope', function ($http, $rootScope) {
        //根据key翻译所有业务字典
        return function (val, name) {
            var subFrom = {};
            subFrom.dictKey = name;
            if (isNull($rootScope.constant[val])) {
                $http.post(manurl +  "/DictController/querySysDictList",{}).then(function (data) {
                    var retval = "";
                    if (data.data.status == "success") {
                        for (var i = 0; i < data.data.retMessage.length; i++) {
                            if (val == data.data.retMessage[i].dictKey) {
                                // console.log(data.data.retMessage)
                                retval = data.data.retMessage[i].dictName;
                                $rootScope.constant[val] = retval;
                                return;
                            }
                        }
                    }

                })
            }
            return '';
        };
    }])
    .filter('translateDictitemvalue', ['$http', '$rootScope', function ($http, $rootScope) {
        return function (val, name) {
            var subFrom = {};
            subFrom.dictKey = name;
            console.info(name + "-" + val)
            // console.info($rootScope.constant)
            if (isNull($rootScope.constant[val])) {
                $http.post(manurl + "/DictController/queryAllDictItem",{}).then(function (data) {
                    var retval = "";
                    if (data.data.status == "success") {
                        for (var i = 0; i < data.data.retMessage.length; i++) {
                            if (val == data.data.retMessage[i].sendValue) {
                                retval = data.data.retMessage[i].itemName;
                                console.log(retval)
                                $rootScope.constant[val] = retval;
                                return;
                            }else{
                                $rootScope.constant[val] = val;
                            }
                        }
                    }

                })
            }
            return '';
        };
    }])
;