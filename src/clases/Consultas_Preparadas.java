
package clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jeremy Calderon Yataco
 */
public class Consultas_Preparadas extends Conexion {
    
    private  PreparedStatement ps;
    private  ResultSet rs;
    private static String _NONBRE;
    private static String  query="";
    private static Consultas_Preparadas consulta;
    
    /*PATRON DE DISEÑO SINGLETON*/
    private Consultas_Preparadas(){
        
    }
    public static Consultas_Preparadas getInstance(){
        if(consulta == null){
            consulta = new Consultas_Preparadas();
        }
        return consulta;
    }
    
    /*---------------------------*/
    
    
    /*SELECCIONAR TODA  LA TABLA*/
    //CONSULTAS SELECT
    public synchronized ResultSet selectALLTable(String tabla){
        try {
            query = "SELECT * FROM "+tabla;
            ps = Connect().prepareStatement(query);
            rs = ps.executeQuery();
            
        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(null, "error AL SELECCIONAR LOS DATOS DE LA DB"+ex.getMessage(),"Error-> clase Consultas",0);
        }
        return rs;
    }
    public synchronized ResultSet busqueda_por_cada_Letra(String tabla, String abc){
        try {
            query = "SELECT * FROM "+tabla+" WHERE descripcion LIKE '%"+abc+"%'";
            ps = Connect().prepareStatement(query);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Consultas_Preparadas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    public synchronized int ultimo_ID(String tabla){
        int c = 1;
        try {
            query = "SELECT id_producto FROM "+tabla+"";
            ps = Connect().prepareStatement(query);
            rs = ps.executeQuery();
            
            while(rs.next()){
                c = rs.getInt(0);
            }
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas_Preparadas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    public synchronized ResultSet selectColumn(String tabla, String Column){
        
        try {
            query = "SELECT "+Column+" FROM "+tabla+" ";
            ps = Connect().prepareStatement(query);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas_Preparadas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    /////////////////////////////////////////////////////////////////////////////////////
    //CONSULTAS INSERT
    
    
    //CONSULTAS UPDATE
    
    
    //CONSULTAS DELETE
    
    
    
    
}
