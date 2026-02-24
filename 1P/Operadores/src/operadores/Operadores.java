package operadores;
import java.util.Scanner;

public class Operadores {
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        int y = 10 + 2;
        int x = 101;
        y = ++ x;
        
        System.out.println("\ncontenido de x: " + x);
        System.out.println("\ncontenido de y: " + y);
        y = 10 + 2;
        x = 101;
        y = x--;
        
        System.out.println("\ncontenido de x: " + x);
        System.out.println("\ncontenido de y: " + y);
    }
    
}
