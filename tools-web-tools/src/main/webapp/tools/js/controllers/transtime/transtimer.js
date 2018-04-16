/**
 * Created by hasee on 2017/9/5.
 */


/*定时器管理控制器*/
MetronicApp.controller('transtime_controller', function ($rootScope, $scope, $state, $stateParams, filterFilter,$modal,$uibModal, $http, $timeout,$interval,i18nService) {
    var transtime = {};
    $scope.transtime = transtime;
    //grid表格
    i18nService.setCurrentLang("zh-cn");
    //生成表格
    var gridOptions = {};
    $scope.gridOptions = gridOptions;
    var myDates = [
        {taskName:'任务一',taskstatus:'启动',tasktype:'定时'},
        {taskName:'任务二',taskstatus:'启动',tasktype:'定时'},
        {taskName:'任务三',taskstatus:'启动',tasktype:'定时'},
        {taskName:'任务四',taskstatus:'启动',tasktype:'定时'},
        {taskName:'任务五',taskstatus:'定时',tasktype:'挂起'}
    ]
    var com = [
        { field: 'taskName', displayName: '任务名称'},
        { field: 'taskstatus', displayName: '任务状态'},
        { field: "tasktype", displayName:'任务类型'}
    ];
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
        }else{
            delete $scope.selectRow;//制空
        }
    }
    $scope.gridOptions = initgrid($scope,gridOptions,filterFilter,com,false,f);
    $scope.gridOptions.data = myDates;

    //新增定时器
    $scope.transtime.add = function() {
        openwindow($uibModal, 'views/transtimes/transtimeerAdd.html', 'lg',
            function ($scope, $modalInstance) {
                var transtime = {};
                $scope.transtime = transtime;
                //循环，渲染数据
                var selectDay =[];
                var selectmonth = [];
                $scope.selectDay = selectDay;
                $scope.selectmonth = selectmonth;
                for (var x = 1; x < 32; x++) {
                    $scope.selectDay.push({name: x + "日", value: x});
                }
                $scope.selectweek = [{name: "星期日", value: 1}, {name: "星期一", value: 2}, {name: "星期二", value: 3}, {name: "星期三", value: 4}, {name: "星期四", value: 5},{name: "星期五", value: 6}, {name: "星期六", value: 7}];
                $scope.selectweekth = [{name: "第一个", value: 1}, {name: "第二个", value: 2}, {name: "第三个", value: 3}, {name: "第四个", value: 4}];
                for(var x = 1; x<13; x++){
                    $scope.selectmonth.push({name:x + "月",value:x});
                }

                //数据处理,在难都自己写
                $scope.chackStartDate = function (beginTime) {
                    if (!isNull(beginTime)) {
                        if (!isNull($scope.transtime.endTime)) {
                            if (!comparisonTimeDateNSY(beginTime, $scope.transtime.endTime)) {
                                $scope.transtime.beginTime = '';
                                toastr['error']("开始时间不能大于结束时间！", "SORRY！")
                            }
                        }
                    }
                };

                $scope.chackEndDate = function (endTime) {
                    if(!isNull(endTime)){
                        if(!isNull($scope.transtime.beginTime)){
                            if(!comparisonTimeDateNSY($scope.transtime.beginTime,endTime)){
                                $scope.transtime.endTime = '';
                                toastr['error']("结束时间不能小于开始时间！", "SORRY！")
                            }
                        }
                    }
                };

                var transtime = {};
                $scope.transtime  =transtime;
                var str = '2017-09-22 23:50:00'
                $scope.transtime.hsmScope =  new Date(str);//初始化开始时间。
                //定义定时器
                $scope.vm = {
                    hstep : 1,
                    mstep : 15,
                };

                //监视每日/每周/每月/每年，如果每次都不一样，那就清空原来的记录
                $scope.$watch('transtime.circleType',function(newValue,oldValue){
                    if(newValue !== oldValue){
                        cleanConf();
                    }
                });
                //监视月，如果每月的input选择不同，也清空所有
                $scope.$watch('transtime.monthone',function(newValue,oldValue){
                    if(newValue !== oldValue){
                        cleanConf();
                    }
                });
                //监视年，如果每年的input选择不同，也清空所有
                $scope.$watch('transtime.Yearone',function(newValue,oldValue){
                    if(newValue !== oldValue){
                        cleanConf();
                    }
                });
                function cleanConf(){
                    $scope.transtime.overweek = [];//清空每周
                    $scope.transtime.monthers = '';//每月月份清空
                    $scope.transtime.weekers = '';//每月第几周清空
                    $scope.transtime.daters = '';//每月第几周第几天清空
                    //每年清空
                    $scope.transtime.Yearmonth = '';//每年第几月清空
                    $scope.transtime.Yearweek = '';//每年第几月第几天清空

                    $scope.transtime.Yearmontht = '';//每年第几月清空
                    $scope.transtime.Yearweekt = '';//每年第几月第几个周清空
                    $scope.transtime.Yeardayt = '';//每年第几月第几周第几天清空
                }
                $scope.add = function (item) {
                    var str = item.hsmScope;
                    var lt = moment(str).format('YY-MM-DD  HH:mm:ss ');
                    console.log(lt);//转换成功,提交到后台即可
                    toastr['success']("新增成功！");
                    console.log(item);

                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }


    //编辑定时器
    $scope.transtime.edit = function(){
        var getSel = $scope.gridOptions.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据进行修改！");
        }else{
            openwindow($uibModal, 'views/transtimes/transtimeerAdd.html', 'lg',
                function ($scope, $modalInstance) {
                    var ids ='4';
                    $scope.id = ids;
                    var transtime = {};
                    $scope.transtime = transtime;
                    //循环，渲染数据
                    var selectDay =[];
                    var selectmonth = [];
                    $scope.selectDay = selectDay;
                    $scope.selectmonth = selectmonth;
                    for (var x = 1; x < 32; x++) {
                        $scope.selectDay.push({name: x + "日", value: x});
                    }
                    $scope.selectweek = [{name: "星期日", value: 1}, {name: "星期一", value: 2}, {name: "星期二", value: 3}, {name: "星期三", value: 4}, {name: "星期四", value: 5},{name: "星期五", value: 6}, {name: "星期六", value: 7}];
                    $scope.selectweekth = [{name: "第一个", value: 1}, {name: "第二个", value: 2}, {name: "第三个", value: 3}, {name: "第四个", value: 4}];
                    for(var x = 1; x<13; x++){
                        $scope.selectmonth.push({name:x + "月",value:x});
                    }

                    //数据处理,在难都自己写
                    $scope.chackStartDate = function (beginTime) {
                        if (!isNull(beginTime)) {
                            if (!isNull($scope.transtime.endTime)) {
                                if (!comparisonTimeDateNSY(beginTime, $scope.transtime.endTime)) {
                                    $scope.transtime.beginTime = '';
                                    toastr['error']("开始时间不能大于结束时间！", "SORRY！")
                                }
                            }
                        }
                    };

                    $scope.chackEndDate = function (endTime) {
                        if(!isNull(endTime)){
                            if(!isNull($scope.transtime.beginTime)){
                                if(!comparisonTimeDateNSY($scope.transtime.beginTime,endTime)){
                                    $scope.transtime.endTime = '';
                                    toastr['error']("结束时间不能小于开始时间！", "SORRY！")
                                }
                            }
                        }
                    };

                    //监视每日/每周/每月/每年，如果每次都不一样，那就清空原来的记录
                    $scope.$watch('transtime.circleType',function(newValue,oldValue){
                        if(newValue !== oldValue){
                            cleanConf();
                        }
                    });
                    //监视月，如果每月的input选择不同，也清空所有
                    $scope.$watch('transtime.monthone',function(newValue,oldValue){
                        if(newValue !== oldValue){
                            cleanConf();
                        }
                    });
                    //监视年，如果每年的input选择不同，也清空所有
                    $scope.$watch('transtime.Yearone',function(newValue,oldValue){
                        if(newValue !== oldValue){
                            cleanConf();
                        }
                    });
                    function cleanConf(){
                        $scope.transtime.overweek = [];//清空每周
                        $scope.transtime.monthers = '';//每月月份清空
                        $scope.transtime.weekers = '';//每月第几周清空
                        $scope.transtime.daters = '';//每月第几周第几天清空
                        //每年清空
                        $scope.transtime.Yearmonth = '';//每年第几月清空
                        $scope.transtime.Yearweek = '';//每年第几月第几天清空

                        $scope.transtime.Yearmontht = '';//每年第几月清空
                        $scope.transtime.Yearweekt = '';//每年第几月第几个周清空
                        $scope.transtime.Yeardayt = '';//每年第几月第几周第几天清空
                    }
                    $scope.add = function (item) {
                        toastr['success']("修改成功！");
                    }

                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }

    }

    //删除定时器
    $scope.transtime.del = function() {
        var getSel = $scope.gridOptions.getSelectedRows();
        if (getSel.length = 0) {
            toastr['error']("请至少选择一条数据进行删除！");
        } else {
            if (confirm('确定删除定时器吗')) {
                toastr['error']("删除成功！");
            }
        }
    }








    //测试开始
    //1.初始化数据
/*    var model = [[[{
        relation: '',//关系
        field: '',//字段
        logical: '',//逻辑
        condition: '',//条件
    }],{connecter: ''}//连接器
    ]];
    $scope.model = model;*/



/*

    //新增方法
    $scope.handlePlusClick = function (index, i) {
        console.log(this.model[index][0][i].relation);
        if(this.model[index][0].length >= 2 && !this.model[index][0][i].relation) {
            alert('逻辑连接符不选，添加尼玛添加啊！！！')
            return;
        }
        this.model[index][0].push(this.initModelItem(index)[0][0]);
        console.log(this.model);
        alert('添加成功')
    }
    
    //删除方法
    $scope.handleMinusClick = function (index, i) {
        console.log(this.model[index][0]);
        if (i === 0 && this.model[index][0].length === 1) {
            alert('老铁，最后一项了，删不了了')
            return;
        }
        this.model[index][0].splice(i, 1);
        alert("删除成功")
    }

    //增加一层方法
    $scope.handleAddLine = function (index) {
        console.log(index)
        if(!this.model[index][1].connecter) {
            alert('逻辑连接符不选，添加尼玛添加啊！！！')
            return;
        }
        var initModelItem = function (index) {
            return [[
                {
                    relation: '',
                    field: '',
                    logical: '',
                    condition: ''
                },
            ],{connecter: ''}]
        }
        this.model.splice(index + 1, 0, initModelItem(index));
        this.$nextTick(() => {
            alert("添加成功")
    })
    }


    //删除一层方法
    $scope.handleMinusLine = function (index) {
        if(this.model.length === 1) {
            alert("老铁，最后一项了，删不了了")
            return;
        }
        this.model.splice(index, 1);
        this.$nextTick(() => {
            alert("删除成功")
        })
    }

*/


});




















