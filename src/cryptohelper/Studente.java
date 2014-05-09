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
    
    int getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return "Id: " + id + "Nick: " + nickname + "Pass: " + password + "Nome: " + nome + "Cognome: " + cognome;
    }
    
}
