package com.cmcorp.spring.BibliotecaDelDesierto.repository;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RepositorioCategoria extends JpaRepository<Categoria, Integer> {
    Categoria findByNombre(@Param("nombre") String nombre);
}
