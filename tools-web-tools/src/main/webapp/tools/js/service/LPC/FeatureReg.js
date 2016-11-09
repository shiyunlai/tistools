/**
 * Created by HP on 2016/10/18.
 */
MetronicApp.factory('FeatureReg_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.query = function (queryCondition) {
        var res = $http.post(manurl + "/FeatureRegController/query",queryCondition).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.add = function (editData) {
        var res = $http.post(manurl + "/FeatureRegController/add",editData).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.delete = function (branchIdArry) {
        var res = $http.post(manurl + "/FeatureRegController/delete",branchIdArry).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.modify = function (editData) {
        var res = $http.post(manurl + "/FeatureRegController/update",editData).then(function (response) {
            return response.data;
        });
        return res;
    }
    return service;
}]);
