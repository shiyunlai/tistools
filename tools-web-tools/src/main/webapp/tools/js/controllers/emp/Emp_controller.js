/**
 * Created by gaojie on 2017/6/26.
 */
angular.module('MetronicApp').controller('Emp_controller', function($rootScope,uiGridConstants, $scope,Emp_service,gridUtil,$window, $http, $timeout,i18nService,filterFilter,uiGridConstants,$uibModal,$state) {
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        App.initAjax();
    });
    //定义主题对象
    var emp = {};
    $scope.emp = emp;
    //定义页签控制对象
    var flag = {};
    $scope.flag = flag;
    //默认为员工列表
    flag.yglb = true;
    flag.xqxx = false;
    flag.zzgs = false;
    flag.ryqx = false;
    //详情页编辑状态标识
    var editflag = true;
    $scope.editflag = editflag;
    //页签切换控制,0--列表,1--详情,2--归属,3--权限
    emp.loaddata = function (number) {
        if(number == "0"){
            for(var i in $scope.flag){
                $scope.flag[i] = false;
            }
            $scope.flag.yglb = true;
            //todo
        }else if(number == "1"){
            for(var i in $scope.flag){
                $scope.flag[i] = false;
            }
            $scope.flag.xqxx = true;
            //todo
        }else if(number == "2"){
            for(var i in $scope.flag){
                $scope.flag[i] = false;
            }
            $scope.flag.zzgs = true;
            //todo
        }else if(number == "3"){
            for(var i in $scope.flag){
                $scope.flag[i] = false;
            }
            $scope.flag.ryqx = true;
            //todo
        }
    }
    //设置插件语言为中文
    i18nService.setCurrentLang("zh-cn");
    //生成员工列表信息
    var empgrid = {};
    $scope.empgrid = empgrid;
    //定义单选事件
    var sele = function () {
        
    }
    //定义表头名
    var com = [{ field: 'empCode', displayName: '员工代码', enableHiding: false},
        { field: 'empName', displayName: '员工姓名', enableHiding: false},
        { field: 'gender', displayName: '性别', enableHiding: false},
        { field: 'empstatus', displayName: '员工状态', enableHiding: false},
        { field: 'empDegree', displayName: '员工职级', enableHiding: false},
        { field: 'guidPosition', displayName: '基本岗位', enableHiding: false},
        { field: 'guidempmajor', displayName: '直接主管', enableHiding: false},
        { field: 'indate', displayName: '入职日期', enableHiding: true},
        { field: 'otel', displayName: '办公电话', enableHiding: true}
    ]
    $scope.empgrid = initgrid($scope,empgrid,filterFilter,com,false,sele);

    var reempgrid = function () {
        //调取工作组信息OM_GROUP
        Emp_service.loadempgrid().then(function (data) {
            if(data.status == "success" && data.retMessage.length > 0){
                for(var i = 0;i<data.retMessage.length;i++){
                    data.retMessage[i].birthdate = FormatDate(data.retMessage[i].birthdate);
                    data.retMessage[i].indate = FormatDate(data.retMessage[i].indate);
                    data.retMessage[i].outdate = FormatDate(data.retMessage[i].outdate);
                    data.retMessage[i].regdate = FormatDate(data.retMessage[i].regdate);
                    data.retMessage[i].lastmodytime = FormatDate(data.retMessage[i].lastmodytime);
                    data.retMessage[i].createtime = FormatDate(data.retMessage[i].createtime);
                }
                $scope.empgrid.data = data.retMessage;
                $scope.empgrid.mydefalutData = data.retMessage;
                $scope.empgrid.getPage(1,$scope.empgrid.paginationPageSize);
                var c = $('#test').width();
                console.log(c)
            }else{

            }

        })
    }
    //拉取列表
    reempgrid();
    //定义放置一个员工详情的对象
    var item = {};
    $scope.item = {};
    //返回详情页按钮事件
    emp.back = function () {
        for(var i in $scope.flag){
            $scope.flag[i] = false;
        }
        $scope.flag.yglb = true;
    }
    //新增
    emp.add = function () {
        openwindow($uibModal, 'views/Emp/addemp_window.html', 'lg',
            function ($scope, $modalInstance) {
                //创建员工实例
                var subFrom = {"empCode":"00001","empName":"第一个员工","gender":"M","empDegree":"P1","guidOrg":"ORG1500343061","guidPosition":"POSITION1500362374","indate":"2017-07-19"};
                $scope.subFrom = subFrom;
                //标题
                $scope.title = "新增员工";
                //增加方法
                $scope.add = function (subFrom) {
                    console.log(subFrom)
                    Emp_service.addemp(subFrom).then(function (data) {
                        if(data.status == "success"){
                            toastr['success'](data.retMessage);
                        }else{
                            toastr['error'](data.retMessage);
                        }
                        reempgrid();
                        $scope.cancel();

                    })
                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }
    //详情按钮事件
    emp.detail = function () {
        var arr = $scope.empgrid.getSelectedRows();
        $scope.item = arr[0];
        if(isNull($scope.item)){
            toastr['error']( "请选择一条记录！");
            return false;
        }
        for(var i in $scope.flag){
            $scope.flag[i] = false;
        }
        $scope.flag.xqxx = true;
        $scope.editflag = true;
        $scope.title = "员工详情信息"
    }
    //编辑按钮事件
    emp.edit = function () {
        var arr = $scope.empgrid.getSelectedRows();
        if(isNull(arr) && arr.length != 1){
            toastr['error']( "请选择一条记录！");
            return false;
        }else{
            for(var i in arr[0]){
                if(arr[0][i] == null){
                    arr[0][i] = "";
                }
            }
            openwindow($uibModal, 'views/Emp/addemp_window.html', 'lg',
                function ($scope, $modalInstance) {
                    //创建员工实例
                    var subFrom = {};
                    $scope.subFrom = subFrom;
                    $scope.subFrom = arr[0];
                    //标题
                    $scope.title = "修改员工信息";
                    //增加方法
                    $scope.add = function (subFrom) {
                        console.log(subFrom)
                        Emp_service.addemp(subFrom).then(function (data) {
                            if(data.status == "success"){
                                toastr['success'](data.retMessage);
                            }else{
                                toastr['error'](data.retMessage);
                            }
                            reempgrid();
                            $scope.cancel();

                        })
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }
        // for(var i in $scope.flag){
        //     $scope.flag[i] = false;
        // }
        // $scope.flag.xqxx = true;
        // $scope.editflag = false;
        // $scope.title = "修改员工信息"
    }
    //员工组织归属按钮事件
    emp.belongorg = function () {
        var arr = $scope.empgrid.getSelectedRows();
        $scope.item = arr[0];
        if(isNull($scope.item)){
            toastr['error']( "请选择一条记录！");
            return false;
        }
        for(var i in $scope.flag){
            $scope.flag[i] = false;
        }
        $scope.flag.zzgs = true;
    }
    //个人权限设置按钮事件
    emp.setqx = function () {
        var arr = $scope.empgrid.getSelectedRows();
        $scope.item = arr[0];
        if(isNull($scope.item)){
            toastr['error']( "请选择一条记录！");
            return false;
        }
        for(var i in $scope.flag){
            $scope.flag[i] = false;
        }
        $scope.flag.ryqx = true;
    }
    //删除人员信息
    emp.deletemp = function () {
        var arr = $scope.empgrid.getSelectedRows();
        if(isNull(arr) && arr.length != 1){
            toastr['error']( "请选择一条记录！");
            return false;
        }else{
            var subFrom = {};
            subFrom.empCode = arr[0].empCode;
            Emp_service.deletemp(subFrom).then(function (data) {
                if(data.status == "success"){
                    toastr['success'](data.retMessage);
                }else{
                    toastr['error'](data.retMessage);
                }
                reempgrid();
            })
        }
    }



    //所属机构列表生成
    var orggrid = {};
    $scope.orggrid = orggrid;
    var initorgdata = function () {
        //调取员工-机构信息OM_EMP_ORG
        //机构名称,机构类型,是否主机构
        return $scope.orgData = [{机构名称: "Moroni", 机构类型: "总行",是否主机构:"是"},
            {机构名称: "Jack", 机构类型: "支行",是否主机构:"否"}];
    }
    //定义单选事件
    var seleorg = function () {

    }
    $scope.orggrid = initgrid($scope,orggrid,initorgdata(),filterFilter,null,false,seleorg);
    //所属岗位列表生成
    var postgrid = {};
    $scope.postgrid = postgrid;
    var initpostdata = function () {
        //调取员工-岗位信息OM_EMP_POSITION
        //岗位名称,岗位类型,是否主岗位
        return $scope.postData = [{岗位名称: "Moroni", 岗位类型: "总行",是否主岗位:"是"},
            {岗位名称: "Jack", 岗位类型: "支行",是否主岗位:"否"}]
    }
    //定义单选事件
    var selepost = function () {

    }
    $scope.postgrid = initgrid($scope,postgrid,initpostdata(),filterFilter,null,false,selepost)

    /**-------------------------------测试table自适应---------------------------*/
    //测试封装
    var list = [];
    list.push($scope.empgrid.columnDefs);
    list.push($scope.empgrid.columnDefs)
    var w = angular.element($window);
    $scope.getWindowDimensions = function () {
        return { 'w': w.width() };
    };
    console.log($scope.getWindowDimensions())
    $scope.$watch($scope.getWindowDimensions, function (newValue, oldValue) {
        var c = $('#test').width();
        console.log(c)
        $scope.windowWidth = newValue.w;
        console.log($scope.windowWidth)
        if(!isNull(c)){
            if(c>1200){
                console.log($scope.empgrid.columnDefs)
                var a = $scope.empgrid.columnDefs
                for(var i=0;i<a.length;i++){
                    a[i].visible = true;
                }

                $scope.empgrid.ref()
            }else if(c<1200){
                var a = $scope.empgrid.columnDefs;
                for(var i=4;i<a.length;i++){
                    a[i].visible = false;
                }
            }
        }

    }, true);
    w.bind('resize', function () {
        $scope.$apply();
    });
    emp.reTest = function () {
        $scope.empgrid.api().core.refresh();
        console.log(123)
    }


});