package com.hsjc.ssoCenter.core.util.excel;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * @author zga
 * @date : 2016-3-14
 *
 * 单元格格式定义
 *
 */
public class XlsxCellStyle {
    public static String CELL_TYPE_BLANK = "blank";
    public static String CELL_TYPE_ERROR = "error";
    public static String CELL_TYPE_BOOLEAN = "boolean";
    public static String CELL_TYPE_TEXT = "text";
    public static String CELL_TYPE_NUMBER = "number";
    public static String CELL_TYPE_PERCENT = "percent";
    public static String CELL_TYPE_DATE = "date";

    public static Short ALIGN_LEFT = XSSFCellStyle.ALIGN_LEFT;
    public static Short ALIGN_CENTER = XSSFCellStyle.ALIGN_CENTER;
    public static Short ALIGN_RIGHT = XSSFCellStyle.ALIGN_RIGHT;

    public static String SPLIT_COMMA = ",";
    public static String SPLIT_DOT = ".";
    public static String SPLIT_BLANK = " ";

    public static String FORMAT_DATE_DEFAULT = "yyyy-mm-dd";
    public static String FORMAT_TIME_DEFAULT = "HH:mm:ss";
    public static String FORMAT_DATETIME_DEFAULT = "yyyy-mm-dd HH:mm:ss";

    public static int TEXT_WRAP_LENGTH = 20;

    // 单元格类型
    private String cellType;
    // 是否为公式
    private boolean formula;
    // 千分位
    private String numberKc;
    // 小数点
    private String numberDot;
    // 精度
    private Integer numberDgits;
    // 日期格式
    private String dateFormat;

    public XlsxCellStyle(String cellType) {
        super();
        this.formula = false;
        this.cellType = cellType;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    public boolean isFormula() {
        return formula;
    }

    public void setFormula(boolean formula) {
        this.formula = formula;
    }

    public String getNumberKc() {
        return numberKc;
    }

    public void setNumberKc(String numberKc) {
        this.numberKc = numberKc;
    }

    public String getNumberDot() {
        return numberDot;
    }

    public void setNumberDot(String numberDot) {
        this.numberDot = numberDot;
    }

    public Integer getNumberDgits() {
        return numberDgits;
    }

    public void setNumberDgits(Integer numberDgits) {
        this.numberDgits = numberDgits;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String toString() {
        return "XlsxCellStyle [" + (cellType != null ? "cellType=" + cellType + ", " : "")
                + "formula=" + formula + ", "
                + (numberKc != null ? "numberKc=" + numberKc + ", " : "")
                + (numberDot != null ? "numberDot=" + numberDot + ", " : "")
                + (numberDgits != null ? "numberDgits=" + numberDgits + ", " : "")
                + (dateFormat != null ? "dateFormat=" + dateFormat : "") + "]";
    }

}
