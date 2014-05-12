
package cryptohelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CommunicationController è il Controller delle comunicazioni tra le classi che si occupano
 * di gestire i messaggi. Dedotto da: DSD_apriMessaggioRicevuto
 * Implementa Singleton.
 * @author Mattia Cerrato, mattia.cerrato[at]studenti.unito[dot]it
 */
public class CommunicationController {
    /* NOTE:
    - La query di getDestinatari è leggermente diversa da quella indicata nel DSD: non mi sembra che
    se voglio i DESTINATARI delle proposte di un certo utente io voglia anche l'utente stesso.
    */
    public static CommunicationController instance = null;
    
    private CommunicationController() {}
    
    public static CommunicationController getInstance() {
        if(instance == null) {
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
    
    public ResultSet inviaDecisione(Proposta proposta, String decisione) throws SQLException{
        if(decisione.compareTo("accepted") == 0) {
            Proposta old = Proposta.caricaAttiva(proposta.getProponente().getId(), proposta.getPartner().getId());
            if (old != null) {
                old.setStato("expired");
                ResultSet rs1 = old.save(); //nei DSD non sembra essere usato altrimenti
            }
            //System.out.println("Partner: "+old.getPartner().getNome());
            //System.out.println("Proponente: "+old.getProponente().getNome());
        }
        proposta.setStato(decisione);
        ResultSet rs2 = proposta.save();
        return rs2;
    }
    
    public List<UserInfo> getDestinatari(Studente st) throws SQLException {
        DBController dbc = DBController.getInstance();
        int id = st.getId();
        ResultSet rs = dbc.execute("SELECT Studente.ID, nome, cognome FROM crypto_user.Proposta"
                                 + "JOIN crypto_user.STUDENTE"
                                 + "ON crypto_user.Proposta.PROPONENTE = "+st.getId()+" AND crypto_user.PROPOSTA.STATO = 'accepted'" 
                                 + "AND crypto_user.STUDENTE.ID != "+st.getId());
        ArrayList result = new ArrayList<UserInfo>();
        while(rs.next()) {
            UserInfo us = new UserInfo(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"));
            result.add(us);
        }
        return result;
    }
}
