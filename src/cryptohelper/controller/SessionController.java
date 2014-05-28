package cryptohelper.controller;

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
    
    public Sessione caricaSessione(int id) throws SQLException, IOException, ClassNotFoundException {
        return Sessione.load(id);
    }
}
