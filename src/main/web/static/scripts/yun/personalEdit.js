/**
 * @author : zga
 * @date : 2016-3-29
 *
 * 个人信息编辑
 *
 */
$(function(){
    $('#content1').css('margin','0 auto 0');

    $(window).on('resize scroll',function(){
        $('#content1').css('margin','0 auto 0');
    });

    $('#basic_message').addClass('selected');
    $('#basic_message a').addClass('selected');


    /**
     * 取消事件
     */
    $('.resetR').click(function(){
       window.location.href = '/sso/personalSettings.html';
    });
});