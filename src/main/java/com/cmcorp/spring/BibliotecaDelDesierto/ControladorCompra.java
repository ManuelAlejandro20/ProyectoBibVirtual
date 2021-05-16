package com.cmcorp.spring.BibliotecaDelDesierto;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class ControladorCompra {
    @GetMapping("/compras")
    public List<Compra> compras(Model model) throws ParseException {
        model.addAttribute("tstamp", LocalDateTime.now());
        Compra[] compras = new Compra[2];
        compras[0] = new Compra(1,0,10000, new SimpleDateFormat("dd/MM/yyyy").parse("14/05/2021"));
        compras[1] = new Compra(2,10,9000, new SimpleDateFormat("dd/MM/yyyy").parse("13/05/2021"));
        return Arrays.asList(compras);
    }
}