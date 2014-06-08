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

import cryptohelper.model.Studente;
import java.awt.CardLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author glaxy
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form newGUI
     */
    public GUI() {
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
        java.awt.GridBagConstraints gridBagConstraints;

        loginPanel = new javax.swing.JPanel();
        loginToolBar = new javax.swing.JToolBar();
        goToRegistrationButton = new javax.swing.JButton();
        loginFormPanel = new javax.swing.JPanel();
        nickLoginField = new javax.swing.JTextField();
        passLoginField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        errorLoginLabel = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel5 = new javax.swing.JLabel();
        registrationPanel = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        communicationPanel = new javax.swing.JPanel();
        comunicationTabs = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CryptoHelper");
        setPreferredSize(new java.awt.Dimension(1024, 650));
        getContentPane().setLayout(new java.awt.CardLayout());

        loginPanel.setLayout(new java.awt.BorderLayout());

        loginToolBar.setFloatable(false);
        loginToolBar.setRollover(true);

        goToRegistrationButton.setText("Registrazione");
        goToRegistrationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToRegistrationButtonActionPerformed(evt);
            }
        });
        loginToolBar.add(goToRegistrationButton);

        loginPanel.add(loginToolBar, java.awt.BorderLayout.PAGE_START);

        loginFormPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 150;
        loginFormPanel.add(nickLoginField, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 150;
        loginFormPanel.add(passLoginField, gridBagConstraints);

        loginButton.setText("Login");
        loginButton.setFocusTraversalPolicyProvider(true);
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 150;
        loginFormPanel.add(loginButton, gridBagConstraints);

        errorLoginLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        loginFormPanel.add(errorLoginLabel, gridBagConstraints);

        loginPanel.add(loginFormPanel, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);
        jToolBar1.add(jLabel5);

        loginPanel.add(jToolBar1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(loginPanel, "card6");

        registrationPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 150;
        registrationPanel.add(jTextField1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 150;
        registrationPanel.add(jTextField2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 150;
        registrationPanel.add(jTextField3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 150;
        registrationPanel.add(jPasswordField1, gridBagConstraints);

        jLabel1.setText("Nome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        registrationPanel.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Cognome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        registrationPanel.add(jLabel2, gridBagConstraints);

        jLabel3.setText("Nickname");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        registrationPanel.add(jLabel3, gridBagConstraints);

        jLabel4.setText("Password");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        registrationPanel.add(jLabel4, gridBagConstraints);

        jButton2.setText("Registrami!");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        registrationPanel.add(jButton2, gridBagConstraints);

        getContentPane().add(registrationPanel, "card4");

        communicationPanel.setLayout(new javax.swing.BoxLayout(communicationPanel, javax.swing.BoxLayout.LINE_AXIS));

        comunicationTabs.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        communicationPanel.add(comunicationTabs);

        getContentPane().add(communicationPanel, "card6");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void goToRegistrationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToRegistrationButtonActionPerformed
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "card4");
    }//GEN-LAST:event_goToRegistrationButtonActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        try {
            studente = Studente.login(nickLoginField.getText(), String.valueOf(passLoginField.getPassword()));
            if (studente != null) {
                CardLayout cl = (CardLayout) getContentPane().getLayout();
                comunicationTabs.addTab("Messaggi", new MessagePanel(studente));
                comunicationTabs.addTab("Proposte", new PropostePanel(studente));
                comunicationTabs.addTab("Sistemi Cifratura", new SdcPanel(studente));
                comunicationTabs.addTab("Sessioni", new SessionePanel(studente));
                comunicationTabs.addTab("Soluzioni", new SoluzioniPanel(studente));
                cl.show(getContentPane(), "card6");
            } else {
                errorLoginLabel.setText("nickname o password errati");
            }
        } catch (SQLException ex) {
            jLabel5.setText("Errore connessione al database");
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    private Studente studente = null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel communicationPanel;
    private javax.swing.JTabbedPane comunicationTabs;
    private javax.swing.JLabel errorLoginLabel;
    private javax.swing.JButton goToRegistrationButton;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPanel loginFormPanel;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JToolBar loginToolBar;
    private javax.swing.JTextField nickLoginField;
    private javax.swing.JPasswordField passLoginField;
    private javax.swing.JPanel registrationPanel;
    // End of variables declaration//GEN-END:variables
}
