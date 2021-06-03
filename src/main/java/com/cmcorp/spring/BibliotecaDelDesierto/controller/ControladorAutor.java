package com.cmcorp.spring.BibliotecaDelDesierto.controller;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Autor;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioAutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ControladorAutor {

    private final ServicioAutor servicioAutor;

    @Autowired
    public ControladorAutor(ServicioAutor servicioAutor){this.servicioAutor = servicioAutor;}

    @GetMapping("autores")
    public List<Autor> lista(){
        return servicioAutor.listaAutores();
    }

    @GetMapping("autor/byid/{id}")
    public ResponseEntity<Autor> getXId(@PathVariable Integer id){
        try {
            Autor autor = servicioAutor.getAutorXId(id);
            return new ResponseEntity<Autor>(autor, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<Autor>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("autor/add")
    public void addAutor(@RequestBody Autor autor){
        servicioAutor.saveUser(autor);
    }

    @PutMapping("autor/update/{id}")
    public ResponseEntity<?> update(@RequestBody Autor autor, @PathVariable Integer id){
        try {
            Autor autorExistente = servicioAutor.getAutorXId(id);
            autor.setId(id);
            servicioAutor.saveUser(autor);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("autor/delete/{id}")
    public void delete(@PathVariable Integer id){
        servicioAutor.deleteAutor(id);
    }
}
