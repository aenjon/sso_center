/**
 * @author : zga
 * @date : 2016/3/18
 *
 * 管理员列表
 *
 */
$(function(){
    /**
     * @author : wuyue
     * @date : 2016-3-23
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
    $('#admin_list').addClass('selected');
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
    var userName = $('input[name="userName"]').val();
    if(SSOSystem.isEmpty(userName)) userName = "0";
    userName = encodeURI(userName);

    var url = '/page/sso/adminList/1,10,' + userName + '.html';
    return url;
}


