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
public class Proposta {
    
    private String stato;
    private boolean notificata;
    private UserInfo proponente;
    private UserInfo partner;
    
    /* tipo di sec? forse è sdc: sistema di cifratura */
    public Proposta(Studente user, Studente partner, String sdc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Proposta(ResultSet queryResult) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static void load(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static void caricaAttiva(int idProp, int idPartner) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
