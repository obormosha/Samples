package com.javarush.task.task30.task3010;

/* 
Минимальное допустимое основание системы счисления
*/

import java.math.BigInteger;

public class Solution {
    public static void main(String[] args) {
        //напишите тут ваш код
        try {
            String str = args[0].toUpperCase();
            int max = -1;
            for (int i = 2; i < 37; i++) {
                try {
                    BigInteger integ = new BigInteger(str, i);
                    max = i;
                    System.out.println(max);
                    break;
                } catch (Exception e) {
                }
            }

            if (max == -1) {
                System.out.println("incorrect");
            }
        } catch (Exception e) {

        }


    }
}