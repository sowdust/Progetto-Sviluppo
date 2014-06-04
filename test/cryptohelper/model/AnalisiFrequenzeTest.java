/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptohelper.model;

import java.sql.SQLException;
import java.util.Map;
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
public class AnalisiFrequenzeTest {

    public AnalisiFrequenzeTest() {
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
     * Test of get_frequency method, of class AnalisiFrequenze.
     */
    @Test
    public void testGetFrequency() throws SQLException {
        System.out.println("get_frequency");
        Messaggio m = Messaggio.load(2);
        Map<Character, Double> expResult = null;
        Map<Character, Double> result = AnalisiFrequenze.getFrequency(m);
        Map<Character, Double> it = AnalisiFrequenze.getFrequency("Italiano");

        for (Map.Entry<Character, Double> entry : result.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
        }
        for (Map.Entry<Character, Double> entry : it.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
        }
    }

}
