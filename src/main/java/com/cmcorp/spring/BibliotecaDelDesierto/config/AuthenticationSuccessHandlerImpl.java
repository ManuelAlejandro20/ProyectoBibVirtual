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

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{

	@Autowired
	private ServicioUser servicioUser;
	
	@Autowired
	private ServicioCartItem servicioCarrito;
	
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
 
    public int calcularTotalCarro(List<CartItem> carrito) {
    	int total = 0;
    	for(CartItem c: carrito) {
    		total += c.getCantidad() * c.getLibro().getPrecio();
    	}
    	return total;
    }    
    
    
}