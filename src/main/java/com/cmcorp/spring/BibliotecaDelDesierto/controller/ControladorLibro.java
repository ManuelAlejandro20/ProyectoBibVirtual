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
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioCategoria;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioIdioma;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioLibro;
import lombok.var;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ControladorLibro {

	private final ServicioLibro servicioLibro;
	private final ServicioCategoria servicioCategoria;
	private final ServicioIdioma servicioIdioma;

	@Autowired
	public ControladorLibro(ServicioLibro servicioLibro, ServicioCategoria servicioCategoria,
			ServicioIdioma servicioIdioma) {
		this.servicioLibro = servicioLibro;
		this.servicioCategoria = servicioCategoria;
		this.servicioIdioma = servicioIdioma;
	}

	@GetMapping("/books-")
	@ResponseBody
	public List<Libro> getAll() {
		return servicioLibro.listaLibros();
	}

	@GetMapping("/books")
	@ResponseBody
	public Page<Libro> obtenerTodos(@PageableDefault(size = 2, page=0) Pageable pageable) {
		Page<Libro> result = servicioLibro.findAll(pageable);
		return result;
	}

	@GetMapping("/books/byautor/{autor}")
	public List<Libro> getAllXAutor(@PathVariable(value = "autor") String autor) {
		return servicioLibro.getLibrosXAutor(autor);
	}

	@GetMapping("/books/byidioma/{idiomaId}")
	public List<Libro> getAllXIdioma(@PathVariable(value = "idiomaId") Integer idiomaId) {
		return servicioLibro.getLibrosXIdioma(idiomaId);
	}

	@GetMapping("/books/bycategorias")
	public List<Libro> getAllXCategorias(@RequestBody LibroCategoriaDTO libroCategoriaDTO) {
		List<Integer> listaIdCategorias = libroCategoriaDTO.getListaCategorias();
		return servicioLibro.getLibrosXCategorias(listaIdCategorias);
	}

	@GetMapping("/book/byid/{id}")
	public ResponseEntity<Libro> getXId(@PathVariable Integer id) {
		try {
			Libro usuario = servicioLibro.getLibroXId(id);
			return new ResponseEntity<Libro>(usuario, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/book/bysku/{sku}")
	public ResponseEntity<Libro> getXSku(@PathVariable String sku) {
		try {
			Libro usuario = servicioLibro.getLibroXSku(sku);
			return new ResponseEntity<Libro>(usuario, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/book/byisbn/{isbn}")
	public ResponseEntity<Libro> getXIsbn(@PathVariable String isbn) {
		try {
			Libro libro = servicioLibro.getLibroXIsbn(isbn);
			return new ResponseEntity<Libro>(libro, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/book/{id}/update")
	public String update(LibroCategoriaDTO libroDTO, @PathVariable Integer id, RedirectAttributes redirAttrs) {
		try {
			Libro libroExistente = servicioLibro.getLibroXId(id);

			Libro libro = libroDTO.getLibro();
			if (libroDTO.getListaCategorias() == null) {
				redirAttrs.addFlashAttribute("error", "Debe seleccionar al menos una categoría");
			} else if (libro.getPrecio() <= 0) {
				redirAttrs.addFlashAttribute("error", "El precio debe ser mayor a cero");
			} else if (libro.getResenia().isEmpty()) {
				redirAttrs.addFlashAttribute("error", "Debe escribir al menos una letra en la reseña del libro");
			}
			else {
				if (libroDTO.getImagen() != null) {
					if (servicioLibro.fileUsed(libroDTO.getImagen().getOriginalFilename())) {
						redirAttrs.addFlashAttribute("error", "La imagen de portada a cambiar ya se encuentra asociada a un libro");
						return "redirect:/book/" + id + "/edit";
					}
					else {
						try {
							MultipartFile imagen = libroDTO.getImagen();
							byte[] bytes = imagen.getBytes();
							libroExistente.setNombreImagen(imagen.getOriginalFilename());
							libroExistente.setBytesImagen(bytes);
						} catch (Exception e) {
							redirAttrs.addFlashAttribute("error",
									"Ocurrio un error al guardar la imagen");
							return "redirect:/book/" + id + "/edit";
						}
					}
				}

				if (libroDTO.getLibro() != null) {
					if (servicioLibro.fileUsed(libroDTO.getArchivoPdf().getOriginalFilename())) {
						redirAttrs.addFlashAttribute("error", "El archivo pdf ya se encuentra asociado a otro libro");
					}
					else {
						try {
							MultipartFile archivoPdf = libroDTO.getArchivoPdf();
							byte[] bytes = archivoPdf.getBytes();
							libro.setNombreArchivo(archivoPdf.getOriginalFilename());
							libro.setBytesArchivo(bytes);
						} catch (Exception e) {
							redirAttrs.addFlashAttribute("error", "Ocurrio un error al guardar el archivo pdf ");
							return "redirect:/book/" + id + "/edit";
						}
					}
				}

				libroExistente.setResenia(libro.getResenia());
				libroExistente.setStock(libro.getStock());
				libroExistente.setPrecio(libro.getPrecio());

				List<Integer> id_categorias = libroDTO.getListaCategorias();

				libroExistente.getCategorias().clear();

				for (Integer id_categoria : id_categorias) {
					Categoria categoria = servicioCategoria.getCategoriaXId(id_categoria);
					libroExistente.getCategorias().add(categoria);
				}

				servicioLibro.saveLibro(libroExistente);
				redirAttrs.addFlashAttribute("success",
						"El libro \"" + libroExistente.getNombre() + "\" se ha editado correctamente");
			}
			return "redirect:/book/" + id + "/edit";
		} catch (NoSuchElementException e) {
			redirAttrs.addFlashAttribute("error", "Error al intentar editar los datos del libro");
			return "redirect:/book/" + id + "/edit";
		}
	}

	@DeleteMapping("/book/delete/{id}")
	public void delete(@PathVariable Integer id) {
		servicioLibro.deleteLibro(id);
	}

	@PostMapping(value = "/book/add", consumes = "multipart/form-data")
	public String addLibro(LibroCategoriaDTO libroDTO, RedirectAttributes redirAttrs) {

		Libro libro = libroDTO.getLibro();

		if (servicioLibro.titleUsed(libro.getNombre())) {
			redirAttrs.addFlashAttribute("error", "El título ingresado ya se encuentra en uso, intente nuevamente");
		} else if (servicioLibro.fileUsed(libroDTO.getImagen().getOriginalFilename())) {
			redirAttrs.addFlashAttribute("error", "La imagen de portada ya se encuentra asociada a un libro");
		} else if (servicioLibro.fileUsed(libroDTO.getArchivoPdf().getOriginalFilename())) {
			redirAttrs.addFlashAttribute("error", "El archivo pdf ya se encuentra asociado a otro libro");
		} else if (servicioLibro.isbnUsed(libro.getIsbn())) {
			redirAttrs.addFlashAttribute("error", "El código ISBN ya se encuentra en uso, intente nuevamente");
		} else if (servicioLibro.skuUsed(libro.getSku())) {
			redirAttrs.addFlashAttribute("error", "El SKU ingresado ya se encuentra en uso, intente nuevamente");
		} else if (libroDTO.getListaCategorias() == null) {
			redirAttrs.addFlashAttribute("error", "Debe seleccionar al menos una categoría");
		}
		else {
			try {
				MultipartFile imagen = libroDTO.getImagen();
				byte[] bytes = imagen.getBytes();
				libro.setNombreImagen(imagen.getOriginalFilename());
				libro.setBytesImagen(bytes);
			} catch (Exception e) {
				redirAttrs.addFlashAttribute("error",
						"Ocurrio un error al guardar la imagen");
				return "redirect:/book/create";
			}

			try {
				MultipartFile archivoPdf = libroDTO.getArchivoPdf();
				byte[] bytes = archivoPdf.getBytes();
				libro.setNombreArchivo(archivoPdf.getOriginalFilename());
				libro.setBytesArchivo(bytes);
			} catch (Exception e) {
				redirAttrs.addFlashAttribute("error", "Ocurrio un error al guardar el archivo pdf ");
				return "redirect:/book/create";
			}
			Idioma idioma = servicioIdioma.getIdiomaXId(libroDTO.getIdioma_id());
			libro.setIdioma(idioma);

			List<Integer> idCategorias = libroDTO.getListaCategorias();

			for (Integer id : idCategorias) {
				Categoria categoria = servicioCategoria.getCategoriaXId(id);
				libro.getCategorias().add(categoria);
			}

			servicioLibro.saveLibro(libro);

			redirAttrs.addFlashAttribute("success", "El libro \"" + libro.getNombre() + "\" se ha registrado correctamente");
		}
		return "redirect:/book/create";
	}

	@GetMapping("/book/create")
	public String crear(Model model) {

		LibroCategoriaDTO libroDTO = new LibroCategoriaDTO();
		libroDTO.setLibro(new Libro());
		List<Idioma> listIdiomas = servicioIdioma.listaIdiomas();
		List<Categoria> listCategorias = servicioCategoria.listaCategorias();

		model.addAttribute("libroDTO", libroDTO);
		model.addAttribute("idiomas", listIdiomas);
		model.addAttribute("categorias", listCategorias);

		return "/newbook";
	}

	@GetMapping("/book/{id}/edit")
	public String editar(@PathVariable Integer id, Model model, RedirectAttributes redirAttrs) {

		Libro libro = null;

		try {
			libro = servicioLibro.getLibroXId(id);
		} catch (Exception e) {
			redirAttrs.addFlashAttribute("error", "No existe ningun libro asociado a ese id");
			return "redirect:/book/create";
		}

		LibroCategoriaDTO libroDTO = new LibroCategoriaDTO();
		List<Integer> categoriasLibro = new ArrayList<Integer>();

		for (Categoria categoria : libro.getCategorias()) {
			categoriasLibro.add(categoria.getId());
		}

		model.addAttribute("libroDTO", libroDTO);
		model.addAttribute("libro", libro);
		model.addAttribute("categoriasLibro", categoriasLibro);
		model.addAttribute("categorias", servicioCategoria.listaCategorias());
		model.addAttribute("idioma", libro.getIdioma());

		return "/editbook";
	}

	@GetMapping("/book/{id}/image")
	public void bookImage(@PathVariable Integer id, HttpServletResponse response) throws IOException {
		var libro = servicioLibro.getLibroXId(id);

		response.setContentType("image/jpeg; image/jpg; image/png");
		InputStream is = new ByteArrayInputStream(libro.getBytesImagen());
		IOUtils.copy(is, response.getOutputStream());
	}

	@GetMapping("/book/{id}/pdf")
	public ResponseEntity<byte[]> bookPdf(@PathVariable Integer id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));

		var libro = servicioLibro.getLibroXId(id);

		String filename = libro.getNombreArchivo();

		headers.add("content-disposition", "inline;filename=" + filename);

		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(libro.getBytesArchivo(), headers, HttpStatus.OK);
		return response;
	}
}
