function initController($scope, thisobj, thisobjName, thisobj_service, filterFilter) {
    thisobj.checkAll = function (headcheck) {
        if (!headcheck) {
            if (thisobj.dataList != undefined) {
                for (var i = 0; i < thisobj.dataList.length; i++) {
                    thisobj.dataList[i].checked = true;
                }
            }
        } else {
            if (thisobj.dataList != undefined) {
                for (var i = 0; i<thisobj.dataList.length; i++) {
                    if( thisobj.dataList[i]!=null){
                        thisobj.dataList[i].checked = false;
                    }
                }
            }

        }
    }
    thisobj.getSelectItems = function () {
        var res = filterFilter(thisobj.dataList, function (record) {
            return record.checked;
        });
        return res;
    }

    //切换标签页
    thisobj.editMethod = {
        'none' :'none',
        'add' : 'add',
        'edit' : 'edit',
        'detail' : 'detail',
        'search' : 'search'
    };
    thisobj.view = {
        'listPage' : 1,
        'addPage' : 2,
        'editPage' : 3,
        'detailPage' : 4,
        'searchPage' : 5
    }
    //页面子标题
    $scope.editMethod = thisobj.editMethod.none;
    //当前标签页，1：列表页，2：编辑页
    $scope.currentView = thisobj.view.listPage;
    $scope.message = '';
    $scope.showListPage = function () {
        if($scope.currentView == thisobj.view.listPage){
            return true;
        } else {
            return false;
        }
    }
    $scope.showEditPage = function () {
        if($scope.currentView == thisobj.view.addPage
            || $scope.currentView == thisobj.view.editPage){
            return true;
        } else {
            return false;
        }
    }
    $scope.showDetailPage = function () {
        if ($scope.currentView == thisobj.view.detailPage) {
            return true;
        } else {
            return false;
        }
    }
    $scope.showSearchPage = function () {
        if ($scope.currentView == thisobj.view.searchPage) {
            return true;
        } else {
            return false;
        }
    }

    //切换标签页
    thisobj.switchView = function (view, editMethod) {
        $scope.currentView = view;
        $scope.editMethod = editMethod;
        if ($scope.editMethod == thisobj.editMethod.add) {
            $scope.editTiTle = '增加' ;
        } else if ($scope.editMethod == thisobj.editMethod.edit) {
            $scope.editTiTle = '修改' ;
        }  else if ($scope.editMethod == thisobj.editMethod.detail) {
            $scope.editTiTle = '详情';
        }
        // if(view  == thisobj.view.listPage) {
        //     $scope.editMethod = thisobj.editMethod.add;
        //     $scope.editTiTle = '增加' +suffixTitle;
        // }

    }
    thisobj.switchToAddView = function () {
        if($scope.currentView == thisobj.view.addPage
            || $scope.currentView == thisobj.view.editPage) {
            return false;
        }
        thisobj.item = {};
        thisobj.switchView(thisobj.view.addPage, thisobj.editMethod.add);
    };
    thisobj.switchToEditView = function () {
        if ($scope.currentView == thisobj.view.editPage) {
            return false;
        }
        thisobj.switchView(thisobj.view.editPage, thisobj.editMethod.edit);
    };
    thisobj.switchToDetailView = function () {
        if ($scope.currentView == thisobj.view.detailPage) {
            return false;
        }
        thisobj.switchView(thisobj.view.detailPage, thisobj.editMethod.detail);
    };
    thisobj.switchToSearchView = function () {
        if ($scope.currentView == thisobj.view.searchPage) {
            return false;
        }
        thisobj.switchView(thisobj.view.searchPage, thisobj.editMethod.search);
    }
    thisobj.switchToListView = function () {
        if ($scope.currentView == thisobj.view.listPage) {
            return false;
        }
        thisobj.switchView(thisobj.view.listPage, thisobj.editMethod.none);
        thisobj.searchN();
    };

    //end-
    //begin-初始化第一次进入页面的全局变量，包括：搜索表单，初始查询,grid初始化,翻页初始化
    thisobj.searchForm = {"page": {"currentPage": 1}, "orderGuize": "", "searchItems": {}};
    $scope.$watch(thisobjName + '.searchForm.page.currentPage+' + thisobjName + '.searchForm.page.itemsperpage', pageChange);
    //var seachnum=0;
    function pageChange(newValue, oldValue, scope) {
        //seachnum++;
        //if(seachnum>=2) {
        //    seachnum=0;
        //    return false;
        //}
        if (newValue == oldValue) {
            return false;
        }
        if (thisobj.searchForm.page!=undefined&&thisobj.searchForm.page.currentPage < 1) {
            thisobj.searchForm.page.currentPage = 1;
            return false;
        }

        if (thisobj.searchForm.page!=undefined&&thisobj.searchForm.page.currentPage > thisobj.searchForm.page.totalPages) {
            thisobj.searchForm.page.currentPage = thisobj.searchForm.page.totalPages;
            return false;
        }
        thisobj.searchN();
    }

    thisobj.firstPage = function () {
        thisobj.searchForm.page.currentPage = 1;
    }
    thisobj.lastPage = function () {
        thisobj.searchForm.page.currentPage = thisobj.searchForm.page.totalPages;
    }
    thisobj.nextPage = function () {
        thisobj.searchForm.page.currentPage = thisobj.searchForm.page.currentPage + 1;
    }
    thisobj.prePage = function () {
        thisobj.searchForm.page.currentPage = thisobj.searchForm.page.currentPage - 1;
    }

    //end-
    //功能开始的地方
    thisobj.reset=function(){
        thisobj.searchForm.searchItems={}
    }
    thisobj.search1 = function search1() {//查询第一页
        if(thisobj.searchForm.page==undefined){
            thisobj.searchForm.page={}
        }
        thisobj.searchForm.page.currentPage = 1;
        thisobj.searchN();
    }
    thisobj.searchCurrent = function (currentPage) {//查询指定页
        thisobj.searchForm.page.currentPage = currentPage;
        thisobj.searchN();
    }
    thisobj.searchN = function search() {//查询第N页
        var pageData = thisobj_service.query(thisobj.searchForm);
        pageData.then(function (data) {  // 调用承诺API获取数据 .resolve
            console.log('data.page'+JSON.stringify(data.page))
            console.log('thisobj.searchForm.page'+JSON.stringify(thisobj.searchForm.page))
            thisobj.searchForm.page = data.page;
            thisobj.dataList = data.list;
        }, function (data) {  // 处理错误 .reject

        });
    }
    thisobj.daochu = function daochu() {//导出
        thisobj.daochuflag=true;
        thisobj.daochuflag2=false;
        toastr['success']("您正在导出，请耐心等待！", "提示！")
        thisobj.searchForm.daochuflag=1;
        var pageData = thisobj_service.query(thisobj.searchForm);
        pageData.then(function (data) {  // 调用承诺API获取数据 .resolve
            thisobj.searchForm.daochuflag=0;
            thisobj.daochuflag=false;
            thisobj.daochuflag2=true;
            thisobj.daochures=data;
            toastr['success']("请点击连接下载！", "导出成功！")
        }, function (data) {  // 处理错误 .reject

        });
    }

    thisobj.deleteData = function (data) {
        if(isdebug){
            var res = confirm("删除是不可恢复的，你确认要删除吗？");
            if (res) {
                for (var j = 0; j < thisobj.dataList.length; j++) {
                    if (data.id == thisobj.dataList[j].id) {
                        thisobj.dataList.splice(j, 1);
                    }
                }
            }
        } else {
            var selectedItems = [];
            selectedItems.push(data);
            var res = confirm("删除是不可恢复的，你确认要删除吗？");
            if (res) {
                var promise = thisobj_service.delete(selectedItems);
                promise.then(function (data) {
                    toastr['success']("您的信息已删除成功！", "恭喜您！")
                    thisobj.searchN();

                });
            }
        }
    }

    thisobj.delete = function () {
        if(isdebug) {
            var selectedItems = thisobj.getSelectItems();
            if (selectedItems.length < 1) {
                alert('请选择要删除的记录');
                return false;
            }
            var res = confirm("删除是不可恢复的，你确认要删除吗？");
            if (res) {
                for (var i = 0; i < selectedItems.length; i++) {
                    for (var j = 0; j < thisobj.dataList.length; j++) {
                        if (selectedItems[i].id == thisobj.dataList[j].id) {
                            thisobj.dataList.splice(j, 1);
                        }
                    }
                }
            }
        } else {
            var selectedItems = thisobj.getSelectItems();
            if (selectedItems.length < 1) {
                alert('请选择要删除的记录');
                return false;
            }
            var res = confirm("删除是不可恢复的，你确认要删除吗？");
            if (res) {
                var promise = thisobj_service.delete(selectedItems);
                promise.then(function (data) {
                    toastr['success']("您的信息已删除成功！", "恭喜您！")
                    thisobj.searchN();

                });
            }
        }
    }
    thisobj.back = function () {
        window.history.back()
    }
}

