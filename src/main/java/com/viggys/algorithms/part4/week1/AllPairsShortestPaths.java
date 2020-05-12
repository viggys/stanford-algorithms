package com.viggys.algorithms.part4.week1;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class AllPairsShortestPaths {

    private static int VERTICES;
    private static int EDGES;
    private static boolean IS_ANY_LENGTH_NEGATIVE;
    private static boolean HAS_NEGATIVE_CYCLE;
    private static Graph GRAPH;
    private static int TO_INFINITY_AND_BEYOND = 99999;

    public static void main(String[] args) {

        String[] inputs = new String[] {"g1.txt", "g2.txt", "g3.txt"};
        for(String input : inputs) {
            String path = "src/main/resources/" + input;
            readData(path);
            System.out.println("IS_ANY_LENGTH_NEGATIVE = " + IS_ANY_LENGTH_NEGATIVE);
            Integer[] distances = new Integer[VERTICES];
            if(IS_ANY_LENGTH_NEGATIVE) {
                System.out.println("Executing Bellman-Ford");
                distances = executeBellmanFord();
            }
            if(!HAS_NEGATIVE_CYCLE) {
                int shortestDistance = findSmallestWeightVertex(distances);
                System.out.println("Found Shortest Path Distance = " + shortestDistance);
            }
            else {
                System.out.println("Result = NULL");
            }
            System.out.println("---------------------------------");
        }
    }

    private static int findSmallestWeightVertex(Integer[] distances) {
        int smallestDistance = 0;
        int vertexId = 0;
        for(int i = 0; i < distances.length; i++) {
            Integer distance = distances[i];
            if(distance < smallestDistance) {
                smallestDistance = distance;
                vertexId = i + 1;
            }
        }
        System.out.println("Smallest weight = " + smallestDistance + ", vertex = " + vertexId);
        return smallestDistance;
    }

    private static Integer[] executeBellmanFord() {
        Integer[] distances = new Integer[VERTICES];
        for(int i = 0; i < VERTICES; i++) {
            distances[i] = 0;
        }
        boolean hasImproved = true;
        for(int id = 1; id <= VERTICES; id++) {
            if(hasImproved) {
//                System.out.println("Iteration number = " + id);
                hasImproved = compute(distances);
            }
            else {
                HAS_NEGATIVE_CYCLE = false;
                break;
            }
        }
        if(hasImproved) {
            System.out.println("Checking for negative cycle");
            if(compute(distances)) {
                System.out.println("Detected Negative Cycle");
                HAS_NEGATIVE_CYCLE = true;
            }
        }

        return distances;
    }

    private static boolean compute(Integer[] distances) {
        boolean hasImproved = false;
        Integer[] currentDistances = new Integer[VERTICES];
//        System.out.println("Start Previous distances = " + Arrays.toString(distances));
//        System.out.println("Start Current distances = " + Arrays.toString(currentDistances));
        for(Vertex vertex : GRAPH.getVertices()) {
            int previousDistance = distances[vertex.getId() - 1];
//            System.out.println("Previous Distance of " + vertex.getId() + " = " + previousDistance);
            int closerDistance = previousDistance;
            for(Edge edge : vertex.getIncomingEdges()) {
                int sourceId = edge.getSource().getId();
                int length = edge.getLength();
                int distance = distances[sourceId - 1] + length;
//                System.out.println(sourceId + "-->" + vertex.getId() + " = " + distance);
                if(distance < closerDistance) {
                    closerDistance = distance;
                }
            }
            int currentDistance = closerDistance;
//            System.out.println("Current Distance of " + vertex.getId() + " = " + currentDistance);
            currentDistances[vertex.getId() - 1] = currentDistance;
            if(currentDistance == previousDistance) {
                hasImproved = (false || hasImproved);
            }
            else {
                hasImproved = true;
            }
//            System.out.println("hasImproved = " + hasImproved);
        }
        for(int i = 0; i < VERTICES; i++) {
            distances[i] = currentDistances[i];
        }
//        System.out.println("End Previous distances = " + Arrays.toString(distances));
//        System.out.println("End Current distances = " + Arrays.toString(currentDistances));
        return hasImproved;
    }

    private static void readData(String path) {
        try {
            IS_ANY_LENGTH_NEGATIVE = false;
            HAS_NEGATIVE_CYCLE = false;
            File inputFile = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            try {
                String line = reader.readLine();
                String[] data = line.split("[ \t]");
                VERTICES = Integer.parseInt(data[0]);
                EDGES =  Integer.parseInt(data[1]);
                System.out.println("Vertices = " + VERTICES + ", Edges = " + EDGES);
                GRAPH = new Graph(VERTICES);
                line = reader.readLine();
                while (StringUtils.isNotEmpty(line)) {
                    data = line.split("[ \t]");
                    int sourceId = Integer.parseInt(data[0]);
                    int targetId = Integer.parseInt(data[1]);
                    int length = Integer.parseInt(data[2]);
                    if(length < 0) {
                        IS_ANY_LENGTH_NEGATIVE = true;
                    }
                    Vertex source = GRAPH.getVertex(sourceId);
                    Vertex target = GRAPH.getVertex(targetId);
                    Edge edge = new Edge(source, target, length);
                    GRAPH.getEdges().add(edge);
//                    System.out.println(sourceId + " --" + length + "--> " + targetId);
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
