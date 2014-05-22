/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptohelper.model;

import java.util.ArrayList;
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
        
        //  PRIMA IPOTESI
        //  a > z, b > w, c > y
        sess.aggiungiIpotesi(new MappaturaParziale(a));
        assertSame(sess.ipotesiCorrente,sess.radice);
        assertEquals(sess.radice.getStato(),a);
        assertNotSame(sess.radice.getStato(),a);
        assertTrue(sess.ipotesiCorrente.padre == null);
        assertEquals(sess.getStato(),a);
        System.out.println("Stato alla prima ipotesi:");
        System.out.println(sess.getStato());
        sess.radice.stampa(0);
        
        
        //  SECONDA IPOTESI [no conflitti]
        //  d > x, e > u
        MappaturaParziale b = new MappaturaParziale("d > x, e > u");
        sess.aggiungiIpotesi(b);
        assertEquals(sess.ipotesiCorrente.map,b);
        assertFalse(sess.radice.figli.isEmpty());
        assertTrue(sess.radice.figli.size() == 1);
        assertSame(sess.radice.figli.get(0),sess.ipotesiCorrente);
        assertFalse(sess.ipotesiCorrente.padre == null);
        assertEquals(sess.ipotesiCorrente.getStato(), sess.getStato());
        assertEquals(sess.getStato(), a.merge(b));
        System.out.println("Stato alla seconda ipotesi:");
        System.out.println(sess.getStato());
        sess.radice.stampa(0);
        
        //  TERZA IPOTESI   [no conflitti]
        MappaturaParziale c = new MappaturaParziale("f > v");
        sess.aggiungiIpotesi(c);
        assertEquals(sess.getStato(),a.merge(b).merge(c));
        System.out.println("Stato alla terza ipotesi:");
        System.out.println(sess.getStato());
        sess.radice.stampa(0);      
        

        
    }
}
