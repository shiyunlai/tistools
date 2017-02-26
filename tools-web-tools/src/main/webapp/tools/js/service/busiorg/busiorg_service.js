/**
 * Created by Jack.Gao on 2017/1/4.
 */
MetronicApp.factory('busiorg_service',['$http', '$q', function ($http,$q) {
    var service={};
    service.query = function(searchForm) {
        var res;
        if (isdebug) {
        } else {
            res = $http.post("http://localhost:8089/tis/torg/omBusiorg/list",searchForm).then(function (response) {
                return response.data;
            });
        }
        return res;
    }


    service.loadById = function(id){
        var res;
        if(isdebug){
        } else {
            res = $http.post("http://localhost:8089/tis/torg/omBusiorg/list/id",{id:id}).then(function (response) {
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