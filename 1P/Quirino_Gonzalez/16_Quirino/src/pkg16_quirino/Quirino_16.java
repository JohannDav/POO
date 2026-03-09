// Autor: Quirino Gonzalez Johann David
package pkg16_quirino;
import java.util.Scanner;
public class Quirino_16 {
    public static void main(String[] args) {
        System.out.println("\nQuirino Gonzalez Johann David");
        Scanner scan = new Scanner(System.in);
        
        int i = 0;
        int j = 0;
        double promedio = 0.0;
        float calificacion[][];
        calificacion = new float[1][3];
        int k = 0;
        
        do {
            System.out.println("\n\nMenu Calculadora de promedio de calificaciones ");
            System.out.println("1 Captura calificaciones del alumno ");
            System.out.println("2 Calculo del promedio ");
            System.out.println("3 Salir ");
            System.out.println("Teclee la opcion deseada ");
            int opcion = scan.nextInt();
            
            switch (opcion) {
                case 1 -> {
                    System.out.println("Ingrese tres calificaciones ");
                    j = 0; // Reiniciamos j para que en cada captura empiece desde 1
                    for (i = 0; i < 3; i++) {
                        j++;
                        System.out.println("Ingrese la calificacion " + j);
                        calificacion[0][i] = scan.nextFloat();
                    }
                    break;
                }
                case 2 -> {
                    System.out.println("Promedio de calificaciones ");
                    promedio = calificacion[0][0] + calificacion[0][1] + calificacion[0][2];
                    promedio = promedio / 3;
                    System.out.println("El promedio de las calificaciones es: " + promedio);
                    break;
                }
                case 3 -> {
                    System.out.println("Gracias por usar la calculadora. Adios");
                    k++;
                    break;
                }
                default -> {
                    System.out.println("Opcion no valida, reintente");
                    break;
                }
            }
        } while (k == 0);
    } //Fin del void main
}