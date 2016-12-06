/**
 * @author : zga
 * @date : 2016/3/23
 *
 * 新增邀请码
 *
 */
$(function(){
    /**
     * 保存事件
     */
    $('#but_addNew').click(function(){
        var organizationCode = $('select[name="organizationCode"]').val();
        var addNum = $('input[name="addNum"]').val();
        var testPattern = /\d+/;
        if(SSOSystem.isEmpty(addNum) || !testPattern.test(addNum)){
            SSOSystem.showAlertDialog('请输入正确的数量');
            return false;
        }

        var data = {
            'organizationCode' : organizationCode,
            'addNum' : addNum
        }
        $.ajax({
            url : '/schoolInvite/addNewSchoolInvite.json',
            type : 'POST',
            data : JSON.stringify(data),
            contentType: 'application/json',
            dataType : 'json',
            success : function(data){
                if(data.success){
                    window.location.href = '/page/batchGenerateInviteCodeSucc.html';
                } else {
                    alert(ErrorMessage[data.message]);
                }
            }
        });
    });
})

/**
 * @author : zga
 * @date : 2016-3-23
 *
 * 页面载入时添加选中样式
 *
 */
window.onload = function(){
    $('#new_invitation').addClass('selected');
}