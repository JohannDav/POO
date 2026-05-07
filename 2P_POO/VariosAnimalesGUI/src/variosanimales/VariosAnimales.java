package variosanimales;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class VariosAnimales extends JFrame {
    
    // ========== ATRIBUTOS ==========
    private final ArrayList<Mascota> listaMascotas;
    private static final String ARCHIVO = "mascotas.txt";
    
    // ========== CONSTRUCTOR ==========
    public VariosAnimales() {
        this.listaMascotas = new ArrayList<>();
        cargarArchivo();  // Cargar datos guardados anteriormente
        
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
    
    // ========== MANEJO DE ARCHIVOS ==========
    
    private void cargarArchivo() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            return;  // No hay archivo previo, no pasa nada
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            int contador = 0;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 6) {  // id,nombre,edad,peso,raza,dosis
                    int id = Integer.parseInt(partes[0].trim());
                    String nombre = partes[1].trim();
                    int edad = Integer.parseInt(partes[2].trim());
                    double peso = Double.parseDouble(partes[3].trim());
                    String raza = partes[4].trim();
                    double dosis = Double.parseDouble(partes[5].trim());
                    
                    Mascota m = new Mascota(id, nombre, edad, peso, raza);
                    m.setDosis(dosis);  // Restaurar la dosis guardada
                    listaMascotas.add(m);
                    contador++;
                }
            }
            if (contador > 0) {
                System.out.println("Se cargaron " + contador + " mascotas del archivo.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: El archivo tiene datos mal formados.");
        }
    }
    
    private void guardarArchivo() {
        if (listaMascotas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay datos para guardar.");
            return;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Mascota m : listaMascotas) {
                // Formato: id,nombre,edad,peso,raza,dosis
                writer.write(m.getId() + "," +
                             m.getNombre() + "," +
                             m.getEdad() + "," +
                             m.getPeso() + "," +
                             m.getRaza() + "," +
                             m.getDosis());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Archivo guardado correctamente.\n" + 
                                         "Ubicacion: " + new File(ARCHIVO).getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + e.getMessage());
        }
    }
    
    // ========== FUNCIONES PRINCIPALES ==========
    
    private void registrarMascota() {
        try {
            // 1. Solicitar ID unico
            String idStr = JOptionPane.showInputDialog(this, "ID de la mascota (numero entero):");
            if (idStr == null || idStr.trim().isEmpty()) return;
            int id = Integer.parseInt(idStr);
            
            // Verificar que el ID no este duplicado
            for (Mascota m : listaMascotas) {
                if (m.getId() == id) {
                    JOptionPane.showMessageDialog(this, "Error: Ya existe una mascota con ese ID.");
                    return;
                }
            }
            
            // 2. Solicitar nombre
            String nombre = JOptionPane.showInputDialog(this, "Nombre de la mascota:");
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio.");
                return;
            }
            
            // 3. Solicitar edad
            int edad = Integer.parseInt(JOptionPane.showInputDialog(this, "Edad de la mascota (anos):"));
            if (edad < 0) {
                JOptionPane.showMessageDialog(this, "La edad no puede ser negativa.");
                return;
            }
            
            // 4. Solicitar peso
            double peso = Double.parseDouble(JOptionPane.showInputDialog(this, "Peso de la mascota (kg):"));
            if (peso <= 0) {
                JOptionPane.showMessageDialog(this, "El peso debe ser mayor a 0.");
                return;
            }
            
            // 5. Solicitar raza
            String raza = JOptionPane.showInputDialog(this, "Raza de la mascota:");
            if (raza == null || raza.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La raza no puede estar vacia.");
                return;
            }
            
            // Crear y agregar la mascota
            Mascota mascota = new Mascota(id, nombre, edad, peso, raza);
            listaMascotas.add(mascota);
            JOptionPane.showMessageDialog(this, "Mascota registrada correctamente!\n" +
                                         "ID: " + id + " | Nombre: " + nombre);
            
            // PUNTO EXTRA: Guardar automáticamente después del alta
            guardarArchivo();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: El ID debe ser numero, la edad numero entero y el peso numero decimal.");
        }
    }
    
    private void calcularDosisMascota() {
        if (listaMascotas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay mascotas registradas.");
            return;
        }
        
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
                if (idStr == null) return;
                int idBuscado = Integer.parseInt(idStr);
                
                for (Mascota m : listaMascotas) {
                    if (m.getId() == idBuscado) {
                        m.calcularDosis();
                        mostrarInfoDosis(m);
                        encontrado = true;
                        // PUNTO EXTRA: Guardar automáticamente después de calcular (por si se actualiza dosis)
                        guardarArchivo();
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID invalido.");
                return;
            }
        } else if (seleccion == 1) {  // Buscar por nombre
            String nombreBuscado = JOptionPane.showInputDialog(this, "Ingrese el nombre de la mascota:");
            if (nombreBuscado == null) return;
            
            for (Mascota m : listaMascotas) {
                if (m.getNombre().equalsIgnoreCase(nombreBuscado)) {
                    m.calcularDosis();
                    mostrarInfoDosis(m);
                    encontrado = true;
                    // PUNTO EXTRA: Guardar automáticamente después de calcular
                    guardarArchivo();
                    break;
                }
            }
        }
        
        if (!encontrado) {
            JOptionPane.showMessageDialog(this, "Mascota no encontrada.");
        }
    }
    
    private void mostrarInfoDosis(Mascota m) {
        String mensaje = "====================================\n" +
                        "      DATOS DE LA MASCOTA\n" +
                        "====================================\n" +
                        "ID: " + m.getId() + "\n" +
                        "Nombre: " + m.getNombre() + "\n" +
                        "Edad: " + m.getEdad() + " anos\n" +
                        "Peso: " + m.getPeso() + " kg\n" +
                        "Raza: " + m.getRaza() + "\n" +
                        "------------------------------------\n" +
                        "DOSIS DE MEDICAMENTO:\n" +
                        "   " + String.format("%.2f", m.getDosis()) + " ml\n" +
                        "------------------------------------\n" +
                        "Formula: Peso x 0.5 ml/kg";
        
        JOptionPane.showMessageDialog(this, mensaje, "Dosis Calculada", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // NUEVA FUNCION: Modificar Mascota (CAMBIO)
    private void modificarMascota() {
        if (listaMascotas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay mascotas registradas.");
            return;
        }
        
        String idStr = JOptionPane.showInputDialog(this, "ID de la mascota a modificar:");
        if (idStr == null) return;
        
        try {
            int idBuscado = Integer.parseInt(idStr);
            for (int i = 0; i < listaMascotas.size(); i++) {
                Mascota m = listaMascotas.get(i);
                if (m.getId() == idBuscado) {
                    // Mostrar datos actuales
                    JOptionPane.showMessageDialog(this, "Datos actuales:\n" +
                        "ID: " + m.getId() + "\n" +
                        "Nombre: " + m.getNombre() + "\n" +
                        "Edad: " + m.getEdad() + " anos\n" +
                        "Peso: " + m.getPeso() + " kg\n" +
                        "Raza: " + m.getRaza());
                    
                    // Solicitar nuevos datos
                    String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", m.getNombre());
                    if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio.");
                        return;
                    }
                    
                    int nuevaEdad = Integer.parseInt(JOptionPane.showInputDialog(this, "Nueva edad:", m.getEdad()));
                    if (nuevaEdad < 0) {
                        JOptionPane.showMessageDialog(this, "La edad no puede ser negativa.");
                        return;
                    }
                    
                    double nuevoPeso = Double.parseDouble(JOptionPane.showInputDialog(this, "Nuevo peso (kg):", m.getPeso()));
                    if (nuevoPeso <= 0) {
                        JOptionPane.showMessageDialog(this, "El peso debe ser mayor a 0.");
                        return;
                    }
                    
                    String nuevaRaza = JOptionPane.showInputDialog(this, "Nueva raza:", m.getRaza());
                    if (nuevaRaza == null || nuevaRaza.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "La raza no puede estar vacia.");
                        return;
                    }
                    
                    // Crear nueva mascota con los mismos ID pero nuevos datos
                    Mascota mascotaModificada = new Mascota(m.getId(), nuevoNombre, nuevaEdad, nuevoPeso, nuevaRaza);
                    // Recalcular dosis
                    mascotaModificada.calcularDosis();
                    listaMascotas.set(i, mascotaModificada);
                    
                    JOptionPane.showMessageDialog(this, "Mascota modificada correctamente.");
                    
                    // PUNTO EXTRA: Guardar automáticamente después de modificar
                    guardarArchivo();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Mascota no encontrada.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: ID, edad y peso deben ser numeros validos.");
        }
    }
    
    // NUEVA FUNCION: Eliminar Mascota (BAJA)
    private void eliminarMascota() {
        if (listaMascotas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay mascotas registradas.");
            return;
        }
        
        String idStr = JOptionPane.showInputDialog(this, "ID de la mascota a eliminar:");
        if (idStr == null) return;
        
        try {
            int idBuscado = Integer.parseInt(idStr);
            for (int i = 0; i < listaMascotas.size(); i++) {
                if (listaMascotas.get(i).getId() == idBuscado) {
                    Mascota m = listaMascotas.get(i);
                    int confirm = JOptionPane.showConfirmDialog(this, 
                        "¿Esta seguro de eliminar a " + m.getNombre() + " (ID: " + m.getId() + ")?",
                        "Confirmar Eliminacion",
                        JOptionPane.YES_NO_OPTION);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        listaMascotas.remove(i);
                        JOptionPane.showMessageDialog(this, "Mascota eliminada correctamente.");
                        
                        // PUNTO EXTRA: Guardar automáticamente después de eliminar
                        guardarArchivo();
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Mascota no encontrada.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID invalido.");
        }
    }
    
    private void mostrarTodas() {
        if (listaMascotas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay mascotas registradas.");
            return;
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("========================================\n");
        builder.append("      LISTADO COMPLETO DE MASCOTAS\n");
        builder.append("========================================\n\n");
        
        for (Mascota m : listaMascotas) {
            m.calcularDosis();
            builder.append("ID: ").append(m.getId()).append("\n");
            builder.append("Nombre: ").append(m.getNombre()).append("\n");
            builder.append("Edad: ").append(m.getEdad()).append(" anos\n");
            builder.append("Peso: ").append(m.getPeso()).append(" kg\n");
            builder.append("Raza: ").append(m.getRaza()).append("\n");
            builder.append("Dosis: ").append(String.format("%.2f", m.getDosis())).append(" ml\n");
            builder.append("------------------------------------\n");
        }
        
        // Mostrar en un area de texto con scroll
        JTextArea textArea = new JTextArea(builder.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 450));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Todas las Mascotas", JOptionPane.INFORMATION_MESSAGE);
        
        // PUNTO EXTRA: Guardar automáticamente después de consultar (aunque no se modificaron datos)
        // Esto es opcional, pero lo pongo para que cada accion principal guarde
        guardarArchivo();
    }
    
    // ========== MAIN ==========
    public static void main(String[] args) {
        // Mostrar nombre del autor
        System.out.println("=========================================");
        System.out.println("   Autor: Quirino Gonzalez Johann David");
        System.out.println("   Proyecto: Sistema Veterinario v3.0");
        System.out.println("   Caracteristicas: ID, GUI, Archivos,");
        System.out.println("   Alta, Baja, Cambio, Consulta y Punto Extra");
        System.out.println("=========================================");
        
        // Iniciar la interfaz grafica (esto es importante para GUI)
        SwingUtilities.invokeLater(() -> {
            new VariosAnimales().setVisible(true);
        });
    }
    
    // ========== CLASE INTERNA MASCOTA ==========
    static class Mascota {
        // Atributos
        private final int id;           // IDENTIFICADOR UNICO
        private final String nombre;
        private final int edad;
        private final double peso;
        private final String raza;
        private double dosisMedicamento;
        
        // Constructor
        public Mascota(int id, String nombre, int edad, double peso, String raza) {
            this.id = id;
            this.nombre = nombre;
            this.edad = edad;
            this.peso = peso;
            this.raza = raza;
            this.dosisMedicamento = 0.0;
        }
        
        // Calcular dosis (0.5 ml por kg)
        public void calcularDosis() {
            this.dosisMedicamento = peso * 0.5;
        }
        
        // Getters
        public int getId() { return id; }
        public String getNombre() { return nombre; }
        public int getEdad() { return edad; }
        public double getPeso() { return peso; }
        public String getRaza() { return raza; }
        public double getDosis() { return dosisMedicamento; }
        
        // Setter para restaurar dosis desde archivo
        public void setDosis(double dosis) {
            this.dosisMedicamento = dosis;
        }
        
        @Override
        public String toString() {
            return "ID: " + id + " | " + nombre + " | " + edad + " anos | " + peso + " kg | " + raza;
        }
    }
}