/**
 * Created by Administrator on 2017/1/5.
 */
/**
 * Created by Administrator on 2016/12/1.
 */
MetronicApp.factory('organization_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.query = function(searchForm) {
        var res;
        if (isdebug) {
        } else {
            res = $http.post(manurl + "/torg/omOrganization/list",searchForm).then(function (response) {
                alert(JSON.stringify(response))
                return response.data;
            });
        }
        return res;
    }
    service.bizTypeList = function () {
        var res;
        if (isdebug) {
        } else {
            res = $http.post(manurl + "/AttrController/attr_biz_typeList").then(function (response) {
                return response.data;
            });
        }
        return res;
    }

    service.funcCodeList = function (selected) {
        var res;
        if (isdebug) {
        } else {
            res = $http.post(manurl + "/AttrController/attr_func_codeList",{item:selected}).then(function (response) {
                return response.data;
            });
        }
        return res;
    }

    service.loadById = function(id){
        var res;
        if(isdebug){
        } else {
            res = $http.post(manurl + "/AttrController/detail",{id:id}).then(function (response) {
                return response.data;
            });
        }
        return res;
    }

    service.attrDel = function (id) {
        var res;
        if(isdebug){
        } else {
            res = $http.post(manurl + "/AttrController/attrDel",{id:id}).then(function (response) {
                return response.data;
            });
        }
        return res;
    }

    service.save = function (item) {
        var res;
        if(isdebug){
        } else {
            res = $http.post(manurl + "/AttrController/save",{item:item}).then(function (response) {
                return response.data;
            });
        }
        return res;
    }

    service.dictList = function () {
        var res;
        if (isdebug) {
        } else {
            res = $http.post(manurl + "/AttrController/dictList").then(function (response) {
                return response.data;
            });
        }
        return res;
    }

    return service;
}]);


