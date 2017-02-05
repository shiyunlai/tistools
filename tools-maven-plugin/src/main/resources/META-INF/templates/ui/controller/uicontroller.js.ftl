'use strict';
/* Controllers */
angular.module('nglanmo.${uicontroller.id}_controller', []).controller('${uicontroller.id}_controller', ['$scope','$rootScope', '$modal', '$routeParams', '${uicontroller.id}_service',<#if uicontroller.sub??> '${uicontroller.sub}_service',</#if> 'changliang_service', '$location', 'filterFilter',
    function ($scope,$rootScope, $modal, $routeParams, ${uicontroller.id}_service,<#if uicontroller.sub??> ${uicontroller.sub}_service,</#if> changliang_service, $location, filterFilter) {
        //begin-本业务的全局变量
        var ${uicontroller.id} = {};
        $scope.${uicontroller.id} = ${uicontroller.id};
		 ${uicontroller.id}.chang={};
        ${uicontroller.id}.getchangliang = function(fenlei){
            if(${uicontroller.id}.chang[fenlei]==undefined){
                changliang_service.getchangliang(fenlei).then(function(data){
                    ${uicontroller.id}.chang[fenlei]=data;
                });
            }
        }
        ${uicontroller.id}.checkAll = function (headcheck) {
            if (!headcheck) {
                for (var i in ${uicontroller.id}.dataList) {
                    ${uicontroller.id}.dataList[i].checked = true;
                }
            } else {
                for (var i in ${uicontroller.id}.dataList) {
                    ${uicontroller.id}.dataList[i].checked = false;
                }
            }
        }
        ${uicontroller.id}.getSelectItems = function () {
            var res = filterFilter(${uicontroller.id}.dataList, function (record) {
                return record.checked;
            });
            return res;
        }
        //end-
        //begin-初始化第一次进入页面的全局变量，包括：搜索表单，初始查询,grid初始化,翻页初始化
        ${uicontroller.id}.searchForm = {"page": {"currentPage": 1}, "orderGuize": "", "searchItems": [
            {"name": "mingcheng", "code": "lk"},
            {"name": "dizhi", "code": "lk"}
        ]};
        ${uicontroller.id}.selectedItems = [];
        $scope.$watch('${uicontroller.id}.searchForm.page.currentPage', pageChange);
        function pageChange(newValue, oldValue, scope) {
            if (newValue == oldValue) {
                return false;
            }
            ${uicontroller.id}.searchN();
        }

        //end-
        //功能开始的地方
        ${uicontroller.id}.search1 = function search1() {//查询第一页
            ${uicontroller.id}.searchForm.page.currentPage = 1;
            ${uicontroller.id}.searchN();
        }
        ${uicontroller.id}.searchCurrent = function (currentPage) {//查询指定页
            ${uicontroller.id}.searchForm.page.currentPage = currentPage;
            ${uicontroller.id}.searchN();
        }
        ${uicontroller.id}.searchN = function search() {//查询第N页
            var pageData = ${uicontroller.id}_service.query(${uicontroller.id}.searchForm);
            pageData.then(function (data) {  // 调用承诺API获取数据 .resolve
                ${uicontroller.id}.searchForm.page = data.page;
                ${uicontroller.id}.dataList = data.list;
//                alert(JSON.stringify(${uicontroller.id}.dataList));
//              ${uicontroller.id}.page=data.page;
            }, function (data) {  // 处理错误 .reject
                alert("服务端返回错误");
            });
//            $scope.gridOptions.selectAll(false);
        }
        ${uicontroller.id}.search1();
        //修改
        ${uicontroller.id}.editData = function (item) {
		<#if uicontroller.sub??>
		  var promise =  ${uicontroller.id}_service.loadById(item.id);
            promise.then(function(res){
                //初始化变量
                 ${uicontroller.id}.item = res.one;
                 ${uicontroller.id}.${uicontroller.sub}List =res.many;
            });
             ${uicontroller.id}.detail();
		<#else>
			${uicontroller.id}.item = angular.copy(item);
            ${uicontroller.id}.detail();
		</#if>
        }
        //添加
        ${uicontroller.id}.add = function () {
            ${uicontroller.id}.item = {};
			<#if uicontroller.sub??>
			  ${uicontroller.id}.${uicontroller.sub}List = [];
			</#if>
            ${uicontroller.id}.detail();
        }
        ${uicontroller.id}.detail = function () {
            var modalInstance = $modal.open({
                templateUrl: 'edit.html',
                controller: modelCtl,
                windowClass: 'app-modal-window',
                backdrop: false,
                resolve: {
                }
            });
            modalInstance.result.then(function () {
            }, function () {

            });

            function modelCtl($scope, $modal, $modalInstance) {
                $scope.${uicontroller.id} = ${uicontroller.id};
                $scope.submit = function () {
					${uicontroller.id}.item.files=[];
					if( ${uicontroller.id}.fileflow!=undefined){
                        initUploadFiles( ${uicontroller.id}.fileflow.files, ${uicontroller.id}.item.files);
                    }
                    var promise = ${uicontroller.id}_service.createOrUpdate(${uicontroller.id}.item);
					promise.then(function (data) {  // 调用承诺API获取数据 .resolve
						 alert('提交成功');
                        $modalInstance.dismiss('cancel');
                        ${uicontroller.id}.searchN();
					}, function (data) {  // 处理错误 .reject
						  alert('提交失败');
					});
                };
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
				
				<#if uicontroller.sub??>
				  $scope.delete = function () {
                    var arrlen=${uicontroller.id}.${uicontroller.sub}List.length;
                    for(var i=arrlen-1; i>=0;i-- ){
                        if(${uicontroller.id}.${uicontroller.sub}List[i].checked){
                            ${uicontroller.id}.${uicontroller.sub}List.splice(i,1);
                        }
                    }

                }
                $scope.deleteData = function (data) {
                    var arrlen=${uicontroller.id}.${uicontroller.sub}List.length;
                    for(var i=arrlen-1; i>=0;i-- ){
                        if(${uicontroller.id}.${uicontroller.sub}List[i].id == data.id){
                            ${uicontroller.id}.${uicontroller.sub}List.splice(i,1);
                            return;
                        }
                    }
                }
				 ${uicontroller.id}.save = function() {
                    if (${uicontroller.id}.${uicontroller.sub}List ==undefined || ${uicontroller.id}.${uicontroller.sub}List.length < 1) {
                        alert('请选择');
                        return false;
                    }
                    var submitform={};
                    submitform.list = ${uicontroller.id}.${uicontroller.sub}List;
                    submitform.item = ${uicontroller.id}.item;
                    var promise = ${uicontroller.id}_service.createOrUpdate(submitform);
                    promise.then(function (data) {  // 调用承诺API获取数据 .resolve
                        alert('提交成功');
                        $modalInstance.dismiss('cancel');
                        ${uicontroller.id}.searchN();
                    }, function (data) {  // 处理错误 .reject
                        alert('提交失败');
                    });
                }
				    ${uicontroller.id}.${uicontroller.sub}xuanze = function () {
                    var modalInstance = $modal.open({
                        templateUrl: '${uicontroller.sub}.html',
                        controller: modelCtl,
                        windowClass: 'app-modal-window',
                        backdrop: false,
                        resolve: {
                        }
                    });
                    modalInstance.result.then(function () {
                    }, function () {

                    });
                    function modelCtl($scope, $modal, $modalInstance) {
                        var ${uicontroller.sub} = {};
                        ${uicontroller.sub}.selectedItems = [];
                        $scope.${uicontroller.sub} = ${uicontroller.sub};

                        //打开窗口的时候把父窗口的list,给子窗口
                        ${uicontroller.sub}.selectedItems=angular.copy( ${uicontroller.id}.${uicontroller.sub}List);
                        //begin-
                        ${uicontroller.sub}.checkAll = function (headcheck) {
                            if (!headcheck) {
                                for (var i in ${uicontroller.sub}.dataList) {
                                    ${uicontroller.sub}.dataList[i].checked = true;
                                }
                            } else {
                                for (var i in ${uicontroller.sub}.dataList) {
                                    ${uicontroller.sub}.dataList[i].checked = false;
                                }
                            }
                        }
                        var getSelectItems = function () {
                            var res = filterFilter(${uicontroller.sub}.dataList, function (record) {
                                return record.checked;
                            });
                            return res;
                        }

                        //begin-初始化第一次进入页面的全局变量，包括：搜索表单，初始查询,grid初始化,翻页初始化
                        ${uicontroller.sub}.searchForm = {"page": {"currentPage": 1}, "orderGuize": "", "searchItems": [
                            {"name": "${uicontroller.sub}mingcheng", "code": "lk"}
                        ]};
                        $scope.$watch('${uicontroller.sub}.searchForm.page.currentPage', pageChange);
                        function pageChange(newValue, oldValue, scope) {
                            if (newValue == oldValue) {
                                return false;
                            }
                            ${uicontroller.sub}.searchN();
                        }
                        //功能开始的地方
                        ${uicontroller.sub}.search1 = function search1() {//查询第一页
                            ${uicontroller.sub}.searchForm.page.currentPage = 1;
                            ${uicontroller.sub}.searchN();
                        }
                        ${uicontroller.sub}.searchCurrent = function (currentPage) {//查询指定页
                            ${uicontroller.sub}.searchForm.page.currentPage = currentPage;
                            ${uicontroller.sub}.searchN();
                        }
                        ${uicontroller.sub}.searchListOnchange=function(d){
                            if(d.checked){
                                ${uicontroller.sub}.selectedItems.push(d);
                            }else{
                                for(var i in ${uicontroller.sub}.selectedItems){
                                    if(${uicontroller.sub}.selectedItems[i].id== d.id){
                                        ${uicontroller.sub}.selectedItems.splice(i,1);
                                        break;
                                    }
                                }
                            }
                        }

                        ${uicontroller.sub}.searchN = function search() {//查询第N页
                            var pageData = ${uicontroller.sub}_service.query(${uicontroller.sub}.searchForm);
                            pageData.then(function (data) {  // 调用承诺API获取数据 .resolve
                                ${uicontroller.sub}.searchForm.page = data.page;
                                ${uicontroller.sub}.dataList = data.list;

                                for(var i in  ${uicontroller.sub}.dataList){
                                    for(var j in  ${uicontroller.sub}.selectedItems){
                                        if(${uicontroller.sub}.dataList[i].id==${uicontroller.sub}.selectedItems[j].id){
                                            ${uicontroller.sub}.dataList[i].checked=true;
                                        }
                                    }
                                }
                            }, function (data) {  // 处理错误 .reject
                                alert("服务端返回错误");
                            });
                        }
                        ${uicontroller.sub}.xuanze = function(){

                            ${uicontroller.id}.${uicontroller.sub}List=angular.copy(${uicontroller.sub}.selectedItems);
                            for (var i in ${uicontroller.id}.${uicontroller.sub}List) {
                                ${uicontroller.id}.${uicontroller.sub}List[i].checked = false;
                            }
                            $modalInstance.dismiss('cancel');
                        }
                        ${uicontroller.sub}.search1();
                        //end-
                        $scope.cancel = function () {
                            $modalInstance.dismiss('cancel');
                        };
                    }
                }
				</#if>
            }
        }
		  ${uicontroller.id}.daoru = function () {
            var modalInstance = $modal.open({
                templateUrl: 'daoru.html',
                controller: modelCtl,
                windowClass: 'app-modal-window',
                backdrop: false,
                resolve: {
                }
            });
            modalInstance.result.then(function () {
            }, function () {

            });

            function modelCtl($scope, $modal, $modalInstance) {
                $scope.${uicontroller.id} = ${uicontroller.id};
                ${uicontroller.id}.item={};
                $scope.submit = function () {
                    ${uicontroller.id}.item.files=[];
                    initUploadFiles( ${uicontroller.id}.fileflow.files, ${uicontroller.id}.item.files);
                    var promise = ${uicontroller.id}_service.daoru(${uicontroller.id}.item);
                    promise.then(function (data) {  // 调用承诺API获取数据 .resolve
                        alert('提交成功');
                        $modalInstance.dismiss('cancel');
                        ${uicontroller.id}.searchN();
                    }, function (data) {  // 处理错误 .reject
                        alert('提交失败');
                    });
                };
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        }
        ${uicontroller.id}.daochu = function () {
            var modalInstance = $modal.open({
                templateUrl: 'daochu.html',
                controller: modelCtl,
                windowClass: 'app-modal-window',
                backdrop: false,
                resolve: {
                }
            });
            modalInstance.result.then(function () {
            }, function () {

            });

            function modelCtl($scope, $modal, $modalInstance) {
                $scope.${uicontroller.id} = ${uicontroller.id};
                $scope.submit = function () {
                    $scope.content=JSON.stringify(${uicontroller.id}.searchForm)
                };
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        }
        ${uicontroller.id}.delete = function () {
            var selectedItems = ${uicontroller.id}.getSelectItems();
            if (selectedItems.length < 1) {
                alert('请选择要删除的记录');
                return false;
            }
            var res = confirm("删除是不可恢复的，你确认要删除吗？");
            if (res) {
			 var req={"data":selectedItems}
                var promise =  ${uicontroller.id}_service.softdelete(req);
                promise.then(function (data) {
                    alert('删除成功');
                    ${uicontroller.id}.search1();

                });

            }
        }
        ${uicontroller.id}.deleteData = function (data) {
            var selectedItems = [];
            selectedItems.push(data);
            var res = confirm("删除是不可恢复的，你确认要删除吗？");
            if (res) {
                var promise = ${uicontroller.id}_service.delete(selectedItems);
                promise.then(function (data) {
                    alert('删除成功');
                    ${uicontroller.id}.search1();

                });

            }
        }
        //-end功能结束
    }]);
	
angular.module('nglanmo.${uicontroller.id}_edit_controller', []).controller('${uicontroller.id}_edit_controller', ['$scope','$rootScope', '$modal', '$routeParams', '${uicontroller.id}_service', 'changliang_service', '$location', 'filterFilter',
    function ($scope,$rootScope, $modal, $routeParams, ${uicontroller.id}_service,changliang_service, $location, filterFilter) {
       var id =  $routeParams.id;
       var ${uicontroller.id} = {};
        $scope.${uicontroller.id} = ${uicontroller.id};
		 ${uicontroller.id}.chang={};
        ${uicontroller.id}.getchangliang = function(fenlei){
            if(${uicontroller.id}.chang[fenlei]==undefined){
                changliang_service.getchangliang(fenlei).then(function(data){
                    ${uicontroller.id}.chang[fenlei]=data;
                });
            }
        }
        if(id!='0'){
          var promise=  ${uicontroller.id}_service.loadById(id);
            promise.then(function(data){
                ${uicontroller.id}.item=data;
            });
        }
        $scope.submit = function () {
			${uicontroller.id}.item.files=[];
			initUploadFiles( ${uicontroller.id}.fileflow.files, ${uicontroller.id}.item.files);
            var promise = ${uicontroller.id}_service.createOrUpdate(${uicontroller.id}.item);
            promise.then(function (data) {  // 调用承诺API获取数据 .resolve
                alert('提交成功');
                $location.path("${uicontroller.id}-list");
            }, function (data) {  // 处理错误 .reject
                alert('提交失败');
            });
        };
        $scope.cancel = function () {
            $location.path("${uicontroller.id}-list");
        };
    }]);