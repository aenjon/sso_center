package com.hsjc.ssoCenter.core.util.excel;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : zga
 * @date : 2016-3-14
 *
 * Excel2007 导出工具类
 * 使用poi插件，提供基本的把查询结果列表导出为xlsx文件的功能
 * 本工具要求数据对象各自段均为String类型，且日期应当以毫秒数的文本形式提供
 * 本工具要求数据对象中定义的表头和列类型的数组长度与数据的列数一致，否则可能出现错误
 * 如果数据对象中未定义列类型，将默认全部按文本处理
 * 暂未实现合并单元格功能
 *
 */
public class XlsxWriter {
    private ByteArrayOutputStream output;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;
    private XSSFCell cell;
    private int rowIndex;
    private int colIndex;
    private int[] colWidth;
    private XSSFDataFormat format;
    private Map<String, XSSFCellStyle> cellStyles;

    private String[] header;
    private XlsxCellStyle[] colStyle;
    private List<String[]> records;

    public XlsxWriter(String[] header, XlsxCellStyle[] colType, List<String[]> records) {
        init();
        this.header = header;
        this.colStyle = colType;
        this.records = records;
    }

    private void init() {
        this.output = new ByteArrayOutputStream();
        this.workbook = new XSSFWorkbook();
        this.format = workbook.createDataFormat();
        this.cellStyles = new HashMap<String, XSSFCellStyle>();
        this.createNewSheet();
    }

    /**
     * 创建新的Sheet，并设置新sheet为活动sheet
     * Sheet命名规则：“Sheet”+序号
     */
    private void createNewSheet() {
        int n = workbook.getNumberOfSheets();
        String sheetName = "Sheet" + (n + 1);
        sheet = workbook.createSheet(sheetName);
        rowIndex = -1;
        colIndex = -1;
    }

    /**
     * 新的一行
     */
    private void createNewRow() {
        rowIndex++;
        row = sheet.createRow(rowIndex);
        colIndex = -1;
    }

    /**
     * 新的单元格
     */
    private void createNewCell() {
        colIndex++;
        cell = row.createCell(colIndex);
        cell.setAsActiveCell();
    }

    /**
     * 方法入口
     *
     * @return
     * @throws IOException
     */
    public byte[] writeSimpleXlsFile() throws IOException {
        // 如果没有数据
        if (records == null || records.size() == 0) {
            // 如果表头也没有，返回空Excel
            if (header == null) {
                createNewRow();
                createNewCell();
                writeEmptyCell();
            } else {
                colWidth = new int[header.length];
                writeHeader();
            }
        }
        // 如果有数据
        else {
            // 如果没有表头
            if (header == null) {
                colWidth = new int[records.get(0).length];
            } else {
                colWidth = new int[Math.max(header.length, records.get(0).length)];
                writeHeader();
            }
            writeRecords();
        }

        // 调整所有 sheet的列宽
        sizeColumn();

        workbook.write(output);
        byte[] bytes = output.toByteArray();
        output.flush();
        output.close();
        return bytes;
    }

