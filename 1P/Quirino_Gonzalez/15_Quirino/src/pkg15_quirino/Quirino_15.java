// Autor: Quirino Gonzalez Johann David

package pkg15_quirino;

import java.util.Scanner;

public class Quirino_15 {

    public static void main(String[] args) {
        System.out.println("\nQuirino Gonzalez Johann David");
        Scanner scan = new Scanner(System.in);
        int i = 0, j = 0, k = 0;
        float calificacion [][];
        calificacion = new float [2][3];
        do{
            k = i + 1;
            System.out.println("\nCalificaciones alumno " + k);
            j = 0;
            do{
                System.out.println("Ingrese la calificacion " + k);
                calificacion [i][j] = scan.nextFloat();
                j ++;
            } while (j < 3);
            i ++;
        } while(i < 2);
        
        System.out.println("\nCalificaciones \n");
        i = 0;
        do{
            j = 0;
            do{
                System.out.println(" " + calificacion [i][j]);
                j ++;
            } while(j < 3);
            System.out.println("\n");
            i ++;
        } while (i < 2);
                
    } //Fin del void main

}