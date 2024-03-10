package Lógica;
public class POO {
    public static void main(String[] args) {
        
        Alumno alu1 = new Alumno ();        
        Alumno alu2 = new Alumno (5, "Luicina", "de Paula");
        
        //Tambien se puede estableciendo una variable int id e igualarla a alu2.getId();
        System.out.println("La id del alumno 2 es: " + alu2.getId());
        System.out.println("El nombre de alu2 es: "+ alu2.getNombre());
        System.out.println("El apellido del alu2 es: " + alu2.getApellido());
        
        //Usar set para agregar los valores manualmente 
        alu1.setId(8);
        alu1.setNombre("Suscribete");
        alu1.setApellido("TodoCode");
        
        System.out.println("-------------------");
        System.out.println("La id del alumno 1 es: " + alu1.getId());
        System.out.println("El nombre de alu1 es: "+ alu1.getNombre());
        System.out.println("El apellido del alu1 es: " + alu1.getApellido());
        
        //Modificar el Id del alumno 2
        alu2.setId(35);
        System.out.println("--------------");
        System.out.println("La id del alumno 2 es: " + alu2.getId());
        System.out.println("El nombre de alu2 es: "+ alu2.getNombre());
        System.out.println("El apellido del alu2 es: " + alu2.getApellido());
        
        
    }
    
}
