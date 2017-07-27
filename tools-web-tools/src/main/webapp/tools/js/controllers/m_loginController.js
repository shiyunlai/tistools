/**
 * Created by zhangsu on 2016/5/9.
 */
angular.module("LoginApp", [])
    .controller("loginController",function ($rootScope,$scope,login_service) {
        var login ={};
        $scope.login = login;

        $scope.limit = function(item){
            console.log(item);
        }


    })