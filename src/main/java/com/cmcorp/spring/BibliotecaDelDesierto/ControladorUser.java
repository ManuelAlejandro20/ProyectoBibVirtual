package com.cmcorp.spring.BibliotecaDelDesierto;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ControladorUser {
    @RequestMapping(method = RequestMethod.GET, path = "/usuarios")
    public List<User> getAllUsers() {
        User[] users = new User[3];
        users[0] = new User(0, "pepito03@gmail.com", "pepe03", "manjar12", "Jose", "Gomez", "Costa", "Los lagos 1090", "usuario");
        users[1] = new User(1, "sofiajuana@yahoo.es", "soph", "jean_01", "Sofia", "Torres", "Rojas", "Amunategui 3010", "usuario");
        users[2] = new User(2, "mariasoto@hotmail.com", "mariasoto", "Z_gretel22", "Maria", "Soto", "Contreras", "Esmeralda 2130", "administrador");

        return Arrays.asList(users);
    }
}
