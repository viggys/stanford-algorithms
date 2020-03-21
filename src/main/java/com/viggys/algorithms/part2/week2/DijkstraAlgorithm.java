package com.viggys.algorithms.part2.week2;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DijkstraAlgorithm {

    public static void main(String[] args) throws IOException {

        String path = "src/main/resources/dijkstraData.txt";
        int vertices = 200;
        Graph graph = readGraphFromFile(path, vertices);
        Vertex startVertex = graph.getVertex(1);
        startVertex.setMarked(true);
        startVertex.setDistance(BigInteger.ZERO);
        List<Edge> frontLineEdges = new ArrayList<>(startVertex.getEdges());
        execute(frontLineEdges);
        System.out.println("----------------------------");
        int[] ids = new int[] {7,37,59,82,99,115,133,165,188,197};
        String separator = "";
        for(int id: ids) {
            System.out.print(separator + graph.getVertex(id).getDistance());
//            System.out.println(graph.getVertex(id));
            separator = ",";
        }
    }

    private static void execute(List<Edge> frontLineEdges) {
        while (!frontLineEdges.isEmpty()) {
            BigInteger minDistance = null;
            Edge edgeToTraverse = null;
            for (Edge edge: frontLineEdges) {
                BigInteger distanceTraversed = edge.getMarkedVertex().getDistance();
                BigInteger stepLength = BigInteger.valueOf(edge.getLength());
                BigInteger distance = distanceTraversed.add(stepLength);
//                System.out.println("Step = " + stepLength + ", Distance = " + distance);
                if(minDistance == null || minDistance.compareTo(distance) > 0) {
                    minDistance = distance;
                    edgeToTraverse = edge;
                    System.out.println("Updating Minimum Distance = " + minDistance);
                }
            }
            Vertex source = edgeToTraverse.getMarkedVertex();
            Vertex target = edgeToTraverse.getVertexConnectedTo(source);
            target.setMarked(true);
            target.setDistance(minDistance);
            updateFrontLineEdges(target, frontLineEdges);
        }
    }

    public static void updateFrontLineEdges(Vertex target, List<Edge> frontLineEdges) {
        for (Edge edge : target.getEdges()) {
            if(edge.getVertexConnectedTo(target).isMarked()) {
                if(frontLineEdges.contains(edge)) {
                    frontLineEdges.remove(edge);
                    System.out.println("Removed edge from frontline " + edge);
                }
            }
            else {
                frontLineEdges.add(edge);
                System.out.println("Added edge to frontline " + edge);
            }
        }
    }

    private static Graph readGraphFromFile(String path, int vertices) throws IOException {
        File inputFile = new File(path);
        Graph graph = new Graph(vertices);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line = reader.readLine();
        while(StringUtils.isNotEmpty(line)) {
            String[] values = line.split("\t");
            int sourceId = Integer.parseInt(values[0]);
            for(int i = 1; i < values.length; i++) {
                String[] connection = values[i].split(",");
                int targetId = Integer.parseInt(connection[0]);
                long length = Long.parseLong(connection[1]);
                graph.addEdge(sourceId, targetId, length);
            }
            line = reader.readLine();
        }
        return graph;
    }

}
