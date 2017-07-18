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
    service.loademp = function (subFrom) {
        //TODO
    }
    service.loadxjjg = function (subFrom) {
        //TODO
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
    service.addposit = function (subFrom) {
        var res = $http.post(manurl + "/om/org/addposit",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    return service;
}]);