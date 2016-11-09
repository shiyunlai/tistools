/**
 * Created by HP on 2016/9/19.
 */
MetronicApp.factory('biztraceHandle_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.upload = function (file) {

        var res = $http.post(manurl + "/BizLogHandleController/upload",{uploadFile:file}).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.resloveBizLogFile = function (path) {
        //if(isNull(path)){
        //    path = "";
        //}
        //alert(path)
        var res = $http.post(manurl + "/BizLogHandleController/reslover",path).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.getResolverProcess = function () {
        var res = $http.post(manurl + "/BizLogHandleController/getResloverProcess","").then(function (response) {
            return response.data;
        });
        return res
    }

    service.analyzeBizLog = function (analyzedLogDateArry) {
        //alert(analyzedLogDateArry)
        var res = $http.post(manurl + "/BizLogHandleController/analyzer",analyzedLogDateArry).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.getUnanalyzedLogDate = function () {
        var res = $http.post(manurl + "/BizLogHandleController/getUnanalyedDate","").then(function (response) {
            return response.data;
        });
        return res
    }
    return service;
}]);