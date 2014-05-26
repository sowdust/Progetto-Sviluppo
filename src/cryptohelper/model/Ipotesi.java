package cryptohelper.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Ipotesi implements Serializable {

    public MappaturaParziale map;
    public List<Ipotesi> figli;
    public Ipotesi padre;
    public String commento;

    public Ipotesi(MappaturaParziale map, Ipotesi padre) {
        this.padre = padre;
        this.map = map;
        this.figli = new LinkedList<Ipotesi>();
    }

    public Ipotesi aggiungiIpotesi(MappaturaParziale map) {
        Ipotesi ip = new Ipotesi(map, this);
        figli.add(ip);
        return ip;
    }

    /*
     * Ritorna la mappatura completa calcolata risalendo il cammino fino
     * alla radice, facendo una merge con ogni mappatura incontrata
     */
    public MappaturaParziale getStato() {
        if( null == padre) {
            return new MappaturaParziale(map);
        }
        return map.merge(padre.getStato());
    }

    /*
     * Percorrendo il cammino fino alla radice, restituisce il primo nodo
     * in cui vi è un assegnazione in conflitto con la mappatura m
     */
    public Ipotesi trovaConflitto(MappaturaParziale m, List<Character> daRimuovere) {
        if(null == padre) {
            return null;
        }
        if(map.conflitto(m)) {
            return this;
        }

        return padre.trovaConflitto(m, daRimuovere);
    }

    public void stampa(int d, Ipotesi ipotesiCorrente) {
        System.out.print("  ");
        for(int i = 0; i <= d; ++i) {
            System.out.print("     ");
        }
        if(this == ipotesiCorrente) {
            System.out.print("\b\b**");

        }
        System.out.println(map);

        for(Ipotesi i : figli) {
            if(i!= null) {
                i.stampa(d + 1, ipotesiCorrente);
            }
        }
    }

    /*
     * Esplorando ricorsivamente i nodi figli, restituisce, se esiste, il primo
     * in cui lo stato sia uguale a m; null altrimenti
     */
    Ipotesi giaRaggiunta(MappaturaParziale m, MappaturaParziale corrente) {
        MappaturaParziale stato = corrente.merge(map);
        
        // se vi è un conflitto, m non è in questo cammino
        if(map.conflitto(m)) {
            return null;
        }
        // se m è uguale allo stato dell'ipotesi in cui siamo
        if(stato.equals(m)) {
            return this;
        }
        // ripetiamo sui figli
        for(Ipotesi i : figli) {
            if(i.giaRaggiunta(m, stato) != null) {
                return i.giaRaggiunta(m, stato);
            }
        }
        return null;
    }

}
