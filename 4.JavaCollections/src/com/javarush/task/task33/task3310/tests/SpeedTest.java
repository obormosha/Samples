package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.greaterThan;


public class SpeedTest {
    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids){
        Date startGetIds = new Date();
        for (String s : strings) {
            ids.add(shortener.getId(s));
        }
        Date endGetIds = new Date();
        return (endGetIds.getTime() - startGetIds.getTime());
    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings){
        Date startGetStrings = new Date();
        for (Long l : ids) {
            strings.add(shortener.getString(l));
        }
        Date endGetStrings = new Date();
        return (endGetStrings.getTime() - startGetStrings.getTime());
    }

    @Test
    public void testHashMapStorage(){
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = new HashSet<>();
        Set<Long> idsHashMapStorageStrategy = new HashSet<>();
        Set<Long> idsHashBiMapStorageStrategy = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }
        Long timeForHashMapStorageStrategy = getTimeForGettingIds(shortener1, origStrings, idsHashMapStorageStrategy);
        Long timeForHHashBiMapStorageStrategy = getTimeForGettingIds(shortener2, origStrings, idsHashBiMapStorageStrategy);
        Assert.assertThat(timeForHashMapStorageStrategy, greaterThan(timeForHHashBiMapStorageStrategy));

        Set<String> stringsHashMapStorageStrategy = new HashSet<>();
        Set<String> stringsHashBiMapStorageStrategy = new HashSet<>();
        Long timeForStringsForHashMapStorageStrategy = getTimeForGettingStrings(shortener1, idsHashMapStorageStrategy, stringsHashMapStorageStrategy);
        Long timeForStringsForHHashBiMapStorageStrategy = getTimeForGettingStrings(shortener2, idsHashBiMapStorageStrategy, stringsHashBiMapStorageStrategy);
        Assert.assertEquals(timeForStringsForHashMapStorageStrategy, timeForStringsForHHashBiMapStorageStrategy, 30);
    }

}
