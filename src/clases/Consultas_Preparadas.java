
package clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private Statement st;
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
        Close();
        try {
            query = "SELECT * FROM "+tabla;
            st = Connect().createStatement();
            rs = st.executeQuery(query);
            
        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(null, "error AL SELECCIONAR LOS DATOS DE LA DB"+ex.getMessage(),"Error-> clase Consultas",0);
        }
        return rs;
    }
    public synchronized ResultSet busqueda_por_cada_Letra(String tabla, String abc){
        Close();
        try {
            query = "SELECT * FROM "+tabla+" WHERE descripcion LIKE '%"+abc+"%'";
            st = Connect().createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Consultas_Preparadas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    public synchronized Object ultimo_Elemento(String tabla,String Column_ID){
        Close();
        Object c = null;
        try {
            query = "SELECT "+Column_ID+" FROM "+tabla+"";
            st = Connect().createStatement();
            rs = st.executeQuery(query);
            
            while(rs.next()){
                 c = rs.getObject(1);
            }
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas_Preparadas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    public synchronized ResultSet selectColumn(String tabla, String Column){
        Close();
        try {
            query = "SELECT "+Column+" FROM "+tabla+" ";
            st = Connect().createStatement();
            rs = st.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas_Preparadas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    /////////////////////////////////////////////////////////////////////////////////////
    //CONSULTAS INSERT
    public boolean insertTableInventario(String codigo, String descripcion, String tipo_Unidad, Double precio, int cantidad, String observacion){
        
        try {
            query = "INSERT INTO inventario (codigo_pro , descripcion , tipo_unidad , precio , stock , observacion) VALUES (?,?,?,?,?,?) ";
            ps = Connect().prepareStatement(query);
            
            ps.setString(1, codigo);
            ps.setString(2, descripcion);
            ps.setString(3, tipo_Unidad);
            ps.setDouble(4, precio);
            ps.setInt(5, cantidad);
            ps.setString(6, observacion);
            
            ps.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas_Preparadas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al insertar los datos --->"+ex.getMessage(),"error",0);
        }
        return false;
    }
    
    
    
    //CONSULTAS UPDATE
    
    
    //CONSULTAS DELETE
    
    
    
    
}
