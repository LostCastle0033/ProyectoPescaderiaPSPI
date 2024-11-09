/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.pescaderiapsip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PescaderiaPSIP {

    public static void main(String[] args) {
        Pescaderia pescaderia = new Pescaderia("La PescaderíaPSIP");

        // Agregar pescados predeterminados a la base de datos
        pescaderia.agregarPescado(new Pescado("Salmon", 451.0));
        pescaderia.agregarPescado(new Pescado("Pulpo", 324.0));
        pescaderia.agregarPescado(new Pescado("Mojarra", 56.0));
        pescaderia.agregarPescado(new PescadoGraso("Atún", 300.0));
        pescaderia.agregarPescado(new PescadoGraso("Sardina", 185.0));
        pescaderia.agregarPescado(new PescadoGraso("Caballa", 100.0));

        // Mostrar los pescados disponibles en la pescadería
        System.out.println(pescaderia);

        // Crear un cliente y un ticket
        Cliente cliente = new Cliente("Alejandro Barroeta");
        Ticket ticket = new Ticket(cliente, pescaderia.getPescados().get(0), 7.2); // Usamos el primer pescado para el ejemplo
        System.out.println(ticket);

        // Permitir que el usuario ingrese un nuevo pescado
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Quieres agregar un nuevo pescado? (si/no)");
        String respuesta = scanner.nextLine();
        
        if (respuesta.equalsIgnoreCase("si")) {
            System.out.print("Introduce el nombre del pescado: ");
            String nombrePescado = scanner.nextLine();
            System.out.print("Introduce el precio por kilo: ");
            double precio = scanner.nextDouble();
            
            Pescado nuevoPescado = new Pescado(nombrePescado, precio);
            pescaderia.agregarPescado(nuevoPescado);
            System.out.println("Pescado agregado: " + nuevoPescado);
        }

        // Mostrar los pescados después de agregar el nuevo
        System.out.println("Pescados actualizados: " + pescaderia);
    }
}

// Clase Pescaderia
class Pescaderia {
    private String nombre;
    private List<Pescado> pescados;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pescaderia";
    private static final String DB_USER = "root";  // Cambia al usuario de tu base de datos
    private static final String DB_PASSWORD = "";  // Cambia al password de tu base de datos

    public Pescaderia(String nombre) {
        this.nombre = nombre;
        this.pescados = new ArrayList<>();
        cargarPescadosDesdeBD();
    }

    // Método para agregar un pescado y guardarlo en la base de datos
    public void agregarPescado(Pescado pescado) {
        pescados.add(pescado);
        guardarPescadoEnBD(pescado);
    }

    // Método para cargar pescados desde la base de datos
    private void cargarPescadosDesdeBD() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nombre, precioPorKilo FROM pescados")) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                double precioPorKilo = rs.getDouble("precioPorKilo");
                pescados.add(new Pescado(nombre, precioPorKilo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para guardar un pescado en la base de datos
    private void guardarPescadoEnBD(Pescado pescado) {
        String query = "INSERT INTO pescados (nombre, precioPorKilo) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, pescado.getNombre());
            pstmt.setDouble(2, pescado.getPrecioPorKilo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pescado> getPescados() {
        return pescados;
    }

    @Override
    public String toString() {
        return "Pescadería: " + nombre + " : Pescados que tenemos: " + pescados;
    }
}

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

// Clase derivada que hereda de Pescado
class PescadoGraso extends Pescado {
    public PescadoGraso(String nombre, double precioPorKilo) {
        super(nombre, precioPorKilo);
    }
}

// Clase para el cliente
class Cliente {
    private String nombre;

    public Cliente(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

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


