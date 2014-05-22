/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptohelper.model;

import java.util.ArrayList;

/**
 * 
 * TODO: valutare dove fare dei clone()
 * @author mat
 */
public class MappaturaParziale {
    
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
    
    public void merge(MappaturaParziale newMap) {
        for(int i = 0; i < newMap.map.size(); ++i ) {
            int k = map.indexOf(newMap.map.get(i));
            if(inverseMap.indexOf(newMap.inverseMap.get(i)) != -1) {
                throw new RuntimeException("Lettera già assegnata a sx");
            }
            if(k == -1) {
                map.add(newMap.map.get(i));
                inverseMap.add(newMap.inverseMap.get(i));                
            }else{
                inverseMap.set(k,newMap.inverseMap.get(i));
            }

        }
    }
    
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
    
    public boolean equals(MappaturaParziale m) {
        int k;
        for(int i = 0; i < m.map.size(); ++i) {
            k = map.indexOf(m.map.get(i));
            if(k != -1 && m.inverseMap.get(i) == inverseMap.get(k)) {
                return true;
            }
        }
        return false;                
    }
    @Override
    public String toString() {
        String s = new String();
        for (int i = 0; i < map.size(); ++i) {
            s += map.get(i) + " -> " + inverseMap.get(i) + "\n";                    
        }
        return s;
    }

}
