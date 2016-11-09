/**
 * Created by HP on 2016/9/13.
 */

MetronicApp.factory('redisClean_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.getRedisSpaceUsage = function () {
        var res = $http.post(manurl + "/RedisCleanController/getRedisSpaceUsage","").then(function (response) {
            return response.data;
        });
        return res;
    }

    service.getDateList = function () {
        var res = $http.post(manurl + "/RedisCleanController/getResolveredLogDate","").then(function (response) {
            return response.data;
        });
        return res;
    }

    service.doClean = function (selectedLogDateArry) {
        var res = $http.post(manurl + "/RedisCleanController/cleanRedisSpace",selectedLogDateArry).then(function (response) {
            return response.data;
        });
        return res;
    }

    return service;
}]);