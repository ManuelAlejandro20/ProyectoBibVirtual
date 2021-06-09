package com.cmcorp.spring.BibliotecaDelDesierto.service;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Compra;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioCompra {
    @Autowired
    private RepositorioCompra repositorioCompra;

    public List<Compra> listaCompras(Integer user_id){
        return repositorioCompra.findByUserId(user_id);
    }

    public void saveCompra(Compra compra){
        repositorioCompra.save(compra);
    }
}
