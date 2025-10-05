package com.jean.springcloud.msvc_clients.service;

import com.jean.springcloud.msvc_clients.UserFeignClient;
import com.jean.springcloud.msvc_clients.dto.UserDTO;
import com.jean.springcloud.msvc_clients.entity.Client;
import com.jean.springcloud.msvc_clients.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserFeignClient userFeignClient;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
    private static final int PASSWORD_LENGTH = 12;
    private static final SecureRandom random = new SecureRandom();

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findByDni(String dni) {
        return clientRepository.findByDni(dni);
    }

    @Override
    @Transactional
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAllClientsWithUserData() {
        // SOLO AGREGA ESTAS 2 LÍNEAS PARA VER EL ROUND ROBIN
        System.out.println("=== LLAMANDO A MSVC-USERS ===");

        List<UserDTO> users = userFeignClient.getAllUsers();

        // SOLO AGREGA ESTA LÍNEA PARA VER EL PUERTO
        if (!users.isEmpty()) {
            System.out.println("Users obtenidos del puerto: " + users.get(0).getPort());
        }

        List<Client> clients = new ArrayList<>();

        for (UserDTO user : users) {
            Client client = new Client();

            client.setDni(user.getDni());
            client.setNombres(user.getNombres());
            client.setApellidos(user.getApellidos());

            String usuario = generarUsuario(user.getNombres(), user.getApellidos());
            client.setUsuario(usuario);

            String contrasenia = generarContraseniaAleatoria();
            client.setContrasenia(contrasenia);

            clients.add(client);
        }

        return clients;
    }

    private String generarUsuario(String nombres, String apellidos) {
        String primerNombre = nombres.split(" ")[0].toLowerCase();
        String primerApellido = apellidos.split(" ")[0].toLowerCase();

        primerNombre = eliminarAcentos(primerNombre);
        primerApellido = eliminarAcentos(primerApellido);

        return primerNombre + "." + primerApellido;
    }

    private String generarContraseniaAleatoria() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    private String eliminarAcentos(String texto) {
        return texto
                .replace("á", "a").replace("é", "e").replace("í", "i")
                .replace("ó", "o").replace("ú", "u").replace("ñ", "n")
                .replace("Á", "A").replace("É", "E").replace("Í", "I")
                .replace("Ó", "O").replace("Ú", "U").replace("Ñ", "N")
                .replaceAll("[^a-zA-Z0-9]", "");
    }
}