package com.api.cabina_giratoria.model.entidades;

import javax.persistence.*;

@Entity
@Table(name = "credenciales")
public class User {
    @Id
    @Column(name = "usuario")
    @Basic
    String userName;

    @Column(name = "contraseña")
    @Basic
    String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}