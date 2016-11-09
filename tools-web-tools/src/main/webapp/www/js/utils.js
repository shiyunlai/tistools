
//生产接口
var baseurl="http://www.juyoutou.com/appserver"
var versionId = '1.1';//版本号
var TEST = true;

if(TEST){
    //生产接口
//    var baseurl="http://www.juyoutou.com/appserver"
    //baseurl = "/appserver2"
    var	baseurl="../"
}


//是否存在
function exist(s){
    if(s==undefined||s==null||s==''||s=='null'||s==' '||s=='undefined'){
        return false
    }
    return true
}
//验证手机号
function validateMobile(mobile)
{
    if(!exist(mobile)||mobile.length==0){
        return '请输入手机号码';
    }
    if(mobile.length!=11){
        return'请输入有效的手机号码';
    }

    var myreg = /^(((1[0-9]{2}))+\d{8})$/;
    if(!myreg.test(mobile))
    {
        return'请输入有效的手机号码';
    }
    return '1'
}
function changeTwoDecimal(floatvar)
{
    var f_x = parseFloat(floatvar);
    if (isNaN(f_x))
    {

        return false;
    }
    var f_x = Math.round(floatvar*100)/100;
    return f_x;
}
/**
 * showShortTop(message)
 showShortCenter(message)
 showShortBottom(message)
 showLongTop(message)
 showLongCenter(message)
 showLongBottom(message)
 * @param message
 */
function toast(message){
//    new Toast({context:$('body'),message:message}).show();
    if(TEST){
        alert(message)
    }else{
        window.plugins.toast.showShortCenter(message)
    }
}


/**
 * Created by zhangsu on 15/8/4.
 */
var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X
function IdCardValidate(idCard) {
    idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格
    if (idCard.length == 15) {
        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证
    } else if (idCard.length == 18) {
        var a_idCard = idCard.split("");                // 得到身份证数组
        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
            return true;
        }else {
            return false;
        }
    } else {
        return false;
    }
}
/**
* 判断身份证号码为18位时最后的验证位是否正确
* @param a_idCard 身份证号码数组
* @return
*/
function isTrueValidateCodeBy18IdCard(a_idCard) {
    var sum = 0;                             // 声明加权求和变量
    if (a_idCard[17].toLowerCase() == 'x') {
        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作
    }
    for ( var i = 0; i < 17; i++) {
        sum += Wi[i] * a_idCard[i];            // 加权求和
    }
    valCodePosition = sum % 11;                // 得到验证码所位置
    if (a_idCard[17] == ValideCode[valCodePosition]) {
        return true;
    } else {
        return false;
    }
}
/**
 * 验证18位数身份证号码中的生日是否是有效生日
 * @param idCard 18位书身份证字符串
 * @return
 */
function isValidityBrithBy18IdCard(idCard18){
    var year =  idCard18.substring(6,10);
    var month = idCard18.substring(10,12);
    var day = idCard18.substring(12,14);
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
    // 这里用getFullYear()获取年份，避免千年虫问题
    if(temp_date.getFullYear()!=parseFloat(year)
        ||temp_date.getMonth()!=parseFloat(month)-1
        ||temp_date.getDate()!=parseFloat(day)){
        return false;
    }else{
        return true;
    }
}
/**
 * 验证15位数身份证号码中的生日是否是有效生日
 * @param idCard15 15位书身份证字符串
 * @return
 */
function isValidityBrithBy15IdCard(idCard15){
    var year =  idCard15.substring(6,8);
    var month = idCard15.substring(8,10);
    var day = idCard15.substring(10,12);
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
    // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
    if(temp_date.getYear()!=parseFloat(year)
        ||temp_date.getMonth()!=parseFloat(month)-1
        ||temp_date.getDate()!=parseFloat(day)){
        return false;
    }else{
        return true;
    }
}
//去掉字符串头尾空格
function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 通过身份证判断是男是女
 * @param idCard 15/18位身份证号码
 * @return 'female'-女、'male'-男
 */
function maleOrFemalByIdCard(idCard){
    idCard = trim(idCard.replace(/ /g, ""));        // 对身份证号码做处理。包括字符间有空格。
    if(idCard.length==15){
        if(idCard.substring(14,15)%2==0){
            return 'female';
        }else{
            return 'male';
        }
    }else if(idCard.length ==18){
        if(idCard.substring(14,17)%2==0){
            return 'female';
        }else{
            return 'male';
        }
    }else{
        return null;
    }
}


/**
 *
 * @param id
 * @param scene 0 1 2
 */
