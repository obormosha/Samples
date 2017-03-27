package com.javarush.task.task22.task2209;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
Составить цепочку слов
*/
public class Solution {
    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String lineOfWords = null;
        try {
            String fileName = reader.readLine();
            reader.close();
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
            lineOfWords = fileReader.readLine();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder result = getLine(lineOfWords.replace("\uFEFF", "").split(" "));
        System.out.println(result.toString());
    }

    public static StringBuilder getLine(String... words) {

        if (words.length == 0) return new StringBuilder();

        HashMap<StringBuilder, Character> map = new HashMap<>();
        HashMap<StringBuilder, Character> mapCopy = new HashMap<>();
        int lengthOfAllWords = 0;
        for (String s : words) {
            lengthOfAllWords += s.length();
            map.put(new StringBuilder(s), s.toLowerCase().charAt(s.length() - 1));
        }
        lengthOfAllWords += words.length - 1;
        boolean isAnswer = false;


        while (!isAnswer) {
            for (Map.Entry<StringBuilder, Character> pair : map.entrySet()) {
                StringBuilder str = pair.getKey();
                char lastLetter = pair.getValue();

                for (int i = 0; i < words.length; i++) {
                    if ((lastLetter == words[i].toLowerCase().charAt(0)) && (str.indexOf(words[i]) < 0)) {
                        StringBuilder str2 = str;
                        str2.append(" ").append(words[i]);
                        lastLetter = words[i].toLowerCase().charAt(words[i].length() - 1);
                        mapCopy.put(str2, lastLetter);
                        //i = 0;
                    }
                }
                if (str.length() == lengthOfAllWords) {
                    isAnswer = true;
                    return str;
                }
            }
            if (!mapCopy.isEmpty()) {
                map.putAll(mapCopy);
                mapCopy.clear();
            } else {
                int count = 0;
                for (Map.Entry<StringBuilder, Character> pair : map.entrySet()) {
                    int countWords = pair.getKey().toString().split(" ").length;
                    if (countWords > count) {
                        count = countWords;
                    }
                }
                for (Map.Entry<StringBuilder, Character> pair : map.entrySet()) {
                    int countWords = pair.getKey().toString().split(" ").length;
                    if (countWords == count) {
                        return pair.getKey();
                    }
                }

            }
        }


        return new StringBuilder("");
    }
}
