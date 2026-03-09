// Autor: Quirino Gonzalez Johann David
package pkg14_quirino;
import java.util.Scanner;
public class Quirino_14 {
    public static void main(String[] args) {
        System.out.println("\nQuirino Gonzalez Johann David");
        Scanner scan = new Scanner(System.in);
        int i = 0, j = 0, k = 0;
        float calificacion [][];
        calificacion = new float [2][3];
        while (i < 2){
            k = i + 1;
            System.out.println("\nCalificaciones alumno: " + k);
            j = 0;
            while (j < 3){
                System.out.println("Ingrese calificacion " + j);
                calificacion [i][j] = scan.nextFloat();
                j ++;
            }
            i ++;
        }
        
        System.out.println("======================================");
        System.out.println("Parte dos del programa");
        System.out.println("while anidado");
        System.out.println("");
        System.out.println("\nCalificaciones \n");
        i = 0;
        while (i < 2){
            j = 0;
            while(j < 3){
                System.out.println(" " + calificacion [i][j]);
                j ++;
            }
            System.out.println("\n");
            i ++;
        }
        
        
    }
}