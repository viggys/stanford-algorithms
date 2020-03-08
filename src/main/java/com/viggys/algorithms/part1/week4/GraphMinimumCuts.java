package com.viggys.algorithms.part1.week4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class GraphMinimumCuts {

	public static void main(String[] args) throws NumberFormatException, IOException {

		String path = "src/main/resources/kargerMinCut.txt";
		int length = 200;
		int minimumCuts = 0;
		for(int i = 0; i < 100; i++) {
			Graph graph = readGraphFromFile(path, length);
			while(graph.getActiveVertices() > 2) {
				contractGraph(graph);
			}
			int cuts = graph.getActiveEdges();
			System.out.println("Cuts in Graph= " + cuts);
			if(cuts < minimumCuts || minimumCuts == 0) {
				minimumCuts = cuts;
			}
//			graph.getEdges().stream().forEach(e -> {
//				System.out.println(e);
//			});
		}
		System.out.println("Minimum cuts in graph = " + minimumCuts);
		
	}
	
	private static void contractGraph(Graph graph) {
		int edges = graph.getActiveEdges();
		int contractIndex = selectRandomIndex(edges);
		Vertex contractedVertex = graph.contractEdge(contractIndex);
		graph.removeSelfLoops(contractedVertex);
	}
	
	public static Graph readGraphFromFile(String path, int length) throws NumberFormatException, IOException {
		File inputFile = new File(path);
		Graph graph = new Graph(length);
//		System.out.println(inputFile.getAbsolutePath());
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		String line = reader.readLine();
		while(line != null && !line.isEmpty()) {
			String[] vertices = line.split("\t");
			for(int i = 1; i < vertices.length; i++) {
				graph.addEdge(Integer.parseInt(vertices[0]), Integer.parseInt(vertices[i]));
			}
			line = reader.readLine();
		}
		reader.close();
		return graph;
	}
	
	private static int selectRandomIndex(int length) {
		Random random = new Random();
		return random.nextInt(length);
	}

}
