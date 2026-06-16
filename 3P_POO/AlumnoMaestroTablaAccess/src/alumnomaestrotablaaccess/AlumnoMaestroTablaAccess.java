// YO
package alumnomaestrotablaaccess;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class AlumnoMaestroTablaAccess extends JFrame {
    
    ConexionBD dbc = new ConexionBD();

    private DefaultTableModel modeloAlumnos;
    private DefaultTableModel modeloMaestros;

    private JTable tablaAlumnos;
    private JTable tablaMaestros;
    
    public AlumnoMaestroTablaAccess() {
        setTitle("Gestión Escolar: Alumnos y Maestros");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        // --- Panel Alumnos ------------------------------------------------
        JPanel panelAlumnos = new JPanel(new BorderLayout());

        // Menu (botones) alumnos - SIN Consultar y SIN Refrescar
        JPanel menuAlumnos = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton btnRegA = new JButton("Registrar Alumno");
        JButton btnActA = new JButton("Actualizar Alumno");
        JButton btnDelA = new JButton("Eliminar Alumno");
        JButton btnSalirA = new JButton("SALIR");

        menuAlumnos.add(btnRegA);
        menuAlumnos.add(btnActA);
        menuAlumnos.add(btnDelA);
        menuAlumnos.add(btnSalirA);

        // Inicializar modelo y tabla alumnos
        modeloAlumnos = new DefaultTableModel();
        modeloAlumnos.setColumnIdentifiers(new String[]{
            "Boleta", "Nombre", "Cal1", "Cal2", "Cal3", "Promedio"
        });
        tablaAlumnos = new JTable(modeloAlumnos);

        JScrollPane scrollA = new JScrollPane(tablaAlumnos);
        panelAlumnos.add(menuAlumnos, BorderLayout.WEST);
        panelAlumnos.add(scrollA, BorderLayout.CENTER);
        tabs.addTab("Alumnos", panelAlumnos);

        // --- Panel Maestros ------------------------------------------------
        JPanel panelMaestros = new JPanel(new BorderLayout());

        JPanel menuMaestros = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton btnRegM = new JButton("Registrar Maestro");
        JButton btnActM = new JButton("Actualizar Maestro");
        JButton btnDelM = new JButton("Eliminar Maestro");
        JButton btnSalirM = new JButton("SALIR");

        menuMaestros.add(btnRegM);
        menuMaestros.add(btnActM);
        menuMaestros.add(btnDelM);
        menuMaestros.add(btnSalirM);

        // Inicializar modelo y tabla maestros
        modeloMaestros = new DefaultTableModel();
        modeloMaestros.setColumnIdentifiers(new String[]{
            "ID", "Nombre", "Materia"
        });
        tablaMaestros = new JTable(modeloMaestros);

        JScrollPane scrollM = new JScrollPane(tablaMaestros);
        panelMaestros.add(menuMaestros, BorderLayout.WEST);
        panelMaestros.add(scrollM, BorderLayout.CENTER);
        tabs.addTab("Maestros", panelMaestros);

        add(tabs);

        // Cargar datos iniciales
        cargarTablaAlumnos();
        cargarTablaMaestros();

        // Listeners para Alumnos
        btnRegA.addActionListener(e -> { registrarAlumno(); cargarTablaAlumnos(); });
        btnActA.addActionListener(e -> { actualizarAlumno(); cargarTablaAlumnos(); });
        btnDelA.addActionListener(e -> { eliminarAlumno(); cargarTablaAlumnos(); });
        btnSalirA.addActionListener(e -> salirAplicacion());

        // Listeners para Maestros
        btnRegM.addActionListener(e -> { registrarMaestro(); cargarTablaMaestros(); });
        btnActM.addActionListener(e -> { actualizarMaestro(); cargarTablaMaestros(); });
        btnDelM.addActionListener(e -> { eliminarMaestro(); cargarTablaMaestros(); });
        btnSalirM.addActionListener(e -> salirAplicacion());
    }
    
    // Método para salir de la aplicación
    private void salirAplicacion() {
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea salir de la aplicación?", 
            "Confirmar Salida", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AlumnoMaestroTablaAccess ventana = new AlumnoMaestroTablaAccess();
            ventana.setVisible(true);
        });
    }
   
    // Carga tabla de alumnos desde la BD
    private void cargarTablaAlumnos() {
        modeloAlumnos.setRowCount(0);
        try {
            Connection conn = ConexionBD.conectarBD();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM alumnos");

            while (rs.next()) {
                float prom = (rs.getFloat("cal1") + rs.getFloat("cal2") + rs.getFloat("cal3")) / 3f;
                modeloAlumnos.addRow(new Object[]{
                    rs.getString("boleta"),
                    rs.getString("nombre"),
                    rs.getFloat("cal1"),
                    rs.getFloat("cal2"),
                    rs.getFloat("cal3"),
                    String.format("%.2f", prom)
                });
            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando alumnos: " + e.getMessage());
        }
    }

    // Carga tabla de maestros desde la BD
    private void cargarTablaMaestros() {
        modeloMaestros.setRowCount(0);
        try {
            Connection conn = ConexionBD.conectarBD();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM maestros");

            while (rs.next()) {
                modeloMaestros.addRow(new Object[]{
                    rs.getString("idMaestro"),
                    rs.getString("nombre"),
                    rs.getString("materia")
                });
            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando maestros: " + e.getMessage());
        }
    }

    // Registrar Alumno
    private void registrarAlumno() {
        try {
            String boleta = JOptionPane.showInputDialog(this, "Teclee el número de boleta ");
            if(boleta == null || boleta.trim().isEmpty()) return;
            
            Connection conn = ConexionBD.conectarBD();
            PreparedStatement psCheck = conn.prepareStatement("SELECT COUNT(*) FROM alumnos WHERE boleta = ?");
            psCheck.setString(1, boleta);
            ResultSet rs = psCheck.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "El alumno con boleta " + boleta + " ya está registrado");
                conn.close();
                return;
            }
            
            String nombre = JOptionPane.showInputDialog(this, "Nombre del alumno:");
            if(nombre == null || nombre.trim().isEmpty()) {
                conn.close();
                return;
            }
            
            int c1 = Integer.parseInt(JOptionPane.showInputDialog(this, "Calificación 1:"));
            int c2 = Integer.parseInt(JOptionPane.showInputDialog(this, "Calificación 2:"));
            int c3 = Integer.parseInt(JOptionPane.showInputDialog(this, "Calificación 3:"));
            
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO alumnos (boleta, nombre, cal1, cal2, cal3) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, boleta);
            ps.setString(2, nombre);
            ps.setInt(3, c1);
            ps.setInt(4, c2);
            ps.setInt(5, c3);
            ps.executeUpdate();
            conn.close();
            JOptionPane.showMessageDialog(this, "Alumno registrado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar alumno: " + ex.getMessage());
        }
    }
 
    // Eliminar Alumno
    private void eliminarAlumno() {
        try {
            String boleta = JOptionPane.showInputDialog(this, "Teclee el número de boleta del alumno a eliminar");
            if(boleta == null || boleta.trim().isEmpty()) return;
            
            Connection conn = ConexionBD.conectarBD();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM alumnos WHERE boleta = ?");
            ps.setString(1, boleta);
            int rows = ps.executeUpdate();
            conn.close();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Alumno eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "Alumno no encontrado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar alumno: " + ex.getMessage());
        }
    }
    
    // Actualizar Alumno
    private void actualizarAlumno() {
        try {
            String boleta = JOptionPane.showInputDialog(this, "Teclee número de boleta del alumno a actualizar");
            if (boleta == null || boleta.trim().isEmpty()) return;
            
            Connection conn = ConexionBD.conectarBD();
            PreparedStatement psCheck = conn.prepareStatement("SELECT * FROM alumnos WHERE boleta = ?");
            psCheck.setString(1, boleta);
            ResultSet rs = psCheck.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "No existe el número de boleta");
                conn.close();
                return;
            }

            String nombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", rs.getString("nombre"));
            int c1 = Integer.parseInt(JOptionPane.showInputDialog(this, "Nueva calificación 1:", rs.getInt("cal1")));
            int c2 = Integer.parseInt(JOptionPane.showInputDialog(this, "Nueva calificación 2:", rs.getInt("cal2")));
            int c3 = Integer.parseInt(JOptionPane.showInputDialog(this, "Nueva calificación 3:", rs.getInt("cal3")));

            PreparedStatement ps = conn.prepareStatement(
                "UPDATE alumnos SET nombre=?, cal1=?, cal2=?, cal3=? WHERE boleta=?"
            );
            ps.setString(1, nombre);
            ps.setInt(2, c1);
            ps.setInt(3, c2);
            ps.setInt(4, c3);
            ps.setString(5, boleta);

            int rows = ps.executeUpdate();
            conn.close();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Alumno actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar los datos del alumno.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar alumno: " + ex.getMessage());
        }
    }

    // Registrar Maestro
    private void registrarMaestro() {
        try {
            String id = JOptionPane.showInputDialog(this, "Teclee el ID del Maestro:");
            if(id == null || id.trim().isEmpty()) return;
            
            Connection conn = ConexionBD.conectarBD();
            PreparedStatement psCheck = conn.prepareStatement("SELECT COUNT(*) FROM maestros WHERE idMaestro = ?");
            psCheck.setString(1, id);
            ResultSet rs = psCheck.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "El maestro con ID " + id + " ya existe.");
                conn.close();
                return;
            }

            String nombre = JOptionPane.showInputDialog(this, "Teclee el Nombre del Maestro:");
            if(nombre == null || nombre.trim().isEmpty()) {
                conn.close();
                return;
            }
            
            String materia = JOptionPane.showInputDialog(this, "Materia que imparte:");
            if(materia == null || materia.trim().isEmpty()) {
                conn.close();
                return;
            }

            PreparedStatement ps = conn.prepareStatement("INSERT INTO maestros (idMaestro, nombre, materia) VALUES (?, ?, ?)");
            ps.setString(1, id);
            ps.setString(2, nombre);
            ps.setString(3, materia);
            ps.executeUpdate();
            conn.close();
            JOptionPane.showMessageDialog(this, "Maestro registrado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar maestro: " + ex.getMessage());
        }
    }

    // Eliminar Maestro
    private void eliminarMaestro() {
        try {
            String id = JOptionPane.showInputDialog(this, "Teclee el ID del Maestro a eliminar:");
            if(id == null || id.trim().isEmpty()) return;
            
            Connection conn = ConexionBD.conectarBD();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM maestros WHERE idMaestro = ?");
            ps.setString(1, id);
            int rows = ps.executeUpdate();
            conn.close();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Maestro eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Maestro no encontrado.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar maestro: " + ex.getMessage());
        }
    }

    // Actualizar Maestro
    private void actualizarMaestro() {
        try {
            String id = JOptionPane.showInputDialog(this, "Teclee el ID del Maestro a actualizar:");
            if(id == null || id.trim().isEmpty()) return;
            
            Connection conn = ConexionBD.conectarBD();
            PreparedStatement psCheck = conn.prepareStatement("SELECT * FROM maestros WHERE idMaestro = ?");
            psCheck.setString(1, id);
            ResultSet rs = psCheck.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Maestro NO encontrado.");
                conn.close();
                return;
            }

            String nombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", rs.getString("nombre"));
            String materia = JOptionPane.showInputDialog(this, "Nueva materia:", rs.getString("materia"));

            PreparedStatement ps = conn.prepareStatement("UPDATE maestros SET nombre=?, materia=? WHERE idMaestro=?");
            ps.setString(1, nombre);
            ps.setString(2, materia);
            ps.setString(3, id);
            ps.executeUpdate();
            conn.close();
            JOptionPane.showMessageDialog(this, "Maestro actualizado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar maestro: " + ex.getMessage());
        }
    }
}