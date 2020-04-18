package com.viggys.algorithms.part3.week2.qs2;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class BigClustering {

    private static int CLUSTERS = 0;
    private static int BIT_COUNT;
    private static Map<BitSet, Vertex> BITMAP = new HashMap<>();
    private static List<Vertex> VERTICES = new ArrayList<>();

    public static void main(String[] args) {

        String path = "src/main/resources/clustering_big.txt";
        readData(path);
        System.out.println("Clusters with 0-spacing = " + CLUSTERS);
        execute();
    }

    private static void execute() {
        for(Vertex vertex : VERTICES) {
            for(int index = 0; index < BIT_COUNT; index++) {
                BitSet possibility = (BitSet) vertex.getData().clone();
                possibility.flip(index);
                insertToMap(vertex, possibility);
            }
        }
        System.out.println("Clusters with 2-spacing = " + CLUSTERS);
    }

    private static void readData(String path) {
        try {
            File inputFile = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            try {
                String line = reader.readLine();
                String[] data = line.split("[ \t]");
                CLUSTERS = Integer.parseInt(data[0]);
                BIT_COUNT = Integer.parseInt(data[1]);
                System.out.println("Vertices = " + CLUSTERS + ", BitCount = " + BIT_COUNT);
                line = reader.readLine();
                int count = 0;
                while (StringUtils.isNotEmpty(line)) {
                    count++;
                    data = line.split("[ \t]");
//                    System.out.println(count + " => " + Arrays.toString(data));
                    Vertex vertex = new Vertex(count, BIT_COUNT);
                    vertex.setData(data);
                    VERTICES.add(vertex);
                    insertToMap(vertex, vertex.getData());
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

    private static void insertToMap(Vertex vertex, BitSet possibility) {
        if(BITMAP.containsKey(possibility)) {
            Vertex clusterVertex = BITMAP.get(possibility);
            Vertex leader1 = find(vertex);
            Vertex leader2 = find(clusterVertex);
            if(leader1 != leader2) {
                union(leader1, leader2);
                CLUSTERS--;
            }
        }
        else {
            BITMAP.put(possibility, vertex);
        }
    }

    private static Vertex find(Vertex vertex) {
        Vertex parent = vertex.getParent();
        while (parent != parent.getParent()) {
//            System.out.println("Parent of " + parent.getId() + " = " + parent.getParent().getId());
            parent = parent.getParent();
        }
        Vertex currentParent = vertex.getParent();
        while (currentParent != parent) {
            vertex.setParent(parent);
//            System.out.println("New Parent of " + vertex.getId() + " = " + vertex.getParent().getId());
            vertex = currentParent;
            currentParent = vertex.getParent();
        }
        return parent;
    }

    private static void union(Vertex leader1, Vertex leader2) {
        int rank1 = leader1.getRank();
        int rank2 = leader2.getRank();
        if(rank1 > rank2) {
            leader2.setParent(leader1);
//            System.out.println("CLUSTERS = " + CLUSTERS + ", " + leader2 + " merged into " + leader1);
        }
        else if(rank1 < rank2) {
            leader1.setParent(leader2);
//            System.out.println("CLUSTERS = " + CLUSTERS + ", " + leader1 + " merged into " + leader2);
        }
        else {
            leader2.setParent(leader1);
            leader1.setRank(rank1 + 1);
//            System.out.println("CLUSTERS = " + CLUSTERS + ", " + leader2 + " merged into " + leader1);
        }
    }
}
