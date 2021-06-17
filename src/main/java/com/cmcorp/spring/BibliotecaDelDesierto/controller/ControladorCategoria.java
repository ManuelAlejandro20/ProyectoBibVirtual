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

import com.cmcorp.spring.BibliotecaDelDesierto.model.Categoria;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controlller ControladorCategoria
 */
@RestController
//@RequestMapping("/categorias")
public class ControladorCategoria {

    private final ServicioCategoria servicioCategoria;

    @Autowired
    public ControladorCategoria(ServicioCategoria servicioCategoria){
        this.servicioCategoria = servicioCategoria;
    }

    /**
     * Method that returns the lists of categories
     * @return List of categories
     */
    @GetMapping("categorias")
    public List<Categoria> lista(){
        return servicioCategoria.listaCategorias();
    }

    /**
     * Method that returns the lists of categories by id
     * @param id  id of the category
     * @return
     */
    @GetMapping("categoria/byid/{id}")
    public ResponseEntity<Categoria> getXId(@PathVariable Integer id){
        try {
            Categoria categoria = servicioCategoria.getCategoriaXId(id);
            return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<Categoria>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method that returns the list of category by name
     * @param nombre
     * @return
     */
    @GetMapping("categoria/byname/{nombre}")
    public ResponseEntity<Categoria> getXNombre(@PathVariable String nombre){
        try {
            Categoria categoria = servicioCategoria.getCategoriaXNombre(nombre);
            return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<Categoria>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method to add a category
     * @param categoria
     */
    @PostMapping("categoria/add")
    public void addCategoria(@RequestBody Categoria categoria){
        servicioCategoria.saveCategoria(categoria);
    }

    /**
     * Method to update a category
     * @param categoria
     * @param id
     * @return
     */
    @PutMapping("categoria/update/{id}")
    public ResponseEntity<?> update(@RequestBody Categoria categoria, @PathVariable Integer id){
        try {
            Categoria categoriaExistente = servicioCategoria.getCategoriaXId(id);
            categoria.setId(id);
            servicioCategoria.saveCategoria(categoria);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<Categoria>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method to delete a category
     * @param id
     */
    @DeleteMapping("categoria/delete/{id}")
    public void delete(@PathVariable Integer id){
        servicioCategoria.deleteCategoria(id);
    }
}
