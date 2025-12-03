// src/main/java/com/planetbooks/services/ReporteTransactionService.java

package com.planetbooks.services;

import com.planetbooks.DTO.SaleTransactionRowDTO;
import com.planetbooks.repositories.VentaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReporteTransactionService {

    private final VentaRepository ventaRepository;

    // Dependency Injection via Constructor
    public ReporteTransactionService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    /**
     * Retrieves filtered sales transactions, handling default date range if filters are not provided.
     * * @param startDate The start date for filtering.
     * @param endDate The end date for filtering.
     * @param category The category filter (i.e., Inventory.exam).
     * @return A list of SaleTransactionRowDTO.
     */
    public List<SaleTransactionRowDTO> getFilteredSaleTransactions(LocalDate startDate, LocalDate endDate, String category) {


        // ***************************************************************
        // LÓGICA DE 30 DÍAS RESTAURADA:
        // ***************************************************************
        
        // Si no se proporcionan fechas (código = carga inicial), calcula el rango de los últimos 30 días.
        if (startDate == null && endDate == null) {
            endDate = LocalDate.now();
            startDate = endDate.minusDays(30);
        }
        // Call the Repository method with the determined dates and category
        return ventaRepository.findFilteredSaleTransactions(startDate, endDate, category);
    }
}