package com.jean.springcloud.msvc_clients;

import com.jean.springcloud.msvc_clients.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-users")
public interface UserFeignClient {

    @GetMapping("/api/users")
    List<UserDTO> getAllUsers();

    @GetMapping("/api/users/{id}")
    UserDTO getUserById(@PathVariable Long id);

    @GetMapping("/api/users/dni/{dni}")
    UserDTO getUserByDni(@PathVariable String dni);
}
