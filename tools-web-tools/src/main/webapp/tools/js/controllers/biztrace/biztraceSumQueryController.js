/**
 * Created by HP on 2016/9/13.
 */

MetronicApp.controller('biztraceSumQuery_controller', function ($filter,$rootScope, $scope, $state, $stateParams, filterFilter,biztraceSum_service, $modal, $http, $timeout,$interval) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });
    var biz = {};
    $scope.biz = biz;

    var page = {};
    page.itemsperpage = 10;
    biz.page = page;



    //按条件查询
    $scope.querySumInfo = function (trans_date_start,trans_date_end) {
        if(!trans_date_start){
            alert("trans date start can not null!");
            return;
        }
        if(!trans_date_end){
            trans_date_end = trans_date_start;
        }

        var transDateStart = $filter('date')(trans_date_start, 'yyyy-MM-dd');
        var transDateEnd = $filter('date')(trans_date_end, 'yyyy-MM-dd');

        if(transDateStart>transDateEnd){
            alert("date end should be later than start!");
            return;
        }
        biztraceSum_service.querySumInfo(transDateStart,transDateEnd).then(function (datas) {
            //alert(JSON.stringify(datas));
            biz.dataList =datas;
            pagination(datas);
        });
    }

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
        biz.queryResult = queryResult;

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

        biz.queryResult = biz.dataList.slice(startIndex, endIndex + 1);

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

        if(currentPage==totalPages-1){
            biz.queryResult = biz.dataList.slice(startIndex);
        }else{
            var endIndex = startIndex + perPageNum-1;
            biz.queryResult = biz.dataList.slice(startIndex,endIndex+1);
        }

        //alert(JSON.stringify(biz.queryResult));
        biz.page.currentPage = currentPage + 1;
    }

});