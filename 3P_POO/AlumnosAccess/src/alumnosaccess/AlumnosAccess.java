// Autor: Quirino Gonzalez Johann David
package alumnosaccess;

import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class AlumnosAccess extends JFrame {
    
    ConexionBD dbc = new ConexionBD();
    
    // Constructor de la ventana principal
    public AlumnosAccess() {
        // Configurar la ventana
        setTitle("Sistema AlumnosAccess");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear los 6 botones
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMostrar = new JButton("Mostrar");
        JButton btnConsultar = new JButton("Consultar");
        JButton btnSalir = new JButton("Salir");
        
        // Organizar botones en GridLayout vertical
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        panel.add(btnRegistrar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnMostrar);
        panel.add(btnConsultar);
        panel.add(btnSalir);
        
        // Agregar el panel a la ventana
        add(panel);
        
        // Acción del botón Salir
        btnSalir.addActionListener(e -> System.exit(0));
        
        // Aquí agregarás las acciones de los otros botones más adelante
    }
    
    // Método main para ejecutar la aplicación
    public static void main(String[] args) {
        // Probar conexión primero
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn != null) {
                System.out.println("Conexion exitosa");
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        
        // Mostrar la ventana
        AlumnosAccess ventana = new AlumnosAccess();
        ventana.setVisible(true);
    }
}