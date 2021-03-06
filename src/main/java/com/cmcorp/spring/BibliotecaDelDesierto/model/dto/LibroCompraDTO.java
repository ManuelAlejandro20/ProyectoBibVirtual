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

package com.cmcorp.spring.BibliotecaDelDesierto.model.dto;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Compra;

/**
 *  Class LibroCompraDTO
 */
public class LibroCompraDTO {

    /**
     * Bidimensional array for the listaLibrosCant
     * Rows index for the
     */
    private Integer[][] listaLibrosCant;

    /**
     * Entity Compra
     */
    private Compra compra;

    /**
     * Constructor
     */
    public LibroCompraDTO() {

    }

    /**
     * Constructo with params
     * @param listaLibrosCant
     * @param compra
     */
    public LibroCompraDTO(Integer[][] listaLibrosCant, Compra compra) {
        this.listaLibrosCant = listaLibrosCant;
        this.compra = compra;
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
