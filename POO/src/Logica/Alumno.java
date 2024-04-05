package Logica;

public class Alumno {
    
    int id;
    String nombre;
    String apellido;
    //Constructor vacio
    public Alumno() {
    }
    //constructor completo
    public Alumno(int id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }
    //get and set
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    
    
    
    public void mostrarNombre(){
        
        System.out.println("Hola soy un alumno y se decir mi nombre ");
        
    }
    
    
}
