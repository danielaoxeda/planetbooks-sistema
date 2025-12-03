package com.planetbooks.services;

import com.planetbooks.DTO.SaleTransactionRowDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelGeneratorService {

    public ByteArrayInputStream generateSalesExcel(List<SaleTransactionRowDTO> rows) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Sales Report");

            // Cabeceras
            String[] headers = { "Transaction ID", "Customer", "Book", "Amount (USD)", "Sale Date", "Payment Method" };
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

            // Datos
            int rowIdx = 1;
            for (SaleTransactionRowDTO r : rows) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(r.getTransactionId());
                row.createCell(1).setCellValue(r.getCustomerName());
                row.createCell(2).setCellValue(r.getBookTitle());
                row.createCell(3).setCellValue(r.getTransactionAmount().doubleValue());
                row.createCell(4).setCellValue(r.getTransactionDate().toString());
                row.createCell(5).setCellValue(r.getPaymentStatus());
            }

            // Auto-ajustar
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}