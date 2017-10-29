package com.javarush.task.task36.task3605;

import java.io.*;
import java.util.Iterator;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader fis = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0]))));
        TreeSet<String> alphabet = new TreeSet<>();

        while (fis.ready()) {
            char[] chars = fis.readLine().toLowerCase().toCharArray();
            for (char ch : chars) {
                if (Character.isLetter(ch)) {
                    alphabet.add(String.valueOf(ch));
                }
            }
        }

        if (alphabet.size() < 5) {
            for (String s : alphabet) {
                System.out.print(s);
            }

        } else {
            Iterator<String> iter = alphabet.iterator();
            for (int i = 0; i < 5; i++) {
                System.out.print(iter.next());
            }
        }
    }
}
