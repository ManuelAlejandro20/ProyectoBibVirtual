package com.cmcorp.spring.BibliotecaDelDesierto;

import java.util.Date;

public class Compra {
    private int id;
    private int userId;
    private int descuento;
    private int MontoTotal;
    private Date fecha;

    public Compra(int userId, int descuento, int montoTotal, Date fecha) {
        this.userId = userId;
        this.descuento = descuento;
        MontoTotal = montoTotal;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getMontoTotal() {
        return MontoTotal;
    }

    public void setMontoTotal(int montoTotal) {
        MontoTotal = montoTotal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}