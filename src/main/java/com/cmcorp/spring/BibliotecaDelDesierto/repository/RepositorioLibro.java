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

    @Query(value = "SELECT l.* FROM Libro AS l WHERE l.autor LIKE CONCAT('%', :autor, '%')", nativeQuery = true)
    List<Libro> findLibrosByAutor(@Param("autor") String autor);

    @Query(value = "SELECT l.* FROM Libro AS l JOIN Libro_Categoria AS lc ON l.id = lc.libro_id " +
            "WHERE lc.categoria_id IN :lista_id_categorias " +
            "GROUP BY lc.libro_id " +
            "HAVING COUNT(lc.libro_id) >= :cant_categorias", nativeQuery = true)
    List<Libro> findLibrosByCategoriasId(@Param("lista_id_categorias") List<Integer> listaIdCategorias, @Param("cant_categorias") Integer cantCategorias);

    @Query(value = "SELECT l.* FROM Libro AS l WHERE l.nombre LIKE CONCAT('%',:titulo,'%')", nativeQuery = true)
    List<Libro> findLibrosByTitulo(@Param("titulo") String titulo);
}
