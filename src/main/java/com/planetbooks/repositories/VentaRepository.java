package com.planetbooks.repositories;

import com.planetbooks.models.LatestSold;
import com.planetbooks.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    // Top 5 libros vendidos
    @Query("SELECT v.inventory.title, SUM(v.quantity) as totalSold " +
           "FROM Venta v " +
           "GROUP BY v.inventory.id, v.inventory.title " +
           "ORDER BY totalSold DESC")
    List<Object[]> getTopSellingBooks();

    // Últimos 5 ventas
    List<Venta> findTop5ByOrderBySaleDateDesc();

    // Suma de ventas del mes actual
    @Query(value = "SELECT COALESCE(SUM(total_price), 0) FROM ventas " +
            "WHERE YEAR(sale_date) = YEAR(CURRENT_DATE) " +
            "AND MONTH(sale_date) = MONTH(CURRENT_DATE)", nativeQuery = true)
    BigDecimal getMonthlySales();


  @Query("SELECT SUM(v.quantity) FROM Venta v")
Integer getTotalBooksSold();



 @Query(value = """
        SELECT 
            i.id AS bookId,
            i.title AS bookTitle,
            v.sale_date AS lastSoldDate,
            CONCAT(c.first_name, ' ', c.last_name_father) AS clientName,
            c.email AS clientEmail
        FROM ventas v
        JOIN inventory i ON v.inventory_id = i.id
        JOIN clients c ON v.client_id = c.id
        ORDER BY v.sale_date DESC
        LIMIT 5
    """, nativeQuery = true)
    List<LatestSold> findLatestBooksSold();


}
