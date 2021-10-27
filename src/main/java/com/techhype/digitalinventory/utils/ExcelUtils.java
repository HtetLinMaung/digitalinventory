package com.techhype.digitalinventory.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.myanmartools.ZawgyiDetector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    private static String getCellValue(Cell cell, Workbook workbook) {
        ZawgyiDetector detector = new ZawgyiDetector();
        try {
            // System.out.println(workbook.getFontAt(cell.getCellStyle().getFontIndex()).getFontName());
            // if (cell.getCellType() != CellType.NUMERIC
            // &&
            // workbook.getFontAt(cell.getCellStyle().getFontIndex()).getFontName().contains("Zawgyi"))
            // {
            // return Converter.zg12uni51(cell.getStringCellValue());
            // }
            if (cell.getCellType() != CellType.NUMERIC
                    && detector.getZawgyiProbability(cell.getStringCellValue()) > 0.999) {
                // System.out.println("Processing cell" + String.valueOf(cell.getColumnIndex() +
                // 1));
                return Converter.zg12uni51(cell.getStringCellValue());
            } else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = cell.getDateCellValue();
                    return df.format(date);
                }
            }
            // System.out.println("Processing cell" + String.valueOf(cell.getColumnIndex() +
            // 1));
            return cell.getStringCellValue();
        } catch (Exception e) {
            // System.out.println("Processing cell" + String.valueOf(cell.getColumnIndex() +
            // 1));
            try {
                return Double.toString(cell.getNumericCellValue());
            } catch (Exception e2) {
                return cell.getStringCellValue();
            }
            // return Double.toString(cell.getNumericCellValue());
        }

    }

    private static String getCellValue(Cell cell) {
        ZawgyiDetector detector = new ZawgyiDetector();
        try {
            if (cell.getCellType() != CellType.NUMERIC
                    && detector.getZawgyiProbability(cell.getStringCellValue()) > 0.999) {
                return Converter.zg12uni51(cell.getStringCellValue());
            }
            if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = cell.getDateCellValue();
                    return df.format(date);
                }
            }

            return cell.getStringCellValue();
        } catch (Exception e) {
            try {
                return Double.toString(cell.getNumericCellValue());
            } catch (Exception e2) {
                return cell.getStringCellValue();
            }
        }
    }

    public static ByteArrayOutputStream writeExcel(List<LinkedHashMap<String, Object>> datalist, String sheetname)
            throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetname);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (datalist.size() > 0) {
            List<String> keys = datalist.get(0).entrySet().stream().map(p -> p.getKey()).collect(Collectors.toList());
            int i = 0;
            Row row = sheet.createRow(i++);

            int j = 0;
            for (String key : keys) {
                Cell cell = row.createCell(j++);
                cell.setCellValue(key);
            }
            for (Map<String, Object> data : datalist) {
                j = 0;
                row = sheet.createRow(i);
                sheet.setColumnWidth(i++, 25 * 256);
                for (String key : keys) {
                    Cell cell = row.createCell(j++);
                    cell.setCellValue((String) data.get(key));
                }
            }
            workbook.write(out);
            workbook.close();
        }
        return out;
    }

    public static List<Map<String, Object>> excelToDataList(String base64File, List<String> headers)
            throws IOException {
        return excelToDataList(FileUtils.getInputStreamFromBase64(base64File), headers);
    }

    public static List<Map<String, Object>> excelToDataList(InputStream inputStream, List<String> headers)
            throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<Map<String, Object>> datalist = new ArrayList<>();
        int i = 0;
        for (Row row : sheet) {
            int j = 0;
            if (i > 0) {
                Map<String, Object> map = new HashMap<>();
                boolean isempty = true;
                for (int cn = 0; cn < row.getLastCellNum(); cn++) {
                    if (j > headers.size()) {
                        break;
                    }
                    Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    try {
                        if (!getCellValue(cell).isEmpty()) {
                            isempty = false;
                        }
                        if (j < headers.size()) {
                            map.put(headers.get(j), getCellValue(cell, workbook));
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        if (j < headers.size()) {
                            map.put(headers.get(j), "");
                        }
                    }
                    j++;
                }
                while (j < headers.size()) {
                    map.put(headers.get(j), "");
                    j++;
                }
                if (!isempty) {
                    datalist.add(map);
                    System.out.println("Processing row " + String.valueOf(i));
                } else {
                    break;
                }
            }
            i++;
        }
        workbook.close();
        return datalist;
    }
}
