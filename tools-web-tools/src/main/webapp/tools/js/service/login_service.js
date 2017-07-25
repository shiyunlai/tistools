/**
 * Created by zhangsu on 2016/5/17.
 */
angular.module("LoginApp", [])
.factory('login_service',['$http', '$q', function ($http,$q) {
    var service={};
    service.login = function(){
        var res;
        res = $http.post(manurl+"/AcAuthenticationController/checkUserStatus").then(function(response){
            return response.data;
        });
        return res;
    }


    return service;
}]);