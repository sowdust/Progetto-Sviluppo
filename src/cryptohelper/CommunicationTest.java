package cryptohelper;

import cryptohelper.controller.CommunicationController;
import cryptohelper.model.Proposta;
import cryptohelper.model.Messaggio;
import cryptohelper.model.Studente;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Mattia Cerrato, mattia.cerrato[at]studenti.unito[dot]it
 */
public class CommunicationTest {

    public static void main(String[] args) throws SQLException {
        Messaggio m = null;
        CommunicationController commC = CommunicationController.getInstance();
        try {
            m = commC.apriMessaggioRicevuto(0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(m.getTesto());

        System.out.println("Testing getProposte(user):");
        Studente st = new Studente(1, "", "", "", "");
        List<Proposta> sis = CommunicationController.getProposte(st);
        System.out.println(sis.size());
        System.out.println(sis.get(0));
    }
}
