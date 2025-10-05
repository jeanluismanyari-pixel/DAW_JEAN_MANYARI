package com.jean.springcloud.msvc_clients.entity;

import java.security.SecureRandom;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 50)
    private String usuario;

    @Column(nullable = false, length = 255)
    private String contrasenia;

    @Transient
    private String port;

    public Client() {
    }

    public Client(Long id, String dni, String nombres, String apellidos) {
        this.id = id;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;

    }

    @PrePersist
    public void generarUsuarioYContrasenia() {
        if (this.usuario == null || this.usuario.isEmpty()) {
            this.usuario = generarUsuario();
        }
        if (this.contrasenia == null || this.contrasenia.isEmpty()) {
            this.contrasenia = generarContraseniaRandom();
        }
    }

    private String generarUsuario() {

        String primerNombre = this.nombres.split(" ")[0].toLowerCase();
        String primerApellido = this.apellidos.split(" ")[0].toLowerCase();

        primerNombre = limpiarCaracteres(primerNombre);
        primerApellido = limpiarCaracteres(primerApellido);

        return primerNombre + "." + primerApellido;
    }

    private String generarContraseniaRandom() {
        String mayusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String minusculas = "abcdefghijklmnopqrstuvwxyz";
        String numeros = "0123456789";
        String especiales = "@#$%&*";

        String todosCaracteres = mayusculas + minusculas + numeros + especiales;
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);

        sb.append(mayusculas.charAt(random.nextInt(mayusculas.length())));
        sb.append(minusculas.charAt(random.nextInt(minusculas.length())));
        sb.append(numeros.charAt(random.nextInt(numeros.length())));
        sb.append(especiales.charAt(random.nextInt(especiales.length())));

        for (int i = 0; i < 6; i++) {
            sb.append(todosCaracteres.charAt(random.nextInt(todosCaracteres.length())));
        }

        return mezclarCadena(sb.toString());
    }

    private String limpiarCaracteres(String texto) {
        return texto.toLowerCase()
                .replace("á", "a").replace("é", "e").replace("í", "i")
                .replace("ó", "o").replace("ú", "u")
                .replace("ñ", "n")
                .replaceAll("[^a-zA-Z0-9.]", "");
    }

    private String mezclarCadena(String input) {
        char[] characters = input.toCharArray();
        SecureRandom random = new SecureRandom();

        for (int i = characters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }

        return new String(characters);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
        if (this.apellidos != null && !this.apellidos.isEmpty()) {
            this.usuario = generarUsuario();
        }
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
        if (this.nombres != null && !this.nombres.isEmpty()) {
            this.usuario = generarUsuario();
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;

    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
