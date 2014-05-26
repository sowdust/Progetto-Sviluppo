package cryptohelper.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/* 
 * NOTE SULL'ALBERO.
 * 
 * Invariante è il fatto che in ogni cammino non vi siano mai due assegnazioni
 * conflittuali ( a->x & a->y oppure a->x & b->x )
 * 
 * Ne derivano le seguenti proprietà:
 * 
 *  1) lunghezza massima di un cammino = cardinalità alfabeto
 *  2) calcolo della mappatura dal basso verso l'alto esplorando solo il cammino corrente
 *  3) detection di stato già visitato si ferma al primo conflitto in ogni ramo
 * 
 * TODO:
 *
 * (1)  Decidere quando fare e come gestire la detect di stati già raggiunti
 * (2)  Una volta deciso formato di input (mappatura intera annienterebbe il vantaggio
 *      di MappaturaParziale) gestire la remove.
 * (3)  Metodo per serializzare intero albero e mosse (o male che vada ultimo stato)
 *      Da bravo deficiente non ho tenuto quello che avevo già fatto e funzionava.
 */
public class AlberoIpotesi implements Serializable{

    
    private final Ipotesi radice;
    public Stack<Ipotesi> mosse;
    public Ipotesi ipotesiCorrente;
    private MappaturaParziale mappaturaCorrente;

    public AlberoIpotesi() {
        
        mosse = new Stack<>();
        mappaturaCorrente = new MappaturaParziale();
        radice = new Ipotesi(mappaturaCorrente, null);
        ipotesiCorrente = radice;
        mosse.push(ipotesiCorrente);
    }
    
    /*
     * Data una mappatura parziale in input, aggiorna l'albero delle ipotesi
     * in modo da mantenerne le proprietà.
     * Aggiorna la mappatura corrente, la lista delle mosse e il puntatore
     * a ipotesi corrente.
     * Ritorna false in caso lo stato corrente fosse già stato raggiunto
     * in passato, true altrimenti
     */
    
    public boolean faiAssunzione(MappaturaParziale map) {
        
        MappaturaParziale nuovaMappatura = mappaturaCorrente.merge(map);
        List<Character> daRimuovere = map.filtraDaRimuovere();
        Ipotesi giaRaggiunta = giaRaggiunta(nuovaMappatura);
        
        if(giaRaggiunta != null) {
            ipotesiCorrente = giaRaggiunta;
            mappaturaCorrente = nuovaMappatura;
            mosse.push(ipotesiCorrente);
            return false;            
        }

        if(!mappaturaCorrente.conflitto(map) && daRimuovere.isEmpty()){
        // semplice aggiunta di un ipotesi
            ipotesiCorrente = ipotesiCorrente.aggiungiIpotesi(map);

        } else {
        // aggiunta di un ipotesi ad un nodo da ricercare
        // in seguito a modifiche o rimozioni d'assunzione
            Ipotesi aCuiAttaccarsi = ipotesiCorrente.trovaConflitto(map, daRimuovere);
            if(null == aCuiAttaccarsi) {
                aCuiAttaccarsi = radice;
            } else {
                aCuiAttaccarsi = aCuiAttaccarsi.padre;
            }
            MappaturaParziale daAggiungere = nuovaMappatura.sottrai(aCuiAttaccarsi.getStato());
            ipotesiCorrente = aCuiAttaccarsi.aggiungiIpotesi(daAggiungere);
        }

        mappaturaCorrente = nuovaMappatura;
        mosse.push(ipotesiCorrente);
        return true;
    }
    
    public void undo() {
        mosse.pop();
        ipotesiCorrente = mosse.peek(); 
        mappaturaCorrente = ipotesiCorrente.getStato();
    }
    
    public MappaturaParziale getStato() {
        return new MappaturaParziale(mappaturaCorrente);
    }
    
    public Ipotesi getAlbero() {
        return radice;
    }
    
    // TODO : PASSA MAPPATURA
    public Ipotesi giaRaggiunta(MappaturaParziale mappaturaCorrente){
        return radice.giaRaggiunta(mappaturaCorrente, new MappaturaParziale()); 
    }
    
    public void stampaAlbero() {
        radice.stampa(0,ipotesiCorrente);
    }
}
