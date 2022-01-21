package com.sqber.commonTool.excel;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CommonCSV {

    public static CommonCSV create(String filePath, String[] firstRow, String charset) {
        return new CommonCSV(filePath, true, firstRow, charset);
    }

    private String filePath;
    private boolean firstRowHeader;
    private String[] firstRow;
    private String charset;

    public CommonCSV(String filePath, boolean firstRowHeader, String[] firstRow, String charset) {
        this.filePath = filePath;
        this.firstRowHeader = firstRowHeader;
        this.firstRow = firstRow;
        this.charset = charset;
    }

    public SaveResult handle() throws IOException {

        StringBuilder msgBuilder = new StringBuilder();
        List<List<String>> data = new ArrayList<>();

        CsvReader csv = CsvReader.builder().build(Paths.get(filePath), Charset.forName(charset));
        int i = 0;
        for (CsvRow item : csv) {
            List<String> list = item.getFields();
            if (this.firstRowHeader && i == 0) {
                try {
                    validateFirstRow(list);
                } catch (ExcelValiException ex) {
                    msgBuilder.append(
                            StringUtils.isEmpty(msgBuilder.toString()) ? ex.getMessage() : ex.getMessage() + ";"
                    );
                    break;
                }
            } else {
                if (!isEmpty(list)) {
                    data.add(list);
                }
            }
            i++;
        }

        return StringUtils.isEmpty(msgBuilder.toString()) ?
                new SaveResult(data) :
                new SaveResult(false, msgBuilder.toString());
    }

    private void validateFirstRow(List<String> rowVal) throws ExcelValiException {
        if (firstRow.length != rowVal.size()) {
            throw new ExcelValiException("表头和模板不符");
        }

        for (int i = 0; i < rowVal.size(); i++) {
            if (!rowVal.get(i).equalsIgnoreCase(firstRow[i])) {
                throw new ExcelValiException("表头和模板不符");
            }
        }
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
}
