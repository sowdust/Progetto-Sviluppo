/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptohelper.model;

import cryptohelper.controller.DBController;
import java.sql.SQLException;
import java.util.Stack;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * La classe AlberoIpotesiTest implementa uno Unit Testing della classe
 * AlberoIpotesi. La gerarchia dei test è: giaRaggiunta -> faiAssunzione -> load
 *                                                                      -> getMappaturaCorrente
 *                                                                      -> undo
 * Assioma è che AlberoIpotesi.toString non sia un metodo-truffa
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
     * Test of load method, of class AlberoIpotesi. Viene richiamato il metodo load di due alberi
     * su cui sono state compiute tramite GUI le stesse assunzioni, e ci si chiede se questi due
     * abbiano la stessa rappresentazione su stringa.
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
     * Test of faiAssunzione method, of class AlberoIpotesi. Il metodo faiAssunzione
     * viene eseguito su due oggetti AlberoIpotesi. Dopo ogni assunzione si controlla che
     * gli oggetti abbiano la stessa rappresentazione su stringa, che è caratterizzante
     * dello stato delle Ipotesi. 
     * Le assunzioni fatte sono:
     * 1. a > b 
     * 2. a > b, a > e
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
     * Test of undo method, of class AlberoIpotesi. Vengono verificate le seguenti situazioni:
     * 1. una undo su un albero con nessuna assunzione fatta non cambia lo stato dell'albero
     * 2. una undo su un albero con una ipotesi non rende l'albero uguale all'albero senza ipotesi...
     * 3. ... ma la mappatura delll'ipotesi corrente è la stessa, cioè la mappatura vuota
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
     * Test of getMappaturaCorrente method, of class AlberoIpotesi. Similmente a faiAssunzione,
     * viene richiamato il metodo faiAssunzione su due alberi inizialmente vuoti. Si effettuano 
     * i seguenti controlli, tramite il metodo toStringa():
     * - la mappaturacorrente dei due alberi è uguale quando ancora non sono state fatte ipotesi?
     * - la mappaturacorrente dei due alberi è uguale quando viene fatta la stessa ipotesi su entrambi?
     * - la mappatura dei due alberi è differente quando uno dei due alberi ha un'ipotesi che l'altro non ha?
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
     * Test of giaRaggiunta method, of class AlberoIpotesi. Vengono testati i seguenti casi:
     * 1. In un albero con una sola ipotesi, l'ipotesi con la stessa mappatura risulta già raggiunta?
     * 2. Un'ipotesi con mappatura diversa per un solo caso da una già presente risulta già raggiunta?
     * 3. L'ipotesi con mappatura vuota risulta già raggiunta?
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
        String expResult = "";
        String result = instance.getCommento();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
    public void testTestVecchio() {
        System.out.println("testVecchio");
        AlberoIpotesi instance = new AlberoIpotesi();
        instance.testVecchio();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
