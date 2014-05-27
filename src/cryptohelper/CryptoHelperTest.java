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

import cryptohelper.controller.CommunicationController;
import cryptohelper.model.Proposta;
import cryptohelper.model.SistemaCifratura;
import cryptohelper.model.Studente;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author glaxy
 */
public class CryptoHelperTest {

    public static void main(String[] args) throws SQLException {
        //questo sono io
        Studente io = Studente.load(1);
        //mi creo un nuovo sistema di cifratura
        SistemaCifratura mySDC = new SistemaCifratura("provagenerale", "parolachiave", io);
        //lo salvo nel database
        mySDC.save();
        // deve essere diverso da -1 ed avere invece l'id aggiunto
        System.out.println(mySDC.getId());
        List<SistemaCifratura> lists = SistemaCifratura.caricaSistemiCifratura(io);
        // prendo l'ultimo creato
        mySDC = lists.get(lists.size() - 1);
        //cerco un amico a cui proporlo (il 4 ha già una proposta accettata)
        Studente amico = Studente.load(4);
        CommunicationController cc = CommunicationController.getInstance();
        //crea l'oggetto proposta e lo salva nel DB
        cc.inviaProposta(io, amico, mySDC);

        // ora agisco nei panni dell'amico
        // guardo le proposte che mi sono state fatte
        List<Proposta> listp = cc.getProposte(amico);
        // prendo l'ultima proposta della lista che dovrebbe essere l'ultima che mi è stata fatta
        Proposta propostaConID = listp.get(listp.size() - 1);
        // accetto la proposta
        cc.inviaDecisione(propostaConID, "accepted");
        //quel che dovrebbe succedere è avere un SDC nuovo, una nuova proposta accepted e quella vecchia
        //dovrebbe essere expired
    }

}
