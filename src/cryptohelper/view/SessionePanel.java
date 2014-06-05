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
import cryptohelper.model.MessaggioSpia;
import cryptohelper.model.Sessione;
import cryptohelper.model.Studente;
import java.awt.Component;
import java.io.IOException;
import java.sql.SQLException;
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
        continuaSessioneButton = new javax.swing.JButton();
        eliminaSessioneButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
        iniziaSessioneButton = new javax.swing.JButton();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jList1.setModel(new javax.swing.DefaultListModel<Sessione>());
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jList1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jList1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                jList1AncestorRemoved(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(jList1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        continuaSessioneButton.setText("Continua");
        continuaSessioneButton.setEnabled(false);
        continuaSessioneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continuaSessioneButtonActionPerformed(evt);
            }
        });
        jPanel3.add(continuaSessioneButton);

        eliminaSessioneButton.setText("Elimina");
        eliminaSessioneButton.setEnabled(false);
        eliminaSessioneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminaSessioneButtonActionPerformed(evt);
            }
        });
        jPanel3.add(eliminaSessioneButton);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Gestisci", jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jList2.setModel(new javax.swing.DefaultListModel<MessaggioSpia>());
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jList2.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jList2AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                jList2AncestorRemoved(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane2.setViewportView(jList2);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        iniziaSessioneButton.setText("Inizia Sessione");
        iniziaSessioneButton.setEnabled(false);
        iniziaSessioneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniziaSessioneButtonActionPerformed(evt);
            }
        });
        jPanel4.add(iniziaSessioneButton);

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
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jList1AncestorAdded

    private void jList2AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jList2AncestorAdded
        try {
            DefaultListModel<MessaggioSpia> dlm = (DefaultListModel<MessaggioSpia>) jList2.getModel();
            List<MessaggioSpia> listaMessaggi = sessController.sniffMessaggi(studente.getUserInfo());
            for (MessaggioSpia s : listaMessaggi) {
                dlm.addElement(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jList2AncestorAdded

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        if (evt.getValueIsAdjusting() == false) {
            if (jList1.getSelectedIndex() == -1) {
                eliminaSessioneButton.setEnabled(false);
                continuaSessioneButton.setEnabled(false);
            } else {
                eliminaSessioneButton.setEnabled(true);
                continuaSessioneButton.setEnabled(true);
            }
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        if (evt.getValueIsAdjusting() == false) {
            if (jList2.getSelectedIndex() == -1) {
                iniziaSessioneButton.setEnabled(false);
            } else {
                iniziaSessioneButton.setEnabled(true);
            }
        }
    }//GEN-LAST:event_jList2ValueChanged

    private void jList2AncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jList2AncestorRemoved
        DefaultListModel<Sessione> dlm = (DefaultListModel<Sessione>) jList2.getModel();
        dlm.clear();
    }//GEN-LAST:event_jList2AncestorRemoved

    private void jList1AncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jList1AncestorRemoved
        DefaultListModel<Sessione> dlm = (DefaultListModel<Sessione>) jList1.getModel();
        dlm.clear();
    }//GEN-LAST:event_jList1AncestorRemoved

    private void iniziaSessioneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniziaSessioneButtonActionPerformed
        try {
            MessaggioSpia messaggio = (MessaggioSpia) jList2.getSelectedValue();
            Sessione nuovaSessione = sessController.creaSessione(studente.getUserInfo(), messaggio);
            sessController.salvaSessione(nuovaSessione);
            creaNuovaSessionePanel(nuovaSessione);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(SessionePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_iniziaSessioneButtonActionPerformed

    private void eliminaSessioneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminaSessioneButtonActionPerformed
        try {
            Sessione daEliminare = (Sessione) jList1.getSelectedValue();
            if (daEliminare.elimina()) {
                DefaultListModel<Sessione> dlm = (DefaultListModel<Sessione>) jList1.getModel();
                dlm.removeElement(daEliminare);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_eliminaSessioneButtonActionPerformed

    private void continuaSessioneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continuaSessioneButtonActionPerformed
        Sessione daContinuare = (Sessione) jList1.getSelectedValue();
        creaNuovaSessionePanel(daContinuare);
    }//GEN-LAST:event_continuaSessioneButtonActionPerformed

    private void creaNuovaSessionePanel(Sessione sessione) {
        SessioneApertaPanel nuovaSessionePanel = new SessioneApertaPanel((sessione));
        jTabbedPane1.addTab("sessione", nuovaSessionePanel);
        int newIndex = jTabbedPane1.indexOfComponent(nuovaSessionePanel);
        jTabbedPane1.setTabComponentAt(newIndex, new ButtonTabComponent(jTabbedPane1));
        jTabbedPane1.setSelectedIndex(newIndex);
    }

    private Studente studente = null;
    private SessionController sessController = SessionController.getInstance();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton continuaSessioneButton;
    private javax.swing.JButton eliminaSessioneButton;
    private javax.swing.JButton iniziaSessioneButton;
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
