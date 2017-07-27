/**
 * Created by gaojie on 2017/5/9.
 */
MetronicApp.factory('Emp_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.loadempgrid = function () {
        var res = $http.post(manurl + "/om/emp/queryemployee",null).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.addemp = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/addemployee",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deletemp = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/deletemp",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    return service;
}]);