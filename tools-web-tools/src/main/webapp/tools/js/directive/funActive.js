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
            var isshow = false;//默认为false。全部移除
            var funCode = $attr.funActive;
            $scope.$on('asycFinish', function(event,data) {//取值。详情看 传值 https://www.cnblogs.com/CraryPrimitiveMan/p/3679552.html
                var datas = data.bhvCodes;//拿到rootscope传过来的值
                // console.log(datas)
                var isCheck = data.isCheck;//拿到rootscope 是否需要判断
                if(!isNull(data)&& isCheck=='Y'){//如果存在，且需要权限验证 那么则判断
                    for(var i = 0;i<datas.length;i++){//对权限数组循环
                        if(datas[i]==funCode){
                            isshow = true;//说明存在权限，则显示
                        }
                    }
                    if(!isshow){//如果是false,代表不存在权限，那么全部移除
                        $element.remove();
                    }
                }
            });
        }
    }
});