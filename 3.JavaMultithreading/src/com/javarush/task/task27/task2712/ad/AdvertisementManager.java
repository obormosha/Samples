package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
        if (storage.list().isEmpty()) {
            throw new NoVideoAvailableException();
        }

        // находим списки вариантов к показу, будем из них выбирать оптимальный
        Set<Set<Advertisement>> listsVarToDisplaying = searchLists(new HashSet<Advertisement>());

        //для тестирования
/*
        System.out.println("Variants: " + listsVarToDisplaying.size());
        for (Set<Advertisement> list : listsVarToDisplaying) {
            for (Advertisement ad : list) {
                System.out.print(ad.getName() + "; ");
            }
            System.out.println("");
        }*/

        Set<Advertisement> advToDisplaying = new HashSet<>();

        int maxSum = 0;
        //выбираем наборы с максимальной стоимостью
        Set<Set<Advertisement>> maxSumSets = new HashSet<>();
        for (Set<Advertisement> s : listsVarToDisplaying) {
            int currentSum = currentSum(s);
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
        }
        for (Set<Advertisement> s : listsVarToDisplaying) {
            if (currentSum(s) == maxSum) {
                maxSumSets.add(s);
            }
        }

        //для тестирования
/*
        System.out.println("Variants: " + maxSumSets.size());
        for (Set<Advertisement> list : maxSumSets) {
            for (Advertisement ad : list) {
                System.out.print(ad.getName() + "; ");
            }
            System.out.println("");
        }*/

        int maxTime = 0;
        int minCount = 0;
        Set<Set<Advertisement>> maxTimeSets = new HashSet<>();
        if (maxSumSets.size() > 1) {
            for (Set<Advertisement> s : maxSumSets) {
                int currentTime = currentTime(s);
                if (currentTime > maxTime) {
                    maxTime = currentTime;
                }
            }
            for (Set<Advertisement> s : maxSumSets) {
                if (currentTime(s) == maxTime) {
                    maxTimeSets.add(s);
                }
            }

            if (maxTimeSets.size() > 1) {
                for (Set<Advertisement> s : maxTimeSets) {
                    if (minCount == 0) { //todo: fix this
                        minCount = s.size();
                        advToDisplaying = s;
                    } else {
                        if (s.size() < minCount) {
                            minCount = s.size();
                            advToDisplaying = s;
                        }
                    }
                }
            } else {
                advToDisplaying = maxTimeSets.iterator().next();
            }
        } else {
            advToDisplaying = maxSumSets.iterator().next();
        }


        List<Advertisement> listAdvToDisplaying = new ArrayList<>(); //fixme: что это? Зачем оно нужно?
        listAdvToDisplaying.addAll(advToDisplaying);
        Collections.sort(listAdvToDisplaying, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                if ((o1.getAmountPerOneDisplaying() - o2.getAmountPerOneDisplaying()) == 0) {
                    return (int) (o1.getAmountPerOneDisplaying() * 1000 / o1.getDuration() - o2.getAmountPerOneDisplaying() * 1000 / o2.getDuration());
                } else {
                    return (int) (o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying());
                }
            }
        });



        /*
        Set<Advertisement> sortedSet = new TreeSet<Advertisement>(new Comparator<Advertisement>() {
            public int compare(Advertisement o1, Advertisement o2) {
                return (int) (o1.getAmountPerOneDisplaying() - o2.getAmountPerOneDisplaying());
            }
        });
        sortedSet.addAll(advToDisplaying);
*/

        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(listAdvToDisplaying, maxSum, maxTime));

        for (Advertisement ad : listAdvToDisplaying) {
            ConsoleHelper.writeMessage(ad.getName() + " is displaying... "
                    + ad.getAmountPerOneDisplaying()
                    + ", " + ad.getAmountPerOneDisplaying() * 1000 / ad.getDuration());
            ad.revalidate();
        }

    }

    public Set<Set<Advertisement>> searchLists(Set<Advertisement> list) {
        boolean isEnd = true;
        Set<Set<Advertisement>> result = new HashSet<>();

        for (Advertisement ad : storage.list()) {
            if (isAvailableBranch(list, ad)) {
                Set<Advertisement> newCopyList = copyList(list);
                newCopyList.add(ad);
                result.addAll(searchLists(newCopyList));
                isEnd = false;
            }
        }
        if (isEnd) {
            result.add(list);
        }
        return result;
    }

    public boolean isAvailableBranch(Set<Advertisement> l, Advertisement ad) {
        return ((currentTime(l) + ad.getDuration() <= timeSeconds) && !l.contains(ad) && ad.getHits() >= 1);
    }

    public Set<Advertisement> copyList(Set<Advertisement> l) {
        Set<Advertisement> copyList = new HashSet<>();
        copyList.addAll(l);
        return copyList;
    }

    public int currentTime(Set<Advertisement> l) {
        int currentTime = 0;
        if (l.size() == 0) {
            return 0;
        } else {

            for (Advertisement ad : l) {
                currentTime += ad.getDuration();
            }
        }
        return currentTime;
    }

    public int currentSum(Set<Advertisement> l) {
        int currentSum = 0;
        if (l.size() == 0) {
            return 0;
        } else {

            for (Advertisement ad : l) {
                currentSum += ad.getAmountPerOneDisplaying();
            }
        }
        return currentSum;
    }

}
