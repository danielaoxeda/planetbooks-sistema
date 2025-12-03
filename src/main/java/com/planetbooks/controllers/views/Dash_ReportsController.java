package com.planetbooks.controllers.views;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.planetbooks.DTO.SaleTransactionRowDTO; 
import com.planetbooks.DTO.SalesExcelDTO;
import com.planetbooks.repositories.VentaRepository;
import com.planetbooks.services.ExcelGeneratorService;
import com.planetbooks.services.ReporteTransactionService; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.ResponseBody; 
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;

@Controller
@RequestMapping("/reports")
public class Dash_ReportsController {

    @Autowired
    private VentaRepository ventaRepository; 
    @Autowired
    private ExcelGeneratorService excelGeneratorService; 

    @Autowired
    private ReporteTransactionService reporteTransactionService;

    @GetMapping("")
    public String salesReport(HttpServletRequest request, Model model) {
        model.addAttribute("currentPath", request.getRequestURI());
        model.addAttribute("activeTab", "sales");
        return "admin/reports/sales-report";
    }

    @GetMapping("/products")
    public String productsReports(HttpServletRequest request, Model model) {
        model.addAttribute("currentPath", request.getRequestURI());
        model.addAttribute("activeTab", "products");
        return "admin/reports/products-report";
    }

    @GetMapping("/clients")
    public String clientsReport(HttpServletRequest request, Model model) {
        model.addAttribute("currentPath", request.getRequestURI());
        model.addAttribute("activeTab", "clients");
        return "admin/reports/clients-report";
    }

    @GetMapping("/failures")
    public String failuresReport(HttpServletRequest request, Model model) {
        model.addAttribute("currentPath", request.getRequestURI());
        model.addAttribute("activeTab", "failures");
        return "admin/reports/failures-report";
    }

    @GetMapping("/transaction-data")
    @ResponseBody 
    public List<SaleTransactionRowDTO> getSalesTransactionData(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category) {

        return reporteTransactionService.getFilteredSaleTransactions(startDate, endDate, category);
    }

    /* Descarga del Excel */
    @GetMapping("/sales/excel")
    public ResponseEntity<InputStreamResource> downloadSalesExcel(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category) throws IOException {

        List<SalesExcelDTO> data = ventaRepository.findForExcelExport(startDate, endDate, category);
        ByteArrayInputStream in = excelGeneratorService.generateSalesExcel(data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=sales_report.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(
                        MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }

}
