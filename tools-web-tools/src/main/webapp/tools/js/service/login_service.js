/**
 * Created by zhangsu on 2016/5/17.
 */
MetronicApp.factory('login_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.login = function(){
        var res;
        res = $http.post(manurl+"/commons/login").then(function(response){
            return response.data;
        });
        return res;
    }


    return service;
}]);