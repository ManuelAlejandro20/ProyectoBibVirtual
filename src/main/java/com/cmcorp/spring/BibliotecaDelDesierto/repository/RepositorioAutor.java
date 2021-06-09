package com.cmcorp.spring.BibliotecaDelDesierto.repository;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioAutor extends JpaRepository<Autor, Integer> {
    Autor findByNombre(@Param("nombre") String nombre);
}
