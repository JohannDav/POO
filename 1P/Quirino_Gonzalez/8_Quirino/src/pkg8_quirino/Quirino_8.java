// Autor: Quirino Gonzalez Johann David

package pkg8_quirino;

import java.util.Scanner;

public class Quirino_8 {

    public static void main(String[] args) {
        System.out.println("\nQuirino Gonzalez Johann David");
        Scanner scan = new Scanner(System.in);
        int [] calificacion = new int [3];
        System.out.println("Ingrese una primera calificacion: ");
        calificacion [0] = scan.nextInt();
        System.out.println("La calificacion tecleada es: " + calificacion [0]);
        System.out.println("Ingrese la segunda calificacion; ");
        calificacion [1] = scan.nextInt();
        System.out.println("La calificacion tecleada es: " + calificacion [1]);
        System.out.println("Ingrese la tercera calificacion; ");
        calificacion [2] = scan.nextInt();
        System.out.println("El numero tecleado es: " + calificacion [2]);
        
    }

}