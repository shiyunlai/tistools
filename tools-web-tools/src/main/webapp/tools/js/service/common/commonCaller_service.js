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
        if(requestInfo.method == "POST") {
             var res = $http.post(requestUrl, requestInfo.postJsonData).success(function (response) {
                return response;
            });

            $http.post(requestUrl, requestInfo.postJsonData).error(function (response, status) {

                alert("Http Status:" +JSON.stringify(status));
            });
        }
        if(requestInfo.method == "GET") {
            var res = $http.get(requestUrl).success(function (response) {

            });
            $http.get(requestUrl).error(function (response, status) {
                alert(JSON.stringify(status));
            });
        }

        return res;
    }


    return service;
}]);

assemblyUrl = function( requestInfo ){
    requestInfo.mapping = requestInfo.app_name + requestInfo.parent_mapping + requestInfo.child_mapping;
    if(requestInfo.method == 'POST') {
        return requestInfo.protocol + ":" + "//" + requestInfo.host + ":" + requestInfo.port + requestInfo.mapping;
    }
    var strBuf = '';
    if(requestInfo.method == 'GET') {
        if(requestInfo.getParamData.length > 0) {
            for (var i = 0; i < requestInfo.getParamData.length; i++) {
                var key = requestInfo.getParamData[i].paramKey;
                var value = requestInfo.getParamData[i].paramVal;
                if (i == 0) {
                    strBuf = strBuf + '?' + key + '=' + value;
                } else {
                    strBuf = strBuf + '&' + key + '=' + value;
                }
            }
        }
        return requestInfo.protocol + ":" + "//" + requestInfo.host + ":" + requestInfo.port + requestInfo.mapping + strBuf;
    }
}
