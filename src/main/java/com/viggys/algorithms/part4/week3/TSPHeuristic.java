package com.viggys.algorithms.part4.week3;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class TSPHeuristic {

    private static int NODES;
    private static double TOUR_LENGTH = 0;
    private static Node[] NODE_LIST;
    private static Set<Integer> NODE_SET = new HashSet<>();
    private static List<Integer> NODE_TOUR = new ArrayList<>();
    private static Integer SOURCE_ID;
    private static Integer DEST_ID;

    public static void main(String[] args) {

        String path = "src/main/resources/nn.txt";
//        String path = "src/main/resources/test.txt";
        readData(path);
        compute();
        System.out.println("Tour Length = " + TOUR_LENGTH);
    }

    private static void compute() {
        double minSqrdDistance = -1;
        int minNodeId = 0;
        while (!NODE_SET.isEmpty()) {
            Iterator<Integer> iter = NODE_SET.iterator();
            minNodeId = 0;
            minSqrdDistance = -1;
            while (iter.hasNext()) {
                int id = iter.next();
                double sqrdDistance = getSquaredDistance(SOURCE_ID, id);
                if(minSqrdDistance < 0 || sqrdDistance < minSqrdDistance) {
                    minSqrdDistance = sqrdDistance;
                    minNodeId = id;
                }
                else if(sqrdDistance == minSqrdDistance) {
                    if(id < minNodeId) {
                        minNodeId = id;
                    }
                }
            }
            addToTour(minNodeId, minSqrdDistance);
            SOURCE_ID = minNodeId;
        }
        double sqrdDistance = getSquaredDistance(minNodeId, DEST_ID);
        addToTour(DEST_ID, sqrdDistance);
    }

    private static void addToTour(int minNodeId, double minSqrdDistance) {
        NODE_SET.remove(minNodeId);
        NODE_TOUR.add(minNodeId);
        TOUR_LENGTH = TOUR_LENGTH + Math.sqrt(minSqrdDistance);
    }

    private static double getSquaredDistance(int a, int b) {
        Node nodeA = NODE_LIST[a - 1];
        Node nodeB = NODE_LIST[b - 1];
        return Math.pow(nodeA.getX() - nodeB.getX(), 2) + Math.pow(nodeA.getY() - nodeB.getY(), 2);
    }

    private static double getDistance(int a, int b) {
        return Math.sqrt(getSquaredDistance(a, b));
    }

    private static void readData(String path) {
        try {
            File inputFile = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            try {
                String line = reader.readLine();
                NODES = Integer.parseInt(line);
                System.out.println("Total Nodes = " + NODES);
                NODE_LIST = new Node[NODES];
                line = reader.readLine();
                while (StringUtils.isNotEmpty(line)) {
                    String data[] = line.split("[ \t]");
                    int id = Integer.parseInt(data[0]);
                    double x = Double.parseDouble(data[1]);
                    double y = Double.parseDouble(data[2]);
//                    System.out.println(id + " => (" + x + ", " + y + ")");
                    Node node = new Node(id, x, y);
                    NODE_LIST[id - 1] = node;
                    if(id == 1) {
                        SOURCE_ID = id;
                        DEST_ID = id;
                    }
                    else {
                        NODE_SET.add(id);
                    }
                    line = reader.readLine();
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
