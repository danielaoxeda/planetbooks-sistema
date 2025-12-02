package com.planetbooks.DTO;

import java.time.LocalDate;
import java.math.BigDecimal; // Nuevo: Necesario para que coincida con la entidad Venta

public class SaleTransactionRowDTO {
    
    // Corresponds to 'Transaction ID'
    private Long transactionId; 
    
    // Corresponds to 'Customer Name' (from Client model)
    private String customerName; 
    
    // Corresponds to 'Book Purchased' (from Inventory model)
    private String bookTitle; 
    
    // Corresponds to 'Amount' (Total Price)
    private BigDecimal transactionAmount; // Corregido: Tipo cambiado de Double a BigDecimal
    
    // Corresponds to 'Sale Date'
    private LocalDate transactionDate;
    
    // Corresponds to 'Status' (paymentMethod de Venta)
    private String paymentStatus;
    
    // Custom field for CSS styling (e.g., 'text-bg-success', 'text-bg-warning')
    // Nota: Este campo existe en el DTO, pero se rellena fuera de la consulta JPQL.
    private String statusBadgeColor; 

    // Constructor requerido por JPQL Projections (IMPORTANTE)
    // CORREGIDO: Se eliminó 'statusBadgeColor' como parámetro.
    // CORREGIDO: Se cambió 'Double' a 'BigDecimal' para transactionAmount.
    public SaleTransactionRowDTO(
        Long transactionId, 
        String customerName, 
        String bookTitle, 
        BigDecimal transactionAmount, // Corregido: Ahora BigDecimal
        LocalDate transactionDate, 
        String paymentStatus
    ) {
        this.transactionId = transactionId;
        this.customerName = customerName;
        this.bookTitle = bookTitle;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.paymentStatus = paymentStatus;
        // statusBadgeColor se deja sin inicializar, se establecerá después o se dejará null
    }

    // Default constructor is required by Spring/Jackson for JSON serialization
    public SaleTransactionRowDTO() {
    }

    // --- GETTERS AND SETTERS ---

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public BigDecimal getTransactionAmount() { // Corregido: Devuelve BigDecimal
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) { // Corregido: Acepta BigDecimal
        this.transactionAmount = transactionAmount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStatusBadgeColor() {
        return statusBadgeColor;
    }

    public void setStatusBadgeColor(String statusBadgeColor) {
        this.statusBadgeColor = statusBadgeColor;
    }
}
