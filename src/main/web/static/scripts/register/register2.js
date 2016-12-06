/**
 * @author : zga
 * @date : 2016-3-18
 *
 * 注册第2步
 *
 */
$(function(){
    $('.step_mark1').html('');

    $('.step_mark1').addClass('selected2');
    $('.step_mark2').addClass('selected1');

    $('.step_step1').addClass('selected2');
    $('.step_step2').addClass('selected1');

    var conh=$('.container-fluid').height();
    var cenh=$('.container-fluid .center').height();
    var cenm=(conh-cenh)/2;
    $('.container-fluid .center').css('margin',cenm+'px auto 0');

    $(window).on('resize scroll',function(){
        var conh=$('.container-fluid').height();
        var cenh=$('.container-fluid .center').height();
        var cenm=(conh-cenh)/2;
        $('.container-fluid .center').css('margin',cenm+'px auto 0');
    });

    $('.sex li').click(function(){
        index=$(this).index();
        $(this).addClass('selected').siblings('.sex li').removeClass('selected');
        $(this).find('input').attr('checked',true).end().siblings('.sex li').find('input').attr('checked',false);

    });

    var demo = $(".regForm").Validform({
        tiptype:3,
        label:".label",
        showAllError:true,
        datatype:{
            "s6-18":/^[\w\.\s]{6,18}$/,
            "s2-10":/^[\u4E00-\u9FA5\uf900-\ufa2d]{2,10}$/,
            "s5" : /^[\w]{5}$/
            //"e" : const
        },
        callback : function(form){
            var username = $('input[name="username"]').val();
            var password = $('input[name="password"]').val();
            var email = $('input[name="email"]').val();
            var type = $('input[name="type"]').val();
            var code = $('input[name="code"]').val();
            var realName = $('input[name="realName"]').val();
            var gender = $('input[name="gender"]').val();

            var parmData = {
                'userName' : username,
                'password' : password,
                'email' : email,
                'type' : type,
                'code' : code,
                'realName' : realName,
                'gender' : gender
            }

            /**
             * 校验用户名与Email是否已经注册或存在
             */
            $.ajax({
                url : '/user/register/isExistsUserName.json',
                type : 'POST',
                data : JSON.stringify(parmData),
                contentType: 'application/json',
                dataType : 'json',
                success : function(data){
                    if(data.success){
                        $.ajax({
                            url : '/user/register/isBindEmail.json',
                            type : 'POST',
                            data : JSON.stringify(parmData),
                            contentType: 'application/json',
                            dataType : 'json',
                            success : function(data){
                                if(data.success){
                                    $.ajax({
                                        url : '/user/register/addNew.json',
                                        type : 'POST',
                                        data : JSON.stringify(parmData),
                                        contentType: 'application/json',
                                        dataType : 'json',
                                        success : function(data){
                                            if(data.success){
                                                window.location.href = '/page/register/3.html?email=' + email;
                                            }else {
                                                SSOSystem.showAlertDialog(ErrorMessage[data.message])
                                            }
                                        }
                                    });
                                }else {
                                    SSOSystem.showAlertDialog(ErrorMessage[data.message]);
                                }
                            }
                        });
                    }else {
                        SSOSystem.showAlertDialog(ErrorMessage[data.message])
                    }
                }
            });
            return false;
        }
    });

    demo.tipmsg.w = {
        's6-18' : ' ', //用户名为6到18位
        's2-10' : ' ',//姓名为2到10位中文字符
        '*6-10' : ' ',//密码为6到10位
        's5' : ' ',//验证码为5位
        'e' : ' '//邮箱格式不正确
    };

    demo.addRule([{
        ele:".inputxt:eq(0)",
        datatype:"s6-18"
    },{
        ele:".inputxt:eq(1)",
        datatype:"e"
    },{
        ele:".inputxt:eq(2)",
        datatype:"s2-10"
    },{
        ele:".inputxt:eq(3)",
        datatype:"*6-10"
    },{
        ele:".inputxt:eq(4)",
        datatype:"*",
        recheck:"password"
    },{
        ele:".inputxt:eq(5)",
        datatype:"s5"
    }]);

    /**
     * @author : zga
     * @date : 2016-3-18
     *
     * 验证码
     *
     */
    $('#vimg').click(function(){
        //时间戳
        //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
        var timestamp = (new Date()).valueOf();
        $(this).attr('src',"/code.html?timestamp=" + timestamp);
    });
});