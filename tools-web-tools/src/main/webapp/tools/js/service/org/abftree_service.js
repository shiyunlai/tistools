/**
 * Created by gaojie on 2017/5/9.
 */
MetronicApp.factory('abftree_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.loadmaintree = function (subFrom) {

        var res = $http.post(manurl + "/om/org/tree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadsearchtree = function (subFrom) {
        console.log(subFrom)
        var res = $http.post(manurl + "/om/org/search",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadgwtree = function (subFrom) {
        //todo
        var res = $http.post(manurl + "/om/org/tree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadempbyorg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/loadempbyorg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadempNotinorg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/loadempNotinorg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadxjjg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/loadxjjg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadqxxx = function (subFrom) {
        //todo
        var res = $http.post(manurl + "/om/org/test",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.addorg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/add",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.initcode = function (subFrom) {
        var res = $http.post(manurl + "/om/org/initcode",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.updateOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/updateOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deleteOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/deleteOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.addposit = function (subFrom) {
        var res = $http.post(manurl + "/om/org/addposit",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    //添加机构-员工关系表
    service.addEmpOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/addEmpOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    //删除机构-员工关系表
    service.deleteEmpOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/deleteEmpOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    return service;
}]);