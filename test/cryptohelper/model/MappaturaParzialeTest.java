/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptohelper.model;

import java.util.ArrayList;
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
public class MappaturaParzialeTest {
    
    public MappaturaParzialeTest() {
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
        ArrayList map = new ArrayList<>();
        map.add('a');
        map.add('b');
        ArrayList inversemap = new ArrayList<>();
        inversemap.add('b');
        inversemap.add('a');
        
        MappaturaParziale m = new MappaturaParziale(map, inversemap);
        System.out.println(m);
    }
}
