/**
 * Created by HP on 2016/9/13.
 */
MetronicApp.factory('biztraceSum_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.querySumInfo = function (transDateStart,transDateEnd) {
        if(isNull(transDateStart)){
            transDateStart = "";
        }
        if(isNull(transDateEnd)){
            transDateEnd = "";
        }

        var res = $http.post(manurl + "/BizLogSumQueryController/dayLogSumInfoReport",{transDate_start:transDateStart,transDate_end:transDateEnd}).then(function (response) {
            return response.data;
        });
        return res;
    }

    return service;
}]);