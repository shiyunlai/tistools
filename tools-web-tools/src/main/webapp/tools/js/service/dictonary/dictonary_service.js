/**
 * Created by wangbo on 2017/7/27.
 */
MetronicApp.factory('dictonary_service',['$http', '$q', function ($http,$q) {
    var service={};
    //新增业务字典
    service.createSysDict = function (subFrom) {
        var res = $http.post(manurl + "/DictController/createSysDict",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //删除业务字典
    service.deleteSysDict = function (subFrom) {
        var res = $http.post(manurl + "/DictController/deleteSysDict",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //修改业务字典
    service.editSysDict = function (subFrom) {
        var res = $http.post(manurl + "/DictController/editSysDict",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询单个业务字典
    service.querySysDict = function (subFrom) {
        var res = $http.post(manurl + "/DictController/querySysDict",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //新增业务字典项
    service.createSysDictItem = function (subFrom) {
        var res = $http.post(manurl + "/DictController/createSysDictItem",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //删除业务字典项
    service.deleteSysDictItem = function (subFrom) {
        var res = $http.post(manurl + "/DictController/deleteSysDictItem",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //修改业务字典项
    service.editSysDictItem = function (subFrom) {
        var res = $http.post(manurl + "/DictController/editSysDictItem",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询单个业务字典项
    service.querySysDictItem = function (subFrom) {
        var res = $http.post(manurl + "/DictController/querySysDictItem",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询所有业务字典
    service.querySysDictList = function (subFrom) {
        var res = $http.post(manurl + "/DictController/querySysDictList",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询业务字典项列表
    service.querySysDictItemList = function (subFrom) {
        var res = $http.post(manurl + "/DictController/querySysDictItemList",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //根据key查询业务字典项列表
    service.queryDictItemListByDictKey = function (subFrom) {
        var res = $http.post(manurl + "/DictController/queryDictItemListByDictKey",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询所有业务字典
    service.queryAllDictItem = function (subFrom) {
        var res = $http.post(manurl + "/DictController/queryAllDictItem",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    return service;
}]);
