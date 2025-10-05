package com.jean.springcloud.msvc_users.repository;

import com.jean.springcloud.msvc_users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Método personalizado para buscar por DNI
    Optional<User> findByDni(String dni);

    // Método para verificar si existe un DNI
    boolean existsByDni(String dni);
}