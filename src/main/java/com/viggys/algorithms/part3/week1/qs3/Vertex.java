package com.viggys.algorithms.part3.week1.qs3;

import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter @ToString(of = { "id" })
@RequiredArgsConstructor
public class Vertex {

    @NonNull
    @Setter
    private int id;

    @Setter
    private Edge spanningEdge;

    @Setter
    private boolean isMarked = false;

    private List<Edge> edges = new ArrayList<>();

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }
}

class VertexComparator implements Comparator<Vertex> {

    private EdgeComparator comparator = new EdgeComparator();

    @Override
    public int compare(Vertex v1, Vertex v2) {
        return comparator.compare(v1.getSpanningEdge(), v2.getSpanningEdge());
    }

}