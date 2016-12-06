/**
 * @author : zga
 * @date : 2016/3/18
 *
 * 注册第4步
 *
 */
$(function(){
    $('.step_mark1').html('');
    $('.step_mark2').html('');
    $('.step_mark3').html('');

    $('.step_mark1').addClass('selected2');
    $('.step_mark2').addClass('selected2');
    $('.step_mark3').addClass('selected2');
    $('.step_mark4').addClass('selected1');

    $('.step_step1').addClass('selected2');
    $('.step_step2').addClass('selected2');
    $('.step_step3').addClass('selected2');
    $('.step_step4').addClass('selected1');


    var conh=$('.container-fluid').height();
    var cenh=$('.container-fluid .center').height();
    var cenm=(conh-cenh)/2;
    $('.container-fluid .center').css('margin',cenm+'px auto 0');

    $(window).on('resize scroll',function(){
        var conh=$('.container-fluid').height();
        var cenh=$('.container-fluid .center').height();
        var cenm=(conh-cenh)/2;
        $('.container-fluid .center').css('margin',cenm+'px auto 0');
    });

    /**
     * 短信获取click函数
     */
    $('.note').click(function(){
        $('input[name="phone"]').blur();
        var phone = $('input[name="phone"]').val();
        if(phone == null || phone == '' || !Constant.phonePattern.test(phone)) return false;

        SSOSystem.time($(this),5,"获取验证码");
        var data = {
            'phone' : phone
        }

        /**
         * 发送短信
         */
        $.ajax({
            url : '/sms/sendSmsCode.json',
            type : 'POST',
            data : JSON.stringify(data),
            contentType: 'application/json',
            dataType : 'json',
            success : function(data){
                if(data.success){
                } else {
                }
            }
        });
    });

    var demo = $(".regForm").Validform({
        btnSubmit:'.ipone_next',
        tiptype:3,
        showAllError:false,
        datatype:{
            "s11":Constant.phonePattern,
            "s4":/^\d{4}$/
        },
        callback : function(form){
            if(true){
                var data = {
                    'phone' : $('input[name="phone"]').val(),
                    'smsInputCode' : $('.verify_code').val()
                }
                $.ajax({
                    url : '/sms/validateSmsCode.json',
                    type : 'POST',
                    data : JSON.stringify(data),
                    contentType: 'application/json',
                    dataType : 'json',
                    success : function(data){
                        if(data.success){
                            //成功跳转
                            window.location.href = '/page/register/5.html?email=' + $('input[name="email"]').val();
                        } else {
                            //否则提示错误
                            alert(ErrorMessage[data.message])
                        }
                    }
                });
            }
            return false;
        }
    });
    demo.tipmsg.w["s11"]=" "; //请输入11位手机号
    demo.tipmsg.w["s4"]=" ";//请输入4位数字的验证码
    demo.addRule([{
        ele:".ipone_num:eq(0)",
        datatype:"s11",
        nullmsg:' ',// 请输入手机号
        sucmsg:' '
    },{
        ele:".verify_code:eq(0)",
        datatype:"s4",
        nullmsg:' ',//请输入验证码
        sucmsg:' '
    }]);

    /**
     * @author : zga
     * @date : 2016-3-18
     *
     * 跳过click函数
     *
     */
    $('.skip').click(function(){
        window.location.href = '/page/register/5.html?email='+$('input[name="email"]').val();
    });
});