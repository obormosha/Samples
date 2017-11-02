package com.javarush.task.task33.task3310.strategy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    private Path path;

    public FileBucket() {
        try {
            path = Files.createTempFile("temp", "");
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
        }
        path.toFile().deleteOnExit();
    }

    public long getFileSize() {
        long size = 0;
        try {
            size = Files.size(path);
        } catch (IOException e) {
        }
        return size;
    }

    public void putEntry(Entry entry) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path));
            oos.writeObject(entry);
            oos.close();
        } catch (IOException e) {
        }
    }

    public Entry getEntry() {
        Entry entry = null;
        if (getFileSize() == 0) {
            return null;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path));
            entry = (Entry) ois.readObject();
            ois.close();
        } catch (Exception e) {
        }
        return entry;
    }

    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException e) {
        }
    }
}
