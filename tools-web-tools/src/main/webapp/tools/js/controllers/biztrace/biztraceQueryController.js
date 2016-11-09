/**
 * Created by cdq on 2016/9/2.
 */
MetronicApp.controller('biztraceQuery_controller', function ($filter,$rootScope, $scope, $state, $stateParams,biztrace_service, filterFilter, $modal, $http, $timeout,$interval) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });

    var biz = {};
    $scope.biz = biz;

    biz.isDisable = true;

    var page = {};
    page.itemsperpage = 10;
    biz.page = page;

    biz.is_selected1 = 'true';
    biz.is_selected2 = 'false';
    $scope.a1 = function () {
        biz.is_selected1 = 'true';
        biz.is_selected2 = 'false';
    }
    $scope.a2 = function () {
        biz.is_selected1 = 'false';
        biz.is_selected2 = 'true';
    }
    $scope.change = function(path){
        if(!isNull(path)){
            biz.isDisable = false;
        }else{
            biz.isDisable = true;
        }
    }
    //按条件查询
    $scope.queryByConditions = function (trans_serial,trans_date) {
        var transDate = $filter('date')(trans_date, 'yyyy-MM-dd');
        biztrace_service.queryByCondition(trans_serial,transDate).then(function (datas) {
            //alert(JSON.stringify(datas));
            biz.dataList =datas;
            pagination(datas);
        });
        //biz.queryResult = [{trans_serial:"123456789",trans_code:"TX000001"},{trans_serial:"0987654",trans_code:"TX111111"}];
    }

    //按排名查询
    $scope.queryByRanking = function (pre_index) {
        biztrace_service.queryByRank(pre_index).then(function (datas) {
            //alert(JSON.stringify(datas));
            biz.dataList =datas;
            for(var index in biz.dataList){
                //alert(index++)
                var data = biz.dataList[index];
                data.ranking = ++index;
            }
            pagination(biz.dataList);
        });
    }

    //显示详细界面
    biz.totalInfoFlag = true;
    biz.detailInfoFlag = false;
    var detail = {};
    $scope.showDetailInfo = function(data){
        biz.totalInfoFlag = false;
        biz.detailInfoFlag = true;

        //alert(JSON.stringify(data));
        detail.trans_serial = data.trans_serial;
        detail.trans_code = data.trans_code;
        detail.trans_name = data.trans_name;
        detail.spend_time = data.handle_time;
        detail.spend_time_index = data.ranking;


        //根据流水号查询具体日志信息
        biztrace_service.queryDetailInfo(data.trans_serial).then(function (datas) {
            biz.bodyPage_dataList = biz.dataList;
            biz.page.bodyPage_currentPage = biz.page.currentPage;
            biz.page.bodyPage_totalPages = biz.page.totalPages;
            biz.page.bodyPage_totalItems = biz.page.totalItems;

            //alert(JSON.stringify(datas));
            biz.dataList = datas;
            pagination(datas);
            //detail.stepInfo =datas;
        });
        //detail.stepInfo = [{uuid:"asdfgdgdfds",step_time:"2016/09/09"},{uuid:"dsfsfdfdfsd",step_time:"2018/01/01"}];

        biz.detail = detail;
    }

    //重置
    $scope.cleardata = function(){
        biz.trans_serial = "";
        biz.trans_date = "";
    }

    //返回查询界面
    $scope.returnTotalInfo = function(){
        biz.totalInfoFlag = true;
        biz.detailInfoFlag = false;

        biz.dataList = biz.bodyPage_dataList;
        biz.page.currentPage = biz.page.bodyPage_currentPage;
        biz.page.totalPages = biz.page.bodyPage_totalPages;
        biz.page.totalItems = biz.page.bodyPage_totalItems;
    }

    //根据路径解析日志文件
    //$scope.reslover = function(path){
    //    //path = "C:/Users/HP/Desktop/M2/logtest";
    //    biztrace_service.resloveBizLogFile(path).then(function (datas){
    //    });
    //}

    //分析日志
    //$scope.analyzer = function(){
    //    //在每次分析分析日志之前到redis中取出尚未被分析的日志日期的集合
    //    biztrace_service.getUnanalyzedLogDate().then(function (datas){
    //        $rootScope.unanalyzedBizLogDates = datas;
    //    });
    //
    //    //将取出的日志日期展示到模态框中供用户选择
    //    var modalInstance = $modal.open({
    //        //template: '<div class="modelMeg">'+
    //        //'<div><span>请选择要分析的日志的日期:</span></div>'+
    //        //'<span>{{$rootScope.unanalyzedBizLogDates}}</span>'+
    //        //'<ul><li ng-repeat="logdate in unanalyzedBizLogDates">'+
    //        //'<input type="checkbox" ng-model="isChecked" ng-click="check(isChecked,logdate)">'+
    //        //'<span>{{logdate}}</span>'+
    //        //'</li> </ul>'+
    //        //'<input  type="button" value="确认" ng-click="ok()">'+
    //        //'<input  type="button" value="取消" ng-click="cancel()">'+
    //        //'</div>'
    //        templateUrl: 'views/biztrace/selectUnAnalyzed.html',
    //        backdrop:'static'
    //    });
    //
    //    //记录用户选择的要分析的日志日期
    //    var analyzedLogDateArry = [];
    //    $rootScope.isChecked = false;
    //    $rootScope.check = function(isChecked,logdate){
    //        if(isChecked){
    //            analyzedLogDateArry.push(logdate);
    //        }else{
    //            analyzedLogDateArry.pop(logdate);
    //        }
    //    }
    //
    //    //选择确认执行日志分析
    //    $rootScope.ok = function () {
    //        if(analyzedLogDateArry.length==0){
    //            alert("please select date which will be analyzed");
    //            return;
    //        }
    //        biztrace_service.analyzeBizLog(analyzedLogDateArry).then(function (datas){
    //        });
    //        modalInstance.close();
    //    };
    //
    //    //选择取消清空用户选择的结果关闭界面
    //    $rootScope.cancel = function () {
    //        //analyzedLogDateArry = [];
    //        modalInstance.close();
    //    };
    //}

    /*分页处理*/
    var pagination = function(list){
        var queryResult = [];
        var perPageNum =  biz.page.itemsperpage;
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
        if(biz.totalInfoFlag) {
            biz.queryResult = queryResult;
        }else{
            detail.stepInfo = queryResult;
        }

        biz.page.currentPage = 1;
        biz.page.totalPages = totalPages;
        biz.page.totalItems = totalSize;
    }

    /*上一页*/
    $scope.getPerPage = function(){
        var currentPage = $scope.biz.page.currentPage;
        if(currentPage==1){
            return;
        }
        var perPageNum = $scope.biz.page.itemsperpage;
        var startIndex = (currentPage-2)*perPageNum;
        var endIndex = startIndex + perPageNum-1;

        if(biz.totalInfoFlag) {
            biz.queryResult = biz.dataList.slice(startIndex, endIndex + 1);
        }else{
            biz.detail.stepInfo = biz.dataList.slice(startIndex, endIndex + 1);
        }
        //alert(JSON.stringify(biz.queryResult));
        biz.page.currentPage = currentPage - 1;
    }

    /*下一页*/
    $scope.getPostPage = function () {
        var currentPage = $scope.biz.page.currentPage;
        var totalPages = $scope.biz.page.totalPages;
        if(currentPage==totalPages){
            return;
        }
        var perPageNum = $scope.biz.page.itemsperpage;
        var startIndex = currentPage*perPageNum;
        var datas=[];
        if(currentPage==totalPages-1){
            datas = biz.dataList.slice(startIndex);
        }else{
            var endIndex = startIndex + perPageNum-1;
            datas = biz.dataList.slice(startIndex,endIndex+1);
        }

        if(biz.totalInfoFlag) {
            biz.queryResult = datas;
        }else{
            biz.detail.stepInfo =datas;
        }
        //alert(JSON.stringify(biz.queryResult));
        biz.page.currentPage = currentPage + 1;
    }

    //展示某笔流水的步骤数据
    $scope.showStepData = function(data){
        $rootScope.data = data;
        var modalInstance = $modal.open({
            templateUrl:'views/biztrace/showStepData.html',
            backdrop:'static'
        });

        $rootScope.cancel = function () {
            modalInstance.close();
        };
    }




});