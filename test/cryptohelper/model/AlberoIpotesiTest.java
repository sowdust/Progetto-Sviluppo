/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptohelper.model;

import java.io.Serializable;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author mat
 */
public class AlberoIpotesiTest implements Serializable {
    
    public AlberoIpotesiTest() {
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
    public void testAlberoIpotesi() {


        AlberoIpotesi albero = new AlberoIpotesi(null, null);
        MappaturaParziale a = new MappaturaParziale("a > z, b > w, c > y");
        
        //  PRIMA ASSUNZIONE
        //  a > z, b > w, c > y
        albero.faiAssunzione(new MappaturaParziale(a));
        assertSame(albero.ipotesiCorrente,albero.getAlbero().figli.get(0));
        assertEquals(albero.getAlbero().figli.get(0).getStato(),a);
        assertNotSame(albero.getAlbero().figli.get(0).getStato(),a);
        assertTrue(albero.ipotesiCorrente.padre == albero.getAlbero());
        assertEquals(albero.getStato(),a);
        System.out.println("Stato alla prima ipotesi:");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();
        
        
        //  SECONDA ASSUNZIONE [no conflitti]
        //  d > x, e > u
        MappaturaParziale b = new MappaturaParziale("d > x, e > u");
        albero.faiAssunzione(b);
        Ipotesi seconda = albero.ipotesiCorrente;
        assertEquals(albero.ipotesiCorrente.map,b);
        assertFalse(albero.getAlbero().figli.isEmpty());
        assertTrue(albero.getAlbero().figli.size() == 1);
        assertSame(albero.getAlbero().figli.get(0).figli.get(0),albero.ipotesiCorrente);
        assertFalse(albero.ipotesiCorrente.padre == null);
        assertEquals(albero.ipotesiCorrente.getStato(), albero.getStato());
        assertEquals(albero.getStato(), a.merge(b));
        System.out.println("Stato alla seconda ipotesi:");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();
        
        //  TERZA ASSUNZIONE   [no conflitti]
        MappaturaParziale c = new MappaturaParziale("f > v");
        albero.faiAssunzione(c);
        Ipotesi terza = albero.ipotesiCorrente;
        assertEquals(albero.getStato(),a.merge(b).merge(c));
        assertSame(albero.mosse.pop(),albero.ipotesiCorrente);
        assertSame(albero.mosse.peek(),albero.ipotesiCorrente.padre);
        assertSame(albero.mosse.push(albero.ipotesiCorrente),albero.ipotesiCorrente);
        System.out.println("Stato alla terza ipotesi:");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();
        
        //  SEMPLICE UNDO
        albero.undo();
        assertEquals(albero.getStato(), a.merge(b));
        assertSame(albero.ipotesiCorrente,seconda);
        assertSame(albero.ipotesiCorrente.figli.get(0),terza);
        System.out.println("Stato dopo undo:");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();
        
        //  TERZA ASSUNZIONE BIS. Riassegnazione terza ipotesi
        c = new MappaturaParziale("f > t");
        albero.faiAssunzione(c);
        Ipotesi terzabis = albero.ipotesiCorrente;
        assertEquals(albero.getStato(),a.merge(b).merge(c));
        assertSame(terzabis.padre,seconda);
        assertTrue(terza.figli.isEmpty());
        assertSame(seconda.figli.get(0),terza);
        assertSame(seconda.figli.get(1),terzabis);
        System.out.println("Stato dopo undo terza ipotesi:");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();    
        
        //  QUINTA ASSUNZIONE. Giusto per allungare
        MappaturaParziale d = new MappaturaParziale("g > s");
        albero.faiAssunzione(d);
        Ipotesi quinta = albero.ipotesiCorrente;
        assertEquals(albero.getStato(),a.merge(b).merge(c).merge(d));
        System.out.println("Stato alla quinta ipotesi:");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();
        
        //  SESTA ASSUNZIONE [ conflitto con la seconda!]
        MappaturaParziale e = new MappaturaParziale("h > q, d > r");
        albero.faiAssunzione(e);
        assertEquals(albero.getStato(),a.merge(b).merge(c).merge(d).merge(e));
        System.out.println("Stato alla sesta ipotesi:");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();
        
        //  SESTA BIS. Controlliamo che l'undo faccia il suo lavoro
        albero.undo();
        assertEquals(albero.getStato(),a.merge(b).merge(c).merge(d));
        assertSame(albero.ipotesiCorrente,quinta);
        System.out.println("Stato dopo undo sesta ipotesi:");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();
        
        //  TORNIAMO ALLA RADICE A FORZA DI UNDO
        albero.undo();
        albero.undo();
        albero.undo();
        albero.undo();
        assertTrue(albero.mosse.size() == 1);
        assertEquals(albero.ipotesiCorrente,albero.getAlbero());
        assertEquals(albero.mosse.peek(),albero.ipotesiCorrente);
        System.out.println("Stato dopo undo fino a radice:");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();        
        
        
        //  ANDIAMO IN UNO STATO GIÀ RAGGIUNTO
        //  identico alla quinta ipotesi
        MappaturaParziale gia = new MappaturaParziale("d > x, e > u, a > z, b > w, c > y, f > t, g > s ");
        
        
        
        assertFalse(albero.faiAssunzione(gia));
        assertSame(albero.ipotesiCorrente, quinta);
        assertSame(albero.mosse.pop(),quinta);
        albero.mosse.push(quinta);
        System.out.println("Stato dopo ipotesi già raggiunta (la quinta)");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();        
        
        
        // ORA TOGLIAMO UN PO' DI ASSUNZIONI E ANDIAMO IN UNO STATO GIÀ RAGGIUNTO
        assertTrue(albero.faiAssunzione(new MappaturaParziale("d > - , c> z")));
        System.out.println("Stato dopo aver tolto la d");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();   
        

        a = new MappaturaParziale("e > h");
        albero.faiAssunzione(a);
        System.out.println("Stato dopo aver impostato manualmente");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();   
        assertSame(albero.ipotesiCorrente.padre, albero.getAlbero());
        assertFalse(false);

        
        a = new MappaturaParziale("d > r, e > u, a > z, b > w, c > y, f > t, g > s, h > q");
        //a = new MappaturaParziale("e > -");
        assertFalse(albero.faiAssunzione(a));
        System.out.println("Stato dopo aver impostato manualmente");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();   
        //assertSame(albero.ipotesiCorrente.padre, albero.getAlbero());
        assertFalse(false);        
        
        
        a = new MappaturaParziale("h > -, d > x, e > u, a > z, b > w, c > y, f > t, g > -");
        assertFalse(albero.faiAssunzione(a));
        assertSame(terzabis,albero.ipotesiCorrente);
        System.out.println("Stato dopo aver impostato manualmente");
        System.out.println("Map corrente: " + albero.getStato());
        albero.stampaAlbero();   
        //assertSame(albero.ipotesiCorrente.padre, albero.getAlbero());
        assertFalse(false); 
    }
}
