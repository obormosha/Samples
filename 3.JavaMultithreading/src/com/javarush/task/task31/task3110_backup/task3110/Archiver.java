package com.javarush.task.task31.task3110_backup.task3110;

import com.javarush.task.task31.task3110_backup.task3110.command.ExitCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

/**
 * Created by Operator on 29.04.2017.
 */
public class Archiver {
    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a full path for archive");
        String fullPathToArchive;
        ZipFileManager zipFileManager = null;
        try {
            fullPathToArchive = bufferedReader.readLine();
            zipFileManager = new ZipFileManager(Paths.get(fullPathToArchive));
        } catch (IOException e) {
        }

        System.out.println("Enter a full path for file");
        String fullPathToFile;
        try {
            fullPathToFile = bufferedReader.readLine();
            zipFileManager.createZip(Paths.get(fullPathToFile));
        } catch (Exception e) {
        }

        ExitCommand exitCommand = new ExitCommand();
        try {
            exitCommand.execute();
        } catch (Exception e) {

        }
    }


}
