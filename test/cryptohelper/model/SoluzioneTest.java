/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptohelper.model;

import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author mat
 */
public class SoluzioneTest {

    public SoluzioneTest() {
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
    public void Soluzione() throws SQLException {
        Mappatura m = new Mappatura("a > z, b > w, c > y");
        Soluzione s = new Soluzione(m, Messaggio.load(1), Studente.load(1).getUserInfo());

        s.save();

        s = Soluzione.load(1);
        System.out.println(s.getMappatura());

    }
}
