// Autor: Quirino Gonzalez Johann David
package sistemaveterinario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SistemaVeterinario extends JFrame {
    
    // ========== ATRIBUTOS DE TABLAS ==========
    private DefaultTableModel modeloMascotas;
    private DefaultTableModel modeloVeterinarios;
    private JTable tablaMascotas;
    private JTable tablaVeterinarios;
    
    // ========== CONSTRUCTOR ==========
    public SistemaVeterinario() {
        setTitle("SISTEMA VETERINARIO - Gestion de Mascotas y Veterinarios");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel con pestañas
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        tabs.addTab("MASCOTAS", crearPanelMascotas());
        tabs.addTab("VETERINARIOS", crearPanelVeterinarios());
        
        add(tabs);
        
        // Cargar datos iniciales
        cargarMascotas();
        cargarVeterinarios();
    }
    
    // ========== METODO PARA CREAR BOTONES ==========
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        return btn;
    }
    
    // ========== PANEL DE MASCOTAS ==========
    private JPanel crearPanelMascotas() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(7, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBotones.setPreferredSize(new Dimension(200, 500));
        
        Color azul = new Color(33, 150, 243);
        
        JButton btnRegistrar = crearBoton("Registrar Mascota", azul);
        JButton btnModificar = crearBoton("Modificar Mascota", azul);
        JButton btnEliminar = crearBoton("Eliminar Mascota", azul);
        JButton btnCalcularDosis = crearBoton("Calcular Dosis", azul);
        JButton btnRefrescar = crearBoton("Refrescar Tabla", azul);
        JButton btnGuardar = crearBoton("Guardar Backup", azul);
        JButton btnSalir = crearBoton("Salir", azul);
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCalcularDosis);
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnSalir);
        
        // Tabla
        modeloMascotas = new DefaultTableModel();
        modeloMascotas.setColumnIdentifiers(new String[]{"ID", "NOMBRE", "EDAD", "PESO (kg)", "RAZA", "DOSIS (ml)"});
        tablaMascotas = new JTable(modeloMascotas);
        tablaMascotas.setRowHeight(25);
        tablaMascotas.getTableHeader().setBackground(azul);
        tablaMascotas.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tablaMascotas);
        scroll.setBorder(BorderFactory.createTitledBorder("LISTADO DE MASCOTAS"));
        
        panel.add(panelBotones, BorderLayout.WEST);
        panel.add(scroll, BorderLayout.CENTER);
        
        // Acciones
        btnRegistrar.addActionListener(e -> { registrarMascota(); cargarMascotas(); });
        btnModificar.addActionListener(e -> { modificarMascota(); cargarMascotas(); });
        btnEliminar.addActionListener(e -> { eliminarMascota(); cargarMascotas(); });
        btnCalcularDosis.addActionListener(e -> calcularDosis());
        btnRefrescar.addActionListener(e -> cargarMascotas());
        btnGuardar.addActionListener(e -> guardarBackupMascotas());
        btnSalir.addActionListener(e -> System.exit(0));
        
        return panel;
    }
    
    // ========== PANEL DE VETERINARIOS ==========
    private JPanel crearPanelVeterinarios() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBotones.setPreferredSize(new Dimension(200, 500));
        
        Color verde = new Color(76, 175, 80);
        
        JButton btnRegistrar = crearBoton("Registrar Veterinario", verde);
        JButton btnModificar = crearBoton("Modificar Veterinario", verde);
        JButton btnEliminar = crearBoton("Eliminar Veterinario", verde);
        JButton btnConsultar = crearBoton("Consultar Veterinario", verde);
        JButton btnRefrescar = crearBoton("Refrescar Tabla", verde);
        JButton btnSalir = crearBoton("Salir", verde);
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnSalir);
        
        // Tabla
        modeloVeterinarios = new DefaultTableModel();
        modeloVeterinarios.setColumnIdentifiers(new String[]{"ID", "NOMBRE", "ESPECIALIDAD", "TELEFONO", "EMAIL"});
        tablaVeterinarios = new JTable(modeloVeterinarios);
        tablaVeterinarios.setRowHeight(25);
        tablaVeterinarios.getTableHeader().setBackground(verde);
        tablaVeterinarios.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tablaVeterinarios);
        scroll.setBorder(BorderFactory.createTitledBorder("LISTADO DE VETERINARIOS"));
        
        panel.add(panelBotones, BorderLayout.WEST);
        panel.add(scroll, BorderLayout.CENTER);
        
        // Acciones
        btnRegistrar.addActionListener(e -> { registrarVeterinario(); cargarVeterinarios(); });
        btnModificar.addActionListener(e -> { modificarVeterinario(); cargarVeterinarios(); });
        btnEliminar.addActionListener(e -> { eliminarVeterinario(); cargarVeterinarios(); });
        btnConsultar.addActionListener(e -> consultarVeterinario());
        btnRefrescar.addActionListener(e -> cargarVeterinarios());
        btnSalir.addActionListener(e -> System.exit(0));
        
        return panel;
    }
    
    // ========== CRUD MASCOTAS ==========
    
    private void cargarMascotas() {
        modeloMascotas.setRowCount(0);
        
        try (Connection conn = ConexionMySQL.conectarBD();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM mascotas ORDER BY id")) {
            
            while (rs.next()) {
                double dosis = rs.getDouble("peso") * 0.5;
                modeloMascotas.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("edad"),
                    rs.getDouble("peso"),
                    rs.getString("raza"),
                    String.format("%.2f", dosis) + " ml"
                });
            }
            System.out.println("Mascotas cargadas: " + modeloMascotas.getRowCount());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar mascotas: " + e.getMessage());
        }
    }
    
    private void registrarMascota() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "ID de la mascota (numero entero):");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);
            
            String nombre = JOptionPane.showInputDialog(this, "Nombre de la mascota:");
            if (nombre == null) return;
            
            String edadStr = JOptionPane.showInputDialog(this, "Edad (anos):");
            if (edadStr == null) return;
            int edad = Integer.parseInt(edadStr);
            
            String pesoStr = JOptionPane.showInputDialog(this, "Peso (kg):");
            if (pesoStr == null) return;
            double peso = Double.parseDouble(pesoStr);
            
            String raza = JOptionPane.showInputDialog(this, "Raza:");
            if (raza == null) return;
            
            double dosis = peso * 0.5;
            
            try (Connection conn = ConexionMySQL.conectarBD();
                 PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO mascotas (id, nombre, edad, peso, raza, dosis) VALUES (?, ?, ?, ?, ?, ?)")) {
                
                pstmt.setInt(1, id);
                pstmt.setString(2, nombre);
                pstmt.setInt(3, edad);
                pstmt.setDouble(4, peso);
                pstmt.setString(5, raza);
                pstmt.setDouble(6, dosis);
                pstmt.executeUpdate();
            }
            
            JOptionPane.showMessageDialog(this, "MASCOTA REGISTRADA CORRECTAMENTE!");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Los datos numericos no son validos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    private void modificarMascota() {
        int fila = tablaMascotas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una mascota de la tabla.");
            return;
        }
        
        try {
            int id = (int) modeloMascotas.getValueAt(fila, 0);
            String nombreActual = (String) modeloMascotas.getValueAt(fila, 1);
            int edadActual = (int) modeloMascotas.getValueAt(fila, 2);
            double pesoActual = (double) modeloMascotas.getValueAt(fila, 3);
            String razaActual = (String) modeloMascotas.getValueAt(fila, 4);
            
            String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", nombreActual);
            if (nuevoNombre == null) return;
            
            String nuevaEdadStr = JOptionPane.showInputDialog(this, "Nueva edad:", edadActual);
            if (nuevaEdadStr == null) return;
            int nuevaEdad = Integer.parseInt(nuevaEdadStr);
            
            String nuevoPesoStr = JOptionPane.showInputDialog(this, "Nuevo peso (kg):", pesoActual);
            if (nuevoPesoStr == null) return;
            double nuevoPeso = Double.parseDouble(nuevoPesoStr);
            
            String nuevaRaza = JOptionPane.showInputDialog(this, "Nueva raza:", razaActual);
            if (nuevaRaza == null) return;
            
            double nuevaDosis = nuevoPeso * 0.5;
            
            try (Connection conn = ConexionMySQL.conectarBD();
                 PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE mascotas SET nombre=?, edad=?, peso=?, raza=?, dosis=? WHERE id=?")) {
                
                pstmt.setString(1, nuevoNombre);
                pstmt.setInt(2, nuevaEdad);
                pstmt.setDouble(3, nuevoPeso);
                pstmt.setString(4, nuevaRaza);
                pstmt.setDouble(5, nuevaDosis);
                pstmt.setInt(6, id);
                pstmt.executeUpdate();
            }
            
            JOptionPane.showMessageDialog(this, "MASCOTA MODIFICADA CORRECTAMENTE!");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void eliminarMascota() {
        int fila = tablaMascotas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una mascota de la tabla.");
            return;
        }
        
        int id = (int) modeloMascotas.getValueAt(fila, 0);
        String nombre = (String) modeloMascotas.getValueAt(fila, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Esta seguro de eliminar a " + nombre + " (ID: " + id + ")?",
            "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConexionMySQL.conectarBD();
                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM mascotas WHERE id=?")) {
                
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "MASCOTA ELIMINADA CORRECTAMENTE!");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
    
    private void calcularDosis() {
        int fila = tablaMascotas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una mascota de la tabla.");
            return;
        }
        
        int id = (int) modeloMascotas.getValueAt(fila, 0);
        String nombre = (String) modeloMascotas.getValueAt(fila, 1);
        double peso = (double) modeloMascotas.getValueAt(fila, 3);
        double dosis = peso * 0.5;
        
        String mensaje = "====================================\n" +
                        "      DOSIS CALCULADA\n" +
                        "====================================\n" +
                        "ID: " + id + "\n" +
                        "Nombre: " + nombre + "\n" +
                        "Peso: " + peso + " kg\n" +
                        "------------------------------------\n" +
                        "DOSIS: " + String.format("%.2f", dosis) + " ml\n" +
                        "Formula: Peso x 0.5 ml/kg";
        
        JOptionPane.showMessageDialog(this, mensaje, "Dosis de Medicamento", JOptionPane.INFORMATION_MESSAGE);
        
        // Actualizar dosis en BD
        try (Connection conn = ConexionMySQL.conectarBD();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE mascotas SET dosis=? WHERE id=?")) {
            
            pstmt.setDouble(1, dosis);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Error al actualizar dosis: " + e.getMessage());
        }
    }
    
    private void guardarBackupMascotas() {
        if (modeloMascotas.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos de mascotas para guardar.");
            return;
        }
        
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(
                new java.io.FileWriter("mascotas_backup.txt"))) {
            
            for (int i = 0; i < modeloMascotas.getRowCount(); i++) {
                writer.write(modeloMascotas.getValueAt(i, 0) + "," +
                             modeloMascotas.getValueAt(i, 1) + "," +
                             modeloMascotas.getValueAt(i, 2) + "," +
                             modeloMascotas.getValueAt(i, 3) + "," +
                             modeloMascotas.getValueAt(i, 4) + "," +
                             modeloMascotas.getValueAt(i, 5).toString().replace(" ml", ""));
                writer.newLine();
            }
            
            JOptionPane.showMessageDialog(this, "Backup guardado en: mascotas_backup.txt");
            
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }
    
    // ========== CRUD VETERINARIOS ==========
    
    private void cargarVeterinarios() {
        modeloVeterinarios.setRowCount(0);
        
        try (Connection conn = ConexionMySQL.conectarBD();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM veterinarios ORDER BY idVeterinario")) {
            
            while (rs.next()) {
                modeloVeterinarios.addRow(new Object[]{
                    rs.getInt("idVeterinario"),
                    rs.getString("nombre"),
                    rs.getString("especialidad"),
                    rs.getString("telefono"),
                    rs.getString("email")
                });
            }
            System.out.println("Veterinarios cargados: " + modeloVeterinarios.getRowCount());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar veterinarios: " + e.getMessage());
        }
    }
    
    private void registrarVeterinario() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "ID del veterinario (numero entero):");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);
            
            String nombre = JOptionPane.showInputDialog(this, "Nombre del veterinario:");
            if (nombre == null) return;
            
            String especialidad = JOptionPane.showInputDialog(this, "Especialidad:");
            if (especialidad == null) return;
            
            String telefono = JOptionPane.showInputDialog(this, "Telefono:");
            if (telefono == null) telefono = "";
            
            String email = JOptionPane.showInputDialog(this, "Email:");
            if (email == null) email = "";
            
            try (Connection conn = ConexionMySQL.conectarBD();
                 PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO veterinarios (idVeterinario, nombre, especialidad, telefono, email) VALUES (?, ?, ?, ?, ?)")) {
                
                pstmt.setInt(1, id);
                pstmt.setString(2, nombre);
                pstmt.setString(3, especialidad);
                pstmt.setString(4, telefono);
                pstmt.setString(5, email);
                pstmt.executeUpdate();
            }
            
            JOptionPane.showMessageDialog(this, "VETERINARIO REGISTRADO CORRECTAMENTE!");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: El ID debe ser un numero.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    private void modificarVeterinario() {
        int fila = tablaVeterinarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un veterinario de la tabla.");
            return;
        }
        
        try {
            int id = (int) modeloVeterinarios.getValueAt(fila, 0);
            String nombreActual = (String) modeloVeterinarios.getValueAt(fila, 1);
            String especialidadActual = (String) modeloVeterinarios.getValueAt(fila, 2);
            String telefonoActual = (String) modeloVeterinarios.getValueAt(fila, 3);
            String emailActual = (String) modeloVeterinarios.getValueAt(fila, 4);
            
            String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", nombreActual);
            if (nuevoNombre == null) return;
            
            String nuevaEspecialidad = JOptionPane.showInputDialog(this, "Nueva especialidad:", especialidadActual);
            if (nuevaEspecialidad == null) return;
            
            String nuevoTelefono = JOptionPane.showInputDialog(this, "Nuevo telefono:", telefonoActual);
            if (nuevoTelefono == null) nuevoTelefono = "";
            
            String nuevoEmail = JOptionPane.showInputDialog(this, "Nuevo email:", emailActual);
            if (nuevoEmail == null) nuevoEmail = "";
            
            try (Connection conn = ConexionMySQL.conectarBD();
                 PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE veterinarios SET nombre=?, especialidad=?, telefono=?, email=? WHERE idVeterinario=?")) {
                
                pstmt.setString(1, nuevoNombre);
                pstmt.setString(2, nuevaEspecialidad);
                pstmt.setString(3, nuevoTelefono);
                pstmt.setString(4, nuevoEmail);
                pstmt.setInt(5, id);
                pstmt.executeUpdate();
            }
            
            JOptionPane.showMessageDialog(this, "VETERINARIO MODIFICADO CORRECTAMENTE!");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void eliminarVeterinario() {
        int fila = tablaVeterinarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un veterinario de la tabla.");
            return;
        }
        
        int id = (int) modeloVeterinarios.getValueAt(fila, 0);
        String nombre = (String) modeloVeterinarios.getValueAt(fila, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Esta seguro de eliminar al veterinario " + nombre + " (ID: " + id + ")?",
            "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConexionMySQL.conectarBD();
                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM veterinarios WHERE idVeterinario=?")) {
                
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "VETERINARIO ELIMINADO CORRECTAMENTE!");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
    
    private void consultarVeterinario() {
        String idStr = JOptionPane.showInputDialog(this, "ID del veterinario a consultar:");
        if (idStr == null) return;
        
        try {
            int id = Integer.parseInt(idStr);
            
            try (Connection conn = ConexionMySQL.conectarBD();
                 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM veterinarios WHERE idVeterinario = ?")) {
                
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    String mensaje = "====================================\n" +
                                    "      DATOS DEL VETERINARIO\n" +
                                    "====================================\n" +
                                    "ID: " + rs.getInt("idVeterinario") + "\n" +
                                    "Nombre: " + rs.getString("nombre") + "\n" +
                                    "Especialidad: " + rs.getString("especialidad") + "\n" +
                                    "Telefono: " + rs.getString("telefono") + "\n" +
                                    "Email: " + rs.getString("email");
                    
                    JOptionPane.showMessageDialog(this, mensaje, "Veterinario Encontrado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Veterinario no encontrado.");
                }
                rs.close();
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: El ID debe ser un numero.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    // ========== MAIN ==========
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   Autor: Quirino Gonzalez Johann David");
        System.out.println("   Proyecto: Sistema Veterinario");
        System.out.println("   Base de Datos: MySQL");
        System.out.println("   Entidades: Mascotas y Veterinarios");
        System.out.println("=========================================");
        
        // Probar conexion
        try {
            Connection conn = ConexionMySQL.conectarBD();
            if (conn != null) {
                System.out.println("MySQL conectado correctamente");
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        
        // Iniciar interfaz grafica
        SwingUtilities.invokeLater(() -> {
            SistemaVeterinario ventana = new SistemaVeterinario();
            ventana.setVisible(true);
        });
    }
}