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
