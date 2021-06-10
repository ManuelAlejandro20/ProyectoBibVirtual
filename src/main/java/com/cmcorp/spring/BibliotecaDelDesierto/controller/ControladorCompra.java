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