/**
 * Created by HP on 2016/9/19.
 */
MetronicApp.factory('biztraceHandle_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.loadfuwu = function (){
        var res = $http.get("http://localhost:8089/tis/log/agents","").then(function (response){
            return response.data;
        });
        return res;
    }

    service.loadfile = function (service){
        var res = $http.get("http://localhost:8089/tis/log/list/"+service.host+":"+service.port,"").then(function (response){
           return response.data;
        });
        return res;
    }

    service.analyzeLog = function (jsondata){
        var res = $http.post("http://localhost:8089/tis/log//analyse/"+jsondata.port,jsondata).then(function (response){
           return response.data;
        });
        return res;
    }

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