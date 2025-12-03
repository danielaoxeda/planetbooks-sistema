package com.planetbooks.services;

import com.planetbooks.DTO.SaleTransactionRowDTO;
import com.planetbooks.DTO.SalesExcelDTO;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelGeneratorService {

 public ByteArrayInputStream generateSalesExcel(List<SalesExcelDTO> rows) throws IOException {
    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

        Sheet sheet = workbook.createSheet("Sales Report");

        // 1. Cabeceras
        String[] headers = {
            "Transaction ID", "Sale Date", "Amount (USD)", "Payment Method",
            "Client ID", "Name", "Last Name Father", "Last Name Mother", "Email", "Publisher"
        };
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // 2. Datos
        int rowIdx = 1;
        for (SalesExcelDTO r : rows) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(r.getTransactionId());
            row.createCell(1).setCellValue(r.getSaleDate().toString());
            row.createCell(2).setCellValue(r.getTotalPrice().doubleValue());
            row.createCell(3).setCellValue(r.getPaymentMethod());
            row.createCell(4).setCellValue(r.getClientId());
            row.createCell(5).setCellValue(r.getClientName());
            row.createCell(6).setCellValue(r.getLastNameFather());
            row.createCell(7).setCellValue(r.getLastNameMother());
            row.createCell(8).setCellValue(r.getEmail());
            row.createCell(9).setCellValue(r.getPublisher());
        }

        // 3. Auto-ajustar
        for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
}