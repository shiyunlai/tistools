/**
 * Created by wangbo on 2017/6/10.
 */
angular.module('MetronicApp').controller('behavior_controller', function($rootScope, $scope, $http,i18nService) {
    /* 行为定义配置*/
    var beha = {};
    $scope.beha = beha;

    $scope.myData = [{'BHVTYPE_CODE': 's', 'BHVTYPE_NAME': '测试类型'}, {'BHVTYPE_CODE': 'a', 'BHVTYPE_NAME': '测试类型11'}]
    /*定义行为类型列表结构*/
    i18nService.setCurrentLang("zh-cn");
    $scope.gridOptions = {
        data: 'myData',
        columnDefs: [{field: 'BHVTYPE_CODE', displayName: '行为类型代码'},
            {field: "BHVTYPE_NAME", displayName: '行为类型名称'}
        ],
        //-------- 分页属性 ----------------
        enablePagination: true, //是否分页，默认为true
        enablePaginationControls: true, //使用默认的底部分页
        paginationPageSizes: [10, 15, 20], //每页显示个数可选项
        paginationCurrentPage: 1, //当前页码
        paginationPageSize: 10, //每页显示个数
        //paginationTemplate:"<div></div>", //自定义底部分页代码
        totalItems: 0, // 总数量
        useExternalPagination: true,//是否使用分页按钮
        //是否多选
         multiSelect:false,
        onRegisterApi: function (gridApi) {
            $scope.gridApi = gridApi;
            //分页按钮事件
            gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {
                if (getPage) {
                    getPage(newPage, pageSize);
                }
            });
            //行选中事件
            $scope.gridApi.selection.on.rowSelectionChanged($scope, function (row, event) {
                if (row.isSelected) {
                    $scope.selectRow = row.entity;
                }else{
                    $scope.beha.active = false;
                    delete $scope.selectRow;
                }
            });
        }
    };
    /*功能列表分页功能*/
    var getPage = function (curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions.totalItems = $scope.myData.length;
        $scope.gridOptions.data = $scope.myData.slice(firstRow, firstRow + pageSize);
    };

    //点击查看事件
    $scope.beha.loonking = function(){
        console.log($scope.selectRow);
        if($scope.selectRow){
            $scope.beha.active = true;
        }else{
            $scope.beha.active = false;
            toastr['error']("请至少选择一种类型");
        }
    }
    /*事件行为列表*/
    $scope.myDatas = [{'BHV_CODE': 'TXT002', 'BHV_NAME': '搜索行为'}, {'BHV_CODE': 'TXT003', 'BHV_NAME': '查询行为'}]
    $scope.gridOptions1 = {
        data: 'myDatas',
        columnDefs: [{field: 'BHV_CODE', displayName: '行为代码'},
            {field: "BHV_NAME", displayName: '行为名称'}
        ],
        //-------- 分页属性 ----------------
        enablePagination: true, //是否分页，默认为true
        enablePaginationControls: true, //使用默认的底部分页
        paginationPageSizes: [10, 15, 20], //每页显示个数可选项
        paginationCurrentPage: 1, //当前页码
        paginationPageSize: 10, //每页显示个数
        //paginationTemplate:"<div></div>", //自定义底部分页代码
        totalItems: 0, // 总数量
        useExternalPagination: true,//是否使用分页按钮
        //是否多选
        // multiSelect:false,
        onRegisterApi: function (gridApi) {
            $scope.gridApi = gridApi;
            //分页按钮事件
            gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {
                if (getPagetwo) {
                    getPagetwo(newPage, pageSize);
                }
            });
            //行选中事件
            $scope.gridApi.selection.on.rowSelectionChanged($scope, function (row, event) {
                if (row.isSelected) {
                    $scope.selectRow1 = row.entity;
                    console.log($scope.selectRow1)
                }else{
                    delete $scope.selectRow1;
                }
            });
        }
    };
    /*功能列表分页功能*/
    var getPagetwo = function (curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions1.totalItems = $scope.myDatas.length;
        $scope.gridOptions1.data = $scope.myDatas.slice(firstRow, firstRow + pageSize);
    };
    var importAll = function(){
        return $scope.gridApi.selection.getSelectedRows();
    }

    //保存方法
    $scope.beha.savas = function(){
        var dats = importAll();//多选数组
        if(dats.length>0){
            toastr['success']("保存成功");
        }else{
            toastr['error']("请至少选中一条！");
        }
    }



});