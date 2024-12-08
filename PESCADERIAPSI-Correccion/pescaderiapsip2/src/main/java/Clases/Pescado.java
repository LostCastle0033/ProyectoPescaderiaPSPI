/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

// Clase base para los tipos de pescados
class Pescado {
    private String nombre;
    private double precioPorKilo;

    public Pescado(String nombre, double precioPorKilo) {
        this.nombre = nombre;
        this.precioPorKilo = precioPorKilo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioPorKilo() {
        return precioPorKilo;
    }

    @Override
    public String toString() {
        return nombre + " : " + precioPorKilo + " $/kg";
    }
}

