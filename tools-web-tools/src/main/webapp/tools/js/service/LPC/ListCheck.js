/**
 * Created by HP on 2016/9/27.
 */
/**
 * Created by HP on 2016/9/13.
 */

MetronicApp.factory('ListCheck_service',['$http', '$q', function ($http,$q) {
    var service={};

    service.import = function (packagePath) {
        //if(isNull(listPath)){
        //   alert("please input list path!");
        //    return;
        //}
        //if(isNull(packagePath)){
        //    alert("please input package path!");
        //    return;
        //}
        var res = $http.post(manurl + "/ListCheckController/importFile","").then(function (response) {
            return response.data;
        });
        return res;
    }

    service.getListFileNames = function (svnusername,svnpassword) {
        var res = $http.post(manurl + "/ListCheckController/getList",{svnUserName:svnusername,svnPassword:svnpassword}).then(function (response) {
            return response.data;
        });
        return res;
    }

    service.checkList = function (featureInfoList,selectedCommitFiles,selectedPackageFiles,svnusername,svnpassword) {
        if(isNull(selectedCommitFiles)){
            alert("please select one list file!");
            return;
        }
        if(isNull(selectedPackageFiles)){
            alert("please select one package file!");
            return;
        }
        //if(isNull(svnusername)){
        //    alert("please input svn username!");
        //    return;
        //}
        //if(isNull(svnpassword)){
        //    alert("please input svn password!");
        //    return;
        //}
        var res = $http.post(manurl + "/ListCheckController/checkList",{
            featureInfoList:featureInfoList,
            selectedCommitFiles:selectedCommitFiles,
            selectedPackageFiles:selectedPackageFiles,
            svnUserName:svnusername,
            svnPassword:svnpassword
        }).then(function (response) {
            return response.data;
        });
        return res;
    }

    return service;
}]);