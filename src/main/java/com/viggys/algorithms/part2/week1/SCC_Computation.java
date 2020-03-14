package com.viggys.algorithms.part2.week1;

import com.viggys.algorithms.part1.week3.QuickSort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SCC_Computation {

    private static final String PATH = "src/main/resources/SCC.txt";
    private static final int LENGTH = 875714;

    private static int TIME = 0;
    private static Vertex LEADER = null;
    private static final int MAX_RECURSION_LEVEL = 2000;
    private static int CURRENT_RECURSION_LEVEL = 0;
    private static Vertex CURRENT_RECURSION_VERTEX = null;
    private static Stack<Vertex> RECURSION_HISTORY = new Stack<>();
    private static int[] LEADER_COUNT = new int[LENGTH];

    public static void main(String[] args) throws IOException {

        Long startTime = new Date().getTime();
        Graph graph = readGraphFromFile(PATH, LENGTH);
        System.out.println("Initial Graph");
//        System.out.println(Arrays.toString(graph.getVertices()));
        DFSLoop(graph, Direction.IN);
        System.out.println("Ordered Graph");
//        System.out.println(Arrays.toString(graph.getOrderedVertices()));
        graph.markUnexplored();
        DFSLoop(graph, Direction.OUT);
//        System.out.println("Total number of SCC = " + LEADER_COUNT.size());
//        System.out.println("Leader Count \n" + Arrays.toString(LEADER_COUNT));
        BubbleSort.sort(LEADER_COUNT, 5);
        Long endTime = new Date().getTime();
        double totalTime = ((double) (endTime - startTime) / (1000));
        System.out.println("Total Time = " + totalTime + " sec");
    }

    public static Graph readGraphFromFile(String path, int length) throws NumberFormatException, IOException {
        File inputFile = new File(path);
        Graph graph = new Graph(length);
		System.out.println(inputFile.getAbsolutePath());
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line = reader.readLine();
        while(line != null && !line.isEmpty()) {
            String[] vertices = line.split(" ");
            graph.addEdge(Integer.parseInt(vertices[0]), Integer.parseInt(vertices[1]));
            line = reader.readLine();
        }
        reader.close();
        return graph;
    }

    public static void DFSLoop(Graph graph, Direction dir) {
        Vertex[] vertices = null;
        LEADER = null;
        CURRENT_RECURSION_LEVEL = 0;

        if(dir == Direction.IN) {
            vertices = graph.getVertices();
        }
        else {
            vertices = graph.getOrderedVertices();
        }
        for(int i = vertices.length; i > 0; i--) {
            Vertex vertex = vertices[i - 1];
            CURRENT_RECURSION_VERTEX = null;
            if(!vertex.isExplored()) {
                vertex.setExplored(true);
                LEADER = vertex;
                while(CURRENT_RECURSION_VERTEX != vertex) {
                    CURRENT_RECURSION_VERTEX = LEADER;
                    CURRENT_RECURSION_LEVEL = 0;
                    clearHistory();
                    while(!DFS(graph, CURRENT_RECURSION_VERTEX, dir)) {
//                        System.out.println("Breaking recursion with CURRENT VERTEX - " + CURRENT_RECURSION_VERTEX);
                        CURRENT_RECURSION_LEVEL = 0;
                    }
                }
            }
        }
    }

    private static void clearHistory() {
        while(!RECURSION_HISTORY.isEmpty()) {
            RECURSION_HISTORY.pop().setExplored(false);
        }
    }

    private static boolean DFS(Graph graph, Vertex vertex, Direction dir) {
        CURRENT_RECURSION_LEVEL++;
//        System.out.println("Current Vertex - " + vertex + ", Current Recursion Level = " + CURRENT_RECURSION_LEVEL);
        if(CURRENT_RECURSION_LEVEL == MAX_RECURSION_LEVEL) {
            CURRENT_RECURSION_VERTEX = vertex;
            return false;
        }
        vertex.setExplored(true);
        RECURSION_HISTORY.push(vertex);
        if(dir == Direction.IN) {
            // First Loop - Reverse Graph
            List<Vertex> tails = vertex.getTails();
            for(Vertex tail : tails) {
                if(!tail.isExplored()) {
                    if(!DFS(graph, tail, dir)) {
                        return false;
                    }
                }
            }
            TIME++;
//            System.out.println("Order of " + vertex + " --> " + TIME);
            vertex.setFTime(TIME);
            graph.addOrderedVertex(vertex);
        }
        else {
            // Second Loop - Actual Graph
            List<Vertex> heads = vertex.getHeads();
            for(Vertex head : heads) {
                if(!head.isExplored()){
                    if(!DFS(graph, head, dir)) {
                        return false;
                    }
                }
            }
            vertex.setLeader(LEADER);
            updateLeaderCount(LEADER.getId());
//            System.out.println("Leader of " + vertex + " --> " + LEADER);
        }
        RECURSION_HISTORY.pop();
        return true;
    }

    private static void updateLeaderCount(int id) {
        /*if(LEADER_COUNT[id - 1] == null) {
            LEADER_COUNT[id - 1] = 0;
        }*/
        LEADER_COUNT[id - 1] = LEADER_COUNT[id - 1] + 1;
//        System.out.println("Leader = " + LEADER + ", Count = " + LEADER_COUNT_MAP.get(LEADER));
    }

}
