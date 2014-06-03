package cryptohelper.model;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;

/*
 * COME USARE IL RISULTATO:
 *
 *  for (Map.Entry<Character, Float> entry : result.entrySet()) {
 *     System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
 *  }
 */
public class AnalisiFrequenze {

    public static Map<Character, Float> get_frequency(Messaggio m) {

        Mappatura mappatura = m.getSistemaCifratura().getMappatura();
        Map<Character, Integer> frequenzeAssolute = new HashMap<>();
        Map<Character, Float> tabellaFrequenzeDisordinata = new HashMap<>();
        Comparator comparator = new ValueComparator(tabellaFrequenzeDisordinata);
        Map<Character, Float> tabellaFrequenzeOrdinata = new TreeMap<>(comparator);
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
                tabellaFrequenzeDisordinata.put(c, 0.0f);
            } else {
                float frequenzaRelativa = new Float(occorrenze) / lunghezzaMessaggio;
                tabellaFrequenzeDisordinata.put(c, frequenzaRelativa * 100.0f);
            }
        }

        //  copiamo la mappa <carattere, frequenza %> in una ordinata
        tabellaFrequenzeOrdinata.putAll(tabellaFrequenzeDisordinata);

        return tabellaFrequenzeOrdinata;
    }

    static class ValueComparator implements Comparator<Character> {

        Map<Character, Float> base;

        ValueComparator(Map<Character, Float> base) {
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
}
