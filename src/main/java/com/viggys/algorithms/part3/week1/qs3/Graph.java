package com.viggys.algorithms.part3.week1.qs3;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Graph {

    private Vertex[] vertices;
    private List<Edge> edges;

    public Graph(int verticesNo, int edgesNo) {
        vertices = new Vertex[verticesNo];
        edges = new ArrayList<>(edgesNo);
    }

    public Vertex getVertex(int id) {
        Vertex vertex = vertices[id - 1];
        if (vertex == null) {
            vertex = new Vertex(id);
            vertices[id - 1] = vertex;
        }
        return vertex;
    }

    public void addEdge(Edge edge) {
        int id = this.edges.size() + 1;
        edge.setId(id);
        this.edges.add(edge);
    }


}
