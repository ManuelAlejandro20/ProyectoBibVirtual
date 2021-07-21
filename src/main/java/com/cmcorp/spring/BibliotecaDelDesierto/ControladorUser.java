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

package com.cmcorp.spring.BibliotecaDelDesierto;

import com.cmcorp.spring.BibliotecaDelDesierto.model.CartItem;
import com.cmcorp.spring.BibliotecaDelDesierto.model.Categoria;
import com.cmcorp.spring.BibliotecaDelDesierto.model.Compra;
import com.cmcorp.spring.BibliotecaDelDesierto.model.Libro;
import com.cmcorp.spring.BibliotecaDelDesierto.model.LibroCompra;
import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import com.cmcorp.spring.BibliotecaDelDesierto.model.dto.UserDTO;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioCartItem;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioCompra;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioLibro;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioLibroCompra;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Controller ControladorUser
 */
@Controller
public class ControladorUser {

    private final ServicioUser servicioUser;
    private final ServicioCartItem servicioCarrito;
    private final ServicioLibro servicioLibro;
    private final ServicioCompra servicioCompra;
    private final ServicioLibroCompra servicioLibroCompra;
    
   
    
    @Autowired
    public ControladorUser(ServicioUser servicioUser, ServicioCartItem servicioCarrito, 
    		ServicioLibro servicioLibro, ServicioCompra servicioCompra,
    		ServicioLibroCompra servicioLibroCompra){
        this.servicioUser = servicioUser;
        this.servicioCarrito = servicioCarrito;
        this.servicioLibro = servicioLibro;
        this.servicioCompra = servicioCompra;
        this.servicioLibroCompra = servicioLibroCompra;
    }

