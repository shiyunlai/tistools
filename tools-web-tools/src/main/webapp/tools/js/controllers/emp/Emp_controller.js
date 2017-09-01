/**
 * Created by gaojie on 2017/6/26.
 */
angular.module('MetronicApp').controller('Emp_controller', function ($rootScope, uiGridConstants, $scope, Emp_service, gridUtil, $window, $http, $timeout, i18nService, filterFilter, uiGridConstants, $uibModal, $state) {
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
    //放置选择的机构GUID
    var selectOrgGuid = "";
    $scope.selectOrgGuid = selectOrgGuid;
    //放置选择的岗位GUID
    var selectPosGuid = "";
    $scope.selectPosGuid = selectPosGuid;
    //放置选择的机构CODE
    var selectOrgCode = "";
    $scope.selectOrgCode = selectOrgCode;
    //放置选择的岗位CODE
    var selectPosCode = "";
    $scope.selectPosCode = selectPosCode;
    //控制按钮
    var posQx = true;
    $scope.posQx = posQx;
    var posZp = true;
    $scope.posZp = posZp;
    var orgQx = true;
    $scope.orgQx = orgQx;
    var orgZp = true;
    $scope.orgZp = orgZp;
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
        if (number == "0") {
            for (var i in $scope.flag) {
                $scope.flag[i] = false;
            }
            $scope.flag.yglb = true;
            //todo
        } else if (number == "1") {
            for (var i in $scope.flag) {
                $scope.flag[i] = false;
            }
            $scope.flag.xqxx = true;
            //todo
        } else if (number == "2") {
            for (var i in $scope.flag) {
                $scope.flag[i] = false;
            }
            $scope.flag.zzgs = true;
            //todo
        } else if (number == "3") {
            for (var i in $scope.flag) {
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
    var com = [{field: 'empCode', displayName: '员工代码'},
        {field: 'empName', displayName: '员工姓名'},
        {field: 'gender', displayName: '性别',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.gender | translateConstants :\'DICT_OM_GENDER\') + $root.constant[\'DICT_OM_GENDER-\'+row.entity.gender]}}</div>'},
        {field: 'empstatus', displayName: '员工状态',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.empstatus | translateConstants :\'DICT_OM_EMPSTATUS\') + $root.constant[\'DICT_OM_EMPSTATUS-\'+row.entity.empstatus]}}</div>'},
        {field: 'empDegree', displayName: '员工职级',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.empDegree | translateConstants :\'DICT_OM_EMPDEGREE\') + $root.constant[\'DICT_OM_EMPDEGREE-\'+row.entity.empDegree]}}</div>'},
        {field: 'guidPosition', displayName: '基本岗位',cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidPosition | translatePosition) + $root.constant[row.entity.guidPosition]}}</div>'},
        {field: 'guidempmajor', displayName: '直接主管'},
        {field: 'guidOrg', displayName: '主机构', enableHiding: true,cellTemplate: '<div  class="ui-grid-cell-contents" title="TOOLTIP">{{(row.entity.guidOrg | translateOrg) + $root.constant[row.entity.guidOrg]}}</div>'},
        {field: 'otel', displayName: '办公电话', enableHiding: true}
    ]

    $scope.empgrid = initgrid($scope, empgrid, filterFilter, com, false, sele);
    // $scope.empgrid.columnDefs[3].cellFilter = 'translateConstants:"DICT_OM_EMPSTATUS":$scope'
    var reempgrid = function () {
        //调取工作组信息OM_GROUP
        Emp_service.loadempgrid().then(function (data) {
            if (data.status == "success" && data.retMessage.length > 0) {
                $scope.empgrid.data = data.retMessage;
                $scope.empgrid.mydefalutData = data.retMessage;
                $scope.empgrid.getPage(1, $scope.empgrid.paginationPageSize);
            } else {
                $scope.empgrid.data = [];
                $scope.empgrid.mydefalutData = [];
                $scope.empgrid.getPage(1, $scope.empgrid.paginationPageSize);
            }

        })
    }
    // var subFrom = {};
    // subFrom.dictKey = dictKey;
    // $http.post("http://localhost:8089/tis/DictController/queryAllDictItem",subFrom).then(function (data) {
    //     console.log(data)
    //     $scope.dataList = data.data.retMessage
    // })
    $scope.formatLocation = function (a,b) {
        var list = $rootScope.$settings.diclist[b];
        console.log(list)
        return 123;
    }


    //拉取列表
    reempgrid();
    //定义放置一个员工详情的对象
    var item = {};
    $scope.item = {};
    //返回详情页按钮事件
    emp.back = function () {
        for (var i in $scope.flag) {
            $scope.flag[i] = false;
        }
        $scope.flag.yglb = true;
    }
    //新增
    emp.add = function () {
        openwindow($uibModal, 'views/emp/addemp_window.html', 'lg',
            function ($scope, $modalInstance) {
                var next = true;
                $scope.next = next;
                //创建员工实例
                var subFrom = {
                    "empName": "第一个员工",
                    "gender": "M",
                    "empDegree": "P1",
                    "guidOrg": "ORG1502868449",
                    "guidPosition": "POSITION1502869376",
                    "indate": "2017-07-19"
                };
                //生成员工编号
                Emp_service.initEmpCode(subFrom).then(function (data) {
                    if (data.status == "success") {
                        subFrom.empCode = data.retMessage
                    } else {
                        toastr['error'](data.retMessage);
                    }
                })
                $scope.subFrom = subFrom;
                //标题
                $scope.title = "新增员工";
                //增加方法
                $scope.add = function (subFrom) {
                    console.log(subFrom)
                    Emp_service.addemp(subFrom).then(function (data) {
                        if (data.status == "success") {
                            toastr['success'](data.retMessage);
                        } else {
                            toastr['error'](data.retMessage);
                        }
                        reempgrid();
                        $scope.cancel();

                    })
                }

                $scope.shownext = function () {
                    $scope.next = !$scope.next;
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
        if (isNull($scope.item)) {
            toastr['error']("请选择一条记录！");
            return false;
        }
        for (var i in $scope.flag) {
            $scope.flag[i] = false;
        }
        $scope.flag.xqxx = true;
        $scope.editflag = true;
        $scope.title = "员工详情信息"
    }
    //编辑按钮事件
    emp.edit = function () {
        var arr = $scope.empgrid.getSelectedRows();
        if (isNull(arr) && arr.length != 1) {
            toastr['error']("请选择一条记录！");
            return false;
        } else {
            for (var i in arr[0]) {
                if (arr[0][i] == null) {
                    arr[0][i] = "";
                }
            }
            openwindow($uibModal, 'views/emp/addemp_window.html', 'lg',
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
                            if (data.status == "success") {
                                toastr['success'](data.retMessage);
                            } else {
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
        if (isNull($scope.item)) {
            toastr['error']("请选择一条记录！");
            return false;
        }
        for (var i in $scope.flag) {
            $scope.flag[i] = false;
        }
        $scope.flag.zzgs = true;
        reorggrid();
        repostgrid();
    }
    //个人权限设置按钮事件
    emp.setqx = function () {
        var arr = $scope.empgrid.getSelectedRows();
        $scope.item = arr[0];
        if (isNull($scope.item)) {
            toastr['error']("请选择一条记录！");
            return false;
        }
        for (var i in $scope.flag) {
            $scope.flag[i] = false;
        }
        $scope.flag.ryqx = true;
    }
    //删除人员信息
    emp.deletemp = function () {
        var arr = $scope.empgrid.getSelectedRows();
        if (isNull(arr) && arr.length != 1) {
            toastr['error']("请选择一条记录！");
            return false;
        } else {
            var subFrom = {};
            subFrom.empCode = arr[0].empCode;
            Emp_service.deletemp(subFrom).then(function (data) {
                if (data.status == "success") {
                    toastr['success'](data.retMessage);
                } else {
                    toastr['error'](data.retMessage);
                }
                reempgrid();
            })
        }
    }


    /**-------------------------------生成各类列表-------------------------------*/
        //所属机构列表生成
    var orggrid = {};
    $scope.orggrid = orggrid;
    //定义表头名
    var com = [
        {field: 'orgName', displayName: '所属机构'}
    ]
    $scope.orggrid = initgrid($scope, orggrid, filterFilter, com, false, function (a) {
        console.log(a)
        //roleGuid
        if(a.isSelected){
            $scope.selectOrgGuid = a.entity.guid;
            $scope.selectOrgCode = a.entity.orgCode;
            $scope.orgQx = false;
            if(a.entity.orgName.substr(a.entity.orgName.length-3,this.length) != "(主)"){
                $scope.orgZp = false;
            }else{
                $scope.orgZp = true;
            }
        }else{
            $scope.selectOrgGuid = "";
            $scope.selectOrgCode = "";
            $scope.orgQx = true;
            $scope.orgZp = true;
        }
    });
    $scope.orggrid.enableFiltering = false;
    $scope.orggrid.enableGridMenu = false;
    $scope.orggrid.enablePaginationControls = false;


    //所属岗位列表生成
    var postgrid = {};
    $scope.postgrid = postgrid;
    var com1 = [
        {field: 'positionName', displayName: '所属岗位'}
    ];
    $scope.postgrid = initgrid($scope, postgrid, filterFilter, com1, false, function (a) {
        //roleGuid
        if(a.isSelected){
            $scope.selectPosGuid = a.entity.guid;
            $scope.selectPosCode = a.entity.positionCode;
            $scope.posQx = false;
            if(a.entity.positionName.substr(a.entity.positionName.length-3,this.length) != "(主)"){
                $scope.posZp = false;
            }else{
                $scope.posZp = true;
            }
        }else{
            $scope.selectPosGuid = "";
            $scope.selectPosCode = "";
            $scope.posQx = true;
            $scope.posZp = true;
        }
    });
    $scope.postgrid.enableFiltering = false;
    $scope.postgrid.enableGridMenu = false;
    $scope.postgrid.enablePaginationControls = false;
    /**-------------------------------各类列表数据拉取方法-------------------------------*/
        //所属机构列表拉取
    var reorggrid = function () {
            var subFrom = {};
            subFrom.empCode = $scope.item.empCode;
            Emp_service.loadEmpOrg(subFrom).then(function (data) {
                console.log(data)
                if (data.status == "success" && !isNull(data.retMessage)) {
                    $scope.orggrid.data = data.retMessage;
                    $scope.orggrid.mydefalutData = data.retMessage;
                    $scope.orggrid.getPage(1, $scope.orggrid.paginationPageSize);
                } else {
                    $scope.orggrid.data = [];
                    $scope.orggrid.mydefalutData = [];
                    $scope.orggrid.getPage(1, $scope.orggrid.paginationPageSize);
                }
            })
        }
    //所属岗位列表
    var repostgrid = function () {
        var subFrom = {};
        subFrom.empCode = $scope.item.empCode;
        Emp_service.loadEmpPos(subFrom).then(function (data) {
            if (data.status == "success" && !isNull(data.retMessage)) {
                $scope.postgrid.data = data.retMessage;
                $scope.postgrid.mydefalutData = data.retMessage;
                $scope.postgrid.getPage(1, $scope.postgrid.paginationPageSize);
            } else {
                $scope.postgrid.data = [];
                $scope.postgrid.mydefalutData = [];
                $scope.postgrid.getPage(1, $scope.postgrid.paginationPageSize);
            }
        })
    }
    /**-------------------------------各类按钮事件---------------------------*/
    //组织所属下,指派机构
    emp.assignOrg = function () {
        var empCode = $scope.item.empCode;
        openwindow($uibModal, 'views/org/addsearhgrid_window.html', 'lg',
            function ($scope, $modalInstance) {
                $scope.title = "指派新机构";
                var commonGrid = {};
                $scope.commonGrid = commonGrid;
                //定义单选事件
                var selework = function () {

                }
                //定义表头名
                var com = [{field: 'orgCode', displayName: '机构代码'},
                    {field: 'orgName', displayName: '机构名称'},
                    {field: 'orgType', displayName: '机构类型'},
                    {field: 'orgDegree', displayName: '机构等级'},
                    {field: 'orgStatus', displayName: '机构状态'},
                    {field: 'orgAddr', displayName: '机构地址'},
                    {field: 'createTime', displayName: '创建时间'}
                ]
                $scope.commonGrid = initgrid($scope, commonGrid, filterFilter, com, false, selework);

                var recommonGrid = function () {
                    var subFrom = {};
                    subFrom.empCode = empCode;
                    console.log(subFrom.empCode);
                    //调取工作组信息OM_GROUP
                    Emp_service.loadOrgNotInbyEmp(subFrom).then(function (data) {
                        if (data.status == "success" && !isNull(data.retMessage)) {
                            $scope.commonGrid.data = data.retMessage;
                            $scope.commonGrid.mydefalutData = data.retMessage;
                            $scope.commonGrid.getPage(1, $scope.commonGrid.paginationPageSize);
                        } else {
                            $scope.commonGrid.data = [];
                            $scope.commonGrid.mydefalutData = [];
                            $scope.commonGrid.getPage(1, $scope.commonGrid.paginationPageSize);
                        }

                    })
                }
                //拉取列表
                recommonGrid();

                $scope.add = function () {
                    var arr = $scope.commonGrid.getSelectedRows();
                    if (arr.length != 1) {
                        toastr['error']("请选择一条需要指派至的机构！");
                        return false;
                    } else  if (confirm("是否指派为主机构?")) {
                        var isMain = "true";
                    }else {
                        var isMain = "false";
                    }
                    var subFrom = {};
                    subFrom.empCode = empCode;
                    subFrom.orgCode = arr[0].orgCode;
                    subFrom.isMain = isMain;
                    Emp_service.assignOrg(subFrom).then(function (data) {
                        if (data.status == "success") {
                            toastr['success'](data.retMessage);
                            reorggrid();
                            reempgrid();
                            $scope.cancel();
                        } else {
                            toastr['error'](data.retMessage);
                        }
                    })
                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }
    //取消指派
    emp.disassignOrg = function () {
        var empGuid = $scope.item.guid;
        if ($scope.selectOrgGuid == "") {
            toastr['error']("请选择需要取消指派的机构！");
            return false;
        } else{
            var subFrom = {};
            subFrom.empGuid = empGuid;
            subFrom.orgGuid = $scope.selectOrgGuid;
            Emp_service.disassignOrg(subFrom).then(function (data) {
                if (data.status == "success") {
                    toastr['success'](data.retMessage);
                    reorggrid();
                } else {
                    toastr['error'](data.retMessage);
                }
            })
        }

    }
    //组织所属下指派岗位
    emp.assignPos = function () {
        var empCode = $scope.item.empCode;
        openwindow($uibModal, 'views/org/addsearhgrid_window.html', 'lg',
            function ($scope, $modalInstance) {
                $scope.title = "指派新岗位";
                var commonGrid = {};
                $scope.commonGrid = commonGrid;
                //定义单选事件
                var selework = function () {

                }
                //定义表头名
                var com = [{field: 'positionCode', displayName: '岗位代码'},
                    {field: 'positionName', displayName: '岗位名称'},
                    {field: 'positionType', displayName: '岗位类型'},
                    {field: 'positionStatus', displayName: '岗位状态'},
                    {field: 'guidDuty', displayName: '所属职务'},
                    {field: 'startDate', displayName: '有效开始日期'},
                    {field: 'endDate', displayName: '有效截止日期'}
                ]
                $scope.commonGrid = initgrid($scope, commonGrid, filterFilter, com, false, selework);

                var recommonGrid = function () {
                    var subFrom = {};
                    subFrom.empCode = empCode;
                    console.log(subFrom.empCode);
                    //调取工作组信息OM_GROUP
                    Emp_service.loadPosNotInbyEmp(subFrom).then(function (data) {
                        if (data.status == "success" && !isNull(data.retMessage)) {
                            $scope.commonGrid.data = data.retMessage;
                            $scope.commonGrid.mydefalutData = data.retMessage;
                            $scope.commonGrid.getPage(1, $scope.commonGrid.paginationPageSize);
                        } else {
                            $scope.commonGrid.data = [];
                            $scope.commonGrid.mydefalutData = [];
                            $scope.commonGrid.getPage(1, $scope.commonGrid.paginationPageSize);
                        }

                    })
                }
                //拉取列表
                recommonGrid();

                $scope.add = function () {
                    var arr = $scope.commonGrid.getSelectedRows();
                    if (arr.length != 1) {
                        toastr['error']("请选择一条需要指派至的岗位！");
                        return false;
                    } else  if (confirm("是否指派为主岗位?")) {
                        var isMain = "true";
                    }else {
                        var isMain = "false";
                    }
                    var subFrom = {};
                    subFrom.empCode = empCode;
                    subFrom.posCode = arr[0].positionCode;
                    subFrom.isMain = isMain;
                    Emp_service.assignPos(subFrom).then(function (data) {
                        if (data.status == "success") {
                            toastr['success'](data.retMessage);
                            repostgrid();
                            reempgrid();
                            $scope.cancel();
                        } else {
                            toastr['error'](data.retMessage);
                        }
                    })
                }

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }
    //取消指派
    emp.disassignPos = function () {
        var empGuid = $scope.item.guid;
        if ($scope.selectPosGuid == "") {
            toastr['error']("请选择需要取消指派的岗位！");
            return false;
        } else{
            var subFrom = {};
            subFrom.empGuid = empGuid;
            subFrom.posGuid = $scope.selectPosGuid;
            Emp_service.disassignPos(subFrom).then(function (data) {
                if (data.status == "success") {
                    toastr['success'](data.retMessage);
                    repostgrid();
                } else {
                    toastr['error'](data.retMessage);
                }
            })
        }
    }

    emp.fixmainOrg = function () {
        var empCode = $scope.item.empCode;
        if ($scope.selectOrgCode == "") {
            toastr['error']("请选择一条需要指定为新主机构的机构！");
            return false;
        } else{
            var subFrom = {};
            subFrom.empCode = empCode;
            subFrom.orgCode = $scope.selectOrgCode;
            Emp_service.fixmainOrg(subFrom).then(function (data) {
                if (data.status == "success") {
                    toastr['success'](data.retMessage);
                    reorggrid();
                    reempgrid();
                } else {
                    toastr['error'](data.retMessage);
                }
            })
        }
    }


    emp.fixmainPos = function () {
        var empCode = $scope.item.empCode;
        if ($scope.selectPosCode == "") {
            toastr['error']("请选择一条需要指定为新主岗位的岗位！");
            return false;
        }else{
            var subFrom = {};
            subFrom.empCode = empCode;
            subFrom.posCode = $scope.selectPosCode;
            Emp_service.fixmainPos(subFrom).then(function (data) {
                if (data.status == "success") {
                    toastr['success'](data.retMessage);
                    repostgrid();
                    reempgrid();
                } else {
                    toastr['error'](data.retMessage);
                }
            })
        }
    }




    /**-------------------------------测试table自适应---------------------------*/
        //测试封装
    // var list = [];
    // list.push($scope.empgrid);
    // table($scope,$window,list);

});