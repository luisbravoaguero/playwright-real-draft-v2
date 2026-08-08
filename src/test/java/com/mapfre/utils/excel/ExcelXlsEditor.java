package com.mapfre.utils.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExcelXlsEditor {
    private ExcelXlsEditor() {}

    /**
     * Updates a single cell and saves the workbook in-place.
     * rowIndex/colIndex are 0-based (POI standard).
     */
    public static void updateCell(Path excelFile, String sheetName, int rowIndex, int colIndex, String value) {
        try (InputStream in = Files.newInputStream(excelFile);
             Workbook wb = WorkbookFactory.create(in)) { // auto-detect .xls/.xlsx :contentReference[oaicite:2]{index=2}

            Sheet sheet = (sheetName != null && !sheetName.isBlank())
                    ? wb.getSheet(sheetName)
                    : wb.getSheetAt(0);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            Row row = sheet.getRow(rowIndex);
            if (row == null) row = sheet.createRow(rowIndex);

            Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

            // Tip: for “9 digits”, set as String to preserve leading zeros (if any)
            cell.setCellValue(value);

            // Write back (overwrite the file)
            try (OutputStream out = Files.newOutputStream(excelFile)) {
                wb.write(out);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update Excel cell: " + excelFile, e);
        }
    }
}