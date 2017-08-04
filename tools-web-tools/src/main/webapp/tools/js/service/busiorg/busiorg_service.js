/**
 * Created by gaojie on 2017/8/2.
 */
MetronicApp.factory('busiorg_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.loadmaintree = function (subFrom) {

        var res = $http.post(manurl + "/om/duty/dutytree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    return service;
}]);