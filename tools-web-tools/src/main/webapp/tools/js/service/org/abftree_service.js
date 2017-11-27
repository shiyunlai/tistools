/**
 * Created by gaojie on 2017/5/9.
 */
MetronicApp.factory('abftree_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.loadmaintree = function (subFrom) {

        var res = $http.post(manurl + "/om/org/tree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadsearchtree = function (subFrom) {
        console.log(subFrom)
        var res = $http.post(manurl + "/om/org/search",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadgwtree = function (subFrom) {
        //todo
        var res = $http.post(manurl + "/om/org/tree",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadempbyorg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/loadempbyorg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadempNotinorg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/loadempNotinorg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadxjjg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/loadxjjg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
/*    service.loadqxxx = function (subFrom) {
        var res = $http.post(manurl + "/om/org/test",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }*/
    service.addorg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/add",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.copyOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/copyOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.moveOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/moveOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.initcode = function (subFrom) {
        var res = $http.post(manurl + "/om/org/initcode",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.updateOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/updateOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deleteOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/deleteOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.createPosition = function (subFrom) {
        var res = $http.post(manurl + "/om/org/createPosition",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    //修改岗位
    service.updatePosition = function (subFrom) {
        var res = $http.post(manurl + "/om/org/updatePosition",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    //添加机构-员工关系表
    service.addEmpOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/addEmpOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    //删除机构-员工关系表
    service.deleteEmpOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/deleteEmpOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadEmpbyPosition = function (subFrom) {
        var res = $http.post(manurl + "/om/org/loadEmpbyPosition",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadempNotinposit = function (subFrom) {
        var res = $http.post(manurl + "/om/org/loadempNotinposit",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.addEmpPosition = function (subFrom) {
        var res = $http.post(manurl + "/om/org/addEmpPosition",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deleteEmpPosition = function (subFrom) {
        var res = $http.post(manurl + "/om/org/deleteEmpPosition",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.loadxjposit = function (subFrom) {
        var res = $http.post(manurl + "/om/org/loadxjposit",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    //启用-注销-重启-停用公用
    service.enableorg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/enableorg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    //启用-注销-重启-停用公用
    service.enableposition = function (subFrom) {
        var res = $http.post(manurl + "/om/org/enableposition",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deletePosition = function (subFrom) {
        var res = $http.post(manurl + "/om/org/deletePosition",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.initPosCode = function (subFrom) {
        var res = $http.post(manurl + "/om/org/initPosCode",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    /**------------------------权限系列方法--------------------*/
    service.queryRole = function (subFrom) {
        var res = $http.post(manurl + "/om/org/queryRole",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.addRoleParty = function (subFrom) {
        var res = $http.post(manurl + "/om/org/addRoleParty",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deleteRoleParty = function (subFrom) {
        var res = $http.post(manurl + "/om/org/deleteRoleParty",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.queryRoleNot = function (subFrom) {
        var res = $http.post(manurl + "/om/org/queryRoleNot",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.queryRoleFun = function (subFrom) {
        var res = $http.post(manurl + "/om/org/queryRoleFun",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.queryAllorg = function () {
        var res = $http.post(manurl + "/om/org/queryAllorg").then(function (response) {
            return response.data;
        });
        return res;
    }
    service.queryAllposbyOrg = function (subFrom) {
        var res = $http.post(manurl + "/om/org/queryAllposbyOrg",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    /**---------------------------应用系列方法----------------------*/
    service.queryAppinPos = function (subFrom) {
        var res = $http.post(manurl + "/om/org/queryAppinPos",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.queryAppNotinPos = function (subFrom) {
        var res = $http.post(manurl + "/om/org/queryAppNotinPos",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.addAppPosition = function (subFrom) {
        var res = $http.post(manurl + "/om/org/addAppPosition",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    service.deleteAppPosition = function (subFrom) {
        var res = $http.post(manurl + "/om/org/deleteAppPosition",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }


    return service;
}]);