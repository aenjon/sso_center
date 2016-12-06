package com.hsjc.ssoCenter.core.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author zga
 * @date : 2016-3-14
 *
 * Excel2007 导入工具类
 * 使用poi插件，提供把简单的xlsx文件读取为数据集合的功能
 * 读取结果为List<Map<String 表头, Object 单元格数据>>格式
 * 暂未实现读取合并单元格功能
 * 暂未实现无表头表格的读取功能
 *
 */
public class XlsxReader {
  private XSSFWorkbook workbook;
  private XSSFSheet sheet;
  private int rowIndex;
  private XSSFFormulaEvaluator fe;

  private boolean hasHeader = true;
  private HashMap<Integer, String> header;

  public XlsxReader(InputStream is) throws IOException {
    workbook = new XSSFWorkbook(is);
    sheet = workbook.getSheetAt(0);
    if (workbook != null) {
      fe = new XSSFFormulaEvaluator(workbook);
    }
  }

  /**
   * main method
   * read excel
   * 
   * @return
   */
  public List<Map<String, Object>> readXlsWithHeader() {
    List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
    if (!hasHeader) return records;

    int sheetNum = workbook.getNumberOfSheets();
    for (int s = 0; s < sheetNum; s++) {
      sheet = workbook.getSheetAt(s);
      // read header
      rowIndex = sheet.getFirstRowNum();
      readHeaderRow();
      // read data
      rowIndex = sheet.getFirstRowNum() + 1;
      for (int rMax = sheet.getLastRowNum(); rowIndex <= rMax; rowIndex++) {
        readDataRow(records);
      }
    }
    return records;
  }

  /**
   * read header row
   */
  private void readHeaderRow() {
    Map<Integer, Object> rowData = readRow();
    if (rowData != null) {
      header = new HashMap<Integer, String>();
      Set<Integer> keys = rowData.keySet();
      for (Integer key : keys) {
        String content = (String) rowData.get(key);
        if (content != null && content.length() > 0) {
          header.put(key, content);
        }
      }
    }
  }

  /**
   * read data row
   * 
   * @param records
   */
  private void readDataRow(List<Map<String, Object>> records) {
    Map<Integer, Object> rowData = readRow();
    if (rowData != null) {
      // build rocord
      Map<String, Object> record = new HashMap<String, Object>();
      Set<Integer> keys = header.keySet();
      for (Integer key : keys) {
        record.put(header.get(key), rowData.get(key));
      }
      records.add(record);
    }
  }

  /**
   * read a row
   * 
   * @return
   */
  private Map<Integer, Object> readRow() {
    XSSFRow row = sheet.getRow(rowIndex);
    if (row == null) return null;

    Map<Integer, Object> rowData = new HashMap<Integer, Object>();
    for (int idx = row.getFirstCellNum(), end = row.getLastCellNum(); idx <= end; idx++) {
      Cell cell = row.getCell(idx);
      rowData.put(idx, readCell(cell));
    }
    return rowData;
  }

  /**
   * read a cell
   * 
   * @param cell
   * @return
   */
  private Object readCell(Cell cell) {
    if (cell == null) return null;

    Object cellContent;
    int cellType = cell.getCellType();

    switch(cellType){
      case Cell.CELL_TYPE_STRING :
        cellContent = cell.getRichStringCellValue().getString();
        break;//字符串
      case Cell.CELL_TYPE_NUMERIC :
        long dd = (long)cell.getNumericCellValue();
        cellContent = dd+"";
        break; //数字
      case Cell.CELL_TYPE_BLANK :
        cellContent = "";
        break;
      case Cell.CELL_TYPE_FORMULA :
        cellContent = String.valueOf(cell.getCellFormula());
        break;
      case Cell.CELL_TYPE_BOOLEAN :
        cellContent = String.valueOf(cell.getBooleanCellValue());
        break;//boolean型值
      case Cell.CELL_TYPE_ERROR :
        cellContent = String.valueOf(cell.getErrorCellValue());
        break;
      default:
        cellContent = null;
        break;
    }
    return cellContent;
  }

  /**
   * read formula calculate result
   * 
   * @param cell
   * @return
   */
  private Object readFormula(Cell cell) {
    if (cell == null) return null;

    Object cellContent;
    CellValue cellValue = fe.evaluate(cell);
    int valueType = cellValue.getCellType();
    switch (valueType) {
    case Cell.CELL_TYPE_STRING:
      cellContent = cellValue.getStringValue();
      break;
    case Cell.CELL_TYPE_BOOLEAN:
      cellContent = cellValue.getBooleanValue();
      break;
    case Cell.CELL_TYPE_NUMERIC:
      cellContent = cellValue.getNumberValue();
      break;
    case Cell.CELL_TYPE_BLANK:
    case Cell.CELL_TYPE_ERROR:
      cellContent = null;
      break;
    default:
      cellContent = cellValue.formatAsString();
      break;
    }
    return cellContent;
  }

  public boolean isHasHeader() {
    return hasHeader;
  }

  public void setHasHeader(boolean hasHeader) {
    this.hasHeader = hasHeader;
  }

}
