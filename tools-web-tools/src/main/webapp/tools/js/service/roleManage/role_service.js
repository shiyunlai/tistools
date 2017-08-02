/**
 * Created by wangbo on 2017/7/31.
 */

MetronicApp.factory('role_service',['$http', '$q', function ($http,$q) {
    var service={};
    //查询角色列表
    service.queryRoleList = function (subFrom) {
        var res = $http.post(manurl + "/AcRoleController/queryRoleList",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //新增角色
    service.createRole = function (subFrom) {
        var res = $http.post(manurl + "/AcRoleController/createRole",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //修改角色
    service.editRole = function (subFrom) {
        var res = $http.post(manurl + "/AcRoleController/editRole",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //删除角色
    service.deleteRole = function (subFrom) {
        var res = $http.post(manurl + "/AcRoleController/deleteRole",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询对应的应用功能
    service.appQuery = function (subFrom) {
        var res = $http.post(manurl + "/AcRoleController/appQuery",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    //角色配置功能权限
    service.configRoleFunc = function (subFrom) {
        var res = $http.post(manurl + "/AcRoleController/configRoleFunc",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //角色添加组织权限
    service.addPartyRole = function (subFrom) {
        var res = $http.post(manurl + "/AcRoleController/addPartyRole",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    // 查询角色的功能权限
    service.queryRoleFunc = function (subFrom) {
        var res = $http.post(manurl + "/AcRoleController/queryRoleFunc",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询角色下某组织类型的权限信息
    service.queryRoleInParty = function (subFrom) {
        var res = $http.post(manurl + "/AcRoleController/queryRoleInParty",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //移除角色下的组织对象
    service.removePartyRole = function (subFrom) {
        var res = $http.post(manurl + "/AcRoleController/removePartyRole",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //角色添加操作员
    service.addOperatorRole = function (subFrom) {
        var res = $http.post(manurl + "/AcRoleController/addOperatorRole",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询角色下的操作员集合
    service.queryOperatorRole = function (subFrom) {
        var res = $http.post(manurl + "/AcRoleController/queryOperatorRole",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //移除角色下的操作员
    service.removeOperatorRole = function (subFrom) {
        var res = $http.post(manurl + "/AcRoleController/removeOperatorRole",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    return service;
}]);