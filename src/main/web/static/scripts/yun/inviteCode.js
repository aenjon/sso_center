/**
 * @author : zga
 * @date : 2016-03-29
 *
 * 绑定邀请码
 *
 */
$(function(){
    $('#bindInviteCode').addClass('selected');
    $('#bindInviteCode a').addClass('selected');

    var inviteCodeForm = $('.inviteCodeForm').Validform({
        tiptype:3,
        label:".label",
        showAllError:true,
        datatype:{
            "s6":/^[\w\s]{6}$/
        },
        callback : function(form){
            /**
             * 绑定邀请码(submit请求)
             */
            var inviteCode = $('input[name="inviteCode"]').val();
            var data = {
                'inviteCode' : inviteCode
            }
            $.ajax({
                url : '/sso/bindInviteCode.json',
                type : 'POST',
                data : JSON.stringify(data),
                contentType: 'application/json',
                dataType : 'json',
                success : function(data){
                    if(data.success){
                        window.location.href = '/page/sso/bindInviteCodeSucc.html';
                    }else {
                        alert(ErrorMessage[data.message])
                        if(data.message == '100007') {
                            window.location.href = "/user/login.html";
                        }
                    }
                }
            });
            return false;
        }
    });

    inviteCodeForm.tipmsg.w["s6"]=" ";
    inviteCodeForm.addRule([{
        ele : "#input_font",
        datatype : "s6"
    }]);
});