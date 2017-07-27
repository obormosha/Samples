package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorTablet {

    public void printAdvertisementProfit() {
        Map<Date, Double> advProfit = StatisticManager.getInstance().calculateProfit();
        //List<Date> dates = new ArrayList<>(advProfit.keySet());
        //Collections.sort(dates);
        double sum = 0.00;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        for (Map.Entry<Date, Double> d : advProfit.entrySet()) {
            System.out.println(simpleDateFormat.format(d.getKey()) + " - " + String.format(Locale.ENGLISH, "%(.2f", d.getValue()));
            sum += d.getValue();
        }
        System.out.println("Total - " + String.format(Locale.ENGLISH, "%(.2f", sum));
    }

    public void printCookWorkloading() {
        Map<Date, TreeMap<String, Integer>> timeOfWorks = StatisticManager.getInstance().calculateTimeOfWork();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        boolean firstLine = true;

        for (Map.Entry<Date, TreeMap<String, Integer>> e : timeOfWorks.entrySet()) {
            System.out.println((firstLine ? "" : "\n") + simpleDateFormat.format(e.getKey()));

            for (Map.Entry<String, Integer> m : e.getValue().entrySet()) {
                System.out.println(m.getKey() + " - " + (int) Math.ceil(m.getValue() / 60.0d) + " min");
            }
            firstLine = false;
        }

    }

    public void printActiveVideoSet() {
        ArrayList<Advertisement> activeAdvList = StatisticAdvertisementManager.getInstance().getAdvertisementsFromStorage().get("Active");
        activeAdvList.sort(new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        for (Advertisement ad : activeAdvList) {
            System.out.println(ad.getName() + " - " + ad.getHits());
        }
    }

    public void printArchivedVideoSet() {
        ArrayList<Advertisement> archiveAdvList = StatisticAdvertisementManager.getInstance().getAdvertisementsFromStorage().get("Archive");
        archiveAdvList.sort(new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        for (Advertisement ad : archiveAdvList) {
            System.out.println(ad.getName());
        }
    }

}
