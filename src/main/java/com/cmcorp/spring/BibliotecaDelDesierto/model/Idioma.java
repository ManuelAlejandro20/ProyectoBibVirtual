package com.cmcorp.spring.BibliotecaDelDesierto.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "idioma")
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

//    @OneToMany(mappedBy = "idioma", fetch = FetchType.LAZY)
//    private Set<Libro> libros = new HashSet<Libro>();

    public Idioma() {
    }

    public Idioma(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

//    public Set<Libro> getLibro() {
//        return libros;
//    }
//
//    public void setLibro(Set<Libro> libros) {
//        this.libros = libros;
//    }
}
