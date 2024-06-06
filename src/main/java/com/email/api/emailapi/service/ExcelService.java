package com.email.api.emailapi.service;

import com.email.api.emailapi.helper.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    ExcelHelper excelHelper;
    public ByteArrayInputStream generateExcelFile(List<String> emails, List<String> firstNames) throws IOException {
        return excelHelper.generateExcelFile(emails, firstNames);
    }
}
