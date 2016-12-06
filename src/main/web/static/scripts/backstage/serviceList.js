/**
 * @author : zga
 * @date : 2016-3-18
 *
 * 客服列表
 *
 */
$(function(){
    $('.checkAll').click(function(){
        if(this.checked){
            $(":checkbox").prop("checked",true);
        }else{
            $(":checkbox").prop("checked",false);
        }
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
    $('#service_list').addClass('selected');
};