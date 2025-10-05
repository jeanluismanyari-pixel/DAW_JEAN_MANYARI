package com.jean.springcloud.msvc_users.controller;

import com.jean.springcloud.msvc_users.entity.User;
import com.jean.springcloud.msvc_users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${server.port}")
    private String port;

    @GetMapping
    public ResponseEntity<List<User>> listar() {
        List<User> users = userService.findAll();
        users.forEach(user -> user.setPort(port));
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> detalle(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPort(port);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<User> buscarPorDni(@PathVariable String dni) {
        Optional<User> userOptional = userService.findByDni(dni);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPort(port);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> crear(@RequestBody User user) {
        User nuevoUser = userService.save(user);
        nuevoUser.setPort(port);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> actualizar(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User userDB = userOptional.get();
            userDB.setDni(user.getDni());
            userDB.setNombres(user.getNombres());
            userDB.setApellidos(user.getApellidos());
            userDB.setNacimiento(user.getNacimiento());

            User userActualizado = userService.save(userDB);
            userActualizado.setPort(port);
            return ResponseEntity.status(HttpStatus.CREATED).body(userActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
