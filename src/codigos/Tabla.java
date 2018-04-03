/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package codigos;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Tabla {
    
    public synchronized void ver_tabla(JTable tabla,ResultSet rs){
        ResultSetMetaData rsmd;
        
        
        try {
            //USAMOS LA CLASE RENDER
            tabla.setDefaultRenderer(Object.class, new render());
            
            //CREAMOS EL PRIMER BOTON
            ImageIcon modificar = new ImageIcon("src/img/lapiz.png");
            JButton btn1 = new JButton("");
            btn1.setName("m");
            btn1.setIcon(modificar);
            btn1.setToolTipText("MODIFICAR ESTE PRODUCTO");
            
            //EL SEGUNDO BOTON
            ImageIcon eliminar = new ImageIcon("src/img/delete.png");
            JButton btn2 = new JButton("");
            btn2.setName("e");
            btn2.setIcon(eliminar);
            btn2.setToolTipText("ELIMINAR ESTE PRODUCTO");
            
            JCheckBox chx = new JCheckBox();
            chx.setName("select");
            
           
            DefaultTableModel d = new DefaultTableModel()
            {
                public boolean isCellEditable(int row, int column){
                    return false;
                }
            
            };
            
            //colocamos las columnas que necesitaremos
            d.addColumn("ID");
            d.addColumn("CATEGORIA");
            d.addColumn("PRODUCTO");
            d.addColumn("PRECIO");
            d.addColumn("CANT");
            d.addColumn("M");
            d.addColumn("E");
            //------>
            
            //llenamos el vector con la lista de la base de datos
            while(rs.next()){
            Vector v = new Vector();
              
              v.add(rs.getString("id_producto"));
              v.add(rs.getString("categoria"));
              v.add(rs.getString("producto"));
              v.add("S/ "+rs.getString("precio"));
              v.add(rs.getString("cantidad")+" und");
              v.add(btn1);
              v.add(btn2);
              d.addRow(v);//AGREGAMOS EL VETOR AL MODELO DEL JTABLE
              
            }
            
            //caracteristicas de la tabla
            tabla.setModel(d);
            tabla.setBackground(Color.WHITE);
            tabla.setBorder(BorderFactory.createEmptyBorder());
            
            //el alto de la celda
            tabla.setRowHeight(30);
            
            
            //el ancho de cada celda
            tabla.getColumnModel().getColumn(0).setMaxWidth(50);
            tabla.getColumnModel().getColumn(1).setMaxWidth(180);
            tabla.getColumnModel().getColumn(2).setMaxWidth(300);
            tabla.getColumnModel().getColumn(3).setMaxWidth(100);
            tabla.getColumnModel().getColumn(4).setMaxWidth(80);
            tabla.getColumnModel().getColumn(5).setMaxWidth(50);
            tabla.getColumnModel().getColumn(6).setMaxWidth(50);
           d.fireTableDataChanged();
            
        } catch (SQLException ex) {
            Logger.getLogger(Tabla.class.getName()).log(Level.SEVERE, null, ex);
        }
   
        
        
        
    }

   
    
    

}
