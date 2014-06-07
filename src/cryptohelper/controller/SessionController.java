package cryptohelper.controller;

import cryptohelper.model.Mappatura;
import cryptohelper.model.Messaggio;
import cryptohelper.model.MessaggioSpia;
import cryptohelper.model.Sessione;
import cryptohelper.model.Soluzione;
import cryptohelper.model.UserInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

public class SessionController {

    private static SessionController instance = null;

    private SessionController() {
    }

    public static SessionController getInstance() {
        if (instance == null) {
            instance = new SessionController();
        }
        return instance;
    }

    public boolean faiAssunzione(Sessione s, Mappatura nuoveAssunzioni) {
        return s.faiAssunzione(nuoveAssunzioni);
    }

    public void undo(Sessione s, String motivazione) {
        s.undo(motivazione);
    }

    public boolean salvaSessione(Sessione s) throws SQLException, IOException {
        return s.save();
    }

    public List<Sessione> mostraSessioni(UserInfo studente) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Sessione WHERE proprietario = ?", studente.getId());
        List<Sessione> listaSessioni = new ArrayList<>();
        while (crs.next()) {
            listaSessioni.add(new Sessione(crs));
        }
        return listaSessioni;

    }

    public Sessione creaSessione(UserInfo proprietario, MessaggioSpia messaggio) {
        return new Sessione(proprietario, messaggio);
    }

    public Sessione caricaSessione(int id) throws SQLException, IOException, ClassNotFoundException {
        return Sessione.load(id);
    }

    public boolean eliminaSessione(Sessione s) throws SQLException {
        return s.elimina();
    }

    public List<Soluzione> mostraSoluzioni(UserInfo st1, UserInfo st2) {
        throw new UnsupportedOperationException();
    }

    public List<Soluzione> mostraSoluzioni(UserInfo proprietario) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Soluzione"
                + "WHERE Creatore = ? ", proprietario.getId());
        List<Soluzione> result = null;
        while (crs.next()) {
            result.add(new Soluzione(crs));
        }
        return result;
    }

    public boolean salvaSoluzione(Sessione s) throws SQLException {
        return s.salvaSoluzione();
    }

    public boolean caricaSoluzione(Sessione s, Soluzione sol) {
        return s.caricaSoluzione(sol);
    }

    public boolean eliminaSoluzione(Soluzione s) throws SQLException {
        return s.elimina();
    }

    public List<MessaggioSpia> sniffMessaggi(UserInfo userInfo) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Messaggio "
                + "WHERE Bozza = ? AND Mittente != ? AND Destinatario != ? AND "
                + "id NOT IN (SELECT messaggio FROM Sessione WHERE proprietario = ?)", false, userInfo.getId(), userInfo.getId(), userInfo.getId());
        List<MessaggioSpia> listaMessaggi = new ArrayList<>();
        while (crs.next()) {
            listaMessaggi.add(new Messaggio(crs));
        }
        return listaMessaggi;
    }

    public String getCommento(Sessione s) {
        return s.getCommento();
    }

}
