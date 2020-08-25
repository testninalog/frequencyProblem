package com.endava.frequence;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FrequencyCreator {

    private String[] charsToRemove = {",", ".", "...", "?", ";", "\"", "'", "!", "”", "”", "’", ":", "(", ")"}; //uvodimo zbog clean metode promenljive

    public Map<String, Integer> calculateFrequency(String path) throws URISyntaxException {//path je putanja fajla

        List<String> words = readFile(path);//from file to list of words

        Map<String, Integer> frequencyMap = new HashMap<>();//unorder, unsorted collection

        //iterate trough each word dodaj u mapu sa frekvencijom 1, ako vec postoji u mapi povecaj frekvenciju++
        for (String word : words) {//for each word in our list
            if (!frequencyMap.containsKey(word)) {
                frequencyMap.put(word, 1);
            } else {
                Integer f = frequencyMap.get(word);//uzimamo frekvenciju reci
                frequencyMap.put(word, f + 1);//povecavamo za jedan
            }
        }
        // treba da sortiramo mapu po frekvenciji
        Map<String,Integer> sortedMap =
                frequencyMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return sortedMap;

    }

    // citanje rec po rec metoda
    public List<String> readFile(String pathToFile) throws URISyntaxException {
        URL url = FrequencyCreator.class.getClassLoader().getResource(pathToFile);
        Path path = Paths.get(url.toURI());
        List<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNext()) {
                String word = scanner.next();//ovo nam sada treba radi clean
                String cleanWord = cleanString(word);
                if (!cleanWord.isEmpty()) {
                    words.add(cleanWord);//dodajemo sledecu rec, da ne bi pokupili specijalne znake praviom metodu cleanString
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    private String cleanString(String dirtyString) {
        String cleanString = dirtyString; // inicijalna vrednost koju cistio od nepotrebnih karaktera

        for (String charToRemove : charsToRemove) { //cesto se koristi ovaj koncept jedan element jednina, niz elemenata mnozina

            cleanString = cleanString.replace(charToRemove, "");
        }
        return cleanString.toLowerCase();//vracamo sve malim slovima
    }
}
