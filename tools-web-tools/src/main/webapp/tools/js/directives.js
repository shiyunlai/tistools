/***
GLobal Directives
***/

// Route State Load Spinner(used on page or content load)
MetronicApp.directive('ngSpinnerBar', ['$rootScope',
    function($rootScope) {
        return {
            link: function(scope, element, attrs) {
                // by defult hide the spinner bar
                element.addClass('hide'); // hide spinner bar by default

                // display the spinner bar whenever the route changes(the content part started loading)
                $rootScope.$on('$stateChangeStart', function() {
                    element.removeClass('hide'); // show spinner bar
                });

                // hide the spinner bar on rounte change success(after the content loaded)
                $rootScope.$on('$stateChangeSuccess', function() {
                    element.addClass('hide'); // hide spinner bar
                    $('body').removeClass('page-on-load'); // remove page loading indicator
                    Layout.setSidebarMenuActiveLink('match'); // activate selected link in the sidebar menu
                   
                    // auto scorll to page top
                    setTimeout(function () {
                        App.scrollTop(); // scroll to the top on content load
                    }, $rootScope.settings.layout.pageAutoScrollOnLoad);     
                });

                // handle errors
                $rootScope.$on('$stateNotFound', function() {
                    element.addClass('hide'); // hide spinner bar
                });

                // handle errors
                $rootScope.$on('$stateChangeError', function() {
                    element.addClass('hide'); // hide spinner bar
                });
            }
        };
    }
])

// Handle global LINK click
MetronicApp.directive('a', function() {
    return {
        restrict: 'E',
        link: function(scope, elem, attrs) {
            if (attrs.ngClick || attrs.href === '' || attrs.href === '#') {
                elem.on('click', function(e) {
                    e.preventDefault(); // prevent link click for above criteria
                });
            }
        }
    };
});

// Handle Dropdown Hover Plugin Integration
MetronicApp.directive('dropdownMenuHover', function () {
  return {
    link: function (scope, elem) {
      elem.dropdownHover();
    }
  };  
});
//常量翻译
MetronicApp.directive('translateConstants', ['$http',function($http) {
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {
            ctrl.$formatters.push(function (viewValue) {
                var subFrom = {}
                subFrom.dictKey = $(elm).attr("comtable");
                console.log(subFrom.dictKey)
                if(subFrom.dictKey == "ORG"){
                    $http.post(manurl + "/om/org/queryAllorg").then(function (data) {
                        var retval = "";
                        if (data.data.status == "success") {
                            for (var i = 0; i < data.data.retMessage.length; i++) {
                                if (viewValue == data.data.retMessage[i].guid) {
                                    retval = data.data.retMessage[i].orgName;
                                    if(isNull(retval)){
                                        retval = "NULL";
                                    }
                                    ctrl.$viewValue = retval;
                                    ctrl.$render()
                                }
                            }
                        }
                    })
                }else if(subFrom.dictKey == "POS"){
                    $http.post(manurl + "/om/org/queryAllposition").then(function (data) {
                        var retval = "";
                        if (data.data.status == "success") {
                            for (var i = 0; i < data.data.retMessage.length; i++) {
                                if (viewValue == data.data.retMessage[i].guid) {
                                    retval = data.data.retMessage[i].positionName;
                                    console.log(retval)
                                    if(isNull(retval)){
                                        retval = "NULL";
                                    }
                                    ctrl.$viewValue = retval;
                                    ctrl.$render()
                                }
                            }
                        }
                    })
                }else if(subFrom.dictKey == "DUTY"){
                    $http.post(manurl + "/om/duty/loadallduty").then(function (data) {
                        var retval = "";
                        if (data.data.status == "success") {
                            for (var i = 0; i < data.data.retMessage.length; i++) {
                                if (viewValue == data.data.retMessage[i].guid) {
                                    retval = data.data.retMessage[i].dutyName;
                                    console.log(retval)
                                    if(isNull(retval)){
                                        retval = "NULL";
                                    }
                                    ctrl.$viewValue = retval;
                                    ctrl.$render()
                                }
                            }
                        }
                    })
                }else if(subFrom.dictKey == "EMP"){
                    $http.post(manurl + "/om/emp/queryemployee").then(function (data) {
                        var retval = "";
                        if (data.data.status == "success") {
                            for (var i = 0; i < data.data.retMessage.length; i++) {
                                if (viewValue == data.data.retMessage[i].guid) {
                                    retval = data.data.retMessage[i].empName;
                                    if(isNull(retval)){
                                        retval = "NULL";
                                    }
                                    ctrl.$viewValue = retval;
                                    ctrl.$render()
                                }
                            }
                        }
                    })
                }else if(subFrom.dictKey == "GROUP"){
                }else{
                    $http.post(manurl + "/DictController/queryDictItemListByDictKey", subFrom).then(function (data) {
                        var retval = "";
                        if (data.data.status == "success") {
                            for (var i = 0; i < data.data.retMessage.length; i++) {
                                if (viewValue == data.data.retMessage[i].sendValue) {
                                    retval = data.data.retMessage[i].itemName;
                                    if(isNull(retval)){
                                        retval = "NULL";
                                    }
                                    ctrl.$viewValue = retval;
                                    ctrl.$render()
                                }
                            }

                        }
                    })
                }

            })


        }
    };
}]);