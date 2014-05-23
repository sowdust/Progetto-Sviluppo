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
    public void testMappatura() {
        assertEquals(new MappaturaParziale("a > a,b>b, c     > c"), new MappaturaParziale("b>b,c>c,a>a"));
        MappaturaParziale a = new MappaturaParziale("a > z, b > w, c > y");
        MappaturaParziale b = new MappaturaParziale("d > x, e > u");
        a = a.merge(b);
        assertEquals(a, new MappaturaParziale("a > z, b > w, c > y,d > x, e > u") );
        a = new MappaturaParziale("a>z,b>x");
        b = new MappaturaParziale("a>x,b>z");
        assertFalse(a.equals(b));
        assertTrue(a.conflitto(b));
        a = a.merge(b);
        assertEquals(a,b);
        a = new MappaturaParziale("a>z,b>x,c>k");
        b = new MappaturaParziale("a>j");
        assertTrue(a.conflitto(b));
        a = a.merge(b);
        assertEquals(a,new MappaturaParziale("a>j,b>x,c>k"));
        a = new MappaturaParziale("a>z,b>x,c>k");
        b = new MappaturaParziale("j>x");
        assertTrue(a.conflitto(b));
        assertTrue(b.conflitto(a));
        a = a.merge(b);
        assertEquals(a,new MappaturaParziale("a>z,j>x,c>k"));
        a = new MappaturaParziale("a > z, b > w, c > y");
        b = new MappaturaParziale("a > z, b > w, c > y, e > i");
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
        assertEquals(a.merge(b),b.merge(a));
        
        assertEquals(b.sottrai(a),new MappaturaParziale("e>i"));
        assertEquals(b.sottrai(new MappaturaParziale("a > z, b > w, c > y, e > i")),new MappaturaParziale());

        
        assertTrue(new MappaturaParziale("a>s,b>y,c>z").subsetOf(new MappaturaParziale("a>s,b>y,c>z")));
        assertTrue(new MappaturaParziale("a>s,b>y").subsetOf(new MappaturaParziale("a>s,b>y,c>z")));
        assertTrue(new MappaturaParziale("a>s,b>y").subsetOf(new MappaturaParziale("a>s,b>y")));
        assertFalse(new MappaturaParziale("a>s,b>k").subsetOf(new MappaturaParziale("a>s,b>y")));
        assertFalse(new MappaturaParziale("a>s,b>k").subsetOf(new MappaturaParziale("a>s,b>y,c>z")));
        assertFalse(new MappaturaParziale("a>s,b>y,c>z,d>p").subsetOf(new MappaturaParziale("a>s,b>y,c>z")));
        assertFalse(new MappaturaParziale("a>s,b>z").subsetOf(new MappaturaParziale("a>s,b>y")));
        
    }
}
