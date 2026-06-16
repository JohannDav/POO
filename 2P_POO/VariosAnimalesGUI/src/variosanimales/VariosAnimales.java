// Autor: Quirino Gonzalez Johann David
package variosanimales;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.sql.*;

public class VariosAnimales extends JFrame {
    
    // ========== ATRIBUTOS ==========
    private static final String ARCHIVO = "mascotas_backup.txt";
    private DefaultTableModel modeloMascotas;
    private JTable tablaMascotas;
    
    // ========== CONSTRUCTOR ==========
    public VariosAnimales() {
        // Configuracion de la ventana
        setTitle("Sistema Veterinario - Gestion de Mascotas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        // ========== Panel de botones (IZQUIERDA) ==========
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(7, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBotones.setPreferredSize(new Dimension(200, 500));
        
        // Crear botones
        JButton btnRegistrar = new JButton("Registrar Mascota");
        JButton btnCalcularDosis = new JButton("Calcular Dosis");
        JButton btnModificar = new JButton("Modificar Mascota");
        JButton btnEliminar = new JButton("Eliminar Mascota");
        JButton btnRefrescar = new JButton("Refrescar Tabla");
        JButton btnGuardar = new JButton("Guardar Backup");
        JButton btnSalir = new JButton("Salir");
        
        // Estilo de botones
        Color colorBotones = new Color(33, 150, 243);
        Font fuenteBotones = new Font("Segoe UI", Font.BOLD, 12);
        
        JButton[] botones = {btnRegistrar, btnCalcularDosis, btnModificar, 
                             btnEliminar, btnRefrescar, btnGuardar, btnSalir};
        for (JButton btn : botones) {
            btn.setBackground(colorBotones);
            btn.setForeground(Color.WHITE);
            btn.setFont(fuenteBotones);
            btn.setFocusPainted(false);
            panelBotones.add(btn);
        }
        
        // ========== TABLA de mascotas (CENTRO) ==========
        // Crear modelo de tabla con columnas
        modeloMascotas = new DefaultTableModel();
        modeloMascotas.setColumnIdentifiers(new String[]{
            "ID", "Nombre", "Edad (años)", "Peso (kg)", "Raza", "Dosis (ml)"
        });
        
        tablaMascotas = new JTable(modeloMascotas);
        tablaMascotas.setRowHeight(25);
        tablaMascotas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaMascotas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaMascotas.getTableHeader().setBackground(new Color(33, 150, 243));
        tablaMascotas.getTableHeader().setForeground(Color.WHITE);
        
        // Ajustar ancho de columnas
        tablaMascotas.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        tablaMascotas.getColumnModel().getColumn(1).setPreferredWidth(150);  // Nombre
        tablaMascotas.getColumnModel().getColumn(2).setPreferredWidth(80);   // Edad
        tablaMascotas.getColumnModel().getColumn(3).setPreferredWidth(80);   // Peso
        tablaMascotas.getColumnModel().getColumn(4).setPreferredWidth(150);  // Raza
        tablaMascotas.getColumnModel().getColumn(5).setPreferredWidth(100);  // Dosis
        
        JScrollPane scrollPane = new JScrollPane(tablaMascotas);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Listado de Mascotas"));
        
        // Agregar paneles al principal
        panelPrincipal.add(panelBotones, BorderLayout.WEST);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        add(panelPrincipal);
        
        // ========== Cargar datos iniciales ==========
        cargarTablaMascotas();
        
        // ========== Acciones de los botones ==========
        btnRegistrar.addActionListener(e -> { registrarMascota(); cargarTablaMascotas(); });
        btnCalcularDosis.addActionListener(e -> { calcularDosisMascota(); cargarTablaMascotas(); });
        btnModificar.addActionListener(e -> { modificarMascota(); cargarTablaMascotas(); });
        btnEliminar.addActionListener(e -> { eliminarMascota(); cargarTablaMascotas(); });
        btnRefrescar.addActionListener(e -> cargarTablaMascotas());
        btnGuardar.addActionListener(e -> guardarArchivo());
        btnSalir.addActionListener(e -> {
            guardarArchivo();
            System.exit(0);
        });
    }
    
    // ========== CARGAR TABLA desde BD ==========
    private void cargarTablaMascotas() {
        // Limpiar tabla
        modeloMascotas.setRowCount(0);
        
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "No hay conexion con la BD");
                return;
            }
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM mascotas ORDER BY id");
            
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
            
            rs.close();
            stmt.close();
            conn.close();
            
            // Mostrar mensaje de cuántos registros hay
            if (modeloMascotas.getRowCount() > 0) {
                System.out.println("Cargadas " + modeloMascotas.getRowCount() + " mascotas");
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar mascotas: " + e.getMessage());
        }
    }
    
