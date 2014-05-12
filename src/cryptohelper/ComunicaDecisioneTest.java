    
package cryptohelper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mattia Cerrato, mattia.cerrato[at]studenti.unito[dot]it
 */
public class ComunicaDecisioneTest {
    public static void main(String[] args) throws SQLException {
        ResultSet rs;
        Studente s1 = new Studente(0, "tory", "pass1", "Pietro", "Torasso");
        Studente s2 = new Studente(1, "felix", "pass2", "Felice", "Cardone");
        SistemaCifratura sys1 = new SistemaCifratura("cacca", "pseudocasuale");
        Proposta p = new Proposta(s1, s2, sys1);
        CommunicationController cc = CommunicationController.getInstance();
        try {
            rs = cc.inviaDecisione(p, "accepted");
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        /*try {
            DBController dbc = DBController.getInstance();
            dbc.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }*/
        
    }
}
