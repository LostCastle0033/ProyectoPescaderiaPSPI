/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

// Clase Pescaderia

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class Pescaderia {
    private String nombre;
    private List<Pescado> pescados;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pescaderia";
    private static final String DB_USER = "root";  
    private static final String DB_PASSWORD = "";  

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

    // Método para actualizar el precio de un pescado
    public void actualizarPescado(String nombre, double nuevoPrecio) {
        if (nuevoPrecio <= 0) {
            System.out.println("Error: El precio no puede ser menor o igual a cero.");
            return;
        }

        String query = "UPDATE pescados SET precioPorKilo = ? WHERE nombre = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, nuevoPrecio);
            pstmt.setString(2, nombre);
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Pescado actualizado correctamente.");
            } else {
                System.out.println("No se encontró el pescado con ese nombre.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un pescado de la base de datos
    public void eliminarPescado(String nombre) {
        String query = "DELETE FROM pescados WHERE nombre = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Pescado eliminado correctamente.");
            } else {
                System.out.println("No se encontró el pescado con ese nombre.");
            }
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
