package com.viggys.algorithms.part3.week2.qs1;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString(of = { "id", "edgeCost" })
public class Edge {

    @NonNull
    private int id;

    @NonNull
    private int edgeCost;

    @NonNull
    private Vertex source;

    @NonNull
    private Vertex target;

    public Edge(int id, int edgeCost, Vertex source, Vertex target) {
        this.id = id;
        this.edgeCost = edgeCost;
        this.source = source;
        this.target = target;
        this.source.addEdge(this);
        this.target.addEdge(this);
    }

}
