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
public class CalcolatoreParolachiave extends CalcolatoreMappatura {

    @Override
    public Mappatura calcola(String chiave, char[] alfabeto) {
        /* NOTE: bisogna assolutamente eliminare le doppie dalla chiave
                 controllare se la chiave è composta unicamente da simboli dell'alfabeto ?
        */
        char[] mappa = new char[alfabeto.length];
        int i = 0;
        int k = 0;
        while(i < chiave.length()) {
            mappa[i] = chiave.charAt(i);
            i++;
        }
        while(k < alfabeto.length) {
            if(chiave.indexOf(alfabeto[k]) == -1) {
                mappa[i] = alfabeto[k];
                i++;
            }
            k++;
        }
        return new Mappatura(mappa, alfabeto);
    }
}
