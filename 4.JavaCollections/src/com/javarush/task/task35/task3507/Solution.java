package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) {
        Set<? super Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? super Animal> getAllAnimals(String pathToAnimals) {
        Set<? super Animal> result = new HashSet<>();
        File[] listOfFiles = new File(pathToAnimals).listFiles();

        class MyClassLoader extends ClassLoader {
            String pathtobin;

            public MyClassLoader(String pathtobin) {
                super();
                this.pathtobin = pathtobin;
            }

            Class load(Path path) throws IOException {
                byte b[] = Files.readAllBytes(path);
                return defineClass(null, b, 0, b.length);
            }
        }

        for (File f : listOfFiles) {
            if (f.isFile() && f.getName().endsWith(".class")) {

                MyClassLoader loader = new MyClassLoader(f.getClass().toString());
                try {
                    Class clazz = loader.load(f.toPath());
                    Animal inst = (Animal) clazz.newInstance();
                    result.add(inst);
                } catch (Exception e) {

                }
            }
        }


        return result;
    }
}