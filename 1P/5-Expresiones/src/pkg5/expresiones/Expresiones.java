package pkg5.expresiones;

import java.util.Scanner;

public class Expresiones {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        double resultado = 0.0;
        resultado = 3*(12 + 1) / 3 + 1;
        System.out.println("\nPrimera operacion resultado: " + resultado);
        
        resultado = 0.0;
        resultado = 3 * (12 + 1) / (3 + 1);
        System.out.println("\nSegunda operacion resultado: " + resultado);
        
    }
    
}
