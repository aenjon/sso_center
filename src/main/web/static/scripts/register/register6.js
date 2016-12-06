/**
 * @author : zga
 * @date : 2016/3/18
 *
 * 注册第6步
 *
 */
$(function(){
    $('.step_mark li').html('');
    $('.step_mark6').html('6');

    $('.step_mark1').addClass('selected2');
    $('.step_mark2').addClass('selected2');
    $('.step_mark3').addClass('selected2');
    $('.step_mark4').addClass('selected2');
    $('.step_mark5').addClass('selected2');
    $('.step_mark6').addClass('selected1');

    $('.step_step1').addClass('selected2');
    $('.step_step2').addClass('selected2');
    $('.step_step3').addClass('selected2');
    $('.step_step4').addClass('selected2');
    $('.step_step5').addClass('selected2');
    $('.step_step6').addClass('selected1');

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

    var second=5;
    $('.leap').html('（'+second+'秒钟后将自动跳转至登录页面）');
    var sec=setInterval(function(){
        if(second>1){
            second--;
            $('.leap').html('（'+second+'秒钟后将自动跳转至登录页面）');
        }else{
            clearInterval(sec);
            window.location.href="/user/login.html";
        };
    },1000);
});