package com.cmcorp.spring.BibliotecaDelDesierto.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmcorp.spring.BibliotecaDelDesierto.model.CartItem;
import com.cmcorp.spring.BibliotecaDelDesierto.model.User;

@Repository
public interface RepositorioCartItem extends JpaRepository<CartItem, Integer>{

	List<CartItem> findByUsuario(User usuario);

	@Modifying
	@Transactional
	@Query("update CartItem c set c.cantidad = ?1 where c.id = ?2")
	void setCantidadInfoById(int cantidad, Integer id);
		
}