	/**
	 * Method to login from api
	 * @param username
	 * @param pwd
	 * @return
	 */
	@RequestMapping(value =   "/app/login", method = RequestMethod.POST)
	public ResponseEntity<User> login(@RequestParam("username") String username, @RequestParam("password") String pwd) {

		try {
			UserDetails details = servicioUser.loadUserByUsername(username);
			String hashedPassword = details.getPassword();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			if (passwordEncoder.matches(pwd,hashedPassword)) {
				return new ResponseEntity<User>(servicioUser.getUserXUsername(username), HttpStatus.OK);
			} else {
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
		}
		catch (NoSuchElementException e){
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

	}


    /**
     * Method that returns all users
     * @return List<User>
     */
    @GetMapping("/users")
    public String lista(Model model){
    	List<User> users = servicioUser.listaUsuarios();
        model.addAttribute("users", users);
    	return "allusers";
    }

    /**
     * Method that returns an user by user id
     * @param id
     * @return User
     */
    @GetMapping("user/byid/{id}")
    public ResponseEntity<User> getXId(@PathVariable Integer id){
        try {
            User usuario = servicioUser.getUserXId(id);
            return new ResponseEntity<User>(usuario, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method that returns an user by email
     * @param email
     * @return User
     */
    @GetMapping("user/byemail/{email}")
    public ResponseEntity<User> getXEmail(@PathVariable String email){
        try {
            User usuario = servicioUser.getUserXEmail(email);
            return new ResponseEntity<User>(usuario, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method that adds an user
     * @param user, password, redirAttrs
     * @return signin view path
     */
    @PostMapping("user/add")
    public String addUser(UserDTO user,String password2, RedirectAttributes redirAttrs){    	
    	
    	if(!user.getPassword().equals(password2)) {
    		redirAttrs.addFlashAttribute("error", "Las contraseñas no coinciden, intenta nuevamente");
    		return "redirect:/login";        		
    	}    	    	
    	if (servicioUser.usernameUsed(user.getUsername())){
     		redirAttrs.addFlashAttribute("error", "Ese nombre de usuario ya está en uso, intenta nuevamente");          	        	
        }    	
        
    	else if(servicioUser.emailUsed(user.getEmail())){
    		redirAttrs.addFlashAttribute("error", "Ese correo ya está en uso, intenta nuevamente");     		              
        }else {
        	        	
            servicioUser.save(user);
            
            redirAttrs.addFlashAttribute("success", user.getUsername() + " se ha registrado correctamente");        	        	        
        }   
  
        return "redirect:/login";    	
    	
    }

    /**
     * Method that adds an user
     * @param user, password, redirAttrs
     * @return signin view path
     */
    @PostMapping("newbook/add")
    public String addBook(){    	  
        return "redirect:/bookgrid";    		
    }    
    
    /**
     * Method that redirects if occurs an login error
     * @param redirAttrs
     * @return
     */
    @PostMapping("/login_error")
	public String loginErrorHandler(RedirectAttributes redirAttrs) {
    	redirAttrs.addFlashAttribute("errorLogin", "Error en el inicio de sesión, intenta nuevamente");
    	return "redirect:/login?error";
	}

    /**
     * Method that redirects if occurs an access denied error
     * @param redirAttrs
     * @return
     */
    @GetMapping("/access_denied")
	public String accessDeniedHandler(Model model) {      	
    	return "accessdenied";
	}
    
    
    /**
     * Method that returns the page after a user logs into 
     * @param model
     * @return
     */
    @GetMapping("/myaccount")
	public String successUserLogin(Model model, HttpSession session) {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	String username = auth.getName();
    	User user = servicioUser.getUserXUsername(username);
    	String email = user.getEmail();
    	String telefono = user.getTelefono();
    	String nombre = user.getNombre();
    	String paterno = user.getPaterno();
    	String materno = user.getMaterno();
    	    	
    	model.addAttribute("username", username);
    	model.addAttribute("email", email);
    	model.addAttribute("phone", telefono);
    	model.addAttribute("nombre", nombre);
    	model.addAttribute("paterno", paterno);
    	model.addAttribute("materno", materno);
    	 	
    	return "/myaccount";
	}    
        
    @GetMapping("/checkout")
    public String checkout(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User usuario = servicioUser.getUserXUsername(auth.getName());
    	List<CartItem> itemsCarrito = servicioCarrito.listCartItems(usuario);
    	model.addAttribute("carrito", itemsCarrito);    	
    	return("/checkout");
    }
    
    @GetMapping("/cart")
    public String cart(Model model) {   	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User usuario = servicioUser.getUserXUsername(auth.getName());
    	List<CartItem> itemsCarrito = servicioCarrito.listCartItems(usuario);
    	model.addAttribute("carrito", itemsCarrito);
    	return("/cart");
    }    
          
    /**
     * Method that update an user by id an the new User
     * @param usuario
     * @param id
     * @return
     */
    @PutMapping("user/update/{id}")
    public ResponseEntity<?> update(@RequestBody User usuario, @PathVariable Integer id){
        try {
            usuario.setId(id);
            servicioUser.saveUser(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method that delete an user by id
     * @param id
     */
    @DeleteMapping("user/delete/{id}")
    public void delete(@PathVariable Integer id){
        servicioUser.deleteUser(id);
    }
        
    @GetMapping("/addtocart/{id}")
    public String agregarAlCarrito(@PathVariable Integer id,@RequestParam("view") String view, RedirectAttributes redirAttrs, HttpSession session) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User usuario = servicioUser.getUserXUsername(auth.getName());
    	
    	Libro libro = servicioLibro.getLibroXId(id);
    	CartItem itemCarrito = new CartItem();
    	
    	itemCarrito.setLibro(libro);
    	itemCarrito.setUsuario(usuario);    	
    	
    	List<CartItem> carrito = servicioCarrito.listCartItems(usuario);
    	    	
    	boolean existeLibro = false;
    	
    	for(CartItem c : carrito) {
    		    		
    		Libro libroCarrito = c.getLibro();
    		if (libroCarrito.getId() == id) {
    			
    			existeLibro = true;
    			
        		if(c.getCantidad()+1 > libroCarrito.getStock()) {
        			redirAttrs.addFlashAttribute("error", "Ya no hay más stock de este libro");
        			break;
        		}
    			
    			
    			Integer idCarrito = c.getId();
    			servicioCarrito.updateCartItem(c.getCantidad()+1, idCarrito);
    			
    			c.setCantidad(c.getCantidad()+1);
    			
    			int total = calcularTotalCarro(carrito);
    			
    			session.setAttribute("Carrito", carrito);
    			session.setAttribute("Total", total);
    			
    			redirAttrs.addFlashAttribute("success", "Añadido al carrito");
    			
    			break;
    			
    		}
    		
    		
    	}
    	
    	if(carrito.size() == 0 || !existeLibro) {
    		itemCarrito.setCantidad(1);
    		servicioCarrito.save(itemCarrito);    		
    		
    		int total = calcularTotalCarro(servicioCarrito.listCartItems(usuario));
    		
    		session.setAttribute("Carrito", servicioCarrito.listCartItems(usuario));
    		session.setAttribute("Total", total);
    	
    		redirAttrs.addFlashAttribute("success", "Añadido al carrito");
    	}
    	
    	
    	    	    	
    	if(view.equals("/bookgrid")) {
    		return "redirect:/bookgrid";
    	}
    	
    	return "redirect:/book/"+ id +"/summary";    	
    	
    }
    
    @GetMapping("/removefromcart/{id}")
    public String removerDelCarrito(@PathVariable Integer id, @RequestParam("url") String url, 
    							HttpServletRequest request, HttpSession session) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User usuario = servicioUser.getUserXUsername(auth.getName());
    	
    	List<CartItem> carrito = servicioCarrito.listCartItems(usuario);    	
    	
    	for(CartItem c : carrito) {
    		Libro libroCarrito = c.getLibro();
    		if (libroCarrito.getId() == id) {
    			
    			int total = 0;
    			
    			if(c.getCantidad()-1 == 0) {
    				servicioCarrito.deleteCartItem(c);    				
    				total = calcularTotalCarro(servicioCarrito.listCartItems(usuario));
    				session.setAttribute("Carrito", servicioCarrito.listCartItems(usuario));
    				session.setAttribute("Total", total);
    			}else {
    			
    				Integer idCarrito = c.getId();
	    			servicioCarrito.updateCartItem(c.getCantidad()-1, idCarrito);
	    			
	    			c.setCantidad(c.getCantidad()-1);
	    				    			
	    			total = calcularTotalCarro(carrito);
	    			session.setAttribute("Carrito", carrito);
	    			session.setAttribute("Total", total);
	    			
    			}

    			break;
    			
    		}
    		
    		
    	} 
    	return "redirect:" + url; 
    	
    }
    
    @PostMapping("/payment")
    public String pago(HttpSession session, RedirectAttributes redirAttrs) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User usuario = servicioUser.getUserXUsername(auth.getName());
    	List<CartItem> carrito = servicioCarrito.listCartItems(usuario);
    	int total = calcularTotalCarro(carrito);
    	
    	Compra compra = new Compra();
    	Set<LibroCompra> librosCompras = new HashSet<>();
    	    	
    	compra.setUser(usuario);
    	compra.setFecha();
    	compra.setMontoTotal(total);
    	compra.setDescuento(0);
    	
    	servicioCompra.saveCompra(compra);
    	
    	for(CartItem c : carrito) {
    		LibroCompra lc= new LibroCompra();
    		lc.setCompra(compra);
    		lc.setLibro(c.getLibro());
    		lc.setUnidades(c.getCantidad());
    		
    		librosCompras.add(lc);
    		
    		servicioLibroCompra.addLibroCompra(lc);
    		
    		servicioLibro.setStockLibro(c.getLibro().getStock()-c.getCantidad(), c.getLibro().getId());
    	
    		servicioCarrito.deleteCartItem(c);
    		
    	}
   
		session.setAttribute("Carrito", servicioCarrito.listCartItems(usuario));
		session.setAttribute("Total", 0);
    	
		redirAttrs.addFlashAttribute("success", "¡Pago realizado con exito!");
		
    	return "redirect:/checkout";
    }
    
    @GetMapping("/library")
    public String biblioteca(Model model) {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User usuario = servicioUser.getUserXUsername(auth.getName());
    	
    	List<Compra> compras = servicioCompra.listaCompras(usuario.getId());
    	List<Libro> libros = new ArrayList<Libro>();
    	List<Integer> cantidades = new ArrayList<Integer>();
    	
    	for(Compra c: compras) {
    		
    		List<LibroCompra> librosCompra = servicioLibroCompra.listaLibros(c);
    		
    		for(LibroCompra lc : librosCompra) {
    			libros.add(lc.getLibro());
    			cantidades.add(lc.getUnidades());
    		}
    		
    		
    	}
    	
    	model.addAttribute("libros", libros);
    	model.addAttribute("cantidades", cantidades);
    	
    	return "library";
    }
    
    @GetMapping("/library/{id}")
    public String resumenLibroComprado(@PathVariable int id, @RequestParam("cantidad") int cantidad, Model model) {
    	
    	Libro libro = servicioLibro.getLibroXId(id);

    	model.addAttribute("libro" ,libro);
    	model.addAttribute("cantidad", cantidad);
    	
		String categorias = libro.getCategorias().stream().map(Categoria::getNombre).collect(Collectors.joining(", "));

		model.addAttribute("categorias", categorias);
    	
    	return "summaryuser";
    }   
    
    public int calcularTotalCarro(List<CartItem> carrito) {
    	int total = 0;
    	for(CartItem c: carrito) {
    		total += c.getCantidad() * c.getLibro().getPrecio();
    	}
    	return total;
    }
}
