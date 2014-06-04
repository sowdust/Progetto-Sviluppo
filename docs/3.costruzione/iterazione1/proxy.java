public abstract class Sessoine() {

    static Sessione mostraSessioni(UserInfo st) {
        DBController ...
        Riempe lista con SessioneProxy()
        return List<Sessioni>;
    }
    
    static Sessione caricaSessione(id) {
        DBController ...
        s = SessioneProxy()
        return s
    }

    public MessagioSpia getMessaggio();
    public faiAssunzione(Mappatura nuovaAssunzione);
}

public class SessioneProxy() {

    private MessaggioSpia messaggio;
    private Sessione realSessione;
    
    public SessionProxy(CachedRowSet crs) {
        riempie messaggio
        riempie id
    }

    public int getId() {
        return id;
    }
    
    public UserInfo getDestinatario() {
        return destinatario;
    }
    
    public boolean faiAssunzione(Mappatura nuoveAssunzioni) {

        return getRealSessione().faiAssunzione(nuoveAssunzioni);
    }
    
    public Mappatura getMappaturaCorrente() {
        return getRealSessione().getMappaturaCorrente();
    }
        
    private Sessione getRealSessione() {
        if(realSessione == null) {
            realSessione = new realSessione(id);
        }
        return realSessione;
    }
}

