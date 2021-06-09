package com.cmcorp.spring.BibliotecaDelDesierto.service;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Categoria;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class ServicioCategoria {
    @Autowired
    private RepositorioCategoria repositorioCategoria;

    public List<Categoria> listaCategorias(){
        return repositorioCategoria.findAll();
    }

    public void saveCategoria(Categoria categoria){
        repositorioCategoria.save(categoria);
    }

    public Categoria getCategoriaXId(Integer id){
        return repositorioCategoria.findById(id).get();
    }

    public Categoria getCategoriaXNombre(String nombre){
        return repositorioCategoria.findByNombre(nombre);
    }

    public void deleteCategoria(Integer id){
        repositorioCategoria.deleteById(id);
    }
}
