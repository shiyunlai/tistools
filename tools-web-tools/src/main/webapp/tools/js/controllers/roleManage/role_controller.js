/**
 * Created by wangbo on 2017/6/11.
 */
    angular.module('MetronicApp').controller('role_controller', function($rootScope, $scope ,$modal,$http,i18nService, $timeout,filterFilter,$uibModal,uiGridConstants) {
        var role = {};
        $scope.role = role;
        /* 左侧角色查询逻辑 */
        i18nService.setCurrentLang("zh-cn");
        //ui-grid
        $scope.myData = [
            {'ROLE_CODE': "001000", 'ROLE_NAME': '角色名称','ROLE_TYPE':'一类','GUID_APP':"测试"},
            {'ROLE_CODE': "001001", 'ROLE_NAME': '角色名称','ROLE_TYPE':'一类','GUID_APP':"搜索"},
            {'ROLE_CODE': "001002", 'ROLE_NAME': '角色名称','ROLE_TYPE':'二类','GUID_APP':"查询"}
        ];
        //ui-grid 具体配置
        //下级机构列表
        $scope.gridOptions = {
            data: 'myData',
            columnDefs: [{ field: 'ROLE_CODE', displayName: '角色代码'},
                { field: "ROLE_NAME", displayName:'角色名称'},
                { field: "ROLE_TYPE", displayName:'角色类别',
                    //配置搜索下拉框
                    filter:{
                        //term: '0',//默认搜索那项
                        type: uiGridConstants.filter.SELECT,
                        selectOptions: [{ value: '一类', label: '一类' }, { value: '二类', label: '二类' }]
                    }},
                { field: "GUID_APP", displayName:'隶属应用'}
            ],
            //---------搜索配置---------------------
            enableFiltering:true,//打开标识,用于搜索
            //-------- 分页属性 ----------------
            enablePagination: true, //是否分页，默认为true
            enablePaginationControls: true, //使用默认的底部分页
            paginationPageSizes: [10, 15, 20], //每页显示个数可选项
            paginationCurrentPage:1, //当前页码
            paginationPageSize: 10, //每页显示个数
            //paginationTemplate:"<div></div>", //自定义底部分页代码
            totalItems : 0, // 总数量
            useExternalPagination: true,//是否使用分页按钮
            //是否多选
            // multiSelect:false,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                //分页按钮事件
                gridApi.pagination.on.paginationChanged($scope,function(newPage, pageSize) {
                    if(getPage) {
                        getPage(newPage, pageSize);
                    }
                });
                //行选中事件
                $scope.gridApi.selection.on.rowSelectionChanged($scope,function(row,event){
                    if(row){
                        $scope.selectRow1 = row.entity;
                        console.log($scope.selectRow1)
                        console.log(event)
                    }
                });
            }
        };
        //新增逻辑
        $scope.role_add = function(){
            alert("新增成功")
        }
    });
