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

import com.cmcorp.spring.BibliotecaDelDesierto.model.Idioma;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioIdioma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controller ControladorIdioma
 */
@RestController
public class ControladorIdioma {

    private final ServicioIdioma servicioIdioma;

    /**
     * Method that returns the controller
     * @param servicioIdioma
     */
    @Autowired
    public ControladorIdioma(ServicioIdioma servicioIdioma){
        this.servicioIdioma = servicioIdioma;
    }

    /**
     * Method that returns the list of Idiomas
     * @return
     */
    @GetMapping("idiomas")
    public List<Idioma> lista(){
        return servicioIdioma.listaIdiomas();
    }

    /**
     * Method that returns an Idioma by id
     * @param id
     * @return
     */
    @GetMapping("idioma/byid/{id}")
    public ResponseEntity<Idioma> getXId(@PathVariable Integer id){
        try {
            Idioma idioma = servicioIdioma.getIdiomaXId(id);
            return new ResponseEntity<Idioma>(idioma, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<Idioma>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method that adds a Idioma
     * @param idioma
     */
    @PostMapping("idioma/add")
    public void addIdioma(@RequestBody Idioma idioma){
        servicioIdioma.saveIdioma(idioma);
    }

    @PutMapping("idioma/update/{id}")
    public ResponseEntity<?> update(@RequestBody Idioma idioma, @PathVariable Integer id){
        try {
            Idioma idiomaExistente = servicioIdioma.getIdiomaXId(id);
            idioma.setId(id);
            servicioIdioma.saveIdioma(idioma);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<Idioma>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method that delete a Idioma by id
     * @param id
     */
    @DeleteMapping("idioma/delete/{id}")
    public void delete(@PathVariable Integer id){
        servicioIdioma.deleteIdioma(id);
    }
}
