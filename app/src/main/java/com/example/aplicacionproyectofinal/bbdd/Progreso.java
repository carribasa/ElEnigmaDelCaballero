package com.example.aplicacionproyectofinal.bbdd;

import com.example.aplicacionproyectofinal.modelo.Heroe;

import java.io.Serializable;

public class Progreso implements Serializable {

    String nombre;
    String contrasenya;
    Heroe heroe;
    Boolean minijuego01Completado;
    Boolean minijuego02Completado;
    Boolean minijuego03Completado;
    Boolean batalla01Completada;
    Boolean batalla02Completada;
    Boolean batalla03Completada;
    Integer monedas;


    public Progreso(String nombre, String contrasenya, Boolean minijuego01Completado,
                    Boolean minijuego02Completado, Boolean minijuego03Completado,
                    Boolean batalla01Completada, Boolean batalla02Completada, Boolean batalla03Completada,
                    Integer monedas, Integer vida,Integer ataque,Integer defensa,Boolean pocion) {
        this.nombre = nombre;
        this.contrasenya = contrasenya;
        this.heroe = new Heroe(nombre,vida,ataque,defensa,pocion);
        this.minijuego01Completado = minijuego01Completado;
        this.minijuego02Completado = minijuego02Completado;
        this.minijuego03Completado = minijuego03Completado;
        this.batalla01Completada = batalla01Completada;
        this.batalla02Completada = batalla02Completada;
        this.batalla03Completada = batalla03Completada;
        this.monedas = monedas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public Heroe getHeroe() {
        return heroe;
    }

    public void setHeroe(Heroe heroe) {
        this.heroe = heroe;
    }

    public Boolean getMinijuego01Completado() {
        return minijuego01Completado;
    }

    public void setMinijuego01Completado(Boolean minijuego01Completado) {
        this.minijuego01Completado = minijuego01Completado;
    }

    public Boolean getMinijuego02Completado() {
        return minijuego02Completado;
    }

    public void setMinijuego02Completado(Boolean minijuego02Completado) {
        this.minijuego02Completado = minijuego02Completado;
    }

    public Boolean getMinijuego03Completado() {
        return minijuego03Completado;
    }

    public void setMinijuego03Completado(Boolean minijuego03Completado) {
        this.minijuego03Completado = minijuego03Completado;
    }

    public Boolean getBatalla01Completada() {
        return batalla01Completada;
    }

    public void setBatalla01Completada(Boolean batalla01Completada) {
        this.batalla01Completada = batalla01Completada;
    }

    public Boolean getBatalla02Completada() {
        return batalla02Completada;
    }

    public void setBatalla02Completada(Boolean batalla02Completada) {
        this.batalla02Completada = batalla02Completada;
    }

    public Boolean getBatalla03Completada() {
        return batalla03Completada;
    }

    public void setBatalla03Completada(Boolean batalla03Completada) {
        this.batalla03Completada = batalla03Completada;
    }

    public Integer getMonedas() {
        return monedas;
    }

    public void setMonedas(Integer monedas) {
        this.monedas = monedas;
    }
}
