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

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Libro
 */
@ToString(exclude = {"categorias", "idioma"})
@EqualsAndHashCode(exclude = {"categorias", "idioma"})
@Entity
@Table(name = "libro")
public class Libro {

	/**
	 * Integer unsigned unique no nullable
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	/**
	 * Idioma class
	 */

	@ManyToOne(cascade = CascadeType.ALL,optional = false, fetch = FetchType.EAGER)
	private Idioma idioma;

	/**
	 * Collection of Categories
	 */

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "libro_categoria", joinColumns = {@JoinColumn(name = "libro_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "categoria_id", referencedColumnName = "id")})
	private Set<Categoria> categorias = new HashSet<Categoria>();

	/**
	 * Collection of Books on the sell
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<LibroCompra> librosCompras = new HashSet<LibroCompra>();

	/**
	 * String no nullable, unique, max length 64 characters
	 */
	@Column(name = "nombre",nullable = false, unique = true)
	private String nombre;

	/**
	 * String no nullable, max length 64 characters
	 */
	@Column(name = "autor", nullable = false)
	private String autor;

	/**
	 * String no nullable, max length 64 characters
	 */
	@Column(name = "editorial",nullable = false)
	private String editorial;

	/**
	 * String no nullable, unique, length 13 characters
	 * global id for a book
	 */
	@Column(name = "isbn",nullable = false, unique = true)
	private String isbn;

	/**
	 * String no nullable, unique, min length 8 max length 12 characters
	 */
	@Column(name = "sku", nullable = false, unique = true)
	private String sku;

	/**
	 * Integer unsigned, no nullable
	 * It can not be zero
	 */
	@Column(name = "precio",nullable = false)
	private int precio;

	/**
	 * Integer unsigned, no nullable
	 */
	@Column(name = "stock",nullable = false)
	private int stock;

	/**
	 * String no nullable, max lenght 255 characters
	 */
	@Lob
	@Column(name = "resenia",nullable = false)
	private String resenia;

	/**
	 * String no nullable, unique
	 * name of the image of the book
	 */
	@Column(name = "nombre_imagen",nullable = false, unique = true)
	private String nombreImagen;

	@JsonIgnore
	@Lob
	@Column(name = "datos_imagen", nullable = false)
	private byte[] bytesImagen;

	/**
	 * String no nullable, unique
	 * name of the pdf file
	 */
	@Column(name = "nombre_archivo",nullable = false, unique = true)
	private String nombreArchivo;

	@JsonIgnore
	@Lob
	@Column(name = "datos_archivo", nullable = false)
	private byte[] bytesArchivo;

	/**
	 * Integer unsigned, no nullable
	 */
	@Column(name = "cantidad_pag", nullable = false)
	private int cantidadPag;

	/**
	 * Constructor
	 */
	public Libro(){

	}

	/**
	 * Constructor Libro
	 * @param nombre  book name
	 * @param isbn code
	 * @param sku code
	 * @param resenia description
	 * @param cantidadPag numbers of pages
	 * @param editorial editorial
	 * @param precio price
	 * @param stock stock
	 * @param nombreArchivo  file name
	 * @param nombreImagen image name
	 */
	public Libro(String nombre, String isbn, String sku,
				 String resenia, int cantidadPag, String editorial,
				 int precio, int stock, String nombreArchivo,
				 String nombreImagen) {
		this.nombre = nombre;
		this.isbn = isbn;
		this.sku = sku;
		this.resenia = resenia;
		this.cantidadPag = cantidadPag;
		this.editorial = editorial;
		this.precio = precio;
		this.stock = stock;
		this.nombreArchivo = nombreArchivo;
		this.nombreImagen = nombreImagen;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getResenia() {
		return resenia;
	}

	public void setResenia(String resenia) {
		this.resenia = resenia;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public Set<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}

	public int getCantidadPag() {
		return cantidadPag;
	}

	public void setCantidadPag(int cantidadPag) {
		this.cantidadPag = cantidadPag;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Set<LibroCompra> getLibrosCompras() {
		return librosCompras;
	}

	public void setLibrosCompras(Set<LibroCompra> librosCompras) {
		this.librosCompras = librosCompras;
	}

	public byte[] getBytesImagen() {
		return bytesImagen;
	}

	public void setBytesImagen(byte[] bytesImagen) {
		this.bytesImagen = bytesImagen;
	}

	public byte[] getBytesArchivo() {
		return bytesArchivo;
	}

	public void setBytesArchivo(byte[] bytesArchivo) {
		this.bytesArchivo = bytesArchivo;
	}
}
