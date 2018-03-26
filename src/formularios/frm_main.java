
package formularios;

import clases.Consultas_Preparadas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

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
    
    DefaultTableModel model = new DefaultTableModel();
         
    
    public frm_main() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        txtA_Observacion.setLineWrap(true);
        
        CargarTabla();
        
    }
    
    private void CargarTabla(){
        ResultSet rs ;
        
        jt_principal.setModel(model);
        /*AGREGAMOS LAS COLUMNAS DE  LA TABLA*/
        model.addColumn("CODIGO_PRODUCTO");
        model.addColumn("DESCRIPCION");
        model.addColumn("UNIDAD DE COMPRA");
        model.addColumn("PRECIO");
        model.addColumn("STOCK");
        model.addColumn("OBSERVACIONES");
        /*-----------------------------------*/
        /*AGREGAMOS EL ANCHO DE CADA COLUMNA*/
        jt_principal.getColumnModel().getColumn(0).setMaxWidth(150);
        jt_principal.getColumnModel().getColumn(1).setMaxWidth(300);
        jt_principal.getColumnModel().getColumn(2).setMaxWidth(150);
        jt_principal.getColumnModel().getColumn(3).setMaxWidth(150);
        jt_principal.getColumnModel().getColumn(4).setMaxWidth(100);
        jt_principal.getColumnModel().getColumn(5).setMaxWidth(200);
        
        /*consultamos la db*/
        rs = consulta.selectALLTable("inventario");
        /*-----------------*/
        String tipo = "";
        try {
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add("S/ "+rs.getDouble(5));
                switch(rs.getString(4)){
                    case "PAQUETE":
                        tipo = "PQT";
                        break;
                }
                v.add(rs.getInt(6)+" "+tipo);
                v.add(rs.getString(7));
                model.addRow(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(frm_main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jt_principal = new javax.swing.JTable();
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
        jTextField1 = new javax.swing.JTextField();
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
        ));
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

        jTextField1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 269, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(91, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
            .addComponent(desktopPane)
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
        
    }//GEN-LAST:event_jt_principalMouseClicked

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
                if ("windows".equals(info.getName())) {
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable jt_principal;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JTextArea txtA_Observacion;
    // End of variables declaration//GEN-END:variables

}
