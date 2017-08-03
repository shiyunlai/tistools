/**
 * Created by hasee on 2017/7/11.
 */
MetronicApp.factory('behavior_service',['$http', '$q', function ($http,$q) {
    var service={};
    //新增行为类型
    service.functypeAdd = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/functypeAdd",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询功能行为类型
    service.functypequery = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/functypequery",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //修改功能行为类型
    service.functypeEdit = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/functypeEdit",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //删除功能行为类型
    service.functypeDel = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/functypeDel",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //新增功能行为
    service.funactAdd = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/funactAdd",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    //修改功能行为
    service.funactEdit = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/funactEdit",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    //删除功能行为
    service.funactDel = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/funactDel",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }


    //根据类型的GUID查询行为
    service.queryBhvDefByBhvType = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/queryBhvDefByBhvType",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    return service;
}]);
