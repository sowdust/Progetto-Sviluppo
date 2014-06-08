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
 *
 * @author glaxy
 */
public class Studente {

    private int id;
    private String nickname;
    private String password;
    private String nome;
    private String cognome;

    public Studente(int id, String nickname, String password, String nome, String cognome) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    public Studente(CachedRowSet crs) throws SQLException {
        this.id = crs.getInt("id");
        this.nickname = crs.getString("nickname");
        this.password = crs.getString("password");
        this.nome = crs.getString("nome");
        this.cognome = crs.getString("cognome");
    }

    public static Studente load(int id) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Studente WHERE id = ?", id);
        crs.next();
        return new Studente(crs);
    }

    public static Studente login(String nickname, String password) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Studente WHERE nickname = ? AND password = ?", nickname, password);
        if (crs.next()) {
            return new Studente(crs);
        }
        return null;
    }

    public static Studente registra(String nome, String cognome, String nickname, String password) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Studente WHERE nickname = ? ", nickname);
        if (crs.next()) {
            throw new RuntimeException("Nickname già in uso");
        }
        int id = dbc.executeInsert("INSERT INTO Studente (nome, cognome, nickname, password) "
                + "VALUES (?,?,?,?)", nome, cognome, nickname, password);
        if (id != -1) {
            return login(nickname, password);
        }

        return null;
    }

    public UserInfo getUserInfo() {
        return new UserInfo(id, nome, cognome);
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " Cognome: " + cognome + " Nickname: " + nickname;
    }

    public List<Studente> elencaCompagni() throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Studente WHERE id != ?", id);
        List<Studente> lista = new ArrayList<>();
        while (crs.next()) {
            lista.add(new Studente(crs));
        }
        return lista;
    }

}
