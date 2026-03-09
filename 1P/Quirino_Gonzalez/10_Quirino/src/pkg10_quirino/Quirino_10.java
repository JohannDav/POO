// Autor: Quirino Gonzalez Johann David

package pkg10_quirino;

import java.util.Scanner;

public class Quirino_10 {

    public static void main(String[] args) {
        System.out.println("\nQuirino Gonzalez Johann David");
        Scanner scan = new Scanner(System.in);
        int i = 0, j = 0;
        float [] precios = new float [3];
        
        while(i < 3) {
            j = i + 1;
            System.out.println("Ingrese el precio " + j);
            precios [i] = scan.nextFloat();
            i++;
        }
        
        i = 0;
        j = 0;
        
        while(i < 3) {
            System.out.println("El precio " + precios [i]);
            i++;
        }
    }
}