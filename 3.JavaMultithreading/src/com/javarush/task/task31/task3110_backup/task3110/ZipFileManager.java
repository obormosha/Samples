package com.javarush.task.task31.task3110_backup.task3110;

import com.javarush.task.task31.task3110_backup.task3110.exception.PathIsNotFoundException;
import com.javarush.task.task31.task3110_backup.task3110.exception.WrongZipFileException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Operator on 26.04.2017.
 */
public class ZipFileManager {
    private Path zipFile;

    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }

    public void createZip(Path source) throws Exception {
        if (!Files.exists(zipFile.getParent())) {
            Files.createDirectories(zipFile.getParent());
        }
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile))) {

            if (Files.isDirectory(source)) {
                com.javarush.task.task31.task3110.FileManager fileManager = new com.javarush.task.task31.task3110.FileManager(source);
                List<Path> fileNames = fileManager.getFileList();
                for (Path p : fileNames) {
                    addNewZipEntry(zipOutputStream, source, p);
                }
            } else if (Files.isRegularFile(source)) {
                addNewZipEntry(zipOutputStream, source.getParent(), source.getFileName());
            } else {
                throw new PathIsNotFoundException();
            }
        }

    }

    private void addNewZipEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws Exception {
        try (InputStream inputStream = Files.newInputStream(filePath.resolve(fileName))) {
            ZipEntry zipEntry = new ZipEntry(fileName.toString());
            zipOutputStream.putNextEntry(zipEntry);
            copyData(inputStream, zipOutputStream);
            zipOutputStream.closeEntry();
        }
    }

    private void copyData(InputStream in, OutputStream out) throws Exception {
        while (in.available() > 0) {
            out.write(in.read());
        }
    }

    public List<com.javarush.task.task31.task3110.FileProperties> getFilesList() throws Exception {
        List<com.javarush.task.task31.task3110.FileProperties> filesProperties = new ArrayList<>();
        if (Files.isRegularFile(zipFile)) {
            try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) {
                ZipEntry zipEntry = zipInputStream.getNextEntry();
                while (zipEntry != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    copyData(zipInputStream, byteArrayOutputStream);

                    filesProperties.add(new com.javarush.task.task31.task3110.FileProperties(zipEntry.getName(), byteArrayOutputStream.size(), zipEntry.getCompressedSize(), zipEntry.getMethod()));
                    zipEntry = zipInputStream.getNextEntry();
                }
            }
        } else {
            throw new WrongZipFileException();
        }
        return filesProperties;
    }

}