/**
 * Created by wangbo on 2017/6/13.
 */
MetronicApp.factory('application_service',['$http', '$q', function ($http,$q) {
    var service={};
    //新增应用服务
    service.appAdd = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/appAdd",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };
    //删除应用服务
    service.appDel = function(item){
        var res = $http.post(manurl + "/AcAppController/appDel",item).then(function (response) {
            return response.data;
        });
        return res;
    }

    //查询应用服务
    service.appQuery = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/appQuery",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }
    //修改应用服务
    service.appEdit = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/appEdit",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    }

    /*功能组服务*/
    //新增功能组、子功能组
    service.groupAdd = function (item) {
        var res = $http.post(manurl + "/AcAppController/groupAdd",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //删除功能组、子功能组
    service.groupDel = function (item) {
        var res = $http.post(manurl + "/AcAppController/groupDel",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询功能组、子功能组
    service.groupQuery = function (item) {
        var res = $http.post(manurl + "/AcAppController/groupQuery",item).then(function (response) {
            return response.data;
        });
        return res;
    };


    //修改功能组、子功能组
    service.groupEdit = function (item) {
        var res = $http.post(manurl + "/AcAppController/groupEdit",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    /* 功能服务*/
    //新增功能服务
    service.acFuncAdd = function (item) {
        var res = $http.post(manurl + "/AcAppController/acFuncAdd",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //修改功能服务
    service.acFuncEdit = function (item) {
        var res = $http.post(manurl + "/AcAppController/acFuncEdit",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //更新资源
    service.acFuncResourceEdit = function (item) {
        var res = $http.post(manurl + "/AcAppController/acFuncResourceEdit",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //删除功能服务
    service.acFuncDel = function (item) {
        var res = $http.post(manurl + "/AcAppController/acFuncDel",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //功能查询服务
    service.acFuncQuery = function (item) {
        var res = $http.post(manurl + "/AcAppController/acFuncQuery",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询所有功能服务
    service.queryAllFunc = function (item) {
        var res = $http.post(manurl + "/AcAppController/queryAllFunc",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //导入功能服务
    service.importFunc = function (item) {
        var res = $http.post(manurl + "/AcAppController/importFunc",item).then(function (response) {
            return response.data;
        });
        return res;
    };

    //查询功能行为类型
    service.functypequery = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/functypequery",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    //根据功能的GUID查询行为类型定义
    service.queryBhvtypeDefByFunc = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/queryBhvtypeDefByFunc",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };

    //根据类型的GUID查询行为
    service.queryBhvDefByBhvType = function (subFrom) {
        var res = $http.post(manurl + "/AcAppController/queryBhvDefByBhvType",subFrom).then(function (response) {
            return response.data;
        });
        return res;
    };


    /*  //资源查询服务


      //功能类型服务
      service.funtypeQuery = function (item) {
          var res = $http.post(manurl + "/AcAppController/funtypeQuery",item).then(function (response) {
              return response.data;
          });
          return res;
      };

      //功能行为
      service.funSearch = function (item) {
          var res = $http.post(manurl + "/AcAppController/funSearch",item).then(function (response) {
              return response.data;
          });
          return res;
      };*/
    return service;
}]);
