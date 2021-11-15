package com.sqber.commonTool.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CommonExcel {

    public static CommonExcel create(String filePath, IResultHandler handler) {
        return new CommonExcel(filePath, true, handler);
    }

    /**
     * excel 结果处理接口
     */
    public interface IResultHandler {

        /**
         * 验证表头
         * @param sheetName sheet 页名称
         * @param rowVal 表头数值
         * @throws ExcelValiException 验证异常
         */
        void validateFirstRow(String sheetName, List<String> rowVal) throws ExcelValiException;

        /**
         * 保存某行数据
         * @param rowVal 行数据
         * @param rowIndex 行索引
         * @param totalRow 总共行数
         * @return
         */
        boolean store(List<String> rowVal, int rowIndex, int totalRow);
    }

    private String filePath;
    private boolean isExcel2003;
    private Workbook wb = null;
    private IResultHandler resultHandler;
    private boolean debug = false;
    private boolean firstRowHeader = true;

    public CommonExcel(String filePath, boolean firstRowHeader, IResultHandler handler) {
        this.filePath = filePath;
        this.resultHandler = handler;
        this.firstRowHeader = firstRowHeader;
        isExcel2003 = filePath.matches("^.+\\.(?i)(xls)$");
    }

    public void enableDebug() {
        this.debug = true;
    }

    /**
     * 处理
     * <p>excel数据可以从 SaveResult 获取，也可以从 IResultHandler.store 方法中获取</p>
     * @return
     * @throws IOException
     */
    public SaveResult handle() throws IOException {
        InputStream stream = new FileInputStream(filePath);

        wb = WorkbookFactory.create(stream);
//        workbook
//
//        if (isExcel2003) {
//            try{
//                wb = new HSSFWorkbook(stream);
//            }catch (Exception exception){
//                wb = new XSSFWorkbook(stream);
//            }
//        } else {
//            try{
//                wb = new XSSFWorkbook(stream);
//            }catch (Exception exception){
//                wb = new HSSFWorkbook(stream);
//            }
//        }
        StringBuilder msgBuilder = new StringBuilder();
        List<List<String>> data = new ArrayList<>();

        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);

            if (sheet == null || sheet.getLastRowNum() == 0) {
                continue;
            }

            Row row = null;
            int totalRow = sheet.getLastRowNum();
            if (this.debug) {
                System.out.println("共有" + totalRow + "行");
            }

            short minColIx = 0;
            short maxColIx = getMaxColIx(sheet);
            for (int r = 0; r <= sheet.getLastRowNum(); r++) {
                row = sheet.getRow(r);
//                short minColIx = row.getFirstCellNum();
//                short maxColIx = row.getLastCellNum();

                List<String> rowVal = new ArrayList<>();
                for (short colIx = minColIx; colIx < maxColIx; colIx++) {

                    Cell cell = row.getCell(colIx);
                    String cellVal = getCellVal(cell);
                    rowVal.add(cellVal.trim());

                    if (this.debug) {
                        System.out.println(String.format("cellVal:%s", cellVal));
                    }
                }
                if (this.firstRowHeader && r == 0) {
                    try {
                        resultHandler.validateFirstRow(sheet.getSheetName(), rowVal);
                    } catch (ExcelValiException ex) {
                        msgBuilder.append(
                                StringUtils.isEmpty(msgBuilder.toString()) ? ex.getMessage() : ex.getMessage() + ";"
                        );
                        break;
                    }
                } else {
                    if (!isEmpty(rowVal)) {
                        if (!resultHandler.store(rowVal, r, sheet.getLastRowNum())) {
                            break;
                        }
                        data.add(rowVal);
                    }
                }
            }
        }
        return StringUtils.isEmpty(msgBuilder.toString()) ?
                new SaveResult(data) :
                new SaveResult(false, msgBuilder.toString());
    }

    /**
     * 最大列数，按有值得单元格个数来计算，而不是 getLastCellNum() 来计算
     */
    private short getMaxColIx(Sheet sheet) {
        short maxColIx = 0;
        if (sheet.getLastRowNum() > 0) {
            Row firstRow = sheet.getRow(0);
            // 先默认最大值就是 getLastCellNum
            maxColIx = firstRow.getLastCellNum();
            for (short colIx = 0; colIx < firstRow.getLastCellNum(); colIx++) {
                Cell cell = firstRow.getCell(colIx);
                String cellVal = getCellVal(cell);

                if (StringUtils.isEmpty(cellVal)) {
                    maxColIx = colIx;
                    break;
                }
            }
        }
        return maxColIx;
    }

    private boolean isEmpty(List<String> rowVal) {
        boolean empty = true;
        for (String item : rowVal) {
            if (!StringUtils.isEmpty(item)) {
                empty = false;
                return empty;
            }
        }
        return empty;
    }

    private String getCellVal(Cell cell) {
        if (cell == null) {
            return "";
        }
        String cellVal = "";
        if (cell.getCellTypeEnum() == CellType.NUMERIC) {

            java.text.DecimalFormat formatter = new java.text.DecimalFormat("########.####");
            cellVal = formatter.format(cell.getNumericCellValue());

        } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            cellVal = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
            try {
                cellVal = cell.getStringCellValue();
            } catch (IllegalStateException e) {
                cellVal = String.valueOf(cell.getNumericCellValue());
            }
        } else {
            cellVal = cell.getStringCellValue();
        }
        return cellVal;
    }


}
