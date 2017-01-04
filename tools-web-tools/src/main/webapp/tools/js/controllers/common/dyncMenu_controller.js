'use strict';
MetronicApp.controller('dynMenu_controller', function ($filter, $rootScope, $scope, $state, $stateParams, filterFilter, $modal, $http, $timeout, $interval) {

    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        ComponentsDateTimePickers.init()
    });



    var menu = [
        {
            operator: "operator1",
            app: [
                {
                    appid: 1,
                    appName: "应用菜单1",
                    funcGroup: [
                        {
                            funGroupId: 1,
                            funGroupName: "组织管理",
                            func: [
                                {funcId: 1, funcName: "机构人员管理", href:"",sequence:1},
                                {funcId: 2, funcName: "工作组管理", href:"",sequence:2},
                                {funcId: 3, funcName: "职务管理", href:"",sequence:3}]
                        },
                        {
                            funGroupId: 2,
                            funGroupName: "权限管理",
                            func: [
                                {funcId: 3, funcName: "应用功能管理", href:"",sequence:1},
                                {funcId: 4, funcName: "菜单管理", href:"",sequence:2}]
                        }
                    ],
                    sequence:1
                },
                {
                    appid: 2,
                    appName: "应用菜单2",
                    funcGroup: [
                        {
                            funGroupId: 3,
                            funGroupName: "功能组3",
                            func: [
                                {funcId: 5, funcName: "功能组3-1", href:"",sequence:1},
                                {funcId: 6, funcName: "功能组3-2", href:"",sequence:2}
                            ]
                        },
                        {
                            funGroupId: 4,
                            funGroupName: "功能组4",
                            func: [
                                {funcId: 7, funcName: "功能组4-1", href:"",sequence:1},
                                {funcId: 8, funcName: "功能组4-2", href:"",sequence:2}]
                        }
                    ],
                    sequence:2
                }
            ]
        },
        {
            operator: "operator2",
            app: [
                {
                    appid: 1,
                    appName: "应用菜单1",
                    funcGroup: [
                        {
                            funGroupId: 1,
                            funGroupName: "组织管理",
                            func: [
                                {funcId: 1, funcName: "机构人员管理",sequence:1},
                                ]
                        }
                    ],sequence:2
                },
                {
                    appid: 2,
                    appName: "应用菜单2",
                    funcGroup: [
                        {
                            funGroupId: 3,
                            funGroupName: "功能组3",
                            func: [
                                {funcId: 5, funcName: "功能组3-1",sequence:1}
                            ]
                        }
                    ],sequence:1
                }
            ]
        },
        {
            operator: "operator3",
            app: [
                {
                    appid: 1,
                    appName: "应用菜单1",
                    funcGroup: [
                        {
                            funGroupId: 1,
                            funGroupName: "组织管理",
                            func: [
                                {funcId: 1, funcName: "机构人员管理", href:"",sequence:3},
                                {funcId: 2, funcName: "工作组管理", href:"",sequence:2},
                                {funcId: 3, funcName: "职务管理", href:"",sequence:1}]
                        },
                        {
                            funGroupId: 2,
                            funGroupName: "权限管理",
                            func: [
                                {funcId: 3, funcName: "应用功能管理", href:"",sequence:2},
                                {funcId: 4, funcName: "菜单管理", href:"",sequence:1}]
                        }
                    ],sequence:1
                }
            ]
        }
    ];
    var operator = "";
    $scope.operator = operator;
    $scope.menu = menu;
    $scope.menuChange = function(operator) {
        if (operator == "operator1") {
            //alert(JSON.stringify(menu[0].app));
            $rootScope.settings.dyn_menu = menu[0].app;
        }
        if (operator == "operator2") {
            //alert(JSON.stringify(menu[1].app));
            $rootScope.settings.dyn_menu = menu[1].app;
        }
        if (operator == "operator3") {
            //alert(JSON.stringify(menu[1].app));
            $rootScope.settings.dyn_menu = menu[2].app;
        }
    }

});
