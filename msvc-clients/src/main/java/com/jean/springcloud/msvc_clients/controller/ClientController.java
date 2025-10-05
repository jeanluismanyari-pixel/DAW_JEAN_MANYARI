package com.jean.springcloud.msvc_clients.controller;

import com.jean.springcloud.msvc_clients.entity.Client;
import com.jean.springcloud.msvc_clients.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Value("${server.port}")
    private String port;

    @GetMapping
    public ResponseEntity<List<Client>> listar() {
        List<Client> clients = clientService.findAll();
        clients.forEach(client -> client.setPort(port));
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/with-users")
    public ResponseEntity<List<Client>> listarConDatosDeUsers() {
        List<Client> clients = clientService.getAllClientsWithUserData();
        clients.forEach(client -> client.setPort(port));
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> detalle(@PathVariable Long id) {
        Optional<Client> clientOptional = clientService.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.setPort(port);
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<Client> buscarPorDni(@PathVariable String dni) {
        Optional<Client> clientOptional = clientService.findByDni(dni);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.setPort(port);
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Client> crear(@RequestBody Client client) {
        Client nuevoClient = clientService.save(client);
        nuevoClient.setPort(port);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> actualizar(@PathVariable Long id, @RequestBody Client client) {
        Optional<Client> clientOptional = clientService.findById(id);
        if (clientOptional.isPresent()) {
            Client clientDB = clientOptional.get();
            clientDB.setDni(client.getDni());
            clientDB.setNombres(client.getNombres());
            clientDB.setApellidos(client.getApellidos());
            clientDB.setUsuario(client.getUsuario());
            clientDB.setContrasenia(client.getContrasenia());

            Client clientActualizado = clientService.save(clientDB);
            clientActualizado.setPort(port);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Client> clientOptional = clientService.findById(id);
        if (clientOptional.isPresent()) {
            clientService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
