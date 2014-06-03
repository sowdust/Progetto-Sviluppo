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
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 * @author mat
 */
public class SistemaCifratura {

    private int id;
    private String chiave;
    private String metodo;
    private UserInfo creatore;
    private CalcolatoreMappatura calcolatore;
    private Mappatura mappatura;

    private final char[] alfabeto = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
        's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private SistemaCifratura(CachedRowSet crs) throws SQLException {

        this.id = crs.getInt("id");
        this.chiave = crs.getString("chiave");
        this.metodo = crs.getString("metodo");
        this.creatore = UserInfo.load(crs.getInt("creatore"));
        this.calcolatore = CalcolatoreMappatura.create(metodo);
        this.mappatura = calcolatore.calcola(chiave, alfabeto);
    }

    public SistemaCifratura(String chiave, String metodo, UserInfo st) {
        this.id = -1;
        this.chiave = chiave;
        this.metodo = metodo;
        this.creatore = st;
        this.calcolatore = CalcolatoreMappatura.create(metodo);
        this.mappatura = calcolatore.calcola(chiave, alfabeto);
    }

    public SistemaCifratura(String chiave, String metodo, Studente st) {
        this(chiave, metodo, st.getUserInfo());
    }

    public void setChiave(String chiave) {
        this.chiave = chiave;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
        this.calcolatore = CalcolatoreMappatura.create(metodo);
    }

    public static List<SistemaCifratura> caricaSistemiCifratura(Studente st) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT id, chiave, metodo, creatore "
                + "FROM SistemaCifratura WHERE creatore = ? AND eliminato = ?", st.getId(), false);
        List<SistemaCifratura> lista = new ArrayList<>();
        while (crs.next()) {
            lista.add(new SistemaCifratura(crs));
        }
        return lista;
    }

    public static List<SistemaCifratura> caricaSistemiCifraturaNonProposti(Studente st) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT id, chiave, metodo, creatore "
                + "FROM SistemaCifratura "
                + "WHERE creatore = ? AND id NOT IN (SELECT sdc FROM Proposta) AND eliminato = ?", st.getId(), false);
        List<SistemaCifratura> lista = new ArrayList<>();
        while (crs.next()) {
            lista.add(new SistemaCifratura(crs));
        }
        return lista;
    }

    public static SistemaCifratura load(int id) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM SistemaCifratura WHERE id = ?", id);
        if (crs.next()) {
            return new SistemaCifratura(crs);
        }
        return null;
    }

    //  non più usato, da eliminare quasi certamente (vedi nuovo DSDcifraMessaggioV2)
    public static SistemaCifratura load(UserInfo st1, UserInfo st2) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT s.id, s.chiave, s.metodo, s.creatore"
                + " FROM crypto_user.Proposta AS p JOIN crypto_user.SistemaCifratura"
                + " AS s ON s.id = p.sdc WHERE "
                + " ((p.proponente = ? AND p.partner = ?)"
                + " OR (p.proponente = ? AND p.partner = ?))"
                + " AND p.stato = 'accepted'", st1.getId(), st2.getId(), st2.getId(), st1.getId());
        crs.next();
        return new SistemaCifratura(crs);
    }

    public Mappatura getMappatura() {
        return mappatura;
    }

    public String prova(String testo) {
        return Cifratore.cifraMonoalfabetica(mappatura, testo);
    }

    public void calcolaMappatura() {
        this.mappatura = calcolatore.calcola(chiave, alfabeto);
    }

    public boolean save() throws SQLException {
        DBController dbc = DBController.getInstance();
        id = dbc.executeInsert("INSERT INTO SistemaCifratura (metodo, chiave, creatore) VALUES ( ?, ?, ?)", metodo, chiave, creatore.getId());
        return id != -1;
    }

    public boolean elimina() throws SQLException {
        DBController dbc = DBController.getInstance();
        if (id < 0) {
            return true;
        }
        return dbc.executeUpdate("UPDATE SistemaCifratura SET eliminato = ? WHERE id = ?", true, id);

    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "metodo: " + metodo + " chiave: " + chiave;
    }

}
