package variosanimales;

import java.util.ArrayList;
import java.util.Scanner;

public class VariosAnimales {
    private static final ArrayList<VariosAnimales> listaAnimales = new ArrayList<>();
    private static final Scanner scan = new Scanner(System.in);
    
    private final String nombre;
    private final int edad;
    private final double peso;
    private final String raza;
    private double dosisMedicamento;

    public VariosAnimales(String nombre, int edad, double peso, String raza) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.raza = raza;
        this.dosisMedicamento = 0.0;
    }
    
    public static void main(String[] args) {
        System.out.println("Quirino Gonzalez Johann David");
        
        boolean salir = false;
        int opcion;
        do {
            System.out.println("\n\n--- MENU DE GESTION VETERINARIA ---");
            System.out.println("1. Registrar una mascota");
            System.out.println("2. Calcular dosis de medicamento de una mascota");
            System.out.println("3. Mostrar todas las dosis de todas las mascotas");
            System.out.println("4. Salir");
            System.out.print("Teclee la opcion deseada: ");
            opcion = scan.nextInt();
            scan.nextLine();
            
            if (opcion == 1) {
                registrarAnimal();
            } else if (opcion == 2) {
                calcularDosisAnimal();
            } else if (opcion == 3) {
                mostrarTodos();
            } else if (opcion == 4) {
                System.out.println("Gracias por usar el sistema veterinario. Hasta pronto!");
                salir = true;
            } else {
                System.out.println("Opcion invalida. Teclee una opcion valida.");
            }
        } while (!salir);
        scan.close();
    }

    private static void registrarAnimal() {
        System.out.print("Ingrese el nombre de la mascota: ");
        String nombre = scan.nextLine();
        System.out.print("Ingrese la edad de la mascota: ");
        int edad = scan.nextInt();
        System.out.print("Ingrese el peso de la mascota (en kg): ");
        double peso = scan.nextDouble();
        scan.nextLine();
        System.out.print("Ingrese la raza de la mascota: ");
        String raza = scan.nextLine();
        VariosAnimales animal = new VariosAnimales(nombre, edad, peso, raza);
        listaAnimales.add(animal);
        System.out.println("Se guardo correctamente los datos de la mascota: " + animal.nombre);
    }

    private static void calcularDosisAnimal() {
        if (listaAnimales.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
            return;
        }
        System.out.print("Ingrese el nombre de la mascota a consultar: ");
        String nombreBuscado = scan.nextLine();
        boolean encontrado = false;
        for (VariosAnimales a : listaAnimales) {
            if (nombreBuscado.equals(a.getNombre())) {
                a.calcularDosis();
                System.out.println("La dosis de medicamento para " + a.getNombre() + " es: " + a.getDosis() + " ml");
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Mascota no encontrada.");
        }
    }

    private static void mostrarTodos() {
        if (listaAnimales.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
            return;
        }
        System.out.println("\nListado de mascotas y sus dosis: ");
        for (VariosAnimales a : listaAnimales) {
            a.calcularDosis();
            System.out.println(a);
        }
    }
   
    public void calcularDosis() {
        this.dosisMedicamento = peso * 0.5;
    }
   
    public double getDosis() {
        return dosisMedicamento;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Mascota: " + nombre + " | Edad: " + edad + " años | Peso: " + peso + " kg | Raza: " + raza + " | Dosis: " + dosisMedicamento + " ml";
    }
}