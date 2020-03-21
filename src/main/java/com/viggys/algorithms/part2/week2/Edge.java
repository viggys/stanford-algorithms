package com.viggys.algorithms.part2.week2;

import lombok.*;

@ToString(of = { "id", "length" })
@Getter @Setter
public class Edge {

    private static int UUID = 1;

    private int id;
    private long length;
    private Vertex source;
    private Vertex target;

    public Edge(long length, Vertex source, Vertex target) {
        this.id = UUID++;
        this.length = length;
        this.source = source;
        this.target = target;
    }

    public Vertex getVertexConnectedTo(Vertex vertex) {
        if (vertex == source)
            return target;
        else if(vertex == target)
            return source;
        throw new IllegalArgumentException(vertex + " is not connected by " + this);
    }

    public Vertex getMarkedVertex() {
        if(source.isMarked())
            return source;
        else
            return target;
    }
}
