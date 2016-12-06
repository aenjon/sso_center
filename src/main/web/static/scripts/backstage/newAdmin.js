/**
 * @author : wuyue
 * @date : 2016/3/22
 *
 * 新增管理员
 *
 */
$(function(){
    /**
     * @author : wuyue
     * @date : 2016/3/22
     *
     * 保存时自动跳转到管理员列表
     *
     */
    $('.selected').click(function(){

        var userName =$('input[name="userName"]').val();
        var realName =$('input[name="realName"]').val();
        var gender =$('input[name="gender"]:checked').val();
        var password =$('input[name="password"]').val();
        var roleId = $('select[name="roleId"]').val();

        //console.log(gender);
        //return false;
        if(SSOSystem.isEmpty(userName)||SSOSystem.isEmpty(password)||SSOSystem.isEmpty(realName)){
            SSOSystem.showAlertDialog("请输入完整信息！");
            return false;
        }

        /**
         * Ajax请求,判断管理员名是否存在
         * @type {string}
         */
        var paramData = {
            'userName' : userName
        };
        $.ajax({
            url : '/user/isExistsAdminName.json',
            type : 'POST',
            data : JSON.stringify(paramData),
            contentType: 'application/json',
            dataType : 'json',
            success : function(data){
                if(data.success){
                    window.location.href ='/user/adminAddNewAdmin.html?userName=' + userName + '&realName=' + realName +'&gender=' + gender +'&password=' + password + '&roleId=' + roleId;
                } else {
                    SSOSystem.showAlertDialog("管理员已存在！");
                }
            }
        });
    });

    /**
     * @author : wuyue
     * @date : 2016/3/24
     *
     * 取消时自动跳转到管理员列表
     *
     */
    $('.cancel').click(function(){
        window.location.href ='/page/sso/adminList/1,10,0.html';
    });



});

/**
 * @author : wuyue
 * @date : 2016-3-24
 *
 * 页面载入时添加选中样式
 *
 */
window.onload = function(){
    $('#new_admin').addClass('selected');
};
