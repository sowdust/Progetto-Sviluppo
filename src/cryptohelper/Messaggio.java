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

/**
 *
 * @author glaxy
 */
public class Messaggio implements MessaggioMittente, MessaggioDestinatario {
    /** 
     * NOTE:
     * controllare il costruttore con resultSet che ora assomiglia a quello di Proposta
     * per come gestisce la creazione di UserInfo. Per Proposta funzionava.
     */
    private int id;
    private String testo;
    private String testoCifrato;
    private String lingua;
    private String titolo;
    private boolean bozza;
    private boolean letto;
    private UserInfo mittente;
    private UserInfo destinatario;
    private SistemaCifratura sdc;
    
    private Messaggio(ResultSet queryResult) throws SQLException {
        DBController dbc = DBController.getInstance();
        id = queryResult.getInt("Id");
        testo = queryResult.getString("Testo");
        testoCifrato = queryResult.getString("TestoCifrato");
        lingua = queryResult.getString("Lingua");
        titolo = queryResult.getString("Titolo");
        bozza = queryResult.getBoolean("Bozza");
        letto = queryResult.getBoolean("Letto");
        ResultSet rs1 = dbc.execute("SELECT * FROM crypto_user.Studente WHERE id = "+queryResult.getInt("mittente"));
        ResultSet rs2 = dbc.execute("SELECT * FROM crypto_user.Studente WHERE id = "+queryResult.getInt("destinatario"));
        this.mittente = new UserInfo(rs1);
        this.destinatario = new UserInfo(rs2);
    }
    
    public static Messaggio load(int id) throws SQLException {
        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT * FROM crypto_user.Messaggio WHERE id = " + id);
        rs.next();
        return new Messaggio(rs);
    }

    public static List<MessaggioMittente> caricaInviati(Studente studente) throws SQLException {
        DBController dbc = DBController.getInstance();
        int studentId = studente.getId();
        ResultSet rs = dbc.execute("SELECT * FROM crypto_user.Messaggio WHERE mittente = " + studentId + " AND bozza = " + false);
        List<MessaggioMittente> listaInviati = new ArrayList<>();
        while(rs.next()) {
            listaInviati.add(new Messaggio(rs));
        }
        return listaInviati;
    }
    
    public static List<MessaggioMittente> caricaBozze(Studente studente) throws SQLException {
        DBController dbc = DBController.getInstance();
        int studentId = studente.getId();
        ResultSet rs = dbc.execute("SELECT * FROM crypto_user.Messaggio WHERE mittente = " + studentId + " AND bozza = " + true);
        List<MessaggioMittente> listaBozze = new ArrayList<>();
        while(rs.next()) {
            listaBozze.add(new Messaggio(rs));
        }
        return listaBozze;
    }
    
    public static List<MessaggioDestinatario> caricaRicevuti(Studente studente) throws SQLException {
        DBController dbc = DBController.getInstance();
        int studentId = studente.getId();
        ResultSet rs = dbc.execute("SELECT * FROM crypto_user.Messaggio WHERE destinatario = " + studentId);
        List<MessaggioDestinatario> listaRicevuti = new ArrayList<>();
        while(rs.next()) {
            listaRicevuti.add(new Messaggio(rs));
        }
        return listaRicevuti;
    }
    
    /*
    NOTA:
    
        Sempre da decidere bene che fare con queste benedette eccezioni
    */
    @Override
    public void cifra() {
        if(null == sdc) {
            try {
                sdc = SistemaCifratura.load(mittente,destinatario);
            } catch (SQLException ex) {
                throw new RuntimeException("SQL exception: " + ex.getMessage());
            }
        }
        testo = Cifratore.cifraMonoalfabetica(sdc.getMappatura(),testo);
    }
    
    @Override
    public void decifra() {
        if(null == sdc) {
            try {
                sdc = SistemaCifratura.load(mittente,destinatario);
            } catch (SQLException ex) {
                throw new RuntimeException("SQL exception: " + ex.getMessage());
            }
        }
        testo = Cifratore.decifraMonoalfabetica(sdc.getMappatura(),testo);
    }

    @Override
    public boolean isBozza() {
        return bozza;
    }

    @Override
    public boolean save() {
        try {
            DBController dbc = DBController.getInstance();
            dbc.execute("UPDATE crypto_user.Messaggio"
                    + "SET crypto_user.Messaggio.testo = '"+this.getTesto()
                    + "SET crypto_user.Messaggio.testocifrato = '"+this.getTestoCifrato()
                    + "SET crypto_user.Messaggio.bozza ="+this.bozza
                    + "SET crypto_user.Messaggio.lingua ="+this.getLingua()
                    + "SET crypto_user.Messaggio.titolo ="+this.getTitolo()
                    + "SET crypto_user.Messaggio.mittente ="+this.mittente.getId()
                    + "SET crypto_user.Messaggio.destinatario ="+this.destinatario.getId()); //TODO
            return true;
        } catch(SQLException e) {
            return false;
        }
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
    public boolean elimina() throws SQLException {
        DBController dbc = DBController.getInstance();
        return dbc.executeUpdate("DELETE * FROM crypto_user.Messaggio WHERE id = " + id);
    }

    @Override
    public boolean isLetto() {
        return letto;
    }
    
    @Override
    public void setLetto(boolean letto) {
        this.letto = letto;
    }
    
    public void setBozza(boolean isBozza) {
        bozza = isBozza();
    }
    
    @Override
    public boolean send() {
        setBozza(false);
        return save();
    }
    
}
