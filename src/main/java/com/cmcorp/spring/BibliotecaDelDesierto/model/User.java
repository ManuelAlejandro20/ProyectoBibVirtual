package com.cmcorp.spring.BibliotecaDelDesierto.model;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "email",nullable = false, unique = true)
    private String email;
    @Column(name = "nickname",nullable = false, unique = true)
    private String nickname;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "nombre",nullable = false)
    private String nombre;
    @Column(name = "paterno",nullable = false)
    private String paterno;
    @Column(name = "materno",nullable = false)
    private String materno;
    @Column(name = "direccion",nullable = false)
    private String direccion;
    @Column(name = "rol",nullable = false)
    private String rol;

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
