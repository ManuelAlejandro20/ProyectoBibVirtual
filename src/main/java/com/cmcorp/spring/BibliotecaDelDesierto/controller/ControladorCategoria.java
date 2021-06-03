package com.cmcorp.spring.BibliotecaDelDesierto.controller;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Categoria;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
//@RequestMapping("/categorias")
public class ControladorCategoria {

    private final ServicioCategoria servicioCategoria;

    @Autowired
    public ControladorCategoria(ServicioCategoria servicioCategoria){
        this.servicioCategoria = servicioCategoria;
    }

    @GetMapping("categorias")
    public List<Categoria> lista(){
        return servicioCategoria.listaCategorias();
    }

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

    @PostMapping("categoria/add")
    public void addCategoria(@RequestBody Categoria categoria){
        servicioCategoria.saveCategoria(categoria);
    }

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

    @DeleteMapping("categoria/delete/{id}")
    public void delete(@PathVariable Integer id){
        servicioCategoria.deleteCategoria(id);
    }
}
