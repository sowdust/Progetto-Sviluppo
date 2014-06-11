/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptohelper.model;

import cryptohelper.controller.DBController;
import java.sql.SQLException;
import java.util.Stack;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * La classe AlberoIpotesiTest implementa uno Unit Testing della classe
 * AlberoIpotesi. La gerarchia dei test è: giaRaggiunta -> faiAssunzione -> load
 * -> getMappaturaCorrente -> undo Assioma è che AlberoIpotesi.toString non sia
 * un metodo-truffa
 *
 * @author Mattia Cerrato, mattia.cerrato[at]studenti.unito[dot]it
 */
public class AlberoIpotesiTest {

    DBController dbc;
    AlberoIpotesi alberoTest;

    public AlberoIpotesiTest() {
    }

    @BeforeClass
    public static void setUpClass() throws SQLException {
        DBController dbc = DBController.getInstance();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        alberoTest = new AlberoIpotesi(); //crea un nuovo albero delle ipotesi vuoto ad ogni metodo di test
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of load method, of class AlberoIpotesi. Viene richiamato il metodo
     * load di due alberi su cui sono state compiute tramite GUI le stesse
     * assunzioni, e ci si chiede se questi due abbiano la stessa
     * rappresentazione su stringa.
     */
    @Test
    public void testLoad() {
        System.out.println("load");
        int idSessione1 = 18;
        int idSessione2 = 19; //due sessioni con le stesse ipotesi fatte
        AlberoIpotesi test1 = AlberoIpotesi.load(idSessione1);
        AlberoIpotesi test2 = AlberoIpotesi.load(idSessione2);
        assertEquals(test1.toString(), test2.toString());
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
        Mappatura nuoveAssunzioni = new Mappatura("a > b");
        AlberoIpotesi instance = new AlberoIpotesi();
        instance.faiAssunzione(nuoveAssunzioni);
        alberoTest.faiAssunzione(nuoveAssunzioni);
        assertEquals(alberoTest.toString(), instance.toString());
        nuoveAssunzioni = new Mappatura("");
        instance.faiAssunzione(nuoveAssunzioni);
        alberoTest.faiAssunzione(nuoveAssunzioni);
        assertEquals(alberoTest.toString(), instance.toString());
        nuoveAssunzioni = new Mappatura("a > b, a > e");
        instance.faiAssunzione(nuoveAssunzioni);
        alberoTest.faiAssunzione(nuoveAssunzioni);
        assertEquals(alberoTest.toString(), instance.toString());
    }

    /**
     * Test of undo method, of class AlberoIpotesi. Vengono verificate le
     * seguenti situazioni: 1. una undo su un albero con nessuna assunzione
     * fatta non cambia lo stato dell'albero 2. una undo su un albero con una
     * ipotesi non rende l'albero uguale all'albero senza ipotesi... 3. ... ma
     * la mappatura delll'ipotesi corrente è la stessa, cioè la mappatura vuota
     */
    @Test
    public void testUndo() {
        System.out.println("undo");
        String motivazione = "prova";
        AlberoIpotesi instance = new AlberoIpotesi();
        instance.undo(motivazione);
        assertEquals(instance.toString(), alberoTest.toString());
        alberoTest.faiAssunzione(new Mappatura("a > b"));
//      assertFalse(alberoTest.toString().equals(instance.toString()));
        alberoTest.undo(motivazione);
        assertFalse(alberoTest.toString().equals(instance.toString()));
        assertTrue(alberoTest.getIpotesiCorrente().assunzioni.toStringa().equals(instance.getIpotesiCorrente().assunzioni.toStringa()));
    }

    /**
     * Test of getMappaturaCorrente method, of class AlberoIpotesi. Similmente a
     * faiAssunzione, viene richiamato il metodo faiAssunzione su due alberi
     * inizialmente vuoti. Si effettuano i seguenti controlli, tramite il metodo
     * toStringa(): - la mappaturacorrente dei due alberi è uguale quando ancora
     * non sono state fatte ipotesi? - la mappaturacorrente dei due alberi è
     * uguale quando viene fatta la stessa ipotesi su entrambi? - la mappatura
     * dei due alberi è differente quando uno dei due alberi ha un'ipotesi che
     * l'altro non ha?
     */
    @Test
    public void testGetMappaturaCorrente() {
        System.out.println("getMappaturaCorrente");
        AlberoIpotesi instance = new AlberoIpotesi();
        Mappatura nuovaMappatura = new Mappatura("");
        alberoTest.faiAssunzione(nuovaMappatura);
        instance.faiAssunzione(nuovaMappatura);
        Mappatura result = instance.getMappaturaCorrente();
        Mappatura expResult = alberoTest.getMappaturaCorrente();
        assertEquals(expResult.toStringa(), result.toStringa());
        nuovaMappatura = new Mappatura("a > b, a > c");
        alberoTest.faiAssunzione(nuovaMappatura);
        instance.faiAssunzione(nuovaMappatura);
        result = instance.getMappaturaCorrente();
        expResult = alberoTest.getMappaturaCorrente();
        assertEquals(expResult.toStringa(), result.toStringa());
        nuovaMappatura = new Mappatura("c > e");
        instance.faiAssunzione(nuovaMappatura);
        result = instance.getMappaturaCorrente();
        assertFalse(result.toStringa().equals(expResult.toStringa()));
    }

    /**
     * Test of giaRaggiunta method, of class AlberoIpotesi. Vengono testati i
     * seguenti casi: 1. In un albero con una sola ipotesi, l'ipotesi con la
     * stessa mappatura risulta già raggiunta? 2. Un'ipotesi con mappatura
     * diversa per un solo caso da una già presente risulta già raggiunta? 3.
     * L'ipotesi con mappatura vuota risulta già raggiunta?
     */
    @Test
    public void testGiaRaggiunta() {
        System.out.println("giaRaggiunta");
        alberoTest.faiAssunzione(new Mappatura("a > b, b > c"));
        alberoTest.undo("prova");
        assertNotNull(alberoTest.giaRaggiunta(new Mappatura("a > b, b > c")));
        assertNull(alberoTest.giaRaggiunta(new Mappatura("a > c, b> c")));
        assertNotNull(alberoTest.giaRaggiunta(new Mappatura("")));
    }

    /**
     * Test of getAlbero method, of class AlberoIpotesi.
     */
    @Test
    public void testGetAlbero() {
        System.out.println("getAlbero");
        AlberoIpotesi instance = new AlberoIpotesi();
        Ipotesi expResult = null;
        Ipotesi result = instance.getAlbero();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stampaAlbero method, of class AlberoIpotesi.
     */
    @Test
    public void testStampaAlbero() {
        System.out.println("stampaAlbero");
        AlberoIpotesi instance = new AlberoIpotesi();
        instance.stampaAlbero();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCommento method, of class AlberoIpotesi.
     */
    @Test
    public void testGetCommento() {
        System.out.println("getCommento");
        AlberoIpotesi instance = new AlberoIpotesi();
        String expResult = "commento";
        instance.getMosse().peek().setCommento(expResult);
        String result = instance.getCommento();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class AlberoIpotesi.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        AlberoIpotesi instance = new AlberoIpotesi();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMosse method, of class AlberoIpotesi.
     */
    @Test
    public void testGetMosse() {
        System.out.println("getMosse");
        AlberoIpotesi instance = new AlberoIpotesi();
        Stack<Ipotesi> expResult = null;
        Stack<Ipotesi> result = instance.getMosse();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIpotesiCorrente method, of class AlberoIpotesi.
     */
    @Test
    public void testGetIpotesiCorrente() {
        System.out.println("getIpotesiCorrente");
        AlberoIpotesi instance = new AlberoIpotesi();
        Ipotesi expResult = null;
        Ipotesi result = instance.getIpotesiCorrente();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of testVecchio method, of class AlberoIpotesi.
     */
    @Test
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
