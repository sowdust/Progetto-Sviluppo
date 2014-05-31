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
 * @author glaxy
 */
public class CalcolatorePseudocasualeTest {

    public CalcolatorePseudocasualeTest() {
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

    /**
     * Test of calcola method, of class CalcolatorePseudocasuale.
     */
    @Test
    public void testCalcola() {
        System.out.println("calcola");
        char[] alfabeto = {'a', 'b', 'c', 'd', 'e'};
        CalcolatorePseudocasuale instance = new CalcolatorePseudocasuale();
        assertEquals(new Mappatura(new char[]{'d', 'a', 'e', 'b', 'c'}, alfabeto), instance.calcola("33", alfabeto));
        assertEquals(new Mappatura(new char[]{'a', 'e', 'd', 'b', 'c'}, alfabeto), instance.calcola("0", alfabeto));
    }

}
