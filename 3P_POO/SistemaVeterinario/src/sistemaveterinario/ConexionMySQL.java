// Autor: Quirino Gonzalez Johann David
package sistemaveterinario;

import java.sql.*;
import javax.swing.JOptionPane;

public class ConexionMySQL {
    
    private static Connection conexion = null;
    private static boolean mensajeMostrado = false;
    
    private static final String URL = "jdbc:mysql://localhost:3306/veterinaria_db";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "n0m3l0";
    
    public static Connection conectarBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            
            if (!mensajeMostrado) {
                System.out.println("Conectado a MySQL - veterinaria_db");
                mensajeMostrado = true;
            }
            return conexion;
            
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL no encontrado");
            JOptionPane.showMessageDialog(null, 
                "Error: No se encontró el driver de MySQL.\nAgrega mysql-connector.jar al proyecto.");
            return null;
            
        } catch (SQLException e) {
            System.err.println("Error de conexion: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                "Error al conectar a MySQL.\nVerifica que:\n" +
                "1. MySQL Server este corriendo\n" +
                "2. Usuario y contrasena sean correctos\n" +
                "3. La BD 'veterinaria_db' exista\n\n" +
                "Error: " + e.getMessage());
            return null;
        }
    }
    
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexion cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar: " + e.getMessage());
        }
    }
}