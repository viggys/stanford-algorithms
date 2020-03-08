package com.viggys.algorithms.part1.week4;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Graph {
	
	public Graph(int totalVertices) {
		this.vertices = new Vertex[totalVertices];
	}
	
	private int activeVertices = 0;

	private Vertex[] vertices;
	
	private List<Edge> edges = new ArrayList<Edge>();

	@Override
	public String toString() {
		return "Graph [vertices=" + activeVertices + ", edges=" + edges.size() + "]";
	}
	
	public int getActiveVertices() {
		return activeVertices;
	}
	
	public int getActiveEdges() {
		return edges.size();
	}
	
	public void addEdge(int sourceId, int targetId) {
		if(sourceId < targetId) {
			Edge edge = new Edge(edges.size() + 1);
			edge.setSource(getVertex(sourceId));
			edge.setTarget(getVertex(targetId));
			edges.add(edge);
//			System.out.println("Created Edge " + edge);
		}
	}
	
	public Vertex contractEdge(int index) {
		try {
			Edge edge = edges.remove(index);
			Vertex source = edge.getSource();
			Vertex target = edge.getTarget();
//			System.out.println("Contracting edge " + edge);
			source.getEdges().remove(edge);
			target.getEdges().remove(edge);
			for(Edge e : target.getEdges()) {
				if(e.getSource() == target) {
					e.setSource(source);
				}
				else {
					e.setTarget(source);
				}
				//			System.out.println("Removed edge from target = " + target.getEdges().remove(e));
//				System.out.println("Cleaning target " + target);
//				System.out.println("Modifying Edge " + e);
//				System.out.println("Added edge to source = " + source.getEdges().add(e));
			}
			vertices[target.id - 1] = null;
//			System.out.println("Removed vertex " + target);
			activeVertices--;
			return source;
		}
		catch(ConcurrentModificationException e) {
			throw e;
		}
	}
	
	public void removeSelfLoops(Vertex vertex) {
		for(int i = 0; i < vertex.getEdges().size(); i++) {
			Edge e = vertex.getEdges().get(i);
//			System.out.println("Checking for self-loop " + e + " = " + (e.getSource() == e.getTarget()));
			if(e.getSource() == e.getTarget()) {
				edges.remove(e);
				vertex.getEdges().remove(i);
				i--;
//				System.out.println("Removed self-loop " + e);
			}
		}
	}
	
	private Vertex getVertex(int id) {
		if(vertices[id - 1] == null) {
			Vertex vertex = new Vertex(id);
			vertices[id - 1] = vertex;
//			System.out.println("Created Vertex " + vertex);
			activeVertices++;
		}
		return vertices[id - 1];
	}

	public List<Edge> getEdges() {
		return edges;
	}
	
	
}
