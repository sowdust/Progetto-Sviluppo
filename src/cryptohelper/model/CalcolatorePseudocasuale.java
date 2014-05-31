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
package cryptohelper.model;

import java.util.Random;

/**
 *
 * @author glaxy
 */
public class CalcolatorePseudocasuale extends CalcolatoreMappatura {

    @Override
    public Mappatura calcola(String chiaveStr, char[] alfabeto) {
        int chiave;
        try {
            chiave = Integer.parseInt(chiaveStr);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("non hai inserito un numero");
        }
        Random rnd = new Random(chiave);
        char[] mappa = alfabeto.clone();
        for (int i = 0; i < alfabeto.length; i++) {
            int irnd = i + rnd.nextInt(alfabeto.length - i);
            char tmp = mappa[irnd];
            mappa[irnd] = mappa[i];
            mappa[i] = tmp;
        }
        return new Mappatura(mappa, alfabeto);
    }

}
