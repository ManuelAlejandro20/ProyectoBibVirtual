package com.cmcorp.spring.BibliotecaDelDesierto.repository;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioCompra extends JpaRepository<Compra, Integer> {
    List<Compra> findByUserId(Integer userId);
}
