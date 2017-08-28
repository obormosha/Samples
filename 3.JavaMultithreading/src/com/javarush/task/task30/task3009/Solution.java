package com.javarush.task.task30.task3009;

import java.util.HashSet;
import java.util.Set;

/* 
Палиндром?
*/

public class Solution {
    static Set<Integer> scales = new HashSet<>();

    static {
        for (int i = 2; i < 37; i++) {
            scales.add(i);
        }
    }

    private static Set<Integer> getRadix(String number) {
        Set<Integer> result = new HashSet<>();

        try {
            for (Integer scale : scales) {
                String b = Integer.toString(Integer.parseInt(number), scale);
                if (isPalindrom(b)) {
                    result.add(scale);
                }
            }
        } catch (NumberFormatException e) {

        }
        return result;
    }

    private static boolean isPalindrom(String b) {
        char[] numbers = b.toCharArray();
        int lengthOfnumbers = numbers.length;

        if (lengthOfnumbers % 2 == 0) {
            for (int i = 0; i < lengthOfnumbers / 2; i++) {
                if (numbers[i] != numbers[lengthOfnumbers - 1 - i]) {
                    return false;
                }
            }
            return true;
        } else {
            for (int i = 0; i < (lengthOfnumbers - 1) / 2; i++) {
                if (numbers[i] != numbers[lengthOfnumbers - 1 - i]) {
                    return false;
                }
            }
            return true;
        }
    }


    public static void main(String[] args) {
        System.out.println(getRadix("112"));        //expected output: [3, 27, 13, 15]
        System.out.println(getRadix("123"));        //expected output: [6]
        System.out.println(getRadix("5321"));       //expected output: []
        System.out.println(getRadix("1A"));         //expected output: []
    }
}