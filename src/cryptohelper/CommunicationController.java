package cryptohelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CommunicationController è il Controller delle comunicazioni tra le classi che
 * si occupano di gestire i messaggi. Dedotto da: DSD_apriMessaggioRicevuto
 * Implementa Singleton.
 *
 * @author Mattia Cerrato, mattia.cerrato[at]studenti.unito[dot]it
 */
public class CommunicationController {
    /* NOTE:
     - La query di getDestinatari è leggermente diversa da quella indicata nel DSD: non mi sembra che
     se voglio i DESTINATARI delle proposte di un certo utente io voglia anche l'utente stesso.
     - la SAVE non modifica chiave e metodo. è giusto così?
    penso (Ale) che la query non sia corretta:
        - manca la relazione che lega le due tabelle
        - non considera le proposte che mi sono state fatte (in cui sono il patner) e ho accettato
       io credo che dovrebbe essere qualcosa del genere, ho provato e funziona
       SELECT S.id, S.nome, S.cognome
       FROM crypto_user.Proposta AS P JOIN crypto_user.STUDENTE AS S ON P.partner = S.id
       WHERE P.stato = 'accepted' AND P.proponente = st.getId();
       UNION
       SELECT S.id, S.nome, S.cognome
       FROM crypto_user.Proposta AS P JOIN crypto_user.STUDENTE AS S ON P.proponente = S.id
       WHERE P.stato = 'accepted' AND P.patner = st.getId();
     */

    public static CommunicationController instance = null;

    private CommunicationController() {
    }

    public static CommunicationController getInstance() {
        if (instance == null) {
            instance = new CommunicationController();
        }
        return instance;
    }

    public Messaggio apriMessaggioRicevuto(int id) throws SQLException {
        Messaggio m = Messaggio.load(id);
        m.setLetto(true);
        m.save();
        return m;
    }

    public boolean inviaDecisione(Proposta proposta, String decisione) throws SQLException {
        if (decisione.compareTo("accepted") == 0) {
            Proposta old = Proposta.caricaAttiva(proposta.getProponente().getId(), proposta.getPartner().getId());
            if (old != null) {
                old.setStato("expired");
                boolean saved = old.save(); //nei DSD non sembra essere usato altrimenti
                if (saved == false) {
                    return false;
                }
            }
            //System.out.println("Partner: "+old.getPartner().getNome());
            //System.out.println("Proponente: "+old.getProponente().getNome());
        }
        proposta.setStato(decisione);
        return proposta.save();
    }

    public List<UserInfo> getDestinatari(Studente st) throws SQLException {
        DBController dbc = DBController.getInstance();
        int id = st.getId();
        ResultSet rs = dbc.execute("SELECT Studente.ID, nome, cognome FROM crypto_user.Proposta"
                + "JOIN crypto_user.STUDENTE"
                + "ON crypto_user.Proposta.PROPONENTE = " + st.getId() + " AND crypto_user.PROPOSTA.STATO = 'accepted'"
                + "AND crypto_user.STUDENTE.ID != " + st.getId());
        ArrayList result = new ArrayList<>();
        while (rs.next()) {
            UserInfo us = new UserInfo(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"));
            result.add(us);
        }
        return result;
    }

    public boolean inviaProposta(Studente user, Studente partner, SistemaCifratura sdc) throws SQLException {
        Proposta p = new Proposta(user, partner, sdc);
        return p.save();
    }

    public boolean send(Messaggio messaggio) {
        return messaggio.send();
    }

    public List<Proposta> getAccettazioneProposte(Studente user) throws SQLException {
        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT * FROM crypto_user.Proposta WHERE "
                + "crypto_user.Proposta.id =" + user.getId() + " "
                + "AND crypto_user.Proposta.stato = 'accepted'"
                + "AND crypto_user.Proposta.notificata = 'false'");
        List<Proposta> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new Proposta(rs));
        }
        return result;
    }

    public static List<Proposta> getProposte(Studente st) throws SQLException {
        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT * FROM crypto_user.Proposta WHERE partner = " + st.getId() + " AND stato = 'pending'");
        List<Proposta> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new Proposta(rs));
        }
        return lista;
    }

}
