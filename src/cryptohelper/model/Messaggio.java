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
public class Messaggio implements MessaggioMittente, MessaggioDestinatario, MessaggioSpia {

    /*
     * Campi caricati automaticamente:
     *      id, titolo, bozza, letto, mittente,destinatario
     * Campi caricati su richiesta:
     *      testo, testoCifrato, lingua, sdc
     */
    private int id;
    private String titolo;
    private boolean bozza;
    private boolean letto;
    private UserInfo mittente;
    private UserInfo destinatario;
    private String testo = null;
    private String testoCifrato = null;
    private String lingua = null;
    private SistemaCifratura sdc = null;

    /* costruttore usato quando si *carica* un messaggio */
    public Messaggio(CachedRowSet queryResult) throws SQLException {
        id = queryResult.getInt("id");
        titolo = queryResult.getString("titolo");
        bozza = queryResult.getBoolean("bozza");
        letto = queryResult.getBoolean("letto");
        mittente = UserInfo.load(queryResult.getInt("mittente"));
        destinatario = UserInfo.load(queryResult.getInt("destinatario"));
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
        CachedRowSet crs = dbc.execute("SELECT id, titolo, bozza, letto, mittente,destinatario FROM Messaggio WHERE id = ?", id);
        if (crs.next()) {
            return new Messaggio(crs);
        }
        return null;
    }

    public static List<MessaggioMittente> caricaInviati(Studente studente) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT id, titolo, bozza, letto, mittente,destinatario FROM Messaggio WHERE mittente = ? AND bozza = ? AND EliminatoMitt = ? ORDER BY id DESC", studente.getId(), false, false);
        List<MessaggioMittente> listaInviati = new ArrayList<>();
        while (crs.next()) {
            listaInviati.add(new Messaggio(crs));
        }
        return listaInviati;
    }

    public static List<MessaggioMittente> caricaBozze(Studente studente) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT id, titolo, bozza, letto, mittente,destinatario FROM Messaggio WHERE mittente = ? AND bozza = ? AND EliminatoMitt = ? ORDER BY id DESC", studente.getId(), true, false);
        List<MessaggioMittente> listaBozze = new ArrayList<>();
        while (crs.next()) {
            listaBozze.add(new Messaggio(crs));
        }
        return listaBozze;
    }

    public static List<MessaggioDestinatario> caricaRicevuti(Studente studente) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT id, titolo, bozza, letto, mittente,destinatario FROM Messaggio WHERE bozza = ? AND destinatario = ? AND EliminatoDest = ? ORDER BY letto DESC, id DESC", false, studente.getId(), false);
        List<MessaggioDestinatario> listaRicevuti = new ArrayList<>();
        while (crs.next()) {
            listaRicevuti.add(new Messaggio(crs));
        }
        return listaRicevuti;
    }

    private void getCampiAggiuntivi() throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT testo, testoCifrato, lingua, sdc FROM Messaggio WHERE id = ?", id);
        crs.next();
        this.testo = crs.getString("testo");
        this.testoCifrato = crs.getString("testoCifrato");
        this.lingua = crs.getString("lingua");
        this.sdc = SistemaCifratura.load(crs.getInt("sdc"));
    }

    @Override
    public void cifra() throws SQLException {
        Proposta attiva = Proposta.caricaAttiva(mittente.getId(), destinatario.getId());
        sdc = attiva.getSdc();
        testoCifrato = Cifratore.cifraMonoalfabetica(getSdc().getMappatura(), getTesto());
    }

    @Override
    public void decifra() throws SQLException {
        testo = Cifratore.decifraMonoalfabetica(getSdc().getMappatura(), getTestoCifrato());
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
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", getTesto(), getTestoCifrato(), getLingua(), titolo, bozza, letto, mittente.getId(), (destinatario != null ? destinatario.getId() : null), (sdc != null ? getSdc().getId() : null));
            return id != -1;
        }
        return dbc.executeUpdate("UPDATE Messaggio SET "
                + "testo = ?, testocifrato = ?, lingua = ?, titolo = ?, bozza = ?, letto = ?, mittente = ?, destinatario = ?, sdc = ?"
                + " WHERE id = ?", getTesto(), getTestoCifrato(), getLingua(), titolo, bozza, letto, mittente.getId(), (destinatario != null ? destinatario.getId() : null), (sdc != null ? sdc.getId() : null), id);
    }

    @Override
    public List<Character> getSimboli() throws SQLException {
        List<Character> simboli = new LinkedList();
        char[] cArray = getTestoCifrato().toCharArray();
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
    public String getTitolo() {
        return titolo;
    }

    @Override
    public String getTesto() throws SQLException {
        if (null == testo) {
            getCampiAggiuntivi();
        }
        return testo;
    }

    @Override
    public String getTestoCifrato() throws SQLException {
        if (null == testoCifrato) {
            getCampiAggiuntivi();
        }
        return testoCifrato;
    }

    @Override
    public String getLingua() throws SQLException {
        if (null == lingua) {
            getCampiAggiuntivi();
        }
        return lingua;
    }

    @Override
    public SistemaCifratura getSdc() throws SQLException {
        if (null == sdc) {
            getCampiAggiuntivi();
        }
        return sdc;
    }

    @Override
    public boolean elimina(UserInfo st) throws SQLException {
        DBController dbc = DBController.getInstance();
        if (st.getId() == mittente.getId()) {
            return dbc.executeUpdate("UPDATE Messaggio SET EliminatoMitt = ? WHERE id = ?", true, id);
        }
        return dbc.executeUpdate("UPDATE Messaggio SET EliminatoDest = ? WHERE id = ?", true, id);
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

    @Override
    public UserInfo getMittente() {
        return this.mittente;
    }

    @Override
    public UserInfo getDestinatario() {
        return this.destinatario;
    }

    @Override
    public String toString() {
        return "Mittente: " + mittente + "; Destinatario: " + destinatario + "; Titolo: " + titolo;
    }

}
