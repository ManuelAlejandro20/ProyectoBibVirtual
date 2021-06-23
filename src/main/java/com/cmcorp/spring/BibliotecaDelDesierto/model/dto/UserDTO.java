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

package com.cmcorp.spring.BibliotecaDelDesierto.model.dto;

/**
 * UserDTO Class
 */
public class UserDTO {

	/**
	 * Integer unsigned id UserDTO
	 */
    private Integer id;

	/**
	 * String email no nullable max length 64 characters
	 */
	private String email;

	/**
	 * String username max length max length 64 characters
	 */
    private String username;

	/**
	 * String password min no nullable min length 8 characters max length 64 characters
	 */
	private String password;

	/**
	 * String name max length max length 64 characters
	 */
    private String nombre;

	/**
	 * String last name max length max length 64 characters
	 */
	private String paterno;

	/**
	 * String last name max length max length 64 characters
	 */
    private String materno;

	/**
	 * String direccion max length max length 64 characters
	 */
	private String direccion;

	/**
	 * String telefono max length max length 32 characters
	 */
    private String telefono;

	/**
	 * String rol max length max length 64 characters
	 */
	private String rol;

	/**
	 * Constructor
	 */
	public UserDTO() {
    	
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
	 * @param rol
	 */
	public UserDTO(Integer id, String email, String username, String password,
				   String nombre, String paterno, String materno, String direccion,
				   String telefono, String rol) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.paterno = paterno;
		this.materno = materno;
		this.direccion = direccion;
		this.telefono = telefono;
		this.rol = rol;
	}

	public Integer getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getNombre() {
		return nombre;
	}

	public String getPaterno() {
		return paterno;
	}

	public String getMaterno() {
		return materno;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getRol() {
		return rol;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}
