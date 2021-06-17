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
 * Copyright (C) 2021 X Consortium
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE X CONSORTIUM BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Except as contained in this notice, the name of the X Consortium shall not be used in advertising or otherwise to promote the sale, use or other dealings in this Software without prior written authorization from the X Consortium.
 *
 * X Window System is a trademark of X Consortium, Inc.
 */

package com.cmcorp.spring.BibliotecaDelDesierto.controller;

import com.cmcorp.spring.BibliotecaDelDesierto.model.*;
import com.cmcorp.spring.BibliotecaDelDesierto.model.dto.LibroCompraDTO;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioCompra;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioLibro;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioLibroCompra;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller Controlador Compra
 */
@RestController
public class ControladorCompra {
    @Autowired
    private final ServicioCompra servicioCompra;
    @Autowired
    private final ServicioUser servicioUser;
    @Autowired
    private final ServicioLibro servicioLibro;
    @Autowired
    private final ServicioLibroCompra servicioLC;

    public ControladorCompra(ServicioCompra servicioCompra, ServicioUser servicioUser, ServicioLibro servicioLibro, ServicioLibroCompra servicioLC){
        this.servicioCompra = servicioCompra;
        this.servicioUser = servicioUser;
        this.servicioLibro = servicioLibro;
        this.servicioLC = servicioLC;
    }

    @GetMapping(value = {"compras/{userId}","user/{userId}/compras"})
    public List<Compra> compras(@PathVariable Integer userId){
        return servicioCompra.listaCompras(userId);
    }

    @PostMapping("compras/add/{userId}")
    public void addCompra(@RequestBody LibroCompraDTO libroCompraDTO, @PathVariable Integer userId){
        User user = servicioUser.getUserXId(userId);

        for (Integer[] lista : libroCompraDTO.getListaLibrosCant()){
            LibroCompra libroCompra = new LibroCompra();

            Compra compra = libroCompraDTO.getCompra();
            compra.setUser(user);
            servicioCompra.saveCompra(compra);

            Libro libro = servicioLibro.getLibroXId(lista[0]);

            libroCompra.setCompra(compra);
            libroCompra.setLibro(libro);
            libroCompra.setUnidades(lista[1]);

            servicioLC.addLibroCompra(libroCompra);
        }
    }
}