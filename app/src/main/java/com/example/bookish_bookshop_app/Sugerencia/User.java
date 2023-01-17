package com.example.bookish_bookshop_app.Sugerencia;

import java.io.Serializable;

public class User implements Serializable {

    public String nombre;
    public String contrasenia;

    public User(String n, String c){
        this.nombre = n;
        this.contrasenia = c;
    }

    public void setNombre(String n){
        this.nombre = n;
    }

    public void setContrasenia(String c){
        this.contrasenia = c;
    }

    public String getNombre(){
        return nombre;
    }

    public String getContrasenia(){
        return contrasenia;
    }

}
