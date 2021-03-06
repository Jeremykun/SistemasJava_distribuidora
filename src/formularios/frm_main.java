
package formularios;

import clases.Consultas_Preparadas;
import clases.Factura;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.*;

/**
 * Numero de Contacto 968 078 803
 * @author Jeremy Kun <sguergachi at jeremyKun85@gmail.com>
 */
public class frm_main extends javax.swing.JFrame  {
    /*INSTACIA DE LA CLASE CONSULTORA DE LA BASE DE DATOS
    ESTAMOS UTILIZZANDO EL PATRON DE DISEÑO SINGLETON 
    */
    Consultas_Preparadas consulta = Consultas_Preparadas.getInstance();
    //---------------------------------------->
    
    
    
    /*VARIABLES*/
    String cadena = "";
    boolean focus = false;
    boolean control = false;
    ArrayList<Factura> factura  = new ArrayList<>();
    
    TextAutoCompleter buscar;
    
   
    public frm_main() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        buscar = new TextAutoCompleter(this.txt_busqueda);
   
        txtA_Observacion.setLineWrap(true);
        CargarTabla(consulta.selectALLTable("inventario"),jt_principal);
        //CargarCombo(consulta.selectColumn("inventario", "descripcion"));
        lbl_valor.setText(valor+"0");
        
