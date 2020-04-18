package com.viggys.algorithms.part3.week2.qs1;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter @ToString(of = { "id", "rank" })
public class Vertex {

    @NonNull
    private int id;

    @Setter
    private int rank = 0;

    @Setter
    private Vertex parent = this;

    private List<Edge> edges = new ArrayList<>();

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

}
