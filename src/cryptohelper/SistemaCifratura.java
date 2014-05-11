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
 * NOTE:        il creatore come e quando viene impostato??
 * 
 *              essendoci tanti modi diversi di costruire un sdc, forse si potrebbe usare un pattern tipo factory?
 *
 * @author mat
 */
public class SistemaCifratura {
    
    private Integer id;
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

    /*
        leggermente diverso dal DSD che chiama prima un metodo 
            SdC.load(id)
        che restituiva un queryResult e poi con questo
            un SdC.new(queryResult)
        che restituiva un nuovo SdC (se ho capito bene il diagramma)
    */
    public SistemaCifratura(Integer id) throws SQLException {
        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT * FROM crypto_user.SistemaCifratura WHERE id = " + id);
        this.creatore = new Studente(rs.getInt("creatore")).getUserInfo();
        this.metodo = rs.getString("metodo");
        this.chiave = rs.getString("chiave");
        this.id = new Integer(id);
        this.calcolatore = CalcolatoreMappatura.create(alfabeto, metodo);
        this.mappatura = calcolatore.calcola(chiave);
    }
    
    public SistemaCifratura(String chiave, String metodo, UserInfo st) {
        
        if(!Arrays.asList(SISTEMI_DI_CIFRATURA).contains(metodo)) {
            throw new IllegalArgumentException("Sistema di Cifratura non valido");
        }
        this.metodo = metodo;
        this.chiave = chiave;
<<<<<<< HEAD
        this.calcolatore = CalcolatoreMappatura.create(alfabeto, metodo);
        this.mappatura = calcolatore.calcola(chiave);
        this.creatore = st;
    }

    public SistemaCifratura(String chiave, String metodo) {
        
        this(chiave, metodo, (UserInfo) null);
=======
        calcolatore = CalcolatoreMappatura.create(metodo);
        this.mappatura = calcolatore.calcola(chiave, alfabeto);
>>>>>>> 96651f2c1614158e5c7d7bf61e54bca1121840a0
    }
    
    public SistemaCifratura(String chiave, String metodo, Studente st) {
        
        this(chiave, metodo, st.getUserInfo());
    }
    
    public final String[] getNomi() {
        return SISTEMI_DI_CIFRATURA;
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
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet.");
=======
        this.mappatura = calcolatore.calcola(chiave, alfabeto);
>>>>>>> 96651f2c1614158e5c7d7bf61e54bca1121840a0
    }
    
    public void save() throws SQLException {
        if(null == creatore) {
            throw new RuntimeException("Non è possibile salvare un sistema di cifratura senza associarvi un valido utente creatore");
        }
        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("INSERT INTO crypto_user.SistemaCifratura (creatore, metodo, chiave), VALUES(" + creatore.getId() + ", " + metodo + ", " + chiave + ")");
    }

    
    /**
     * NOTE:
     * si basa sul valore di ritorno di executeUpdate che però potrebbe diventare int
     */
    public boolean elimina() throws SQLException {
        DBController dbc = DBController.getInstance();
        
        if( id != null ) {
            return dbc.executeUpdate("DELETE FROM crypto_user.SistemaCifratura WHERE id = " + id );        
        } else {
            return dbc.executeUpdate("DELETE FROM crypto_user.SistemaCifratura WHERE creatore = " + creatore.getId() + " AND metodo = " + metodo + " AND chiave = " + chiave);
        }
    }

}
