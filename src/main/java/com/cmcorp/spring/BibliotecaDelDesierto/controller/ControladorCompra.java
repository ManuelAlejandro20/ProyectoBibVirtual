package com.cmcorp.spring.BibliotecaDelDesierto.controller;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Compra;
import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioCompra;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class ControladorCompra {
    @Autowired
    private final ServicioCompra servicioCompra;
    @Autowired
    private final ServicioUser servicioUser;

    public ControladorCompra(ServicioCompra servicioCompra, ServicioUser servicioUser){
        this.servicioCompra = servicioCompra;
        this.servicioUser = servicioUser;
    }

    @GetMapping(value = {"compras/{userId}","user/{userId}/compras"})
    public List<Compra> compras(@PathVariable Integer userId){
        return servicioCompra.listaCompras(userId);
    }

    @PostMapping("/add/{userId}")
    public void addCompra(@RequestBody Compra compra, @PathVariable Integer userId){
        User user = servicioUser.getUserXId(userId);
        compra.setUser(user);
        servicioCompra.saveCompra(compra);
    }
}