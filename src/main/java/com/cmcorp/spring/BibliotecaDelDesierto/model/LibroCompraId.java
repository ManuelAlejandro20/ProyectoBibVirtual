package com.cmcorp.spring.BibliotecaDelDesierto.model;

import java.io.Serializable;

public class LibroCompraId implements Serializable {
    private Integer libro;
    private Integer compra;

    public Integer getLibro() {
        return libro;
    }

    public void setLibro(Integer libro) {
        this.libro = libro;
    }

    public Integer getCompra() {
        return compra;
    }

    public void setCompra(Integer compra) {
        this.compra = compra;
    }
}
