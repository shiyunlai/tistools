/**
 * Created by HP on 2016/9/19.
 */
MetronicApp.controller('biztraceHandle_controller', function ($filter,$rootScope, $scope, $state, $stateParams, filterFilter,biztraceHandle_service, $modal, $http, $timeout,$interval) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });
    var biz = {};
    $scope.biz = biz;
    biz.isDisable = true;


    //上传文件
    $scope.upload = function () {
        var fd = new FormData();
        //var file = document.querySelector('input[type=file]').files[0];
        //var file = $scope.myFile;
        var file = document.getElementById("uploadFile").files[0];

        //alert(file.size/1024/1024);
        fd.append('file', file);
        $http({
            method:'POST',
            url:manurl + "/BizLogHandleController/upload",
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

    //检验路径是否为空
    $scope.checkPathIsEmpty = function(path){
        if(!isNull(path)){
            biz.isDisable = false;
        }else{
            biz.isDisable = true;
        }
    }

    //显示解析过程
    var resolvingFileList = [];
    $scope.showReslovePanel = function(path){
        $rootScope.resolvedRatio = 0;
        //显示模态框
        var modalInstance = $modal.open({
            templateUrl: 'views/biztrace/showResloveProcess.html',
            backdrop:'static'
        });

        //开始解析
        biztraceHandle_service.resloveBizLogFile(path).then(function (datas) {
        });

        //监听解析进度
        $rootScope.resolverListener = setInterval(biz.startResolverListener, 2000);


        //关闭界面
        $rootScope.cancel = function () {
            if(!biz.resolverProcessFlag){
                return;
            }
            biz.resolverProcessFlag = "false";
            resolvingFileList = [];
            $rootScope.resolvingFileList = [];
            $rootScope.resolvedRatio = 0;
            $rootScope.processBarStyle = {width:"1%"};
            modalInstance.close();
        };
    }

    /*解析监听*/
    biz.startResolverListener = function(){
        biztraceHandle_service.getResolverProcess().then(function (data) {
            //alert(JSON.stringify(data));
            biz.resolverProcessFlag = data.resolverOverFlag;
            if(biz.resolverProcessFlag == true) {
                clearInterval($rootScope.resolverListener);
            }

            resolvingFileList = [];
            var fileParseRecord = data.fileParseRecord;
            for(var path in fileParseRecord){
                var parser = {};
                parser.path = path;
                parser.status = fileParseRecord[path];
                resolvingFileList.push(parser);
            }

            $rootScope.resolvingFileList = resolvingFileList;
            var fileTotalNum = data.fileTotalNum;
            var fileReadNum = data.fileReadNum;
            var resolvedRatio = (fileReadNum/fileTotalNum)*100;
            //if(resolvedRatio < 5){
            //    resolvedRatio = 5;
            //}
            $rootScope.resolvedRatio = resolvedRatio;
            $rootScope.processBarStyle = {width:$rootScope.resolvedRatio+"%"};
        });
    }

    //分析日志
    $scope.analyzer = function(){
        //在每次分析分析日志之前到redis中取出尚未被分析的日志日期的集合
        biztraceHandle_service.getUnanalyzedLogDate().then(function (datas){
            $rootScope.unanalyzedBizLogDates = datas;
        });

        //将取出的日志日期展示到模态框中供用户选择
        var modalInstance = $modal.open({
            templateUrl: 'views/biztrace/selectUnAnalyzed.html',
            backdrop:'static'
        });

        //记录用户选择的要分析的日志日期
        var analyzedLogDateArry = [];
        $rootScope.isChecked = false;
        $rootScope.check = function(isChecked,logdate){
            if(isChecked){
                analyzedLogDateArry.push(logdate);
            }else{
                //analyzedLogDateArry.pop(logdate);
                for(var i=0;i<analyzedLogDateArry.length;i++){
                    if(analyzedLogDateArry[i] == logdate){
                        analyzedLogDateArry.splice(i,1);
                        break;
                    }
                }
            }
        }

        //选择确认执行日志分析
        $rootScope.ok = function () {
            if(analyzedLogDateArry.length==0){
                alert("please select date which will be analyzed");
                return;
            }
            biztraceHandle_service.analyzeBizLog(analyzedLogDateArry).then(function (datas){
                alert(datas.status);
            });
            //modalInstance.close();
        };

        //选择取消清空用户选择的结果关闭界面
        $rootScope.cancel = function () {
            modalInstance.close();
        };
    }
});