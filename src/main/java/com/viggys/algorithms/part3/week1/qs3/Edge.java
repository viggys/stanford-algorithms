package com.viggys.algorithms.part3.week1.qs3;

import lombok.*;

import java.util.Comparator;

@RequiredArgsConstructor
@Getter
@ToString(of = { "id", "cost", "source", "target" })
public class Edge {

    @Setter
    private int id;
    @NonNull private int cost;
    private Vertex source;
    private Vertex target;

    public void setSource(Vertex source) {
        this.source = source;
        source.addEdge(this);
    }

    public void setTarget(Vertex target) {
        this.target = target;
        target.addEdge(this);
    }

    public Vertex getMarkedVertex() {
        if(source.isMarked())
            return source;
        else
            return target;
    }

    public Vertex getNeighbourOf(Vertex vertex) {
        if(vertex == source) {
            return target;
        }
        else {
            return source;
        }
    }

    public boolean isSelfEdge() {
        return  (source == target);
    }
}

class EdgeComparator implements Comparator<Edge> {

    @Override
    public int compare(Edge e1, Edge e2) {
        if(e1 != null && e2 != null) {
            return ((Integer) e1.getCost()).compareTo((Integer) e2.getCost());
        }
        else {
            if(e1 != null)
                return -1;
            else if(e2 != null)
                return 1;
            else
                return 0;
        }
    }
}