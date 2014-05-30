package cryptohelper.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MappaturaImpl implements Serializable, Mappatura, MappaturaParziale {

    ArrayList<Character> map;
    ArrayList<Character> inverseMap;
    //carattere della mappatura che indica la richiesta di rimozione
    static final char DA_RIMUOVERE = '-';

    public MappaturaImpl() {
        this.map = new ArrayList();
        this.inverseMap = new ArrayList();
    }
    
    public MappaturaImpl(char[] map, char[] inverseMap) {
        this.map = new ArrayList(Arrays.asList(map));
        this.inverseMap = new ArrayList(Arrays.asList(inverseMap));
    }

    public MappaturaImpl(MappaturaParziale m) {
        this.map = new ArrayList();
        this.inverseMap = new ArrayList();
        for (Character c : m.getMap()) {
            this.getMap().add(c);
        }
        for (Character c : m.getInverseMap()) {
            this.getInverseMap().add(c);
        }
    }

    public MappaturaImpl(String s) {
        this.map = new ArrayList();
        this.inverseMap = new ArrayList();
        String[] split = s.split(",");
        for (String t : split) {
            String[] m = t.trim().split(">");
            this.getMap().add(m[0].trim().charAt(0));
            this.getInverseMap().add(m[1].trim().charAt(0));
        }
    }
    
    public ArrayList<Character> getMap() {
        return this.map;
    }
    
    public ArrayList<Character> getInverseMap() {
        return this.inverseMap;
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
    public MappaturaParziale merge(MappaturaParziale nMap) {

        MappaturaParziale m = new MappaturaImpl(nMap);
        MappaturaParziale r = new MappaturaImpl(this);

        //  prima è necessario rimuovere 
        for (int i = 0; i < m.getMap().size(); ++i) {
            if (m.getInverseMap().get(i) == DA_RIMUOVERE) {
                int k = r.getMap().indexOf(m.getMap().get(i));
                if (k != -1) {
                    r.getMap().remove(k);
                    r.getInverseMap().remove(k);
                }
                m.getMap().remove(i);
                m.getInverseMap().remove(i);
                --i;
            }
        }

        // ora aggiungiamo risolvendo i conflitti
        for (int i = 0; i < m.getMap().size(); ++i) {
            int k = r.getMap().indexOf(m.getMap().get(i));
            int j = r.getInverseMap().indexOf(m.getInverseMap().get(i));

            // se non ci sono conflitti
            if (k == -1 && j == -1) {

                r.getMap().add(m.getMap().get(i));
                r.getInverseMap().add(m.getInverseMap().get(i));
                continue;
            }

            // se c'è un'assegnazione modificata ( a->x ; a->k ==> a->k)
            if (k != -1) {
                char c = m.getInverseMap().get(i);
                // prima vediamo se esiste già un'eventuale { x -> k }
                int t = r.getInverseMap().indexOf(c);
                // asssegnamo la nuova lettera 
                r.getInverseMap().set(k, c);
                // se la lettera era già assegnata a sx in precedenza, la rimuoviamo
                if (t != -1 && t != k) {
                    r.getInverseMap().remove(t);
                    r.getMap().remove(t);
                }
                continue;
            }

            // lettera ri-assegnata ( a->j ; b->j ==> b->j )
            if (j != -1) {
                r.getMap().set(j, m.getMap().get(i));
                continue;
            }

            // doppio conflitto ( a->j && b->k ; a->k ==> a->k )
            {
                r.getMap().remove(k);
                r.getInverseMap().remove(k);
                r.getMap().set(j, m.getMap().get(i));
                r.getInverseMap().set(j, m.getInverseMap().get(i));
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
    public MappaturaParziale sottrai(MappaturaParziale m) {
        int size = this.size();
        if (m.size() > size) {
            throw new IllegalArgumentException("Impossibile sottrarre mappa: troppo lunga");
        }
        MappaturaParziale r = new MappaturaImpl();
        for (int i = 0; i < size; ++i) {
            int k = m.getMap().indexOf(map.get(i));
            if (k == -1) {
                r.getMap().add(map.get(i));
                r.getInverseMap().add(inverseMap.get(i));
            } else {
                if (m.getInverseMap().get(k) != inverseMap.get(i)) {
                    r.getMap().add(map.get(i));
                    r.getInverseMap().add(inverseMap.get(i));
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
    public boolean conflitto(MappaturaParziale m) {
        for (int i = 0; i < m.getMap().size(); ++i) {
            int k = map.indexOf(m.getMap().get(i));
            int j = inverseMap.indexOf(m.getInverseMap().get(i));
            if (k != -1 && inverseMap.get(k) != m.getInverseMap().get(i)) {
                return true;
            }
            if (j != -1 && map.get(j) != m.getMap().get(i)) {
                return true;
            }
        }
        return false;
    }

    
    public int contaConflitti(MappaturaParziale m) {

        int conflitti = 0;
        for (int i = 0; i < m.getMap().size(); ++i) {
            int k = map.indexOf(m.getMap().get(i));
            int j = inverseMap.indexOf(m.getInverseMap().get(i));
            if (k != -1 && inverseMap.get(k) != m.getInverseMap().get(i)) {
                ++conflitti;
            }
            if (j != -1 && map.get(j) != m.getMap().get(i)) {
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
    public boolean equals(MappaturaParziale m) {
        int i, k;
        for (i = 0; i < m.size(); ++i) {
            k = map.indexOf(m.getMap().get(i));
            if (k == -1 || m.getInverseMap().get(i) != inverseMap.get(k)) {
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
        for(char c : listaCaratteri) {
            if(!inverseMap.contains(c)) {
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
        return this.equals((MappaturaParziale) m);
    }

    @Override
    public String toString() {
        if (map.isEmpty()) {
            return " Ø ";
        }
        String s = "{";
        for (int i = 0; i < map.size(); ++i) {
            s += " " + map.get(i) + " > " + inverseMap.get(i) + ",";
        }
        return s + "\b }";
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
