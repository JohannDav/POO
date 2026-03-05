// Autor: Quirino Gonzalez Johann David

package quirino_11;

import java.util.Scanner;

public class Quirino_11 {

    public static void main(String[] args) {
        System.out.println("\nQuirino Gonzalez Johann David");
        Scanner scan = new Scanner(System.in);
        int i = 0, j = 0;
        float [] precios = new float [3];
        do{
            j = i + 1;
            System.out.println("Ingrese el precio " + j + ":");
            precios [i] = scan.nextFloat();
            i ++;
        }
        while(i < 3);
        i = 0;
        j = 0;
        do{
            System.out.println("El precio " + precios [i]);
            i ++;
        }
        while(i < 3);
        
    }

}