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
import com.cmcorp.spring.BibliotecaDelDesierto.model.LibroCompra;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioLibroCompra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for LibroCompra
 */
@Service
@Transactional
public class ServicioLibroCompra {
    @Autowired
    private RepositorioLibroCompra repositorioLC;

    /**
     * INSERT a book to the buy list
     * @param libroCompra
     */
    public void addLibroCompra(LibroCompra libroCompra){
        repositorioLC.save(libroCompra);
    }
    
    /**
     * GET a all purchased books by a purchase
     * @param compra
     * @return List<LibroCompra>
     */
    public List<LibroCompra> listaLibros(Compra compra){
        return repositorioLC.findLibroCompraByCompra(compra);
    }	    
}
