/*
 * Copyright (C) 2014 glaxy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cryptohelper;

/**
 *
 * @author glaxy
 */
public class CalcolatoreMappaturaTest {
    public static void main(String[] args) {
        char[] alfabeto = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };
        CalcolatoreMappatura cesare = CalcolatoreMappatura.create("cesare");
        System.out.println("Cesare:\n" + cesare.calcola("5", alfabeto));
        
        CalcolatoreMappatura pseudocasuale = CalcolatoreMappatura.create("pseudocasuale");
        System.out.println("Pseudocasuale:\n" + pseudocasuale.calcola("5", alfabeto));
        
        CalcolatoreMappatura parolachiave = CalcolatoreMappatura.create("parolachiave");
        System.out.println("Parolachiave:\n" + parolachiave.calcola("pallacanestro", alfabeto));
    }

}
