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
 * NOTE: i metodi sono dichiarati statici in quanto non vedo (Mattia V.) motivo
 * di fare altrimenti da valutare se conviene fare così oppure no
 *
 * la cifratura viene fatta eliminando spazi e segni di punteggiatura
 *
 * !! da aggiungere alla documentazione il metodo Mappatura.inAlphabet
 *
 * usare string buffer al posto di +=
 *
 * @author mat
 */
public class Cifratore {

    public static String cifraMonoalfabetica(Mappatura mappa, String testo) {
        StringBuilder testoCifrato = new StringBuilder(testo.length());

        for (char c : testo.toCharArray()) {
            char k = Character.toLowerCase(c);
            if (mappa.inAlphabet(k)) {
                testoCifrato.append(mappa.map(k));
            }
        }

        return testoCifrato.toString();
    }

    public static String decifraMonoalfabetica(Mappatura mappa, String testo) {
        StringBuilder testoDeCifrato = new StringBuilder(testo.length());

        for (char c : testo.toCharArray()) {
            char k = Character.toLowerCase(c);
            if (mappa.inAlphabet(k)) {
                testoDeCifrato.append(mappa.inverseMap(k));
            }
        }

        return testoDeCifrato.toString();
    }

}
