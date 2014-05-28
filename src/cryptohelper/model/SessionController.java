package cryptohelper.model;

import java.sql.SQLException;

public class SessionController {

    /**
     * NOTA::::
     * dal modello di progetto session controller è un singleton,
     * ma non dovrebbe avere il riferimento almeno a un oggetto sessione?
     */
    private static SessionController instance = null;
    
    private SessionController() {
        
    }
    
    /*
     * NOTA: 
     * Se mi ritorna falso non so cosa sia andato storto
     * (eliminazione della sessione o salvataggio della soluzione?)
     */
    public boolean salvaSoluzione() throws SQLException {
        throw new RuntimeException("non supportata");
    }
}
