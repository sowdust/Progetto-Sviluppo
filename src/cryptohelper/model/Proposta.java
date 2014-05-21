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
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author glaxy
 */
public class Proposta {
    /*

     NOTE: ( da mattia V. - usando "proposta" in SDC )
     pensare di mettere gli stati di "proposta" in un array e di poterli
     richiamare con dei metodi?
     Non molto bello dover scrivere
     stato = "accepted" in una query
     */
    /*

     NOTE: rispondo a Mattia V, sono C
     altrettanto brutto stato = "+ arrayDiStati[0] ... no? "accepted" più leggibile!
     */

    private int id;
    private String stato;
    private boolean notificata;
    private UserInfo proponente;
    private UserInfo partner;
    private SistemaCifratura sdc;

    /* costruttore usato quando si *crea* una proposta */
    public Proposta(Studente proponente, Studente partner, SistemaCifratura sdc) {
        this.id = -1;
        this.stato = "pending";
        this.notificata = false;
        this.proponente = proponente.getUserInfo();
        this.partner = partner.getUserInfo();
        this.sdc = sdc;
    }

    /* costruttore usato quando si *carica* una proposta */
    public Proposta(CachedRowSet queryResult) throws SQLException {
        id = queryResult.getInt("id");
        stato = queryResult.getString("stato");
        notificata = queryResult.getBoolean("notificata");
        proponente = UserInfo.load(queryResult.getInt("proponente"));
        partner = UserInfo.load(queryResult.getInt("partner"));
        sdc = SistemaCifratura.load(queryResult.getInt("sdc"));
    }

    public static Proposta load(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static Proposta caricaAttiva(int id1, int id2) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Proposta "
                + "WHERE ((Proponente = ? AND Partner = ?) "
                + "OR (Proponente = ? AND Partner = ?)) AND stato='accepted'", id1, id2, id2, id1);
        if (crs.next()) {
            return new Proposta(crs);
        }
        return null;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public boolean save() throws SQLException {
        DBController dbc = DBController.getInstance();
        if (id < 0) {
            return dbc.executeUpdate("INSERT INTO Proposta (stato, notificata, proponente, partner, sdc) "
                    + "VALUES (?, ?, ?, ?, ?)", stato, notificata, proponente.getId(), partner.getId(), sdc.getId());
        }
        return dbc.executeUpdate("UPDATE Proposta SET "
                + "stato =  ?, notificata = ?, proponente = ?, partner = ?, sdc = ? "
                + "WHERE id = ?", stato, notificata, proponente.getId(), partner.getId(), sdc.getId(), id);
    }

    public int getId() {
        return id;
    }

    public String getStato() {
        return stato;
    }

    public boolean isNotificata() {
        return notificata;
    }

    public UserInfo getProponente() {
        return proponente;
    }

    public UserInfo getPartner() {
        return partner;
    }

    public SistemaCifratura getSdc() {
        return sdc;
    }

    @Override
    public String toString() {
        return "Proposta: \n"
                + stato + "\n"
                + notificata + "\n"
                + proponente + "\n"
                + partner + "\n"
                + sdc + "\n";
    }
}
