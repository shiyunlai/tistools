/**
 * Created by gaojie on 2017/5/9.
 */
MetronicApp.factory('Workgroup_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.loadmaintree = function (subFrom) {

        var res = $http.post(manurl + "/om/workgroup/workgrouptree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadworksearchtree = function (subFrom) {
        console.log(subFrom)
        var res = $http.post(manurl + "/om/workgroup/searchtree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.loadempin = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/loadempin",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadempNotin = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/loadempNotin",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.loadposin = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/loadpositionin",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadposNotin = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/loadpositionNotin",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.loadxjgroup = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/queryChild",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadallgroup = function () {
        //todo
        var res = $http.post(manurl + "/om/workgroup/queryall",null).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.addgroup = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/add",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deletegroup = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/delete",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.enableGroup = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/enableGroup",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.updateGroup = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/updateGroup",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.initGroupCode = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/initGroupCode",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadPosition = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/loadPosition",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.addEmpGroup = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/addEmpGroup",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.addGroupPosition = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/addGroupPosition",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deleteEmpGroup = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/deleteEmpGroup",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deleteGroupPosition = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/deleteGroupPosition",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    /**----------------------------------工作组应用服务---------------------------------*/
    service.addGroupApp = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/addGroupApp",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deleteGroupApp = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/deleteGroupApp",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.queryApp = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/queryApp",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.queryNotInApp = function (subFrom) {
        var res = $http.post(manurl + "/om/workgroup/queryNotInApp",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    return service;
}]);