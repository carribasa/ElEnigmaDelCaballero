package com.example.aplicacionproyectofinal.modelo;

import android.widget.ImageView;

import java.io.Serializable;

public class Monstruo extends Personaje implements Serializable {

    int imagen;
    public Monstruo(String nombre, int vida, int ataque, int defensa, int imagen) {
        super(nombre, vida, ataque, defensa);
        this.imagen = imagen;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
