package com.planetbooks.repositories;

import com.planetbooks.models.Client;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Traer todos los clientes activos
    List<Client> findByActiveTrue();

    // Traer los últimos 5 clientes activos usando consulta JPQL
    @Query("SELECT c FROM Client c WHERE c.active = true ORDER BY c.id DESC")
    List<Client> findLatest5ActiveClients();

    

}
