package com.viggys.algorithms.part4.week1;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
public class Edge {

    private Vertex source;
    private Vertex target;
    private int length;

    @Setter
    private int modLength;

    public Edge(@NonNull Vertex source, @NonNull Vertex target, @NonNull int length) {
        this.source = source;
        this.target = target;
        this.length = length;

        this.source.addOutgoingEdge(this);
        this.target.addIncomingEdge(this);
    }

}
