package com.planetbooks.DTO;

import java.time.LocalDate;
import java.math.BigDecimal;

public interface SalesExcelDTO {
    Long getTransactionId();
    LocalDate getSaleDate();
    BigDecimal getTotalPrice();
    String getPaymentMethod();
    Long getClientId();
    String getClientName();
    String getLastNameFather();
    String getLastNameMother();
    String getEmail();
    String getPublisher();
}