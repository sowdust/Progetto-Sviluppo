package cryptohelper.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MappaturaParziale implements Serializable {
    
    ArrayList<Character> map;
    ArrayList<Character> inverseMap;
    
    public MappaturaParziale() {
        this.map = new ArrayList();
        this.inverseMap = new ArrayList();       
    }
    
    public MappaturaParziale(ArrayList<Character> map, ArrayList<Character> inverseMap) {
        this.map = map;
        this.inverseMap = inverseMap;        
    }
    
    public MappaturaParziale(MappaturaParziale m) {
        this.map = new ArrayList();
        this.inverseMap = new ArrayList();
        for(Character c : m.map) {
            this.map.add(c);
        }
        for(Character c : m.inverseMap) {
            this.inverseMap.add(c);
        }
    }
    
    public MappaturaParziale(String s) {
        this.map = new ArrayList();
        this.inverseMap = new ArrayList();
        try{
            String[] split = s.split(",");
            for(String t : split) {
                String[] m = t.trim().split(">");
                this.map.add(m[0].trim().charAt(0));
                this.inverseMap.add(m[1].trim().charAt(0));
            }
        }catch(Exception e) {
            System.out.println("Stringa non valida: " + s);
        }
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
    public MappaturaParziale merge(MappaturaParziale m) {
        
        MappaturaParziale r = new MappaturaParziale(this);
        
        for(int i = 0; i < m.map.size(); ++i ) {
            int k = r.map.indexOf(m.map.get(i));
            int j = r.inverseMap.indexOf(m.inverseMap.get(i));
            
            // se non ci sono conflitti
            if(k == -1 && j == -1) {
                r.map.add(m.map.get(i));
                r.inverseMap.add(m.inverseMap.get(i)); 
                continue ;
            }
            
            // se c'è un'assegnazione modificata ( a->x ; a->k ==> a->k)
            if(k != -1) {
                r.inverseMap.set(k,m.inverseMap.get(i));
                continue ;
            }
            
            // lettera ri-assegnata ( a->j ; b->j ==> b->j )
            if( j != -1) {
                r.map.set(j,m.map.get(i));
                continue ;
            }
            
            // doppio conflitto ( a->j && b->k ; a->k ==> a->k )
            r.map.remove(k);
            r.inverseMap.remove(k);
            r.map.set(j,m.map.get(i));
            r.inverseMap.set(j,m.inverseMap.get(i));
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
     * {a > z, b > y} sottrai {a > z, b > w }  ==> IllegalArgumentException: conflitti
     * {b > y, c > w} sottrai {a > z,  b > y, c > w }  ==> IllegalArgumentException: troppo lunga
     * 
     */
    public MappaturaParziale sottrai(MappaturaParziale m) {
        int size = this.size();
        if(m.size() > size) {
            throw new IllegalArgumentException("Impossibile sottrarre mappa: troppo lunga");
        }
        MappaturaParziale r = new MappaturaParziale();
        for(int i = 0; i < size; ++i) {
            int k = m.map.indexOf(map.get(i));
            if(k == -1) {
                r.map.add(map.get(i));
                r.inverseMap.add(inverseMap.get(i));
            }else{
                if(m.inverseMap.get(k) != inverseMap.get(i)) {
                    throw new IllegalArgumentException("Impossibile sottrarre mappa: conflitti");
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
        for(int i = 0; i < m.map.size(); ++i) {
            int k = map.indexOf(m.map.get(i));
            int j = inverseMap.indexOf(m.inverseMap.get(i));
            if  (  k != -1  && inverseMap.get(k)!=m.inverseMap.get(i)) {
                return true;
            }
            if  ( j != -1   && map.get(j)!=m.map.get(i)) {
                return true;
            }
        }
        return false;
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
        int i,k;
        for(i = 0; i < m.size(); ++i) {
            k = map.indexOf(m.map.get(i));
            if(k == -1 || m.inverseMap.get(i) != inverseMap.get(k)) {
                return false;
            }
        }
        return this.size() == i;
    }
    
    @Override
    public boolean equals(Object m) {
        return this.equals((MappaturaParziale) m);
    }
    
    @Override
    public String toString() {
        if(map.isEmpty()) {
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
