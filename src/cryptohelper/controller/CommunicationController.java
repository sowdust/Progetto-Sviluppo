package cryptohelper.controller;

import cryptohelper.model.Messaggio;
import cryptohelper.model.MessaggioDestinatario;
import cryptohelper.model.MessaggioMittente;
import cryptohelper.model.Proposta;
import cryptohelper.model.SistemaCifratura;
import cryptohelper.model.Studente;
import cryptohelper.model.UserInfo;
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

    /* (Ale) dato che sappiamo che è un messaggio ricevuto dovrebbe ritornare MessaggioDestinatario
     in modo da avere a disposizione solo le funzionalità di un messaggio ricevuto
     però MessaggioDestinatario non dispone di save(), forse andrebbe in MessaggioAstrato?
     per ora ho fatto in modo che ritorni MessaggioDestinatario anche se ha usato save
     */
    public MessaggioDestinatario apriMessaggioRicevuto(int id) throws SQLException {
        Messaggio m = Messaggio.load(id);
        m.setLetto(true);
        m.save();
        return (MessaggioDestinatario) m;
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
        ResultSet rs = dbc.execute("SELECT partner AS idDest "
                + "FROM Proposta "
                + "WHERE stato = 'accepted' AND proponente = ? "
                + "UNION "
                + "SELECT proponente AS idDest "
                + "FROM Proposta "
                + "WHERE stato = 'accepted' AND partner = ? ", id, id);
        List<UserInfo> listaDest = new ArrayList<>();
        while (rs.next()) {
            listaDest.add(UserInfo.load(rs.getInt("idDest")));
        }
        return listaDest;
    }

    public boolean inviaProposta(Studente user, Studente partner, SistemaCifratura sdc) throws SQLException {
        Proposta p = new Proposta(user, partner, sdc);
        return p.save();
    }

    public boolean send(MessaggioMittente messaggio) {
        return messaggio.send();
    }

    /* (Ale) prende l'unica proposta che ha come id lo stesso id dello studente e non considera lo stato refused*/
    public List<Proposta> getAccettazioneProposte(Studente user) throws SQLException {
        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT * FROM Proposta WHERE "
                + "proponente = ? AND stato = 'accepted' AND notificata = 'false'", user.getId());
        List<Proposta> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new Proposta(rs));
        }
        return result;
    }

    public static List<Proposta> getProposte(Studente st) throws SQLException {
        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT * FROM Proposta WHERE partner = ? AND stato = 'pending'", st.getId());
        List<Proposta> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new Proposta(rs));
        }
        return lista;
    }

}
