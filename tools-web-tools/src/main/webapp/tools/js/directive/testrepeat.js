MetronicApp.directive('testRepeats', ['$http',function($http) {
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {
            var sem = $(elm).attr("comtasssble");
            ctrl.$formatters.push(function(viewValue){
                if(!isNull(viewValue)){
                    var sem = viewValue.children;//拿到数据  不存放跟结构
                    var sum = 0;//标识，判断第几次循环
                    var html = ''//字符串模板
                    var num = sem;//数组
                    getArray(sem);
                }
                function  getArray(data){
                    sum++;//标识++
                    var sumer = sum+1;
                    if(!isNull(num)){
                        num = [];
                        for(var i=0;i< data.length;i++){
                            if(!isNull(data[i].children)){
                                num = num.concat(data[i].children);//把所有的子合并在一个数组中。即把所有的子取出
                            }
                        }
                    }
                    if(sum == 1){//sum是标识，区分第一次循环
                        for(var i = 0;i< data.length;i++){
                            if(data[i].isLeaf == 'Y'){//如果是，那么按照最后一层循环
                                html +=  '<li class="nav-item"><a href=" '+ data[i].href + '"><i class="'+data[i].icon+'"></i><span class="title">'+data[i].label+'</span></a><ul class="sub-menu ids'+ sumer +'" id="'+ data[i].guid +'"></ul></li>'
                            }else{//如果不是，按照正常循环方式
                                if(!isNull(data[i].children)){//必须有一个功能才会展示
                                    html +=  '<li class="nav-item"><a href="javascript:;"><i class="'+data[i].icon+'"></i><span class="title">'+data[i].label+'</span> <span class="arrow"></span></a><ul class="sub-menu ids'+ sumer +'" id="'+ data[i].guid +'"></ul></li>'
                                }
                            }
                        }
                        $(".ids" + sum).append(html);//追加到li中
                    }
                    for(var j =0;j<data.length;j++){//循环，拿到第一层,与本身这层进行判断，找到对应的层级，guid和parentguid匹配
                        var array = [];
                        for(var i =0; i<num.length;i++){//循环所有的子级，然后进行匹配
                            if(data[j].guid == num[i].parentGuid){
                                array.push(num[i]);
                            }
                        }
                        var htmltwo = '';//模板标识
                        array.forEach(function(v,i){
                            if(array[i].isLeaf =='Y'){//是否是最后一层，如果是，那么按照最后一层的样式拼接
                                htmltwo += '<li><a style="height: 41px;line-height: 31px;"  href="#/'+array[i].href + '"><i class="'+array[i].icon+'"></i><span class="title">'+array[i].label+'</span></a></li>'
                            }else if(array[i].isLeaf !=='Y' && !isNull(array[i].children) ){//否则，按照非最后一层样式拼接
                                htmltwo += '<li class="start nav-item"><a  style="height: 41px;line-height: 31px;"   href="javascript:;"><i class="'+array[i].icon+'"></i><span class="title">'+array[i].label+'</span><span class="arrow "></span></a><ul class="sub-menu ids'+ sumer +'"id="'+ array[i].guid+'"></ul></li>'
                            }
                        })
                        $('#'+data[j].guid).append(htmltwo);//追加到对应的父节点的标签中
                    }
                    /*   if(isNull(data.children)){//标识,如果有children,那么就一直递归下去*/
                    if(sum<40){//标识,如果有children,那么就一直递归下去
                        getArray(num);//递归调用
                    }else{
                        return num;
                    }
                }
            })
        }
    };
}]);