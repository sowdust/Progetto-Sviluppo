/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptohelper.model;

import cryptohelper.model.MappaturaImpl;
import cryptohelper.model.Cifratore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author glaxy
 */
public class CifratoreTest {

    public CifratoreTest() {
    }

    private MappaturaImpl mappa;
    private String testo;
    private String testoCifrato;
    private String testoDecifrato;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        mappa = new MappaturaImpl(
                new char[]{
                    'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                    'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                    's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a'
                },
                new char[]{
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                    'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                    's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
                }
        );
        testo = "ciao come va?";
        testoCifrato = "djbpdpnfwb";
        testoDecifrato = "ciaocomeva";

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of cifraMonoalfabetica method, of class Cifratore.
     */
    @Test
    public void testCifraMonoalfabetica() {
        System.out.println("cifraMonoalfabetica");
        assertEquals(testoCifrato, Cifratore.cifraMonoalfabetica(mappa, testo));
    }

    /**
     * Test of decifraMonoalfabetica method, of class Cifratore.
     */
    @Test
    public void testDecifraMonoalfabetica() {
        System.out.println("decifraMonoalfabetica");
        assertEquals(testoDecifrato, Cifratore.decifraMonoalfabetica(mappa, testoCifrato));
    }

}
