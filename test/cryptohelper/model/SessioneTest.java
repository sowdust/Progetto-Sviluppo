/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptohelper.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mat
 */
public class SessioneTest {
    
    public SessioneTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testSessione() {


        Sessione sess = new Sessione(null, null);
        MappaturaParziale a = new MappaturaParziale("a > z, b > w, c > y");
        
        //  PRIMA ASSUNZIONE
        //  a > z, b > w, c > y
        sess.aggiungiIpotesi(new MappaturaParziale(a));
        assertSame(sess.ipotesiCorrente,sess.getAlbero().figli.get(0));
        assertEquals(sess.getAlbero().figli.get(0).getStato(),a);
        assertNotSame(sess.getAlbero().figli.get(0).getStato(),a);
        assertTrue(sess.ipotesiCorrente.padre == sess.getAlbero());
        assertEquals(sess.getStato(),a);
        System.out.println("Stato alla prima ipotesi:");
        System.out.println("Map corrente: " + sess.getStato());
        sess.stampaAlbero();
        
        
        //  SECONDA ASSUNZIONE [no conflitti]
        //  d > x, e > u
        MappaturaParziale b = new MappaturaParziale("d > x, e > u");
        sess.aggiungiIpotesi(b);
        Ipotesi seconda = sess.ipotesiCorrente;
        assertEquals(sess.ipotesiCorrente.map,b);
        assertFalse(sess.getAlbero().figli.isEmpty());
        assertTrue(sess.getAlbero().figli.size() == 1);
        assertSame(sess.getAlbero().figli.get(0).figli.get(0),sess.ipotesiCorrente);
        assertFalse(sess.ipotesiCorrente.padre == null);
        assertEquals(sess.ipotesiCorrente.getStato(), sess.getStato());
        assertEquals(sess.getStato(), a.merge(b));
        System.out.println("Stato alla seconda ipotesi:");
        System.out.println("Map corrente: " + sess.getStato());
        sess.stampaAlbero();
        
        //  TERZA ASSUNZIONE   [no conflitti]
        MappaturaParziale c = new MappaturaParziale("f > v");
        sess.aggiungiIpotesi(c);
        Ipotesi terza = sess.ipotesiCorrente;
        assertEquals(sess.getStato(),a.merge(b).merge(c));
        assertSame(sess.mosse.pop(),sess.ipotesiCorrente);
        assertSame(sess.mosse.peek(),sess.ipotesiCorrente.padre);
        assertSame(sess.mosse.push(sess.ipotesiCorrente),sess.ipotesiCorrente);
        System.out.println("Stato alla terza ipotesi:");
        System.out.println("Map corrente: " + sess.getStato());
        sess.stampaAlbero();
        
        //  SEMPLICE UNDO
        sess.undo();
        assertEquals(sess.getStato(), a.merge(b));
        assertSame(sess.ipotesiCorrente,seconda);
        assertSame(sess.ipotesiCorrente.figli.get(0),terza);
        System.out.println("Stato dopo undo:");
        System.out.println("Map corrente: " + sess.getStato());
        sess.stampaAlbero();
        
        //  TERZA ASSUNZIONE BIS. Riassegnazione terza ipotesi
        c = new MappaturaParziale("f > t");
        sess.aggiungiIpotesi(c);
        Ipotesi terzabis = sess.ipotesiCorrente;
        assertEquals(sess.getStato(),a.merge(b).merge(c));
        assertSame(terzabis.padre,seconda);
        assertTrue(terza.figli.isEmpty());
        assertSame(seconda.figli.get(0),terza);
        assertSame(seconda.figli.get(1),terzabis);
        System.out.println("Stato dopo undo terza ipotesi:");
        System.out.println("Map corrente: " + sess.getStato());
        sess.stampaAlbero();    
        
        //  QUINTA ASSUNZIONE. Giusto per allungare
        MappaturaParziale d = new MappaturaParziale("g > s");
        sess.aggiungiIpotesi(d);
        Ipotesi quinta = sess.ipotesiCorrente;
        assertEquals(sess.getStato(),a.merge(b).merge(c).merge(d));
        System.out.println("Stato alla quinta ipotesi:");
        System.out.println("Map corrente: " + sess.getStato());
        sess.stampaAlbero();
        
        //  SESTA ASSUNZIONE [ conflitto con la seconda!]
        MappaturaParziale e = new MappaturaParziale("h > q, d > r");
        sess.aggiungiIpotesi(e);
        assertEquals(sess.getStato(),a.merge(b).merge(c).merge(d).merge(e));
        System.out.println("Stato alla sesta ipotesi:");
        System.out.println("Map corrente: " + sess.getStato());
        sess.stampaAlbero();
        
        //  SESTA BIS. Controlliamo che l'undo faccia il suo lavoro
        sess.undo();
        assertEquals(sess.getStato(),a.merge(b).merge(c).merge(d));
        assertSame(sess.ipotesiCorrente,quinta);
        System.out.println("Stato dopo undo sesta ipotesi:");
        System.out.println("Map corrente: " + sess.getStato());
        sess.stampaAlbero();
        
        //  TORNIAMO ALLA RADICE A FORZA DI UNDO
        sess.undo();
        sess.undo();
        sess.undo();
        sess.undo();
        assertTrue(sess.mosse.size() == 1);
        assertEquals(sess.ipotesiCorrente,sess.getAlbero());
        assertEquals(sess.mosse.peek(),sess.ipotesiCorrente);
        System.out.println("Stato dopo undo fino a radice:");
        System.out.println("Map corrente: " + sess.getStato());
        sess.stampaAlbero();        
        
        
        //  ANDIAMO IN UNO STATO GIÀ RAGGIUNTO
        //  identico alla quinta ipotesi
        MappaturaParziale gia = new MappaturaParziale("d > x, e > u, a > z, b > w, c > y, f > t, g > s ");
//        sess.aggiungiIpotesi(gia);
        System.out.println("Ipotesi già raggiunta: " + sess.getAlbero().giaRaggiunta(gia, new MappaturaParziale()).getStato());
        assertSame(sess.getAlbero().giaRaggiunta(gia, new MappaturaParziale()), quinta);
        
    }
}
