// Autor: Quirino Gonzalez Johann David
package variosanimales;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;

public class VariosAnimales extends JFrame {
    
    // ========== ATRIBUTOS ==========
    // Ya NO usamos ArrayList, ahora usamos la BD
    private static final String ARCHIVO = "mascotas_backup.txt";  // Para guardado extra
    ConexionBD dbc = new ConexionBD();  // Objeto para conexion (igual al ejemplo)
    
    // ========== CONSTRUCTOR ==========
    public VariosAnimales() {
        // Ya NO cargamos archivo, la BD tiene los datos
        
        // Configuracion de la ventana
        setTitle("Sistema Veterinario - Gestion de Mascotas");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear botones
        JButton btnRegistrar = new JButton("Registrar Mascota");
        JButton btnCalcularDosis = new JButton("Calcular Dosis de Medicamento");
        JButton btnModificar = new JButton("Modificar Mascota");
        JButton btnEliminar = new JButton("Eliminar Mascota");
        JButton btnMostrarTodos = new JButton("Mostrar Todas las Mascotas");
        JButton btnGuardar = new JButton("Guardar Archivo");
        JButton btnSalir = new JButton("Salir");
        
        // Panel con los botones (GridLayout de 7 filas para los 7 botones)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(btnRegistrar);
        panel.add(btnCalcularDosis);
        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnMostrarTodos);
        panel.add(btnGuardar);
        panel.add(btnSalir);
        
        add(panel);
        
