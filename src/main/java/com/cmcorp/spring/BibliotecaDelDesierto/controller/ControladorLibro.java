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

package com.cmcorp.spring.BibliotecaDelDesierto.controller;

import com.cmcorp.spring.BibliotecaDelDesierto.model.*;
import com.cmcorp.spring.BibliotecaDelDesierto.model.dto.LibroCategoriaDTO;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioLibro;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * ControladorLibro RestController
 *
 */
@RestController
public class ControladorLibro {

	private final ServicioLibro servicioLibro;

	/**
	 * Constructor, uses the book services to do the operations 
	 * @param servicioLibro
	 */
	@Autowired
	public ControladorLibro(ServicioLibro servicioLibro) {
		this.servicioLibro = servicioLibro;
	}

	/**
	 * Return all the books in the system
	 * @return all the books in the system
	 */
	@GetMapping("/books-")
	@ResponseBody
	public List<Libro> getAll() {
		return servicioLibro.listaLibros();
	}

	/**
	 * Return all the books in the system considering the pagination
	 * @param page
	 * @param size
	 * @param sortingField
	 * @param sortingDirection
	 * @return all the books in the system
	 */
	@GetMapping("/books")
	public Page<Libro> obtenerTodos(@RequestParam Integer page, @RequestParam Integer size, @RequestParam String sortingField, @RequestParam String sortingDirection) {
		return servicioLibro.allBooks(page, size, sortingField, sortingDirection);
	}

	/**
	 * Returns the page book using the page number and size of the page
	 * @param page
	 * @param size
	 * @param sortingField
	 * @param sortingDirection
	 * @param idioma
	 * @param categoria
	 * @param texto
	 * @return Page books
	 */
	@GetMapping("/booksfield")
	public Page<Libro> getAllXNombreOrAutor(@RequestParam Integer page, @RequestParam Integer size, @RequestParam String sortingField, @RequestParam String sortingDirection, @RequestParam Integer idioma, @RequestParam Integer categoria, @RequestParam String texto) {

		return servicioLibro.allBooksBy(page, size, sortingField, sortingDirection, idioma, categoria, texto);
	}

	/**
	 * Return all the books by author
	 * @param autor
	 * @return List of books
	 */
	@GetMapping("/books/byautor/{autor}")
	public List<Libro> getAllXAutor(@PathVariable(value = "autor") String autor) {
		return servicioLibro.getLibrosXAutor(autor);
	}

	/**
	 * Return all the books by idioma
	 * @param idiomaId language id
	 * @return List of books
	 */
	@GetMapping("/books/byidioma/{idiomaId}")
	public List<Libro> getAllXIdioma(@PathVariable(value = "idiomaId") Integer idiomaId) {
		return servicioLibro.getLibrosXIdioma(idiomaId);
	}

	/**
	 * Return all the books by LibroCategoria object
	 * @param libroCategoriaDTO
	 * @return List of books
	 */
	@GetMapping("/books/bycategorias")
	public List<Libro> getAllXCategorias(@RequestBody LibroCategoriaDTO libroCategoriaDTO) {
		List<Integer> listaIdCategorias = libroCategoriaDTO.getListaCategorias();
		return servicioLibro.getLibrosXCategorias(listaIdCategorias);
	}

	/**
	 * Return a book by his id
	 * @param id, book id
	 * @return book 
	 */
	@GetMapping("/book/byid/{id}")
	public ResponseEntity<Libro> getXId(@PathVariable Integer id) {
		try {
			Libro usuario = servicioLibro.getLibroXId(id);
			return new ResponseEntity<Libro>(usuario, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Return a book by his sku
	 * @param sku, book sku
	 * @return book
	 */
	@GetMapping("/book/bysku/{sku}")
	public ResponseEntity<Libro> getXSku(@PathVariable String sku) {
		try {
			Libro usuario = servicioLibro.getLibroXSku(sku);
			return new ResponseEntity<Libro>(usuario, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Return a book using his isbn
	 * @param isbn, book isbn
	 * @return book
	 */
	@GetMapping("/book/byisbn/{isbn}")
	public ResponseEntity<Libro> getXIsbn(@PathVariable String isbn) {
		try {
			Libro libro = servicioLibro.getLibroXIsbn(isbn);
			return new ResponseEntity<Libro>(libro, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Delete a book using a id
	 * @param id, book id
	 */
	@DeleteMapping("/book/delete/{id}")
	public void delete(@PathVariable Integer id) {
		servicioLibro.deleteLibro(id);
	}

	/**
	 * Send a response using the image of a book
	 * @param id, book id
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/book/{id}/image")
	public void bookImage(@PathVariable Integer id, HttpServletResponse response) throws IOException {
		Libro libro = servicioLibro.getLibroXId(id);

		response.setContentType("image/jpeg; image/jpg; image/png");
		InputStream is = new ByteArrayInputStream(libro.getBytesImagen());
		IOUtils.copy(is, response.getOutputStream());
	}

	/**
	 * Configure the book pdf 
	 * @param id, book id
	 * @return response 
	 */
	@GetMapping("/book/{id}/pdf")
	public ResponseEntity<byte[]> bookPdf(@PathVariable Integer id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));

		Libro libro = servicioLibro.getLibroXId(id);

		String filename = libro.getNombreArchivo();

		headers.add("content-disposition", "inline;filename=" + filename);

		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(libro.getBytesArchivo(), headers, HttpStatus.OK);
		return response;
	}

}
