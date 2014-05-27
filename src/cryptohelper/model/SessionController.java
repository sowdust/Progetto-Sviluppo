package cryptohelper.model;

import java.sql.SQLException;

public class SessionController {

    /**
     * NOTA::::
     * dal modello di progetto session controller è un singleton,
     * ma non dovrebbe avere il riferimento almeno a un oggetto sessione?
     */
    private Sessione sessione;
    
    SessionController(Sessione sessione) {
        this.sessione = sessione;
    }
    
    /*
     * NOTA: 
     * Se mi ritorna falso non so cosa sia andato storto
     * (eliminazione della sessione o salvataggio della soluzione?)
     */
    public boolean salvaSoluzione() throws SQLException {
        return this.sessione.salvaSoluzione();        
    }
}