    // ========== MANEJO DE ARCHIVO (BACKUP) ==========
    private void guardarArchivo() {
        if (modeloMascotas.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos para guardar.");
            return;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (int i = 0; i < modeloMascotas.getRowCount(); i++) {
                writer.write(modeloMascotas.getValueAt(i, 0) + "," +
                             modeloMascotas.getValueAt(i, 1) + "," +
                             modeloMascotas.getValueAt(i, 2) + "," +
                             modeloMascotas.getValueAt(i, 3) + "," +
                             modeloMascotas.getValueAt(i, 4) + "," +
                             modeloMascotas.getValueAt(i, 5).toString().replace(" ml", ""));
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Backup guardado correctamente en:\n" + 
                                         new File(ARCHIVO).getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar backup: " + e.getMessage());
        }
    }
    
    // ========== REGISTRAR MASCOTA ==========
    private void registrarMascota() {
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) return;
            
            // 1. Solicitar ID
            String idStr = JOptionPane.showInputDialog(this, "ID de la mascota (numero entero):");
            if (idStr == null || idStr.trim().isEmpty()) {
                conn.close();
                return;
            }
            int id = Integer.parseInt(idStr);
            
            // Verificar ID duplicado
            PreparedStatement checkStmt = conn.prepareStatement("SELECT id FROM mascotas WHERE id = ?");
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Error: Ya existe una mascota con ID " + id);
                rs.close();
                checkStmt.close();
                conn.close();
                return;
            }
            rs.close();
            checkStmt.close();
            
            // 2. Solicitar nombre
            String nombre = JOptionPane.showInputDialog(this, "Nombre de la mascota:");
            if (nombre == null || nombre.trim().isEmpty()) {
                conn.close();
                return;
            }
            
            // 3. Solicitar edad
            String edadStr = JOptionPane.showInputDialog(this, "Edad (años):");
            if (edadStr == null) {
                conn.close();
                return;
            }
            int edad = Integer.parseInt(edadStr);
            if (edad < 0) {
                JOptionPane.showMessageDialog(this, "La edad no puede ser negativa.");
                conn.close();
                return;
            }
            
            // 4. Solicitar peso
            String pesoStr = JOptionPane.showInputDialog(this, "Peso (kg):");
            if (pesoStr == null) {
                conn.close();
                return;
            }
            double peso = Double.parseDouble(pesoStr);
            if (peso <= 0) {
                JOptionPane.showMessageDialog(this, "El peso debe ser mayor a 0.");
                conn.close();
                return;
            }
            
            // 5. Solicitar raza
            String raza = JOptionPane.showInputDialog(this, "Raza:");
            if (raza == null || raza.trim().isEmpty()) {
                conn.close();
                return;
            }
            
            // Calcular dosis
            double dosis = peso * 0.5;
            
            // Insertar en BD
            String sql = "INSERT INTO mascotas (id, nombre, edad, peso, raza, dosis) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre);
            pstmt.setInt(3, edad);
            pstmt.setDouble(4, peso);
            pstmt.setString(5, raza);
            pstmt.setDouble(6, dosis);
            pstmt.executeUpdate();
            
