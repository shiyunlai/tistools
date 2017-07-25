/**
 * Created by wangbo on 2017/7/21.
 */
MetronicApp.directive("lengthLimit",function () {
    return {
        restrict : 'AE',
        scope:{
            scopeModels:'=',
            numLength:'='
        },
        link :  function (scope, element) {
            element.on("input",function(){
                var numlength = scope.numLength;
                if(scope.scopeModels.length>=numlength){
                    if(!element.hasClass('verify')){
                        element.addClass('verify').after('<i class="ver_size">最大值不能超过' + numlength + '个字符</i>');
                    }else{
                        element.removeClass('verify');
                        element.siblings('.ver_size').remove();
                    }
                }else{
                    element.removeClass('verify');
                    element.siblings('.ver_size').remove();
                }
            })
        }
    }
});