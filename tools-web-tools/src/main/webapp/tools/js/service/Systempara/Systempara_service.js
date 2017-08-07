/**
 * Created by hasee on 2017/8/2.
 */

MetronicApp.factory('systempara_service',['$http', '$q', function ($http,$q) {
    var service={};
    //查询系统运行参数列表
    service.queryRunConfigList = function (subFrom) {
        var res = $http.post(manurl + "/RunConfigController/queryRunConfigList",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //新增系统参数
    service.createRunConfig = function (subFrom) {
        var res = $http.post(manurl + "/RunConfigController/createRunConfig",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //修改系统参数
    service.editRunConfig = function (subFrom) {
        var res = $http.post(manurl + "/RunConfigController/editRunConfig",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //删除系统参数
    service.deleteRunConfig = function (subFrom) {
        var res = $http.post(manurl + "/RunConfigController/deleteRunConfig",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };



    return service;
}]);