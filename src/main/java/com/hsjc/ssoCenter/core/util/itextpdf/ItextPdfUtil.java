package com.hsjc.ssoCenter.core.util.itextpdf;

import com.hsjc.ssoCenter.core.constant.Constant;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : zga
 * @date : 2016-03-14
 *
 * PDF工具类
 */
public class ItextPdfUtil {
    private static Logger logger = Logger.getLogger(ItextPdfUtil.class);

    /**
     * 获取pdf内容
     *
     * @param list
     *            要替换内容
     * @param filePath
     *            模板路径
     * @return
     */
    public static byte[] readTemplateFile(List<HashMap> list, String filePath, String imgPath) throws Exception {
        Document doc = null;
        PdfWriter pdfWriter = null;
        final int margin = 20;
        ByteArrayOutputStream baos = null;
        BufferedReader bufferedReader = null;
        return getExportDocs(list, imgPath, margin, bufferedReader);
    }

    /**
     * @author : zga
     * @date : 2016-3-14
     *
     * 构造导出内容
     *
     * @param list
     * @param imgPath
     * @param margin
     * @param bufferedReader
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static byte[] getExportDocs(List<HashMap> list, String imgPath, int margin, BufferedReader bufferedReader)
            throws DocumentException, IOException {
        ByteArrayOutputStream baos;
        Document doc;
        PdfWriter pdfWriter;
        List<byte[]> resultList = new ArrayList<>();
        baos = new ByteArrayOutputStream();
        BaseFont bFont = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(bFont, 14, Font.NORMAL);
        Font font1 = new Font(bFont, 14, Font.BOLD);
        Font font2 = FontFactory.getFont("/resources/fonts/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 14);

        doc = new Document(PageSize.A4, margin, margin, margin, margin);
        pdfWriter = PdfWriter.getInstance(doc, baos);
        doc.open();

        for (int i = 0; i < list.size(); i++) {
            HashMap<String,Object> HashMap =  list.get(i);
            Image image = Image.getInstance(imgPath);
            doc.add(image);
            doc.add(new Paragraph("",font));

            doc.add(new Chunk("邀请码：",font1));
            doc.add(new Chunk(HashMap.get("inviteCode").toString() + "\n",font2));

            doc.add(new Chunk("所属学校：",font1));
            doc.add(new Chunk(HashMap.get("organizationName").toString() + "\n",font));

            doc.add(new Paragraph("使用说明：\n",font1));
            doc.add(new Chunk("1．\t请登录华师智慧教育云平台" ,font));
            doc.add(new Chunk(Constant.websiteAddress,font2));
            doc.add(new Chunk("，本邀请码适用于邀请老师加入学校；\n",font));

            doc.add(new Paragraph("2．\t已有账号的老师，请登录平台后，在“绑定邀请码”中输入邀请码；\n",font));

            doc.add(new Paragraph("3．\t未注册账号的老师，请登录平台注册新账号，同时输入邀请码即可完成绑定；\n",font));

            doc.add(new Paragraph("4．\t本邀请码只能使用一次，请妥善保管；如有泄露，请及时与学校管理员联系；\n",font));

            doc.add(new Chunk("5．\t更多信息，请查阅帮助中心：",font));
            doc.add(new Chunk("help.01edut.cn；\n",font2));

            doc.add(new Paragraph("6．\t如有问题，请致电咨询：400-800-5056\n",font));

            doc.add(new Paragraph("--------------------------------------------------------------------------------------------------------\n",font));
        }

        if(bufferedReader != null){
            bufferedReader.close();
        }
        if (doc != null) {
            doc.close();
        }
        if (pdfWriter != null) {
            pdfWriter.close();
        }
        return baos.toByteArray();
    }

    /**
     * 构造PDF table
     * @param map
     * @param baseFont
     * @return
     */
    public static PdfPTable buildPdfPTable(Map<String, Object> map, BaseFont baseFont) {
        int CELLHIGHT = 30;// 行高
        // 创建表格对象
        PdfPTable table = new PdfPTable(5);
        int[] cellsWidth = { 10, 10, 10, 10, 10 };
        try {
            table.setWidths(cellsWidth);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        // 设置表格边框颜色
        // 设置单元格的边距间隔等
        table.getDefaultCell().setPadding(0);
        table.getDefaultCell().setBorderWidth(0);
        // 单元格对象
        PdfPCell cell = new PdfPCell();
        Font font1 = new Font(baseFont, 15, Font.NORMAL);
        Paragraph cel = new Paragraph("投资者", font1);
        cel.setAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(cel);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(CELLHIGHT);
        table.addCell(cell);

        cel = new Paragraph("姓名", font1);
        cel.setAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(cel);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(CELLHIGHT);
        table.addCell(cell);

        cel = new Paragraph("出借金额", font1);
        cel.setAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(cel);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(CELLHIGHT);
        table.addCell(cell);

        cel = new Paragraph("应收本金", font1);
        cel.setAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(cel);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(CELLHIGHT);
        table.addCell(cell);

        cel = new Paragraph("应收利息", font1);
        cel.setAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(cel);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        PdfPCell pdfTableContentCell = new PdfPCell();
        pdfTableContentCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        pdfTableContentCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                pdfTableContentCell.setPhrase(new Paragraph("乙方", font1));
            }
            if (i == 1) {
                pdfTableContentCell.setPhrase(new Paragraph(map.get("touziRenName") + "", font1));
            }
            if (i == 2) {
                pdfTableContentCell.setPhrase(new Paragraph(map.get("touziMoney") + "", font1));
            }
            if (i == 3) {
                pdfTableContentCell.setPhrase(new Paragraph(map.get("touzibenjin") + "", font1));
            }
            if (i == 4) {
                pdfTableContentCell.setPhrase(new Paragraph(map.get("touziLiXi") + "", font1));
            }
            table.addCell(pdfTableContentCell);
        }
        return table;
    }


    public static Chunk buildChunkText(String content,Font font){
        Chunk underline = new Chunk(content);
        underline.setFont(font);

        return underline;
    }

    public static Map<String, Object> buildMap(String inviteCode,String schoolName,String website) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("inviteCode", inviteCode);
        map.put("schoolName", schoolName);
        map.put("website", website);
        return map;
    }
}
