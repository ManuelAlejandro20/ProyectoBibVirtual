package com.cmcorp.spring.BibliotecaDelDesierto.model.dto;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Libro;

import java.util.List;

public class LibroCategoriaDTO {
    private Libro libro;
    private Integer idioma_id;

    private List<Integer> lista_categorias;

    public LibroCategoriaDTO(){}

    public Libro getLibro() {
        return libro;
    }

    public Integer getIdioma_id() {
        return idioma_id;
    }

    public List<Integer> getLista_categorias() {
        return lista_categorias;
    }
}
