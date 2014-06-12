/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptohelper.model;

import cryptohelper.controller.DBController;
import java.sql.SQLException;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * La classe AlberoIpotesiTest implementa uno Unit Testing della classe
 * AlberoIpotesi.
 *
 * @author Mattia Cerrato, mattia.cerrato[at]studenti.unito[dot]it
 */
public class AlberoIpotesiTest {

    DBController dbc;
    AlberoIpotesi albero;
    private Mappatura a;
    private Mappatura b;

    public AlberoIpotesiTest() {
    }

    @BeforeClass
    public static void setUpClass() throws SQLException {
        DBController dbc = DBController.getInstance();
        Mappatura a;
        Mappatura b;

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        albero = new AlberoIpotesi(); //crea un nuovo albero delle ipotesi vuoto ad ogni metodo di test
        a = new Mappatura("a > z, b > w, c > y");
        b = new Mappatura("d > x, e > u");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of faiAssunzione method, of class AlberoIpotesi. Il metodo
     * faiAssunzione viene eseguito su due oggetti AlberoIpotesi. Dopo ogni
     * assunzione si controlla che gli oggetti abbiano la stessa
     * rappresentazione su stringa, che è caratterizzante dello stato delle
     * Ipotesi. Le assunzioni fatte sono: 1. a > b 2. a > b, a > e
     */
    @Test
    public void testFaiAssunzione() {
        System.out.println("faiAssunzione");
        //Mappatura a = new Mappatura("a > z, b > w, c > y");
        //  PRIMA ASSUNZIONE
        //  a > z, b > w, c > y
        albero.faiAssunzione(new Mappatura(a));
        assertSame(albero.getIpotesiCorrente(), albero.getAlbero().figli.get(0));
        System.out.println("Stato alla prima ipotesi:");
        System.out.println("Map corrente: " + albero.getMappaturaCorrente());
        albero.stampaAlbero();

        //  SECONDA ASSUNZIONE
        //  d > x, e > u
        //Mappatura b = new Mappatura("d > x, e > u");
        albero.faiAssunzione(b);
        //Ipotesi seconda = albero.getIpotesiCorrente();
        assertTrue(albero.getAlbero().figli.size() == 1);

        System.out.println("Stato alla seconda ipotesi:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

    }

    /**
     * Test of getMappaturaCorrente method, of class AlberoIpotesi.
     */
    @Test
    public void testGetMappaturaCorrente() {
        System.out.println("getMappaturaCorrente");

        //  PRIMA ASSUNZIONE
        //  a > z, b > w, c > y
        albero.faiAssunzione(new Mappatura(a));
        assertEquals(albero.getAlbero().figli.get(0).getMappatura(), a);
        assertNotSame(albero.getAlbero().figli.get(0).getMappatura(), a);
        assertEquals(albero.getMappaturaCorrente(), a);

        //  SECONDA ASSUNZIONE
        //  d > x, e > u
        //Mappatura b = new Mappatura("d > x, e > u");
        albero.faiAssunzione(b);
        assertEquals(albero.getMappaturaCorrente(), a.merge(b));

    }

    /**
     * Test of getIpotesiCorrente method, of class AlberoIpotesi.
     */
    @Test
    public void testGetIpotesiCorrente() {
        System.out.println("getIpotesiCorrente");

        albero.faiAssunzione(new Mappatura(a));
        albero.faiAssunzione(b);
        assertSame(albero.getAlbero().figli.get(0).figli.get(0), albero.getIpotesiCorrente());
        assertFalse(albero.getIpotesiCorrente().padre == null);
        assertEquals(albero.getIpotesiCorrente().getMappatura(), albero.getMappaturaCorrente());

    }

    /**
     * Test of undo method, of class AlberoIpotesi.
     */
    @Test
    public void testUndo() {
        System.out.println("undo");
        albero.faiAssunzione(new Mappatura(a));
        albero.faiAssunzione(b);
        Ipotesi seconda = albero.getIpotesiCorrente();

        Mappatura c = new Mappatura("f > v");
        albero.faiAssunzione(c);
        albero.undo("");
        assertSame(albero.getIpotesiCorrente(), seconda);
    }

    public void testTotale() {
        AlberoIpotesi albero = new AlberoIpotesi();
        Mappatura a = new Mappatura("a > z, b > w, c > y");

        //  PRIMA ASSUNZIONE
        //  a > z, b > w, c > y
        albero.faiAssunzione(new Mappatura(a));
        assertSame(albero.getIpotesiCorrente(), albero.getAlbero().figli.get(0));
        assertEquals(albero.getAlbero().figli.get(0).getMappatura(), a);
        assertNotSame(albero.getAlbero().figli.get(0).getMappatura(), a);
        assertTrue(albero.getIpotesiCorrente().padre == albero.getAlbero());
        assertEquals(albero.getMappaturaCorrente(), a);
        System.out.println("Stato alla prima ipotesi:");
        System.out.println("Map corrente: " + albero.getMappaturaCorrente());
        albero.stampaAlbero();

        //  SECONDA ASSUNZIONE [no contaConflitti]
        //  d > x, e > u
        Mappatura b = new Mappatura("d > x, e > u");
        albero.faiAssunzione(b);
        Ipotesi seconda = albero.getIpotesiCorrente();
        assertEquals(albero.getIpotesiCorrente().assunzioni, b);
        assertFalse(albero.getAlbero().figli.isEmpty());
        assertTrue(albero.getAlbero().figli.size() == 1);
        assertSame(albero.getAlbero().figli.get(0).figli.get(0), albero.getIpotesiCorrente());
        assertFalse(albero.getIpotesiCorrente().padre == null);
        assertEquals(albero.getIpotesiCorrente().getMappatura(), albero.getMappaturaCorrente());
        assertEquals(albero.getMappaturaCorrente(), a.merge(b));
        System.out.println("Stato alla seconda ipotesi:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        //  TERZA ASSUNZIONE   [no contaConflitti]
        Mappatura c = new Mappatura("f > v");
        albero.faiAssunzione(c);
        Ipotesi terza = albero.getIpotesiCorrente();
        assertEquals(albero.getMappaturaCorrente(), a.merge(b).merge(c));
        assertSame(albero.getMosse().pop(), albero.getIpotesiCorrente());
        assertSame(albero.getMosse().peek(), albero.getIpotesiCorrente().padre);
        assertSame(albero.getMosse().push(albero.getIpotesiCorrente()), albero.getIpotesiCorrente());
        System.out.println("Stato alla terza ipotesi:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        //  SEMPLICE UNDO
        albero.undo("");
        assertEquals(albero.getMappaturaCorrente(), a.merge(b));
        assertSame(albero.getIpotesiCorrente(), seconda);
        assertSame(albero.getIpotesiCorrente().figli.get(0), terza);
        System.out.println("Stato dopo undo:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        //  TERZA ASSUNZIONE BIS. Riassegnazione terza ipotesi
        c = new Mappatura("f > t");
        albero.faiAssunzione(c);
        Ipotesi terzabis = albero.getIpotesiCorrente();
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
        Ipotesi quinta = albero.getIpotesiCorrente();
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
        assertSame(albero.getIpotesiCorrente(), quinta);
        System.out.println("Stato dopo undo sesta ipotesi:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        //  TORNIAMO ALLA RADICE A FORZA DI UNDO
        albero.undo("");
        albero.undo("");
        albero.undo("");
        albero.undo("");
        assertTrue(albero.getMosse().size() == 1);
        assertEquals(albero.getIpotesiCorrente(), albero.getAlbero());
        assertEquals(albero.getMosse().peek(), albero.getIpotesiCorrente());
        System.out.println("Stato dopo undo fino a radice:");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();

        //  ANDIAMO IN UNO STATO GIÀ RAGGIUNTO
        //  identico alla quinta ipotesi
        Mappatura gia = new Mappatura("d > x, e > u, a > z, b > w, c > y, f > t, g > s ");

        assertFalse(albero.faiAssunzione(gia));
        assertSame(albero.getIpotesiCorrente(), quinta);
        assertSame(albero.getMosse().pop(), quinta);
        albero.getMosse().push(quinta);
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
        assertSame(albero.getIpotesiCorrente().padre, albero.getAlbero());
        assertFalse(false);

        a = new Mappatura("d > r, e > u, a > z, b > w, c > y, f > t, g > s, h > q");
        //a = new MappaturaImpl("e > -");
        assertFalse(albero.faiAssunzione(a));
        System.out.println("Stato dopo aver impostato manualmente");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();
        //assertSame(albero.getIpotesiCorrente().padre, albero.getAlbero());
        assertFalse(false);

        a = new Mappatura("h > -, d > x, e > u, a > z, b > w, c > y, f > t, g > -");
        assertFalse(albero.faiAssunzione(a));
        assertSame(terzabis, albero.getIpotesiCorrente());
        System.out.println("Stato dopo aver impostato eliminato");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();
        //assertSame(albero.getIpotesiCorrente().padre, albero.getAlbero());

        assertTrue(albero.faiAssunzione(new Mappatura("f>-,d>-")));
        System.out.println("Stato dopo aver disimpostato f e d");
        System.out.print("Map corrente: ");
        albero.getMappaturaCorrente().stampa();
        albero.stampaAlbero();
        //assertSame(albero.getIpotesiCorrente().padre, albero.getAlbero());
    }

}
