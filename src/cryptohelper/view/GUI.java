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

import cryptohelper.controller.GUIController;
import cryptohelper.model.Mappatura;
import cryptohelper.model.Proposta;
import cryptohelper.model.SistemaCifratura;
import java.awt.CardLayout;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;

/**
 *
 * @author glaxy
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        //getRootPane().setDefaultButton(loginButton);
        metodoCifraturaComboBox.setSelectedIndex(-1);
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
        personalPanel = new javax.swing.JPanel();
        communicationPanel = new javax.swing.JPanel();
        comunicationTabs = new javax.swing.JTabbedPane();
        messagePanel = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        propostePanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        rifiutaPropostaButton = new javax.swing.JButton();
        accettaPropostaButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        elencoProposteValutare = new javax.swing.JList();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel7 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        sdcPanel = new javax.swing.JPanel();
        sdcTabs = new javax.swing.JTabbedPane();
        elencoSdcPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        elencoSdcList = new javax.swing.JList();
        jPanel14 = new javax.swing.JPanel();
        deleteSdcButton = new javax.swing.JButton();
        nuovoSdcPanel = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        salvaSdcButton = new javax.swing.JButton();
        provaSdcButton = new javax.swing.JButton();
        risultatoProvaField = new javax.swing.JTextField();
        testoProvaField = new javax.swing.JTextField();
        feedbackNuovoSdcLabel = new javax.swing.JLabel();
        chiaveCifraturaField = new javax.swing.JTextField();
        calcolaMappaturaButton = new javax.swing.JButton();
        metodoCifraturaComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CryptoHelper");
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

        nickLoginField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nickLoginFieldActionPerformed(evt);
            }
        });
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

        personalPanel.setLayout(new java.awt.BorderLayout());
        getContentPane().add(personalPanel, "card5");

        communicationPanel.setLayout(new javax.swing.BoxLayout(communicationPanel, javax.swing.BoxLayout.LINE_AXIS));

        comunicationTabs.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        messagePanel.setLayout(new javax.swing.BoxLayout(messagePanel, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 588, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Ricevuti", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 588, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Inviati", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 588, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Bozze", jPanel3);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 588, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Crea Nuovo", jPanel8);

        messagePanel.add(jTabbedPane2);

        comunicationTabs.addTab("Messaggi", messagePanel);

        propostePanel.setLayout(new javax.swing.BoxLayout(propostePanel, javax.swing.BoxLayout.LINE_AXIS));

        jPanel6.setLayout(new java.awt.BorderLayout());

        rifiutaPropostaButton.setText("Rifiuta");
        rifiutaPropostaButton.setEnabled(false);
        rifiutaPropostaButton.setFocusable(false);
        rifiutaPropostaButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rifiutaPropostaButton.setPreferredSize(new java.awt.Dimension(65, 33));
        rifiutaPropostaButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rifiutaPropostaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rifiutaPropostaButtonActionPerformed(evt);
            }
        });
        jPanel13.add(rifiutaPropostaButton);

        accettaPropostaButton.setText("Accetta");
        accettaPropostaButton.setEnabled(false);
        accettaPropostaButton.setFocusable(false);
        accettaPropostaButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        accettaPropostaButton.setPreferredSize(new java.awt.Dimension(65, 33));
        accettaPropostaButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        accettaPropostaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accettaPropostaButtonActionPerformed(evt);
            }
        });
        jPanel13.add(accettaPropostaButton);

        jPanel6.add(jPanel13, java.awt.BorderLayout.PAGE_END);

        elencoProposteValutare.setModel(new javax.swing.DefaultListModel<Proposta>());
        elencoProposteValutare.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        elencoProposteValutare.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                elencoProposteValutareValueChanged(evt);
            }
        });
        elencoProposteValutare.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                elencoProposteValutareAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                elencoProposteValutareAncestorRemoved(evt);
            }
        });
        jScrollPane1.setViewportView(elencoProposteValutare);

        jPanel6.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Ricevute da valutare", jPanel6);

        jPanel9.setLayout(new javax.swing.BoxLayout(jPanel9, javax.swing.BoxLayout.LINE_AXIS));

        jList1.setModel(new javax.swing.DefaultListModel<Proposta>());
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jList1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                jList1AncestorRemoved(evt);
            }
        });
        jScrollPane4.setViewportView(jList1);

        jPanel9.add(jScrollPane4);

        jTabbedPane1.addTab("Inviate e valutate", jPanel9);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jButton1.setText("Invia Proposta");
        jButton1.setEnabled(false);
        jPanel4.add(jButton1);

        jPanel7.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.LINE_AXIS));

        jPanel11.setLayout(new javax.swing.BoxLayout(jPanel11, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Sistemi di cifratura");
        jPanel11.add(jLabel6);

        jScrollPane3.setViewportView(jList2);

        jPanel11.add(jScrollPane3);

        jPanel12.add(jPanel11);

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Studenti");
        jPanel5.add(jLabel5);

        jScrollPane5.setViewportView(jList3);

        jPanel5.add(jScrollPane5);

        jPanel12.add(jPanel5);

        jPanel7.add(jPanel12, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Crea Nuova", jPanel7);

        propostePanel.add(jTabbedPane1);

        comunicationTabs.addTab("Proposte", propostePanel);

        sdcPanel.setLayout(new javax.swing.BoxLayout(sdcPanel, javax.swing.BoxLayout.LINE_AXIS));

        elencoSdcPanel.setLayout(new java.awt.BorderLayout());

        elencoSdcList.setModel(new javax.swing.DefaultListModel<SistemaCifratura>());
        elencoSdcList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        elencoSdcList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                elencoSdcListValueChanged(evt);
            }
        });
        elencoSdcList.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                elencoSdcListAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                elencoSdcListAncestorRemoved(evt);
            }
        });
        jScrollPane2.setViewportView(elencoSdcList);

        elencoSdcPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        deleteSdcButton.setText("Elimina");
        deleteSdcButton.setEnabled(false);
        deleteSdcButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSdcButtonActionPerformed(evt);
            }
        });
        jPanel14.add(deleteSdcButton);

        elencoSdcPanel.add(jPanel14, java.awt.BorderLayout.PAGE_END);

        sdcTabs.addTab("Elenco", elencoSdcPanel);

        nuovoSdcPanel.setLayout(new java.awt.GridBagLayout());

        salvaSdcButton.setText("Salva");
        salvaSdcButton.setEnabled(false);
        salvaSdcButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvaSdcButtonActionPerformed(evt);
            }
        });

        provaSdcButton.setText("Prova");
        provaSdcButton.setEnabled(false);
        provaSdcButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                provaSdcButtonActionPerformed(evt);
            }
        });

        risultatoProvaField.setEditable(false);
        risultatoProvaField.setEnabled(false);

        testoProvaField.setEnabled(false);

        feedbackNuovoSdcLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        feedbackNuovoSdcLabel.setText("Scegli un metodo di cifratura");

        chiaveCifraturaField.setEnabled(false);
        chiaveCifraturaField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                chiaveCifraturaFieldKeyTyped(evt);
            }
        });

        calcolaMappaturaButton.setText("Calcola Mappatura");
        calcolaMappaturaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcolaMappaturaButtonActionPerformed(evt);
            }
        });

        metodoCifraturaComboBox.setModel(new javax.swing.DefaultComboBoxModel(guiController.ottieniMetodiDiCifratura()));
        metodoCifraturaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                metodoCifraturaComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(risultatoProvaField)
            .addComponent(testoProvaField)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(181, 181, 181)
                .addComponent(provaSdcButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(salvaSdcButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(metodoCifraturaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chiaveCifraturaField))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(feedbackNuovoSdcLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(184, Short.MAX_VALUE)
                .addComponent(calcolaMappaturaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(feedbackNuovoSdcLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(metodoCifraturaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chiaveCifraturaField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(calcolaMappaturaButton)
                .addGap(99, 99, 99)
                .addComponent(testoProvaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(risultatoProvaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salvaSdcButton)
                    .addComponent(provaSdcButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        nuovoSdcPanel.add(jPanel10, new java.awt.GridBagConstraints());

        sdcTabs.addTab("Crea Nuovo", nuovoSdcPanel);

        sdcPanel.add(sdcTabs);

        comunicationTabs.addTab("Sistemi Cifratura", sdcPanel);

        communicationPanel.add(comunicationTabs);

        getContentPane().add(communicationPanel, "card6");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        try {
            if (guiController.login(nickLoginField.getText(), String.valueOf(passLoginField.getPassword()))) {
                CardLayout cl = (CardLayout) getContentPane().getLayout();
                cl.show(getContentPane(), "card6");
            } else {
                errorLoginLabel.setText("nickname o password errati");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    private void goToRegistrationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToRegistrationButtonActionPerformed
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "card4");
    }//GEN-LAST:event_goToRegistrationButtonActionPerformed

    private void nickLoginFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nickLoginFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nickLoginFieldActionPerformed

    private void metodoCifraturaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_metodoCifraturaComboBoxActionPerformed
        JComboBox cb = (JComboBox) evt.getSource();
        String metodo = (String) cb.getSelectedItem();
        if (metodo != null) {
            chiaveCifraturaField.setEnabled(true);
            feedbackNuovoSdcLabel.setText(guiController.mostraSceltaChiave(metodo));
            setSdcWidgetEnabled(false);
        }
    }//GEN-LAST:event_metodoCifraturaComboBoxActionPerformed

    private void calcolaMappaturaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcolaMappaturaButtonActionPerformed
        String metodo = (String) metodoCifraturaComboBox.getSelectedItem();
        try {
            Mappatura map = guiController.generaMappatura(chiaveCifraturaField.getText(), metodo);
            /* bisogna pensare a come far visualizzare la mappatura */
            System.out.println(map);
            setSdcWidgetEnabled(true);
        } catch (IllegalArgumentException ex) {
            feedbackNuovoSdcLabel.setText(ex.getMessage());
        }
    }//GEN-LAST:event_calcolaMappaturaButtonActionPerformed

    private void chiaveCifraturaFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chiaveCifraturaFieldKeyTyped
        setSdcWidgetEnabled(false);
    }//GEN-LAST:event_chiaveCifraturaFieldKeyTyped

    private void provaSdcButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_provaSdcButtonActionPerformed
        risultatoProvaField.setText(guiController.cifra(testoProvaField.getText()));
    }//GEN-LAST:event_provaSdcButtonActionPerformed

    private void salvaSdcButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvaSdcButtonActionPerformed
        try {
            guiController.salvaSistemaCifratura();
            setSdcWidgetEnabled(false);
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_salvaSdcButtonActionPerformed

    private void deleteSdcButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSdcButtonActionPerformed
        try {
            SistemaCifratura sdc = (SistemaCifratura) elencoSdcList.getSelectedValue();
            if (guiController.eliminaSistemaCifratura(sdc)) {
                DefaultListModel<SistemaCifratura> dlm = (DefaultListModel<SistemaCifratura>) elencoSdcList.getModel();
                dlm.removeElement(sdc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_deleteSdcButtonActionPerformed

    private void elencoSdcListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_elencoSdcListValueChanged
        if (evt.getValueIsAdjusting() == false) {
            if (elencoSdcList.getSelectedIndex() == -1) {
                deleteSdcButton.setEnabled(false);
            } else {
                deleteSdcButton.setEnabled(true);
            }
        }
    }//GEN-LAST:event_elencoSdcListValueChanged

    private void jList1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jList1AncestorAdded
        DefaultListModel<Proposta> dlm = (DefaultListModel<Proposta>) jList1.getModel();
        List<Proposta> listaProposteValutate = null;
        try {
            listaProposteValutate = guiController.vediNotificheAccettazioneProposte();
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Proposta p : listaProposteValutate) {
            dlm.addElement(p);
        }
    }//GEN-LAST:event_jList1AncestorAdded

    private void elencoProposteValutareAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_elencoProposteValutareAncestorAdded
        DefaultListModel<Proposta> dlm = (DefaultListModel<Proposta>) elencoProposteValutare.getModel();
        List<Proposta> listaProposte = null;
        try {
            listaProposte = guiController.vediProposteSistemaCifratura();
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Proposta p : listaProposte) {
            dlm.addElement(p);
        }
    }//GEN-LAST:event_elencoProposteValutareAncestorAdded

    private void elencoProposteValutareValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_elencoProposteValutareValueChanged
        if (evt.getValueIsAdjusting() == false) {
            if (elencoProposteValutare.getSelectedIndex() == -1) {
                accettaPropostaButton.setEnabled(false);
                rifiutaPropostaButton.setEnabled(false);
            } else {
                accettaPropostaButton.setEnabled(true);
                rifiutaPropostaButton.setEnabled(true);
            }
        }
    }//GEN-LAST:event_elencoProposteValutareValueChanged

    private void accettaPropostaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accettaPropostaButtonActionPerformed
        try {
            Proposta proposta = (Proposta) elencoProposteValutare.getSelectedValue();
            if (guiController.comunicaDecisione(proposta, "accepted")) {
                DefaultListModel<Proposta> dlm = (DefaultListModel<Proposta>) elencoProposteValutare.getModel();
                dlm.removeElement(proposta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_accettaPropostaButtonActionPerformed

    private void rifiutaPropostaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rifiutaPropostaButtonActionPerformed
        try {
            Proposta proposta = (Proposta) elencoProposteValutare.getSelectedValue();
            if (guiController.comunicaDecisione(proposta, "refused")) {
                DefaultListModel<Proposta> dlm = (DefaultListModel<Proposta>) elencoProposteValutare.getModel();
                dlm.removeElement(proposta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_rifiutaPropostaButtonActionPerformed

    private void jList1AncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jList1AncestorRemoved
        DefaultListModel<Proposta> dlm = (DefaultListModel<Proposta>) jList1.getModel();
        dlm.clear();
    }//GEN-LAST:event_jList1AncestorRemoved

    private void elencoProposteValutareAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_elencoProposteValutareAncestorRemoved
        DefaultListModel<Proposta> dlm = (DefaultListModel<Proposta>) elencoProposteValutare.getModel();
        dlm.clear();
    }//GEN-LAST:event_elencoProposteValutareAncestorRemoved

    private void elencoSdcListAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_elencoSdcListAncestorAdded
        DefaultListModel<SistemaCifratura> dlm = (DefaultListModel<SistemaCifratura>) elencoSdcList.getModel();
        List<SistemaCifratura> listasdc = null;
        try {
            listasdc = guiController.elencaSistemiCifratura();
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (SistemaCifratura sdc : listasdc) {
            dlm.addElement(sdc);
        }
    }//GEN-LAST:event_elencoSdcListAncestorAdded

    private void elencoSdcListAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_elencoSdcListAncestorRemoved
        DefaultListModel<SistemaCifratura> dlm = (DefaultListModel<SistemaCifratura>) elencoSdcList.getModel();
        dlm.clear();
    }//GEN-LAST:event_elencoSdcListAncestorRemoved

    private void setSdcWidgetEnabled(boolean b) {
        provaSdcButton.setEnabled(b);
        salvaSdcButton.setEnabled(b);
        testoProvaField.setEnabled(b);
        risultatoProvaField.setEnabled(b);
    }

    private final GUIController guiController = GUIController.getInstance();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton accettaPropostaButton;
    javax.swing.JButton calcolaMappaturaButton;
    javax.swing.JTextField chiaveCifraturaField;
    javax.swing.JPanel communicationPanel;
    javax.swing.JTabbedPane comunicationTabs;
    javax.swing.JButton deleteSdcButton;
    javax.swing.JList elencoProposteValutare;
    javax.swing.JList elencoSdcList;
    javax.swing.JPanel elencoSdcPanel;
    javax.swing.JLabel errorLoginLabel;
    javax.swing.JLabel feedbackNuovoSdcLabel;
    javax.swing.JButton goToRegistrationButton;
    javax.swing.JButton jButton1;
    javax.swing.JButton jButton2;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel4;
    javax.swing.JLabel jLabel5;
    javax.swing.JLabel jLabel6;
    javax.swing.JList jList1;
    javax.swing.JList jList2;
    javax.swing.JList jList3;
    javax.swing.JPanel jPanel1;
    javax.swing.JPanel jPanel10;
    javax.swing.JPanel jPanel11;
    javax.swing.JPanel jPanel12;
    javax.swing.JPanel jPanel13;
    javax.swing.JPanel jPanel14;
    javax.swing.JPanel jPanel2;
    javax.swing.JPanel jPanel3;
    javax.swing.JPanel jPanel4;
    javax.swing.JPanel jPanel5;
    javax.swing.JPanel jPanel6;
    javax.swing.JPanel jPanel7;
    javax.swing.JPanel jPanel8;
    javax.swing.JPanel jPanel9;
    javax.swing.JPasswordField jPasswordField1;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JScrollPane jScrollPane2;
    javax.swing.JScrollPane jScrollPane3;
    javax.swing.JScrollPane jScrollPane4;
    javax.swing.JScrollPane jScrollPane5;
    javax.swing.JTabbedPane jTabbedPane1;
    javax.swing.JTabbedPane jTabbedPane2;
    javax.swing.JTextField jTextField1;
    javax.swing.JTextField jTextField2;
    javax.swing.JTextField jTextField3;
    javax.swing.JButton loginButton;
    javax.swing.JPanel loginFormPanel;
    javax.swing.JPanel loginPanel;
    javax.swing.JToolBar loginToolBar;
    javax.swing.JPanel messagePanel;
    javax.swing.JComboBox metodoCifraturaComboBox;
    javax.swing.JTextField nickLoginField;
    javax.swing.JPanel nuovoSdcPanel;
    javax.swing.JPasswordField passLoginField;
    javax.swing.JPanel personalPanel;
    javax.swing.JPanel propostePanel;
    javax.swing.JButton provaSdcButton;
    javax.swing.JPanel registrationPanel;
    javax.swing.JButton rifiutaPropostaButton;
    javax.swing.JTextField risultatoProvaField;
    javax.swing.JButton salvaSdcButton;
    javax.swing.JPanel sdcPanel;
    javax.swing.JTabbedPane sdcTabs;
    javax.swing.JTextField testoProvaField;
    // End of variables declaration//GEN-END:variables
}
