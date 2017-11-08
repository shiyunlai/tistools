/**
 * Created by wangbo on 2017/7/23.
 */
MetronicApp.factory('menu_service',['$http', '$q', function ($http,$q) {
    var service={};
    //��ѯ����Ӧ��
    service.queryAllAcApp = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/queryAllAcApp",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //��ѯӦ���µĸ��˵�
    service.queryRootMenu = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/queryRootMenu",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //��ѯӦ���²˵�
    service.queryRootMenuTree = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/queryRootMenuTree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //��ѯ�˵����Ӳ˵�
    service.queryChildMenu = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/queryChildMenu",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //�������˵�
    service.createRootMenu = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/createRootMenu",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //�����Ӳ˵�
    service.createChildMenu = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/createChildMenu",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //ɾ���˵�
    service.deleteMenu = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/deleteMenu",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //�޸Ĳ˵�����
    service.editMenu = function (subFrom) {
        var res = $http.post(manurl + "/AcMenuController/editMenu",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };



    return service;
}]);