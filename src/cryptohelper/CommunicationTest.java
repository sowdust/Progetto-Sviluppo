
package cryptohelper;

import java.sql.SQLException;

/**
 *
 * @author Mattia Cerrato, mattia.cerrato[at]studenti.unito[dot]it
 */
public class CommunicationTest {
    public static void main(String[] args) {
        Messaggio m = null;
        CommunicationController commC = CommunicationController.getInstance();
        try {
            m = commC.apriMessaggioRicevuto(0);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(m.getTesto());
    }
}
