package com.cmcorp.spring.BibliotecaDelDesierto.service;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Idioma;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioIdioma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioIdioma {
    @Autowired
    private RepositorioIdioma repositorioIdioma;

    public List<Idioma> listaIdiomas(){
        return repositorioIdioma.findAll();
    }

    public void saveIdioma(Idioma idioma){
        repositorioIdioma.save(idioma);
    }

    public Idioma getIdiomaXId(Integer id){
        return repositorioIdioma.findById(id).get();
    }

    public void deleteIdioma(Integer id){
        repositorioIdioma.deleteById(id);
    }
}
