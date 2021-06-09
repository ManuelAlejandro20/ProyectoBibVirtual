package com.cmcorp.spring.BibliotecaDelDesierto.repository;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioLibro extends JpaRepository<Libro, Integer> {
    Libro findBySku(@Param("sku") String sku);
    Libro findByIsbn(@Param("isbn") String isbn);

    List<Libro> findByIdiomaId(Integer id);

    List<Libro> findLByAutorId(Integer id);

    @Query(value = "SELECT l FROM Libro l JOIN l.categorias c WHERE c.id IN :lista_id_categorias")
    List<Libro> findLibrosByCategoriasId(@Param("lista_id_categorias") List<Integer> lista_id_categorias);
}
