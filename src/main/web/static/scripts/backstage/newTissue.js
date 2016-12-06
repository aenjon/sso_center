/**
 * @author : zga
 * @date : 2016/3/14
 *
 * 新增组织机构
 *
 */
$(function(){
    /**
     * 保存事件
     */
    $('.build_submit li').eq(0).find('a').click(function(){
        var parentId = $('select[name="parentId"]').find("option:selected").val();
        var organizationName = $('input[name="organizationName"]').val();

        /*if(parentId == 'ss'){
            SSOSystem.showAlertDialog('请选择所属区域')
            return false;
        }*/

        if(SSOSystem.isEmpty(organizationName)){
            SSOSystem.showAlertDialog('组织机构名不能为空')
            return false;
        }

        var paramData = {
            'parentId' : parentId,
            'organizationName' : organizationName
        };
        $.ajax({
            url : '/organization/adminAddNewOrganization.json',
            type : 'POST',
            data : JSON.stringify(paramData),
            contentType: 'application/json',
            dataType : 'json',
            success : function(data){
                SSOSystem.showAlertDialog(ErrorMessage[data.message],'','111');
                if(data.success){
                    window.location.href = '/page/sso/tissueList/1,10,s,0,0.html';
                }
            }
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
    $('#new_tissue').addClass('selected');
};