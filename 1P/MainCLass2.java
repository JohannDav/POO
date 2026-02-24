import java.util.Scanner;

class MainCLass2 {
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		String nombre;

		System.out.println("Ingrese su nombre");
		nombre = scan.next();

		System.out.println("Hola " + nombre);
	}
}