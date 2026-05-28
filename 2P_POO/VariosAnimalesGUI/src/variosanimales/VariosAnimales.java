// Autor: Quirino Gonzalez Johann David
package variosanimales;

import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class VariosAnimales extends JFrame {
    
    ConexionBD dbc = new ConexionBD();
    
    // Constructor de la ventana principal
    public VariosAnimales() {
        // Configurar la ventana
        setTitle("Sistema Veterinaria");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear los 7 botones
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMostrar = new JButton("Mostrar");
        JButton btnConsultar = new JButton("Consultar Dosis");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnSalir = new JButton("Salir");
        
        // Organizar botones en GridLayout vertical
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));
        panel.add(btnRegistrar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnMostrar);
        panel.add(btnConsultar);
        panel.add(btnGuardar);
        panel.add(btnSalir);
        
        // Agregar el panel a la ventana
        add(panel);
        
        // Acción del botón Salir
        btnSalir.addActionListener(e -> System.exit(0));
        
        // Acciones de los botones
        btnRegistrar.addActionListener(e -> registrarMascota());
        btnActualizar.addActionListener(e -> actualizarMascota());
        btnEliminar.addActionListener(e -> eliminarMascota());
        btnMostrar.addActionListener(e -> mostrarTodas());
        btnConsultar.addActionListener(e -> consultarDosis());
        btnGuardar.addActionListener(e -> guardarEnArchivo());
    }
    
    // ========== MÉTODOS QUE TRABAJAN CON LA BD ==========
    
    // Alta (Registrar) - INSERT
    private void registrarMascota() {
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "No hay conexion con la BD");
                return;
            }
            
            // Pedir datos
            String idStr = JOptionPane.showInputDialog(this, "ID de la mascota:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);
            
            String nombre = JOptionPane.showInputDialog(this, "Nombre:");
            if (nombre == null) return;
            
            String edadStr = JOptionPane.showInputDialog(this, "Edad:");
            if (edadStr == null) return;
            int edad = Integer.parseInt(edadStr);
            
            String pesoStr = JOptionPane.showInputDialog(this, "Peso (kg):");
            if (pesoStr == null) return;
            double peso = Double.parseDouble(pesoStr);
            
            String raza = JOptionPane.showInputDialog(this, "Raza:");
            if (raza == null) return;
            
            // Calcular dosis
            double dosis = peso * 0.5;
            
            // Insertar en la BD
            String sql = "INSERT INTO mascotas (id, nombre, edad, peso, raza, dosis) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre);
            pstmt.setInt(3, edad);
            pstmt.setDouble(4, peso);
            pstmt.setString(5, raza);
            pstmt.setDouble(6, dosis);
            
            int resultado = pstmt.executeUpdate();
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Mascota registrada correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar");
            }
            
            pstmt.close();
            conn.close();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: ID, edad y peso deben ser numeros");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error SQL: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error driver: " + ex.getMessage());
        }
    }
    
    // Cambio (Actualizar) - UPDATE
    private void actualizarMascota() {
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) return;
            
            String idStr = JOptionPane.showInputDialog(this, "ID de la mascota a actualizar:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);
            
            // Verificar si existe
            String checkSql = "SELECT * FROM mascotas WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Mascota no encontrada");
                conn.close();
                return;
            }
            
            // Pedir nuevos datos
            String nombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", rs.getString("nombre"));
            if (nombre == null) return;
            
            String edadStr = JOptionPane.showInputDialog(this, "Nueva edad:", rs.getInt("edad"));
            if (edadStr == null) return;
            int edad = Integer.parseInt(edadStr);
            
            String pesoStr = JOptionPane.showInputDialog(this, "Nuevo peso:", rs.getDouble("peso"));
            if (pesoStr == null) return;
            double peso = Double.parseDouble(pesoStr);
            
            String raza = JOptionPane.showInputDialog(this, "Nueva raza:", rs.getString("raza"));
            if (raza == null) return;
            
            // Recalcular dosis
            double dosis = peso * 0.5;
            
            // Actualizar
            String sql = "UPDATE mascotas SET nombre = ?, edad = ?, peso = ?, raza = ?, dosis = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setInt(2, edad);
            pstmt.setDouble(3, peso);
            pstmt.setString(4, raza);
            pstmt.setDouble(5, dosis);
            pstmt.setInt(6, id);
            
            int resultado = pstmt.executeUpdate();
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Mascota actualizada correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar");
            }
            
            rs.close();
            checkStmt.close();
            pstmt.close();
            conn.close();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Los valores deben ser numeros");
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    // Baja (Eliminar) - DELETE
    private void eliminarMascota() {
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) return;
            
            String idStr = JOptionPane.showInputDialog(this, "ID de la mascota a eliminar:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);
            
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres eliminar la mascota con ID " + id + "?");
            if (confirm != JOptionPane.YES_OPTION) {
                conn.close();
                return;
            }
            
            String sql = "DELETE FROM mascotas WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            int resultado = pstmt.executeUpdate();
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Mascota eliminada correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "Mascota no encontrada");
            }
            
            pstmt.close();
            conn.close();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID debe ser numero");
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    // Mostrar todos (SELECT)
    private void mostrarTodas() {
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) return;
            
            String sql = "SELECT * FROM mascotas ORDER BY id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            StringBuilder builder = new StringBuilder();
            builder.append("========================================\n");
            builder.append("      LISTADO COMPLETO DE MASCOTAS\n");
            builder.append("========================================\n\n");
            
            boolean hayDatos = false;
            
            while (rs.next()) {
                hayDatos = true;
                builder.append("ID: ").append(rs.getInt("id")).append("\n");
                builder.append("Nombre: ").append(rs.getString("nombre")).append("\n");
                builder.append("Edad: ").append(rs.getInt("edad")).append(" años\n");
                builder.append("Peso: ").append(rs.getDouble("peso")).append(" kg\n");
                builder.append("Raza: ").append(rs.getString("raza")).append("\n");
                builder.append("Dosis: ").append(String.format("%.2f", rs.getDouble("dosis"))).append(" ml\n");
                builder.append("------------------------------------\n");
            }
            
            if (!hayDatos) {
                JOptionPane.showMessageDialog(this, "No hay mascotas registradas");
            } else {
                JTextArea textArea = new JTextArea(builder.toString());
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(600, 450));
                JOptionPane.showMessageDialog(this, scrollPane, "Todas las Mascotas", JOptionPane.INFORMATION_MESSAGE);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    // Consultar dosis (SELECT por ID)
    private void consultarDosis() {
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) return;
            
            String idStr = JOptionPane.showInputDialog(this, "ID de la mascota para calcular dosis:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);
            
            String sql = "SELECT * FROM mascotas WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                double peso = rs.getDouble("peso");
                double dosis = peso * 0.5;
                
                String mensaje = "====================================\n" +
                                "      DATOS DE LA MASCOTA\n" +
                                "====================================\n" +
                                "ID: " + rs.getInt("id") + "\n" +
                                "Nombre: " + rs.getString("nombre") + "\n" +
                                "Edad: " + rs.getInt("edad") + " años\n" +
                                "Peso: " + peso + " kg\n" +
                                "Raza: " + rs.getString("raza") + "\n" +
                                "------------------------------------\n" +
                                "DOSIS DE MEDICAMENTO:\n" +
                                "   " + String.format("%.2f", dosis) + " ml\n" +
                                "------------------------------------\n" +
                                "Formula: Peso x 0.5 ml/kg";
                
                JOptionPane.showMessageDialog(this, mensaje, "Dosis Calculada", JOptionPane.INFORMATION_MESSAGE);
                
                // Opcional: actualizar la dosis en la BD
                String updateSql = "UPDATE mascotas SET dosis = ? WHERE id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setDouble(1, dosis);
                updateStmt.setInt(2, id);
                updateStmt.executeUpdate();
                updateStmt.close();
                
            } else {
                JOptionPane.showMessageDialog(this, "Mascota no encontrada");
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID debe ser numero");
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    // Guardar en archivo (extra)
    private void guardarEnArchivo() {
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) return;
            
            String sql = "SELECT * FROM mascotas";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("mascotas_backup.txt"));
            
            while (rs.next()) {
                writer.write(rs.getInt("id") + "," +
                             rs.getString("nombre") + "," +
                             rs.getInt("edad") + "," +
                             rs.getDouble("peso") + "," +
                             rs.getString("raza") + "," +
                             rs.getDouble("dosis"));
                writer.newLine();
            }
            
            writer.close();
            rs.close();
            stmt.close();
            conn.close();
            
            JOptionPane.showMessageDialog(this, "Datos guardados en mascotas_backup.txt");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }
    
    // ========== MAIN ==========
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
        VariosAnimales ventana = new VariosAnimales();
        ventana.setVisible(true);
    }
}