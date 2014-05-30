/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptohelper.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mat
 */
public interface MappaturaParziale {

    public Character map(char c);

    public Character inverseMap(char c);

    public String serialize();

    public int size();

    public ArrayList<Character> getMap();

    public ArrayList<Character> getInverseMap();

    public MappaturaParziale merge(MappaturaParziale m);

    public int contaConflitti(MappaturaParziale m);

    boolean contains(Character daRimuovere);

    public boolean conflitto(MappaturaParziale m);

    List<Character> filtraDaRimuovere();

    public boolean isCompleta(List<Character> listaCaratteri);

    public MappaturaParziale sottrai(MappaturaParziale m);

}
