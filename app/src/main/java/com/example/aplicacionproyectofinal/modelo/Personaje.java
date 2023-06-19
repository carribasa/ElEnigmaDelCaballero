package com.example.aplicacionproyectofinal.modelo;

import java.io.Serializable;

public class Personaje implements Serializable {

    String nombre;
    int vida;
    int vidaActual;
    int ataque;
    int ataqueActual;
    int defensa;
    int defensaActual;

    public Personaje(String nombre, int vida, int ataque, int defensa) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.vidaActual = vida;
        this.ataqueActual = ataque;
        this.defensaActual = defensa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getAtaqueActual() {
        return ataqueActual;
    }

    public void setAtaqueActual(int ataqueActual) {
        this.ataqueActual = ataqueActual;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getDefensaActual() {
        return defensaActual;
    }

    public void setDefensaActual(int defensaActual) {
        this.defensaActual = defensaActual;
    }
}
