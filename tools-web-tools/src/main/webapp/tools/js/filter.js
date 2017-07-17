/**
 * 高亮字符
 * @author:jiangwq
 * @time:2017-07-13
 * @params: val:整体字符串 type:string
 *           highlightVal:高亮字符串 type:string
 *           color: 高亮颜色 type:string
 */
MetronicApp.filter('highlightTrust2Html', ['$sce',function($sce) {
    return function(val,highlightVal,color) {
        if(_.isEmpty(highlightVal)){
            return val;
        }
        val = val.replace(highlightVal,"<span style='color : " + color + "'>" + highlightVal + "</span>");
        return $sce.trustAsHtml(val);
    };
}]);