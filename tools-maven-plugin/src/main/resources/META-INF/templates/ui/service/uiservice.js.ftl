'use strict';
var services = angular.module('nglanmo.${uiservice.id}_service', []);
services.factory('${uiservice.id}_service',['$http', '$q', function ($http,$q) {
    var service={};
    service.query = function(searchForm){
        var res;
        if(isdebug){
            res= [
                {"id":"20","lastName":"su0","firstName":"zhang0"},
                {"id":"21","lastName":"su1","firstName":"zhang1"},
                {"id":"22","lastName":"su2","firstName":"zhang2"},
                {"id":"23","lastName":"su3","firstName":"zhang3"},
                {"id":"24","lastName":"su4","firstName":"zhang4"},
                {"id":"25","lastName":"su5","firstName":"zhang5"},
                {"id":"26","lastName":"su6","firstName":"zhang6"},
                {"id":"27","lastName":"su7","firstName":"zhang7"},
                {"id":"28","lastName":"su8","firstName":"zhang8"},
                {"id":"29","lastName":"su9","firstName":"zhang9"}
            ];
        }else{
           res=  $http.post(baseUrl+'/${uijsserviceId}/${uiservice.id}/list', searchForm).then(function (response) {
                return response.data;
            });
          //  return [{"createTime":null,"guige":"b","id":"","idpath":"","leixing":"","mingcheng":"a","modifiedTime":null,"namepath":"","page":null,"toporgid":""}];
        }
        return res;
    };
	 service.loadById = function(id){
	     var res;
        res=  $http.post(baseUrl+'/${uijsserviceId}/${uiservice.id}/list/id', {"id":id }).then(function (response) {
            return response.data;
        })
        return res;
    };
    service.createOrUpdate = function(item){
        var promise;
    	if(isdebug){
    	}else{
            promise=   $http.post(baseUrl+'/${uijsserviceId}/${uiservice.id}/edit',item);
    	}
    	return promise;
       
    };
	  service.daoru = function(item){
        var promise;
        if(isdebug){
        }else{
            promise=   $http.post(baseUrl+'/${uijsserviceId}/${uiservice.id}/daoru',item);
        }
        return promise;

    };
    service.delete = function(items){
        var promise;
        if(isdebug){
        }else{
            promise=   $http.post(baseUrl+'/${uijsserviceId}/${uiservice.id}/delete',items);
        }
        return promise;
    };
	 service.softdelete = function(items){
        var promise;
        if(isdebug){
        }else{
            promise=   $http.post(baseUrl+'/${uijsserviceId}/${uiservice.id}/softdelete',items);
        }
        return promise;
    };

    return service;
}]);
