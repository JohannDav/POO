// Autor: Quirino Gonzalez Johann David
package variosanimales;

import java.sql.*;
import javax.swing.JOptionPane;

public class ConexionBD {
    static Connection ConexionBD = null;
    static String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    static String url = "jdbc:ucanaccess://C:\\Users\\jdqg2\\Documents\\NetBeansProjects\\veterinaria.accdb";

    public static Connection conectarBD() throws ClassNotFoundException {
        try {
            Class.forName(driver);
            ConexionBD = DriverManager.getConnection(url);
            //Se comenta para quitar el mensaje de "Se conecto..." en cada interaccion
            //JOptionPane.showMessageDialog(null, "Se conecto la BD Access");
        } catch (SQLException e) {
            System.out.println("No se conecto la BD - Error: " + e);
        }
        return ConexionBD;
    }
}