/**
 * Created by wangbo on 2017/6/13.
 */

MetronicApp.factory('application_service',['$http', '$q', function ($http,$q) {
    var service={};

    //新增应用服务
    service.createAcApp = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/appAdd",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询应用服务
    service.query = function (subFrom) {

        var res = $http.post(manurl + "/AcAppController/appQuery",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    //删除应用服务
    service.delete = function(item){
        var res = $http.post(manurl + "/AcAppController/appDel",item).then(function (response) {
            return response.data;
        });
        return res;
    }

    //新增功能组服务
    service.createAcFuncGroup = function (item) {
        var res = $http.post(manurl + "/AcAppController/groupAdd",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //新增功能服务
    service.createAcFunc = function (item) {
        var res = $http.post(manurl + "/AcAppController/AcFunc",item).then(function (response) {
            return response.data;
        });
        return res;
    };
    return service;
}]);
