// Autor: Quirino Gonzalez Johann David

package pkg7_quirino;

import java.util.Scanner;

public class Quirino_7 {

    public static void main(String[] args) {
        System.out.println("\nQuirino Gonzalez Johann David");
        Scanner scan = new Scanner(System.in);
        double resultado = 0.0;
        System.out.println("Ingrese un numero: ");
        int numero = scan.nextInt();
        System.out.println("El numero tecleado es: " + numero);
        resultado = 3 * (10 + numero ) / 10;
        System.out.println("\nResultado de la operacion: " + resultado);
        System.out.println((resultado > 10) ? "Es mayor a 10" : "Es menor o igual a 10");                

    }

}