package com.api.cabina_giratoria.model.repositorios;

import com.api.cabina_giratoria.model.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUser extends JpaRepository<User, String> {
    User findByUserNameAndPassword(String userName, String password);
}