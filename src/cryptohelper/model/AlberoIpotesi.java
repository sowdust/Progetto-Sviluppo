package cryptohelper.model;

import java.io.Serializable;
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

    
    private Ipotesi radice;
    public Stack<Ipotesi> mosse;
    public Ipotesi ipotesiCorrente;
    private MappaturaParziale mappaturaCorrente;

    public AlberoIpotesi(String a, String b) {
        
        mosse = new Stack<>();
        mappaturaCorrente = new MappaturaParziale();
        radice = new Ipotesi(mappaturaCorrente, null);
        ipotesiCorrente = radice;
        mosse.push(ipotesiCorrente);
    }
    
    public void aggiungiIpotesi(MappaturaParziale map) {
                
        // se lettera non ancora assegnata in questo cammino
        if(!mappaturaCorrente.conflitto(map)){
            ipotesiCorrente = ipotesiCorrente.aggiungiIpotesi(map);
            mappaturaCorrente = mappaturaCorrente.merge(map);
            mosse.push(ipotesiCorrente);
            return ;
        }
 
        // se c'e' un conflitto risaliamo
        mappaturaCorrente = mappaturaCorrente.merge(map);
        Ipotesi aCuiAttaccarsi = ipotesiCorrente.trovaConflitto(map).padre;
        MappaturaParziale daAggiungere = mappaturaCorrente.sottrai(aCuiAttaccarsi.getStato());
        ipotesiCorrente = aCuiAttaccarsi.aggiungiIpotesi(daAggiungere);
        mosse.push(ipotesiCorrente);
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
    
    public Ipotesi giaRaggiunta(){
        return radice.giaRaggiunta(mappaturaCorrente, new MappaturaParziale()); 
    }
    
    public void stampaAlbero() {
        radice.stampa(0,ipotesiCorrente);
    }
}
