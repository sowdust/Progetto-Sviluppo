/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptohelper.model;

import java.io.IOException;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author mat
 */
public class SessioneTest {

    public SessioneTest() {
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
    public void test() throws SQLException, IOException, ClassNotFoundException {
        AlberoIpotesi albero = new AlberoIpotesi();
        Mappatura a = new Mappatura("a > z, b > w, c > y");
        Sessione sess = new Sessione(Studente.load(1).getUserInfo(), Messaggio.load(1));

        //  PRIMA ASSUNZIONE
        //  a > z, b > w, c > y
        sess.getAlbero().faiAssunzione(new Mappatura(a));
        Mappatura b = new Mappatura("d > x, e > u");
        sess.getAlbero().faiAssunzione(b);

        Mappatura c = new Mappatura("f > v");
        sess.getAlbero().faiAssunzione(c);

        sess.save();
        System.out.println(sess.getId());

        Sessione due = Sessione.load(1);
        System.out.println("test: ");
        due.getAlbero().stampaAlbero();
        System.out.println("Prima mossa: " + due.getAlbero().mosse.get(0).getMappatura());
    }

}
