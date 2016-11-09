angular.module('starter.services', [])

    .factory('app_service', ['$http', '$q', function ($http, $q) {
        var service = {};
        service.login = function (item) {       
            return $http.post(baseurl + '/phone/user/login', item).success(function (reponse) {
                    return reponse;
                });                     
        }
        return service;
    }]);
