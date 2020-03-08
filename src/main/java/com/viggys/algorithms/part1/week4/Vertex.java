package com.viggys.algorithms.part1.week4;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

	public int id;
	
	public Vertex(int id) {
		this.id = id;
	}
	
	private List<Edge> edges = new ArrayList<>();

	public List<Edge> getEdges() {
		return edges;
	}

	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}

	@Override
	public String toString() {
		return "V[" + id + "]";
	}
	
	
}
