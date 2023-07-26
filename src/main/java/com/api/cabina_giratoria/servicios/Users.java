package com.api.cabina_giratoria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Users {

    @Autowired
    private Archivo archivo;

    public boolean isUserExist(String user, String password) {
        if (user == null || password == null) {
            return false;
        }

        boolean userExist = archivo.LeerArchivoLineaPorLinea(user, password);;

        return userExist;
    }
}
