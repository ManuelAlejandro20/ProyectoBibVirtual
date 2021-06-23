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

package com.cmcorp.spring.BibliotecaDelDesierto.service;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Libro;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Service of Libro
 */
@Service
@Transactional
public class ServicioLibro {

    @Autowired
    private RepositorioLibro repositorioLibro;

    /**
     * GET all the books
     * @return List<Libro>
     */
    public List<Libro> listaLibros(){
        return repositorioLibro.findAll();
    }

    /**
     * GET books by a list of categories
     * @param listaCategorias
     * @return List<Libro>
     */
    public List<Libro> getLibrosXCategorias(List<Integer> listaCategorias){
        Integer tamanio = listaCategorias.size();
        return repositorioLibro.findLibrosByCategoriasId(listaCategorias,tamanio);
    }

    /**
     * GET all books by his autor
     * @param autor
     * @return List<Libro>
     */
    public List<Libro> getLibrosXAutor(String autor){
        return repositorioLibro.findLibrosByAutor(autor);
    }

    /**
     * GET all books by his language id
     * @param id
     * @return List<Libro>
     */
    public List<Libro> getLibrosXIdioma(Integer id){
        return repositorioLibro.findByIdiomaId(id);
    }

    /**
     * INSERT a book
     * @param libro
     */
    public void saveLibro(Libro libro){
        repositorioLibro.save(libro);
    }

    /**
     * GET a book by his id
     * @param id
     * @return Libro
     */
    public Libro getLibroXId(Integer id){
        return repositorioLibro.findById(id).get();
    }

    /**
     * GET a book by his Isbn id
     * @param isbn
     * @return Libro
     */
    public Libro getLibroXIsbn(String isbn){
        return repositorioLibro.findByIsbn(isbn);
    }

    /**
     * GET a book by his Sku id
     * @param sku
     * @return Libro
     */
    public Libro getLibroXSku(String sku){
        return repositorioLibro.findBySku(sku);
    }

    /**
     * DELETE a book by his id
     * @param id
     */
    public void deleteLibro(Integer id){
        if(repositorioLibro.existsById(id)) {
            Libro libro = repositorioLibro.findById(id).get();

            libro.getCategorias().clear();

            libro.setAutor(null);
            libro.setIdioma(null);

            repositorioLibro.deleteById(id);
        }
    }
}
