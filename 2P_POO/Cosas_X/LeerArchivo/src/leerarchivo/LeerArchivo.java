// Autor: Quirino Gonzalez Johann David
package leerarchivo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LeerArchivo {
    public static void main(String[] args) throws IOException {
        System.out.println("\nQuirino Gonzalez Johann David");
        
        FileReader in = null;
        FileWriter out = null;
 
        try {
            in = new FileReader("prueba.txt");
            out = new FileWriter("copia.txt");
            int c;
 
            while ((c = in.read()) != -1) {
                char leido = (char)c;
                out.write(String.valueOf(leido).toUpperCase());
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        
    } //Fin del void main
}