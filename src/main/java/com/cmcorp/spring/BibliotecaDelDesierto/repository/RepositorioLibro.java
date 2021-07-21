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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

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
    @Query(value = "SELECT l.* FROM libro AS l WHERE l.autor LIKE CONCAT('%', :autor, '%')", nativeQuery = true)
    List<Libro> findLibrosByAutor(@Param("autor") String autor);

    /**
     * Returns all the books by a category list id given a listaIdCategorias and the size of the category list
     * @param listaIdCategorias
     * @param cantCategorias
     * @return List<Libro>
     */
    @Query(value = "SELECT l.* FROM libro AS l JOIN libro_categoria AS lc ON l.id = lc.libro_id " +
            "WHERE lc.categoria_id IN :lista_id_categorias " +
            "GROUP BY lc.libro_id " +
            "HAVING COUNT(lc.libro_id) >= :cant_categorias", nativeQuery = true)
    List<Libro> findLibrosByCategoriasId(@Param("lista_id_categorias") List<Integer> listaIdCategorias, @Param("cant_categorias") Integer cantCategorias);

    /**
     * Returns a list of books by a title or autor
     * @param texto
     * @return List<Libro>
     */
    @Query(value = "SELECT l.* FROM libro AS l WHERE l.nombre LIKE CONCAT('%',:texto,'%') OR l.autor LIKE CONCAT('%',:texto,'%')", nativeQuery = true)
    Page<Libro> findAllByTituloOrAutor(@Param("texto") String texto, Pageable pageable);

    @Query(value = "SELECT l.* FROM libro AS l WHERE l.idioma_id = :idioma AND ( l.nombre LIKE CONCAT('%',:texto,'%') OR l.autor LIKE CONCAT('%',:texto,'%'))", nativeQuery = true)
    Page<Libro> findAllByTituloOrAutorAndIdioma(@Param("texto") String texto, @Param("idioma") Integer idioma, Pageable pageable);

    @Query(value = "SELECT l.* FROM libro AS l JOIN libro_categoria AS lc ON l.id = lc.libro_id WHERE lc.categoria_id = :categoria AND l.idioma_id = :idioma", nativeQuery = true)
    Page<Libro> findAllByIdiomaAndCategoria(@Param("idioma") Integer idioma, @Param("categoria") Integer categoria, Pageable pageable);

    @Query(value = "SELECT l.* FROM libro AS l JOIN libro_categoria AS lc ON l.id = lc.libro_id WHERE lc.categoria_id = :categoria AND l.idioma_id = :idioma AND ( l.nombre LIKE CONCAT('%',:texto,'%') OR l.autor LIKE CONCAT('%',:texto,'%'))", nativeQuery = true)
    Page<Libro> findAllByTituloOrAutorAndIdiomaAndCategoria(@Param("texto") String texto, @Param("idioma") Integer idioma, @Param("categoria") Integer categoria, Pageable pageable);

    Page<Libro> findAllByIdiomaId(@Param("idioma") Integer idioma, Pageable pageable);

    /**
     * Returns true if the ISBN is asociated to another book
     * @param isbn
     * @return
     */
    boolean existsByIsbn(@Param("isbn") String isbn);

    /**
     * Returns true if the SKU is asociated to another book
     * @param sku
     * @return
     */
    boolean existsBySku(@Param("sku") String sku);

    /**
     * Returns true if the Title(nombre) is asociated to another book
     * @param nombre
     * @return
     */
    boolean existsByNombre(@Param("nombre") String nombre);

    /**
     * Return true if the file is asociated to another book
     * @param nombreImagen
     * @param nombreArchivo
     * @return
     */
    boolean existsByNombreImagenOrNombreArchivo(@Param("nombreImagen") String nombreImagen, @Param("nombreArchivo") String nombreArchivo);

	@Modifying
	@Transactional
	@Query("update Libro l set l.stock = ?1 where l.id = ?2")
	void setStockInfoById(int stock, Integer id);
}
