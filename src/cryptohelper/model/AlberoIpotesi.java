package cryptohelper.model;

import cryptohelper.controller.DBController;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import javax.sql.rowset.CachedRowSet;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

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
    private Stack<Ipotesi> mosse;
    private Ipotesi ipotesiCorrente;
    private Mappatura mappaturaCorrente;

    public AlberoIpotesi() {

        mosse = new Stack<>();
        mappaturaCorrente = new Mappatura();
        radice = new Ipotesi(mappaturaCorrente, null);
        ipotesiCorrente = radice;
        mosse.push(ipotesiCorrente);
    }

    public static AlberoIpotesi load(int idSessione) {
        try {
            DBController dbc = DBController.getInstance();
            CachedRowSet crs = dbc.execute("SELECT albero FROM Sessione WHERE id = ?", idSessione);
            if (!crs.next()) {
                return null;
            }
            Blob bl = crs.getBlob("albero");
            byte[] buf = bl.getBytes(1, (int) bl.length());
            ObjectInputStream objectIn;
            objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
            return ((AlberoIpotesi) objectIn.readObject());
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
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
        //System.out.println("Arrivata assunzione \t" + nuoveAssunzioni.toStringa());
        Mappatura nuovaMappatura = mappaturaCorrente.merge(nuoveAssunzioni);
        //System.out.println("Nuova mappatura \t" + nuovaMappatura.toStringa());
        Ipotesi giaRaggiunta = giaRaggiunta(nuovaMappatura);

        //  Se l'ipotesi è già raggiunta, ci spostiamo lì e torniamo false
        if (giaRaggiunta != null) {
            ipotesiCorrente = giaRaggiunta;
            mappaturaCorrente = nuovaMappatura;
            mosse.push(ipotesiCorrente);
            return false;
        }

        Ipotesi ipotesiACuiAttaccarsi;
        // filtraDaRimuovere ha side effect su nuoveAssunzioni:
        // rimuove tutte quelle della forma { x > - }
        List<Character> listaDaRimuovere = nuoveAssunzioni.filtraDaRimuovere();
        int nConflitti = mappaturaCorrente.contaConflitti(nuoveAssunzioni);
        Mappatura daAggiungere;
        //System.out.println("Numero conflitti \t" + nConflitti);
        //System.out.println("Da rimuovere \t");
        for (char c : listaDaRimuovere) {
            //System.out.print(c + " ");
        }

        if (nConflitti < 1 && listaDaRimuovere.isEmpty()) {

            ipotesiACuiAttaccarsi = ipotesiCorrente;
            daAggiungere = nuoveAssunzioni.sottrai(mappaturaCorrente);
            //System.out.println("Aggiunto al nodo corrente \t" + nuoveAssunzioni.toStringa());

        } else {

            ipotesiACuiAttaccarsi = ipotesiCorrente.trovaConflitto(nuoveAssunzioni, listaDaRimuovere, nConflitti);
            daAggiungere = nuovaMappatura.sottrai(ipotesiACuiAttaccarsi.getMappatura());
            //System.out.println("Aggiunto più in su \t" + nuoveAssunzioni.toStringa());

        }

        ipotesiCorrente = ipotesiACuiAttaccarsi.aggiungiIpotesi(daAggiungere);
        mappaturaCorrente = nuovaMappatura;
        mosse.push(ipotesiCorrente);
        stampaAlbero();
        return true;
    }

    public void undo(String motivazione) {
        try {
            mosse.pop().setCommento(motivazione);
            ipotesiCorrente = mosse.peek();
            mappaturaCorrente = ipotesiCorrente.getMappatura();
        } catch (EmptyStackException ex) {
            ipotesiCorrente = radice;
            mappaturaCorrente = new Mappatura();
        }
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

    public String getCommento() {
        return ipotesiCorrente.getCommento();
    }

    private void test(Mappatura nuoveAss, List<Character> r, int conflitti) {
        System.out.println("Mappatura corrente:" + mappaturaCorrente);
        System.out.println("Nuove assunzioni:" + nuoveAss);
        System.out.print("Da rimuovere:" + nuoveAss);
        for (char c : r) {
            System.out.print(r + " ");
        }
        System.out.println();
        System.out.println("Conflitti:" + conflitti);
        stampaAlbero();
    }

    public String toString() {
        return radice.toString(0, ipotesiCorrente);
    }

    public Stack<Ipotesi> getMosse() {
        return mosse;
    }

    public Ipotesi getIpotesiCorrente() {
        return this.ipotesiCorrente;
    }

    public void testVecchio() {

        AlberoIpotesi albero = new AlberoIpotesi();
        Mappatura a = new Mappatura("a > z, b > w, c > y");

        //  PRIMA ASSUNZIONE
        //  a > z, b > w, c > y
        albero.faiAssunzione(new Mappatura(a));
        assertSame(albero.ipotesiCorrente, albero.getAlbero().figli.get(0));
        assertEquals(albero.getAlbero().figli.get(0).getMappatura(), a);
        assertNotSame(albero.getAlbero().figli.get(0).getMappatura(), a);
        assertTrue(albero.ipotesiCorrente.padre == albero.getAlbero());
        assertEquals(albero.getMappaturaCorrente(), a);
        System.out.println("Stato alla prima ipotesi:");
        System.out.println("Map corrente: " + albero.getMappaturaCorrente());
        albero.stampaAlbero();

        //  SECONDA ASSUNZIONE [no contaConflitti]
        //  d > x, e > u
        Mappatura b = new Mappatura("d > x, e > u");
        albero.faiAssunzione(b);
        Ipotesi seconda = albero.ipotesiCorrente;
        assertEquals(albero.ipotesiCorrente.assunzioni, b);
        assertFalse(albero.getAlbero().figli.isEmpty());
        assertTrue(albero.getAlbero().figli.size() == 1);
        assertSame(albero.getAlbero().figli.get(0).figli.get(0), albero.ipotesiCorrente);
        assertFalse(albero.ipotesiCorrente.padre == null);
        assertEquals(albero.ipotesiCorrente.getMappatura(), albero.getMappaturaCorrente());
        assertEquals(albero.getMappaturaCorrente(), a.merge(b));
        System.out.println("Stato alla seconda ipotesi:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        //  TERZA ASSUNZIONE   [no contaConflitti]
        Mappatura c = new Mappatura("f > v");
        albero.faiAssunzione(c);
        Ipotesi terza = albero.ipotesiCorrente;
        assertEquals(albero.getMappaturaCorrente(), a.merge(b).merge(c));
        assertSame(albero.mosse.pop(), albero.ipotesiCorrente);
        assertSame(albero.mosse.peek(), albero.ipotesiCorrente.padre);
        assertSame(albero.mosse.push(albero.ipotesiCorrente), albero.ipotesiCorrente);
        System.out.println("Stato alla terza ipotesi:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        //  SEMPLICE UNDO
        albero.undo("");
        assertEquals(albero.getMappaturaCorrente(), a.merge(b));
        assertSame(albero.ipotesiCorrente, seconda);
        assertSame(albero.ipotesiCorrente.figli.get(0), terza);
        System.out.println("Stato dopo undo:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        //  TERZA ASSUNZIONE BIS. Riassegnazione terza ipotesi
        c = new Mappatura("f > t");
        albero.faiAssunzione(c);
        Ipotesi terzabis = albero.ipotesiCorrente;
        assertEquals(albero.getMappaturaCorrente(), a.merge(b).merge(c));
        assertSame(terzabis.padre, seconda);
        assertTrue(terza.figli.isEmpty());
        assertSame(seconda.figli.get(0), terza);
        assertSame(seconda.figli.get(1), terzabis);
        System.out.println("Stato dopo undo terza ipotesi:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        //  QUINTA ASSUNZIONE. Giusto per allungare
        Mappatura d = new Mappatura("g > s");
        albero.faiAssunzione(d);
        Ipotesi quinta = albero.ipotesiCorrente;
        assertEquals(albero.getMappaturaCorrente(), a.merge(b).merge(c).merge(d));
        System.out.println("Stato alla quinta ipotesi:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        //  SESTA ASSUNZIONE [ conflitto con la seconda!]
        Mappatura e = new Mappatura("h > q, d > r");
        albero.faiAssunzione(e);
        assertEquals(albero.getMappaturaCorrente(), a.merge(b).merge(c).merge(d).merge(e));
        System.out.println("Stato alla sesta ipotesi:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        //  SESTA BIS. Controlliamo che l'undo faccia il suo lavoro
        albero.undo("");
        assertEquals(albero.getMappaturaCorrente(), a.merge(b).merge(c).merge(d));
        assertSame(albero.ipotesiCorrente, quinta);
        System.out.println("Stato dopo undo sesta ipotesi:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        //  TORNIAMO ALLA RADICE A FORZA DI UNDO
        albero.undo("");
        albero.undo("");
        albero.undo("");
        albero.undo("");
        assertTrue(albero.mosse.size() == 1);
        assertEquals(albero.ipotesiCorrente, albero.getAlbero());
        assertEquals(albero.mosse.peek(), albero.ipotesiCorrente);
        System.out.println("Stato dopo undo fino a radice:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        //  ANDIAMO IN UNO STATO GIÀ RAGGIUNTO
        //  identico alla quinta ipotesi
        Mappatura gia = new Mappatura("d > x, e > u, a > z, b > w, c > y, f > t, g > s ");

        assertFalse(albero.faiAssunzione(gia));
        assertSame(albero.ipotesiCorrente, quinta);
        assertSame(albero.mosse.pop(), quinta);
        albero.mosse.push(quinta);
        System.out.println("Stato dopo ipotesi già raggiunta (la quinta)");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();
        /*
         assertTrue(albero.faiAssunzione(new MappaturaImpl("f>x,d>k")));
         System.out.println("Stato dopo doppio conflitto");
         System.out.println("Map corrente: " + albero.getMappatura());
         albero.stampaAlbero();
         */

        assertTrue(albero.faiAssunzione(new Mappatura("f>x,d>k,a>-")));
        System.out.println("Stato dopo doppio e rimozione");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        // ORA TOGLIAMO UN PO' DI ASSUNZIONI E ANDIAMO IN UNO STATO GIÀ RAGGIUNTO
        assertTrue(albero.faiAssunzione(new Mappatura("d > - , c> z")));
        System.out.println("Stato dopo aver tolto la d");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        a = new Mappatura("e > h");
        albero.faiAssunzione(a);
        System.out.println("Stato dopo aver impostato manualmente");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();
        assertSame(albero.ipotesiCorrente.padre, albero.getAlbero());
        assertFalse(false);

        a = new Mappatura("d > r, e > u, a > z, b > w, c > y, f > t, g > s, h > q");
        //a = new MappaturaImpl("e > -");
        assertFalse(albero.faiAssunzione(a));
        System.out.println("Stato dopo aver impostato manualmente");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();
        //assertSame(albero.ipotesiCorrente.padre, albero.getAlbero());
        assertFalse(false);

        a = new Mappatura("h > -, d > x, e > u, a > z, b > w, c > y, f > t, g > -");
        assertFalse(albero.faiAssunzione(a));
        assertSame(terzabis, albero.ipotesiCorrente);
        System.out.println("Stato dopo aver impostato eliminato");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();
        //assertSame(albero.ipotesiCorrente.padre, albero.getAlbero());

        assertTrue(albero.faiAssunzione(new Mappatura("f>-,d>-")));
        System.out.println("Stato dopo aver disimpostato f e d");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();
        //assertSame(albero.ipotesiCorrente.padre, albero.getAlbero());

    }

}
