package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

        char[] arr;

        while (true) {
            arr = randomGeneration(arrayOutputStream).toString().toCharArray();
            if (isValid(arr)) {
                return arrayOutputStream;
            }
        }

    }

    public static ByteArrayOutputStream randomGeneration(ByteArrayOutputStream arrayOutputStream) {
        try {
            arrayOutputStream.reset();
            arrayOutputStream.write(Integer.toString((int) (Math.random() * 36), 36).toUpperCase().getBytes());
            for (int i = 1; i < 6; i++) {
                arrayOutputStream.write(Integer.toString((int) (Math.random() * 36), 36).getBytes());
            }
            arrayOutputStream.write(Integer.toString((int) (Math.random() * 36), 36).toUpperCase().getBytes());
            arrayOutputStream.write(Integer.toString((int) (Math.random() * 36), 36).toUpperCase().getBytes());

            return arrayOutputStream;
        } catch (IOException e) {
        }
        return arrayOutputStream;
    }

    static boolean isValid(char[] arr) {
        boolean hasNumber = false;
        boolean hasSmallLetter = false;
        boolean hasBiglLetter = false;
        for (int i = 0; i < 8; i++) {
            if (arr[i] > 47 && arr[i] < 58) {
                hasNumber = true;
            } else if (arr[i] > 96 && arr[i] < 123) {
                hasSmallLetter = true;
            } else if(arr[i] > 64 && arr[i] < 91){
                hasBiglLetter = true;
            }
        }

        return hasNumber && hasSmallLetter && hasBiglLetter;
    }
}