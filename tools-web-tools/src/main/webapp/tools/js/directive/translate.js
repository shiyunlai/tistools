/*表单验证控件*/
MetronicApp
    .directive('multipleEmail', [function () {
        //验证邮箱
        return {
            require: "ngModel",
            link: function (scope, element, attr, ngModel) {
                if (ngModel) {
                    var emailsRegexp = /^([a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9-]+(\.[a-z0-9-]+))+$/;
                }
                var customValidator = function (value) {
                    var validity = emailsRegexp.test(value);
                    ngModel.$setValidity("multipleEmail", validity);
                    return validity ? value : undefined;
                };
                // ngModel.$formatters.push(customValidator);
                ngModel.$parsers.push(customValidator);//通讯,可以在前台打印myform.验证名称，查看传过去的所有数据
            }
        };
    }])
    .directive('multiplePhone', [function () {
        //验证手机
        return {
            require: "ngModel",
            link: function (scope, element, attr, ngModel) {
                if (ngModel) {
                    var emailsPhone = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
                }
                var customValidator = function (value) {
                    console.log(value)
                    var validity = emailsPhone.test(value);
                    console.log(validity)
                    ngModel.$setValidity("multipleEmail", validity);
                    return validity ? value : undefined;
                };
                // ngModel.$formatters.push(customValidator);
                ngModel.$parsers.push(customValidator);//通讯,可以在前台打印myform.验证名称，查看传过去的所有数据
            }
        };
    }])
    .directive('multipleCertificates', [function () {
        //验证证件号
        return {
            require: "ngModel",
            link: function (scope, element, attr, ngModel) {
                if (ngModel) {
                    var emailsCertificates = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;//18位
                    var twoCertificates  = /^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$/;
                }
                var customValidator = function (value) {
                    var validity = emailsCertificates.test(value) || twoCertificates.test(value);
                    console.log(validity)
                    ngModel.$setValidity("multipleEmail", validity);
                    return validity ? value : undefined;
                };
                // ngModel.$formatters.push(customValidator);
                ngModel.$parsers.push(customValidator);//通讯,可以在前台打印myform.验证名称，查看传过去的所有数据
            }
        };
    }])
