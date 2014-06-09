package cryptohelper.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Ipotesi implements Serializable {

    public Mappatura assunzioni;
    public List<Ipotesi> figli;
    public Ipotesi padre;
    public String commento;

    public Ipotesi(Mappatura map, Ipotesi padre) {
        this.padre = padre;
        this.assunzioni = map;
        this.figli = new LinkedList<>();
    }

    Ipotesi aggiungiIpotesi(Mappatura map) {
        Ipotesi ip = new Ipotesi(map, this);
        figli.add(ip);
        return ip;
    }

    /*
     * Ritorna la mappatura completa calcolata risalendo il cammino fino
     * alla radice, facendo una merge con ogni mappatura incontrata
     */
    public Mappatura getMappatura() {
        if (null == padre) {
            return new Mappatura(assunzioni);
        }
        return assunzioni.merge(padre.getMappatura());
    }

    /*
     * Percorrendo il cammino fino alla radice, restituisce il primo nodo
     * in cui vi è un assegnazione in conflitto con la mappatura m
     */
    Ipotesi trovaConflitto(Mappatura m, List<Character> listaDaRimuovere, int conflitti) {
        Mappatura diQuestoNodo = new Mappatura(assunzioni);
        if (null == padre) {
            return this;
        }
        for (int i = 0; i < listaDaRimuovere.size(); ++i) {
            if (diQuestoNodo.contains(listaDaRimuovere.get(i))) {
                listaDaRimuovere.remove(i);
            }
        }
        diQuestoNodo.filtraDaRimuovere();
        conflitti -= diQuestoNodo.contaConflitti(m);
        if (conflitti == 0 && listaDaRimuovere.isEmpty()) {
            return this.padre;
        }
//        System.out.println("Mappa corrente: " + m);
//        System.out.println("Da rimuovere: " + listaDaRimuovere.size());
//        System.out.println("Conflitti rimasti: " + conflitti);
//        System.out.println("risalgo");
        return padre.trovaConflitto(m, listaDaRimuovere, conflitti);
    }

    void stampa(int d, Ipotesi ipotesiCorrente) {
        System.out.print("  ");
        for (int i = 0; i <= d; ++i) {
            System.out.print("     ");
        }
        if (this == ipotesiCorrente) {
            System.out.print("\b\b**");

        }
        assunzioni.stampa();

        for (Ipotesi i : figli) {
            if (i != null) {
                i.stampa(d + 1, ipotesiCorrente);
            }
        }
    }

    public String toString(int d, Ipotesi ipotesiCorrente) {
        String result = null;
        result += "  ";
        for (int i = 0; i <= d; ++i) {
            result += "     ";
        }
        if (this == ipotesiCorrente) {
            result += "\b\b**";
        }
        assunzioni.toStringa();
        for (Ipotesi i : figli) {
            if (i != null) {
                result += i.toString(d + 1, ipotesiCorrente);
            }
        }
        return result;
    }

    /*
     * Esplorando ricorsivamente i nodi figli, restituisce, se esiste, il primo
     * in cui lo stato sia uguale a m; null altrimenti
     */
    Ipotesi giaRaggiunta(Mappatura totale, Mappatura corrente) {
        Mappatura stato = corrente.merge(assunzioni);

        // se m è uguale allo stato dell'ipotesi in cui siamo
        if (stato.equals(totale)) {
            return this;
        }
        // se vi è un conflitto, m non è in questo cammino
        if (assunzioni.conflitto(totale)) {
            ///System.out.println("Ritorno nullo perchè conflitto " + assunzioni.toStringa());
            return null;
        }
        // ripetiamo sui figli
        for (Ipotesi i : figli) {
            if (i.giaRaggiunta(totale, stato) != null) {
                return i.giaRaggiunta(totale, stato);
            }
        }
        return null;
    }

    void setCommento(String commento) {
        this.commento = commento;
    }

    String getCommento() {
        return this.commento;
    }

}
