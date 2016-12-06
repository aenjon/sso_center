/**
 * @author : zga
 * @date : 2016/3/18
 *
 * 后台页面内容样式
 *
 */
$(function(){

    /**
     * @author : zga
     * @date : 2016-3-18
     *
     * 菜单动态的添加样式(class\id属性)
     *
     */
    $('dl').each(function(){
        var className = $(this).find('input[name="class_style"]').val();
        $(this).find('dt').prop('class',className);

        $(this).find('dd').each(function(){
            var idName = $(this).find('input[name="id_style"]').val();
            $(this).find('a').prop('id',idName);
        });
    });
});