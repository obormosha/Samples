package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance;
    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager(){}

    public static StatisticAdvertisementManager getInstance() {
        if (instance == null) {
            instance = new StatisticAdvertisementManager();
        }
        return instance;
    }

    public Map<String, ArrayList<Advertisement>> getAdvertisementsFromStorage() {
        Map<String, ArrayList<Advertisement>> map = new HashMap<>();
        ArrayList<Advertisement> activeAdvertisements = new ArrayList<>();
        ArrayList<Advertisement> archiveAdvertisements = new ArrayList<>();

        for (Advertisement ad : advertisementStorage.list()) {
            if (ad.getHits() > 0) {
                activeAdvertisements.add(ad);
            } else {
                archiveAdvertisements.add(ad);
            }
        }
        map.put("Active", activeAdvertisements);
        map.put("Archive", archiveAdvertisements);

        return map;
    }

}
