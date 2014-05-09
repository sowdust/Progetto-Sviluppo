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
import java.util.Arrays;
import java.util.List;


/**
 * 
 * 
 * NOTE:        decidere come definire i nomi (costanti) dei sistemi
 *              di cifrature
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
    
    private final char[] alfabeto = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
        's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private final String[] SISTEMI_DI_CIFRATURA = {
        "pseudocasuale",
        "cesare",
        "parolachiave"
    };
    
    public SistemaCifratura(String chiave, String metodo) {
        if(!Arrays.asList(SISTEMI_DI_CIFRATURA).contains(metodo)) {
            throw new IllegalArgumentException("metodo non valido");
        }
        this.metodo = metodo;
        this.chiave = chiave;
        calcolatore = CalcolatoreMappatura.create(alfabeto, metodo);
        this.mappatura = calcolatore.calcola(chiave);
    }
    
    public static List<SistemaCifratura> caricaSistemiCifratura(Studente st) throws SQLException {
        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT chiave, metodo FROM crypto_user.SistemaCifratura WHERE creatore = " + st.getId());
        List<SistemaCifratura> lista = new ArrayList<>();
        while(rs.next()) {
            lista.add(new SistemaCifratura(rs.getString("chiave"),rs.getString("metodo")));
        }
        return lista;
    }
    
    public static SistemaCifratura load(Studente user1, Studente user2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
     
    /* QueryResult? magari ResultSet */
    public SistemaCifratura(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String prova(String testo) {
        return Cifratore.cifraMonoalfabetica(mappatura, testo);
    }
    
    public void calcolaMappatura() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean save() {
        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT chiave, metodo FROM crypto_user.SistemaCifratura WHERE creatore = " + st.getId());
        List<SistemaCifratura> lista = new ArrayList<>();
        while(rs.next()) {
            lista.add(new SistemaCifratura(rs.getString("chiave"),rs.getString("metodo")));
        }
        return lista;
        

    }

}
