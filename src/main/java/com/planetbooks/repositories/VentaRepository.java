package com.planetbooks.repositories;

import com.planetbooks.models.LatestSold;
import com.planetbooks.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.planetbooks.DTO.BookFilterDTO;
import com.planetbooks.DTO.SaleTransactionRowDTO;
import com.planetbooks.DTO.SalesExcelDTO;

import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
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
                                     CONCAT(c.name, ' ', c.last_name_father) AS clientName,
                                     c.email AS clientEmail
                                 FROM ventas v
                                 JOIN inventory i ON v.inventory_id = i.id
                                 JOIN clients c ON v.client_id = c.id
                                 ORDER BY v.sale_date DESC
                                 LIMIT 5;

                        """, nativeQuery = true)
        List<LatestSold> findLatestBooksSold();

        @Query("SELECT new com.planetbooks.DTO.SaleTransactionRowDTO(" +
                        "v.id, v.client.name, v.inventory.title, v.totalPrice, v.saleDate, v.paymentMethod) " +
                        "FROM Venta v " +
                        "WHERE (:startDate IS NULL OR v.saleDate >= :startDate) " +
                        "AND (:endDate IS NULL OR v.saleDate <= :endDate) " +
                        "AND (:category IS NULL OR :category = 'All...' OR v.inventory.exam = :category) " +
                        "ORDER BY v.saleDate DESC")
        List<SaleTransactionRowDTO> findFilteredSaleTransactions(
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("category") String category);

        @Query(value = """
                        SELECT  v.id                AS transactionId,
                                v.sale_date         AS saleDate,
                                v.total_price       AS totalPrice,
                                v.payment_method    AS paymentMethod,
                                c.id                AS clientId,
                                c.name              AS clientName,
                                c.last_name_father  AS lastNameFather,
                                c.last_name_mother  AS lastNameMother,
                                c.email             AS email,
                                i.publisher         AS publisher
                        FROM ventas v
                        JOIN clients c ON c.id = v.client_id
                        JOIN inventory i ON i.id = v.inventory_id
                        WHERE (:startDate IS NULL OR v.sale_date >= :startDate)
                          AND (:endDate   IS NULL OR v.sale_date <= :endDate)
                          AND (:category  IS NULL OR :category = 'All...' OR i.publisher = :category)
                        ORDER BY v.sale_date DESC
                        """, nativeQuery = true)
        List<SalesExcelDTO> findForExcelExport(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("category") String category);

        /* ---------- Todos los libros filtrados por publisher y precio ---------- */
        @Query(value = """
                        SELECT i.id,
                               i.title,
                               i.publisher,
                               CAST(i.price AS DOUBLE) AS price,
                               i.exam,
                               i.level
                        FROM inventory i
                        WHERE (:publisher IS NULL OR i.publisher = :publisher)
                          AND (:minPrice IS NULL OR CAST(i.price AS DOUBLE) >= :minPrice)
                          AND (:maxPrice IS NULL OR CAST(i.price AS DOUBLE) <= :maxPrice)
                        ORDER BY i.title
                        """, nativeQuery = true)
        List<BookFilterDTO> findAllBooks(@Param("publisher") String publisher,
                        @Param("minPrice") Double minPrice,
                        @Param("maxPrice") Double maxPrice);
}
