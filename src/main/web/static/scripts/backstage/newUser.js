/**
 * @author : zga
 * @date : 2016-3-10
 *
 * 新增用户
 *
 */
$(function(){
    /**
     * 新增用户
     */
    $('.build_submit li').eq(0).click(function(){
        var userName = $('input[name="userName"]').val();
        var realName = $('input[name="realName"]').val();
        var password = $('input[name="password"]').val();

        var email = $('input[name="email"]').val();
        var phone = $('input[name="phone"]').val();
        var inviteCode = $('input[name="inviteCode"]').val();


        /**
         * 判断重要信息是否为空
         */
        if((SSOSystem.isEmpty(userName)&&SSOSystem.isEmpty(password))
            ||(SSOSystem.isEmpty(userName)&&SSOSystem.isEmpty(realName))
            ||(SSOSystem.isEmpty(realName)&&SSOSystem.isEmpty(password))
            ||(SSOSystem.isEmpty(userName)&&SSOSystem.isEmpty(realName)&&SSOSystem.isEmpty(password))
        ){
            SSOSystem.showAlertDialog("请将重要信息填写完整！");
            return false;
        } else if(SSOSystem.isEmpty(userName)){
            SSOSystem.showAlertDialog("用户名不能为空！");
            return false;
        } else if(SSOSystem.isEmpty(realName)){
            SSOSystem.showAlertDialog("真实姓名不能为空！");
            return false;
        } else if(SSOSystem.isEmpty(password)){
            SSOSystem.showAlertDialog("密码不能为空！");
            return false;
        }

        if(!SSOSystem.isEmpty(email) && !Constant.emailPattern.test(email)){
            SSOSystem.showAlertDialog("邮箱格式不正确！");
            return false;
        }

        if(!SSOSystem.isEmpty(phone) && !Constant.phonePattern.test(phone)){
            SSOSystem.showAlertDialog("手机格式不正确！");
            return false;
        }

        if(!SSOSystem.isEmpty(inviteCode) && inviteCode.length != 6){
            SSOSystem.showAlertDialog("邀请码格式不正确！");
            return false;
        }

        /**
         * 判断用户名是否存在（利用注册用户时判断的controller来判断）
         * @type {{userName: (*|jQuery)}}
         */
        var paramData = {
            'userName' : userName,
            'email' : email,
            'phone' : phone,
            'inviteCode' : inviteCode
        };
        $.ajax({
        url : '/user/validateUserNameEmailPhoneAndInviteCode.json',
        type : 'POST',
        data : JSON.stringify(paramData),
        contentType: 'application/json',
        dataType : 'json',
        success : function(data){
                if(data.success&&data.userNameSuccess&&data.emailSuccess&&data.phoneSuccess&&data.inviteCodeSuccess){
                    $('form').submit();
                } else {
                    if(!data.userNameSuccess){
                        SSOSystem.showAlertDialog(ErrorMessage[data.userNameMessage]);
                        return false;
                    }

                    if(!data.emailSuccess){
                        SSOSystem.showAlertDialog(ErrorMessage[data.emailMessage]);
                        return false;
                    }

                    if(!data.phoneSuccess){
                        SSOSystem.showAlertDialog(ErrorMessage[data.phoneMessage]);
                        return false;
                    }

                    if(!data.inviteCodeSuccess){
                        SSOSystem.showAlertDialog(ErrorMessage[data.inviteCodeMessage]);
                        return false;
                    }
                }
            }
        });
    });

    /**
     * 取消按钮事件
     */
    $('.cancel').click(function(){
        window.location.href='/page/sso/userList/1,10,0,0,0,0,0.html';
    });

    $("#up").uploadPreview({
        Img: "user_img", Width: 100, Height: 100
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
    $('#new_user').addClass('selected');
};