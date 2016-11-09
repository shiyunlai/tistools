/**
 * Created by cdq on 2016/9/2.
 */
MetronicApp.factory('biztrace_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.queryByCondition = function (trans_serial,trans_date) {
        //{trans_serial:trans_serial,trans_date:trans_date}
        if(isNull(trans_serial)){
            trans_serial = "";
        }
        if(isNull(trans_date)){
            trans_date = "";
        }

        var res = $http.post(manurl + "/BizLogQueryController/bodyInfoReport",{trans_serial:trans_serial,trans_date:trans_date}).then(function (response) {
                return response.data;
        });
        return res;
    }

    service.queryByRank = function (pre_index) {
        if(isNull(pre_index)){
            pre_index = "";
        }

        var res = $http.post(manurl + "/BizLogQueryController/bodyInfoByRankReport",pre_index).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.queryDetailInfo = function (serialNo) {
        if(isNull(serialNo)){
            serialNo = "";
        }

        var res = $http.post(manurl + "/BizLogQueryController/detailInfoReport",serialNo).then(function (response) {
            return response.data;
        });
        return res;
    }

    //service.resloveBizLogFile = function (path) {
    //    if(isNull(path)){
    //        path = "";
    //    }
    //    //alert(path)
    //    var res = $http.post(manurl + "/BizLogQueryController/reslover",path).then(function (response) {
    //        return response.data;
    //    });
    //    return res;
    //}

    //service.analyzeBizLog = function (analyzedLogDateArry) {
    //    //alert(analyzedLogDateArry)
    //    var res = $http.post(manurl + "/BizLogQueryController/analyzer",analyzedLogDateArry).then(function (response) {
    //        return response.data;
    //    });
    //    return res;
    //}

    //service.getUnanalyzedLogDate = function () {
    //    var res = $http.post(manurl + "/BizLogQueryController/getUnanalyedDate","").then(function (response) {
    //        return response.data;
    //    });
    //    return res
    //}
    return service;
}]);