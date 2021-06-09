package com.cmcorp.spring.BibliotecaDelDesierto.controller;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Idioma;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioIdioma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ControladorIdioma {

    private final ServicioIdioma servicioIdioma;

    @Autowired
    public ControladorIdioma(ServicioIdioma servicioIdioma){
        this.servicioIdioma = servicioIdioma;
    }

    @GetMapping("idiomas")
    public List<Idioma> lista(){
        return servicioIdioma.listaIdiomas();
    }

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

    @DeleteMapping("idioma/delete/{id}")
    public void delete(@PathVariable Integer id){
        servicioIdioma.deleteIdioma(id);
    }
}
