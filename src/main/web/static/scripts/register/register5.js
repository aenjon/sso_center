/**
 * @author : zga
 * @date : 2016/3/18
 *
 * 注册第5步
 *
 */
$(function(){
    $('.step_mark1').html('');
    $('.step_mark2').html('');
    $('.step_mark3').html('');
    $('.step_mark4').html('');

    $('.step_mark1').addClass('selected2');
    $('.step_mark2').addClass('selected2');
    $('.step_mark3').addClass('selected2');
    $('.step_mark4').addClass('selected2');
    $('.step_mark5').addClass('selected1');

    $('.step_step1').addClass('selected2');
    $('.step_step2').addClass('selected2');
    $('.step_step3').addClass('selected2');
    $('.step_step4').addClass('selected2');
    $('.step_step5').addClass('selected1');

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
     * 表单ValidateForm事件
     *
     * @type {jQuery}
     */
    var demo=$(".inviteForm").Validform({
        btnSubmit:'.invite_next',
        tiptype:3,
        showAllError:false,
        datatype:{
            "n8":/^\d{8}$/
        },
        callback : function(form){
            if(true){
                var paramData = {
                    'email' : $('input[name="email"]').val(),
                    'inviteCode' : $('.verify_code').val()
                }
                $.ajax({
                    url : '/user/register/checkInviteCode.json',
                    type : 'post',
                    data : JSON.stringify(paramData),
                    contentType: 'application/json',
                    dataType : 'json',
                    success : function(data){
                        if(data.success){
                            //成功跳转
                            activateInviteCode();
                        } else {
                            //否则提示错误
                            $.Showmsg('邀请码错误!');
                        }
                    }
                })
            }
            return false;
        }
    });
    demo.tipmsg.w["n8"]="请输入8位数的邀请码";
    demo.addRule([{
        ele:".inviteCode:eq(0)",
        datatype:"n8",
        nullmsg:'请输入邀请码',
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
        activateInviteCode();
    });
});

/**
 * 激活验证码
 */
function activateInviteCode(){
    var email = $('input[name="email"]').val();
    window.location.href = '/user/register/activateInviteCode.html?email='+email;
}