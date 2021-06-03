package com.cmcorp.spring.BibliotecaDelDesierto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "compra")
public class Compra {
    private static final String date_formatter = "dd-MM-yyyy HH:mm:ss";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @Column(name = "descuento", nullable = false)
    private int descuento;

    @Column(name = "monto_total", nullable = false)
    private int monto_total;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @CreationTimestamp()
    @Column(name = "fecha", nullable = false, updatable = false)
    private LocalDateTime fecha;

    @PrePersist
    public void setFecha() {
        this.fecha = LocalDateTime.now(ZoneId.of("Chile/Continental"));
    }

    public Compra(){}

    public Compra(int descuento, int monto_total) {
        this.descuento = descuento;
        this.monto_total = monto_total;
    }

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

    public int getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(int monto_total) {
        this.monto_total = monto_total;
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
}