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
public class CalcolatoreCesare extends CalcolatoreMappatura {

    @Override
    public Mappatura calcola(String chiaveStr, char[] alfabeto) {
        int chiave;
        try {
            chiave = Integer.parseInt(chiaveStr);
        } catch(NumberFormatException ex) {
            return null;
        }
        int l = alfabeto.length;
        if(chiave <= 0 || chiave >= l) {
            return null;
        }
        char[] mappa = new char[l];
        for(int i = 0; i < l; i++) {
            mappa[i] = alfabeto[(i + chiave)%l];
        }
        return new Mappatura(mappa, alfabeto);
    }
    
}
