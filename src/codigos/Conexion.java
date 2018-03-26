

package clases;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Conexion {

    private Connection con;
    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String JDBC_URL = "jdbc:mysql://127.0.0.1/libreria";
    private static String JDBC_USER = "root";
    private static String JDBC_PASS = "";
    private static Driver driver = null;
    
   
    protected Connection Connet(){
        
        try {
            
            if(driver == null){
                try {
                    //se registra el Diver
                    Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
                    driver = (Driver) jdbcDriverClass.newInstance();
                    DriverManager.registerDriver(driver);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "error", 0);
           
        }
        
        return con;
        
    }
    
    protected void Close(){
        try {
            
            this.con.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
