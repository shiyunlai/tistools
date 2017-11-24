/**
 * Created by hasee on 2017/7/11.
 */
MetronicApp.factory('common_service',['$rootScope','$http', '$q',function ($rootScope,$http,$q) {
    var service={};
    //公共的服务方法，直接调用即可,第三个参数
    service.post = function (api, subFrom) {
        //在main中的，路由监视器获得路由传过来的功能guid和应用guid,然后绑定给rootscope.test,在这里拿到，在请求服务的时候，给请求头传入每个功能的功能guid，也就是第三个参数
        if(!isNull($rootScope.Appfunc)){//判断是否能拿到传过来的请求头内容
            var res = $http.post(manurl+'/' + api.ctrl + "/" + api.func,subFrom,{
                headers : {'Authorization' : $rootScope.Appfunc}
            }).then(function (response) {
                return response.data;
            });
        }else{
            //如果不存在，说明在请求的时候不需要传入header，那么直接调用服务
            var res = $http.post(manurl+'/' + api.ctrl + "/" + api.func,subFrom).then(function (response) {
                return response.data;
            });
        }
        return res;
    };


/*
    service.get = function (api) {
        var res = $http.get(manurl+'/' + api.ctrl + "/" + api.func).then(function (response) {
            return response.data;
        });
        return res;
    };
*/
    return service;
}]);
