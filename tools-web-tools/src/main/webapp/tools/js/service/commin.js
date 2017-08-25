/**
 * Created by hasee on 2017/7/11.
 */
MetronicApp.factory('common_service',['$http', '$q', function ($http,$q) {
    var service={};
    //新增行为类型
    service.post = function (api, subFrom) {
        var res = $http.post(manurl+'/' + api.ctrl + "/" + api.func,subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };



    //公用的service，以后就不需要每个页面都写service了，直接在每个页面引用common_service,然后传入main.js 我们配置的API
    //service.post(API.app_service,subFrom)  直接这样调用即可

/*
    service.get = function (api) {
        var res = $http.get(manurl+'/' + api.ctrl + "/" + api.func).then(function (response) {
            return response.data;
        });
        return res;
    };
*/


    return service;
}]);
