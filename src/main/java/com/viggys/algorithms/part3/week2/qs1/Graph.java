package com.viggys.algorithms.part3.week2.qs1;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Getter
public class Graph {

    private Vertex[] vertices;
    private List<Edge> edges = new ArrayList<>();


    Graph(int vertices) {
        this.vertices = new Vertex[vertices];
    }

    public Vertex getVertex(int id) {
        Vertex vertex = vertices[id - 1];
        if(vertex == null) {
            vertex = new Vertex(id);
            vertices[id - 1] = vertex;
        }
        return vertex;
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }
}
