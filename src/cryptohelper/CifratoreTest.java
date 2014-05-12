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
public class CifratoreTest {
    
    public static void main(String args[]) {
        char[] alfabeto = {
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };
        
        char[] mappa = {
                'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a'
        };
        Mappatura map = new Mappatura(mappa, alfabeto);
        String testo = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris.";
        System.out.println("Testo:");
        System.out.println(testo);
        System.out.println("Mappatura:");
        System.out.println(map);
        System.out.println("Testo cifrato:");
        String cifrato = Cifratore.cifraMonoalfabetica(map,testo);
        System.out.println(cifrato);
        System.out.println("Testo decifrato:");
        System.out.println(Cifratore.decifraMonoalfabetica(map,cifrato));

        
    }
}
