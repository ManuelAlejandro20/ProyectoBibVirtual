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

package com.cmcorp.spring.BibliotecaDelDesierto.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cmcorp.spring.BibliotecaDelDesierto.model.CartItem;
import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioCartItem;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioUser;

/*
 * Custom authentication handler component
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{

	@Autowired
	private ServicioUser servicioUser;
	
	@Autowired
	private ServicioCartItem servicioCarrito;
	
	/**
	 * Redirects to the myaccount view after a successful login, also add the shopping cart for the user using the 
	 * session
	 */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
    	
    	HttpSession session = request.getSession();
    	
    	String username = authentication.getName();
    	User user = servicioUser.getUserXUsername(username);

		int total = calcularTotalCarro(servicioCarrito.listCartItems(user));
		
		session.setAttribute("Total", total);    	
    	
    	session.setAttribute("Carrito", servicioCarrito.listCartItems(user));    
    	
    	response.sendRedirect("/myaccount");
    }    
 
    /**
     * Calculate the total price of the shopping cart
     * @param carrito
     * @return total, total price of the shopping cart
     */
    public int calcularTotalCarro(List<CartItem> carrito) {
    	int total = 0;
    	for(CartItem c: carrito) {
    		total += c.getCantidad() * c.getLibro().getPrecio();
    	}
    	return total;
    }    
    
    
}