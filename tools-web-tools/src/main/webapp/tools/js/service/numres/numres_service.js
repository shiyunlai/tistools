/**
 * Created by wangbo on 2017/8/4.
 */
MetronicApp.factory('numres_service',['$http', '$q', function ($http,$q) {
    var service={};
    //查询系统运行参数列表
    service.querySeqnoList = function (subFrom) {
        var res = $http.post(manurl + "/SeqnoController/querySeqnoList",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //修改系统运行参数列表
    service.editSeqno = function (subFrom) {
        var res = $http.post(manurl + "/SeqnoController/editSeqno",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //删除系统运行参数列表
    service.deleteSeqno = function (subFrom) {
        var res = $http.post(manurl + "/SeqnoController/deleteSeqno",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };



    return service;
}]);

