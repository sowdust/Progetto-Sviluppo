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

import cryptohelper.controller.DBController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author glaxy
 */
public class Messaggio implements MessaggioMittente, MessaggioDestinatario {

    /**
     * NOTE: controllare il costruttore con resultSet che ora assomiglia a
     * quello di Proposta per come gestisce la creazione di UserInfo. Per
     * Proposta funzionava.
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

    /* costruttore usato quando si *carica* un messaggio */
    private Messaggio(CachedRowSet queryResult) throws SQLException {
        id = queryResult.getInt("id");
        testo = queryResult.getString("testo");
        testoCifrato = queryResult.getString("testoCifrato");
        lingua = queryResult.getString("lingua");
        titolo = queryResult.getString("titolo");
        bozza = queryResult.getBoolean("bozza");
        letto = queryResult.getBoolean("letto");
        mittente = UserInfo.load(queryResult.getInt("mittente"));
        destinatario = UserInfo.load(queryResult.getInt("destinatario"));
        sdc = SistemaCifratura.load(queryResult.getInt("sdc"));
    }

    /* appena creo un messaggio è una bozza, se lo invio bozza=false */
    public Messaggio(Studente studente) {
        id = -1;
        testo = "";
        testoCifrato = "";
        lingua = "";
        titolo = "";
        bozza = true;
        letto = false;
        mittente = studente.getUserInfo();
        destinatario = null;
        sdc = null;
    }

    public static Messaggio load(int id) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Messaggio WHERE id = ?", id);
        if (crs.next()) {
            return new Messaggio(crs);
        }
        return null;
    }

    public static List<MessaggioMittente> caricaInviati(Studente studente) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Messaggio WHERE mittente = ? AND bozza = ?", studente.getId(), false);
        List<MessaggioMittente> listaInviati = new ArrayList<>();
        while (crs.next()) {
            listaInviati.add(new Messaggio(crs));
        }
        return listaInviati;
    }

    public static List<MessaggioMittente> caricaBozze(Studente studente) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Messaggio WHERE mittente = ? AND bozza = ?", studente.getId(), true);
        List<MessaggioMittente> listaBozze = new ArrayList<>();
        while (crs.next()) {
            listaBozze.add(new Messaggio(crs));
        }
        return listaBozze;
    }

    public static List<MessaggioDestinatario> caricaRicevuti(Studente studente) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM crypto_user.Messaggio WHERE destinatario = ?", studente.getId());
        List<MessaggioDestinatario> listaRicevuti = new ArrayList<>();
        while (crs.next()) {
            listaRicevuti.add(new Messaggio(crs));
        }
        return listaRicevuti;
    }

    /* riguardo ai DSD "cifraMessaggio" e "decifraMessaggio" sono presenti due note:
     in cifra si parla di assumere che sdc non ci sia in memoria, mentre in decifra
     si parla di assumere che il sdc sia caricato in memoria.
     Pensandoci su credo che si intendesse che non vi deve essere nessun controllo,
     usando sempre la load in cifra e non usandola mai in decifra. Il motivo penso che sia
     perchè noi cifriamo un messaggio dopo averlo *creato* perciò il sistema di cifratura
     non c'è ancora in memoria, mentre si decifra solo se lo si legge (quindi è già presente nel DB) ovvero
     dopo averlo *caricato* facendo la load (che evidentemente dovrà caricare in memoria anche il sdc).
     In ogni caso è tutto un forse, bisogna fare la GUI per capire come siamo messi
     */
    @Override
    public void cifra() throws SQLException {
        if (null == sdc) {
            sdc = SistemaCifratura.load(mittente, destinatario);
        }
        testoCifrato = Cifratore.cifraMonoalfabetica(sdc.getMappatura(), testo);
    }

    @Override
    public void decifra() throws SQLException {
        if (null == sdc) {
            sdc = SistemaCifratura.load(mittente, destinatario);
        }
        testo = Cifratore.decifraMonoalfabetica(sdc.getMappatura(), testoCifrato);
    }

    @Override
    public boolean isBozza() {
        return bozza;
    }

    @Override
    public boolean save() throws SQLException {
        DBController dbc = DBController.getInstance();
        if (id < 0) {
            id = dbc.executeInsert("INSERT INTO Messaggio (testo, testocifrato, lingua, titolo, bozza, letto, mittente, destinatario, sdc) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", testo, testoCifrato, lingua, titolo, bozza, letto, mittente.getId(), (destinatario != null ? destinatario.getId() : null), (sdc != null ? sdc.getId() : null));
            return id != -1;
        }
        return dbc.executeUpdate("UPDATE Messaggio SET "
                + "testo = ?, testocifrato = ?, lingua = ?, titolo = ?, bozza = ?, letto = ?, mittente = ?, destinatario = ?, sdc = ?"
                + " WHERE id = ?", testo, testoCifrato, lingua, titolo, bozza, letto, mittente.getId(), (destinatario != null ? destinatario.getId() : null), (sdc != null ? sdc.getId() : null), id);
    }

    public List<Character> getSimboli() {
        List<Character> simboli = new LinkedList();
        char[] cArray = testo.toCharArray();
        for (char c : cArray) {
            if (simboli.indexOf(c) == -1) {
                simboli.add(c);
            }
        }
        return simboli;
    }

    @Override
    public int getId() {
        return id;
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
        return dbc.executeUpdate("DELETE FROM Messaggio WHERE id = ?", id);
    }

    @Override
    public boolean isLetto() {
        return letto;
    }

    @Override
    public void setLetto(boolean letto) {
        this.letto = letto;
    }

    public void setBozza(boolean b) {
        bozza = b;
    }

    @Override
    public boolean send() throws SQLException {
        setBozza(false);
        return save();
    }

    @Override
    public String toString() {
        return (!letto ? "**" : "")
                + "Mittente: " + mittente
                + "; Destinatario: " + (destinatario == null ? "**nessuno**" : destinatario)
                + "; Titolo: " + (titolo.equals("") ? "**senza titolo**" : titolo);
    }

    public void setDestinatario(UserInfo destinatario) {
        this.destinatario = destinatario;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public UserInfo getMittente() {
        return this.mittente;
    }

    public UserInfo getDestinatario() {
        return this.destinatario;
    }

}
