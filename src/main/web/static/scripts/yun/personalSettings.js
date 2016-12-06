/**
 * @author : zga
 * @date : 2016-03-29
 *
 * 个人信息
 *
 */
$(function(){
    $('#content1').css('margin','0 auto 0');

    $(window).on('resize scroll',function(){
        $('#content1').css('margin','0 auto 0');
    });

    $('#basic_message').addClass('selected');
    $('#basic_message a').addClass('selected');
});