package com.viggys.algorithms.part1.week4;

public class Edge {

	public int id;
	
	public Edge(int id) {
		this.id = id;
	}
	
	private Vertex source;
	
	private Vertex target;
	
	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
		source.addEdge(this);
	}

	public Vertex getTarget() {
		return target;
	}

	public void setTarget(Vertex target) {
		this.target = target;
		target.addEdge(this);
	}

	@Override
	public String toString() {
		return source + "--E[" + id + "]-->" + target;
	}
	
}
