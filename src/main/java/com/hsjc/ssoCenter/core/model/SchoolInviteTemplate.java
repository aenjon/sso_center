package com.hsjc.ssoCenter.core.model;

/**
 * Created by Administrator on 2015/6/3.
 */

import com.hsjc.ssoCenter.core.util.excel.XlsxCellStyle;
import com.hsjc.ssoCenter.core.util.excel.XlsxWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2016-3-14
 *
 * 邀请码导出模板类
 *
 */
public class SchoolInviteTemplate {
    private int columnCount = 9;  //列数
    private String[] header;  //表头
    private XlsxCellStyle[] styles;
    private List<String[]> data;

    /**
     * 生成Excel数据
     *
     * @param orders
     * @return
     * @throws IOException
     */
    public byte[] generateExcel(List<HashMap<String, Object>> orders) throws IOException {
        defineHeaders();
        defineStyles();
        transData(orders);
        XlsxWriter writer = new XlsxWriter(header, styles, data);
        return writer.writeSimpleXlsFile();
    }

    /**
     * 定义表头
     */
    private void defineHeaders() {
        header = new String[columnCount];
        header[0] = "订单编号";
        header[1] = "手机号";
        header[2] = "用户名";
        header[3] = "公司名称";
        header[4] = "注册金额";
        header[5] = "经营范围";
        header[6] = "股东姓名";
        header[7] = "股东身份证";
        header[8] = "股东股份";
    }

    /**
     * 定义列格式
     */
    private void defineStyles() {
        styles = new XlsxCellStyle[columnCount];
        styles[0] = new XlsxCellStyle(XlsxCellStyle.CELL_TYPE_NUMBER);
        styles[1] = new XlsxCellStyle(XlsxCellStyle.CELL_TYPE_TEXT);
        styles[2] = new XlsxCellStyle(XlsxCellStyle.CELL_TYPE_TEXT);
        styles[3] = new XlsxCellStyle(XlsxCellStyle.CELL_TYPE_TEXT);
        styles[4] = new XlsxCellStyle(XlsxCellStyle.CELL_TYPE_NUMBER);
        styles[5] = new XlsxCellStyle(XlsxCellStyle.CELL_TYPE_TEXT);
        styles[6] = new XlsxCellStyle(XlsxCellStyle.CELL_TYPE_TEXT);
        styles[7] = new XlsxCellStyle(XlsxCellStyle.CELL_TYPE_TEXT);
        styles[8] = new XlsxCellStyle(XlsxCellStyle.CELL_TYPE_NUMBER);
    }

    /**
     * 转换数据
     * 所有数据转成String，日期转为毫秒数String
     *
     * @param
     */
    private void transData(List<HashMap<String, Object>> list) {
        data = new ArrayList<String[]>();
        if (list != null && !list.isEmpty()) {
            String[] oneData = new String[columnCount];
            for(int i= 0 ;i < list.size();i++){
                oneData[0] = list.get(i).get("id").toString();
                oneData[1] = list.get(i).get("phone").toString();
                oneData[2] = list.get(i).get("name").toString();
                oneData[3] = list.get(i).get("cname").toString();
                oneData[4] = list.get(i).get("amount").toString();
                oneData[5] = list.get(i).get("business_scope_desc").toString();
                oneData[6] = list.get(i).get("name").toString();
                oneData[7] = list.get(i).get("card").toString();
                oneData[8] = list.get(i).get("share").toString();
                data.add(oneData);
            }
        }
    }
}
