package com.planetbooks.services;

import com.planetbooks.models.Inventory;
import com.planetbooks.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repo;

    // Listing
    public List<Inventory> findAll() {
        return repo.findAll();
    }

    public List<Inventory> findByExam(String exam) {
        return repo.findByExam(exam.toUpperCase());
    }

    public List<Inventory> findByLevel(String level) {
        return repo.findByLevel(level.toUpperCase());
    }

    // Get by ID
    public Optional<Inventory> findById(int id) {
        return repo.findById(id);
    }

    // Save (create or edit)
    public Inventory save(Inventory book) {
        return repo.save(book);
    }

    // Delete
    public void deleteById(int id) {
        repo.deleteById(id);
    }
}
