/**
 * Created by wangbo on 2017/7/4.
 */
angular.module('MetronicApp').controller('dictionary_controller', function($rootScope, $scope, $http,i18nService,$modal,filterFilter,$uibModal){
    //tab页切换
    var dictflag = {};
    $scope.dictflag = dictflag;
    var zdgl = false;
    dictflag.zdgl = zdgl;
    //功能组列表
    var zddr = false;
    dictflag.zddr = zddr;
    //初始化tab展现
    $scope.dictflag.zdgl = true;
    initController($scope, dictflag,'dictflag',dictflag,filterFilter)//初始化一下，才能使用里面的方法
    dictflag.loadgwdata = function (type) {
        if(type == 0){
            $scope.dictflag.zdgl = true;
            $scope.dictflag.zddr = false;
        }else if (type == 1){
            $scope.dictflag.zdgl = false;
            $scope.dictflag.zddr = true;
        }
    }

    // 公用对象
    var dictconfig = {};
    $scope.dictconfig = dictconfig;
    //表格渲染
    i18nService.setCurrentLang("zh-cn");
    $scope.importadd = [
        {'dictType':'ABF_APPTYPE','dictName':"应用类型"},
        {'dictType':'ABF_AUTHMODE','dictName':"认证模式"},
        {'dictType':'ABF_BUSIORGTY','dictName':"业务机构类别"},
        {'dictType':'ABF_CARDTYPE','dictName':"证件类型"},
        {'dictType':'ABF_GONFIGTYPE','dictName':"配置类型"},
        {'dictType':'ABF_DUTYTYPE','dictName':"职位套别"}
    ];
    var gridOptions0 = {};
    $scope.gridOptions0 = gridOptions0;
    var initdata = function(){
        return $scope.importadd;//数据方法
    }
    var com = [{ field: 'dictType', displayName: '类型名称'},
        { field: "dictName", displayName:'类型名称'},
        //{ field: "dictName", displayName:'类型名称',visible: false}
    ];
    //自定义点击事件
    var f = function(row){
        if(row.isSelected){
            $scope.selectRow = row.entity;
            $scope.dictconfig.show =true;
        }else{
            delete $scope.selectRow;//制空
            $scope.dictconfig.show =false;
        }
    }
    $scope.gridOptions0 = initgrid($scope,gridOptions0,initdata(),filterFilter,com,false,f,"jjj");



    dictconfig.initt2 = function(num){//查询服务公用方法
        var subFrom = {};
        subFrom.id = num;
        console.log($scope.subFrom.id)
        application_service.appQuery(subFrom).then(function (data) {
            var datas = data[0].data.groupList;
            if(isNull(datas)){
                var datas = [];
                $scope.gridOptions0.data = datas;
            }
            $scope.gridOptions0.data = datas;//把获取到的数据复制给表
            ($scope.$$phase)?null: $scope.$apply();
        })
    }

    /*按钮逻辑*/
    //新增
    $scope.show_win=function(){
        openwindow($uibModal, 'views/dictionary/dictnameAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){//保存新增的函数
                    toastr['success']("保存成功！");
                    $modalInstance.close();
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }
    //修改
    $scope.show_edit=function(id){
       /* var getSel = $scope.gridOptions1.getSelectedRows();
        console.log(getSel)
        console.log($scope.gridOptions0)
        console.log($scope.gridOptions1)*/
        if($scope.selectRow){
            openwindow($uibModal, 'views/dictionary/dictnameAdd.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    var idds = id;
                    $scope.id = idds;
                    $scope.add = function(item){//保存新增的函数
                        toastr['success']("保存成功！");
                        $modalInstance.close();
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }else{
            toastr['error']("请至少选中一条进行修改！");
        }
    }

    //删除
    $scope.showDelAll=function(){
        if($scope.selectRow){
            if(confirm("确定删除选中的功能组？删除功能组将删除该功能下的所有子功能组和资源")){
                toastr['success']("删除成功！");
            }
        }else{
            toastr['error']("请至少选中一条进行修改！");
        }
    }

    //刷新
    $scope.dictconfig_refresh = function(){
   /*     dictconfig.initt2(ids);//刷新*/
        toastr['success']("刷新成功！");
    }

    //导出
    $scope.dictconfig_down = function(){
        if($scope.selectRow){
            toastr['success']("导出一条成功！");
        }else{
            toastr['success']("导出全部数据成功！");
        }
    }

    //树结构类型逻辑
    $("#s").submit(function(e) {
        e.preventDefault();
        $("#container").jstree(true).search($("#q").val());
    });
    //树自定义右键功能(根据类型判断)
    var items = function customMenu(node) {
        var control;
        if(node.parent == '#'){
            var it = {
                "增加字典类型项":{
                    "id":"createa",
                    "label":"增加字典类型项",
                    "action":function(data){
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        console.log(obj)
                        openwindow($uibModal, 'views/dictionary/dicttypeAdd.html', 'lg',
                            function ($scope, $modalInstance) {
                                console.log($modalInstance)
                                //创建机构实例
                                var dictFrom = {};
                                $scope.dictFrom = dictFrom;
                                //处理新增机构父机构
                                dictFrom.guidParents = obj.original.guid;
                                //增加方法
                                $scope.add = function (menuFrom) {
                                    //TODO.
                                    console.log(menuFrom)
                                    toastr['success']("保存成功！");
                                    $modalInstance.close();
                                }

                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
                                };
                            }
                        )
                    }
                },
                "刷新":{
                    "label":"刷新",
                    "action":function(data){
                        $("#container").jstree().refresh();
                    }
                }
            }
            return it;
        }
    };
    //组织机构树
    $("#container").jstree({
        "core" : {
            "themes" : {
                "responsive": false
            },
            // so that create works
            "check_callback" : true,
            'data':
                [{
                    "id": "1",
                    "text": "应用类型",
                    icon:'fa fa-hospital-o icon-state-info icon-lg ',
                    "children":
                        [
                            {
                                "id": "2",
                                "text": "本地",
                                icon:'fa fa-home icon-state-info icon-lg',
                            },
                            {
                                "id": "3",
                                "text": "远程",
                                icon:'fa fa-home icon-state-info icon-lg',
                            }
                        ]
                }]
        },
        "types" : {
            "default" : {
                "icon" : "fa fa-folder icon-state-warning icon-lg"
            },
            "file" : {
                "icon" : "fa fa-file icon-state-warning icon-lg"
            }
        },
        "state" : { "key" : "demo3" },
        "contextmenu":{'items':items
        },
        'dnd': {
            'dnd_start': function () {
                console.log("start");
            },
            'is_draggable':function (node) {
                return true;
            }
        },
        'callback' : {
            move_node:function (node) {
            }
        },

        "plugins" : [ "dnd", "state", "types","search","contextmenu" ]
    }).bind("copy.jstree", function (node,e, data ) {
    })


    //数据字典项列表渲染
    $scope.dictAdd = [
        {'itemType':'0','itemName':"本地",'itemOrder':'1','itemSealed':'是'},
        {'itemType':'1','itemName':"远程",'itemOrder':'2','itemSealed':'否'}
    ];
    var gridOptions1 = {};
    $scope.gridOptions1 = gridOptions1;
    var initdata1 = function(){
        return $scope.dictAdd;//数据方法
    }
    var com1 = [{ field: 'itemType', displayName: '类型项代码'},
        { field: "itemName", displayName:'类型项名称'},
        { field: "itemOrder", displayName:'排序'},
        { field: "itemSealed", displayName:'是否封存'}
    ];
    //自定义点击事件
    var f1 = function(row){
        if(row.isSelected){
            $scope.selectRow1 = row.entity;
        }else{
            delete $scope.selectRow1;//制空
        }
    }
    $scope.gridOptions1 = initgrid($scope,gridOptions1,initdata1(),filterFilter,com1,false,f1,"lll");

    //新增
    $scope.dict_win = function(){
        openwindow($uibModal, 'views/dictionary/dicttypeAdd.html', 'lg',// 弹出页面
            function ($scope, $modalInstance) {
                $scope.add = function(item){//保存新增的函数
                    toastr['success']("保存成功！");
                    $modalInstance.close();
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        )
    }

    //修改
    $scope.dict_edit = function(id){
        var getSel = $scope.gridOptions1.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条进行修改！");
        }else{
            openwindow($uibModal, 'views/dictionary/dicttypeAdd.html', 'lg',// 弹出页面
                function ($scope, $modalInstance) {
                    var ids = id;
                    $scope.id = ids;
                    $scope.add = function(item){//保存新增的函数
                        toastr['success']("保存成功！");
                        $modalInstance.close();
                    }
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            )
        }
    }

    //删除
    $scope.dictDelAll = function(){
        var getSel = $scope.gridOptions1.getSelectedRows();
        if(isNull(getSel) || getSel.length>1){
            toastr['error']("请至少选中一条数据进行删除！");
        }else{
            if(confirm("确定删除选中的功能组？删除功能组将删除该功能下的所有子功能组和资源")){
                openwindow($uibModal, 'views/dictionary/dicttypeAdd.html', 'lg',// 弹出页面
                    function ($scope, $modalInstance) {
                        var ids = id;
                        $scope.id = ids;
                        $scope.add = function(item){//保存新增的函数
                            toastr['success']("保存成功！");
                            $modalInstance.close();
                        }
                        $scope.cancel = function () {
                            $modalInstance.dismiss('cancel');
                        };
                    }
                )
            }
        }
    }

});


angular.module('MetronicApp').controller('dictitwos_controller', function($rootScope, $scope, $http,$modal,filterFilter,FileUploader){

    //上传字典类型
    $scope.typeFile = function openVersion() {
        openwindow( $modal,'views/dictionary/fillwindow.html','lg',function ($scope, $modalInstance) {
            var uploader = $scope.uploader = new FileUploader({
                /*
                url: myport+'/GovernorService/UPLOADFILE',//保存的地址，后台的地址
                mime_types: [
                    {title : "war", extensions : "war"},
                    {title : "zip", extensions : "zip"},
                    {title : "pkg", extensions : "pkg"}
                ]*/ //定义上传的格式

            })
            uploader.filters.push({ //过滤器，控制上传数量
                name: 'customFilter',//过滤器的名字
                fn: function(item /*{File|FileLikeObject}*/ , options) {
                    return this.queue.length < 10;
                }
            });
            uploader.onSuccessItem = function(item, response, status, headers) {
                if(response.returnCode == '000000'){
                    version.item.fileName = response.returnMessage;
                } else if(response.returnCode != '000000'){
                    uploader.queue[i].isSuccess = false;
                    uploader.queue[i].isCancel = false;
                    uploader.queue[i].isError = true;
                } else {
                    uploader.queue[i].isSuccess = false;
                    uploader.queue[i].isCancel = false;
                    uploader.queue[i].isError = true;
                }
            }


            $scope.ok = function () {
                $modalInstance.close();
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        });
    };

    //上传字典项
    $scope.projectFile = function openVersion() {
        openwindow( $modal,'views/dictionary/fillwindow.html','lg',function ($scope, $modalInstance) {
            var uploader = $scope.uploader = new FileUploader({
                /*
                 url: myport+'/GovernorService/UPLOADFILE',//保存的地址，后台的地址
                 mime_types: [
                 {title : "war", extensions : "war"},
                 {title : "zip", extensions : "zip"},
                 {title : "pkg", extensions : "pkg"}
                 ]*/ //定义上传的格式
            })

            uploader.filters.push({
                name: 'customFilter',
                fn: function(item /*{File|FileLikeObject}*/ , options) {
                    return this.queue.length < 10;
                }
            });
            uploader.onSuccessItem = function(item, response, status, headers) {
                if(response.returnCode == '000000'){
                    version.item.fileName = response.returnMessage;
                } else if(response.returnCode != '000000'){
                    uploader.queue[i].isSuccess = false;
                    uploader.queue[i].isCancel = false;
                    uploader.queue[i].isError = true;
                } else {
                    uploader.queue[i].isSuccess = false;
                    uploader.queue[i].isCancel = false;
                    uploader.queue[i].isError = true;
                }
            }

            $scope.ok = function () {
                $modalInstance.close();
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        });
    };

})

