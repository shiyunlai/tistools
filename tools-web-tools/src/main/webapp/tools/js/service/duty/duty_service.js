/**
 * Created by gaojie on 2017/7/26.
 */
MetronicApp.factory('duty_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.loadmaintree = function (subFrom) {

        var res = $http.post(manurl + "/om/duty/dutytree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loaddutysearchtree = function (subFrom) {

        var res = $http.post(manurl + "/om/duty/searchtree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadallduty = function () {

        var res = $http.post(manurl + "/om/duty/loadallduty").then(function (response) {
            return response.data;
        });
        return res;
    }
    service.addduty = function (subFrom) {

        var res = $http.post(manurl + "/om/duty/addduty",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.initdutyCode = function (subFrom) {

        var res = $http.post(manurl + "/om/duty/initdutyCode",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.querydutybyType = function (subFrom) {

        var res = $http.post(manurl + "/om/duty/querydutybyType",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.querychild = function (subFrom) {

        var res = $http.post(manurl + "/om/duty/querychild",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.queryempbudutyCode = function (subFrom) {

        var res = $http.post(manurl + "/om/duty/queryempbudutyCode",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deletedutyByCode = function (subFrom) {

        var res = $http.post(manurl + "/om/duty/deletedutyByCode",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.updateDuty = function (subFrom) {

        var res = $http.post(manurl + "/om/duty/updateDuty",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    return service;
}]);
