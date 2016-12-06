/**
 * @author : liuren
 * @date : 2016-3-11
 *
 * 计算宽高,水平上下居中
 *
 */
$(function(){
    var ch= $(window).height()-($('.header').height())-($('.footer').outerHeight());
    $('.container').css('min-height',ch+'px');
    $('.datum_r').css('min-height',ch-70+'px');

    var cr=$('.datum_r').outerHeight();
    $('.datum_l').css('min-height',cr+'px');

    var drh=$('.datum_r').height();
    var conh= $('#content1').height();
    var mconh=(drh-conh)/2;
    $('#content1').css('margin',mconh+'px auto 0');

    $(window).on('resize scroll',function(){
        var ch= $(window).height()-($('.header').height())-($('.footer').outerHeight());
       $('.container').css('min-height',ch+'px');
        $('.datum_r').css('min-height',ch-70+'px');

        var cr=$('.datum_r').outerHeight();
        $('.datum_l').css('min-height',cr+'px');

        var drh=$('.datum_r').height();
        var conh= $('#content1').height();
        var mconh=(drh-conh)/2;
        $('#content1').css('margin',mconh+'px auto 0');
    });

    $('.remember li').click(function(){
        $(this).toggleClass('checked');
    });

    $('.sex li').click(function(){
        $(this).addClass('selected').siblings('.sex li').removeClass('selected');
    });
});
