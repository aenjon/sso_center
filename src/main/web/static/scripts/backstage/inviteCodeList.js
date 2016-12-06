/**
 * @author : zga
 * @date : 2016/3/14
 *
 * 邀请码管理列表
 *
 */
$(function(){
    /**
     * @author : zga
     * @date : 2016-3-14
     *
     * 查询条件的改变事件
     *
     */
    $('.queryCondition').change(function(){
        window.location.href = returnInviteCodeManageURL();
    });

    /**
     * @author : zga
     * @date : 2016-3-14
     *
     * 点击查询事件
     *
     */
    $('.tab_fnLi').click(function(){
        window.location.href = returnInviteCodeManageURL();
    });

    /**
     * @author : zga
     * @date : 2016-3-15
     *
     * 导出查询结果
     *
     */
    $('.export_result').click(function(){
        var queryOrganization = $('select[name="organization"]').val();
        var status = $('select[name="status"]').val();
        var createTime = $('select[name="createTime"]').val();
        var queryInviteCode = $('input[name="inviteCode"]').val();
        if(SSOSystem.isEmpty(queryInviteCode)) queryInviteCode = '0';

        if($('tbody tr').length < 1){
            SSOSystem.showAlertDialog('无导出数据','','xxx')
            return false;
        }
        var url = '/schoolInvite/exportSchoolInvite/'+ queryOrganization + ',' + status + ',' + createTime + ',' + queryInviteCode +  '.html';
        window.location.href = url;
    });
});

/**
 * @author : zga
 * @date : 2016-3-14
 *
 * 返回邀请码管理列表
 *
 * @returns {string}
 */
function returnInviteCodeManageURL(){
    var queryOrganization = $('select[name="organization"]').val();
    var status = $('select[name="status"]').val();
    var createTime = $('select[name="createTime"]').val();
    var queryInviteCode = $('input[name="inviteCode"]').val();
    if(SSOSystem.isEmpty(queryInviteCode)) queryInviteCode = '0';

    var url = '/page/sso/invitationManage/1,10,' + queryOrganization + ',' + status + ','  + createTime + ',' + queryInviteCode + '.html';
    return url;
}


/**
 * @author : zga
 * @date : 2016-3-18
 *
 * 页面载入时添加选中样式
 *
 */
window.onload = function(){
    $('#invitation_manage').addClass('selected');
};