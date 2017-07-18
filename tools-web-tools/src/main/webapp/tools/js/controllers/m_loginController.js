/**
 * Created by zhangsu on 2016/5/9.
 */
angular.module("LoginApp", [])
    .controller("loginController",function ($rootScope,$scope,login_service) {
        var user = {};
        $scope.user = user;
        $scope.login = function () {
            if(!isNull(user.loginname)&&!isNull(user.passwd)){
                login_service.toLogin(user).then(function (data) {
                    if(data.retCode == '1'){
                        sessionStorage.user = JSON.stringify(data.user);
                        window.location = "/governor/m/index.html";
                    }else if(data.retCode == '2') {
                        toastr['error'](data.retMessage, "登录失败！");
                    } else {
                        toastr['error']( "登录异常！");
                    }
                });
            }
        }
    })
    .factory('login_service',['$http', '$q', function ($http,$q) {
        var service={};

        service.toLogin = function(item) {
            return $http.post(manurl + "/user/login1",{item:item}).then(function (response) {
                return response.data;
            });
        }

        return service;
    }]);