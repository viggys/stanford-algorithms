package com.viggys.algorithms.part3.week1.qs1and2;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigInteger;

public class GreedyJobScheduling {

    private static Job ROOT_JOB = null;
    private static BigInteger COMPLETION_TIME = BigInteger.ZERO;
    private static BigInteger WEIGHTED_COMPLETION_TIME = BigInteger.ZERO;

    public static void main(String[] args) {

        String path = "src/main/resources/jobs.txt";
        readData(path);
        calculateByDFS(ROOT_JOB);
//        System.out.println("COMPLETION TIME = " + COMPLETION_TIME);
        System.out.println("WEIGHTED COMPLETION TIME = " + WEIGHTED_COMPLETION_TIME);
    }

    private static void calculateByDFS(Job job) {
        if(job == null)
            return;

        calculateByDFS(job.getRightChild());

        COMPLETION_TIME = BigInteger.valueOf(job.getLength()).add(COMPLETION_TIME);
        BigInteger weightedCompletionTime = BigInteger.valueOf(job.getWeight()).multiply(COMPLETION_TIME);
        WEIGHTED_COMPLETION_TIME = WEIGHTED_COMPLETION_TIME.add(weightedCompletionTime);
        calculateByDFS(job.getLeftChild());
    }

    private static void readData(String path) {
        try {
            File inputFile = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();
            int jobs = Integer.parseInt(line);
            line = reader.readLine();
            while (StringUtils.isNotEmpty(line)) {
                String[] data = line.split("[ \t]");
                int weight = Integer.parseInt(data[0]);
                int length = Integer.parseInt(data[1]);
                Job job = new Job(weight,length);
                insert(job);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void insert(Job job) {
        if (ROOT_JOB == null) {
            ROOT_JOB = job;
        }
        else {
            ROOT_JOB.insert(job);
        }
    }

}
