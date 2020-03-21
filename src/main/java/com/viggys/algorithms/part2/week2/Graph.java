package com.viggys.algorithms.part2.week2;

import lombok.Getter;

public class Graph {

    @Getter
    private Vertex[] vertices;

    public Graph(int vertices) {
        this.vertices = new Vertex[vertices];
    }

    public Vertex getVertex(int id) {
        if(vertices[id - 1] == null) {
            vertices[id - 1] = new Vertex(id);
//            System.out.println("New " + vertices[id - 1]);
        }
        return vertices[id - 1];
    }

    public void addEdge(int sourceId, int targetId, long length) {
        if(sourceId < targetId) {
            Vertex source = getVertex(sourceId);
            Vertex target = getVertex(targetId);
            Edge edge = new Edge(length, source, target);
            source.addEdge(edge);
            target.addEdge(edge);
//            System.out.println("New " + edge);
        }
    }
}
