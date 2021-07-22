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

package com.cmcorp.spring.BibliotecaDelDesierto.service;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Compra;
import com.cmcorp.spring.BibliotecaDelDesierto.model.Libro;
import com.cmcorp.spring.BibliotecaDelDesierto.model.LibroCompra;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioCompra;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service of Compra
 */
@Service
@Transactional
public class ServicioCompra {
    @Autowired
    private RepositorioCompra repositorioCompra;

    /**
     * GET a all purchases by an user id
     * @param user_id
     * @return List<Compra>
     */
    public List<Compra> listaCompras(Integer user_id){
        return repositorioCompra.findByUserId(user_id);
    }

    /**
     * GET a all books purchases by an user id
     * @param userId
     * @return List<Libro>
     */
    public List<Libro> listaLibrosComprados(Integer userId) {
        var listaCompras = repositorioCompra.findByUserId(userId);
        var libros = new ArrayList<Libro>();
        for (Compra c : listaCompras) {
            for (LibroCompra l : c.getLibrosCompras()) {
                libros.add(l.getLibro());
            }
        }
        return libros;
    }

    /**
     * INSERT a buy
     * @param compra
     */
    public void saveCompra(Compra compra){
        repositorioCompra.save(compra);
    }
}
