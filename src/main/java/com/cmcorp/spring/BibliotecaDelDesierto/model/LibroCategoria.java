package com.cmcorp.spring.BibliotecaDelDesierto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "libro_categoria")
public class LibroCategoria {
    @Id
    private Integer id;

    @Column(name = "libro_id")
    private Integer libroId;

    @Column(name = "categoria_id")
    private Integer categoriaId;

    public LibroCategoria() {
    }
}
