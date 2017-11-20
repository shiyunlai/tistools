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
            console.log(val)
            subFrom.dictKey = name;
            $rootScope.vals = val;
            if (isNull($rootScope.constant[name + "-" + val])) {
                $http.post(manurl +  "/DictController/queryDictItemListByDictKey", subFrom).then(function (data) {
                    var retval = "";
                    if (data.data.status == "success") {
                        for (var i = 0; i < data.data.retMessage.length; i++) {
                            if (val == data.data.retMessage[i].sendValue) {
                                //retval = data.data.retMessage[i].sendValue + '（' + data.data.retMessage[i].itemName + '）';
                                retval = data.data.retMessage[i].itemName ;
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
            if (isNull($rootScope.constant[val])) {
                $http.post(manurl +  "/om/emp/queryemployee").then(function (data) {
                    var retval = "";
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
                                $rootScope.constant[val] = retval;//把翻译的内容放在rootScope中，下次在用的时候，判断如果有就直接用，就不需要每次都调用后台了
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
    .filter('toCondStr', function () {
        //预览删除记录检查引用关系
        return function (val) {
            if(val == null || val.length < 1)
                return '';//if 不加上{}　代表单次匹配，加上｛｝　代表多次匹配
            var str = '';
            for (var i = 0; i < val.length; i++) {
                if(!isNull(val[i].tableName) && !isNull(val[i].columeName) && !isNull(val[i].isDel)) {
                    str += val[i].tableName + '.' + val[i].columeName + '/[' + val[i].isDel + ']';
                    if (i !== val.length - 1)
                        str += ';';//单次匹配，如果没有，那就默认为空
                }
            }
            return str;
        };
    })
    .filter('toRangeStr', function () {
        //预览删除记录检查引用关系
        return function (val) {
            if(val == null || val.length < 1)
                return '';//if 不加上{}　代表单次匹配，加上｛｝　代表多次匹配

            //进行数组循环
            var str = '';
            for(var i=0,len=val.length;i<len;i++){
                var str2 = '';
                for(var j=0,len2=val[i].length;j<len2;j++){
                    if(val[i][j].type){
                        var sql = '';
                        if(val[i][j].rlea){
                            sql = val[i][j].rlea + '('+  val[i][j].type +' '+ val[i][j].guanxi +' '+ val[i][j].ce+')';
                        }else{
                            sql = '('+val[i][j].type +' '+ val[i][j].guanxi +' '+ val[i][j].ce+')';
                        }
                        str2 += sql;
                    }
                }
                if(val[i][val[i].length-1].orAdd){
                    str += '('+ str2 +')' + val[i][val[i].length-1].orAdd;
                }else{
                    str += '('+ str2 +')';
                }

            }
            return str;
        };
    })
    
    //翻译岗位内容
    .filter('SelectPosition', ['$http', '$rootScope', function ($http, $rootScope) {
        return function (val) {
            if (isNull($rootScope.constant[val])) {
                var retval = [];//重新绘制的数组
                $http.post(manurl +  "/om/org/queryAllposition").then(function (data) {
                    var send = {}
                    if (data.data.status == "success") {
                        for (var i = 0; i < data.data.retMessage.length; i++) {
                            if (val == data.data.retMessage[i].guid) {
                                send.value = data.data.retMessage[i].guid;
                                send.label = data.data.retMessage[i].positionName;
                                retval.push(send)
                                $rootScope.constant[val + 'post'] = retval;
                            }
                        }
                    }
                })
            }
            return retval;
        };
    }])
    .filter('Arraysort', ['$http', '$rootScope', function ($http, $rootScope) {
        //表格排序管道
        return function (val) {
            if(!isNull(val)) {
                var index = 0;
                for (var i = 0; i < val[0].guid.length; i++) {
                    if (isNaN(val[0].guid[i])) {
                        index++
                    } else {
                        break;
                    }
                }
                var datas = val.sort(function (a, b) {
                    return toNum(b) - toNum(a);
                });

                function toNum(str) {
                    return parseInt(str.guid.slice(index))
                }

                for (var j = 0; j < datas.length; j++) {
                }
                return datas;
            }else{
                return [];
            }
        };
    }])
    //翻译表格业务字典测试,岗位也是一样，查询所有的然后拼接即可
    .filter('translateSearch', ['$http', '$rootScope', function ($http, $rootScope) {
        return function (val) {
            var subFrom = {};
            subFrom.dictKey = val;
            console.log(subFrom)
            $rootScope.vals = val;
            var retval = [];
            if (isNull($rootScope.constant[name + "-" + val])) {
                $http.post(manurl +  "/DictController/queryDictItemListByDictKey", subFrom).then(function (data) {
                    if (data.data.status == "success") {
                        for (var i = 0; i < data.data.retMessage.length; i++) {
                            var strs = {};
                            strs.value = data.data.retMessage[i].sendValue;
                            strs.label = data.data.retMessage[i].itemName;
                            retval.push(strs);
                            $rootScope.constant[val+'forEach'] = retval;
                        }
                    }
                })
            }
            return retval;
        };
}])
;