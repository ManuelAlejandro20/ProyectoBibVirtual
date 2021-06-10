package com.cmcorp.spring.BibliotecaDelDesierto.service;

import com.cmcorp.spring.BibliotecaDelDesierto.model.LibroCompra;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioLibroCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioLibroCompra {
    @Autowired
    private RepositorioLibroCompra repositorioLC;

    public void addLibroCompra(LibroCompra libroCompra){
        repositorioLC.save(libroCompra);
    }
}
