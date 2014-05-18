/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptohelper.model;

import cryptohelper.model.Mappatura;
import cryptohelper.model.CalcolatoreCesare;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author glaxy
 */
public class CalcolatoreCesareTest {

    public CalcolatoreCesareTest() {
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

    /*
     @Rule
     public ExpectedException thrown = ExpectedException.none();
     */
    /**
     * Test of calcola method, of class CalcolatoreCesare.
     */
    @Test
    public void testCalcola() {
        System.out.println("calcola");
        char[] alfabeto = {'a', 'b', 'c', 'd', 'e'};
        CalcolatoreCesare instance = new CalcolatoreCesare();
        assertEquals(new Mappatura(new char[]{'b', 'c', 'd', 'e', 'a'}, alfabeto), instance.calcola("1", alfabeto));
        assertEquals(new Mappatura(new char[]{'c', 'd', 'e', 'a', 'b'}, alfabeto), instance.calcola("2", alfabeto));
        assertEquals(new Mappatura(new char[]{'d', 'e', 'a', 'b', 'c'}, alfabeto), instance.calcola("3", alfabeto));
        assertEquals(new Mappatura(new char[]{'e', 'a', 'b', 'c', 'd'}, alfabeto), instance.calcola("4", alfabeto));
        /*
         thrown.expect(IllegalArgumentException.class);
         thrown = ExpectedException.none();
         instance.calcola("0", alfabeto);
         System.out.println("ciao");
         instance.calcola("5", alfabeto);
         instance.calcola("cane", alfabeto);
         instance.calcola("5 cani", alfabeto);
         */

    }

}
