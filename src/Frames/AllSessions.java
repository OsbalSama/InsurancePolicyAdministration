/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import Classes.Usuarios;
import Classes.Sesiones;
import Classes.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author terra
 */
public class AllSessions extends javax.swing.JFrame {

    /**
     * Creates new form AllSessions
     */
    DefaultTableModel modelo;

    public AllSessions() {
        initComponents();
        tabla_sesiones.setRowHeight(30);
        btn_cerrar.requestFocus();
        this.setTitle("Registro de Sesiones");
        setIconImage(new ConfigDat().getImageIcon().getImage());
        this.setLocationRelativeTo(null);
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas < 0) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        modelo.addColumn("Identificador");
        modelo.addColumn("Cuenta");
        modelo.addColumn("Fecha inicio");
        modelo.addColumn("Hora Entrada");
        modelo.addColumn("Hora Salida");
        modelo.addColumn("N. Logueos");
        tabla_sesiones.setModel(modelo);
        cargar_usuarios();
    }

    public void AplicarFiltros() {
        String filtro = "";
        int select = ComboFechas.getSelectedIndex();
        switch (select) {
            case 0:
                if (ComboCuentas.getItemCount() > 0 && ComboFechas.getItemCount() > 0) {
                    mostrar_Sessions_Hoy();
                }
                break;
            case 1:
                if (ComboCuentas.getItemCount() > 0 && ComboFechas.getItemCount() > 0) {
                    mostrar_Sessions_Semana();
                }
                break;
            case 2:
                if (ComboCuentas.getItemCount() > 0 && ComboFechas.getItemCount() > 0) {
                    mostrar_Sessions_Mes();
                }
                
                break;
            case 3:
                if (ComboCuentas.getItemCount() > 0 && ComboFechas.getItemCount() > 0) {
                    mostrar_sesiones();
                }                
                break;
        }
    }

    public void mostrar_Sessions_Mes() {
        modelo.setRowCount(0);
        String datos[] = new Sesiones().getDBSesiones();
        try {
            if (datos != null) {
                if (ComboCuentas.getSelectedIndex() == 0) {
                    java.util.List fechas = getDaysOfMont();
                    for (int i = 0; i < datos.length; i++) {
                        String[] venta = datos[i].split(",");
                        for (int j = 0; j < fechas.size(); j++) {
                            if (venta[2].equals(fechas.get(j))) {
                                modelo.addRow(datos[i].split(","));
                            }
                        }
                    }
                } else {
                    java.util.List fechas = getDaysOfMont();
                    for (int i = 0; i < datos.length; i++) {
                        String[] venta = datos[i].split(",");
                        for (int j = 0; j < fechas.size(); j++) {
                            if (venta[2].equals(fechas.get(j)) && venta[1].equals(ComboCuentas.getSelectedItem().toString())) {
                                modelo.addRow(datos[i].split(","));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public java.util.List getDaysOfMont() {
        java.util.List<String> lista = new ArrayList<>();
        try {
            Calendar c = new GregorianCalendar(new Date().getYear(), new Date().getMonth(), new Date().getDate());
            int dia_mes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 0; i < dia_mes; i++) {
                if (i < new Date().getDate()) {
                    lista.add(new DecimalFormat("00").format(i + 1) + "-" + new DecimalFormat("00").format(new Date().getMonth() + 1) + "-" + (1900 + new Date().getYear()));
                }
            }
            return lista;
        } catch (Exception e) {
            return null;
        }
    }

    public void mostrar_Sessions_Semana() {
        try {
            modelo.setRowCount(0);
            String datos[] = new Sesiones().getDBSesiones();
            if (datos != null) {
                if (ComboCuentas.getSelectedIndex() == 0) {
                    java.util.List fechas = getDaysOfWeek();
                    for (int i = 0; i < datos.length; i++) {
                        String[] venta = datos[i].split(",");
                        for (int j = 0; j < fechas.size(); j++) {
                            if (venta[2].equals(fechas.get(j))) {
                                modelo.addRow(datos[i].split(","));
                            }
                        }
                    }
                } else {
                    java.util.List fechas = getDaysOfWeek();
                    for (int i = 0; i < datos.length; i++) {
                        String[] venta = datos[i].split(",");
                        for (int j = 0; j < fechas.size(); j++) {
                            if (venta[2].equals(fechas.get(j)) && venta[1].equals(ComboCuentas.getSelectedItem().toString())) {
                                modelo.addRow(datos[i].split(","));
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public java.util.List getDaysOfWeek() {
        java.util.List<String> lista = new ArrayList<>();
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            int mondayNo = c.get(Calendar.DAY_OF_MONTH) - c.get(Calendar.DAY_OF_WEEK) + 1;
            c.set(Calendar.DAY_OF_MONTH, mondayNo);
            int dom = c.getTime().getDate();
            int sab = dom + 7;
            for (int i = dom; i < sab; i++) {
                if (i < new Date().getDate()) {
                    lista.add(new DecimalFormat("00").format(i + 1) + "-" + new DecimalFormat("00").format(new Date().getMonth() + 1) + "-" + (1900 + new Date().getYear()));
                }
            }
            return lista;
        } catch (Exception e) {
            return null;
        }
    }

    public void mostrar_Sessions_Hoy() {
        modelo.setRowCount(0);
        String datos[] = new Sesiones().getDBSesiones();
        try {
            if (datos != null) {
                if (ComboCuentas.getSelectedIndex() == 0) {
                    for (int i = 0; i < datos.length; i++) {
                        String[] venta = datos[i].split(",");
                        if (venta[2].equals(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))) {
                            modelo.addRow(datos[i].split(","));
                        }
                    }
                } else {
                    for (int i = 0; i < datos.length; i++) {
                        String[] venta = datos[i].split(",");
                        if (venta[2].equals(new SimpleDateFormat("dd-MM-yyyy").format(new Date())) && venta[1].equals(ComboCuentas.getSelectedItem().toString())) {
                            modelo.addRow(datos[i].split(","));
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrar_sesiones() {
        modelo.setRowCount(0);
        String datos[] = new Sesiones().getDBSesiones();
        try {
            if (datos != null) {
                if (ComboCuentas.getSelectedIndex() == 0) {
                    for (int i = 0; i < datos.length; i++) {
                        modelo.addRow(datos[i].split(","));
                    }
                } else {
                    for (int i = 0; i < datos.length; i++) {
                        String[] venta = datos[i].split(",");
                        if (venta[1].equals(ComboCuentas.getSelectedItem().toString())) {
                            modelo.addRow(datos[i].split(","));
                        }                        
                    }
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargar_usuarios() {
        ComboCuentas.removeAllItems();
        ComboCuentas.addItem("Todas las Cuentas");
        String[] database = new Usuarios().getDBUsers();
        for (int i = 0; i < database.length; i++) {
            String[] cuenta = database[i].split(",");
            if (!cuenta[1].equals("root")) {
                ComboCuentas.addItem(cuenta[1]);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_sesiones = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btn_cerrar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        ComboFechas = new javax.swing.JComboBox<>();
        ComboCuentas = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mostrar todas las sesiones iniciadas");

        tabla_sesiones.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tabla_sesiones.getTableHeader().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        tabla_sesiones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabla_sesiones.setRowHeight(19);
        tabla_sesiones.setSelectionBackground(new java.awt.Color(255, 153, 51));
        jScrollPane1.setViewportView(tabla_sesiones);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("SESIONES INICIADAS");

        btn_cerrar.setText("CERRAR");
        btn_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar por"));

        jLabel4.setText("Fecha");

        ComboFechas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Logueos de hoy", "Logueos de la semana", "Logueos del mes", "Todos los logueos" }));
        ComboFechas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboFechasActionPerformed(evt);
            }
        });

        ComboCuentas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas las Cuentas", "CARGAR MAS CUENTAS" }));
        ComboCuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboCuentasActionPerformed(evt);
            }
        });

        jLabel3.setText("Cuenta");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ComboCuentas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ComboFechas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboFechas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboCuentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_cerrar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cerrar))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_cerrarActionPerformed

    private void ComboFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboFechasActionPerformed
        AplicarFiltros();
    }//GEN-LAST:event_ComboFechasActionPerformed

    private void ComboCuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboCuentasActionPerformed
        AplicarFiltros();
    }//GEN-LAST:event_ComboCuentasActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AllSessions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AllSessions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AllSessions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AllSessions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AllSessions().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboCuentas;
    private javax.swing.JComboBox<String> ComboFechas;
    private javax.swing.JButton btn_cerrar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_sesiones;
    // End of variables declaration//GEN-END:variables
}
