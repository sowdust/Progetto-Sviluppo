/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptohelper.model;

import java.util.Stack;

/**
 * 
 * 
 * TODO.
 * Metodo per serializzare intero albero e mosse (o male che vada ultimo stato)
 * Da bravo deficiente non ho tenuto quello che avevo già fatto e funzionava.
 * 
 * 
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
 * TODO.
 * (1)  Pensare al miglior modo per gestire detection di stati già raggiunti.
 *      Essendo l'ordinamento temporale e non alfabetico (e quindi non potendo fare
 *      una detect in modo ottimo) valutare se in un uso pratico sia proprio quella
 *      la prossima opzione aggiuntiva più utile, piuttosto che altre possibili.
 *      Almeno una comunque da aggiungere.
 * (2)  Una volta deciso formato di input (mappatura intera annienterebbe il vantaggio
 *      di MappaturaParziale) gestire la remove.
 *  
 * @author mat
 */
public class Sessione {

    
    //  TODO: variabili public solo in fase di testing
    private UserInfo proprietario;
    private Messaggio messaggio;
    private Ipotesi radice;
    public Stack<Ipotesi> mosse;
    public Ipotesi ipotesiCorrente;
    private MappaturaParziale mappaturaCorrente;

    public Sessione(UserInfo proprietario, Messaggio messaggio) {
        
        this.proprietario = proprietario;
        this.messaggio = messaggio;
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
    
    public void stampaAlbero() {
        radice.stampa(0,ipotesiCorrente);
    }
    
    public Ipotesi giaRaggiunta(){
        //  TODO:
        // la funzione map.conflitto(map) non deve tornare errore
        // se stessa assegnazione (o usare altro metodo)
        // ora smetto perchè mi sono un po' scaramellato
        return radice.giaRaggiunta(mappaturaCorrente); 
    }

}
