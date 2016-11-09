/**
 * Created by HP on 2016/10/18.
 */
MetronicApp.controller('FeatureReg_controller', function ($filter,$rootScope, $scope, $state, pagination_service, filterFilter,FeatureReg_service, $modal, $http, $timeout,$interval) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });
    var lpc = {};
    $scope.lpc = lpc;

    var page = {};
    page.itemsperpage = 10;
    //page.totalPages=1;
    //page.totalItems=0;
    lpc.page = page;

    lpc.showMainPanel = true;
    lpc.showEditPanel = false;

    lpc.showModal=false;
    $scope.alertModal = function(meg){
        lpc.showModal=true;
        lpc.modalMeg = meg;
    }
    $scope.closeModal = function(){
        lpc.showModal=false;
    }

    $scope.query = function(){
        var queryCondition = {};
        if(isNull(lpc.workId)){
            queryCondition.workId = "";
        }else{
            queryCondition.workId = lpc.workId;
        }

        if(isNull(lpc.taskId)){
            queryCondition.taskId = "";
        }else{
            queryCondition.taskId = lpc.taskId;
        }

        if(isNull(lpc.featureType)){
            queryCondition.featureType = "";
        }else{
            queryCondition.featureType = lpc.featureType;
        }

        if(isNull(lpc.developer)){
            queryCondition.developer = "";
        }else{
            queryCondition.developer = lpc.developer;
        }

        if(isNull(lpc.status)){
            queryCondition.status = "";
        }else{
            queryCondition.status = lpc.status;
        }
        queryCondition.releaseDate = "";
        FeatureReg_service.query(queryCondition).then(function (data) {
            //alert(JSON.stringify(data));
            lpc.resultList = data;
            pagination_service.pagination(lpc);
        },function (err) {
            //alert("信息查询失败!");
            $scope.alertModal("信息查询失败!");
        });
    }

    $scope.reset = function(){
        lpc.workId = "";
        lpc.taskId = "";
        lpc.featureType = "";
        lpc.developer = "";
        lpc.status = "";
        lpc.queryResult = [];
    }

    $scope.modify = function(workId){
        //初始化编辑界面字段值
        emptyEditValue();

        var results = lpc.queryResult;
        for(var index in results){
            if(workId == results[index].workId){
                lpc.edit.workId = results[index].workId;
                lpc.edit.taskId = results[index].artfId;
                lpc.edit.workDesc = results[index].workDesc;
                lpc.edit.featureId = results[index].branchId;
                lpc.edit.featureType = results[index].branchType;
                lpc.edit.status = results[index].status;
                lpc.edit.featureMgr = results[index].branchMgr;
                var openDateStr = results[index].openDate;
                var openDate = new Date(openDateStr.substring(0,4)+"/"+openDateStr.substring(4,6)+"/"+openDateStr.substring(6));
                lpc.edit.openDate = openDate;
                lpc.edit.listFileName = results[index].listFileName;
                lpc.edit.developer = results[index].developer;
                lpc.edit.releaser = results[index].releaser;
                var releaseDateStr = results[index].releaseDate;
                var releaseDate = new Date(releaseDateStr.substring(0,4)+"/"+releaseDateStr.substring(4,6)+"/"+releaseDateStr.substring(6));
                lpc.edit.releaseDate = releaseDate;
                break;
            }
        }

        lpc.showMainPanel = false;
        lpc.showEditPanel = true;

        lpc.editFlag = "modify";
        lpc.workIdIsRead = true;//workId字段只读
        lpc.featureIdIsRead = true;//featureId字段只读
    }

    $scope.add = function(){
        emptyEditValue();

        lpc.showMainPanel = false;
        lpc.showEditPanel = true;

        lpc.editFlag = "add";
        lpc.workIdIsRead = false;
        lpc.featureIdIsRead = false;
    }

    var emptyEditValue = function(workId){
        var edit = {};
        lpc.edit = edit;
        lpc.edit.submit = false;
    }

    $scope.delete = function(){
        if(lpc.workIdArry.length == 0){
            //alert("请选择要删除的信息!");
            $scope.alertModal("请选择要删除的信息!");
            return;
        }
        FeatureReg_service.delete(lpc.workIdArry).then(function (data) {
            //删除缓存中记录
            for(var index in lpc.paginationList){
                var perPageList = lpc.paginationList[index];
                for(var ind in perPageList){
                    var workId = perPageList[ind].workId;
                    if(arrayContains(workId,lpc.workIdArry)){
                        perPageList.splice(ind,1);
                    }
                }
            }

            //删除表中的记录
            for(var ind in lpc.queryResult){
                var branchId = lpc.queryResult[ind].workId;
                if(arrayContains(workId,lpc.workIdArry)){
                    lpc.queryResult.splice(ind,1);
                }
            }

            lpc.workIdArry = [];//重置选择的记录
            //alert("信息删除成功!");
            $scope.alertModal("信息删除成功!");
        },function (err) {
            //alert("信息删除失败!");
            $scope.alertModal("信息删除失败!");
        })
    }

    var arrayContains = function(value,array){
        for(var index in array){
            if(array[index] == value){
               return true;
            }
        }
        return false;
    }

    /*记录选择的记录*/
    lpc.workIdArry = [];
    $scope.selectOneRecord = function(isChecked,workId){
        if(isChecked){
            lpc.workIdArry.push(workId);
        }else{
            for(var i=0;i<lpc.workIdArry.length;i++){
                if(lpc.workIdArry[i] == workId){
                    lpc.workIdArry.splice(i,1);
                    break;
                }
            }
        }
    }

    $scope.selectAllRecord = function(){
        if(lpc.selectAll){
            lpc.workIdArry = [];
            for(var i=0;i<lpc.queryResult.length;i++){//全选时只将当前界面作为删除对象
                lpc.workIdArry.push(lpc.queryResult[i].workId);
            }
        }else{
            lpc.workIdArry = [];
        }
    }

    $scope.save = function(){
        //启用表单验证
        if ($scope.editPanel_form.$invalid) {
            lpc.edit.submit = true;
            return;
        }
        var openDate = $filter('date')(lpc.edit.openDate, 'yyyyMMdd');
        var releaseDate = $filter('date')(lpc.edit.releaseDate, 'yyyyMMdd');
        lpc.edit.openDate = openDate;
        lpc.edit.releaseDate = releaseDate;

        //记录编辑界面的数据
        var result = {};
        result.workId = lpc.edit.workId;
        result.artfId = lpc.edit.taskId;
        result.workDesc = lpc.edit.workDesc;
        result.branchId = lpc.edit.featureId;
        result.branchType = lpc.edit.featureType;
        result.status = lpc.edit.status;
        result.branchMgr = lpc.edit.featureMgr;
        result.openDate = lpc.edit.openDate;
        result.listFileName = lpc.edit.listFileName;
        result.developer = lpc.edit.developer;
        result.releaser = lpc.edit.releaser;
        result.releaseDate = lpc.edit.releaseDate;

        if(lpc.editFlag == "add"){
            FeatureReg_service.add(lpc.edit).then(function (data) {
                if(isNull(lpc.paginationList)){
                    $scope.alertModal("信息保存成功!");
                    lpc.showMainPanel = true;
                    lpc.showEditPanel = false;
                    return;
                }
                //在缓存和视图中最后一页添加该记录
                var lastPageList = lpc.paginationList[lpc.paginationList.length-1];
                if(lastPageList.length<lpc.page.itemsperpage){
                    lastPageList.push(result);

                    lpc.page.currentPage = lpc.page.totalPages;
                    lpc.page.totalItems = lpc.page.totalItems + 1;
                    lpc.queryResult = lastPageList;
                }else{
                    var newPageList = [result];
                    lpc.paginationList[lpc.paginationList.length] = newPageList;

                    lpc.page.totalPages = lpc.page.totalPages + 1;
                    lpc.page.totalItems = lpc.page.totalItems + 1;
                    lpc.page.currentPage = lpc.page.totalPages;
                    lpc.queryResult = newPageList;
                }

                //alert("信息保存成功!");
                $scope.alertModal("信息保存成功!");
                lpc.showMainPanel = true;
                lpc.showEditPanel = false;
            },function (err) {
                //alert("信息保存失败!");
                $scope.alertModal("信息保存失败!");
            })
        }else if(lpc.editFlag == "modify"){
            FeatureReg_service.modify(lpc.edit).then(function (data) {
                //在缓存和视图中最后一页添加该记录
                var currentPageList = lpc.paginationList[lpc.page.currentPage-1];
                for(var index in currentPageList){
                    var workId = currentPageList[index].workId;
                    if(result.workId == workId){
                        currentPageList[index] = result;
                        break;
                    }
                }
                lpc.paginationList[lpc.page.currentPage-1] = currentPageList;

                lpc.queryResult = currentPageList;

                //alert("信息修改成功!");
                $scope.alertModal("信息修改成功!");
                lpc.showMainPanel = true;
                lpc.showEditPanel = false;
            },function (err) {
                $scope.alertModal("信息修改失败!");
                //alert("信息修改失败!");
            })
        }
    }

    $scope.cancel = function(){
        var flag = confirm("继续取消，将不会保存当前信息！");
        if(flag){
            lpc.showMainPanel = true;
            lpc.showEditPanel = false;
        }
    }

    $scope.getPerPage = function(){
        pagination_service.getPerPage(lpc);
    }

    $scope.getPostPage = function(){
        pagination_service.getPostPage(lpc);
    }
});