function wechatshare(id,scene,title,desc,thumb,link) {

    //alert('id:'+id);
    var params = {
        scene: scene
    };

    if (id == 'send-text') {
        params.text = "人文的东西并不是体现在你看得到的方面，它更多的体现在你看不到的那些方面，它会影响每一个功能，这才是最本质的。但是，对这点可能很多人没有思考过，以为人文的东西就是我们搞一个很小清新的图片什么的。”综合来看，人文的东西其实是贯穿整个产品的脉络，或者说是它的灵魂所在。";
    } else {
        params.message = {
            title: title,
            description: desc,
            thumb: thumb,
            mediaTagName: "TEST-TAG-001",
            messageExt: "这是第三方带的测试字段",
            messageAction: "<action>dotalist</action>",
            media: {}
        };



        switch (id) {
            case 'check-installed':
                Wechat.isInstalled(function (installed) {
                    alert("Wechat installed: " + (installed ? "Yes" : "No"));
                });
                return ;

            case 'send-photo':
                params.message.thumb = "www/img/res1thumb.png";
                params.message.media.type = Wechat.Type.IMAGE;
                params.message.media.image = "www/img/res1.jpg";
                break;

            case 'send-link':
                params.message.thumb = thumb;
                params.message.media.type = Wechat.Type.LINK;
                params.message.media.webpageUrl = link;
                break;

            case 'send-music':
                params.message.thumb = "www/img/res3.jpg";
                params.message.media.type = Wechat.Type.MUSIC;
                params.message.media.musicUrl = "http://y.qq.com/i/song.html#p=7B22736F6E675F4E616D65223A22E4B880E697A0E68980E69C89222C22736F6E675F5761704C69766555524C223A22687474703A2F2F74736D7573696334382E74632E71712E636F6D2F586B30305156342F4141414130414141414E5430577532394D7A59344D7A63774D4C6735586A4C517747335A50676F47443864704151526643473444442F4E653765776B617A733D2F31303130333334372E6D34613F7569643D3233343734363930373526616D703B63743D3026616D703B636869643D30222C22736F6E675F5769666955524C223A22687474703A2F2F73747265616D31342E71716D757369632E71712E636F6D2F33303130333334372E6D7033222C226E657454797065223A2277696669222C22736F6E675F416C62756D223A22E4B880E697A0E68980E69C89222C22736F6E675F4944223A3130333334372C22736F6E675F54797065223A312C22736F6E675F53696E676572223A22E5B494E581A5222C22736F6E675F576170446F776E4C6F616455524C223A22687474703A2F2F74736D757369633132382E74632E71712E636F6D2F586C464E4D313574414141416A41414141477A4C36445039536A457A525467304E7A38774E446E752B6473483833344843756B5041576B6D48316C4A434E626F4D34394E4E7A754450444A647A7A45304F513D3D2F33303130333334372E6D70333F7569643D3233343734363930373526616D703B63743D3026616D703B636869643D3026616D703B73747265616D5F706F733D35227D";
                params.message.media.musicDataUrl = "http://stream20.qqmusic.qq.com/32464723.mp3";
                break;

            case 'send-video':
                params.message.thumb = "www/img/res7.jpg";
                params.message.media.type = Wechat.Type.VIDEO;
                params.message.media.videoUrl = "http://v.youku.com/v_show/id_XNTUxNDY1NDY4.html";
                break;

            case 'send-app':
                params.message.thumb = "www/img/res2.jpg";
                params.message.media.type = Wechat.Type.APP;
                params.message.media.extInfo = "<xml>extend info</xml>";
                params.message.media.url = "http://weixin.qq.com";
                break;

            case 'send-nongif':
                params.message.thumb = "www/img/res5thumb.png";
                params.message.media.type = Wechat.Type.EMOTION;
                params.message.media.emotion = "www/resources/res5.jpg";
                break;

            case 'send-gif':
                params.message.thumb = "www/img/res6thumb.png";
                params.message.media.type = Wechat.Type.EMOTION;
                params.message.media.emotion = "www/resources/res6.gif";
                break;

            case 'send-file':
                params.message.thumb = "www/resources/res2.jpg";
                params.message.media.type = Wechat.Type.FILE;
                params.message.media.file = "www/img/iphone4.pdf";
                break;

            case 'auth':
                Wechat.auth("snsapi_userinfo", function (response) {
                    // you may use response.code to get the access token.
                    alert(JSON.stringify(response));
                }, function (reason) {
                    alert("Failed: " + reason);
                });
                return ;

            default:
                $ionicPopup.alert({
                    title: 'Not supported!',
                    template: 'Keep patient, young man.'
                });

                return ;
        }
    }

    try{
        //alert('before share:'+JSON.stringify(params))
        Wechat.share(params, function () {
            toast("分享成功");
        }, function (reason) {
            toast("分享失败");
        });
    }catch(e){
        //alert('share error:'+e)
    }


};


function share(index,app_service,$cordovaClipboard) {
    if (index == '0') {//微信好友wechatshare(id,scene,title,desc,thumb,link)
        //wechatshare('send-link', index, '聚友投微信分享测试', '大红包来了,点击就有100块大红包赠送,还送美女一枚,具体联系JACK', 'http://ranyunapp.com/img/juzi.jpg', 'http://www.jyb998.icoc.cc/')
        var  sharereq ={}
        sharereq.userId=window.localStorage.userId
        sharereq.type=index
        var promise = app_service.shareToFirend(sharereq);
        promise.success(function(data){

            wechatshare('send-link', index, data.retMessage.title, data.retMessage.content, data.retMessage.thumbnail, data.retMessage.link)

        })
    } else if (index == '1') {//微信朋友圈
        //wechatshare('send-link', index, '聚友投微信分享测试', '大红包来了,点击就有100块大红包赠送,还送美女一枚,具体联系JACK', 'http://ranyunapp.com/img/juzi.jpg', 'http://www.jyb998.icoc.cc/')
        var  sharereq ={}
        sharereq.userId=window.localStorage.userId
        sharereq.type=index
        var promise = app_service.shareToFirend(sharereq);
        promise.success(function(data){
            wechatshare('send-link', index, data.retMessage.title, data.retMessage.content, data.retMessage.thumbnail, data.retMessage.link)

        })
    } else if (index == '2') {//复制连接
        var  sharereq ={}
        sharereq.userId=window.localStorage.userId
        sharereq.type=index
        var promise = app_service.shareToFirend(sharereq);
        promise.success(function(data){
            $cordovaClipboard
                .copy( data.retMessage.content+data.retMessage.link)
                .then(function () {
                    toast('分享信息拷贝到粘贴板')
                }, function () {
                    // error
                });
        })

    }
}
