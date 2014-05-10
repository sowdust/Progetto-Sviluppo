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
 *              load(mittente, destinatario) al posto di load(st1, st2)
 *              in quanto viene chiamata da "Messaggio"
 * 
 *              se per fornirli alla spia vogliamo caricare tutti i sdc tra st1 e st2 
 *              abbiamo bisogno di altro metodo (da implementare)
 * 
 *              pensare di mettere gli stati di "proposta" in un array e di poterli
 *              richiamare con dei metodi. Non molto bello dover scrivere 
 *                  stato = "accepted" in una query. 
 * 
 *              il creatore come e quando viene impostato??
 *
 * @author mat
 */
public class SistemaCifratura {
    
    private int id;
    private String chiave;
    private String metodo;
    private CalcolatoreMappatura calcolatore;
    private Mappatura mappatura;
    private UserInfo creatore;
    /**
     *  NOTE: che se ne fa il sistema di cifratura di un oggetto proposta??
     */
    private Proposta proposta;
    
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
    
    /*
    NOTE: davvero utile?
    */
    public SistemaCifratura(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet.");
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
    /*
    NOTE:
        da qualche parte dobbiamo mettere un controllo per essere sicuri che ci
        sia sempre solo al massimo una proposta attiva tra a -> b e b -> a
        qua non mi pare il luogo adatto. Forse in Proposta ? 
    */
    public static SistemaCifratura load(Studente mittente, Studente destinatario) throws SQLException {

        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT chiave, metodo FROM crypto_user.Proposta WHERE proponente = " + mittente.getId() + "AND destinatario = " + destinatario.getId() + "AND stato = 'accepted' ");
        return new SistemaCifratura(rs.getString("chiave"), rs.getString("metodo"));
    }
     
    public String prova(String testo) {
        return Cifratore.cifraMonoalfabetica(mappatura, testo);
    }
    
    /**
     * NOTE:
     * probabilmente da modificare e da usare piuttosto ogni qual volta
     * un utente fa una nuova ipotesi e va aggiornata la nuova mappatura?
     */
    public void calcolaMappatura() {
        this.mappatura = calcolatore.calcola(chiave);
    }
    
    public void save() throws SQLException {
        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("INSERT INTO crypto_user.SistemaCifratura (creatore, metodo, chiave), VALUES(" + creatore.getId() + ", " + metodo + ", " + chiave + ")");
    }

}
