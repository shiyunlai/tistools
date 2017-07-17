/**
 * Created by gaojie on 2017/5/9.
 */
MetronicApp.factory('Workgroup_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.loadmaintree = function (subFrom) {

        var res = $http.post(manurl + "/om/workgroup/workgrouptree",subFrom).then(function (response) {
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

    service.loademp = function (subFrom) {
        //TODO
    }

    service.loadxjgroup = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/queryChild",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadallgroup = function () {
        //todo
        var res = $http.post(manurl + "/om/workgroup/queryall",null).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.addgroup = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/add",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deletegroup = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/delete",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    return service;
}]);