package com.planetbooks.repositories;

import com.planetbooks.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByEmailAndPassword(String email, String password);

    // Obtener clientes activos
    List<Client> findByActiveTrue();

    // Obtener clientes eliminados
    List<Client> findByActiveFalse();

    // Últimos 5 clientes activos
    @Query("SELECT c FROM Client c WHERE c.active = true ORDER BY c.id DESC")
    List<Client> findLatest5ActiveClients();

    // Cantidad por país
    @Query("SELECT c.country, COUNT(c) FROM Client c WHERE c.active = true GROUP BY c.country")
    List<Object[]> getClientsByCountry();

    // BUSCADOR
    @Query("""
        SELECT c FROM Client c 
        WHERE c.active = true AND (
            LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(c.last_name_father) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(c.last_name_mother) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(c.user) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    List<Client> searchClients(String search);
}
