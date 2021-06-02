package com.cmcorp.spring.BibliotecaDelDesierto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ControladorPantallaPrincipal {
	
    @GetMapping("/")
    public String index() {
        return "index";
    }
 
    @GetMapping("/catalogo-panel")
    public String catalogo() {
        return "catalogo-panel";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }  
    
    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }      
    
}
