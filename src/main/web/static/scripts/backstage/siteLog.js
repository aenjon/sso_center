/**
 * @author : zga
 * @date : 2016/3/18
 *
 * 站点日志
 *
 */
$(function(){
    $('#fromDate').focus(function(){
        WdatePicker({
            isShowToday:false,
            isShowOK:false,
            isShowClear:false,
            onpicked:function(){
                $('#toDate').focus();
            }
        });
    });

    $('#toDate').focus(function(){
        WdatePicker({
            minDate:"#F{$dp.$D('fromDate')}"
        });
    });

    /**
     * @author : wuyue
     * @date : 2016-3-30
     *
     * 点击查询事件
     *
     */
    $('.tab_fnLi').click(function(){
        window.location.href = returnUrl();
    });
});

/**
 * @author : zga
 * @date : 2016-3-18
 *
 * 页面载入时添加选中样式
 *
 */
window.onload = function(){
    $('#site_log').addClass('selected');
};

/**
 * @author : wuyue
 * @date : 2016-3-23
 *
 * 返回URL
 *
 * @returns {string}
 */
function returnUrl(){
    var c_description = $('input[name="c_description"]').val();
    if(SSOSystem.isEmpty(c_description)) c_description = "0";
    c_description = encodeURI(c_description);

    var url = '/page/sso/siteLog/1,10,' + c_description + '.html';
    return url;
}