package com.javarush.task.task32.task3201;

import java.io.IOException;
import java.io.RandomAccessFile;

/*
Запись в существующий файл
*/
public class Solution {
    public static void main(String... args) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(args[0], "rw");

            byte[] textInArrayBytes = args[2].getBytes();
            long pointInText = Integer.parseInt(args[1]);

            if ((pointInText) > randomAccessFile.length()) {
                pointInText = randomAccessFile.length();
            }
            randomAccessFile.seek(pointInText);
            randomAccessFile.write(textInArrayBytes);
            randomAccessFile.close();
        } catch (IOException e) {

        }


    }
}
