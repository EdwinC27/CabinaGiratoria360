package com.api.cabina_giratoria.controladores;

import com.api.cabina_giratoria.model.constants.EndPoints;
import com.api.cabina_giratoria.model.entidades.User;
import com.api.cabina_giratoria.model.repositorios.RepositorioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndPoints.BASE)
public class UserControler {

    @Autowired
    private RepositorioUser usuarioRepository;

    @GetMapping(EndPoints.USERS)
    public boolean verificarCredenciales(@RequestParam(value = "user") String userName, @RequestParam(value = "password") String password) {
        User usuario = usuarioRepository.findByUserNameAndPassword(userName, password);
        return usuario != null;
    }
}
