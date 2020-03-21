package com.viggys.algorithms.part2.week2;

import lombok.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data @Builder @ToString(of = { "id", "distance" })
@AllArgsConstructor
public class Vertex {

    @NonNull
    private int id;
    private boolean marked;
    private BigInteger distance;

    public Vertex(@NonNull int id) {
        this.id = id;
    }

    private List<Edge> edges = new ArrayList<>();

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

}
