package com.javarush.task.task31.task3110_backup.task3110;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Operator on 29.04.2017.
 */
public class ConsoleHelper {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        String message = null;
        while (message == null) {
            message = bufferedReader.readLine();
        }
        return message;
    }

    public static int readInt() throws IOException {
        Integer num = null;
        while (num == null) {
            num = Integer.parseInt(readString());
        }
        return num;
    }

}
