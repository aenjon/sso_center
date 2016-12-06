/**
 * @author : zga
 * @date : 2016/3/18
 *
 * 主页面
 *
 */
$(function(){
    var comh= $('.container').height();
    var rh= $('.register_nav').height();
    var rrh=(comh-rh)/2;
    $('.register_nav').css('margin',rrh+ 'px auto 0');

    $(window).on('resize scroll',function(){
        var comh= $('.container').height();
        var rh= $('.register_nav').height();
        var rrh=(comh-rh)/2;
        $('.register_nav').css('margin',rrh+ 'px auto 0');
    });

    /**
     * a click事件
     */
    $('a').click(function(){
        var resUrl = $(this).find('input[name="resUrl"]').val();
        var resType = $(this).find('input[name="resType"]').val();
        var clientId = $(this).find('input[name="clientId"]').val();
        var ssoPassword = $(this).find('input[name="ssoPassword"]').val();
        if(resType == '0'){
            var data = {
                'clientId' : clientId,
                'ssoPassword' : ssoPassword
            }
            $.ajax({
                url : '/sso/generateSSOAccessThirdURL.json',
                type : 'POST',
                data : JSON.stringify(data),
                contentType: 'application/json',
                dataType : 'json',
                success : function(data){
                    var targetURL = data.targetURL;
                    window.location.href = resUrl + targetURL;
                }
            });
        } else {
            window.location.href = resUrl;
        }
    });
});