package com.viggys.algorithms.part4.week1;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Graph {

    private Vertex[] vertices;

    private List<Edge> edges = new ArrayList<>();

    public Graph(@NonNull int vertices) {
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


}
