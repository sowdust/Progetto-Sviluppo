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
public abstract class CalcolatoreMappatura {
    
<<<<<<< HEAD
    public static CalcolatoreMappatura create(char[] alfabeto, String metodo) {
        throw new UnsupportedOperationException("Not supported yet.");
=======
    public static CalcolatoreMappatura create(String metodo) {
        String className = "Calcolatore" + metodo;
        try {
            CalcolatoreMappatura cm = (CalcolatoreMappatura)(Class.forName(className)).newInstance();
            return cm;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            return null;
        }
>>>>>>> 96651f2c1614158e5c7d7bf61e54bca1121840a0
    }
    
    public abstract Mappatura calcola(String chiave, char[] alfabeto);
    
}
