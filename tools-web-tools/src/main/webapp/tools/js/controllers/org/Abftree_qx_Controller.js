/**
 * Created by gaojie on 2017/6/8.
 */
angular.module('MetronicApp').controller('abftree_qx_controller', function($rootScope, $scope,abftree_service, $http, $timeout,i18nService,filterFilter,uiGridConstants,$uibModal,$stateParams) {
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

    var mygrid = {};
    $scope.mygrid = mygrid;
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
    $scope.mygrid = initgrid($scope,mygrid,initdata(),filterFilter,com);
    //已经授权的角色
    var alselectitem = ["ts1","ts2"];
    //未授权角色--所有可用角色
    var selectitem = ["ts1","ts2","ts3","ts4","ts5"];
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
    for(var i = 0;i < selectitem.length;i++){
        $('#myselect').multiSelect('addOption', { value: selectitem[i], text: selectitem[i]});
    }
    $('#myselect').multiSelect('select',alselectitem);
    abftree_service.loadqxxx(alselectitem).then(function (data) {
        console.log(data);

    });
    qx.test = function () {
        console.log($scope.Jurisdiction);
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