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

public class Cifratore {

    public static String cifraMonoalfabetica(Mappatura mappa, String testo) {
        StringBuilder testoCifrato = new StringBuilder(testo.length());
        for (Character c : testo.toCharArray()) {
            Character k = mappa.map(Character.toLowerCase(c));
            if (k != null) {
                testoCifrato.append(k);
            }
        }
        return testoCifrato.toString();
    }

    public static String decifraMonoalfabetica(Mappatura mappa, String testo) {
        StringBuilder testoDecifrato = new StringBuilder(testo.length());
        for (Character c : testo.toCharArray()) {
            Character k = mappa.inverseMap(Character.toLowerCase(c));
            if (k != null) {
                testoDecifrato.append(k);
            } else {
                testoDecifrato.append("_");
            }
        }
        return testoDecifrato.toString();
    }

}
