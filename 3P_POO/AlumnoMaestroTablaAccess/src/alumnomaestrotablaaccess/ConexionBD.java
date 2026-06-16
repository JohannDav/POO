// Autor: Quirino González Johann David
package alumnomaestrotablaaccess;
import java.sql.*;
import javax.swing.JOptionPane;

public class ConexionBD {
    static Connection ConexionBD = null;
    static String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    static String url = "jdbc:ucanaccess://C:\\Users\\jdqg2\\Documents\\NetBeansProjects/Alumno.accdb";

    public static Connection conectarBD() throws ClassNotFoundException {
        try {
            Class.forName(driver);
            ConexionBD = DriverManager.getConnection(url);
            JOptionPane.showMessageDialog(null, "Se conecto la BD Access");
        } catch (SQLException e) {
            System.out.println("No se conecto la BD - Error: " + e);
        }
        return ConexionBD;
    }
}
