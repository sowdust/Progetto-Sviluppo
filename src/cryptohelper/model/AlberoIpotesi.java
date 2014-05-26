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
    
    // RETURN boolean false se già raggiunto
    public boolean faiAssunzione(MappaturaParziale map) {
        
        MappaturaParziale nuovaMappatura = mappaturaCorrente.merge(map);
        System.out.println("NuovaMappatura:" + nuovaMappatura);
        System.out.println("mappaturacorrente:" + mappaturaCorrente);
        System.out.println("mao:" + map);
        List<Character> daRimuovere = map.filtraDaRimuovere();


        
        Ipotesi giaRaggiunta = giaRaggiunta(nuovaMappatura);
        
        if(giaRaggiunta != null) {
            ipotesiCorrente = giaRaggiunta;
            mappaturaCorrente = nuovaMappatura;
            mosse.push(ipotesiCorrente);
            return false;            
        }
  

        
        // se lettera non ancora assegnata in questo cammino
        if(!mappaturaCorrente.conflitto(map) && daRimuovere.isEmpty()){
            ipotesiCorrente = ipotesiCorrente.aggiungiIpotesi(map);
            mappaturaCorrente = nuovaMappatura;
            mosse.push(ipotesiCorrente);
            return true;
        }
 
        // se c'e' un conflitto risaliamo
        
        Ipotesi aCuiAttaccarsi = ipotesiCorrente.trovaConflitto(map, daRimuovere);
        if(null == aCuiAttaccarsi) {
            aCuiAttaccarsi = radice;
        } else {
            aCuiAttaccarsi = aCuiAttaccarsi.padre;
        }
        MappaturaParziale daAggiungere = nuovaMappatura.sottrai(aCuiAttaccarsi.getStato());
        ipotesiCorrente = aCuiAttaccarsi.aggiungiIpotesi(daAggiungere);
        mappaturaCorrente = nuovaMappatura;
        mosse.push(ipotesiCorrente);
        return true;
    
        
        /*
        
        List<Character> daRimuovere = map.filtraDaRimuovere();
        Ipotesi giaRaggiunta = giaRaggiunta(mappaturaCorrente);
        
        
        if(giaRaggiunta == null) {
            
            // se lettera non ancora assegnata in questo cammino
            if(!mappaturaCorrente.conflitto(map)){
                    ipotesiCorrente = ipotesiCorrente.aggiungiIpotesi(map);
                    mosse.push(ipotesiCorrente);
                    mappaturaCorrente = mappaturaCorrente.merge(map);
                    return true;
            }
            
            // se c'e' un conflitto risaliamo
            
            mappaturaCorrente = mappaturaCorrente.merge(map);

            Ipotesi aCuiAttaccarsi = ipotesiCorrente.trovaConflitto(map, daRimuovere).padre;
            if(aCuiAttaccarsi == null ) {
                aCuiAttaccarsi = radice;
            }
            MappaturaParziale daAggiungere = mappaturaCorrente.sottrai(aCuiAttaccarsi.getStato());
            ipotesiCorrente = aCuiAttaccarsi.aggiungiIpotesi(daAggiungere);
            mosse.push(ipotesiCorrente);
            return true;
        }
        
        ipotesiCorrente = giaRaggiunta;
        mosse.push(ipotesiCorrente);
        
        return false;
        
        */
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
