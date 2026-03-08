// Autor: Quirino Gonzalez Johann David

package pkg9_quirino;

import java.util.Scanner;

public class Quirino_9 {

    public static void main(String[] args) {
        System.out.println("\nQuirino Gonzalez Johann David");
        Scanner scan = new Scanner(System.in);
        
        int i = 0, j = 0;
        float [] precios = new float [3];
        for(i = 0; i < 3; i ++){
            j = i + 1;
            System.out.println("Ingrese el precio");
            precios [i] = scan.nextFloat();
            
        }
        
        j = 0;
        for ( i = 0; i < 3; i ++){
            System.out.println("El precio " + precios [i]);
        }
        
    }

}