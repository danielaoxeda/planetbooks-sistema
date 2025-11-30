package com.planetbooks.models;

import java.time.LocalDate;

public interface LatestSold {

    Long getBookId();
    String getBookTitle();
    LocalDate getLastSoldDate();
    String getClientName();
    String getClientEmail();
}
