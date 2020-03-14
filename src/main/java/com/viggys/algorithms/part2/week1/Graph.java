package com.viggys.algorithms.part2.week1;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Stream;

public class Graph {
	
	public Graph(int totalVertices) {
		this.vertices = new Vertex[totalVertices];
		this.orderedVertices = new Vertex[totalVertices];
	}

	private Vertex[] vertices;
	private Vertex[] orderedVertices;

	@Override
	public String toString() {
		return "Graph [vertices=" + this.vertices.length;
	}

	public void addOrderedVertex(Vertex vertex) {
		this.orderedVertices[vertex.getFTime() - 1] = vertex;
	}

	public void addEdge(int tailId, int headId) {
		Vertex tail = getVertex(tailId);
		Vertex head = getVertex(headId);
		tail.addHead(head);
		head.addTail(tail);
	}
	
	private Vertex getVertex(int id) {
		if(vertices[id - 1] == null) {
			Vertex vertex = new Vertex(id);
			vertices[id - 1] = vertex;
//			System.out.println("Created Vertex " + vertex);
		}
		return vertices[id - 1];
	}

	public Vertex[] getVertices() {
		return vertices;
	}

	public Vertex[] getOrderedVertices() {
		return orderedVertices;
	}

	public void markUnexplored() {
		this.vertices = null;
		Stream.of(this.orderedVertices).forEach(vertex -> {
			vertex.setExplored(false);
			vertex.setTails(new ArrayList<>());
		});
	}
}
