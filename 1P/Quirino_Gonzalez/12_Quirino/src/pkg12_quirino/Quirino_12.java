// Autor: Quirino Gonzalez Johann David
package pkg12_quirino;
import java.util.Scanner;
public class Quirino_12 {
    public static void main(String[] args) {
        System.out.println("\nQuirino Gonzalez Johann David");
        Scanner scan = new Scanner(System.in);
        int i = 0, j  =0, k = 0;
        float [] precios = new float [3];
        while (k == 0){
            i = 0; j = 0;
            while(i < 3){
                j = i + 1;
                System.out.println("Ingrese el precio " + j);
                precios [i] = scan.nextFloat();
                i ++;
            }
            i = 0;
            j = 0;
            while(i < 3){
                System.out.println("El precio " + precios [i]);
                i ++;
            }
            System.out.println("Si desea repetir teclee 0: ");
            k = scan.nextInt();
            
        }
        
    }

}