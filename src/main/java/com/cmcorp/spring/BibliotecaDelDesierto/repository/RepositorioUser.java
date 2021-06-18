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
     * Returns an user by his nickname
     * @param nickanme
     * @return User
     */
    User findUserByNickname(@Param("nickname") String nickname);    
    
    /**
     * Returns a boolean value if the nickname exists or not
     * @param nickname
     * @return boolean
     */
    boolean existsByNickname(@Param("nickname") String nickname);

    /**
     * Returns a boolean value if the email exists or not
     * @param email
     * @return boolean
     */
    boolean existsByEmail(@Param("email") String email);
}
