package cryptohelper.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Mappatura implements Serializable {

    private ArrayList<Character> map;
    private ArrayList<Character> inverseMap;
    //carattere della mappatura che indica la richiesta di rimozione
    static final char DA_RIMUOVERE = '-';

    public Mappatura() {
        this.map = new ArrayList();
        this.inverseMap = new ArrayList();
    }

    public Mappatura(char[] map, char[] inverseMap) {
        this.map = new ArrayList<>();
        this.inverseMap = new ArrayList<>();
        /* non il più furbo dei metodi? */
        for (int i = 0; i < map.length; i++) {
            this.map.add(map[i]);
            this.inverseMap.add(inverseMap[i]);
        }
    }

    public Mappatura(Mappatura m) {
        this.map = new ArrayList();
        this.inverseMap = new ArrayList();
        for (Character c : m.map) {
            this.map.add(c);
        }
        for (Character c : m.inverseMap) {
            this.inverseMap.add(c);
        }
    }

    public Mappatura(String s) {
        this.map = new ArrayList();
        this.inverseMap = new ArrayList();
        String[] split = s.split(",");
        for (String t : split) {
            String[] m = t.trim().split(">");
            this.map.add(m[0].trim().charAt(0));
            this.inverseMap.add(m[1].trim().charAt(0));
        }
    }

    public Character map(char c) {
        int i = 0;
        for (Character k : inverseMap) {
            if (c == k) {
                return map.get(i);
            }
            ++i;
        }
        return null;
    }

    public Character inverseMap(char c) {
        int i = 0;
        for (Character k : map) {
            if (c == k) {
                return inverseMap.get(i);
            }
            ++i;
        }
        return null;
    }

    public int size() {
        return map.size();
    }

    /*
     * Data una mappatura parziale m, la funzione merge restituisce una nuova
     * mappatura contenente tutte le assegnazioni di m più tutte le assegnazioni
     * di this non in conflitto con m.
     * 
     * Es:
     * {a > z } merge {b > w} ==> {a > z, b > w }
     * {a > z, b > w } merge {a > y} ==> {a > y, b > w} 
     * {a > z, b > w } merge {c > z} ==> {c > z, b > w} 
     * 
     */
    public Mappatura merge(Mappatura nMap) {

        Mappatura m = new Mappatura(nMap);
        Mappatura r = new Mappatura(this);

        //  prima è necessario rimuovere 
        for (int i = 0; i < m.map.size(); ++i) {
            if (m.inverseMap.get(i) == DA_RIMUOVERE) {
                int k = r.map.indexOf(m.map.get(i));
                if (k != -1) {
                    r.map.remove(k);
                    r.inverseMap.remove(k);
                }
                m.map.remove(i);
                m.inverseMap.remove(i);
                --i;
            }
        }

        // ora aggiungiamo risolvendo i conflitti
        for (int i = 0; i < m.map.size(); ++i) {
            int k = r.map.indexOf(m.map.get(i));
            int j = r.inverseMap.indexOf(m.inverseMap.get(i));

            // se non ci sono conflitti
            if (k == -1 && j == -1) {

                r.map.add(m.map.get(i));
                r.inverseMap.add(m.inverseMap.get(i));
                continue;
            }

            // se c'è un'assegnazione modificata ( a->x ; a->k ==> a->k)
            if (k != -1) {
                char c = m.inverseMap.get(i);
                // prima vediamo se esiste già un'eventuale { x -> k }
                int t = r.inverseMap.indexOf(c);
                // asssegnamo la nuova lettera 
                r.inverseMap.set(k, c);
                // se la lettera era già assegnata a sx in precedenza, la rimuoviamo
                if (t != -1 && t != k) {
                    r.inverseMap.remove(t);
                    r.map.remove(t);
                }
                continue;
            }

            // lettera ri-assegnata ( a->j ; b->j ==> b->j )
            if (j != -1) {
                r.map.set(j, m.map.get(i));
                continue;
            }

            // doppio conflitto ( a->j && b->k ; a->k ==> a->k )
            {
                r.map.remove(k);
                r.inverseMap.remove(k);
                r.map.set(j, m.map.get(i));
                r.inverseMap.set(j, m.inverseMap.get(i));
                continue;
            }
        }
        return r;
    }

    /*
     * Data una mappatura parziale m, ritorna una mappatura contentente gli
     * elementi di this che non compaiono in m.
     * Se me non è strettamente contenuta in this, IllegalArgumentException
     * 
     * Es:
     * {a > z, b > y, c > w} sottrai { b > y, c > w }  ==> { a > z }
     * {a > z, b > y} sottrai {a > z, b > w }  ==> IllegalArgumentException: contaConflitti
     * {b > y, c > w} sottrai {a > z,  b > y, c > w }  ==> IllegalArgumentException: troppo lunga
     * 
     */
    public Mappatura sottrai(Mappatura m) {
        int size = this.size();
        if (m.size() > size) {
            throw new IllegalArgumentException("Impossibile sottrarre mappa: troppo lunga");
        }
        Mappatura r = new Mappatura();
        for (int i = 0; i < size; ++i) {
            int k = m.map.indexOf(map.get(i));
            if (k == -1) {
                r.map.add(map.get(i));
                r.inverseMap.add(inverseMap.get(i));
            } else {
                if (m.inverseMap.get(k) != inverseMap.get(i)) {
                    r.map.add(map.get(i));
                    r.inverseMap.add(inverseMap.get(i));
                }
            }
        }
        return r;
    }

    /*
     * Data una mappatura parziale m, restituisce true se vi sono lettere già 
     * assegnate o già definite in maniera diversa in this, false altrimenti.
     * 
     * Es:
     * ( {a > z} ∈ this && {a > z} ∈ m ==> false )
     * ( {a > z} ∈ this && {a > y} ∈ m ==> true )
     * ( {a > z} ∈ this && {b > z} ∈ m ==> true )
     * 
     */
    public boolean conflitto(Mappatura m) {
        for (int i = 0; i < m.map.size(); ++i) {
            int k = map.indexOf(m.map.get(i));
            int j = inverseMap.indexOf(m.inverseMap.get(i));
            if (k != -1 && inverseMap.get(k) != m.inverseMap.get(i)) {
                return true;
            }
            if (j != -1 && map.get(j) != m.map.get(i)) {
                return true;
            }
        }
        return false;
    }

    public int contaConflitti(Mappatura m) {

        int conflitti = 0;
        for (int i = 0; i < m.map.size(); ++i) {
            int k = map.indexOf(m.map.get(i));
            int j = inverseMap.indexOf(m.inverseMap.get(i));
            if (k != -1 && inverseMap.get(k) != m.inverseMap.get(i)) {
                ++conflitti;
            }
            if (j != -1 && map.get(j) != m.map.get(i)) {
                ++conflitti;
            }
        }
        return conflitti;
    }

    /*
     * Restituisce true se tutte le assegnazioni in this sono contenute in m
     * e viceversa.
     * L'ordine in cui compaiono le assegnazioni non è importante.
     *
     * a.equals(b) <==> a ⊆ b && b ⊆ a
     *
     */
    public boolean equals(Mappatura m) {
        int i, k;
        for (i = 0; i < m.size(); ++i) {
            k = map.indexOf(m.map.get(i));
            if (k == -1 || m.inverseMap.get(i) != inverseMap.get(k)) {
                return false;
            }
        }
        return this.size() == i;
    }

    /*
     * data una mappatura restituisce in una lista
     * l'elenco dei caratteri che devono essere disassegnati
     * Side Effect!: mappature con DA_RIMUOVERE rimosse da map
     * 
     * ES:
     * 
     * {a > z, b > DA_RIMUOVERE, c > DA_RIMUOVERE}.filtraDaRimuovere() ==> [b,c]
     * @return 
     */
    public List<Character> filtraDaRimuovere() {
        List<Character> daRimuovere = new ArrayList<>();
        for (int i = 0; i < map.size(); ++i) {
            if (inverseMap.get(i) == DA_RIMUOVERE) {
                daRimuovere.add(map.get(i));
                map.remove(i);
                inverseMap.remove(i);
                --i;
            }
        }
        return daRimuovere;
    }

    /*
     * data una lista di caratteri, restituisce true se almeno di essi
     * è già definito nella mappatura
     */
    public boolean contains(Character daRimuovere) {
        return map.indexOf(daRimuovere) != -1;
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean isCompleta(List<Character> listaCaratteri) {
        for (char c : listaCaratteri) {
            if (!inverseMap.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public String serialize() {
        String s = "";
        for (int i = 0; i < map.size(); ++i) {
            s += map.get(i) + ">" + inverseMap.get(i) + ",";
        }
        return s;
    }

    @Override
    public boolean equals(Object m) {
        return this.equals((Mappatura) m);
    }

    @Override
    public String toString() {
        String s = "";
        for (char c : inverseMap) {
            s += c + " ";
        }
        s += "\n";
        for (char c : map) {
            s += c + " ";
        }
        return s;
    }

    public void stampa() {
        String s = " Ø ";
        if (!map.isEmpty()) {
            s = "{";
            for (int i = 0; i < map.size(); ++i) {
                s += " " + map.get(i) + " > " + inverseMap.get(i) + ",";
            }
            s += "\b }";
        }
        System.out.println(s);
    }

    /*
     * Data una mappatura parziale m, ritorna true se m è strettamente contenuta
     * in this.
     * 
     * Es:
     * {a > z} ⊆ {a > z, b > y} ==> true
     * {a > z} ⊆ {a > z} ==> true
     * {a > z} ⊆ {a > y} ==> false
     * {a > b} ⊆ {a > z} ==> false
     * {a > z, b > y} ⊆ {a > z} ==> false
     * 
     
     public boolean subsetOf(MappaturaParziale m) {
     try {
     return m.sottrai(this).size() == (m.size() - this.size());
     }catch(Exception e) {
     return false;
     }
        
     }
     */
    /*   
     // mi dice se una lettera della mappatura m è già definita in this
     public boolean giaDefinita(MappaturaParziale m) {
     for(char c : m.map) {
     if(map.indexOf(c) != -1) {
     return true;
     }
     }
     return false;
     }
    

     // mi dice se una lettera della mappatura m è già assegnata in this
     public boolean giaAssegnata(MappaturaParziale m) {
     for(char c : m.inverseMap) {
     if(inverseMap.indexOf(c) != -1) {
     return true;
     }
     }
     return false;
     }
     */
}
