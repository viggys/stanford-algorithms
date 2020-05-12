package com.viggys.algorithms.part4.week1;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Vertex {

    @NonNull private int id;
    private List<Edge> outgoingEdges = new ArrayList<>();
    private List<Edge> incomingEdges = new ArrayList<>();

    public void addOutgoingEdge(Edge edge) {
        outgoingEdges.add(edge);
    }

    public void addIncomingEdge(Edge edge) {
        incomingEdges.add(edge);
    }

}
