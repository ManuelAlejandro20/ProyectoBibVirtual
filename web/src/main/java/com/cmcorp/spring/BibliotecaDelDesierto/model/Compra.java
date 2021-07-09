/*
 * Copyright (c) 2021 CMCORP
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * An intermediate form of license used by the X Consortium for X11 used the following wording:[16]
 *
 */

package com.cmcorp.spring.BibliotecaDelDesierto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Compra
 */
@Entity
@Table(name = "compra")
public class Compra {

    /**
     * Integer unsigned for id Compra
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * User of the Compra
     */
    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    /**
     * List of books of the Compra
     */
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LibroCompra> librosCompras;

    /**
     * Integer beetween 0 to 100
     */
    @Column(name = "descuento", nullable = false)
    private int descuento;

    /**
     * Integer unsigned
     */
    @Column(name = "monto_total", nullable = false)
    private int montoTotal;

    /**
     * Format LocalDateTime dd-MM-yyyy HH:mm:ss
     */
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @CreationTimestamp()
    @Column(name = "fecha", nullable = false, updatable = false)
    private LocalDateTime fecha;

    /**
     * Set the Local time zone
     */
    @PrePersist
    public void setFecha() {
        this.fecha = LocalDateTime.now(ZoneId.of("Chile/Continental"));
    }

    /**
     * Constructor
     */
    public Compra(){}

    /**
     * Constructor with params
     * @param id
     * @param user
     * @param librosCompras
     * @param descuento
     * @param montoTotal
     * @param fecha
     */
    public Compra(Integer id, User user, Set<LibroCompra> librosCompras, int descuento, int montoTotal, LocalDateTime fecha) {
        this.id = id;
        this.user = user;
        this.librosCompras = librosCompras;
        this.descuento = descuento;
        this.montoTotal = montoTotal;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}