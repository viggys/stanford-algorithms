package com.viggys.algorithms.part4.week2;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ForkJoinTask;

public class TravellingSalesmanProblem {

    static int NODES;
    static Node SOURCE;
    static Node[] NODE_ARR;
    static Set<Node> NODE_SET = new HashSet<>();
    static Map<BitSet, float[]>[] HISTORY = new HashMap[2];

    public static void main(String[] args) {

        String path = "src/main/resources/tsp.txt";
//        String path = "src/main/resources/test.txt";
        Date start = new Date();
        readData(path);
        Set<BitSet>[] combinations = getCombinations();
        float distance = execute(combinations);
        Date end = new Date();
        System.out.println("Distance = " + distance);
        double time = (end.getTime() - start.getTime()) / (1000 * 60);
        System.out.println("Time taken = " + time);
    }

    private static float execute(Set<BitSet>[] combinations) {
        for(int size = 1; size <= NODE_ARR.length; size++) {
            System.out.println("Reached Problem Size = " + size + " at " + new Date());
            HISTORY[1] = new HashMap<>();
            List<ComputeAction> tasks = new ArrayList<>();
            for(BitSet subset : combinations[size - 1]) {
                HISTORY[1].put(subset, new float[NODE_ARR.length]);
                tasks.add(new ComputeAction(subset));
//                ComputeAction.execute(subset);
            }
            ForkJoinTask.invokeAll(tasks);
            HISTORY[0] = HISTORY[1];
        }

        float distance = -1;
        BitSet subset = new BitSet(NODE_ARR.length);
        subset.set(0, NODE_ARR.length, true);
        for(Node stop : NODE_ARR) {
            float stopToDest = getDistance(stop, SOURCE);
            float sourceToStop = HISTORY[1].get(subset)[stop.getId() - 2];
            if((sourceToStop + stopToDest) < distance || distance < 0) {
                distance = sourceToStop + stopToDest;
            }
        }
        return distance;
    }

    static float getDistance(Node a, Node b) {
       float distance = (float) Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
//       System.out.println("Distance between " + a + " and " + b + " = " + distance);
       return distance;
    }

    private static Set<BitSet>[] getCombinations() {
        Set<BitSet>[] combinations = new HashSet[NODES - 1];
        for(int i = 1; i <= NODE_ARR.length; i++) {
            Set<BitSet> bitSets = new HashSet<>();
            for(Set<Node> set : Sets.combinations(Set.of(NODE_ARR), i)) {
                BitSet bitSet = new BitSet(NODES - 1);
                for(Node node : set) {
                    bitSet.flip(node.getId() - 2);
                }
                bitSets.add(bitSet);
            }
//            System.out.println("Combinations of size " + i);
//            System.out.println("No. of combinations = " + bitSets.size());
//            System.out.println("----------------------------------------");
            combinations[i - 1] = bitSets;
        }
        return combinations;
    }

    private static void readData(String path) {
        try {
            File inputFile = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            try {
                String line = reader.readLine();
                NODES = Integer.parseInt(line);
                System.out.println("Total Nodes = " + NODES);
                NODE_ARR = new Node[NODES - 1];
                line = reader.readLine();
                int count = 0;
                while (StringUtils.isNotEmpty(line)) {
                    count++;
                    String data[] = line.split("[ \t]");
                    float x = Float.parseFloat(data[0]);
                    float y = Float.parseFloat(data[1]);
//                    System.out.printf("%d -> x = %f, y = %f%n", count, x, y);
                    Node node = new Node(count, x, y);
                    if(count == 1)
                        SOURCE = node;
                    else {
                        NODE_ARR[count - 2] = node;
                    }
                    line = reader.readLine();
                }
//                System.out.println(Arrays.toString(NODE_ARR));
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
