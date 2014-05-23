/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptohelper.model;

import java.util.LinkedList;
import java.util.List;

/**
 *  
 * 
 * @author mat
 */
public class Ipotesi {

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
    public Ipotesi trovaConflitto(MappaturaParziale m) {
        if(map.conflitto(m)) {
            return this;
        }
        if(null == padre) {
            return null;
        }
        return padre.trovaConflitto(m);
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
    Ipotesi giaRaggiunta(MappaturaParziale m) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
