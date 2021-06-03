package com.cmcorp.spring.BibliotecaDelDesierto.model;

import java.util.List;

public class LibroCategoria {
    private Libro libro;
    private Integer idioma_id;
    private Integer autor_id;
    private List<Integer> lista_categorias;

    public LibroCategoria(){}

    public Libro getLibro() {
        return libro;
    }

    public Integer getIdioma_id() {
        return idioma_id;
    }

    public Integer getAutor_id() {
        return autor_id;
    }

    public List<Integer> getLista_categorias() {
        return lista_categorias;
    }
}
