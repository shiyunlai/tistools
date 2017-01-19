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
                return response.data;
            });
        }
        return res;
    }

    service.loadChildOrgList = function(searchForm) {
        var res;
        if (isdebug) {
        } else {
            res = $http.post(manurl + "/torg/omOrganization/list",searchForm).then(function (response) {
                return response.data;
            });
        }
        return res;
    }

    service.loadTreeData = function () {
        var res;
        if (isdebug) {
        } else {
            res = $http.get(manurl + "/torg/test").then(function (response) {
                var res1 = angular.fromJson(response.data.message)
                alert(JSON.stringify(res1[0].jt))
                console.log(res1[0].jt)
                return res1[0].jt

            });
        }
        return res;
    }

    service.loadByOrgId = function(orgId){
        var res;
        if(isdebug){
        } else {
            res = $http.post(manurl + "/torg/omOrganization/loadByOrgId",{orgId:orgId}).then(function (response) {
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
            res = $http.post(manurl + "/torg/omOrganization/save",{item:item}).then(function (response) {
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
MetronicApp.factory('employee_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.query = function(searchForm) {
        var res;
        if (isdebug) {
        } else {
            res = $http.post(manurl + "/torg/omEmployee/list",searchForm).then(function (response) {
                return response.data;
            });
        }
        return res;
    }


    service.loadById = function(id){
        var res;
        if(isdebug){
        } else {
            res = $http.post(manurl + "/torg/omEmployee/detail",{id:id}).then(function (response) {
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



    return service;
}]);
MetronicApp.factory('childOrg_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.query = function(searchForm) {
        var res;
        if (isdebug) {
        } else {
            res = $http.post(manurl + "/torg/omOrganization/loadChildOrgList",searchForm).then(function (response) {
                return response.data;
            });
        }
        return res;
    }

    return service;
}]);


