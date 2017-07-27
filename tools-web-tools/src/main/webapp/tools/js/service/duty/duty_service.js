/**
 * Created by gaojie on 2017/7/26.
 */
MetronicApp.factory('abftree_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.loadmaintree = function (subFrom) {

        var res = $http.post(manurl + "/om/duty/dutytree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    return service;
}]);
