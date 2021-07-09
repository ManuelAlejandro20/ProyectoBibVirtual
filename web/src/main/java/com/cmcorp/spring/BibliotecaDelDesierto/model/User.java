/*
 * Copyright (c) 2021 CMCORP
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * An intermediate form of license used by the X Consortium for X11 used the following wording:[16]
 *
 */

package com.cmcorp.spring.BibliotecaDelDesierto.model;

import java.util.Collection;

import javax.persistence.*;

/**
 * Class User
 */
@Entity
@Table(name = "usuario")
public class User {

    /**
     * Integer unsigned, no nullable, unique
     * id for the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    /**
     * String no nullable, unique, max length 64
     */
    @Column(name = "email",nullable = false, unique = true)
    private String email;

    /**
     * String no nullable, unique, max length 64
     */
    @Column(name = "username",nullable = false, unique = true)
    private String username;

    /**
     * String no nullable, min length 8 max length 64
     */
    @Column(name = "password",nullable = false)
    private String password;

    /**
     * String no nullable, max length 64
     */
    @Column(name = "nombre",nullable = false)
    private String nombre;

    /**
     * String no nullable, max length 64
     */
    @Column(name = "paterno",nullable = false)
    private String paterno;

    /**
     * String no nullable, max length 64
     */
    @Column(name = "materno",nullable = false)
    private String materno;

    /**
     * String no nullable, max length 64
     */
    @Column(name = "direccion",nullable = false)
    private String direccion;

    /**
     * String no nullable, max length 64
     */
    @Column(name = "telefono",nullable = false)
    private String telefono;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "roles_usuarios",
			joinColumns = @JoinColumn(
		            name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
				            name = "rol_id", referencedColumnName = "id"))
	
	private Collection<Rol> roles;    
    
    /**
     * Constructor
     */
    public User() {

    }

    /**
     * Constructor with params
     * @param id
     * @param email
     * @param username
     * @param password
     * @param nombre
     * @param paterno
     * @param materno
     * @param direccion
     * @param telefono
     * @param roles
     */
    public User(Integer id, String email, String username, String password, String nombre, String paterno,
                String materno, String direccion, String telefono, Collection<Rol> roles) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.direccion = direccion;
        this.telefono = telefono;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setDireccion(String telefono) {
        this.direccion = telefono;
    }
    
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }    

	public Collection<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Rol> roles) {
		this.roles = roles;
	}
    
    
}
