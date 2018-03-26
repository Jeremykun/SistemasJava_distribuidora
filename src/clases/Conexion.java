
package clases;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jeremy Kun 
 */
public class Conexion {
    
    private static String JDBC_NAME = "distribuidora";
    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String JDBC_URL = "jdbc:mysql://127.0.0.1/"+JDBC_NAME;
    private static String JDBC_USER = "root";
    private static String JDBC_PASS = "";
    /*---------------------------------*/
    private Driver driver;
    private Connection con;
    
    
    protected Connection Connect(){
        try {
            String mensaje = "";
            
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
          
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Conexion no establecida"+ex.getMessage(),"error->CLASE CONEXION",0);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Conexion no establecida"+ex.getMessage(), "error->clase conexion",0 );
        }
        return con;
    }
    
    
    protected void Close(){
        if(con != null){
            try {
                this.con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error en el metodo Close() "+ex.getMessage(),"erro -> clase Conexion",0);
            }
        }
    }
    
    
    
}
