/**
 * @author : zga
 * @date : 2016/3/11
 *
 * organization列表
 *
 */

/**
 * @author : zga
 * @date : 2016-3-11
 *
 * 返回URL
 *
 * @returns {string}
 */
function returnOrganizationListUrl(){
    var status = $('select[name="status"]').val();
    var createTime = $('select[name="createTime"]').val();
    var queryOrganizationName = $('input[name="queryOrganizationName"]').val();
    if(SSOSystem.isEmpty(queryOrganizationName)) queryOrganizationName = "0";
    queryOrganizationName = encodeURI(queryOrganizationName);

    var url = '/page/sso/tissueList/1,10,' + status + ',' + queryOrganizationName + ','  + createTime + '.html';
    return url;
}


$(function(){
    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * 页面加载后添加前面的checkbox选框
     *
     */
    $('.tissue_tab tbody tr').each(function(){
        var $td = $(this).find('td').eq(0);
        var str = $td.text();
        $td.html(str + '<input type="checkbox" class="organizationSelect"/>');
    });

    /**
     * 全选/反选事件
     */
    $('.checkAll').click(function(){
        $('.organizationSelect').prop('checked',$(this).prop("checked"));
    });

    /**
     * 每行前的复选事件
     */
    $('.userSelect').click(function(){
        $('.checkAll').prop("checked",($('.organizationSelect').length == $('.organizationSelect:checked').length))
    });

    /**
     * @author : zga
     * @date : 2016-3-11
     *
     * 查询条件的改变事件
     *
     */
    $('.queryCondition').change(function(){
        window.location.href = returnOrganizationListUrl();
    });

    /**
     * @author : zga
     * @date : 2016-3-10
     *
     * 点击查询事件
     *
     */
    $('.tab_fnLi').click(function(){
        window.location.href = returnOrganizationListUrl();
    });

    /**
     * @author : zga
     * @date : 2016-3-11
     *
     * 修改基本信息
     *
     */
    $('.handle li').eq(0).click(function(){
        var $checkedOrganizations = $('.organizationSelect:checked');
        if($checkedOrganizations.length == 0){
            SSOSystem.showAlertDialog('请选择组织机构');
            return false;
        }

        if($checkedOrganizations.length > 1){
            SSOSystem.showAlertDialog('只能选择一个组织机构');
            return false;
        }

        var organizationId = $checkedOrganizations.parent().siblings('input[name="organizationId"]').val();
        var organizationCode = $checkedOrganizations.parent().siblings('input[name="organizationCode"]').val();
        var organizationName = $checkedOrganizations.parent().siblings('input[name="organizationName"]').val();
        var organizationStatus = $checkedOrganizations.parent().siblings('input[name="organizationStatus"]').val();
        var organizationCreateTime = $checkedOrganizations.parent().siblings('input[name="organizationCreateTime"]').val();

        var selectHtml = '';
        if(organizationStatus == '0'){
            selectHtml = '<option value="0" selected="selected">删除</option><option value="1">正常</option>';
        }

        if(organizationStatus == '1'){
            selectHtml = '<option value="0">删除</option><option value="1" selected="selected">正常</option>';
        }


        var dialogHtml = '组织机构码:' + organizationCode + '<br/>' +
            '<br/>组织机构名称:<input name="modifyOrganizationName" value="'+organizationName+'"/>' + '<br/>' +
            '状态:<select name="modifyOrganizationStatus">' + selectHtml + '</select>' + '<br/>' +
            '<br/> 添加时间:' + organizationCreateTime;

        var d = dialog({
            title: '修改组织机构信息',
            width : '366',
            height : '200',
            content: dialogHtml,
            okValue: '确定',
            ok: function () {
                var modifyOrganizationStatus = $('select[name="modifyOrganizationStatus"]').find("option:selected").val();
                var modifyOrganizationName = $('input[name="modifyOrganizationName"]').val();

                if(modifyOrganizationName == organizationName && modifyOrganizationStatus == organizationStatus){
                    SSOSystem.showAlertDialog('没有更改!');
                    return false;
                }

                var paramData = {
                    'organizationId' : organizationId,
                    'organizationStatus' : modifyOrganizationStatus,
                    'organizationName' : modifyOrganizationName
                };

                $.ajax({
                    url : '/organization/adminModifyOrganization.json',
                    type : 'POST',
                    data : JSON.stringify(paramData),
                    contentType: 'application/json',
                    dataType : 'json',
                    success : function(data){
                        if(data.success){
                            d.close();
                            SSOSystem.showAlertDialog('修改组织机构成功');
                            window.location.reload();
                        }
                    }
                });
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
                d.close();
            }
        });
        d.showModal();
    });

    /**
     * @author : zga
     * @date : 2016-3-11
     *
     * 删除
     *
     */
    $('.handle li').eq(1).click(function(){
        var $checkedOrganizations = $('.organizationSelect:checked');
        if($checkedOrganizations.length == 0){
            SSOSystem.showAlertDialog('请选择组织机构');
            return false;
        }

        if(!confirm("确定要删除该组织机构吗?"))return false;

        var organizations = new Array();
        $checkedOrganizations.each(function(){
            organizations.push($(this).parent().siblings("input[name='organizationId']").val());
        });

        var paramData = {
            'organizations' : organizations
        };

        $.ajax({
            url : '/organization/adminDeleteOrganization.json',
            type : 'POST',
            data : JSON.stringify(paramData),
            contentType: 'application/json',
            dataType : 'json',
            success : function(data){
                SSOSystem.showAlertDialog(ErrorMessage[data.message]);
                window.location.reload();
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
    $('#tissue_list').addClass('selected');
};
