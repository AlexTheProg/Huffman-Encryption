package weights;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

class ModelProbabilistic {
    private FileInputStream input_file;


    static Comparator<Entry<Character, Double>> valueComparator = new Comparator<Entry<Character, Double>>() {
        @Override
        public int compare(Entry<Character, Double> e1, Entry<Character, Double> e2) {
            Double v1 = e1.getValue();
            Double v2 = e2.getValue();
            return v2.compareTo(v1);
        }
    };

    void calculModel(FileInputStream file) throws IOException, FileNotFoundException  {
        input_file = new FileInputStream("text_sample.txt");
        DataInputStream dis = new DataInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(dis));
        String Contents = "";
        String str = "";

        while ((Contents = br.readLine()) != null) {
            str += Contents;
        }

        char[] char_array = str.toCharArray();
        int count = 0;
        char ch = char_array[count];
        Map<Character, Double> charCounter = new HashMap<Character, Double>();
        for (int i = 0; i < str.length(); i++) {
            ch = char_array[i];
            if (charCounter.containsKey(ch)) {
                charCounter.put(ch, charCounter.get(ch) + 1);
            } else {
                charCounter.put(ch, 1d);
            }

            Set<Entry<Character, Double>> entries = charCounter.entrySet();

            List<Entry<Character, Double>> listOfEntries = new ArrayList<Entry<Character, Double>>(entries);
            Collections.sort(listOfEntries, valueComparator);

            LinkedHashMap<Character, Double> sortedByValue = new LinkedHashMap<Character, Double>(listOfEntries.size());

            for (Entry<Character, Double> entry : listOfEntries) {
                sortedByValue.put(entry.getKey(), entry.getValue());
            }

            System.out.println("Frequency of each character in the sample text: ");
            Set<Entry<Character, Double>> entrySetSortedByValue = sortedByValue.entrySet();

            for (Entry<Character, Double> mapping : entrySetSortedByValue)
                System.out.println(mapping.getKey() + "===>" + mapping.getValue());
        }

    }
}