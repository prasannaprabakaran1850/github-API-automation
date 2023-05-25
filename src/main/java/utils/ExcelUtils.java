package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    public static Object[][] readExcelData(String filePath, String sheetName) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
        XSSFSheet firstSheet = workbook.getSheet(sheetName);
        int numRows = firstSheet.getLastRowNum();
        int cols = firstSheet.getRow(0).getLastCellNum();

        Object[][] excelData = new Object[numRows-1][cols];
        for (int rowIndex = 1; rowIndex < numRows; rowIndex++) {
            XSSFRow row = firstSheet.getRow(rowIndex + 1);
            if (row != null) {
                for (int cellIndex = 0; cellIndex < cols; cellIndex++) {
                    XSSFCell cell = row.getCell(cellIndex);
                    if (cell != null) {
                        try {
                            excelData[rowIndex-1][cellIndex] = String.valueOf(cell);
                        } catch (IllegalStateException illegalStateException) {
                            System.out.println(illegalStateException);
                        }
                    }
                }
            }
        }

        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return excelData;
    }
}