        UltimoCodigo();
        
    }
    ///////////////cagar tablas///////////////////
    private double valor;
    private void CargarTabla(ResultSet rs2,JTable t){
        JTable tabla = t;
        DefaultTableModel model = new DefaultTableModel(){
            public boolean isCellEditable(int row , int colmun){
                return false;
            }
        };
     
        ResultSet rs = rs2;
        tabla.setModel(model);
        /*AGREGAMOS LAS COLUMNAS DE  LA TABLA*/
        model.addColumn("CODIGO_PRODUCTO");
        model.addColumn("DESCRIPCION");
        model.addColumn("UNIDAD DE COMPRA");
        model.addColumn("PRECIO");
        model.addColumn("STOCK");
        model.addColumn("OBSERVACIONES");
        /*-----------------------------------*/
        /*AGREGAMOS EL ANCHO DE CADA COLUMNA*/
        tabla.getColumnModel().getColumn(0).setMaxWidth(120);
        tabla.getColumnModel().getColumn(1).setMaxWidth(420);
        tabla.getColumnModel().getColumn(2).setMaxWidth(100);
        tabla.getColumnModel().getColumn(3).setMaxWidth(100);
        tabla.getColumnModel().getColumn(4).setMaxWidth(100);
        tabla.getColumnModel().getColumn(5).setMaxWidth(200);
        
        /*consultamos la db*/
        //rs = consulta.selectALLTable("inventario");
        /*-----------------*/
        String tipo = "";
        try {
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add("S/ "+rs.getDouble(5)+"0");
                
                switch(rs.getString(4)){
                    case "PAQUETE":
                        tipo = "PQT";
                        break;
                    case "BOLSA":
                        tipo = "BOL";
                        break;
                }
                v.add(rs.getInt(6)+" "+tipo);
                /*el valor del inventario*/
                valor += rs.getDouble(5) * rs.getInt(6);
                /*-----------------------*/
                
                v.add(rs.getString(7));
                model.addRow(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(frm_main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void CargarCombo(ResultSet rs2){
        try {
            
            //TextAutoCompleter txt_busco = new TextAutoCompleter(txt_busqueda);

            ResultSet rs = rs2;
            buscar.removeAllItems();
            while (rs.next()) {
                //cbo_inventario.addItem(rs.getString(1));
                buscar.addItem(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(frm_main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar el combo box "+ex.getMessage(),"error de carga",0);
        }
      
    }
    private void CargarTablaFactura(){
        
        DefaultTableModel model = new DefaultTableModel(){
            public boolean isCellEditable(){
                return false;
            }
        };
        jt_factura.setModel(model);
        model.addColumn("CODIGO_PRODUCTO");
        model.addColumn("DESCRIPCION");
        model.addColumn("PRECIO");
        model.addColumn("CANTIDAD");
        model.addColumn("IMPORTE");
        
        jt_factura.getColumnModel().getColumn(0).setMaxWidth(100);
        jt_factura.getColumnModel().getColumn(1).setMaxWidth(250);
        jt_factura.getColumnModel().getColumn(2).setMaxWidth(70);
        jt_factura.getColumnModel().getColumn(3).setMaxWidth(70);
        jt_factura.getColumnModel().getColumn(4).setMaxWidth(70);
        
        
        Iterator<Factura>itrFactura = factura.iterator();
        Double imp =0.0;
        while(itrFactura.hasNext()){
            Factura fact = itrFactura.next();
            Vector v = new Vector();
            v.add(fact.getCodigo());
            v.add(fact.getDescripcion());
            v.add(fact.getPrecio());
            v.add(fact.getCantidad());
            v.add(fact.getImporte());
            imp += Double.parseDouble(fact.getImporte());
            lbl_valorFactura.setText(""+imp);
            model.addRow(v);
        }
        
        txt_busqueda.setText("");
        txt_busqueda.requestFocus();
        
        //CargarTabla(consulta.selectALLTable("inventario"));
        
        
    }
    //////////////////////////////////
    
    /////////ultimo codigo//////////////////
    private void UltimoCodigo(){
         CargarTabla(consulta.selectALLTable("inventario"),jt_principal);
        CargarCombo(consulta.selectColumn("inventario", "descripcion"));
        
        String codigo = (String) consulta.ultimo_Elemento("inventario", "codigo_pro");
        txt_codigo.setText(codigo);
        
        String[] partes = codigo.split("_");
        int numero = Integer.parseInt(partes[1]);
        //System.out.println(numero);
        numero++;
        String aux = ""+numero;
        
        if(aux.length() == 1){
            txt_codigo.setText("PROD_000"+aux);
        }
        
        if(aux.length() == 2){
           txt_codigo.setText("PROD_00"+aux);
        }
        if(aux.length() == 3){
           txt_codigo.setText("PROD_0"+aux);
        }
        
        if(aux.length() == 4){
           txt_codigo.setText("PROD_"+aux);
        }
    }
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtA_Observacion = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lbl_valor = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jt_principal = new javax.swing.JTable();
        txt_busqueda = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jt_factura = new javax.swing.JTable();
        jTextField4 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        btn_crearBoleta = new javax.swing.JButton();
        lbl_valorFactura = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbl_idProduc = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        txt_descripcion = new javax.swing.JTextField();
        cbo_unidad = new javax.swing.JComboBox<>();
        txt_precio = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        stxt_stock = new javax.swing.JSpinner();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtA_registro_observaciones = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jt_registro = new javax.swing.JTable();
        btn_insertDatos = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        desktopPane.setLayout(new javax.swing.BoxLayout(desktopPane, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jPanel4.setBackground(new java.awt.Color(9, 205, 209));
        jPanel4.setEnabled(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 85, 87));
        jLabel2.setText("Jeremy Calderon Yataco");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 111, 174, -1));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 85, 87));
        jLabel3.setText("Orlando cano Ezpinosa");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 136, 174, -1));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 85, 87));
        jLabel4.setText("Joel Cotos Huaman");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 166, 174, -1));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 85, 87));
        jLabel5.setText("Shesira Russel Utrilla");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 191, 174, -1));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Programado");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 150, -1));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 102));
        jLabel8.setText("Diseñado");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 120, -1));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("y");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 30, -1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setDisabledIcon(null);
        jLabel1.setDoubleBuffered(true);
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 160, 160));

        txtA_Observacion.setEditable(false);
        txtA_Observacion.setColumns(20);
        txtA_Observacion.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        txtA_Observacion.setRows(5);
        txtA_Observacion.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txtA_Observacion);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "VALOR DEL INVENTARIO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 14), new java.awt.Color(9, 205, 209))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(0, 204, 153));

        jLabel10.setBackground(new java.awt.Color(255, 51, 51));
        jLabel10.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 51, 51));
        jLabel10.setText("S/");

        lbl_valor.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        lbl_valor.setForeground(new java.awt.Color(9, 205, 209));
        lbl_valor.setText("9999.0000");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel10)
                .addComponent(lbl_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nombre.png"))); // NOI18N

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jTabbedPane1.setForeground(new java.awt.Color(0, 102, 102));
        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 2, 20)); // NOI18N

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        jt_principal.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jt_principal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "CODIGO DE ARTICULO", "DESCRIPCION", "UNIDAD_COMPRA", "PRECIO", "STOCK", "OBSERVACION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jt_principal.setRowHeight(28);
        jt_principal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jt_principalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jt_principal);
        if (jt_principal.getColumnModel().getColumnCount() > 0) {
            jt_principal.getColumnModel().getColumn(0).setMinWidth(50);
            jt_principal.getColumnModel().getColumn(0).setPreferredWidth(150);
            jt_principal.getColumnModel().getColumn(0).setMaxWidth(150);
            jt_principal.getColumnModel().getColumn(1).setMinWidth(100);
            jt_principal.getColumnModel().getColumn(1).setPreferredWidth(350);
            jt_principal.getColumnModel().getColumn(1).setMaxWidth(350);
            jt_principal.getColumnModel().getColumn(2).setMinWidth(50);
            jt_principal.getColumnModel().getColumn(2).setPreferredWidth(100);
            jt_principal.getColumnModel().getColumn(2).setMaxWidth(100);
            jt_principal.getColumnModel().getColumn(3).setMinWidth(50);
            jt_principal.getColumnModel().getColumn(3).setPreferredWidth(150);
            jt_principal.getColumnModel().getColumn(3).setMaxWidth(150);
            jt_principal.getColumnModel().getColumn(4).setMinWidth(50);
            jt_principal.getColumnModel().getColumn(4).setPreferredWidth(100);
            jt_principal.getColumnModel().getColumn(4).setMaxWidth(100);
            jt_principal.getColumnModel().getColumn(5).setMinWidth(100);
            jt_principal.getColumnModel().getColumn(5).setPreferredWidth(150);
            jt_principal.getColumnModel().getColumn(5).setMaxWidth(150);
        }

        txt_busqueda.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        txt_busqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_busquedaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_busquedaKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("BUSCAR ARTICULO");

        jPanel8.setBackground(new java.awt.Color(0, 102, 102));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "FACTURACION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 24), new java.awt.Color(51, 51, 51))); // NOI18N

        jt_factura.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jt_factura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CODIGO DE ARTICULO", "DESCRIPCION", "PRECIO", "CANTIDAD", "IMPORTE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jt_factura.setRowHeight(18);
        jScrollPane5.setViewportView(jt_factura);
        if (jt_factura.getColumnModel().getColumnCount() > 0) {
            jt_factura.getColumnModel().getColumn(0).setMinWidth(50);
            jt_factura.getColumnModel().getColumn(0).setPreferredWidth(100);
            jt_factura.getColumnModel().getColumn(0).setMaxWidth(100);
            jt_factura.getColumnModel().getColumn(1).setMinWidth(100);
            jt_factura.getColumnModel().getColumn(1).setPreferredWidth(150);
            jt_factura.getColumnModel().getColumn(1).setMaxWidth(150);
            jt_factura.getColumnModel().getColumn(2).setMinWidth(20);
            jt_factura.getColumnModel().getColumn(2).setPreferredWidth(100);
            jt_factura.getColumnModel().getColumn(2).setMaxWidth(100);
            jt_factura.getColumnModel().getColumn(3).setMinWidth(20);
            jt_factura.getColumnModel().getColumn(3).setPreferredWidth(100);
            jt_factura.getColumnModel().getColumn(3).setMaxWidth(100);
            jt_factura.getColumnModel().getColumn(4).setMinWidth(80);
            jt_factura.getColumnModel().getColumn(4).setPreferredWidth(120);
            jt_factura.getColumnModel().getColumn(4).setMaxWidth(120);
        }

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("NOMBRE DEL CIENTE:");

        btn_crearBoleta.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        btn_crearBoleta.setText("CREAR BOLETA");
        btn_crearBoleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearBoletaActionPerformed(evt);
            }
        });

        lbl_valorFactura.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        lbl_valorFactura.setForeground(new java.awt.Color(9, 205, 209));
        lbl_valorFactura.setText("9999.0000");

        jLabel18.setBackground(new java.awt.Color(255, 51, 51));
        jLabel18.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 51, 51));
        jLabel18.setText("total :  S/");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btn_crearBoleta, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(86, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_valorFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(367, 367, 367))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_valorFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(btn_crearBoleta)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        lbl_idProduc.setText("jLabel19");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1073, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(lbl_idProduc, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lbl_idProduc, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );

        jTabbedPane1.addTab("INVENTARIO", jPanel5);

        jPanel6.setBackground(new java.awt.Color(0, 102, 102));
        jPanel6.setFont(new java.awt.Font("Century Gothic", 2, 24)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("DESCRIPCION:");

        jLabel13.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("CODIGO:");

        jLabel14.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("PRECIO:");

        jLabel15.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("UNIDAD DE COMPRA:");

        txt_codigo.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        txt_descripcion.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        cbo_unidad.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        cbo_unidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UNIDAD", "PAQUETE", "BOLSA", "TIRA", "ATADO" }));

        txt_precio.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("STOCK:");

        stxt_stock.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        jPanel7.setBackground(new java.awt.Color(0, 102, 102));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Observacion del articulo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 14))); // NOI18N

        txtA_registro_observaciones.setBackground(java.awt.SystemColor.control);
        txtA_registro_observaciones.setColumns(20);
        txtA_registro_observaciones.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtA_registro_observaciones.setForeground(new java.awt.Color(0, 102, 102));
        txtA_registro_observaciones.setRows(5);
        jScrollPane2.setViewportView(txtA_registro_observaciones);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jt_registro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jt_registro);

        btn_insertDatos.setText("INSERTAR DATOS");
        btn_insertDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertDatosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbo_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(stxt_stock, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_precio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(387, 387, 387))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_insertDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(126, 126, 126))))
            .addComponent(jScrollPane4)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(43, 43, 43)
                    .addComponent(jLabel13)
                    .addContainerGap(957, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btn_insertDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(cbo_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txt_precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(stxt_stock, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addComponent(jLabel13)
                    .addContainerGap(584, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("REGISTRAR  ARTICULO", jPanel6);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(90, 90, 90)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1078, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1)))
                .addContainerGap())
        );

        jPanel1.add(jPanel2);

        desktopPane.add(jPanel1);

        menuBar.setBackground(new java.awt.Color(255, 255, 255));
        menuBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        menuBar.setFont(new java.awt.Font("Century Gothic", 2, 18)); // NOI18N
        menuBar.setMaximumSize(new java.awt.Dimension(120, 32767));

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Edit");

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentMenuItem.setMnemonic('c');
        contentMenuItem.setText("Contents");
        helpMenu.add(contentMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void jt_principalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jt_principalMouseClicked
        int col = jt_principal.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jt_principal.getRowHeight();
        
        /*SELECCIONANDO LA FILA DE LA TABLA*/
        txtA_Observacion.setText(""+jt_principal.getValueAt(row,5));
        /*---------------------------------*/
        
    }//GEN-LAST:event_jt_principalMouseClicked

    private void txt_busquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busquedaKeyTyped
        // TODO add your handling code here:
        CargarTabla(consulta.busqueda_por_cada_Letra("inventario", txt_busqueda.getText()),jt_principal);
        for (int i = 0; i < jt_principal.getRowCount(); i++) {
            if(jt_principal.getValueAt(i,1).equals(txt_busqueda.getText())){
                lbl_idProduc.setText(jt_principal.getValueAt(i, 0).toString());
            }
        }
    }//GEN-LAST:event_txt_busquedaKeyTyped

    private void btn_crearBoletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearBoletaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_crearBoletaActionPerformed
     
    private void txt_busquedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busquedaKeyPressed
       if(evt.getExtendedKeyCode() == KeyEvent.VK_CONTROL ){
            control = true;
        }else{
            if(control && evt.getExtendedKeyCode() == KeyEvent.VK_P){
                //JOptionPane.showMessageDialog(null, "Control + p");
                control = false;
                /*se PRECIONO CONTROL + P */
                
                String cantidad = JOptionPane.showInputDialog(null, "Ingrese La cantidad " ,"Mensaje",0);
                //System.out.println(cantidad);
                Factura fact = new Factura();
                for (int i = 0; i < jt_principal.getRowCount(); i++) {
                    if(jt_principal.getValueAt(i, 0).equals(lbl_idProduc.getText())){
                        
                        //GUARDAMOS LA INFORMACION DEL ARTICULO ELEGIDO
                        String codigo = jt_principal.getValueAt(i, 0).toString();
                        String descripcion = jt_principal.getValueAt(i, 1).toString();
                         
                        String cadena_precio = jt_principal.getValueAt(i, 3).toString();
                        String[] partes = cadena_precio.split(" ");
                        String precio = partes[1];
                        System.out.println(partes[1]);
                        int cant = Integer.parseInt(cantidad);
                        String importe =  ""+(cant * Double.parseDouble(precio));
                        //JOptionPane.showMessageDialog(null, "valor "+ jt_principal.getValueAt(i, 0));
                        //LLENAMOS LAS PROPIEDADES DE LA CLASE FACTURA
                        fact.setCodigo(codigo);
                        fact.setDescripcion(descripcion);
                        fact.setPrecio(precio);
                        fact.setCantidad(cant);
                        fact.setImporte(importe);
                        //AGREGAMOS LA FACTURA AL ARRAY LIST  EL ARRAYLIST DE NOMBRE FACTURA
                        factura.add(fact);
                        CargarTablaFactura();
                        
                        
                    }
                }
                
            }
        }
        
        
    }//GEN-LAST:event_txt_busquedaKeyPressed

    private void btn_insertDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertDatosActionPerformed
        //EL CODIGO SE GENERA AUTOMATICAMENTE
        String codigo = txt_codigo.getText();
        ///NO PROBLEM
        String descripcion = txt_descripcion.getText();
        String tipo_Unidad = cbo_unidad.getSelectedItem().toString();
        Double precio = Double.parseDouble(txt_precio.getText());
        int cantidad = Integer.parseInt(stxt_stock.getValue().toString());
        String observacion = txtA_registro_observaciones.getText();
        //VALIDAR LOS DATOS  PORFAVOR 
        System.out.println(observacion);
        boolean inserto = consulta.insertTableInventario(codigo, descripcion, tipo_Unidad, precio, cantidad, observacion);
        
        if(inserto){
            JOptionPane.showMessageDialog(null, "Los datos fueron ingresados correctamente","Mensaje",3);
            CargarTabla(consulta.selectALLTable("inventario"),jt_registro);

        }
        UltimoCodigo();
    }//GEN-LAST:event_btn_insertDatosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton btn_crearBoleta;
    private javax.swing.JButton btn_insertDatos;
    private javax.swing.JComboBox<String> cbo_unidad;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTable jt_factura;
    private javax.swing.JTable jt_principal;
    private javax.swing.JTable jt_registro;
    private javax.swing.JLabel lbl_idProduc;
    private javax.swing.JLabel lbl_valor;
    private javax.swing.JLabel lbl_valorFactura;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JSpinner stxt_stock;
    private javax.swing.JTextArea txtA_Observacion;
    private javax.swing.JTextArea txtA_registro_observaciones;
    private javax.swing.JTextField txt_busqueda;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_descripcion;
    private javax.swing.JTextField txt_precio;
    // End of variables declaration//GEN-END:variables

}
