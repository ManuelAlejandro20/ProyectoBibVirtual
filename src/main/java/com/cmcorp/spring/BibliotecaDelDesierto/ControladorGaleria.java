package com.cmcorp.spring.BibliotecaDelDesierto;

import java.util.Arrays;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorGaleria {
    @GetMapping("/galeria")
    public List<Libro> galeria (Model model){
        Libro[] libros = new Libro[2];
        libros[0] = new Libro("Antonio Javier y las formas de hablar bien", 
        				"La galeria del libro",
        				"Rebeca Mena Rodriguez",
        				22,
        				"978-84-938495-1-1",
        				35000,
        				33,
        				"Muy bueno",
        				"AJylFdHB.png",
        				"Español",
        				32,
        				"book.pdf");
        libros[1] = new Libro("El mono pelon", 
							"La galeria del libro",
							"GARABATOS",
							45,
							"978-84-938495-4-2",
							20000,
							15,
							"Muy MALO",
							"siwjfmvl.png",
							"Español",
							30,
							"book2.pdf");
        return Arrays.asList(libros);
    }
}
