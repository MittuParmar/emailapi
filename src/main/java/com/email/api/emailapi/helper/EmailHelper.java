package com.email.api.emailapi.helper;

import com.email.api.emailapi.model.MailModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class EmailHelper {
    public List<MailModel> convertExcelToList (InputStream is){

        List<MailModel>  mailModelList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet1 = workbook.getSheet("Sheet1");
            Iterator<Row> iterator = sheet1.iterator();

            while(iterator.hasNext()){
                Row row = iterator.next();

                MailModel mailModel = new MailModel();
//                mailModel.setTo(row.getCell(0).getStringCellValue());
//                mailModel.setSubject(row.getCell(1).getStringCellValue());
//                mailModel.setText(row.getCell(2).getStringCellValue());

                mailModel.setTo(row.getCell(0) != null ? row.getCell(0).getStringCellValue() : null);
                mailModel.setSubject(row.getCell(1) != null ? row.getCell(1).getStringCellValue() : null);
                mailModel.setName(row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null);
                mailModel.setText(row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null);
                mailModelList.add(mailModel);
            }
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mailModelList;
    }

    public List<MailModel> convertExcelToList(MultipartFile excelFile, String subject, String template, MultipartFile attachment) {

        List<MailModel>  mailModelList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
            XSSFSheet sheet1 = workbook.getSheet("Emails");
            Iterator<Row> iterator = sheet1.iterator();

            while(iterator.hasNext()){
                Row row = iterator.next();

                MailModel mailModel = new MailModel();

                mailModel.setTo(row.getCell(0) != null ? row.getCell(0).getStringCellValue() : null);
                mailModel.setSubject(subject);
                mailModel.setName(row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "Hiring Team");
                if(template.contains("{name}")) {
                    mailModel.setText(template.replace("{name}",mailModel.getName()));
                }else {
                    mailModel.setText(template);
                }
                mailModelList.add(mailModel);
            }
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mailModelList;
    }
}
