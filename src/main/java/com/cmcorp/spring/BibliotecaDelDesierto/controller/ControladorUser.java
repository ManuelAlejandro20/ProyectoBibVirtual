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

package com.cmcorp.spring.BibliotecaDelDesierto.controller;

import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import com.cmcorp.spring.BibliotecaDelDesierto.model.dto.UserDTO;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controller ControladorUser
 */
@Controller
public class ControladorUser {

    private final ServicioUser servicioUser;
   
    @Autowired
    public ControladorUser(ServicioUser servicioUser){
        this.servicioUser = servicioUser;
    }

    /**
     * Method that returns all users
     * @return List<User>
     */
    @GetMapping("/users")
    public List<User> lista(){
        return servicioUser.listaUsuarios();
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
        	        	
            user.setRol("Usuario");
            
            servicioUser.save(user);
            
            redirAttrs.addFlashAttribute("success", user.getUsername() + " se ha registrado correctamente");        	        	        
        }   
  
        return "redirect:/login";    	
    	
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
     * Method that returns the page after a user logs into 
     * @param model
     * @return
     */
    @GetMapping("/myaccount")
	public String successUserLogin(Model model) {
    	
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
}