    /**
     * 调整所有 sheet的列宽
     */
    private void sizeColumn() {
        int sheetCount = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            XSSFSheet currSheet = workbook.getSheetAt(i);
            for (int j = 0; j < colWidth.length; j++) {
                currSheet.autoSizeColumn(j);
                // 如果实际宽度小于表头宽度，重置为表头宽度
                int currWidth = currSheet.getColumnWidth(j) + 512;
                currSheet.setColumnWidth(j, currWidth < colWidth[j] ? colWidth[j] : currWidth);
            }
        }
    }

    /**
     * 写表头
     */
    private void writeHeader() {
        createNewRow();
        for (int i = 0; i < header.length; i++) {
            createNewCell();
            cell.setCellValue(header[i]);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellStyle(getTextStyle(null, XSSFCellStyle.ALIGN_CENTER, true, false));
            colWidth[i] = (calcLength(header[i]) + 2) * 256;
        }
    }

    /**
     * 写数据
     */
    private void writeRecords() {
        for (String[] record : records) {
            // 如果一个Sheet写满了，创建新的sheet
            if (rowIndex > 65534) {
                createNewSheet();
                writeHeader();
            }

            createNewRow();
            for (String text : record) {
                createNewCell();
                XlsxCellStyle styleDfn = (colStyle == null || colStyle.length <= colIndex)
                        ? null : colStyle[colIndex];
                if (text == null) {
                    text = "";
                }
                writeCellSelective(text, styleDfn);
            }
        }
    }

    /**
     * 根据列定义，自动选择写入单元格的类型
     *
     * @param text
     * @param styleDfn
     */
    private void writeCellSelective(String text, XlsxCellStyle styleDfn) {
        // 未定义格式，按文本处理
        if (styleDfn == null) {
            writeTextCell(text, styleDfn);
        }
        // 公式
        else if (styleDfn.isFormula()) {
            writeFormulaCell(text, styleDfn);
        }
        // 空单元格
        else if (XlsxCellStyle.CELL_TYPE_BLANK.equals(styleDfn.getCellType())) {
            writeEmptyCell();
        }
        // 数字
        else if (XlsxCellStyle.CELL_TYPE_NUMBER.equals(styleDfn.getCellType())) {
            writeNumberCell(text, styleDfn);
        }
        // 百分比数字
        else if (XlsxCellStyle.CELL_TYPE_PERCENT.equals(styleDfn.getCellType())) {
            writeNumberCell(text, styleDfn);
        }
        // 日期
        else if (XlsxCellStyle.CELL_TYPE_DATE.equals(styleDfn.getCellType())) {
            writeDateCell(text, styleDfn);
        }
        // 其他，按文本处理
        else {
            writeTextCell(text, styleDfn);
        }
    }

    /**
     * 公式单元格
     *
     * @param text
     * @param styleDfn
     */
    private void writeFormulaCell(String text, XlsxCellStyle styleDfn) {
        cell.setCellFormula(text);
        cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);

        XSSFCellStyle cellStyle = null;
        // 数字
        if (XlsxCellStyle.CELL_TYPE_NUMBER.equals(styleDfn.getCellType())) {
            cellStyle = getNumberStyle(styleDfn.getNumberKc(), styleDfn.getNumberDgits(),
                    styleDfn.getNumberDot());
        }
        // 百分比数字
        else if (XlsxCellStyle.CELL_TYPE_PERCENT.equals(styleDfn.getCellType())) {
            cellStyle = getPercentStyle(styleDfn.getNumberKc(), styleDfn.getNumberDgits(),
                    styleDfn.getNumberDot());
        }
        // 日期
        else if (XlsxCellStyle.CELL_TYPE_DATE.equals(styleDfn.getCellType())) {
            cellStyle = getDateStyle(styleDfn.getDateFormat());
        }
        // 其他，按文本处理
        else {
            Short color = null;
            boolean wrap = true;
            if (XlsxCellStyle.CELL_TYPE_ERROR.equals(styleDfn.getCellType())) {
                color = XSSFFont.COLOR_RED;
                wrap = false;
            } else if (XlsxCellStyle.CELL_TYPE_BOOLEAN.equals(styleDfn.getCellType())) {
                wrap = false;
            }
            cellStyle = getTextStyle(color, XSSFCellStyle.ALIGN_CENTER, false, wrap);
        }
        cell.setCellStyle(cellStyle);
    }

    /**
     * 空单元格
     */
    private void writeEmptyCell() {
        cell.setCellValue("");
        cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
    }

    /**
     * 数字单元格
     *
     * @param text
     * @param styleDfn
     */
    private void writeNumberCell(String text, XlsxCellStyle styleDfn) {
        // 把文本值转换为数值
        BigDecimal value = null;
        try {
            value = new BigDecimal(text);
        } catch (Exception e) {
            // 如果不是数字，作为文本处理
            writeTextCell(text, styleDfn);
            return;
        }

        // 数字整数位长度超过15，作为文本处理
        if (value.precision() - value.scale() > 15) {
            // 如果定义了精度，按精度格式化，但忽略千分位
            if (styleDfn.getNumberDgits() != null) {
                value = value.setScale(styleDfn.getNumberDgits(), BigDecimal.ROUND_HALF_UP);
            }
            cell.setCellValue(value.toPlainString());
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            XSSFCellStyle cellStyle = getTextStyle(null, XSSFCellStyle.ALIGN_RIGHT, false, false);
            cell.setCellStyle(cellStyle);
        }
        // 否则，正常显示为数字
        else {
            cell.setCellValue(value.doubleValue());
            cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
            XSSFCellStyle cellStyle = null;
            if (XlsxCellStyle.CELL_TYPE_PERCENT.equals(styleDfn.getCellType())) {
                cellStyle = getPercentStyle(styleDfn.getNumberKc(),
                        styleDfn.getNumberDgits(), styleDfn.getNumberDot());
            } else {
                cellStyle = getNumberStyle(styleDfn.getNumberKc(),
                        styleDfn.getNumberDgits(), styleDfn.getNumberDot());
            }
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * 日期单元格
     *
     * @param text
     * @param styleDfn
     */
    private void writeDateCell(String text, XlsxCellStyle styleDfn) {
        // 把文本转换为日期值
        Date date = null;
        try {
            date = new Date(Long.parseLong(text));
        } catch (Exception e) {
            // 如果日期格式不被识别，作为文本处理
            writeTextCell(text, styleDfn);
            return;
        }

        cell.setCellValue(date);
        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
        XSSFCellStyle cellStyle = getDateStyle(styleDfn.getDateFormat());
        cell.setCellStyle(cellStyle);
    }

    /**
     * 文本单元格
     *
     * @param text
     * @param styleDfn
     */
    private void writeTextCell(String text, XlsxCellStyle styleDfn) {
        if (text == null) text = "";

        cell.setCellValue(text);
        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
        Short color = null;
        if (styleDfn != null && XlsxCellStyle.CELL_TYPE_ERROR.equals(styleDfn.getCellType())) {
            color = XSSFFont.COLOR_RED;
        }
        boolean wrap = calcLength(text) > XlsxCellStyle.TEXT_WRAP_LENGTH;
        XSSFCellStyle cellStyle = getTextStyle(color, XSSFCellStyle.ALIGN_CENTER, false, wrap);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 计算字符串长度，中文字符=2
     *
     * @param string
     * @return
     */
    private int calcLength(String string) {
        int length;
        if (StringUtils.isEmpty(string)) {
            length = 0;
        } else {
            try {
                length = string.getBytes("gb2312").length;
            } catch (Exception ex) {
                length = string.length();
            }
        }
        return length;
    }

    /**
     * 创建数字格式
     *
     * @param kc
     * @param dgits
     * @param dot
     * @return
     */
    private XSSFCellStyle getNumberStyle(String kc, Integer dgits, String dot) {
        String styleName = "number_dgits[" + dgits + "]_kc[" + kc + "]_dot[" + dot + "]";
        XSSFCellStyle numberStyle = cellStyles.get(styleName);

        if (numberStyle == null) {
            numberStyle = workbook.createCellStyle();
            numberStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            if (kc != null || dgits != null) {
                String pattern = createNumberPattern(kc, dgits, dot);
                numberStyle.setDataFormat(format.getFormat(pattern));
            }
            this.cellStyles.put(styleName, numberStyle);
        }

        return numberStyle;
    }

    /**
     * 创建百分比数字格式
     *
     * @param kc
     * @param dgits
     * @param dot
     * @return
     */
    private XSSFCellStyle getPercentStyle(String kc, Integer dgits, String dot) {
        String styleName = "percent_dgits[" + dgits + "]_kc[" + kc + "]_dot[" + dot + "]";
        XSSFCellStyle percentStyle = cellStyles.get(styleName);

        if (percentStyle == null) {
            percentStyle = workbook.createCellStyle();
            percentStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            String pattern = createNumberPattern(kc, dgits, dot) + "%";
            percentStyle.setDataFormat(format.getFormat(pattern));
            this.cellStyles.put(styleName, percentStyle);
        }

        return percentStyle;
    }

    /**
     * 拼装数字格式字符串
     *
     * @param kc
     * @param dgits
     * @param dot
     * @return
     */
    private String createNumberPattern(String kc, Integer dgits, String dot) {
        String pattern;
        if (kc != null
                && (kc.equals(XlsxCellStyle.SPLIT_BLANK)
                        || kc.equals(XlsxCellStyle.SPLIT_COMMA)
                        || kc.equals(XlsxCellStyle.SPLIT_DOT))) {
            pattern = "#" + kc + "##0";
        } else {
            pattern = "#0";
        }

        if (dgits != null && dgits > 0) {
            if (dot != null
                    && (dot.equals(XlsxCellStyle.SPLIT_DOT)
                    || dot.equals(XlsxCellStyle.SPLIT_COMMA))) {
                pattern += dot;
            } else if (kc != null && kc.equals(XlsxCellStyle.SPLIT_DOT)) {
                pattern += XlsxCellStyle.SPLIT_COMMA;
            } else {
                pattern += XlsxCellStyle.SPLIT_DOT;
            }
            for (int i = 0; i < dgits; i++) {
                pattern += "0";
            }
        }

        return pattern;
    }

    /**
     * 创建日期格式
     *
     * @param pattern
     * @return
     */
    private XSSFCellStyle getDateStyle(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = XlsxCellStyle.FORMAT_DATETIME_DEFAULT;
        }
        String styleName = "date_" + pattern;
        XSSFCellStyle dateStyle = this.cellStyles.get(styleName);

        if (dateStyle == null) {
            dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(format.getFormat(pattern));
            dateStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            this.cellStyles.put(styleName, dateStyle);
        }

        return dateStyle;
    }

    /**
     * 创建文本格式
     *
     * @param color
     * @param align
     * @param bold
     * @param wrap
     * @return
     */
    private XSSFCellStyle getTextStyle(Short color, Short align, boolean bold, boolean wrap) {
        String styleName = "text_color[" + color + "]_align[" + align + "]_bold[" + bold
                + "]_wrap[" + wrap + "]";
        XSSFCellStyle textStyle = cellStyles.get(styleName);

        if (textStyle == null) {
            textStyle = workbook.createCellStyle();
            textStyle.setAlignment(align);
            XSSFFont textFont = workbook.createFont();
            if (color != null) {
                textFont.setColor(color);
            }
            if (bold) {
                textFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            }
            textStyle.setFont(textFont);
            if (wrap) {
                textStyle.setWrapText(true);
            }
            this.cellStyles.put(styleName, textStyle);
        }

        return textStyle;
    }

}
