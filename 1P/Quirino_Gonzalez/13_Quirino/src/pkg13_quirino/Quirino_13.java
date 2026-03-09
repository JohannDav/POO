// Autor: Quirino Gonzalez Johann David
package pkg13_quirino;
import java.util.Scanner;
public class Quirino_13 {
    public static void main(String[] args) {
        System.out.println("\nQuirino Gonzalez Johann David");
        Scanner scan = new Scanner(System.in);
        int i = 0, j = 0, k = 0;
        float calificacion [][];
        calificacion = new float [2][3];
        for (i = 0; i < 2; i ++){
            k = i + 1;
            System.out.println("\nCalificaciones alumno " + k);
            for (j = 0; j < 3; j++) {
                System.out.println("Ingrese calificacion " +j);
                calificacion [i][j] = scan.nextFloat();
                
            }
        }
        System.out.println("\nCalificaciones \n");
        for (i = 0; i < 2; i++) {
            for (j = 0; j < 3; j ++){
                System.out.println(" " + calificacion [i][j]);
                
            }
            System.out.println("\n");
        }
        
    }

}