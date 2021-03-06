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

import com.cmcorp.spring.BibliotecaDelDesierto.model.Libro;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Class LibroCategoriaDTO
 */
public class LibroCategoriaDTO {

    /**
     * Entity Libro
     */
    private Libro libro;

    /**
     * Integer id Idioma
     */
    private Integer idioma_id;

    /**
     * List of categories
     */
    private List<Integer> listaCategorias;

    private MultipartFile imagen;
    private MultipartFile archivoPdf;

    /**
     * Constructor
     */
    public LibroCategoriaDTO(){

    }

    /**
     * Constructor with params
     * @param libro
     * @param idioma_id
     * @param listaCategorias
     */
    public LibroCategoriaDTO(Libro libro, Integer idioma_id, List<Integer> listaCategorias) {
        this.libro = libro;
        this.idioma_id = idioma_id;
        this.listaCategorias = listaCategorias;
    }

    public Libro getLibro() {
        return libro;
    }

    public Integer getIdioma_id() {
        return idioma_id;
    }

    public List<Integer> getListaCategorias() {
        return listaCategorias;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public void setIdioma_id(Integer idioma_id) {
        this.idioma_id = idioma_id;
    }

    public void setListaCategorias(List<Integer> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public MultipartFile getImagen() {
        return imagen;
    }

    public void setImagen(MultipartFile imagen) {
        this.imagen = imagen;
    }

    public MultipartFile getArchivoPdf() {
        return archivoPdf;
    }

    public void setArchivoPdf(MultipartFile archivoPdf) {
        this.archivoPdf = archivoPdf;
    }

}
