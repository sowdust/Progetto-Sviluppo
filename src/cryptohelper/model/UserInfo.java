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
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

/**
 *
 * @author glaxy
 */
public class UserInfo {

    private final int id;
    private final String nome;
    private final String cognome;

    public UserInfo(int id, String nome, String cognome) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
    }

    private UserInfo(CachedRowSet crs) throws SQLException {
        this.id = crs.getInt("id");
        this.nome = crs.getString("nome");
        this.cognome = crs.getString("cognome");
    }

    public static UserInfo load(int id) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT id, nome, cognome FROM crypto_user.Studente WHERE id = ?", id);
        crs.next();
        return new UserInfo(crs);
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

}
