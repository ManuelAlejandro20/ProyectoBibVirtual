package com.cmcorp.spring.BibliotecaDelDesierto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LibroCompra> librosCompras;

    @Column(name = "descuento", nullable = false)
    private int descuento;

    @Column(name = "monto_total", nullable = false)
    private int montoTotal;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @CreationTimestamp()
    @Column(name = "fecha", nullable = false, updatable = false)
    private LocalDateTime fecha;

    @PrePersist
    public void setFecha() {
        this.fecha = LocalDateTime.now(ZoneId.of("Chile/Continental"));
    }

    public Compra(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id){this.id = id;}

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getMontoTotal() {
        return montoTotal;
    }

    public void setMontototal(int montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<LibroCompra> getLibrosCompras() {
        return librosCompras;
    }

    public void setLibrosCompras(Set<LibroCompra> librosCompras) {
        this.librosCompras = librosCompras;
    }
}