package com.javarush.task.task26.task2601;

import java.util.Arrays;
import java.util.Comparator;

/*
Почитать в инете про медиану выборки
*/
public class Solution {

    public static void main(String[] args) {
        /*
        Integer[] sortedArray = new Integer[]{2, 5, 6, 7, 21, 1};
        sort(sortedArray);

        for (int i : sortedArray) {
            System.out.println(i);
        }
*/
    }

    public static Integer[] sort(Integer[] array) {
        //implement logic here
        final double mediana;
        int middle = array.length / 2;
        Arrays.sort(array);

        if (array.length % 2 != 0) {
            mediana = array[middle];
        } else {
            mediana = (array[middle - 1] + array[middle]) / 2.0;
        }

        Arrays.sort(array, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                double result = Math.abs(o1 - mediana) - Math.abs(o2 - mediana);

                return result == 0 ? o1 - o2 : (int) Math.round(result);
            }
        });

        return array;
    }
}
