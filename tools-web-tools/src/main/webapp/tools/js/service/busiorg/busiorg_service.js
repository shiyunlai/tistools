/**
 * Created by gaojie on 2017/8/2.
 */
MetronicApp.factory('busiorg_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.loadmaintree = function (subFrom) {

        var res = $http.post(manurl + "/om/busiorg/busitree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadsearchtree = function (subFrom) {

        var res = $http.post(manurl + "/om/busiorg/searchtree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.loadywtb = function () {

        var res = $http.post(manurl + "/om/busiorg/busidomain").then(function (response) {
            return response.data;
        });
        return res;
    }

    service.loadloworg = function (subFrom) {
        var res = $http.post(manurl + "/om/busiorg/busidomain",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.initcode = function (subFrom) {
        var res = $http.post(manurl + "/om/busiorg/initCode",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.addbusiorg = function (subFrom) {

        var res = $http.post(manurl + "/om/busiorg/addbusiorg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.deletebusiorg = function (subFrom) {

        var res = $http.post(manurl + "/om/busiorg/deletebusiorg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.updatebusiorg = function (subFrom) {

        var res = $http.post(manurl + "/om/busiorg/updatebusiorg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.loadbusiorgbyType = function (subFrom) {

        var res = $http.post(manurl + "/om/busiorg/loadbusiorgbyType",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }


    //根据guid查询业务机构名称
    service.queryBusiorgByGuid = function (subFrom) {
        var res = $http.post(manurl + "/om/busiorg/queryBusiorgByGuid",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    return service;
}]);