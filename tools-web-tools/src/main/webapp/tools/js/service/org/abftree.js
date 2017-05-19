/**
 * Created by gaojie on 2017/5/9.
 */
MetronicApp.factory('abftree_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.test = function (subFrom) {

        var res = $http.post(manurl + "/om/org/tree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadchild = function (pobj) {
        var res = $http.post(manurl + "/om/org/child",pobj).then(function (response) {
            return response.data;
        });
        return res;
    }

    return service;
}]);