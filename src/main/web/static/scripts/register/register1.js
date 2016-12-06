/**
 * @author : zga
 * @date : 2016-3-18
 *
 * 注册第1步
 *
 */
$(function(){
    $('.step_mark1').addClass('selected1');
    $('.step_step1').addClass('selected1');

    var conh=$('.container-fluid').height();

    var figh=$('.figure').height();

    var figm=(conh-figh)/2;

    $('.figure').css('margin',figm+'px auto 0');

    $(window).on('resize scroll',function(){
        var conh=$('.container-fluid').height();

        var figh=$('.figure').height();

        var figm=(conh-figh)/2;

        $('.figure').css('margin',figm+'px auto 0');
    });
});