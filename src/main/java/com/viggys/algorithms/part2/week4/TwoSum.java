package com.viggys.algorithms.part2.week4;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;

public class TwoSum {

    private static long COUNT = 0;
    private static int LOWER_RANGE = -10000;
    private static int UPPER_RANGE = 10000;

    public static void main(String[] args) {

        Date start = new Date();
        int length = 1000000;
        String path = "src/main/resources/2sum.txt";
        Set<Long> numbers = readData(path, length);
        execute(numbers);
        System.out.println("Count = " + COUNT);
        Date end = new Date();
        double time = (end.getTime() - start.getTime()) / (1000 * 60);
        System.out.println("Time in min = " + time);
    }

    private static Set readData(String path, int length) {
        Set<Long> numbers = new HashSet<>(length);
        try {
            File input = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(input));
            try {
                String line = reader.readLine();
                while (StringUtils.isNotEmpty(line)) {
                    Long number = Long.parseLong(line);
                    numbers.add(number);
                    line = reader.readLine();
                }
                System.out.println("Unique Elements = " + numbers.size());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                reader.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    /**
     * Not so efficient. Needs improvement.
     * @param numbers
     */
    private static void execute(Set<Long> numbers) {
        for(long i = LOWER_RANGE; i <= UPPER_RANGE; i++) {
            Iterator<Long> iter = numbers.iterator();
            while (iter.hasNext()) {
                Long x = iter.next();
                Long y = i - x;
                if(x != y && numbers.contains(y)) {
                    System.out.println(x + " + " + y + " = " + i);
                    COUNT++;
                    break;
                }
            }
        }
    }
}
