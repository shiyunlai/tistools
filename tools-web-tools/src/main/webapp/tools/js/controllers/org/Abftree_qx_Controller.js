/**
 * Created by gaojie on 2017/6/8.
 */
angular.module('MetronicApp').controller('abftree_qx_controller', function($rootScope, $scope,abftree_service, $http, $timeout,i18nService,filterFilter,uiGridConstants,$uibModal) {
    $scope.$on('$viewContentLoaded', function() {
        // initialize core components
        App.initAjax();
    });
    $('#pre-selected-options').multiSelect({
        afterSelect: function(values){
            alert("Select value: "+values);
        },
        afterDeselect: function(values){
            alert("Deselect value: "+values);
        },
        selectableHeader: "<div class='custom-header'>未授予角色</div>",
        selectionHeader: "<div class='custom-header'>已授予角色</div>"
    });


});