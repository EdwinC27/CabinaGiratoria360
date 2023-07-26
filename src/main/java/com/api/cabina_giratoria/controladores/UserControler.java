package com.api.cabina_giratoria.controladores;

import com.api.cabina_giratoria.model.constants.EndPoints;
import com.api.cabina_giratoria.servicios.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndPoints.BASE)
public class UserControler {

    @Autowired
    private Users users;

    @GetMapping(EndPoints.USERS)
    public boolean verificarCredenciales(@RequestParam(value = "user") String userName, @RequestParam(value = "password") String password) {
        return users.isUserExist(userName, password);
    }
}
