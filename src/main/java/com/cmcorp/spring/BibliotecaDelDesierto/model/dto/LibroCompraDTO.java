package com.cmcorp.spring.BibliotecaDelDesierto.model.dto;


import com.cmcorp.spring.BibliotecaDelDesierto.model.Compra;

public class LibroCompraDTO {
    private Integer[][] listaLibrosCant;

    private Compra compra;

    public LibroCompraDTO() {
    }

    public Integer[][] getListaLibrosCant() {
        return listaLibrosCant;
    }

    public void setListaLibrosCant(Integer[][] listaLibrosCant) {
        this.listaLibrosCant = listaLibrosCant;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}
