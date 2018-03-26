

package clases;

import java.sql.ResultSet;
import java.sql.Statement;
import clases.Conexion;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Consultas extends Conexion {

    private String query;
    private Statement st;
    private PreparedStatement pst;
    private ResultSet rs;
    private Object[] dato;
    
    private boolean cambio_insertar;
    private boolean cambio_viewData;
    private boolean cambio_eliminar;
    private boolean cambio_actualizar;
    
    
    
    
    //TABLAS
    private static final String TABLA_ALMACEN = "almacen";
    //--->
    private static Consultas consulta;
    
    
    private Consultas() {
        
    }
    
    public static Consultas getInstance(){
        if(consulta == null){
            consulta = new Consultas();
        }
        return consulta;
    }
    
    
    //INSERTAR LOS DATOS EN LA DB 
    public synchronized Boolean insert(String nombre,  String categoria, int cantidad,String precio){
        setCambio_insertar(false);
        try {
            //usaremos preparedStatement <- esta funcion lo  que permite es  poner el sinbolo de ? 
            //lo que hace es que la informacion no valla directamente  en la sentencia sql si no
            // que compila  y asegura que no alla  injecion sql 
            query = "INSERT INTO "+TABLA_ALMACEN+"(producto, categoria, cantidad, precio) VALUES (?,?,?,?)";
            //preparedStatement  recibe como parametro la sentencia sql
            pst = Connet().prepareStatement(query);
            
            //cada ? es una posicion con mensando con la primera que seria el 1
            //pst.setString es el tipo de dato que recibe la BD
            //como el primer campo de la base de datos es el producto dicho de otra forma
            //el nombre de este producto 
            pst.setString(1, nombre);//por eso pongo el 1 es el primer '?'-> producto 
            pst.setString(2, categoria);
            pst.setInt(3, cantidad);
            pst.setString(4, precio);
            
            //ejecuta la query  y isertar los datos
            //executeUpdate se utiliza para cuando vallas a ser
            //Update, Insert, Delete
            pst.executeUpdate();
            setCambio_insertar(true);
            
        } catch (SQLException ex) {
           Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "error", 0);
           return false;
        }finally{
            Close();
        }
        
    
       return true;
        
    }

    //TRAER TODOS LOS DATOS DE DB
    //ESTE METODO DEVUELVE UN OCJECTO DE TIPO RESULT SET 
    //PARA PODER RECIBIR LOS DATOS Y COLOCARLOS EN EL JTABLE
    public synchronized ResultSet viewData(){
        setCambio_viewData(false);
        try {
            
            query = "SELECT  * FROM "+TABLA_ALMACEN+"";//SENTENCIA SQL
            st = Connet().createStatement();
            rs = st.executeQuery(query);
            setCambio_viewData(true);
          
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        }finally{
           
        }
        return rs;
    }
    public synchronized ResultSet buscar(String abc){
        setCambio_viewData(false);
        try {
            // BUSCAR EN DOS DIFERENTES CAMPOS
            query = "SELECT * FROM almacen WHERE categoria LIKE '%"+abc+"%' or producto LIKE '%"+abc+"%'";//SENTENCIA SQL
            st = Connet().createStatement();
            rs = st.executeQuery(query);
            
//           SI LO QUE BUSCAS ESTA EN UN SOLO CAMPO
//                query = "SELECT * FROM almacen WHERE producto LIKE '%"+abc+"%'";//SENTENCIA SQL
//                st = Connet().createStatement();
//                rs = st.executeQuery(query);
//       
//            
          
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        }finally{
           
        }
        return rs;
    }
    public synchronized boolean eliminarDatos(int id){
        setCambio_eliminar(false);
        try {
            query = "DELETE FROM "+TABLA_ALMACEN+" WHERE id_producto = ?";
            pst = Connet().prepareStatement(query);
            
            pst.setInt(1, id);
            setCambio_eliminar(true);
            
            pst.executeUpdate();
            
            return true;
           
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            Close();
        }
        return false ;
    }
    
    public synchronized boolean actualizar(String producto, String categoria, int cantidad, String precio, int id){
        
        try {
            query = "UPDATE "+TABLA_ALMACEN+" SET producto = ?, categoria = ?, cantidad = ?, precio = ? WHERE id_producto = ?";
            pst = Connet().prepareStatement(query);
            
            pst.setString(1, producto);
            pst.setString(2, categoria);
            pst.setString(3, ""+cantidad);
            pst.setString(4, precio);
            pst.setInt(5, id);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            Close();
        }
        return true;
    }
    
    public synchronized boolean estaEnDB(String producto, String categoria){
        
        try {
            
            query = "SELECT producto , categoria  FROM almacen";//SENTENCIA SQL
            st = Connet().createStatement();
            rs = st.executeQuery(query);
            
          
            while (rs.next()) {  
                
                if(rs.getString(1).equals(producto) &&
                   rs.getString(2).equals(categoria))
                {
                    //JOptionPane.showMessageDialog(null,"ESTOY AKI", "Error", 0);
                    return true;
                    
                }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        }finally{
           
        }
        return false;
    }
    
    
    public boolean isCambio_insertar() {
        return cambio_insertar;
    }

    public void setCambio_insertar(boolean cambio_insertar) {
        this.cambio_insertar = cambio_insertar;
    }

    public boolean isCambio_viewData() {
        return cambio_viewData;
    }

    public void setCambio_viewData(boolean cambio_viewData) {
        this.cambio_viewData = cambio_viewData;
    }

    public boolean isCambio_eliminar() {
        return cambio_eliminar;
    }

    public void setCambio_eliminar(boolean cambio_eliminar) {
        this.cambio_eliminar = cambio_eliminar;
    }

    public boolean isCambio_actualizar() {
        return cambio_actualizar;
    }

    public void setCambio_actualizar(boolean cambio_actualizar) {
        this.cambio_actualizar = cambio_actualizar;
    }
    
    
    
}
