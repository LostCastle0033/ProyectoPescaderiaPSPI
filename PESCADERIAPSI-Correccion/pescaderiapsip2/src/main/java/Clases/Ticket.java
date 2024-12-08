/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

// Clase Ticket
class Ticket {
    private Cliente cliente;
    private Pescado pescado;
    private double kilos;

    public Ticket(Cliente cliente, Pescado pescado, double kilos) {
        this.cliente = cliente;
        this.pescado = pescado;
        this.kilos = kilos;
    }

    public double calcularTotal() {
        return pescado.getPrecioPorKilo() * kilos;
    }

    @Override
    public String toString() {
        return "Orden para " + cliente + ": " + kilos + " kg de " + pescado + " : Total: " + calcularTotal() + "$";
    }
}

