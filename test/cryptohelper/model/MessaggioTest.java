package cryptohelper.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cryptohelper.model.Messaggio;
import java.sql.SQLException;
import java.util.List;
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
public class MessaggioTest {
    
    public MessaggioTest() {
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

    @Test
    public void simboli() throws SQLException {
        Messaggio m = Messaggio.load(1);
        List<Character> simboli = m.getSimboli();
        for(char c : simboli) {
            System.out.println(c + " ");
        }
        
        System.out.println("Mess:");
        System.out.println(m.getTesto());
    }
}
