package com.viggys.algorithms.part3.week4;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class KnapsackComputation {

    private static int CAPACITY;
    private static Item[] ITEMS;
    private static Map<Integer, Map<Integer, Long>> CACHE = new HashMap<>();

    public static void main(String[] args) {

        String path = "src/main/resources/knapsack_big.txt";
        readData(path);
        long sackValue = computeSackValue(ITEMS.length, CAPACITY);
        System.out.println("SackValue[" + ITEMS.length + "][" + CAPACITY + "] = " + sackValue);
    }

    private static long computeSackValue(int items, int capacity) {
        long sackValue = 0;
        if(CACHE.containsKey(items)) {
            if(CACHE.get(items).containsKey(capacity)) {
                sackValue = CACHE.get(items).get(capacity);
//                System.out.println("Found cache for SackValue[" + items + "][" + capacity + "] = " + sackValue);
                return sackValue;
            }
        }
        if(capacity == 0) sackValue = 0;
        else if(items == 1) {
            Item item = ITEMS[items - 1];
            if(item.getWeight() <= capacity)
                sackValue = item.getValue();
        }
        else {
            Item item = ITEMS[items - 1];
            long addedSackValue = 0;
            long previousStackValue = 0;
            if(item.getWeight() <= capacity) {
                addedSackValue = item.getValue() +
                        computeSackValue(items - 1, capacity - item.getWeight());
            }
            previousStackValue = computeSackValue(items - 1, capacity);
            sackValue = Math.max(previousStackValue, addedSackValue);
        }
        if(!CACHE.containsKey(items))
            CACHE.put(items, new HashMap<>());
        CACHE.get(items).put(capacity, sackValue);
//        System.out.println("SackValue[" + items + "][" + capacity + "] = " + sackValue);
        return sackValue;
    }

    private static void readData(String path) {
        try{
            File inputFile = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            try {
                String line = reader.readLine();
                String[] data = line.split("[ \t]");
                CAPACITY = Integer.parseInt(data[0]);
                int itemsCount = Integer.parseInt(data[1]);
                System.out.println("CAPACITY = " + CAPACITY + ", ITEMS = " + itemsCount);
                ITEMS = new Item[itemsCount];
                line = reader.readLine();
                int count = 0;
                while (StringUtils.isNotEmpty(line)) {
                    data = line.split("[ \t]");
                    long value = Long.parseLong(data[0]);
                    int weight = Integer.parseInt(data[1]);
//                    System.out.println(count + " => " + value + ", " + weight);
                    ITEMS[count] = new Item(value, weight);
                    line = reader.readLine();
                    count++;
                }
            }
            finally {
                reader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
