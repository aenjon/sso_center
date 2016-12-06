/**
 * @author : zga
 * @date : 2015-12-30
 *
 * 密码强度类
 *
 */
$(function(){
    var ch=$(window).height()-$('.header').outerHeight()-$('.footer').outerHeight();

    $('.container').css('min-height',ch+'px');

    var conh=$('.container').height();

    var cenh=$('.container .center').height();

    var mcen=(conh-cenh)/2;

    $('.container .center').css('margin', mcen+'px auto 0');


    $(window).on('resize scroll',function(){
        var ch=$(window).height()-$('.header').outerHeight()-$('.footer').outerHeight();

        $('.container').css('min-height',ch+'px');

        var conh=$('.container').height();

        var cenh=$('.container .center').height();

        var mcen=(conh-cenh)/2;

        $('.container .center').css('margin', mcen+'px auto 0');

    });

});
