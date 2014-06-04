/*
 * Copyright (C) 2014 glaxy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cryptohelper.view;

import cryptohelper.controller.SessionController;
import cryptohelper.model.Sessione;
import cryptohelper.model.Studente;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author glaxy
 */
public class SessionePanel extends javax.swing.JPanel {

    /**
     * Creates new form SessionePanel
     *
     * @param st
     */
    public SessionePanel(Studente st) {
        studente = st;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jList1.setModel(new javax.swing.DefaultListModel<Sessione>());
        jList1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jList1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(jList1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButton1.setText("jButton1");
        jPanel3.add(jButton1);

        jButton2.setText("jButton2");
        jPanel3.add(jButton2);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Gestisci", jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jList2.setModel(new javax.swing.DefaultListModel<MessaggioSpia>());
        jScrollPane2.setViewportView(jList2);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jButton3.setText("jButton3");
        jPanel4.add(jButton3);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Nuova", jPanel2);

        add(jTabbedPane1);
    }// </editor-fold>//GEN-END:initComponents

    private void jList1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jList1AncestorAdded
        try {
            DefaultListModel<Sessione> dlm = (DefaultListModel<Sessione>) jList1.getModel();
            List<Sessione> listaSessioni = sessController.mostraSessioni(studente.getUserInfo());
            for (Sessione s : listaSessioni) {
                dlm.addElement(s);
            }
        } catch (Exception ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jList1AncestorAdded

    private Studente studente = null;
    private SessionController sessController = SessionController.getInstance();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
