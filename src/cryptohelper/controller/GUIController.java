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
package cryptohelper.controller;

import cryptohelper.model.Mappatura;
import cryptohelper.model.Messaggio;
import cryptohelper.model.MessaggioDestinatario;
import cryptohelper.model.MessaggioMittente;
import cryptohelper.model.Proposta;
import cryptohelper.model.SistemaCifratura;
import cryptohelper.model.Studente;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author glaxy
 */
public class GUIController {

    private static GUIController instance = null;
    private Studente studente = null;
    private SistemaCifratura nuovoSdc = null;

    private GUIController() {
    }

    public static GUIController getInstance() {
        if (instance == null) {
            instance = new GUIController();
        }
        return instance;
    }

    public boolean login(String nickname, String password) throws SQLException {
        studente = Studente.login(nickname, password);
        return studente != null;
    }

    public String[] ottieniMetodiDiCifratura() {
        return new String[]{"parolachiave", "pseudocasuale", "cesare"};
    }

    public List<SistemaCifratura> elencaSistemiCifratura() throws SQLException {
        return SistemaCifratura.caricaSistemiCifratura(studente);
    }

    public List<SistemaCifratura> elencaSistemiCifraturaNonProposti() throws SQLException {
        return SistemaCifratura.caricaSistemiCifraturaNonProposti(studente);
    }

    /* come comportarsi se il sdc è stato proposto?
     la cosa più ragionevole e facile da implementare (già implementata) è cancellare
     tutti i messaggi che usano quel sdc e la proposta che lo ha proposto       
     */
    public boolean eliminaSistemaCifratura(SistemaCifratura sdc) throws SQLException {
        return sdc.elimina();
    }

    public List<Proposta> vediProposteSistemaCifratura() throws SQLException {
        return CommunicationController.getInstance().getProposte(studente);
    }

    public List<Proposta> vediNotificheAccettazioneProposte() throws SQLException {
        return CommunicationController.getInstance().getAccettazioneProposte(studente);
    }

    public boolean comunicaDecisione(Proposta proposta, String decisione) throws SQLException {
        return CommunicationController.getInstance().inviaDecisione(proposta, decisione);
    }

    public String mostraSceltaChiave(String metodo) {
        String vincolo = "";
        switch (metodo) {
            case "parolachiave":
                vincolo = "inserisci una parola (solo lettere latine)";
                break;
            case "cesare":
                vincolo = "inserisci un numero da 1 a 25";
                break;
            case "pseudocasuale":
                vincolo = "inserisci un numero";
                break;
        }
        return vincolo;
    }

    public Mappatura generaMappatura(String chiave, String metodo) {
        if (nuovoSdc == null) {
            nuovoSdc = new SistemaCifratura(chiave, metodo, studente);
        } else {
            nuovoSdc.setChiave(chiave);
            nuovoSdc.setMetodo(metodo);
            nuovoSdc.calcolaMappatura();
        }
        return nuovoSdc.getMappatura();
    }

    public String cifra(String testo) {
        return nuovoSdc.prova(testo);
    }

    public boolean salvaSistemaCifratura() throws SQLException {
        return nuovoSdc.save();
    }

    public List<MessaggioDestinatario> elencaMessaggiRicevuti() throws SQLException {
        return Messaggio.caricaRicevuti(studente);
    }

    public List<MessaggioMittente> elencaMessaggiInviati() throws SQLException {
        return Messaggio.caricaInviati(studente);
    }

}
