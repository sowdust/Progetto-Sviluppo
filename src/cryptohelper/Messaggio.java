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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author glaxy
 */
public class Messaggio implements MessaggioMittente, MessaggioDestinatario {

    private int id;
    private String testo;
    private String testoCifrato;
    private String lingua;
    private String titolo;
    private boolean bozza;
    private boolean letto;
    
    private Messaggio(ResultSet queryResult) throws SQLException {
        queryResult.next();
        id = queryResult.getInt("Id");
        testo = queryResult.getString("Testo");
        testoCifrato = queryResult.getString("TestoCifrato");
        lingua = queryResult.getString("Lingua");
        titolo = queryResult.getString("Titolo");
        bozza = queryResult.getBoolean("Bozza");
        letto = queryResult.getBoolean("Letto");
    }
    
    public static Messaggio load(int id) throws SQLException {
        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT * FROM crypto_user.Messages WHERE Id = " + id);
        return new Messaggio(rs);
    }

    public static List<MessaggioMittente> caricaInviati(Studente studente) {       
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static List<MessaggioMittente> caricaBozze(Studente studente) {
        /*
        DBController dbc = DBController.getInstance();
        int studentId = studente.getId();
        ResultSet rs = dbc.execute("SELECT * FROM crypto_user.Messages WHERE Mittente = " + studentId + " AND Bozza = " + true);
        List<MessaggioMittente> drafts = new ArrayList<MessaggioMittente>();
        while(rs.next()) {
            rs
        }
        */
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static List<MessaggioDestinatario> caricaRicevuti(Studente studente) {       
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isBozza() {
        return bozza;
    }

    @Override
    public boolean save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cifra() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getTesto() {
        return testo;
    }

    @Override
    public String getTestoCifrato() {
        return testoCifrato;
    }

    @Override
    public String getLingua() {
        return lingua;
    }

    @Override
    public String getTitolo() {
        return titolo;
    }

    @Override
    public boolean elimina() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isLetto() {
        return letto;
    }
    
}
