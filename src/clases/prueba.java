
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
        
        try {
            String sql = "SELECT * FROM inventario";
            Conexion con = new Conexion();
            
            PreparedStatement ps = con.Connect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {                
                System.out.println(rs.getString("codigo_pro"));
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
               
                
        
        
    }
    
}
