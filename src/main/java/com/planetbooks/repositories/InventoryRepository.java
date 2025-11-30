package com.planetbooks.repositories;

import com.planetbooks.models.Inventory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> findByExam(String exam);

    List<Inventory> findByExamOrderByPriceAsc(String exam);

    List<Inventory> findByLevel(String level);
}
