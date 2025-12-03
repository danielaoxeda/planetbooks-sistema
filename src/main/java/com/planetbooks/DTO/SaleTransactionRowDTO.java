package com.planetbooks.DTO;

import java.time.LocalDate;
import java.math.BigDecimal; 

public class SaleTransactionRowDTO {

    private Long transactionId; 

    private String customerName; 
    
    private String bookTitle; 
    
    private BigDecimal transactionAmount; 

    private LocalDate transactionDate;
 
    private String paymentStatus;

    private String statusBadgeColor; 

    public SaleTransactionRowDTO(
        Long transactionId, 
        String customerName, 
        String bookTitle, 
        BigDecimal transactionAmount, 
        LocalDate transactionDate, 
        String paymentStatus
    ) {
        this.transactionId = transactionId;
        this.customerName = customerName;
        this.bookTitle = bookTitle;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.paymentStatus = paymentStatus;

    }

    public SaleTransactionRowDTO() {
    }

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

    public BigDecimal getTransactionAmount() { 
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) { 
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
