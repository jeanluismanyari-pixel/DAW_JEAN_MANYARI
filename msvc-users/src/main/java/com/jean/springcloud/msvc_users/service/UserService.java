package com.jean.springcloud.msvc_users.service;

import com.jean.springcloud.msvc_users.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByDni(String dni);

    User save(User user);

    void deleteById(Long id);
}
