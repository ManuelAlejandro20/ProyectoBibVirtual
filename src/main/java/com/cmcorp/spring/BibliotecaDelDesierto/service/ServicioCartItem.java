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

package com.cmcorp.spring.BibliotecaDelDesierto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmcorp.spring.BibliotecaDelDesierto.model.CartItem;
import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioCartItem;

/**
 * Service of the CartItem
 * 
 *
 */
@Service
public class ServicioCartItem {

	@Autowired
	private RepositorioCartItem cartRepo;
	
	/**
	 * Return a list of CartItems given an user
	 * @param user
	 * @return List<CartItem>
	 */
	public List<CartItem> listCartItems(User user){
		return cartRepo.findByUsuario(user);
	}
	
	/**
	 * Save the CartItem object in the database
	 * @param cartitem
	 */
	public void save(CartItem cartitem) {
		cartRepo.save(cartitem);
	}
	
	/**
	 * Update the CartItem quantity given an id
	 * @param cantidad
	 * @param id, CartItem id
	 */
	public void updateCartItem(int cantidad, Integer id) {
		cartRepo.setCantidadInfoById(cantidad, id);
	}
	
	/**
	 * Deletes a Cartitem
	 * @param cartItem
	 */
	public void deleteCartItem(CartItem cartItem) {
		cartRepo.delete(cartItem);
	}

}
