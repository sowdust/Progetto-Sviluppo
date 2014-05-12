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

/**
 *
 * @author glaxy
 */
public class Proposta {
    /* NOTE:
    - mi sembra che la save di Messaggio e questa siano differenti. Infatti il DSD vuole che questa
    ritorni una queryResult.
    - problemi con save(): in proponiSistemaCifratura deve salvare una nuova voce nel DB; in comunicaDecisione
    deve cambiare lo stato (una colonna) di una voce
    */
    
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
    
    private String stato;
    private boolean notificata;
    private UserInfo proponente;
    private UserInfo partner;
    private SistemaCifratura sdc;
    
    public Proposta(Studente proponente, Studente partner, SistemaCifratura sdc) {
        this.proponente = new UserInfo(proponente.getId(), proponente.getNome(), proponente.getCognome());
        this.partner = new UserInfo(partner.getId(), partner.getNome(), partner.getCognome());
        this.sdc = sdc;
        this.stato = "pending";
        this.notificata = false; //TODO
    }
    
    public Proposta(ResultSet queryResult) throws SQLException {
        DBController dbc = DBController.getInstance();
        ResultSet rs1 = dbc.execute("SELECT * FROM crypto_user.Studente WHERE id = "+queryResult.getInt("proponente"));
        ResultSet rs2 = dbc.execute("SELECT * FROM crypto_user.Studente WHERE id = "+queryResult.getInt("partner"));
        this.proponente = new UserInfo(rs1);
        this.partner = new UserInfo(rs2);
        this.notificata = false; //TODO
        this.stato = "pending"; 
        
    }
    
    public static void load(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public UserInfo getProponente() {
        return this.proponente;
    }
    
    public UserInfo getPartner() {
        return this.partner;
    }
    
    public static Proposta caricaAttiva(int idProp, int idPartner) throws SQLException {
        Proposta old = null; //necessario per CommunicationController.inviaDecisione
        int cont = 0;
        DBController dbc = DBController.getInstance();
        ResultSet rs = dbc.execute("select * from crypto_user.Proposta where Proponente = "+idProp+" and Partner ="+idPartner+" and stato='accepted'");
        while(rs.next()) {
            if (cont != 0) {
                throw new SQLException("ritornata più di una proposta attiva!");
            }
            //id, stato, proponente, partner
            old = new Proposta(rs);
            cont++;
        }
        return old;
    }
    
    public void setStato(String stato) {
        this.stato = stato;
    }
    
    public ResultSet save() throws SQLException { //mi sembra che la save di messaggio e questa siano diverse, dal DSD
        ResultSet rs = null;
        DBController dbc = DBController.getInstance();
        //rs = dbc.execute(""); 
        return rs;
    }
}
