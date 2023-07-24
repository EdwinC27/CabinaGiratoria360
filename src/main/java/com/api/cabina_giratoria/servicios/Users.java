package com.api.cabina_giratoria.servicios;

import org.springframework.stereotype.Service;

@Service
public class Users {
    public boolean isUserExist(String user, String password) {
        boolean userExist = false;

        if (user.equals("Edwin") && password.equals("123456")) {
            userExist =  true;
        }

        return userExist;
    }
}
