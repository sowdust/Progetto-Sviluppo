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
import cryptohelper.model.AnalisiFrequenze;
import cryptohelper.model.Cifratore;
import cryptohelper.model.Mappatura;
import cryptohelper.model.MessaggioSpia;
import cryptohelper.model.Sessione;
import cryptohelper.model.UserInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author glaxy
 */
public class SessioneApertaPanel extends javax.swing.JPanel {

    /**
     * Creates new form SessioneAperta
     *
     * @param s la sessione aperta
     */
    public SessioneApertaPanel(Sessione s) {
        sessione = s;
        messaggio = sessione.getMessaggio();
        proprietario = sessione.getProprietario();
        initComponents();
        initMyComponents();
        provaMappaturaCorrente();
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        faiAssunzioniTestButton = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        undoButtonTest = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        undoButton = new javax.swing.JButton();
        faiAssunzioneButton = new javax.swing.JButton();
        caricaSoluzioneButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        try {
            jTable1 = new javax.swing.JTable(getFrequencyData(AnalisiFrequenze.getFrequency(messaggio)), new String[] {"Carattere", "Frequenza"});
            jPanel13 = new javax.swing.JPanel();
            jLabel2 = new javax.swing.JLabel();
            jScrollPane5 = new javax.swing.JScrollPane();
            jTable2 = new javax.swing.JTable(getFrequencyData(AnalisiFrequenze.getFrequency(messaggio.getLingua())), new String[] {"Carattere", "Frequenza"});
            jPanel6 = new javax.swing.JPanel();
            jPanel10 = new javax.swing.JPanel();
            cMittenteLabel = new javax.swing.JLabel();
            cDestinatarioLabel = new javax.swing.JLabel();
            cTitoloLabel = new javax.swing.JLabel();
            cLinguaLabel = new javax.swing.JLabel();
            mittenteLabel = new javax.swing.JLabel();
            titoloLabel = new javax.swing.JLabel();
            destinatarioLabel = new javax.swing.JLabel();
            linguaLabel = new javax.swing.JLabel();
            jPanel5 = new javax.swing.JPanel();
            salvaSessioneButton = new javax.swing.JButton();
            salvaSoluzioneButton = new javax.swing.JButton();
            feedbackSessione = new javax.swing.JLabel();

            setLayout(new java.awt.BorderLayout());

            jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

            jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

            jTextArea1.setColumns(150);
            jTextArea1.setLineWrap(true);
            jTextArea1.setRows(5);
            jScrollPane1.setViewportView(jTextArea1);

            jPanel2.add(jScrollPane1);

            jTextArea2.setColumns(150);
            jTextArea2.setLineWrap(true);
            jTextArea2.setRows(5);
            jScrollPane2.setViewportView(jTextArea2);

            jPanel2.add(jScrollPane2);

            jPanel1.add(jPanel2);

            faiAssunzioniTestButton.setText("Fai Assunzioni");
            faiAssunzioniTestButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    faiAssunzioniTestButtonActionPerformed(evt);
                }
            });

            undoButtonTest.setText("Annulla...");
            undoButtonTest.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    undoButtonTestActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(60, 60, 60)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(undoButtonTest)
                            .addGap(85, 85, 85)
                            .addComponent(faiAssunzioniTestButton))
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(738, Short.MAX_VALUE))
            );
            jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(faiAssunzioniTestButton)
                        .addComponent(undoButtonTest))
                    .addContainerGap(459, Short.MAX_VALUE))
            );

            jTabbedPane1.addTab("Assunzioni", jPanel3);

            jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.PAGE_AXIS));

            jPanel8.setLayout(new java.awt.GridLayout(13, 4));
            jScrollPane4.setViewportView(jPanel8);

            jPanel7.add(jScrollPane4);

            undoButton.setText("Annulla...");
            undoButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    undoButtonActionPerformed(evt);
                }
            });
            jPanel9.add(undoButton);

            faiAssunzioneButton.setText("Fai Assunzioni");
            faiAssunzioneButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    faiAssunzioneButtonActionPerformed(evt);
                }
            });
            jPanel9.add(faiAssunzioneButton);

            caricaSoluzioneButton.setText("Carica Soluzione");
            caricaSoluzioneButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    caricaSoluzioneButtonActionPerformed(evt);
                }
            });
            jPanel9.add(caricaSoluzioneButton);

            jPanel7.add(jPanel9);

            jTabbedPane1.addTab("Assunzioni Test", jPanel7);

            jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

            jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.PAGE_AXIS));

            jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel1.setText("Frequenze messaggio");
            jPanel12.add(jLabel1);

        }catch(SQLException e) {

        }
        jTable1.setEnabled(false);
        jScrollPane3.setViewportView(jTable1);

        jPanel12.add(jScrollPane3);

        jPanel4.add(jPanel12);

        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Frequenze lingua");
        jPanel13.add(jLabel2);

        jTable2.setEnabled(false);
        jScrollPane5.setViewportView(jTable2);

        jPanel13.add(jScrollPane5);

        jPanel4.add(jPanel13);

        jTabbedPane1.addTab("Frequenze", jPanel4);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        cMittenteLabel.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N
        cMittenteLabel.setText("Mittente:");

        cDestinatarioLabel.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N
        cDestinatarioLabel.setText("Destinatario:");

        cTitoloLabel.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N
        cTitoloLabel.setText("Titolo:");

        cLinguaLabel.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N
        cLinguaLabel.setText("Lingua:");

        mittenteLabel.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N

        titoloLabel.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N

        destinatarioLabel.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N

        linguaLabel.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cLinguaLabel, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cDestinatarioLabel))
                    .addComponent(cTitoloLabel)
                    .addComponent(cMittenteLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mittenteLabel)
                    .addComponent(titoloLabel)
                    .addComponent(destinatarioLabel)
                    .addComponent(linguaLabel))
                .addContainerGap(193, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cMittenteLabel)
                    .addComponent(mittenteLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cDestinatarioLabel)
                    .addComponent(destinatarioLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cTitoloLabel)
                    .addComponent(titoloLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cLinguaLabel)
                    .addComponent(linguaLabel))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel10, new java.awt.GridBagConstraints());

        jTabbedPane1.addTab("Informazioni", jPanel6);

        jPanel1.add(jTabbedPane1);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        salvaSessioneButton.setText("Salva Sessione");
        salvaSessioneButton.setEnabled(false);
        salvaSessioneButton.setFocusable(false);
        salvaSessioneButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        salvaSessioneButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        salvaSessioneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvaSessioneButtonActionPerformed(evt);
            }
        });
        jPanel5.add(salvaSessioneButton, new java.awt.GridBagConstraints());

        salvaSoluzioneButton.setText("Salva Soluzione");
        salvaSoluzioneButton.setFocusable(false);
        salvaSoluzioneButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        salvaSoluzioneButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        salvaSoluzioneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvaSoluzioneButtonActionPerformed(evt);
            }
        });
        jPanel5.add(salvaSoluzioneButton, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        jPanel5.add(feedbackSessione, gridBagConstraints);

        add(jPanel5, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void faiAssunzioniTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faiAssunzioniTestButtonActionPerformed
        Mappatura temp = new Mappatura(jTextField1.getText());
        salvaSessioneButton.setEnabled(true);
        if (!sessController.faiAssunzione(sessione, temp)) {
            /* si potrebbe dare più autorità al session controller facendo sì che
             sessController.faiAssunzioni nel caso in cui faiAssunzioni è false, recupera e restituisce
             il commento
             */
            JFrame padre = (JFrame) SwingUtilities.getWindowAncestor(this);
            AlreadyReachedDialog ard = new AlreadyReachedDialog(padre, true, sessione.getCommento());
            if (ard.getReturnStatus() == AlreadyReachedDialog.RET_UNDO) {
                sessController.undo(sessione, sessione.getCommento());
            }
        }
        provaMappaturaCorrente();
    }//GEN-LAST:event_faiAssunzioniTestButtonActionPerformed

    private void undoButtonTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonTestActionPerformed
        JFrame padre = (JFrame) SwingUtilities.getWindowAncestor(this);
        UndoDialog undoDialog = new UndoDialog(padre, true);
        if (undoDialog.getReturnStatus() == UndoDialog.RET_OK) {
            sessController.undo(sessione, undoDialog.getMotivazione());
            provaMappaturaCorrente();
        }
    }//GEN-LAST:event_undoButtonTestActionPerformed

    private void salvaSessioneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvaSessioneButtonActionPerformed
        try {
            if (sessController.salvaSessione(sessione)) {
                feedbackSessione.setText("Sessione salvata");
                salvaSessioneButton.setEnabled(false);
            } else {
                feedbackSessione.setText("Errore salvataggio");
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(SessioneApertaPanel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_salvaSessioneButtonActionPerformed

    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonActionPerformed
        JFrame padre = (JFrame) SwingUtilities.getWindowAncestor(this);
        UndoDialog undoDialog = new UndoDialog(padre, true);
        if (undoDialog.getReturnStatus() == UndoDialog.RET_OK) {
            sessController.undo(sessione, undoDialog.getMotivazione());
            provaMappaturaCorrente();
        }
    }//GEN-LAST:event_undoButtonActionPerformed

    private void faiAssunzioneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faiAssunzioneButtonActionPerformed
        salvaSessioneButton.setEnabled(true);
        // merge della mappatura e dei caratteri da rimuovere
        daInviare = mergeMapRimuovi(daInviare, daRimuovere);
        feedbackSessione.setText("Inviata mappatura: " + daInviare.toStringa());
//        System.out.println("Mappatura inviata: " + daInviare.toStringa());
        if (!sessController.faiAssunzione(sessione, daInviare)) {
            /* si potrebbe dare più autorità al session controller facendo sì che
             sessController.faiAssunzioni nel caso in cui faiAssunzioni è false, recupera e restituisce
             il commento
             */
            JFrame padre = (JFrame) SwingUtilities.getWindowAncestor(this);
            AlreadyReachedDialog ard = new AlreadyReachedDialog(padre, true, sessione.getCommento());

            if (ard.getReturnStatus() == AlreadyReachedDialog.RET_UNDO) {
                sessController.undo(sessione, sessione.getCommento());
            }
        }
        provaMappaturaCorrente();
        daInviare = new Mappatura();
        daRimuovere = new LinkedList();
    }//GEN-LAST:event_faiAssunzioneButtonActionPerformed

    private void caricaSoluzioneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caricaSoluzioneButtonActionPerformed
        JFrame padre = (JFrame) SwingUtilities.getWindowAncestor(this);
        CaricaSoluzioneDialog caricaSoluzioneDialog = new CaricaSoluzioneDialog(padre, true, proprietario, sessione);
    }//GEN-LAST:event_caricaSoluzioneButtonActionPerformed

    private void salvaSoluzioneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvaSoluzioneButtonActionPerformed
        try {
            // TODO add your handling code here:
            sessController.salvaSoluzione(sessione);
            feedbackSessione.setText("Soluzione salvata");

        } catch (SQLException ex) {
            feedbackSessione.setText("Errore SQL: " + ex);
        }
    }//GEN-LAST:event_salvaSoluzioneButtonActionPerformed

    private void provaMappaturaCorrente() {
        try {
            /* temporaneamente è così */
            mapCorrente = sessione.getMappaturaCorrente();
            jTextArea1.setText(messaggio.getTestoCifrato());
            jTextArea2.setText(Cifratore.decifraMonoalfabetica(mapCorrente, messaggio.getTestoCifrato()));
            undoButtonTest.setEnabled(!mapCorrente.isEmpty());
            for (CharField charField : charFields) {
                Character toWrite = mapCorrente.inverseMap(charField.getInternalChar());
                charField.setText((toWrite != null ? toWrite : "") + "");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessioneApertaPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Object[][] getFrequencyData(Map<Character, Double> frequenze) {
        Object[][] data = new Object[frequenze.size()][2];
        int i = 0;
        for (Map.Entry<Character, Double> entry : frequenze.entrySet()) {
            data[i][0] = entry.getKey();
            data[i][1] = entry.getValue();
            i++;
        }
        return data;
    }

    private void initMyComponents() {
        for (int i = 0; i < 26; i++) {
            JLabel label = new JLabel(alfabeto[i] + " = ");
            label.setHorizontalAlignment(JLabel.RIGHT);
            jPanel8.add(label);
            CharField charField = new CharField(alfabeto[i]);
            charFields.add(charField);
            charField.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    feedbackSessione.setText("");
                    azioni = "";
                    CharField charField = (CharField) e.getSource();
                    Character internalChar = charField.getInternalChar();
                    String newValue = charField.getText();
                    if (newValue.equals("")) {
                        azioni += internalChar + " > -,";
                        System.out.println("Map era " + internalChar + ":" + mapCorrente.inverseMap(internalChar));
                        if (mapCorrente.inverseMap(internalChar) != null) {
                            daRimuovere.add(internalChar);
                            System.out.println(newValue + " aggiunto da lista rimozione");
                        }
                    } else {
                        azioni += internalChar + " > " + newValue;
                        int indexRimozione = daRimuovere.indexOf(internalChar);
                        if (indexRimozione != -1) {
                            daRimuovere.remove(indexRimozione);
                            System.out.println(newValue + " rimosso da lista rimozione");
                        }
                    }
                    /* per testing */
                    System.out.println("Azioni: " + azioni);
                    Mappatura tempp = new Mappatura(azioni);
                    daInviare = daInviare.merge(tempp);
                    System.out.println("Mappatura temp: " + tempp.toStringa());
                    System.out.println("Mappatura: " + daInviare.toStringa());

                }
            });
            jPanel8.add(charField);
        }
        try {
            mittenteLabel.setText(messaggio.getMittente().toString());
            destinatarioLabel.setText(messaggio.getDestinatario().toString());
            titoloLabel.setText(messaggio.getTitolo());
            linguaLabel.setText(messaggio.getLingua());
        } catch (SQLException ex) {
            Logger.getLogger(SessioneApertaPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Mappatura daInviare = new Mappatura();
    private List<Character> daRimuovere = new LinkedList<>();
    private Sessione sessione = null;
    private MessaggioSpia messaggio = null;
    private Mappatura mapCorrente = null;
    private String azioni = "";
    private final SessionController sessController = SessionController.getInstance();
    private List<CharField> charFields = new ArrayList<CharField>();
    private final char[] alfabeto = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
        's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    private UserInfo proprietario = null;

    private Mappatura mergeMapRimuovi(Mappatura m, List<Character> r) {
        String s = m.serialize();
        for (char c : r) {
            s += c + ">-,";
        }
        System.out.println("creo nuova mappatura con stringa " + s);
        return new Mappatura(s);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cDestinatarioLabel;
    private javax.swing.JLabel cLinguaLabel;
    private javax.swing.JLabel cMittenteLabel;
    private javax.swing.JLabel cTitoloLabel;
    private javax.swing.JButton caricaSoluzioneButton;
    private javax.swing.JLabel destinatarioLabel;
    private javax.swing.JButton faiAssunzioneButton;
    private javax.swing.JButton faiAssunzioniTestButton;
    private javax.swing.JLabel feedbackSessione;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel linguaLabel;
    private javax.swing.JLabel mittenteLabel;
    private javax.swing.JButton salvaSessioneButton;
    private javax.swing.JButton salvaSoluzioneButton;
    private javax.swing.JLabel titoloLabel;
    private javax.swing.JButton undoButton;
    private javax.swing.JButton undoButtonTest;
    // End of variables declaration//GEN-END:variables

    private class CharField extends JTextField {

        private final char character;

        public CharField(char c) {
            character = c;
        }

        @Override
        public void processKeyEvent(KeyEvent ev) {
            char c = ev.getKeyChar();
//            if ( (ev.getKeyCode() != KeyEvent.VK_BACK_SPACE) && ((!Character.isLetter(c) && c != '-') || getDocument().getLength() > 0)) {
//                ev.consume();
//                System.out.println("consumato");
//                return;
//            }
            super.processKeyEvent(ev);
        }

        public char getInternalChar() {
            return character;
        }
    }
}
