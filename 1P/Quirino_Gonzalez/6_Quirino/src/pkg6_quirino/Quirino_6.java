// Autor: Quirino Gonzalez Johann David

package pkg6_quirino;

import java.util.Scanner;

public class Quirino_6 {

    public static void main(String[] args) {
        System.out.println("\nQuirino Gonzalez Johann David");
        
        Scanner scan = new Scanner(System.in);
        double resultado = 0.0;
        
        System.out.println("Ingrese un numero ");
        int numero = scan.nextInt();
        System.out.println("El mumero tecleado es: " + numero);
        resultado = 3 * (10 + numero) / 10;
        System.out.println("\nResultado de la operacion: " + resultado);
        
        if(resultado > 10){
            System.out.println(" es mayor que 10 ");
        }
        else{
            System.out.println(" es menor a 10 ");
        }
    }

}