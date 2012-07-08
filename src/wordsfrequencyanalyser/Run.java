package wordsfrequencyanalyser;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.*;
import java.io.*;

public class Run {

    /**
     * @param args
     *            Путь к текстовому файлу, содержащему слова на английском
     *            языке, знаки препинания и т.п. java -jar App.jar
     *            com.example.App input.txt
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        /*
         * Я не понял, что такое com.example.App. (package? Или путь внутри собранного jar файла? 
         * Пока что я не знаю что с этим делать). Входные файлы положил в корень проекта,
         * и получаю к ним доступ, указав только лишь их имя.
         */
        String fileName = args[1];
        Map<String, Integer> wordsMap = new TreeMap<String, Integer>();
        BufferedReader inp = null;

        try {

            inp = new BufferedReader(new FileReader(fileName));
            String line;

            //Работа с файлом построчно. Отделение слов с помощью BreakIterator.
            while ((line = inp.readLine()) != null) {
                BreakIterator boundary = BreakIterator.getWordInstance();
                boundary.setText(line);
                int start = boundary.first();
                int end = boundary.next();
                while (end != BreakIterator.DONE) {
                    String word = line.substring(start, end);
                    //все слова к нижнему регистру, чтобы не было дублирования
                    word = word.toLowerCase();
                    if (Character.isLetterOrDigit(word.charAt(0))) {
                        //если еще не встречали такое слово, добавим в wordsMap
                        if (!wordsMap.containsKey(word)) {
                            wordsMap.put(word, 0);
                        }
                        //увеличим количество встреч этого слова
                        wordsMap.put(word, wordsMap.get(word) + 1);
                    }
                    start = end;
                    end = boundary.next();
                }
            }

        } finally {
            if (inp != null) {
                inp.close();
            }
        }

        //Множество с количеством встреч разных слов, упорядоченное в обратном порядке
        Set<Integer> frequenciesDescentSet = new TreeSet<Integer>(
                new Comparator<Integer>() {
                    public int compare(Integer int1, Integer int2) {
                        return -int1.compareTo(int2);
                    }
                });

        Iterator<Integer> frequenciesValuesIterator = wordsMap.values().iterator();
        while (frequenciesValuesIterator.hasNext()) {
            frequenciesDescentSet.add(frequenciesValuesIterator.next());
        }
        
        //проходя по количеству встреч слов и ключам/словам выписываем подходящие на экран
        Iterator<Integer> frequencySetIterator = frequenciesDescentSet.iterator();
        while (frequencySetIterator.hasNext()) {
            int i = frequencySetIterator.next();
            for (int j = 0; j < wordsMap.size(); j++) {
                String word = (String) wordsMap.keySet().toArray()[j];
                if (i == wordsMap.get(word)) {
                    System.out.println(word + " " + i);
                }
            }
        }
    }
}
