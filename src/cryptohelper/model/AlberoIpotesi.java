package cryptohelper.model;

import cryptohelper.controller.DBController;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Stack;
import javax.sql.rowset.CachedRowSet;

/*
 * NOTE SULL'ALBERO.
 *
 * Invariante è il fatto che in ogni cammino non vi siano mai due assegnazioni
 * conflittuali ( a->x & a->y oppure a->x & b->x )
 *
 * Ne derivano le seguenti proprietà:
 *
 *  1) lunghezza massima di un cammino = cardinalità alfabeto
 *  2) calcolo della mappatura dal basso verso l'alto esplorando solo il cammino corrente
 *  3) detection di stato già visitato si ferma al primo conflitto in ogni ramo
 *
 */
public class AlberoIpotesi implements Serializable {

    private final Ipotesi radice;
    public Stack<Ipotesi> mosse;
    public Ipotesi ipotesiCorrente;
    private Mappatura mappaturaCorrente;

    public AlberoIpotesi() {

        mosse = new Stack<>();
        mappaturaCorrente = new Mappatura();
        radice = new Ipotesi(mappaturaCorrente, null);
        ipotesiCorrente = radice;
        mosse.push(ipotesiCorrente);
    }

    public static AlberoIpotesi load(int idSessione) throws SQLException, IOException, ClassNotFoundException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT id, messaggio, proprietario FROM Sessione WHERE id = ?", idSessione);
        if (!crs.next()) {
            return null;
        }
        Blob bl = crs.getBlob("albero");
        byte[] buf = bl.getBytes(1, (int) bl.length());
        ObjectInputStream objectIn;
        objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
        return ((AlberoIpotesi) objectIn.readObject());
    }

    /*
     * Data una mappatura parziale in input, aggiorna l'albero delle ipotesi
     * in modo da mantenerne le proprietà.
     * Aggiorna la mappatura corrente, la lista delle mosse e il puntatore
     * a ipotesi corrente.
     * Ritorna false in caso lo stato corrente fosse già stato raggiunto
     * in passato, true altrimenti
     */
    public boolean faiAssunzione(Mappatura nuoveAssunzioni) {

        Mappatura nuovaMappatura = mappaturaCorrente.merge(nuoveAssunzioni);
        Ipotesi giaRaggiunta = giaRaggiunta(nuovaMappatura);

        //  Se l'ipotesi è già raggiunta, ci spostiamo lì e torniamo false
        if (giaRaggiunta != null) {
            ipotesiCorrente = giaRaggiunta;
            mappaturaCorrente = nuovaMappatura;
            mosse.push(ipotesiCorrente);
            return false;
        }

        int nConflitti = mappaturaCorrente.contaConflitti(nuoveAssunzioni);
        Ipotesi ipotesiACuiAttaccarsi;
        // filtraDaRimuovere ha side effect su nuoveAssunzioni:
        // rimuove tutte quelle della forma { x > - }
        List<Character> listaDaRimuovere = nuoveAssunzioni.filtraDaRimuovere();
        Mappatura daAggiungere;

        if (nConflitti == 0 && listaDaRimuovere.isEmpty()) {

            ipotesiACuiAttaccarsi = ipotesiCorrente;
            daAggiungere = nuoveAssunzioni;

        } else {

            ipotesiACuiAttaccarsi = ipotesiCorrente.trovaConflitto(nuoveAssunzioni, listaDaRimuovere, nConflitti);
            daAggiungere = nuovaMappatura.sottrai(ipotesiACuiAttaccarsi.getMappatura());
        }

        ipotesiCorrente = ipotesiACuiAttaccarsi.aggiungiIpotesi(daAggiungere);
        mappaturaCorrente = nuovaMappatura;
        mosse.push(ipotesiCorrente);

        return true;
    }

    public void undo(String motivazione) {
        mosse.pop().setCommento(motivazione);
        ipotesiCorrente = mosse.peek();
        mappaturaCorrente = ipotesiCorrente.getMappatura();
    }

    public Mappatura getMappaturaCorrente() {
        return new Mappatura(mappaturaCorrente);
    }

    public Ipotesi getAlbero() {
        return radice;
    }

    public Ipotesi giaRaggiunta(Mappatura mappaturaCorrente) {
        return radice.giaRaggiunta(mappaturaCorrente, new Mappatura());
    }

    public void stampaAlbero() {
        radice.stampa(0, ipotesiCorrente);
    }
}
