import java.util.Scanner;

class MainClass2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String nombre;

        System.out.println("Como te llamas? ");
        nombre = scan.next();

        System.out.println("Hola " + nombre);
    }
}