        // Acciones de los botones (usando lambdas)
        btnRegistrar.addActionListener(e -> registrarMascota());
        btnCalcularDosis.addActionListener(e -> calcularDosisMascota());
        btnModificar.addActionListener(e -> modificarMascota());
        btnEliminar.addActionListener(e -> eliminarMascota());
        btnMostrarTodos.addActionListener(e -> mostrarTodas());
        btnGuardar.addActionListener(e -> guardarArchivo());
        btnSalir.addActionListener(e -> {
            guardarArchivo();  // Guardar antes de salir
            System.exit(0);
        });
    }
    
    // ========== MANEJO DE ARCHIVO (BACKUP) ==========
    // Este metodo ahora guarda desde la BD a un archivo de texto
    private void guardarArchivo() {
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "No hay conexion con la BD");
                return;
            }
            
            String sql = "SELECT * FROM mascotas ORDER BY id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
                int contador = 0;
                while (rs.next()) {
                    writer.write(rs.getInt("id") + "," +
                                 rs.getString("nombre") + "," +
                                 rs.getInt("edad") + "," +
                                 rs.getDouble("peso") + "," +
                                 rs.getString("raza") + "," +
                                 rs.getDouble("dosis"));
                    writer.newLine();
                    contador++;
                }
                
                if (contador > 0) {
                    JOptionPane.showMessageDialog(this, "Archivo guardado correctamente.\n" + 
                                                 "Ubicacion: " + new File(ARCHIVO).getAbsolutePath() +
                                                 "\nSe guardaron " + contador + " mascotas.");
                } else {
                    JOptionPane.showMessageDialog(this, "No hay datos para guardar.");
                }
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException | ClassNotFoundException | IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + e.getMessage());
        }
    }
    
    // ========== FUNCIONES PRINCIPALES (USANDO BD) ==========
    
    // Alta (Registrar) - INSERT en BD
    private void registrarMascota() {
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "No hay conexion con la BD");
                return;
            }
            
            // 1. Solicitar ID unico
            String idStr = JOptionPane.showInputDialog(this, "ID de la mascota (numero entero):");
            if (idStr == null || idStr.trim().isEmpty()) {
                conn.close();
                return;
            }
            int id = Integer.parseInt(idStr);
            
            // Verificar que el ID no este duplicado (buscar en BD)
            String checkSql = "SELECT id FROM mascotas WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Error: Ya existe una mascota con ese ID.");
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
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio.");
                conn.close();
                return;
            }
            
            // 3. Solicitar edad
            String edadStr = JOptionPane.showInputDialog(this, "Edad de la mascota (anos en numero entero):");
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
            String pesoStr = JOptionPane.showInputDialog(this, "Peso de la mascota (Kg):");
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
            String raza = JOptionPane.showInputDialog(this, "Raza de la mascota:");
            if (raza == null || raza.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La raza no puede estar vacia.");
                conn.close();
                return;
            }
            
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
                JOptionPane.showMessageDialog(this, "Mascota registrada correctamente!\n" +
                                             "ID: " + id + " | Nombre: " + nombre);
                // PUNTO EXTRA: Guardar automaticamente despues del alta
                guardarArchivo();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar mascota.");
            }
            
            pstmt.close();
            conn.close();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: El ID debe ser numero, la edad numero entero y el peso numero decimal.");
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error de BD: " + ex.getMessage());
        }
    }
    
    // Calcular dosis - Consulta en BD
    private void calcularDosisMascota() {
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) return;
            
            // Primero verificar si hay mascotas
            String countSql = "SELECT COUNT(*) FROM mascotas";
            Statement countStmt = conn.createStatement();
            ResultSet countRs = countStmt.executeQuery(countSql);
            countRs.next();
            if (countRs.getInt(1) == 0) {
                JOptionPane.showMessageDialog(this, "No hay mascotas registradas.");
                countRs.close();
                countStmt.close();
                conn.close();
                return;
            }
            countRs.close();
            countStmt.close();
            
            // Opcion: buscar por ID o por nombre
            String[] opciones = {"Buscar por ID", "Buscar por Nombre"};
            int seleccion = JOptionPane.showOptionDialog(this, 
                "Como desea buscar la mascota?", 
                "Buscar Mascota",
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                opciones, 
                opciones[0]);
            
            boolean encontrado = false;
            
            if (seleccion == 0) {  // Buscar por ID
                try {
                    String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID de la mascota:");
                    if (idStr == null) {
                        conn.close();
                        return;
                    }
                    int idBuscado = Integer.parseInt(idStr);
                    
                    String sql = "SELECT * FROM mascotas WHERE id = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, idBuscado);
                    ResultSet rs = pstmt.executeQuery();
                    
                    if (rs.next()) {
                        double dosis = rs.getDouble("peso") * 0.5;
                        mostrarInfoDosis(rs, dosis);
                        encontrado = true;
                        
                        // Actualizar dosis en BD
                        String updateSql = "UPDATE mascotas SET dosis = ? WHERE id = ?";
                        PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                        updateStmt.setDouble(1, dosis);
                        updateStmt.setInt(2, idBuscado);
                        updateStmt.executeUpdate();
                        updateStmt.close();
                        
                        // PUNTO EXTRA: Guardar automaticamente
                        guardarArchivo();
                    }
                    rs.close();
                    pstmt.close();
                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "ID invalido.");
                    conn.close();
                    return;
                }
            } else if (seleccion == 1) {  // Buscar por nombre
                String nombreBuscado = JOptionPane.showInputDialog(this, "Ingrese el nombre de la mascota:");
                if (nombreBuscado == null) {
                    conn.close();
                    return;
                }
                
                String sql = "SELECT * FROM mascotas WHERE nombre = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, nombreBuscado);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    double dosis = rs.getDouble("peso") * 0.5;
                    mostrarInfoDosis(rs, dosis);
                    encontrado = true;
                    
                    // Actualizar dosis en BD
                    String updateSql = "UPDATE mascotas SET dosis = ? WHERE nombre = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setDouble(1, dosis);
                    updateStmt.setString(2, nombreBuscado);
                    updateStmt.executeUpdate();
                    updateStmt.close();
                    
                    // PUNTO EXTRA: Guardar automaticamente
                    guardarArchivo();
                }
                rs.close();
                pstmt.close();
            }
            
            conn.close();
            
            if (!encontrado) {
                JOptionPane.showMessageDialog(this, "Mascota no encontrada.");
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error de BD: " + ex.getMessage());
        }
    }
    
    // Metodo auxiliar para mostrar info de dosis (adaptado para ResultSet)
    private void mostrarInfoDosis(ResultSet rs, double dosis) throws SQLException {
        String mensaje = "====================================\n" +
                        "      DATOS DE LA MASCOTA\n" +
                        "====================================\n" +
                        "ID: " + rs.getInt("id") + "\n" +
                        "Nombre: " + rs.getString("nombre") + "\n" +
                        "Edad: " + rs.getInt("edad") + " anos\n" +
                        "Peso: " + rs.getDouble("peso") + " kg\n" +
                        "Raza: " + rs.getString("raza") + "\n" +
                        "------------------------------------\n" +
                        "DOSIS DE MEDICAMENTO:\n" +
                        "   " + String.format("%.2f", dosis) + " ml\n" +
                        "------------------------------------\n" +
                        "Formula: Peso x 0.5 ml/kg";
        
        JOptionPane.showMessageDialog(this, mensaje, "Dosis Calculada", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Modificar Mascota (CAMBIO) - UPDATE en BD
    private void modificarMascota() {
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) return;
            
            // Verificar si hay mascotas
            String countSql = "SELECT COUNT(*) FROM mascotas";
            Statement countStmt = conn.createStatement();
            ResultSet countRs = countStmt.executeQuery(countSql);
            countRs.next();
            if (countRs.getInt(1) == 0) {
                JOptionPane.showMessageDialog(this, "No hay mascotas registradas.");
                countRs.close();
                countStmt.close();
                conn.close();
                return;
            }
            countRs.close();
            countStmt.close();
            
            String idStr = JOptionPane.showInputDialog(this, "ID de la mascota a modificar:");
            if (idStr == null) {
                conn.close();
                return;
            }
            
            int idBuscado = Integer.parseInt(idStr);
            
            // Buscar la mascota
            String selectSql = "SELECT * FROM mascotas WHERE id = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, idBuscado);
            ResultSet rs = selectStmt.executeQuery();
            
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Mascota no encontrada.");
                rs.close();
                selectStmt.close();
                conn.close();
                return;
            }
            
            // Mostrar datos actuales
            JOptionPane.showMessageDialog(this, "Datos actuales:\n" +
                "ID: " + rs.getInt("id") + "\n" +
                "Nombre: " + rs.getString("nombre") + "\n" +
                "Edad: " + rs.getInt("edad") + " anos\n" +
                "Peso: " + rs.getDouble("peso") + " kg\n" +
                "Raza: " + rs.getString("raza"));
            
            // Solicitar nuevos datos
            String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", rs.getString("nombre"));
            if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio.");
                rs.close();
                selectStmt.close();
                conn.close();
                return;
            }
            
            String nuevaEdadStr = JOptionPane.showInputDialog(this, "Nueva edad:", rs.getInt("edad"));
            if (nuevaEdadStr == null) {
                rs.close();
                selectStmt.close();
                conn.close();
                return;
            }
            int nuevaEdad = Integer.parseInt(nuevaEdadStr);
            if (nuevaEdad < 0) {
                JOptionPane.showMessageDialog(this, "La edad no puede ser negativa.");
                rs.close();
                selectStmt.close();
                conn.close();
                return;
            }
            
            String nuevoPesoStr = JOptionPane.showInputDialog(this, "Nuevo peso (kg):", rs.getDouble("peso"));
            if (nuevoPesoStr == null) {
                rs.close();
                selectStmt.close();
                conn.close();
                return;
            }
            double nuevoPeso = Double.parseDouble(nuevoPesoStr);
            if (nuevoPeso <= 0) {
                JOptionPane.showMessageDialog(this, "El peso debe ser mayor a 0.");
                rs.close();
                selectStmt.close();
                conn.close();
                return;
            }
            
            String nuevaRaza = JOptionPane.showInputDialog(this, "Nueva raza:", rs.getString("raza"));
            if (nuevaRaza == null || nuevaRaza.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La raza no puede estar vacia.");
                rs.close();
                selectStmt.close();
                conn.close();
                return;
            }
            
            rs.close();
            selectStmt.close();
            
            // Recalcular dosis
            double nuevaDosis = nuevoPeso * 0.5;
            
            // Actualizar en BD
            String updateSql = "UPDATE mascotas SET nombre = ?, edad = ?, peso = ?, raza = ?, dosis = ? WHERE id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, nuevoNombre);
            updateStmt.setInt(2, nuevaEdad);
            updateStmt.setDouble(3, nuevoPeso);
            updateStmt.setString(4, nuevaRaza);
            updateStmt.setDouble(5, nuevaDosis);
            updateStmt.setInt(6, idBuscado);
            
            int resultado = updateStmt.executeUpdate();
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Mascota modificada correctamente.");
                // PUNTO EXTRA: Guardar automaticamente despues de modificar
                guardarArchivo();
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar mascota.");
            }
            
            updateStmt.close();
            conn.close();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: ID, edad y peso deben ser numeros validos.");
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error de BD: " + e.getMessage());
        }
    }
    
    // Eliminar Mascota (BAJA) - DELETE en BD
    private void eliminarMascota() {
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn == null) return;
            
            // Verificar si hay mascotas
            String countSql = "SELECT COUNT(*) FROM mascotas";
            Statement countStmt = conn.createStatement();
            ResultSet countRs = countStmt.executeQuery(countSql);
            countRs.next();
            if (countRs.getInt(1) == 0) {
                JOptionPane.showMessageDialog(this, "No hay mascotas registradas.");
                countRs.close();
                countStmt.close();
                conn.close();
                return;
            }
            countRs.close();
            countStmt.close();
            
            String idStr = JOptionPane.showInputDialog(this, "ID de la mascota a eliminar:");
            if (idStr == null) {
                conn.close();
                return;
            }
            
            int idBuscado = Integer.parseInt(idStr);
            
            // Obtener nombre para el mensaje de confirmacion
            String selectSql = "SELECT nombre FROM mascotas WHERE id = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, idBuscado);
            ResultSet rs = selectStmt.executeQuery();
            
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Mascota no encontrada.");
                rs.close();
                selectStmt.close();
                conn.close();
                return;
            }
            
            String nombre = rs.getString("nombre");
            rs.close();
            selectStmt.close();
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Esta seguro de eliminar a " + nombre + " (ID: " + idBuscado + ")?",
                "Confirmar Eliminacion",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                String deleteSql = "DELETE FROM mascotas WHERE id = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                deleteStmt.setInt(1, idBuscado);
                
                int resultado = deleteStmt.executeUpdate();
                
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(this, "Mascota eliminada correctamente.");
                    // PUNTO EXTRA: Guardar automaticamente despues de eliminar
                    guardarArchivo();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar mascota.");
                }
                deleteStmt.close();
            }
            
            conn.close();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID invalido.");
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error de BD: " + e.getMessage());
        }
    }
    
    // Mostrar todas las mascotas (CONSULTA) - SELECT en BD
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
                double dosis = rs.getDouble("peso") * 0.5;
                builder.append("ID: ").append(rs.getInt("id")).append("\n");
                builder.append("Nombre: ").append(rs.getString("nombre")).append("\n");
                builder.append("Edad: ").append(rs.getInt("edad")).append(" anos\n");
                builder.append("Peso: ").append(rs.getDouble("peso")).append(" kg\n");
                builder.append("Raza: ").append(rs.getString("raza")).append("\n");
                builder.append("Dosis: ").append(String.format("%.2f", dosis)).append(" ml\n");
                builder.append("------------------------------------\n");
            }
            
            if (!hayDatos) {
                JOptionPane.showMessageDialog(this, "No hay mascotas registradas.");
            } else {
                // Mostrar en un area de texto con scroll
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
            JOptionPane.showMessageDialog(this, "Error de BD: " + ex.getMessage());
        }
    }
    
    // ========== MAIN ==========
    public static void main(String[] args) {
        // Mostrar nombre del autor
        System.out.println("=========================================");
        System.out.println("   Autor: Quirino Gonzalez Johann David");
        System.out.println("   Proyecto: Sistema Veterinario v3.0");
        System.out.println("   Caracteristicas: ID, GUI, Access, Archivos,");
        System.out.println("   Alta, Baja, Cambio, Consulta y Punto Extra");
        System.out.println("=========================================");
        
        // Probar conexion primero (igual al ejemplo de AlumnosAccess)
        try {
            Connection conn = ConexionBD.conectarBD();
            if (conn != null) {
                System.out.println("Conexion exitosa a la BD veterinaria.accdb");
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: " + e);
        }
        
        // Iniciar la interfaz grafica
        SwingUtilities.invokeLater(() -> {
            new VariosAnimales().setVisible(true);
        });
    }
}