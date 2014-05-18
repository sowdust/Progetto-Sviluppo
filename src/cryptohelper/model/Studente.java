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
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Studente(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.nickname = rs.getString("nickname");
        this.password = rs.getString("password");
        this.nome = rs.getString("nome");
        this.cognome = rs.getString("cognome");
    }

    public static Studente load(int id) throws SQLException {
        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("SELECT * FROM crypto_user.Studente WHERE id = " + id);
        rs.next();
        return new Studente(rs);
    }

    public UserInfo getUserInfo() {
        return new UserInfo(id, nome, cognome);
    }

    public int getId() {
        return id;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    public String toString() {
        return "\nId: " + id + "\nNick: " + nickname + "\nPass: " + password + "\nNome: " + nome + "\nCognome: " + cognome;
    }

}
