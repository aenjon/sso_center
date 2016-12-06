/**
 * @author : zga
 * @date : 2016-3-10
 *
 * 用户管理
 *
 */

/**
 * @author : zga
 * @date : 2016-3-10
 *
 * 返回URL
 *
 * @returns {string}
 */
function returnUrl(){
    var organization = $('select[name="organization"]').val();
    var type = $('select[name="type"]').val();
    var status = $('select[name="status"]').val();
    var createTime = $('select[name="createTime"]').val();
    var realName = $('input[name="realName"]').val();
    if(SSOSystem.isEmpty(realName)) realName = "0";
    realName = encodeURI(realName);

    var url = '/page/sso/userList/1,10,' + organization + ',' + type + ',' + status + ',' + createTime + "," + realName + '.html';
    return url;
}

$(function(){
    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * select 下拉框事件
     *
     */
    $('select').change(function(){
        window.location.href = returnUrl();
    });

    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * 点击查询事件
     *
     */
    $('.tab_fnLi').click(function(){
        window.location.href = returnUrl();
    });

    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * 页面加载后添加前面的checkbox选框
     *
     */
    $('.user_tab tbody tr').each(function(){
        var $td = $(this).find('td').eq(0);
        var str = $td.text();
        $td.html(str + '<input type="checkbox" class="userSelect"/>');
    });

    /**
     * 全选/反选事件
     */
    $('.checkAll').click(function(){
       $('.userSelect').prop('checked',$(this).prop("checked"));
    });

    /**
     * 每行前的复选事件
     */
    $('.userSelect').click(function(){
        $('.checkAll').prop("checked",($('.userSelect').length == $('.userSelect:checked').length))
    });


    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * 修改账号状态弹出窗事件
     *
     */
    $('.li_modifyAccountStatus').click(function(){
        var $checkedUsers = $('.userSelect:checked');
        if($checkedUsers.length == 0){
            SSOSystem.showAlertDialog('请选择用户');
            return false;
        }

        var d = dialog({
            title: '修改用户状态',
            width : '200',
            height : '100',
            content: '<select name="modify_status" style="text-align: center;margin: 0 auto;"><option value="activated">正常</option><option value="locked">锁定</option></select>',
            okValue: '确定',
            ok: function () {
                var userNames = new Array();
                $checkedUsers.each(function(){
                    userNames.push($(this).parent().siblings("input[name='userName']").val());
                });

                var status = $('select[name="modify_status"]').find("option:selected").val();
                var paramData = {
                    'status' : status,
                    'userNames' : userNames
                };
                $.ajax({
                    url : '/user/adminModifyStatus.json',
                    type : 'POST',
                    data : JSON.stringify(paramData),
                    contentType: 'application/json',
                    dataType : 'json',
                    success : function(data){
                        if(data.success){
                            d.close();
                            SSOSystem.showAlertDialog('状态修改成功');
                            window.location.reload();
                        }
                    }
                });
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
                d.close();
            }
        });
        d.showModal();
    });

    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * 重置密码事件
     *
     */
    $('.li_resetPassword').click(function(){
        var $checkedUsers = $('.userSelect:checked');
        if($checkedUsers.length == 0){
            SSOSystem.showAlertDialog('请选择用户');
            return false;
        }

        if(!confirm("确定要重置密码吗?"))return false;

        var userNames = new Array();
        $checkedUsers.each(function(){
            userNames.push($(this).parent().siblings("input[name='userName']").val());
        });

        var paramData = {
            'userNames' : userNames
        };

        $.ajax({
            url : '/user/adminResetPassword.json',
            type : 'POST',
            data : JSON.stringify(paramData),
            contentType: 'application/json',
            dataType : 'json',
            success : function(data){
                if(data.success){
                    SSOSystem.showAlertDialog('密码重置成功,新密码为123456');
                }
            }
        });
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
    $('#user_list').addClass('selected');

    /**
     * realName做转码处理
     * @type {*|jQuery}
     */
    var realName = $('input[name="realName"]').val();
    if(realName == '0' || SSOSystem.isEmpty(realName)){
        $('input[name="realName"]').val('');
    } else {
        $('input[name="realName"]').val(decodeURI(realName));
    }
};