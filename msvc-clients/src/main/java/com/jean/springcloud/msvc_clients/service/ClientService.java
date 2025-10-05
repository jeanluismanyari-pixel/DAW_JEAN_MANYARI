package com.jean.springcloud.msvc_clients.service;

import com.jean.springcloud.msvc_clients.entity.Client;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findAll();
    
    Optional<Client> findById(Long id);
    
    Optional<Client> findByDni(String dni);
    
    Client save(Client client);
    
    void deleteById(Long id);
    
    List<Client> getAllClientsWithUserData();
}
