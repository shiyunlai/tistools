/**
 * Created by HP on 2016/9/27.
 */

MetronicApp.controller('ListCheck_controller', function ($rootScope, $scope, $state, $stateParams, filterFilter,ListCheck_service,FeatureReg_service, $modal, $http, $timeout,$interval) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });
    var lpc = {};
    $scope.lpc = lpc;

    var page = {};
    page.itemsperpage = 10;
    lpc.page = page;

    //上传文件
    $scope.upload = function () {
        var flag = checkFile();
        alert(flag);
        if(!flag){
            return;
        }

        var fd = new FormData();
        var file = document.getElementById("uploadFile").files[0];
        fd.append('file', file);
        $http({
            method:'POST',
            url:manurl + "/ListCheckController/upload",
            data: fd,
            headers: {'Content-Type':undefined},
            transformRequest: angular.identity,
            eventHandlers: {
                progress: function(c) {
                    console.log('Progress -> ' + c);
                    console.log(c);
                }
            },
            uploadEventHandlers: {
                progress: function(e) {
                    console.log('UploadProgress -> ' + e);
                    console.log(e);
                }
            }
        }).success( function ( response ) {
            //上传成功的操作
            alert("上传成功!");
        });
    };

    var checkFile = function(){
        if(isNull(lpc.packagePath)){
           alert("请选择上传的文件!");
            return false;
        }
        if(lpc.packagePath.substring(lpc.packagePath.indexOf(".")) != ".zip"){
            alert("文件格式不支持,请选择.zip文件!");
            return false;
        }
        return true;
    }

    lpc.disCheck = true;
    $scope.checkSvnEmpty = function(){
        if(isNull(lpc.svnusername) || isNull(lpc.svnpassword)){
            lpc.disCheck = true;
        }else{
            lpc.disCheck = false;
        }
    }

    /*根据条件找出清单文件*/
    $scope.importList = function(){
        var queryCondition = {};
        if(isNull(lpc.developer)){
            queryCondition.developer = "";
        }else{
            queryCondition.developer = lpc.developer;
        }

        if(isNull(lpc.releaseDate)){
            queryCondition.releaseDate = "";
        }else{
            queryCondition.releaseDate = lpc.releaseDate;
        }
        queryCondition.workId = "";
        queryCondition.taskId = "";
        queryCondition.featureType = "";
        queryCondition.status = "";
        FeatureReg_service.query(queryCondition).then(function (data) {
            lpc.featureInfoList = data;
            lpc.commit_list = [];
            for(var index in data){
                lpc.commit_list.push(data[index].listFileName);
            }
        },function (err) {
            alert("导入清单失败!");
        });
    }

    /*根据路径找出包文件*/
    $scope.importPackage = function(){
        //checkImport();
        ListCheck_service.import().then(function (data) {
            //alert(JSON.stringify(data));
            if(data.status == "error"){
                alert(data.message);
                return;
            }
            if(data.package_list.length == 0){
                alert("未找到包文件!");
                return;
            }
            //lpc.commit_list = data.commit_list;
            lpc.package_list = data.package_list;
        });
    }

    /*记录选择的清单文件*/
    lpc.selectedCommitFiles = [];
    $scope.selectCommitFile = function(isChecked,commitFile){
        if(isChecked){
            lpc.selectedCommitFiles.push(commitFile);
        }else{
            for(var i=0;i<lpc.selectedCommitFiles.length;i++){
                if(lpc.selectedCommitFiles[i] == commitFile){
                    lpc.selectedCommitFiles.splice(i,1);
                    break;
                }
            }
        }
        //alert(lpc.selectedCommitFiles);
    }

    $scope.selectAllCommitFile = function(listIsChecked){

        if(listIsChecked){
            lpc.selectedCommitFiles = [];
            for(var i=0;i<lpc.commit_list.length;i++){
                lpc.selectedCommitFiles.push(lpc.commit_list[i]);
            }
        }else{
            lpc.selectedCommitFiles = [];
        }
        //alert(lpc.selectedCommitFiles);
    }

    /*记录选择的包文件*/
    lpc.selectedPackageFiles = [];
    $scope.selectPackageFile = function(isChecked,packageFile){
        if(isChecked){
            lpc.selectedPackageFiles.push(packageFile);
        }else{
            for(var i=0;i<lpc.selectedPackageFiles.length;i++){
                if(lpc.selectedPackageFiles[i] == packageFile){
                    lpc.selectedPackageFiles.splice(i,1);
                    break;
                }
            }
        }
        //alert(lpc.selectedPackageFiles);
    }

    $scope.selectAllPackageFile = function(packageIsChecked){
        if(packageIsChecked){
            lpc.selectedPackageFiles = [];
            for(var i=0;i<lpc.package_list.length;i++){
                lpc.selectedPackageFiles.push(lpc.package_list[i]);
            }
        }else{
            lpc.selectedPackageFiles = [];
        }
        //alert(lpc.selectedPackageFiles);
    }

    /*根据清单检查包中是否存在*/
    $scope.checkList = function(){
        var featureInfoList = [];
        var featureInfo = {};
        for(var index in lpc.selectedCommitFiles){
            for(var ind in lpc.featureInfoList){
                var listFileName =  lpc.featureInfoList[ind].listFileName;
                if(listFileName == lpc.selectedCommitFiles[index]){
                    featureInfo.listFileName = listFileName;
                    featureInfo.branchId = lpc.featureInfoList[ind].branchId;
                    featureInfoList.push(featureInfo);
                    break;
                }
            }
        }
        ListCheck_service.checkList(featureInfoList,lpc.selectedCommitFiles,lpc.selectedPackageFiles,lpc.svnusername,lpc.svnpassword).then(function (data) {
            //alert(JSON.stringify(data));
            if(data.status == "error"){
                alert(data.message);
                return;
            }
            if(data.length == 0){
                alert("result is ok!");
            }
            lpc.differList = data;
            pagination(data);
        });
    }

    $scope.selectUploadFile = function(){
        $('#uploadFile').click();
    }

    $('#uploadFile').change(function() {
        var file = document.getElementById("uploadFile").files[0];
        $('#uploadFile1').val(file.name);
        lpc.packagePath = file.name;
    });

    /*分页处理*/
    var pagination = function(list){
        var queryResult = [];
        var perPageNum =  lpc.page.itemsperpage;
        var totalSize = list.length;
        var totalPages = Math.floor(totalSize/perPageNum);
        var lastPageNum = totalSize%perPageNum;
        if(lastPageNum!=0){
            totalPages++;
        }
        //alert(perPageNum);alert(dataSize);
        if(totalSize<=perPageNum){
            queryResult = list;
        }else{
            for(var i=0;i<perPageNum;i++){
                queryResult.push(list[i]);
            }
        }
        lpc.queryResult = queryResult;

        lpc.page.currentPage = 1;
        lpc.page.totalPages = totalPages;
        lpc.page.totalItems = totalSize;
    }

    /*上一页*/
    $scope.getPerPage = function(){
        var currentPage = lpc.page.currentPage;
        if(currentPage==1){
            return;
        }
        var perPageNum = lpc.page.itemsperpage;
        var startIndex = (currentPage-2)*perPageNum;
        var endIndex = startIndex + perPageNum-1;

        lpc.queryResult = lpc.differList.slice(startIndex, endIndex + 1);

        //alert(JSON.stringify(biz.queryResult));
        lpc.page.currentPage = currentPage - 1;
    }

    /*下一页*/
    $scope.getPostPage = function () {
        var currentPage = lpc.page.currentPage;
        var totalPages = lpc.page.totalPages;
        if(currentPage==totalPages){
            return;
        }
        var perPageNum = lpc.page.itemsperpage;
        var startIndex = currentPage*perPageNum;

        if(currentPage==totalPages-1){
            lpc.queryResult = lpc.differList.slice(startIndex);
        }else{
            var endIndex = startIndex + perPageNum-1;
            lpc.queryResult = lpc.differList.slice(startIndex,endIndex+1);
        }

        //alert(JSON.stringify(biz.queryResult));
        lpc.page.currentPage = currentPage + 1;
    }
});