            conn.close();
            JOptionPane.showMessageDialog(this, "✅ Mascota registrada correctamente!\nID: " + id + " | " + nombre);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: ID, edad y peso deben ser numeros validos.");
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error de BD: " + ex.getMessage());
        }
    }
    
    // ========== CALCULAR DOSIS ==========
    private void calcularDosisMascota() {
        int filaSeleccionada = tablaMascotas.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            // Si no hay fila seleccionada, buscar por ID
            String idStr = JOptionPane.showInputDialog(this, "ID de la mascota para calcular dosis:");
            if (idStr == null) return;
            
            try {
                int id = Integer.parseInt(idStr);
                
                Connection conn = ConexionBD.conectarBD();
                if (conn == null) return;
                
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM mascotas WHERE id = ?");
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    double dosis = rs.getDouble("peso") * 0.5;
                    
                    // Actualizar dosis en BD
                    PreparedStatement updateStmt = conn.prepareStatement("UPDATE mascotas SET dosis = ? WHERE id = ?");
                    updateStmt.setDouble(1, dosis);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();
                    updateStmt.close();
                    
                    mostrarInfoDosis(rs, dosis);
                } else {
                    JOptionPane.showMessageDialog(this, "Mascota no encontrada.");
                }
                
                rs.close();
                pstmt.close();
                conn.close();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID invalido.");
            } catch (SQLException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        } else {
            // Usar la fila seleccionada
            int id = (int) modeloMascotas.getValueAt(filaSeleccionada, 0);
            String nombre = (String) modeloMascotas.getValueAt(filaSeleccionada, 1);
            double peso = (double) modeloMascotas.getValueAt(filaSeleccionada, 3);
            double dosis = peso * 0.5;
            
            String mensaje = "====================================\n" +
                            "      DOSIS CALCULADA\n" +
                            "====================================\n" +
                            "ID: " + id + "\n" +
                            "Nombre: " + nombre + "\n" +
                            "Peso: " + peso + " kg\n" +
                            "------------------------------------\n" +
                            "DOSIS: " + String.format("%.2f", dosis) + " ml\n" +
                            "Formula: Peso × 0.5 ml/kg";
            
            JOptionPane.showMessageDialog(this, mensaje, "Dosis Calculada", JOptionPane.INFORMATION_MESSAGE);
            
            // Actualizar en BD
            try {
                Connection conn = ConexionBD.conectarBD();
                if (conn != null) {
                    PreparedStatement updateStmt = conn.prepareStatement("UPDATE mascotas SET dosis = ? WHERE id = ?");
                    updateStmt.setDouble(1, dosis);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();
                    updateStmt.close();
                    conn.close();
                }
            } catch (Exception ex) {
                System.out.println("Error al actualizar dosis: " + ex.getMessage());
            }
        }
    }
    
    // ========== MOSTRAR INFO DOSIS ==========
    private void mostrarInfoDosis(ResultSet rs, double dosis) throws SQLException {
        String mensaje = "====================================\n" +
                        "      DATOS DE LA MASCOTA\n" +
                        "====================================\n" +
                        "ID: " + rs.getInt("id") + "\n" +
                        "Nombre: " + rs.getString("nombre") + "\n" +
                        "Edad: " + rs.getInt("edad") + " años\n" +
                        "Peso: " + rs.getDouble("peso") + " kg\n" +
                        "Raza: " + rs.getString("raza") + "\n" +
                        "------------------------------------\n" +
                        "DOSIS DE MEDICAMENTO:\n" +
                        "   " + String.format("%.2f", dosis) + " ml\n" +
                        "------------------------------------\n" +
                        "Formula: Peso × 0.5 ml/kg";
        
        JOptionPane.showMessageDialog(this, mensaje, "Dosis Calculada", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ========== MODIFICAR MASCOTA ==========
    private void modificarMascota() {
        int filaSeleccionada = tablaMascotas.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una mascota de la tabla para modificar.");
            return;
        }
        
        int id = (int) modeloMascotas.getValueAt(filaSeleccionada, 0);
        String nombreActual = (String) modeloMascotas.getValueAt(filaSeleccionada, 1);
        int edadActual = (int) modeloMascotas.getValueAt(filaSeleccionada, 2);
        double pesoActual = (double) modeloMascotas.getValueAt(filaSeleccionada, 3);
        String razaActual = (String) modeloMascotas.getValueAt(filaSeleccionada, 4);
        
        String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", nombreActual);
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) return;
        
        String nuevaEdadStr = JOptionPane.showInputDialog(this, "Nueva edad:", edadActual);
        if (nuevaEdadStr == null) return;
        int nuevaEdad = Integer.parseInt(nuevaEdadStr);
        
        String nuevoPesoStr = JOptionPane.showInputDialog(this, "Nuevo peso (kg):", pesoActual);
        if (nuevoPesoStr == null) return;
        double nuevoPeso = Double.parseDouble(nuevoPesoStr);
        
        String nuevaRaza = JOptionPane.showInputDialog(this, "Nueva raza:", razaActual);
        if (nuevaRaza == null || nuevaRaza.trim().isEmpty()) return;
        
        double nuevaDosis = nuevoPeso * 0.5;
        
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) return;
            
            PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE mascotas SET nombre = ?, edad = ?, peso = ?, raza = ?, dosis = ? WHERE id = ?");
            pstmt.setString(1, nuevoNombre);
            pstmt.setInt(2, nuevaEdad);
            pstmt.setDouble(3, nuevoPeso);
            pstmt.setString(4, nuevaRaza);
            pstmt.setDouble(5, nuevaDosis);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            
            conn.close();
            JOptionPane.showMessageDialog(this, "✅ Mascota modificada correctamente.");
            
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    // ========== ELIMINAR MASCOTA ==========
    private void eliminarMascota() {
        int filaSeleccionada = tablaMascotas.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una mascota de la tabla para eliminar.");
            return;
        }
        
        int id = (int) modeloMascotas.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloMascotas.getValueAt(filaSeleccionada, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar a " + nombre + " (ID: " + id + ")?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = ConexionBD.conectarBD();
                if (conn == null) return;
                
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM mascotas WHERE id = ?");
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                conn.close();
                
                JOptionPane.showMessageDialog(this, "✅ Mascota eliminada correctamente.");
                
            } catch (SQLException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
    
    // ========== MAIN ==========
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   Autor: Quirino Gonzalez Johann David");
        System.out.println("   Proyecto: Sistema Veterinario v4.0");
        System.out.println("   Caracteristicas: JTable, Access, CRUD");
        System.out.println("   Alta, Baja, Cambio, Consulta");
        System.out.println("=========================================");
        
        // Probar conexion
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn != null) {
                System.out.println("✅ Conexion exitosa a la BD veterinaria.accdb");
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("❌ Error de conexion: " + e);
        }
        
        SwingUtilities.invokeLater(() -> {
            new VariosAnimales().setVisible(true);
        });
    }
}