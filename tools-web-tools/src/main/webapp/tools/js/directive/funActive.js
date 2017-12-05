/**
 * Created by wangbo on 2017/7/21.
 */
MetronicApp.directive("funActive",function () {
    return {
        restrict : 'AE',
        scope:{
            scopeModels:'=',
            numLength:'='
        },
        link :  function ($scope, $element,$attr,$timeout) {
            var isshow = false;
            var funCode = $attr.funActive;
            $scope.$on('asycFinish', function(event,data) {//取值。详情看 传值 https://www.cnblogs.com/CraryPrimitiveMan/p/3679552.html
                var datas = data.bhvCodes;//拿到rootscope传过来的值
                var isCheck = data.isCheck;//拿到rootscope 是否需要判断
                if(!isNull(data)){//如果存在，且需要权限验证 那么则判断
                    for(var i = 0;i<datas.length;i++){//对权限数组循环
                        if(datas[i]==funCode){
                            isshow = true;
                        }
                    }
                    if(!isshow){//如果是false。则全部移除
                        $element.remove();
                    }
                }
            });


        }
    }
});