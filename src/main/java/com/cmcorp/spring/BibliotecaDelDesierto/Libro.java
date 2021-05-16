package com.cmcorp.spring.BibliotecaDelDesierto;

public class Libro {
	private String nombreLibro;
	private String editorial;
	private String nombreAutor;
	private int numeroID;
	private String isbn;
	private int precio;
	private int cantidad;
	private String resenia;
	private String nombreImagen;
	private String idioma;
	private int cantidadPag;
	private String nombreArchivo;
	
	public Libro(String nombreLibro, String editorial, String nombreAutor, int numeroID, String isbn, int precio,
			int cantidad, String resenia, String nombreImagen, String idioma, int cantidadPag, String nombreArchivo) {
		this.nombreLibro = nombreLibro;
		this.editorial = editorial;
		this.nombreAutor = nombreAutor;
		this.numeroID = numeroID;
		this.isbn = isbn;
		this.precio = precio;
		this.cantidad = cantidad;
		this.resenia = resenia;
		this.nombreImagen = nombreImagen;
		this.idioma = idioma;
		this.cantidadPag = cantidadPag;
		this.nombreArchivo = nombreArchivo;
	}

	public String getNombreLibro() {
		return nombreLibro;
	}

	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getNombreAutor() {
		return nombreAutor;
	}

	public void setNombreAutor(String nombreAutor) {
		this.nombreAutor = nombreAutor;
	}

	public int getNumeroID() {
		return numeroID;
	}

	public void setNumeroID(int numeroID) {
		this.numeroID = numeroID;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
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

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public int getCantidadPag() {
		return cantidadPag;
	}

	public void setCantidadPag(int cantidadPag) {
		this.cantidadPag = cantidadPag;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	
}
