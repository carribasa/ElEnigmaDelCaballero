package com.example.aplicacionproyectofinal.modelo;

import java.io.Serializable;

public class Heroe extends Personaje implements Serializable {

    Boolean pocion;

    public Heroe(String nombre, int vida, int ataque, int defensa,boolean pocion) {
        super(nombre, vida, ataque, defensa);
        this.pocion = pocion;
        super.ataque = ataque;
        super.defensa = defensa;
    }

    public Boolean getPocion() {
        return pocion;
    }

    public void setPocion(Boolean pocion) {
        this.pocion = pocion;
    }

}
