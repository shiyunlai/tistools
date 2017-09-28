/**
 * Created by hasee on 2017/7/11.
 */
MetronicApp.factory('common_service',['$http', '$q', function ($http,$q) {
    var service={};
    //公共的服务方法，直接调用即可
    service.post = function (api, subFrom) {
        var res = $http.post(manurl+'/' + api.ctrl + "/" + api.func,subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };



    //���õ�service���Ժ�Ͳ���Ҫÿ��ҳ�涼дservice�ˣ�ֱ����ÿ��ҳ������common_service,Ȼ����main.js �������õ�API
    //service.post(API.app_service,subFrom)  ֱ���������ü���

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
