package com.cmcorp.spring.BibliotecaDelDesierto.controller;

import com.cmcorp.spring.BibliotecaDelDesierto.model.*;
import com.cmcorp.spring.BibliotecaDelDesierto.model.dto.LibroCategoriaDTO;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioCategoria;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioIdioma;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
public class ControladorLibro {

    @Autowired
    private final ServicioLibro servicioLibro;
    @Autowired
    private final ServicioCategoria servicioCategoria;
    @Autowired
    private final ServicioIdioma servicioIdioma;

    public ControladorLibro(ServicioLibro servicioLibro, ServicioCategoria servicioCategoria,
                            ServicioIdioma servicioIdioma){
        this.servicioLibro = servicioLibro;
        this.servicioCategoria = servicioCategoria;
        this.servicioIdioma = servicioIdioma;
    }

    @GetMapping("/books")
    public List<Libro> getAll() {
        return servicioLibro.listaLibros();
    }

    @GetMapping("/books/byautor/{autor}")
    public List<Libro> getAllXAutor(@PathVariable(value = "autor") String autor){
        return servicioLibro.getLibrosXAutor(autor);
    }

    @GetMapping("/books/byidioma/{idiomaId}")
    public List<Libro> getAllXIdioma(@PathVariable(value = "idiomaId") Integer idiomaId){
        return servicioLibro.getLibrosXIdioma(idiomaId);
    }

    @GetMapping("/books/bycategorias")
    public List<Libro> getAllXCategorias(@RequestBody LibroCategoriaDTO libroCategoriaDTO){
        List<Integer> listaIdCategorias = libroCategoriaDTO.getLista_categorias();
        return servicioLibro.getLibrosXCategorias(listaIdCategorias);
    }

    @GetMapping("/book/byid/{id}")
    public ResponseEntity<Libro> getXId(@PathVariable Integer id){
        try {
            Libro usuario = servicioLibro.getLibroXId(id);
            return new ResponseEntity<Libro>(usuario, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/book/bysku/{sku}")
    public ResponseEntity<Libro> getXSku(@PathVariable String sku){
        try {
            Libro usuario = servicioLibro.getLibroXSku(sku);
            return new ResponseEntity<Libro>(usuario, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/book/byisbn/{isbn}")
    public ResponseEntity<Libro> getXIsbn(@PathVariable String isbn){
        try {
            Libro libro = servicioLibro.getLibroXIsbn(isbn);
            return new ResponseEntity<Libro>(libro, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/book/update/{id}")
    public ResponseEntity<?> update(@RequestBody LibroCategoriaDTO libroCategoriaDTO, @PathVariable Integer id){
        try {
            Libro libroExistente = servicioLibro.getLibroXId(id);

            Libro libro = libroCategoriaDTO.getLibro();

            Idioma idioma = servicioIdioma.getIdiomaXId(libroCategoriaDTO.getIdioma_id());

            libro.setIdioma(idioma);

            List<Integer> id_categorias = libroCategoriaDTO.getLista_categorias();

            for (Integer id_categoria: id_categorias) {
                Categoria categoria = servicioCategoria.getCategoriaXId(id_categoria);
                libro.getCategorias().add(categoria);
            }

            libro.setId(id);
            servicioLibro.saveLibro(libro);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/book/delete/{id}")
    public void delete(@PathVariable Integer id){
        servicioLibro.deleteLibro(id);
    }

    @PostMapping("/book/add")
    public void addLibro(@RequestBody LibroCategoriaDTO libroCategoriaDTO){
        Libro libro = libroCategoriaDTO.getLibro();

        Idioma idioma = servicioIdioma.getIdiomaXId(libroCategoriaDTO.getIdioma_id());
        libro.setIdioma(idioma);

        List<Integer> id_categorias = libroCategoriaDTO.getLista_categorias();

        for (Integer id: id_categorias) {
            Categoria categoria = servicioCategoria.getCategoriaXId(id);
            libro.getCategorias().add(categoria);
        }
        servicioLibro.saveLibro(libro);
    }
}
