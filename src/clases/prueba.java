
package clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Castillo
 */
public class prueba extends Conexion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String cadena = "hola mundo verga";
        String parte = "ol";
        if(cadena.contains(parte)){
            System.out.println("encontrado");
        }
        
        
    }
    
}
