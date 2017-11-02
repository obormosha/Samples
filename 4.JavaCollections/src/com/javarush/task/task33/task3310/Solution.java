package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        HashMapStorageStrategy strategy = new HashMapStorageStrategy();
        s.testStrategy(strategy, 100);
        OurHashMapStorageStrategy strategy1 = new OurHashMapStorageStrategy();
        s.testStrategy(strategy1, 100);
        FileStorageStrategy strategy2 = new FileStorageStrategy();
        s.testStrategy(strategy2, 100);
        OurHashBiMapStorageStrategy strategy3 = new OurHashBiMapStorageStrategy();
        s.testStrategy(strategy3, 100);
        HashBiMapStorageStrategy strategy4 = new HashBiMapStorageStrategy();
        s.testStrategy(strategy4, 100);
        DualHashBidiMapStorageStrategy strategy5 = new DualHashBidiMapStorageStrategy();
        s.testStrategy(strategy5, 100);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> ids = new HashSet<>();
        for (String s : strings) {
            ids.add(shortener.getId(s));
        }
        return ids;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> strings = new HashSet<>();
        for (Long l : keys) {
            strings.add(shortener.getString(l));
        }
        return strings;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName());
        Set<String> testStirng = new HashSet<>();
        for (int i = 0; i < elementsNumber; i++) {
            testStirng.add(Helper.generateRandomString());
        }
        Shortener shortener = new Shortener(strategy);
        Set<Long> ids = new HashSet<>();
        Date startGetIds = new Date();
        ids = getIds(shortener, testStirng);
        Date endGetIds = new Date();
        Helper.printMessage(String.valueOf(endGetIds.getTime() - startGetIds.getTime()));

        Set<String> strings = new HashSet<>();
        Date startGetStrings = new Date();
        strings = getStrings(shortener, ids);
        Date endGetStrings = new Date();
        Helper.printMessage(String.valueOf(endGetStrings.getTime() - startGetStrings.getTime()));

        if (testStirng.equals(strings)) {
            Helper.printMessage("Тест пройден.");
        } else {
            Helper.printMessage("Тест не пройден.");
        }
    }

}
