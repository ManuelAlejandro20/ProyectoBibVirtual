package com.cmcorp.spring.BibliotecaDelDesierto.repository;

import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Interface of the User for encapsulating storage
 */
public interface RepositorioUser extends JpaRepository<User, Integer> {

    /**
     * Returns an user by his email
     * @param email
     * @return User
     */
    User findUserByEmail(@Param("email") String email);

    /**
     * Returns an user by his username
     * @param username
     * @return User
     */
    User findUserByUsername(@Param("username") String username);    
    
    /**
     * Returns a boolean value if the username exists or not
     * @param username
     * @return boolean
     */
    boolean existsByUsername(@Param("username") String username);

    /**
     * Returns a boolean value if the email exists or not
     * @param email
     * @return boolean
     */
    boolean existsByEmail(@Param("email") String email);
}
