/**
 * @author : zga
 * @date : 2016/3/18
 *
 * js file
 *
 */

$(function(){
    var id = "";
    var prokey= "";
    var provalue= "";
    var createtime= "";

    $('.update').click(function(){
        prokey= $(this).parent().siblings().children('.prokey').val();
        provalue= $(this).parent().siblings().children('.provalue').val();
        createtime= $(this).parent().siblings().children('.createtime').val();
        $(this).parent().siblings().children('.emailIn2').css('color','#ff0000').attr('disabled',false);
        $(this).hide();
        $(this).siblings('.emailBut').show();

    });

    $('.updateL').click(function(){
        $(this).parents().siblings().children('.emailIn2').css('color','#333333').attr('disabled',true);
        id = $(this).parents().siblings('.eid').val();
        prokey= $(this).parents().siblings().children('.prokey').val();
        provalue= $(this).parents().siblings().children('.provalue').val();
        createtime= $(this).parents().siblings().children('.createtime').val();
        $.ajax({
            url: "/email/updateEmail.json",
            dataType: "json",
            data: {
                id:id,
                prokey:prokey,
                provalue:provalue,
                createtime:createtime,
            },
        });
        $(this).parent().siblings('.update').show();
        $(this).parent('.emailBut').hide();
    });

    $('.updateR').click(function(){
        $(this).parents().siblings().children('.emailIn2').css('color','#333333').attr('disabled',true);
        $(this).parents().siblings().children('.prokey').val(prokey);
        $(this).parents().siblings().children('.provalue').val(provalue);
        $(this).parents().siblings().children('.createtime').val(createtime);
        $(this).parents('.emailBut').hide();
        $(this).parent().siblings('.update').show();

    });

    $('.fromDate').focus(function(){
        WdatePicker({
            /*  isShowToday:false,
             isShowOK:false,*/
            isShowClear:false,

            /*  onpicked:function(){
             $('.toDate').focus();
             }*/
        });
    });

    $('.toDate').focus(function(){
        WdatePicker({
            // minDate:"#F{$dp.$D('fromDate')}"
            isShowClear:false,
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
    $('#email_port').addClass('selected');
};