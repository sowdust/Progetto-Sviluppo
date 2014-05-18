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
 * NOTE. save() e elimina() per ora lanciano eccezioni se creatore e id
 * rispettivamente non impostati. vedere se necessarie o meno
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

    private final char[] alfabeto = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
        's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private SistemaCifratura(ResultSet rs) throws SQLException {

        this.id = rs.getInt("id");
        this.chiave = rs.getString("chiave");
        this.metodo = rs.getString("metodo");
        this.creatore = UserInfo.load(rs.getInt("creatore"));
        this.calcolatore = CalcolatoreMappatura.create(metodo);
        this.mappatura = calcolatore.calcola(chiave, alfabeto);
    }

    public SistemaCifratura(String chiave, String metodo, UserInfo st) {

        this.metodo = metodo;
        this.chiave = chiave;
        this.calcolatore = CalcolatoreMappatura.create(metodo);
        this.mappatura = calcolatore.calcola(chiave, alfabeto);
        this.creatore = st;
    }

    public SistemaCifratura(String chiave, String metodo, Studente st) {
        this(chiave, metodo, st.getUserInfo());
    }

    void setChiave(String chiave) {
        this.chiave = chiave;
    }

    void setMetodo(String metodo) {
        this.metodo = metodo;
        this.calcolatore = CalcolatoreMappatura.create(metodo);
    }

    public static List<SistemaCifratura> caricaSistemiCifratura(Studente st) throws SQLException {

        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT id, chiave, metodo, creatore FROM "
                + "crypto_user.SistemaCifratura WHERE creatore = " + st.getId());
        List<SistemaCifratura> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new SistemaCifratura(rs));
        }
        return lista;
    }

    public static SistemaCifratura load(int id) throws SQLException {

        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT * FROM crypto_user.SistemaCifratura"
                + " WHERE id = " + id);
        rs.next();
        return new SistemaCifratura(rs);
    }

    //  controllare che la query sia giusta!
    public static SistemaCifratura load(UserInfo st1, UserInfo st2) throws SQLException {

        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT s.id, s.chiave, s.metodo, s.creatore"
                + " FROM crypto_user.Proposta AS p JOIN crypto_user.SistemaCifratura"
                + " AS s ON s.id = p.sdc WHERE "
                + " ((p.proponente = " + st1.getId() + " AND p.partner = " + st2.getId() + ")"
                + " OR (p.proponente = " + st2.getId() + " AND p.partner = " + st1.getId() + "))"
                + " AND p.stato = 'accepted'");
        rs.next();
        return new SistemaCifratura(rs);
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
        if (null == creatore) {
            throw new RuntimeException("Non è possibile salvare un sistema di cifratura senza associarvi un valido utente creatore");
        }
        DBController dbc = DBController.getInstance();
        return dbc.executeUpdate("INSERT INTO crypto_user.SistemaCifratura (creatore, metodo, chiave) VALUES(" + creatore.getId() + ", " + metodo + ", " + chiave + ")");
    }

    public boolean elimina() throws SQLException {
        DBController dbc = DBController.getInstance();

        if (null == id) {
            throw new RuntimeException("Problema nell'eliminazione: SdC non identificato.");
        }
        return dbc.executeUpdate("DELETE FROM crypto_user.SistemaCifratura WHERE id = " + id);

    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {

        return this.metodo + "\n"
                + this.chiave + "\n"
                + this.creatore + "\n"
                + this.calcolatore;
    }

}
