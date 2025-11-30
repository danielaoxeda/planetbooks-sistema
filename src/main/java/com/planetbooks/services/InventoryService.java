package com.planetbooks.services;

import com.planetbooks.models.Inventory;
import com.planetbooks.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repo;

    public List<Inventory> findAll() {
        return repo.findAll();
    }

    public List<Inventory> findByExam(String exam) {
        return repo.findByExam(exam.toUpperCase());
    }

    public List<Inventory> findByLevel(String level) {
        return repo.findByLevel(level.toUpperCase());
    }
}
