/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptohelper;

/**
 *
 * @author mat
 */
public class MappaturaTest {
    public static void main(String args[]) {
        char[] m = {
        'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
        's', 't', 'u', 'v', 'w', 'x', 'y', 'z','a'
        };
        Mappatura map = new Mappatura(m);
        System.out.println(map);
        System.out.println(map.map('a'));
        System.out.println(map.inverseMap('a'));
        System.out.println();
        System.out.println(map.map('z'));
        System.out.println(map.inverseMap('z'));
        System.out.println(map.isInAlphabet('!'));
        System.out.println(map.isInAlphabet('k'));
    }
}
