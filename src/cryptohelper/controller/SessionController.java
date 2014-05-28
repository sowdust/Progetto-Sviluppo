package cryptohelper.controller;

import cryptohelper.model.MappaturaParziale;
import cryptohelper.model.Sessione;
import java.io.IOException;
import java.sql.SQLException;

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
    
    public boolean faiAssunzione(Sessione s, MappaturaParziale nuoveAssunzioni) {
        return s.faiAssunzione(nuoveAssunzioni);
    }
    
    public void undo(Sessione s, String motivazione) {
        s.undo(motivazione);
    }
}
