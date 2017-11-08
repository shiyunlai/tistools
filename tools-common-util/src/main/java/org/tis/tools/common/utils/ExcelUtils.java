package org.tis.tools.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoch on 2017/7/19.
 */
public class ExcelUtils {
    /**
     * 判断不同类型获取单元格数据
     *
     * @param cell
     * @return
     */
    public static String getCellVal(Cell cell) {
        String cellValue = "";
        if (null != cell) {
            // 以下是判断数据的类型
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                    cellValue = cell.getNumericCellValue() + "";
                    break;

                case HSSFCell.CELL_TYPE_STRING: // 字符串
                    cellValue = cell.getStringCellValue();
                    break;

                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                    cellValue = cell.getBooleanCellValue() + "";
                    break;

                case HSSFCell.CELL_TYPE_FORMULA: // 公式
                    cellValue = cell.getCellFormula() + "";
                    break;

                case HSSFCell.CELL_TYPE_BLANK: // 空值
                    cellValue = "";
                    break;

                case HSSFCell.CELL_TYPE_ERROR: // 故障
                    cellValue = "";
                    break;

                default:
                    cellValue = "";
                    break;
            }
        }
        return cellValue;
    }

    /**
     * 判断是否是Excel2003
     */
    public static boolean isExcel2003(String filename)
    {
        return filename.matches("^.+\\.(?i)(xls)$");
    }

    /**
     *判断是否是excel2007
     */
    public static boolean isExcel2007(String filename)
    {
        return filename.matches("^.+\\.(?i)(xlsx)$");
    }

    public static String getRowCellStr(Row row, int columnIndex)
    {
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return null;
        }
        String strval = null;
        columnIndex = cell.getColumnIndex();
        switch (cell.getCellType()) {
            case 1:
                strval = cell.getRichStringCellValue().getString();
                break;
            case 0:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    strval = sdf.format(cell.getDateCellValue());
                } else {
                    double d = cell.getNumericCellValue();
                    strval = "" + d;
                    if (d - (int) d < Double.MIN_VALUE) {
                        strval = new BigDecimal(d).longValue()+"";
                    } else {
                        strval = "" + d;
                    }
                }
                break;
            case 4:
                strval = "" + cell.getBooleanCellValue();
                break;
            case 2:
                break;
            case 3:
        }

        return strval;
    }

    public static List<String> getRowCellStrList(Row row) {
        List rowList = new ArrayList();
        for (Cell cell : row) {
            String strval = null;
            switch (cell.getCellType()) {
                case 1:
                    strval = cell.getRichStringCellValue().getString();
                    break;
                case 0:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        strval = sdf.format(cell.getDateCellValue());
                    } else {
                        strval = "" + cell.getNumericCellValue();
                    }
                    break;
                case 4:
                    strval = "" + cell.getBooleanCellValue();
                    break;
                case 2:
                    break;
                case 3:
            }

            rowList.add(strval);
        }
        return rowList;
    }
}
