package com.planetbooks.repositories;

import com.planetbooks.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByActiveTrue();
    List<Client> findByActiveFalse();
}