function isNull(d){
    if(d==undefined||d==''||d=='null'||d=='NULL'){
        return true
    }
    return false
}

function openwindow($modal, url, size, ctl) {
    var modalInstance = $modal.open({
        templateUrl: url,
        controller: ctl,
        size: size,
        backdrop: false

    });
    return modalInstance;
}

function stringToList(str){
    var value = {};
    value.data = [];
    if(str.indexOf(',')>0){
        value.data = str.split(',');
        return value.data;
    }else{
        value.data = str;
        return value;
    }
}

//日期格式化format（yyyyMMdd）
function timeFormatOne(time) {
    //eg:2015-09-09 -- 20150909
    return time.replace("-","").replace("-","");
}

//日期格式化 format（yyyy-MM-dd）
function timeFormatTwo(time) {
    //eg:20150909 -- 2015-09-09
    var date = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8);
    return date;
}

//获取当前时间后N天的日期
function getAfterNDate(addDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate()+addDayCount);//获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
    var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate(); //获取当前几号，不足10补0
    return y+"-"+m+"-"+d;
}

//格式化日期－return yyyy-MM-dd
function getFormatTime() {
    var dd = new Date();
    var y = dd.getFullYear();
    var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
    var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate(); //获取当前几号，不足10补0
    return y+"-"+m+"-"+d;
}

