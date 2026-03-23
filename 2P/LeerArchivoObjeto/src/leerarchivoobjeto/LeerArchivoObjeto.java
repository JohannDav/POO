// Autor: Quirino Gonzalez Johann David
package leerarchivoobjeto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Clase Alumno que representa un alumno con calificaciones y promedio
//   se puede generar la clase Alumno en otro archivo del paquete
class Alumno {
    private String nombre;
    private int cal1, cal2, cal3;
    private double promedio;

    public Alumno(String nombre, int cal1, int cal2, int cal3) {
        this.nombre = nombre;
        this.cal1 = cal1;
        this.cal2 = cal2;
        this.cal3 = cal3;
    }

    public void calcularPromedio() {
        this.promedio = (cal1 + cal2 + cal3) / 3.0;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPromedio() {
        return promedio;
    }

    @Override
    public String toString() {
        return nombre + "," + cal1 + "," + cal2 + "," + cal3 + "," + String.format("%.2f", promedio);
    }
}



public class LeerArchivoObjeto {
    public static void main(String[] args) throws IOException {
        System.out.println("\nQuirino Gonzalez Johann David");
        
        // El main puede utilizar sentencias de IO para el manejo de archivos
      
        BufferedReader in = null;                    //Se declara para leer líneas de texto desde un archivo   
        BufferedWriter out = null;                   //Se declara para escribir líneas de texto en un archivo  
        List<Alumno> alumnos = new ArrayList<>();    //Se declara el arreglo para la generación de objetos

        try {
            in = new BufferedReader(new FileReader("alumnos.txt"));   // Abre el archivo de entrada
            out = new BufferedWriter(new FileWriter("salida.txt"));   // Abre el archivo de salida

            String linea;

            while ((linea = in.readLine()) != null) {                     // Lectura del archivo (línea por línea) hasta encontrar el fin y guarda los datos en 
                String[] partes = linea.split(",");                       // Indica que los datos estan separados por una coma
                if (partes.length == 4) {                                 // Guarda el nombre y calificaciones (separadas por comas en el archivo)
                    String nombre = partes[0].trim();
                    int c1 = Integer.parseInt(partes[1].trim());
                    int c2 = Integer.parseInt(partes[2].trim());
                    int c3 = Integer.parseInt(partes[3].trim());

                    Alumno alumno = new Alumno(nombre, c1, c2, c3);       // Con los datos leidos del archivo crea el objeto Alumno
                    alumnos.add(alumno);                                  // Agrega al alumno al arreglo (vector)
                }
            }

            
            calcularPromediosDeLista(alumnos);                                 // Llama a la función de calculo - Para todos los alumnos se calcula el promedio

           
            for (Alumno a : alumnos) {                                         // Repite por cada alumno. La variable a repreenta al objeto   
                System.out.println("Alumno: " + a);                            // Imprime los datos del alumno (representado por a)
                System.out.println("Promedio calculado: " + a.getPromedio());  // Llama a la función getPromedio e Imprime el promedio calculado
                out.write(a.toString());                                       // Guarda en el archivo de salida los datos del objeto representado por a (nombre, calificaciones,  promedio)
                out.newLine();                                                 // Guarda un salto de linea para separar los objetos en el archivo de salida
            }

        } finally {
            if (in != null) in.close();   // Cierra el lector                  // Al terminar cierra el archivo de entrada y de salida
            if (out != null) out.close(); // Cierra el escritor
        }
    }

    public static void calcularPromediosDeLista(List<Alumno> lista) {          // Llama a la función de calcular promedio
        for (Alumno a : lista) {                                               //    Esta duplicado el llamado para permitir que la clase este en otro archivo
            a.calcularPromedio();  // Calcula el promedio de cada objeto
        }        
        
    } //Fin del void main
}