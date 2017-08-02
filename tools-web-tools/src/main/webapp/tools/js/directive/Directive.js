/**
 * Created by Administrator on 2017-02-24.
 */
MetronicApp.directive('btnThree', function () {
    return {
        restrict: 'A',
        link: function(scope,element,attr){
            var elementstyle={
                'width': '220px',
                'overflow': 'hidden',
                'position':'relative',
            }
            element.css(elementstyle);
            element.append('<div  style="width: 300%;transition: all 1s;position: relative;left: 0%;"><button style="width: 15%;margin-left: 3%;margin-right: 3%" class="btn-left btn btn-primary" data-state="1">催办</button><div class="goright" style="display:inline-block;width:70%;"><!--<span style="margin-right:8%;margin-left: 8%;">>></span><span><button class="btn btn-primary" style="margin-right: 5px;">按钮1</button><button class="btn btn-primary" style="margin-right: 5px;">按钮2</button><button class="btn btn-primary" style="margin-right: 5px;">按钮3</button>--></span></div></div>')
            console.log(element.children('div').children('.btn-left'));
            console.log(element.children('div').children('.goright').parent());
            element.children('div').children('.btn-left').click(function(){
                var state=$(this).attr('data-state');
                if(state==1){
                    $(this).addClass('disabled');
                    $(this).html('催办中');
                    $(this).attr('data-state',0);
                }else{
                    $(this).removeClass('disabled');
                    $(this).html('催办');
                    $(this).attr('data-state',1);
                }
            })
            element.children('div').children('.goright').mouseover(function(){
                $(this).parent().css('left','-100%');
            })
            element.children('div').children('.goright').mouseleave(function(){
                $(this).parent().css('left','0%');
            })
        }
    }
})
    .directive('customNull', function () {
        return {
            restrict: 'A',
            require: "ngModel",
            link: function(scope,element,attr,ctrl){
                console.log(scope);//打印当前作用域
                console.log(element);//打印当前标签属性列表
                console.log(attr);//打印当前ctrl

                var target = attr["customNull"];//获取自定义指令属性键值

                if (target) {//判断键是否存在
                    scope.$watch(target, function () {//存在启动监听其值
                        ctrl.$validate()//每次改变手动调用验证
                    })

                    // 获取当前模型控制器的父控制器FormController
                    var targetCtrl = ctrl.$$parentForm[target];//获取指定模型控制器
                    console.log(targetCtrl)

                    ctrl.$validators.equalTo = function (modelValue, viewValue) {//自定义验证器内容

                        var targetValue = targetCtrl.$viewValue;//获取password的输入值

                        return targetValue == viewValue;//是否等于passwordConfirm的值
                    }

                    ctrl.$formatters.push(function (value) {
                        console.log("正在进行数据格式化的值:",value)
                        return value;
                    })

                    ctrl.$parsers.push(function (value) {
                        console.log("正在进行数据转换的值:",value)
                        return value;
                    })

                }
            }
        }
    })



