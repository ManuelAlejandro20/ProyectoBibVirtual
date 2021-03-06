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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class LibroCategoria
 */
@Entity
@Table(name = "libro_categoria")
public class LibroCategoria {

    /**
     * Integer unsigned
     */
    @Id
    private Integer id;

    /**
     * Integer unsigned
     * id of the book
     */
    @Column(name = "libro_id")
    private Integer libroId;

    /**
     * Integer unsigned
     * id of the category
     */
    @Column(name = "categoria_id")
    private Integer categoriaId;

    /**
     * Constructor
     */
    public LibroCategoria() {
    }

    /**
     * Constructor with params
     * @param id
     * @param libroId
     * @param categoriaId
     */
    public LibroCategoria(Integer id, Integer libroId, Integer categoriaId) {
        this.id = id;
        this.libroId = libroId;
        this.categoriaId = categoriaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLibroId() {
        return libroId;
    }

    public void setLibroId(Integer libroId) {
        this.libroId = libroId;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }
}
