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

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cmcorp.spring.BibliotecaDelDesierto.model.dto.UserDTO;

/**
 * Main controller
 */
@Controller
public class ControladorPantallaPrincipal {
    
	/**
	 * Get the index path and return the user account or the main index view depending if the user is authenticated
	 * @return index view or myaccount view
	 */
    @GetMapping("/")
    public String index() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if(auth == null || auth instanceof AnonymousAuthenticationToken) {
    		return "index";
    	}        	
        return "redirect:/myaccount";
    }    
     
    /**
     * Get the contact path and return the contact view
     * @param model
     * @return contact view
     */
    @GetMapping("/contact")
    public String contacto(Model model) {	
        return "contact";
    }  
    
    /**
     * Get the login path and return the same view, but if the user is already authenticated in the system 
     * return myaccount view
     * @param model
     * @param request
     * @return login view or myaccount view
     */
    @GetMapping("/login")
    public String signin(Model model, HttpServletRequest request) {    	
    	
    	request.getSession();    	
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if(auth == null || auth instanceof AnonymousAuthenticationToken) {
    		
        	//If you want to register a user
        	model.addAttribute("user", new UserDTO());
            model.addAttribute("password2", new String());    		
    		
    		return "login";
    	}          	
    	        
        return "redirect:/myaccount";
    }          
}
