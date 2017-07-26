/**
 * Created by wangbo on 2017/6/13.
 */
MetronicApp.factory('application_service',['$http', '$q', function ($http,$q) {
    var service={};
    //����Ӧ�÷���
    service.appAdd = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/appAdd",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };
    //ɾ��Ӧ�÷���
    service.appDel = function(item){
        var res = $http.post(manurl + "/AcAppController/appDel",item).then(function (response) {
            return response.data;
        });
        return res;
    }

    //��ѯӦ�÷���
    service.appQuery = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/appQuery",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    //�޸�Ӧ�÷���
    service.appEdit = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/appEdit",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    /*���������*/
    //���������顢�ӹ�����
    service.groupAdd = function (item) {
        var res = $http.post(manurl + "/AcAppController/groupAdd",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //ɾ�������顢�ӹ�����
    service.groupDel = function (item) {
        var res = $http.post(manurl + "/AcAppController/groupDel",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //��ѯ�����顢�ӹ�����
    service.groupQuery = function (item) {
        var res = $http.post(manurl + "/AcAppController/groupQuery",item).then(function (response) {
            return response.data;
        });
        return res;
    };


    //�޸Ĺ����顢�ӹ�����
    service.groupEdit = function (item) {
        var res = $http.post(manurl + "/AcAppController/groupEdit",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    /* ���ܷ���*/
    //�������ܷ���
    service.acFuncAdd = function (item) {
        var res = $http.post(manurl + "/AcAppController/acFuncAdd",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //�޸Ĺ��ܷ���
    service.acFuncEdit = function (item) {
        var res = $http.post(manurl + "/AcAppController/acFuncEdit",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //������Դ
    service.acFuncResourceEdit = function (item) {
        var res = $http.post(manurl + "/AcAppController/acFuncResourceEdit",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //ɾ�����ܷ���
    service.acFuncDel = function (item) {
        var res = $http.post(manurl + "/AcAppController/acFuncDel",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //���ܲ�ѯ����
    service.acFuncQuery = function (item) {
        var res = $http.post(manurl + "/AcAppController/acFuncQuery",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //��ѯ���й��ܷ���
    service.queryAllFunc = function (item) {
        var res = $http.post(manurl + "/AcAppController/queryAllFunc",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //���빦�ܷ���
    service.importFunc = function (item) {
        var res = $http.post(manurl + "/AcAppController/importFunc",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //��ѯ������Ϊ����
    service.functypequery = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/functypequery",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //���������Ϊ����
    service.addBhvtypeForFunc = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/addBhvtypeForFunc",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //���������Ϊ����
    service.addBhvDefForFunc = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/addBhvDefForFunc",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //��ѯ������ĳ����Ϊ���͵Ĳ�����Ϊ
    service.queryBhvDefInTypeForFunc = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/queryBhvDefInTypeForFunc",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //���ݹ��ܵ�GUID��ѯ��Ϊ���Ͷ���
    service.queryBhvtypeDefByFunc = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/queryBhvtypeDefByFunc",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //�������͵�GUID��ѯ��Ϊ
    service.queryBhvDefByBhvType = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/queryBhvDefByBhvType",subFrom).then(function (response) {

            return response.data;
        });
        return res;
    };



    //��ѯ������������Ϊ����
    service.queryAllBhvDefForFunc = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/queryAllBhvDefForFunc",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //ɾ�����ܶ�Ӧ����Ϊ����
    service.delFuncBhvType = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/delFuncBhvType",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //ɾ�����ܶ�Ӧ����Ϊ����
    service.delFuncBhvDef = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/delFuncBhvDef",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //����Ӧ��
    service.enableApp = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/enableApp",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //�ر�Ӧ��
    service.disableApp = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/disableApp",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    return service;
}]);
