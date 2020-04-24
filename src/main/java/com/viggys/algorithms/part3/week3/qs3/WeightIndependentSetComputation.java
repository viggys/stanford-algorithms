package com.viggys.algorithms.part3.week3.qs3;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class WeightIndependentSetComputation {

    private static int VERTICES;
    private static Long[] WEIGHT_ARR;
    private static Long[] MAX_WEIGHT_ARR;

    public static void main(String[] args) {

        String path = "src/main/resources/mwis.txt";
        readData(path);
        compute();
        System.out.println("Max Weight = " + MAX_WEIGHT_ARR[VERTICES - 1]);
        boolean[] WIpresence = reconstruct();
        int[] verticesToCheck = new int[]{1, 2, 3, 4, 17, 117, 517, 997};
        for(int vertexId : verticesToCheck) {
            if (vertexId >= VERTICES)
                System.out.print(0);
            else if(WIpresence[vertexId - 1])
                System.out.print(1);
            else
                System.out.print(0);
        }
    }

    private static boolean[] reconstruct() {
        boolean[] WISpresence = new boolean[VERTICES];
        int vertexId = VERTICES - 1;
        while (vertexId > 1) {
            long maxWeightI_1 = MAX_WEIGHT_ARR[vertexId - 1];
            long maxWeightI_2 = MAX_WEIGHT_ARR[vertexId - 2];
            long weightI = WEIGHT_ARR[vertexId];
            if((weightI + maxWeightI_2) > maxWeightI_1) {
                WISpresence[vertexId] = true;
                vertexId--;
            }
            vertexId--;
        }
        if(vertexId == 0) {
            WISpresence[vertexId] = true;
        }
        else {
            if(MAX_WEIGHT_ARR[1] > MAX_WEIGHT_ARR[0])
                WISpresence[1] = true;
            else
                WISpresence[0] = true;
        }
        return WISpresence;
    }

    private static void compute() {
        int vertexId = 0;
        MAX_WEIGHT_ARR[vertexId] = WEIGHT_ARR[vertexId];
        vertexId++;
        if(WEIGHT_ARR[vertexId] > WEIGHT_ARR[vertexId - 1]) {
            MAX_WEIGHT_ARR[vertexId] = WEIGHT_ARR[vertexId];
        }
        else {
            MAX_WEIGHT_ARR[vertexId] = WEIGHT_ARR[vertexId - 1];
        }
        vertexId++;
        while (vertexId < VERTICES) {
            long maxWeightI_1 = MAX_WEIGHT_ARR[vertexId - 1];
            long maxWeightI_2 = MAX_WEIGHT_ARR[vertexId - 2];
            long weightI = WEIGHT_ARR[vertexId];
            if((weightI + maxWeightI_2) > maxWeightI_1) {
                MAX_WEIGHT_ARR[vertexId] = weightI + maxWeightI_2;
            }
            else {
                MAX_WEIGHT_ARR[vertexId] = maxWeightI_1;
            }
            vertexId++;
        }
    }

    private static void readData(String path) {
        try {
            File inputFile = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            try {
                String line = reader.readLine();
                VERTICES = Integer.parseInt(line);
                WEIGHT_ARR = new Long[VERTICES];
                MAX_WEIGHT_ARR = new Long[VERTICES];
                System.out.println("Number of vertices = " + VERTICES);
                line = reader.readLine();
                int index = 0;
                while (StringUtils.isNotEmpty(line)) {
                    long weight = Long.parseLong(line);
//                    System.out.println(index + " => " + weight);
                    WEIGHT_ARR[index] = weight;
                    line = reader.readLine();
                    index++;
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
