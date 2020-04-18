package com.viggys.algorithms.part3.week2.qs1;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class SimpleClustering {

    private static Graph GRAPH;
    private static int EDGE_COUNT = 0;
    private static int TARGET_CLUSTERS = 4;
    private static PriorityQueue<Edge> MAX_EDGES = new PriorityQueue<Edge>(new EdgeComparator());
    private static int CLUSTERS = 0;

    public static void main(String[] args) {

        String path = "src/main/resources/clustering.txt";
        readData(path);
        System.out.println("Total edges = " + EDGE_COUNT + " Heap Size = " + MAX_EDGES.size());
        Edge edge = execute();
        System.out.println(CLUSTERS + "-clustering Max Spacing = " + edge.getEdgeCost());
    }

    private static Edge execute() {
        while (CLUSTERS >= TARGET_CLUSTERS) {
            Edge edge = MAX_EDGES.poll();
            Vertex source = edge.getSource();
            Vertex target = edge.getTarget();
            Vertex leaderSource = find(source);
            Vertex leaderTarget = find(target);
            if(leaderSource != leaderTarget) {
                if(CLUSTERS == TARGET_CLUSTERS) return edge;
                else {
                    union(leaderSource, leaderTarget);
                    CLUSTERS--;
                }
            }
        }
        return null;
    }

    private static Vertex find(Vertex vertex) {
        Vertex parent = vertex.getParent();
        while (parent != parent.getParent()) {
            System.out.println("Parent of " + parent.getId() + " = " + parent.getParent().getId());
            parent = parent.getParent();
        }
        Vertex currentParent = vertex.getParent();
        while (currentParent != parent) {
            vertex.setParent(parent);
            System.out.println("New Parent of " + vertex.getId() + " = " + vertex.getParent().getId());
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
            System.out.println("CLUSTERS = " + CLUSTERS + ", " + leader2 + " merged into " + leader1);
        }
        else if(rank1 < rank2) {
            leader1.setParent(leader2);
            System.out.println("CLUSTERS = " + CLUSTERS + ", " + leader1 + " merged into " + leader2);
        }
        else {
            leader2.setParent(leader1);
            leader1.setRank(rank1 + 1);
            System.out.println("CLUSTERS = " + CLUSTERS + ", " + leader2 + " merged into " + leader1);
        }
    }

    private static void readData(String path) {
        try {
            File inputFile = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            try {
                String line = reader.readLine();
                int vertices = Integer.parseInt(line);
                System.out.println("Number of vertices = " + vertices);
                CLUSTERS = vertices;
                GRAPH = new Graph(vertices);
                line = reader.readLine();
                while (StringUtils.isNotEmpty(line)) {
                    EDGE_COUNT++;
                    String[] data = line.split("[ \t]");
                    int sourceId = Integer.parseInt(data[0]);
                    int targetId = Integer.parseInt(data[1]);
                    int edgeCost = Integer.parseInt(data[2]);
//                    System.out.println(sourceId + "\t" + targetId + "\t" + edgeCost);
                    Vertex source = GRAPH.getVertex(sourceId);
                    Vertex target = GRAPH.getVertex(targetId);
                    Edge edge = new Edge(EDGE_COUNT, edgeCost, source, target);
                    GRAPH.addEdge(edge);
                    MAX_EDGES.add(edge);
                    line = reader.readLine();
                }
            }
            finally {
                reader.close();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
