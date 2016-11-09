
function initRoute($urlRouterProvider, $stateProvider) {
    $urlRouterProvider.otherwise('/tab/tab1');

    $stateProvider
        .state('tab', {
            url: '/tab',
            abstract: true,
            templateUrl: 'templates/tabs.html'
        })
        .state('tab.tab1', {
            url: '/tab1',
            views: {
                'tab1': {
                    templateUrl: 'templates/tab1.html',
                    controller: 'Tab1Ctrl'
                }
            }
        })
        .state('tab.tab2', {
            url: '/tab2',
            views: {
                'tab2': {
                    templateUrl: 'templates/tab2.html',
                    controller: 'Tab2Ctrl'
                }
            }
        }) .state('tab.tab2detail', {
            url: '/tab2detail/:id',
            data: {noTab: true},
            views: {
                'tab2': {
                    templateUrl: 'templates/tab2detail.html',
                    controller: 'Tab2DetailCtrl'
                }
            }
        }).state('tab.tab3', {
            url: '/tab3',
            views: {
                'tab3': {
                    templateUrl: 'templates/tab3.html',
                    controller: 'Tab3Ctrl'
                }
            }
        }).state('tab.tab4', {
            url: '/tab4',
            views: {
                'tab4': {
                    templateUrl: 'templates/tab4.html',
                    controller: 'Tab4Ctrl'
                }
            }
        })


}