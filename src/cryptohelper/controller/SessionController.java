package cryptohelper.controller;

import cryptohelper.model.MappaturaParziale;
import cryptohelper.model.Sessione;
import cryptohelper.model.Studente;
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

    public boolean faiAssunzione(Sessione s, MappaturaParziale nuoveAssunzioni) {
        return s.faiAssunzione(nuoveAssunzioni);
    }
    
    public void undo(Sessione s, String motivazione) {
        s.undo(motivazione);
    }
    
    public boolean salvaSessione(Sessione s) throws SQLException, IOException {
        return s.save();
    }
    
    public List<Sessione> mostraSessioni(Studente studente) throws SQLException, IOException, ClassNotFoundException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM crypto_user.Sessione WHERE proprietario = ?", studente.getId());
        List<Sessione> listaSessioni = new ArrayList<>();
        while (crs.next()) {
            listaSessioni.add(new Sessione(crs));
        }
        return listaSessioni;
        
    }
    
    public boolean salvaSoluzione(Sessione s) throws SQLException {
        return s.salvaSoluzione();
    }
    
    public Sessione caricaSessione(int id) {
        try {
            return Sessione.load(id);
        }catch(IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Eccezione " + e.toString() + "\n" + e.getMessage());
        }
    }
    

    

}
