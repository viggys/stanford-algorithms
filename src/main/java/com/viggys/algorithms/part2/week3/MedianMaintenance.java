package com.viggys.algorithms.part2.week3;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

public class MedianMaintenance {

    private static Heap minHeap = new Heap(HeapType.MIN);
    private static Heap maxHeap = new Heap(HeapType.MAX);

    public static void main(String[] args) {

        String path = "src/main/resources/Median.txt";
        int length = 10000;
        int[] input = readData(path, length);
        BigInteger sum = BigInteger.ZERO;
        for(int number : input) {
            insert(number);
            Integer median = getMedian();
            System.out.println("Median = " + median);
            sum = sum.add(BigInteger.valueOf(median));
        }
        System.out.println("Median Sum = " + sum);
        BigInteger result = sum.mod(BigInteger.valueOf(10000));
        System.out.println("Result = " + result);
    }

    public static Integer getMedian() {
        int minSize = minHeap.getSize();
        int maxSize = maxHeap.getSize();
        if(minSize < maxSize)
            return maxHeap.get(0);
        else if(minSize > maxSize)
            return minHeap.get(0);
        else
            return maxHeap.get(0);
    }

    public static void insert(Integer number) {
        if(minHeap.isEmpty()) {
            minHeap.insert(number);
        }
        else {
            Integer min = minHeap.get(0);
            if(number > min) {
                minHeap.insert(number);
            }
            else {
                maxHeap.insert(number);
            }

            int sizeDiff = Math.abs(minHeap.getSize() - maxHeap.getSize());
            if(sizeDiff > 1) {
                rearrange();
            }
        }
    }

    private static void rearrange() {
        if(minHeap.getSize() > maxHeap.getSize()) {
            Integer min = minHeap.remove(0);
            maxHeap.insert(min);
        }
        else {
            Integer max = maxHeap.remove(0);
            minHeap.insert(max);
        }
    }

    private static int[] readData(String path, int length) {
        int[] input = new int[length];
        File inputFile = new File(path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            try{
                String line = reader.readLine();
                int count = 0;
                while(StringUtils.isNotEmpty(line)) {
                    input[count++] = Integer.parseInt(line);
//                    System.out.println(count + " => " +line);
                    line = reader.readLine();
                }
            }
            finally {
                reader.close();
            }
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return input;
    }



}
