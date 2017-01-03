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
    var resolvingFileList = [];
    $rootScope.resolvingFileList = resolvingFileList;
    //新增code
    var fuwu = [];
    $scope.reloadga = function () {
        fuwu.splice(0, fuwu.length);
        $scope.fuwu = fuwu;
        biztraceHandle_service.loadfuwu().then(function (datas) {
            var a = angular.fromJson(datas.message);
            if (a[0].agents == undefined) {
                fuwu.push({"serviceName": "当前没有服务"});
                $scope.fuwu = fuwu;
                return;
            }
            for (var i = 0; i < a.length; i++) {
                fuwu.push(a[0].agents[i]);
            }
            $scope.fuwu = fuwu;
        });
    }
    //初始化服务器列表
    $scope.reloadga();
    //初始化日志列表
    $scope.load = function (service) {
        var tb = {};
        //alert(JSON.stringify(service));
        tb = service;
        $scope.tb = tb;
        biztraceHandle_service.loadfile(service).then(function (datas) {
            //alert(JSON.stringify(datas.message));
            var a = angular.fromJson(datas.message);
            //alert(JSON.stringify(a[0].logFiles));
            biz.dataList = a[0].logFiles;
            var b = 0;
            for (var i = 0; i < biz.dataList.length; i++) {
                b = b + biz.dataList[i].fileSize;
            }
            tb.num = biz.dataList.length;
            tb.size = b + "KB";
            $scope.tb = tb;
            $scope.biz.dataList = biz.dataList;
        });
    }
    //公共初始化方法的一部分
    biz.checkAll = function (headcheck) {
        if (!headcheck) {
            if (biz.dataList != undefined) {
                for (var i = 0; i < biz.dataList.length; i++) {
                    biz.dataList[i].checked = true;
                }
            }
        } else {
            if (biz.dataList != undefined) {
                for (var i = 0; i < biz.dataList.length; i++) {
                    if (biz.dataList[i] != null) {
                        biz.dataList[i].checked = false;
                    }
                }
            }

        }
    }
    biz.getSelectItems = function () {
        var res = filterFilter(biz.dataList, function (record) {
            return record.checked;
        });
        return res;
    }
    //点击分析,将日志送往redis
    $scope.fenxi = function (service) {
        var sel = biz.getSelectItems();
        if (sel.length > 0) {
            if (confirm("确认开始分析选中的日志？")) {
                alert(JSON.stringify(sel));
                var jsondata = {};
                jsondata.type = "part";
                jsondata.logs = [];
                jsondata.port = service;
                for (var i = 0; i < sel.length; i++) {
                    jsondata.logs.push(sel[i].fileName);
                }
                //初始化模态框数据

                $rootScope.resolvingFileList = sel;

                //显示模态框
                var modalInstance = $modal.open({
                    templateUrl: 'views/biztrace/showResloveProcess.html',
                    backdrop: 'static'
                });
                //监听解析进度
                biz.startResolverListener = function(){
                    biztraceHandle_service.getProcess(service).then(function (data) {
                        var a = angular.fromJson(data.message);
                        //alert(JSON.stringify(a));

                         $rootScope.resolvedRatio = a[0].process.parsedProcess;
                        biz.resolverProcessFlag = a[0].process.parsing;
                        if(biz.resolverProcessFlag == true) {
                        clearInterval($rootScope.resolverListener);
                        }})};
                $rootScope.resolverListener = setInterval(biz.startResolverListener,1000);
                $rootScope.processBarStyle = {width:$rootScope.resolvedRatio+"%"};

                //关闭界面
                $rootScope.cancel = function () {
                    //if(!biz.resolverProcessFlag){
                    //    return;
                    //}
                    //biz.resolverProcessFlag = "false";
                    //resolvingFileList = [];
                    //$rootScope.resolvingFileList = [];
                    //$rootScope.resolvedRatio = 0;
                    //$rootScope.processBarStyle = {width: "1%"};
                    clearInterval($rootScope.resolverListener);
                    modalInstance.close();
                };
                //alert(JSON.stringify(jsondata));
                //开始解析
                biztraceHandle_service.analyzeLog(jsondata).then(function(datas){

                });
                //biztraceHandle_service.getProcess(service).then(function(datas){
                //    alert(JSON.stringify(datas.message));
                //});

            }
        }
    };
    });


    //上传文件
