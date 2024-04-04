package Logica;
public class POO {
    public static void main(String[] args) {
        
        Alumno alu1 = new Alumno ();
        Alumno alu2 = new Alumno (5, "Luisina", "de Paul");
        
        System.out.println("El id del alumno 2 es: " + alu2.getId());
        System.out.println("El nombre del alumno 2 es: " + alu2.getNombre());
        System.out.println("El apellido del alumno 2 es: " + alu2.getApellido());
        System.out.println("---------------------------");
        
        alu1.setId(8);
        alu1.setNombre("Suscribete");
        alu1.setApellido("TodoCode");
        
        System.out.println("El id del alumno 1 es: " + alu1.getId());
        System.out.println("El nombre del alumno 1 es: " + alu1.getNombre());
        System.out.println("El apellido del alumno 1 es: " + alu1.getApellido());
        System.out.println("----------------------------");
        
        //Modificar el id del alumno 2
        alu2.setId(35);
        System.out.println("El id del alumno 2 es: " + alu2.getId());
        System.out.println("El nombre del alumno 2 es: " + alu2.getNombre());
        System.out.println("El apellido del alumno 2 es: " + alu2.getApellido());
        System.out.println("---------------------------");
        
        
    }
}
