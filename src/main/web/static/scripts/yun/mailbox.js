/**
 * @author : zga
 * @date : 2016-03-29
 *
 * 绑定邮箱
 *
 */
$(function(){
    var demo = $(".bindMailForm").Validform({
        tiptype:3,
        label:".label",
        showAllError:true,
        datatype:{
            "s6-18":/^[\w\.\s]{6,18}$/,
            "s2-10":/^\s{2,10}/,
            "e":/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
        },
        callback : function(form){
            var email = $('input[name="email"]').val();
            var data = {
                'email' : email
            }

            $.ajax({
                url : '/sso/updateEmail.json',
                type : 'POST',
                data : JSON.stringify(data),
                contentType: 'application/json',
                dataType : 'json',
                success : function(data){
                    if(data.success){
                        window.location.href = '/page/sso/bindEmailSucc.html?email=' + email;
                        //window.location.href = '/sso/personalSettings.html';
                    }else {
                        alert(ErrorMessage[data.message])
                    }
                }
            });
            return false;
        }
    });

    /* demo.tipmsg.w["s6-18"]="请输入6到18位英文和数字";*/
    demo.tipmsg.w["s6-18"]=" ";

    demo.addRule([{
        ele : ".email:eq(0)",
        datatype : "e"
    }]);

    $('#mailbox').addClass('selected');
    $('#mailbox').children('#mailbox a').addClass('selected');
});