package com.cmcorp.spring.BibliotecaDelDesierto.repository;

import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RepositorioUser extends JpaRepository<User, Integer> {
    User findByEmail(@Param("email") String email);
}
