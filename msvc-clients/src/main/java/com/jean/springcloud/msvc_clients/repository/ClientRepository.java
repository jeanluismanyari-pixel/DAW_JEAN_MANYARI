package com.jean.springcloud.msvc_clients.repository;

import com.jean.springcloud.msvc_clients.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByDni(String dni);

    Optional<Client> findByUsuario(String usuario);

    boolean existsByDni(String dni);

    boolean existsByUsuario(String usuario);
}
