/**
 * @author : zga
 * @date : 2016-3-10
 *
 * 登录
 *
 */
$(function(){
    alertErrorMessage();

    $('.register_input').focus(function(){
        $(this).siblings('.registerImg1').addClass('fillout');
        $(this).siblings('.registerImg2').addClass('fillout');
    });

    $('.register_input').blur(function(){
        $(this).siblings('.registerImg1').removeClass('fillout');
        $(this).siblings('.registerImg2').addClass('fillout');
    });

    $('.remember li').click(function(){
        $(this).toggleClass('checked');
    });

    var demo = $(".regForm").Validform({
        tiptype: 3,
        label: "label",
        showAllError: true,
        datatype: {
            "s5-18": /^[\w\.\s]{4,18}$/
        }
    });
    demo.tipmsg.w["s4-18"] = "";
    demo.addRule([{
        ele:".inputxt:eq(0)",
        datatype:"s4-18"
    },{
        ele:".inputxt:eq(1)",
        datatype:"*",
    }]);


    var ch= ($(window).height())-($('.header').height())-($('.footer').outerHeight())-($('.banner').height());

    $('.container').css('min-height',ch+'px');

    $(window).on('resize scroll',function(){
        var ch= $(window).height()-($('.header').height())-($('.footer').outerHeight())-($('.banner').height());
        $('.container').css('min-height',ch+'px');
    });
});

/**
 * @author : zga
 * @date : 2016-3-10
 *
 * 弹出错误信息
 *
 * @returns {boolean}
 */
function alertErrorMessage(){
    var errorMessage = $('input[name="errorMessage"]').val();
    if(!SSOSystem.isEmpty(errorMessage)){
        //$('input[name="username"]').val("").prop("placeholder",errorMessage);
        var d = dialog({
            title: '提示信息',
            content: '<span style="text-align: center;">' + errorMessage + '</span>',
            follow : document.getElementById('register_submit'),
            size : function(){

            }
        });
        d.show();
    }
}