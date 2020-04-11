package com.viggys.algorithms.part3.week1.qs3;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimsMST {

    private static int NODES;
    private static int EDGES;
    private static Graph GRAPH;
    private static Heap HEAP = new Heap(HeapType.MIN);
    private static List<Edge> SPANNING_TREE_EDGES = new ArrayList<>();
    private static List<Edge> FRONTIER_EDGES = new ArrayList<>();

    public static void main(String[] args) {

        String path = "src/main/resources/edges.txt";
        readData(path);
        insertIntoHeap();
//        System.out.println("Heap = " + HEAP);
        int startVertexId = new Random().nextInt(NODES - 1) + 1;
        Vertex vertex = GRAPH.getVertex(startVertexId);
        HEAP.remove(vertex);
        span(vertex);
        int cost = 0;
        for(Edge edge : SPANNING_TREE_EDGES) {
            cost = cost + edge.getCost();
        }
        System.out.println("No. of Spanning Tree Edges = " + SPANNING_TREE_EDGES.size());
        System.out.println("Minimum Spanning Cost = " + cost);
    }

    private static void insertIntoHeap() {
        for(Vertex vertex : GRAPH.getVertices()) {
            HEAP.insert(vertex);
        }
    }

    private static void span(Vertex vertex) {
        while(vertex != null) {
            vertex.setMarked(true);
            Edge spanningEdge = vertex.getSpanningEdge();
            if(spanningEdge != null) {
                SPANNING_TREE_EDGES.add(spanningEdge);
            }
            updateFrontier(vertex.getEdges());
            heapifyFrontier();
            vertex = HEAP.pop();
        }
    }

    private static void heapifyFrontier() {
        for(Edge edge : FRONTIER_EDGES) {
            Vertex vertex = edge.getNeighbourOf(edge.getMarkedVertex());
            HEAP.remove(vertex);
            vertex.setSpanningEdge(getMinimumCostEdge(vertex));
            HEAP.insert(vertex);
        }
    }

    private static void updateFrontier(List<Edge> edges) {
        for(Edge edge : edges) {
            if(FRONTIER_EDGES.contains(edge)) {
                FRONTIER_EDGES.remove(edge);
            }
            else {
                if(!edge.isSelfEdge()) {
                    FRONTIER_EDGES.add(edge);
                }
            }
        }
    }

    private static Edge getMinimumCostEdge(Vertex vertex) {
        Edge requiredEdge = null;
        EdgeComparator comparator = new EdgeComparator();
        for(Edge edge : vertex.getEdges()) {
            if (!edge.isSelfEdge() && FRONTIER_EDGES.contains(edge) && comparator.compare(edge, requiredEdge) < 0) {
                requiredEdge = edge;
            }
        }
        return requiredEdge;
    }

    private static void readData(String path) {
        try {
            File inputFile = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();
            String[] data = line.split("[ \t]");
            NODES = Integer.parseInt(data[0]);
            EDGES = Integer.parseInt(data[1]);
            System.out.println("Nodes = " + NODES);
            System.out.println("Edges = " + EDGES);
            GRAPH = new Graph(NODES, EDGES);
            line = reader.readLine();
            while (StringUtils.isNotEmpty(line)) {
                data = line.split("[ \t]");
                int sourceId = Integer.parseInt(data[0]);
                int targetId = Integer.parseInt(data[1]);
                int edgeCost = Integer.parseInt(data[2]);
                Edge edge = new Edge(edgeCost);
                edge.setSource(GRAPH.getVertex(sourceId));
                edge.setTarget(GRAPH.getVertex(targetId));
                GRAPH.addEdge(edge);

                line = reader.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
