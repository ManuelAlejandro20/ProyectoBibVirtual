package com.cmcorp.spring.BibliotecaDelDesierto.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import com.cmcorp.spring.BibliotecaDelDesierto.model.dto.UserDTO;

public interface UserService extends UserDetailsService{
	User save(UserDTO registrationDto);
}
