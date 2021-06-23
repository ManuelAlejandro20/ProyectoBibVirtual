/*
 * Copyright (c) 2021 CMCORP
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * An intermediate form of license used by the X Consortium for X11 used the following wording:[16]
 *
 */

package com.cmcorp.spring.BibliotecaDelDesierto.repository;

import com.cmcorp.spring.BibliotecaDelDesierto.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface of the Libro for encapsulating storage
 */
@Repository
public interface RepositorioLibro extends JpaRepository<Libro, Integer> {

    /**
     * Returns a book by his sku id
     *
     * @param sku
     * @return Libro
     */
    Libro findBySku(@Param("sku") String sku);

    /**
     * Returns a book by his isbn id
     * @param isbn
     * @return Libro
     */
    Libro findByIsbn(@Param("isbn") String isbn);

    /**
     * Returns a list of books by a language id
     * @param id Idioma
     * @return List<Libro>
     */
    List<Libro> findByIdiomaId(Integer id);

    /**
     * Returns a list of books by an autor (it could be a similar autor)
     * @param autor
     * @return List<Libro>
     */
    @Query(value = "SELECT l.* FROM Libro AS l WHERE l.autor LIKE CONCAT('%', :autor, '%')", nativeQuery = true)
    List<Libro> findLibrosByAutor(@Param("autor") String autor);

    /**
     * Returns all the books by a category list id given a listaIdCategorias and the size of the category list
     * @param listaIdCategorias
     * @param cantCategorias
     * @return List<Libro>
     */
    @Query(value = "SELECT l.* FROM Libro AS l JOIN Libro_Categoria AS lc ON l.id = lc.libro_id " +
            "WHERE lc.categoria_id IN :lista_id_categorias " +
            "GROUP BY lc.libro_id " +
            "HAVING COUNT(lc.libro_id) >= :cant_categorias", nativeQuery = true)
    List<Libro> findLibrosByCategoriasId(@Param("lista_id_categorias") List<Integer> listaIdCategorias, @Param("cant_categorias") Integer cantCategorias);

    /**
     * Returns a list of books by a title
     * @param titulo
     * @return List<Libro>
     */
    @Query(value = "SELECT l.* FROM Libro AS l WHERE l.nombre LIKE CONCAT('%',:titulo,'%')", nativeQuery = true)
    List<Libro> findLibrosByTitulo(@Param("titulo") String titulo);
}
