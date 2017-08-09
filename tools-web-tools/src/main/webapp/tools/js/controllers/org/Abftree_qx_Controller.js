/**
 * Created by gaojie on 2017/6/8.
 */
angular.module('MetronicApp').controller('abftree_qx_controller', function($rootScope, $scope,abftree_service,role_service, $http, $timeout,i18nService,filterFilter,uiGridConstants,$uibModal,$stateParams) {
    $scope.$on('$viewContentLoaded', function() {
        // initialize core components
        App.initAjax();
    });
    var qx = {};
    $scope.qx = qx;
    //获取的GUID
    var guid = "";
    $scope.guid = guid;
    //已经授权的角色
    var alselectitem = [];
    $scope.alselectitem = alselectitem;
    //未授权角色--所有可用角色
    var selectitem = [];
    $scope.selectitem = selectitem;
    //所有角色
    var allrole = [];
    $scope.allrole = allrole;



    $scope.$on('to-child', function(d,data) {
        $scope.guid = data;
        console.log($scope.guid)//父级能得到值
        //加载已经拥有的信息
        var subFrom = {};
        subFrom.guid = $scope.guid;
        realrolegird();
        renotrolegird();
        // abftree_service.queryRole(subFrom).then(function (data) {
        //     console.log(data);
        //     //已经授予的角色
        //     $scope.alselectitem = data.retMessage;
        // })
    });
    var subFrom = {};
    //加载所有角色信息
    role_service.queryRoleList(subFrom).then(function (data) {
        console.log(data)
        $scope.allrole = data.retMessage;
        for(var i = 0;i < $scope.allrole.length;i++){
            // $('#myselect').multiSelect('addOption', { value: $scope.allrole[i].guidApp, text: $scope.allrole[i].roleName,index:i});
        }
        // $('#myselect').multiSelect('select',$scope.alselectitem);
    })

    //生成各个列表
    var mygrid = {};
    $scope.mygrid = mygrid;
    $scope.Jurisdiction = [{角色拥有权限: "test"},
        {角色拥有权限: "test1"},
        {角色拥有权限: "test2"},
        {角色拥有权限: "test3"}];
    var com = [
        { field: '角色拥有权限'}
    ]
    $scope.mygrid = initgrid($scope,mygrid,filterFilter,com,false,function () {

    });
    //拉取权限列表
    var remygrid = function () {
        var subFrom = {};

    }
    //已经授予
    var alrolegird = {};
    $scope.alrolegird = alrolegird;
    var com1 = [{field:'roleName',displayName: '已授予权限'}]
    $scope.alrolegird = initgrid($scope,alrolegird,filterFilter,com1,false,function () {

    });
    $scope.alrolegird.enableFiltering = false;
    $scope.alrolegird.enableGridMenu = false;
    //未授予
    var notrolegird = {};
    $scope.notrolegird = notrolegird;
    var com2 = [{field:'roleName',displayName: '未授予权限'}]
    $scope.notrolegird = initgrid($scope,notrolegird,filterFilter,com2,false,function () {

    });
    $scope.notrolegird.enableFiltering = false;
    $scope.notrolegird.enableGridMenu = false;
    //拉取已授予列表
    var realrolegird = function () {
        var subFrom = {};
        subFrom.guid = $scope.guid;
        abftree_service.queryRole(subFrom).then(function (data) {
            console.log(1)
            if(data.status == "success" && !isNull(data.retMessage)){
                $scope.alrolegird.data =  data.retMessage;
                $scope.alrolegird.mydefalutData =  data.retMessage;
                $scope.alrolegird.getPage(1,$scope.alrolegird.paginationPageSize);
            }else{
                $scope.alrolegird.data =  [];
                $scope.alrolegird.mydefalutData = [];
                $scope.alrolegird.getPage(1,$scope.alrolegird.paginationPageSize);
            }
        })
    }
    //拉取未授予
    var renotrolegird = function () {
        var subFrom = {};
        subFrom.guid = $scope.guid;
        abftree_service.queryRoleNot(subFrom).then(function (data) {
            console.log(data)
        })
    }










    //生成
    $('#myselect').multiSelect({'dblClick':true});
    //权限信息
    var Jurisdiction = [];
    $scope.Jurisdiction = Jurisdiction;
    $('#myselect').multiSelect({
            afterSelect: function(values){
                console.log(values);
                //每次点击,加载当前角色拥有的权限
                $scope.Jurisdiction = [{角色拥有权限: "Moroni"},
                    {角色拥有权限: "Tiancum"},
                    {角色拥有权限: "Jacob"},
                    {角色拥有权限: "Nephi"},
                    {角色拥有权限: "Enos"}];
                $scope.mygrid.data = $scope.Jurisdiction;
                ($scope.$$phase)?null: $scope.$apply();

            },
            afterDeselect: function(values){
                console.log(values);
                $scope.Jurisdiction = [{角色拥有权限: "test"},
                    {角色拥有权限: "test1"},
                    {角色拥有权限: "test2"},
                    {角色拥有权限: "test3"}];
                $scope.mygrid.data = $scope.Jurisdiction;
                $scope.$apply();
            },
            selectableHeader: "<div class='custom-header'>未授予角色</div>",
            selectionHeader: "<div class='custom-header'>已授予角色</div>",
            keepOrder: true
        },
        'select', 'elem_1');
    $('#myselect').multiSelect("destroy").multiSelect({});

    qx.test = function () {
        console.log($('#myselect').val())
        $('#myselect').multiSelect('refresh');
    }
});
angular.module('MetronicApp').controller('abftree_gwqx_controller', function($rootScope, $scope,abftree_service, $http, $timeout,i18nService,filterFilter,uiGridConstants,$uibModal,$stateParams) {
    $scope.$on('$viewContentLoaded', function() {
        // initialize core components
        App.initAjax();
    });
    var qx = {};
    $scope.qx = qx;
    //获取的GUID
    var guid = {};
    $scope.guid = guid;
    $scope.$on('to-child', function(d,data) {
        $scope.guid = data;
        console.log($scope.guid)//父级能得到值
    });

    var Jurisdictiongrid = {};
    $scope.Jurisdictiongrid = Jurisdictiongrid;
    $scope.Jurisdiction = [{角色拥有权限: "test"},
        {角色拥有权限: "test1"},
        {角色拥有权限: "test2"},
        {角色拥有权限: "test3"}];
    var initdata = function () {
        return $scope.Jurisdiction;
    }
    var com = [
        { field: '角色拥有权限', width: 400}
    ]
    $scope.Jurisdictiongrid = initgrid($scope,Jurisdictiongrid,initdata(),filterFilter,com);
    //已经授权的角色
    var alselectitem = ["ts1","ts2"];
    //未授权角色--所有可用角色
    var selectitem = ["ts1","ts2","ts3","ts4","ts5"];
    //权限信息
    var Jurisdiction = [];
    $scope.Jurisdiction = Jurisdiction;
    $('#gwJurisdiction').multiSelect({
            afterSelect: function(values){
                console.log(values);
                //每次点击,加载当前角色拥有的权限
                $scope.Jurisdiction = [{角色拥有权限: "Moroni"},
                    {角色拥有权限: "Tiancum"},
                    {角色拥有权限: "Jacob"},
                    {角色拥有权限: "Nephi"},
                    {角色拥有权限: "Enos"}];
                $scope.Jurisdictiongrid.data = $scope.Jurisdiction;
                ($scope.$$phase)?null: $scope.$apply();

            },
            afterDeselect: function(values){
                console.log(values);
                $scope.Jurisdiction = [{角色拥有权限: "test"},
                    {角色拥有权限: "test1"},
                    {角色拥有权限: "test2"},
                    {角色拥有权限: "test3"}];
                $scope.Jurisdictiongrid.data = $scope.Jurisdiction;
                $scope.$apply();
            },
            selectableHeader: "<div class='custom-header'>未授予角色</div>",
            selectionHeader: "<div class='custom-header'>已授予角色</div>",
            keepOrder: true
        },
        'select', 'elem_1');
    for(var i = 0;i < selectitem.length;i++){
        $('#gwJurisdiction').multiSelect('addOption', { value: selectitem[i], text: selectitem[i]});
    }
    $('#gwJurisdiction').multiSelect('select',alselectitem);
    abftree_service.loadqxxx(alselectitem).then(function (data) {
        console.log(data);

    });
    qx.test = function () {
        console.log($scope.Jurisdiction);
    }
});