//格式化日期－return yyyy-MM月-日 hh:mm:ss
function getFormatTimeHHmmss() {
    var dd = new Date();
    var y = dd.getFullYear();
    var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
    var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate(); //获取当前几号，不足10补0

    var hour = dd.getHours();
    var min = dd.getMinutes()<10?"0"+dd.getMinutes():dd.getMinutes();
    var ss = dd.getSeconds()<10?"0"+dd.getSeconds():dd.getSeconds();
    return y+"-"+m+"-"+d+" "+hour+":"+min+":"+ss;
}

//比较时间大小－（时间类型：yyyy-MM-dd hh:mm:ss）
function comparisonTime(startTime, endTime){
    //先比较年月日大小
    var dateTime1=new Date(startTime.substring(0, 10));
    var dateTime2=new Date(endTime.substring(0, 10));

    if(dateTime1.getTime()!=dateTime2.getTime()){
        if(dateTime1.getTime() < dateTime2.getTime()) {
            return true
        }
        return false;
    }

    //如何年月日相同，再比较时分秒大小
    var hhmmss1 = startTime.substring(11, 19).split(":");
    var hhmmss2 = endTime.substring(11, 19).split(":");
    var hhmmssnum1 = parseInt(hhmmss1[0]+hhmmss1[1]);
    var hhmmssnum2 = parseInt(hhmmss2[0]+hhmmss2[1]);
    if(hhmmssnum1 <= hhmmssnum2) {
        return false;
    }
    return false;
}

//比较时间大小－（时间类型：yyyy-MM-dd）
function comparTime(startTime, endTime){
    var dateTime1=new Date(startTime.substring(0, 10));
    var dateTime2=new Date(endTime.substring(0, 10));

    if(dateTime1.getTime()!=dateTime2.getTime()){
        if(dateTime1.getTime() < dateTime2.getTime()) {
            return true
        }
        return false;
    }
    return false;
}

//制作报文参数
//返回当前时间的hhmmss
function getHHMMSS(){
    var dd = new Date();

    var hour = dd.getHours();
    var min = dd.getMinutes()<10?"0"+dd.getMinutes():dd.getMinutes();
    var ss = dd.getSeconds()<10?"0"+dd.getSeconds():dd.getSeconds();
    return hour+""+min+""+ss;
}

//返回当前时间的hh:mm:ss
function getHMS(){
    var dd = new Date();

    var hour = dd.getHours();
    var min = dd.getMinutes()<10?"0"+dd.getMinutes():dd.getMinutes();
    var ss = dd.getSeconds()<10?"0"+dd.getSeconds():dd.getSeconds();
    return hour+":"+min+":"+ss;
}

//返回当前时间的yyyyMMdd
function getYYYYMMDD(){
    var dd = new Date();

    var y = dd.getFullYear();
    var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
    var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate(); //获取当前几号，不足10补0
    return y+m+d;
}
