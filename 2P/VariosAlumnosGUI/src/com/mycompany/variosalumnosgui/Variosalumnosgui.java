// Autor: Quirino González Johann David
package com.mycompany.variosalumnosgui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Variosalumnosgui extends JFrame {

    private final ArrayList<Alumno> listaAlumnos;

    public Variosalumnosgui() {
        this.listaAlumnos = new ArrayList<>(); 
        setTitle("Gestión de Alumnos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnRegistrar = new JButton("Registrar Alumno");    
        JButton btnCalcular = new JButton("Calcular Promedio");
        JButton btnMostrar = new JButton("Mostrar Todos");
        JButton btnSalir = new JButton("Salir");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.add(btnRegistrar);
        panel.add(btnCalcular);
        panel.add(btnMostrar);
        panel.add(btnSalir);
        add(panel);

        btnRegistrar.addActionListener(e -> registrarAlumno());
        btnCalcular.addActionListener(e -> calcularPromedioAlumno());
        btnMostrar.addActionListener(e -> mostrarTodos());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Variosalumnosgui().setVisible(true);
            }
        });
    }

    private void registrarAlumno() {
        try {
            // Solicita los datos al usuario
            String nombre = JOptionPane.showInputDialog(this, "Nombre del alumno:");
            
            // Solicita el número de boleta (10 dígitos)
            String boletaStr = JOptionPane.showInputDialog(this, "Número de boleta (10 dígitos):");
            
            // Validar que la boleta tenga exactamente 10 dígitos
            if (boletaStr.length() != 10) {
                JOptionPane.showMessageDialog(this, "Error: El número de boleta debe tener exactamente 10 dígitos.");
                return;
            }
            
            // Validar que la boleta solo contenga números
            long boleta = Long.parseLong(boletaStr);
            
            int c1 = Integer.parseInt(JOptionPane.showInputDialog(this, "Calificación 1:"));
            int c2 = Integer.parseInt(JOptionPane.showInputDialog(this, "Calificación 2:"));
            int c3 = Integer.parseInt(JOptionPane.showInputDialog(this, "Calificación 3:"));

            // Crea el objeto Alumno con todos los datos incluyendo la boleta
            Alumno alumno = new Alumno(nombre, boleta, c1, c2, c3);
            listaAlumnos.add(alumno);
            JOptionPane.showMessageDialog(this, "Alumno registrado correctamente.");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: La boleta debe ser un número y las calificaciones números enteros.");
        }
    }

    private void calcularPromedioAlumno() {
        if (listaAlumnos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay alumnos registrados.");
            return;
        }

        String nombreBuscado = JOptionPane.showInputDialog(this, "Nombre del alumno:");
        for (Alumno a : listaAlumnos) {
            if (a.getNombre().equalsIgnoreCase(nombreBuscado)) {
                a.calcularPromedio();
                JOptionPane.showMessageDialog(this, "Boleta: " + a.getBoleta() + "\n" +
                        "Promedio de " + a.getNombre() + ": " + String.format("%.2f", a.getPromedio()));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Alumno no encontrado.");
    }

    private void mostrarTodos() {
        if (listaAlumnos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay alumnos registrados.");
            return;
        }

        StringBuilder builder = new StringBuilder("Listado de alumnos:\n");
        builder.append("====================================\n");
        for (Alumno a : listaAlumnos) {
            a.calcularPromedio();
            builder.append(a).append("\n");
            builder.append("------------------------------------\n");
        }

        JTextArea textArea = new JTextArea(builder.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Todos los Alumnos", JOptionPane.INFORMATION_MESSAGE);
    }

    // Clase interna de Alumno con número de boleta
    static class Alumno {
        private final String nombre;
        private final long boleta;      // Número de boleta de 10 dígitos
        private final int cal1;
        private final int cal2;
        private final int cal3;
        private double promedio;

        // Constructor modificado para incluir la boleta
        public Alumno(String nombre, long boleta, int cal1, int cal2, int cal3) {
            this.nombre = nombre;
            this.boleta = boleta;
            this.cal1 = cal1;
            this.cal2 = cal2;
            this.cal3 = cal3;
        }

        public void calcularPromedio() {
            promedio = (cal1 + cal2 + cal3) / 3.0;
        }

        public String getNombre() {
            return nombre;
        }
        
        public long getBoleta() {
            return boleta;
        }

        public double getPromedio() {
            return promedio;
        }

        @Override
        public String toString() {
            return "Boleta: " + boleta + "\n" +
                   "Nombre: " + nombre + "\n" +
                   "Calificaciones: " + cal1 + ", " + cal2 + ", " + cal3 + "\n" +
                   "Promedio: " + String.format("%.2f", promedio);
        }
    }
}