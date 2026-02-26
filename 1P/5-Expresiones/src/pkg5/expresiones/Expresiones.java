package pkg5.expresiones;

import java.util.*;
import java.io.*;

/**
 * Clase de prueba para visualizar colores del editor
 * @author David
 */
@Deprecated
public class Expresiones<T extends Number> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int contador = 0;
    private List<String> lista = new ArrayList<>();
    private Map<String, Integer> mapa = new HashMap<>();

    public Expresiones() {
        System.out.println("Constructor ejecutado...");
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.print("Ingresa tu nombre: ");
        String nombre = scan.nextLine();

        System.out.print("Ingresa tu edad: ");
        int edad = scan.nextInt();

        double resultado = calcularOperacion(3, 12);

        System.out.println("\nHola " + nombre.toUpperCase());
        System.out.println("Edad: " + edad);
        System.out.println("Resultado: " + resultado);

        // Operador ternario
        String mensaje = (edad >= 18) ? "Mayor de edad" : "Menor de edad";
        System.out.println(mensaje);

        // Bucle for
        for (int i = 0; i < 5; i++) {
            System.out.println("i = " + i);
        }

        // While
        int x = 0;
        while (x < 3) {
            x++;
        }

        // Try-catch-finally
        try {
            int division = 10 / 0; // Esto generará excepción
        } catch (ArithmeticException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Bloque finally ejecutado");
        }

        // Lambda (Java 8+)
        List<String> nombres = Arrays.asList("Ana", "Luis", "Carlos");
        nombres.forEach(n -> System.out.println(n));

        scan.close();
    }

    public static double calcularOperacion(int a, int b) {
        return a * (b + 1) / (a + 1.0);
    }

    @Override
    public String toString() {
        return "Expresiones{" +
                "contador=" + contador +
                ", lista=" + lista +
                ", mapa=" + mapa +
                '}';
    }
}