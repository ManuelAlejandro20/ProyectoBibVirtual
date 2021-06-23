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

import com.cmcorp.spring.BibliotecaDelDesierto.model.Rol;
import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import com.cmcorp.spring.BibliotecaDelDesierto.model.dto.UserDTO;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service of the User
 */
@Service
@Transactional
public class ServicioUser implements UserService{
    @Autowired
    private RepositorioUser repositorioUser;
    
    /**
     * GET all the users
     *
     * @return List<User>
     */
    public List<User> listaUsuarios() {
        return repositorioUser.findAll();
    }

    /**
     * INSERT an user
     *
     * @param usuario
     */
    public void saveUser(User usuario) {
        repositorioUser.save(usuario);
    }

    /**
     * Check if exists an email
     *
     * @param email
     * @return response
     */
    public boolean emailUsed(String email) {
        return repositorioUser.existsByEmail(email);
    }

    /**
     * Check if exists a username
     *
     * @param username
     * @return response
     */
    public boolean usernameUsed(String username) {
        return repositorioUser.existsByUsername(username);
    }

    /**
     * GET user by his id
     *
     * @param id
     * @return User
     */
    public User getUserXId(Integer id) {
        return repositorioUser.findById(id).get();
    }

    /**
     * GET user by his email
     *
     * @param email
     * @return User
     */
    public User getUserXEmail(String email) {
        return repositorioUser.findUserByEmail(email);
    }
    
    /**
     * GET user by his username
     *
     * @param username
     * @return User
     */
    public User getUserXUsername(String username) {
        return repositorioUser.findUserByUsername(username);
    }    

    /**
     * DELETE user by his id
     * @param id
     */
    public void deleteUser(Integer id){
        repositorioUser.deleteById(id);
    }

    
    /**
     * Get the authenticated user by his username and load the components 
     * @param username
     * @return UserDetails components
     */    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

		User user = repositorioUser.findUserByUsername(username);
		
		if(user == null) {
		
			throw new UsernameNotFoundException("Invalid username or password");
			
		}
	
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));	
	}
	
	/**
	 * Use the UserDTO object and create a user object
	 * @param UserDTO object 
	 * @return new User created
	 * 
	 */
	@Override
	public User save(UserDTO registrationDto) {
		User user = new User();
		user.setId(registrationDto.getId());
		user.setEmail(registrationDto.getEmail());
		user.setUsername(registrationDto.getUsername());
		user.setPassword(new BCryptPasswordEncoder().encode(registrationDto.getPassword()));
		user.setNombre(registrationDto.getNombre());
		user.setPaterno(registrationDto.getPaterno());
		user.setMaterno(registrationDto.getMaterno());
		user.setDireccion(registrationDto.getDireccion());
		user.setTelefono(registrationDto.getTelefono());
		user.setRol(registrationDto.getRol());
		user.setRoles(Arrays.asList(new Rol("ROLE_USER")));
		return repositorioUser.save(user);
	}
	
	/**
	 * 
	 * Create a collection of granted authorities
	 * 
	 * @param roles
	 * @return Collection of authorities
	 */
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Rol> roles){
		return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList());
	}	
	
	
}
