var SSOSystem = {
    /**
     * @author : zga
     * @date : 2016-01-12
     *
     * SSO系统Js工具类
     *
     */
    time : function(o,wait,buttonVal){
        if (wait == 0) {
            o.val(buttonVal);
            o.removeAttr("disabled");
        } else {
            o.attr("disabled", true);
            o.val("重新发送(" + wait + " s)");
            wait--;
            setTimeout(function () {
                SSOSystem.time(o, wait, buttonVal);
            }, 1000)
        }
    },

    /**
     * @author : zga
     * @date : 2016-1-28
     *
     * SSO系统退出函数
     *
     */
    exit : function(){
        /*window.opener = null;
        window.open("", "_self");
        window.close();*/
        //window.open("/page/logout.html",'_blank');
        window.location.href = '/page/logout.html';
    },

    /**
     * @author : zga
     * @date : 2016-3-11
     *
     * 判断字符串是否为空
     *
     * @param str
     * @returns {boolean}
     */
    isEmpty : function(str){
        if(str == '' || str == null || str == undefined || str == 'undefined'){
            return true;
        }
        return false;
    },

    /**
     * @author : zga
     * @date : 2016-3-11
     *
     * artDialog 弹出窗口
     *
     * @param title
     * @param content
     */
    showAlertDialog : function(content,title,modalType){
        if(SSOSystem.isEmpty(title)) title = '提示信息';
        var d = dialog({
            title: title,
            content: content
        });
        if(SSOSystem.isEmpty(modalType)){
            d.show();
        } else {
            d.showModal();
        }
    }
};