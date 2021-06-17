package com.cmcorp.spring.BibliotecaDelDesierto.service;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Idioma;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioIdioma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service of Idioma
 */
@Service
@Transactional
public class ServicioIdioma {

    @Autowired
    private RepositorioIdioma repositorioIdioma;

    /**
     * GET all languages
     * @return List<Idioma>
     */
    public List<Idioma> listaIdiomas(){
        return repositorioIdioma.findAll();
    }

    /**
     * INSERT a language
     * @param idioma
     */
    public void saveIdioma(Idioma idioma){
        repositorioIdioma.save(idioma);
    }

    /**
     * GET a language by id
     * @param id
     * @return Idioma
     */
    public Idioma getIdiomaXId(Integer id){
        return repositorioIdioma.findById(id).get();
    }

    /**
     * DELETE a language by id
     * @param id
     */
    public void deleteIdioma(Integer id){
        repositorioIdioma.deleteById(id);
    }
}
