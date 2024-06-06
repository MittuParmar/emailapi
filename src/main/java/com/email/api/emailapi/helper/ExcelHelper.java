package com.email.api.emailapi.helper;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelHelper {

    public ByteArrayInputStream generateExcelFile(List<String> emails, List<String> firstNames) throws IOException, IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Emails");

        for (int i = 0; i < emails.size(); i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(emails.get(i));
            row.createCell(1).setCellValue(firstNames.get(i));
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
