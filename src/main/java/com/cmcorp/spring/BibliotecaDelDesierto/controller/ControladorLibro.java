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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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

    @GetMapping("/book/create")
    public ModelAndView crear(){

        Libro libro = new Libro();
        List<Idioma> listIdiomas = servicioIdioma.listaIdiomas();
        List<Categoria> listCategorias = servicioCategoria.listaCategorias();

        ModelAndView mv = new ModelAndView("modulo-admistrador-crear-libro");

        mv.addObject("libro", libro);
        mv.addObject("idiomas", listIdiomas);
        mv.addObject("categorias", listCategorias);

        return mv;
    }

    @GetMapping("/book/edit/{id}")
    public ModelAndView editar(@PathVariable Integer id){

        Libro libro = servicioLibro.getLibroXId(id);
        List<Categoria> categorias = new ArrayList<Categoria>();

        for (Categoria categoria : libro.getCategorias())
        {
            categorias.add(categoria);
        }

        ModelAndView mv = new ModelAndView("modulo-admistrador-editar-libro");
        mv.addObject("libro", libro);
        //mv.addObject("cantpaginas", libro.getCantidadPags());

        return mv;
    }
}
