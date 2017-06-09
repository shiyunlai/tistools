/**
 * Created by wangbo on 2017/6/1.
 */
angular.module('MetronicApp').controller('menu_controller', function($rootScope, $scope, $http, $timeout,i18nService,uiGridConstants,uiGridSelectionService) {
    i18nService.setCurrentLang("zh-cn");

    /*应用功能模块逻辑*/
    $scope.myData = [
        {id: "0", 'appname':'应用框架模型1', 'appcode':'ABFRAME', 'dict_type':'本地', 'dict_open':'是', 'dict_data':'2017-06-01', 'address':'上海', 'ip':'192.168.1.101', 'port':'8080', 'dict_des':'这里是测试描述页面1'
        },
        {id: "1", 'appname':'应用框架模型2', 'appcode':'ABFRAME1', 'dict_type':'远程', 'dict_open':'否', 'dict_data':'2017-06-03', 'address':'苏州', 'ip':'192.168.1.102', 'port':'8081', 'dict_des':'这里是测试描述页面2'
        },
        {id: "2", 'appname':'应用框架模型3', 'appcode':'ABFRAME3', 'dict_type':'本地', 'dict_open':'是', 'dict_data':'2017-06-03', 'address':'北京', 'ip':'192.168.1.103', 'port':'8082', 'dict_des':'这里是测试描述页面3'
        },
        {id: "3", 'appname':'应用框架模型4', 'appcode':'ABFRAME4', 'dict_type':'本地', 'dict_open':'是', 'dict_data':'2017-06-04', 'address':'深圳', 'ip':'192.168.1.103', 'port':'8083', 'dict_des':'这里是测试描述页面4'
        },
        {id: "4", 'appname':'应用框架模型5', 'appcode':'ABFRAME5', 'dict_type':'远程', 'dict_open':'否', 'dict_data':'2017-06-04', 'address':'南京', 'ip':'192.168.1.104', 'port':'8085', 'dict_des':'这里是测试描述页面5'
        },
        {id: "5", 'appname':'应用框架模型6', 'appcode':'ABFRAME6', 'dict_type':'本地', 'dict_open':'是', 'dict_data':'2017-06-05', 'address':'佛山', 'ip':'192.168.1.105', 'port':'8086', 'dict_des':'这里是测试描述页面6'}
    ];
    //ui-grid 具体配置
    //应用列表
    $scope.gridOptions = {
        data: 'myData',
        columnDefs: [{ field: 'appname', displayName: '应用名称'},
            { field: "appcode", displayName:'应用代码'},
            { field: "dict_type", displayName:'应用类型',filter:{
                type: uiGridConstants.filter.SELECT,
                    selectOptions: [ { value: '本地', label: '本地'},{ value: '远程', label: '远程' }]
            }},
            { field: "dict_open", displayName:'是否开通'},
            { field: "dict_data",displayName:'开通日期'},
            { field: "address",displayName:'访问地址'},
            { field: "ip",displayName:'ip'},
            { field: "port",displayName:'端口'},
            { field: "dict_des",displayName:'应用描述'}
        ],
        enableGridMenu: true, //是否显示grid 菜单
        enableFiltering:true,//打开标识
        //-------- 选中属性 ----------------
        enableFooterTotalSelected: true, // 是否显示选中的总数，默认为true, 如果显示，showGridFooter 必须为true
        enableRowHeaderSelection : true, //是否显示选中checkbox框 ,默认为true
        enableRowSelection : true, // 行选择是否可用，默认为true;
        enableSelectAll : true, // 选择所有checkbox是否可用，默认为true;
        modifierKeysToMultiSelect: true ,//默认false,为true时只能 按ctrl或shift键进行多选, multiSelect 必须为true;
        showGridFooter:true,
        multiSelect:true,
        noUnselect: false,//默认false,选中后是否可以取消选中
        selectionRowHeaderWidth:30 ,//默认30 ，设置选择列的宽度；

        //-------- 分页属性 ----------------
        enablePaginationControls: true, //使用默认的底部分页
        enablePagination: true, //是否分页，默认为true
        paginationPageSizes: [10, 15, 20], //每页显示个数可选项
        paginationCurrentPage:1, //当前页码
        paginationPageSize: 10, //每页显示个数
        //paginationTemplate:"<div></div>", //自定义底部分页代码
        totalItems : 0, // 总数量
        enableSelectionBatchEvent:true,
        useExternalPagination: true,//是否使用分页按钮
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
                if(row.isSelected){
                    $scope.selectRow = row.entity;
                    console.log($scope.selectRow)
                }else{
                    delete $scope.selectRow;//制空
                }
            });
            //多选事件
            $scope.gridApi.selection.on.rowSelectionChangedBatch($scope,function(row,event){
                if(row){
                    for(var i = 0; i <row.length; i++){
                        console.log(row[i].entity)
                    }
                }
            });


        }
    };
    //ui-grid getPage方法 分页方法
    var getPage = function(curPage, pageSize) {
        var firstRow = (curPage - 1) * pageSize;
        $scope.gridOptions.totalItems = $scope.myData.length;
        $scope.gridOptions.data = $scope.myData.slice(firstRow, firstRow + pageSize);
    };
    $scope.import=function(){
        if($scope.selectRow){
            
        }else{
            alert("请必须选择一样")
        }
    }


});
