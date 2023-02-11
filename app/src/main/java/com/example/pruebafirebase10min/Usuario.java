package com.example.pruebafirebase10min;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Usuario {
    String usu, email, password;

    public Usuario(String usu, String email, String password) {
        this.usu = usu;
        this.email = email;
        this.password = password;
    }

    public Usuario() {}

    public String getUsu() {
        return usu;
    }

    public void setUsu(String usu) {
        this.usu = usu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
