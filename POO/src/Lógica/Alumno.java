package Lógica;
public class Alumno {
    
    int id;
    String nombre;
    String Apellido;
    //modificador, tipo de dato que va a retornar, nombre del método, recibir un parametro?
    public void mostrarNombre(){
        
        System.out.println("Hola soy un alumno y sé decir mi nombre: ");
        
    }
    
    public void saberAprobado(double calificacion){
        
        if(calificacion <= 6){
            System.out.println("Aprobé la materia");
        }
        else{
            System.out.println("Uyy, no aprobé");
        }
        
    }
}
