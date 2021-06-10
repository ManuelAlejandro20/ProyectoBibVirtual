package com.cmcorp.spring.BibliotecaDelDesierto.service;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Libro;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class ServicioLibro {

    @Autowired
    private RepositorioLibro repositorioLibro;

    public List<Libro> listaLibros(){
        return repositorioLibro.findAll();
    }

    public List<Libro> getLibrosXCategorias(List<Integer> listaCategorias){
        Integer tamanio = listaCategorias.size();
        return repositorioLibro.findLibrosByCategoriasId(listaCategorias,tamanio);
    }

    public List<Libro> getLibrosXAutor(String autor){
        return repositorioLibro.findLibrosByAutor(autor);
    }

    public List<Libro> getLibrosXIdioma(Integer id){
        return repositorioLibro.findByIdiomaId(id);
    }

    public void saveLibro(Libro libro){
        repositorioLibro.save(libro);
    }

    public Libro getLibroXId(Integer id){
        return repositorioLibro.findById(id).get();
    }

    public Libro getLibroXIsbn(String isbn){
        return repositorioLibro.findByIsbn(isbn);
    }

    public Libro getLibroXSku(String sku){
        return repositorioLibro.findBySku(sku);
    }

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
