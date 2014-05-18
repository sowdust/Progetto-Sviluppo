/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptohelper.model;

import cryptohelper.model.Mappatura;
import cryptohelper.model.CalcolatoreParolachiave;
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
public class CalcolatoreParolachiaveTest {

    public CalcolatoreParolachiaveTest() {
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
     * Test of calcola method, of class CalcolatoreParolachiave.
     */
    @Test
    public void testCalcola() {
        System.out.println("calcola");
        char[] alfabeto = {'a', 'b', 'c', 'd', 'e'};
        CalcolatoreParolachiave instance = new CalcolatoreParolachiave();
        assertEquals(new Mappatura(new char[]{'e', 'c', 'b', 'a', 'd'}, alfabeto), instance.calcola("eebcb", alfabeto));
        assertEquals(new Mappatura(new char[]{'c', 'a', 'd', 'e', 'b'}, alfabeto), instance.calcola("cade", alfabeto));
        assertEquals(new Mappatura(new char[]{'d', 'c', 'a', 'b', 'e'}, alfabeto), instance.calcola("dcc", alfabeto));
        assertEquals(new Mappatura(new char[]{'e', 'b', 'c', 'd', 'a'}, alfabeto), instance.calcola("ebcda", alfabeto));
    }

}