//    $scope.upload = function () {
//        var fd = new FormData();
//        //var file = document.querySelector('input[type=file]').files[0];
//        //var file = $scope.myFile;
//        var file = document.getElementById("uploadFile").files[0];
//
//        //alert(file.size/1024/1024);
//        fd.append('file', file);
//        $http({
//            method:'POST',
//            url:manurl + "/BizLogHandleController/upload",
//            data: fd,
//            headers: {'Content-Type':undefined},
//            transformRequest: angular.identity,
//            eventHandlers: {
//                progress: function(c) {
//                    console.log('Progress -> ' + c);
//                    console.log(c);
//                }
//            },
//            uploadEventHandlers: {
//                progress: function(e) {
//                    console.log('UploadProgress -> ' + e);
//                    console.log(e);
//                }
//            }
//        }).success( function ( response ) {
//            //上传成功的操作
//            alert("上传成功!");
//        });
//    };
//
//    //检验路径是否为空
//    $scope.checkPathIsEmpty = function(path){
//        if(!isNull(path)){
//            biz.isDisable = false;
//        }else{
//            biz.isDisable = true;
//        }
//    }
//
//    //显示解析过程
//    var resolvingFileList = [];
//    $scope.showReslovePanel = function(path){
//        $rootScope.resolvedRatio = 0;
//        //显示模态框
//        var modalInstance = $modal.open({
//            templateUrl: 'views/biztrace/showResloveProcess.html',
//            backdrop:'static'
//        });
//
//        //开始解析
//        biztraceHandle_service.resloveBizLogFile(path).then(function (datas) {
//        });
//
//        //监听解析进度
//        $rootScope.resolverListener = setInterval(biz.startResolverListener, 2000);
//
//
//        //关闭界面
//        $rootScope.cancel = function () {
//            if(!biz.resolverProcessFlag){
//                return;
//            }
//            biz.resolverProcessFlag = "false";
//            resolvingFileList = [];
//            $rootScope.resolvingFileList = [];
//            $rootScope.resolvedRatio = 0;
//            $rootScope.processBarStyle = {width:"1%"};
//            modalInstance.close();
//        };
//    }
//
//    /*解析监听*/
//    biz.startResolverListener = function(){
//        biztraceHandle_service.getResolverProcess().then(function (data) {
//            //alert(JSON.stringify(data));
//            biz.resolverProcessFlag = data.resolverOverFlag;
//            if(biz.resolverProcessFlag == true) {
//                clearInterval($rootScope.resolverListener);
//        }
//
//            resolvingFileList = [];
//            var fileParseRecord = data.fileParseRecord;
//            for(var path in fileParseRecord){
//                var parser = {};
//                parser.path = path;
//                parser.status = fileParseRecord[path];
//                resolvingFileList.push(parser);
//            }
//
//            $rootScope.resolvingFileList = resolvingFileList;
//            var fileTotalNum = data.fileTotalNum;
//            var fileReadNum = data.fileReadNum;
//            var resolvedRatio = (fileReadNum/fileTotalNum)*100;
//            //if(resolvedRatio < 5){
//            //    resolvedRatio = 5;
//            //}
//            $rootScope.resolvedRatio = resolvedRatio;
//            $rootScope.processBarStyle = {width:$rootScope.resolvedRatio+"%"};
//        });
//    }
//
//    //分析日志
//    $scope.analyzer = function(){
//        //在每次分析分析日志之前到redis中取出尚未被分析的日志日期的集合
//        biztraceHandle_service.getUnanalyzedLogDate().then(function (datas){
//            $rootScope.unanalyzedBizLogDates = datas;
//        });
//
//        //将取出的日志日期展示到模态框中供用户选择
//        var modalInstance = $modal.open({
//            templateUrl: 'views/biztrace/selectUnAnalyzed.html',
//            backdrop:'static'
//        });
//
//        //记录用户选择的要分析的日志日期
//        var analyzedLogDateArry = [];
//        $rootScope.isChecked = false;
//        $rootScope.check = function(isChecked,logdate){
//            if(isChecked){
//                analyzedLogDateArry.push(logdate);
//            }else{
//                //analyzedLogDateArry.pop(logdate);
//                for(var i=0;i<analyzedLogDateArry.length;i++){
//                    if(analyzedLogDateArry[i] == logdate){
//                        analyzedLogDateArry.splice(i,1);
//                        break;
//                    }
//                }
//            }
//        }
//
//        //选择确认执行日志分析
//        $rootScope.ok = function () {
//            if(analyzedLogDateArry.length==0){
//                alert("please select date which will be analyzed");
//                return;
//            }
//            biztraceHandle_service.analyzeBizLog(analyzedLogDateArry).then(function (datas){
//                alert(datas.status);
//            });
//            //modalInstance.close();
//        };
//
//        //选择取消清空用户选择的结果关闭界面
//        $rootScope.cancel = function () {
//            modalInstance.close();
//        };
//    }
//});