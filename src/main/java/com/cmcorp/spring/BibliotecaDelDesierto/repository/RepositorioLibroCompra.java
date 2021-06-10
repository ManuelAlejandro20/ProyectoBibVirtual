package com.cmcorp.spring.BibliotecaDelDesierto.repository;

import com.cmcorp.spring.BibliotecaDelDesierto.model.LibroCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioLibroCompra extends JpaRepository<LibroCompra, Integer> {
}
