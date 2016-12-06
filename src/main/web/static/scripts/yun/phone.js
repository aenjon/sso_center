/**
 * @author : zga
 * @date : 2016-03-29
 *
 * 绑定手机
 *
 */
$(function(){
    $('#phone').addClass('selected');
    $('#phone a').addClass('selected');

    /**
     *表单校验
     */
    var phoneForm = $('.phoneForm').Validform({
        tiptype:3,
        label:".label",
        showAllError:true,
        datatype:{
            "n11":/^\d{11}$/,
            "n4":/^\d{4}$/
        },
        callback : function(form){
            /**
             * 绑定手机(submit请求)
             */
            var phone = $('input[name="phone"]').val();
            var code = $('input[name="code"]').val();
            var data = {
                'phone' : phone,
                'code' : code
            }
            $.ajax({
                url : '/sso/bindPhone.json',
                type : 'post',
                data : JSON.stringify(data),
                dataType : 'json',
                contentType : 'application/json',
                success : function(data){
                    if(data.success){
                        window.location.href = '/page/sso/bindPhonelSucc.html';
                    }else {
                        alert(ErrorMessage[data.message]);
                        if(data.message == '100007') {
                            window.location.href = "/user/login.html";
                        }
                    }
                }
            });
            return false;
        }
    });

    phoneForm.tipmsg.w["n11"]=" ";
    phoneForm.addRule([{
        ele : "#phone_input",
        datatype : "n11"
    },{
        ele : "#code",
        datatype : "n4"
    }]);

    /**
     * 获取验证码
     */
    $('.get_phone').click(function () {
        var phone = $('input[name="phone"]').val();
        if(phone == null || phone == '') {
            alert('手机号不能为空');
            return false;
        }

        if(!Constant.phonePattern.test(phone)) {
            alert('手机号码格式不正确');
            return false;
        }

        /**
         * 获取验证码倒计时
         */
        SSOSystem.time($(this),60,"免费获取验证码");

        var data = {
            'phone' : phone
        }
        $.ajax({
            url : '/sms/sendSmsCode.json',
            type : 'post',
            data : JSON.stringify(data),
            dataType : 'json',
            contentType : 'application/json',
            success : function(data){
                if(data.success){

                }else {
                    alert(ErrorMessage[data.message]);
                    if(data.message == '100007') window.location.href = "/user/login.html";

                }
            }
        });
    });
});