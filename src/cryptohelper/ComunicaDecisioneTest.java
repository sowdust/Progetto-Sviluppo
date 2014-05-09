
package cryptohelper;

/**
 *
 * @author Mattia Cerrato, mattia.cerrato[at]studenti.unito[dot]it
 */
public class ComunicaDecisioneTest {
    public static void main(String[] args) {
        Studente s1 = new Studente(0, "tory", "pass1", "Pietro", "Torasso");
        Studente s2 = new Studente(1, "felix", "pass2", "Felice", "Cardone");
        SistemaCifratura sys1 = new SistemaCifratura("cacca", "pseudocasuale");
    }
}
