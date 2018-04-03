
package clases;

import java.awt.Component;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sun.swing.table.DefaultTableCellHeaderRenderer;

/**
 *
 * @author jeremy Calderon Yataco
 */
public class AutoJtable {
    
    private boolean addIcon1; 
    private boolean addIcon2;
    
    private boolean addBtn1;
    private boolean addBtn2;
    
    private String name_btn1 = "btn1";
    private String name_btn2 = "btn2";
    
    private String text_btn1;
    private String text_btn2;
    
    private ImageIcon IBtn1;
    private ImageIcon IBtn2;
    
    
    private boolean edit = false;
    
    public void EditCell(boolean x){
        this.edit = x;
    }
    
    private void addBtn1(String text){
        this.text_btn1 = text;
        addBtn1 = true;
    }
    private void addBtn2(String text){
        this.text_btn2 = text;
        addBtn2 = true;
    }
    private void addBtn1(String name,String text){
        this.name_btn1 = name;
        this.text_btn1 = text;
        addBtn1 = true;
    }
    private void addBtn2(String name,String text){
        this.name_btn2 = name;
        this.text_btn2 = text;
        addBtn2 = true;
    }
    private void addBtn1(String name, String text, String url){
        this.name_btn1 = name;
        this.text_btn1 = text;
        this.IBtn1 = new ImageIcon(url);
        addBtn1 = true;
        addIcon1 = true;
    }
    private void addBtn2(String name, String text, String url){
        this.name_btn2 = name;
        this.text_btn2 = text;
        this.IBtn2 = new ImageIcon(url);
        addBtn2 = true;
        addIcon2 = true;
    }
    
    
    
    class Render extends DefaultTableCellHeaderRenderer{
         @Override
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {

            if(o instanceof  JButton){
                JButton btn = (JButton)o;
                return btn;
            }


            return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1); //To change body of generated methods, choose Tools | Templates.
        }   
    }
    
    
    public synchronized void loadingTable(JTable t, ResultSet rs, String[] col){
        
        t.setDefaultRenderer(Object.class, new Render());//rederizado por defecto
        if(addBtn1){
            JButton btn1 = new JButton(this.text_btn1);
            btn1.setName(name_btn1);
            if(addIcon1){
                 btn1.setIcon(this.IBtn1);
            }
        }
        if(addBtn2){
            JButton btn2 = new JButton(this.text_btn2);
            btn2.setName(name_btn2);
            if(addIcon2){
                 btn2.setIcon(this.IBtn2);
            }
        }
        
        //sobre escribimos el metodo  para que las celdas sean editables o no
        DefaultTableModel d = new DefaultTableModel()
        {
            public boolean isCellEditable(int row, int column){
                return edit;
            }
            
        };
        
        
        
        
        
    }
    
    
}/*LLAVE DE LA CLASE*/
