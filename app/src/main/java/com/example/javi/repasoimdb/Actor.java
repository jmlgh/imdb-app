package com.example.javi.repasoimdb;

import java.util.ArrayList;

public class Actor {

    private String nombre;
    private String bio;
   ArrayList<Pelicula> peliculas;

    public Actor(){}
    public Actor(String nombre, String bio) {
        this.nombre = nombre;
        this.bio = bio;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(ArrayList<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
