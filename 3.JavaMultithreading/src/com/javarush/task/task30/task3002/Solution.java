package com.javarush.task.task30.task3002;

/* 
Осваиваем методы класса Integer
*/
public class Solution {

    public static void main(String[] args) {
        System.out.println(convertToDecimalSystem("0x16")); //22
        System.out.println(convertToDecimalSystem("012"));  //10
        System.out.println(convertToDecimalSystem("0b10")); //2
        System.out.println(convertToDecimalSystem("62"));   //62
    }

    public static String convertToDecimalSystem(String s) {

        int rad = 10;

        if (s.startsWith("0x")) {
            rad = 16;
            s = s.substring(2);
        } else if (s.startsWith("0b")) {
            rad = 2;
            s = s.substring(2);
        } else if (s.startsWith("0")) {
            rad = 8;
            //s = s.substring(1);
        }

        int i = Integer.parseInt(s, rad);
        s = String.valueOf(i);

        return s;
    }
}
