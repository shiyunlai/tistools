/**
 * Created by wangbo on 2017/7/28.
 */
MetronicApp.factory('operator_service',['$http', '$q', function ($http,$q) {
    var service={};
    //查询操作员列表
    service.queryAllOperator = function (subFrom) {
        var res = $http.post(manurl + "/AcOperatorController/queryAllOperator",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //新增操作员
    service.createOperator = function (subFrom) {
        var res = $http.post(manurl + "/AcOperatorController/createOperator",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //修改操作员
    service.editOperator = function (subFrom) {
        var res = $http.post(manurl + "/AcOperatorController/editOperator",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //设置操作员默认身份
    service.setDefaultOperatorIdentity = function (subFrom) {
        var res = $http.post(manurl + "/AcOperatorController/setDefaultOperatorIdentity",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询操作员身份列表
    service.queryAllOperatorIdentity = function (subFrom) {
        var res = $http.post(manurl + "/AcOperatorController/queryAllOperatorIdentity",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //新增操作员身份
    service.createOperatorIdentity = function (subFrom) {
        var res = $http.post(manurl + "/AcOperatorController/createOperatorIdentity",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //删除操作员身份
    service.deleteOperatorIdentity = function (subFrom) {
        var res = $http.post(manurl + "/AcOperatorController/deleteOperatorIdentity",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //修改操作员身份
    service.editOperatorIdentity = function (subFrom) {
        var res = $http.post(manurl + "/AcOperatorController/editOperatorIdentity",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询操作员身份权限集列表
    service.queryAllOperatorIdentityRes = function (subFrom) {
        var res = $http.post(manurl + "/AcOperatorController/queryAllOperatorIdentityRes",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //新增操作员身份权限
    service.createOperatorIdentityres = function (subFrom) {
        var res = $http.post(manurl + "/AcOperatorController/createOperatorIdentityres",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //删除操作员身份
    service.deleteOperatorIdentityres = function (subFrom) {
        var res = $http.post(manurl + "/AcOperatorController/deleteOperatorIdentityres",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //修改操作员身份权限
    service.editOperatorIdentityres = function (subFrom) {
        var res = $http.post(manurl + "/AcOperatorController/editOperatorIdentityres",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    return service;
}]);
