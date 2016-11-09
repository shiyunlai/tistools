/**
 * Created by HP on 2016/9/13.
 */

MetronicApp.controller('redisClean_controller', function ($filter,$rootScope, $scope, $state, $stateParams, filterFilter,redisClean_service, $modal, $http, $timeout,$interval) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });
    var redis = {};
    $scope.redis = redis;

    redisClean_service.getRedisSpaceUsage().then(function (datas) {
        //alert(JSON.stringify(datas));
        $scope.redis.spaceUsage = datas.redisSpace;
    });

    $scope.redisClean = function () {
        redisClean_service.getDateList().then(function (datas) {
            //alert(JSON.stringify(datas));
            $rootScope.cleanDateList = datas;
        });

        var modalInstance = $modal.open({
            templateUrl:'views/biztrace/showRedisClean.html',
            backdrop:'static'
        });

        //记录用户选择的要清理的日志日期
        var selectedLogDateArry = [];
        $rootScope.isChecked = false;
        $rootScope.check = function(isChecked,logdate){
            if(isChecked){
                selectedLogDateArry.push(logdate);
            }else{
                //selectedLogDateArry.pop(logdate);
                for(var i=0;i<selectedLogDateArry.length;i++){
                    if(selectedLogDateArry[i] == logdate){
                        selectedLogDateArry.splice(i,1);
                        break;
                    }
                }
            }
        }

        //选择确认执行清理
        $rootScope.ok = function () {
            if(selectedLogDateArry.length==0){
                alert("please select date which will be cleaned");
                return;
            }
            redisClean_service.doClean(selectedLogDateArry).then(function (datas){
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