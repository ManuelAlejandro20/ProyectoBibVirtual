package com.cmcorp.spring.BibliotecaDelDesierto.model.dto;

import java.util.HashSet;
import java.util.Set;
import com.cmcorp.spring.BibliotecaDelDesierto.model.Categoria;
import com.cmcorp.spring.BibliotecaDelDesierto.model.Idioma;
import com.cmcorp.spring.BibliotecaDelDesierto.model.LibroCompra;

public class LibroDTO {

	private Integer id;
	private Idioma idioma;
	private Set<Categoria> categorias = new HashSet<Categoria>();
	private Set<LibroCompra> librosCompras = new HashSet<LibroCompra>();
	private String nombre;
	private String autor;
	private String editorial;
	private String isbn;
	private String sku;
	private int precio;
	private int stock;
	private String resenia;
	private String nombreImagen;
	private byte[] bytesImagen;
	private String nombreArchivo;
	private byte[] bytesArchivo;
	private int cantidadPag;


	public LibroDTO(){

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
	public LibroDTO(String nombre, String isbn, String sku,
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
