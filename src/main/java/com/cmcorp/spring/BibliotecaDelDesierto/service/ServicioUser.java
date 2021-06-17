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

package com.cmcorp.spring.BibliotecaDelDesierto.service;

import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service of the User
 */
@Service
@Transactional
public class ServicioUser {
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
     * Check if exists a nickname
     *
     * @param nickname
     * @return response
     */
    public boolean nicknameUsed(String nickname) {
        return repositorioUser.existsByNickname(nickname);
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
     * DELETE user by his id
     * @param id
     */
    public void deleteUser(Integer id){
        repositorioUser.deleteById(id);
    }
}
