/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptohelper.model;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void prova() {
        System.out.println("Inizio test sessione");
        Sessione sess = new Sessione(null,null);
        ArrayList map = new ArrayList<>();
        map.add('a');
        map.add('b');
        ArrayList inversemap = new ArrayList<>();
        inversemap.add('z');
        inversemap.add('x');
        ArrayList mapp = new ArrayList<>();
        mapp.add('b');
        mapp.add('a');
        ArrayList inversemapp = new ArrayList<>();
        inversemapp.add('x');
        inversemapp.add('z');
        ArrayList map2 = new ArrayList<>();
        map2.add('c');
        map2.add('d');
        map2.add('a');
        ArrayList inversemap2 = new ArrayList<>();
        inversemap2.add('w');
        inversemap2.add('r');        
        inversemap2.add('t');     
        MappaturaParziale m = new MappaturaParziale(map, inversemap);
        MappaturaParziale m2 = new MappaturaParziale(map2, inversemap2);
        MappaturaParziale mm = new MappaturaParziale(mapp, inversemapp);
        System.out.println("m: " + m);
        System.out.println("m2: " + m2);
        System.out.println("mm: " + mm);
        
        assertEquals((m.equals(m2)),false);
        assertEquals((m2.equals(m)),false);
        assertEquals((mm.equals(m)),true);
        
        sess.aggiungiIpotesi(m);
        sess.ricalcolaMappatura();
        //System.out.println(sess.mappaturaCorrente);
        sess.aggiungiIpotesi(m2);
        sess.ricalcolaMappatura();
        //System.out.println(sess.mappaturaCorrente);

        sess.aggiungiIpotesi(m2);

        //System.out.println(m);
        //System.out.println(m2);
        //System.out.println(m2.giaDefinita(m));
        //System.out.println(m2.giaAssegnata(m));
        

        
        //System.out.println(m.equals(mm));
        //System.out.println(mm);
        //System.out.print("\t \t");
        //System.out.println(m);
        //System.out.println("\t prova!!");
        //System.out.println(sess.ipotesiCorrente);
        //sess.ipotesiCorrente.aggiungiIpotesi(m);
        //System.out.println(sess.mappaturaCorrente);
        
        sess.radice.stampa(0);
        
    }
}
