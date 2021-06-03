package com.cmcorp.spring.BibliotecaDelDesierto.service;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Autor;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioAutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioAutor {
    @Autowired
    private RepositorioAutor repositorioAutor;

    public List<Autor> listaAutores(){
        return repositorioAutor.findAll();
    }

    public void saveUser(Autor usuario){
        repositorioAutor.save(usuario);
    }

    public Autor getAutorXId(Integer id){
        return repositorioAutor.findById(id).get();
    }

    public Autor getAutorXNombre(String nombre){
        return repositorioAutor.findByNombre(nombre);
    }

    public void deleteAutor(Integer id){
        repositorioAutor.deleteById(id);
    }
}
