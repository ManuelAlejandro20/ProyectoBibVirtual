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
 * Copyright (C) 2021 X Consortium
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE X CONSORTIUM BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Except as contained in this notice, the name of the X Consortium shall not be used in advertising or otherwise to promote the sale, use or other dealings in this Software without prior written authorization from the X Consortium.
 *
 * X Window System is a trademark of X Consortium, Inc.
 */

package com.cmcorp.spring.BibliotecaDelDesierto.controller;


import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controller ControladorUser
 */
@Controller
//@RequestMapping("/users")
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
     * Method that sign in the user
     * @param nickname
     * @return registered user view path
     */
    
    //TODO: User authentication
    
    @PostMapping("user/{nickname}")
    public String login(String nickname, String password){
    	System.out.println("Nickname: " + nickname);
    	System.out.println("Password: " + password);
    	return "redirect:/signin"; 
    }    

    /**
     * Method that adds an user
     * @param usuario
     * @return signin view path
     */
    @PostMapping("user/add")
    public String addUser(User user,String password2, RedirectAttributes redirAttrs){    	
        
    	
    	if(!user.getPassword().equals(password2)) {
    		redirAttrs.addFlashAttribute("error", "Las contraseñas no coinciden, intenta nuevamente");
    		return "redirect:/signin";        		
    	}    	    	
    	if (servicioUser.nicknameUsed(user.getNickname())){
     		redirAttrs.addFlashAttribute("error", "Ese nombre de usuario ya está en uso, intenta nuevamente");          	        	
        }    	
        
    	else if(servicioUser.emailUsed(user.getEmail())){
    		redirAttrs.addFlashAttribute("error", "Ese correo ya está en uso, intenta nuevamente");     		              
        }else {
        	        	
            user.setRol("Usuario");
            user.setPassword(new BCryptPasswordEncoder().encode(password2));
            
            servicioUser.saveUser(user);
            
            redirAttrs.addFlashAttribute("success", user.getNickname() + " se ha registrado correctamente");        	        	        
        }   
  
        return "redirect:/signin";    	
    	
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
