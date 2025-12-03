package com.planetbooks.controllers.views;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.planetbooks.DTO.SaleTransactionRowDTO; // Importa el DTO
import com.planetbooks.repositories.VentaRepository;
import com.planetbooks.services.ExcelGeneratorService;
import com.planetbooks.services.ReporteTransactionService; // Importa el servicio
import org.springframework.beans.factory.annotation.Autowired; // Necesario para inyección
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat; // Necesario para parsear fechas
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam; // Necesario para los parámetros de filtro
import org.springframework.web.bind.annotation.ResponseBody; // CRUCIAL para devolver JSON
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.planetbooks.repositories.VentaRepository;
import com.planetbooks.services.ExcelGeneratorService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class Dash_ReportsController {

    // Inyección del nuevo servicio
    @Autowired
    private VentaRepository ventaRepository; // ya existe en tu proyecto
    @Autowired
    private ExcelGeneratorService excelGeneratorService; // la creamos antes

    @Autowired
    private ReporteTransactionService reporteTransactionService;

    // ----------------------------------------------------------------------
    // MÉTODOS EXISTENTES (Mantienen la funcionalidad de devolver vistas)
    // ----------------------------------------------------------------------

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
    // ----------------------------------------------------------------------
    // NUEVO MÉTODO AJAX (Devuelve datos JSON)
    // ----------------------------------------------------------------------

    /**
     * AJAX Endpoint to fetch filtered sales transaction data for the table.
     * Full URL: /reports/transaction-data
     */
    @GetMapping("/transaction-data")
    @ResponseBody // Indica a Spring que serialice el retorno (List<DTO>) a JSON
    public List<SaleTransactionRowDTO> getSalesTransactionData(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category) {

        // Llama al servicio para obtener los datos filtrados
        return reporteTransactionService.getFilteredSaleTransactions(startDate, endDate, category);
    }

    /* Descarga del Excel */
    @GetMapping("/sales/excel")
    public ResponseEntity<InputStreamResource> downloadSalesExcel(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category) throws IOException {

        List<SaleTransactionRowDTO> data = ventaRepository.findFilteredSaleTransactions(startDate, endDate, category);
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
