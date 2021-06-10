package com.cmcorp.spring.BibliotecaDelDesierto.service;

import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioUser {
    @Autowired
    private RepositorioUser repositorioUser;

    public List<User> listaUsuarios(){
        return repositorioUser.findAll();
    }

    public void saveUser(User usuario){
        repositorioUser.save(usuario);
    }

    public boolean emailUsed(String email){
        return repositorioUser.existsByEmail(email);
    }

    public boolean nicknameUsed(String nickname){
        return repositorioUser.existsByNickname(nickname);
    }

    public User getUserXId(Integer id){
        return repositorioUser.findById(id).get();
    }

    public User getUserXEmail(String email){
        return repositorioUser.findUserByEmail(email);
    }

    public void deleteUser(Integer id){
        repositorioUser.deleteById(id);
    }
}
