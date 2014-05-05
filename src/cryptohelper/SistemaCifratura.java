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

import java.sql.ResultSet;

/**
 *
 * @author glaxy
 */
public class SistemaCifratura {
    
    private int id;
    private String chiave;
    private String metodo;
    private CalcolatoreMappatura calcolatore;
    private Mappatura mappatura;
    private Proposta proposta;
    private UserInfo creatore;
    
    /* void? */
    public static void caricaSistemiCifratura(Studente st) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static SistemaCifratura load(Studente user1, Studente user2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public SistemaCifratura(String chiave, String metodo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /* QueryResult? magari ResultSet */
    public SistemaCifratura(ResultSet queryResult) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String prova(String testo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void calcolaMappatura() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
