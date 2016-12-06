/**
 * @author : zga
 * @date : 2016-03-29
 *
 * 修改密码
 *
 */
$(function(){
    $('#amend_password').addClass('selected');
    $('#amend_password a').addClass('selected');

    var resetPwdForm = $('.resetPwdForm').Validform({
        tiptype:3,
        label:".label",
        showAllError:true,
        datatype:{
            "s6-18":/^[\w\.\s]{6,18}$/
        },
        callback : function(form){
            var oldPassword = $('input[name="old_password"]').val();
            var password = $('input[name="password"]').val();
            var data = {
                'oldPassword' : oldPassword,
                'password' : password
            }

            $.ajax({
                url : '/sso/modifyPassword.json',
                type : 'POST',
                data : JSON.stringify(data),
                contentType: 'application/json',
                dataType : 'json',
                success : function(data){
                    if(data.success){
                        window.location.href = '/page/sso/modifyPasswordSucc.html';
                    }else {
                        alert(ErrorMessage[data.message]);
                        if(data.message == '100007') window.location.href = "/user/login.html";
                    }
                }
            });
            return false;
        }
    });
    resetPwdForm.tipmsg.w["s6-18"]=" ";
    resetPwdForm.addRule([{
        ele : ".password:eq(0)",
        datatype : "s6-18"
    },{
        ele : ".password:eq(1)",
        datatype : "s6-18"
    }]);
});