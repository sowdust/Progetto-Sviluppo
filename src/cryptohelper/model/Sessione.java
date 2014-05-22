/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptohelper.model;

import java.util.Stack;

/**
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
 * 
 * @author mat
 */
public class Sessione {

    private UserInfo proprietario;
    private Messaggio messaggio;
    public Ipotesi radice;
    private Stack<Ipotesi> mosse;
    public Ipotesi ipotesiCorrente;
    private MappaturaParziale mappaturaCorrente;

    //  TODO:
    //  gestire costruttori in caso di nuova sessione o load da db.
    //  mosse va inizializzato o nel costruttore o in aggiungiIpotesi nel
    //  caso ipotesiCorrente == null
    public Sessione(UserInfo proprietario, Messaggio messaggio) {
        this.proprietario = proprietario;
        this.messaggio = messaggio;
    }
    
    public void aggiungiIpotesi(MappaturaParziale map) {
        
        // se l'albero è ancora vuoto
        if(null == ipotesiCorrente) {
            mosse = new Stack<>();
            ipotesiCorrente = new Ipotesi(map, null);
            radice = ipotesiCorrente;
            mappaturaCorrente = map;
            mosse.push(ipotesiCorrente);
            return ;
        }
        
        // se lettera non ancora assegnata in questo cammino
        if(!mappaturaCorrente.conflitto(map)){
            ipotesiCorrente = ipotesiCorrente.aggiungiIpotesi(map);
            mappaturaCorrente = mappaturaCorrente.merge(map);
            mosse.push(ipotesiCorrente);
            return ;
        }
    }
    
    public MappaturaParziale getStato() {
        return new MappaturaParziale(mappaturaCorrente);
    }
    
    // metodo che prende una mappatura e risale l'albero finchè non trova il padre
    // del nodo in cui parte della mappatura era definito.
    // da lì ricopia mappatura unita a tutte le assunzioni fatte dopo
 /*   public void modifica(MappaturaParziale map) {
        Ipotesi i = ipotesiCorrente;
        MappaturaParziale m = new MappaturaParziale();
        while(!i.map.giaDefinita(map)) {
            m.merge(i.map);
            i = i.padre;
        }
        m.merge(map);
        ipotesiCorrente = i.padre.aggiungiIpotesi(m);
        mappaturaCorrente = m;
    }

    public void ricalcolaMappatura() {
        Ipotesi i = ipotesiCorrente;
        mappaturaCorrente = new MappaturaParziale();
        while(i != null) {
            mappaturaCorrente.merge(i.map);
            i = i.padre;
        }
    }
    public void undo() {
        // se invece vogliamo mantenere l'intera history si aggiunge e non si toglie da mosse
        mosse.pop();
        ipotesiCorrente = mosse.peek();
        //TODO: aggiornare mappatura corrente
    }
    
    public Ipotesi raggiunto(MappaturaParziale map) {
        return radice.raggiunto(map, new MappaturaParziale());
    }*/
}
