package com.cmcorp.spring.BibliotecaDelDesierto.controller;


import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
//@RequestMapping("/users")
public class ControladorUser {

    private final ServicioUser servicioUser;

    @Autowired
    public ControladorUser(ServicioUser servicioUser){
        this.servicioUser = servicioUser;
    }

    @GetMapping("/users")
    public List<User> lista(){
        return servicioUser.listaUsuarios();
    }

    @GetMapping("user/byid/{id}")
    public ResponseEntity<User> getXId(@PathVariable Integer id){
        try {
            User usuario = servicioUser.getUserXId(id);
            return new ResponseEntity<User>(usuario, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("user/byemail/{email}")
    public ResponseEntity<User> getXEmail(@PathVariable String email){
        try {
            User usuario = servicioUser.getUserXEmail(email);
            return new ResponseEntity<User>(usuario, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("user/add")
    public void addUser(@RequestBody User usuario){
        servicioUser.saveUser(usuario);
    }

    @PutMapping("user/update/{id}")
    public ResponseEntity<?> update(@RequestBody User usuario, @PathVariable Integer id){
        try {
            User usuarioExistente = servicioUser.getUserXId(id);
            usuario.setId(id);
            servicioUser.saveUser(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("user/delete/{id}")
    public void delete(@PathVariable Integer id){
        servicioUser.deleteUser(id);
    }
}
