package com.cmcorp.spring.BibliotecaDelDesierto;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Categoria;
import com.cmcorp.spring.BibliotecaDelDesierto.model.Idioma;
import com.cmcorp.spring.BibliotecaDelDesierto.model.Libro;
import com.cmcorp.spring.BibliotecaDelDesierto.model.dto.LibroCategoriaDTO;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioCategoria;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioIdioma;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioLibro;

@Controller
public class ControladorLibroClient {

	private final ServicioLibro servicioLibro;
	private final ServicioCategoria servicioCategoria;
	private final ServicioIdioma servicioIdioma;

	@Autowired
	public ControladorLibroClient(ServicioLibro servicioLibro, ServicioCategoria servicioCategoria, ServicioIdioma servicioIdioma) {
		this.servicioLibro = servicioLibro;
		this.servicioCategoria = servicioCategoria;
		this.servicioIdioma = servicioIdioma;
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
					if (libroDTO.getImagen().getOriginalFilename() != "") {
						if (servicioLibro.fileUsed(libroDTO.getImagen().getOriginalFilename())) {
							redirAttrs.addFlashAttribute("error", "La imagen de portada a cambiar ya se encuentra asociada a un libro");
							return "redirect:/book/" + id + "/edit";
						} else {
							try {
								MultipartFile imagen = libroDTO.getImagen();
								byte[] bytes = imagen.getBytes();
								libro.setNombreImagen(imagen.getOriginalFilename());
								libro.setBytesImagen(bytes);
							} catch (Exception e) {
								redirAttrs.addFlashAttribute("error",
										"Ocurrio un error al guardar la imagen");
								return "redirect:/book/" + id + "/edit";
							}
						}
					}
				}

				if (libroDTO.getArchivoPdf() != null) {
					if (libroDTO.getArchivoPdf().getOriginalFilename() != "") {
						if (servicioLibro.fileUsed(libroDTO.getArchivoPdf().getOriginalFilename())) {
							redirAttrs.addFlashAttribute("error", "El archivo pdf ya se encuentra asociado a otro libro");
						} else {
							try {
								{
									MultipartFile archivoPdf = libroDTO.getArchivoPdf();
									byte[] bytes = archivoPdf.getBytes();
									libro.setNombreArchivo(archivoPdf.getOriginalFilename());
									libro.setBytesArchivo(bytes);
								}
							} catch (Exception e) {
								redirAttrs.addFlashAttribute("error", "Ocurrio un error al guardar el archivo pdf ");
								return "redirect:/book/" + id + "/edit";
							}
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

				if (libro.getBytesImagen() != null) {
					libroExistente.setNombreImagen(libro.getNombreImagen());
					libroExistente.setBytesImagen(libro.getBytesImagen());
				}

				if (libro.getBytesArchivo() != null) {
					libroExistente.setNombreArchivo(libro.getNombreArchivo());
					libroExistente.setBytesArchivo(libro.getBytesArchivo());
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

	@GetMapping("/book/{id}/read")
	public String readBook(@PathVariable Integer id, Model model) {
		model.addAttribute("id", id);
		return "readbook";
	}

	@GetMapping("/bookgrid")
	public String bookgrid(Model model) {
		ResponseEntity<Libro[]> responseEntity = new RestTemplate().getForEntity("http://localhost:8080/books", Libro[].class);
		if(responseEntity.getStatusCode() == HttpStatus.OK) {
			model.addAttribute("libros", responseEntity.getBody());
			return "bookgrid"; 
		}
		throw new RuntimeException("El servidor no respondio de forma correcta");
	}
	
    @GetMapping("/booklist")
    public String catalogoLista(Model model) {
		ResponseEntity<Libro[]> responseEntity = new RestTemplate().getForEntity("http://localhost:8080/books-", Libro[].class);
		if(responseEntity.getStatusCode() == HttpStatus.OK) {
			model.addAttribute("libros", responseEntity.getBody());
			return "booklist"; 
		}
		throw new RuntimeException("El servidor no respondio de forma correcta");
    }    	
    
    @GetMapping("/book/{id}/summary")
    public String verLibro(@PathVariable Integer id, Model model) {
		ResponseEntity<Libro> responseEntity = new RestTemplate().getForEntity("http://localhost:8080/book/byid/"+id, Libro.class);
		if(responseEntity.getStatusCode() == HttpStatus.OK) {
			model.addAttribute("libro", responseEntity.getBody());

			List<Categoria> listaCategorias = new ArrayList<Categoria>(responseEntity.getBody().getCategorias());
			String categorias = listaCategorias.stream().map(Categoria::getNombre).collect(Collectors.joining(", "));

			model.addAttribute("categorias", categorias);

			return "summary"; 
		}
		throw new RuntimeException("El servidor no respondio de forma correcta");
    }      
	
	
}
