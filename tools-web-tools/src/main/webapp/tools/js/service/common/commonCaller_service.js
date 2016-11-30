/**
 * Created by HP on 2016/9/19.
 */
MetronicApp.factory('commonCaller_service',['$http', '$q', function ($http,$q) {

    var service={};

    service.call = function ( requestInfo ) {

        //TODO 实现通用调服务
        var urlStr = assemblyUrl( requestInfo ) ;

        // POST 请求
        if( requestInfo.method == "POST" ){
            var res = $http.post(urlStr,{data:requestInfo.postJsonData}).then(function (response) {
                return response.data;
            });
            return res;
        }

        // GET 请求
        if ( requestInfo.method == "GET" ){

            //拼接上请求参数
            var allUrl = urlStr + assemblyGETParams(requestInfo)  ;

            var res = $http.get(allUrl).then(function (response) {
                return response.data;
            });
            return res;
        }

        // PUT 请求
        //if (requestInfo.method == "PUT" ){
        //    var res = $http.get(manurl + "/BizLogHandleController/upload",{uploadFile:file}).then(function (response) {
        //        return response.data;
        //    });
        //    return res;
        //}

    }

    service.callTest = function ( requestInfo ) {

        var requestUrl = assemblyUrl( requestInfo ) ;

        var res = $http.post( requestUrl , requestInfo.postJsonData ).then(function (response) {
            return response.data;
        });

        return res
    }

    return service;
}]);

assemblyUrl = function( requestInfo ){
    return requestInfo.protocol +":"+"//"+requestInfo.host +":"+requestInfo.port + requestInfo.mapping ;
}
