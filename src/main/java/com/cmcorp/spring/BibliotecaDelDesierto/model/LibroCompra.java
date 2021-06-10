package com.cmcorp.spring.BibliotecaDelDesierto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "libro_compra")
@IdClass(LibroCompraId.class)
public class LibroCompra implements Serializable {

    @JsonIgnoreProperties(value = {"id","categorias","librosCompras","editorial","isbn","sku","stock","resenia","nombreImagen","nombreArchivo","cantidad_pag"})
    @Id
    @ManyToOne
    @JoinColumn(name = "libro_id", referencedColumnName = "id")
    private Libro libro;

    @JsonIgnore
    @Id
    @ManyToOne
    @JoinColumn(name = "compra_id", referencedColumnName = "id")
    private Compra compra;

    @JoinColumn(name = "unidades")
    private Integer unidades;

    public LibroCompra() {
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }
}
