package com.cmcorp.spring.BibliotecaDelDesierto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmcorp.spring.BibliotecaDelDesierto.model.CartItem;
import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioCartItem;

@Service
public class ServicioCartItem {

	@Autowired
	private RepositorioCartItem cartRepo;
	
	public List<CartItem> listCartItems(User user){
		return cartRepo.findByUsuario(user);
	}
	
	public void save(CartItem cartitem) {
		cartRepo.save(cartitem);
	}
	
	public void updateCartItem(int cantidad, Integer id) {
		cartRepo.setCantidadInfoById(cantidad, id);
	}
	
	public void deleteCartItem(CartItem cartItem) {
		cartRepo.delete(cartItem);
	}

}
