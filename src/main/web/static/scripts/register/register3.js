/**
 * @author : zga
 * @date : 2016-3-18
 *
 * 注册第3步
 *
 */
var time = 60;
var timer = null;
function refreshTime(){
    $('input.anew60').val(time+'秒后可重新发送');
    timer=setInterval(function(){
        if(time>1){
            time--;
            $('.anew60').val(time+'秒后可重新发送');
        }else{
            clearInterval(timer);
            time = 60;
            $('.anew60').val(time+'秒后可重新发送');
            $('.anew60').hide();
            $('.anew').show();
        };

    },1000);
}

/**
 * @author : zga
 * @date : 2016-3-18
 *
 * 重新发送Email
 *
 */
function reSendEmail(){
    if($('.anew60')){
        $('.anew60').show();
        $('.anew').hide();
        clearInterval(timer);
        timer=setInterval(function(){
            if(time>1){
                time--;
                $('.anew60').val(time+'秒后可重新获取');
            }else{
                clearInterval(timer);
                time = 60;
                $('.anew60').val(time+'秒后可重新获取');
                $('.anew60').hide();
                $('.anew').show();
            };
        },1000);
    }

    var paramData = {
        'email' : $('input[name="email"]').val()
    };
    $.ajax({
        url : '/user/register/reSendEmail.json',
        type : 'POST',
        data : JSON.stringify(paramData),
        contentType: 'application/json',
        dataType : 'json',
        success : function(data){
            if($('.reSendEmail')){
                window.location.reload();
            }
        }
    });
}

$(function(){
    $('.step_mark1').html('');
    $('.step_mark2').html('');

    $('.step_mark1').addClass('selected2');
    $('.step_mark2').addClass('selected2');
    $('.step_mark3').addClass('selected1');


    $('.step_step1').addClass('selected2');
    $('.step_step2').addClass('selected2');
    $('.step_step3').addClass('selected1');

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
     * 重新发送
     */
    $(document).on('click','.anew',function(){
        reSendEmail();
    });

    $(document).on('click','.reSendEmail',function(){
        reSendEmail();
    });

    /**
     * @author : zga
     * @date : 2016-3-18
     *
     * 立即前往
     *
     */
    $('.promptly').click(function(){
        var email = $('input[name="email"]').val();
        var sub = email.substring(email.indexOf("@")+1);
        window.location.href = 'http://mail.'+ sub;
    });
});

window.onload = function(){
    var expiredTicket = $('input[name="expiredTicket"]').val();
    if(expiredTicket == '1'){
        refreshTime();
    }
}