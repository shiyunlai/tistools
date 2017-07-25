/**
 * Created by wangbo on 2017/7/23.
 */
MetronicApp.factory('menu_service',['$http', '$q', function ($http,$q) {
    var service={};
    //查询所有应用
    service.queryAllAcApp = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/queryAllAcApp",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询应用下的跟菜单
    service.queryRootMenu = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/queryRootMenu",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询应用下菜单
    service.queryRootMenuTree = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/queryRootMenuTree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询菜单下子菜单
    service.queryChildMenu = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/queryChildMenu",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //新增根菜单
    service.createRootMenu = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/createRootMenu",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //新增子菜单
    service.createChildMenu = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/createChildMenu",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //删除菜单
    service.deleteMenu = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/deleteMenu",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //修改菜单方法
    service.editMenu = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/editMenu",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    return service;
}]);