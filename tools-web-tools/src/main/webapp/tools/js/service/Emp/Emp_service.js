/**
 * Created by gaojie on 2017/5/9.
 */
MetronicApp.factory('Emp_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.loadempgrid = function () {
        var res = $http.post(manurl + "/om/emp/queryemployee",{}).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.addemp = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/addemployee",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deletemp = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/deletemp",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadEmpOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/loadEmpOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadEmpPos = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/loadEmpPos",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadOrgNotInbyEmp = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/loadOrgNotInbyEmp",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadPosNotInbyEmp = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/loadPosNotInbyEmp",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.assignOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/assignOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.updateemployee = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/updateemployee",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.fixmainOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/fixmainOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.disassignOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/disassignOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.assignPos = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/assignPos",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.fixmainPos = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/fixmainPos",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.disassignPos = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/disassignPos",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.initEmpCode = function (subFrom) {
        var res = $http.post(manurl + "/om/emp/initEmpCode",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    return service;
}]);