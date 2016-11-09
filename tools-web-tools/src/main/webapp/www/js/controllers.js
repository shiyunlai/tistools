
angular.module('starter.controllers', [])


    .controller('Tab1Ctrl', function($scope,$state,$http,$ionicModal,app_service,$stateParams,$interval) {


    })
 .controller('Tab2Ctrl', function($scope,$state,$http,$ionicModal,app_service,$stateParams,$interval) {
        $scope.goDetail=function(){
            $state.go('tab.tab2detail',{'id':'id'})
        }

    })
 .controller('Tab2DetailCtrl', function($scope,$state,$http,$ionicModal,app_service,$stateParams,$interval) {
        var id=$stateParams.id


    })
 .controller('Tab3Ctrl', function($scope,$state,$http,$ionicModal,app_service,$stateParams,$interval) {


    })
 .controller('Tab4Ctrl', function($scope,$state,$http,$ionicModal,app_service,$stateParams,$interval) {


    })





