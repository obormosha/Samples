package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorTablet {

    public void printAdvertisementProfit() {
        Map<Date, Double> advProfit = StatisticManager.getInstance().calculateProfit();

        double sum = 0.00;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        for (Map.Entry<Date, Double> d : advProfit.entrySet()) {
            System.out.println(simpleDateFormat.format(d.getKey()) + " - " + String.format(Locale.ENGLISH, "%(.2f", d.getValue()));
            sum += d.getValue();
        }
        ConsoleHelper.writeMessage("Total - " + String.format(Locale.ENGLISH, "%(.2f", sum));

    }

    public void printCookWorkloading() {
        Map<Date, TreeMap<String, Integer>> timeOfWorks = StatisticManager.getInstance().calculateTimeOfWork();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        boolean firstLine = true;

        for (Map.Entry<Date, TreeMap<String, Integer>> e : timeOfWorks.entrySet()) {
            ConsoleHelper.writeMessage((firstLine ? "" : "\n") + simpleDateFormat.format(e.getKey()));

            for (Map.Entry<String, Integer> m : e.getValue().entrySet()) {
                ConsoleHelper.writeMessage(m.getKey() + " - " + (int) Math.ceil(m.getValue() / 60.0d) + " min");
            }
            firstLine = false;
        }

    }

    public void printActiveVideoSet() {
        ArrayList<Advertisement> activeAdvList = StatisticAdvertisementManager.getInstance().getAdvertisementsFromStorage().get("Active");
        Collections.sort(activeAdvList, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        for (Advertisement ad : activeAdvList) {
            ConsoleHelper.writeMessage(ad.getName() + " - " + ad.getHits());
        }
    }

    public void printArchivedVideoSet() {
        ArrayList<Advertisement> archiveAdvList = StatisticAdvertisementManager.getInstance().getAdvertisementsFromStorage().get("Archive");
        Collections.sort(archiveAdvList, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        for (Advertisement ad : archiveAdvList) {
            ConsoleHelper.writeMessage(ad.getName());
        }
    }

}
