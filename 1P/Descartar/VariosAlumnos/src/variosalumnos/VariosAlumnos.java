// Autor: Quirino Gonzalez Johann David

package variosalumnos;
import java.util.ArrayList;
import java.util.Scanner;

public class VariosAlumnos {
    private static final ArrayList<VariosAlumnos> listaAlumnos = new ArrayList<>();
    private static final Scanner scan = new Scanner(System.in);
    
    private final String nombre;
    private final int calificacion1;
    private final int calificacion2;
    private final int calificacion3;
    private float promedio;

    public VariosAlumnos(String nombre, int calificacion1, int calificacion2, int calificacion3) {
        this.nombre = nombre;
        this.calificacion1 = calificacion1;
        this.calificacion2 = calificacion2;
        this.calificacion3 = calificacion3;
        this.promedio = 0.0f;
    }
    
    public static void main(String[] args) {
        boolean salir = false;
        int opcion;
        do {
            System.out.println("\n\n--- MENU DE GESTION DE ALUMNOS ---");
            System.out.println("1. Registrar a un alumno");
            System.out.println("2. Calcular promedio de un alumno");
            System.out.println("3. Mostrar todos los promedios de todos los alumnos");
            System.out.println("4. Salir");
            System.out.println("Teclee la opcion deseada: ");
            opcion = scan.nextInt();
            scan.nextLine(); // limpiar buffer y accepta datos nuevos
            switch (opcion) {
                case 1 -> registrarAlumno();
                case 2 -> calcularPromedioAlumno();
                case 3 -> mostrarTodos();
                case 4 -> {
                    System.out.println("Gracias por usar el sistema. Hasta pronto!");
                    salir = true;
                }
                default -> System.out.println("Opcion invalida. Teclee una opcion valida.");
            }

        } while (!salir);
        scan.close();
    }

    private static void registrarAlumno() {
        System.out.print("Ingrese el nombre del alumno: ");
        String nombre = scan.nextLine();
        System.out.print("Ingrese calificacion 1: ");
        int c1 = scan.nextInt();
        System.out.print("Ingrese calificacion 2: ");
        int c2 = scan.nextInt();
        System.out.print("Ingrese calificacion 3: ");
        int c3 = scan.nextInt();
        VariosAlumnos alumno = new VariosAlumnos(nombre, c1, c2, c3);
        listaAlumnos.add(alumno);
        System.out.println("Se guardo correctamente los datos del alumno." + alumno.nombre);
    }

    
    private static void calcularPromedioAlumno() {
        if (listaAlumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
            return;
        }
        System.out.print("Ingrese el nombre del alumno a consultar: ");
        String nombreBuscado = scan.nextLine();
        boolean encontrado = false;
        for (VariosAlumnos a : listaAlumnos) {
            if (nombreBuscado.equals(a.getNombre())) {
                a.calcularPromedio();
                System.out.println("El promedio de calificaciones de " + a.getNombre() + " es: " + a.getPromedio());
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Alumno no encontrado.");
        }
    }

    
    private static void mostrarTodos() {
        if (listaAlumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados. ");
            return;
        }
        System.out.println("\nListado de alumnos y sus promedios: ");
        for (VariosAlumnos a : listaAlumnos) {
            a.calcularPromedio();
            System.out.println(a);
        }
    }
   
    
    public void calcularPromedio() {
        this.promedio = (calificacion1 + calificacion2 + calificacion3) / 3.0f;
    }
   
    
    public float getPromedio() {
        return promedio;
    }

    
    public String getNombre() {
        return nombre;
    }


    @Override  //El método de la subclase sobrescribe el método de la superclas
    public String toString() {
        return "Alumno: " + nombre +
               " | Calificaciones: [" + calificacion1 + ", " + calificacion2 + ", " + calificacion3 + "]" +
               " | Promedio: " + promedio;
    }
}
