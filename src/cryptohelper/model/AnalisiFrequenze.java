package cryptohelper.model;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Collections;

/*
 * COME USARE IL RISULTATO:
 *
 *  for (Map.Entry<Character, Double> entry : result.entrySet()) {
 *     System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
 *  }
 */
public class AnalisiFrequenze {

    private AnalisiFrequenze() {

    }

    public static Map<Character, Double> getFrequency(MessaggioSpia m) throws SQLException {

        Mappatura mappatura = m.getSdc().getMappatura();
        Map<Character, Integer> frequenzeAssolute = new HashMap<>();
        Map<Character, Double> tabellaFrequenzeDisordinata = new HashMap<>();
        Comparator comparator = new ValueComparator(tabellaFrequenzeDisordinata);
        Map<Character, Double> tabellaFrequenzeOrdinata = new TreeMap<>(comparator);
        char[] messaggioToArray = m.getTestoCifrato().toCharArray();
        int lunghezzaMessaggio = messaggioToArray.length;

        //  recuperiamo le frequenze assolute per ogni carattere
        for (char c : messaggioToArray) {
            Integer count = frequenzeAssolute.get(c);
            if (null == count) {
                frequenzeAssolute.put(c, 1);
            } else {
                frequenzeAssolute.put(c, count + 1);
            }
        }

        //  per ogni simbolo nella mappatura assegnamo la percentuale di occorrenza
        for (char c : mappatura.getInverseMap()) {
            Integer occorrenze = frequenzeAssolute.get(c);
            if (null == occorrenze) {
                tabellaFrequenzeDisordinata.put(c, 0.0);
            } else {
                double frequenzaRelativa = new Double(occorrenze) / lunghezzaMessaggio;
                /* il calcolo calcola la percentuale con due cifre decimali */
                tabellaFrequenzeDisordinata.put(c, Math.round(frequenzaRelativa * 10000) / 100.0);
            }
        }

        //  copiamo la mappa <carattere, frequenza %> in una ordinata
        tabellaFrequenzeOrdinata.putAll(tabellaFrequenzeDisordinata);

        return tabellaFrequenzeOrdinata;
    }

    public static Map<Character, Double> getFrequency(String lingua) {
        Map<Character, Double> tabellaFrequenzeDisordinata = frequenzeLingue.get(lingua);
        Comparator comparator = new ValueComparator(tabellaFrequenzeDisordinata);
        Map<Character, Double> tabellaFrequenzeOrdinata = new TreeMap<>(comparator);
        tabellaFrequenzeOrdinata.putAll(tabellaFrequenzeDisordinata);
        return tabellaFrequenzeOrdinata;
    }

    private static class ValueComparator implements Comparator<Character> {

        Map<Character, Double> base;

        ValueComparator(Map<Character, Double> base) {
            this.base = base;
        }

        @Override
        public int compare(Character a, Character b) {
            // return 0 fa il merge dei valori uguali (no buono)
            if (base.get(a) > base.get(b)) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    private static final Map<String, Map<Character, Double>> frequenzeLingue = new HashMap();

    static {
        Map<Character, Double> aMap = new HashMap();
        aMap.put('e', 11.79);
        aMap.put('a', 11.74);
        aMap.put('i', 11.28);
        aMap.put('o', 9.83);
        aMap.put('n', 6.88);
        aMap.put('l', 6.51);
        aMap.put('r', 6.37);
        aMap.put('t', 5.62);
        aMap.put('s', 4.98);
        aMap.put('c', 4.50);
        aMap.put('d', 3.73);
        aMap.put('p', 3.05);
        aMap.put('u', 3.01);
        aMap.put('m', 2.51);
        aMap.put('v', 2.10);
        aMap.put('g', 1.64);
        aMap.put('h', 1.54);
        aMap.put('f', 0.95);
        aMap.put('b', 0.92);
        aMap.put('q', 0.51);
        aMap.put('z', 0.49);
        aMap.put('k', 0.0);
        aMap.put('j', 0.0);
        aMap.put('w', 0.0);
        aMap.put('x', 0.0);
        aMap.put('y', 0.0);

        frequenzeLingue.put("Italiano", Collections.unmodifiableMap(aMap));

        aMap = new HashMap();
        aMap.put('e', 13.00);
        aMap.put('t', 9.06);
        aMap.put('a', 8.17);
        aMap.put('o', 7.52);
        aMap.put('i', 6.97);
        aMap.put('n', 6.75);
        aMap.put('s', 6.33);
        aMap.put('h', 6.09);
        aMap.put('r', 5.99);
        aMap.put('d', 4.25);
        aMap.put('l', 4.03);
        aMap.put('c', 2.78);
        aMap.put('u', 2.76);
        aMap.put('m', 2.41);
        aMap.put('w', 2.36);
        aMap.put('f', 2.23);
        aMap.put('g', 2.02);
        aMap.put('y', 1.97);
        aMap.put('p', 1.93);
        aMap.put('b', 1.49);
        aMap.put('v', 0.98);
        aMap.put('k', 0.77);
        aMap.put('j', 0.15);
        aMap.put('x', 0.15);
        aMap.put('q', 0.10);
        aMap.put('z', 0.07);

        frequenzeLingue.put("Inglese", Collections.unmodifiableMap(aMap));
    }

}
